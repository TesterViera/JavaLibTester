package rangeCheck;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Comparator;

import tester.Tester;

/**
 * <P>At times we just want to make sure the value falls within the
 * given range. The tester supports three variants of range checks.
 * For every variant the programmer supplies the expected value and
 * the lower and upper bounds for the desired range. Additional
 * arguments may specify whether the bounds are inclusive or exclusive
 * (see below).</P>
 *
 * <P>The first variant, the method
 * <CODE>{@link tester.Tester#checkNumRange}</CODE>, defines the
 * comparison of arbitrary numerical values, allowing (for example)
 * for mixing <CODE>double</CODE> range with <CODE>int</CODE> expected
 * values and vice versa:
 * <CODE>
 *    <P>t.checkNumRange(5, 0, 4.9, "Should fail");</P>
 *    <P>t.checkNumRange(5, 0, 5.0, false, true, "succeeds: inclusive upper bound");</P>
 *    <P>t.checkNumRange(0.0, 0, 5.0, false, true, "Should fail: exclusive lower bound");</P>
 * </CODE></P>
 *
 * <P>The second variant, the method
 * <CODE>{@link tester.Tester#checkRange(Comparable, Object, Object)}</CODE>,
 * accepts the actual value (and the desired lower and upper bounds)
 * of the type that implements the <CODE>{@link Comparable}</CODE>
 * interface. All wrapper classes for the primitive types implement
 * this interface, but require that the argument matches the invoking
 * wrapper class. That means, for example, that we can use this variant
 * to compare the actual <CODE>int</CODE> value within <CODE>int</CODE>
 * bounds, but need to use the <CODE>{@link tester.Tester#checkNumRange}</CODE>
 * variant if the types are mixed.</P>
 *
 * <P>The third variant, the method
 * <CODE>{@link tester.Tester#checkRange(Object, Object, Object, java.util.Comparator)}</CODE>,
 * accepts three values of arbitrary type <T> together with an Comparator<T>
 * object that allows us to compare the actual value with the given lower
 * and upper bounds.</P>
 *
 * <P>In all three variants, by default, the test uses the inclusive
 * lower bound and the exclusive upper bound . The user may supply two
 * additional boolean values that determine whether the lower bound and
 * the upper bound should be inclusive.</P>
 * 
 * @author Virag Shah
 * @since 25 February 2011
 * 
 */
public class ExamplesRangeCheck {
	/**
	 * <p>Tests for the checkRange for all primitive types and for
	 * <code>String</code></p>
	 * <p>To facilitate multiple failed tests we use the imperative style for
	 * the test case definitions and the test method.</p>
	 *
	 * @param t the <CODE>{@link Tester Tester}</CODE> that performs the tests
	 */
	public void testPrimitiveRange(Tester t) {

		/**
		 * Int test
		 */
		t.checkRange(3, 3, 5, "Success: int: [3 <= 3 < 5)");
		t.checkRange(4, 2, 5, "Success: int: [2 <= 4 < 5)");
		t.checkRange(5, 3, 5, false, true, "Success: int: (3 < 5 <= 5]");
		t.checkRange(3, 3, 5, false, true, "Should fail: int: (3 < 3 <= 5]");
		t.checkRange(-2, 3, 5, "Should fail: int: [3 <= -2 < 5)");
		t.checkRange(5, 3, 5, "Should fail: int: [3 <= 5 < 5)");
		t.checkRange(8, 3, 5, "Should fail: int: [3 <= 8 < 5)");

		/**
		 * Short test
		 */
		short sminus2 = -2;
		short s3 = 3;
		short s4 = 4;
		short s5 = 5;
		short s8 = 8;
		t.checkRange(s3, s3, s5, "Success: short: [3 <= 3 < 5)");
		t.checkRange(s4, s3, s5, "Success: short: [3 <= 4 < 5)");
		t.checkRange(s5, s3, s5, false, true, "Success: short: (3 < 5 <= 5]");
		t.checkRange(s3, s3, s5, false, true, "Should fail: short: (3 < 3 <= 5]");
		t.checkRange(sminus2, s3, s5, "Should fail: short: [3 <= -2 < 5)");
		t.checkRange(s5, s3, s5, "Should fail: short: [3 <= 5 < 5)");
		t.checkRange(s8, s3, s5, "Should fail: short: [3 <= 8 < 5)");

		/**
		 * Long test
		 */
		long lminus2 =-2;
		long l3 = 3;
		long l4 = 4;
		long l5 = 5;
		long l8 = 8;
		t.checkRange(l3, l3, l5, "Success: long: [3 <= 3 < 5)");
		t.checkRange(l4, l3, l5, "Success: long: [3 <= 4 < 5)");
		t.checkRange(l5, l3, l5, false, true, "Success: long: (3 < 5 <= 5]");
		t.checkRange(l3, l3, l5, false, true, "Should fail: long: (3 < 3 <= 5]");
		t.checkRange(lminus2, l3, l5, "Should fail: long: [3 <= -2 < 5)");
		t.checkRange(l5, l3, l5, "Should fail: long: [3 <= 5 < 5)");
		t.checkRange(l8, l3, l5, "Should fail: long: [3 <= 8 < 5)");

		/**
		 * Byte test
		 */
		byte bminus2 = -2;
		byte b3 = 3;
		byte b4 = 4;
		byte b5 = 5;
		byte b8 = 8;
		t.checkRange(b3, b3, b5, "Success: byte: [3 <= 3 < 5)");
		t.checkRange(b4, b3, b5, "Success: byte: [3 <= 4 < 5)");
		t.checkRange(b5, b3, b5, false, true, "Success: byte: (3 < 5 <= 5]");
		t.checkRange(b3, b3, b5, false, true, "Should fail: byte: (3 < 3 <= 5]");
		t.checkRange(bminus2, b3, b5, "Should fail: byte: [3 <= -2 < 5)");
		t.checkRange(b5, b3, b5, "Should fail: byte: [3 <= 5 < 5)");
		t.checkRange(b8, b3, b5, "Should fail: byte: [3 <= 8 < 5)");

		/**
		 * Boolean test
		 */
		boolean btrue = true;
		boolean bfalse = false;
		t.checkRange(false, bfalse, btrue, "Success: boolean: [false <= false < true)");
		t.checkRange(true, bfalse, btrue, "Should fail: boolean: [false <= true < true)");

		/**
		 * Char test
		 */
		char m = 'm';
		char p1 = 'p';
		char p2 = 'p';
		char q = 'q';
		char r1 = 'r';
		char r2 = 'r';
		char s = 's';
		t.checkRange(p1, p2, r1, "Success: char: [p <= p < r)");
		t.checkRange(q, p2, r1, "Sucess: char: [p <= q < r)");
		t.checkRange(m, p2, r1, "Should fail: char: [p <= m < r)");
		t.checkRange(r2, p2, r1, "Should fail: char: [p <= r < r)");
		t.checkRange(s, p2, r1, "Should fail: char: [p <= s < r)");

		/**
		 * Float test
		 */
		float fminus2 = -2.0f;
		float f3 = 3.0f;
		float f4 = 4.0f;
		float f5 = 5.0f;
		float f8 = 8.0f;
		t.checkRange(f3, f3, f5, "Success: float: [3.0 <= 3.0 < 5.0)");
		t.checkRange(f4, f3, f5, "Success: float: [3.0 <= 4.0 < 5.0)");
		t.checkRange(f5, f3, f5, false, true, "Success: float: (3.0 < 5.0 <= 5.0]");
		t.checkRange(f3, f3, f5, false, true, "Should fail: float: (3.0 < 3.0 <= 5.0]");
		t.checkRange(fminus2, f3, f5, "Should fail: float: [3.0 <= -2.0 < 5.0)");
		t.checkRange(f5, f3, f5, "Should fail: float: [3.0 <= 5.0 < 5.0)");
		t.checkRange(f8, f3, f5, "Should fail: float: [3.0 <= 8.0 < 5.0)");

		/**
		 * Double test
		 */
		double dminus2 = -2.0;
		double d3 = 3.0;
		double d4 = 4.0;
		double d5 = 5.0;
		double d8 = 8.0;
		t.checkRange(d3, d3, d5, "Success: double: [3.0 <= 3.0 < 5.0)");
		t.checkRange(d4, d3, d5, "Success: double: [3.0 <= 4.0 < 5.0)");
		t.checkRange(d5, d3, d5, false, true, "Success: double: (3.0 < 5.0 <= 5.0]");
		t.checkRange(d3, d3, d5, false, true, "Should fail: double: (3.0 < 3.0 <= 5.0]");
		t.checkRange(dminus2, d3, d5, "Should fail: double: [3.0 <= -2.0 < 5.0)");
		t.checkRange(d5, d3, d5, "Should fail: double: [3.0 <= 5.0 < 5.0)");
		t.checkRange(d8, d3, d5, "Should fail double: [3.0 <= 8.0 < 5.0)");

		/**
		 * String test
		 */
		String aaa = "aaa";
		String abc1 = "abc";
		String abc2 = "abc";
		String bcd = "bcd";
		String cde = "cde";
		String cde2 = "cde";
		String def = "def";
		t.checkRange(abc2, abc1, cde, "Success: String: [abc <= abc < cde)");
		t.checkRange(bcd, abc1, cde, "Success: String: [abc <= bcd < cde)");
		t.checkRange(aaa, abc1, cde, "Should fail: String: [abc <= aaa < cde)");
		t.checkRange(cde, abc1, cde2, "Should fail: String: [abc <= cde < cde)");
		t.checkRange(def, abc1, cde, "Should fail: String: [abc <= def < cde)");
	}

	/**
	 * Test the range checking of <CODE>{@link java.lang.Number}</CODE> instances
	 * via invoking  <CODE>{@link Tester#checkNumRange}</CODE> method.
	 *
	 * @param t the <CODE>{@link Tester Tester}</CODE> that performs the tests
	 */
	public void testNumRange(Tester t) {

		/**
		 * checkNumRange tests
		 */
		t.checkNumRange(1.0, 1, 2.0, "Success: Num range: [1 <= 1.0 < 2.0)");
		t.checkNumRange(1, 1.0, 2.0, "Success: Num range: [1.0 <= 1 < 2.0)");
		t.checkNumRange(1, 1, 2.0, "Success: Num range: [1 <= 1 < 2.0)");
		t.checkNumRange(1.0, 1.0, 2.0, "Success: Num range: [1.0 <= 1.0 < 2.0)");
		t.checkNumRange(2, 1.0, 2.0, false, true, "Success: Num range: (1.0 < 2 <= 2.0]");
		t.checkNumRange(1.0, (long) 1, 2.0, "Success: Num range: [(long) 1 <= 1.0 < 2.0)");
		t.checkNumRange(1.0, (short) 1, 2.0,  "Success: Num range: [(short) 1 <= 1.0 < 2.0)");
		t.checkNumRange(1.0, new Float(1.0), 2.0, "Success: Num range: [new Float (1.0) <= 1.0 < 2.0)");
		t.checkNumRange(2, 1.0, 2.0, "Should fail: Num range: [1.0 <= 2 < 2.0]");
		t.checkNumRange(1, 2.0, 3.0,  "Should fail: Num range: [2.0 <= 1 < 3.0)");
		t.checkNumRange(4.0, 2.0, 3.0,  "Should fail: Num range: [2.0 <= 4.0 < 3.0)");


		BigInteger base = new BigInteger("10").pow(1); // create a big number 10^1
		BigInteger bi3 = base.add(new BigInteger("3"));   // 10^1 + 3
		BigInteger bi4 = base.add(new BigInteger("4"));   // 10^1 + 4

		t.checkNumRange(bi3, base, bi4,  "Success: Num range: [10^1 <= 10^1 + 3 < 10^1 + 4)");

		/** 
		 * this test case shows the limitation for the range of big numbers 
		 * being passed to checkNumRange() method -  due to 
		 * Number.doubleValue() conversion inside -
		 * to be fixed in the future releases of Tester library
		 */
		BigInteger baseHuge = new BigInteger("10").pow(1000); // create a huge number 10^1000
		BigInteger biHuge3 = baseHuge.add(new BigInteger("3"));   // 10^1000 + 3
		BigInteger biHuge4 = baseHuge.add(new BigInteger("4"));   // 10^1000 + 4

		t.checkNumRange(biHuge3, baseHuge, biHuge4,  
		"Success: Num range (***to be fixed!***): [10^1000 <= 10^1000 + 3 < 10^1000 + 4)");


		BigDecimal baseBD = new BigDecimal("10").pow(1); // create a big number 10^1
		BigDecimal bd3 = baseBD.add(new BigDecimal("0.3"));   // 10^1 + 3
		BigDecimal bd4 = baseBD.add(new BigDecimal("0.4"));   // 10^1 + 4

		t.checkNumRange(bd3, baseBD, bd4,  "Success: Num range: [10^1 <= 10^1 + 0.3 < 10^1 + 0.4)");

		/** 
		 * this test case shows the limitation for the range of big numbers 
		 * being passed to checkNumRange() method -  due to 
		 * Number.doubleValue() conversion inside -
		 * to be fixed in the future releases of Tester library
		 */
		BigDecimal baseHugeBD = new BigDecimal("10").pow(1000); // create a huge number 10^1000
		BigDecimal bdHuge3 = baseHugeBD.add(new BigDecimal("0.3"));   // 10^1000 + 3
		BigDecimal bdHuge4 = baseHugeBD.add(new BigDecimal("0.4"));   // 10^1000 + 4

		t.checkNumRange(bdHuge3, baseHugeBD, bdHuge4,  
		"Success: Num range (***to be fixed!***): [10^1000 <= 10^1000 + 0.3 < 10^1000 + 0.4)");
	}


	/**
	 * <p>Tests for the range check for data types that represent
	 * big numerical values:</p>
	 *
	 * @param t the <CODE>{@link Tester Tester}</CODE> that performs the tests
	 * @return true
	 */
	public void testBigNumbersRange(Tester t) {
		/**
		 * BigInteger test
		 */
		BigInteger base = new BigInteger("10").pow(1000); // create a big number
		BigInteger biminus2 = base.add(new BigInteger("-2"));
		BigInteger bi3 = base.add(new BigInteger("3"));
		BigInteger bi4 = base.add(new BigInteger("4"));
		BigInteger bi5 = base.add(new BigInteger("5"));
		BigInteger bi8 = base.add(new BigInteger("8"));
		t.checkRange(bi3, bi3, bi5, 
		"Success: BigInteger: [10^1000 + 3 <= 10^1000 + 3 < 10^1000 + 5)");
		t.checkRange(bi4, bi3, bi5, 
		"Success: BigInteger: [10^1000 + 3 <= 10^1000 + 4 < 10^1000 + 5)");
		t.checkRange(bi5, bi3, bi5, false, true, 
		"Success: BigInteger: (10^1000 + 2 < 10^1000 + 5 <= 10^1000 + 5]");
		t.checkRange(bi3, bi3, bi5, false, true, 
		"Should fail: BigInteger: (10^1000 + 3 < 10^1000 + 3 <= 10^1000 + 5]");
		t.checkRange(biminus2, bi3, bi5, 
		"Should fail: BigInteger: [10^1000 + 3 <= 10^1000 - 2 < 10^1000 + 5)");
		t.checkRange(bi5, bi3, bi5, 
		"Should fail: BigInteger: [10^1000 + 3 <= 10^1000 + 5 < 10^1000 + 5)");
		t.checkRange(bi8, bi3, bi5, 
		"Should fail: BigInteger: [10^1000 + 3 <= 10^1000 + 8 < 10^1000 + 5)");


		/**
		 * BigDecimal test
		 */
		BigDecimal baseBD = new BigDecimal("10").pow(1000); // create a big number
		BigDecimal bdminus2 = baseBD.add(new BigDecimal("-0.2"));
		BigDecimal bd3 = baseBD.add(new BigDecimal("0.3"));
		BigDecimal bd4 = baseBD.add(new BigDecimal("0.4"));
		BigDecimal bd5 = baseBD.add(new BigDecimal("0.5"));
		BigDecimal bd8 = baseBD.add(new BigDecimal("0.8"));
		t.checkRange(bd3, bd3, bd5, 
		"Success: BigDecimal: [10^1000 + 0.3 <= 10^1000 + 0.3 < 10^1000 + 0.5)");
		t.checkRange(bd4, bd3, bd5, 
		"Success: BigDecimal: [10^1000 + 0.3 <= 10^1000 + 0.4 < 10^1000 + 0.5)");
		t.checkRange(bd5, bd3, bd5, false, true, 
		"Success: BigDecimal: (10^1000 + 0.2 < 10^1000 + 0.5 <= 10^1000 + 0.5]");
		t.checkRange(bd3, bd3, bd5, false, true, 
		"Should fail: BigDecimal: (10^1000 + 0.3 < 10^1000 + 0.3 <= 10^1000 + 0.5]");
		t.checkRange(bdminus2, bd3, bd5, 
		"Should fail: BigDecimal: [10^1000 + 0.3 <= 10^1000 - 0.2 < 10^1000 + 0.5)");
		t.checkRange(bd5, bd3, bd5, 
		"Should fail: BigDecimal: [10^1000 + 0.3 <= 10^1000 + 0.5 < 10^1000 + 0.5)");
		t.checkRange(bd8, bd3, bd5, 
		"Should fail: BigDecimal: [10^1000 + 0.3 <= 10^1000 + 0.8 < 10^1000 + 0.5)");     
	}

	/**
	 * Create several instances of Person class.
	 */
	Person jon20 = new Person("Jon", 20);
	Person ann10 = new Person("Ann", 10);
	Person ken40 = new Person("Ken", 40);
	Person dan40 = new Person("Dan", 40);
	Person kim30 = new Person("Kim", 30);
	Person pat60 = new Person("Pat", 60);

	/**
	 * Test the range checking of <CODE>{@link Person Person}</CODE> data
	 * using its implementation of <code>Comparable</code>
	 *
	 * @param t the <CODE>{@link Tester Tester}</CODE> that performs the tests
	 */
	public void testRangeComparable(Tester t) {

		t.checkRange(jon20, jon20, ken40, 
		"Success: RangeComparable: hits the lower bound");
		t.checkRange(kim30, jon20, ken40, 
		"Success: RangeComparable: within the bounds");
		t.checkRange(dan40, jon20, ken40, true, true, 
		"Success: RangeComparable: incl upper bound");
		t.checkRange(ann10, jon20, ken40, 
		"Should fail: RangeComparable: below the lower bound");
		t.checkRange(dan40, jon20, ken40, 
		"Should fail: RangeComparable: hits the upper bound");
		t.checkRange(jon20, jon20, ken40, false, false, 
		"Should fail: RangeComparable: excl lower bound");
		t.checkRange(pat60, jon20, ken40, 
		"Should fail: RangeComparable: over the upper bound");
	}

	/**
	 * Test the range checking of <CODE>{@link Person Person}</CODE> data
	 * using the given <code>Comparator</code>
	 *
	 * @param t the <CODE>{@link Tester Tester}</CODE> that performs the tests
	 */
	public void testRangeComparator(Tester t) {

		/** construct a comparator for Person*/
		Comparator<Person> comp = new PersonComparator();

		t.checkRange(jon20, jon20, ken40, comp, 
		"Success: RangeComparator: hits the lower bound");
		t.checkRange(kim30, jon20, ken40, comp, 
		"Success: RangeComparator: within the bounds");
		t.checkRange(dan40, jon20, ken40, true, true, comp, 
		"Success: RangeComparator: incl upper bound");
		t.checkRange(ann10, jon20, ken40, comp, 
		"Should fail: RangeComparator: below the lower bound");
		t.checkRange(jon20, jon20, ken40, false, false, comp, 
		"Should fail: RangeComparator: excl lower bound");
		t.checkRange(dan40, jon20, ken40, comp, 
		"Should fail: RangeComparator: hits upper the bound");
		t.checkRange(pat60, jon20, ken40, comp, 
		"Should fail: RangeComparator: over the upper bound");


		/**
		 * Inline comparator test
		 */
		Book p = new Book("Pearl", null, 1930);
		Book c = new Book("Cosmos", null, 1960);
		Book h = new Book("Hamlet", null, 1600);

		t.checkRange(   // assert that
				h,      // "Hamlet" is
				c,      // between "Cosmos"
				p,      // and "Pearl"
				new Comparator<Book>() {  // according to alphabetical order
					public int compare(Book b1, Book b2){
						return b1.title.compareTo(b2.title);
					}}, "Hamlet between Cosmos and Pearl");
	}

	/**
	 * <P>Display all data defined in the <CODE>{@link ExamplesRangeCheck ExamplesRangeCheck}</CODE> 
	 * class.</P>
	 * <P>Run all tests defined in the <CODE>{@link ExamplesRangeCheck ExamplesRangeCheck}</CODE> 
	 * class.</P>
	 */
	public static void main(String[] argv) {
		ExamplesRangeCheck erc = new ExamplesRangeCheck();

		System.out.println("Show all data defined in the ExamplesRangeCheck class:");
		System.out.println("\n\n---------------------------------------------------");
		System.out.println("Invoke tester.runReport(this, true, true):");
		System.out.println("Print all data, all test results");

		Tester.runReport(erc, true, true);

		System.out.println("\n---------------------------------------------------");
		System.out.println("\n---------------------------------------------------");
		System.out.println("\n---------------------------------------------------");
		System.out.println("Invoke tester.runReport(this, false, false, true):");
		System.out.println("Print no data, all test results, no warnings");

		Tester.runReport(erc, false, false);
	}
}