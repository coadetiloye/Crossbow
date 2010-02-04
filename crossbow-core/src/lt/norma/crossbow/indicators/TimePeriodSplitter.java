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
   private final Object lock;
   private final DateTimeZone timeZone;
   
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
         
         PeriodSplitterAction result = PeriodSplitterAction.NO_ACTION;
         if (isFirstPeriod)
         {
            result = PeriodSplitterAction.START_BEFORE;
            previousPeriodTime = periodTime;
            isFirstPeriod = false;
         }
         else
         {
            if (periodTime != previousPeriodTime)
            {
               result = PeriodSplitterAction.RESTART_BEFORE;
               previousPeriodTime = periodTime;
            }
         }
         
         return new PeriodSplitterResult(result, new DateTime(periodTime, timeZone));
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
}
