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

import lt.norma.crossbow.contracts.Currency;
import lt.norma.crossbow.contracts.Exchange;
import lt.norma.crossbow.contracts.ForexContract;
import lt.norma.crossbow.exceptions.ContractException;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test ForexContract class.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class ForexContractTest {
	/**
	 * Test constructor.
	 * 
	 * @throws ContractException
	 */
	@Test
	public void testCreation() throws ContractException {
		Currency currency1 = Currency.createEur();
		Currency currency2 = Currency.createJpy();
		Exchange exchange = Exchange.createNasdaqExchange();
		ForexContract c = new ForexContract(currency1, currency2, exchange);

		assertEquals(currency1, c.getCurrency1());
		assertEquals(new Currency("EUR"), c.getCurrency1());
		assertEquals(currency2, c.getCurrency2());
		assertEquals(new Currency("JPY"), c.getCurrency2());
		assertEquals("FOREX", c.type);
	}

	/**
	 * Test constructor.
	 * 
	 * @throws ContractException
	 */
	@Test(expected = ContractException.class)
	public void testCreation2() throws ContractException {
		Currency currency2 = Currency.createJpy();
		Exchange exchange = Exchange.createNasdaqExchange();
		ForexContract c = new ForexContract(null, currency2, exchange);
	}

	/**
	 * Test constructor.
	 * 
	 * @throws ContractException
	 */
	@Test(expected = ContractException.class)
	public void testCreation3() throws ContractException {
		Currency currency1 = Currency.createJpy();
		Exchange exchange = Exchange.createNasdaqExchange();
		ForexContract c = new ForexContract(currency1, null, exchange);
	}

	/**
	 * Test constructor.
	 * 
	 * @throws ContractException
	 */
	@Test(expected = ContractException.class)
	public void testCreation4() throws ContractException {
		Currency currency1 = Currency.createJpy();
		Exchange exchange = Exchange.createNasdaqExchange();
		ForexContract c = new ForexContract(currency1, currency1, exchange);
	}

	/**
	 * Test constructor.
	 * 
	 * @throws ContractException
	 */
	@Test(expected = ContractException.class)
	public void testCreation5() throws ContractException {
		Currency currency1 = Currency.createJpy();
		Exchange exchange = Exchange.createNasdaqExchange();
		ForexContract c = new ForexContract(currency1, new Currency("JPY"),
				exchange);
	}

	/**
	 * Test of toString method, of class ForexContract.
	 * 
	 * @throws ContractException
	 */
	@Test
	public void testToString() throws ContractException {
		Currency currency1 = Currency.createUsd();
		Currency currency2 = Currency.createGbp();
		Exchange exchange = Exchange.createNasdaqExchange();
		ForexContract c = new ForexContract(currency1, currency2, exchange);
		assertEquals("USD/GBP", c.toString());
	}

	/**
	 * Test of compareTo method, of class ForexContract.
	 * 
	 * @throws ContractException
	 */
	@Test
	public void testCompareTo() throws ContractException {
		Currency currency1 = Currency.createJpy();
		Currency currency2 = Currency.createGbp();
		Currency currency3 = Currency.createEur();
		Currency currency4 = Currency.createUsd();
		Exchange exchange = Exchange.createNasdaqExchange();
		ForexContract c1 = new ForexContract(currency1, currency2, exchange);
		ForexContract c2 = new ForexContract(currency1, currency2, exchange);
		ForexContract c3 = new ForexContract(currency3, currency2, exchange);
		ForexContract c4 = new ForexContract(currency1, currency4, exchange);

		assertEquals(c1, c1);
		assertEquals(c1, c2);
		assertEquals(0, c1.compareTo(c1));
		assertEquals(0, c1.compareTo(c2));
		assertTrue(c1.compareTo(c3) > 0);
		assertTrue(c1.compareTo(c4) < 0);
	}
}
