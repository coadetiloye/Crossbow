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

package lt.norma.crossbow.indicators;

import lt.norma.crossbow.orders.Order;
import lt.norma.crossbow.trading.ExecutionReport;

/**
 * Base class for all measures. Extend this class to create custom strategy performance measures.
 * 
 * @param <Type>
 *           type of the value
 * @author Vilius Normantas <code@norma.lt>
 */
public abstract class Measure<Type> extends Indicator<Type>
{
   /**
    * Constructor.
    * 
    * @param title
    *           short human readable title of this indicator
    * @param collectPeriodicData
    *           specifies if this indicator collects periodic data
    */
   public Measure(String title, boolean collectPeriodicData)
   {
      super(title, collectPeriodicData);
   }
   
   /**
    * Called as order gets partially or completely filled.
    * 
    * @param report
    *           execution details
    */
   public void orderExecuted(ExecutionReport report)
   {
   }
   
   /**
    * Called as order is sent or updated.
    * 
    * @param order
    *           order details
    */
   public void orderUpdated(Order order)
   {
   }
}
