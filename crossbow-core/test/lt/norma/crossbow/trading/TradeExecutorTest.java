/*
 * Copyright 2009, 2010 Vilius Normantas <code@norma.lt>
 * 
 * This file is part of Crossbow trading library.
 * 
 * Crossbow is free software: you can redistribute it and/or modify it under the terms of the GNU 
 * General Public License as published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * Crossbow is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU 
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with Crossbow.  If not, 
 * see <http://www.gnu.org/licenses/>.
 */

package lt.norma.crossbow.trading;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import lt.norma.crossbow.contracts.Contract;
import lt.norma.crossbow.contracts.Currency;
import lt.norma.crossbow.contracts.Exchange;
import lt.norma.crossbow.contracts.StockContract;
import lt.norma.crossbow.exceptions.ContractException;
import lt.norma.crossbow.exceptions.OrderException;
import lt.norma.crossbow.orders.ExecutionReport;
import lt.norma.crossbow.orders.Order;
import lt.norma.crossbow.orders.OrderDirection;

/**
 * @author Vilius Normantas <code@norma.lt>
 */
public class TradeExecutorTest
{
   /**
    * @throws OrderException
    * @throws ContractException
    */
   @Test
   public void testOrderUpdatedEvent() throws OrderException, ContractException
   {
      Currency currency = Currency.createJpy();
      Exchange exchange = Exchange.createNasdaq();
      StockContract c = new StockContract("ABC", exchange, currency);
      MockOrder o1 = new MockOrder(55, c, "MYORDER", OrderDirection.SELL, 800);
      MockOrder o2 = new MockOrder(55, c, "MYORDER", OrderDirection.SELL, 800);
      
      MockTradeExecutor executor = new MockTradeExecutor();
      MockTradeExecutorListener listener1 = new MockTradeExecutorListener();
      MockTradeExecutorListener listener2 = new MockTradeExecutorListener();
      MockTradeExecutorListener listener3 = new MockTradeExecutorListener();
      executor.fireOrderUpdate(o1);
      executor.fireOrderUpdate(o2);
      // Nobody received the quotes as no listeners added
      assertNull(listener1.lastOrder);
      assertNull(listener1.lastOrderUpdatedSender);
      assertNull(listener2.lastOrder);
      assertNull(listener2.lastOrderUpdatedSender);
      assertNull(listener3.lastOrder);
      assertNull(listener3.lastOrderUpdatedSender);
      // Add listener1 and listener2
      executor.addListener(listener1);
      executor.addListener(listener2);
      executor.fireOrderUpdate(o1);
      assertEquals(o1, listener1.lastOrder);
      assertNotNull(listener1.lastOrderUpdatedSender);
      assertEquals(o1, listener2.lastOrder);
      assertNotNull(listener2.lastOrderUpdatedSender);
      assertNull(listener3.lastOrder);
      assertNull(listener3.lastOrderUpdatedSender);
      // Remove listener2
      executor.removeListener(listener2);
      executor.fireOrderUpdate(o2);
      assertEquals(o2, listener1.lastOrder);
      assertNotNull(listener1.lastOrderUpdatedSender);
      assertEquals(o1, listener2.lastOrder);
      assertNotNull(listener2.lastOrderUpdatedSender);
      assertNull(listener3.lastOrder);
      assertNull(listener3.lastOrderUpdatedSender);
   }
   
   /**
    * @throws OrderException
    * @throws ContractException
    */
   @Test
   public void testOrderExecutedEvent() throws OrderException, ContractException
   {
      Currency currency = Currency.createJpy();
      Exchange exchange = Exchange.createNasdaq();
      StockContract c = new StockContract("ABC", exchange, currency);
      MockOrder o = new MockOrder(55, c, "MYORDER", OrderDirection.SELL, 800);
      ExecutionReport r1 = new ExecutionReport(o);
      ExecutionReport r2 = new ExecutionReport(o);
      
      MockTradeExecutor executor = new MockTradeExecutor();
      MockTradeExecutorListener listener1 = new MockTradeExecutorListener();
      MockTradeExecutorListener listener2 = new MockTradeExecutorListener();
      MockTradeExecutorListener listener3 = new MockTradeExecutorListener();
      executor.fireOrderExecution(r1);
      executor.fireOrderExecution(r2);
      // Nobody received the quotes as no listeners added
      assertNull(listener1.lastExecutionReport);
      assertNull(listener1.lastExecutedSender);
      assertNull(listener2.lastExecutionReport);
      assertNull(listener2.lastExecutedSender);
      assertNull(listener3.lastExecutionReport);
      assertNull(listener3.lastExecutedSender);
      // Add listener1 and listener2
      executor.addListener(listener1);
      executor.addListener(listener2);
      executor.fireOrderExecution(r1);
      assertEquals(r1, listener1.lastExecutionReport);
      assertNotNull(listener1.lastExecutedSender);
      assertEquals(r1, listener2.lastExecutionReport);
      assertNotNull(listener2.lastExecutedSender);
      assertNull(listener3.lastExecutionReport);
      assertNull(listener3.lastExecutedSender);
      // Remove listener2
      executor.removeListener(listener2);
      executor.fireOrderExecution(r2);
      assertEquals(r2, listener1.lastExecutionReport);
      assertNotNull(listener1.lastExecutedSender);
      assertEquals(r1, listener2.lastExecutionReport);
      assertNotNull(listener2.lastExecutedSender);
      assertNull(listener3.lastExecutionReport);
      assertNull(listener3.lastExecutedSender);
   }
   
   /**
    * Mock trade executor listener.
    * 
    * @author Vilius Normantas <code@norma.lt>
    */
   private class MockTradeExecutorListener implements TradeExecutorListener
   {
      public Order lastOrder;
      public Object lastOrderUpdatedSender;
      public ExecutionReport lastExecutionReport;
      public Object lastExecutedSender;
      
      @Override
      public void orderUpdated(OrderUpdatedEvent event)
      {
         lastOrder = event.getOrder();
         lastOrderUpdatedSender = event.getSource();
      }
      
      @Override
      public void orderExecuted(OrderExecutedEvent event)
      {
         lastExecutionReport = event.getExecutionReport();
         lastExecutedSender = event.getSource();
      }
   }
   
   /**
    * Mock trade executor.
    * 
    * @author Vilius Normantas <code@norma.lt>
    */
   private class MockTradeExecutor extends TradeExecutor
   {
      /**
       * @param order
       */
      public void fireOrderUpdate(Order order)
      {
         fireOrderUpdatedEvent(order);
      }
      
      public void fireOrderExecution(ExecutionReport report)
      {
         fireOrderExecutedEvent(report);
      }
      
      @Override
      public void cancelAllOrders()
      {
      }
      
      @Override
      public void cancelAllOrdersByContract(Contract contract)
      {
      }
      
      @Override
      public void cancelOrder(Order order)
      {
      }
      
      @Override
      public boolean isReady()
      {
         return true;
      }
      
      @Override
      public void sendOrder(Order order)
      {
      }
   }
   
   /**
    * Mock order.
    */
   private class MockOrder extends Order
   {
      public MockOrder(long id, Contract contract, String type, OrderDirection direction, int size)
            throws OrderException
      {
         super(id, contract, type, direction, size);
      }
   }
}
