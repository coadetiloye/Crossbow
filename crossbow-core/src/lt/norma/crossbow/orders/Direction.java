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

/**
 * Direction of trading action. Can have one of two values: BUY or SELL.
 * <p>
 * Use <code>toString</code> method to get a human readable string representation of the direction.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public enum Direction
{
   /**
    * Long direction. For orders it means "order to buy", for positions - positive amount of
    * contracts.
    */
   LONG("long"),
      /**
    * Short direction. For orders it means "order to sell", for positions - negative amount of
    * contracts.
    */
   SHORT("short");
   
   /** A human readable title. */
   private final String title;
   
   /**
    * Constructor.
    * 
    * @param title
    *           a human readable title. This title is returned by <code>toString</code> method.
    *           Actual field names, like <code>BUY</code> or <code>SELL</code> should not be
    *           displayed to the user but can be used for persistence and similar purposes.
    */
   private Direction(String title)
   {
      this.title = title;
   }
   
   /**
    * Gets a human readable string representation of the direction.
    * 
    * @return a human readable string representation of the direction
    */
   @Override
   public String toString()
   {
      return title;
   }
   
   /**
    * Gets a human readable string representation of the direction.
    * 
    * @return a human readable string representation of the direction
    */
   public String getTitle()
   {
      return title;
   }
}
