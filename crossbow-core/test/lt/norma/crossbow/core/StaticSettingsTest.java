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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.RoundingMode;

import lt.norma.crossbow.core.StaticSettings;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

/**
 * @author Vilius Normantas <code@norma.lt>
 */
public class StaticSettingsTest
{
   /**
    * Test StaticSettings.dateFormat.
    */
   @Test
   public void testDateFormat()
   {
      DateTimeFormatter formatter = DateTimeFormat.forPattern(StaticSettings.dateFormat);
      LocalDate parsedDate = formatter.parseDateTime("2009-12-31").toLocalDate();
      LocalDate testDate = new LocalDate(2009, 12, 31);
      assertEquals(testDate, parsedDate);
   }
   
   /**
    * Test StaticSettings.timeFormat.
    */
   @Test
   public void testTimeFormat()
   {
      DateTimeFormatter formatter = DateTimeFormat.forPattern(StaticSettings.timeFormat);
      LocalTime parsedTime = formatter.parseDateTime("14:00:51").toLocalTime();
      LocalTime testTime = new LocalTime(14, 0, 51);
      assertEquals(testTime, parsedTime);
   }
   
   /**
    * Test StaticSettings.dateTimeFormat.
    */
   @Test
   public void testDateTimeFormat()
   {
      DateTimeFormatter formatter = DateTimeFormat.forPattern(StaticSettings.dateTimeFormat);
      LocalDateTime parsedDateTime =
            formatter.parseDateTime("2009-12-31 14:00:51").toLocalDateTime();
      LocalDateTime testDateTime = new LocalDateTime(2009, 12, 31, 14, 0, 51);
      assertEquals(testDateTime, parsedDateTime);
   }
   
   /**
    * Test StaticSettings.pricePrecision.
    */
   @Test
   public void testPricePrecision()
   {
      assertTrue(StaticSettings.priceMathContext.getPrecision() >= 0);
   }
   
   /**
    * Test StaticSettings.priceRoundingMode.
    */
   @Test
   public void testPriceRoundingMode()
   {
      assertTrue(StaticSettings.priceMathContext.getRoundingMode() == RoundingMode.CEILING
                 || StaticSettings.priceMathContext.getRoundingMode() == RoundingMode.DOWN
                 || StaticSettings.priceMathContext.getRoundingMode() == RoundingMode.UP
                 || StaticSettings.priceMathContext.getRoundingMode() == RoundingMode.FLOOR
                 || StaticSettings.priceMathContext.getRoundingMode() == RoundingMode.HALF_DOWN
                 || StaticSettings.priceMathContext.getRoundingMode() == RoundingMode.HALF_EVEN
                 || StaticSettings.priceMathContext.getRoundingMode() == RoundingMode.HALF_UP);
   }
}
