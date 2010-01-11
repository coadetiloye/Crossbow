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

package lt.norma.crossbow.exceptions;

import lt.norma.crossbow.contracts.Contract;
import lt.norma.crossbow.contracts.Currency;
import lt.norma.crossbow.contracts.Exchange;
import lt.norma.crossbow.contracts.StockContract;
import lt.norma.crossbow.exceptions.ContractException;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test ContractException class.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class ContractExceptionTest
{
   Contract c;
   
   /**
    * Constructor.
    * 
    * @throws ContractException
    */
   public ContractExceptionTest() throws ContractException
   {
      c = new StockContract("MSFT", Exchange.createNyse(), Currency.createEur());
   }
   
   /**
    * Test the constructor.
    * 
    * @throws ContractException
    */
   @Test
   public void testCreation1() throws ContractException
   {
      Exception cause = new Exception("AAA");
      ContractException ce = new ContractException(c, "Test exception.", cause);
      assertEquals(c, ce.getContract());
      assertEquals(new StockContract("MSFT", Exchange.createNyse(), Currency.createEur()),
                   ce.getContract());
      assertEquals(cause, ce.getCause());
      assertEquals("Contract MSFT is invalid. Test exception.", ce.getMessage());
      ContractException ce2 = new ContractException(null, "Test exception.", cause);
      assertNull(ce2.getContract());
      
      ContractException ce3 = new ContractException(c, "Test exception.", null);
      assertNull(ce3.getCause());
      
      ContractException ce4 = new ContractException(c, null, cause);
      assertEquals("Contract MSFT is invalid. null", ce4.getMessage());
   }
   
   /**
    * Test the constructor.
    */
   @Test
   public void testCreation2()
   {
      ContractException ce = new ContractException(c, "Test exception.");
      assertNull(ce.getCause());
      assertEquals("Contract MSFT is invalid. Test exception.", ce.getMessage());
   }
   
   /**
    * Test the constructor.
    */
   @Test
   public void testCreation3()
   {
      ContractException ce = new ContractException("Test exception.");
      assertEquals("Invalid contract. Test exception.", ce.getMessage());
      assertNull(ce.getCause());
      assertNull(ce.getContract());
   }
   
   /**
    * Test throwing.
    * 
    * @throws ContractException
    */
   @Test(expected = ContractException.class)
   public void testThrowing() throws ContractException
   {
      Exception cause = new Exception("AAA");
      ContractException ce = new ContractException(c, "Test exception.", cause);
      throw ce;
   }
}
