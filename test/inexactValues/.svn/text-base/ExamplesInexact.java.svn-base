package inexactValues;

import java.util.Arrays;
import java.util.List;

import tester.Tester;

/**
 * <P>Test suite for handling inexact comparison for <code>double</code>,
 * <code>Double</code>, <code>float</code>, <code>Float</code> values and
 * for classes that contain such inexact values. </P>
 * <p/>
 * <P>The comparison of two objects that contain a field with the values
 * of the types <code>double</code> or <code>float</code> (or their wrapper
 * classes) must invoke the appropriate
 * <CODE>{@link tester.Tester#checkInexact(Object, Object, double, String) Inexact}</CODE>
 * variant of the test method, and must provide the desired relative
 * <CODE>TOLERANCE</CODE> to determine the desired accuracy of the comparison.</P>
 * <p/>
 * <P>Inexact numbers (<code>double</code>, <code>Double</code>, <code>float</code>,
 * <code>Float</code>) are considered the same if their values <CODE>val1</CODE>
 * and <CODE>val2</CODE> satisfy the formula:
 * <CODE>(Math.abs(val1 - val2) / (Math.max (Math.abs(val1), Math.abs(val2)))) < TOLERANCE</CODE></P>
 * <p/>
 * <P>However, if either <CODE>val1</CODE> or <CODE>val2</CODE> is exact zero,
 * the test checks that the absolute value of the other data is less that
 * <CODE>TOLERANCE</CODE>. Otherwise, the test would always fail.</P>
 * <p/>
 * <P>If a comparison between two objects involves inexact comparison
 * of any two fields, the test case report, whether successful or not,
 * includes a warning that an inexact comparison has been involved in
 * evaluating this test case.</P>
 * <p/>
 * <P><B>Note:</B> It is important to note that a comparison of two numbers
 * of the types <CODE>double</CODE> or <CODE>float</CODE> could succeed
 * through direct comparison. For example <CODE>new Double(5.0) == 5.0</CODE>
 * and in that case the comparison succeeds and no warning of inexact comparison
 * is issued.</P>
 * <p/>
 * <P>If the comparison of any two objects involves inexact comparison, and
 * the test method required exact comparison, (the programmer failed to use the
 * <CODE>{@link tester.Tester#checkInexact(Object, Object, double, String) Inexact} </CODE>
 * variant) the test case fails and the warning is displayed as well.</P>
 *
 * @author Virag Shah
 * @since 21 February 2011
 * 
 */
public class ExamplesInexact {
	/**
	 * Perform inexact comparison for <code>Double</code> values.
	 *
	 * @param t the <CODE>{@link tester.Tester Tester}</CODE> that performs the tests
	 */
	public void testDouble(Tester t) {
		// construct instances of primitive double type
		double d1a = 10.0 / 3.0;
		double d1b = 100.0 / 30.0; // the same as d1a
		double d2a = 3.333;        // roughly the same as d1a
		double d0 = 0.0;           // zero-valued argument
		double d0001 = 0.0001;

		// compare the same double values via exact comparison procedure
		t.checkExpect(d1a, d1b, "Success: t.checkExpect(d1a, d1b)");

		// will fail if two roughly equivalent numbers are compared via exact comparison procedure
		t.checkFail(d1a, d2a, "Test to fail: t.checkFail(d1a, d2a)");

		// compare the same double values via inexact comparison procedure
		t.checkInexact(d1a, d1b, 0.001, "Success: t.checkInexact(d1a, d1b, 0.001)");

		// will succeed if two roughly equivalent numbers are compared via inexact comparison procedure
		// provided that the tolerance level is big enough
		t.checkInexact(d1a, d2a, 0.001, "Success: t.checkInexact(d1a, d2a, 0.001)");

		// will fail if two roughly equivalent numbers are compared via inexact comparison procedure
		// and the tolerance level is not big enough
		t.checkInexact(d1a, d2a, 0.0001, "Should fail: t.checkInexact(d1a, d2a, 0.0001)");

		// assert that the method fails (another variation on the previous example)
		t.checkInexactFail(d1a, d2a, 0.0001, "Test to fail: t.checkInexactFail(d1a, d2a, 0.0001)");

		// when either of the arguments is 0.0, the other argument's absolute value has to be less than
		// the threshold to succeed
		t.checkInexact(d0, d0001, 0.00011, "Success: t.checkInexact(d0, d0001, 0.00011)");

		// when either of the arguments is 0.0, the other argument's absolute value has to be less than
		// the threshold to succeed
		t.checkInexact(0.0, -0.0001, 0.00011, "Success: t.checkInexact(0.0, -0.0001, 0.00011)");

		// will fail since the first arguments is 0.0, and the second is greater than the threshold
		t.checkInexactFail(d0, d0001, 0.000099, "Test to fail: t.checkInexactFail(d0, d0001, 0.000099)");

		// will fail since the provided tolerance level was less than 0
		t.checkInexactFail(d0, d0001, -0.00011, "Test to fail: t.checkInexactFail(d0, d0001, -0.00011)");

		// make sure that two numbers with the same absolute value but with opposite signs
		// are not roughly equivalent (provided that the tolerance is small enough)
		t.checkInexact(0.005, -0.005, 0.001, "Should fail: t.checkInexact(0.005, -0.005, 0.001)");

		// comparing two immediate double values
		t.checkInexact(123000.0, 128000.0, 0.1, "Success: t.checkInexact(123000.0, 128000.0, 0.1)");
	}

	/**
	 * Perform inexact comparison for <code>Float</code> values.
	 *
	 * @param t the <CODE>{@link tester.Tester Tester}</CODE> that performs the tests
	 */
	public void testFloat(Tester t) {
		// construct instances of primitive float type
		float f1a = 10.0f / 3.0f;
		float f1b = 100.0f / 30.0f; // the same as f1a
		float f2a = 3.333f;         // roughly the same as f1a
		double f0 = 0.0f;            // zero-valued argument
		double f0001 = 0.0001f;

		// compare the same float values via exact comparison procedure
		t.checkExpect(f1a, f1b, "Success: t.checkExpect(f1a, f1b)");

		// will fail if two roughly equivalent numbers are compared via exact comparison procedure
		t.checkFail(f1a, f2a, "Test to fail: t.checkFail(f1a, f2a)");

		// compare the same float values via inexact comparison procedure
		t.checkInexact(f1a, f1b, 0.001, "Success: t.checkInexact(d1a, d1b, 0.001)");

		// will succeed if two roughly equivalent numbers are compared via inexact comparison procedure
		// provided that the tolerance level is big enough
		t.checkInexact(f1a, f2a, 0.001, "Success: t.checkInexact(f1a, f2a, 0.001)");

		// will fail if two roughly equivalent numbers are compared via inexact comparison procedure
		// and the tolerance level is not big enough
		t.checkInexact(f1a, f2a, 0.0001, "Success: t.checkInexact(f1a, f2a, 0.0001)");

		// when either of the arguments is 0.0, the other argument's absolute value has to be less than
		// the threshold to succeed
		t.checkInexact(f0, f0001, 0.00011, "Success: t.checkInexact(f0, f0001, 0.00011)");

		// when either of the arguments is 0.0, the other argument's absolute value has to be less than
		// the threshold to succeed
		t.checkInexact(0.0f, -0.0001f, 0.00011, "Success: t.checkInexact(0.0f, -0.0001f, 0.00011)");

		// will fail since the first arguments is 0.0, and the second is greater than the threshold
		t.checkInexactFail(f0, f0001, 0.000099, 
		"Success: Test to fail: t.checkInexactFail(f0, f0001, 0.000099)");

		// will fail since the provided tolerance level was less than 0
		t.checkInexact(f0, f0001, -0.00011, "Should fail: t.checkInexact(f0, f0001, -0.00011)");

		// make sure that two numbers with the same absolute value but with opposite signs
		// are not roughly equivalent (provided that the tolerance is small enough)
		t.checkInexactFail(0.005f, -0.005f, 0.001, "Test to fail: t.checkInexactFail(0.005f, -0.005f, 0.001)");

		// comparing two immediate float values
		t.checkInexact(123000.0f, 128000.0f, 0.1, "Success: t.checkInexact(123000.0f, 128000.0f, 0.1)");
	}

	/**
	 * Perform comparison for <CODE>Double</CODE> values with <CODE>Float</CODE> values.
	 * All the methods should fail since the classes of both values being compared
	 * have to be the same.
	 *
	 * @param t the <CODE>{@link tester.Tester Tester}</CODE> that performs the tests
	 */
	public void testDoubleComparedToFloat(Tester t) {
		// comparing a double number with (roughly) the same float number
		// via exact comparison procedure will fail
		t.checkFail(1000d, 1000f, "Test to fail: t.checkFail(1000d, 1000f)");

		// comparing a double number with (roughly) the same float number
		// via inexact comparison procedure will fail as well
		t.checkInexactFail(1000d, 1000f, 1.0, "Test to fail: t.checkInexactFail(1000d, 1000f, 1.0)");
	}

	/**
	 * Demonstrates that
	 * <CODE>{@link tester.Tester#checkInexact(Object, Object, double, String)}</CODE>
	 * method has to fail given arguments of any of
	 * the following classes representing exact values:
	 * <CODE>Integer</CODE>, <CODE>Long</CODE>, <CODE>Short</CODE>, <CODE>Byte</CODE>,
	 * <CODE>Boolean</CODE> or <CODE>Char</CODE>.
	 * <p/>
	 * Accordingly,
	 * <CODE>{@link tester.Tester#checkInexactFail(Object, Object, double, String)}</CODE>
	 * invoked with the same arguments will succeed.
	 *
	 * @param t the <CODE>{@link tester.Tester Tester}</CODE> that performs the tests
	 */
	public void testExactPrimitiveOrWrapper(Tester t) {
		// make sure the comparison succeeds given double arguments
		t.checkInexact(123000.0, 128000.0, 0.1, "Success: t.checkInexact(123000.0, 128000.0, 0.1)");

		// check that supplying Integer arguments will fail
		t.checkInexactFail(123000, 128000, 0.1, "Test to fail: t.checkInexactFail(123000, 128000, 0.1)");

		// checkInexactFail for Integer arguments will succeed
		t.checkInexactFail(123000, 128000, 0.1, "Success: t.checkInexactFail(123000, 128000, 0.1)");

		// check that supplying Long arguments will fail
		t.checkInexactFail((long) 123000, (long) 128000, 0.1, 
		"Test to fail: t.checkInexactFail((long) 123000, (long) 128000, 0.1)");

		// checkInexactFail for Long arguments will succeed
		t.checkInexactFail((long) 123000, (long) 128000, 0.1, 
		"Test to fail: t.checkInexactFail((long) 123000, (long) 128000, 0.1)");

		// check that supplying Short arguments will fail
		t.checkInexactFail((short) 12300, (short) 12800, 0.1, 
		"Test to fail: t.checkInexactFail((short) 12300, (short) 12800, 0.1)");

		// checkInexactFail for Short arguments will succeed
		t.checkInexactFail((short) 12300, (short) 12800, 0.1, 
		"Test to fail: t.checkInexactFail((short) 12300, (short) 12800, 0.1)");

		// check that supplying Byte arguments will fail
		t.checkInexactFail((byte) 123, (byte) 127, 0.1, 
		"Test to fail: t.checkInexactFail((byte) 123, (byte) 127, 0.1)");

		// checkInexactFail for Short arguments will succeed
		t.checkInexactFail((byte) 123, (byte) 127, 0.1, 
		"Test to fail: t.checkInexactFail((byte) 123, (byte) 127, 0.1)");

		// check that supplying Char arguments will fail
		t.checkInexactFail('m', 'n', 0.1, "Test to fail: t.checkInexactFail('m', 'n', 0.1)");

		// checkInexactFail for Char arguments will succeed
		t.checkInexactFail('m', 'n', 0.1, "Test to fail: t.checkInexactFail('m', 'n', 0.1)");

		// check that supplying Boolean arguments will fail
		t.checkInexactFail(true, true, 0.1, "Test to fail: t.checkInexactFail(true, true, 0.1)");

		// checkInexactFail for Char arguments will succeed
		t.checkInexactFail(true, true, 0.1, "Test to fail: t.checkInexactFail(true, true, 0.1)");
	}


	// -------------------------------------------------
	// Test functions returning numerical values


	// construct 3 objects of class Location, having
	// methods to produce values of primitive double
	// and primitive int types
	Location st14av5 = new Location(14, 5);
	Location st18av8 = new Location(18, 8);
	Location st26av10 = new Location(26, 10);

	/**
	 * Test the method <CODE>{@link testertester.inexact.Location#walkDistTo walkDistTo}</CODE>
	 * in the class <CODE>{@link testertester.inexact.Location Location}</CODE>.
	 *
	 * @param t the <CODE>{@link tester.Tester Tester}</CODE> that performs the tests
	 */
	public void testWalkDistTo(Tester t) {
		t.checkExpect(
				// compute the walking distance between 2 locations (integer)
				st14av5.walkDistTo(st18av8),

				// expected value
				7,

				// test name
		"Success: Check exact testWalkDistTo");

		t.checkInexactFail(
				// compute the walking distance between 2 locations (equal to 17)
				st14av5.walkDistTo(st26av10),

				// expected value
				17.01,

				// allowed tolerance
				1,

				// test name
		"Test to fail: Check inexact testWalkDistTo");
	}

	/**
	 * Test the method <CODE>{@link testertester.inexact.Location#flyDistTo flyDistTo}</CODE>
	 * in the class <CODE>{@link testertester.inexact.Location Location}</CODE>.
	 *
	 * @param t the <CODE>{@link tester.Tester Tester}</CODE> that performs the tests
	 */
	public void testFlyDistTo(Tester t) {
		t.checkExpect(
				// compute the flying distance between 2 locations (double)
				st14av5.flyDistTo(st18av8),

				// expected value
				5.0,

				// test name
		"Success: Check exact testFlyDistTo");

		t.checkInexact(
				// compute the flying distance between 2 locations (equal to 13.0)
				st14av5.flyDistTo(st26av10),

				// expected value
				12.99,

				// allowed tolerance
				0.1,

				// test name
		"Success: Check inexact testFlyDistTo");
	}


	/**
	 * Perform inexact comparison for the instances of
	 * <CODE>{@link testertester.inexact.CartPtDouble}</CODE> class
	 * containing <code>Double</code> values.
	 *
	 * @param t the <CODE>{@link tester.Tester Tester}</CODE> that performs the tests
	 */
	public void testCartPtDouble(Tester t) {
		// create an instance of CartPtDouble class
		CartPtDouble pt = new CartPtDouble(3.0, 4.0);

		// the distance from pt to the origin is exactly 5.0
		t.checkExpect(
				pt.distTo0(),  // compute distance from pt to the origin (expected to be 5.0)
				5.0,           // expected value
				"Success: Check exact distTo0 - Double"  // test case name
		);

		// make sure 9.0 / 2.999 is not equal to 3.0
		t.checkFail(
				9.0 / 2.999,  // the first value
				3.0,          // the second value
				"Test to fail: 9.0/2.999 vs. 3.0 (should be inexact) - Double" // test case name
		);

		// exact comparison of a point having x=9.0/2.999 with a point having x=3.0 will fail
		t.checkFail(
				pt,                                  // the first point
				new CartPtDouble(9.0 / 2.999, 4.0),  // create the second point
				"Test to fail: x=9.0/2.999 vs. x=3.0 (should be inexact) - Double" // test case name
		);


		// make sure inexact comparison of 9.0/2.999 with a 3.0 will fail
		// since the tolerance is too tight
		t.checkInexactFail(
				9.0 / 2.999,  // the first value
				3.0,          // the second value
				0.000001,     // tolerance level (too tight)
		"Test to fail: 9.0/2.998 vs. 3.0 (inaccurate since tolerance = 0.000001) - Double");

		// inexact comparison of a point having x=9.0/2.999 with a point having x=3.0 will fail
		// since the tolerance is too tight
		t.checkInexactFail(
				pt,                                  // the first point
				new CartPtDouble(9.0 / 2.999, 4.0),  // create the second point
				0.000001,                            // tolerance level (too tight)
		"Test to fail: x=9.0/2.998 vs. x=3.0 (inaccurate since tolerance = 0.000001) - Double");


		// make sure inexact comparison of 9.0/2.999 with a 3.0 will succeed
		// since the tolerance is loose enough
		t.checkInexact(
				9.0 / 2.999,  // the first value
				3.0,          // the second value
				0.01,         // tolerance level (loose enough)
		"Success: 9.0/2.999 vs. 3.0 (tolerance = 0.01) - Double"); // test case name

		// inexact comparison of a point having x=9.0/2.999 with a point having x=3.0 will succeed
		// since the tolerance is loose enough
		t.checkInexact(
				pt,                                  // the first point
				new CartPtDouble(9.0 / 2.999, 4.0),  // create the second point
				0.01,                                // tolerance level (loose enough)
		"Success: x=9.0/2.998 vs. x=3.0 (tolerance = 0.01) - Double"); // test case name
	}

	/**
	 * Perform inexact comparison for the instances of
	 * <CODE>{@link testertester.inexact.CartPtPrimitiveDouble}</CODE> class
	 * containing <code>double</code> values.
	 *
	 * @param t the <CODE>{@link tester.Tester Tester}</CODE> that performs the tests
	 */
	public void testCartPtPrimitiveDouble(Tester t) {
		// create an instance of CartPtPrimitiveDouble class
		CartPtPrimitiveDouble pt = new CartPtPrimitiveDouble(3.0, 4.0);

		// the distance from pt to the origin is exactly 5.0
		t.checkExpect(
				pt.distTo0(),  // compute distance from pt to the origin (expected to be 5.0)
				5.0,           // expected value
				"Success: Check exact distTo0 - double"  // test case name
		);

		// make sure 9.0 / 2.999 is not equal to 3.0
		t.checkExpect(
				9.0 / 2.999,  // the first value
				3.0,          // the second value
		"Should fail: 9.0/2.999 vs. 3.0 (should be inexact) - double"); // test case name

		// exact comparison of a point having x=9.0/2.999 with a point having x=3.0 will fail
		t.checkFail(
				pt,                                           // the first point
				new CartPtPrimitiveDouble(9.0 / 2.999, 4.0),  // create the second point
		"Test to fail: x=9.0/2.999 vs. x=3.0 (should be inexact) - double"); // test case name


		// make sure inexact comparison of 9.0/2.999 with a 3.0 will fail
		// since the tolerance is too tight
		t.checkInexactFail(
				9.0 / 2.999,  // the first value
				3.0,          // the second value
				0.000001,     // tolerance level (too tight)
		"Test to fail: 9.0/2.998 vs. 3.0 (inaccurate since tolerance = 0.000001) - double");

		// inexact comparison of a point having x=9.0/2.999 with a point having x=3.0 will fail
		// since the tolerance is too tight
		t.checkInexactFail(
				pt,                                           // the first point
				new CartPtPrimitiveDouble(9.0 / 2.999, 4.0),  // create the second point
				0.000001,                                     // tolerance level (too tight)
		"Test to fail: x=9.0/2.998 vs. x=3.0 (inaccurate since tolerance = 0.000001) - double");


		// make sure inexact comparison of 9.0/2.999 with a 3.0 will succeed
		// since the tolerance is loose enough
		t.checkInexact(
				9.0 / 2.999,  // the first value
				3.0,          // the second value
				0.01,         // tolerance level (loose enough)
		"Success: 9.0/2.999 vs. 3.0 (tolerance = 0.01) - double"); // test case name

		// inexact comparison of a point having x=9.0/2.999 with a point having x=3.0 will succeed
		// since the tolerance is loose enough
		t.checkInexact(
				pt,                                           // the first point
				new CartPtPrimitiveDouble(9.0 / 2.999, 4.0),  // create the second point
				0.01,                                         // tolerance level (loose enough)
		"Success: x=9.0/2.998 vs. x=3.0 (tolerance = 0.01) - double"); // test case name
	}

	/**
	 * Perform inexact comparison for the instances of
	 * <CODE>{@link testertester.inexact.CartPtFloat}</CODE> class
	 * containing <code>Float</code> values.
	 *
	 * @param t the <CODE>{@link tester.Tester Tester}</CODE> that performs the tests
	 */
	public void testCartPtFloat(Tester t) {
		// create an instance of CartPtFloat class
		CartPtFloat pt = new CartPtFloat(3.0f, 4.0f);

		// the distance from pt to the origin is exactly 5.0f
		t.checkExpect(
				pt.distTo0(),                  // compute distance from pt to the origin (expected to be 5.0f)
				5.0f,                          // expected value
		"Success: Check exact distTo0 - Float");  // test case name

		// make sure 9.0f / 2.999f is not equal to 3.0f
		t.checkFail(
				9.0f / 2.999f,  // the first value
				3.0f,           // the second value
		"Test to fail: 9.0f/2.999f vs. 3.0f (should be inexact) - Float"); // test case name

		// exact comparison of a point having x=9.0f/2.999f with a point having x=3.0f will fail
		t.checkFail(
				pt,                                    // the first point
				new CartPtFloat(9.0f / 2.999f, 4.0f),  // create the second point
		"Test to fail: x=9.0f/2.999f vs. x=3.0f (should be inexact) - Float"); // test case name


		// make sure inexact comparison of 9.0f/2.999f with a 3.0f will fail
		// since the tolerance is too tight
		t.checkInexactFail(
				9.0f / 2.999f,  // the first value
				3.0f,           // the second value
				0.000001,       // tolerance level (too tight)
		"Test to fail: 9.0f/2.998f vs. 3.0f (inaccurate since tolerance = 0.000001) - Float"); // test case name   

		// inexact comparison of a point having x=9.0f/2.999f with a point having x=3.0f will fail
		// since the tolerance is too tight
		t.checkInexactFail(
				pt,                                     // the first point
				new CartPtFloat(9.0f / 2.999f, 4.0f),   // create the second point
				0.000001,                               // tolerance level (too tight)
		"Test to fail: x=9.0f/2.998f vs. x=3.0f (inaccurate since tolerance = 0.000001) - Float");

		// make sure inexact comparison of 9.0f/2.999f with a 3.0f will succeed
		// since the tolerance is loose enough
		t.checkInexact(
				9.0f / 2.999f,  // the first value
				3.0f,           // the second value
				0.01f,          // tolerance level (loose enough)
		"Success: 9.0f/2.999f vs. 3.0f (tolerance = 0.01) - Float"); // test case name
		// inexact comparison of a point having x=9.0f/2.999f with a point having x=3.0f will succeed
		// since the tolerance is loose enough
		t.checkInexact(
				pt,                                     // the first point
				new CartPtFloat(9.0f / 2.999f, 4.0f),   // create the second point
				0.01,                                   // tolerance level (loose enough)
		"Success: x=9.0f/2.998f vs. x=3.0f (tolerance = 0.01) - Float"); // test case name
	}


	/**
	 * Perform inexact comparison for the instances of
	 * <CODE>{@link testertester.inexact.CartPtFloat}</CODE> class
	 * containing <code>float</code> values.
	 *
	 * @param t the <CODE>{@link tester.Tester Tester}</CODE> that performs the tests
	 */
	public void testCartPtPrimitiveFloat(Tester t) {
		// create an instance of CartPtFloat class
		CartPtPrimitiveFloat pt = new CartPtPrimitiveFloat(3.0f, 4.0f);

		// the distance from pt to the origin is exactly 5.0f
		t.checkExpect(
				pt.distTo0(),                  // compute distance from pt to the origin (expected to be 5.0f)
				5.0f,                          // expected value
		"Success: Check exact distTo0 - float");  // test case name

		// make sure 9.0f / 2.999f is not equal to 3.0f
		t.checkFail(
				9.0f / 2.999f,  // the first value
				3.0f,           // the second value
		"Test to fail: 9.0f/2.999f vs. 3.0f (should be inexact) - float"); // test case name

		// exact comparison of a point having x=9.0f/2.999f with a point having x=3.0f will fail
		t.checkFail(
				pt,                                    // the first point
				new CartPtPrimitiveFloat(9.0f / 2.999f, 4.0f),  // create the second point
		"Test to fail: x=9.0f/2.999f vs. x=3.0f (should be inexact) - float"); // test case name

		// make sure inexact comparison of 9.0f/2.999f with a 3.0f will fail
		// since the tolerance is too tight
		t.checkInexactFail(
				9.0f / 2.999f,  // the first value
				3.0f,           // the second value
				0.000001,       // tolerance level (too tight)
		"Test to fail: 9.0f/2.998f vs. 3.0f (inaccurate since tolerance = 0.000001) - float"); // test case name

		// inexact comparison of a point having x=9.0f/2.999f with a point having x=3.0f will fail
		// since the tolerance is too tight
		t.checkInexactFail(
				pt,                                              // the first point
				new CartPtPrimitiveFloat(9.0f / 2.999f, 4.0f),   // create the second point
				0.000001,                                        // tolerance level (too tight)
				"Test to fail: x=9.0f/2.998f vs. x=3.0f (inaccurate since tolerance = 0.000001) - float" // test case name
		);

		// make sure inexact comparison of 9.0f/2.999f with a 3.0f will succeed
		// since the tolerance is loose enough
		t.checkInexact(
				9.0f / 2.999f,  // the first value
				3.0f,           // the second value
				0.01f,          // tolerance level (loose enough)
				"Success: 9.0f/2.999f vs. 3.0f (tolerance = 0.01) - float" // test case name
		);
		// inexact comparison of a point having x=9.0f/2.999f with a point having x=3.0f will succeed
		// since the tolerance is loose enough
		t.checkInexact(
				pt,                                                     // the first point
				new CartPtPrimitiveFloat(9.0f / 2.999f, 4.0f),          // create the second point
				0.01,                                                   // tolerance level (loose enough)
		"Success: x=9.0f/2.998f vs. x=3.0f (tolerance = 0.01) - float"); // test case name
	}

	// -------------------------------------------------
	// Method Invocation

	// A point in Euclidean space with coordinates (3.0, 4.0)
	CartPtDouble pt = new CartPtDouble(3.0, 4.0);

	/**
	 * An example that performs exact comparison of the distance
	 * from the <CODE>{@link testertester.inexact.CartPtDouble point}</CODE>
	 * to the origin and the expected value of the type <CODE>double</CODE>.
	 *
	 * @param t the <CODE>{@link tester.Tester Tester}</CODE> that performs the tests
	 */
	public void testMethodInvocationDistTo0(Tester t) {
		t.checkMethod(
				"Success: Check distance to origin exact - will succeed", // test name
				5.0,        // expected distance to the origin
				pt,         // the object to be tested
		"distTo0");   // the name of the method to be invoked and tested
	}


	/**
	 * An example that performs inexact comparison of the distance
	 * from the <CODE>{@link testertester.inexact.CartPtDouble point}</CODE>
	 * to another <CODE>{@link testertester.inexact.CartPtDouble point}</CODE>
	 * and the expected value of the type <CODE>double</CODE>
	 * up to the desired tolerance level.
	 *
	 * @param t the <CODE>{@link tester.Tester Tester}</CODE> that performs the tests
	 */
	public void testMethodInvocationDistTo(Tester t) {
		t.checkInexactMethod(
				"Success: Inexact check distance to - success", // test name
				4.998,     // expected distance to another point
				0.001,     // allowed tolerance
				pt,        // the source object to be tested
				"distTo",  // the name of the method to be invoked and tested
				new CartPtDouble(7.0, 7.0)); // argument: the destination object
	}


	// -------------------------------------------------
	// Iterables

	// create instances of CartPtDouble class
	CartPtDouble p1 = new CartPtDouble(3.0, 4.0);
	CartPtDouble p2 = new CartPtDouble(7.0, 7.0);
	CartPtDouble p2a = new CartPtDouble(7.0, 6.9995); // p2a is very close to p2 but not exactly
	CartPtDouble p3 = new CartPtDouble(15.0, 8.0);


	// create 3 lists of CartPtDouble elements
	List<CartPtDouble> list1 = Arrays.asList(p1, p2, p3);
	List<CartPtDouble> list2 = Arrays.asList(p1, p2a, p3); // list2 is very close to list1 but not exactly
	List<CartPtDouble> list3 = Arrays.asList(p1, p3);


	/**
	 * An example that performs exact comparison of two list.
	 * Because the 2nd elements in the lists are different, this
	 * test will fail.
	 *
	 * @param t the <CODE>{@link tester.Tester Tester}</CODE> that performs the tests
	 */
	public void testIterableExact(Tester t) {
		t.checkIterable(
				list1,  // 1st iterable
				list2,  // 2nd iterable
		"Should fail: should be checkInexactIterable"); // test name
	}

	/**
	 * An example that performs inexact comparison of two list.
	 * Because the tolerance is negative, the test will fail.
	 *
	 * @param t the <CODE>{@link tester.Tester Tester}</CODE> that performs the tests
	 */
	public void testIterableInexactNegativeTolerance(Tester t) {
		t.checkInexactIterable(
				list1,  // 1st iterable
				list2,  // 2nd iterable
				-0.1,   // tolerance
		"Should fail: tolerance < 0");  //test name
	}


	/**
	 * An example that performs inexact comparison of two list.
	 * It will pass, since the elements in the lists are equal
	 * up to the desired tolerance level.
	 *
	 * @param t the <CODE>{@link tester.Tester Tester}</CODE> that performs the tests
	 */
	public void testIterableInexactOK(Tester t) {
		t.checkInexactIterable(
				list1, // 1st iterable
				list2, // 2nd iterable
				0.01,  // tolerance
		"Success: Test Inexact Iterable");  // test name
	}


	/**
	 * An example that performs inexact comparison of two list.
	 * Because the number of elements in the first list is different
	 * from the number of elements in the second, the test will fail.
	 *
	 * @param t the <CODE>{@link tester.Tester Tester}</CODE> that performs the tests
	 */
	public void testIterableInexactDifferentSize(Tester t) {
		t.checkInexactIterable(
				list1,  // 1st iterable (3 elements)
				list3,  // 2nd iterable (2 elements)
				0.01,   // tolerance
		"Should fail: not the same lists");  // test name
	}

	/**
	 * <P>Display all data defined in the <CODE>{@link ExamplesInexact ExamplesInexact}</CODE> 
	 * class.</P>
	 * <P>Run all tests defined in the <CODE>{@link ExamplesInexact ExamplesInexact}</CODE> 
	 * class.</P>
	 */
	public static void main(String[] argv)
	{
		ExamplesInexact ei = new ExamplesInexact();

		System.out.println("Show all data defined in the ExamplesInexact class:");
		System.out.println("\n\n---------------------------------------------------");
		System.out.println("Invoke tester.runReport(this, true, true):");
		System.out.println("Print all data, all test results");

		Tester.runReport(ei, true, true);

		System.out.println("\n---------------------------------------------------");
		System.out.println("\n---------------------------------------------------");
		System.out.println("\n---------------------------------------------------");
		System.out.println("Invoke tester.runReport(this, false, false, true):");
		System.out.println("Print no data, all test results, no warnings");

		Tester.runReport(ei, false, false);
	}
}