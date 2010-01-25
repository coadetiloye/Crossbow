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

import java.util.HashMap;
import java.util.Iterator;

import lt.norma.crossbow.exceptions.CrossbowException;

/**
 * Stores collection of orders.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class OrderBook
{
   private final HashMap<Long, Order> orders;
   private final Object lock;
   
   /**
    * Constructor.
    */
   public OrderBook()
   {
      orders = new HashMap<Long, Order>();
      lock = new Object();
   }
   
   /**
    * Adds a new order to this order book. Throws a <code>CrossbowException</code> if an order with
    * the same ID already exists in this book.
    * <p>
    * Orders cannot be modified, instead they should be removed and and re-added.
    * 
    * @param order
    *           a new order to be added
    * @throws CrossbowException
    *            if an order with the same ID already exists in this book
    * @see #removeOrder(Order)
    */
   public void addOrder(Order order) throws CrossbowException
   {
      synchronized (lock)
      {
         if (orders.containsKey(order.getId()))
         {
            throw new CrossbowException("This order book allready contains order '" +
                                        order.toString() + "'.");
         }
         orders.put(order.getId(), order);
      }
   }
   
   /**
    * Removes order from the book.
    * 
    * @param order
    *           order to be removed
    */
   public void removeOrder(Order order)
   {
      synchronized (lock)
      {
         orders.remove(order.getId());
      }
   }
   
   /**
    * Returns order by specified order ID, or null if no order found.
    * 
    * @param id
    *           ID of an order to be obtained
    * @return order by specified order ID, or null if no order found
    */
   public Order getOrderById(long id)
   {
      synchronized (lock)
      {
         return orders.get(id);
      }
   }
   
   /**
    * Remove inactive orders from this book.
    * 
    * @see Order#isActive()
    */
   public void removeInactive()
   {
      synchronized (lock)
      {
         for (Iterator<Order> iterator = orders.values().iterator(); iterator.hasNext();)
         {
            if (!iterator.next().isActive())
            {
               iterator.remove();
            }
         }
      }
   }
}