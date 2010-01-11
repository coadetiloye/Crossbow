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

import java.math.BigDecimal;
import org.joda.time.DateTime;

/**
 * A portion of the order that has been filled. Class is immutable.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public final class FilledBlock
{
   /** Number of contracts filled. */
   private final int size;
   /** Average price at which the block was filled. */
   private final BigDecimal price;
   /** Time when the block was filled. */
   private final DateTime time;
   
   /**
    * Constructor.
    * 
    * @param size
    *           number of contracts filled
    * @param price
    *           average price at which the block was filled
    * @param time
    *           time when the block was filled
    */
   public FilledBlock(int size, BigDecimal price, DateTime time)
   {
      this.size = size;
      this.price = price;
      this.time = time;
   }
   
   /**
    * Calculates value of the block. Value = Size * Price
    * 
    * @return size multiplied by the price
    */
   public BigDecimal calculateValue()
   {
      return price.multiply(new BigDecimal(size));
   }
   
   /**
    * Gets number of contracts filled.
    * 
    * @return number of contracts filled
    */
   public int getSize()
   {
      return size;
   }
   
   /**
    * Gets average price at which the block was filled.
    * 
    * @return averge price
    */
   public BigDecimal getAveragePrice()
   {
      return price;
   }
   
   /**
    * Gets time when the block was filled.
    * 
    * @return time when the block was filled
    */
   public DateTime getTime()
   {
      return time;
   }
}
