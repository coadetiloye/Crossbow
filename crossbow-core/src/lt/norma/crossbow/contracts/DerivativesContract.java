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

import lt.norma.crossbow.configuration.StaticSettings;
import lt.norma.crossbow.exceptions.ContractException;

import org.joda.time.DateMidnight;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Abstract class for derivative contracts.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public abstract class DerivativesContract extends Contract
{
   /** Expiration date of a derivatives contract. */
   protected DateMidnight maturityDate;
   /**
    * Price multiplier. Contract`s quoted price must be multiplied by this value to get actual price
    * of one contract.
    */
   protected BigDecimal multiplier;
   /** Maturity date formatter. */
   protected DateTimeFormatter dateFormatter;
   /** Underlying contract. */
   protected Contract underlyingContract;
   
   /**
    * Constructor.
    * 
    * @param symbol
    *           symbol of an underlying asset
    * @param type
    *           human readable type of the contract, not used for comparison
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
   public DerivativesContract(String symbol, String type, DateMidnight maturityDate,
         Exchange exchange, Currency currency, BigDecimal multiplier)
         throws ContractException
   {
      super(symbol, type, exchange, currency);
      createDerivativesContract(symbol, maturityDate, exchange, multiplier, null);
   }
   
   /**
    * Constructor. Sets underlying contract.
    * 
    * @param symbol
    *           symbol of an underlying asset
    * @param type
    *           type of the contract
    * @param maturityDate
    *           expiration date of a derivatives contract
    * @param exchange
    *           exchange of the contract
    * @param currency
    *           base currency of the contract
    * @param multiplier
    *           price multiplier
    * @param underlyingContract
    *           underlying contract
    * @throws ContractException
    *            throws an exception if invalid contract details are specified
    */
   public DerivativesContract(String symbol, String type, DateMidnight maturityDate,
         Exchange exchange, Currency currency, BigDecimal multiplier,
         Contract underlyingContract) throws ContractException
   {
      super(symbol, type, exchange, currency);
      createDerivativesContract(symbol, maturityDate, exchange, multiplier, underlyingContract);
   }
   
   private void createDerivativesContract(String symbol, DateMidnight maturityDate,
         Exchange exchange, BigDecimal multiplier,
         Contract underlyingContract) throws ContractException
   {
      if (maturityDate == null)
      {
         throw new ContractException("Maturity date of the contract " + symbol + " is null.");
      }
      if (multiplier.compareTo(BigDecimal.ZERO) <= 0)
      {
         throw new ContractException("Invalid multiplier value (" + multiplier
                                     + ") of the contract " + symbol + " .");
      }
      
      this.maturityDate = maturityDate;
      this.multiplier = multiplier;
      this.underlyingContract = underlyingContract;
      dateFormatter =
            DateTimeFormat.forPattern(StaticSettings.dateFormat).withZone(exchange.getTimeZone());
   }
   
   /**
    * Calculates years to contracts maturity. Assumes an average year of 365.25 days, or 31557600000
    * milliseconds.
    * 
    * @param now
    *           current date
    * @return years to option's maturity
    * @throws ContractException
    *            throws an exception if the contract has expired
    */
   public double yearsToMaturity(DateMidnight now) throws ContractException
   {
      if (now.isAfter(maturityDate))
      {
         throw new ContractException(this, "Cannot calculate years to matyrity for "
                                           + this.toString()
                                           + " contract. The contract is expired.");
      }
      
      return (maturityDate.getMillis() - now.getMillis()) / 31557600000.0;
   }
   
   @Override
   protected final boolean contractEquals(Contract contract)
   {
      DerivativesContract derivative = (DerivativesContract) contract;
      return maturityDate.equals(derivative.maturityDate)
             && multiplier.compareTo(derivative.multiplier) == 0
             && (underlyingContract == null && derivative.underlyingContract == null
             || underlyingContract != null && derivative.underlyingContract != null
                && underlyingContract.equals(derivative.underlyingContract))
             && derivativeEquals(derivative);
   }
   
   /**
    * Checks if specified derivatives contract is equal to this contract. It is essential to
    * implement <code>derivativeEquals</code> method for proper comparison of custom
    * derivatives contract classes.
    * <p>
    * Fields of the base classes (<code>symbol</code>, <code>exchange</code> and
    * <code>currency</code> from <code>Contract</code> class and <code>maturityDate</code>,
    * <code>multiplier</code> and <code>underlyingContract</code> from
    * <code>DerivativesContract</code>) are compared before calling the abstract
    * <code>derivativeEquals</code>, therefore there is no need to compare these fields again in
    * child classes.
    * <p>
    * Field <code>type</code> is not used in comparison, contracts are compared by class instead.
    * Contracts with different classes cannot be equal. Child class can safely cast the specified
    * contract to it's own class.
    * 
    * @param derivative
    *           a derivatives contract to be compared to this contract
    * @return true if specified derivatives contract is equal to this contract, false otherwise
    * @see #equals(Contract)
    */
   protected abstract boolean derivativeEquals(DerivativesContract derivative);
   
   /**
    * Get maturity date.
    * 
    * @return expiration date of the contract
    */
   public DateMidnight getMaturityDate()
   {
      return maturityDate;
   }
   
   /**
    * Get multiplier.
    * 
    * @return multiplier
    */
   public BigDecimal getMultiplier()
   {
      return multiplier;
   }
   
   /**
    * Gets underlying contract.
    * 
    * @return underlying contract, may be null if no underlying contract is set
    */
   public Contract getUnderlyingContract()
   {
      return underlyingContract;
   }
}
