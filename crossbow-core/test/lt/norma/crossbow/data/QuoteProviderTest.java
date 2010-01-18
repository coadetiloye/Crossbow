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

package lt.norma.crossbow.data;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import lt.norma.crossbow.account.Currency;
import lt.norma.crossbow.contracts.Contract;
import lt.norma.crossbow.contracts.Exchange;
import lt.norma.crossbow.contracts.StockContract;
import lt.norma.crossbow.exceptions.ContractException;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

/**
 * @author Vilius Normantas <code@norma.lt>
 */
public class QuoteProviderTest
{
   private Exchange e;
   private Currency c;
   private Contract s;
   private DateTime t;
   private Quote q1;
   private Quote q2;
   
   /**
    * Constructor.
    * 
    * @throws ContractException
    */
   public QuoteProviderTest() throws ContractException
   {
      e = Exchange.createNasdaq();
      c = Currency.createEur();
      s = new StockContract("B", e, c);
      t = new DateTime(2005, 1, 1, 14, 0, 0, 0, DateTimeZone.forID("America/New_York"));
      q1 = new Quote(s, new BigDecimal("8.05"), 500, new BigDecimal("7.0"), 800, t);
      q2 = new Quote(s, new BigDecimal("500"), 500, new BigDecimal("7.0"), 800, t);
   }
   
   /**
    * Test method for
    * {@link lt.norma.crossbow.data.QuoteProvider#fireQuoteEvent(lt.norma.crossbow.data.Quote)}.
    */
   @Test
   public void testFireQuoteEvent()
   {
      MockQuoteProvider provider = new MockQuoteProvider();
      MockQuoteListener listener1 = new MockQuoteListener();
      MockQuoteListener listener2 = new MockQuoteListener();
      MockQuoteListener listener3 = new MockQuoteListener();
      provider.fire(q1);
      provider.fire(q2);
      // Nobody received the quotes as no listeners added
      assertNull(listener1.lastQuote);
      assertNull(listener1.lastSender);
      assertNull(listener2.lastQuote);
      assertNull(listener2.lastSender);
      assertNull(listener3.lastQuote);
      assertNull(listener3.lastSender);
      // Add listener1 and listener2
      provider.addListener(listener1);
      provider.addListener(listener2);
      provider.fire(q1);
      assertEquals(q1, listener1.lastQuote);
      assertNotNull(listener1.lastSender);
      assertEquals(q1, listener2.lastQuote);
      assertNotNull(listener2.lastSender);
      assertNull(listener3.lastQuote);
      assertNull(listener3.lastSender);
      // Remove listener2
      provider.removeListener(listener2);
      provider.fire(q2);
      assertEquals(q2, listener1.lastQuote);
      assertNotNull(listener1.lastSender);
      assertEquals(q1, listener2.lastQuote);
      assertNotNull(listener2.lastSender);
      assertNull(listener3.lastQuote);
      assertNull(listener3.lastSender);
   }
   
   private class MockQuoteProvider extends QuoteProvider
   {
      public void fire(Quote quote)
      {
         fireQuoteEvent(quote);
      }
   }
   
   private class MockQuoteListener implements QuoteListener
   {
      public Quote lastQuote;
      public Object lastSender;
      
      @Override
      public void quoteReceived(QuoteEvent event)
      {
         lastQuote = event.getQuote();
         lastSender = event.getSource();
      }
   }
}
