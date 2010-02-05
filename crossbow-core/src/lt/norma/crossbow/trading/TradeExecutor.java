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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lt.norma.crossbow.contracts.Contract;
import lt.norma.crossbow.orders.Order;

/**
 * Interface for execution of trade. Trading systems use this interface to send trading instructions
 * and other communication with a trading destination (broker or trading simulator).
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public abstract class TradeExecutor
{
   private final List<TradeExecutorListener> listeners = new ArrayList<TradeExecutorListener>();
   private final Object lock = new Object();
   
   /**
    * Adds trade executor listener.
    * 
    * @param listener
    *           trade executor listener to be added
    */
   public void addListener(TradeExecutorListener listener)
   {
      synchronized (lock)
      {
         listeners.add(listener);
      }
   }
   
   /**
    * Removes trade executor listener.
    * 
    * @param listener
    *           trade executor listener to be removed
    */
   public void removeListener(TradeExecutorListener listener)
   {
      synchronized (lock)
      {
         listeners.remove(listener);
      }
   }
   
   /**
    * Fires order update event. Call this method from concrete trade executors.
    * 
    * @param order
    *           order to be updated
    */
   protected final void fireOrderUpdatedEvent(Order order)
   {
      OrderUpdatedEvent event = new OrderUpdatedEvent(this, order);
      
      synchronized (lock)
      {
         Iterator<TradeExecutorListener> iterator = listeners.iterator();
         while (iterator.hasNext())
         {
            ((TradeExecutorListener)iterator.next()).orderUpdated(event);
         }
      }
   }
   
   /**
    * Fires order execution event. Call this method from concrete trade executors.
    * 
    * @param report
    *           order execution report
    */
   protected final void fireOrderExecutedEvent(ExecutionReport report)
   {
      OrderExecutedEvent event = new OrderExecutedEvent(this, report);
      
      synchronized (lock)
      {
         Iterator<TradeExecutorListener> iterator = listeners.iterator();
         while (iterator.hasNext())
         {
            ((TradeExecutorListener)iterator.next()).orderExecuted(event);
         }
      }
   }
   
   /**
    * Checks if the trade executor is connected and ready for trading.
    * 
    * @return true if the trade executor is connected and ready, false otherwise
    */
   public abstract boolean isReady();
   
   /**
    * Sends specified order for execution.
    * 
    * @param order
    *           order to be sent
    */
   public abstract void sendOrder(Order order);
   
   /**
    * Cancel specified order.
    * 
    * @param order
    *           order to be canceled
    */
   public abstract void cancelOrder(Order order);
   
   /**
    * Cancel all orders from the order book.
    */
   public abstract void cancelAllOrders();
   
   /**
    * Cancel all orders for specified contract from the order book.
    * 
    * @param contract
    *           all orders for this contract will be canceled
    */
   public abstract void cancelAllOrdersByContract(Contract contract);
}
