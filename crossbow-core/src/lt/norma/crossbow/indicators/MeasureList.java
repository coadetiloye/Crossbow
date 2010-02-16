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

import lt.norma.crossbow.trading.OrderExecutedEvent;
import lt.norma.crossbow.trading.OrderUpdatedEvent;
import lt.norma.crossbow.trading.TradeExecutorListener;

/**
 * Stores list of measures.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class MeasureList extends IndicatorList implements TradeExecutorListener
{
   private final Object lock;
   
   /**
    * Constructor.
    * 
    * @param periodSplitter
    *           used to check for end and beginning of data periods. If set to null, data periods
    *           will not be generated for any of indicators in this list.
    */
   public MeasureList(PeriodSplitter periodSplitter)
   {
      super(periodSplitter);
      lock = new Object();
   }
   
   @Override
   public final void orderExecuted(OrderExecutedEvent event)
   {
      synchronized (lock)
      {
         for (Indicator<?> indicator : indicators)
         {
            if (indicator instanceof Measure<?>)
            {
               ((Measure<?>)indicator).orderExecuted(event.getExecutionReport());
            }
         }
      }
   }
   
   @Override
   public final void orderUpdated(OrderUpdatedEvent event)
   {
      synchronized (lock)
      {
         for (Indicator<?> indicator : indicators)
         {
            if (indicator instanceof Measure<?>)
            {
               ((Measure<?>)indicator).orderUpdated(event.getOrder());
            }
         }
      }
   }
}
