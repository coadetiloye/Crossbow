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
import lt.norma.crossbow.orders.Direction;
import lt.norma.crossbow.orders.MarketOrder;
import lt.norma.crossbow.orders.Order;
import lt.norma.crossbow.trading.ExecutionReport;
import lt.norma.crossbow.trading.FilledBlock;
import lt.norma.crossbow.trading.OrderExecutedEvent;
import lt.norma.crossbow.trading.OrderUpdatedEvent;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

/**
 * @author Vilius Normantas <code@norma.lt>
 */
public class MeasureListTest
{
   /**
    * Test method for
    * {@link lt.norma.crossbow.indicators.MeasureList#orderExecuted(lt.norma.crossbow.trading.OrderExecutedEvent)}
    * .
    * 
    * @throws Throwable
    */
   @Test
   public void testOrderExecuted() throws Throwable
   {
      Contract c = new StockContract("B", Exchange.createNasdaq(), Currency.createEur());
      MarketOrder o = new MarketOrder(0, c, Direction.LONG, 500);
      FilledBlock b = new FilledBlock(Direction.LONG, 100, new BigDecimal("8"), new DateTime());
      ExecutionReport r = new ExecutionReport(o, b);
      OrderExecutedEvent e = new OrderExecutedEvent(this, r);
      
      MockMeasure m1 = new MockMeasure("AA", false);
      MockMeasure m2 = new MockMeasure("BB", false);
      MockIndicator i1 = new MockIndicator("CC", true);
      
      MeasureList l = new MeasureList(new TimePeriodSplitter(1000 * 60, DateTimeZone.UTC));
      l.add(m1);
      l.add(m2);
      l.add(i1);
      
      l.orderExecuted(e);
      
      assertEquals(r, m1.lastExecutionReport);
      assertEquals(r, m2.lastExecutionReport);
   }
   
   /**
    * Test method for
    * {@link lt.norma.crossbow.indicators.MeasureList#orderUpdated(lt.norma.crossbow.trading.OrderUpdatedEvent)}
    * .
    * 
    * @throws Throwable
    */
   @Test
   public void testOrderUpdated() throws Throwable
   {
      Contract c = new StockContract("B", Exchange.createNasdaq(), Currency.createEur());
      MarketOrder o = new MarketOrder(0, c, Direction.LONG, 500);
      OrderUpdatedEvent e = new OrderUpdatedEvent(this, o);
      
      MockMeasure m1 = new MockMeasure("AA", false);
      MockMeasure m2 = new MockMeasure("BB", false);
      MockIndicator i1 = new MockIndicator("CC", true);
      
      MeasureList l = new MeasureList(new TimePeriodSplitter(1000 * 60, DateTimeZone.UTC));
      l.add(m1);
      l.add(m2);
      l.add(i1);
      
      l.orderUpdated(e);
      
      assertEquals(o, m1.lastUpdatedOrder);
      assertEquals(o, m2.lastUpdatedOrder);
   }
   
   private class MockMeasure extends Measure<Integer>
   {
      public ExecutionReport lastExecutionReport;
      public Order lastUpdatedOrder;
      
      public MockMeasure(String title, boolean collectPeriodicData)
      {
         super(title, collectPeriodicData);
      }
      
      @Override
      public void orderExecuted(ExecutionReport report)
      {
         lastExecutionReport = report;
      }
      
      @Override
      public void orderUpdated(Order order)
      {
         lastUpdatedOrder = order;
      }
   }
   
   private class MockIndicator extends Indicator<Integer>
   {
      public MockIndicator(String title, boolean collectPeriodicData)
      {
         super(title, collectPeriodicData);
      }
   }
}
