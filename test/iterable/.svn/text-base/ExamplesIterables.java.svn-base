package iterable;

import java.util.Arrays;
import java.util.List;

import tester.Tester;

/**
 * <P>Class to test the exact and inexact comparison of two lists using the
 * <CODE>checkIterable</CODE> and <CODE>checkInexactIterable</CODE> in the 
 * <CODE>{@link Tester Tester}</CODE> class.</P>
 * 
 * @author Virag Shah
 * @since 17 March 2011
 *
 */
public class ExamplesIterables {

	/**
	 * create instances of CartPtDouble class
	 */
	CartPtDouble cpt1 = new CartPtDouble(3.0, 4.0);
	CartPtDouble cpt2 = new CartPtDouble(7.0, 7.0);
	CartPtDouble cpt2a = new CartPtDouble(7.0, 6.9995); // p2a is very close to p2 but not exactly
	CartPtDouble cpt3 = new CartPtDouble(15.0, 8.0);

	/**
	 * create 3 lists of CartPtDouble elements
	 */
	List<CartPtDouble> list1 = Arrays.asList(cpt1, cpt2, cpt3);
	List<CartPtDouble> list2 = Arrays.asList(cpt1, cpt2a, cpt3); // list2 is very close to list1 but not exactly
	List<CartPtDouble> list3 = Arrays.asList(cpt1, cpt3);
	List<CartPtDouble> list4 = Arrays.asList(cpt1, cpt3);

	/**
	 * <P>Tests to perform exact and inexact comparison of two lists using the
	 * <CODE>checkIterable</CODE> and <CODE>checkInexactIterable</CODE> in the 
	 * <CODE>{@link Tester Tester}</CODE> class.</P>
	 * 
	 * @param t the <CODE>{@link tester.Tester Tester}</CODE> that performs the tests
	 */
	public void testIterables(Tester t) {

		t.checkIterable(
				list3,  // 1st iterable
				list4,  // 2nd iterable
		"Success: same lists"); // test name

		t.checkIterable(
				list1,  // 1st iterable (3 elements)
				list4,  // 2nd iterable (2 elements)
		"Should fail: not the same lists"); // test name

		t.checkIterable(
				list1,  // 1st iterable
				list2,  // 2nd iterable
		"Should fail: should be checkInexactIterable"); // test name

		t.checkInexactIterable(
				list1, // 1st iterable
				list2, // 2nd iterable
				0.01,  // tolerance
		"Success: Test Inexact Iterable");  // test name

		t.checkInexactIterable(
				list3,  // 1st iterable
				list4,  // 2nd iterable
				0.01,   // tolerance
		"Success: same lists"); // test name

		t.checkInexactIterable(
				list1,  // 1st iterable
				list2,  // 2nd iterable
				-0.1,   // tolerance
		"Should fail: tolerance < 0");  //test name

		t.checkInexactIterable(
				list1,  // 1st iterable (3 elements)
				list3,  // 2nd iterable (2 elements)
				0.01,   // tolerance
		"Should fail: not the same lists");  // test name
	}

	/**
	 * <P>Display all data defined in the <CODE>{@link ExamplesIterables ExamplesIterables}</CODE> 
	 * class.</P>
	 * <P>Run all tests defined in the <CODE>{@link ExamplesIterables ExamplesIterables}</CODE> 
	 * class.</P>
	 */
	public static void main(String[] argv)
	{
		ExamplesIterables ei = new ExamplesIterables();

		System.out.println("Show all data defined in the ExamplesIterables class:");
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
