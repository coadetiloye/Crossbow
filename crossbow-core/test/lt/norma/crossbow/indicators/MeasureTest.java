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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author Vilius Normantas <code@norma.lt>
 */
public class MeasureTest
{
   
   /**
    * Test method for
    * {@link lt.norma.crossbow.indicators.Measure#Measure(java.lang.String, boolean)}.
    */
   @Test
   public void testMeasure()
   {
      Measure<?> m = new MockMeasure("ttt", false);
      assertEquals("ttt", m.getTitle());
   }
   
   private class MockMeasure extends Measure<Double>
   {
      public MockMeasure(String title, boolean collectPeriodicData)
      {
         super(title, collectPeriodicData);
      }
   }
}
