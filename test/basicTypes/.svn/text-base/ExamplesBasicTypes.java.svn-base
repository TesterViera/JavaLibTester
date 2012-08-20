package basicTypes;

import java.math.BigDecimal;
import java.math.BigInteger;

import tester.Tester;

/**
 * A class to demonstrate and test the use of the tester library 
 * for data of primitive types, numerical types, and the wrapper classes
 * for primitive types.
 *  
 * @author Virag Shah
 * @since 17 February 2011
 */
public class ExamplesBasicTypes {
	
	ExamplesBasicTypes() {}
	
	/** --------- Examples and Tests for all primitive types and wrapper classes -------- */	
	/** 
	 * Data: Primitive types
	 */
	public int int1 = 1;
	public int int2 = 2;
	public short short1 = 1;
	public short short2 = 2;
	public short short1a = 1;
	public long long1 = 1;
	public long long2 = 2;
	public long long1a = 1;
	public byte byte1 = 1;
	public byte byte2 = 2;
	public byte byte1a = 1;
	public boolean bool1 = true;
	public boolean bool2 = false;
	public boolean bool1a = true;
	public char charA = 'A';
	public char charB = 'B';
	
	/** 
	 * Method that adds 5 to the number n
	 * @param n Number to add 5 to
	 */
	public int nPlus5(int n){
		return n+5;
	}
	
	/**
	 * <p>Tests for all primitive data types:</p>
	 * <p>    test the same method for the type - result true</p>
	 * <p>    test the same method for the type - result false</p>
	 * <p>    test the checkExpect for the type</p>
	 * 
	 * @param t the <CODE>{@link Tester Tester}</CODE> that performs the tests
	 */
	public void testPrimitive(Tester t) {
		/**
		 * Int test
		 */
		t.checkExpect(t.same(int1, int2), false, "Success: primitive - int same - different");
	    t.checkExpect(t.same(int1, 1), true, "Success: primitive - int same - same");
	    t.checkExpect(nPlus5(5), 10, "Success: primitive - int - check");
	    		
	    /**
		 * Short test
		 */
	    t.checkExpect(t.same(short1, short2), false, "Success: primitive - short - different");
	    t.checkExpect(t.same(short1, short1a), true, "Success: primitive - short - same");
	    t.checkExpect(short1, short1a, "Success: primitive - short - check");
	   
	    /**
		 * Long test
		 */
	    t.checkExpect(t.same(long1, long2), false, "Success: primitive - long - different");
	    t.checkExpect(t.same(long1, long1a), true, "Success: primitive - long - same");
	    t.checkExpect(long1, long1a, "Success: primitive - long - check");
	    		
	    /**
		 * Byte test
		 */
	    t.checkExpect(t.same(byte1, byte2), false, "Success: primitive - byte - different");
	    t.checkExpect(t.same(byte1, byte1a), true, "Success: primitive - byte - same");
	    t.checkExpect(byte1, byte1a, "Success: primitive - byte - check");
	    		
	    /**
		 * Boolean test
		 */
	    t.checkExpect(t.same(bool1, bool2), false, "Success: primitive - boolean - different");
	    t.checkExpect(t.same(bool1, bool1a), true, "Success: primitive - boolean - same");
	    t.checkExpect(bool1, bool1a, "Success: primitive - boolean - check");
	    		
	    /**
		 * Char test
		 */
	    t.checkExpect(t.same(charA, charB), false, "Success: primitive - char - different");
	    t.checkExpect(t.same(charA, 'A'), true, "Success: primitive - char - same");
	    t.checkExpect(charA, 'A', "Success: primitive - char - check");
	    t.checkFail(charA, charB, "Test to fail: primitive - char - different");
	}
	/**
	 * <p>Tests for the checkRange for all primitive types and for 
	 * <code>String</code></p>
	 * <p>To facilitate multiple failed tests we use the imperative style for 
	 * the test case definitions and the test method.</p>
	 * @param t the <CODE>{@link Tester Tester}</CODE> that performs the tests
	 * @return true
	 */
	public void testPrimitiveRange(Tester t) {
		/**
		 * Int test
		 */
		t.checkRange(3, 3, 5, "Success: [3 <= 3 < 5)");
	    t.checkRange(4, 3, 5, "Success: [3 <= 4 < 5)");
	    t.checkRange(-2, 3, 5, "Fails: [3 <= -2 < 5)");
	    t.checkRange(5, 3, 5, "Fails: [3 <= 5 < 5)");
	    t.checkRange(8, 3, 5, "Fails: [3 <= 8 < 5)");	    
	    
	    /**
		 * Short test
		 */
	    short sminus2 = (short)-2;
	    short s3 = (short)3;
	    short s4 = (short)4;
	    short s5 = (short)5;
	    short s8 = (short)8;
	    
	    t.checkRange(s3, s3, s5, "Success: short: [3 <= 3 < 5)");
	    t.checkRange(s4, s3, s5, "Success: short: [3 <= 4 < 5)");
	    t.checkRange(sminus2, s3, s5, "Fails: short: [3 <= -2 < 5)");
	    t.checkRange(s5, s3, s5, "Fails: short: [3 <= 5 < 5)");
	    t.checkRange(s8, s3, s5, "Fails: short: [3 <= 8 < 5)"); 
	    
	    /**
		 * Long test
		 */
	    long lminus2 = (long)-2;
	    long l3 = (long)3;
	    long l4 = (long)4;
	    long l5 = (long)5;
	    long l8 = (long)8;
	    
	    t.checkRange(l3, l3, l5, "Success: long: [3 <= 3 < 5)");
	    t.checkRange(l4, l3, l5, "Success: long: [3 <= 4 < 5)");
	    t.checkRange(lminus2, l3, l5, "Fails: long: [3 <= -2 < 5)");
	    t.checkRange(l5, l3, l5, "Fails: long: [3 <= 5 < 5)");
	    t.checkRange(l8, l3, l5, "Fails: long: [3 <= 8 < 5)"); 
	    
	    /**
		 * Byte test
		 */
	    byte bminus2 = (byte)-2;
	    byte b3 = (byte)3;
	    byte b4 = (byte)4;
	    byte b5 = (byte)5;
	    byte b8 = (byte)8;
	    
	    t.checkRange(b3, b3, b5, "Success: byte: [3 <= 3 < 5)");
	    t.checkRange(b4, b3, b5, "Success: byte: [3 <= 4 < 5)");
	    t.checkRange(bminus2, b3, b5, "Fails: byte: [3 <= -2 < 5)");
	    t.checkRange(b5, b3, b5, "Fails: byte: [3 <= 5 < 5)");
	    t.checkRange(b8, b3, b5, "Fails: byte: [3 <= 8 < 5)"); 
	    
	    /**
		 * Boolean test
		 */
	    boolean btrue = true;
	    boolean bfalse = false;  
	    
	    t.checkRange(false, bfalse, btrue, "Success: boolean: [false <= false < true)");
	    t.checkRange(true, bfalse, btrue, "Fails: boolean: [false <= true < true)");
	    
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
	    t.checkRange(q, p2, r1, "Success: char: [p <= q < r)");
	    t.checkRange(m, p2, r1, "Fails: char: [p <= m < r)");
	    t.checkRange(r2, p2, r1, "Fails: char: [p <= r < r)");
	    t.checkRange(s, p2, r1, "Fails: char: [p <= s < r)");
	    
	    /**
		 * Float test
		 */
	    float fminus2 = (float)-2.0;
	    float f3 = (float)3.0;
	    float f4 = (float)4.0;
	    float f5 = (float)5.0;
	    float f8 = (float)8.0;
	    
	    t.checkRange(f3, f3, f5, "Success: float: [3.0 <= 3.0 < 5.0)");
	    t.checkRange(f4, f3, f5, "Success: float: [3.0 <= 4.0 < 5.0)");
	    t.checkRange(fminus2, f3, f5, "Fails: float: [3.0 <= -2.0 < 5.0)");
	    t.checkRange(f5, f3, f5, "Fails: float: [3.0 <= 5.0 < 5.0)");
	    t.checkRange(f8, f3, f5, "Fails: float: [3.0 <= 8.0 < 5.0)"); 
	    
	    /**
		 * Double test
		 */
	    double dminus2 = (double)-2.0;
	    double d3 = (double)3.0;
	    double d4 = (double)4.0;
	    double d5 = (double)5.0;
	    double d8 = (double)8.0;
	    
	    t.checkRange(d3, d3, d5, "Success: double: [3.0 <= 3.0 < 5.0)");
	    t.checkRange(d4, d3, d5, "Success: double: [3.0 <= 4.0 < 5.0)");
	    t.checkRange(dminus2, d3, d5, "Fails: double: [3.0 <= -2.0 < 5.0)");
	    t.checkRange(d5, d3, d5, "Fails: double: [3.0 <= 5.0 < 5.0)");
	    t.checkRange(d8, d3, d5, "Fails: double: [3.0 <= 8.0 < 5.0)"); 
	    
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
	    t.checkRange(aaa, abc1, cde, "Fails String: [abc <= aaa < cde)");
	    t.checkRange(cde, abc1, cde2, "Fails String: [abc <= cde < cde)");
	    t.checkRange(def, abc1, cde, "Fails String: [abc <= def < cde)");
	}
	/** 
	 * Data: Wrapper classes 
	 */
	public Integer wint1 = 1;
	public Integer wint2 = 2;
	public Short wshort1 = 1;
	public Short wshort2 = 2;
	public Long wlong1 = new Long("12345678901");
	public Long wlong2 = new Long("12345678902");
	public Byte wbyte1 = 1;
	public Byte wbyte2 = 2;
	public Boolean wbool1 = true;
	public Boolean wbool2 = false;
	public Boolean wbool1a = true;
	public Character wcharA = 'A';
	public Character wcharB = 'B';
	/**
	 * <p>Tests for all wrapper data types:</p>
	 * <p>    test the same method for the type - result true</p>
	 * <p>    test the same method for the type - result false</p>
	 * <p>    test the checkExpect for the type</p>
	 * 
	 * @param t the <CODE>{@link Tester Tester}</CODE> that performs the tests
	 */
	public void testWrapper(Tester t) {
		/**
		 * Int test
		 */
		t.checkExpect(int1, wint1, "Success: int - Integer - same");
	    t.checkExpect(t.same(wint1, wint2), false, "Success: wrapper - Integer - different");
	    t.checkExpect(t.same(wint1, new Integer(1)), true, "Success: wrapper - Integer - same");
	    t.checkExpect(wint1, new Integer(1), "Success: wrapper - Integer - check");
	    		
	    /**
		 * Short test
		 */
	    t.checkExpect(t.same(wshort1, wshort2), false, "Success: wrapper - Short - different");
	    t.checkExpect(t.same(wshort1, new Short("1")), true, "Success: wrapper - Short - same");
	    t.checkExpect(wshort1, new Short("1"), "Success: wrapper - Short - check");
	    		
	    /**
		 * Long test
		 */
	    t.checkExpect(t.same(wlong1, wlong2), false, "Success: wrapper - Long - different");
	    t.checkExpect(t.same(wlong1, new Long("12345678901")), true, "Success: wrapper - Long - same");
	    t.checkExpect(wlong1, new Long("12345678901"), "Success: wrapper - Long - check");
	    		
	    /**
		 * Byte test
		 */
	    t.checkExpect(t.same(wbyte1, wbyte2), false, "Success: wrapper - Byte - different");
	    t.checkExpect(t.same(wbyte1, new Byte("1")), true, "Success: wrapper - Byte - same");
	    t.checkExpect(wbyte1, new Byte("1"), "Success: wrapper - Byte - same");
	    		
	    /**
		 * Boolean test
		 */
	    t.checkExpect(t.same(wbool1, wbool2), false, "Success: wrapper - Boolean - different");
	    t.checkExpect(t.same(wbool1, wbool1a), true, "Success: wrapper - Boolean - same");
	    t.checkExpect(wbool1, wbool1a, "Success: wrapper - Boolean - check");
	    		
	    /**
		 * Char test
		 */
	    t.checkExpect(t.same(wcharA, wcharB), false, "wrapper - Character - different");
	    t.checkExpect(t.same(wcharA, new Character('A')), true, "wrapper - Character - same");
	    t.checkExpect(wcharA, new Character('A'), "wrapper - Character - check");
	    t.checkFail(wcharA, wcharB, "Test to fail: wrapper - Character - different");
	}
	
	/** 
	 * Data: additional Number classes
	 */
	public BigInteger bigint1 = new BigInteger("12345678901");
	public BigInteger bigint1a = new BigInteger("12345678901");
	public BigInteger bigint2 = new BigInteger("12345678902");
  	public BigDecimal bigdec1 = new BigDecimal("1234567890.12345678901");
  	public BigDecimal bigdec1a = new BigDecimal("1234567890.12345678901");
	public BigDecimal bigdec2 = new BigDecimal("1234567890.12345678902");
	
	/**
	 * <p>Tests for data types that represent big numerical values:</p>
	 * <p>    test the same method for the type - result true</p>
	 * <p>    test the same method for the type - result false</p>
	 * <p>    test the checExpect for the type</p>

	 * @param t the <CODE>{@link Tester Tester}</CODE> that performs the tests
	 */
	public void testBigNumbers(Tester t) {
		/**
		 * BigInt test
		 */
		t.checkExpect(t.same(bigint1, bigint2), false, "Success: big integer - different");
	    t.checkExpect(t.same(bigint1, bigint1a), true, "Success: big integer - same");
	    t.checkExpect(bigint1, bigint1a, "Success: big integer - check");
	    		
	    /**
		 * BigDecimal test
		 */
	    t.checkExpect(t.same(bigdec1, bigdec2), false, "Success: big decimal - different");
	    t.checkExpect(t.same(bigdec1, bigdec1a), true, "Success: big decimal - same");
	    t.checkExpect(bigdec1, bigdec1a, "Success: big decimal - same");
	}
	/**
	 * <p>Tests for the range check for data types that represent 
	 * big numerical values:</p>
	 * 
	 * @param t the <CODE>{@link Tester Tester}</CODE> that performs the tests
	 */
	public void testBigNumbersRange(Tester t) {
		
		BigInteger bigint0 = new BigInteger("12345678890");
	    BigInteger bigint1 = new BigInteger("12345678901");
	    BigInteger bigint1a = new BigInteger("12345678901");
	    BigInteger bigint2 = new BigInteger("12345678902");
	    BigInteger bigint3 = new BigInteger("12345678903");
	    BigInteger bigint3a = new BigInteger("12345678903");
	    BigInteger bigint4 = new BigInteger("12345678904");
	    
	  	t.checkRange(bigint1a, bigint1, bigint3, "Success: BigInteger: [1 <= 1 < 3)");
	  	t.checkRange(bigint2, bigint1, bigint3, "Success: BigInteger: [1 <= 2 < 3)");
	  	t.checkRange(bigint0, bigint1, bigint3, "Fails: BigInteger: [1 <= 0 < 3)");
	  	t.checkRange(bigint3a, bigint1, bigint3, "Fails: BigInteger: [1 <= 3 < 3)");
	  	t.checkRange(bigint4, bigint1, bigint3, "Fails: BigInteger: [1 <= 4 < 3)");

	  	
	    BigDecimal bigdec0 = new BigDecimal("1234567890.12345678900");
	    BigDecimal bigdec1 = new BigDecimal("1234567890.12345678901");
	    BigDecimal bigdec1a = new BigDecimal("1234567890.12345678901");
	    BigDecimal bigdec2 = new BigDecimal("1234567890.12345678902");
	    BigDecimal bigdec3 = new BigDecimal("1234567890.12345678903");
	    BigDecimal bigdec3a = new BigDecimal("1234567890.12345678903");
	    BigDecimal bigdec4 = new BigDecimal("1234567890.12345678904");
	    
	   	t.checkRange(bigdec1a, bigdec1, bigdec3, "Success: BigDecimal: [1 <= 1 < 3)");
	  	t.checkRange(bigdec2, bigdec1, bigdec3, "Success: BigDecimal: [1 <= 2 < 3)");
	  	t.checkRange(bigdec0, bigdec1, bigdec3, "Fails: BigDecimal: [1 <= 0 < 3)");
	  	t.checkRange(bigdec3a, bigdec1, bigdec3, "Fails: BigDecimal: [1 <= 3 < 3)");
	  	t.checkRange(bigdec4, bigdec1, bigdec3, "Fails: BigDecimal: [1 <= 4 < 3)");
	}
	
	/** ---------------- Examples and Tests for inexact numbers --------------- */
	/** 
	 * Data: Cartesian points -- using all inexact data types
	 */
	public CartPt pt1 = new CartPt(4.0, 3.0);
	public CartPt pt1a = new CartPt(4.0, 3.0);
	public CartPt pt2 = new CartPt(100.001, 0.0);
	public CartPtD pt1D = new CartPtD((Double)4.0, (Double)3.0);
	public CartPtD pt2D = new CartPtD((Double)100.001, (Double)0.0);
	public CartPtF pt1F = new CartPtF(new Float(4.0), new Float(3.0));
	public CartPtF pt2F = new CartPtF(new Float(100.001), new Float(0.0));
	public CartPtfloat pt1float = 
		new CartPtfloat(((Double)4.0).floatValue(), ((Double)3.0).floatValue());
	public CartPtfloat pt2float = 
		new CartPtfloat(((Double)100.001).floatValue(), ((Double)0.0).floatValue());
	
	/**
	 * Test the comparisons for inexact numbers of the types
	 * <code>double</code>
	 *
	 * @param t the <CODE>{@link Tester Tester}</CODE> that performs the tests
	 */
	public void testDoubles(Tester t) {
		/**
		 * Doubles test
		 */
	    t.checkExpect(pt1.distTo0(), 
	    		      5.0, 
	    		      "Success: Distance to 0 - double - test1 success- same doubles: ");
	    t.checkExpect(pt2.distTo0(), 
	    		      100.0, 
	    		      "Fails: Distance to 0 - double - test2 fail - not inexact: ");
	    t.checkInexact(pt1.distTo0(), 
	    		       5.0, 
	    		       0.001, 
	    		       "Success: Distance to 0 - double - test3 - OK: ");
	    t.checkInexact(pt2.distTo0(), 
	    		       100.0, 
	    		       0.001, 
	    		       "Success: Distance to 0 - double - test4 - OK: ");
	}
	
	public void testDoubleFields(Tester t) {
	  	t.checkExpect(pt1, pt1a, "Success: Compare CartPt-s");
	  	t.checkExpect(pt1, pt2, "Fails: Compare CartPt-s: fail");
	}
	
	/**
	 * Test the comparisons for inexact numbers of the types
	 * <code>double</code> -- setting the tolerance too low
	 * @param t the <CODE>{@link Tester Tester}</CODE> that performs the tests
	 */
	public void testTolerance1(Tester t) {
		/** 
		 * make the tolerance too low
		 */
	    t.checkInexact(pt2.distTo0(),
	                   100.0,
	                   0.0000001,
	                   "Fails: Distance to 0 - double - low tolerance: ");
	}
	/**
	 * Test the comparisons for inexact numbers of the types
	 * <code>Double</code>
	 * 
	 * @param t the <CODE>{@link Tester Tester}</CODE> that performs the tests
	 */
	public void testTolerance2(Tester t) {
		/** 
		 * reset the tolerance -- test Double-s
		 */
	    t.checkInexact(pt1D.distTo0(),
	    		       5.0,
	                   0.01,
	                   "Success: Distance to 0 - Double - test1: ");
	    t.checkInexact(pt2D.distTo0(),
	        		   100.0,
	                   0.01,
	                   "Success: Distance to 0 - Double - test2: ");
	}
	
	/**
	 * Test the comparisons for inexact numbers of the types
	 * <code>Double</code> setting the tolerance too low
	 *
	 * @param t the <CODE>{@link Tester Tester}</CODE> that performs the tests
	 */
	public void testTolerance3(Tester t) {
		/** 
		 * make the tolerance too low
		 */
		t.checkInexact(pt2D.distTo0(),
	                   100.0,
	                   0.0000001,
	                   "Fails: Distance to 0 - Double - low tolerance: ");
	}
	
	/**
	 * Test the comparisons for inexact numbers of the types 
	 * <code>float</code>
	 * 
	 * @param t the <CODE>{@link Tester Tester}</CODE> that performs the tests
	 */
	public void testTolerance4(Tester t) {
		/** reset the tolerance -- test float-s */
	    t.checkInexact(pt1float.distTo0(),
	    		       5.0,
	                   0.01,
	                   "Success: Distance to 0 - float - test1: ");
	    t.checkInexact(pt2float.distTo0(),
	        		   100.0,
	                   0.01,
	                   "Success: Distance to 0 - float - test2: ");
	}
	
	/**
	 * Test the comparisons for inexact numbers of the types
	 * <code>float</code> setting the tolerance too low
	 * 
	 * @param t the <CODE>{@link Tester Tester}</CODE> that performs the tests
	 */
	public void testTolerance5(Tester t) {
		/** make the tolerance too low */
	    t.checkInexact(pt2float.distTo0(),
	                   100.0,
	                   0.0000001,
	                   "Fails: Distance to 0 - float - low tolerance: ");
	}
	
	/**
	 * Test the comparisons for inexact numbers of the types
	 * <code>Float</code>
	 * 
	 * @param t the <CODE>{@link Tester Tester}</CODE> that performs the tests
	 */
	public void testTolerance6(Tester t) {
		/** reset the tolerance -- test Float-s */
	    t.checkInexact(pt1F.distTo0(),
	    		       5.0,
	                   0.01,
	                   "Success: Distance to 0 - Float - test1: ");
	    t.checkInexact(pt2F.distTo0(),
	        		   100.0,
	                   0.01,
	                   "Success: Distance to 0 - Float - test2: ");
	}
	
	/**
	 * Test the comparisons for inexact numbers of the types
	 * <code>Float</code> setting the tolerance too low
	 * 
	 * @param t the <CODE>{@link Tester Tester}</CODE> that performs the tests
	 */
	public void testTolerance7(Tester t) {
		/** make the tolerance too low */
		t.checkInexact(pt2F.distTo0(),
	                   100.0,
	                   0.0000001,
	                   "Fails: Distance to 0 - Float - low tolerance: ");
	}
	
	/**
	 * Test the range checking of <CODE>{@link Person Person}</CODE> data
	 * using its implementation of <code>Comparable</code>
	 * 
	 * @param t the <CODE>{@link Tester Tester}</CODE> that performs the tests
	 */
	public void testNumRange(Tester t) {
		short n = 1;
	  	t.checkNumRange(1.0, 1, 2.0, "Success: Num range: lower bound double and int");
	  	t.checkNumRange(1, 1.0, 2.0, "Success: Num range: lower bound double and int");
	  	t.checkNumRange(1, 1, 2.0, "Success: Num range: lower bound int and int");
	  	t.checkNumRange(1.0, 1.0, 2.0, "Success: Num range: lower bound double and int");
	  	t.checkNumRange(1.0, 2, 2.0, false, true, "Fails: Num range: lower bound double and int");
	  	t.checkNumRange(1, 2.0, 2.0, "Fails: Num range: lower bound double and int");
	  	t.checkNumRange(1.0, new Long(1), 2.0, "Success: Num range: lower bound double and Long");
	  	t.checkNumRange(1.0, n, 2.0, "Success: Num range: lower bound double and short");
	  	t.checkNumRange(1.0, new Float(1.0), 2.0, "Success: Num range: lower bound double and float");
	}
	
	/**
	 * <P>Display all data defined in the <CODE>{@link ExamplesBasicTypes ExamplesBasicTypes}</CODE> 
	 * class.</P>
	 * <P>Run all tests defined in the <CODE>{@link ExamplesBasicTypes ExamplesBasicTypes}</CODE> 
	 * class.</P>
	 */
	public static void main(String[] argv) {
		ExamplesBasicTypes ebt = new ExamplesBasicTypes();
		
		System.out.println("Show all data defined in the ExamplesBasicTypes class:");
        System.out.println("\n\n---------------------------------------------------");
        System.out.println("Invoke tester.runReport(this, true, true):");
	  	System.out.println("Print all data, all test results");

        Tester.runReport(ebt, true, true);
        
        System.out.println("\n---------------------------------------------------");
        System.out.println("\n---------------------------------------------------");
        System.out.println("\n---------------------------------------------------");
        System.out.println("Invoke tester.runReport(this, false, false, true):");
        System.out.println("Print no data, all test results, no warnings");

        Tester.runReport(ebt, false, false);
        
	}
}