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

/**
 * Marks importance of the attributes.
 */
public enum OrderAttributeFlag
{
   /**
    * Marks attributes used for informational purposes only. Order executors ingore such attributes.
    */
   DESCRIPTIVE,
   /**
    * Marks optional attributes. These attributes are safe to ignore, but some executors may use
    * them.
    */
   OPTIONAL,
   /**
    * Marks obligatory attributes. These attributes cannot be ignored by order executors. Executors
    * throw an exception and/or mark the order as rejected if they find any unrecognized obligatory
    * attributes within the order.
    */
   OBLIGATORY;
}
