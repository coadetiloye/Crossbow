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

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.Test;

/**
 * @author Vilius Normantas <code@norma.lt>
 */
public class PeriodSplitterResultTest
{
   /**
    * Test method for
    * {@link lt.norma.crossbow.indicators.PeriodSplitterResult#PeriodSplitterResult(lt.norma.crossbow.indicators.PeriodSplitterAction, org.joda.time.DateTime)}
    * .
    */
   @Test
   public void testPeriodSplitterResult()
   {
      PeriodSplitterAction a = PeriodSplitterAction.END_BEFORE;
      DateTime t = new DateTime();
      
      PeriodSplitterResult r = new PeriodSplitterResult(a, t);
      assertEquals(a, r.getAction());
      assertEquals(t, r.getTime());
   }
}
