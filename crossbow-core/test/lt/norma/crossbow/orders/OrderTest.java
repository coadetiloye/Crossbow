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

import static org.junit.Assert.*;
import lt.norma.crossbow.account.Currency;
import lt.norma.crossbow.contracts.Contract;
import lt.norma.crossbow.contracts.Exchange;
import lt.norma.crossbow.contracts.StockContract;
import lt.norma.crossbow.exceptions.ContractException;
import lt.norma.crossbow.exceptions.InvalidArgumentRuntimeException;
import lt.norma.crossbow.exceptions.OrderException;

import org.joda.time.DateTime;
import org.junit.Test;

/**
 * Test OrderTest class.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class OrderTest
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
      MockOrder o = new MockOrder(55, c, "MYORDER", Direction.SHORT, 800);
      assertEquals(55, o.getId());
      assertEquals(c, o.getContract());
      assertEquals("MYORDER", o.getType());
      assertTrue(o.isSell());
      assertFalse(o.isBuy());
      assertEquals(800, o.getSize());
      assertEquals(OrderStatus.NEW, o.getStatus());
      assertNull(o.getSubmitTime());
      assertNotNull(o.getAttributes());
      
      MockOrder o2 = new MockOrder(55, c, "MYORDER", Direction.LONG, 800);
      assertTrue(o2.isBuy());
      assertFalse(o2.isSell());
   }
   
   /**
    * Test method for {@link Order#isActive()}.
    * 
    * @throws ContractException
    * @throws OrderException
    */
   @Test
   public void testIsActive() throws ContractException, OrderException
   {
      Currency currency = Currency.createJpy();
      Exchange exchange = Exchange.createNasdaq();
      StockContract c = new StockContract("ABC", exchange, currency);
      MockOrder o2 = new MockOrder(55, c, "MYORDER", Direction.LONG, 800);
      
      assertTrue(o2.isActive());
      o2.setStatus(OrderStatus.CANCELED);
      assertFalse(o2.isActive());
      o2.setStatus(OrderStatus.REJECTED);
      assertFalse(o2.isActive());
      o2.setStatus(OrderStatus.FILLED);
      assertFalse(o2.isActive());
      o2.setStatus(OrderStatus.NEW);
      assertTrue(o2.isActive());
   }
   
   /**
    * Test the constructor.
    * 
    * @throws ContractException
    * @throws OrderException
    */
   @Test(expected = OrderException.class)
   public void testCreation1() throws ContractException, OrderException
   {
      new MockOrder(55, null, "MYORDER", Direction.SHORT, 800);
   }
   
   /**
    * Test the constructor.
    * 
    * @throws ContractException
    * @throws OrderException
    */
   @Test(expected = OrderException.class)
   public void testCreation2() throws ContractException, OrderException
   {
      Currency currency = Currency.createJpy();
      Exchange exchange = Exchange.createNasdaq();
      StockContract c = new StockContract("ABC", exchange, currency);
      new MockOrder(55, c, null, Direction.SHORT, 800);
   }
   
   /**
    * Test the constructor.
    * 
    * @throws ContractException
    * @throws OrderException
    */
   @Test(expected = OrderException.class)
   public void testCreation3() throws ContractException, OrderException
   {
      Currency currency = Currency.createJpy();
      Exchange exchange = Exchange.createNasdaq();
      StockContract c = new StockContract("ABC", exchange, currency);
      new MockOrder(55, c, "", Direction.SHORT, 800);
   }
   
   /**
    * Test the constructor.
    * 
    * @throws ContractException
    * @throws OrderException
    */
   @Test(expected = OrderException.class)
   public void testCreation4() throws ContractException, OrderException
   {
      Currency currency = Currency.createJpy();
      Exchange exchange = Exchange.createNasdaq();
      StockContract c = new StockContract("ABC", exchange, currency);
      new MockOrder(55, c, "MYORDER", null, 800);
   }
   
   /**
    * Test the constructor.
    * 
    * @throws ContractException
    * @throws OrderException
    */
   @Test(expected = OrderException.class)
   public void testCreation5() throws ContractException, OrderException
   {
      Currency currency = Currency.createJpy();
      Exchange exchange = Exchange.createNasdaq();
      StockContract c = new StockContract("ABC", exchange, currency);
      new MockOrder(55, c, "MYORDER", Direction.SHORT, 0);
   }
   
   /**
    * Test of toString method, of class Order.
    * 
    * @throws ContractException
    * @throws OrderException
    */
   @Test
   public void testGetOrderDirectionString() throws ContractException, OrderException
   {
      Currency currency = Currency.createJpy();
      Exchange exchange = Exchange.createNasdaq();
      StockContract c = new StockContract("ABC", exchange, currency);
      MockOrder o1 = new MockOrder(55, c, "MYORDER", Direction.LONG, 800);
      assertEquals("buy", o1.getOrderDirectionString());
      
      MockOrder o2 = new MockOrder(55, c, "MYORDER", Direction.SHORT, 800);
      assertEquals("sell", o2.getOrderDirectionString());
   }
   
   /**
    * Test of toString method, of class Order.
    * 
    * @throws ContractException
    * @throws OrderException
    */
   @Test
   public void testToString() throws ContractException, OrderException
   {
      Currency currency = Currency.createJpy();
      Exchange exchange = Exchange.createNasdaq();
      StockContract c = new StockContract("ABC", exchange, currency);
      MockOrder o = new MockOrder(55, c, "MYORDER", Direction.SHORT, 800);
      assertEquals("new MYORDER order to sell 800 of 'ABC'", o.toString());
   }
   
   /**
    * Test of getSubmitTime and setSubmitTime methods, of class Order.
    * 
    * @throws ContractException
    * @throws OrderException
    */
   @Test
   public void testGetSetSubmitTime() throws ContractException, OrderException
   {
      Currency currency = Currency.createJpy();
      Exchange exchange = Exchange.createNasdaq();
      StockContract c = new StockContract("ABC", exchange, currency);
      MockOrder o = new MockOrder(55, c, "MYORDER", Direction.SHORT, 800);
      assertNull(o.getSubmitTime());
      o.setSubmitTime(new DateTime(8));
      assertEquals(new DateTime(8), o.getSubmitTime());
   }
   
   /**
    * Test of getSubmitTime and setSubmitTime methods, of class Order.
    * 
    * @throws ContractException
    * @throws OrderException
    */
   @Test(expected=InvalidArgumentRuntimeException.class)
   public void testGetSetSubmitTime2() throws ContractException, OrderException
   {
      Currency currency = Currency.createJpy();
      Exchange exchange = Exchange.createNasdaq();
      StockContract c = new StockContract("ABC", exchange, currency);
      MockOrder o = new MockOrder(55, c, "MYORDER", Direction.SHORT, 800);
      o.setSubmitTime(null);
   }
   
   /**
    * Test of setStatus method, of class Order.
    * 
    * @throws ContractException
    * @throws OrderException
    */
   @Test
   public void testSetStatus() throws ContractException, OrderException
   {
      Currency currency = Currency.createJpy();
      Exchange exchange = Exchange.createNasdaq();
      StockContract c = new StockContract("ABC", exchange, currency);
      MockOrder o = new MockOrder(55, c, "MYORDER", Direction.SHORT, 800);
      assertEquals(OrderStatus.NEW, o.getStatus());
      o.setStatus(OrderStatus.CANCELLATION_PENDING);
      assertEquals(OrderStatus.CANCELLATION_PENDING, o.getStatus());
   }
   
   /**
    * Test of setStatus method, of class Order.
    * 
    * @throws ContractException
    * @throws OrderException
    */
   @Test(expected = InvalidArgumentRuntimeException.class)
   public void testSetStatus2() throws ContractException, OrderException
   {
      Currency currency = Currency.createJpy();
      Exchange exchange = Exchange.createNasdaq();
      StockContract c = new StockContract("ABC", exchange, currency);
      MockOrder o = new MockOrder(55, c, "MYORDER", Direction.SHORT, 800);
      o.setStatus(null);
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
      
      @Override
      public String getOrderDirectionString()
      {
         return super.getOrderDirectionString();
      }
   }
}
