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

package lt.norma.crossbow.properties;

import lt.norma.crossbow.exceptions.InvalidArgumentRuntimeException;
import lt.norma.crossbow.properties.Property;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test Property class.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class PropertyTest
{
   /**
    * Test the constructor.
    */
   @Test
   public void testCreation()
   {
      Property<Integer> pi = new Property<Integer>("Žĵ", 8, "integer property");
      assertEquals("Žĵ", pi.getName());
      assertEquals(8, (int) pi.getValue());
      assertEquals("integer property", pi.getDescription());
      assertEquals(Integer.class, pi.getType());
      
      Object value = new Object();
      Property<Object> po = new Property<Object>("po", value);
      assertEquals("po", po.getName());
      assertEquals(value, po.getValue());
      assertEquals("", po.getDescription());
      assertEquals(Object.class, po.getType());
      
      Property<Object> po2 = new Property<Object>("po", null);
      assertNull(po2.getValue());
   }
   
   /**
    * Test the constructor.
    */
   @Test(expected = InvalidArgumentRuntimeException.class)
   public void testCreation2()
   {
      new Property<Double>("", 6.0);
   }
   
   /**
    * Test the constructor.
    */
   @Test(expected = InvalidArgumentRuntimeException.class)
   public void testCreation3()
   {
      new Property<Double>(null, 6.0);
   }
   
   /**
    * Test of toString method, of class Property.
    */
   @Test
   public void testToString_0args()
   {
      Property<String> ps = new Property<String>("name", "value");
      assertEquals("name: value", ps.toString());
   }
   
   /**
    * Test of toString method, of class Property.
    */
   @Test
   public void testToString_String()
   {
      Property<String> ps = new Property<String>("name", "value");
      assertEquals("name~~~value", ps.toString("~~~"));
   }
   
   /**
    * Test of typeToString method, of class Property.
    */
   @Test
   public void testTypeToString()
   {
      Object value = new Object();
      Property<Object> po = new Property<Object>("po", value);
      assertEquals("java.lang.Object", po.typeToString());
   }
   
   /**
    * Test of compareTo method, of class Property.
    */
   @Test
   public void testCompareTo()
   {
      Object value = new Object();
      Property<Object> p1 = new Property<Object>("b", value);
      Property<String> p2 = new Property<String>("b", "v");
      Property<Double> p3 = new Property<Double>("a", 5.0);
      Property<Integer> p4 = new Property<Integer>("c", 8);
      
      assertEquals(0, p1.compareTo(p1));
      assertEquals(0, p1.compareTo(p2));
      assertTrue(p1.compareTo(p3) > 0);
      assertTrue(p1.compareTo(p4) < 0);
   }
}
