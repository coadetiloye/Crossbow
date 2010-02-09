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

package lt.norma.crossbow.trading;

import lt.norma.crossbow.data.*;
import lt.norma.crossbow.indicators.IndicatorList;
import lt.norma.crossbow.indicators.MeasureList;
import lt.norma.crossbow.indicators.PeriodSplitter;
import lt.norma.crossbow.orders.Order;
import lt.norma.crossbow.properties.Properties;

/**
 * Base class for trading strategies. Extend this class to create custom strategy.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class Strategy implements TradeListener, QuoteListener, TradeExecutorListener
{
   /**
    * Strategy title.
    */
   protected String title = "Unnamed strategy";
   /**
    * Short description.
    */
   protected String description = "";
   /**
    * Strategy version.
    */
   protected String version = "0.0";
   
   private final Properties parameters;
   private final IndicatorList indicators;
   private final MeasureList measures;
   private final TradeExecutor tradeExecutor;
   
   /**
    * Constructor.
    * 
    * @param indicatorPeriodSplitter
    *           used to split periodic data for indicators of this strategy
    * @param measurePeriodSplitter
    *           used to split periodic data for measures of this strategy
    * @param tradeExecutor
    */
   public Strategy(PeriodSplitter indicatorPeriodSplitter,
         PeriodSplitter measurePeriodSplitter, TradeExecutor tradeExecutor)
   {
      parameters = new Properties();
      indicators = new IndicatorList(indicatorPeriodSplitter);
      measures = new MeasureList(measurePeriodSplitter);
      this.tradeExecutor = tradeExecutor;
   }
   
   /**
    * Send an order.
    * 
    * @param order
    *           order to be sent
    */
   protected void sendOrder(Order order)
   {
      tradeExecutor.sendOrder(order);
   }
   
   @Override
   public void orderExecuted(OrderExecutedEvent event)
   {
      measures.orderExecuted(event);
   }
   
   @Override
   public void orderUpdated(OrderUpdatedEvent event)
   {
      measures.orderUpdated(event);
   }
   
   /**
    * Called as order sent by this strategy gets partially or completely executed.
    * 
    * @param report
    *           execution report
    */
   protected void orderExecuted(ExecutionReport report)
   {
   }
   
   /**
    * Called as order sent by this strategy is updated.
    * 
    * @param order
    *           order details
    */
   protected void orderUpdated(Order order)
   {
   }
   
   @Override
   public final void quoteReceived(QuoteEvent event)
   {
      indicators.quoteReceived(event);
      measures.quoteReceived(event);
      quoteReceived(event.getQuote());
   }
   
   @Override
   public final void tradeReceived(TradeEvent event)
   {
      indicators.tradeReceived(event);
      measures.tradeReceived(event);
      tradeReceived(event.getTrade());
   }
   
   /**
    * Called as new quote data is received.
    * 
    * @param quote
    *           quote data
    */
   protected void quoteReceived(Quote quote)
   {
   }
   
   /**
    * Called as new trade data is received.
    * 
    * @param trade
    *           trade data
    */
   protected void tradeReceived(Trade trade)
   {
   }
   
   /**
    * @return title of the strategy
    */
   public final String getTitle()
   {
      return title;
   }
   
   /**
    * @return short description of the strategy
    */
   public final String getDescription()
   {
      return description;
   }
   
   /**
    * @return version of the strategy
    */
   public final String getVersion()
   {
      return version;
   }
   
   /**
    * @return strategy parameters
    */
   public final Properties getParameters()
   {
      return parameters;
   }
   
   /**
    * @return indicators used by this strategy
    */
   public final IndicatorList getIndicators()
   {
      return indicators;
   }
   
   /**
    * @return measures of the strategy performance
    */
   public final IndicatorList getMeasures()
   {
      return measures;
   }
}

// TODO Create unit tests.
// TODO Consider concurrency.
// TODO Consider access levels and mutability of fields.
