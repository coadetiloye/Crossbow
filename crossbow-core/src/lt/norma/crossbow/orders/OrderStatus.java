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
 * Order statuses.
 * <p>
 * Use <code>toString</code> method to get a human readable string representation of the status. A
 * more detailed description is available via <code>getDescription</code> method.
 * <p>
 * Status of a newly created order is set to <code>NEW</code> by the constructor. All other statuses
 * should be modified by the trade executor only.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public enum OrderStatus
{
   /**
    * Order is created and ready for sending. All newly created orders must have a <code>NEW</code>
    * status.
    */
   NEW("new", "Order is created and ready for sending."),
   /** Order is sent to the executor, but not yet confirmed as submitted. */
   PENDING("pending", "Order is sent to the executor, but not yet confirmed as submitted."),
   /** Order is sent and confirmed by the executor. */
   SUBMITTED("submitted", "Order is sent and confirmed by the executor."),
   /** Order is partially filled. */
   PARTIALLY_FILLED("partially filled", "Order is partially filled."),
   /** Order is completely filled. */
   FILLED("filled", "Order is completely filled."),
   /** Order is submitted, but for some reason deactivated by the executor. */
   INACTIVE("inactive", "Order is submitted, but for some reason deactivated by the executor."),
   /** Order cancellation is sent but not yet confirmed by the executor. */
   CANCELLATION_PENDING("cancelation pending", "Order cancellation is sent but not yet confirmed "
                                               + "by the executor."),
   /** Order is canceled by the executor. */
   CANCELED("canceled", "Order is canceled by the executor."),
   /** Order is rejected by the executor. */
   REJECTED("rejected", "Order is rejected by the executor.");
   /** A human readable status title. */
   private final String title;
   /** State description. */
   private final String description;
   
   /**
    * Constructor.
    * 
    * @param title a human readable status title. This title is returned by <code>toString</code>
    *           method. Actual field names, like <code>PENDING</code> or
    *           <code>PARTIALLY_FILLED</code> should not be displayed to the user but can be used
    *           for persistence and similar purposes.
    * @param description a more detailed description of the order status
    */
   private OrderStatus(String title, String description)
   {
      this.title = title;
      this.description = description;
   }
   
   /**
    * Gets a human readable string representation of the status.
    * 
    * @return a human readable string representation of the status
    */
   @Override
   public String toString()
   {
      return title;
   }
   
   /**
    * Gets a human readable string representation of the status.
    * 
    * @return a human readable string representation of the status
    */
   public String getTitle()
   {
      return title;
   }
   
   /**
    * Gets a more detailed description of the status.
    * 
    * @return description of the status
    */
   public String getDescription()
   {
      return description;
   }
}
