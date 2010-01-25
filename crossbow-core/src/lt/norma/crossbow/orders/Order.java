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

import lt.norma.crossbow.contracts.Contract;
import lt.norma.crossbow.exceptions.InvalidArgumentRuntimeException;
import lt.norma.crossbow.exceptions.OrderException;

import org.joda.time.DateTime;

/**
 * Base class for all order types. Extend this class to create new order types. Use attributes to
 * attach additional instructions to the order.
 * <p>
 * Most fields are final. To modify an order, cancel or remove it and create a new order with
 * modified properties.
 * <p>
 * Additional attributes can be added to the order for additional instructions. Use
 * <code>getAttributes</code> method to get list of attributes.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public abstract class Order
{
   private final long id;
   private final Object lock;
   
   /** Contract of a traded instrument. */
   protected final Contract contract;
   /**
    * type of this order. Used only to provide human readable type of the order. Internally order
    * types are identified by class and attributes.
    */
   protected final String type;
   /** Order direction. */
   protected final Direction direction;
   /**
    * Size of an order. Must be greater than zero. Use <code>direction</code> to set direction of
    * the order.
    */
   protected final int size;
   /** Order status. */
   protected OrderStatus status;
   /** Sending time. Can be null if the order is not submitted yet. */
   protected DateTime submitTime;
   /**
    * Carries additional information about this order. Make sure the order executor knows how to
    * interpret attributes added to the order.
    */
   protected final OrderAttributes attributes;
   
   /**
    * Constructor.
    * 
    * @param id
    *           local order ID. Make sure to set unique IDs for each order within context of the
    *           application.
    * @param contract
    *           contract of a traded instrument
    * @param type
    *           type of the order. Used only to provide human readable type of the order. Internally
    *           order types are identified by class and attributes.
    * @param direction
    *           direction of the trade
    * @param size
    *           size of an order. Must be greater than zero. Use <code>direction</code> to set
    *           direction of the order.
    * @throws OrderException
    *            on invalid order details
    */
   public Order(long id, Contract contract, String type, Direction direction, int size)
         throws OrderException
   {
      // Data control.
      if (contract == null)
      {
         throw new OrderException("Contract is not set.");
      }
      if (type == null || type.isEmpty())
      {
         throw new OrderException("Type of the order for contract '" + contract.toString()
                                  + "' is not set.");
      }
      if (direction == null || direction != Direction.LONG && direction != Direction.SHORT)
      {
         throw new OrderException("Order for contract '" + contract.toString()
                                  + "' has invalid direction '" + String.valueOf(direction) + "'.");
      }
      if (size <= 0)
      {
         throw new OrderException("Order for contract '" + contract.toString()
                                  + "' has invalid size '" + size + "'.");
      }
      
      this.id = id;
      this.contract = contract;
      this.type = type;
      this.direction = direction;
      this.size = size;
      status = OrderStatus.NEW;
      attributes = new OrderAttributes();
      lock = new Object();
   }
   
   /**
    * Direction of the order.
    * 
    * @return true if buy order, false if sell
    */
   public boolean isBuy()
   {
      return direction == Direction.LONG;
   }
   
   /**
    * Direction of the order.
    * 
    * @return true if sell order, false if buy
    */
   public boolean isSell()
   {
      return direction == Direction.SHORT;
   }
   
   /**
    * Checks if this order is active. Orders are active, unless they are canceled, filled or
    * rejected.
    * 
    * @return false if this order is canceled, filled or rejected; true otherwise
    * @see #getStatus()
    * @see OrderStatus
    */
   public boolean isActive()
   {
      synchronized (lock)
      {
         return status != OrderStatus.CANCELED && 
                status != OrderStatus.FILLED &&
                status != OrderStatus.REJECTED;
      }
   }
   
   /**
    * @return order data as a text string
    */
   @Override
   public String toString()
   {
      synchronized (lock)
      {
         return (status + " " + type + " order to " + getOrderDirectionString() + " " + size
                 + " of '" + contract.toString() + "'");
      }
   }
   
   /**
    * @return "buy" for <code>LONG</code> direction; "sell" for <code>SHORT</code> direction;
    * @see Direction
    */
   protected String getOrderDirectionString()
   {
      return direction == Direction.LONG ? "buy" : "sell";
   }
   
   /**
    * @return contract of the order
    */
   public Contract getContract()
   {
      return contract;
   }
   
   /**
    * @return time when the order was submitted. Can be null, if the order is not submitted yet.
    */
   public DateTime getSubmitTime()
   {
      synchronized (lock)
      {
         return submitTime;
      }
   }
   
   /**
    * Set time when the order was submitted.
    * 
    * @param time
    *           submit time
    */
   public void setSubmitTime(DateTime time)
   {
      if (time == null)
      {
         throw new InvalidArgumentRuntimeException("time", "null",
               "Order submit time cannot be null.");
      }
      
      synchronized (lock)
      {
         this.submitTime = time;
      }
   }
   
   /**
    * @return size of an order
    */
   public int getSize()
   {
      return size;
   }
   
   /**
    * @return order status
    * @see #isActive()
    * @see OrderStatus
    */
   public OrderStatus getStatus()
   {
      synchronized (lock)
      {
         return status;
      }
   }
   
   /**
    * Sets order status.
    * 
    * @param status
    *           order status
    * @see OrderStatus
    */
   public void setStatus(OrderStatus status)
   {
      if (status == null)
      {
         throw new InvalidArgumentRuntimeException("status", "null",
               "Order status cannot be null.");
      }
      
      synchronized (lock)
      {
         this.status = status;
      }
   }
   
   /**
    * @return type of this order. Used only to provide human readable type of the order. Internally
    *         order types are identified by class and attributes.
    */
   public String getType()
   {
      return type;
   }
   
   /**
    * @return order attributes
    */
   public OrderAttributes getAttributes()
   {
      return attributes;
   }
   
   /**
    * @return order identifier
    */
   public long getId()
   {
      return id;
   }
}
