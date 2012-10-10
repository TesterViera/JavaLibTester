package tester;

//import java.lang.annotation.Annotation;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import org.apache.log4j.Logger;

/**
 * Copyright 2007, 2008, 2009 2010, 2011 Viera K. Proulx, Weston Josey
 * This program is distributed under the terms of the
 * GNU Lesser General Public License (LGPL)
 */

/**
 * <P>
 * A test harness that compares arbitrary objects for extensional equality.
 * </P>
 * <P>It catches exceptions in tests - the remaining tests are omitted.</P>
 *
 * <P>It uses the visitor pattern to accept test cases.</P>
 *
 * <P>Displays the data even if no tests are present.</P>
 *
 * @author Viera K. Proulx, Weston Jossey, Shayne Caswell, Sachin Venugopalan
 *         Virag Shah
 * @since 21 March 2008, 11 June 2008, 24 June 2008, 16 October 2008, 11
 *        November 2008, 23 November 2008, 12 December 2008, 20 January 2009,
 *        18 May 2009, 19 October 2009, 10 November 2009, 16 November 2009,
 *        5 February 2010, 15 March 2010, 12 May 2010, 5 October 2010,
 *        18 February 2011, 5 July 2011, 12 January 2012, 17 June 2012,
 *        20 September 2012
 */
public class Tester {
	// private static final Logger logger = Logger.getLogger(Tester.class);

	/** A <code>String</code> that reports the current tester version */	
	private static String version;

	/** A <code>String</code> that records the results for all failed tests */
	protected StringBuilder failedResults = new StringBuilder(
			"Failed test results: \n--------------\n");

	/** A <code>String</code> that records all test results */
	protected StringBuilder fullTestResults = new StringBuilder(
			"Full test results: " + "\n-------------------\n");

	/** the total number of tests */
	protected int numberOfTests;

	/** the total number of errors */
	protected int errors;

	/** the total number of warnings of inexact comparisons */
	protected int warnings;

	/** the name of the current test */
	protected String testname;

	/** An instance of the Inspector to use throughout */
	protected Inspector inspector = new Inspector();

	/** start with no tests and no failures */
	public Tester() {
		Inspector.TOLERANCE = 0.001;
		this.numberOfTests = 0;
		this.errors = 0;
		this.warnings = 0;
		this.testname = "";
	}

	/**
	 * <P>Read the current version of tester.
	 * 
	 * <P>This static block code basically reads the current version of tester being used.
	 * Since the tester library is build using the maven script it has a META-INF folder 
	 * which has the pom.xml file with the tester version number. So the code tries to read 
	 * the version from that file. 
	 * 
	 * <P>If the tester library is being used by someone working on the library then it tries to 
	 * read the version number from the pom.xml file part of the library project.
	 * 
	 * It updates the 'version' instance variable with the version number read.
	 * 
	 * If it fails to read the version number from the pom.xml file then it simply prints the 
	 * hardcoded version.
	 */
	static {
		try {
			// Read it from the pom.xml file int META-INF folder from the tester library.
			InputStream inputStream = Tester.class.getClassLoader().getResourceAsStream("META-INF/maven/ccs.neu.edu/tester/pom.xml");
			BufferedReader br;

			// If fails, read it from the pom.xml part of the project
			if(inputStream == null)
				br = new BufferedReader(new FileReader(new File("pom.xml")));
			else
				br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

			String line;
			StringBuilder sb = new StringBuilder();

			while((line = br.readLine()) != null) {
				sb.append(line.trim());
			}

			// Parse the xml and find the version number.
			Pattern p = Pattern.compile("<version>(.*?)</version>");
			Matcher m = p.matcher(sb);
			m.find();

			version = "Tester Prima v." + m.group(1) + "\n-----------------------------------";

		} catch(Exception e) {
			// If fails to read from pom.xml file, simply print the hardcoded version.
			System.out.println("** Failed to read tester version. **");
			version = "Tester Prima v.\n-----------------------------------";
		}
	}

	/*--------------------------------------------------------------------*/
	/*------------- TEST SELECTION AND REPORTING SECTION -----------------*/
	/*--------------------------------------------------------------------*/
	/**
	 * <p>Find all test methods in the class of the given object; run tests
	 * and report results.</p>
	 * <p>
	 * If the class extends the <code>IExamples</code> all tests within the
	 * <code>void tests(Tester t)</code> method are performed.
	 * Otherwise we locate all methods
	 * with names that start with <code>test</code> and consume one parameter
	 * of the type <code>Tester</code> and perform the desired tests.
	 * </p>
	 *
	 * <p>Report only the results of failed tests.</p>
	 *
	 * @param f the instance of a class that defines all tests and the data
	 *          for them
	 */
	protected void runAnyTests(Object f) {
		runAnyTests(f, false);
	}

	/**
	 * <p>Find all test methods in the class of the given object; run tests
	 * and report results.</p>
	 * <p>
	 * If the class extends the <code>IExamples</code> all tests within the
	 * <code>void tests(Tester t)</code> method are performed.
	 * Otherwise we locate all methods
	 * with names that start with <code>test</code> and consume one parameter
	 * of the type <code>Tester</code> and perform the desired tests.
	 * </p>
	 *
	 * <p>Report test results --- failed or all as specified.</p>
	 *
	 * @param f the instance of a class that defines all tests and the data
	 *          for them
	 * @param full <code>true</code> if full test report is desired
	 */
	protected void runAnyTests(Object f, boolean full) {
		runAnyTests(f, full, false);
	}

	/**
	 * <p>Find all test methods in the class of the given object; run tests
	 * and report results.</p>
	 * <p>
	 * If the class extends the <code>IExamples</code> all tests within the
	 * <code>void tests(Tester t)</code> method are performed.
	 * Otherwise we locate all methods
	 * with names that start with <code>test</code> and consume one parameter
	 * of the type <code>Tester</code> and perform the desired tests.
	 * </p>
	 *
	 * <p>Report test results --- failed or all as specified.</p>
	 * <p>Print all data in the class that defines the tests - as specified.</p>
	 *
	 * @param f the instance of a class that defines all tests and the data
	 *          for them
	 * @param full true if full test report is desired
	 * @param printall <code>true</code> if <code>Examples</code> class data is to be printed
	 */
	protected void runAnyTests(Object f, boolean full, boolean printall) {
		this.numberOfTests = 0; // number of tests run
		boolean failed = false; // any tests failed?

		System.out.println(version);
		// print the name of the 'Examples' class
		System.out.println("Tests defined in the class: " + f.getClass().getName() +
				":\n---------------------------");

		if (printall) {
			// pretty-print the 'Examples' class data when desired
			System.out.println(f.getClass().getName() + ":\n---------------");
			System.out.println(Printer.produceString(f) + "\n---------------");
		}

		// check if the 'Examples' class extends 'IExamples'
		// if yes, run its 'tests' method
		Class<?>[] interfaces = f.getClass().getInterfaces();
		boolean foundInterface = false;

		for (Class<?> c : interfaces) {
			if (c.getName().equals("tester.IExamples")) {
				runTests((IExamples) f, full, printall);
				foundInterface = true;
			}
		}

		// otherwise use reflection to find all test methods
		if (!foundInterface) {

			// find all methods that start with the String 'test...'
			// and consume one argument of the type Tester
			// Never used- Wes Jossey
			// Class<?>[] classparams = new Class[] { this.getClass() };

			ArrayList<Method> testMethods = this.findTestMethods(f,
					"Your class does not define any method with the header\n"
							+ " boolean test...(Tester t)");

			// make sure there are tests to run
			if (!(testMethods == null)) {
				// invoke every method that starts with 'test'
				// and accepts one Tester argument (ignore the resulting
				// boolean)
				Object[] args = new Object[] { this };

				try {
					for (Method testMethod : testMethods) {
						if (testMethod != null) {
							testMethod.invoke(f, args);
						}
					}
				}

				// catch all exceptions
				catch (Throwable e) {
					this.errors = this.errors + 1;
					this.numberOfTests = this.numberOfTests + 1;
					System.out.println("Threw exception during test "
							+ this.numberOfTests);
					e.printStackTrace();
					failed = true;
				}

				// print the test results at the end
				finally {
					if (full)
						this.fullTestReport();
					else
						this.testReport();
					done(failed);
				}
			}
			else
				System.out.println("No test methods found.");
		}
	}

	/**
	 * <P>Run the tests, accept the class to be tested as a visitor</P>
	 * <P>An alternative method for running all tests.</p>
	 *
	 * @param f   the class to be tested -- it defines the method
	 *            <code>void tests(Tester t)</code> that invokes all test
	 *            cases.
	 * @param full <code>true</code> if full test report is desired
	 */
	protected void runTests(IExamples f, boolean full, boolean printall) {
		this.numberOfTests = 0;
		boolean failed = false;

		if (printall){
			System.out.println("Examples class:\n---------------");
			System.out.println(Printer.produceString(f) + "\n---------------");
		}

		try {
			f.tests(this);
		} catch (Throwable e) { // catch all exceptions
			this.errors = this.errors + 1;
			this.numberOfTests = this.numberOfTests + 1;
			System.out.println("Threw exception during test "
					+ this.numberOfTests);
			e.printStackTrace();
			failed = true;
		} finally {
			if (full)
				this.fullTestReport();
			else
				this.testReport();
			done(failed);
		}
	}

	/**
	 * If the test evaluation terminated due to an exception, report the test
	 * that threw the exception..
	 */
	protected void done(boolean failed) {
		if (failed) {
			this.reportErrors(this.testname, "caused RuntimeException");
			System.runFinalization();
		}

	}

	/*--------------------------------------------------------------------*/
	/*------------- TEST EVALUATION SECTION: Public API ------------------*/
	/*--------------------------------------------------------------------*/

	/*--------------- Tolerance for comparing inexact numbers  -----------*/

	/**
	 * Set the relative tolerance for the comparison of inexact numbers
	 *
	 * @param epsilon the desired tolerance
	 */
	protected boolean setTolerance(double epsilon) {
		Inspector.TOLERANCE = epsilon;
		return epsilon > 0;
	}

	/*-------- Delegate the 'same' method to the Inspector class ---------*/
	/**
	 * <P>Provide a general extensional equality comparison for arbitrary pair of
	 * objects</P>
	 *
	 * <P>Inspector invokes the user-defined <code>same</code> method, if the
	 * corresponding classes implement the <code>ISame</code> interface.</P>
	 *
	 * @param obj1 the first object
	 * @param obj2 the second object
	 * @return <code>true</code> if the two objects represent the same data
	 */
	public boolean same(Object obj1, Object obj2) {
		return this.inspector.isSame(obj1, obj2);
	}

	/*------------------------ Test definitions  -------------------------*/


	/**
	 * Test nothing, just report success.
	 *
	 * @return always returns <code>true</code>
	 */
	public boolean success() {
		return success("");
	}

	/**
	 * Test nothing, just report success.
	 *
	 * @param testname the name of the test
	 * @return always returns <code>true</code>
	 */
	public boolean success(String testname) {
		return checkExpect(true, testname);
	}

	/**
	 * Test nothing, just report failure.
	 *
	 * @return always returns <code>false</code>
	 */
	public boolean fail() {
		return fail("");
	}

	/**
	 * Test nothing, just report failure.
	 *
	 * @param testname the name of the test
	 * @return always returns <code>false</code>
	 */
	public boolean fail(String testname) {
		return checkExpect(false, testname);
	}

	/**
	 * Test that only reports success or failure
	 *
	 * @param result the test result
	 * @return <code>true</code> if the test succeeds
	 */
	public boolean checkExpect(boolean result) {
		return checkExpect(result, "");
	}

	/**
	 * Test that only reports success or failure
	 *
	 * @param testname the name of this test
	 * @param result the test result
	 * @return <code>true</code> if the test succeeds
	 */
	public boolean checkExpect(boolean result, String testname) {

		this.testname = testname;
		if (!result)
			// add the report of failure
			return this.reportErrors(testname, ": error -- no blame -- \n");
		// add the report of success
		else
			return this.reportSuccess(testname, ": success \n");
	}

	/**
	 * Test that only reports success or failure
	 *
	 * @param result the test result that should fail
	 * @return <code>true</code> if the test fails
	 */
	public boolean checkFail(boolean result) {
		return checkFail(result, "");
	}

	/**
	 * Test that only reports success or failure
	 *
	 * @param result the test result that should fail
	 * @return <code>true</code> if the test fails
	 */
	public boolean checkFail(boolean result, String testname) {
		return checkExpect(!result, "Failure expected: \n" + testname);
	}

	/*---------------------------- <T> vs. <T>  -------------------------------*/

	/**
	 * Test that compares two objects of any kind -- exact values only
	 *
	 * @param <T> the type of the objects being compared
	 * @param actual the computed value of the type T
	 * @param expected the expected value of the type T
	 * @return <code>true</code> if the test succeeds
	 */
	public <T> boolean checkExpect(T actual, T expected) {
		return checkExpect(actual, expected, "");
	}

	/**
	 * Test that compares two objects of any kind -- exact values only
	 *
	 * @param <T> the type of the objects being compared
	 * @param actual the computed value of the type T
	 * @param expected the expected value of the type T
	 * @param testname the name of this test
	 * @return <code>true</code> if the test succeeds
	 */
	public <T> boolean checkExpect(T actual, T expected, String testname) {
		this.testname = testname;
		return this.report(this.inspector.exactTest() &&
				this.inspector.isSame(actual, expected) &&
				!this.inspector.inexactViolation(),
				testname,
				"actual:                                 expected:\n" +
						Printer.combineActualExpected(Printer.produceString(actual),
								Printer.produceString(expected)) + "\n");
	}

	/**
	 * <p>Test that compares two objects of any kind.</p>
	 * <p>The objects can contain inexact values (of the types
	 * <code>double</code> or <code>float</code>). These will be compared within
	 * the given relative tolerance. The relative tolerance used in the
	 * comparison will appear on the test report.</p>
	 *
	 * @param <T> the type of the objects being compared
	 * @param actual the computed value of the type T
	 * @param expected the expected value of the type T
	 * @param tolerance the desired relative tolerance
	 * @return <code>true</code> if the test succeeds
	 */
	public <T> boolean checkInexact(T actual, T expected, double tolerance) {
		return checkInexact(actual, expected, tolerance, "");
	}

	/**
	 * <p>Test that compares two objects of any kind.</p>
	 * <p>The objects can contain inexact values (of the types
	 * <code>double</code> or <code>float</code>). These will be compared within
	 * the given relative tolerance. The relative tolerance used in the
	 * comparison will appear on the test report.</p>
	 *
	 * @param <T> the type of the objects being compared
	 * @param actual the computed value of the type T
	 * @param expected the expected value of the type T
	 * @param tolerance the desired relative tolerance
	 * @param testname the name of this test
	 * @return <code>true</code> if the test succeeds
	 */
	public <T> boolean checkInexact(T actual, T expected, double tolerance,
			String testname) {
		String result =
				"actual:                                 expected:\n" +
						Printer.combineActualExpected(Printer.produceString(actual),
								Printer.produceString(expected)) + "\n";

		if (this.inspector.inexactTest(tolerance))
			return report(false, testname + "\nProvided tolerance value was < 0",
					result);

		if (this.isExactType(actual.getClass().getName()) ||
				this.isExactType(expected.getClass().getName()))
			return report(false, testname +
					"\nAttempt to make inexact comparison of " + "" +
					"exact primitive or wrapper data",
					result);
		else
			return this.report(this.inspector.isSame(actual, expected),
					testname, result);
	}

	/*---------------------- Set<T> vs. Set<T>  -----------------------------*/

	/**
	 * Test that compares two objects that implement {@link Set Set}
	 * interface by comparing data using the <code>equals</code> method for
	 * comparing the elements of the two sets.
	 *
	 * @param actual the computed value of the type {@link Set Set}
	 * @param expected the expected value of the type {@link Set Set}
	 * @return <code>true</code> if the test succeeds
	 */
	public <T> boolean checkSet(Set<T> actual, Set<T> expected) {
		return checkSet(actual, expected, "");
	}

	/**
	 * Test that compares two objects that implement {@link Set Set}
	 * interface by comparing data using the <code>equals</code> method for
	 * comparing the elements of the two sets.
	 *
	 * @param actual the computed value of the type {@link Set Set}
	 * @param expected the expected value of the type {@link Set Set}
	 * @param testname the name of this test
	 * @return <code>true</code> if the test succeeds
	 */
	public <T> boolean checkSet(Set<T> actual, Set<T> expected,
			String testname) {
		this.testname = testname;
		return this.report(this.inspector.exactTest() &&
				this.inspector.isSameSet(actual, expected) &&
				!this.inspector.inexactViolation(),
				testname, this.combineIterable(actual, expected));
	}

	/*------------------ Iterable<T> vs. Iterable<T>  -------------------------*/

	/**
	 * Test that compares two objects that implement {@link Iterable Iterable}
	 * interface by traversing over the data -- objects must be composed of
	 * exact values only
	 *
	 * @param actual the computed value of the type {@link Iterable Iterable}
	 * @param expected the expected value of the type {@link Iterable Iterable}
	 * @return <code>true</code> if the test succeeds
	 */
	public <T> boolean checkIterable(Iterable<T> actual, Iterable<T> expected) {
		return checkIterable(actual, expected, "");
	}

	/**
	 * Test that compares two objects that implement {@link Iterable Iterable}
	 * interface by traversing over the data -- objects must be composed of
	 * exact values only
	 *
	 * @param actual the computed value of the type {@link Iterable Iterable}
	 * @param expected the expected value of the type {@link Iterable Iterable}
	 * @param testname the name of this test
	 * @return <code>true</code> if the test succeeds
	 */
	public <T> boolean checkIterable(Iterable<T> actual, Iterable<T> expected,
			String testname) {
		this.testname = testname;
		return this.report(this.inspector.exactTest() &&
				this.inspector.isSameIterable(actual, expected) &&
				!this.inspector.inexactViolation(),
				testname, this.combineIterable(actual, expected));
	}

	/**
	 * <p>Test that compares two objects that implement {@link Iterable Iterable}
	 * interface by traversing over the data.</p>
	 * <p>The objects can contain inexact values (of the types
	 * <code>double</code> or <code>float</code>). These will be compared within
	 * the given relative tolerance. The relative tolerance used in the
	 * comparison will appear on the test report.</p>
	 *
	 * @param actual the computed value of the type {@link Iterable Iterable}
	 * @param expected the expected value of the type {@link Iterable Iterable}
	 * @param tolerance the desired relative tolerance
	 * @return <code>true</code> if the test succeeds
	 */
	public <T> boolean checkInexactIterable(Iterable<T> actual,
			Iterable<T> expected,
			double tolerance) {
		return checkInexactIterable(actual, expected, tolerance, "");
	}

	/**
	 * <p>Test that compares two objects that implement {@link Iterable Iterable}
	 * interface by traversing over the data.</p>
	 * <p>The objects can contain inexact values (of the types
	 * <code>double</code> or <code>float</code>). These will be compared within
	 * the given relative tolerance. The relative tolerance used in the
	 * comparison will appear on the test report.</p>
	 *
	 * @param actual the computed value of the type {@link Iterable Iterable}
	 * @param expected the expected value of the type {@link Iterable Iterable}
	 * @param tolerance the desired relative tolerance
	 * @param testname the name of this test
	 * @return <code>true</code> if the test succeeds
	 */
	public <T> boolean checkInexactIterable(Iterable<T> actual,
			Iterable<T> expected,
			double tolerance, String testname) {

		if (this.inspector.inexactTest(tolerance))
			return report(false, testname + "\nProvided tolerance value was < 0",
					this.combineIterable(actual, expected));
		this.testname = testname;
		return this.report(this.inspector.isSameIterable(actual, expected),
				testname, this.combineIterable(actual, expected));
	}

	/*----------------- Traversal<T> vs. Traversal<T>  ------------------------*/

	/**
	 * Test that compares two objects that implement {@link Traversal Traversal}
	 * interface by traversing over the data -- objects must be composed of
	 * exact values only
	 *
	 * @param actual the computed value of the type {@link Traversal Traversal}
	 * @param expected the expected value of the type {@link Traversal Traversal}
	 * @return <code>true</code> if the test succeeds
	 */
	public <T> boolean checkTraversal(Traversal<T> actual,
			Traversal<T> expected) {
		return checkTraversal(actual, expected, "");
	}

	/**
	 * Test that compares two objects that implement {@link Traversal Traversal}
	 * interface by traversing over the data -- objects must be composed of
	 * exact values only
	 *
	 * @param actual the computed value of the type {@link Traversal Traversal}
	 * @param expected the expected value of the type {@link Traversal Traversal}
	 * @param testname the name of this test
	 * @return <code>true</code> if the test succeeds
	 */
	public <T> boolean checkTraversal(Traversal<T> actual, Traversal<T> expected,
			String testname) {
		this.testname = testname;
		return this.report(this.inspector.exactTest() &&
				this.inspector.isSameTraversal(actual, expected) &&
				!this.inspector.inexactViolation(),
				testname, this.combineTraversal(actual, expected));
	}

	/**
	 * <p>Test that compares two objects that implement
	 * {@link Traversal Traversal} interface by traversing over the data.</p>
	 * <p>The objects can contain inexact values (of the types
	 * <code>double</code> or <code>float</code>). These will be compared within
	 * the given relative tolerance. The relative tolerance used in the
	 * comparison will appear on the test report.</p>
	 *
	 * @param actual the computed value of the type {@link Traversal Traversal}
	 * @param expected the expected value of the type {@link Traversal Traversal}
	 * @param tolerance the desired relative tolerance
	 * @return <code>true</code> if the test succeeds
	 */
	public <T> boolean checkInexactTraversal(Traversal<T> actual,
			Traversal<T> expected,
			double tolerance){
		return checkInexactTraversal(actual, expected, tolerance, "");
	}

	/**
	 * <p>Test that compares two objects that implement
	 * {@link Traversal Traversal} interface by traversing over the data.</p>
	 * <p>The objects can contain inexact values (of the types
	 * <code>double</code> or <code>float</code>). These will be compared within
	 * the given relative tolerance. The relative tolerance used in the
	 * comparison will appear on the test report.</p>
	 *
	 * @param actual the computed value of the type {@link Traversal Traversal}
	 * @param expected the expected value of the type {@link Traversal Traversal}
	 * @param tolerance the desired relative tolerance
	 * @param testname the name of this test
	 * @return <code>true</code> if the test succeeds
	 */
	public <T> boolean checkInexactTraversal(Traversal<T> actual,
			Traversal<T> expected,
			double tolerance,
			String testname) {

		if (this.inspector.inexactTest(tolerance))
			return report(false, testname + "\nProvided tolerance value was < 0",
					this.combineTraversal(actual, expected));

		this.testname = testname;
		return this.report(this.inspector.isSameTraversal(actual, expected),
				testname, this.combineTraversal(actual, expected));
	}

	/*------------------------ Expect failure --------------------------------*/

	/**
	 * <p>Test that compares two objects of any kind and is expected to fail:
	 * comparison failure constitutes a successful test.</p>
	 * <p>Assumes all values that are compared are exact.</p>
	 *
	 * @param <T> the type of the objects being compared
	 * @param actual the computed value of the type T
	 * @param expected the expected value of the type T
	 * @return <code>true</code> if the test fails
	 */
	public <T> boolean checkFail(T actual, T expected) {
		return this.checkFail(actual, expected, "");
	}

	/**
	 * <p>Test that compares two objects of any kind and is expected to fail:
	 * comparison failure constitutes a successful test.</p>
	 * <p>Assumes all values that are compared are exact.</p>
	 *
	 * @param <T> the type of the objects being compared
	 * @param actual the computed value of the type T
	 * @param expected the expected value of the type T
	 * @param testname the name of this test
	 * @return <code>true</code> if the test fails
	 */
	public <T> boolean checkFail(T actual, T expected, String testname) {
		this.testname = testname;

		return this.report(this.inspector.exactTest() &&
				(!this.inspector.isSame(actual, expected) ||
						this.inspector.inexactViolation()),
						"Failure expected: \n" + testname,
						"actual:                                 expected:\n" +
								Printer.combineActualExpected(Printer.produceString(actual),
										Printer.produceString(expected)) + "\n");
	}

	/**
	 * <p>Test that compares two objects of any kind and is expected to fail:
	 * comparison failure constitutes a successful test.</p>
	 * <p>Allows comparison of inexact values.</p>
	 *
	 * @param <T> the type of the objects being compared
	 * @param actual the computed value of the type T
	 * @param expected the expected value of the type T
	 * @return <code>true</code> if the test fails
	 */
	public <T> boolean checkInexactFail(T actual, T expected, double tolerance) {
		return this.checkInexactFail(actual, expected, tolerance, "");
	}

	/**
	 * <p>Test that compares two objects of any kind and is expected to fail:
	 * comparison failure constitutes a successful test.</p>
	 * <p>Allows comparison of inexact values.</p>
	 *
	 * @param <T> the type of the objects being compared
	 * @param actual the computed value of the type T
	 * @param expected the expected value of the type T
	 * @param testname the name of this test
	 * @return <code>true</code> if the test fails
	 */
	public <T> boolean checkInexactFail(T actual, T expected, double tolerance,
			String testname) {

		String result =

				"actual:                                 expected:\n" +
						Printer.combineActualExpected(Printer.produceString(actual),
								Printer.produceString(expected)) + "\n";

		// test fails if the tolerance is negative
		if (tolerance < 0)
			return this.report(true, testname + " Failure expected: \n" +
					"\n Test failed because the provided tolerance is < 0",
					result);

		if (this.isExactType(actual.getClass().getName()) ||
				this.isExactType(expected.getClass().getName()))
			return report(true, testname + " Failure expected: \n" +
					"\nTest failed because we cannot make inexact comparison of " +
					"exact primitive or wrapper data",
					result);

		setTolerance(tolerance);

		this.testname = testname;
		return this.report(!this.inspector.isSame(actual, expected),
				"Failure expected: \n" + testname, result);
	}

	/*---------------------- Exceptions tests --------------------------------*/

	/**
	 * <p>Test that verifies that when the given object invokes the given method
	 * with the given arguments, it throws the expected exception with the
	 * expected message.</p>
	 *
	 * @param <T> The type of the object that invokes the given method
	 * @param object The object that invokes the method to be tested
	 * @param method The name of the method to test
	 * @param e An instance of the expected exception -- including the
	 *          expected message
	 * @param args An <em>Ellipsis</em> of arguments for the method
	 * @return <code>true</code> if the test succeeds
	 */
	public <T> boolean checkException(Exception e, T object, String method,
			Object... args) {
		return checkException("", e, object, method, args);
	}

	/**
	 * <p>Test that verifies that when the given object invokes the given method
	 * with the given arguments, it throws the expected exception with the
	 * expected message.</p>
	 *
	 * @param <T> The type of the object that invokes the given method
	 * @param testname The description of this test
	 * @param e An instance of the expected exception -- including the
	 *          expected message
	 * @param object The object that invokes the method to be tested
	 * @param method The name of the method to test
	 * @param args An <em>Ellipsis</em> of arguments for the method
	 * @return <code>true</code> if the test succeeds
	 */
	public <T> boolean checkException(String testname, Exception e, T object,
			String method, Object... args) {
		// this is needed to record the correct stack trace before the
		// test throws an exception
		return checkPrivateException(testname, e, object, method, args);
	}

	/**
	 * <p>Test that verifies that when the given object invokes the given method
	 * with the given arguments, it throws the expected exception with the
	 * expected message.</p>
	 * <p>The invocation of this method allows us to correctly record the
	 * stack trace for reporting errors before the test throws an exception.</p>
	 *
	 * @param <T> The type of the object that invokes the given method
	 * @param testname The description of this test
	 * @param e An instance of the expected exception -- including the
	 *          expected message
	 * @param object The object that invokes the method to be tested
	 * @param method The name of the method to test
	 * @param args An <em>Ellipsis</em> of arguments for the method
	 * @return <code>true</code> if the test succeeds
	 */
	private <T> boolean checkPrivateException(String testname,
			Exception e, T object, String method, Object... args) {
		this.testname = testname;

		// create an Array of the argument types
		int length = (args != null) ? args.length : 0;
		Class<?>[] parameters = new Class[length];

		for (int i = 0; i < length; i++) {
			parameters[i] = args[i].getClass();
		}

		// get the type of the expected exception
		Class<?> exceptClass = e.getClass();
		String exceptName = exceptClass.getName();
		String exceptMessage = e.getMessage();

		// get the stack trace for error report before trying to invoke the method
		String trace = this.getStackTrace();

		try {
			// find the method
			Method meth = this
					.findMethod(object, method, parameters);
			if (meth == null)
				throw new NoSuchMethodException("Method " + method + " not found");

			// allow access to protected and private methods
			Reflector.ensureIsAccessible(meth);

			Object result = meth.invoke(object, args);

			// if the invocation succeeds, the test fails because
			// it does not throw the expected exception
			if (result == null)
				return this.report(false, testname,
						"\n invocation did not throw any exception "
								+ "\n  method name: " + meth.getName()
								+ "\n  object class: " + object.getClass().getName()
								+ "\n  result: void" + ": "
								+ "\n  expected exception was: \n    class: "
								+ exceptName + "\n    message: " + exceptMessage);
			else
				return this.report(false, testname,
						"\n invocation did not throw any exception "
								+ "\n  method name: " + meth.getName()
								+ "\n  object class: " + object.getClass().getName()
								+ "\n  result: " + result.getClass().getName() + ": "
								+ Printer.produceString(result)
								+ "\n  expected exception was: \n    class: "
								+ exceptName + "\n    message: " + exceptMessage);
		} catch (Throwable exception) {

			String excName;
			String excMessage;
			if (exception.getCause() != null) {
				excName = exception.getCause().getClass().getName();
				excMessage = exception.getCause().getMessage();
			} else {
				excName = exception.getClass().getName();
				excMessage = exception.getMessage();
			}

			// check that the method threw an exception of the desired type
			if (excName.equals(exceptName)) {

				// check whether the message is correct
				if ((exceptMessage == null && excMessage == null) ||
						(excMessage != null && excMessage.equals(exceptMessage))) {

					// report correct exception, correct message -- test
					// succeeds
					return this.reportSuccess(testname,
							"\n correct exception: \n" + "" + " class: "
									+ exceptName + "\n correct message: "
									+ exceptMessage
									+ "\n    after invoking the method " +  method
									+ "\n    by an object in the class: " + object.getClass().getName()
									+ "\n    object value was: \n" + Printer.produceString(object));
				} else {

					// report correct exception, incorrect message -- test
					// fails
					return this.reportErrors(testname,
							"\n correct exception: "
									+ "\n class: " + exceptName
									+ "\n incorrect message: \n"
									+ "\n message produced: " + excMessage
									+ "\n message expected: " + exceptMessage
									+ "\n    after invoking the method " +  method
									+ "\n    by an object in the class: " + object.getClass().getName()
									+ "\n    object value was: \n" + Printer.produceString(object)
									+ "\n\n" + trace + "\n");
				}
			} else {
				// report that method invocation threw an exception of the
				// wrong
				// type
				return this.reportErrors(testname,
						"\n incorrect exception was thrown: "
								+ "\n exception thrown:   " + excName
								+ "\n exception expected: " + exceptName
								+ "\n    with the message: " + exceptMessage
								+ "\n    after invoking the method " +  method
								+ "\n    by an object in the class: " + object.getClass().getName()
								+ "\n    object value was: \n" + Printer.produceString(object)
								+ "\n\n" + trace + "\n");
			}
		}
	}

	/*---------------------- Exceptions tests for constructors --------------*/

	/**
	 * <p>Test that verifies that when the constructor for the given class
	 * is invoked with the given arguments,
	 * it throws the expected exception with the expected message.</p>
	 *
	 * @param e An instance of the expected exception -- including the
	 *          expected message
	 * @param className the name of the class whose constructor is tested
	 * @param args An <em>Ellipsis</em> of arguments for the method
	 * @return <code>true</code> if the test succeeds
	 */
	public <T> boolean checkConstructorException(Exception e, String className,
			Object... args) {
		return checkConstructorException("", e, className, args);
	}

	/**
	 * <p>Test that verifies that when the constructor for the given class
	 * is invoked with the given arguments,
	 * it throws the expected exception with the expected message.</p>
	 *
	 * @param testname The description of this test
	 * @param e An instance of the expected exception -- including the
	 *          expected message
	 * @param className the name of the class whose constructor is tested
	 * @param args An <em>Ellipsis</em> of arguments for the method
	 * @return <code>true</code> if the test succeeds
	 */
	public <T> boolean checkConstructorException(String testname, Exception e,
			String className, Object... args) {
		// this is needed to record the correct stack trace before the
		// test throws an exception
		return checkPrivateConstructorException(testname, e, className, args);
	}

	/**
	 * <p>Test that verifies that when the constructor for the given class
	 * is invoked with the given arguments,
	 * it throws the expected exception with the expected message.</p>
	 * <p>The invocation of this method allows us to correctly record the
	 * stack trace for reporting errors before the test throws an exception.</p>
	 *
	 * @param testname The description of this test
	 * @param e An instance of the expected exception -- including the
	 *          expected message
	 * @param className the name of the class whose constructor is tested
	 * @param args An <em>Ellipsis</em> of arguments for the method
	 * @return <code>true</code> if the test succeeds
	 */
	private <T> boolean checkPrivateConstructorException(String testname,
			Exception e, String className, Object... args) {
		this.testname = testname;

		// create an Array of the argument types
		int length = (args != null) ? args.length : 0;
		Class<?>[] parameters = new Class[length];

		for (int i = 0; i < length; i++) {
			parameters[i] = args[i].getClass();
			//System.out.println("parameter class [" + i + "] " + parameters[i].getName());
		}

		// get the type of the expected exception
		Class<?> exceptClass = e.getClass();
		String exceptName = exceptClass.getName();
		String exceptMessage = e.getMessage();

		// get the stack trace for error report before trying to invoke the method
		String trace = this.getStackTrace();


		try {
			// find the class with the given name
			Class<?> objectClass = Reflector.classForName(className);

			// find the constructor that matches the given argument list
			Constructor<?> constr = null;

			Constructor<?>[] constructors = objectClass.getDeclaredConstructors();

			// find the constructor with matching types
			// invoke matchParams to resolve primitive type matching
			for (int i = 0; i < Array.getLength(constructors); i++){
				if (this.matchParams(parameters,
						constructors[i].getParameterTypes())){
					constr = constructors[i];
					//System.out.println(constr.toGenericString());
					break;
				}
			}

			if (constr == null){
				throw new NoSuchMethodException("Constructor for the class "
						+ className + " with the given arguments not found");
			}

			// allow access to protected and private constructors
			Reflector.ensureIsAccessible(constr);

			// invoke the constructor - should throw the expected exception
			Object result = constr.newInstance(args);

			// if the invocation succeeds, the test fails because
			// it does not throw the expected exception
			if (result == null)
				return this.report(false, testname,
						"\n constructor invocation for the class " + className +
						" did not throw any exception "
						+ "\n  result: void" + ": "
						+ "\n  expected exception was: \n    class: "
						+ exceptName + "\n    message: " + exceptMessage);
			else
				return this.report(false, testname,
						"\n constructor invocation for the class " + className +
						" did not throw any exception "
						+ "\n  result: " + result.getClass().getName() + ": "
						+ Printer.produceString(result)
						+ "\n  expected exception was: \n    class: "
						+ exceptName + "\n    message: " + exceptMessage);
		} catch (Throwable exception) {
			String excName;
			String excMessage;
			if (exception.getCause() != null) {
				excName = exception.getCause().getClass().getName();
				excMessage = exception.getCause().getMessage();
			} else {
				excName = exception.getClass().getName();
				excMessage = exception.getMessage();
			}

			// check that the method threw an exception of the desired type
			if (excName.equals(exceptName)) {

				// check whether the message is correct
				if ((exceptMessage == null && excMessage == null) ||
						(excMessage != null && excMessage.equals(exceptMessage))) {

					// report correct exception, correct message -- test
					// succeeds
					return this.reportSuccess(testname,
							"\n correct exception: \n" + "" + " class: "
									+ exceptName + "\n correct message: "
									+ exceptMessage + "\n"
									+ "\n after invoking the constructor for the class " + className);
				} else {

					// report correct exception, incorrect message -- test
					// fails
					return this.reportErrors(testname,
							"\n correct exception: "
									+ "\n class: " + exceptName
									+ "\n incorrect message: \n"
									+ "\n message produced: " + excMessage
									+ "\n message expected: " + exceptMessage
									+ "\n after invoking the constructor for the class " + className
									// we may want to print the argument list here - but not for now
									+ "\n\n" + trace + "\n");
				}
			} else {
				// report that method invocation threw an exception of the
				// wrong
				// type
				return this.reportErrors(testname,
						"\n incorrect exception was thrown: "
								+ "\n exception thrown:   " + excName
								+ "\n message produced: " + excMessage
								+ "\n exception expected: " + exceptName
								+ "\n    with the message: " + exceptMessage
								+ "\n after invoking the constructor for the class " + className
								// we may want to print the argument list here - but not for now
								+ "\n\n" + trace + "\n");

			}
		}
	}

	/*--------------------- Method invocation tests ---------------------------*/

	/**
	 * <p>Invoke the method with the given name on a given object with the given
	 * arguments - check if it produces the expected result</p>
	 * <p>The expected result must be composed of exact values only</p>
	 *
	 * @param expected The expected result of the method being tested
	 * @param <T> The type of the object that invokes the given method
	 * @param object The object that invokes the method to be tested
	 * @param method The name of the method to test
	 * @param args An array of arguments for the method - use Array of length 0
	 *             if no arguments are needed
	 * @return <code>true</code> if the test succeeds
	 */
	public <T> boolean checkMethod(Object expected,
			T object, String method, Object... args) {
		return checkMethod("", expected, object, method, args);
	}

	/**
	 * <p>Invoke the method with the given name on a given object with the given
	 * arguments - check if it produces the expected result</p>
	 * <p>The expected result must be composed of exact values only</p>
	 *
	 * @param testname The description of this test
	 * @param expected The expected result of the method being tested
	 * @param <T> The type of the object that invokes the given method
	 * @param object The object that invokes the method to be tested
	 * @param method The name of the method to test
	 * @param args An array of arguments for the method - use Array of length 0
	 *             if no arguments are needed
	 * @return <code>true</code> if the test succeeds
	 */
	public <T> boolean checkMethod(String testname, Object expected,
			T object, String method, Object... args) {
		return this.inspector.exactTest() &&
				checkPrivateMethod(testname, expected, object, method, args, false);
	}

	/**
	 * Invoke the method with the given name on a given object with the given
	 * arguments - check if it produces the expected value
	 * <p>The resulting object can contain inexact values (of the types
	 * <code>double</code> or <code>float</code>). These will be compared within
	 * the given relative tolerance. The relative tolerance used in the
	 * comparison will appear on the test report.</p>
	 *
	 * @param expected The expected result of the method being tested
	 * @param <T> The type of the object that invokes the given method
	 * @param object The object that invokes the method to be tested
	 * @param method The name of the method to test
	 * @param args An array of arguments for the method - use Array of length 0
	 *             if no arguments are needed
	 * @return <code>true</code> if the test succeeds
	 */
	public <T> boolean checkInexactMethod(Object expected, double tolerance,
			T object, String method, Object... args) {
		return checkInexactMethod("", expected, tolerance,
				object, method, args);
	}

	/**
	 * Invoke the method with the given name on a given object with the given
	 * arguments - check if it produces the expected value
	 * <p>The resulting object can contain inexact values (of the types
	 * <code>double</code> or <code>float</code>). These will be compared within
	 * the given relative tolerance. The relative tolerance used in the
	 * comparison will appear on the test report.</p>
	 *
	 * @param testname The description of this test
	 * @param expected The expected result of the method being tested
	 * @param <T> The type of the object that invokes the given method
	 * @param object The object that invokes the method to be tested
	 * @param method The name of the method to test
	 * @param args An array of arguments for the method - use Array of length 0
	 *             if no arguments are needed
	 * @return <code>true</code> if the test succeeds
	 */
	public <T> boolean checkInexactMethod(
			String testname, Object expected, double tolerance,
			T object, String method, Object... args) {
		if (this.inspector.inexactTest(tolerance))
			return reportErrors(testname + "\nProvided tolerance value was < 0",
					"\n Inexact method invocation test ");

		return checkPrivateMethod(testname, expected, object, method, args, true);
	}

	/**
	 * Invoke the method with the given name on a given object with the given
	 * arguments - check if it produces the expected value
	 *
	 * @param testname The description of this test
	 * @param expected The expected result of the method being tested
	 * @param <T> The type of the object that invokes the given method
	 * @param object The object that invokes the method to be tested
	 * @param method The name of the method to test
	 * @param args An array of arguments for the method - use Array of length 0
	 *             if no arguments are needed
	 * @return <code>true</code> if the test succeeds
	 */
	private <T> boolean checkPrivateMethod(String testname, Object expected,
			T object, String method, Object[] args, boolean inexact) {

		this.testname = testname;
		// create an Array of the argument types
		int length = Array.getLength(args);
		Class<?>[] parameters = new Class[length];

		for (int i = 0; i < length; i++) {
			parameters[i] = args[i].getClass();
		}

		String parlist = "(";
		for (Class<?> parameter : parameters)
			parlist = parlist + (parameter.getName()) + ",";

		parlist = parlist.substring(0, parlist.length() - 1) + ")";

		try {
			// find the method to be invoked by 'object' with the name 'method'
			// and parameters in the classes given by 'parameters'
			Method meth = findMethod(object, method, parameters);

			if (meth == null) {
				return report(false, testname + "\nNo method with the name "
						+ method + " found\n",
						"Failed to invoke the method "
								+ object.getClass().getName() + "." + method
								+ parlist +
								Printer.produceString(expected));
			} else {
				// allow access to protected and private methods
				Reflector.ensureIsAccessible(meth);

				// produce a test massage that includes the object that invoked
				// the method, an the argument list
				String testmessage = testname + "\n"
						+ Printer.produceString(object) + "\n invoked method "
						+ method + " in the class "
						+ object.getClass().getName() + "\n with arguments "
						+ makeArglist(args) + ")\n";

				if (inexact) // tolerance has been set already
					return checkInexact(meth.invoke(object, args), expected,
							Inspector.TOLERANCE, testmessage);
				else
					return checkExpect(meth.invoke(object, args), expected,
							testmessage);
			}
		} catch (Throwable exception) {
			String testmessage = testname + "\n"
					+ Printer.produceString(object) + "\n invoked method "
					+ method + " in the class " + object.getClass().getName()
					+ "\n with arguments " + makeArglist(args) + ")\n";

			// report that the method threw an exception
			boolean result = this.report(false,
					testmessage + "\nthrew an excception ",
					Printer.produceString(object) + Printer.produceString(args));
			exception.printStackTrace();
			return result;
		}
	}

	/**
	 * Convert the argument list into <code>String</code> enclosed in (..)
	 * and separated by commas --- so it does not look like Array
	 *
	 * @param args the arguments to represent as <code>String</code>s
	 * @return one <code>String</code> that looks like method argument list
	 */
	private String makeArglist(Object... args){
		// Convert the argument list into String enclosed in (..)
		// and separated by commas --- so it does not look like Array
		String arglist = "(";
		for (Object o : args){
			arglist = arglist.concat(Printer.produceString(o) + ",\n");
		}
		if (arglist.length() > 1)
			arglist = arglist.substring(0, arglist.length()-2);

		return arglist;
	}

	/*-------------------- Checks for a random value  ------------------------*/

	/**
	 * <p>Test that determines whether the value of the given object (of any kind)
	 * is the same as one of the expected results</p>
	 * <p>The expected results must be composed of exact values only.</p>
	 *
	 * <p>Warning: If the actual and expected values
	 * are String-s, the programmer must provide the test name ---
	 * there is no way the tester can determine whether or not the test name
	 * has been included.</p>
	 *
	 * @param <T> The type of the objects being compared
	 * @param actual The computed value of the type T
	 * @param expected An <em>Ellipsis</em> of the expected values of the type T
	 * @return <code>true</code> if the test succeeds
	 */
	public <T> boolean checkOneOf(T actual, T... expected) {
		return checkOneOf("", actual, expected);
	}

	/**
	 * <p>Test that determines whether the value of the given object (of any kind)
	 * is the same as one of the expected results</p>
	 * <p>The expected results must be composed of exact values only.</p>
	 *
	 * <p>Warning: If the actual and expected values
	 * are String-s, the programmer must provide the test name ---
	 * there is no way the tester can determine whether or not the test name
	 * has been included.</p>
	 *
	 * @param <T> The type of the objects being compared
	 * @param testname The name of this test
	 * @param actual The computed value of the type T
	 * @param expected An <em>Ellipsis</em> of the expected values of the type T
	 * @return <code>true</code> if the test succeeds
	 */
	public <T> boolean checkOneOf(String testname, T actual, T... expected) {

		for (int i = 0; i < Array.getLength(expected); i++) {
			if (this.inspector.isSame(actual, expected[i]) &&
					!this.inspector.inexactViolation())
				return this.report(true, testname, this.combine(actual, expected[i]));
		}

		return this.report(false, testname + "\nNo matching value found "
				+ "among the list of expected values",
				this.combineOneOf(actual, expected));
	}

	/**
	 * <p>Test that determines whether the value of the given object (of any kind)
	 * is the same as one of the expected results</p>
	 * <p>The expected results must be composed of exact values only.</p>
	 *
	 * @param <T> The type of the objects being compared
	 * @param actual The computed value of the type T
	 * @param expected An <em>Ellipsis</em> of the expected values of the type T
	 * @return <code>true</code> if the test succeeds
	 */
	public <T> boolean checkInexactOneOf(double tolerance,
			T actual, T... expected) {
		return checkInexactOneOf("", tolerance, actual, expected);
	}

	/**
	 * <p>Test that determines whether the value of the given object (of any kind)
	 * is the same as one of the expected results</p>
	 * <p>The expected results must be composed of exact values only.</p>
	 *
	 * @param <T> The type of the objects being compared
	 * @param testname The name of this test
	 * @param actual The computed value of the type T
	 * @param expected An <em>Ellipsis</em> of the expected values of the type T
	 * @return <code>true</code> if the test succeeds
	 */
	public <T> boolean checkInexactOneOf(String testname, double tolerance,
			T actual, T... expected) {

		if (this.inspector.inexactTest(tolerance))
			return report(false, testname + "\nProvided tolerance value was < 0",
					this.combineOneOf(actual, expected));

		this.testname = testname;
		for (int i = 0; i < Array.getLength(expected); i++) {
			if (this.inspector.isSame(actual, expected[i]))
				return this.report(true, testname, this.combine(actual, expected[i]));
		}

		return this.report(false, testname + "\nNo matching value found "
				+ "among the list of expected values",
				this.combineOneOf(actual, expected));
	}

	/*---------------- None of the choices checks -----------------------*/

	/**
	 * <p>Test that determines whether the value of the given object (of any kind)
	 * is not equal to any of the expected  results</p>
	 * <p>The expected results must be composed of exact values only.</p>
	 * <p>A 'same' item that involved inexact comparison is not considered a
	 * match.</p>
	 *
	 * @param <T> the type of the objects being compared
	 * @param actual the computed value of the type T
	 * @param expected the expected values of the type T
	 * @return <code>true</code> if the test succeeds
	 */
	public <T> boolean checkNoneOf(T actual, T... expected) {
		return checkNoneOf(actual, expected, "");
	}

	/**
	 * <p>Test that determines whether the value of the given object (of any kind)
	 * is not equal to any of the expected  results</p>
	 * <p>The expected results must be composed of exact values only.</p>
	 * <p>A 'same' item that involved inexact comparison is not considered a
	 * match.</p>
	 *
	 * @param <T> the type of the objects being compared
	 * @param actual the computed value of the type T
	 * @param expected the expected values of the type T
	 * @return <code>true</code> if the test succeeds
	 */
	public <T> boolean checkNoneOf(String testname, T actual, T... expected) {

		for (int i = 0; i < Array.getLength(expected); i++) {
			if (this.inspector.isSame(actual, expected[i]) &&
					!this.inspector.inexactViolation())
				return this.report(false,
						"Matching value found in none-of test\n" + testname,
						this.combine(actual, expected[i]));
		}
		return this.report(
				true,
				testname
				+ "\nNo matching value found among the list of excluded values",
				this.combineOneOf(actual, expected));
	}

	/**
	 * <p>Test that determines whether the value of the given object (of any kind)
	 * is not equal to any of the expected  results</p>
	 * <p>The expected results can include inexact values.</p>
	 * <p>Providing <code>tolerance < 0</code> results in an automatic
	 * success of the test.</p>
	 *
	 * @param <T> the type of the objects being compared
	 * @param actual the computed value of the type T
	 * @param expected the expected values of the type T
	 * @return <code>true</code> if the test succeeds
	 */
	public <T> boolean checkInexactNoneOf(double tolerance,
			T actual, T... expected) {
		return checkInexactNoneOf(tolerance, actual, expected, "");
	}

	/**
	 * <p>Test that determines whether the value of the given object (of any kind)
	 * is not equal to any of the expected  results</p>
	 * <p>The expected results can include inexact values.</p>
	 * <p>Providing <code>tolerance < 0</code> results in an automatic
	 * success of the test.</p>
	 *
	 * @param <T> the type of the objects being compared
	 * @param actual the computed value of the type T
	 * @param expected the expected values of the type T
	 * @return <code>true</code> if the test succeeds
	 */
	public <T> boolean checkInexactNoneOf(String testname, double tolerance,
			T actual, T... expected) {

		if (this.inspector.inexactTest(tolerance))
			return report(false, testname + "\nProvided tolerance value was < 0",
					this.combineOneOf(actual, expected));

		this.testname = testname;
		for (int i = 0; i < Array.getLength(expected); i++) {
			if (this.inspector.isSame(actual, expected[i]))
				return this.report(false,
						"Matching value found in none-of test\n" + testname,
						this.combine(actual, expected[i]));
		}
		return this.report(
				true,
				testname
				+ "\nNo matching value found among the list of excluded values",
				this.combineOneOf(actual, expected));
	}

	/*---------------- Numeric (mixed) range checks -----------------------*/

	/**
	 * Test that determines whether the value of the given numerical object (of
	 * any kind) is the range between low (inclusive) and high (exclusive)
	 * values. Allows for comparison of mixed numeric types.
	 *
	 * @param actual the computed value of the type <code>Number</code>
	 * @param low the lower limit of the type <code>Number</code> (inclusive) for this range
	 * @param high the upper limit of the type <code>Number</code> (exclusive) for this range
	 * @return <code>true</code> if the test succeeds
	 */
	public <T> boolean checkNumRange(Number actual, Number low, Number high) {
		return checkNumRange(actual, low, high, "");
	}

	/**
	 * Test that determines whether the value of the given numerical object (of
	 * any kind) is the range between low (inclusive) and high (exclusive)
	 * values. Allows for comparison of mixed numeric types.
	 *
	 * @param actual the computed value of the type <code>Number</code>
	 * @param low the lower limit of the type <code>Number</code> (inclusive) for this range
	 * @param high the upper limit of the type <code>Number</code> (exclusive) for this range
	 * @return <code>true</code> if the test succeeds
	 */
	public <T> boolean checkNumRange(Number actual, Number low, Number high,
			String testname) {
		return checkNumRange(actual, low, high, true, false, testname);
	}

	/**
	 * Test that determines whether the value of the given object (of any kind)
	 * is the range between low and high values. User must specify whether the
	 * low and high bounds are inclusive or exclusive.
	 *
	 * @param actual the computed value of the type <code>Number</code>
	 * @param low the lower limit of the type <code>Number</code> (inclusive) for this range
	 * @param high the upper limit of the type <code>Number</code> (exclusive) for this range
	 * @param lowIncl is the low limit inclusive for this range?
	 * @param highIncl is the upper limit inclusive for this range?
	 * @return <code>true</code> if the test succeeds
	 */
	public <T> boolean checkNumRange(Number actual, Number low, Number high,
			boolean lowIncl, boolean highIncl) {
		return checkNumRange(actual, low, high, lowIncl, highIncl, "");
	}

	/**
	 * Test that determines whether the value of the given object (of any kind)
	 * is the range between low and high values. User must specify whether the
	 * low and high bounds are inclusive or exclusive.
	 *
	 * @param actual the computed value of the type <code>Number</code>
	 * @param low the lower limit of the type <code>Number</code> (inclusive) for this range
	 * @param high the upper limit of the type <code>Number</code> (exclusive) for this range
	 * @param lowIncl is the low limit inclusive for this range?
	 * @param highIncl is the upper limit inclusive for this range?
	 * @return <code>true</code> if the test succeeds
	 */
	public <T> boolean checkNumRange(Number actual, Number low, Number high,
			boolean lowIncl, boolean highIncl, String testname) {

		// do not report inexact comparisons
		Inspector.INEXACT_COMPARED = false;
		this.testname = testname;
		boolean within = true;
		boolean aboveLow =
				((Double) actual.doubleValue()).compareTo(low.doubleValue()) > 0;
				boolean belowHigh =
						((Double) actual.doubleValue()).compareTo(high.doubleValue()) < 0;

				if (lowIncl)
					aboveLow = aboveLow ||
					((Double) actual.doubleValue()).compareTo(low.doubleValue()) == 0;

				if (highIncl)
					belowHigh = belowHigh ||
					((Double) actual.doubleValue()).compareTo(high.doubleValue()) == 0;

				within = aboveLow && belowHigh;
				// System.out.println("Compared " + actual.toString() +
				// " within [" + low.toString() + ", " + high.toString() + "): " +
				// within);
				if (within)
					return this.report(within, testname, actual, low, high);
				else
					return this.report(within, testname
							+ "\nActual value is not within the [low high) range.",
							actual, low, high);
	}

	/*-------------------- General range checks --------------------------*/

	/**
	 * Test that determines whether the value of the given object (of any kind)
	 * is the range between low (inclusive) and high (exclusive) values
	 *
	 * @param <T> the type of the objects being compared
	 * @param actual the computed value of the type T
	 * @param low the lower limit of the type T (inclusive) for this range
	 * @param high the upper limit of the type T (exclusive) for this range
	 * @return <code>true</code> if the test succeeds
	 */
	public <T> boolean checkRange(Comparable<T> actual, T low, T high) {
		return checkRange(actual, low, high, "");
	}

	/**
	 * Test that determines whether the value of the given object (of any kind)
	 * is the range between low (inclusive) and high (exclusive) values
	 *
	 * @param <T> the type of the objects being compared
	 * @param actual the computed value of the type T
	 * @param low the lower limit of the type T (inclusive) for this range
	 * @param high the upper limit of the type T (exclusive) for this range
	 * @param testname the name of this test
	 * @return <code>true</code> if the test succeeds
	 */
	public <T> boolean checkRange(Comparable<T> actual, T low, T high,
			String testname) {
		return checkRange(actual, low, high, true, false, testname);
	}

	/**
	 * Test that determines whether the value of the given object (of any kind)
	 * is the range between low and high values. User must specify whether the
	 * low and high bounds are inclusive or exclusive.
	 *
	 * @param <T> the type of the objects being compared
	 * @param actual the computed value of the type T
	 * @param low the lower limit of the type T for this range
	 * @param high the upper limit of the type T for this range
	 * @param lowIncl is the low limit inclusive for this range?
	 * @param highIncl is the upper limit inclusive for this range?
	 * @return <code>true</code> if the test succeeds
	 */
	public <T> boolean checkRange(Comparable<T> actual, T low, T high,
			boolean lowIncl, boolean highIncl) {
		return checkRange(actual, low, high, lowIncl, highIncl, "");
	}

	/**
	 * Test that determines whether the value of the given object (of any kind)
	 * is the range between low and high values. User must specify whether the
	 * low and high bounds are inclusive or exclusive.
	 *
	 * @param <T> the type of the objects being compared
	 * @param actual the computed value of the type T
	 * @param low the lower limit of the type T for this range
	 * @param high the upper limit of the type T for this range
	 * @param lowIncl is the low limit inclusive for this range?
	 * @param highIncl is the upper limit inclusive for this range?
	 * @param testname the name of this test
	 * @return <code>true</code> if the test succeeds
	 */
	public <T> boolean checkRange(Comparable<T> actual, T low, T high,
			boolean lowIncl, boolean highIncl, String testname) {

		// do not report inexact comparisons
		Inspector.INEXACT_COMPARED = false;
		this.testname = testname;
		boolean within = true;
		boolean aboveLow = true;
		boolean belowHigh = true;
		String ls = "(";
		String hs = ")";

		if (lowIncl) {
			aboveLow = actual.compareTo(low) >= 0;
			ls = "[";
		} else
			aboveLow = actual.compareTo(low) > 0;

			if (highIncl) {
				belowHigh = actual.compareTo(high) <= 0;
				hs = "]";
			} else
				belowHigh = actual.compareTo(high) < 0;

			within = aboveLow && belowHigh;
			// System.out.println("Compared " + actual.toString() +
			// " within " + ls + low.toString() + ", " + high.toString() + hs +
			// ": " + within);
			if (within)
				return this.report(within, testname, actual, low, high);
			else
				return this.report(within, testname
						+ "\nActual value is not within the " + ls + "low high"
						+ hs + " range.", actual, low, high);
	}

	/**
	 * Test that determines whether the value of the given object (of any kind)
	 * is the range between low (inclusive) and high (exclusive) values
	 *
	 * @param <T> the type of the objects being compared
	 * @param actual the computed value of the type T
	 * @param low the lower limit of the type T (inclusive) for this range
	 * @param high the upper limit of the type T (exclusive) for this range
	 * @param testname the name of this test
	 * @return <code>true</code> if the test succeeds
	 */

	public <T> boolean checkRange(T actual, T low, T high, Comparator<T> comp,
			String testname) {
		return checkRange(actual, low, high, true, false, comp, testname);
	}

	/**
	 * Test that determines whether the value of the given object (of any kind)
	 * is the range between low (inclusive) and high (exclusive) values
	 *
	 * @param <T> the type of the objects being compared
	 * @param actual the computed value of the type T
	 * @param low the lower limit of the type T (inclusive) for this range
	 * @param high the upper limit of the type T (exclusive) for this range
	 * @return <code>true</code> if the test succeeds
	 */
	public <T> boolean checkRange(T actual, T low, T high, Comparator<T> comp) {
		return checkRange(actual, low, high, true, false, comp, "");
	}

	/**
	 * Test that determines whether the value of the given object (of any kind)
	 * is the range between low (inclusive) and high (exclusive) values
	 *
	 * @param <T> the type of the objects being compared
	 * @param actual the computed value of the type T
	 * @param low the lower limit of the type T (inclusive) for this range
	 * @param high the upper limit of the type T (exclusive) for this range
	 * @param lowIncl is the low limit inclusive for this range?
	 * @param highIncl is the upper limit inclusive for this range?
	 * @param comp The <code>Comparator</code> used to compare the values
	 * @return <code>true</code> if the test succeeds
	 */
	public <T> boolean checkRange(T actual, T low, T high, boolean lowIncl,
			boolean highIncl, Comparator<T> comp) {
		return checkRange(actual, low, high, lowIncl, highIncl, comp, "");
	}

	/**
	 * Test that determines whether the value of the given object (of any kind)
	 * is the range between low (inclusive) and high (exclusive) values
	 *
	 * @param <T> the type of the objects being compared
	 * @param actual the computed value of the type T
	 * @param low the lower limit of the type T (inclusive) for this range
	 * @param high the upper limit of the type T (exclusive) for this range
	 * @param lowIncl is the low limit inclusive for this range?
	 * @param highIncl is the upper limit inclusive for this range?
	 * @param comp The <code>Comparator</code> used to compare the values
	 * @param testname the name of this test
	 * @return <code>true</code> if the test succeeds
	 */
	public <T> boolean checkRange(T actual, T low, T high, boolean lowIncl,
			boolean highIncl, Comparator<T> comp, String testname) {

		// do not report inexact comparisons
		Inspector.INEXACT_COMPARED = false;
		this.testname = testname;
		boolean within = true;
		boolean aboveLow = true;
		boolean belowHigh = true;
		String ls = "(";
		String hs = ")";

		if (lowIncl) {
			aboveLow = comp.compare(actual, low) >= 0;
			ls = "[";
		} else
			aboveLow = comp.compare(actual, low) > 0;

			if (highIncl) {
				belowHigh = comp.compare(actual, high) <= 0;
				hs = "]";
			} else
				belowHigh = comp.compare(actual, high) < 0;

			within = aboveLow && belowHigh;

			// System.out.println("Compared " + actual.toString() +
			// " within " + ls + low.toString() + ", " + high.toString() + hs +
			// ": " + within);

			if (within)
				return this.report(within, testname, actual, low, high);
			else
				return this.report(within, testname
						+ "\nActual value is not within the " + ls + "low high"
						+ hs + " range.", actual, low, high);
	}

	/*----------- Checks for equivalence  of two objects ------------------*/

	/**
	 * Use the given <CODE>{@link Equivalence Equivalence}</CODE>
	 * function object to determine the equivalence
	 * of the two given objects.
	 *
	 * @param obj1 the first object to compare
	 * @param obj2 the second object to compare
	 * @param equiv the function object that implements the
	 * <CODE>{@link Equivalence Equivalence}</CODE> comparison
	 *
	 * @return <code>true</code> if the two objects are equivalent
	 */
	public <T> boolean checkEquivalent(T obj1, T obj2,
			Equivalence<T> equiv) {
		return this.checkEquivalent(obj1, obj2, equiv, "");
	}

	/**
	 * Use the given <CODE>{@link Equivalence Equivalence}</CODE>
	 * function object to determine the equivalence
	 * of the two given objects.
	 *
	 * @param obj1 the first object to compare
	 * @param obj2 the second object to compare
	 * @param equiv the function object that implements the
	 * <CODE>{@link Equivalence Equivalence}</CODE> comparison
	 * @param testname the <code>String</code> that describes this test
	 *
	 * @return <code>true</code> if the two objects are equivalent
	 */
	public <T> boolean checkEquivalent(T obj1, T obj2,
			Equivalence<T> equiv, String testname) {
		this.testname = "Equivalence test: \n" + testname;
		return this.report(equiv.equivalent(obj1, obj2), testname,
				this.combine(obj1, obj2));
	}

	/*--------------------------------------------------------------------*/
	/*---------- HELPERS FOR THE TEST EVALUATION SECTION -----------------*/
	/*--------------------------------------------------------------------*/

	/**
	 * Find the method with the given name for the type of the given object,
	 * that consumes parameters of the given types. Account for any autoboxing
	 * of primitive types
	 *
	 * @param object The object expected to invoke the method
	 * @param method The name of the method to invoke
	 * @param parameters The parameter types for the method invocation
	 * @return The instance of the declared method
	 */
	private <T> Method findMethod(T object, String method,
			Class<?>[] parameters) {

		Method[] allMethods = findAllMethods(object.getClass());
		ArrayList<Method> allNamed = new ArrayList<Method>();

		// make a list of all methods with the given name
		for (Method elt : allMethods)
			if (elt.getName().equals(method))
				allNamed.add(elt);

		// / add the test to compare parameters -- invocation with int works!!
		if (allNamed.size() > 0) {
			for (Method m : allNamed) {
				if (this.matchParams(parameters, m.getParameterTypes())){
					return m;
				}
			}
			// no methods matched the given parameter list
			this.testname = this.testname + "\nNo method with the name " + method
					+ " had a matching argument list\n";
			System.out.println(this.testname);
			return null;
		}
		// no methods found with the given name
		else {
			this.testname = this.testname + "\nNo method with the name " + method
					+ " found\n";
			System.out.println(this.testname);
			return null;
		}
	}

	/**
	 * Find all test methods (with the name that starts with test...) in the
	 * class determined by the instance of the given object, that consumes a
	 * <code>Tester</code> argument.
	 *
	 * @param object The object expected to invoke the method
	 * @param testname The <code>String</code> that records test diagnosis info
	 * @return an <code>ArrayList</code> of all test methods
	 */
	private <T> ArrayList<Method> findTestMethods(T object, String testname) {

		Method[] allMethods = findAllMethods(object.getClass());
		ArrayList<Method> allNamed = new ArrayList<Method>();
		Class<?>[] testerParam = new Class[] { this.getClass() };

		// make a list of all methods with the given name
		for (Method method : allMethods) {

			if (method.getName().startsWith("test")
					&& this.matchParams(method.getParameterTypes(),
							testerParam)) {
				allNamed.add(method);
				Reflector.ensureIsAccessible(method);
			} else if (method.getAnnotation(TestMethod.class) != null) {
				allNamed.add(method);
				Reflector.ensureIsAccessible(method);
			}
		}

		if (allNamed.size() > 0) {
			// found test methods that matched the given parameter list
			testname = testname + "Found " + allNamed.size() + " test methods";

			return allNamed;
		}
		// no test methods that matched the given parameter list
		else {
			testname = testname + "\nNo method with the name test..."
					+ " found in the class " + object.getClass().getName()
					+ "\n";
			return null;
		}
	}

	/**
	 * Finds all of the methods for a particular class, including those that
	 * are part of a super class. This will stop when it hits java.lang.Object.
	 * While traversing the methods, it also marks all of the methods to
	 * accessible so that we can utilize them elsewhere in our code. This
	 * should be abstracted out to a custom reflector (similar to what we
	 * already have) in the future.
	 *
	 * @param c class to collect methods from
	 * @return the resulting array of methods found
	 */
	private Method[] findAllMethods(Class<?> c) {
		Class<?> classToSurvey = c;

		ArrayList<Method> list = new ArrayList<Method>();
		// While we have a class to examine
		while (classToSurvey != null && classToSurvey != Object.class) {
			// Get all public and private methods
			Method[] allMethods = classToSurvey.getDeclaredMethods();
			// For each method, set it to accessible and add it to the list
			for (Method m : allMethods) {
				Reflector.ensureIsAccessible(m);
				list.add(m);
			}

			// Get the super class and loop
			classToSurvey = classToSurvey.getSuperclass();
		}

		// Convert the ArrayList to an array and return
		return list.toArray(new Method[0]);
	}

	/**
	 * See if the list of <CODE>Class</CODE> instances
	 *   that represent the parameter list for the method to invoke
	 *   matches the method definition with the same name
	 *
	 * @param parInput <CODE>Array</CODE> of <CODE>Class</CODE> instances
	 *   that represent the input parameter list
	 * @param parDefined <CODE>Array</CODE> of <CODE>Class</CODE instances
	 *   that represent the parameter list for a method with matching name
	 * @return <code>true</code> if the parameter lists represent the same classes allowing
	 *         for primitive types to match their wrapper classes.
	 */
	private boolean matchParams(Class<?>[] parInput, Class<?>[] parDefined) {

		// make sure both methods have the same number of arguments
		if (Array.getLength(parInput) != Array.getLength(parDefined)){
			return false;
		}

		if (Array.getLength(parInput)  == 0)
			// no parameters to match
			return true;

		for (int i = 0; i < Array.getLength(parInput); i++) {
			if (!matchPair(parInput[i], parDefined[i])){
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>Match one pair of arguments.</p>
	 * @param parInput the class of the provided argument
	 * @param parDefined the class of the method parameter
	 * @return <code>true</code> if the provided argument can be assigned to the method parameter
	 */
	private boolean matchPair(Class<?> parInput, Class<?> parDefined){

		String in = parInput.getName();
		String def = parDefined.getName();
		//System.out.println("Comparing argument types " + in + " with " + def);

		// same names are OK
		if (in.equals(def))
			return true;

		// every type matches Object (especially needed for generics)
		// has to be checked before String and primitive/wrapper types are compared
		if (def.equals("java.lang.Object")){
			// System.out.println("any type with Object is OK");
			return true;
		}

		// String arguments must match - we know they are not equal
		if (in.equals("java.lang.String") || def.equals("java.lang.String")){
			// System.out.println("String comparison failed");
			return false;
		}

		// special handling for the primitive and wrapper classes
		else {
			if (Inspector.isWrapperClass(def) || Inspector.isWrapperClass(in)) {
				// System.out.println("Comparing primitive/wrappers");

				if (!isWrapperMatch(in, def)){
					// System.out.println("primitive comparison failed");
					return false;
				}
			}

			// now look for compatible classes
			else {
				if (!parDefined.isAssignableFrom(parInput)){
					// System.out.println("isAssignable comparison failed");
					return false;
				}
			}
		}
		return true;
	}


	/**
	 * Check whether the primitive or wrapper type with the name <CODE>in</CODE>
	 * matches the primitive or wrapper type with the name <CODE>def</CODE>.
	 *
	 * @param in The name of the primitive or wrapper type to match
	 * @param def The name of the primitive or wrapper class to match
	 * @return <code>true</code> if the two types are compatible
	 */
	private boolean isWrapperMatch(String in, String def) {
		if (def.equals("java.lang.Integer") && in.equals("int"))
			return true;
		else if (def.equals("java.lang.Short") && in.equals("short"))
			return true;
		else if (def.equals("java.lang.Long") && in.equals("long"))
			return true;
		else if (def.equals("java.lang.Byte") && in.equals("byte"))
			return true;
		else if (def.equals("java.lang.Character") && in.equals("char"))
			return true;
		else if (def.equals("java.lang.Double") && in.equals("double"))
			return true;
		else if (def.equals("java.lang.Float") && in.equals("float"))
			return true;
		else if (def.equals("java.lang.Boolean") && in.equals("boolean"))
			return true;

		if (def.equals("int") && in.equals("java.lang.Integer"))
			return true;
		else if (def.equals("short") && in.equals("java.lang.Short"))
			return true;
		else if (def.equals("long") && in.equals("java.lang.Long"))
			return true;
		else if (def.equals("byte") && in.equals("java.lang.Byte"))
			return true;
		else if (def.equals("char") && in.equals("java.lang.Character"))
			return true;
		else if (def.equals("double") && in.equals("java.lang.Double"))
			return true;
		else if (def.equals("float") && in.equals("java.lang.Float"))
			return true;
		else if (def.equals("boolean") && in.equals("java.lang.Boolean"))
			return true;

		if (def.equals("java.lang.Integer") && in.equals("java.lang.Integer"))
			return true;
		else if (def.equals("java.lang.Short") && in.equals("java.lang.Short"))
			return true;
		else if (def.equals("java.lang.Long") && in.equals("java.lang.Long"))
			return true;
		else if (def.equals("java.lang.Byte") && in.equals("java.lang.Byte"))
			return true;
		else if (def.equals("java.lang.Character") && in.equals("java.lang.Character"))
			return true;
		else if (def.equals("java.lang.Double") && in.equals("java.lang.Double"))
			return true;
		else if (def.equals("java.lang.Float") && in.equals("java.lang.Float"))
			return true;
		else if (def.equals("java.lang.Boolean") && in.equals("java.lang.Boolean"))
			return true;
		else
			return false;
	}

	/**
	 * Check whether the type with the name <CODE>in</CODE>
	 * represents an exact data type and so should not be used
	 * in the <code>checkInexact</code> comparison.
	 *
	 * @param in The name of the type to verify
	 * @return <code>true</code> if the type represents an exact data type.
	 */
	private boolean isExactType(String in) {
		return
				in.equals("java.lang.Integer") ||
				in.equals("int") ||
				in.equals("java.lang.Short") ||
				in.equals("short") ||
				in.equals("java.lang.Long") ||
				in.equals("long") ||
				in.equals("java.lang.Byte") ||
				in.equals("byte") ||
				in.equals("java.lang.Character") ||
				in.equals("char") ||
				in.equals("java.lang.Boolean") ||
				in.equals("boolean");
	}

	/**
	 * Combine side by side the actual and expected values.
	 * @param <T> the type of data that is to be shown
	 * @param actual the actual value
	 * @param expected the expected value
	 * @return a <code>String</code> with two values
	 * side-by-side, with a marker for the first diff.
	 */
	private <T> String combine(T actual, T expected){
		return
				"actual:                                 expected:\n" +
				Printer.combineActualExpected(Printer.produceString(actual),
						Printer.produceString(expected))
						+ "\n";
	}

	/**
	 * Produce actual value  and a list of expected values
	 * used in 'one-of' comparisons.
	 * @param <T>
	 * @param actual the actual value produced by the test
	 * @param expected the expected result of the test
	 * @return combined <code>String</code> that shows the result
	 */
	private <T> String combineOneOf(T actual, T... expected){
		return "actual:   "   + Printer.produceString(actual) +
				"\nexpected: \n" + Printer.produceString(expected) + "\n";
	}

	/**
	 * Produce actual and expected values of an <code>Iterable</code> type,
	 * side-by-side, marking the first place where they differ.
	 * @param <T>
	 * @param actual the actual value produced by the test
	 * @param expected the expected result of the test
	 * @return combined <code>String</code> that shows the result
	 */
	private <T> String combineIterable(Iterable<T> actual,
			Iterable<T> expected){
		return
				"actual:                                 expected:\n" +
				Printer.combineActualExpected(Printer.produceIterableStrings(actual),
						Printer.produceIterableStrings(expected))
						+ "\n";
	}

	/**
	 * Produce actual and expected values of an <code>Traversal</code> type,
	 * side-by-side, marking the first place where they differ.
	 * @param <T>
	 * @param actual the actual value produced by the test
	 * @param expected the expected result of the test
	 * @return combined <code>String</code> that shows the result
	 */
	private <T> String combineTraversal(Traversal<T> actual,
			Traversal<T> expected){
		return
				"actual:                                 expected:\n" +
				Printer.combineActualExpected(Printer.produceTraversalStrings(actual),
						Printer.produceTraversalStrings(expected))
						+ "\n";
	}


	/*--------------------------------------------------------------------*/
	/*--------------------- TEST REPORTING SECTION -----------------------*/
	/*--------------------------------------------------------------------*/

	/**
	 * General contractor to report test results - invokes the success or
	 * the error reporter; invokes teh stack trace generator if the test failed
	 *
	 * @param success Did the test succeed?
	 * @param testname The name of this test
	 * @param result The <code>String</code> that represents the value of the result
	 * @return <code>true</code> if we are reporting success
	 */
	private boolean report(boolean success, String testname, String result) {

		if (success)
			return this.reportSuccess(testname, result);
		else {
			// get the relevant stack trace for this test case
			String trace = this.getStackTrace();
			return this.reportErrors(testname + "\n" + trace, result);
		}
	}

	/**
	 * General contractor to report test results for range check
	 *
	 * @param success Did the test succeed?
	 * @param testname The name of this test
	 * @param actual The computed value of the result
	 * @param low The low value (inclusive)of the range
	 * @param high The high value (exclusive) of the range
	 * @return <code>true</code> if we are reporting success
	 */
	private boolean report(boolean success, String testname, Object actual,
			Object low, Object high) {
		if (success)
			return this.reportSuccess(testname, actual, low, high);
		else {
			// get the relevant stack trace for this test case
			String trace = this.getStackTrace();
			return this.reportErrors(testname + "\n" + trace, actual, low, high);
		}
	}

	/**
	 * Add a test to the list of failed tests in the case when no actual
	 * and expected values are to be reported.
	 *
	 * @param testname The name of the failed test
	 * @param result The resulting message of the test
	 * @return <code>false</code>
	 */
	private boolean reportErrors(String testname, String result) {

		// add test report to the error report and the full test report
		return this.addError("Error in test number " + (this.numberOfTests + 1)
				+ "\n" + testname
				+ this.insertWarning()
				+ result + "\n");
	}

	/**
	 * Add a range test to the list of failed tests
	 *
	 * @param testname The name of the failed range test
	 * @param actual The computed value of the test
	 * @param low The low (inclusive) value of the range
	 * @param high The high (exclusive) value of the range
	 * @return <code>false</code>
	 */
	private boolean reportErrors(String testname, Object actual, Object low,
			Object high) {

		// add test report to the error report and the full test report
		return this.addError("Error in range test number "
				+ (this.numberOfTests + 1) + "\n" + testname + "\n"
				+ this.insertWarning()
				+ "actual:    " + Printer.produceString(actual) + "\n"
				+ "low:       " + Printer.produceString(low) + "\n"
				+ "high:      "
				+ Printer.produceString(high) + "\n");
	}

	/**
	 * Produce a formatted String that represent the relevant entries of the
	 * <CODE>StackTrace</CODE> that provides a link to the test case that
	 * produced the error.
	 *
	 * @return a formatted String representation of the relevant stack trace
	 */
	private String getStackTrace() {
		try {
			// force an exception so you can inspect the stack trace
			throw new ErrorReport("Error trace:");
		} catch (ErrorReport e) { // catch the exception
			// record the original stack trace
			StackTraceElement[] ste = e.getStackTrace();

			// copy only the relevant entries
			int length = Array.getLength(ste);
			StackTraceElement[] tmpSTE = new StackTraceElement[length];
			int ui = 0; // index for the stripped stack trace
			for (int i = 3; i < length; i++) {
				String cname = ste[i].getClassName();
				if (!((cname.startsWith("tester."))
						|| (cname.startsWith("sun.reflect"))
						|| (cname.startsWith("java.lang"))
						|| (cname.startsWith("bluej")) || (cname
								.startsWith("__SHELL")))) {
					tmpSTE[ui] = ste[i];
					ui = ui + 1;
				}
			}

			// we cannot have null entries at the end
			StackTraceElement[] userSTE = new StackTraceElement[ui];
			for (int i = 0; i < ui; i++) {
				userSTE[i] = tmpSTE[i];
			}

			// now set the stack trace so it can be converted to a String
			e.setStackTrace(userSTE);

			StringWriter writer = new StringWriter();
			PrintWriter printwriter = new PrintWriter(writer);
			e.printStackTrace(printwriter);

			return writer.toString();
		}
	}

	/**
	 * Add a test to the list of successful tests in the case when no actual
	 * and expected values are to be reported.
	 *
	 * @param testname The name of the successful test
	 * @param result The resulting message of the test
	 * @return <code>true</code>
	 */
	private boolean reportSuccess(String testname, String result) {

		// add test report to the full test report
		return this.addSuccess("Success in the test number "
				+ (this.numberOfTests + 1) + "\n" + testname + "\n"
				+ this.insertWarning()
				+ result + "\n");
	}

	/**
	 * Add a test to the list of successful tests
	 *
	 * @param testname The name of the successful range test
	 * @param actual The computed value of the test
	 * @param low The low (inclusive) value of the range
	 * @param high The high (exclusive) value of the range
	 * @return <code>true</code>
	 */
	private boolean reportSuccess(String testname, Object actual,
			Object low, Object high) {

		// add test report to the full test report
		return this.addSuccess("Success in the range test number "
				+ (this.numberOfTests + 1) + "\n" + testname + "\n"
				+ this.insertWarning()
				+ "actual:   " + Printer.produceString(actual) + "\n"
				+ "low:      " + Printer.produceString(low) + "\n"
				+ "high:     "
				+ Printer.produceString(high) + "\n");
	}

	/**
	 * Produce a warning message and increase the count of warnings if the
	 * comparison involved inexact numbers.
	 *
	 * @return an empty <code>String</code> if no warning,
	 * a message when warning is issued.
	 */
	private String insertWarning(){
		if (Inspector.INEXACT_COMPARED){
			this.warnings = this.warnings + 1;
			return "The comparison involved inexact numbers " +
			"with relative tolerance " + Inspector.TOLERANCE + "\n";
		}
		else
			return "";
	}

	/**
	 * Add the given test successful test result to the full report
	 *
	 * @param testResult The successful test result
	 * @return <code>true</code>
	 */
	private boolean addSuccess(String testResult) {
		// update the count of all tests
		this.numberOfTests = this.numberOfTests + 1;
		this.fullTestResults = this.fullTestResults.append("\n" + testResult);
		return true;
	}

	/**
	 * Add the given test failed test result to the full report and to the
	 * error report
	 *
	 * @param testResult The failed test result
	 * @return <code>false</code>
	 */
	private boolean addError(String testResult) {
		// update the count of all tests
		this.numberOfTests = this.numberOfTests + 1;
		// update the count of the errors tests
		this.errors = this.errors + 1;

		this.failedResults = this.failedResults.append("\n" + testResult);
		this.fullTestResults = this.fullTestResults.append("\n" + testResult);
		return false;
	}

	/**
	 * Produce a <code>String</code> describing the number of tests that were
	 * run and that failed.
	 * @return the desired <code>String</code>
	 */
	private String testCount() {
		String tCount = "";

		// report test totals
		if (this.numberOfTests == 1) {
			tCount = "\nRan 1 test.\n";
		} else if (this.numberOfTests > 1) {
			tCount = "\nRan " + this.numberOfTests + " tests.\n";
		}

		// report error totals
		if (this.errors == 0) {
			tCount = tCount + "All tests passed.\n";
		}
		if (this.errors == 1) {
			tCount = tCount + "1 test failed.\n";

		} else if (this.errors > 1) {
			tCount = tCount + this.errors + " tests failed.\n";
		}

		// report warnings totals
		if (this.warnings == 0) {
			tCount = tCount + "\n";
		}
		if (this.warnings == 1) {
			tCount = tCount + "Issued 1 warning of inexact comparison.\n\n";

		} else if (this.warnings > 1) {
			tCount = tCount + "Issued " + this.warnings +
					" warnings of inexact comparison.\n\n";
		}

		return tCount;
	}

	/**
	 * Report on the number and nature of failed tests
	 */
	protected void testReport() {
		System.out.print(testCount());
		if (this.errors > 0)
			System.out.println(this.failedResults);
		System.out.println("--- END OF TEST RESULTS ---");
	}

	/**
	 * Produce test names and values compared for all tests
	 */
	protected void fullTestReport() {
		System.out.println(testCount() + this.fullTestResults
				+ "\n--- END OF FULL TEST RESULTS ---");
	}

	/*--------------------------------------------------------------------*/
	/*-------------- TESTER INVOCATION HELPERS SECTION -------------------*/
	/*--------------------------------------------------------------------*/

	/**
	 * A hook to run the tester for any object --- needed to run from BlueJ.
	 * Print all fields of the 'Examples' class and a report of all failed
	 * tests.
	 *
	 * @param obj
	 *            The 'Examples' class instance where the tests are defined
	 */
	public static void run(Object obj) {
		Tester t = new Tester();
		t.runAnyTests(obj, false, true);
	}

	/**
	 * A hook to run the tester for a collection of objects
	 * and produce a full test report
	 *
	 * @param objs An array of 'Examples' class instances where tests are defined
	 */
	public static void runFullReport(Object... objs) {
		Tester t = new Tester();
		if(objs != null){
			for(Object obj : objs){
				t.runAnyTests(obj, true, true);
			}
		}
	}

	/**
	 * A hook to run the tester for any object and produce a test report.
	 * The user selects whether to print all tests and all data
	 *
	 * @param obj The 'Examples' class instance where the tests are defined
	 */
	public static void runReport(Object obj, boolean full, boolean printall) {
		Tester t = new Tester();
		t.runAnyTests(obj, full, printall);
	}

	/**
	 * A hook to run the tester for a collection of objects and produce
	 * the specified test reports
	 *
	 * @param full true if all test results should be reported
	 * @param printall true if all data should be displayed
	 * @param objs An array of 'Examples' class instances where tests are defined
	 */
	public static void runReports(boolean full, boolean printall,
			Object... objs) {
		Tester t = new Tester();
		if(objs != null){
			for(Object obj : objs){
				t.runAnyTests(obj, full, printall);
			}
		}
	}
}