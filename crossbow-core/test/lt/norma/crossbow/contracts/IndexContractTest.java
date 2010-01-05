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
import lt.norma.crossbow.contracts.IndexContract;
import lt.norma.crossbow.exceptions.ContractException;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test IndexContract class.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class IndexContractTest
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
      IndexContract c = new IndexContract("ABC", exchange, currency);
      
      assertEquals("INDEX", c.type);
   }
   
   /**
    * Test of toString method, of class IndexContract.
    * 
    * @throws ContractException
    */
   @Test
   public void testToString() throws ContractException
   {
      Currency currency = Currency.createJpy();
      Exchange exchange = Exchange.createNasdaqExchange();
      IndexContract c = new IndexContract("ABC", exchange, currency);
      
      assertEquals("$ABC", c.toString());
   }
}
