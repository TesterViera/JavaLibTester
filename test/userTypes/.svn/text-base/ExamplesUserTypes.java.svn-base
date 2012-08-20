package userTypes;

import tester.Tester;

/** 
 * <P> Test suite for handling user defined types.
 * The equality of any two instances of user defined classes is determined by 
 * comparing the pairs of values for each field defined for that class. 
 * If the field value is an instance of another class, the comparison recursively 
 * continues the comparison of the two values. Circularity in data definitions is detected.
 * </P>
 * <P> The comparison includes all public, protected, and private fields, both those 
 * declared in this class, and those declared in the super classes. The static, 
 * volatile, and transient fields are not included in the comparison.
 * </P>
 * If the objects that are being compared contain a field of the type <CODE>double</CODE>
 *  or <CODE>float</CODE> (or their wrapper classes), the 
 *  <CODE>{@link tester.Tester#checkInexact(Object, Object, double, String) Inexact}</CODE> 
 *  variant of the test method must be used.
 *  
 * @author Virag Shah
 * @since 15 February 2011
 * 
 */
public class ExamplesUserTypes {

	/** Construct instances of Author */
	Author js = new Author("John Steinbeck", 66);
	Author jkr = new Author("J K Rowling", 45);

	/** Construct instances of Book */
	Book tp = new Book("The Pearl", js);
	Book eoe = new Book("East of Eden", js);
	Book hp = new Book("Harry Potter", jkr, 100.50f);
	Book omam = new Book("Of Mice and Men", js, 75.25f);

	/** Perform tests for user defined types
	 * @param t the <CODE>{@link tester.Tester Tester}</CODE> that performs the tests
	 */
	public void testBooks(Tester t) {

		/** Tests for Exact comparisons */

		/** compares two instances of Author that are equal */
		t.checkExpect(js, new Author("John Steinbeck", 66), 
		"Success - t.checkExpect(js, new Author(John Steinbeck, 66))");

		/** compares two instances of Author that are not equal. Age is different */
		t.checkFail(jkr, new Author("J K Rowling", 44), 
		"Test to fail - t.checkFail(jkr, new Author(J K Rowling, 44))");

		/** compares two different instances of Author that are not equal */
		t.checkFail(jkr, js, "Test to fail - t.checkFail(jkr, js)");

		/** compares two instances of Book that are equal */
		t.checkExpect(tp, new Book ("The Pearl", js), 
		"Success - t.checkExpect(tp, new Book (The Pearl, js))");

		/** compares two instances of Book that are equal. A variant of above test */
		t.checkExpect(tp, new Book ("The Pearl", new Author("John Steinbeck", 66)), 
		"Success - t.checkExpect(tp, new Book (The Pearl, new Author(John Steinbeck, 66)))");

		/** compares two instances of Book that are not equal */
		t.checkFail(tp, new Book ("The Pearl", jkr), 
		"Test to fail - t.checkFail(tp, new Book (The Pearl, jkr))");

		/** compares two instances of Book that are not equal */
		t.checkFail(tp, new Book ("Harry Potter", js), 
		"Test to fail - t.checkFail(tp, new Book (Harry Potter, js))");

		/** compares two instances of Book that are not equal. Age is different */
		t.checkFail(tp, new Book ("The Pearl", new Author("John Steinbeck", 65)), 
		"Test to fail - t.checkFail(tp, new Book (The Pearl, new Author(John Steinbeck, 65)))");

		/** compares two different instances of Book that are not equal */
		t.checkFail(tp, eoe, "Test to fail - t.checkFail(tp, eoe)");


		/** Tests for Inexact comparisons */

		/** compares two instances of Book that are equal */
		t.checkInexact(hp, new Book("Harry Potter", jkr, 100.0f), 0.01, 
		"Success - t.checkInexact(hp, new Book(Harry Potter, jkr, 100.0f), 0.01)");

		/** compares two instances of Book that are equal. A variant of above test */
		t.checkInexact(hp, new Book ("Harry Potter", 
				new Author("J K Rowling", 45), 100.50f), 0.01, 
				"Success - t.checkInexact(hp, new Book (Harry Potter, " +
		"new Author(J K Rowling, 45), 100.50f), 0.01)");

		/** compares two instances of Book that are not equal. Author is different */
		t.checkInexactFail(hp, new Book ("Harry Potter", js), 0.01, 
		"Test to fail - t.checkInexactFail(hp, new Book (Harry Potter, js), 0.01)");

		/** compares two instances of Book that are not equal. Price not specified */
		t.checkInexactFail(hp, new Book ("Harry Potter", jkr), 0.01, 
		"Test to fail - t.checkInexactFail(hp, new Book (Harry Potter, jkr), 0.01)");

		/** compares two instances of Book that are not equal. Tolerance value not enough */
		t.checkInexactFail(hp, new Book ("Harry Potter", 
				new Author("J K Rowling", 45), 100.49f), 0.0000001, 
				"Test to fail - t.checkInexactFail(hp, new Book (Harry Potter, " +
		"new Author(J K Rowling, 45), 100.49f), 0.0000001)");

		/** compares two instances of Book that are not equal. One value is zero.
		 * Tolerance value not enough */
		t.checkInexactFail(hp, new Book ("Harry Potter", 
				new Author("J K Rowling", 45), 0.0f), 1, 
				"Test to fail - t.checkInexactFail(hp, new Book (Harry Potter, " +
		"new Author(J K Rowling, 45), 0.0f), 1)");

		/** compares two instances of Book that are not equal. Age is different */
		t.checkInexactFail(hp, new Book ("Harry Potter", 
				new Author("J K Rowling", 44), 100.50f), 0.01, 
				"Test to fail - t.checkInexactFail(hp, new Book (Harry Potter, " +
		"new Author(J K Rowling, 44), 100.50f), 0.01)");

		/** compares two different instances of Book that are not equal */
		t.checkInexactFail(hp, omam, 0.5, "Test to fail - t.checkInexactFail(hp, omam, 0.5)");	

	}

	/**
	 * <P>Display all data defined in the <CODE>{@link ExamplesUserTypes ExamplesUserTypes}</CODE> 
	 * class.</P>
	 * <P>Run all tests defined in the <CODE>{@link ExamplesUserTypes ExamplesUserTypes}</CODE> 
	 * class.</P>
	 */
	public static void main(String[] argv) {
		ExamplesUserTypes eut = new ExamplesUserTypes();

		System.out.println("Show all data defined in the ExamplesUserTypes class:");
		System.out.println("\n\n---------------------------------------------------");
		System.out.println("Invoke tester.runReport(this, true, true):");
		System.out.println("Print all data, all test results");

		Tester.runReport(eut, true, true);

		System.out.println("\n---------------------------------------------------");
		System.out.println("\n---------------------------------------------------");
		System.out.println("\n---------------------------------------------------");
		System.out.println("Invoke tester.runReport(this, false, false, true):");
		System.out.println("Print no data, all test results, no warnings");

		Tester.runReport(eut, false, false);
	}
}
