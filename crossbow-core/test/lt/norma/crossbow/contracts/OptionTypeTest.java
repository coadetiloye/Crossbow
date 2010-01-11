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

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test OptionType enumeration.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class OptionTypeTest
{
   /**
    * 
    */
   @Test
   public void testValues()
   {
      assertEquals(2, OptionType.values().length);
      assertEquals("CALL", OptionType.values()[0].toString());
      assertEquals("PUT", OptionType.values()[1].toString());
      assertEquals(OptionType.CALL, OptionType.valueOf("CALL"));
      assertEquals(OptionType.PUT, OptionType.valueOf("PUT"));
   }
}
