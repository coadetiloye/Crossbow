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

package lt.norma.crossbow.core;

import java.math.MathContext;
import java.math.RoundingMode;

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
    * Math context for BigDecimal price calculations. Precision is set to 15 decimal places and 
    * rounding mode to "half up".
    */
   public static final MathContext priceMathContext = new MathContext(15, RoundingMode.HALF_UP);

   /**
    * A private constructor. This class cannot not be instantiated.
    */
   private StaticSettings()
   {
   }
}
