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
import lt.norma.crossbow.exceptions.InvalidArgumentRuntimeException;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test InvalidArgumentRuntimeException class.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class InvalidArgumentRuntimeExceptionTest
{
   /**
    * Test the constructor.
    */
   @Test
   public void testCreation1()
   {
      ContractException cause = new ContractException("Test cause.");
      InvalidArgumentRuntimeException e1 =
            new InvalidArgumentRuntimeException("name", "value", "Test exception.", cause);
      assertEquals("name", e1.getArgumentName());
      assertEquals("value", e1.getArgumentValue());
      assertEquals("Invalid argument name=value. Test exception.", e1.getMessage());
      assertEquals(cause, e1.getCause());
      
      Object value = new Object();
      InvalidArgumentRuntimeException e2 =
            new InvalidArgumentRuntimeException("name", value, "Test exception.", cause);
      assertEquals(value, e2.getArgumentValue());
      InvalidArgumentRuntimeException e3 =
            new InvalidArgumentRuntimeException("name", null, "Test exception.", cause);
      assertEquals(null, e3.getArgumentValue());
      
      InvalidArgumentRuntimeException e4 =
            new InvalidArgumentRuntimeException(null, "value", "Test exception.", cause);
      assertEquals("null", e4.getArgumentName());
      InvalidArgumentRuntimeException e5 =
            new InvalidArgumentRuntimeException("", "value", "Test exception.", cause);
      assertEquals("", e5.getArgumentName());
      
      new InvalidArgumentRuntimeException("name", "value", null, cause);
      new InvalidArgumentRuntimeException("name", "value", "", cause);
      
      InvalidArgumentRuntimeException e8 =
            new InvalidArgumentRuntimeException("name", "value", "Test exception.", null);
      assertNull(e8.getCause());
   }
   
   /**
    * Test the constructor.
    */
   @Test
   public void testCreation2()
   {
      InvalidArgumentRuntimeException e1 =
            new InvalidArgumentRuntimeException("name", "value", "Test exception.");
      assertEquals("name", e1.getArgumentName());
      assertEquals("value", e1.getArgumentValue());
      assertEquals("Invalid argument name=value. Test exception.", e1.getMessage());
      
      Object value = new Object();
      InvalidArgumentRuntimeException e2 =
            new InvalidArgumentRuntimeException("name", value, "Test exception.");
      assertEquals(value, e2.getArgumentValue());
      InvalidArgumentRuntimeException e3 =
            new InvalidArgumentRuntimeException("name", null, "Test exception.");
      assertEquals(null, e3.getArgumentValue());
      
      InvalidArgumentRuntimeException e4 =
            new InvalidArgumentRuntimeException(null, "value", "Test exception.");
      assertEquals("null", e4.getArgumentName());
      InvalidArgumentRuntimeException e5 =
            new InvalidArgumentRuntimeException("", "value", "Test exception.");
      assertEquals("", e5.getArgumentName());
      
      new InvalidArgumentRuntimeException("name", "value", null);
      new InvalidArgumentRuntimeException("name", "value", "");
      
      InvalidArgumentRuntimeException e8 =
            new InvalidArgumentRuntimeException("name", "value", "Test exception.");
      assertNull(e8.getCause());
   }
   
   /**
    * Test the constructor.
    */
   @Test
   public void testCreation3()
   {
      InvalidArgumentRuntimeException e1 = new InvalidArgumentRuntimeException("name", "value");
      assertEquals("name", e1.getArgumentName());
      assertEquals("value", e1.getArgumentValue());
      assertEquals("Invalid argument name=value.", e1.getMessage());
      
      Object value = new Object();
      InvalidArgumentRuntimeException e2 = new InvalidArgumentRuntimeException("name", value);
      assertEquals(value, e2.getArgumentValue());
      InvalidArgumentRuntimeException e3 = new InvalidArgumentRuntimeException("name", null);
      assertEquals(null, e3.getArgumentValue());
      
      InvalidArgumentRuntimeException e4 = new InvalidArgumentRuntimeException(null, "value");
      assertEquals("null", e4.getArgumentName());
      InvalidArgumentRuntimeException e5 = new InvalidArgumentRuntimeException("", "value");
      assertEquals("", e5.getArgumentName());
      
      InvalidArgumentRuntimeException e8 = new InvalidArgumentRuntimeException("name", "value");
      assertNull(e8.getCause());
   }
   
   /**
    * Test throwing.
    */
   @Test(expected = InvalidArgumentRuntimeException.class)
   public void testThrowing()
   {
      ContractException cause = new ContractException("Test cause.");
      InvalidArgumentRuntimeException e1 =
            new InvalidArgumentRuntimeException("name", "value", "Test exception.", cause);
      throw e1;
   }
}
