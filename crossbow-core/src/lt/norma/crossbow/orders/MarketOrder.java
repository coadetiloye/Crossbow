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
import lt.norma.crossbow.exceptions.OrderException;

/**
 * Market order.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public final class MarketOrder extends Order
{
   /** Order type. */
   private static final String ORDER_TYPE = "market";
   
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
    * @param size
    *           size of an order. Negative value means short trade, positive - long. Cannot be
    *           zero
    * @throws OrderException
    *            on invalid order details
    */
   public MarketOrder(long id, Contract contract, OrderDirection direction, int size)
         throws OrderException
   {
      super(id, contract, ORDER_TYPE, direction, size);
   }
}
