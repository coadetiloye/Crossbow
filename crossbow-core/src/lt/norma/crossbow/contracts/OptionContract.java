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

package lt.norma.crossbow.contracts;

import java.math.BigDecimal;

import lt.norma.crossbow.account.Currency;
import lt.norma.crossbow.exceptions.ContractException;

import org.joda.time.DateMidnight;

/**
 * Option contract.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public final class OptionContract extends DerivativesContract
{
   /** Type of an option. */
   protected final OptionType optionType;
   /** Strike price of the option. */
   protected final BigDecimal strike;
   
   /**
    * Constructor.
    * 
    * @param symbol
    *           symbol of an underlying asset
    * @param optionType
    *           type of an option
    * @param strike
    *           strike price of the option
    * @param maturityDate
    *           expiration date of a derivatives contract
    * @param exchange
    *           exchange of the contract
    * @param currency
    *           base currency of the contract
    * @param multiplier
    *           price multiplier
    * @throws ContractException
    *            throws an exception if invalid contract details are specified
    */
   public OptionContract(String symbol, OptionType optionType, BigDecimal strike,
         DateMidnight maturityDate, Exchange exchange, Currency currency,
         BigDecimal multiplier) throws ContractException
   {
      super(symbol, "OPTION", maturityDate, exchange, currency, multiplier);
      
      if (strike.compareTo(BigDecimal.ZERO) <= 0)
      {
         throw new ContractException("Invalid strike price (" + strike + ").");
      }
      
      this.optionType = optionType;
      this.strike = strike;
   }
   
   /**
    * Get option type.
    * 
    * @return option type
    */
   public OptionType getOptionType()
   {
      return optionType;
   }
   
   /**
    * Check if the option's type is call.
    * 
    * @return true, if the option`s type is call, false otherwise
    */
   public boolean isCall()
   {
      return optionType == OptionType.CALL;
   }
   
   /**
    * Check if the option's type is call.
    * 
    * @return true, if the option`s type is call, false otherwise
    */
   public boolean isPut()
   {
      return optionType == OptionType.PUT;
   }
   
   /**
    * Get strike price of the option.
    * 
    * @return strike price
    */
   public BigDecimal getStrike()
   {
      return strike;
   }
   
   @Override
   protected boolean derivativeEquals(DerivativesContract derivative)
   {
      OptionContract option = (OptionContract) derivative;
      return optionType.equals(option.optionType)
             && strike.compareTo(option.strike) == 0;
   }
   
   /**
    * Returns option contract formated as "Symbol OptionType MaturityDate@Strike" string.
    * 
    * @return option contract as string
    */
   @Override
   public String toString()
   {
      return symbol + " " + optionType + " " + maturityDate.toString(dateFormatter) + "@"
             + strike.toString();
   }
}
