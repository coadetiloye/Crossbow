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

import java.math.BigDecimal;

import lt.norma.crossbow.account.Currency;
import lt.norma.crossbow.contracts.Contract;
import lt.norma.crossbow.contracts.Exchange;
import lt.norma.crossbow.contracts.StockContract;
import lt.norma.crossbow.data.Quote;
import lt.norma.crossbow.exceptions.ContractException;
import lt.norma.crossbow.exceptions.InvalidArgumentRuntimeException;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test Quote class.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class QuoteTest
{
   private Exchange e;
   private Currency c;
   private Contract s;
   private DateTime t;
   
   /**
    * Constructor.
    * 
    * @throws ContractException
    */
   public QuoteTest() throws ContractException
   {
      e = Exchange.createNasdaq();
      c = Currency.createEur();
      s = new StockContract("B", e, c);
      t = new DateTime(2005, 1, 1, 14, 0, 0, 0, DateTimeZone.forID("America/New_York"));
   }
   
   /**
    * Test the constructor.
    * 
    * @throws ContractException
    */
   @Test
   public void testCreation() throws ContractException
   {
      Quote q = new Quote(s, new BigDecimal("8.05"), 500, new BigDecimal("7.0"), 800, t);
      
      assertEquals(new StockContract("B", Exchange.createNasdaq(), Currency.createEur()),
            q.getContract());
      assertTrue(q.getAskPrice().compareTo(new BigDecimal("8.0500")) == 0);
      assertEquals(500, q.getAskSize());
      assertTrue(q.getBidPrice().compareTo(new BigDecimal("7")) == 0);
      assertEquals(800, q.getBidSize());
      assertEquals(t, q.getTime());
   }
   
   /**
    * Test the constructor.
    * 
    * @throws ContractException
    */
   @Test(expected = InvalidArgumentRuntimeException.class)
   public void testCreation2() throws ContractException
   {
      new Quote(null, new BigDecimal("8.05"), 500, new BigDecimal("7.0"), 800, t);
   }
   
   /**
    * Test the constructor.
    * 
    * @throws ContractException
    */
   @Test(expected = InvalidArgumentRuntimeException.class)
   public void testCreation3() throws ContractException
   {
      new Quote(s, null, 500, new BigDecimal("7.0"), 800, t);
   }
   
   /**
    * Test the constructor.
    * 
    * @throws ContractException
    */
   @Test(expected = InvalidArgumentRuntimeException.class)
   public void testCreation4() throws ContractException
   {
      new Quote(s, new BigDecimal("8.05"), 500, null, 800, t);
   }
   
   /**
    * Test the constructor.
    * 
    * @throws ContractException
    */
   @Test(expected = InvalidArgumentRuntimeException.class)
   public void testCreation5() throws ContractException
   {
      new Quote(s, new BigDecimal("8.05"), 500, new BigDecimal("7.0"), 800, null);
   }
   
   /**
    * Test of spread method, of class Quote.
    */
   @Test
   public void testSpread()
   {
      Quote q = new Quote(s, new BigDecimal("200.05"), 500, new BigDecimal("200"), 800, t);
      assertTrue(q.spread().compareTo(new BigDecimal("0.05")) == 0);
      Quote q2 = new Quote(s, new BigDecimal("195.02"), 500, new BigDecimal("200"), 800, t);
      assertTrue(q2.spread().compareTo(new BigDecimal("-4.98")) == 0);
   }
   
   /**
    * Test of isCrossed method, of class Quote.
    */
   @Test
   public void testIsCrossed()
   {
      Quote q = new Quote(s, new BigDecimal("200.05"), 500, new BigDecimal("200"), 800, t);
      assertFalse(q.isCrossed());
      Quote q2 = new Quote(s, new BigDecimal("195.02"), 500, new BigDecimal("200"), 800, t);
      assertTrue(q2.isCrossed());
   }
   
   /**
    * Test of toString method, of class Quote.
    */
   @Test
   public void testToString()
   {
      Quote q = new Quote(s, new BigDecimal("200.05"), 500, new BigDecimal("200"), 800, t);
      assertEquals("B  ask: 500 @ 200.05  bid: 800 @ 200.00  [2005-01-01 14:00:00]", q.toString());
   }
}
