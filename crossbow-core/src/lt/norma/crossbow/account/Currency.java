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

package lt.norma.crossbow.account;

import java.math.BigDecimal;

import lt.norma.crossbow.core.StaticSettings;
import lt.norma.crossbow.exceptions.InvalidArgumentRuntimeException;

/**
 * Currency data.
 * <p>
 * Currency is not a type of contract.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public final class Currency implements Comparable<Currency>
{
   private final String code;
   private final String name;
   private int digits;
   private final Object lock;
   
   /**
    * Constructor.
    * 
    * @param code
    *           ISO 4217 currency code
    * @param name
    *           ISO 4217 currency name
    */
   public Currency(String code, String name)
   {
      if (code == null || code.length() < 1)
      {
         throw new InvalidArgumentRuntimeException("code", null);
      }
      
      this.code = code;
      this.name = name != null ? name : "";
      digits = 2;
      lock = new Object();
   }
   
   /**
    * Constructor. Name of the currency is set to an empty string.
    * 
    * @param code
    *           ISO 4217 currency code
    */
   public Currency(String code)
   {
      this(code, null);
   }
   
   /**
    * Set number of decimal places.
    * 
    * @param decimalPlaces
    *           number of decimal places
    */
   public void setDecimalPlaces(int decimalPlaces)
   {
      synchronized (lock)
      {
         digits = decimalPlaces;
      }
   }
   
   /**
    * Formats a number with specified decimal places.
    * 
    * @param number
    *           number to be formatted
    * @return formatted number
    */
   public String formatNumber(BigDecimal number)
   {
      synchronized (lock)
      {
         return number.setScale(
               digits, StaticSettings.priceMathContext.getRoundingMode()).toString();
      }
   }
   
   /**
    * Formats a number with specified decimal places. Add currency code.
    * 
    * @param number
    *           number to be formatted
    * @return formatted number
    */
   public String formatNumberWithCurrency(BigDecimal number)
   {
      synchronized (lock)
      {
         return number.setScale(
               digits, StaticSettings.priceMathContext.getRoundingMode()).toString() + " " + code;
      }
   }
   
   /**
    * Returns currency code.
    * 
    * @return currency code
    */
   @Override
   public String toString()
   {
      return code;
   }
   
   /**
    * Get currency code.
    * 
    * @return ISO 4217 currency code
    */
   public String getCode()
   {
      return code;
   }
   
   /**
    * Get full currency name.
    * 
    * @return ISO 4217 currency name
    */
   public String getName()
   {
      return name;
   }
   
   /**
    * Check if the other currency is equal to this instance. Two currencies are equal if they have
    * the save currency code.
    * 
    * @param object
    *           currency to be compared to this currency
    * @return true if codes of both currencies are the same, false otherwise
    */
   @Override
   public boolean equals(Object object)
   {
      if (this == object)
      {
         return true;
      }
      if (object == null || !getClass().equals(object.getClass()))
      {
         return false;
      }
      return this.code.equals(((Currency)object).code);
   }
   
   /**
    * Hash code of the currency.
    * 
    * @return hash code
    */
   @Override
   public int hashCode()
   {
      return code.hashCode();
   }
   
   /**
    * Alphabetically compares codes of this and other currency.
    * 
    * @param o
    *           currency to be compared to this currency
    * @return 0 if codes of both currencies are the same; a negative integer if the code of this
    *         currency is "less" than that of the other currency; a positive integer if the code of
    *         this currency is "greater";
    */
   @Override
   public int compareTo(Currency o)
   {
      return this.code.compareTo(o.code);
   }
   
   /**
    * Creates US dollar currency.
    * 
    * @return USD currency
    */
   public static Currency createUsd()
   {
      return new Currency("USD", "US dollar");
   }
   
   /**
    * Creates euro currency.
    * 
    * @return EUR currency
    */
   public static Currency createEur()
   {
      return new Currency("EUR", "euro");
   }
   
   /**
    * Creates Pound sterling currency.
    * 
    * @return GBP currency
    */
   public static Currency createGbp()
   {
      return new Currency("GBP", "Pound sterling");
   }
   
   /**
    * Creates Japanese yen currency.
    * 
    * @return JPY currency
    */
   public static Currency createJpy()
   {
      Currency currency = new Currency("JPY", "Japanese yen");
      currency.setDecimalPlaces(0);
      return currency;
   }
}
