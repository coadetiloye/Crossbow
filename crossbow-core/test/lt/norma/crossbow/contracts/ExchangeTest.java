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

import lt.norma.crossbow.contracts.Exchange;
import lt.norma.crossbow.exceptions.InvalidArgumentRuntimeException;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalTime;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test Exchange class.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class ExchangeTest
{
   /**
    * Test the constructor.
    */
   @Test
   public void testCreatetion()
   {
      Exchange exchange =
            new Exchange("A", DateTimeZone.forID("America/New_York"), new LocalTime(9, 30, 0),
            new LocalTime(16, 0, 0));
      assertEquals("A", exchange.getName());
      assertEquals(DateTimeZone.forID("America/New_York"), exchange.getTimeZone());
   }
   
   /**
    * Test the constructor exceptions.
    */
   @Test(expected = InvalidArgumentRuntimeException.class)
   public void testCreatetion2()
   {
      new Exchange(null, DateTimeZone.forID("America/New_York"), new LocalTime(9, 30, 0),
            new LocalTime(16, 0, 0));
   }
   
   /**
    * Test the constructor exceptions.
    */
   @Test(expected = InvalidArgumentRuntimeException.class)
   public void testCreatetion3()
   {
      new Exchange("", DateTimeZone.forID("America/New_York"), new LocalTime(9, 30, 0),
            new LocalTime(16, 0, 0));
   }
   
   /**
    * Test the constructor exceptions.
    */
   @Test(expected = InvalidArgumentRuntimeException.class)
   public void testCreatetion4()
   {
      new Exchange("A", null, new LocalTime(9, 30, 0), new LocalTime(16, 0, 0));
   }
   
   /**
    * Test the constructor exceptions.
    */
   @Test(expected = InvalidArgumentRuntimeException.class)
   public void testCreatetion5()
   {
      new Exchange("A", DateTimeZone.forID("America/New_York"), null, new LocalTime(16, 0, 0));
   }
   
   /**
    * Test the constructor exceptions.
    */
   @Test(expected = InvalidArgumentRuntimeException.class)
   public void testCreatetion6()
   {
      new Exchange("A", DateTimeZone.forID("America/New_York"), new LocalTime(9, 30, 0), null);
   }
   
   /**
    * Test of createNasdaqExchange method, of class Exchange.
    */
   @Test
   public void testCreateNasdaq()
   {
      Exchange exchange = Exchange.createNasdaq();
      assertEquals("Nasdaq", exchange.getName());
      assertEquals(DateTimeZone.forID("America/New_York"), exchange.getTimeZone());
   }
   
   /**
    * Test of createNyseExchange method, of class Exchange.
    */
   @Test
   public void testCreateNyse()
   {
      Exchange exchange = Exchange.createNyse();
      assertEquals("Nyse", exchange.getName());
      assertEquals(DateTimeZone.forID("America/New_York"), exchange.getTimeZone());
   }
   
   /**
    * Test of isTradingHours method, of class Exchange.
    */
   @Test
   public void testIsTradingHours()
   {
      Exchange exchange =
            new Exchange("A", DateTimeZone.forID("America/New_York"), new LocalTime(9, 30, 0),
            new LocalTime(16, 0, 0));
      assertFalse(exchange.isTradingHours(new LocalTime(0, 0, 0)));
      assertFalse(exchange.isTradingHours(new LocalTime(7, 15, 0)));
      assertTrue(exchange.isTradingHours(new LocalTime(9, 30, 0))); // Inclusive.
      assertTrue(exchange.isTradingHours(new LocalTime(9, 30, 1)));
      assertTrue(exchange.isTradingHours(new LocalTime(14, 15, 15)));
      assertTrue(exchange.isTradingHours(new LocalTime(15, 59, 59)));
      assertFalse(exchange.isTradingHours(new LocalTime(16, 0, 0))); // Not inclusive.
      assertFalse(exchange.isTradingHours(new LocalTime(23, 59, 59)));
      
      // Exchange does not close
      Exchange exchange2 =
            new Exchange("A", DateTimeZone.forID("America/New_York"), new LocalTime(9, 30, 0),
            new LocalTime(0, 0, 0));
      assertFalse(exchange2.isTradingHours(new LocalTime(0, 0, 0)));
      assertFalse(exchange2.isTradingHours(new LocalTime(7, 15, 0)));
      assertTrue(exchange2.isTradingHours(new LocalTime(9, 30, 0))); // Inclusive.
      assertTrue(exchange2.isTradingHours(new LocalTime(9, 30, 1)));
      assertTrue(exchange2.isTradingHours(new LocalTime(14, 15, 15)));
      assertTrue(exchange2.isTradingHours(new LocalTime(15, 59, 59)));
      assertTrue(exchange2.isTradingHours(new LocalTime(16, 0, 0)));
      assertTrue(exchange2.isTradingHours(new LocalTime(23, 59, 59)));
      
      // Exchange is always open
      Exchange exchange3 =
            new Exchange("A", DateTimeZone.forID("America/New_York"), new LocalTime(0, 0, 0),
            new LocalTime(0, 0, 0));
      assertTrue(exchange3.isTradingHours(new LocalTime(0, 0, 0)));
      assertTrue(exchange3.isTradingHours(new LocalTime(7, 15, 0)));
      assertTrue(exchange3.isTradingHours(new LocalTime(9, 30, 0))); // Inclusive.
      assertTrue(exchange3.isTradingHours(new LocalTime(9, 30, 1)));
      assertTrue(exchange3.isTradingHours(new LocalTime(14, 15, 15)));
      assertTrue(exchange3.isTradingHours(new LocalTime(15, 59, 59)));
      assertTrue(exchange3.isTradingHours(new LocalTime(16, 0, 0)));
      assertTrue(exchange3.isTradingHours(new LocalTime(23, 59, 59)));
   }
   
   /**
    * Test of isTradingHours method, of class Exchange. 24 hours trading.
    */
   @Test
   public void testIsTradingHours2()
   {
      Exchange exchange =
            new Exchange("A", DateTimeZone.forID("America/New_York"), LocalTime.MIDNIGHT,
            LocalTime.MIDNIGHT);
      assertTrue(exchange.isTradingHours(new LocalTime(0, 0, 0)));
      assertTrue(exchange.isTradingHours(new LocalTime(7, 15, 0)));
      assertTrue(exchange.isTradingHours(new LocalTime(9, 30, 0)));
      assertTrue(exchange.isTradingHours(new LocalTime(9, 30, 1)));
      assertTrue(exchange.isTradingHours(new LocalTime(14, 15, 15)));
      assertTrue(exchange.isTradingHours(new LocalTime(15, 59, 59)));
      assertTrue(exchange.isTradingHours(new LocalTime(16, 0, 0)));
      assertTrue(exchange.isTradingHours(new LocalTime(23, 59, 59)));
      
      Exchange exchange2 =
            new Exchange("A", DateTimeZone.forID("America/New_York"), new LocalTime(0, 0, 0),
            new LocalTime(0, 0, 0));
      assertTrue(exchange2.isTradingHours(new LocalTime(0, 0, 0)));
      assertTrue(exchange2.isTradingHours(new LocalTime(7, 15, 0)));
      assertTrue(exchange2.isTradingHours(new LocalTime(9, 30, 0)));
      assertTrue(exchange2.isTradingHours(new LocalTime(9, 30, 1)));
      assertTrue(exchange2.isTradingHours(new LocalTime(14, 15, 15)));
      assertTrue(exchange2.isTradingHours(new LocalTime(15, 59, 59)));
      assertTrue(exchange2.isTradingHours(new LocalTime(16, 0, 0)));
      assertTrue(exchange2.isTradingHours(new LocalTime(23, 59, 59)));
   }
   
   /**
    * Test of toString method, of class Exchange.
    */
   @Test
   public void testToString()
   {
      Exchange exchange =
            new Exchange("AAAAAAaaaa", DateTimeZone.forID("America/New_York"), LocalTime.MIDNIGHT,
            LocalTime.MIDNIGHT);
      assertEquals(exchange.getName(), exchange.toString());
   }
   
   /**
    * Test of equals method, of class Exchange.
    */
   @Test
   public void testEquals()
   {
      Exchange exchange1 =
            new Exchange("AAAAAAaaaa", DateTimeZone.forID("America/New_York"), LocalTime.MIDNIGHT,
            LocalTime.MIDNIGHT);
      Exchange exchange2 =
            new Exchange("AAAAAAaaaa", DateTimeZone.forID("America/New_York"), new LocalTime(1, 1,
            1),
            new LocalTime(2, 2, 2));
      Exchange exchange3 =
            new Exchange("BAAAAAaaaa", DateTimeZone.forID("America/New_York"), new LocalTime(1, 1,
            1),
            new LocalTime(2, 2, 2));
      assertEquals(exchange1, exchange1);
      assertEquals(exchange1.hashCode(), exchange1.hashCode());
      assertEquals(exchange1, exchange2);
      assertEquals(exchange1.hashCode(), exchange2.hashCode());
      assertFalse(exchange1.equals(exchange3));
      assertFalse(exchange1.equals(null));
      assertFalse(exchange1.equals(new Object()));
   }
   
   /**
    * Test of equals method, of class Exchange.
    */
   @Test
   public void testCompareTo()
   {
      Exchange exchange1 =
            new Exchange("BB", DateTimeZone.forID("America/New_York"), LocalTime.MIDNIGHT,
            LocalTime.MIDNIGHT);
      Exchange exchange2 =
            new Exchange("BB", DateTimeZone.forID("America/New_York"), new LocalTime(1, 1, 1),
            new LocalTime(2, 2, 2));
      Exchange exchange3 =
            new Exchange("AA", DateTimeZone.forID("America/New_York"), new LocalTime(1, 1, 1),
            new LocalTime(2, 2, 2));
      Exchange exchange4 =
            new Exchange("CC", DateTimeZone.forID("America/New_York"), new LocalTime(1, 1, 1),
            new LocalTime(2, 2, 2));
      assertEquals(0, exchange1.compareTo(exchange2));
      assertTrue(exchange1.compareTo(exchange3) > 0);
      assertTrue(exchange1.compareTo(exchange4) < 0);
   }
}
