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

package lt.norma.crossbow.contracts;

import lt.norma.crossbow.exceptions.InvalidArgumentRuntimeException;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalTime;

/**
 * Information about an exchange.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class Exchange implements Comparable<Exchange>
{
   /** Exchange name. */
   private String name;
   /** Time zone of the exchange. */
   private DateTimeZone timeZone;
   /** Beginning of trading day. */
   private LocalTime tradingStarts;
   /** End of trading day. */
   private LocalTime tradingEnds;
   /** Does the exchange have closing hours. */
   private boolean doesClose;
   
   /**
    * Constructor.
    * 
    * @param name name of the exchange
    * @param tradingStarts beginning of trading day
    * @param tradingEnds end of trading day
    * @param timeZone time zone at the exchange
    */
   public Exchange(String name, DateTimeZone timeZone, LocalTime tradingStarts,
                   LocalTime tradingEnds)
   {
      // Data control
      if (name == null)
      {
         throw new InvalidArgumentRuntimeException("name", "null");
      }
      if (name == null || name.length() < 1)
      {
         throw new InvalidArgumentRuntimeException("name", "");
      }
      if (timeZone == null)
      {
         throw new InvalidArgumentRuntimeException("timeZone", "null");
      }
      if (tradingStarts == null)
      {
         throw new InvalidArgumentRuntimeException("tradingStarts", "null");
      }
      if (tradingEnds == null)
      {
         throw new InvalidArgumentRuntimeException("tradingEnds", "null");
      }
      
      this.name = name;
      this.timeZone = timeZone;
      this.tradingStarts = tradingStarts;
      this.tradingEnds = tradingEnds;
      doesClose = !tradingEnds.equals(new LocalTime(0, 0, 0));
   }
   
   /**
    * Creates an exchange with time zone set to "America/New_York" and trading hours 09:30 - 16:00.
    * 
    * @return Nasdaq exchange
    */
   public static Exchange createNasdaqExchange()
   {
      return new Exchange("Nasdaq", DateTimeZone.forID("America/New_York"),
                          new LocalTime(9, 30, 0), new LocalTime(16, 0, 0));
   }
   
   /**
    * Creates an exchange with time zone set to "America/New_York" and trading hours 09:30 - 16:00.
    * 
    * @return Nyse exchange
    */
   public static Exchange createNyseExchange()
   {
      return new Exchange("Nyse", DateTimeZone.forID("America/New_York"), new LocalTime(9, 30, 0),
                          new LocalTime(16, 0, 0));
   }
   
   /**
    * Is specified time inside trading hours of the exchange.
    * 
    * @param time time to be checked
    * @return true if the specified time equals to or is after trading start time, and is before
    *         trading end time.
    */
   public boolean isTradingHours(LocalTime time)
   {
      if (doesClose)
      {
         return (time.isEqual(tradingStarts) || time.isAfter(tradingStarts))
                && time.isBefore(tradingEnds);
      }
      else
      {
         return time.isEqual(tradingStarts) || time.isAfter(tradingStarts);
      }
   }
   
   /**
    * Returns name of the exchange.
    * 
    * @return name of the exchange
    */
   @Override
   public String toString()
   {
      return name;
   }
   
   /**
    * Checks if the exchange is equal to this exchange. Exchanges with the same name are equal.
    * 
    * @param object exchange to be compared to this exchange
    * @return true if the names of the exchanges are equal, false otherwise
    */
   @Override
   public boolean equals(Object object)
   {
      if (object == null || !object.getClass().equals(this.getClass()))
      {
         return false;
      }
      Exchange exchange = (Exchange) object;
      if (exchange == this)
      {
         return true;
      }
      else
      {
         return this.compareTo(exchange) == 0;
      }
   }
   
   /**
    * Alphabetically compares name of this exchange to the other exchange's name.
    * 
    * @param exchange exchange to be compared to this exchange
    * @return 0 if names of both exchanges are the same; a negative integer if name of this exchange
    *         is "less" than that of the other exchange; a positive integer if name of this exchange
    *         is "greater";
    */
   @Override
   public int compareTo(Exchange exchange)
   {
      return this.name.compareTo(exchange.name);
   }
   
   /**
    * Generates hash code from the name of the exchange.
    * 
    * @return hash code
    */
   @Override
   public int hashCode()
   {
      return name.hashCode();
   }
   
   /**
    * Get exchange name.
    * 
    * @return exchange name
    */
   public String getName()
   {
      return name;
   }
   
   /**
    * Get time zone at the exchange.
    * 
    * @return time zone
    */
   public DateTimeZone getTimeZone()
   {
      return timeZone;
   }
}
