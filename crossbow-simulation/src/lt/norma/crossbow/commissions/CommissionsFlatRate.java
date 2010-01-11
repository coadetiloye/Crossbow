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
 * Flat-rate method to calculate commissions.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class CommissionsFlatRate implements Commissions
{
   private BigDecimal rate;
   
   /**
    * Constructor.
    * 
    * @param rate
    *           flat rate in base currency applied to every order
    */
   public CommissionsFlatRate(BigDecimal rate)
   {
      this.rate = rate;
   }
   
   @Override
   public BigDecimal calculate(int size, BigDecimal price)
   {
      return rate;
   }
}
