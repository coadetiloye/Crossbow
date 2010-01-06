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

package lt.norma.crossbow.data;

import java.util.EventObject;

/**
 * Quote event. Used by data providers to send <code>Quote</code>s for multiple listeners.
 * 
 * @author Vilius Normantas <code@norma.lt>
 * @see Quote
 */
public class QuoteEvent extends EventObject
{
   private Quote quote;
   
   /**
    * Constructor.
    * 
    * @param source event sender
    * @param quote quote data
    */
   public QuoteEvent(Object source, Quote quote)
   {
      super(source);
      this.quote = quote;
   }
   
   /**
    * @return quote data
    */
   public Quote getQuote()
   {
      return quote;
   }
}
