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

import lt.norma.crossbow.exceptions.CrossbowException;
import lt.norma.crossbow.orders.OrderAttribute;
import lt.norma.crossbow.orders.OrderAttributeFlag;
import lt.norma.crossbow.properties.Properties;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test OrderAttribute class.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class OrderAttributeTest
{
   /**
    * Test the constructor.
    */
   @Test
   public void testCreation()
   {
      OrderAttribute<String> a =
            new OrderAttribute<String>("n", "v", OrderAttributeFlag.DESCRIPTIVE);
      assertEquals(OrderAttributeFlag.DESCRIPTIVE, a.getFlag());
      
      OrderAttribute<String> a2 =
            new OrderAttribute<String>("n", "v", "d", OrderAttributeFlag.OBLIGATORY);
      assertEquals(OrderAttributeFlag.OBLIGATORY, a2.getFlag());
   }
   
   /**
    * Tests attributes added to list of properties.
    * 
    * @throws CrossbowException
    */
   @Test
   public void testProperties() throws CrossbowException
   {
      OrderAttribute<String> a =
            new OrderAttribute<String>("n1", "v", OrderAttributeFlag.DESCRIPTIVE);
      OrderAttribute<String> a2 =
            new OrderAttribute<String>("n2", "v", "d", OrderAttributeFlag.OBLIGATORY);
      
      Properties pl = new Properties();
      pl.add(a);
      pl.add(a2);
      
      assertEquals(OrderAttributeFlag.DESCRIPTIVE,
            ((OrderAttribute<?>) pl.getByName("n1")).getFlag());
      assertEquals(OrderAttributeFlag.OBLIGATORY,
            ((OrderAttribute<?>) pl.getByName("n2")).getFlag());
   }
}
