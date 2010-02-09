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
import static org.junit.Assert.fail;
import lt.norma.crossbow.account.Currency;
import lt.norma.crossbow.contracts.Contract;
import lt.norma.crossbow.contracts.Exchange;
import lt.norma.crossbow.contracts.StockContract;
import lt.norma.crossbow.indicators.PeriodSplitter;
import lt.norma.crossbow.indicators.TimePeriodSplitter;
import lt.norma.crossbow.orders.Direction;
import lt.norma.crossbow.orders.MarketOrder;
import lt.norma.crossbow.orders.Order;

import org.joda.time.DateTimeZone;
import org.junit.Test;

/**
 * @author Vilius Normantas <code@norma.lt>
 */
public class StrategyTest
{
   
   /**
    * Test method for
    * {@link lt.norma.crossbow.trading.Strategy#Strategy(lt.norma.crossbow.indicators.PeriodSplitter, lt.norma.crossbow.indicators.PeriodSplitter, lt.norma.crossbow.trading.TradeExecutor)}
    * .
    */
   @Test
   public void testStrategy()
   {
      TimePeriodSplitter tps1 = new TimePeriodSplitter(1000, DateTimeZone.UTC);
      TimePeriodSplitter tps2 = new TimePeriodSplitter(1000, DateTimeZone.UTC);
      TradeExecutor te = new MockTradeExecutor();
      MockStrategy s = new MockStrategy(tps1, tps2, te);
      
      assertNotNull(s.getTitle());
      assertNotNull(s.getDescription());
      assertNotNull(s.getVersion());
      
      assertNotNull(s.getParameters());
      assertNotNull(s.getIndicators());
      assertNotNull(s.getMeasures());
   }
   
   /**
    * Test method for
    * {@link lt.norma.crossbow.trading.Strategy#sendOrder(lt.norma.crossbow.orders.Order)}.
    * 
    * @throws Throwable
    */
   @Test
   public void testSendOrder() throws Throwable
   {
      TimePeriodSplitter tps1 = new TimePeriodSplitter(1000, DateTimeZone.UTC);
      TimePeriodSplitter tps2 = new TimePeriodSplitter(1000, DateTimeZone.UTC);
      MockTradeExecutor te = new MockTradeExecutor();
      MockStrategy s = new MockStrategy(tps1, tps2, te);
      
      StockContract c = new StockContract("ABC", Exchange.createNasdaq(), Currency.createJpy());
      MarketOrder o = new MarketOrder(0, c, Direction.LONG, 100);
      
      s.sendOrder(o);
      
      assertEquals(o, te.lastOrder);
   }
   
   /**
    * Test method for
    * {@link lt.norma.crossbow.trading.Strategy#orderExecuted(lt.norma.crossbow.trading.OrderExecutedEvent)}
    * .
    */
   @Test
   public void testOrderExecutedOrderExecutedEvent()
   {
      fail("Not yet implemented");
   }
   
   /**
    * Test method for
    * {@link lt.norma.crossbow.trading.Strategy#orderUpdated(lt.norma.crossbow.trading.OrderUpdatedEvent)}
    * .
    */
   @Test
   public void testOrderUpdatedOrderUpdatedEvent()
   {
      fail("Not yet implemented");
   }
   
   /**
    * Test method for
    * {@link lt.norma.crossbow.trading.Strategy#orderExecuted(lt.norma.crossbow.trading.ExecutionReport)}
    * .
    */
   @Test
   public void testOrderExecutedExecutionReport()
   {
      fail("Not yet implemented");
   }
   
   /**
    * Test method for
    * {@link lt.norma.crossbow.trading.Strategy#orderUpdated(lt.norma.crossbow.orders.Order)}.
    */
   @Test
   public void testOrderUpdatedOrder()
   {
      fail("Not yet implemented");
   }
   
   /**
    * Test method for
    * {@link lt.norma.crossbow.trading.Strategy#quoteReceived(lt.norma.crossbow.data.QuoteEvent)}.
    */
   @Test
   public void testQuoteReceivedQuoteEvent()
   {
      fail("Not yet implemented");
   }
   
   /**
    * Test method for
    * {@link lt.norma.crossbow.trading.Strategy#tradeReceived(lt.norma.crossbow.data.TradeEvent)}.
    */
   @Test
   public void testTradeReceivedTradeEvent()
   {
      fail("Not yet implemented");
   }
   
   /**
    * Test method for
    * {@link lt.norma.crossbow.trading.Strategy#quoteReceived(lt.norma.crossbow.data.Quote)}.
    */
   @Test
   public void testQuoteReceivedQuote()
   {
      fail("Not yet implemented");
   }
   
   /**
    * Test method for
    * {@link lt.norma.crossbow.trading.Strategy#tradeReceived(lt.norma.crossbow.data.Trade)}.
    */
   @Test
   public void testTradeReceivedTrade()
   {
      fail("Not yet implemented");
   }
   
   /**
    * Test method for {@link lt.norma.crossbow.trading.Strategy#getTitle()}.
    */
   @Test
   public void testGetTitle()
   {
      fail("Not yet implemented");
   }
   
   /**
    * Test method for {@link lt.norma.crossbow.trading.Strategy#getDescription()}.
    */
   @Test
   public void testGetDescription()
   {
      fail("Not yet implemented");
   }
   
   /**
    * Test method for {@link lt.norma.crossbow.trading.Strategy#getVersion()}.
    */
   @Test
   public void testGetVersion()
   {
      fail("Not yet implemented");
   }
   
   /**
    * Test method for {@link lt.norma.crossbow.trading.Strategy#getParameters()}.
    */
   @Test
   public void testGetParameters()
   {
      fail("Not yet implemented");
   }
   
   /**
    * Test method for {@link lt.norma.crossbow.trading.Strategy#getIndicators()}.
    */
   @Test
   public void testGetIndicators()
   {
      fail("Not yet implemented");
   }
   
   /**
    * Test method for {@link lt.norma.crossbow.trading.Strategy#getMeasures()}.
    */
   @Test
   public void testGetMeasures()
   {
      fail("Not yet implemented");
   }
   
   private class MockStrategy extends Strategy
   {
      public MockStrategy(PeriodSplitter indicatorPeriodSplitter,
            PeriodSplitter measurePeriodSplitter, TradeExecutor tradeExecutor)
      {
         super(indicatorPeriodSplitter, measurePeriodSplitter, tradeExecutor);
      }
   }
   
   private class MockTradeExecutor extends TradeExecutor
   {
      public Order lastOrder;
      
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
         lastOrder = order;
      }
   }
}
