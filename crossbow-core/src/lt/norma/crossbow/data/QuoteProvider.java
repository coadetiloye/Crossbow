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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Base class for all quote data providers.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public abstract class QuoteProvider
{
   private final List<QuoteListener> listeners = new ArrayList<QuoteListener>();
   private final Object lock = new Object();
   
   /**
    * Adds quote listener.
    * 
    * @param listener
    *           quote listener
    */
   public final void addListener(QuoteListener listener)
   {
      synchronized (lock)
      {
         listeners.add(listener);
      }
   }
   
   /**
    * Removes quote listener.
    * 
    * @param listener
    *           quote listener
    */
   public final void removeListener(QuoteListener listener)
   {
      synchronized (lock)
      {
         listeners.remove(listener);
      }
   }
   
   /**
    * Fires quote event. Call this method from concrete quote providers.
    * 
    * @param quote
    *           quote data to be sent to all listeners
    */
   protected final void fireQuoteEvent(Quote quote)
   {
      QuoteEvent quoteEvent = new QuoteEvent(this, quote);
      
      synchronized (lock)
      {
         Iterator<QuoteListener> iterator = listeners.iterator();
         while (iterator.hasNext())
         {
            ((QuoteListener)iterator.next()).quoteReceived(quoteEvent);
         }
      }
   }
}
