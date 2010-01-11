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

package lt.norma.crossbow.exceptions;

import lt.norma.crossbow.contracts.Contract;

/**
 * Exception thrown if details of a contract are invalid.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class ContractException extends CrossbowException
{
   private Contract contract;
   
   /**
    * Constructor.
    * 
    * @param contract
    *           contract that raised an exception or null if the contract is not initiated at
    *           the time of exception
    * @param message
    *           exception message
    * @param cause
    *           cause of the exception
    */
   public ContractException(Contract contract, String message, Throwable cause)
   {
      super("Contract " + String.valueOf(contract) + " is invalid. " + message, cause);
      this.contract = contract;
   }
   
   /**
    * Constructor. No cause specified.
    * <p>
    * Do not use this constructor if this exception is caused by other exception.
    * 
    * @param contract
    *           contract that raised an exception or null if the contract is not initiated at
    *           the time of exception
    * @param message
    *           exception message
    */
   public ContractException(Contract contract, String message)
   {
      super("Contract " + String.valueOf(contract) + " is invalid. " + message);
      this.contract = contract;
   }
   
   /**
    * Constructor. No cause or contract specified.
    * <p>
    * Use this exception when the contract that caused this exception is not yet fully initiated.
    * For example when the constructor of the contract fails.
    * 
    * @param message
    *           exception message
    */
   public ContractException(String message)
   {
      super("Invalid contract. " + message);
   }
   
   /**
    * Gets the contract that caused this exception.
    * 
    * @return contract, may be null if no contract set by the constructor
    */
   public Contract getContract()
   {
      return contract;
   }
}
