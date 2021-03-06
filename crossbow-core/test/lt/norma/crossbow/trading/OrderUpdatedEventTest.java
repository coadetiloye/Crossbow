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

package lt.norma.crossbow.trading;

import static org.junit.Assert.assertEquals;
import lt.norma.crossbow.account.Currency;
import lt.norma.crossbow.contracts.Contract;
import lt.norma.crossbow.contracts.Exchange;
import lt.norma.crossbow.contracts.StockContract;
import lt.norma.crossbow.exceptions.ContractException;
import lt.norma.crossbow.exceptions.OrderException;
import lt.norma.crossbow.orders.Order;
import lt.norma.crossbow.orders.Direction;

import org.junit.Test;

/**
 * @author Vilius Normantas <code@norma.lt>
 */
public class OrderUpdatedEventTest
{
   /**
    * @throws ContractException
    * @throws OrderException
    */
   @Test
   public void testOrderUpdateEvent() throws ContractException, OrderException
   {
      Currency currency = Currency.createJpy();
      Exchange exchange = Exchange.createNasdaq();
      StockContract c = new StockContract("ABC", exchange, currency);
      MockOrder o = new MockOrder(55, c, "MYORDER", Direction.SHORT, 800);
      
      Object source = new Object();
      OrderUpdatedEvent oue = new OrderUpdatedEvent(source, o);
      assertEquals(source, oue.getSource());
      assertEquals(o, oue.getOrder());
   }
   
   /**
    * Mock order.
    */
   private class MockOrder extends Order
   {
      public MockOrder(long id, Contract contract, String type, Direction direction, int size)
            throws OrderException
      {
         super(id, contract, type, direction, size);
      }
   }
}
