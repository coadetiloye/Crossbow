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

/**
 * Runtime exception thrown if the requested value is not set yet.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class ValueNotSetRuntimeException extends CrossbowRuntimeException
{
   private String fieldName;
   
   /**
    * Constructor.
    * 
    * @param fieldName
    *           name of the field that caused this exception
    * @param message
    *           exception message. Use this field to explain the value should be set to avoid
    *           this exception.
    * @param cause
    *           an exception that caused this invalid argument exception, may be null.
    */
   public ValueNotSetRuntimeException(String fieldName, String message, Throwable cause)
   {
      super("Value of " + fieldName + " is not set. " + message, cause);
      this.fieldName = String.valueOf(fieldName);
   }
   
   /**
    * Constructor. No cause specified.
    * 
    * @param fieldName
    *           name of the field that caused this exception
    * @param message
    *           exception message. Use this field to explain the value should be set to avoid
    *           this exception.
    */
   public ValueNotSetRuntimeException(String fieldName, String message)
   {
      super("Value of " + fieldName + " is not set. " + message);
      this.fieldName = String.valueOf(fieldName);
   }
   
   /**
    * Constructor. No cause or message specified.
    * 
    * @param fieldName
    *           name of the field that caused this exception
    */
   public ValueNotSetRuntimeException(String fieldName)
   {
      super("Value of " + fieldName + " is not set.");
      this.fieldName = String.valueOf(fieldName);
   }
   
   /**
    * Gets name of the field.
    * 
    * @return name of the field
    */
   public String getFieldName()
   {
      return fieldName;
   }
}
