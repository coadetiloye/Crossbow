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
 * Base class for all contracts. Extend this class to create custom contract types.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public abstract class Contract implements Comparable<Contract>
{
   /** Contract symbol. */
   protected String symbol;
   /**
    * Type of this contract. Used only to provide human readable type of the contract. Internally
    * contract types are identified by class.
    */
   protected final String type;
   /** Exchange, where the contract is listed. */
   protected Exchange exchange;
   /** Contract`s base currency. */
   protected Currency currency;
   
   /**
    * Constructor.
    * 
    * @param symbol contract symbol
    * @param type type of the contract. Used only to provide human readable type of the contract.
    *           Internally contract types are identified by class.
    * @param exchange exchange of the contract
    * @param currency base currency of the contract
    * @throws ContractException on invalid contract details are specified
    */
   public Contract(String symbol, String type, Exchange exchange, Currency currency)
         throws ContractException
   {
      // Data control
      if (symbol == null || symbol.isEmpty())
      {
         throw new ContractException("Symbol is empty.");
      }
      if (type == null || type.isEmpty())
      {
         throw new ContractException("Type of the contract " + symbol + " is not set.");
      }
      if (exchange == null)
      {
         throw new ContractException("Exchange of the contract " + symbol + " is null.");
      }
      if (currency == null)
      {
         throw new ContractException("Currency of the contract " + symbol + " is null.");
      }
      
      this.symbol = symbol;
      this.type = type;
      this.exchange = exchange;
      this.currency = currency;
   }
   
   /**
    * Converts contract to a text string.
    * 
    * @return a text string representing the contract
    */
   @Override
   public String toString()
   {
      return symbol + " (" + type + ")";
   }
   
   /**
    * Generates hash code from contract's symbol and type. Method is final.
    * 
    * @return hash code
    */
   @Override
   public final int hashCode()
   {
      return symbol.hashCode() * 31 + type.hashCode();
   }
   
   /**
    * Checks if a specified contract is equal to this contract.
    * <p>
    * <code>equals</code> method is final and cannot be overr idden, however it uses
    * <code>compareTo</code> to check if two contracts are equal (when types of both contracts match
    * and this contract is not compared to <code>null</code> or itself). Override
    * <code>compareTo</code> method to create custom comparison of contracts.
    * 
    * @param object contract
    * @return true if the specified contract is equal to this contract, false otherwise
    */
   @Override
   public final boolean equals(Object object)
   {
      if (object == null || !object.getClass().equals(this.getClass()))
      {
         return false;
      }
      Contract contract = (Contract) object;
      if (contract == this)
      {
         return true;
      }
      else
      {
         return this.compareTo(contract) == 0;
      }
   }
   
   /**
    * Compares a specified contract to this contract.
    * <p>
    * Override this method to implement comparison of custom contract types.
    * 
    * @param contract a contract to be compared to this contract
    * @return 0 if both contracts are the same;<br>
    *         a negative integer if this contract is "less" than the other contract;<br>
    *         a positive integer if this contract is "greater";
    */
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
         // Compare symbols
         k = symbol.compareTo(contract.symbol);
         if (k != 0)
         {
            return k;
         }
         // Compare exchanges
         k = exchange.getName().compareTo(contract.exchange.getName());
         if (k != 0)
         {
            return k;
         }
         // Compare currencies
         return currency.compareTo(contract.currency);
      }
   }
   
   /**
    * Get symbol.
    * 
    * @return symbol of an underlying asset
    */
   public String getSymbol()
   {
      return symbol;
   }
   
   /**
    * Gets type of this contract.
    * 
    * @return type of this contract
    */
   public String getType()
   {
      return type;
   }
   
   /**
    * Get exchange.
    * 
    * @return exchange
    */
   public Exchange getExchange()
   {
      return exchange;
   }
   
   /**
    * Get currency.
    * 
    * @return currency
    */
   public Currency getCurrency()
   {
      return currency;
   }
   
}
