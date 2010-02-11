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
import lt.norma.crossbow.indicators.*;
import lt.norma.crossbow.orders.Order;
import lt.norma.crossbow.properties.Properties;

/**
 * Base class for trading strategies. Extend this class to create custom strategy.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public abstract class Strategy implements TradeListener, QuoteListener, TradeExecutorListener
{
   private String title;
   private String description;
   private String version;
   private final Object propertyLock;
   
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
      title = "Unnamed strategy";
      description = "";
      version = "0.0";
      propertyLock = new Object();
      
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
   protected final void sendOrder(Order order)
   {
      tradeExecutor.sendOrder(order);
   }
   
   @Override
   public final void orderExecuted(OrderExecutedEvent event)
   {
      measures.orderExecuted(event);
      orderExecuted(event.getExecutionReport());
   }
   
   @Override
   public final void orderUpdated(OrderUpdatedEvent event)
   {
      measures.orderUpdated(event);
      orderUpdated(event.getOrder());
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
      // Update indicators and measures.
      indicators.quoteReceived(event);
      measures.quoteReceived(event);
      
      // Check if any period events occurred before the quote.
      if (indicators.getPeriodSplitter() != null)
      {
         PeriodSplitterResult result = indicators.getPeriodSplitter().getLastResult();
         switch (result.getAction())
         {
            case START_BEFORE:
               periodStarted();
               break;
            case END_BEFORE:
               periodEnded();
               break;
            case RESTART_BEFORE:
               periodEnded();
               periodStarted();
               break;
         }
      }
      
      // Forward quoteReceived event to child strategy.
      quoteReceived(event.getQuote());
      
      // Check if any period events occurred after the quote.
      if (indicators.getPeriodSplitter() != null)
      {
         PeriodSplitterResult result = indicators.getPeriodSplitter().getLastResult();
         switch (result.getAction())
         {
            case START_AFTER:
               periodStarted();
               break;
            case END_AFTER:
               periodEnded();
               break;
            case RESTART_AFTER:
               periodEnded();
               periodStarted();
               break;
         }
      }
   }
   
   @Override
   public final void tradeReceived(TradeEvent event)
   {
      // Update indicators and measures.
      indicators.tradeReceived(event);
      measures.tradeReceived(event);
      
      // Check if any period events occurred before the trade.
      if (indicators.getPeriodSplitter() != null)
      {
         PeriodSplitterResult result = indicators.getPeriodSplitter().getLastResult();
         switch (result.getAction())
         {
            case START_BEFORE:
               periodStarted();
               break;
            case END_BEFORE:
               periodEnded();
               break;
            case RESTART_BEFORE:
               periodEnded();
               periodStarted();
               break;
         }
      }
      
      // Forward tradeReceived event to child strategy.
      tradeReceived(event.getTrade());
      
      // Check if any period events occurred after the quote.
      if (indicators.getPeriodSplitter() != null)
      {
         PeriodSplitterResult result = indicators.getPeriodSplitter().getLastResult();
         switch (result.getAction())
         {
            case START_AFTER:
               periodStarted();
               break;
            case END_AFTER:
               periodEnded();
               break;
            case RESTART_AFTER:
               periodEnded();
               periodStarted();
               break;
         }
      }
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
    * Called at the beginning of period.
    */
   protected void periodStarted()
   {
   }
   
   /**
    * Called at the end of period.
    */
   protected void periodEnded()
   {
   }
   
   /**
    * @return title of the strategy
    */
   public final String getTitle()
   {
      synchronized (propertyLock)
      {
         return title;
      }
   }
   
   /**
    * @param title
    *           title of the strategy
    */
   protected final void setTitle(String title)
   {
      synchronized (propertyLock)
      {
         this.title = title;
      }
   }
   
   /**
    * @return short description of the strategy
    */
   public final String getDescription()
   {
      synchronized (propertyLock)
      {
         return description;
      }
   }
   
   /**
    * @param description
    *           short description of the strategy
    */
   protected final void setDescription(String description)
   {
      synchronized (propertyLock)
      {
         this.description = description;
      }
   }
   
   /**
    * @return version of the strategy
    */
   public final String getVersion()
   {
      synchronized (propertyLock)
      {
         return version;
      }
   }
   
   /**
    * @param version
    *           version of the strategy
    */
   protected final void setVersion(String version)
   {
      synchronized (propertyLock)
      {
         this.version = version;
      }
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
