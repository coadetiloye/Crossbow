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

import lt.norma.crossbow.contracts.Contract;
import lt.norma.crossbow.contracts.Currency;
import lt.norma.crossbow.contracts.Exchange;
import lt.norma.crossbow.contracts.StockContract;
import lt.norma.crossbow.data.Trade;
import lt.norma.crossbow.exceptions.ContractException;
import lt.norma.crossbow.exceptions.InvalidArgumentRuntimeException;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test TradeTest class.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class TradeTest
{
   private Exchange e;
   private Currency c;
   private Contract s;
   private DateTime time;
   
   /**
    * Constructor.
    * 
    * @throws ContractException
    */
   public TradeTest() throws ContractException
   {
      e = Exchange.createNasdaqExchange();
      c = Currency.createEur();
      s = new StockContract("B", e, c);
      time = new DateTime(2005, 1, 1, 14, 0, 0, 0, DateTimeZone.forID("America/New_York"));
   }
   
   /**
    * Test the constructor.
    * 
    * @throws ContractException
    */
   @Test
   public void testCreation() throws ContractException
   {
      Trade t = new Trade(s, new BigDecimal("8.05"), 500, time);
      
      assertEquals(new StockContract("B", Exchange.createNasdaqExchange(), Currency.createEur()),
                   t.getContract());
      assertTrue(t.getPrice().compareTo(new BigDecimal("8.0500")) == 0);
      assertEquals(500, t.getSize());
      assertEquals(time, t.getTime());
   }
   
   /**
    * Test the constructor.
    * 
    * @throws ContractException
    */
   @Test(expected = InvalidArgumentRuntimeException.class)
   public void testCreation2() throws ContractException
   {
      new Trade(null, new BigDecimal("8.05"), 500, time);
   }
   
   /**
    * Test the constructor.
    * 
    * @throws ContractException
    */
   @Test(expected = InvalidArgumentRuntimeException.class)
   public void testCreation3() throws ContractException
   {
      new Trade(s, null, 500, time);
   }
   
   /**
    * Test the constructor.
    * 
    * @throws ContractException
    */
   @Test(expected = InvalidArgumentRuntimeException.class)
   public void testCreation4() throws ContractException
   {
      new Trade(s, new BigDecimal("8.05"), 500, null);
   }
   
   /**
    * Test of toString method, of class Trade.
    */
   @Test
   public void testToString()
   {
      Trade t = new Trade(s, new BigDecimal("8.05"), 500, time);
      assertEquals("B  500 @ 8.05  [2005-01-01 14:00:00]", t.toString());
   }
}
