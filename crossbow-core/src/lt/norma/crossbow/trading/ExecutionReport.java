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

import lt.norma.crossbow.orders.Order;

/**
 * Order execution report. Sent by trade executor to the listener as the order gets completely or
 * partially filled.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class ExecutionReport
{
   /** Executed order. */
   private Order order;
   /** Portion of the order that has been filled. */
   private FilledBlock block;
   
   /**
    * Constructor.
    * 
    * @param order
    *           executed order
    * @param block
    *           portion of the order that has been filled
    */
   public ExecutionReport(Order order, FilledBlock block)
   {
      this.order = order;
      this.block = block;
   }
   
   /**
    * @return executed order
    */
   public Order getOrder()
   {
      return order;
   }
   
   /**
    * @return portion of the order that has been filled
    */
   public FilledBlock getFilledBlock()
   {
      return block;
   }
}
