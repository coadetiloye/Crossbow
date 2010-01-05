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

package lt.norma.crossbow.commissions;

import java.math.BigDecimal;

import lt.norma.crossbow.commissions.Commissions;
import lt.norma.crossbow.commissions.CommissionsFlatRate;
import lt.norma.crossbow.commissions.CommissionsIbOptions;
import lt.norma.crossbow.commissions.CommissionsIbStocks;
import lt.norma.crossbow.commissions.CommissionsPerShare;
import lt.norma.crossbow.commissions.CommissionsZero;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test commissions classes.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class CommissionsTest
{
   /**
    * Test of calculate method, of class CommissionsFlatRate.
    */
   @Test
   public void testCommissionsFlatRate()
   {
      Commissions c = new CommissionsFlatRate(new BigDecimal("50"));
      assertTrue(c.calculate(0, BigDecimal.ZERO).compareTo(new BigDecimal("50.00")) == 0);
      assertTrue(c.calculate(-50, new BigDecimal("0.002")).compareTo(new BigDecimal("50.00")) == 0);
      assertTrue(c.calculate(10000, new BigDecimal("200.50")).compareTo(new BigDecimal("50.00")) == 0);
   }
   
   /**
    * Test of calculate method, of class CommissionsZero.
    */
   @Test
   public void testCommissionsZero()
   {
      Commissions c = new CommissionsZero();
      assertTrue(c.calculate(0, BigDecimal.ZERO).compareTo(new BigDecimal("0.00")) == 0);
      assertTrue(c.calculate(-50, new BigDecimal("0.002")).compareTo(new BigDecimal(0)) == 0);
      assertTrue(c.calculate(10000, new BigDecimal("200.50")).compareTo(new BigDecimal("0")) == 0);
   }
   
   /**
    * Test of calculate method, of class CommissionsPerShare.
    */
   @Test
   public void testCommissionsPerShare()
   {
      Commissions c = new CommissionsPerShare(new BigDecimal("0.5"));
      assertTrue(c.calculate(0, BigDecimal.ZERO).compareTo(new BigDecimal("-0")) == 0);
      assertTrue(c.calculate(-50, new BigDecimal("0.002")).compareTo(new BigDecimal("-25")) == 0);
      assertTrue(c.calculate(10000, new BigDecimal("200.50")).compareTo(new BigDecimal("5000.00")) == 0);
   }
   
   /**
    * Test of calculate method, of class CommissionsIbStocks.
    * <ul>
    * <li>0.005 USD per share
    * <li>Maximum 0.5% of trade value per order (exchange, ECN, and specialist fees are <b>not</b>
    * included)
    * <li>Minimum 1.0 USD per order
    * </ul>
    */
   @Test
   public void testCommissionsIbStocks()
   {
      Commissions c = new CommissionsIbStocks();
      assertTrue(c.calculate(1, new BigDecimal("200")).compareTo(new BigDecimal("1")) == 0);
      assertTrue(c.calculate(199, new BigDecimal("200")).compareTo(new BigDecimal("1")) == 0);
      assertTrue(c.calculate(201, new BigDecimal("200")).compareTo(new BigDecimal("1.005")) == 0);
      assertTrue(c.calculate(1000, new BigDecimal("200")).compareTo(new BigDecimal("5")) == 0);
      assertTrue(c.calculate(10000, new BigDecimal("1")).compareTo(new BigDecimal("50")) == 0);
      assertTrue(c.calculate(10000, new BigDecimal("0.999")).compareTo(new BigDecimal("49.95")) == 0);
      assertTrue(c.calculate(1000000, new BigDecimal("0.05")).compareTo(new BigDecimal("250")) == 0);
   }
   
   /**
    * Test of calculate method, of class CommissionsIbOptions.
    * <ul>
    * <li>0.70 USD per contract, if premium >= 0.10 USD
    * <li>0.50 USD per contract, if premium >= 0.05 USD and premium {@literal <} 0.10 USD
    * <li>0.25 USD per contract, if premium {@literal <} 0.05 USD
    * <li>Minimum 1.0 USD per order
    * </ul>
    */
   @Test
   public void testCommissionsIbOptions()
   {
      Commissions c = new CommissionsIbOptions();
      // premium >= 0.10 USD
      assertTrue(c.calculate(3, new BigDecimal("0.1")).compareTo(new BigDecimal("2.1")) == 0);
      assertTrue(c.calculate(2, new BigDecimal("500")).compareTo(new BigDecimal("1.4")) == 0);
      assertTrue(c.calculate(1, new BigDecimal("500")).compareTo(new BigDecimal("1")) == 0);
      // premium >= 0.05 USD and < 0.10 USD
      assertTrue(c.calculate(3, new BigDecimal("0.05")).compareTo(new BigDecimal("1.5")) == 0);
      assertTrue(c.calculate(14, new BigDecimal("0.05001")).compareTo(new BigDecimal("7")) == 0);
      assertTrue(c.calculate(1, new BigDecimal("0.08")).compareTo(new BigDecimal("1")) == 0);
      assertTrue(c.calculate(2, new BigDecimal("0.09999")).compareTo(new BigDecimal("1")) == 0);
      // premium < 0.05 USD
      assertTrue(c.calculate(5, new BigDecimal("0.000001")).compareTo(new BigDecimal("1.25")) == 0);
      assertTrue(c.calculate(14, new BigDecimal("0.02")).compareTo(new BigDecimal("3.5")) == 0);
      assertTrue(c.calculate(1, new BigDecimal("0.02")).compareTo(new BigDecimal("1")) == 0);
      assertTrue(c.calculate(20, new BigDecimal("0.04999")).compareTo(new BigDecimal("5")) == 0);
   }
}
