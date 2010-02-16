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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import lt.norma.crossbow.data.QuoteEvent;
import lt.norma.crossbow.data.QuoteListener;
import lt.norma.crossbow.data.TradeEvent;
import lt.norma.crossbow.data.TradeListener;

/**
 * Stores list of indicators.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class IndicatorList implements TradeListener, QuoteListener
{
   /**
    * List of indicators.
    */
   protected final List<Indicator<?>> indicators;
   private final Object lock;
   private final PeriodSplitter periodSplitter;
   
   /**
    * Constructor.
    * 
    * @param periodSplitter
    *           used to check for end and beginning of data periods. If set to null, data periods
    *           will not be generated for any of indicators in this list.
    */
   public IndicatorList(PeriodSplitter periodSplitter)
   {
      this.periodSplitter = periodSplitter;
      indicators = new ArrayList<Indicator<?>>();
      lock = new Object();
   }
   
   /**
    * @param indicator
    *           indicator to be added to this list
    */
   public final void add(Indicator<?> indicator)
   {
      synchronized (lock)
      {
         indicators.add(indicator);
         Collections.sort(indicators, new IndicatorComparator());
      }
   }
   
   @Override
   public final void tradeReceived(TradeEvent event)
   {
      synchronized (lock)
      {
         // Update period actions, if they occur before the trade.
         if (periodSplitter != null)
         {
            PeriodSplitterResult result = periodSplitter.checkEndOfPeriod(event.getTrade());
            for (Indicator<?> indicator : indicators)
            {
               switch (result.getAction())
               {
                  case START_BEFORE:
                     indicator.beginningOfPeriod(result.getTime());
                     break;
                  case END_BEFORE:
                     indicator.endOfPeriod(result.getTime());
                     break;
                  case RESTART_BEFORE:
                     indicator.endOfPeriod(result.getTime());
                     indicator.beginningOfPeriod(result.getTime());
                     break;
               }
            }
         }
         
         // Send trade.
         for (Indicator<?> indicator : indicators)
         {
            indicator.tradeReceived(event.getTrade());
         }
         
         // Update period actions, if they occur after the trade.
         if (periodSplitter != null)
         {
            PeriodSplitterResult result = periodSplitter.checkEndOfPeriod(event.getTrade());
            for (Indicator<?> indicator : indicators)
            {
               switch (result.getAction())
               {
                  case START_AFTER:
                     indicator.beginningOfPeriod(result.getTime());
                     break;
                  case END_AFTER:
                     indicator.endOfPeriod(result.getTime());
                     break;
                  case RESTART_AFTER:
                     indicator.endOfPeriod(result.getTime());
                     indicator.beginningOfPeriod(result.getTime());
                     break;
               }
            }
         }
      }
   }
   
   @Override
   public final void quoteReceived(QuoteEvent event)
   {
      synchronized (lock)
      {
         // Update period actions, if they occur before the quote.
         if (periodSplitter != null)
         {
            PeriodSplitterResult result = periodSplitter.checkEndOfPeriod(event.getQuote());
            for (Indicator<?> indicator : indicators)
            {
               switch (result.getAction())
               {
                  case START_BEFORE:
                     indicator.beginningOfPeriod(result.getTime());
                     break;
                  case END_BEFORE:
                     indicator.endOfPeriod(result.getTime());
                     break;
                  case RESTART_BEFORE:
                     indicator.endOfPeriod(result.getTime());
                     indicator.beginningOfPeriod(result.getTime());
                     break;
               }
            }
         }
         
         // Send quote.
         for (Indicator<?> indicator : indicators)
         {
            indicator.quoteReceived(event.getQuote());
         }
         
         // Update period actions, if they occur after the trade.
         if (periodSplitter != null)
         {
            PeriodSplitterResult result = periodSplitter.checkEndOfPeriod(event.getQuote());
            for (Indicator<?> indicator : indicators)
            {
               switch (result.getAction())
               {
                  case START_AFTER:
                     indicator.beginningOfPeriod(result.getTime());
                     break;
                  case END_AFTER:
                     indicator.endOfPeriod(result.getTime());
                     break;
                  case RESTART_AFTER:
                     indicator.endOfPeriod(result.getTime());
                     indicator.beginningOfPeriod(result.getTime());
                     break;
               }
            }
         }
      }
   }
   
   /**
    * @return list of indicators
    */
   public final List<Indicator<?>> getIndicators()
   {
      synchronized (lock)
      {
         return indicators;
      }
   }
   
   /**
    * @return period splitter used by this list of indicators
    */
   public final PeriodSplitter getPeriodSplitter()
   {
      return periodSplitter;
   }
   
   /**
    * Compares two indicators by total number of dependencies.
    * 
    * @author Vilius Normantas <code@norma.lt>
    */
   private class IndicatorComparator implements Comparator<Indicator<?>>
   {
      @Override
      public int compare(Indicator<?> indicator1, Indicator<?> indicator2)
      {
         return countTotalDependencies(indicator1) - countTotalDependencies(indicator2);
      }
      
      /**
       * @param indicator
       *           indicator
       * @return total number of dependencies of the specified indicator
       */
      private int countTotalDependencies(Indicator<?> indicator)
      {
         int count = 0;
         
         // Recursively calculate number of sub-dependencies.
         for (Indicator<?> subindicator : indicator.getDependencies())
         {
            count += countTotalDependencies(subindicator);
         }
         
         // Add number of dependencies of the specified indicator.
         count += indicator.getDependencies().size();
         
         return count;
      }
   }
}
