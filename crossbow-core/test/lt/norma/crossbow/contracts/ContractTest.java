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

import lt.norma.crossbow.contracts.Contract;
import lt.norma.crossbow.contracts.Currency;
import lt.norma.crossbow.contracts.Exchange;
import lt.norma.crossbow.exceptions.ContractException;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test contract class.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class ContractTest {
	private Exchange exchange = Exchange.createNasdaqExchange();
	private Currency currency = Currency.createUsd();

	/**
	 * Test constructor.
	 * 
	 * @throws ContractException
	 *             contract is invalid
	 */
	@Test
	public void testConstructor() throws ContractException {
		Contract contract = new MockContract("AAA", "STOCK", exchange, currency);
		assertEquals("AAA", contract.getSymbol());
		assertEquals("STOCK", contract.getType());
		assertEquals(Exchange.createNasdaqExchange(), contract.getExchange());
	}

	/**
	 * Test constructor ContractException in constructor.
	 * 
	 * @throws ContractException
	 *             contract is invalid
	 */
	@Test(expected = ContractException.class)
	public void testConstructorException1() throws ContractException {
		Contract contract = new MockContract("", "STOCK", exchange, currency);
	}

	/**
	 * Test constructor ContractException in constructor.
	 * 
	 * @throws ContractException
	 *             contract is invalid
	 */
	@Test(expected = ContractException.class)
	public void testConstructorException2() throws ContractException {
		Contract contract = new MockContract(null, "STOCK", exchange, currency);
	}

	/**
	 * Test constructor ContractException in constructor.
	 * 
	 * @throws ContractException
	 *             contract is invalid
	 */
	@Test(expected = ContractException.class)
	public void testConstructorException3() throws ContractException {
		Contract contract = new MockContract("AAA", null, exchange, currency);
	}

	/**
	 * Test constructor ContractException in constructor.
	 * 
	 * @throws ContractException
	 *             contract is invalid
	 */
	@Test(expected = ContractException.class)
	public void testConstructorException4() throws ContractException {
		Contract contract = new MockContract("AAA", "FOREX", null, currency);
	}

	/**
	 * Test constructor ContractException in constructor.
	 * 
	 * @throws ContractException
	 *             contract is invalid
	 */
	@Test(expected = ContractException.class)
	public void testConstructorException5() throws ContractException {
		Contract contract = new MockContract("AAA", "FOREX", exchange, null);
	}

	/**
	 * Test of toString method, of class Contract.
	 * 
	 * @throws ContractException
	 *             contract is invalid
	 */
	@Test
	public void testToString() throws ContractException {
		Contract contract = new MockContract("AAA", "STOCK", exchange, currency);
		assertEquals("AAA (STOCK)", contract.toString());
	}

	/**
	 * Test of equals method, of class Contract. Other cases are tested in
	 * testCompareTo.
	 * 
	 * @throws ContractException
	 *             contract is invalid
	 */
	@Test
	public void testEquals() throws ContractException {
		Contract contract = new MockContract("AAA", "STOCK", exchange, currency);
		Contract contract2 = new MockContract("AAA", "STOCK", exchange,
				currency);
		Contract contract3 = new MockContract("AA", "STOCK", exchange, currency);
		Contract contract4 = new MockContract("AAA", "FOREX", exchange,
				currency);
		Contract contract5 = new MockContract("AAA", "STOCK", Exchange
				.createNyseExchange(), currency);

		assertEquals(contract, contract);
		assertEquals(contract.hashCode(), contract.hashCode());
		assertEquals(contract, contract2);
		assertEquals(contract.hashCode(), contract2.hashCode());
		assertFalse(contract.equals(contract3));
		assertFalse(contract.equals(contract4));
		assertFalse(contract.equals(contract5));
		assertFalse(contract.equals(new Object()));
		assertFalse(contract.equals(null));
	}

	/**
	 * Test of compareTo method, of class Contract.
	 * 
	 * @throws ContractException
	 *             contract is invalid
	 */
	@Test
	public void testCompareTo() throws ContractException {
		Contract contract = new MockContract("AAA", "STOCK", exchange, currency);
		Contract contract2 = new MockContract("AAA", "STOCK", exchange,
				currency);
		Contract contract3 = new MockContract("AAA", "FUTURES", exchange,
				currency);
		Contract contract4 = new MockContract("BBB", "STOCK", exchange,
				currency);

		assertEquals(0, contract.compareTo(contract));
		assertEquals(0, contract.compareTo(contract2));
		assertTrue(contract.compareTo(contract3) > 0);
		assertTrue(contract.compareTo(contract4) < 0);
	}

	/**
	 * Mock contract class.
	 */
	private class MockContract extends Contract {
		public MockContract(String symbol, String type, Exchange exchange,
				Currency currency) throws ContractException {
			super(symbol, type, exchange, currency);
		}
	}

}
