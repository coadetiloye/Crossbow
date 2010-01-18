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

import lt.norma.crossbow.configuration.StaticSettings;
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
   public synchronized void add(Money money) throws CrossbowException
   {
      if (!currency.equals(money.currency))
      {
         throw new CrossbowException("Cannot add money with different currencies.");
      }
      amount = amount.add(money.amount);
   }
   
   /**
    * Subtracts money from this instance.
    * 
    * @param money
    *           money to be subtracted
    * @throws CrossbowException
    *            if currencies do not match
    */
   public synchronized void subtract(Money money) throws CrossbowException
   {
      if (!currency.equals(money.currency))
      {
         throw new CrossbowException("Cannot subtract money with different currencies.");
      }
      amount = amount.subtract(money.amount);
   }
   
   /**
    * Multiplies this money instance by a factor.
    * 
    * @param factor
    *           multiplication factor
    */
   public synchronized void multiply(BigDecimal factor)
   {
      amount = amount.multiply(factor);
   }
   
   /**
    * Divides this money instance by a divisor.
    * 
    * @param divisor
    *           amount will be divided by this divisor
    */
   public synchronized void divide(BigDecimal divisor)
   {
      amount = amount.divide(divisor, StaticSettings.pricePrecision,
            StaticSettings.priceRoundingMode);
   }
   
   /**
    * @return this amount of money with specified currency.
    */
   @Override
   public synchronized String toString()
   {
      return currency.formatNumberWithCurrency(amount);
   }
   
   /**
    * @return amount of currency units
    */
   public synchronized BigDecimal getAmount()
   {
      return amount;
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
   public synchronized boolean equals(Object obj)
   {
      if (this == obj)
      {
         return true;
      }
      if (obj == null || !this.getClass().equals(obj.getClass()))
      {
         return false;
      }
      return this.amount.compareTo(((Money)obj).amount) == 0 &&
             this.currency.equals(((Money)obj).currency);
   }
   
   /**
    * Hash code of the money instance.
    * 
    * @return hash code
    */
   @Override
   public synchronized int hashCode()
   {
      return amount.stripTrailingZeros().hashCode() * 31 + currency.hashCode();
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
   public synchronized int compareTo(Money money)
   {
      int k = currency.compareTo(money.currency);
      if (k != 0)
      {
         return k;
      }
      return amount.compareTo(money.amount);
   }
}
