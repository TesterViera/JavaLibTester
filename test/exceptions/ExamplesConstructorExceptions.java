package exceptions;

import tester.Tester;

/**
 * <P> This class provides a test suite for tracking object constructor exceptions.</P>
 * <P> The <I>tester</I> also allows the user to test whether a constructor invocation
 * for the given class and with the given arguments throws the expected exception with
 * the message the programmer expects. The tester will report every type of failure -
 *  when the constructor fails to throw exception, when the constructor throws and
 * exception of a different type, when the constructor throws an exception of the
 * correct type, but with a wrong message. </P>
 *
 * <P> In this test suite we consider the following examples:
 *
 * <li>Assert no exception is thrown when constructing objects
 * of class <CODE>{@link Date Date}</CODE> with correct
 * parameters</li>
 *
 * <li>Assert an <CODE>{@link IllegalArgumentException IllegalArgumentException}</CODE>
 * exception is thrown when constructing an object of class
 * <CODE>{@link Date Date}</CODE> with illegal parameters</li>
 *
 * <li> Make sure there is a constructor exception
 * if we provide invalid String representation of a month
 * for a given month-to-number <CODE>{@link IMonth2Number Converter}</CODE> </li>
 *
 * <li>Check exception for a year beyond the allowed range</li>

 * <li>Check exception for an invalid month</li>

 * <li>Check exception for an invalid day</li>

 * <li>Check that <CODE>{@link tester.Tester#checkConstructorException
 * checkConstructorException}</CODE> will fail since all parameters are correct</li>

 * <li>Check that <CODE>{@link tester.Tester#checkConstructorException checkConstructorException}
 * </CODE> will fail since we supplied a wrong exception class</li></P>
 * 
 * @author Virag Shah
 * @since 22 February 2011
 * 
 */
public class ExamplesConstructorExceptions {
	/**
	 * <P> Define error message for <I>Invalid Year</I> exception </P>
	 */
	String MSG_INVALID_YEAR_IN_DATE = "Invalid year in Date";

	/**
	 * <P> Define error message for <I>Invalid Month</I> exception </P>
	 */
	String MSG_INVALID_MONTH_IN_DATE = "Invalid month in Date";

	/**
	 * <P> Define error message for <I>Invalid Day</I> exception </P>
	 */
	String MSG_INVALID_DAY_IN_DATE = "Invalid day in Date";


	/**
	 * <P> Assert no exception is thrown when constructing objects
	 * of class <CODE>{@link Date Date}</CODE> with correct
	 * parameters</P>
	 *
	 * @param t the <CODE>{@link tester.Tester Tester}</CODE> that performs the tests
	 */
	boolean testNoConstructorExceptionThrown(Tester t) {
		// by default there's no exception
		boolean caught = false;

		try {
			//create several dates providing correct parameters to constructors
			Date d20100228 = new Date(2010, 2, 28);         // February 28, 2010
			Date d20091012 = new Date(2009, 10, 12);        // Oct 12, 2009

			// create an instance of a class that converts the
			// short String representation of a month into a number
			IMonth2Number ms2n = new MonthShort2Number();

			// create an instance of a class that converts the
			// long String representation of a month into a number
			IMonth2Number ml2n = new MonthLong2Number();

			// make sure that the constructor taking an instance of
			// IMonth2Number interface also works fine
			Date d20091012a = new Date(2009, "Oct", 12, ms2n);    // Oct 12, 2009
			Date d20091012b = new Date(2009, "October", 12, ml2n);    // Oct 12, 2009

			// test constructor which allows to omit the year
			Date d20201012a = new Date("Oct", 12, ms2n);          // Oct 12, 2010
		} catch (IllegalArgumentException exc) {
			// if everything goes right
			// we will never get here
			caught = true;
		}

		// we don't expect any exception to have been caught
		return t.checkExpect(
				!caught, // condition
		"Should be success: no constructor exception thrown"); // test name
	}

	/**
	 * <P> Assert an <CODE>{@link IllegalArgumentException IllegalArgumentException}</CODE> exception
	 * is thrown when constructing an object of class
	 * <CODE>{@link Date Date}</CODE> with illegal
	 * parameters.</P>
	 *
	 * @param t the <CODE>{@link tester.Tester Tester}</CODE> that performs the tests
	 */
	boolean testConstructorExceptionIsCaught(Tester t) {
		boolean caught = false;
		try {
			// trying to create a bad date
			// which results in raising a constructor exception
			Date b34453323 = new Date(3445, 33, 23);
		} catch (IllegalArgumentException exc) {
			// we expect to get here
			String message = exc.getMessage();

			// check the exception is due to invalid date
			if ((message.equals(MSG_INVALID_YEAR_IN_DATE)) ||
					(message.equals(MSG_INVALID_MONTH_IN_DATE)) ||
					(message.equals(MSG_INVALID_DAY_IN_DATE)))
				caught = true;
		}

		// assert caught = true
		return t.checkExpect(
				caught, // condition
		"Should be success: constructor exception is caught"); // test name
	}

	/**
	 * Make sure there is a constructor exception
	 * if we provide invalid String representation of a month
	 * for a given month-to-number <CODE>{@link IMonth2Number Converter}</CODE>
	 *
	 * @param t the <CODE>{@link tester.Tester Tester}</CODE> that performs the tests
	 */
	boolean testConstructorException1(Tester t) {
		// create an instance of a class that converts the
		// short String representation of a month into a number
		IMonth2Number ms2n = new MonthShort2Number();

		return
		t.checkConstructorException(
				// test case name (optional)
				"Should be success: invalid month for IMonth2Number",

				// the expected exception
				new IllegalArgumentException(MSG_INVALID_MONTH_IN_DATE),

				// the name of the constructor to be invoked and tested
				"exceptions.Date",

				// the arguments for the constructor:
				2000,    // the year
				"Fbb",   // the month (incorrect)
				12,      // the day
				ms2n);     // month-to-number converter instance
	}

	/**
	 * Make sure there is a constructor exception
	 * if we provide invalid String representation of a month
	 * for a given month-to-number <CODE>{@link IMonth2Number Converter}</CODE>
	 *
	 * @param t the <CODE>{@link tester.Tester Tester}</CODE> that performs the tests
	 */
	boolean testConstructorException2(Tester t) {
		// create an instance of a class that converts the
		// long String representation of a month into a number
		IMonth2Number ml2n = new MonthLong2Number();

		return
		t.checkConstructorException(
				// test case name (optional)
				"Should be success: invalid month for IMonth2Number",

				// the expected exception
				new IllegalArgumentException(MSG_INVALID_MONTH_IN_DATE),

				// the name of the constructor to be invoked and tested
				"exceptions.Date",

				// the arguments for the constructor:
				2000,     // the year
				"Feb",    // the month (incorrect since long representation is expected)
				12,       // the day
				ml2n);      // month-to-number converter instance
	}

	/**
	 * Check exception for a year beyond the allowed range
	 *
	 * @param t the <CODE>{@link tester.Tester Tester}</CODE> that performs the tests
	 */
	boolean testConstructorException3(Tester t) {
		return
		t.checkConstructorException(
				// test case name (optional)
				"Should be success: year beyond the allowed range",

				// the expected exception
				new IllegalArgumentException(MSG_INVALID_YEAR_IN_DATE),

				// the name of the constructor to be invoked and tested
				"exceptions.Date",

				// the arguments for the constructor:
				4000,    // the year (beyond the allowed range)
				10,      // the month
				12);       // the day
	}

	/**
	 * Check exception for an invalid month
	 *
	 * @param t the <CODE>{@link tester.Tester Tester}</CODE> that performs the tests
	 */
	boolean testConstructorException4(Tester t) {
		return
		t.checkConstructorException(
				// test case name (optional)
				"Should be success: invalid month",

				// the expected exception
				new IllegalArgumentException(MSG_INVALID_MONTH_IN_DATE),

				// the name of the constructor to be invoked and tested
				"exceptions.Date",

				// the arguments for the constructor:
				2000,    // the year
				15,      // the month (invalid)
				12);      // the day
	}

	/**
	 * Check exception for an invalid day
	 *
	 * @param t the <CODE>{@link tester.Tester Tester}</CODE> that performs the tests
	 */
	boolean testConstructorException5(Tester t) {
		return
		t.checkConstructorException(
				// test case name (optional)
				"Should be success: invalid day",

				// the expected exception
				new IllegalArgumentException(MSG_INVALID_DAY_IN_DATE),

				// the name of the constructor to be invoked and tested
				"exceptions.Date",

				// the arguments for the constructor:
				2000,    // the year
				10,      // the month
				42);       // the day (invalid)
	}

	/**
	 * Check exception for an invalid day
	 *
	 * @param t the <CODE>{@link tester.Tester Tester}</CODE> that performs the tests
	 */
	boolean testConstructorException6(Tester t) {
		// create an instance of a class that converts the
		// short String representation of a month into a number
		IMonth2Number ms2n = new MonthShort2Number();

		return
		t.checkConstructorException(
				// test case name (optional)
				"Should be success: invalid day - 2",

				// the expected exception
				new IllegalArgumentException(MSG_INVALID_DAY_IN_DATE),

				// the name of the constructor to be invoked and tested
				"exceptions.Date",

				// the arguments for the constructor:
				"Oct",    // the months (short String representation)
				42,       // the day (invalid)
				ms2n);   // month-to-number converter instance
	}

	/**
	 * This method will fail since all parameters are correct
	 *
	 * @param t the <CODE>{@link tester.Tester Tester}</CODE> that performs the tests
	 */
	boolean testConstructorException7(Tester t) {
		// create an instance of a class that converts the
		// short String representation of a month into a number
		IMonth2Number ms2n = new MonthShort2Number();

		return
		t.checkConstructorException(
				// test case name (optional)
				"The test should fail: Correct params supplied",

				// the expected exception
				new IllegalArgumentException(MSG_INVALID_MONTH_IN_DATE),

				// the name of the constructor to be invoked and tested
				"exceptions.Date",

				// the arguments for the constructor:
				2000,    // the year
				"Feb",   // the months (short String representation)
				12,      // the day
				ms2n);   // month-to-number converter instance
	}

	/**
	 * This method will fail since we supplied a wrong exception class
	 *
	 * @param t the <CODE>{@link tester.Tester Tester}</CODE> that performs the tests
	 */
	boolean testConstructorException8(Tester t) {
		return
		t.checkConstructorException(
				// test case name (optional)
				"The test should fail: we supplied a wrong exception class",

				// the expected exception (wrong)
				new IllegalArgumentException(MSG_INVALID_DAY_IN_DATE),

				// the name of the constructor to be invoked and tested
				"exceptions.Date",

				// the arguments for the constructor:
				2000,    // the year
				99,      // the month (incorrect)
				12);     // the day
	}
}