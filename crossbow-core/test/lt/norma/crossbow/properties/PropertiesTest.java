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

import java.util.Collections;
import java.util.List;

import lt.norma.crossbow.exceptions.CrossbowException;
import lt.norma.crossbow.properties.Properties;
import lt.norma.crossbow.properties.Property;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test PropertiesTest class.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class PropertiesTest {
	/**
	 * Test of add and getByName methods, of class Properties.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddGet() throws Exception {
		Properties pl = new Properties();
		Property<String> p1 = new Property<String>("name1", "v1");
		Property<String> p2 = new Property<String>("name2", "v2");
		pl.add(p1);
		pl.add(p2);

		assertTrue(pl.propertyExists("name1"));
		assertTrue(pl.propertyExists("name2"));
		assertFalse(pl.propertyExists("name3"));
		assertEquals(p1, pl.getByName("name1"));
		assertEquals(p2, pl.getByName("name2"));
	}

	/**
	 * Test of add method, of class Properties.
	 * 
	 * @throws Exception
	 */
	@Test(expected = CrossbowException.class)
	public void testAdd() throws Exception {
		Properties pl = new Properties();
		Property<String> p1 = new Property<String>("name1", "v1");
		Property<String> p2 = new Property<String>("name1", "v2");
		pl.add(p1);
		pl.add(p2);
	}

	/**
	 * Test of set method, of class Properties.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSet() throws Exception {
		Properties pl = new Properties();
		Property<String> p1 = new Property<String>("name1", "v1");
		Property<String> p2 = new Property<String>("name1", "v2");
		pl.set(p1);
		pl.set(p2);
		assertEquals(1, pl.getList().size());
		assertEquals("v2", pl.getByName("name1").getValue());
	}

	/**
	 * Test of set method, of class Properties.
	 * 
	 * @throws Exception
	 */
	@Test(expected = CrossbowException.class)
	public void testSet2() throws Exception {
		Properties pl = new Properties();
		Property<String> p1 = new Property<String>("name1", "v1");
		Property<Integer> p2 = new Property<Integer>("name1", 105);
		pl.set(p1);
		pl.set(p2);
	}

	/**
	 * Test of getByName method, of class Properties.
	 * 
	 * @throws Exception
	 */
	@Test(expected = CrossbowException.class)
	public void testGetByName() throws Exception {
		Properties pl = new Properties();
		Property<String> p1 = new Property<String>("name1", "v1");
		Property<String> p2 = new Property<String>("name2", "v2");
		pl.getByName("name3");
	}

	/**
	 * Test of getByName method, of class Properties.
	 * 
	 * @throws Exception
	 */
	@Test(expected = CrossbowException.class)
	public void testGetByName2() throws Exception {
		Properties pl = new Properties();
		Property<String> p1 = new Property<String>("name1", "v1");
		Property<String> p2 = new Property<String>("name2", "v2");
		pl.getByName(null);
	}

	/**
	 * Test of getByName method, of class Properties.
	 * 
	 * @throws Exception
	 */
	@Test(expected = CrossbowException.class)
	public void testGetByName3() throws Exception {
		Properties pl = new Properties();
		pl.getByName("name");
	}

	/**
	 * Test of removeByName method, of class Properties.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRemoveByName() throws Exception {
		Properties pl = new Properties();
		Property<String> p1 = new Property<String>("name1", "v1");
		pl.add(p1);
		assertTrue(pl.propertyExists("name1"));
		pl.removeByName("name1");
		pl.removeByName("name2");
		assertFalse(pl.propertyExists("name1"));
	}

	/**
	 * Test of getList method, of class Properties.
	 * 
	 * @throws Exception
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testGetList() throws Exception {
		Properties pl = new Properties();
		pl.add(new Property<String>("name4", "v1"));
		pl.add(new Property<String>("name5", "v1"));
		pl.add(new Property<String>("name1", "v1"));
		pl.add(new Property<String>("name3", "v1"));
		pl.add(new Property<String>("name2", "v1"));
		List<Property> l = pl.getList();
		Collections.sort(l);
		assertEquals(5, l.size());
		l.remove(0);
		assertEquals("name2", l.get(0).getName());
		assertEquals("name5", l.get(3).getName());
		assertEquals(4, l.size());
		// Size of property list remains unchanged.
		assertEquals(5, pl.getList().size());

		// Test empty list.
		Properties pl2 = new Properties();
		assertEquals(0, pl2.getList().size());
	}
}
