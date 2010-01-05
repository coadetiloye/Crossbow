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
import lt.norma.crossbow.exceptions.InvalidArgumentRuntimeException;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test currency class.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class CurrencyTest
{
   /**
    * Test the constructor.
    */
   @Test
   public void testCreation1()
   {
      Currency c = new Currency("DDD", "My ddd currency");
      assertEquals("DDD", c.getCode());
      assertEquals("My ddd currency", c.getName());
      
      Currency c2 = new Currency("FFF2");
      assertEquals("FFF2", c2.getCode());
      assertEquals("", c2.getName());
      
      Currency c3 = new Currency("FFF3", null);
      assertEquals("FFF3", c3.getCode());
      assertEquals("", c3.getName());
   }
   
   /**
    * Test the constructor.
    */
   @Test(expected = InvalidArgumentRuntimeException.class)
   public void testCreation2()
   {
      new Currency("");
   }
   
   /**
    * Test the constructor.
    */
   @Test(expected = InvalidArgumentRuntimeException.class)
   public void testCreation3()
   {
      new Currency("", "Name");
   }
   
   /**
    * Test the constructor.
    */
   @Test(expected = InvalidArgumentRuntimeException.class)
   public void testCreation4()
   {
      new Currency(null);
   }
   
   /**
    * Test the constructor.
    */
   @Test(expected = InvalidArgumentRuntimeException.class)
   public void testCreation5()
   {
      new Currency(null, "Name");
   }
   
   /**
    * Test of setDecimalPlaces, formatNumber and formatNumberWithCurrency methods, of class
    * Currency.
    */
   @Test
   public void testFormatNumber()
   {
      Currency c = new Currency("FFF");
      c.setDecimalPlaces(5);
      assertEquals("2.12345", c.formatNumber(new BigDecimal("2.1234511111")));
      assertEquals("2.12346", c.formatNumber(new BigDecimal("2.1234599999")));
      assertEquals("2.12346FFF", c.formatNumberWithCurrency(new BigDecimal("2.1234599999")));
      c.setDecimalPlaces(0);
      assertEquals("2", c.formatNumber(new BigDecimal("2.1234511111")));
      assertEquals("3", c.formatNumber(new BigDecimal("2.91234599999")));
      assertEquals("1231231231", c.formatNumber(new BigDecimal("1231231231")));
      assertEquals("1231231231FFF", c.formatNumberWithCurrency(new BigDecimal("1231231231")));
   }
   
   /**
    * Test of toString method, of class Currency.
    */
   @Test
   public void testToString()
   {
      Currency c = new Currency("FFF");
      assertEquals("FFF", c.toString());
   }
   
   /**
    * Test of equals method, of class Currency.
    */
   @Test
   public void testEquals()
   {
      Currency c = new Currency("DDD", "One One One");
      Currency c2 = new Currency("DDD", "Another Another");
      Currency c3 = new Currency("FFF", "One One One");
      assertEquals(c, c);
      assertEquals(c.hashCode(), c.hashCode());
      assertEquals(c2, c);
      assertEquals(c2.hashCode(), c.hashCode());
      assertFalse(c.equals(c3));
      assertFalse(c.equals(null));
      assertFalse(c.equals(new Object()));
   }
   
   /**
    * Test of compareTo method, of class Currency.
    */
   @Test
   public void testCompareTo()
   {
      Currency c = new Currency("DDD", "One One One");
      Currency c2 = new Currency("DDD", "Another Another");
      Currency c3 = new Currency("CCC", "Another Another");
      Currency c4 = new Currency("EEE", "Another Another");
      assertEquals(0, c.compareTo(c2));
      assertTrue(c.compareTo(c3) > 0);
      assertTrue(c.compareTo(c4) < 0);
   }
   
   /**
    * Test of createUsd method, of class Currency.
    */
   @Test
   public void testCreateUsd()
   {
      Currency usd = Currency.createUsd();
      assertEquals("USD", usd.getCode());
   }
   
   /**
    * Test of createEur method, of class Currency.
    */
   @Test
   public void testCreateEur()
   {
      Currency eur = Currency.createEur();
      assertEquals("EUR", eur.getCode());
   }
   
   /**
    * Test of createGbp method, of class Currency.
    */
   @Test
   public void testCreateGbp()
   {
      Currency gbp = Currency.createGbp();
      assertEquals("GBP", gbp.getCode());
   }
   
   /**
    * Test of createJpy method, of class Currency.
    */
   @Test
   public void testCreateJpy()
   {
      Currency jpy = Currency.createJpy();
      assertEquals("JPY", jpy.getCode());
   }
}
