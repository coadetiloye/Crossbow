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

package lt.norma.crossbow.exceptions;

import lt.norma.crossbow.exceptions.ContractException;
import lt.norma.crossbow.exceptions.ValueNotSetRuntimeException;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test ValueNotSetRuntimeException class.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class ValueNotSetRuntimeExceptionTest
{
   /**
    * Test the constructor.
    */
   @Test
   public void testCreation1()
   {
      ContractException cause = new ContractException("Test cause.");
      ValueNotSetRuntimeException e1 =
            new ValueNotSetRuntimeException("name", "Test exception.", cause);
      assertEquals("name", e1.getFieldName());
      assertEquals("Value of name is not set. Test exception.", e1.getMessage());
      assertEquals(cause, e1.getCause());
      
      ValueNotSetRuntimeException e4 =
            new ValueNotSetRuntimeException(null, "Test exception.", cause);
      assertEquals("null", e4.getFieldName());
      ValueNotSetRuntimeException e5 =
            new ValueNotSetRuntimeException("", "Test exception.", cause);
      assertEquals("", e5.getFieldName());
      
      ValueNotSetRuntimeException e6 = new ValueNotSetRuntimeException("name", null, cause);
      assertEquals("Value of name is not set.", e6.getMessage());
      ValueNotSetRuntimeException e7 = new ValueNotSetRuntimeException("name", "", cause);
      assertEquals("Value of name is not set.", e7.getMessage());
      
      ValueNotSetRuntimeException e8 =
            new ValueNotSetRuntimeException("name", "Test exception.", null);
      assertNull(e8.getCause());
   }
   
   /**
    * Test the constructor.
    */
   @Test
   public void testCreation2()
   {
      ValueNotSetRuntimeException e1 = new ValueNotSetRuntimeException("name", "Test exception.");
      assertEquals("name", e1.getFieldName());
      assertEquals("Value of name is not set. Test exception.", e1.getMessage());
      
      ValueNotSetRuntimeException e4 = new ValueNotSetRuntimeException(null, "Test exception.");
      assertEquals("null", e4.getFieldName());
      ValueNotSetRuntimeException e5 = new ValueNotSetRuntimeException("", "Test exception.");
      assertEquals("", e5.getFieldName());
      
      ValueNotSetRuntimeException e6 = new ValueNotSetRuntimeException("name", null);
      assertEquals("Value of name is not set.", e6.getMessage());
      ValueNotSetRuntimeException e7 = new ValueNotSetRuntimeException("name", "");
      assertEquals("Value of name is not set.", e7.getMessage());
      
      ValueNotSetRuntimeException e8 = new ValueNotSetRuntimeException("name", "Test exception.");
      assertNull(e8.getCause());
   }
   
   /**
    * Test the constructor.
    */
   @Test
   public void testCreation3()
   {
      ValueNotSetRuntimeException e1 = new ValueNotSetRuntimeException("name");
      assertEquals("name", e1.getFieldName());
      assertEquals("Value of name is not set.", e1.getMessage());
      
      ValueNotSetRuntimeException e4 = new ValueNotSetRuntimeException(null);
      assertEquals("null", e4.getFieldName());
      ValueNotSetRuntimeException e5 = new ValueNotSetRuntimeException("");
      assertEquals("", e5.getFieldName());
      
      ValueNotSetRuntimeException e8 = new ValueNotSetRuntimeException("name");
      assertNull(e8.getCause());
   }
   
   /**
    * Test throwing.
    */
   @Test(expected = ValueNotSetRuntimeException.class)
   public void testThrowing()
   {
      ContractException cause = new ContractException("Test cause.");
      ValueNotSetRuntimeException e1 =
            new ValueNotSetRuntimeException("name", "Test exception.", cause);
      throw e1;
   }
}
