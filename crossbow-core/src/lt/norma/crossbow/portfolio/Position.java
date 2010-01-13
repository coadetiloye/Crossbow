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

package lt.norma.crossbow.portfolio;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;

import lt.norma.crossbow.configuration.StaticSettings;
import lt.norma.crossbow.contracts.Contract;
import lt.norma.crossbow.trading.FilledBlock;

/**
 * Stores information about a position.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class Position
{
   /** Contract. */
   private Contract contract;
   /** Position size. */
   private int size;
   /** List of filled blocks. */
   private List<FilledBlock> filledBlocks;
   
   public Position()
   {
      filledBlocks = new LinkedList<FilledBlock>();
   }
   
   /**
    * Adds a new filled block of the order.
    * 
    * @param block
    *           filled block
    */
   public void addFilledBlock(FilledBlock block)
   {
      filledBlocks.add(block);
   }
   
   /**
    * Gets total number of filled contracts.
    * 
    * @return total number of filled contracts
    */
   public int calculateTotalSize()
   {
      int total = 0;
      for (FilledBlock block : filledBlocks)
      {
         total += block.getSize();
      }
      return total;
   }
   
   /**
    * Calculates average execution price.
    * 
    * @return average execution price
    */
   public BigDecimal calculateAveragePrice()
   {
      BigDecimal totalValue = BigDecimal.ZERO;
      for (FilledBlock block : filledBlocks)
      {
         totalValue = totalValue.add(block.calculateValue());
      }
      return totalValue.divide(new BigDecimal(calculateTotalSize()), StaticSettings.pricePrecision,
            RoundingMode.HALF_UP);
   }
   
   /**
    * Gets list of blocks.
    * 
    * @return list of block
    */
   public List<FilledBlock> getFilledBlocks()
   {
      return filledBlocks;
   }
}
