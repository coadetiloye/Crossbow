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

import lt.norma.crossbow.exceptions.ContractException;

/**
 * Forex contract.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class ForexContract extends Contract
{
   private final Currency currency1;
   private final Currency currency2;
   
   // Error messages.
   private static final String FIRST_CURRENCY_ERROR =
         "The first currency of a forex contract is not set.";
   private static final String SECOND_CURRENCY_ERROR =
         "The second currency of a forex contract is not set.";
   private static final String EQUAL_CURRENCIES_ERROR =
         "Currencies of a forex contract are equal.";
   
   /**
    * Constructor.
    * 
    * @param currency1
    *           code of the first currency
    * @param currency2
    *           code of the second currency
    * @param exchange
    *           exchange of the contract
    * @throws ContractException
    *            throws an exception if invalid contract details are specified
    */
   public ForexContract(Currency currency1, Currency currency2, Exchange exchange)
         throws ContractException
   {
      super(checkCurrency(currency1, FIRST_CURRENCY_ERROR).getCode(),
            "FOREX", exchange,
            checkCurrency(currency2, SECOND_CURRENCY_ERROR));
      
      // Data control
      if (currency2.equals(currency1))
      {
         throw new ContractException(EQUAL_CURRENCIES_ERROR);
      }
      
      this.currency1 = currency1;
      this.currency2 = currency2;
   }
   
   private static Currency checkCurrency(Currency currency, String errorMessage)
         throws ContractException
   {
      if (currency == null)
      {
         throw new ContractException(errorMessage);
      }
      return currency;
   }
   
   /**
    * Gets the first currency.
    * 
    * @return first currency
    */
   public Currency getCurrency1()
   {
      return currency1;
   }
   
   /**
    * Gets the second currency.
    * 
    * @return second currency
    */
   public Currency getCurrency2()
   {
      return currency2;
   }
   
   /**
    * Returns Forex contract formated as "CurrencyCode1/CurrencyCode2" string.
    * 
    * @return Forex contract as string
    */
   @Override
   public String toString()
   {
      return currency1.getCode() + "/" + currency2.getCode();
   }
   
   @Override
   protected boolean contractEquals(Contract contract)
   {
      ForexContract forexContract = (ForexContract)contract;
      return currency1.equals(forexContract.getCurrency1());
      // Second currency is compared as currency field of Contract class.
   }
}
