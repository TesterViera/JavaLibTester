package tester;

/**
 * @(#)ErrorReport 
 * 
 * Intentionally the name does not include <code>Exception</code>
 * so that the reported trace would not contain a link to this class.
 * 
 * @since 8 February 2008
 * @author Viera K. Proulx
 */

/**
 * Copyright 2008 Viera K. Proulx
 * This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)
 */

/**
 * Exception to be raised when a test case fails, so that we can record and
 * report the stack trace at that point.
 */
public class ErrorReport extends RuntimeException {

	private static final long serialVersionUID = 5935282584833474005L;

	/**
	 * Constructs a new exception with null as its detail message.
	 */
	public ErrorReport() {
		super();
	}

	/**
	 * Constructs a new exception with the specified detail message.
	 * 
	 * @param message
	 *            the error message
	 */
	public ErrorReport(String message) {
		super(message);
	}

	/**
	 * Constructs a new exception with the specified detail message and cause.
	 * 
	 * @param message
	 *            the error message
	 * @param cause
	 *            the cause for the message
	 */
	public ErrorReport(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new exception with the specified cause and a detail message.
	 * 
	 * @param cause
	 *            the cause for the message
	 */
	public ErrorReport(Throwable cause) {
		super(cause);
	}
}
