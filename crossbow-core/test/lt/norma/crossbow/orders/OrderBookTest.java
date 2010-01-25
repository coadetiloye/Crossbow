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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashMap;

import lt.norma.crossbow.account.Currency;
import lt.norma.crossbow.contracts.Exchange;
import lt.norma.crossbow.contracts.StockContract;
import lt.norma.crossbow.exceptions.ContractException;
import lt.norma.crossbow.exceptions.CrossbowException;
import lt.norma.crossbow.exceptions.OrderException;
import lt.norma.testutilities.Reflection;

import org.junit.Test;

/**
 * @author Vilius Normantas <code@norma.lt>
 */
public class OrderBookTest
{
   /**
    * Test method for {@link OrderBook#OrderBook()}.
    * 
    * @throws IllegalAccessException
    * @throws NoSuchFieldException
    * @throws IllegalArgumentException
    */
   @Test
   public void testOrderBook() throws IllegalArgumentException, NoSuchFieldException,
         IllegalAccessException
   {
      OrderBook b = new OrderBook();
      assertNotNull(Reflection.getField("orders", b));
   }
   
   /**
    * Test method for {@link OrderBook#addOrder(Order)}.
    * 
    * @throws OrderException
    * @throws CrossbowException
    * @throws IllegalAccessException
    * @throws NoSuchFieldException
    * @throws IllegalArgumentException
    */
   @SuppressWarnings("unchecked")
   @Test
   public void testAddOrder() throws OrderException, CrossbowException, IllegalArgumentException,
         NoSuchFieldException, IllegalAccessException
   {
      OrderBook b = new OrderBook();
      Order o1 = new MockOrder(8);
      Order o2 = new MockOrder(16);
      Order o3 = new MockOrder(2);
      
      b.addOrder(o1);
      b.addOrder(o2);
      b.addOrder(o3);
      
      assertEquals(3, ((HashMap<Long, Order>)Reflection.getField("orders", b)).size());
   }
   
   /**
    * Test method for {@link OrderBook#addOrder(Order)}.
    * 
    * @throws OrderException
    * @throws CrossbowException
    * @throws IllegalAccessException
    * @throws NoSuchFieldException
    * @throws IllegalArgumentException
    */
   @Test(expected = CrossbowException.class)
   public void testAddOrder2() throws OrderException, CrossbowException, IllegalArgumentException,
         NoSuchFieldException, IllegalAccessException
   {
      OrderBook b = new OrderBook();
      Order o1 = new MockOrder(1);
      Order o2 = new MockOrder(2);
      Order o3 = new MockOrder(1);
      
      b.addOrder(o1);
      b.addOrder(o2);
      b.addOrder(o3);
   }
   
   /**
    * Test method for {@link OrderBook#removeOrder(Order)}.
    * 
    * @throws OrderException
    * @throws CrossbowException
    * @throws IllegalAccessException
    * @throws NoSuchFieldException
    * @throws IllegalArgumentException
    */
   @SuppressWarnings("unchecked")
   @Test
   public void testRemoveOrder() throws OrderException, CrossbowException,
         IllegalArgumentException, NoSuchFieldException, IllegalAccessException
   {
      final OrderBook b = new OrderBook();
      final Order o1 = new MockOrder(8);
      Order o2 = new MockOrder(16);
      Order o3 = new MockOrder(2);
      
      b.addOrder(o1);
      b.addOrder(o2);
      b.addOrder(o3);
      
      b.removeOrder(o1);
      b.removeOrder(o3);
      
      assertEquals(1, ((HashMap<Long, Order>)Reflection.getField("orders", b)).size());
   }
   
   /**
    * Test method for {@link OrderBook#getOrderById(long)}.
    * 
    * @throws OrderException
    * @throws CrossbowException
    */
   @Test
   public void testGetOrderById() throws OrderException, CrossbowException
   {
      OrderBook b = new OrderBook();
      Order o1 = new MockOrder(8);
      Order o2 = new MockOrder(16);
      Order o3 = new MockOrder(2);
      
      b.addOrder(o1);
      b.addOrder(o2);
      b.addOrder(o3);
      
      assertEquals(o1, b.getOrderById(8));
      assertEquals(o2, b.getOrderById(16));
      assertEquals(o3, b.getOrderById(2));
   }
   
   /**
    * Test method for {@link OrderBook#removeInactive()}.
    * 
    * @throws OrderException
    * @throws CrossbowException
    * @throws IllegalAccessException
    * @throws NoSuchFieldException
    * @throws IllegalArgumentException
    */
   @SuppressWarnings("unchecked")
   @Test
   public void testRemoveInactive() throws OrderException, CrossbowException,
         IllegalArgumentException, NoSuchFieldException, IllegalAccessException
   {
      OrderBook b = new OrderBook();
      Order o1 = new MockOrder(1);
      Order o2 = new MockOrder(2);
      Order o3 = new MockOrder(3);
      Order o4 = new MockOrder(4);
      Order o5 = new MockOrder(5);
      
      b.addOrder(o1);
      b.addOrder(o2);
      b.addOrder(o3);
      b.addOrder(o4);
      b.addOrder(o5);
      
      o1.setStatus(OrderStatus.CANCELED); // Inactive
      o2.setStatus(OrderStatus.FILLED); // Inactive
      o3.setStatus(OrderStatus.REJECTED); // Inactive
      o4.setStatus(OrderStatus.NEW); // Active
      o5.setStatus(OrderStatus.SUBMITTED); // Active
      
      b.removeInactive();
      
      assertEquals(2, ((HashMap<Long, Order>)Reflection.getField("orders", b)).size());
      assertNull(b.getOrderById(1));
      assertNull(b.getOrderById(2));
      assertNull(b.getOrderById(3));
      assertNotNull(b.getOrderById(4));
      assertNotNull(b.getOrderById(5));
   }
   
   /**
    * Mock order.
    */
   private class MockOrder extends Order
   {
      public MockOrder(long id)
            throws OrderException, ContractException
      {
         super(id, new StockContract("AA", Exchange.createNasdaq(), Currency.createEur()), "MOCK",
               Direction.LONG, 5500);
      }
   }
}
