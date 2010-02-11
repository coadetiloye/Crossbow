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

import java.math.BigDecimal;

import lt.norma.crossbow.account.Currency;
import lt.norma.crossbow.contracts.Contract;
import lt.norma.crossbow.contracts.Exchange;
import lt.norma.crossbow.contracts.StockContract;
import lt.norma.crossbow.data.Quote;
import lt.norma.crossbow.data.QuoteEvent;
import lt.norma.crossbow.data.Trade;
import lt.norma.crossbow.data.TradeEvent;
import lt.norma.crossbow.indicators.*;
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
    * {@link lt.norma.crossbow.trading.Strategy#tradeReceived(lt.norma.crossbow.data.TradeEvent)}.
    * 
    * @throws Throwable
    */
   @Test
   public void testTradeReceivedTradeEvent() throws Throwable
   {
      StockContract c = new StockContract("ABC", Exchange.createNasdaq(), Currency.createJpy());
      DateTime t = new DateTime(2005, 1, 1, 14, 0, 0, 0, DateTimeZone.forID("America/New_York"));
      Trade trade = new Trade(c, new BigDecimal("8.05"), 888, t);
      Quote quote = new Quote(c, new BigDecimal("8.05"), 500, new BigDecimal("7.0"), 800, t);
      Object source = new Object();
      TradeEvent te = new TradeEvent(source, trade);
      QuoteEvent qe = new QuoteEvent(source, quote);
      
      MockPeriodSplitter tpsI = new MockPeriodSplitter();
      MockPeriodSplitter tpsM = new MockPeriodSplitter();
      MockTradeExecutor executor = new MockTradeExecutor();
      MockStrategy2 s = new MockStrategy2(tpsI, tpsM, executor);
      MockMeasure m = new MockMeasure("AA", true);
      s.getIndicators().add(m);
      s.getMeasures().add(m);
      
      // Test if trade and quote events are forwarded to indicators and measures.
      tpsI.value = new PeriodSplitterResult(PeriodSplitterAction.START_BEFORE, new DateTime());
      tpsM.value = new PeriodSplitterResult(PeriodSplitterAction.START_BEFORE, new DateTime());
      s.quoteReceived(qe);
      s.tradeReceived(te);
      assertEquals(quote, ((MockMeasure)s.getMeasures().getIndicators().get(0)).lastQuote);
      assertEquals(trade, ((MockMeasure)s.getMeasures().getIndicators().get(0)).lastTrade);
      assertEquals(quote, s.lastQuote);
      assertEquals(trade, s.lastTrade);
      
      // Test order of periodic events.
      // TRADE BEFORE
      tpsI.value = new PeriodSplitterResult(PeriodSplitterAction.START_BEFORE, new DateTime());
      tpsM.value = new PeriodSplitterResult(PeriodSplitterAction.START_BEFORE, new DateTime());
      s.reset();
      s.tradeReceived(te);
      assertEquals(trade, ((MockMeasure)s.getMeasures().getIndicators().get(0)).lastTrade);
      assertEquals(0, s.beginningOrder);
      assertEquals(-1, s.endOrder);
      assertEquals(-1, s.quoteOrder);
      assertEquals(1, s.tradeOrder);
      
      tpsI.value = new PeriodSplitterResult(PeriodSplitterAction.END_BEFORE, new DateTime());
      tpsM.value = new PeriodSplitterResult(PeriodSplitterAction.START_BEFORE, new DateTime());
      s.reset();
      s.tradeReceived(te);
      assertEquals(trade, ((MockMeasure)s.getMeasures().getIndicators().get(0)).lastTrade);
      assertEquals(-1, s.beginningOrder);
      assertEquals(0, s.endOrder);
      assertEquals(-1, s.quoteOrder);
      assertEquals(1, s.tradeOrder);
      
      tpsI.value = new PeriodSplitterResult(PeriodSplitterAction.RESTART_BEFORE, new DateTime());
      tpsM.value = new PeriodSplitterResult(PeriodSplitterAction.START_BEFORE, new DateTime());
      s.reset();
      s.tradeReceived(te);
      assertEquals(trade, ((MockMeasure)s.getMeasures().getIndicators().get(0)).lastTrade);
      assertEquals(1, s.beginningOrder);
      assertEquals(0, s.endOrder);
      assertEquals(-1, s.quoteOrder);
      assertEquals(2, s.tradeOrder);
      
      // TRADE AFTER
      tpsI.value = new PeriodSplitterResult(PeriodSplitterAction.START_AFTER, new DateTime());
      tpsM.value = new PeriodSplitterResult(PeriodSplitterAction.START_BEFORE, new DateTime());
      s.reset();
      s.tradeReceived(te);
      assertEquals(trade, ((MockMeasure)s.getMeasures().getIndicators().get(0)).lastTrade);
      assertEquals(1, s.beginningOrder);
      assertEquals(-1, s.endOrder);
      assertEquals(-1, s.quoteOrder);
      assertEquals(0, s.tradeOrder);
      
      tpsI.value = new PeriodSplitterResult(PeriodSplitterAction.END_AFTER, new DateTime());
      tpsM.value = new PeriodSplitterResult(PeriodSplitterAction.START_BEFORE, new DateTime());
      s.reset();
      s.tradeReceived(te);
      assertEquals(trade, ((MockMeasure)s.getMeasures().getIndicators().get(0)).lastTrade);
      assertEquals(-1, s.beginningOrder);
      assertEquals(1, s.endOrder);
      assertEquals(-1, s.quoteOrder);
      assertEquals(0, s.tradeOrder);
      
      tpsI.value = new PeriodSplitterResult(PeriodSplitterAction.RESTART_AFTER, new DateTime());
      tpsM.value = new PeriodSplitterResult(PeriodSplitterAction.START_BEFORE, new DateTime());
      s.reset();
      s.tradeReceived(te);
      assertEquals(trade, ((MockMeasure)s.getMeasures().getIndicators().get(0)).lastTrade);
      assertEquals(2, s.beginningOrder);
      assertEquals(1, s.endOrder);
      assertEquals(-1, s.quoteOrder);
      assertEquals(0, s.tradeOrder);
      
      // QUOTE BEFORE
      tpsI.value = new PeriodSplitterResult(PeriodSplitterAction.START_BEFORE, new DateTime());
      tpsM.value = new PeriodSplitterResult(PeriodSplitterAction.START_BEFORE, new DateTime());
      s.reset();
      s.quoteReceived(qe);
      assertEquals(trade, ((MockMeasure)s.getMeasures().getIndicators().get(0)).lastTrade);
      assertEquals(0, s.beginningOrder);
      assertEquals(-1, s.endOrder);
      assertEquals(1, s.quoteOrder);
      assertEquals(-1, s.tradeOrder);
      
      tpsI.value = new PeriodSplitterResult(PeriodSplitterAction.END_BEFORE, new DateTime());
      tpsM.value = new PeriodSplitterResult(PeriodSplitterAction.START_BEFORE, new DateTime());
      s.reset();
      s.quoteReceived(qe);
      assertEquals(trade, ((MockMeasure)s.getMeasures().getIndicators().get(0)).lastTrade);
      assertEquals(-1, s.beginningOrder);
      assertEquals(0, s.endOrder);
      assertEquals(1, s.quoteOrder);
      assertEquals(-1, s.tradeOrder);
      
      tpsI.value = new PeriodSplitterResult(PeriodSplitterAction.RESTART_BEFORE, new DateTime());
      tpsM.value = new PeriodSplitterResult(PeriodSplitterAction.START_BEFORE, new DateTime());
      s.reset();
      s.quoteReceived(qe);
      assertEquals(trade, ((MockMeasure)s.getMeasures().getIndicators().get(0)).lastTrade);
      assertEquals(1, s.beginningOrder);
      assertEquals(0, s.endOrder);
      assertEquals(2, s.quoteOrder);
      assertEquals(-1, s.tradeOrder);
      
      // QUOTE AFTER
      tpsI.value = new PeriodSplitterResult(PeriodSplitterAction.START_AFTER, new DateTime());
      tpsM.value = new PeriodSplitterResult(PeriodSplitterAction.START_BEFORE, new DateTime());
      s.reset();
      s.quoteReceived(qe);
      assertEquals(trade, ((MockMeasure)s.getMeasures().getIndicators().get(0)).lastTrade);
      assertEquals(1, s.beginningOrder);
      assertEquals(-1, s.endOrder);
      assertEquals(0, s.quoteOrder);
      assertEquals(-1, s.tradeOrder);
      
      tpsI.value = new PeriodSplitterResult(PeriodSplitterAction.END_AFTER, new DateTime());
      tpsM.value = new PeriodSplitterResult(PeriodSplitterAction.START_BEFORE, new DateTime());
      s.reset();
      s.quoteReceived(qe);
      assertEquals(trade, ((MockMeasure)s.getMeasures().getIndicators().get(0)).lastTrade);
      assertEquals(-1, s.beginningOrder);
      assertEquals(1, s.endOrder);
      assertEquals(0, s.quoteOrder);
      assertEquals(-1, s.tradeOrder);
      
      tpsI.value = new PeriodSplitterResult(PeriodSplitterAction.RESTART_AFTER, new DateTime());
      tpsM.value = new PeriodSplitterResult(PeriodSplitterAction.START_BEFORE, new DateTime());
      s.reset();
      s.quoteReceived(qe);
      assertEquals(trade, ((MockMeasure)s.getMeasures().getIndicators().get(0)).lastTrade);
      assertEquals(2, s.beginningOrder);
      assertEquals(1, s.endOrder);
      assertEquals(0, s.quoteOrder);
      assertEquals(-1, s.tradeOrder);
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
   
   private class MockStrategy2 extends Strategy
   {
      public Trade lastTrade;
      public Quote lastQuote;
      public int counter;
      public int quoteOrder;
      public int tradeOrder;
      public int endOrder;
      public int beginningOrder;
      
      public MockStrategy2(PeriodSplitter indicatorPeriodSplitter,
            PeriodSplitter measurePeriodSplitter, TradeExecutor tradeExecutor)
      {
         super(indicatorPeriodSplitter, measurePeriodSplitter, tradeExecutor);
         reset();
      }
      
      public void reset()
      {
         counter = 0;
         quoteOrder = -1;
         tradeOrder = -1;
         endOrder = -1;
         beginningOrder = -1;
         lastTrade = null;
         lastQuote = null;
      }
      
      @Override
      public void quoteReceived(Quote qoute)
      {
         lastQuote = qoute;
         quoteOrder = counter++;
      }
      
      @Override
      public void tradeReceived(Trade trade)
      {
         lastTrade = trade;
         tradeOrder = counter++;
      }
      
      @Override
      protected void periodStarted()
      {
         beginningOrder = counter++;
      }
      
      @Override
      public void periodEnded()
      {
         endOrder = counter++;
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
   
   private class MockPeriodSplitter implements PeriodSplitter
   {
      public PeriodSplitterResult value;
      
      @Override
      public PeriodSplitterResult checkEndOfPeriod(Trade trade)
      {
         return value;
      }
      
      @Override
      public PeriodSplitterResult checkEndOfPeriod(Quote quote)
      {
         return value;
      }
      
      @Override
      public PeriodSplitterResult getLastResult()
      {
         return value;
      }
   }
   
   private class MockMeasure extends Measure<Integer>
   {
      public ExecutionReport lastExecutionReport;
      public Order lastUpdatedOrder;
      public Trade lastTrade;
      public Quote lastQuote;
      
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
      
      @Override
      public void quoteReceived(Quote qoute)
      {
         lastQuote = qoute;
      }
      
      @Override
      public void tradeReceived(Trade trade)
      {
         lastTrade = trade;
      }
   }
}
