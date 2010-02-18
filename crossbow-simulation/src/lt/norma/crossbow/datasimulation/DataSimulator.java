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

package lt.norma.crossbow.datasimulation;

/**
 * Simulates market data.
 * 
 * @author Charles Adetiloye
 */
public class DataSimulator
{
   // private final TradeSimulator tradeSimulator;
   // private final QuoteSimulator quoteSimulator;
   private boolean continueLoop;
   
   /**
    * Constructor.
    */
   public DataSimulator()
   {
      // Constructor should take some arguments. See my email for suggestions.
      
      // quoteSimulator = new QuoteSimulator();
      // tradeSimulator = new TradeSimulator();
      
      continueLoop = false;
   }
   
   private void generateData()
   {
      // Generate random medianPrice
      // quoteSimulator.generateQuote(medianPrice);
      // tradeSimulator.generateTrade(medianPrice);
   }
   
   /**
    * Starts data simulation.
    */
   public void Start()
   {
      continueLoop = true;
      while (continueLoop)
      {
         generateData();
      }
   }
   
   /**
    * Starts data simulation with specified number of quote-trade pairs.
    * 
    * @param numberOfDataItems
    *           number of quote-trade pairs to be generated
    */
   public void Start(int numberOfDataItems)
   {
      for (int i = 0; i < numberOfDataItems; i++)
      {
         generateData();
      }
   }
   
   /**
    * Stops data simulation.
    */
   public void Stop()
   {
      continueLoop = false;
   }
   
   /**
    * @return true if the provider is generating data, false otherwise
    */
   public boolean isBuisy()
   {
      return continueLoop;
   }
}

// TODO DataSimulator is unfinished. CA is working on it.
// Consider access levels and mutability of fields.
// Consider concurrency.
// Create unit tests.

/*
 * Some remarks for CA:
 * Throw exceptions only from lt.norma.crossbow.exceptions package. If you need to create a custom
 * exception, extend CrossbowException or CrossbowRuntimeException.
 * Throw checked exceptions (CrossbowException or it's child) only for business logic level
 * problems.
 * Write at least minimal javaDoc comments for everything public or protected.
 * Quotes and trades don't have to go in pairs. You can generate several quotes, than several
 * trades. Try to simulate data as similar to real quotes as possible.
 */
