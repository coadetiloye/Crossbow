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
   private Currency currency1;
   private Currency currency2;
   
   /**
    * Constructor.
    * 
    * @param currency1 code of the first currency
    * @param currency2 code of the second currency
    * @param exchange exchange of the contract
    * @throws ContractException throws an exception if invalid contract details are specified
    */
   public ForexContract(Currency currency1, Currency currency2, Exchange exchange)
         throws ContractException
   {
      super(getCurrencyCode(currency1, "The first currency of a forex contract is not set."),
            "FOREX", exchange, currency2);
      
      // Data control
      if (currency2 == null)
      {
         throw new ContractException("The second currency of a forex contract is not set.");
      }
      if (currency2.equals(currency1))
      {
         throw new ContractException("Currencies of a forex contract are equal.");
      }
      
      this.currency1 = currency1;
      this.currency2 = currency2;
   }
   
   private static String getCurrencyCode(Currency currency, String errorMessage)
         throws ContractException
   {
      if (currency == null)
      {
         throw new ContractException(errorMessage);
      }
      return currency.getCode();
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
   public int compareTo(Contract contract)
   {
      // The same contract
      if (this == contract)
      {
         return 0;
      }
      else
      {
         int k;
         // Compare types
         k = type.compareTo(contract.type);
         if (k != 0)
         {
            return k;
         }
         
         if (contract instanceof ForexContract)
         {
            ForexContract forexContract = (ForexContract) contract;
            
            // Compare the first currency
            k = currency1.compareTo(forexContract.currency1);
            if (k != 0)
            {
               return k;
            }
            // Compare the second currency
            k = currency2.compareTo(forexContract.currency2);
            if (k != 0)
            {
               return k;
            }
         }
         else
         {
            // Compare symbol
            k = symbol.compareTo(contract.symbol);
            if (k != 0)
            {
               return k;
            }
            // Compare the second currency
            k = currency.compareTo(contract.currency);
            if (k != 0)
            {
               return k;
            }
         }
         
         // Compare exchanges
         return exchange.compareTo(contract.exchange);
      }
   }
}
