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

import lt.norma.crossbow.contracts.Contract;
import lt.norma.crossbow.exceptions.OrderException;

/**
 * Limit order.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public final class LimitOrder extends Order
{
   /** Limit price. */
   private final BigDecimal limitPrice;
   /** Order type. */
   private static final String ORDER_TYPE = "limit";
   
   /**
    * Constructor.
    * 
    * @param id
    *           local order ID. Make sure to set unique IDs for each order within context of the
    *           application.
    * @param contract
    *           contract of a traded instrument
    * @param direction
    *           direction of the trade
    * @param limitPrice
    *           limit price
    * @param size
    *           size of an order. Must be greater than zero. Use <code>direction</code> to set
    *           direction of the order
    * @throws OrderException
    *            on invalid order details
    */
   public LimitOrder(long id, Contract contract, Direction direction, int size,
         BigDecimal limitPrice) throws OrderException
   {
      super(id, contract, ORDER_TYPE, direction, size);
      this.limitPrice = limitPrice;
   }
   
   /**
    * Gets limit price.
    * 
    * @return limit price
    */
   public BigDecimal getLimitPrice()
   {
      return limitPrice;
   }
   
   @Override
   public String toString()
   {
      return super.toString() + " at " + limitPrice.toPlainString();
   }
}
