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

import java.util.LinkedList;
import java.util.List;

/**
 * OCA (one cancels all) order group.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class OcaGroup
{
   /**
    * Title of the OCA group. Titles must be unique as they are used to identify the OCA group.
    */
   private String title;
   /** List of orders. */
   private List<Order> orders;
   
   /**
    * Constructor.
    * 
    * @param title title of the OCA group. Make sure the title is unique, as it is used to identify
    *           the group
    */
   public OcaGroup(String title)
   {
      this.title = title;
      orders = new LinkedList<Order>();
   }
   
   /**
    * Adds a new order to this OCA group.
    * 
    * @param order a new order to be added
    */
   public void addOrder(Order order)
   {
      if (order != null)
      {
         orders.add(order);
      }
   }
   
   /**
    * Gets a text string containing title and number of orders assigned to this group.
    * <p>
    * Example: <code>OCA group A (3)</code>
    * 
    * @return a text string representing this OCA group
    */
   @Override
   public String toString()
   {
      return "OCA group " + title + " (" + orders.size() + ")";
   }
   
   /**
    * Gets list of orders contained in thi OCA group.
    * 
    * @return list of orders
    */
   public List<Order> getOrders()
   {
      return orders;
   }
   
   /**
    * Gets title of the OCA group.
    * 
    * @return title of the OCA group
    */
   public String getTitle()
   {
      return title;
   }
}
