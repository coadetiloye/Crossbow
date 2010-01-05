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

import lt.norma.crossbow.contracts.Currency;
import lt.norma.crossbow.contracts.Exchange;
import lt.norma.crossbow.contracts.OptionContract;
import lt.norma.crossbow.contracts.OptionType;
import lt.norma.crossbow.exceptions.ContractException;

import org.joda.time.DateMidnight;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test OptionContract class.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class OptionContractTest
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
      Exchange exchange = Exchange.createNasdaqExchange();
      DateMidnight maturityDate =
            new DateMidnight(2010, 1, 1, DateTimeZone.forID("America/New_York"));
      OptionContract c =
            new OptionContract("S", OptionType.CALL, new BigDecimal("8.05"), maturityDate,
                               exchange, currency, new BigDecimal("100"));
      assertEquals("OPTION", c.type);
      assertEquals(OptionType.CALL, c.getOptionType());
      assertTrue(c.isCall());
      assertFalse(c.isPut());
      assertEquals(new BigDecimal("8.05"), c.getStrike());
   }
   
   /**
    * Test of toString method, of class OptionContract.
    * 
    * @throws ContractException
    */
   @Test
   public void testToString() throws ContractException
   {
      Currency currency = Currency.createJpy();
      Exchange exchange = Exchange.createNasdaqExchange();
      DateMidnight maturityDate =
            new DateMidnight(2010, 1, 1, DateTimeZone.forID("America/New_York"));
      OptionContract c =
            new OptionContract("S", OptionType.CALL, new BigDecimal("8.05"), maturityDate,
                               exchange, currency, new BigDecimal("100"));
      
      assertEquals("S CALL 2010-01-01@8.05", c.toString());
   }
   
   /**
    * Test of compareTo method, of class OptionContract.
    * 
    * @throws ContractException
    */
   @Test
   public void testCompareTo() throws ContractException
   {
      Currency currency = Currency.createJpy();
      Exchange exchange = Exchange.createNasdaqExchange();
      DateMidnight maturityDate =
            new DateMidnight(2010, 1, 1, DateTimeZone.forID("America/New_York"));
      
      OptionContract c1 =
            new OptionContract("S", OptionType.CALL, new BigDecimal("8.05"), maturityDate,
                               exchange, currency, new BigDecimal("100"));
      OptionContract c2 =
            new OptionContract("S", OptionType.CALL, new BigDecimal("8.05"), maturityDate,
                               exchange, currency, new BigDecimal("100"));
      OptionContract c3 =
            new OptionContract("S", OptionType.PUT, new BigDecimal("8.05"), maturityDate, exchange,
                               currency, new BigDecimal("100"));
      
      assertEquals(c1, c1);
      assertEquals(c1, c2);
      assertTrue(c1.compareTo(c2) == 0);
      assertTrue(c1.compareTo(c3) < 0);
      assertTrue(c3.compareTo(c1) > 0);
   }
   
   /**
    * Test of compareTo method, of class OptionContract.
    * 
    * @throws ContractException
    */
   @Test
   public void testCompareTo2() throws ContractException
   {
      Currency currency = Currency.createJpy();
      Exchange exchange = Exchange.createNasdaqExchange();
      DateMidnight maturityDate =
            new DateMidnight(2010, 1, 1, DateTimeZone.forID("America/New_York"));
      
      OptionContract c1 =
            new OptionContract("S", OptionType.CALL, new BigDecimal("8.05"), maturityDate,
                               exchange, currency, new BigDecimal("100"));
      OptionContract c2 =
            new OptionContract("S", OptionType.CALL, new BigDecimal("8.05"), maturityDate,
                               exchange, currency, new BigDecimal("100"));
      OptionContract c3 =
            new OptionContract("S", OptionType.CALL, new BigDecimal("8.06"), maturityDate,
                               exchange, currency, new BigDecimal("100"));
      
      assertEquals(c1, c1);
      assertEquals(c1, c2);
      assertTrue(c1.compareTo(c2) == 0);
      assertTrue(c1.compareTo(c3) < 0);
      assertTrue(c3.compareTo(c1) > 0);
   }
}
