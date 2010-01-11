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

import org.joda.time.DateTime;

import lt.norma.crossbow.contracts.Contract;
import lt.norma.crossbow.exceptions.OrderException;
import lt.norma.crossbow.properties.Properties;

/**
 * Base class for all order types. Extend this class to create new order types. Use attributes to
 * attach additional instructions to the order.
 * <p>
 * Most fields are final. To modify an order, cancel it and resubmit a new order with modified
 * properties.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public abstract class Order
{
   /** Local order ID. */
   private final long id;
   /** Contract of a traded instrument. */
   protected final Contract contract;
   /**
    * Type of the order. Used only to provide human readable type of the order. Internally order
    * types are identified by class and attributes.
    */
   protected final String type;
   /** Order direction. */
   protected final OrderDirection direction;
   /** Size of an order. */
   protected final int size;
   /** Order status. */
   protected OrderStatus status;
   /** Sending time. Can be null if the order is not submited yet. */
   protected DateTime submitTime;
   /** Order comment. */
   private String comment;
   /**
    * Carries additional information about this order. Make sure the order executor knows how to
    * interpret the added attributes.
    */
   protected final Properties attributes;
   
   /**
    * Constructor.
    * 
    * @param id
    *           local order ID. Make sure to set unique IDs for each order within context of the
    *           application.
    * @param contract
    *           contract of a traded instrument
    * @param type
    *           type of an order
    * @param direction
    *           direction of the trade
    * @param size
    *           size of an order. Negative value means short trade, positive - long. Cannot be
    *           zero
    * @throws OrderException
    *            on invalid order details
    */
   public Order(long id, Contract contract, String type, OrderDirection direction, int size)
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
      if (direction == null || direction != OrderDirection.BUY && direction != OrderDirection.SELL)
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
      attributes = new Properties();
      comment = "";
   }
   
   /**
    * Direction of the order.
    * 
    * @return true if buy order, false if sell
    */
   public boolean isBuy()
   {
      return direction == OrderDirection.BUY;
   }
   
   /**
    * Direction of the order.
    * 
    * @return true if sell order, false if buy
    */
   public boolean isSell()
   {
      return direction == OrderDirection.SELL;
   }
   
   /**
    * Return order data as a text string.
    * 
    * @return order data as text
    */
   @Override
   public String toString()
   {
      return (status + " " + type + " order to " + direction + " " + size + " of '"
              + contract.toString() + "'");
   }
   
   /**
    * Gets order comment.
    * 
    * @return order comment
    */
   public String getComment()
   {
      return comment;
   }
   
   /**
    * Sets order comment.
    * 
    * @param comment
    *           order comment
    */
   public void setComment(String comment)
   {
      this.comment = comment;
   }
   
   /**
    * Get contract.
    * 
    * @return contract
    */
   public Contract getContract()
   {
      return contract;
   }
   
   /**
    * Get time when the order was submitted. Can be null if the order is not submited yet.
    * 
    * @return submit time
    */
   public DateTime getSubmitTime()
   {
      return submitTime;
   }
   
   /**
    * Set time when the order was submited.
    * 
    * @param time
    *           submit time
    */
   public void setSubmitTime(DateTime time)
   {
      this.submitTime = time;
   }
   
   /**
    * Get order size.
    * 
    * @return size of an order. Negative value means short trade, positive - long
    */
   public int getSize()
   {
      return size;
   }
   
   /**
    * Get order status.
    * 
    * @return order status
    */
   public OrderStatus getStatus()
   {
      return status;
   }
   
   /**
    * Set order status.
    * 
    * @param status
    *           order status
    */
   public void setStatus(OrderStatus status)
   {
      this.status = status;
   }
   
   /**
    * Gets type.
    * 
    * @return type of the order
    */
   public String getType()
   {
      return type;
   }
   
   /**
    * Gets orders attributes.
    * 
    * @return order attributes
    */
   public Properties getAttributes()
   {
      return attributes;
   }
   
   /**
    * Get broker ID.
    * 
    * @return ID assigned by a broker
    */
   public long getId()
   {
      return id;
   }
}
