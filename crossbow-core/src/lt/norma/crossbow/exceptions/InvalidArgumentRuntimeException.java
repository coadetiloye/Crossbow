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
 * An argument passed to a method or constructor is invalid. Throw this runtime
 * exception if the value passed to a method or constructor is invalid and the
 * program is not expected to handle the exception properly.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class InvalidArgumentRuntimeException extends CrossbowRuntimeException {
	private String argumentName;
	private Object argumentValue;

	/**
	 * Constructor.
	 * 
	 * @param argumentName
	 *            name of the argument, null value converted to a string "null"
	 * @param argumentValue
	 *            value of the argument that caused the exception, may be null.
	 *            If the object does not have a toString method, pass a string
	 *            representation of the value.
	 * @param message
	 *            exception message. Use this field to explain why the value of
	 *            passed argument in not valid.
	 * @param cause
	 *            an exception that caused this invalid argument exception, may
	 *            be null.
	 */
	public InvalidArgumentRuntimeException(String argumentName,
			Object argumentValue, String message, Throwable cause) {
		super("Invalid argument " + argumentName + "="
				+ String.valueOf(argumentValue) + ". " + message, cause);
		this.argumentName = String.valueOf(argumentName);
		this.argumentValue = argumentValue;
	}

	/**
	 * Constructor. No cause specified.
	 * <p>
	 * Do not use this constructor when a causing exception exists.
	 * 
	 * @param argumentName
	 *            name of the argument, null value converted to a string "null"
	 * @param argumentValue
	 *            value of the argument that caused the exception, may be null.
	 *            If the object does not have a toString method, pass a string
	 *            representation of the value.
	 * @param message
	 *            exception message. Use this field to explain why the value of
	 *            passed argument in not valid.
	 */
	public InvalidArgumentRuntimeException(String argumentName,
			Object argumentValue, String message) {
		super("Invalid argument " + argumentName + "="
				+ String.valueOf(argumentValue) + ". " + message);
		this.argumentName = String.valueOf(argumentName);
		this.argumentValue = argumentValue;
	}

	/**
	 * Constructor. No cause or message specified.
	 * <p>
	 * Use this constructor when no causing exception exists and no message is
	 * needed to explain the problem. For example, this exception may be used
	 * when unexpected null value is received. However in most cases an
	 * explanation of the exception should be specified.
	 * 
	 * @param argumentName
	 *            name of the argument, null value converted to a string "null"
	 * @param argumentValue
	 *            value of the argument that caused the exception, may be null.
	 *            If the object does not have a toString method, pass a string
	 *            representation of the value.
	 */
	public InvalidArgumentRuntimeException(String argumentName,
			Object argumentValue) {
		super("Invalid argument " + argumentName + "="
				+ String.valueOf(argumentValue) + ".");
		this.argumentName = String.valueOf(argumentName);
		this.argumentValue = argumentValue;
	}

	/**
	 * Gets name of the argument.
	 * 
	 * @return argument name
	 */
	public String getArgumentName() {
		return argumentName;
	}

	/**
	 * Gets the invalid value of an argument that caused this exception.
	 * 
	 * @return invalid value
	 */
	public Object getArgumentValue() {
		return argumentValue;
	}
}
