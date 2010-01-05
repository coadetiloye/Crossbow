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
import lt.norma.crossbow.contracts.Currency;
import lt.norma.crossbow.contracts.Exchange;
import lt.norma.crossbow.contracts.StockContract;
import lt.norma.crossbow.exceptions.ContractException;
import lt.norma.crossbow.exceptions.OrderException;
import lt.norma.crossbow.orders.Order;
import lt.norma.crossbow.orders.OrderDirection;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test OrderExceptionTest class.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class OrderExceptionTest {
	Order o;
	Contract c;

	/**
	 * Constructor.
	 * 
	 * @throws ContractException
	 * @throws OrderException
	 */
	public OrderExceptionTest() throws ContractException, OrderException {
		c = new StockContract("MSFT", Exchange.createNyseExchange(), Currency
				.createEur());
		o = new DummyOrder(55, c, "MYORDER", OrderDirection.SELL, 800);
	}

	/**
	 * Test the constructor.
	 * 
	 * @throws OrderException
	 */
	@Test
	public void testCreation1() throws OrderException {
		Exception cause = new Exception("AAA");
		OrderException oe = new OrderException(o, "mmm.", cause);
		assertEquals(o, oe.getOrder());
		assertEquals(cause, oe.getCause());
		assertTrue(oe.getMessage().contains("mmm."));
		OrderException oe2 = new OrderException(null, "mmm.", cause);
		assertNull(oe2.getOrder());

		OrderException e3 = new OrderException(o, "mmm.", null);
		assertNull(e3.getCause());

		OrderException e4 = new OrderException(o, null, cause);
		assertTrue(e4.getMessage().contains("null"));
	}

	/**
	 * Test the constructor.
	 */
	@Test
	public void testCreation2() {
		OrderException e = new OrderException(o, "mmm.");
		assertTrue(e.getMessage().contains("mmm."));
		assertNull(e.getCause());
	}

	/**
	 * Test the constructor.
	 */
	@Test
	public void testCreation3() {
		OrderException e = new OrderException("mmm.");
		assertEquals("Invalid order. mmm.", e.getMessage());
		assertNull(e.getCause());
		assertNull(e.getOrder());
	}

	/**
	 * Test throwing.
	 * 
	 * @throws OrderException
	 */
	@Test(expected = OrderException.class)
	public void testThrowing() throws OrderException {
		Exception cause = new Exception("AAA");
		OrderException e = new OrderException(o, "mmm.", cause);
		throw e;
	}

	/**
	 * Dummy order.
	 */
	private class DummyOrder extends Order {
		public DummyOrder(long id, Contract contract, String type,
				OrderDirection direction, int size) throws OrderException {
			super(id, contract, type, direction, size);
		}
	}

}
