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
public class QuoteEventTest
{
   private Exchange e;
   private Currency c;
   private Contract s;
   private DateTime t;
   private Quote q;
   
   /**
    * Constructor.
    * 
    * @throws ContractException
    */
   public QuoteEventTest() throws ContractException
   {
      e = Exchange.createNasdaq();
      c = Currency.createEur();
      s = new StockContract("B", e, c);
      t = new DateTime(2005, 1, 1, 14, 0, 0, 0, DateTimeZone.forID("America/New_York"));
      q = new Quote(s, new BigDecimal("8.05"), 500, new BigDecimal("7.0"), 800, t);
   }
   
   /**
    * Test method for
    * {@link lt.norma.crossbow.data.QuoteEvent#QuoteEvent(java.lang.Object, lt.norma.crossbow.data.Quote)}
    * .
    */
   @Test
   public void testQuoteEvent()
   {
      Object source = new Object();
      QuoteEvent qe = new QuoteEvent(source, q);
      assertEquals(source, qe.getSource());
      assertEquals(q, qe.getQuote());
   }
}
