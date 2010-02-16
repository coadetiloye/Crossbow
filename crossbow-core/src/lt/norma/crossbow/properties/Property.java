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

/**
 * Generic property. Contains property value, name by which the property is referred and a short
 * description.
 * 
 * @param <Type>
 *           type of value this property can contain
 * @author Vilius Normantas <code@norma.lt>
 */
public class Property<Type> implements Comparable<Property<?>>
{
   private final String name;
   private Type value;
   private final String description;
   private final Object lock;
   
   /**
    * Constructor.
    * <p>
    * Throws <code>InvalidArgumentRuntimeException</code> if property name is null or empty.
    * 
    * @param name
    *           name by which the property is referred, cannot be null or empty.
    * @param value
    *           property value, can be null
    * @param description
    *           a short description of the property
    */
   public Property(String name, Type value, String description)
   {
      if (name == null || name.isEmpty())
      {
         throw new InvalidArgumentRuntimeException("name", name,
               "Property name cannot be null or empty.");
      }
      
      this.name = name;
      this.value = value;
      this.description = description == null ? "" : description;
      lock = new Object();
   }
   
   /**
    * Constructor. Description is set to an empty string.
    * <p>
    * Throws <code>InvalidArgumentRuntimeException</code> if property name is null or empty.
    * 
    * @param name
    *           name by which the property is referred, cannot be null or empty.
    * @param value
    *           a short description of the property
    */
   public Property(String name, Type value)
   {
      this(name, value, null);
   }
   
   /**
    * Returns property as text with a default delimiter ": ".
    * <p>
    * Example: <br>
    * <code>MyProperty: 8</code>
    * 
    * @return property as text
    */
   @Override
   public final String toString()
   {
      return toString(": ");
   }
   
   /**
    * Returns property as text with specified delimiter.
    * <p>
    * Example (delimiter is set to " = "): <br>
    * <code>MyProperty = 8</code>
    * 
    * @param delimiter
    *           delimiter string
    * @return property as text
    */
   public final String toString(String delimiter)
   {
      synchronized (lock)
      {
         return name + delimiter + value.toString();
      }
   }
   
   /**
    * Returns type of the value name as text.
    * 
    * @return class name
    */
   public final String typeToString()
   {
      synchronized (lock)
      {
         return value.getClass().getName();
      }
   }
   
   /**
    * Returns type of the value.
    * 
    * @return type of the value
    */
   public final Class<?> getType()
   {
      synchronized (lock)
      {
         return value.getClass();
      }
   }
   
   /**
    * Alphabetically compares name of the specified property to name of this property.
    * 
    * @param property
    *           a property to be compared to this property
    * @return 0 if both properties are have the same name;<br>
    *         a negative integer if this property is "less" than the specified property;<br>
    *         a positive integer if this property is "greater";
    */
   @Override
   public int compareTo(Property<?> property)
   {
      return name.compareTo(property.name);
   }
   
   /**
    * Gets name.
    * 
    * @return property name
    */
   public final String getName()
   {
      return name;
   }
   
   /**
    * Gets value.
    * 
    * @return property value
    */
   public final Type getValue()
   {
      synchronized (lock)
      {
         return value;
      }
   }
   
   /**
    * Sets property value.
    * 
    * @param value
    *           property value
    */
   public final void setValue(Type value)
   {
      synchronized (lock)
      {
         this.value = value;
      }
   }
   
   /**
    * Gets description.
    * 
    * @return property description
    */
   public final String getDescription()
   {
      return description;
   }
}
