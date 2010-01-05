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

import lt.norma.crossbow.orders.OrderStatus;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test OrderStatus enumeration.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class OrderStatusTest
{
   /**
    * Test of toString method, of class OrderStatus.
    */
   @Test
   public void testToString()
   {
      OrderStatus s1 = OrderStatus.PARTIALLY_FILLED;
      assertEquals("partially filled", s1.toString());
      assertEquals("partially filled", s1.getTitle());
      assertEquals("Order is partially filled.", s1.getDescription());
      
      OrderStatus s2 = OrderStatus.CANCELED;
      assertEquals("canceled", s2.toString());
      assertEquals("canceled", s2.getTitle());
      assertEquals("Order is canceled by the executor.", s2.getDescription());
   }
}
