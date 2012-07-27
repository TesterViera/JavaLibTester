package tester;


/**
 * Copyright 2007, 2008 Viera K. Proulx
 * This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)
 */

/**
 * Exception to be raised on an attempt to advance a traversal 
 * over an empty collection 
 * or to produce the first element of an empty collection.
 * 
 * @author Viera K. Proulx
 */
public class IllegalUseOfTraversalException extends RuntimeException {

  /**
	 * 
	 */
	private static final long serialVersionUID = 3990453678693279717L;

/**
   * Constructs a new exception with null as its detail message.
   */
  public IllegalUseOfTraversalException() {
    super();
  }

  /**
   * Constructs a new exception with the specified detail message.
   * 
   * @param message the error message
   */
  public IllegalUseOfTraversalException(String message) {
    super(message);
  }

  /**
   * Constructs a new exception with the specified detail message and cause.
   * 
   * @param message the error message
   * @param cause the cause for the message
   */
  public IllegalUseOfTraversalException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructs a new exception with the specified cause and a detail message.
   * 
   * @param cause the cause for the message
   */
  public IllegalUseOfTraversalException(Throwable cause) {
    super(cause);
  }

}
