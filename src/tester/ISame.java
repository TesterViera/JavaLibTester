package tester;

/**
 * Copyright 2007 Viera K. Proulx
 * This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)
 */

/**
 * An interface to represent a method that compares two objects 
 * for user-defined equality.
 * 
 * @author Viera K. Proulx
 * @since 30 May 2007
 */
public interface ISame<T>{
  
  /**
   * Is this object the same as that?
   * @param that object
   * @return true is the two objects are the same (by our definition)
   */
  public boolean same(T that);
}