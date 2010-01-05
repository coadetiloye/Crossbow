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

package lt.norma.crossbow.commissions;

import java.math.BigDecimal;

/**
 * Interactive Brokers method to calculate options commissions.
 * <ul>
 * <li>0.70 USD per contract, if premium >= 0.10 USD
 * <li>0.50 USD per contract, if premium >= 0.05 USD and premium {@literal <} 0.10 USD
 * <li>0.25 USD per contract, if premium {@literal <} 0.05 USD
 * <li>Minimum 1.0 USD per order
 * </ul>
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class CommissionsIbOptions implements Commissions
{
   private BigDecimal premiumHigh = new BigDecimal("0.10");
   private BigDecimal premiumLow = new BigDecimal("0.05");
   private BigDecimal rateHigh = new BigDecimal("0.70");
   private BigDecimal rateMedium = new BigDecimal("0.50");
   private BigDecimal rateLow = new BigDecimal("0.25");
   private BigDecimal minimumPerOrder = new BigDecimal("1.00");
   
   @Override
   public BigDecimal calculate(int size, BigDecimal price)
   {
      BigDecimal c;
      BigDecimal s = new BigDecimal(size);
      
      if (price.compareTo(premiumHigh) >= 0)
      {
         c = rateHigh.multiply(s);
      }
      else if (price.compareTo(premiumLow) < 0)
      {
         c = rateLow.multiply(s);
      }
      else
      {
         c = rateMedium.multiply(s);
      }
      
      if (c.compareTo(minimumPerOrder) < 0)
      {
         c = minimumPerOrder;
      }
      
      return c;
   }
}
