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

package lt.norma.crossbow.orders;

import lt.norma.crossbow.contracts.Contract;
import lt.norma.crossbow.contracts.Currency;
import lt.norma.crossbow.contracts.Exchange;
import lt.norma.crossbow.contracts.StockContract;
import lt.norma.crossbow.exceptions.ContractException;
import lt.norma.crossbow.exceptions.OrderException;
import lt.norma.crossbow.orders.OcaGroup;
import lt.norma.crossbow.orders.Order;
import lt.norma.crossbow.orders.OrderDirection;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test OcaGroup class.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class OcaGroupTest {
	/**
	 * Test the constructor.
	 */
	@Test
	public void testCreation() {
		OcaGroup o = new OcaGroup("AAA");
		assertEquals("AAA", o.getTitle());
	}

	/**
	 * Test of addOrder method, of class OcaGroup.
	 * 
	 * @throws ContractException
	 * @throws OrderException
	 */
	@Test
	public void testAddOrder() throws ContractException, OrderException {
		Currency currency = Currency.createJpy();
		Exchange exchange = Exchange.createNasdaqExchange();
		StockContract c = new StockContract("ABC", exchange, currency);
		Order o1 = new DummyOrder(55, c, "MYORDER", OrderDirection.SELL, 800);
		Order o2 = new DummyOrder(55, c, "MYORDER", OrderDirection.SELL, 800);

		OcaGroup og = new OcaGroup("AAA");

		assertEquals(0, og.getOrders().size());
		og.addOrder(o1);
		og.addOrder(o2);
		assertEquals(2, og.getOrders().size());
		assertEquals(o1, og.getOrders().get(0));
		assertEquals("OCA group AAA (2)", og.toString());
		assertEquals("AAA", og.getTitle());
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
