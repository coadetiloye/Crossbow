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

package lt.norma.crossbow.data;

import java.math.BigDecimal;

import lt.norma.crossbow.configuration.StaticSettings;
import lt.norma.crossbow.contracts.Contract;
import lt.norma.crossbow.exceptions.InvalidArgumentRuntimeException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Trade data.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class Trade {
	/** Time of the quote. */
	private DateTime time;
	/** Contract specification. */
	private Contract contract;
	/** Price of the trade. */
	private BigDecimal price;
	/** Size of the trade. */
	private int size;
	/** Maturity date formatter. */
	private DateTimeFormatter timeFormatter;

	/**
	 * Constructor.
	 * 
	 * @param contract
	 *            contract specification
	 * @param price
	 *            price of the trade
	 * @param size
	 *            size of the trade
	 * @param time
	 *            time of the trade
	 */
	public Trade(Contract contract, BigDecimal price, int size, DateTime time) {
		if (contract == null) {
			throw new InvalidArgumentRuntimeException("contract", String
					.valueOf(contract));
		}
		if (price == null) {
			throw new InvalidArgumentRuntimeException("price", String
					.valueOf(price));
		}
		if (time == null) {
			throw new InvalidArgumentRuntimeException("time", String
					.valueOf(time));
		}

		this.contract = contract;
		this.price = price;
		this.size = size;
		this.time = time;

		timeFormatter = DateTimeFormat
				.forPattern(StaticSettings.dateTimeFormat).withZone(
						contract.getExchange().getTimeZone());
	}

	/**
	 * Returns trade data as text. Example:
	 * <p>
	 * Trade MSFT 800 @ 30.2 [2009-12-19 09:45:52]
	 * 
	 * @return trade data as text
	 */
	@Override
	public String toString() {
		return contract.toString() + "  " + size + " @ " + price + "  ["
				+ time.toString(timeFormatter) + "]";
	}

	/**
	 * Get contract.
	 * 
	 * @return contract
	 */
	public Contract getContract() {
		return contract;
	}

	/**
	 * Get trade price.
	 * 
	 * @return trade price
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * Get trade size.
	 * 
	 * @return trade size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Gets time of the quote.
	 * 
	 * @return time of the quote
	 */
	public DateTime getTime() {
		return time;
	}

}
