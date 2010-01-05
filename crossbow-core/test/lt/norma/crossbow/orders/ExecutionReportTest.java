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

import java.math.BigDecimal;
import java.math.RoundingMode;

import lt.norma.crossbow.configuration.StaticSettings;
import lt.norma.crossbow.contracts.Contract;
import lt.norma.crossbow.contracts.Currency;
import lt.norma.crossbow.contracts.Exchange;
import lt.norma.crossbow.contracts.StockContract;
import lt.norma.crossbow.exceptions.ContractException;
import lt.norma.crossbow.exceptions.OrderException;
import lt.norma.crossbow.orders.ExecutionReport;
import lt.norma.crossbow.orders.FilledBlock;
import lt.norma.crossbow.orders.Order;
import lt.norma.crossbow.orders.OrderDirection;

import org.joda.time.DateTime;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test ExecutionReportTest class.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class ExecutionReportTest {
	/**
	 * Test the constructor.
	 * 
	 * @throws OrderException
	 * @throws ContractException
	 */
	@Test
	public void testCreation() throws OrderException, ContractException {
		Currency currency = Currency.createJpy();
		Exchange exchange = Exchange.createNasdaqExchange();
		StockContract c = new StockContract("ABC", exchange, currency);
		Order o = new DummyOrder(55, c, "MYORDER", OrderDirection.SELL, 800);

		ExecutionReport r = new ExecutionReport(o);
		assertEquals(o, r.getOrder());
		assertNotNull(r.getFilledBlocks());
	}

	/**
	 * Test of addFilledBlock method, of class ExecutionReport.
	 * 
	 * @throws ContractException
	 * @throws OrderException
	 */
	@Test
	public void testAddFilledPart() throws ContractException, OrderException {
		Currency currency = Currency.createJpy();
		Exchange exchange = Exchange.createNasdaqExchange();
		StockContract c = new StockContract("ABC", exchange, currency);
		Order o = new DummyOrder(55, c, "MYORDER", OrderDirection.SELL, 800);
		FilledBlock b1 = new FilledBlock(100, new BigDecimal("550.27"),
				new DateTime());
		FilledBlock b2 = new FilledBlock(100, new BigDecimal("550.27"),
				new DateTime());

		ExecutionReport r = new ExecutionReport(o);
		r.addFilledBlock(b1);
		r.addFilledBlock(b1);
		r.addFilledBlock(b2);
		assertEquals(3, r.getFilledBlocks().size());
		assertEquals(b1, r.getFilledBlocks().get(0));
		assertEquals(b1, r.getFilledBlocks().get(1));
		assertEquals(b2, r.getFilledBlocks().get(2));
	}

	/**
	 * Test of calculateTotalSize method, of class ExecutionReport.
	 * 
	 * @throws OrderException
	 * @throws ContractException
	 */
	@Test
	public void testCalculateTotalSize() throws OrderException,
			ContractException {
		Currency currency = Currency.createJpy();
		Exchange exchange = Exchange.createNasdaqExchange();
		StockContract c = new StockContract("ABC", exchange, currency);
		Order o = new DummyOrder(55, c, "MYORDER", OrderDirection.SELL, 800);
		ExecutionReport r = new ExecutionReport(o);
		FilledBlock b1 = new FilledBlock(200, new BigDecimal("500"),
				new DateTime());
		FilledBlock b2 = new FilledBlock(300, new BigDecimal("600"),
				new DateTime());
		FilledBlock b3 = new FilledBlock(400, new BigDecimal("700"),
				new DateTime());
		r.addFilledBlock(b1);
		r.addFilledBlock(b2);
		r.addFilledBlock(b3);

		assertEquals(900, r.calculateTotalSize());
	}

	/**
	 * Test of calculateAveragePrice method, of class ExecutionReport.
	 * 
	 * @throws ContractException
	 * @throws OrderException
	 */
	@Test
	public void testCalculateAveragePrice() throws ContractException,
			OrderException {
		Currency currency = Currency.createJpy();
		Exchange exchange = Exchange.createNasdaqExchange();
		StockContract c = new StockContract("ABC", exchange, currency);
		Order o = new DummyOrder(55, c, "MYORDER", OrderDirection.SELL, 800);
		ExecutionReport r = new ExecutionReport(o);
		FilledBlock b1 = new FilledBlock(1, new BigDecimal("100"),
				new DateTime());
		FilledBlock b2 = new FilledBlock(1, new BigDecimal("500"),
				new DateTime());
		FilledBlock b3 = new FilledBlock(1, new BigDecimal("500"),
				new DateTime());
		r.addFilledBlock(b1);
		r.addFilledBlock(b2);
		r.addFilledBlock(b3);

		BigDecimal correct = (new BigDecimal("1100")).divide(new BigDecimal(3),
				StaticSettings.pricePrecision, RoundingMode.HALF_UP);

		assertTrue(correct.compareTo(r.calculateAveragePrice()) == 0);
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
