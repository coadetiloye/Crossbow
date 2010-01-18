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

package lt.norma.crossbow.orders;

import static org.junit.Assert.assertEquals;
import lt.norma.crossbow.account.Currency;
import lt.norma.crossbow.contracts.Exchange;
import lt.norma.crossbow.contracts.StockContract;
import lt.norma.crossbow.exceptions.ContractException;
import lt.norma.crossbow.exceptions.OrderException;

import org.junit.Test;

/**
 * Test MarketOrderTest class.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class MarketOrderTest
{
   /**
    * Test the constructor.
    * 
    * @throws ContractException
    * @throws OrderException
    */
   @Test
   public void testCreation() throws ContractException, OrderException
   {
      Currency currency = Currency.createJpy();
      Exchange exchange = Exchange.createNasdaq();
      StockContract c = new StockContract("ABC", exchange, currency);
      MarketOrder o = new MarketOrder(0, c, Direction.LONG, 500);
      assertEquals("market", o.getType());
   }
}
