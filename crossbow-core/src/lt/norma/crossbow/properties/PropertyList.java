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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import lt.norma.crossbow.exceptions.CrossbowException;

/**
 * Generic list of properties. Extend this class to create lists of custom properties.
 * <p>
 * Use concrete class <code>Properties</code> to hold properties.
 * 
 * @param <PropertyType>
 *           type of properties held in this list. Must be <code>Property</code> or it's child
 *           class.
 * @see Properties
 * @author Vilius Normantas <code@norma.lt>
 */
public abstract class PropertyList<PropertyType extends Property<?>>
{
   /** Hash map of properties. */
   private final HashMap<String, PropertyType> propertyMap;
   
   /**
    * Constructor.
    */
   public PropertyList()
   {
      propertyMap = new HashMap<String, PropertyType>();
   }
   
   /**
    * Add a new property.
    * 
    * @param property
    *           property to be added
    * @throws CrossbowException
    *            on duplicate property names
    */
   public void add(PropertyType property) throws CrossbowException
   {
      if (propertyExists(property.getName()))
      {
         throw new CrossbowException("Unable to add property \"" + property.toString()
                                     + "\". Property with the same name already exists.");
      }
      propertyMap.put(property.getName(), property);
   }
   
   /**
    * Sets existing property or adds a new one. If property with the same name and type already
    * exists, it will be overriden by a new property. Use <code>add</code> to safely add a new
    * property or <code>propertyExists</code> to check for existing properties.
    * 
    * @param property
    *           property to be set or added
    * @throws CrossbowException
    *            on duplicate property with different type
    */
   public void set(PropertyType property) throws CrossbowException
   {
      Property<?> existingProperty;
      try
      {
         existingProperty = getByName(property.getName());
      }
      catch (CrossbowException exception)
      {
         // Existing property not found, insert new property.
         propertyMap.put(property.getName(), property);
         return;
      }
      
      if (existingProperty.getType().equals(property.getType()))
      {
         // Override existing property.
         propertyMap.put(property.getName(), property);
      }
      else
      {
         throw new CrossbowException("Unable to set property \"" + property.toString()
                                     + "\". Existing property \"" + existingProperty.toString()
                                     + "\" has the same name but different type.");
      }
   }
   
   /**
    * Removes the property by specified name.
    * 
    * @param propertyName
    *           property name
    */
   public void removeByName(String propertyName)
   {
      propertyMap.remove(propertyName);
   }
   
   /**
    * Checks if the property with a specified name already exists.
    * 
    * @param propertyName
    *           property name
    * @return true if the property exists, false otherwise
    */
   public boolean propertyExists(String propertyName)
   {
      return propertyMap.containsKey(propertyName);
   }
   
   /**
    * Gets property by name.
    * 
    * @param propertyName
    *           property name
    * @return property
    * @throws CrossbowException
    *            if no property is found by specified name
    */
   public Property<?> getByName(String propertyName) throws CrossbowException
   {
      Property<?> result = propertyMap.get(propertyName);
      
      if (result == null)
      {
         throw new CrossbowException("Property with name \"" + propertyName + "\" not found.");
      }
      return result;
   }
   
   /**
    * Returns list of properties.
    * 
    * @return list
    */
   public List<Property<?>> getList()
   {
      List<Property<?>> list = new LinkedList<Property<?>>(propertyMap.values());
      return list;
   }
}
