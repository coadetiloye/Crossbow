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

/**
 * Action returned by a period splitter.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public enum PeriodSplitterAction
{
   /**
    * Beginning of period detected before current trade or quote.
    */
   START_BEFORE,
      /**
    * Beginning of period detected after current trade or quote.
    */
   START_AFTER,
      /**
    * End of period detected before current trade or quote.
    */
   END_BEFORE,
      /**
    * End of period detected after current trade or quote.
    */
   END_AFTER,
      /**
    * End the current period and beginning of the new one detected before current trade or quote.
    */
   RESTART_BEFORE,
      /**
    * End the current period and beginning of the new one detected after current trade or quote.
    */
   RESTART_AFTER,
      /**
    * The same period continues.
    */
   NO_ACTION
}
