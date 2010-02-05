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

package lt.norma.crossbow.account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lt.norma.crossbow.configuration.StaticSettings;
import lt.norma.crossbow.contracts.Contract;
import lt.norma.crossbow.orders.Direction;
import lt.norma.crossbow.trading.FilledBlock;

/**
 * Stores information about a position.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class Position
{
   private final Contract contract;
   private int size;
   private BigDecimal averagePrice;
   private final List<FilledBlock> openBlocks;
   private final Object lock;
   
   /**
    * Constructor.
    * 
    * @param contract
    *           contract of this position
    */
   public Position(Contract contract)
   {
      this.contract = contract;
      openBlocks = new ArrayList<FilledBlock>();
      size = 0;
      averagePrice = null;
      lock = new Object();
   }
   
   /**
    * Adds a new filled block.
    * 
    * @param block
    *           filled block
    */
   public void addFilledBlock(FilledBlock block)
   {
      synchronized (lock)
      {
         if (size >= 0 && block.getDirection() == Direction.LONG ||
             size <= 0 && block.getDirection() == Direction.SHORT)
         {
            // Add long block to long position or short block to short position.
            openBlocks.add(block);
         }
         else
         {
            // Add long block to short position or short block to long position.
            int blockSize = block.getSize();
            while (blockSize != 0 && !openBlocks.isEmpty())
            {
               FilledBlock firstBlock = openBlocks.get(0);
               openBlocks.remove(0);
               if (blockSize >= firstBlock.getSize())
               {
                  blockSize -= firstBlock.getSize();
               }
               else
               {
                  FilledBlock replaceBlock = new FilledBlock(
                        firstBlock.getDirection(), firstBlock.getSize() - blockSize,
                        firstBlock.getAveragePrice(), firstBlock.getTime());
                  blockSize = 0;
                  openBlocks.add(0, replaceBlock);
               }
            }
            if (blockSize > 0)
            {
               FilledBlock replaceBlock = new FilledBlock(
                     block.getDirection(), blockSize,
                     block.getAveragePrice(), block.getTime());
               openBlocks.add(replaceBlock);
            }
         }
         
         // Update size and average price.
         updateSizePrice();
      }
   }
   
   private void updateSizePrice()
   {
      int total = 0;
      for (FilledBlock block : openBlocks)
      {
         if (block.getDirection() == Direction.LONG)
            total += block.getSize();
         else
            total -= block.getSize();
      }
      size = total;
      
      BigDecimal totalValue = BigDecimal.ZERO;
      for (FilledBlock block : openBlocks)
      {
         totalValue = totalValue.add(block.calculateValue());
      }
      if (size == 0)
         averagePrice = null;
      else
         averagePrice =
               totalValue.divide(new BigDecimal(Math.abs(size)), StaticSettings.priceMathContext);
   }
   
   /**
    * @return position size. Can be negative if the position is short.
    */
   public int getSize()
   {
      synchronized (lock)
      {
         return size;
      }
   }
   
   /**
    * @return average price at which the position was opened. If the position is partially closed,
    *         the older blocks are removed or modified and average price is recalculated. Value can
    *         be null if position size is 0.
    */
   public BigDecimal getAveragePrice()
   {
      synchronized (lock)
      {
         return averagePrice;
      }
   }
   
   /**
    * @return the contract of this position
    */
   public Contract getContract()
   {
      return contract;
   }
}
