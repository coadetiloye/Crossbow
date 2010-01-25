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

package lt.norma.testutilities;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Vilius Normantas <code@norma.lt>
 */
public final class Reflection
{
   /**
    * Get value of a private field.
    * <p>
    * Usage:
    * 
    * <pre>
    * ValueType v = (ValueType)Reflection.getField(&quot;fieldName&quot;, object);
    * </pre>
    * 
    * @param name
    *           name of the field
    * @param object
    *           object containing the field
    * @return value of the field
    * @throws NoSuchFieldException
    * @throws IllegalArgumentException
    * @throws IllegalAccessException
    */
   public static Object getField(String name, Object object)
         throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException
   {
      Field field = object.getClass().getDeclaredField(name);
      field.setAccessible(true);
      return field.get(object);
   }
   
   /**
    * Get value of a private field.
    * <p>
    * Usage:
    * 
    * <pre>
    * ValueType v = (ValueType)Reflection.getField(&quot;fieldName&quot;, object, MyClass.class);
    * </pre>
    * 
    * @param name
    *           name of the field
    * @param object
    *           object containing the field
    * @param type
    *           class of the object
    * @return value of the field
    * @throws NoSuchFieldException
    * @throws IllegalArgumentException
    * @throws IllegalAccessException
    */
   @SuppressWarnings("unchecked")
   public static Object getField(String name, Object object, Class type)
         throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException
   {
      Field field = type.getField(name);
      field.setAccessible(true);
      return field.get(object);
   }
   
   /**
    * Sets value of a private field.
    * <p>
    * Usage:
    * 
    * <pre>
    * Reflection.setField(&quot;fieldName&quot;, object, value);
    * </pre>
    * 
    * @param name
    *           name of the field
    * @param object
    *           object containing the field
    * @param value
    *           value to be assigned to the field
    * @throws NoSuchFieldException
    * @throws IllegalArgumentException
    * @throws IllegalAccessException
    */
   public static void setField(String name, Object object, Object value)
         throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException
   {
      Field field = object.getClass().getDeclaredField(name);
      field.setAccessible(true);
      field.set(object, value);
   }
   
   /**
    * Sets value of a private field.
    * <p>
    * Usage:
    * 
    * <pre>
    * Reflection.setField(&quot;fieldName&quot;, object, value);
    * </pre>
    * 
    * @param name
    *           name of the field
    * @param object
    *           object containing the field
    * @param value
    *           value to be assigned to the field
    * @param type
    *           class of the object
    * @throws NoSuchFieldException
    * @throws IllegalArgumentException
    * @throws IllegalAccessException
    */
   @SuppressWarnings("unchecked")
   public static void setField(String name, Object object, Object value, Class type)
         throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException
   {
      Field field = type.getDeclaredField(name);
      field.setAccessible(true);
      field.set(object, value);
   }
   
   /**
    * Call method.
    * <p>
    * Usage:
    * 
    * <pre>
    * arguments = new Object[1];
    * arguments[0] = &quot;argument1&quot;;
    * ResultType result =
    *       (ResultType)Reflection.callMethod(&quot;methodName&quot;, object, arguments);
    * </pre>
    * 
    * @param name
    *           name of the method
    * @param object
    *           object containing the method
    * @param arguments
    *           method arguments
    * @return result of the method, or null if the method return void
    * @throws NoSuchMethodException
    * @throws IllegalAccessException
    * @throws IllegalArgumentException
    * @throws InvocationTargetException
    */
   public static Object callMethod(String name, Object object, Object arguments[])
         throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException,
         InvocationTargetException
   {
      Class<?> parameterTypes[] = new Class[arguments.length];
      for (int i = 0; i < arguments.length; i++)
      {
         parameterTypes[i] = arguments[i].getClass();
      }
      
      Method method = object.getClass().getDeclaredMethod(name, parameterTypes);
      method.setAccessible(true);
      
      if (method.getReturnType() == void.class)
      {
         method.invoke(object, arguments);
         return null;
      }
      else
      {
         return method.invoke(object, arguments);
      }
   }
}
