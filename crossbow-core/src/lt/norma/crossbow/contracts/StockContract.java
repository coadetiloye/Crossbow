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
 * Stock contract.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public final class StockContract extends Contract
{
   /**
    * Constructor.
    * 
    * @param symbol
    *           symbol of an underlying asset
    * @param exchange
    *           exchange of the contract
    * @param currency
    *           base currency of the contract
    * @throws ContractException
    *            throws an exception if invalid contract details are specified
    */
   public StockContract(String symbol, Exchange exchange, Currency currency)
         throws ContractException
   {
      super(symbol, "STOCK", exchange, currency);
   }
   
   @Override
   protected boolean contractEquals(Contract contract)
   {
      // Stock contract has no fields to compare. All fields have already been compared by the
      // parent class.
      return true;
   }
   
   /**
    * Returns stock symbol.
    * 
    * @return stock symbol
    */
   @Override
   public String toString()
   {
      return symbol;
   }
}
