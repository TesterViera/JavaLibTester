package tester;

/**
 * Copyright 2008 Viera K. Proulx
 * This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)
 */

/**
 * A visitor designed to invoke test cases that will be evaluated 
 * by the <CODE>{@link Tester Tester}</CODE> class
 * 
 * @author Viera K. Proulx
 * @since 11 February 2008
 *
 */

public interface IExamples {

  /**
   * The method implemented by the user that invokes the test cases 
   * in the <CODE>{@link Tester Tester}</CODE> class
   * 
   * @param t the <CODE>{@link Tester Tester}</CODE> object used to
   * invoke the test cases and to produce the reports
   */
  public void tests(Tester t);
}