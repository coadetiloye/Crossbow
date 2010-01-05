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

import lt.norma.crossbow.properties.Property;

/**
 * Carries additional information about an order. Make sure the order executor
 * knows how to interpret custom attributes.
 * 
 * @param <Type>
 *            type of value this attribute can contain
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class OrderAttribute<Type> extends Property<Type> {
	/** Marks importance of this attribute. */
	private OrderAttributeFlag flag;

	/**
	 * Constructor.
	 * <p>
	 * Throws <code>InvalidArgumentRuntimeException</code> if attribute name is
	 * null or empty.
	 * 
	 * @param name
	 *            name by wich the attribute is referred, cannot be null or
	 *            empty.
	 * @param value
	 *            attribute value, can be null
	 * @param description
	 *            a short description of the attribute
	 * @param flag
	 *            marks importance of the attribute
	 */
	public OrderAttribute(String name, Type value, String description,
			OrderAttributeFlag flag) {
		super(name, value, description);
		this.flag = flag;
	}

	/**
	 * Constructor. Description is set to an empty string.
	 * <p>
	 * Throws <code>InvalidArgumentRuntimeException</code> if attribute name is
	 * null or empty.
	 * 
	 * @param name
	 *            name by wich the attribute is referred, cannot be null or
	 *            empty.
	 * @param value
	 *            attribute value, can be null
	 * @param flag
	 *            marks importance of the attribute
	 */
	public OrderAttribute(String name, Type value, OrderAttributeFlag flag) {
		super(name, value);
		this.flag = flag;
	}

	/**
	 * Gets importance flag of this attribute.
	 * 
	 * @return importance flag
	 */
	public OrderAttributeFlag getFlag() {
		return flag;
	}
}
