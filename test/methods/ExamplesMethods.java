package methods;

import tester.Tester;

/**
 * <P>Class that test the <CODE>checkMethod</CODE> and <CODE>checkInexactMethod</CODE>
 * in the <CODE>{@link Tester Tester}</CODE> class.</P>
 * 
 * @author Virag Shah
 * @since 15 March 2011
 *
 */
public class ExamplesMethods {

	Song song1 = new Song("title1", 4);
	Song song2 = new Song("title2", 2);
	Song song1a = new Song("title1", 4);

	CartPtDouble pt = new CartPtDouble(3.0, 4.0);

	/**
	 * Tests for <CODE>checkMethod</CODE>
	 * 
	 * @param t the <CODE>{@link Tester Tester}</CODE> that performs the tests
	 */
	public void testMethod(Tester t) {
		/**
		 * test this.song1.shorter(this.song2) -- expect song2
		 */
		t.checkMethod("Success: Check if you get the shorter song",
				this.song2,       // expected value
				this.song1,       // object that invokes the method
				"shorter",        // method name
				this.song2);      // method arguments
		/**
		 * test this.song1.shorter(this.song2) -- expect song2
		 */
		t.checkMethod("Success: Check if you get the shorter song",
				this.song2,       // expected value
				this.song2,       // object that invokes the method
				"shorter",        // method name
				this.song1);      // method arguments
		/**
		 * test this.song1.shorter(this.song2) -- expect song1
		 */
		t.checkMethod("Should fail: Check if you get the shorter song",
				this.song1,       // expected value
				this.song1,       // object that invokes the method
				"shorter",        // method name
				this.song2);      // method arguments
		/**
		 * test this.song1.shorter(this.song2) -- expect song1
		 */
		t.checkMethod("Should fail: Check if you get the shorter song",
				this.song1,       // expected value
				this.song2,       // object that invokes the method
				"shorter",        // method name
				this.song1);      // method arguments

		/**
		 * test song1.tenMinutesTotal(song1a, song2) -- expect true
		 */
		t.checkMethod("Success: Check if the total playing time is 10",
				true,                       // expected value
				this.song1,                // object that invokes the method
				"tenMinutesTotal",         // method name
				this.song1a, this.song2);  // method arguments
		/**
		 * test song1.tenMinutesTotal(song1a, song2) -- expect false
		 */
		t.checkMethod("Success: Check if the total playing time is 10",
				false,                       // expected value
				this.song1,                // object that invokes the method
				"tenMinutesTotal",         // method name
				this.song1a, this.song1a);  // method arguments
		/**
		 * test song1.tenMinutesTotal(song1a, song2) -- expect false
		 */
		t.checkMethod("Should fail: Check if the total playing time is 10",
				false,                       // expected value
				this.song1,                // object that invokes the method
				"tenMinutesTotal",         // method name
				this.song1a, this.song2);  // method arguments
		/**
		 * test song1.tenMinutesTotal(song1a, song2) -- expect true
		 */
		t.checkMethod("Should fail: Check if the total playing time is 10",
				true,                       // expected value
				this.song1,                // object that invokes the method
				"tenMinutesTotal",         // method name
				this.song1a, this.song1a);  // method arguments
	}

	/**
	 * Tests for <CODE>checkInexactMethod</CODE>
	 * 
	 * @param t the <CODE>{@link Tester Tester}</CODE> that performs the tests
	 */
	public void testInexactMethod(Tester t) {

		t.checkMethod("Success: Exact check", 5.0, this.pt, "distTo0");

		/**
		 * Tests for distTo0 method
		 */
		t.checkInexactMethod("Success: Inexact check",
				4.998,
				0.001,
				this.pt,
		"distTo0");

		t.checkInexactMethod("Should fail: Inexact check",
				4.998,
				0.0001,
				this.pt,
		"distTo0");	

		/**
		 * Tests for distTo method
		 */
		t.checkInexactMethod("Success: Inexact check",
				4.998,
				0.001,
				this.pt,
				"distTo", 
				new CartPtDouble(7.0, 7.0));  	

		t.checkInexactMethod("Should fail: Inexact check",
				4.998,
				0.0001,
				this.pt,
				"distTo", 
				new CartPtDouble(7.0, 7.0));  	
	}

	/**
	 * <P>Display all data defined in the <CODE>{@link ExamplesMethods ExamplesMethods}</CODE> 
	 * class.</P>
	 * <P>Run all tests defined in the <CODE>{@link ExamplesMethods ExamplesMethods}</CODE> 
	 * class.</P>
	 */
	public static void main(String[] argv) {

		ExamplesMethods em = new ExamplesMethods();

		System.out.println("Show all data defined in the ExamplesMethods class:");
		System.out.println("\n\n---------------------------------------------------");
		System.out.println("Invoke tester.runReport(this, true, true):");
		System.out.println("Print all data, all test results");

		Tester.runReport(em, true, true);

		System.out.println("\n---------------------------------------------------");
		System.out.println("\n---------------------------------------------------");
		System.out.println("\n---------------------------------------------------");
		System.out.println("Invoke tester.runReport(this, false, false, true):");
		System.out.println("Print no data, all test results, no warnings");

		Tester.runReport(em, false, false);
	}

}