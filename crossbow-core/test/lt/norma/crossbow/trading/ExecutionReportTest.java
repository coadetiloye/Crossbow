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

import java.math.BigDecimal;

import lt.norma.crossbow.contracts.Contract;
import lt.norma.crossbow.contracts.Currency;
import lt.norma.crossbow.contracts.Exchange;
import lt.norma.crossbow.contracts.StockContract;
import lt.norma.crossbow.exceptions.ContractException;
import lt.norma.crossbow.exceptions.OrderException;
import lt.norma.crossbow.orders.Order;
import lt.norma.crossbow.orders.OrderDirection;
import lt.norma.crossbow.trading.ExecutionReport;

import org.joda.time.DateTime;
import org.junit.Test;

/**
 * Test ExecutionReportTest class.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class ExecutionReportTest
{
   /**
    * Test the constructor.
    * 
    * @throws OrderException
    * @throws ContractException
    */
   @Test
   public void testCreation() throws OrderException, ContractException
   {
      Currency currency = Currency.createJpy();
      Exchange exchange = Exchange.createNasdaq();
      StockContract c = new StockContract("ABC", exchange, currency);
      Order o = new MockOrder(55, c, "MYORDER", OrderDirection.SELL, 800);
      FilledBlock b = new FilledBlock(100, new BigDecimal("88"), new DateTime());
      
      ExecutionReport r = new ExecutionReport(o, b);
      assertEquals(o, r.getOrder());
      assertEquals(b, r.getFilledBlock());
   }
   
   /**
    * Mock order.
    */
   private class MockOrder extends Order
   {
      public MockOrder(long id, Contract contract, String type, OrderDirection direction, int size)
            throws OrderException
      {
         super(id, contract, type, direction, size);
      }
   }
   
}
