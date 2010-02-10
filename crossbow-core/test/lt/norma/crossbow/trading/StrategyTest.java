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

import java.math.BigDecimal;

import lt.norma.crossbow.account.Currency;
import lt.norma.crossbow.contracts.Contract;
import lt.norma.crossbow.contracts.Exchange;
import lt.norma.crossbow.contracts.StockContract;
import lt.norma.crossbow.indicators.Measure;
import lt.norma.crossbow.indicators.PeriodSplitter;
import lt.norma.crossbow.indicators.TimePeriodSplitter;
import lt.norma.crossbow.orders.Direction;
import lt.norma.crossbow.orders.MarketOrder;
import lt.norma.crossbow.orders.Order;

import org.joda.time.DateTime;
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
    * 
    * @throws Throwable
    */
   @Test
   public void testOrderExecuted() throws Throwable
   {
      TimePeriodSplitter tps1 = new TimePeriodSplitter(1000, DateTimeZone.UTC);
      TimePeriodSplitter tps2 = new TimePeriodSplitter(1000, DateTimeZone.UTC);
      MockTradeExecutor te = new MockTradeExecutor();
      MockStrategy s = new MockStrategy(tps1, tps2, te);
      
      StockContract c = new StockContract("ABC", Exchange.createNasdaq(), Currency.createJpy());
      MarketOrder o = new MarketOrder(0, c, Direction.LONG, 100);
      
      FilledBlock b = new FilledBlock(Direction.LONG, 100, new BigDecimal("8"), new DateTime());
      ExecutionReport r = new ExecutionReport(o, b);
      OrderExecutedEvent e = new OrderExecutedEvent(this, r);
      
      s.orderExecuted(e);
      assertEquals(r, s.lastReport);
      assertEquals(r, s.m.lastExecutionReport);
   }
   
   /**
    * Test method for
    * {@link lt.norma.crossbow.trading.Strategy#orderUpdated(lt.norma.crossbow.trading.OrderUpdatedEvent)}
    * .
    * 
    * @throws Throwable
    */
   @Test
   public void testOrderUpdated() throws Throwable
   {
      TimePeriodSplitter tps1 = new TimePeriodSplitter(1000, DateTimeZone.UTC);
      TimePeriodSplitter tps2 = new TimePeriodSplitter(1000, DateTimeZone.UTC);
      MockTradeExecutor te = new MockTradeExecutor();
      MockStrategy s = new MockStrategy(tps1, tps2, te);
      
      StockContract c = new StockContract("ABC", Exchange.createNasdaq(), Currency.createJpy());
      MarketOrder o = new MarketOrder(0, c, Direction.LONG, 100);
      OrderUpdatedEvent e = new OrderUpdatedEvent(this, o);
      
      s.orderUpdated(e);
      assertEquals(o, s.lastUpdatedOrder);
      assertEquals(o, s.m.lastUpdatedOrder);
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
    * Test method for {@link lt.norma.crossbow.trading.Strategy#getTitle()}.
    */
   @Test
   public void testGetTitle()
   {
      TimePeriodSplitter tps1 = new TimePeriodSplitter(1000, DateTimeZone.UTC);
      TimePeriodSplitter tps2 = new TimePeriodSplitter(1000, DateTimeZone.UTC);
      MockTradeExecutor te = new MockTradeExecutor();
      MockStrategy s = new MockStrategy(tps1, tps2, te);
      
      assertNotNull(s.getTitle());
      s.setTitle("ttt");
      assertEquals("ttt", s.getTitle());
   }
   
   /**
    * Test method for {@link lt.norma.crossbow.trading.Strategy#getDescription()}.
    */
   @Test
   public void testGetDescription()
   {
      TimePeriodSplitter tps1 = new TimePeriodSplitter(1000, DateTimeZone.UTC);
      TimePeriodSplitter tps2 = new TimePeriodSplitter(1000, DateTimeZone.UTC);
      MockTradeExecutor te = new MockTradeExecutor();
      MockStrategy s = new MockStrategy(tps1, tps2, te);
      
      assertNotNull(s.getDescription());
      s.setDescription("ddd");
      assertEquals("ddd", s.getDescription());
   }
   
   /**
    * Test method for {@link lt.norma.crossbow.trading.Strategy#getVersion()}.
    */
   @Test
   public void testGetVersion()
   {
      TimePeriodSplitter tps1 = new TimePeriodSplitter(1000, DateTimeZone.UTC);
      TimePeriodSplitter tps2 = new TimePeriodSplitter(1000, DateTimeZone.UTC);
      MockTradeExecutor te = new MockTradeExecutor();
      MockStrategy s = new MockStrategy(tps1, tps2, te);
      
      assertNotNull(s.getVersion());
      s.setVersion("vvv");
      assertEquals("vvv", s.getVersion());
   }
   
   private class MockStrategy extends Strategy
   {
      ExecutionReport lastReport;
      Order lastUpdatedOrder;
      MockMeasure m;
      
      public MockStrategy(PeriodSplitter indicatorPeriodSplitter,
            PeriodSplitter measurePeriodSplitter, TradeExecutor tradeExecutor)
      {
         super(indicatorPeriodSplitter, measurePeriodSplitter, tradeExecutor);
         m = new MockMeasure("MM", false);
         getMeasures().add(m);
      }
      
      @Override
      protected void orderExecuted(ExecutionReport report)
      {
         lastReport = report;
      }
      
      @Override
      public void orderUpdated(Order order)
      {
         lastUpdatedOrder = order;
      }
   }
   
   private class MockTradeExecutor extends TradeExecutor
   {
      public Order lastOrder;
      
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
   
   private class MockMeasure extends Measure<Integer>
   {
      public ExecutionReport lastExecutionReport;
      public Order lastUpdatedOrder;
      
      public MockMeasure(String title, boolean collectPeriodicData)
      {
         super(title, collectPeriodicData);
      }
      
      @Override
      public void orderExecuted(ExecutionReport report)
      {
         lastExecutionReport = report;
      }
      
      @Override
      public void orderUpdated(Order order)
      {
         lastUpdatedOrder = order;
      }
   }
}
