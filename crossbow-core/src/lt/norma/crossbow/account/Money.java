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
import lt.norma.crossbow.exceptions.CrossbowException;

/**
 * Amount of money with specified currency.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class Money implements Comparable<Money>
{
   private final Currency currency;
   private BigDecimal amount;
   private final Object lock;
   
   /**
    * Constructor.
    * 
    * @param amount
    *           amount of currency units
    * @param currency
    *           currency of this money instance. Only money instances with equal currency can be
    *           added or subtracted from this money instance.
    */
   public Money(BigDecimal amount, Currency currency)
   {
      this.amount = amount;
      this.currency = currency;
      lock = new Object();
   }
   
   /**
    * Constructor.
    * 
    * @param currency
    *           currency of this money instance. Only money instances with equal currency can be
    *           added or subtracted from this money instance.
    */
   public Money(Currency currency)
   {
      this(BigDecimal.ZERO, currency);
   }
   
   /**
    * Adds money to this instance.
    * 
    * @param money
    *           money to be added
    * @throws CrossbowException
    *            if currencies do not match
    */
   public void add(Money money) throws CrossbowException
   {
      if (!currency.equals(money.currency))
      {
         throw new CrossbowException("Cannot add money with different currencies.");
      }
      
      synchronized (lock)
      {
         amount = amount.add(money.amount);
      }
   }
   
   /**
    * Subtracts money from this instance.
    * 
    * @param money
    *           money to be subtracted
    * @throws CrossbowException
    *            if currencies do not match
    */
   public void subtract(Money money) throws CrossbowException
   {
      if (!currency.equals(money.currency))
      {
         throw new CrossbowException("Cannot subtract money with different currencies.");
      }
      
      synchronized (lock)
      {
         amount = amount.subtract(money.amount);
      }
   }
   
   /**
    * Multiplies this money instance by a factor.
    * 
    * @param factor
    *           multiplication factor
    */
   public void multiply(BigDecimal factor)
   {
      synchronized (lock)
      {
         amount = amount.multiply(factor);
      }
   }
   
   /**
    * Divides this money instance by a divisor.
    * 
    * @param divisor
    *           amount will be divided by this divisor
    */
   public void divide(BigDecimal divisor)
   {
      synchronized (lock)
      {
         amount = amount.divide(divisor, StaticSettings.priceMathContext);
      }
   }
   
   /**
    * @return this amount of money with specified currency.
    */
   @Override
   public String toString()
   {
      synchronized (lock)
      {
         return currency.formatNumberWithCurrency(amount);
      }
   }
   
   /**
    * @return amount of currency units
    */
   public BigDecimal getAmount()
   {
      synchronized (lock)
      {
         return amount;
      }
   }
   
   /**
    * @return currency of this money instance
    */
   public Currency getCurrency()
   {
      return currency;
   }
   
   /**
    * Check if the other instance of money is equal to this instance. Two instances are equal if
    * amounts and currencies are the same.
    * 
    * @param obj
    *           money instance to be compared to this instance
    * @return true if both instances are the same, false otherwise
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
      
      boolean result;
      synchronized (lock)
      {
         result = amount.compareTo(((Money)object).amount) == 0;
      }
      return result && currency.equals(((Money)object).currency);
   }
   
   /**
    * Hash code of the money instance.
    * 
    * @return hash code
    */
   @Override
   public int hashCode()
   {
      int hashCode;
      synchronized (lock)
      {
         hashCode = amount.stripTrailingZeros().hashCode();
      }
      return hashCode * 31 + currency.hashCode();
   }
   
   /**
    * Alphabetically compares currencies and then mathematically compares amounts.
    * 
    * @param money
    *           money instance to be compared to this instance
    * @return 0 if currencies and amounts of both instances are the same;
    *         a negative integer if the this instance is smaller than the other instance;
    *         a positive integer if this instance is greater;
    */
   @Override
   public int compareTo(Money money)
   {
      int k = currency.compareTo(money.currency);
      if (k != 0)
      {
         return k;
      }
      
      synchronized (lock)
      {
         return amount.compareTo(money.amount);
      }
   }
}
