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

import static org.junit.Assert.*;

import java.math.BigDecimal;

import lt.norma.crossbow.account.Currency;
import lt.norma.crossbow.contracts.Contract;
import lt.norma.crossbow.contracts.Exchange;
import lt.norma.crossbow.contracts.StockContract;
import lt.norma.crossbow.data.Quote;
import lt.norma.crossbow.data.Trade;
import lt.norma.crossbow.exceptions.ContractException;
import lt.norma.crossbow.exceptions.ValueNotSetRuntimeException;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

/**
 * @author Vilius Normantas <code@norma.lt>
 */
public class IndicatorTest
{
   private Contract s;
   private DateTime time;
   
   /**
    * Constructor.
    * 
    * @throws ContractException
    */
   public IndicatorTest() throws ContractException
   {
      s = new StockContract("B", Exchange.createNasdaq(), Currency.createEur());
      time = new DateTime(2005, 1, 1, 14, 0, 0, 0, DateTimeZone.forID("America/New_York"));
   }
   
   /**
    * Test method for {@link Indicator#Indicator(String, boolean)}.
    */
   @Test
   public void testIndicator()
   {
      MockIndicator<Double> i1 = new MockIndicator<Double>("AA", true);
      assertNotNull(i1.getParameters());
   }
   
   /**
    * Test method for {@link Indicator#tradeReceived(Trade)}.
    */
   @Test
   public void testTradeReceived()
   {
      MockIndicator<Double> i1 = new MockIndicator<Double>("AA", true);
      Trade t = new Trade(s, new BigDecimal("8.05"), 500, time);
      
      i1.tradeReceived(t);
      assertEquals(t, i1.lastTrade);
   }
   
   /**
    * Test method for {@link Indicator#quoteReceived(Quote)}.
    */
   @Test
   public void testQuoteReceived()
   {
      MockIndicator<Double> i1 = new MockIndicator<Double>("AA", true);
      Quote q = new Quote(s, new BigDecimal("8.05"), 500, new BigDecimal("7.0"), 800, time);
      
      i1.quoteReceived(q);
      assertEquals(q, i1.lastQuote);
   }
   
   /**
    * Test method for {@link Indicator#getValue()}, {@link Indicator#setValue(Object)},
    * {@link Indicator#isSet()}.
    */
   @Test
   public void testGetValue()
   {
      MockIndicator<Integer> i1 = new MockIndicator<Integer>("AA", true);
      i1.setValue(99);
      assertTrue(i1.isSet());
      assertEquals(99, (int)i1.getValue());
      i1.setValue(1);
      assertTrue(i1.isSet());
      assertEquals(1, (int)i1.getValue());
      
      Object v1 = new Object();
      Object v2 = new Object();
      MockIndicator<Object> i2 = new MockIndicator<Object>("BB", true);
      i2.setValue(v1);
      assertTrue(i2.isSet());
      assertEquals(v1, i2.getValue());
      i2.setValue(v2);
      assertTrue(i2.isSet());
      assertEquals(v2, i2.getValue());
   }
   
   /**
    * Test method for {@link Indicator#getValue()}.
    */
   @Test(expected = ValueNotSetRuntimeException.class)
   public void testGetValueException()
   {
      MockIndicator<Integer> i1 = new MockIndicator<Integer>("AA", true);
      i1.getValue();
   }
   
   /**
    * Test method for {@link Indicator#unsetValue()}.
    */
   @Test(expected = ValueNotSetRuntimeException.class)
   public void testUnsetValue()
   {
      MockIndicator<Integer> i1 = new MockIndicator<Integer>("AA", true);
      i1.setValue(99);
      assertTrue(i1.isSet());
      assertEquals(99, (int)i1.getValue());
      i1.unsetValue();
      assertFalse(i1.isSet());
      i1.getValue();
   }
   
   /**
    * Test method for {@link Indicator#updateEndOfPeriod()}.
    */
   @Test
   public void testUpdateEndOfPeriod()
   {
      MockIndicator<Integer> i1 = new MockIndicator<Integer>("AA", true);
      
      // Before end of the first period.
      assertEquals(0, i1.periodCount);
      assertEquals(0, i1.getPeriodicData().size());
      assertEquals(0, i1.getPeriodicDataFlags().size());
      
      // Add the first period.
      i1.setValue(18);
      i1.updateEndOfPeriod();
      assertEquals(1, i1.periodCount);
      assertEquals(1, i1.getPeriodicData().size());
      assertEquals(18, (int)i1.getPeriodicData().get(0));
      assertEquals(1, i1.getPeriodicDataFlags().size());
      assertTrue(i1.getPeriodicDataFlags().get(0));
      
      // Add the second period.
      i1.setValue(19);
      i1.updateEndOfPeriod();
      assertEquals(2, i1.periodCount);
      assertEquals(2, i1.getPeriodicData().size());
      assertEquals(19, (int)i1.getPeriodicData().get(1));
      assertEquals(2, i1.getPeriodicDataFlags().size());
      assertTrue(i1.getPeriodicDataFlags().get(1));
      
      // Unset data and add the third period.
      i1.unsetValue();
      i1.updateEndOfPeriod();
      assertEquals(3, i1.periodCount);
      assertEquals(3, i1.getPeriodicData().size());
      assertNull(i1.getPeriodicData().get(2));
      assertEquals(3, i1.getPeriodicDataFlags().size());
      assertFalse(i1.getPeriodicDataFlags().get(2));
      
      // Add the fourth period.
      i1.setValue(20);
      i1.updateEndOfPeriod();
      assertEquals(4, i1.periodCount);
      assertEquals(4, i1.getPeriodicData().size());
      assertEquals(20, (int)i1.getPeriodicData().get(3));
      assertEquals(4, i1.getPeriodicDataFlags().size());
      assertTrue(i1.getPeriodicDataFlags().get(3));
   }
   
   /**
    * Test method for {@link Indicator#getPeriodicData()}, {@link Indicator#getPeriodicDataFlags()}
    * and {@link Indicator#hasPeriodicData()}.
    */
   @Test
   public void testGetPeriodicData()
   {
      MockIndicator<Double> i1 = new MockIndicator<Double>("AA", true);
      assertTrue(i1.hasPeriodicData());
      assertNotNull(i1.getPeriodicData());
      assertNotNull(i1.getPeriodicDataFlags());
      
      MockIndicator<Double> i2 = new MockIndicator<Double>("AA", false);
      assertFalse(i2.hasPeriodicData());
      assertNull(i2.getPeriodicData());
      assertNull(i2.getPeriodicDataFlags());
   }
   
   /**
    * Test method for {@link Indicator#getTitle()}, {@link Indicator#setTitle(String)} and
    * {@link Indicator#getOriginalTitle()}.
    */
   @Test
   public void testSetTitle()
   {
      MockIndicator<Double> i1 = new MockIndicator<Double>("AA", true);
      assertEquals("AA", i1.getTitle());
      assertEquals("AA", i1.getOriginalTitle());
      i1.setTitle("BB");
      assertEquals("BB", i1.getTitle());
      assertEquals("AA", i1.getOriginalTitle());
      
      MockIndicator<Double> i2 = new MockIndicator<Double>(null, true);
      assertEquals("null", i2.getTitle());
      assertEquals("null", i2.getOriginalTitle());
      i2.setTitle("CC");
      assertEquals("CC", i2.getTitle());
      assertEquals("null", i2.getOriginalTitle());
      
      MockIndicator<Double> i3 = new MockIndicator<Double>("", true);
      assertEquals("", i3.getTitle());
      assertEquals("", i3.getOriginalTitle());
      
      MockIndicator<Double> i4 = new MockIndicator<Double>("DD", true);
      assertEquals("DD", i4.getTitle());
      assertEquals("DD", i4.getOriginalTitle());
      i4.setTitle("");
      assertEquals("DD", i4.getTitle());
      assertEquals("DD", i4.getOriginalTitle());
   }
   
   /**
    * Test method for {@link Indicator#getDescription()} and
    * {@link Indicator#setDescription(String)}.
    */
   @Test
   public void testGetDescription()
   {
      MockIndicator<Double> i1 = new MockIndicator<Double>("AA", true);
      assertEquals("", i1.getDescription());
      
      i1.setDescription("AAA");
      assertEquals("AAA", i1.getDescription());
      
      i1.setDescription("BBB");
      assertEquals("BBB", i1.getDescription());
      
      i1.setDescription("");
      assertEquals("", i1.getDescription());
      
      i1.setDescription(null);
      assertEquals("null", i1.getDescription());
   }
   
   private class MockIndicator<Type> extends Indicator<Type>
   {
      public Trade lastTrade;
      public Quote lastQuote;
      public int periodCount;
      
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
      
      @Override
      protected void endOfPeriod()
      {
         periodCount++;
      }
   }
}
