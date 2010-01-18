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

package lt.norma.crossbow.account;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import lt.norma.crossbow.exceptions.CrossbowException;

import org.junit.Test;

/**
 * @author Vilius Normantas <code@norma.lt>
 */
public class MoneyTest
{
   Currency eur = Currency.createEur();
   Currency usd = Currency.createUsd();
   Currency jpy = Currency.createJpy();
   
   /**
    * Test creation of the object.
    */
   @Test
   public void testCreation()
   {
      Money m1 = new Money(eur);
      assertEquals(eur, m1.getCurrency());
      assertTrue(BigDecimal.ZERO.compareTo(m1.getAmount()) == 0);
      
      Money m2 = new Money(new BigDecimal("88"), usd);
      assertEquals(usd, m2.getCurrency());
      assertTrue((new BigDecimal("88.00")).compareTo(m2.getAmount()) == 0);
   }
   
   /**
    * Test methods <code>hashCode</code> and <code>equals</code>.
    */
   @Test
   public void testHashCodeAndEquals()
   {
      Money m1 = new Money(new BigDecimal("88"), usd);
      Money m2 = new Money(new BigDecimal("88"), usd);
      Money m3 = new Money(new BigDecimal("88.00"), usd);
      Money m4 = new Money(new BigDecimal("70"), usd);
      Money m5 = new Money(new BigDecimal("88"), eur);
      Money m6 = new Money(new BigDecimal("50"), jpy);
      
      assertTrue(m1.equals(m1));
      assertEquals(m1.hashCode(), m1.hashCode());
      assertTrue(m1.equals(m2));
      assertEquals(m2.hashCode(), m1.hashCode());
      assertTrue(m1.equals(m3));
      assertEquals(m3.hashCode(), m1.hashCode());
      
      assertFalse(m1.equals(new Object()));
      assertFalse(m1.equals(null));
      assertFalse(m1.equals(m4));
      assertFalse(m1.equals(m5));
      assertFalse(m1.equals(m6));
   }
   
   /**
    * Test <code>compareTo</code> method.
    */
   @Test
   public void testCompareTo()
   {
      Money m1 = new Money(new BigDecimal("88"), jpy);
      Money m2 = new Money(new BigDecimal("88"), jpy);
      Money m3 = new Money(new BigDecimal("88.00"), jpy);
      Money m4 = new Money(new BigDecimal("50"), jpy);
      Money m5 = new Money(new BigDecimal("100"), jpy);
      
      Money m6 = new Money(new BigDecimal("88"), eur);
      Money m7 = new Money(new BigDecimal("100"), eur);
      Money m8 = new Money(new BigDecimal("50"), eur);
      
      Money m9 = new Money(new BigDecimal("88"), usd);
      Money m10 = new Money(new BigDecimal("100"), usd);
      Money m11 = new Money(new BigDecimal("50"), usd);
      
      assertEquals(0, m1.compareTo(m1));
      assertEquals(0, m1.compareTo(m2));
      assertEquals(0, m1.compareTo(m3));
      assertTrue(m1.compareTo(m4) > 0);
      assertTrue(m1.compareTo(m5) < 0);
      
      assertTrue(m1.compareTo(m6) > 0);
      assertTrue(m1.compareTo(m7) > 0);
      assertTrue(m1.compareTo(m8) > 0);
      
      assertTrue(m1.compareTo(m9) < 0);
      assertTrue(m1.compareTo(m10) < 0);
      assertTrue(m1.compareTo(m11) < 0);
   }
   
   /**
    * Test <code>toString</code> method.
    */
   @Test
   public void testToString()
   {
      Money m;
      
      m = new Money(new BigDecimal("88"), usd);
      assertEquals("88.00 USD", m.toString());
      
      m = new Money(new BigDecimal("1234567890123456789.125"), eur);
      assertEquals("1234567890123456789.13 EUR", m.toString());
      
      m = new Money(new BigDecimal("1234567890123456789.123456789"), jpy);
      assertEquals("1234567890123456789 JPY", m.toString());
   }
   
   /**
    * Test <code>add</code> method.
    * 
    * @throws CrossbowException
    */
   @Test
   public void testAdd() throws CrossbowException
   {
      Money m1, m2, r;
      
      m1 = new Money(new BigDecimal("88"), usd);
      m2 = new Money(new BigDecimal("5"), usd);
      r = new Money(new BigDecimal("93"), usd);
      m1.add(m2);
      assertEquals(r, m1);
      
      m1 = new Money(new BigDecimal("88"), usd);
      m2 = new Money(new BigDecimal("-0.001"), usd);
      r = new Money(new BigDecimal("87.999"), usd);
      m1.add(m2);
      assertEquals(r, m1);
   }
   
   /**
    * Test <code>add</code> method.
    * 
    * @throws CrossbowException
    */
   @Test(expected = CrossbowException.class)
   public void testAdd2() throws CrossbowException
   {
      Money m1, m2;
      
      m1 = new Money(new BigDecimal("88"), usd);
      m2 = new Money(new BigDecimal("5"), eur);
      m1.add(m2);
   }
   
   /**
    * Test <code>subtract</code> method.
    * 
    * @throws CrossbowException
    */
   @Test
   public void testSubtract() throws CrossbowException
   {
      Money m1, m2, r;
      
      m1 = new Money(new BigDecimal("88"), usd);
      m2 = new Money(new BigDecimal("5"), usd);
      r = new Money(new BigDecimal("83.00"), usd);
      m1.subtract(m2);
      assertEquals(r, m1);
      
      m1 = new Money(new BigDecimal("88"), usd);
      m2 = new Money(new BigDecimal("-0.001"), usd);
      r = new Money(new BigDecimal("88.001"), usd);
      m1.subtract(m2);
      assertEquals(r, m1);
   }
   
   /**
    * Test <code>subtract</code> method.
    * 
    * @throws CrossbowException
    */
   @Test(expected = CrossbowException.class)
   public void testSubtract2() throws CrossbowException
   {
      Money m1, m2;
      
      m1 = new Money(new BigDecimal("88"), usd);
      m2 = new Money(new BigDecimal("5"), eur);
      m1.subtract(m2);
   }
   
   /**
    * Test <code>multiply</code> method.
    */
   @Test
   public void testMultiply()
   {
      Money m1, r;
      
      m1 = new Money(new BigDecimal("88"), usd);
      r = new Money(new BigDecimal("352"), usd);
      m1.multiply(new BigDecimal("4"));
      assertEquals(r, m1);
   }
   
   /**
    * Test <code>divide</code> method.
    */
   @Test
   public void testDivide()
   {
      Money m1, r;
      
      m1 = new Money(new BigDecimal("88"), usd);
      r = new Money(new BigDecimal("22"), usd);
      m1.divide(new BigDecimal("4"));
      assertEquals(r, m1);
   }
}
