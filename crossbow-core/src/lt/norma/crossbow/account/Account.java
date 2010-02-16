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

import lt.norma.crossbow.orders.OrderBook;

/**
 * Porfolio extended by account details.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public final class Account
{
   private final Money cash;
   private final Portfolio portfolio;
   private final OrderBook orderBook;
   
   /**
    * Constructor.
    * 
    * @param baseCurrency
    *           base currency of this account
    */
   public Account(Currency baseCurrency)
   {
      cash = new Money(baseCurrency);
      portfolio = new Portfolio();
      orderBook = new OrderBook();
   }
   
   /**
    * @return amount of money in this account
    */
   public Money getCash()
   {
      return cash;
   }
   
   /**
    * @return porfolio
    */
   public Portfolio getPortfolio()
   {
      return portfolio;
   }
   
   /**
    * @return order book
    */
   public OrderBook getOrderBook()
   {
      return orderBook;
   }
}
