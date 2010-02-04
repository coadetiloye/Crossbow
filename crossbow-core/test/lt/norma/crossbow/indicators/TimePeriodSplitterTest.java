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

package lt.norma.crossbow.indicators;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import lt.norma.crossbow.account.Currency;
import lt.norma.crossbow.contracts.Contract;
import lt.norma.crossbow.contracts.Exchange;
import lt.norma.crossbow.contracts.StockContract;
import lt.norma.crossbow.data.Quote;
import lt.norma.crossbow.data.Trade;
import lt.norma.crossbow.exceptions.ContractException;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

/**
 * @author Vilius Normantas <code@norma.lt>
 */
public class TimePeriodSplitterTest
{
   /**
    * Test method for {@link TimePeriodSplitter#checkEndOfPeriod(Quote)} and
    * {@link TimePeriodSplitter#checkEndOfPeriod(Trade)}.
    * 
    * @throws ContractException
    */
   @Test
   public void testCheckEndOfPeriod() throws ContractException
   {
      Contract c = new StockContract("B", Exchange.createNasdaq(), Currency.createEur());
      
      DateTime t1 = new DateTime(2010, 1, 1, 14, 0, 1, 0, DateTimeZone.forID("America/New_York"));
      DateTime t2 = new DateTime(2010, 1, 1, 14, 0, 1, 0, DateTimeZone.forID("America/New_York"));
      DateTime t3 = new DateTime(2010, 1, 1, 14, 0, 1, 999, DateTimeZone.forID("America/New_York"));
      DateTime t4 = new DateTime(2010, 1, 1, 14, 0, 2, 0, DateTimeZone.forID("America/New_York"));
      DateTime t5 = new DateTime(2999, 1, 1, 14, 0, 2, 0, DateTimeZone.forID("America/New_York"));
      
      Trade trade1 = new Trade(c, new BigDecimal("8.05"), 888, t1);
      Trade trade2 = new Trade(c, new BigDecimal("8.05"), 888, t2);
      Quote quote3 = new Quote(c, new BigDecimal("8.05"), 500, new BigDecimal("7.0"), 800, t3);
      Quote quote4 = new Quote(c, new BigDecimal("8.05"), 500, new BigDecimal("7.0"), 800, t4);
      Trade trade5 = new Trade(c, new BigDecimal("8.05"), 888, t5);
      
      TimePeriodSplitter s = new TimePeriodSplitter(1000, DateTimeZone.forID("America/New_York"));
      
      PeriodSplitterResult r;
      
      r = s.checkEndOfPeriod(trade1);
      assertEquals(PeriodSplitterAction.START_BEFORE, r.getAction());
      
      r = s.checkEndOfPeriod(trade2);
      assertEquals(PeriodSplitterAction.NO_ACTION, r.getAction());
      
      r = s.checkEndOfPeriod(quote3);
      assertEquals(PeriodSplitterAction.NO_ACTION, r.getAction());
      
      r = s.checkEndOfPeriod(quote4);
      assertEquals(PeriodSplitterAction.RESTART_BEFORE, r.getAction());
      
      r = s.checkEndOfPeriod(trade5);
      assertEquals(PeriodSplitterAction.RESTART_BEFORE, r.getAction());
   }
}
