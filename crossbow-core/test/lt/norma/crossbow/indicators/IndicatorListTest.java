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

package lt.norma.crossbow.indicators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lt.norma.crossbow.account.Currency;
import lt.norma.crossbow.contracts.Contract;
import lt.norma.crossbow.contracts.Exchange;
import lt.norma.crossbow.contracts.StockContract;
import lt.norma.crossbow.data.Quote;
import lt.norma.crossbow.data.QuoteEvent;
import lt.norma.crossbow.data.Trade;
import lt.norma.crossbow.data.TradeEvent;
import lt.norma.crossbow.exceptions.ContractException;
import lt.norma.testutilities.Reflection;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

/**
 * @author Vilius Normantas <code@norma.lt>
 */
public class IndicatorListTest
{
   
   /**
    * Test method for {@link IndicatorList#IndicatorList()}.
    * 
    * @throws Throwable
    */
   @Test
   public void testIndicatorList() throws Throwable
   {
      IndicatorList l = new IndicatorList(null);
      assertNotNull(l.getIndicators());
      assertNotNull(Reflection.getField("lock", l));
   }
   
   /**
    * Test method for {@link IndicatorList#add(Indicator)}.
    */
   @Test
   public void testAdd1()
   {
      IndicatorList l = new IndicatorList(null);
      MockIndicator<String> ia = new MockIndicator<String>("A", true);
      MockIndicator<String> ib = new MockIndicator<String>("B", true);
      
      assertEquals(0, l.getIndicators().size());
      
      l.add(ia);
      assertEquals(1, l.getIndicators().size());
      
      l.add(ib);
      assertEquals(2, l.getIndicators().size());
      assertTrue(l.getIndicators().contains(ia));
      assertTrue(l.getIndicators().contains(ib));
   }
   
   /**
    * Test method for {@link IndicatorList#add(Indicator)}. Test indicator sorting.
    */
   @Test
   public void testAdd2()
   {
      IndicatorList l = new IndicatorList(null);
      
      List<Indicator<?>> tmpList = new ArrayList<Indicator<?>>();
      
      // Isolated indicator
      MockIndicator<String> ia = new MockIndicator<String>("A", true);
      tmpList.add(ia);
      
      // Simple tree
      MockIndicator<String> ib = new MockIndicator<String>("B", true);
      tmpList.add(ib);
      MockIndicator<String> ic = new MockIndicator<String>("C", true);
      ib.getDependencies().add(ic);
      tmpList.add(ic);
      
      // Complex tree
      MockIndicator<String> id = new MockIndicator<String>("D", true);
      tmpList.add(id);
      MockIndicator<String> ie = new MockIndicator<String>("E", true);
      id.getDependencies().add(ie);
      tmpList.add(ie);
      MockIndicator<String> ih = new MockIndicator<String>("H", true);
      tmpList.add(ih);
      MockIndicator<String> if_ = new MockIndicator<String>("F", true);
      ie.getDependencies().add(if_);
      tmpList.add(if_);
      MockIndicator<String> ig = new MockIndicator<String>("G", true);
      ie.getDependencies().add(ig);
      ih.getDependencies().add(ig);
      tmpList.add(ig);
      MockIndicator<String> ii = new MockIndicator<String>("I", true);
      if_.getDependencies().add(ii);
      tmpList.add(ii);
      MockIndicator<String> ij = new MockIndicator<String>("J", true);
      if_.getDependencies().add(ij);
      tmpList.add(ij);
      MockIndicator<String> im = new MockIndicator<String>("M", true);
      ig.getDependencies().add(im);
      tmpList.add(im);
      MockIndicator<String> ik = new MockIndicator<String>("K", true);
      ij.getDependencies().add(ik);
      tmpList.add(ik);
      MockIndicator<String> il = new MockIndicator<String>("L", true);
      ij.getDependencies().add(il);
      tmpList.add(il);
      
      Collections.shuffle(tmpList);
      for (Indicator<?> indicator : tmpList)
      {
         l.add(indicator);
      }
      
      assertEquals(13, l.getIndicators().size());
      
      // Isolated indicator is above non-isolated indicators.
      assertTrue(l.getIndicators().indexOf(ia) < l.getIndicators().indexOf(ib));
      assertTrue(l.getIndicators().indexOf(ia) < l.getIndicators().indexOf(id));
      assertTrue(l.getIndicators().indexOf(ia) < l.getIndicators().indexOf(ij));
      
      // Dependencies are above the owners.
      assertTrue(l.getIndicators().indexOf(ic) < l.getIndicators().indexOf(ib));
      
      assertTrue(l.getIndicators().indexOf(id) == 12);
      assertTrue(l.getIndicators().indexOf(ie) > l.getIndicators().indexOf(ih));
      assertTrue(l.getIndicators().indexOf(ie) > l.getIndicators().indexOf(ig));
      assertTrue(l.getIndicators().indexOf(ih) > l.getIndicators().indexOf(ig));
      assertTrue(l.getIndicators().indexOf(ik) < l.getIndicators().indexOf(if_));
   }
   
   /**
    * Test method for {@link IndicatorList#tradeReceived(TradeEvent)} and
    * {@link IndicatorList#quoteReceived(QuoteEvent)}.
    * 
    * @throws ContractException
    */
   @Test
   public void testTradeReceived() throws ContractException
   {
      Exchange e = Exchange.createNasdaq();
      Currency c = Currency.createEur();
      Contract s = new StockContract("B", e, c);
      DateTime t = new DateTime(2005, 1, 1, 14, 0, 0, 0, DateTimeZone.forID("America/New_York"));
      Trade trade = new Trade(s, new BigDecimal("8.05"), 888, t);
      Quote quote = new Quote(s, new BigDecimal("8.05"), 500, new BigDecimal("7.0"), 800, t);
      Object source = new Object();
      TradeEvent te = new TradeEvent(source, trade);
      QuoteEvent qe = new QuoteEvent(source, quote);
      
      IndicatorList l = new IndicatorList(null);
      MockIndicator<String> ia = new MockIndicator<String>("A", true);
      MockIndicator<String> ib = new MockIndicator<String>("B", true);
      l.add(ia);
      l.add(ib);
      
      l.tradeReceived(te);
      l.quoteReceived(qe);
      
      assertEquals(trade, ia.lastTrade);
      assertEquals(trade, ib.lastTrade);
      assertEquals(quote, ia.lastQuote);
      assertEquals(quote, ib.lastQuote);
   }
   
   /**
    * Test method for {@link IndicatorList#tradeReceived(TradeEvent)} and
    * {@link IndicatorList#quoteReceived(QuoteEvent)}.
    * 
    * @throws ContractException
    */
   @Test
   public void testTradeReceived2() throws ContractException
   {
      Exchange e = Exchange.createNasdaq();
      Currency c = Currency.createEur();
      Contract s = new StockContract("B", e, c);
      DateTime t = new DateTime(2005, 1, 1, 14, 0, 0, 0, DateTimeZone.forID("America/New_York"));
      Trade trade = new Trade(s, new BigDecimal("8.05"), 888, t);
      Quote quote = new Quote(s, new BigDecimal("8.05"), 500, new BigDecimal("7.0"), 800, t);
      Object source = new Object();
      TradeEvent te = new TradeEvent(source, trade);
      QuoteEvent qe = new QuoteEvent(source, quote);
      
      MockPeriodSplitter splitter = new MockPeriodSplitter();
      IndicatorList l = new IndicatorList(splitter);
      MockIndicator2<String> ia = new MockIndicator2<String>("A", true);
      l.add(ia);
      
      splitter.value = new PeriodSplitterResult(PeriodSplitterAction.START_BEFORE, new DateTime());
      ia.reset();
      l.tradeReceived(te);
      assertEquals(0, ia.beginningOrder);
      assertEquals(-1, ia.endOrder);
      assertEquals(-1, ia.quoteOrder);
      assertEquals(1, ia.tradeOrder);
      
      splitter.value = new PeriodSplitterResult(PeriodSplitterAction.END_BEFORE, new DateTime());
      ia.reset();
      l.tradeReceived(te);
      assertEquals(-1, ia.beginningOrder);
      assertEquals(0, ia.endOrder);
      assertEquals(-1, ia.quoteOrder);
      assertEquals(1, ia.tradeOrder);
      
      splitter.value =
            new PeriodSplitterResult(PeriodSplitterAction.RESTART_BEFORE, new DateTime());
      ia.reset();
      l.tradeReceived(te);
      assertEquals(1, ia.beginningOrder);
      assertEquals(0, ia.endOrder);
      assertEquals(-1, ia.quoteOrder);
      assertEquals(2, ia.tradeOrder);
      
      splitter.value = new PeriodSplitterResult(PeriodSplitterAction.START_AFTER, new DateTime());
      ia.reset();
      l.tradeReceived(te);
      assertEquals(1, ia.beginningOrder);
      assertEquals(-1, ia.endOrder);
      assertEquals(-1, ia.quoteOrder);
      assertEquals(0, ia.tradeOrder);
      
      splitter.value = new PeriodSplitterResult(PeriodSplitterAction.END_AFTER, new DateTime());
      ia.reset();
      l.tradeReceived(te);
      assertEquals(-1, ia.beginningOrder);
      assertEquals(1, ia.endOrder);
      assertEquals(-1, ia.quoteOrder);
      assertEquals(0, ia.tradeOrder);
      
      splitter.value = new PeriodSplitterResult(PeriodSplitterAction.RESTART_AFTER, new DateTime());
      ia.reset();
      l.tradeReceived(te);
      assertEquals(2, ia.beginningOrder);
      assertEquals(1, ia.endOrder);
      assertEquals(-1, ia.quoteOrder);
      assertEquals(0, ia.tradeOrder);
      
      splitter.value = new PeriodSplitterResult(PeriodSplitterAction.START_BEFORE, new DateTime());
      ia.reset();
      l.quoteReceived(qe);
      assertEquals(0, ia.beginningOrder);
      assertEquals(-1, ia.endOrder);
      assertEquals(1, ia.quoteOrder);
      assertEquals(-1, ia.tradeOrder);
      
      splitter.value = new PeriodSplitterResult(PeriodSplitterAction.END_BEFORE, new DateTime());
      ia.reset();
      l.quoteReceived(qe);
      assertEquals(-1, ia.beginningOrder);
      assertEquals(0, ia.endOrder);
      assertEquals(1, ia.quoteOrder);
      assertEquals(-1, ia.tradeOrder);
      
      splitter.value =
            new PeriodSplitterResult(PeriodSplitterAction.RESTART_BEFORE, new DateTime());
      ia.reset();
      l.quoteReceived(qe);
      assertEquals(1, ia.beginningOrder);
      assertEquals(0, ia.endOrder);
      assertEquals(2, ia.quoteOrder);
      assertEquals(-1, ia.tradeOrder);
      
      splitter.value = new PeriodSplitterResult(PeriodSplitterAction.START_AFTER, new DateTime());
      ia.reset();
      l.quoteReceived(qe);
      assertEquals(1, ia.beginningOrder);
      assertEquals(-1, ia.endOrder);
      assertEquals(0, ia.quoteOrder);
      assertEquals(-1, ia.tradeOrder);
      
      splitter.value = new PeriodSplitterResult(PeriodSplitterAction.END_AFTER, new DateTime());
      ia.reset();
      l.quoteReceived(qe);
      assertEquals(-1, ia.beginningOrder);
      assertEquals(1, ia.endOrder);
      assertEquals(0, ia.quoteOrder);
      assertEquals(-1, ia.tradeOrder);
      
      splitter.value = new PeriodSplitterResult(PeriodSplitterAction.RESTART_AFTER, new DateTime());
      ia.reset();
      l.quoteReceived(qe);
      assertEquals(2, ia.beginningOrder);
      assertEquals(1, ia.endOrder);
      assertEquals(0, ia.quoteOrder);
      assertEquals(-1, ia.tradeOrder);
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
   }
   
   private class MockIndicator2<Type> extends Indicator<Type>
   {
      public int counter;
      public int quoteOrder;
      public int tradeOrder;
      public int endOrder;
      public int beginningOrder;
      
      /**
       * Constructor.
       * 
       * @param title
       * @param collectPeriodicData
       */
      public MockIndicator2(String title, boolean collectPeriodicData)
      {
         super(title, collectPeriodicData);
         reset();
      }
      
      public void reset()
      {
         counter = 0;
         quoteOrder = -1;
         tradeOrder = -1;
         endOrder = -1;
         beginningOrder = -1;
      }
      
      @Override
      public void quoteReceived(Quote qoute)
      {
         quoteOrder = counter++;
      }
      
      @Override
      public void tradeReceived(Trade trade)
      {
         tradeOrder = counter++;
      }
      
      @Override
      protected void endOfPeriod(DateTime time)
      {
         endOrder = counter++;
      }
      
      @Override
      public void beginningOfPeriod(DateTime time)
      {
         beginningOrder = counter++;
      }
   }
   
   private class MockIndicator<Type> extends Indicator<Type>
   {
      public Trade lastTrade;
      public Quote lastQuote;
      
      /**
       * Constructor.
       * 
       * @param title
       * @param collectPeriodicData
       */
      public MockIndicator(String title, boolean collectPeriodicData)
      {
         super(title, collectPeriodicData);
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
