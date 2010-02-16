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
 * Base class for all trade data providers.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public abstract class TradeProvider
{
   private final List<TradeListener> listeners = new ArrayList<TradeListener>();
   private final Object lock = new Object();
   
   /**
    * Adds trade listener.
    * 
    * @param listener
    *           trade listener
    */
   public final void addListener(TradeListener listener)
   {
      synchronized (lock)
      {
         listeners.add(listener);
      }
   }
   
   /**
    * Removes trade listener.
    * 
    * @param listener
    *           trade listener
    */
   public final void removeListener(TradeListener listener)
   {
      synchronized (lock)
      {
         listeners.remove(listener);
      }
   }
   
   /**
    * Fires trade event. Call this method from concrete trade providers.
    * 
    * @param trade
    *           trade data to be sent to all listeners
    */
   protected final void fireTradeEvent(Trade trade)
   {
      TradeEvent tradeEvent = new TradeEvent(this, trade);
      
      synchronized (lock)
      {
         Iterator<TradeListener> iterator = listeners.iterator();
         while (iterator.hasNext())
         {
            ((TradeListener)iterator.next()).tradeReceived(tradeEvent);
         }
      }
   }
}
