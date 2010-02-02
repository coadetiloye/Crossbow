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
public final class IndicatorList implements TradeListener, QuoteListener
{
   private final List<Indicator<?>> indicators;
   private final Object lock;
   
   /**
    * Constructor.
    */
   public IndicatorList()
   {
      indicators = new ArrayList<Indicator<?>>();
      lock = new Object();
   }
   
   /**
    * @param indicator
    *           indicator to be added to this list
    */
   public void add(Indicator<?> indicator)
   {
      synchronized (lock)
      {
         indicators.add(indicator);
         Collections.sort(indicators, new IndicatorComparator());
      }
   }
   
   @Override
   public void tradeReceived(TradeEvent event)
   {
      synchronized (lock)
      {
         for (Indicator<?> indicator : indicators)
         {
            indicator.tradeReceived(event.getTrade());
         }
      }
   }
   
   @Override
   public void quoteReceived(QuoteEvent event)
   {
      synchronized (lock)
      {
         for (Indicator<?> indicator : indicators)
         {
            indicator.quoteReceived(event.getQuote());
         }
      }
   }
   
   /**
    * @return list of indicators
    */
   public List<Indicator<?>> getIndicators()
   {
      synchronized (lock)
      {
         return indicators;
      }
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

// TODO create PeriodSplitter
