package exceptions;

import tester.Tester;

/**
 * <P>This class provides a test suite for tracking object method exceptions.</P>
 * <P>The <I>tester</I> allows the user to test whether a method invocation by the
 * given instance and with the given arguments throws the expected exception with
 * the message the programmer expects. The tester will report every type of
 * failure - when the method fails to throw exception, when the method throws and
 * exception of a different type, when the method throws an exception of the
 * correct type, but with a wrong message. </P>
 *
 * <P> In this test suite we consider the following examples:
 *
 * <ul>1: An example that only determines whether the method invocation
 * throws the desired exception - and displays the message for this
 * exception </ul>
 *
 * <ul>2: We are checking for an exception although the code is not expected
 * to throw any - so the test should fail</ul>
 *
 * <ul>3: The code is expected to throw an exception with the given
 * message - and the test should succeed</ul>
 *
 * <ul>4: The code is expected to throw an exception of the given type
 * but with a different message - and the test should fail</ul>
 *
 * <ul>5: The code is expected to throw an exception of the type that
 * is different from type of the given exception - and the test
 * should fail</ul>
 *
 * <ul>6: The code is expected to throw an exception by invoking a
 * non-existing method - and the test should fail</ul></P>
 * 
 * @author Virag Shah
 * @since 22 February 2011
 * 
 */
public class ExamplesMethodExceptions {
	/**
	 * An example that only determines whether the method invocation
	 * throws the desired exception - and displays the message for this
	 * exception
	 *
	 * @param t the <CODE>{@link tester.Tester Tester}</CODE> that performs the tests
	 */
	public boolean testExceptions1(Tester t) {
		/** A test that throws an exception */
		try {
			new MTListTr<Object>().getFirst();

			// we don't expect to get to here since an exception should
			// have been thrown before
			return
			t.checkExpect(
					false, // condition
			"new MTListTr().getFirst() failed to throw an exception"); // test case name
		} catch (UnsupportedOperationException e) {
			//we expect to come here to catch the exception
			return
			t.checkExpect(
					true,
					"new MTListTr().getFirst() threw exception: \n" + e.getMessage());
		}
	}

	/**
	 * This code is not expected to throw an exception - and 
	 * the test should fail
	 *
	 * @param t the <CODE>{@link Tester Tester}</CODE> that performs the tests
	 */
	public boolean testExceptions2(Tester t) {
		return
		/** A test that should not throw an exception */
		t.checkException(
				// test case name (optional)
				"new MTListTr().isEmpty() \n" +
				"The test should fail: the method fails to throw an exception",

				// the expected exception
				new UnsupportedOperationException(
				"Cannot access the first element of an empty data set"),

				// the object to be tested
				new MTListTr<Object>(),

				// the name of the method to be invoked and tested
				"isEmpty",

				// the arguments for the method
				new Object[0]);
	}

	/**
	 * This code is expected to throw an exception with the given
	 * message and the test should succeed
	 *
	 * @param t the <CODE>{@link Tester Tester}</CODE> that performs the tests
	 */
	public boolean testExceptions3(Tester t) {
		return
		/** A test that throws an exception */
		t.checkException(
				// test case name (optional)
				"new MTListTr().getFirst() \n" +
				"Should be success: throws correct exception with the correct message",

				// the expected exception
				new UnsupportedOperationException("Cannot access the first element of an empty data set"),

				// the object to be tested
				new MTListTr<Object>(),

				// the name of the method to be invoked and tested
				"getFirst",

				// the arguments for the method
				new Object[0]);
	}

	/**
	 * This code is expected to throw an exception of the given type
	 * but with a different message - and the test should fail
	 *
	 * @param t the <CODE>{@link Tester Tester}</CODE> that performs the tests
	 */
	public boolean testExceptions4(Tester t) {
		return
		/** A test that throws an exception */
		t.checkException(
				// test case name (optional)
				"new MTListTr().getFirst() \n" +
				"The test should fail: we supplied a wrong message",

				// the expected exception
				new UnsupportedOperationException("Cannot access the first element of an empty data set"),

				// the object to be tested
				new MTListTr<Object>(),

				// the name of the method to be invoked and tested
				// this method should raise "Cannot advance to the rest of an empty data set"
				"getRest",

				// the arguments for the method
				new Object[0]);
	}

	/**
	 * This code is expected to throw an exception of the type that
	 * is different from type of the given exception - and the test
	 * should fail
	 *
	 * @param t the <CODE>{@link Tester Tester}</CODE> that performs the tests
	 */
	public boolean testExceptions5(Tester t) {
		return
		/** A test that throws an exception */
		t.checkException(
				// test case name (optional)
				"new MTListTr().getFirst() \n" +
				"The test should fail: we supplied a wrong exception class",

				// the expected exception (wrong)
				new IllegalArgumentException("Cannot access the first element of an empty data set"),

				// the object to be tested
				new MTListTr<Object>(),

				// the name of the method to be invoked and tested
				// it will throw an UnsupportedOperationException
				"getFirst",

				// the arguments for the method
				new Object[0]);
	}

	/**
	 * This code is expected to throw an exception by invoking a
	 * non-existing method - and the test should fail
	 *
	 * @param t the <CODE>{@link Tester Tester}</CODE> that performs the tests
	 */
	public boolean testExceptions6(Tester t) {
		return
		/** A test that throws an exception */
		t.checkException(
				// test case name (optional)
				"new MTListTr().getFirsttt() \n" +
				"The test should fail: we supplied a wrong method name",

				// the expected exception
				new IllegalArgumentException("Cannot access the first element of an empty data set"),

				// the object to be tested
				new MTListTr<Object>(),

				// the name of the method to be invoked and tested
				// it as a non-existing method
				"getFirsttt",

				// the arguments for the method
				new Object[0]);
	}
}
