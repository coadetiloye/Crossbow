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

package lt.norma.crossbow.data;

import java.math.BigDecimal;

import lt.norma.crossbow.configuration.StaticSettings;
import lt.norma.crossbow.contracts.Contract;
import lt.norma.crossbow.exceptions.InvalidArgumentRuntimeException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Quote data.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class Quote
{
   /** Time of the quote. */
   private final DateTime time;
   /** Contract specification. */
   private final Contract contract;
   /** Ask price. */
   private final BigDecimal askPrice;
   /** Ask size. */
   private final int askSize;
   /** Bid price. */
   private final BigDecimal bidPrice;
   /** Bid size. */
   private final int bidSize;
   /** Maturity date formatter. */
   private final DateTimeFormatter timeFormatter;
   
   /**
    * Constructor.
    * 
    * @param contract
    *           contract specification
    * @param askPrice
    *           ask price
    * @param askSize
    *           ask size
    * @param bidPrice
    *           bid price
    * @param bidSize
    *           bid size
    * @param time
    *           time of the quote
    */
   public Quote(Contract contract, BigDecimal askPrice, int askSize, BigDecimal bidPrice,
         int bidSize, DateTime time)
   {
      if (contract == null)
      {
         throw new InvalidArgumentRuntimeException("contract", String.valueOf(contract));
      }
      if (askPrice == null)
      {
         throw new InvalidArgumentRuntimeException("askPrice", String.valueOf(askPrice));
      }
      if (bidPrice == null)
      {
         throw new InvalidArgumentRuntimeException("bidPrice", String.valueOf(bidPrice));
      }
      if (time == null)
      {
         throw new InvalidArgumentRuntimeException("time", String.valueOf(time));
      }
      
      this.contract = contract;
      this.askPrice = askPrice;
      this.askSize = askSize;
      this.bidPrice = bidPrice;
      this.bidSize = bidSize;
      this.time = time;
      
      timeFormatter = DateTimeFormat.forPattern(StaticSettings.dateTimeFormat).withZone(
            contract.getExchange().getTimeZone());
   }
   
   /**
    * Calculates spread of the quote. Spread = AskPrice - BidPrice.
    * 
    * @return size of spread. Value may be negative in case of a crossed market.
    */
   public BigDecimal spread()
   {
      return askPrice.subtract(bidPrice);
   }
   
   /**
    * Checks if bid and ask prices are crossed {@literal (AskPrice < BidPrice)} .
    * 
    * @return true if ask price is less than bid price, false otherwise.
    */
   public boolean isCrossed()
   {
      return askPrice.compareTo(bidPrice) < 0;
   }
   
   /**
    * Returns quote data as text. Example:
    * <p>
    * Quote MSFT ask: 800 @ 30.2 bid: 1600 @ 30.18 [2009-12-19 09:45:52]
    * 
    * @return quote data as text
    */
   @Override
   public String toString()
   {
      return contract.toString() + "  ask: " + askSize + " @ "
             + contract.getCurrency().formatNumber(askPrice) + "  bid: " + bidSize + " @ "
             + contract.getCurrency().formatNumber(bidPrice) + "  [" + time.toString(timeFormatter)
             + "]";
   }
   
   /**
    * Get contract.
    * 
    * @return contract
    */
   public Contract getContract()
   {
      return contract;
   }
   
   /**
    * Get ask price.
    * 
    * @return ask price
    */
   public BigDecimal getAskPrice()
   {
      return askPrice;
   }
   
   /**
    * Get ask size.
    * 
    * @return ask size
    */
   public int getAskSize()
   {
      return askSize;
   }
   
   /**
    * Get bid price.
    * 
    * @return bid price
    */
   public BigDecimal getBidPrice()
   {
      return bidPrice;
   }
   
   /**
    * Get bid size.
    * 
    * @return bid size
    */
   public int getBidSize()
   {
      return bidSize;
   }
   
   /**
    * Gets time of the quote.
    * 
    * @return time of the quote
    */
   public DateTime getTime()
   {
      return time;
   }
}
