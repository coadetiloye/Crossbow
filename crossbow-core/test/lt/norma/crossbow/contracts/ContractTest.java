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

import lt.norma.crossbow.account.Currency;
import lt.norma.crossbow.contracts.Contract;
import lt.norma.crossbow.contracts.Exchange;
import lt.norma.crossbow.exceptions.ContractException;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test contract class.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class ContractTest
{
   private Exchange exchange = Exchange.createNasdaq();
   private Currency currency = Currency.createUsd();
   
   /**
    * Test constructor.
    * 
    * @throws ContractException
    *            contract is invalid
    */
   @Test
   public void testConstructor() throws ContractException
   {
      Contract contract = new MockContract("AAA", "STOCK", exchange, currency);
      assertEquals("AAA", contract.getSymbol());
      assertEquals("STOCK", contract.getType());
      assertEquals(Exchange.createNasdaq(), contract.getExchange());
   }
   
   /**
    * Test constructor ContractException in constructor.
    * 
    * @throws ContractException
    *            contract is invalid
    */
   @Test(expected = ContractException.class)
   public void testConstructorException1() throws ContractException
   {
      new MockContract("", "STOCK", exchange, currency);
   }
   
   /**
    * Test constructor ContractException in constructor.
    * 
    * @throws ContractException
    *            contract is invalid
    */
   @Test(expected = ContractException.class)
   public void testConstructorException2() throws ContractException
   {
      new MockContract(null, "STOCK", exchange, currency);
   }
   
   /**
    * Test constructor ContractException in constructor.
    * 
    * @throws ContractException
    *            contract is invalid
    */
   @Test(expected = ContractException.class)
   public void testConstructorException3() throws ContractException
   {
      new MockContract("AAA", null, exchange, currency);
   }
   
   /**
    * Test constructor ContractException in constructor.
    * 
    * @throws ContractException
    *            contract is invalid
    */
   @Test(expected = ContractException.class)
   public void testConstructorException4() throws ContractException
   {
      new MockContract("AAA", "FOREX", null, currency);
   }
   
   /**
    * Test constructor ContractException in constructor.
    * 
    * @throws ContractException
    *            contract is invalid
    */
   @Test(expected = ContractException.class)
   public void testConstructorException5() throws ContractException
   {
      new MockContract("AAA", "FOREX", exchange, null);
   }
   
   /**
    * Test of toString method, of class Contract.
    * 
    * @throws ContractException
    *            contract is invalid
    */
   @Test
   public void testToString() throws ContractException
   {
      Contract contract = new MockContract("AAA", "STOCK", exchange, currency);
      assertEquals("AAA (STOCK)", contract.toString());
   }
   
   /**
    * Test of equals method, of class Contract. Other cases are tested in testCompareTo.
    * 
    * @throws ContractException
    *            contract is invalid
    */
   @Test
   public void testEquals() throws ContractException
   {
      Contract contract1 = new MockContract("AAA", "STOCK", exchange, currency);
      Contract contract2 = new MockContract("AAA", "STOCK", exchange, currency);
      Contract contract3 = new MockContract("B", "STOCK", exchange, currency);
      Contract contract4 = new MockContract("AAA", "FOREX", exchange, currency);
      Contract contract5 =
            new MockContract("AAA", "STOCK", Exchange.createNyse(), currency);
      Contract contract6 = new MockContract2("AAA", "STOCK", exchange, currency);
      Contract contract7 = new MockContract("AAA", "STOCK", exchange, Currency.createGbp());
      
      assertTrue(contract1.equals(contract1)); // Equals to itself.
      assertEquals(contract1.hashCode(), contract1.hashCode());
      assertTrue(contract1.equals(contract2)); // Equals to contract with the same fields.
      assertEquals(contract1.hashCode(), contract2.hashCode());
      assertTrue(contract1.equals(contract4)); // Type is not used for comparison.
      assertEquals(contract1.hashCode(), contract2.hashCode());
      
      assertFalse(contract1.equals(contract3));
      assertFalse(contract1.equals(contract5));
      assertFalse(contract1.equals(contract6)); // Classes are different.
      assertFalse(contract1.equals(contract7));
      assertFalse(contract1.equals(new Object()));
      assertFalse(contract1.equals(null));
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
   
   /**
    * Mock contract class.
    */
   private class MockContract2 extends Contract
   {
      public MockContract2(String symbol, String type, Exchange exchange, Currency currency)
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
