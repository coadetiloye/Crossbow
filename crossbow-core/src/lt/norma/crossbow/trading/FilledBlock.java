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

import java.math.BigDecimal;

import lt.norma.crossbow.exceptions.InvalidArgumentRuntimeException;
import lt.norma.crossbow.orders.Direction;

import org.joda.time.DateTime;

/**
 * A portion of the order that has been filled. Class is immutable.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public final class FilledBlock
{
   private final Direction direction;
   private final int size;
   private final BigDecimal price;
   private final DateTime time;
   
   /**
    * Constructor.
    * 
    * @param direction
    *           direction of a trade
    * @param size
    *           number of contracts filled. Must be greater that 0.
    * @param price
    *           average price at which the block was filled
    * @param time
    *           time when the block was filled
    */
   public FilledBlock(Direction direction, int size, BigDecimal price, DateTime time)
   {
      if (size <= 0)
      {
         throw new InvalidArgumentRuntimeException("size", String.valueOf(size),
               "Must be a positive integer.");
      }
      
      this.direction = direction;
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
    * @return direction of a trade
    */
   public Direction getDirection()
   {
      return direction;
   }
   
   /**
    * @return number of contracts filled
    */
   public int getSize()
   {
      return size;
   }
   
   /**
    * @return average price
    */
   public BigDecimal getAveragePrice()
   {
      return price;
   }
   
   /**
    * @return time when the block was filled
    */
   public DateTime getTime()
   {
      return time;
   }
}
