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

import lt.norma.crossbow.orders.Direction;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test OrderDirection enumeration.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class DirectionTest
{
   /**
    * 
    */
   @Test
   public void test()
   {
      assertEquals(Direction.LONG, Direction.valueOf("LONG"));
      assertEquals(Direction.SHORT, Direction.valueOf("SHORT"));
      assertEquals(Direction.LONG, Direction.valueOf(Direction.LONG.name()));
   }
   
   /**
    * Test of toString method, of class OrderDirection.
    */
   @Test
   public void testToString()
   {
      Direction b = Direction.LONG;
      Direction s = Direction.SHORT;
      
      assertEquals("long", b.toString());
      assertEquals("long", b.getTitle());
      assertEquals("short", s.toString());
      assertEquals("short", s.getTitle());
   }
}
