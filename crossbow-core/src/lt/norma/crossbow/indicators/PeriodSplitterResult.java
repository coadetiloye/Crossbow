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

import org.joda.time.DateTime;

/**
 * @author Vilius Normantas <code@norma.lt>
 */
public final class PeriodSplitterResult
{
   private final PeriodSplitterAction action;
   private final DateTime time;
   
   /**
    * Constructor.
    * 
    * @param action
    *           period splitter action
    * @param time
    *           time of the period split. May or may not be equal to the time of trade or quote,
    *           depending on the splitter used.
    */
   public PeriodSplitterResult(PeriodSplitterAction action, DateTime time)
   {
      super();
      this.action = action;
      this.time = time;
   }
   
   /**
    * @return period splitter action
    */
   public PeriodSplitterAction getAction()
   {
      return action;
   }
   
   /**
    * @return time of the period split. May or may not be equal to the time of trade or quote,
    *         depending on the splitter used.
    */
   public DateTime getTime()
   {
      return time;
   }
}
