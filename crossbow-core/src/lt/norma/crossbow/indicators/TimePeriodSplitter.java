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

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import lt.norma.crossbow.data.Quote;
import lt.norma.crossbow.data.Trade;

/**
 * Splits data into periods by time of trade or quote.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public final class TimePeriodSplitter implements PeriodSplitter
{
   private final long zeroDate;
   private final long periodLength;
   private long previousPeriodTime;
   private boolean isFirstPeriod;
   private final DateTimeZone timeZone;
   private PeriodSplitterResult lastResult;
   private final Object lock;
   
   /**
    * Constructor.
    * 
    * @param periodLength
    *           length of the time period in milliseconds
    * @param timeZone
    *           time zone of the data source (for example - exchange)
    */
   public TimePeriodSplitter(long periodLength, DateTimeZone timeZone)
   {
      this.periodLength = periodLength;
      this.timeZone = timeZone;
      zeroDate = (new DateTime(1900, 1, 1, 0, 0, 0, 0, timeZone)).getMillis();
      isFirstPeriod = true;
      lock = new Object();
   }
   
   private PeriodSplitterResult checkEndOfPeriod(DateTime time)
   {
      synchronized (lock)
      {
         long nper = (long)Math.floor((double)(time.getMillis() - zeroDate) / periodLength);
         long periodTime = zeroDate + nper * periodLength;
         
         PeriodSplitterAction action = PeriodSplitterAction.NO_ACTION;
         if (isFirstPeriod)
         {
            action = PeriodSplitterAction.START_BEFORE;
            previousPeriodTime = periodTime;
            isFirstPeriod = false;
         }
         else
         {
            if (periodTime != previousPeriodTime)
            {
               action = PeriodSplitterAction.RESTART_BEFORE;
               previousPeriodTime = periodTime;
            }
         }
         
         lastResult = new PeriodSplitterResult(action, new DateTime(periodTime, timeZone));
         return lastResult;
      }
   }
   
   @Override
   public PeriodSplitterResult checkEndOfPeriod(Quote quote)
   {
      return checkEndOfPeriod(quote.getTime());
   }
   
   @Override
   public PeriodSplitterResult checkEndOfPeriod(Trade trade)
   {
      return checkEndOfPeriod(trade.getTime());
   }
   
   @Override
   public PeriodSplitterResult getLastResult()
   {
      synchronized (lock)
      {
         return lastResult;
      }
   }
}
