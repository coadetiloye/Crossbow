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

import lt.norma.crossbow.account.Currency;
import lt.norma.crossbow.exceptions.ContractException;

/**
 * Base class for all contracts. Extend this class to create custom contract types.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public abstract class Contract
{
   /** Contract symbol. */
   protected final String symbol;
   /**
    * Type of this contract. Used only to provide human readable type of the contract. Internally
    * contract types are identified by class.
    */
   protected final String type;
   /** Exchange, where the contract is listed. */
   protected final Exchange exchange;
   /** Contract`s base currency. */
   protected final Currency currency;
   
   /**
    * Constructor.
    * 
    * @param symbol
    *           contract symbol
    * @param type
    *           type of the contract. Used only to provide human readable type of the contract.
    *           Internally contract types are identified by class.
    * @param exchange
    *           exchange of the contract
    * @param currency
    *           base currency of the contract
    * @throws ContractException
    *            on invalid contract details are specified
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
      return symbol.hashCode() * 31 + getClass().hashCode();
   }
   
   /**
    * Checks if specified contract is equal to this contract.
    * <p>
    * <code>equals(Object)</code> method is final and cannot be overridden, however it uses abstract
    * <code>equals(Contract)</code> method to check if two custom contracts are equal. It is
    * essential to implement <code>equals(Contract)</code> method for proper comparison of custom
    * contract classes. Fields of the base class (<code>symbol</code>, <code>exchange</code> and
    * <code>currency</code>) are compared before calling the abstract <code>equals(Contract)</code>,
    * therefore there is no need to compare these fields again in child classes.
    * <p>
    * Field <code>type</code> is not used in comparison, contracts are compared by class instead.
    * Contracts with different classes cannot be equal.
    * 
    * @param object
    *           a contract to be compared to this contract
    * @return true if specified contract is equal to this contract, false otherwise
    * @see #equals(Contract)
    */
   @Override
   public final boolean equals(Object object)
   {
      if (object == null || !getClass().equals(object.getClass()))
      {
         return false;
      }
      if (object == this)
      {
         return true;
      }
      else
      {
         Contract contract = (Contract) object;
         return symbol.equals(contract.symbol)
                && exchange.equals(contract.exchange)
                && currency.equals(contract.currency)
                && contractEquals(contract);
      }
   }
   
   /**
    * Checks if specified contract is equal to this contract. It is essential to implement
    * <code>contractEquals</code> method for proper comparison of custom contract classes.
    * <p>
    * Fields of the base class (<code>symbol</code>, <code>exchange</code> and <code>currency</code>
    * ) are compared before calling the abstract <code>equals(Contract)</code>, therefore there is
    * no need to compare these fields again in child classes.
    * <p>
    * Field <code>type</code> is not used in comparison, contracts are compared by class instead.
    * Contracts with different classes cannot be equal. Child class can safely cast the specified
    * contract to it's own class.
    * 
    * @param contract
    *           a contract to be compared to this contract
    * @return true if specified contract is equal to this contract, false otherwise
    * @see #equals(Object)
    */
   protected abstract boolean contractEquals(Contract contract);
   
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
