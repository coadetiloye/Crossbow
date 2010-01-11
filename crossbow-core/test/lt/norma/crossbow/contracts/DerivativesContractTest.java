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

import lt.norma.crossbow.contracts.Contract;
import lt.norma.crossbow.contracts.Currency;
import lt.norma.crossbow.contracts.DerivativesContract;
import lt.norma.crossbow.contracts.Exchange;
import lt.norma.crossbow.exceptions.ContractException;

import org.joda.time.DateMidnight;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test DerivativesContract class.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class DerivativesContractTest
{
   /**
    * Test constructor.
    * 
    * @throws ContractException
    */
   @Test
   public void testCreation() throws ContractException
   {
      DerivativesContract c = new MockDerivativesContract(new DateMidnight(
            2010, 1, 1, DateTimeZone.forID("America/New_York")),
            new BigDecimal("10"));
      
      assertEquals(new DateMidnight(2010, 1, 1, DateTimeZone
            .forID("America/New_York")), c.getMaturityDate());
      assertEquals(new BigDecimal("10"), c.getMultiplier());
      // "US/Eastern" is an alias of "America/New_York".
      assertEquals(new DateMidnight(2010, 1, 1, DateTimeZone
            .forID("US/Eastern")), c.getMaturityDate());
      assertFalse(new DateMidnight(2010, 1, 1, DateTimeZone
            .forID("Pacific/Noumea")).equals(c.getMaturityDate()));
      assertNotNull(c.dateFormatter);
      assertNull(c.getUnderlyingContract());
      assertEquals(DateTimeZone.forID("America/New_York"), c.dateFormatter
            .getZone());
   }
   
   /**
    * Test constructor. With underlying contract.
    * 
    * @throws ContractException
    */
   @Test
   public void testCreationU() throws ContractException
   {
      DerivativesContract cU = new MockDerivativesContract(new DateMidnight(
            2005, 1, 1, DateTimeZone.forID("America/New_York")),
            new BigDecimal("8"));
      DerivativesContract c = new MockDerivativesContract(new DateMidnight(
            2010, 1, 1, DateTimeZone.forID("America/New_York")),
            new BigDecimal("10"), cU);
      
      assertEquals(cU, c.getUnderlyingContract());
   }
   
   /**
    * Test constructor.
    * 
    * @throws ContractException
    */
   @Test(expected = ContractException.class)
   public void testCreation2() throws ContractException
   {
      new MockDerivativesContract(null, new BigDecimal("1"));
   }
   
   /**
    * Test constructor.
    * 
    * @throws ContractException
    */
   @Test(expected = ContractException.class)
   public void testCreation3() throws ContractException
   {
      new MockDerivativesContract(new DateMidnight(2010, 1, 1, DateTimeZone
            .forID("US/Eastern")), new BigDecimal("0"));
   }
   
   /**
    * Test of yearsToMaturity method, of class DerivativesContract.
    * 
    * @throws Exception
    */
   @Test
   public void testYearsToMaturity() throws Exception
   {
      DateTimeZone timeZone = DateTimeZone.forID("America/New_York");
      DerivativesContract c = new MockDerivativesContract(new DateMidnight(
            2009, 12, 31, timeZone), new BigDecimal("1"));
      
      // Precision is set to half a day, or (0.5 / 365.0) years.
      assertEquals(1.0, c.yearsToMaturity(new DateMidnight(2008, 12, 31, timeZone)), 0.5 / 365.0);
      assertEquals(2.0, c.yearsToMaturity(new DateMidnight(2007, 12, 31, timeZone)), 0.5 / 365.0);
      assertEquals(20.0, c.yearsToMaturity(new DateMidnight(1989, 12, 31, timeZone)), 0.5 / 365.0);
      assertEquals(0.5, c.yearsToMaturity(new DateMidnight(2009, 7, 1, timeZone)), 0.5 / 365.0);
   }
   
   /**
    * Test of yearsToMaturity method, of class DerivativesContract.
    * 
    * @throws Exception
    */
   @Test(expected = ContractException.class)
   public void testYearsToMaturity2() throws Exception
   {
      DateTimeZone timeZone = DateTimeZone.forID("America/New_York");
      DerivativesContract c = new MockDerivativesContract(
            new DateMidnight(2009, 12, 31, timeZone), new BigDecimal("1"));
      c.yearsToMaturity(new DateMidnight(2010, 1, 1, timeZone));
   }
   
   /**
    * Test of equals method, of class DerivativesContract.
    * 
    * @throws ContractException
    */
   @Test
   public void testEquals() throws ContractException
   {
      Contract contract1 = new MockDerivativesContract(
            new DateMidnight(2000, 12, 20), new BigDecimal("88.0123"), new MockContract("A"));
      Contract contract2 = new MockDerivativesContract(
            new DateMidnight(2000, 12, 20), new BigDecimal("88.012300"), new MockContract("A"));
      Contract contract3 = new MockDerivativesContract(
            new DateMidnight(1999, 12, 20), new BigDecimal("88.0123"), new MockContract("A"));
      Contract contract4 = new MockDerivativesContract(
            new DateMidnight(2000, 12, 20), new BigDecimal("88"), new MockContract("A"));
      Contract contract5 = new MockDerivativesContract(
            new DateMidnight(2000, 12, 20), new BigDecimal("88.0123"), new MockContract("AB"));
      Contract contract6 = new MockDerivativesContract(
            new DateMidnight(2000, 12, 20), new BigDecimal("88.0123"));
      
      assertTrue(contract1.equals(contract1));
      assertEquals(contract1.hashCode(), contract1.hashCode());
      assertTrue(contract1.equals(contract2));
      assertEquals(contract2.hashCode(), contract1.hashCode());
      
      assertFalse(contract1.equals(contract3));
      assertFalse(contract1.equals(contract4));
      assertFalse(contract1.equals(contract5));
      assertFalse(contract1.equals(contract6));
      assertFalse(contract6.equals(contract1));
   }
   
   /**
    * Mock DerivativesContract class.
    */
   public class MockDerivativesContract extends DerivativesContract
   {
      /**
       * Constructor. With underlying contract.
       * 
       * @param maturityDate
       *           expiration date of a derivatives contract
       * @param multiplier
       *           price multiplier
       * @param underlyingContract
       *           underlying contract
       * @throws ContractException
       *            throws an exception if invalid symbol is specified
       */
      public MockDerivativesContract(DateMidnight maturityDate,
            BigDecimal multiplier, Contract underlyingContract)
            throws ContractException
      {
         super("MSFT", "FUTURES", maturityDate, Exchange.createNasdaq(),
               Currency.createJpy(), multiplier, underlyingContract);
      }
      
      /**
       * Constructor.
       * 
       * @param maturityDate
       *           expiration date of a derivatives contract
       * @param multiplier
       *           price multiplier
       * @throws ContractException
       *            throws an exception if invalid symbol is specified
       */
      public MockDerivativesContract(DateMidnight maturityDate,
            BigDecimal multiplier) throws ContractException
      {
         super("MSFT", "FUTURES", maturityDate, Exchange.createNasdaq(),
               Currency.createJpy(), multiplier);
      }
      
      @Override
      protected boolean derivativeEquals(DerivativesContract contract)
      {
         return true;
      }
   }
   
   /**
    * Mock contract class.
    */
   private class MockContract extends Contract
   {
      public MockContract(String symbol) throws ContractException
      {
         super(symbol, "TTT", Exchange.createNasdaq(), Currency.createEur());
      }
      
      @Override
      public boolean contractEquals(Contract contract)
      {
         return true;
      }
   }
}
