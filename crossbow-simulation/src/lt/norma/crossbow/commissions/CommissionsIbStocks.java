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
import lt.norma.crossbow.configuration.StaticSettings;

/**
 * Simplified Interactive Brokers method to calculate equities commissions.
 * <ul>
 * <li>0.005 USD per share
 * <li>Maximum 0.5% of trade value per order (exchange, ECN, and specialist fees are <b>not</b>
 * included)
 * <li>Minimum 1.0 USD per order
 * </ul>
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class CommissionsIbStocks implements Commissions
{
   private final BigDecimal ratePerShare = new BigDecimal("0.005");
   private final BigDecimal minimumPerOrder = new BigDecimal("1.00");
   private final BigDecimal maximumPercentPerOrder = new BigDecimal("0.50");
   
   @Override
   public BigDecimal calculate(int size, BigDecimal price)
   {
      BigDecimal c;
      BigDecimal s = new BigDecimal(size);
      
      BigDecimal max =
            price.multiply(s).multiply(maximumPercentPerOrder).divide(
            new BigDecimal("100"), StaticSettings.priceMathContext);
      c = ratePerShare.multiply(s);
      if (c.compareTo(max) > 0)
      {
         c = max;
      }
      if (c.compareTo(minimumPerOrder) < 0)
      {
         c = minimumPerOrder;
      }
      
      return c;
   }
}
