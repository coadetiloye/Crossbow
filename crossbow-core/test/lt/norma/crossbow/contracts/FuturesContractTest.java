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

package lt.norma.crossbow.contracts;

import java.math.BigDecimal;

import lt.norma.crossbow.account.Currency;
import lt.norma.crossbow.contracts.Contract;
import lt.norma.crossbow.contracts.Exchange;
import lt.norma.crossbow.contracts.FuturesContract;
import lt.norma.crossbow.exceptions.ContractException;

import org.joda.time.DateMidnight;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test FuturesContract class.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class FuturesContractTest
{
   /**
    * Test the constructor.
    * 
    * @throws ContractException
    */
   @Test
   public void testCreation() throws ContractException
   {
      Currency currency = Currency.createJpy();
      Exchange exchange = Exchange.createNasdaq();
      DateMidnight maturityDate =
            new DateMidnight(2010, 1, 1, DateTimeZone.forID("America/New_York"));
      Contract c =
            new FuturesContract("S", maturityDate, exchange, currency, new BigDecimal("100"));
      assertEquals("FUTURES", c.type);
   }
   
   /**
    * Test of toString method, of class FuturesContract.
    * 
    * @throws ContractException
    */
   @Test
   public void testToString() throws ContractException
   {
      Currency currency = Currency.createJpy();
      Exchange exchange = Exchange.createNasdaq();
      DateMidnight maturityDate =
            new DateMidnight(2010, 1, 1, DateTimeZone.forID("America/New_York"));
      FuturesContract c =
            new FuturesContract("S", maturityDate, exchange, currency, new BigDecimal("100"));
      
      assertEquals("S 2010-01-01", c.toString());
   }
   
   /**
    * Test of equals method, of class FuturesContract.
    * 
    * @throws ContractException
    */
   @Test
   public void testEquals() throws ContractException
   {
      Currency currency = Currency.createJpy();
      Exchange exchange = Exchange.createNasdaq();
      DateMidnight maturityDate1 =
            new DateMidnight(2010, 1, 1, DateTimeZone.forID("America/New_York"));
      DateMidnight maturityDate3 =
            new DateMidnight(2009, 1, 1, DateTimeZone.forID("America/New_York"));
      DateMidnight maturityDate4 =
            new DateMidnight(2011, 1, 1, DateTimeZone.forID("America/New_York"));
      Contract c1 =
            new FuturesContract("S", maturityDate1, exchange, currency, new BigDecimal("100"));
      Contract c2 =
            new FuturesContract("S", maturityDate1, exchange, currency, new BigDecimal("100"));
      Contract c3 =
            new FuturesContract("S", maturityDate3, exchange, currency, new BigDecimal("100"));
      Contract c4 =
            new FuturesContract("S", maturityDate4, exchange, currency, new BigDecimal("100"));
      Contract c5 =
            new FuturesContract("S", maturityDate1, exchange, currency, new BigDecimal("101"));
      
      assertTrue(c1.equals(c1));
      assertEquals(c1.hashCode(), c1.hashCode());
      assertTrue(c1.equals(c2));
      assertEquals(c1.hashCode(), c2.hashCode());
      
      assertFalse(c1.equals(c3));
      assertFalse(c1.equals(c4));
      assertFalse(c1.equals(c5));
   }
}
