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

import java.util.HashMap;

import lt.norma.crossbow.contracts.Contract;
import lt.norma.crossbow.trading.OrderExecutedEvent;
import lt.norma.crossbow.trading.OrderUpdatedEvent;
import lt.norma.crossbow.trading.TradeExecutorListener;

/**
 * Contains list of positions.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class Portfolio implements TradeExecutorListener
{
   private final HashMap<Contract, Position> positions;
   private final Object lock;
   
   /**
    * Constructor.
    */
   public Portfolio()
   {
      positions = new HashMap<Contract, Position>();
      lock = new Object();
   }
   
   @Override
   public void orderExecuted(OrderExecutedEvent event)
   {
      synchronized (lock)
      {
         Contract contract = event.getExecutionReport().getOrder().getContract();
         if (!positions.containsKey(contract))
         {
            Position position = new Position(contract);
            position.addFilledBlock(event.getExecutionReport().getFilledBlock());
            positions.put(contract, position);
         }
         else
         {
            positions.get(contract).addFilledBlock(event.getExecutionReport().getFilledBlock());
         }
      }
   }
   
   /**
    * @param contract
    *           contract of a requested position
    * @return position for specified contract, or null if no position found
    */
   public Position getPosition(Contract contract)
   {
      synchronized (lock)
      {
         return positions.get(contract);
      }
   }
   
   @Override
   public void orderUpdated(OrderUpdatedEvent event)
   {
      // Ignore order updates.
   }
}
