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

import lt.norma.crossbow.exceptions.ContractException;

import org.joda.time.DateMidnight;

/**
 * Futures contract.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class FuturesContract extends DerivativesContract {
	/**
	 * Constructor.
	 * 
	 * @param symbol
	 *            symbol of an underlying asset
	 * @param maturityDate
	 *            expiration date of a derivatives contract
	 * @param exchange
	 *            exchange of the contract
	 * @param currency
	 *            base currency of the contract
	 * @param multiplier
	 *            price multiplier
	 * @throws ContractException
	 *             throws an exception if invalid contract details are specified
	 */
	public FuturesContract(String symbol, DateMidnight maturityDate,
			Exchange exchange, Currency currency, BigDecimal multiplier)
			throws ContractException {
		super(symbol, "FUTURES", maturityDate, exchange, currency, multiplier);
	}

	/**
	 * Returns futures contract formated as "Symbol MaturityDate" string.
	 * 
	 * @return futures contract as string
	 */
	@Override
	public String toString() {
		return symbol + " " + maturityDate.toString(dateFormatter);
	}

	@Override
	public int compareTo(Contract contract) {
		// The same contract
		if (this == contract) {
			return 0;
		} else {
			int k;
			// Compare types
			k = type.compareTo(contract.type);
			if (k != 0) {
				return k;
			}
			// Compare symbols
			k = symbol.compareTo(contract.symbol);
			if (k != 0) {
				return k;
			}
			// Compare formated maturity dates
			if (contract instanceof DerivativesContract) {
				DerivativesContract derivative = (DerivativesContract) contract;
				k = maturityDate.toString(dateFormatter).compareTo(
						derivative.maturityDate.toString(dateFormatter));
				if (k != 0) {
					return k;
				}
			}
			// Compare exchanges
			k = exchange.compareTo(contract.exchange);
			if (k != 0) {
				return k;
			}
			// Compare currencies
			k = currency.compareTo(contract.currency);
			if (k != 0) {
				return k;
			}
			// Compare multipliers
			if (contract instanceof DerivativesContract) {
				return multiplier
						.compareTo(((DerivativesContract) contract).multiplier);
			} else {
				// Contracts are equal
				return 0;
			}
		}
	}
}
