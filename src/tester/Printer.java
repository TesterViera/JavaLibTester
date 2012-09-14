package tester;

import java.lang.reflect.*;
import java.util.*;

//import org.apache.log4j.Logger;

/**
 * Copyright 2007, 2008 Viera K. Proulx
 * This program is distributed under the terms of the
 * GNU Lesser General Public License (LGPL)
 */

/**
 * This class leverages the use of the Java reflection classes to pretty-print
 * the values of an arbitrary object. It includes the values of user-defined
 * fields, but does not extend to fields inherited from Java library
 * superclasses.
 *
 * @author Viera K. Proulx
 * @since 3 March 2008, 11 November 2008, 23 January 2009
 *
 */
public class Printer {
	// private static final Logger logger = Logger
	// .getLogger(Printer.class);
	/** current indentation level for pretty-printing */
	private static String INDENT = "";

	/** the String class - needed as indentation argument type for WorldImage-s */
  private static Class<?> stringClass = INDENT.getClass();

	/** object counter */
	private static int counter;

	/**
	 * a hashmap of the hashcodes for the objects that are being printed: if the
	 * same pair is compared again, the loop of printing stops and produces true
	 */
	private static HashMap<Integer, Integer> hashmap = new HashMap<Integer, Integer>();

	/**
	 * Print the values of the given object
	 *
	 * @param obj
	 *            the object to display in the console
	 */
	public static void print(Object obj) {
		hashmap.clear();
		counter = 0;
		System.out.println(makeString(obj));
	}

	/**
	 * Produce a <code>String</code> representation of the values of the given
	 * object
	 *
	 * @param obj
	 *            the object to represent
	 * @return a <code>String</code> representation of the values of the given
	 *         object
	 */
	public static String produceString(Object obj) {
		hashmap.clear();
		counter = 0;
		return makeString(obj);
	}

	/**
	 * Produce a <code>String</code> representation of the values of the given
	 * object
	 *
	 * @param it
	 *            the object that produces the iterator to be used traverse data
	 * @return a <code>String</code> representation of the values of the given
	 *         object
	 */
	public static <T> String produceIterableStrings(Iterable<T> it) {
		hashmap.clear();
		counter = 0;
		return makeIterableStrings(it.iterator());
	}

	/**
	 * Produce a String representation of the values of the given object of the
	 * type Traversal
	 *
	 * @param tr
	 *            the <CODE>{@link Traversal Traversal}</CODE> iterator to be
	 *            used traverse data
	 * @return a <code>String</code> representation of the values of the given
	 *         object
	 */
	public static <T> String produceTraversalStrings(Traversal<T> tr) {
		hashmap.clear();
		counter = 0;
		return makeTraversalStrings(tr);
	}

	/**
	 * Produce a <code>String</code> representation of the given object.
	 * <P>
	 * Show <code>String</code> 'as is'.
	 * </P>
	 * <P>
	 * For primitive datatypes and their wrapper classes show the primitive
	 * values.
	 * </P>
	 * <P>
	 * For all <code>Array</code>s traverse over all elements.
	 * </P>
	 * <P>
	 * For datatypes that implement <code>Iterable</code> interface traverse
	 * over all elements.
	 * </P>
	 * <P>
	 * For datatypes that implement <CODE>{@link Traversal Traversal}</CODE>
	 * interface traverse over all elements.
	 * </P>
	 * <P>
	 * For an instance of a declared class show all fields
	 * </P>
	 *
	 * @param obj
	 *            the given object
	 * @return a <code>String</code> representation of the given object
	 */
	private static String makeString(Object obj) {

		// if the object is null, we are done
		if (obj == null)
			return "null";

		// if the object is a String already - show it
		if (obj instanceof java.lang.String)
			return " \"" + obj.toString() + "\"";

		// if the object is an instance of Random - show it
		if (obj instanceof java.util.Random)
			return " new Random() ";

		// if the object is a Color object already - show it
		if (obj instanceof java.awt.Color)
			return " \"" + obj.toString() + "\"";

		// handle the objects of the type Enum
		if (obj instanceof java.lang.Enum) {
			Enum e = (Enum) obj;
			return e.getDeclaringClass().getName().replace('$', '.') + "."
					+ e.name();
		}

		// for WorldImage instances, tunes classes, and all that implement
		// the toIndentedString method - use that method
		// augmented with the appropriate indentation
		Method tism = toIndentedStringMethod(obj);
		if (tism != null){
			return getIndentedString(obj, tism);
		}


		Class<?> objClass = obj.getClass();

		// if the object is of primitive data type
		// or an instance of a wrapper class - use default toString method
		if (objClass.isPrimitive()
				|| Inspector.isWrapperClass(objClass.getName())) {
			return makePrimitiveStrings(objClass.getName(), obj);
		}

		// if the objects is an instance of a WorldImage in our world libraries
		// use its toString method
		if (Inspector.isWorldImage(obj.getClass().getName()))
			return obj.toString();

		// if the class where the object is defined defined its own toString
		// method, use the String it produces
		String s = hasDefinedToString(obj);
		if (!(s == null))
			s = s + "\n";
		else
			s = "\n";
		// System.out.println("returned from hasDefinedString");

		// check whether the object has been viewed before, if not,
		// enter a record for this object into the hashmap
		Integer i1 = System.identityHashCode(obj);
		/*
		 * <<NOTE>>
		 * [Author: Sachin Venugopalan; Date: 03/03/2012]
		 * See note in Inspector.java about the use of System.identityHashCode(obj)
		 * instead of obj.hashCode()
		 */

		Integer i1match = hashmap.get(i1);

		if (i1match != null) {
			// object has been displayed already - show class name and its id
			return obj.getClass().getName() + ":" + i1match;
		} else {
			counter = counter + 1;
			i1match = counter;
			hashmap.put(i1, counter);
		}

		/** handle the Canvas class in the draw teachpack */
		if (Inspector.isOurCanvas(obj.getClass().getName()))
			return obj.toString();

		// if the object is an Array -
		// traverse over the data
		if (obj instanceof Object[]) {

			// show the length of the array
			int length = Array.getLength(obj);

			StringBuilder tmp = new StringBuilder("\n" + INDENT
					+ " new Object[" + length + "](){");
			INDENT = INDENT + "  ";

			// keep track of the index for each element and show it
			int n = 0;
			for (int i = 0; i < length; i++) {
				tmp = tmp.append("\n" + INDENT + "[" + n + "] "
						+ makeString(((Object[]) obj)[i]) + ",");
				n = n + 1;
			}
			if (Array.getLength(obj) > 0) {
				tmp.deleteCharAt(tmp.length() - 1);
			}
			INDENT = INDENT.substring(0, INDENT.length() - 2);
			tmp = tmp.append("}");
			return tmp + "";
		}

		// for an instance of a declared class start with the class name
		String result = s + INDENT + " new " + objClass.getName() + ":"
				+ i1match + "(";
		// + ":" + counter + "(";
		INDENT = INDENT + "  ";
		String field;

		// if the object is Iterable and a part of Java Collections
		// traverse over the data generated by the iterator
		// Note: user-specified traversals of Iterable are handled separately
		// and are invoked by check..Iterable tests
		if ((obj instanceof Iterable)
				&& obj.getClass().getName().startsWith("java.util")) {
			result = result + "){"
					+ makeIterableStrings(((Iterable<?>) obj).iterator()) + "}";
		}

		// instance of a Map: show the class and the key-value bindings
		else if (obj instanceof Map) {
			result = result + "){" + makeMapStrings((Map<?, ?>) obj) + "}";
		}

		/** instance of a class that may have several defined fields */
		else {
			Reflector r = new Reflector(obj);

			// TBD: print only the public fields for Java library classes
			/*
			 * if (objClass.getName().startsWith("java."))
			 * System.out.println("Java library class: " + objClass.getName());
			 */

			/** display all fields */
			for (Field f : r.sampleDeclaredFields) {
				try {
                    Reflector.ensureIsAccessible(f);

					if ((f.get(obj)) == null)
						field = "this." + f.getName() + " = null";
					else
						field = "this." + f.getName() + " = "
								+ makeString(f.get(obj));

					result = result + "\n" + INDENT + field;

				} catch (IllegalAccessException e) {
					System.out.println("makeString cannot access the field "
							+ f.getName() + " of the class "
							+ r.sampleClass.getName() + "\n   message: "
							+ e.getMessage());
				}
			}
			/** close parentheses and finish up */
			result = result + ")";
		}

		INDENT = INDENT.substring(0, INDENT.length() - 2);
		return result;
	}

	/**
	 * <p>
	 * Produce a <code>String</code> that represents a data of primitive type or
	 * an instance of a wrapper class.
	 * </p>
	 * <p>
	 * Show <code>int</code>, <code>double</code>, <code>byte</code>, and
	 * <code>char</code> values as generated by the built-in
	 * <code>toString</code> method.
	 * </p>
	 * <p>
	 * Append
	 * <ul>
	 * <li><code>long</code> values with <code>L</code></li>
	 * <li><code>short</code> values with <code>S</code></li>
	 * <li><code>float</code> values with <code>F</code></li>
	 * <li><code>BigInteger</code> values with <code>BigInteger</code></li>
	 * <li><code>BigDecimal</code> values with <code>BigDecimal</code></li>
	 * </ul>
	 * </p>
	 * <p>
	 * This makes it possible to understand a failed test that compares
	 * unmatched but seemingly equal numerical values.
	 *
	 * @param className
	 *            the class name for the wrapper class
	 * @param value
	 *            the value of the object to display
	 * @return a <code>String</code> that represents the given value and
	 *         indicates its <code>Wrapper</code> class name
	 */
	private static <T> String makePrimitiveStrings(String className, T value) {
		StringBuilder result = new StringBuilder();

		if (className.equals("java.lang.Short"))
			return result + value.toString() + "S";
		else if (className.equals("java.lang.Long"))
			return result + value.toString() + "L";
		else if (className.equals("java.lang.Float"))
			return result + value.toString() + "F";
		else if (className.equals("java.math.BigInteger"))
			return result + value.toString() + "BigInteger";
		else if (className.equals("java.math.BigDecimal"))
			return result + value.toString() + "BigDecimal";
		else
			/*
			 * (className.equals("java.lang.Integer") ||
			 * className.equals("java.lang.Double") ||
			 * className.equals("java.lang.Character") ||
			 * className.equals("java.lang.Byte") ||
			 * className.equals("java.lang.Boolean"))
			 */
			return result + value.toString();
	}

	/**
	 * Produce a <code>String</code> that represents the data generated by the
	 * given iterator -- comma separated.
	 *
	 * @param it
	 *            the iterator for generating data
	 * @return the <code>String</code> that represents all generated data
	 */
	private static <T> String makeIterableStrings(Iterator<T> it) {
		StringBuilder result = new StringBuilder();

		/** keep track of the index for each element and show we are iterating */
		int n = 0;
		while (it.hasNext()) {
			result = result.append("\n" + INDENT + "Iterable[" + n + "] "
					+ makeString(it.next()) + ",");
			n = n + 1;
		}

		/** remove the last comma - if any data is present */
		if (result.length() > 0)
			result.deleteCharAt(result.length() - 1);
		return result + "";
	}

	/**
	 * Produce a <code>String</code> that represents the data generated by the
	 * given <CODE>{@link Traversal Traversal}</CODE> -- comma separated.
	 *
	 * @param tr
	 *            the <CODE>{@link Traversal Traversal}</CODE> iterator for
	 *            generating data
	 * @return the <code>String</code> that represents all generated data
	 */
	private static <T> String makeTraversalStrings(Traversal<T> tr) {
		StringBuilder result = new StringBuilder();

		/** keep track of the index for each element and show we are iterating */
		int n = 0;
		while (!tr.isEmpty()) {
			result = result.append("\n" + INDENT + "Traversal[" + n + "] "
					+ makeString(tr.getFirst()) + ",");
			n = n + 1;
			tr = tr.getRest();
		}

		/** remove the last comma - if any data is present */
		if (result.length() > 0)
			result.deleteCharAt(result.length() - 1);
		return result + "";
	}

	/**
	 * Produce a <code>String</code> representation of the entries in the given
	 * <code>Map</code>.
	 *
	 * @param <K>
	 *            the type of the keys in this <code>Map</code>
	 * @param <V>
	 *            the type of the values in this <code>Map</code>
	 * @param hm
	 *            the <code>Map</code> to represent as <code>String</code>
	 * @return a <code>String</code> representation of the key and values in
	 *         this <code>Map</code>
	 */
	private static <K, V> String makeMapStrings(Map<K, V> hm) {
		StringBuilder result = new StringBuilder();
		Set<Map.Entry<K, V>> data = new HashSet<Map.Entry<K, V>>(hm.entrySet());

		for (Map.Entry<K, V> entry : data) {
			result = result.append("\n" + INDENT + "(key: "
					+ makeString(entry.getKey()) + "\n" + INDENT + " value: "
					+ makeString(entry.getValue()) + "),");
		}

		/** remove the last comma - if any data is present */
		if (result.length() > 0)
			result.deleteCharAt(result.length() - 1);
		return result + "";
	}

	/**
	 * Produce a String generated by this object's <code>toString</code> method
	 * when the method has been defined there.
	 *
	 * @param o
	 *            the object to be converted to <code>String</code>
	 * @return null if the <code>toString</code> method has not been redefined,
	 *         or the <code.String</code> generated by the <code>toString</code>
	 *         method
	 */
	private static String hasDefinedToString(Object o) {

		Method tsm = null;

		// *** traverse up the class inheritance chain
		Class<?> c = o.getClass();
		Class<?> cs = c.getSuperclass();

		while (c != null) {
			// *** find the toString method
			try {
				// *** get the method toString for this class
				tsm = c.getDeclaredMethod("toString", new Class[] {});
			}
			// *** if method not found, do nothing
			catch (NoSuchMethodException e) {
				// -- omitted diagnostics
				// -- System.out.println("NoSuchMethodException:" +
				// -- e.getMessage());
				// -- System.out.println("Class: " + c.getName() + "\n");
			}

			// *** if found, invoke the method and return
			if (tsm != null) {
				try {
					// *** toString method found - get the declaring class
					Class<?> defclass = tsm.getDeclaringClass();

					// omit toString method in the Object class or
					// AbstractCollection
					if (defclass.getName().equals("java.lang.Object")
							|| defclass.getName().equals(
									"java.util.AbstractCollection"))
						return "";
					else {
						// invoke the toString method and return the String it
						// produces
                        Reflector.ensureIsAccessible(tsm);
						return (String) tsm.invoke(o, new Object[] {});
					}
				}
				// *** catch errors in invoking the toString method
				catch (IllegalAccessException e) {
					// *** do nothing
					// this should never happen
					// -- omitted diagnostics
					System.out.println("IllegalAccessException:"
							+ e.getMessage());
					System.out
							.println("Incorrectly invoked toString method in the class:"
									+ o.getClass().getName() + "\n");
				} catch (InvocationTargetException e) {
					// *** do nothing
					// this should never happen
					// -- omitted diagnostics
					System.out.println("InvocationTargetException:"
							+ e.getMessage());
					System.out
							.println("Incorrectly invoked toString method in the class:"
									+ o.getClass().getName() + "\n");
				}
			}
			// *** look for toString defined in the super class-es
			else {
				c = cs;
				cs = c.getSuperclass();
			}
		}
		return "";
	}

	/**
	 * If the class where the given <code>Object</code> is defined has
	 * defined the method with the signature
	 * <code>String toIndentedString(String indent)</code>
	 * return that defined method, otherwise return <code>null</code>
	 *
	 * @param o the object we expect to pretty-print using <em>toIndentedString</em>
	 * @return the <code>toIndentedString</code> method or <code>null</code>
	 * if not defined.
	 */
	private static Method toIndentedStringMethod(Object o){
	  Method tsm = null;

    // get the class where this object is defined
    Class<?> c = o.getClass();

    if (c != null) {
      // *** find the toString method
      try {
        // *** get the method toString for this class
        tsm = c.getDeclaredMethod("toIndentedString", new Class[] {stringClass});
      }
      // *** if method not found, do nothing
      catch (NoSuchMethodException e) {
        // -- omitted diagnostics
        // -- System.out.println("NoSuchMethodException:" +
        // -- e.getMessage());
        // -- System.out.println("Class: " + c.getName() + "\n");
      }

      return tsm;
    }
    else
      return null;
	}

  /**
   * Produce a String generated by this object's <code>toString</code> method
   * when the method has been defined there.
   *
   * @param o
   *            the object to be converted to <code>String</code>
   * @return null if the <code>toString</code> method has not been redefined,
   *         or the <code.String</code> generated by the <code>toString</code>
   *         method
   */
	private static String getIndentedString(Object o, Method tism) {

	  try {
	    // invoke the toString method and return the String it
	    // produces
        Reflector.ensureIsAccessible(tism);
	    return (String) tism.invoke(o, new Object[] {INDENT});
	  }
	  // *** catch errors in invoking the toString method
	  catch (IllegalAccessException e) {
	    // *** do nothing
	    // this should never happen
	    // -- omitted diagnostics
	    System.out.println("IllegalAccessException:"
	        + e.getMessage());
	    System.out
	    .println("Incorrectly invoked toIndentedString method in the class:"
	        + o.getClass().getName() + "\n");
	  } catch (InvocationTargetException e) {
	    // *** do nothing
	    // this should never happen
	    // -- omitted diagnostics
	    System.out.println("InvocationTargetException:"
	        + e.getMessage());
	    System.out
	    .println("Incorrectly invoked toIndentedString method in the class:"
	        + o.getClass().getName() + "\n");
	  }
	  return "";
	}

	/**
	 * Combine the representation of the actual and expected line-by-line and
	 * indicate with "......" the location of the first difference. If the
	 * actual is too long, print the actual and expected values in consecutive
	 * lines and label them as such
	 *
	 * @param expected
	 *            the <code>String</code> that represents the expected value
	 * @param actual
	 *            the <code>String</code> that represents the actual value
	 * @return <code>String</code> that represents the actual and expected
	 *         side-by-side
	 */
	protected static String combineActualExpected(String actual, String expected) {
		// pad the space between actual and expected with spaces or dots
		String space = "                                                  ";
		String fill = "..................................................";

		// the resulting String
		StringBuilder combined = new StringBuilder("");

		// setup the parsing of the expected and actual values
		StringTokenizer actLine = new StringTokenizer(actual, "\n");
		StringTokenizer expLine = new StringTokenizer(expected, "\n");

		// prime the while loop below with the first lines to compare and align
		String actString = actLine.nextToken();
		String expString = expLine.nextToken();

		// print the values side-by-side while they are matching
		while ((expString.equals(actString))) {
			// combine the matching lines with space between them
			combined.append(combineSpaceFill(actString, expString, space));

			// if no more lines in the expected print the rest of the actual
			if (!expLine.hasMoreTokens())
				return finishActual(combined, actLine);

			// if no more lines in the actual print the rest of the expected
			if (!actLine.hasMoreTokens())
				return finishExpected(combined, expLine);

			// get next lines for both, actual and expected
			actString = actLine.nextToken();
			expString = expLine.nextToken();
		}

		// produce the first differing line
		combined.append(combineSpaceFill(actString, expString, fill));

		// print the remaining values side-by-side
		// we get here only after a differing line has been printed
		while (expLine.hasMoreTokens() && actLine.hasMoreTokens()) {
			// get next lines for both, actual and expected
			actString = actLine.nextToken();
			expString = expLine.nextToken();

			// we only print spaces between lines after the first mis-match
			combined.append(combineSpaceFill(actString, expString, space));
		}

		// if no more lines in the expected print the rest of the actual
		if (!expLine.hasMoreTokens())
			return finishActual(combined, actLine);

		// if no more lines in the actual print the rest of the expected
		if (!actLine.hasMoreTokens())
			return finishExpected(combined, expLine);

		return combined.toString();
	}

	/**
	 * <p>
	 * Combine one line of 'actual' and 'expected' into one line.
	 * <p>
	 * <p>
	 * Pad in between with either blanks or dots (if not a match).
	 * </p>
	 * <p>
	 * If actual is longer than 48 characters, print them on two lines and label
	 * the lines accordingly.
	 * </p>
	 *
	 * @param act
	 *            the <code>String</code> that represents the actual value
	 * @param exp
	 *            the <code>String</code> that represents the expected value
	 * @param spfill
	 *            the <code>String</code> of the padding characters
	 * @return <code>String</code> with the actual and expected combined.
	 */
	private static String combineSpaceFill(String act, String exp, String spfill) {
		// make it two lines -- with blank line before and after
		// if actual is longer than 48 characters
		if (act.length() > 48)
			return "\n--  actual  : " + act + spfill.substring(0, 10) + "\n"
					+ "\n--  expected: " + exp + "\n\n";
		else
			// make it into one line
			return act + spfill.substring(0, 50 - act.length()) + exp + "\n";
	}

	/**
	 * Add the rest of the 'actual' <code>String</code> to the combined lines
	 * when there is nothing more of 'expected' to display
	 *
	 * @param combined
	 *            the combined actual-expected lines collected so far
	 * @param actLine
	 *            the remainder of the 'actual' <code>String
	 * @return the final <code>String with the combined display
	 */
	private static String finishActual(StringBuilder combined,
			StringTokenizer actLine) {
		while (actLine.hasMoreTokens())
			combined.append(actLine.nextToken() + "\n");
		return combined.toString();
	}

	/**
	 * Add the rest of the 'expected' <code>String</code> to the combined lines
	 * when there is nothing more of 'actual' to display
	 *
	 * @param combined
	 *            the combined actual-expected lines collected so far
	 * @param expLine
	 *            the remainder of the 'expected' <code>String
	 * @return the final <code>String with the combined display
	 */
	private static String finishExpected(StringBuilder combined,
			StringTokenizer expLine) {
		String space40 = "                                        ";
		while (expLine.hasMoreTokens())
			combined.append(space40 + expLine.nextToken() + "\n");
		return combined.toString();
	}

	/**
	 * A simple test for the combining of the expected and actual lines
	 *
	 * @param argv
	 */
	public static void main(String[] argv) {

		String act = "hello\ngoodbye\nadieau\n";
		String expTrue = "hello\ngoodbye\nadieau\n";
		String expFalse1 = "hello\ngoodbyes\nadieau\n";
		String expFalse2 = "hello\ngoodbye\n";
		String expFalse3 = "hello\ngoodbyes\nadieau\nahoy\n";
		String longact = "abcdefghijklmnopqrtsuvwxyzabcdefghijklmnopqrtsuvwxyz";
		String longexp = "abcdefghijklmnopqrtsuvwxyzabcdefghijklmnopqrtsuvwxyz";
		String longexpbad = "abcdefghijklmnopqrtsuvwxyzabcdefghijklmnopqrtsuvwxyz1";

		System.out.println(Printer.combineActualExpected(act, expTrue));
		System.out.println(Printer.combineActualExpected(act, expFalse1));
		System.out.println(Printer.combineActualExpected(act, expFalse2));
		System.out.println(Printer.combineActualExpected(act, expFalse3));
		System.out.println(Printer.combineActualExpected(longact, longexp));
		System.out.println(Printer.combineActualExpected(longact, longexpbad));
	}
}