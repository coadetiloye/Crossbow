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

import java.math.BigDecimal;

import lt.norma.crossbow.contracts.Currency;
import lt.norma.crossbow.contracts.Exchange;
import lt.norma.crossbow.contracts.StockContract;
import lt.norma.crossbow.exceptions.ContractException;
import lt.norma.crossbow.exceptions.OrderException;
import lt.norma.crossbow.orders.Direction;
import lt.norma.crossbow.orders.StopOrder;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test StopOrder class.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class StopOrderTest
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
      StopOrder o = new StopOrder(-50, c, Direction.BUY, 500, new BigDecimal("-5"));
      
      assertTrue(o.getStopPrice().compareTo(new BigDecimal("-5.0")) == 0);
      assertTrue(o.toString().endsWith(" at -5"));
      assertEquals("stop", o.getType());
   }
}
