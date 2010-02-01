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

import java.util.ArrayList;
import java.util.List;

import lt.norma.crossbow.data.*;
import lt.norma.crossbow.exceptions.ValueNotSetRuntimeException;
import lt.norma.crossbow.properties.Properties;

/**
 * Base class for all indicators. Extend this class to create custom indicators.
 * 
 * @param <Type>
 *           type of the indicators value
 * @author Vilius Normantas <code@norma.lt>
 */
public abstract class Indicator<Type>
{
   // Properties.
   private final String originalTitle;
   private String title;
   private String description;
   private final Properties parameters;
   private Object propertyLock;
   
   // Value.
   private Type value;
   private boolean isValueSet;
   private Object valueLock;
   
   // Periodic data.
   private final boolean collectPeriodicData;
   private final List<Type> periodicData;
   private final List<Boolean> periodicDataFlags;
   private Object periodicDataLock;
   
   /**
    * Constructor.
    * 
    * @param title
    *           short human readable title of this indicator
    * @param description
    *           brief description of this indicator
    * @param collectPeriodicData
    *           specifies if this indicator collects periodic data
    */
   public Indicator(String title, boolean collectPeriodicData)
   {
      this.originalTitle = String.valueOf(title);
      description = "";
      parameters = new Properties();
      propertyLock = new Object();
      
      isValueSet = false;
      valueLock = new Object();
      
      this.collectPeriodicData = collectPeriodicData;
      periodicData = new ArrayList<Type>();
      periodicDataFlags = new ArrayList<Boolean>();
      periodicDataLock = new Object();
   }
   
   /**
    * Called by data provider when trade data is received. Override this method to update
    * indicators value on new trade.
    * 
    * @param trade
    *           trade data
    */
   public void tradeReceived(Trade trade)
   {
   }
   
   /**
    * Called by data provider when quote data is received. Override this method to update
    * indicators value on new quote.
    * 
    * @param qoute
    *           quote data
    */
   public void quoteReceived(Quote qoute)
   {
   }
   
   /**
    * @return value of this indicator
    * @throws ValueNotSetRuntimeException
    *            if value of this indicator is not set yet
    * @see #isSet()
    */
   public final Type getValue()
   {
      synchronized (valueLock)
      {
         if (!isValueSet)
            throw new ValueNotSetRuntimeException("indicator " + getTitle());
         
         return value;
      }
   }
   
   /**
    * Sets value of this indicator.
    * 
    * @param value
    *           new value
    */
   public final void setValue(Type value)
   {
      synchronized (valueLock)
      {
         this.value = value;
         isValueSet = true;
      }
   }
   
   /**
    * @return true if value of the indicator is set, false otherwise
    */
   public final boolean isSet()
   {
      synchronized (valueLock)
      {
         return isValueSet;
      }
   }
   
   /**
    * Marks value of this indicator as not set. Further calls to <code>getValue()</code> will throw
    * an exception until value of this indicator is not set again by <code>setValue()</code>.
    */
   public final void unsetValue()
   {
      synchronized (valueLock)
      {
         isValueSet = false;
      }
   }
   
   /**
    * Updates periodic data at the end of period. Called by
    */
   public final void updateEndOfPeriod()
   {
      if (collectPeriodicData)
      {
         synchronized (valueLock)
         {
            synchronized (periodicDataLock)
            {
               if (isValueSet)
               {
                  periodicData.add(value);
                  periodicDataFlags.add(true);
               }
               else
               {
                  periodicData.add(null);
                  periodicDataFlags.add(false);
               }
            }
         }
      }
      
      endOfPeriod();
   }
   
   /**
    * Override this method to update indicators value on the end of period.
    * 
    * @param qoute
    *           quote data
    */
   protected void endOfPeriod()
   {
   }
   
   /**
    * @return true if periodic data is collected for this indicator
    */
   public final boolean hasPeriodicData()
   {
      return collectPeriodicData;
   }
   
   /**
    * @return periodic data of null if periodic data is not collected for this indicator
    */
   public final List<Type> getPeriodicData()
   {
      if (collectPeriodicData)
      {
         synchronized (periodicDataLock)
         {
            return periodicData;
         }
      }
      else
      {
         return null;
      }
   }
   
   /**
    * @return periodic data flags of null if periodic data is not collected for this indicator
    */
   public final List<Boolean> getPeriodicDataFlags()
   {
      if (collectPeriodicData)
      {
         synchronized (periodicDataLock)
         {
            return periodicDataFlags;
         }
      }
      else
      {
         return null;
      }
   }
   
   /**
    * @return parameters of this indicator
    */
   public final Properties getParameters()
   {
      return parameters;
   }
   
   /**
    * @return short human readable title of this indicator
    */
   public final String getTitle()
   {
      synchronized (propertyLock)
      {
         if (title == null || title.isEmpty())
         {
            return originalTitle;
         }
         else
         {
            return title;
         }
      }
   }
   
   /**
    * @return original title as set by the constructor of this indicator.
    */
   public final String getOriginalTitle()
   {
      return originalTitle;
   }
   
   /**
    * Sets title of this indicator. Title should include name of the indicator and the most
    * important parameters.
    * <p>
    * Examples: 'Volume', 'EMA (10)', 'Spread (ignoreCrossedBidAsk = true)', etc.
    * 
    * @param title
    *           new title
    */
   public final void setTitle(String title)
   {
      synchronized (propertyLock)
      {
         this.title = title;
      }
   }
   
   /**
    * @return brief description of this indicator
    */
   public final String getDescription()
   {
      synchronized (propertyLock)
      {
         return description;
      }
   }
   
   /**
    * Sets description.
    * 
    * @param description
    *           brief description of this indicator
    */
   public final void setDescription(String description)
   {
      synchronized (propertyLock)
      {
         this.description = String.valueOf(description);
      }
   }
}
