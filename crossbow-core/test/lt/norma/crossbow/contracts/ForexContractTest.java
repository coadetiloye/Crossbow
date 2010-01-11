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

import lt.norma.crossbow.contracts.Currency;
import lt.norma.crossbow.contracts.Exchange;
import lt.norma.crossbow.contracts.ForexContract;
import lt.norma.crossbow.exceptions.ContractException;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test ForexContract class.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class ForexContractTest
{
   /**
    * Test constructor.
    * 
    * @throws ContractException
    */
   @Test
   public void testCreation() throws ContractException
   {
      Currency currency1 = Currency.createEur();
      Currency currency2 = Currency.createJpy();
      Exchange exchange = Exchange.createNasdaq();
      ForexContract c = new ForexContract(currency1, currency2, exchange);
      
      assertEquals(currency1, c.getCurrency1());
      assertEquals(new Currency("EUR"), c.getCurrency1());
      assertEquals(currency2, c.getCurrency2());
      assertEquals(new Currency("JPY"), c.getCurrency2());
      assertEquals("FOREX", c.type);
   }
   
   /**
    * Test constructor.
    * 
    * @throws ContractException
    */
   @Test(expected = ContractException.class)
   public void testCreation2() throws ContractException
   {
      Currency currency2 = Currency.createJpy();
      Exchange exchange = Exchange.createNasdaq();
      new ForexContract(null, currency2, exchange);
   }
   
   /**
    * Test constructor.
    * 
    * @throws ContractException
    */
   @Test(expected = ContractException.class)
   public void testCreation3() throws ContractException
   {
      Currency currency1 = Currency.createJpy();
      Exchange exchange = Exchange.createNasdaq();
      new ForexContract(currency1, null, exchange);
   }
   
   /**
    * Test constructor.
    * 
    * @throws ContractException
    */
   @Test(expected = ContractException.class)
   public void testCreation4() throws ContractException
   {
      Currency currency1 = Currency.createJpy();
      Exchange exchange = Exchange.createNasdaq();
      new ForexContract(currency1, currency1, exchange);
   }
   
   /**
    * Test constructor.
    * 
    * @throws ContractException
    */
   @Test(expected = ContractException.class)
   public void testCreation5() throws ContractException
   {
      Currency currency1 = Currency.createJpy();
      Exchange exchange = Exchange.createNasdaq();
      new ForexContract(currency1, new Currency("JPY"), exchange);
   }
   
   /**
    * Test of toString method, of class ForexContract.
    * 
    * @throws ContractException
    */
   @Test
   public void testToString() throws ContractException
   {
      Currency currency1 = Currency.createUsd();
      Currency currency2 = Currency.createGbp();
      Exchange exchange = Exchange.createNasdaq();
      ForexContract c = new ForexContract(currency1, currency2, exchange);
      assertEquals("USD/GBP", c.toString());
   }
   
   /**
    * Test of equals method, of class ForexContract.
    * 
    * @throws ContractException
    */
   @Test
   public void testEquals() throws ContractException
   {
      Currency currency1 = Currency.createJpy();
      Currency currency2 = Currency.createGbp();
      Currency currency3 = Currency.createEur();
      Currency currency4 = Currency.createUsd();
      Exchange exchange = Exchange.createNasdaq();
      
      Contract c1 = new ForexContract(currency1, currency2, exchange);
      Contract c2 = new ForexContract(currency1, currency2, exchange);
      Contract c3 = new ForexContract(currency3, currency2, exchange);
      Contract c4 = new ForexContract(currency1, currency4, exchange);
      Contract s = new MockContract("JPY", "MOCK", exchange, currency2);
      
      assertTrue(c1.equals(c1));
      assertEquals(c1.hashCode(), c1.hashCode());
      assertTrue(c1.equals(c2));
      assertEquals(c1.hashCode(), c2.hashCode());
      assertTrue(c2.equals(c1));
      
      assertFalse(c1.equals(c3));
      assertFalse(c1.equals(c4));
      assertFalse(c3.equals(c4));
      assertFalse(c1.equals(s));
   }
   
   /**
    * Mock contract class.
    */
   private class MockContract extends Contract
   {
      public MockContract(String symbol, String type, Exchange exchange, Currency currency)
            throws ContractException
      {
         super(symbol, type, exchange, currency);
      }
      
      @Override
      public boolean contractEquals(Contract contract)
      {
         return true;
      }
   }
}
