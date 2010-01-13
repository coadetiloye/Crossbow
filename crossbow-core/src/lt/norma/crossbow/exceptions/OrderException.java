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

package lt.norma.crossbow.exceptions;

import lt.norma.crossbow.orders.Order;

/**
 * Exception thrown if order creation or execution fails.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class OrderException extends Exception
{
   private final Order order;
   
   /**
    * Constructor.
    * 
    * @param order
    *           order data
    * @param message
    *           error message
    * @param cause
    *           cause of the exception
    */
   public OrderException(Order order, String message, Throwable cause)
   {
      super("Order '" + String.valueOf(order) + "' is invalid. " + message, cause);
      this.order = order;
   }
   
   /**
    * Constructor. No cause specified.
    * <p>
    * Do not use this constructor if this exception is caused by other exception.
    * 
    * @param order
    *           order data
    * @param message
    *           error message
    */
   public OrderException(Order order, String message)
   {
      this(order, message, null);
   }
   
   /**
    * Constructor. No cause or order specified.
    * <p>
    * Use this exception when the order that caused this exception is not yet fully initiated. For
    * example when the constructor of the order fails.
    * 
    * @param message
    *           error message
    */
   public OrderException(String message)
   {
      super("Invalid order. " + message);
      order = null;
   }
   
   /**
    * Gets the order that caused this exception.
    * 
    * @return order, may be null if no order is set by the constructor
    */
   public Order getOrder()
   {
      return order;
   }
}
