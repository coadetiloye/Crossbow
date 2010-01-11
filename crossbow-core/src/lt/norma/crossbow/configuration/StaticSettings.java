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

package lt.norma.crossbow.configuration;

import java.math.BigDecimal;

/**
 * Static project-wide settings.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public final class StaticSettings
{
   /**
    * Date format.
    */
   public static final String dateFormat = "yyyy-MM-dd";
   /**
    * Time format.
    */
   public static final String timeFormat = "HH:mm:ss";
   /**
    * Date-time format.
    */
   public static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
   /**
    * Precision of price calculations. Used for price calculations with <code>BigDecimal</code>
    * numbers.
    */
   public static final int pricePrecision = 15;
   /**
    * Rounding mode of price calculations. Used for price calculations with <code>BigDecimal</code>
    * numbers.
    */
   public static final int priceRoundingMode = BigDecimal.ROUND_HALF_UP;
   
   /**
    * A private constructor. This class cannot not be instantiated.
    */
   private StaticSettings()
   {
   }
}
