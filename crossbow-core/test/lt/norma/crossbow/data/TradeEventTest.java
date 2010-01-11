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

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import lt.norma.crossbow.contracts.Contract;
import lt.norma.crossbow.contracts.Currency;
import lt.norma.crossbow.contracts.Exchange;
import lt.norma.crossbow.contracts.StockContract;
import lt.norma.crossbow.exceptions.ContractException;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;


/**
 * 
 * @author Vilius Normantas <code@norma.lt>
 *
 */
public class TradeEventTest
{
   private Exchange e;
   private Currency c;
   private Contract s;
   private DateTime t;
   private Trade trade;
   
   /**
    * Constructor.
    * 
    * @throws ContractException
    */
   public TradeEventTest() throws ContractException
   {
      e = Exchange.createNasdaq();
      c = Currency.createEur();
      s = new StockContract("B", e, c);
      t = new DateTime(2005, 1, 1, 14, 0, 0, 0, DateTimeZone.forID("America/New_York"));
      trade = new Trade(s, new BigDecimal("8.05"), 888, t);
   }
   
   /**
    * 
    */
   @Test
   public void testTradeEvent()
   {
      Object source = new Object();
      TradeEvent te = new TradeEvent(source, trade);
      assertEquals(source, te.getSource());
      assertEquals(trade, te.getTrade());
   }
}
