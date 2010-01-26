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

package lt.norma.crossbow.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;

import lt.norma.crossbow.contracts.Contract;
import lt.norma.crossbow.contracts.Exchange;
import lt.norma.crossbow.contracts.StockContract;
import lt.norma.crossbow.exceptions.OrderException;
import lt.norma.crossbow.orders.Direction;
import lt.norma.crossbow.orders.Order;
import lt.norma.crossbow.trading.ExecutionReport;
import lt.norma.crossbow.trading.FilledBlock;
import lt.norma.crossbow.trading.OrderExecutedEvent;
import lt.norma.crossbow.trading.TradeExecutor;
import lt.norma.testutilities.Reflection;

import org.joda.time.DateTime;
import org.junit.Test;

/**
 * @author Vilius Normantas <code@norma.lt>
 */
public class PortfolioTest
{
   /**
    * Test method for {@link Portfolio#Portfolio()}.
    * 
    * @throws Throwable
    */
   @Test
   public void testPortfolio() throws Throwable
   {
      Portfolio p = new Portfolio();
      assertNotNull(Reflection.getField("positions", p));
      assertNotNull(Reflection.getField("lock", p));
   }
   
   /**
    * Test method for {@link Portfolio#orderExecuted(OrderExecutedEvent)}.
    * 
    * @throws Throwable
    */
   @Test
   public void testOrderExecuted() throws Throwable
   {
      Currency currency = Currency.createJpy();
      Exchange exchange = Exchange.createNasdaq();
      StockContract c = new StockContract("ABC", exchange, currency);
      MockOrder o = new MockOrder(55, c, Direction.SHORT, 800);
      FilledBlock b1 = new FilledBlock(Direction.SHORT, 123, new BigDecimal("88"), new DateTime());
      FilledBlock b2 = new FilledBlock(Direction.SHORT, 200, new BigDecimal("88"), new DateTime());
      FilledBlock b3 = new FilledBlock(Direction.LONG, 150, new BigDecimal("88"), new DateTime());
      ExecutionReport r1 = new ExecutionReport(o, b1);
      ExecutionReport r2 = new ExecutionReport(o, b2);
      ExecutionReport r3 = new ExecutionReport(o, b3);
      
      Portfolio p = new Portfolio();
      MockTradeExecutor executor = new MockTradeExecutor();
      executor.addListener(p);
      
      assertNull(p.getPosition(c));
      
      executor.fireOrderExecution(r1);
      assertEquals(-123, p.getPosition(c).getSize());
      
      executor.fireOrderExecution(r2);
      assertEquals(-323, p.getPosition(c).getSize());
      
      executor.fireOrderExecution(r3);
      assertEquals(-173, p.getPosition(c).getSize());
   }
   
   /**
    * Mock trade executor.
    * 
    * @author Vilius Normantas <code@norma.lt>
    */
   private class MockTradeExecutor extends TradeExecutor
   {
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
      public MockOrder(long id, Contract contract, Direction direction, int size)
            throws OrderException
      {
         super(id, contract, "MOCK", direction, size);
      }
   }
}
