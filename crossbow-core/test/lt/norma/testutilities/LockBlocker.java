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

package lt.norma.testutilities;

/**
 * Intentionally blocks specified lock.
 * 
 * @author Vilius Normantas <code@norma.lt>
 */
public class LockBlocker extends Thread
{
   private final Object lock;
   private final long timeout;
   
   /**
    * Constructor.
    * 
    * @param lock
    *           a lock to be blocked in this thread
    * @param timeout
    *           specifies time in milliseconds, for how long the lock will be held blocked
    */
   public LockBlocker(Object lock, long timeout)
   {
      this.lock = lock;
      this.timeout = timeout;
   }
   
   @Override
   public void run()
   {
      synchronized (lock)
      {
         try
         {
            Thread.sleep(timeout);
         }
         catch (InterruptedException exception)
         {
            System.err.println("Thread was interrupted.");
         }
      }
   }
}
