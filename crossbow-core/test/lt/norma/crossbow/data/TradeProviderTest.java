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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
 * @author Vilius Normantas <code@norma.lt>
 */
public class TradeProviderTest
{
   /**
    * @throws ContractException
    */
   @Test
   public void testFireQuoteEvent() throws ContractException
   {
      Exchange e = Exchange.createNasdaq();
      Currency c = Currency.createEur();
      Contract s = new StockContract("B", e, c);
      DateTime t = new DateTime(2005, 1, 1, 14, 0, 0, 0, DateTimeZone.forID("America/New_York"));
      Trade t1 = new Trade(s, new BigDecimal("500.01"), 1, t);
      Trade t2 = new Trade(s, new BigDecimal("15.1"), 2, t);
      
      MockTradeProvider provider = new MockTradeProvider();
      MockTradeListener listener1 = new MockTradeListener();
      MockTradeListener listener2 = new MockTradeListener();
      MockTradeListener listener3 = new MockTradeListener();
      provider.fire(t1);
      provider.fire(t2);
      // Nobody received the quotes as no listeners added
      assertNull(listener1.lastTrade);
      assertNull(listener1.lastSender);
      assertNull(listener2.lastTrade);
      assertNull(listener2.lastSender);
      assertNull(listener3.lastTrade);
      assertNull(listener3.lastSender);
      // Add listener1 and listener2
      provider.addListener(listener1);
      provider.addListener(listener2);
      provider.fire(t1);
      assertEquals(t1, listener1.lastTrade);
      assertNotNull(listener1.lastSender);
      assertEquals(t1, listener2.lastTrade);
      assertNotNull(listener2.lastSender);
      assertNull(listener3.lastTrade);
      assertNull(listener3.lastSender);
      // Remove listener2
      provider.removeListener(listener2);
      provider.fire(t2);
      assertEquals(t2, listener1.lastTrade);
      assertNotNull(listener1.lastSender);
      assertEquals(t1, listener2.lastTrade);
      assertNotNull(listener2.lastSender);
      assertNull(listener3.lastTrade);
      assertNull(listener3.lastSender);
   }
   
   private class MockTradeProvider extends TradeProvider
   {
      public void fire(Trade trade)
      {
         fireTradeEvent(trade);
      }
   }
   
   private class MockTradeListener implements TradeListener
   {
      public Trade lastTrade;
      public Object lastSender;
      
      @Override
      public void tradeReceived(TradeEvent event)
      {
         lastTrade = event.getTrade();
         lastSender = event.getSource();
      }
   }
}
