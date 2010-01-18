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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import lt.norma.crossbow.account.Currency;
import lt.norma.crossbow.account.Position;
import lt.norma.crossbow.contracts.Exchange;
import lt.norma.crossbow.contracts.StockContract;
import lt.norma.crossbow.exceptions.ContractException;
import lt.norma.crossbow.orders.Direction;
import lt.norma.crossbow.trading.FilledBlock;
import lt.norma.testutilities.Reflection;

import org.joda.time.DateTime;
import org.junit.Test;

/**
 * @author Vilius Normantas <code@norma.lt>
 */
public class PositionTest
{
   Currency currency;
   Exchange exchange;
   StockContract c;
   
   /**
    * Constructor.
    * 
    * @throws ContractException
    */
   public PositionTest() throws ContractException
   {
      currency = Currency.createJpy();
      exchange = Exchange.createNasdaq();
      c = new StockContract("ABC", exchange, currency);
   }
   
   /**
    * Test method for {@link lt.norma.crossbow.account.Position#Position()}.
    * 
    * @throws IllegalAccessException
    * @throws NoSuchFieldException
    * @throws IllegalArgumentException
    */
   @Test
   public void testPosition() throws IllegalArgumentException, NoSuchFieldException,
         IllegalAccessException
   {
      Position p = new Position(c);
      assertEquals(c, p.getContract());
      assertEquals(0, p.getSize());
      assertNull(p.getAveragePrice());
      assertNotNull(getOpenBlocks(p));
   }
   
   /**
    * Test adding long blocks to long position.
    * 
    * @throws IllegalAccessException
    * @throws NoSuchFieldException
    * @throws IllegalArgumentException
    */
   @Test
   public void testAddFilledBlock1()
         throws IllegalArgumentException, NoSuchFieldException, IllegalAccessException
   {
      Position p = new Position(c);
      FilledBlock b1 = new FilledBlock(Direction.LONG, 500, new BigDecimal(80), new DateTime(100));
      FilledBlock b2 = new FilledBlock(Direction.LONG, 300, new BigDecimal(50), new DateTime(200));
      FilledBlock b3 = new FilledBlock(Direction.LONG, 200, new BigDecimal(70), new DateTime(300));
      
      // Before first block.
      assertEquals(0, getOpenBlocks(p).size());
      assertEquals(0, p.getSize());
      assertNull(p.getAveragePrice());
      
      // Add first block.
      p.addFilledBlock(b1);
      assertEquals(1, getOpenBlocks(p).size());
      assertEquals(b1, getOpenBlocks(p).get(0));
      assertEquals(500, p.getSize());
      assertTrue(p.getAveragePrice().compareTo(new BigDecimal("80.00")) == 0);
      
      // Add second block.
      p.addFilledBlock(b2);
      assertEquals(2, getOpenBlocks(p).size());
      assertEquals(b1, getOpenBlocks(p).get(0));
      assertEquals(b2, getOpenBlocks(p).get(1));
      assertEquals(800, p.getSize());
      assertTrue(p.getAveragePrice().compareTo(new BigDecimal("68.75")) == 0);
      
      // Add third block.
      p.addFilledBlock(b3);
      assertEquals(3, getOpenBlocks(p).size());
      assertEquals(b1, getOpenBlocks(p).get(0));
      assertEquals(b2, getOpenBlocks(p).get(1));
      assertEquals(b3, getOpenBlocks(p).get(2));
      assertEquals(1000, p.getSize());
      assertTrue(p.getAveragePrice().compareTo(new BigDecimal("69")) == 0);
   }
   
   /**
    * Test adding short blocks to short position.
    * 
    * @throws IllegalAccessException
    * @throws NoSuchFieldException
    * @throws IllegalArgumentException
    */
   @Test
   public void testAddFilledBlock2()
         throws IllegalArgumentException, NoSuchFieldException, IllegalAccessException
   {
      Position p = new Position(c);
      FilledBlock b1 = new FilledBlock(Direction.SHORT, 500, new BigDecimal(80), new DateTime(100));
      FilledBlock b2 = new FilledBlock(Direction.SHORT, 300, new BigDecimal(50), new DateTime(200));
      FilledBlock b3 = new FilledBlock(Direction.SHORT, 200, new BigDecimal(70), new DateTime(300));
      
      // Before first block.
      assertEquals(0, getOpenBlocks(p).size());
      assertEquals(0, p.getSize());
      assertNull(p.getAveragePrice());
      
      // Add first block.
      p.addFilledBlock(b1);
      assertEquals(1, getOpenBlocks(p).size());
      assertEquals(b1, getOpenBlocks(p).get(0));
      assertEquals(-500, p.getSize());
      assertTrue(p.getAveragePrice().compareTo(new BigDecimal("80.00")) == 0);
      
      // Add second block.
      p.addFilledBlock(b2);
      assertEquals(2, getOpenBlocks(p).size());
      assertEquals(b1, getOpenBlocks(p).get(0));
      assertEquals(b2, getOpenBlocks(p).get(1));
      assertEquals(-800, p.getSize());
      assertTrue(p.getAveragePrice().compareTo(new BigDecimal("68.75")) == 0);
      
      // Add third block.
      p.addFilledBlock(b3);
      assertEquals(3, getOpenBlocks(p).size());
      assertEquals(b1, getOpenBlocks(p).get(0));
      assertEquals(b2, getOpenBlocks(p).get(1));
      assertEquals(b3, getOpenBlocks(p).get(2));
      assertEquals(-1000, p.getSize());
      assertTrue(p.getAveragePrice().compareTo(new BigDecimal("69")) == 0);
   }
   
   /**
    * Test adding short blocks to long position.
    * 
    * @throws IllegalAccessException
    * @throws NoSuchFieldException
    * @throws IllegalArgumentException
    */
   @Test
   public void testAddFilledBlock3()
         throws IllegalArgumentException, NoSuchFieldException, IllegalAccessException
   {
      Position p = new Position(c);
      FilledBlock bl = new FilledBlock(Direction.LONG, 500, new BigDecimal(80), new DateTime(100));
      FilledBlock b1 = new FilledBlock(Direction.SHORT, 300, new BigDecimal(50), new DateTime(200));
      FilledBlock b2 = new FilledBlock(Direction.SHORT, 400, new BigDecimal(70), new DateTime(300));
      FilledBlock b3 = new FilledBlock(Direction.SHORT, 800, new BigDecimal(55), new DateTime(300));
      
      // Add long block.
      p.addFilledBlock(bl);
      assertEquals(1, getOpenBlocks(p).size());
      assertEquals(bl, getOpenBlocks(p).get(0));
      assertEquals(500, p.getSize());
      assertTrue(p.getAveragePrice().compareTo(new BigDecimal("80.00")) == 0);
      
      // Add first short block.
      p.addFilledBlock(b1);
      assertEquals(1, getOpenBlocks(p).size());
      assertEquals(200, p.getSize());
      assertTrue(p.getAveragePrice().compareTo(new BigDecimal("80.00")) == 0);
      
      // Add second short block.
      p.addFilledBlock(b2);
      assertEquals(1, getOpenBlocks(p).size());
      assertEquals(-200, p.getSize());
      assertTrue(p.getAveragePrice().compareTo(new BigDecimal("70.00")) == 0);
      
      // Add third short block.
      p.addFilledBlock(b3);
      assertEquals(2, getOpenBlocks(p).size());
      assertEquals(-1000, p.getSize());
      assertTrue(p.getAveragePrice().compareTo(new BigDecimal("58.00")) == 0);
      
      // ---------
      // Close long position
      Position p2 = new Position(c);
      FilledBlock bo = new FilledBlock(Direction.LONG, 500, new BigDecimal(80), new DateTime(100));
      FilledBlock bc = new FilledBlock(Direction.SHORT, 500, new BigDecimal(50), new DateTime(200));
      p2.addFilledBlock(bo);
      p2.addFilledBlock(bc);
      assertEquals(0, getOpenBlocks(p2).size());
      assertEquals(0, p2.getSize());
      assertNull(p2.getAveragePrice());
      
      // Add first short block.
      p2.addFilledBlock(b1);
      assertEquals(1, getOpenBlocks(p2).size());
      assertEquals(-300, p2.getSize());
      assertTrue(p2.getAveragePrice().compareTo(new BigDecimal("50.00")) == 0);
   }
   
   /**
    * Test adding long blocks to short position.
    * 
    * @throws IllegalAccessException
    * @throws NoSuchFieldException
    * @throws IllegalArgumentException
    */
   @Test
   public void testAddFilledBlock4()
         throws IllegalArgumentException, NoSuchFieldException, IllegalAccessException
   {
      Position p = new Position(c);
      FilledBlock bl = new FilledBlock(Direction.SHORT, 500, new BigDecimal(80), new DateTime(100));
      FilledBlock b1 = new FilledBlock(Direction.LONG, 300, new BigDecimal(50), new DateTime(200));
      FilledBlock b2 = new FilledBlock(Direction.LONG, 400, new BigDecimal(70), new DateTime(300));
      FilledBlock b3 = new FilledBlock(Direction.LONG, 800, new BigDecimal(55), new DateTime(300));
      
      // Add long block.
      p.addFilledBlock(bl);
      assertEquals(1, getOpenBlocks(p).size());
      assertEquals(bl, getOpenBlocks(p).get(0));
      assertEquals(-500, p.getSize());
      assertTrue(p.getAveragePrice().compareTo(new BigDecimal("80.00")) == 0);
      
      // Add first short block.
      p.addFilledBlock(b1);
      assertEquals(1, getOpenBlocks(p).size());
      assertEquals(-200, p.getSize());
      assertTrue(p.getAveragePrice().compareTo(new BigDecimal("80.00")) == 0);
      
      // Add second short block.
      p.addFilledBlock(b2);
      assertEquals(1, getOpenBlocks(p).size());
      assertEquals(200, p.getSize());
      assertTrue(p.getAveragePrice().compareTo(new BigDecimal("70.00")) == 0);
      
      // Add third short block.
      p.addFilledBlock(b3);
      assertEquals(2, getOpenBlocks(p).size());
      assertEquals(1000, p.getSize());
      assertTrue(p.getAveragePrice().compareTo(new BigDecimal("58.00")) == 0);
      
      // ---------
      // Close long position
      Position p2 = new Position(c);
      FilledBlock bo = new FilledBlock(Direction.SHORT, 500, new BigDecimal(80), new DateTime(100));
      FilledBlock bc = new FilledBlock(Direction.LONG, 500, new BigDecimal(50), new DateTime(200));
      p2.addFilledBlock(bo);
      p2.addFilledBlock(bc);
      assertEquals(0, getOpenBlocks(p2).size());
      assertEquals(0, p2.getSize());
      assertNull(p2.getAveragePrice());
      
      // Add first short block.
      p2.addFilledBlock(b1);
      assertEquals(1, getOpenBlocks(p2).size());
      assertEquals(300, p2.getSize());
      assertTrue(p2.getAveragePrice().compareTo(new BigDecimal("50.00")) == 0);
   }
   
   @SuppressWarnings("unchecked")
   private static List<FilledBlock> getOpenBlocks(Position position)
         throws IllegalArgumentException, NoSuchFieldException, IllegalAccessException
   {
      return (List<FilledBlock>)Reflection.getField("openBlocks", position);
   }
}
