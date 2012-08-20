package tester;

import java.lang.reflect.*;
import java.util.*;

/**
 * Copyright 2007, 2008, 2010 Viera K. Proulx
 * This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)
 */

/**
 * This class leverages the use of the Java reflection classes to implement
 * comparison for extensional equality between two objects of arbitrary type.
 * 
 * @author Viera K. Proulx, Sachin Venugopalan
 * @since 3 March 2008, 5 October 2010, 17 June 2012
 * 
 */
public class Inspector {

	/** the tolerance for comparison of relative difference of inexact numbers */
	protected static double TOLERANCE = 0.001;

	/** current indentation level for pretty-printing */
	protected static String INDENT = "  ";

	/**
	 * a hashmap of pairs of hashcodes for two objects that are being compared:
	 * if the same pair is compared again, the loop of comparisons stops and
	 * produces <code>true</code>
	 */
	private HashMap<Integer, Integer> hashmap = new HashMap<Integer, Integer>();

	/** set to <code>true</code> if comparison involved inexact numbers */
	protected static boolean INEXACT_COMPARED = false;

	/** set to <code>true</code> if comparison of inexact numbers is expected */
	protected static boolean INEXACT_ALLOWED = false;

	/**
	 * Constructor: For the given instance get its <code>Class</code> and
	 * fields.
	 */
	public Inspector() {
	}

	/**
	 * Has there been a violation -- was there inexact comparison done when it
	 * was not allowed?
	 * 
	 * @return <code>true</code> if inexact values have been involved in the last comparison
	 *         and the inexact comparison was not allowed
	 */
	protected boolean inexactViolation() {
		return INEXACT_COMPARED && !INEXACT_ALLOWED;
	}

	/**
	 * <P>
	 * Compare the two given objects for extensional equality.
	 * <P>
	 * <P>
	 * Consider inexact numbers (types <code>double</code>, <code>float</code>,
	 * <code>Double</code>, <code>Float</code>) to be the same if the relative
	 * difference is below <code>TOLERANCE</code>
	 * </P>
	 * <P>
	 * Use <code>==</code> for all other primitive types and their wrapper
	 * classes.
	 * </P>
	 * <P>
	 * Use the <code>String equals</code> method to compare two objects of the
	 * type <code>String</code>.
	 * <P>
	 * Traverse over <code>Arrays</code>, datasets that implement
	 * <CODE>{@link Traversal Traversal}</CODE> and datasets that implement
	 * <code>Iterable</code>interface.
	 * </P>
	 * <P>
	 * For datasets that implement the <code>Map</code> interface compare their
	 * sizes, and entry sets for set equality of key-value pairs.
	 * </p>
	 * <P>
	 * For the instances of a class that implements the
	 * <CODE>{@link ISame ISame}</CODE> interface, use its <CODE>same</CODE>
	 * method.
	 * </P>
	 * <P>
	 * Special tests allow the user to provide the method to invoke on the given
	 * object with the given argument list
	 * </P>
	 * <P>
	 * Additionally, user may specify that a method invocation must throw a
	 * given <code>Exception</code> with the given message.
	 * </P>
	 * 
	 * @param <T>
	 *            the type of the objects being compared
	 * @param obj1
	 * @param obj2
	 * @return <code>true<code> if the two given object are the same
	 */
	public <T> boolean isSame(T obj1, T obj2) {
		this.hashmap.clear();
		INEXACT_COMPARED = false;
		return isSamePrivate(obj1, obj2);
	}

	/**
	 * Set up the parameters for inexact test: report failure if tolerance is
	 * set below 0
	 * 
	 * @param tolerance
	 *            the desired tolerance for the inexact tests
	 * @return <code>false</code> if tolerance is negative
	 */
	protected boolean inexactTest(double tolerance) {
		INEXACT_COMPARED = false;
		INEXACT_ALLOWED = true;
		// check if the provided tolerance is > 0 - fail if not.
		TOLERANCE = tolerance;
		return (tolerance < 0);
	}

	/**
	 * Set up the parameters for exact test: report failure if inexact
	 * comparisons are made
	 * 
	 * @return <code>true<code>
	 */
	protected boolean exactTest() {
		INEXACT_COMPARED = false;
		INEXACT_ALLOWED = false;
		return true;
	}

	/**
	 * Compare two objects that implement the <code>Iterable</code> interface
	 * 
	 * @param obj1
	 *            an <code>Iterable</code> object
	 * @param obj2
	 *            an <code>Iterable</code> object
	 * @return <code>true<code> if the two given <code>Iterable</code>object are the same
	 */
	public <T> boolean isSameIterable(Iterable<T> obj1, Iterable<T> obj2) {
		this.hashmap.clear();
		INEXACT_ALLOWED = false;
		INEXACT_COMPARED = false;
		return isSameIterablePrivate(obj1, obj2);
	}

	/**
	 * Compare two objects that implement the <code>Set</code> interface
	 * 
	 * @param obj1
	 *            a <code>Set</code> object
	 * @param obj2
	 *            a <code>Set</code> object
	 * @return <code>true<code> if the two given object represent the same set
	 */
	public <T> boolean isSameSet(Set<T> obj1, Set<T> obj2) {
		this.hashmap.clear();
		INEXACT_ALLOWED = false;
		INEXACT_COMPARED = false;
		return isSameSetPrivate(obj1, obj2);
	}

	/**
	 * Compare two objects that implement the <code>Traversal</code> interface
	 * 
	 * @param obj1
	 *            a <code>Traversal</code> object
	 * @param obj2
	 *            a <code>Traversal</code> object
	 * @return <code>true<code> if the two given object represent the same
	 *         <code>Traversal</code>
	 */
	public <T> boolean isSameTraversal(Traversal<T> obj1, Traversal<T> obj2) {
		this.hashmap.clear();
		INEXACT_ALLOWED = false;
		INEXACT_COMPARED = false;
		return isSameTraversalPrivate(obj1, obj2);
	}

	/* ------------ THE METHODS USED TO COMPARE TWO OBJECTS ------------- */
	/**
	 * <P>
	 * Invoked by <CODE>isSame</CODE> method after the hashmap
	 * that records seen objects has been cleared.
	 * </P>
	 * 
	 * <P>
	 * Compare the two given objects for extensional equality.
	 * <P>
	 * <P>
	 * Consider inexact numbers (types <code>double</code>, <code>float</code>,
	 * <code>Double</code>, <code>Float</code>) to be the same if the relative
	 * difference is below <code>TOLERANCE</code>
	 * </P>
	 * <P>
	 * Use <code>==</code> for all other primitive types and their wrapper
	 * classes.
	 * </P>
	 * <P>
	 * Use the <code>String equals</code> method to compare two objects of the
	 * type <code>String</code>.
	 * <P>
	 * Compare two objects in the same class that implements the
	 * <code>Set</code> interface by their size and by matching the elements
	 * using the <code>equals</code> method.
	 * </P>
	 * <P>
	 * Traverse over <code>Arrays</code>, datasets that implement
	 * <CODE>{@link Traversal Traversal}</CODE> and datasets that implement
	 * <code>Iterable</code>interface.
	 * </P>
	 * <P>
	 * For datasets that implement the <code>Map</code> interface compare their
	 * sizes, and entry sets for set equality of key-value pairs.
	 * </p>
	 * <P>
	 * For the instances of a class that implements the
	 * <CODE>{@link ISame ISame}</CODE> interface, use its <CODE>same</CODE>
	 * method.
	 * </P>
	 * <P>
	 * Special tests allow the user to provide the method to invoke on the given
	 * object with the given argument list
	 * </P>
	 * <P>
	 * Additionally, user may specify that a method invocation must throw a
	 * given <code>Exception</code> with the given message.
	 * </P>
	 * 
	 * @param obj1
	 *            typically the actual value
	 * @param obj2
	 *            typically the expected value
	 * @return <code>true<code> if the two given object are the same
	 */
	@SuppressWarnings("unchecked")
	private <T> boolean isSamePrivate(T obj1, T obj2) {

		/** make sure both objects are not null */
		if (obj1 == null)
			return obj2 == null; // Returns true iff obj1 & obj2 are both null,
									// otherwise returns false
		if (obj2 == null)
			return false;
		if (obj1 == obj2)
			return true; // obj1 and obj2 are the same object

		/** handle the world teachpack colors */
		if (this.checkIColors(obj1, obj2))
			return true;

		Reflector r1 = new Reflector(obj1);
		Reflector r2 = new Reflector(obj2);

		boolean sameClass = r1.sampleClass.equals(r2.sampleClass);

		if (sameClass) {
		  String r1Name = r1.sampleClass.getName();
      String r2Name = r2.sampleClass.getName();
		  

			/** handle String objects separately */
			if (r1Name.equals("java.lang.String")) {
				return obj1.equals(obj2);
			}

			/** handle the primitive types separately */
			if (r1.sampleClass.isPrimitive()) {
				if (isDouble(r1Name))
					return isSameDouble((Double) obj1, (Double) obj2);
				else if (isFloat(r1Name))
					return isSameFloat((Float) obj1, (Float) obj2);
				else
					return (obj1.equals(obj2));
			}

			/** handle the wrapper types separately */
			if (isWrapperClass(r1Name)) {
				if (isDouble(r1Name))
					return isSameDouble((Double) obj1, (Double) obj2);
				else if (isFloat(r1Name))
					return isSameFloat((Float) obj1, (Float) obj2);
				else
					return (obj1.equals(obj2));
			}

			/** handle the Canvas class in the draw teachpack */
			if (isOurCanvas(r1Name))
					return (obj1.equals(obj2));
			
			/** handle the images in the WorldImage hierarchy */
			if (isWorldImage(r1Name))
					return obj1.equals(obj2);
      
      /** handle the images in the tunes package */
      if (isTunesPackage(r1Name))
          return obj1.equals(obj2);
				
			
			/**
			 * Record the information about the object compared and check
			 * whether the current pair has already been tested for equality, or
			 * has been viewed before.
			 */
			Integer i1 = System.identityHashCode(obj1);
			Integer i2 = System.identityHashCode(obj2);
			/*
			 * <<NOTE>>
			 * [Author: Sachin Venugopalan; Date: 03/03/2012]
			 * System.identityHashCode(obj) is used instead of obj.hashCode() for 2
			 * reasons: 
			 * 1. The default (Java-platform-implemented) hashcode for an
			 * Object, although not guaranteed to be unique, may for all practical
			 * purposes be treated as such. However, in the event that hashcode() of
			 * user-defined objects is overridden such that objects that are not the
			 * "same" ["same()"-ness in our Tester framework is analogous to Java's
			 * definition of "equal()"] return the same hashcode [since there exists
			 * no restriction in language/convention that would disallow this; e.g.
			 * all objects returning hashcode 43, say, is permitted, albeit regarded
			 * a bad hashing function], we do not wish to call upon the overridden
			 * version of hashcode(); instead we'd like to use the value retunred by
			 * the original implementation. System.identityHashCode(obj) achieves
			 * this. 
			 * 2. Asking for the hashcode of self-referential definitions
			 * using Java's in-built container objects like java.unil.Arraylist
			 * (which extends AbstractList that overrides hashcode()) causes Java to
			 * spawn a circular call structure that loops infinitely; it then
			 * becomes the responsibility of the programmer to make sure that such a
			 * structure is culled!!) Calling System.identityHashCode(obj) instead
			 * avoids this complexity.
			 */ 

// this code can be used for debugging:
//			if(showComparisonDetail){
//				System.out.println("Comparing:-");
//				System.out.println("\tclass 1:" + r1.sampleClass.getName()
//						+ "; hashcode:" + i1);
//				System.out.println("\tclass 2:" + r2.sampleClass.getName()
//						+ "; hashcode:" + i2);
//				System.out.println("\tpair-hashmap key:" + i1 * i1 + i2 * i2);
//			}

			Integer i2match = this.hashmap.get(i1 * i1 + i2 * i2);
			/*
			 * <<NOTE>>
			 * [Author: Sachin Venugopalan; Date: 03/03/2012]
			 * In order to handle circularity, we need to keep track of pairs of
			 * objects that have already been compared for equality (or for
			 * which an equality check has already been initiated, and is
			 * pending completion owing to a circular definition). In order to
			 * look up pairs of already-seen objects efficiently, we need a
			 * hash-based lookup directory such as a Hashset. The "key" (or
			 * identifier) that is stored in the lookup directory should
			 * identify the pair of objects; also it ought to be "symmetrical"
			 * i.e. the pair of objects is not to be treated as an ordered pair
			 * -- this stems from the fact that equality (rather, "sameness") 
			 * between two objects itself is a symmetrical relationship.
			 * In other words, having seen the pair (obj1, obj2) is identical to
			 * having seen the pair (obj2, obj1) We have chosen i1*i1 + i2*i2 to
			 * be this symmetrical identifier of the pair of objects, where i1 &
			 * i2 are their (original) hashcodes. This choice is perhaps better
			 * than i1*i2, say, because such an alternate choice potentially
			 * leads to more collisions. However, there is no absolute guarantee
			 * of uniqueness of i1*i1 + i2*i2 (simply because there is no
			 * absolute guarantee of uniqueness of i1 and i2; we simply assume
			 * their uniqueness for all practical purposes, given Java's current
			 * implementation of hashcodes that relates to the unique memory
			 * locations at which objects reside) Hence, we perform a second
			 * "validation" step to confirm the identity of the pair of objects
			 * by seeing if they map to a chosen value i1+i2 (note that this is
			 * again symmetric) The second validation step may not be very
			 * meaningful, and for the time being, it exists more for legacy reasons.
			 */
			if ((i2match != null) && (i2match.equals(i1 + i2))) {
//				if(showComparisonDetail)
//					System.out.println("\tcheck done already for this pair:" + i1
//							* i1 + i2 * i2 + " => " + i2match.toString());
				return true;
			}
			this.hashmap.put(i1 * i1 + i2 * i2, i1 + i2);

			/** handle Array objects */
			if (obj1.getClass().isArray() && obj2.getClass().isArray()
					&& obj1.getClass() == obj2.getClass()) {
				int length = Array.getLength(obj1);
				if (Array.getLength(obj2) == length) {
					for (int i = 0; i < length; i++) {
						if (!isSame(Array.get(obj1, i), Array.get(obj2, i)))
							return false;
					}
					return true;
				} else
					return false;
			}

			/** handle ISame objects by delegating to the user-defined method */
			if ((obj1 instanceof ISame) && (obj2 instanceof ISame))
				return ((ISame) obj1).same((ISame) obj2);

			/**
			 * handle the Set objects in the Java Collection library by
			 * comparing their size, and data items -- here we use the equals
			 * method when comparing the set elements
			 */
			if ((obj1 instanceof Set) && (obj2 instanceof Set)
					&& obj1.getClass().getName().startsWith("java.util"))
				return isSameSet((Set) obj1, (Set) obj2);

			/**
			 * handle Iterable objects in the Java Collection library by
			 * comparing their size, then comparing the elements pairwise
			 */
			if ((obj1 instanceof Iterable) && (obj2 instanceof Iterable)
					&& obj1.getClass().getName().startsWith("java.util"))
				return isSameIterablePrivate((Iterable<T>) obj1,
						(Iterable<T>) obj2);

			/**
			 * handle the Map objects in the Java Collection library by
			 * comparing their size, the key-set, and key-value mappings
			 */
			if ((obj1 instanceof Map) && (obj2 instanceof Map)
					&& obj1.getClass().getName().startsWith("java.util"))
				return isSameMap((Map) obj1, (Map) obj2);

			/** now handle the general case */
			boolean sameValues = true;
			int i = 0;
			try {
				for (; i < Array.getLength(r1.sampleDeclaredFields); i++) {
					sameValues = sameValues
							&& isSamePrivate(
									r1.sampleDeclaredFields[i].get(obj1),
									r2.sampleDeclaredFields[i].get(obj2));
				}
			} catch (IllegalAccessException e) {
				System.out.println("same comparing "
						+ r1.sampleDeclaredFields[i].getType().getName()
						+ " and "
						+ r2.sampleDeclaredFields[i].getType().getName()
						+ "cannot access the field " + i + " message: "
						+ e.getMessage());
				System.out.println("class 1: " + r1.sampleClass.getName());
				System.out.println("class 2: " + r2.sampleClass.getName());
			}

			return sameValues;
		} else
			return false;
	}

	/**
	 * Determine whether the relative difference between two double numbers is
	 * below the expected <code>TOLERANCE</code> Measure absolute tolerance, if
	 * one of the numbers is exact zero.
	 * 
	 * @param d1
	 *            the first inexact number
	 * @param d2
	 *            the second inexact number
	 * @return <code>true<code> is the two numbers are nearly the same
	 */
	protected boolean isSameDouble(double d1, double d2) {
		if (d1 - d2 == 0.0)
			return true;
		else {
			INEXACT_COMPARED = true;
			if (d1 == 0.0)
				return Math.abs(d2) < TOLERANCE;
			if (d2 == 0.0)
				return Math.abs(d1) < TOLERANCE;

			else
				// d1, d2 are non-zero
				// return (Math.abs(d1 - d2) / (Math.max (Math.abs(d1),
				// Math.abs(d2))))
				return Math.abs(d1 - d2) / (Math.abs((d1 + d2) / 2)) < TOLERANCE;
		}
	}

	/**
	 * Determine whether the relative difference between two float numbers is
	 * below the expected <code>TOLERANCE</code> Measure absolute tolerance, if
	 * one of the numbers is exact zero.
	 * 
	 * @param f1
	 *            the first inexact number
	 * @param f2
	 *            the second inexact number
	 * @return <code>true<code> is the two numbers are nearly the same
	 */
	protected boolean isSameFloat(float f1, float f2) {
		if (f1 - f2 == 0.0)
			return true;
		else {
			INEXACT_COMPARED = true;
			Double d1 = ((Float) f1).doubleValue();
			Double d2 = ((Float) f2).doubleValue();

			if (f1 == 0.0)
				return Math.abs(d2) < TOLERANCE;
			if (f2 == 0.0)
				return Math.abs(d1) < TOLERANCE;

			// f1, f2 are non-zero
			return (Math.abs(d1 - d2) / (Math.max(Math.abs(d1), Math.abs(d2)))) < TOLERANCE;
		}
	}

	/**
	 * Determine whether two <code>Iterable</code> objects generate the same
	 * data elements in the same order.
	 * 
	 * @param obj1
	 *            the first <code>Iterable</code> dataset
	 * @param obj2
	 *            the second <code>Iterable</code> dataset
	 * @return <code>true<code> is the two datasets are extensionally equal
	 */
	private <T> boolean isSameIterablePrivate(Iterable<T> obj1, Iterable<T> obj2) {
		Iterator<T> it1 = obj1.iterator();
		Iterator<T> it2 = obj2.iterator();

		return this.isSameData(it1, it2);
	}

	/**
	 * Determine whether two <code>Traversal</code> objects generate the same
	 * data elements in the same order.
	 * 
	 * @param obj1
	 *            the first <code>Traversal</code> dataset
	 * @param obj2
	 *            the second <code>Traversal</code> dataset
	 * @return <code>true<code> is the two datasets are extensionally equal
	 */
	private <T> boolean isSameTraversalPrivate(Traversal<T> obj1,
			Traversal<T> obj2) {
		Traversal<T> it1 = obj1;
		Traversal<T> it2 = obj2;

		return this.isSameTraversalData(it1, it2);
	}

	/**
	 * Determine whether two <code>Iterator</code>s generate the same data in
	 * the same order.
	 * 
	 * @param it1
	 *            the first <code>Iterator</code>
	 * @param it2
	 *            the second <code>Iterator</code>
	 * @return <code>true</code> is both datasets contained the same data elements (in the
	 *         same order)
	 */
	protected <T> boolean isSameData(Iterator<T> it1, Iterator<T> it2) {
		/** if the first dataset is empty, the second one has to be too */
		if (!it1.hasNext()) {
			return !it2.hasNext();
		}
		/** the first dataset is nonempty - make sure the second one is too... */
		else if (!it2.hasNext())
			return false;
		/** now both have data - compare the next pair of data and recur */
		else {
			return this.isSamePrivate(it1.next(), it2.next())
					&& this.isSameData(it1, it2);
		}
	}

	/**
	 * Determine whether two <code>Traversal</code>s generate the same data in
	 * the same order.
	 * 
	 * @param tr1
	 *            the first <code>Traversal</code>
	 * @param tr2
	 *            the second <code>Traversal</code>
	 * @return <code>true</code> is both datasets contained the same data elements 
	 *         (in the same order)
	 */
	protected <T> boolean isSameTraversalData(Traversal<T> tr1, Traversal<T> tr2) {
		/** if the first dataset is empty, the second one has to be too */
		if (tr1.isEmpty()) {
			return tr2.isEmpty();
		}
		/** the first dataset is nonempty - make sure the second one is too... */
		else if (tr2.isEmpty())
			return false;
		/** now both have data - compare the next pair of data and recur */
		else {
			return this.isSamePrivate(tr1.getFirst(), tr2.getFirst())
					&& this.isSameTraversalData(tr1.getRest(), tr2.getRest());
		}
	}

	/**
	 * Determine whether two <code>Map</code> objects have the same key-value
	 * sets of data
	 * 
	 * @param obj1
	 *            the first <code>Map</code> dataset
	 * @param obj2
	 *            the second <code>Map</code> dataset
	 * @return <code>true</code> if the two maps are extensionally equal
	 */
	protected <K, V> boolean isSameMap(Map<K, V> obj1, Map<K, V> obj2) {
		// make sure both maps have the same size keyset
		if (obj1.size() != obj2.size())
			return false;

		// the key sets for the two maps have the same size - pick one
		Set<K> set1 = obj1.keySet();

		for (Object key : set1) {
			// make sure each key is in both key sets
			if (!obj2.containsKey(key))
				return false;

			// now compare the corresponding values
			if (!this.isSamePrivate(obj1.get(key), obj2.get(key)))
				return false;
		}

		// all tests passed
		return true;
	}

	/**
	 * Determine whether two <code>Set</code> objects contain the same data
	 * 
	 * @param obj1
	 *            the first <code>Set</code> dataset
	 * @param obj2
	 *            the second <code>Set</code> dataset
	 * @return <code>true</code> if the two sets contain the same elements, compared by 
	 *         using the Java <code>equals</code> method.
	 */
	protected <T> boolean isSameSetPrivate(Set<T> obj1, Set<T> obj2) {
		// make sure both sets have the same size number of elements
		if (obj1.size() != obj2.size())
			return false;

		// return obj1.containsAll(obj2);

		// the sets have the same size - pick one
		for (T item1 : obj1) {
			boolean match = false; // did we find a match for this item?

			// make sure each item of the first set matches an item in the
			// second set
			for (T item2 : obj2) {
				// now compare the corresponding values using 'equals' as
				// required
				// for the Set interface
				if (item1.equals(item2))
					match = true; // match found
			}

			// no match found for this item
			if (match == false) {
				System.out.println("Mismatch for " + obj1 + " and " + obj2);
				return false;
			}
		}

		// all tests passed
		return true;

	}

	/** ------- THE METHODS USED TO DETERMINE THE TYPES OF OBJECTS ---------- */
	/**
	 * Does the class with the given name represent inexact numbers?
	 * 
	 * @param name
	 *            the name of the class in question
	 * @return <code>true</code> if this is <code>double</code>, 
	 *         <code>float</code>, or <code>Double</code> or <code>Float</code>
	 */
	protected boolean isDouble(String name) {
		return name.equals("double") || name.equals("java.lang.Double");
	}

	/**
	 * Does the class with the given name represent inexact numbers?
	 * 
	 * @param name
	 *            the name of the class in question
	 * @return <code>true</code> if this is double, float, or Double or Float
	 */
	protected boolean isFloat(String name) {
		return name.equals("float") || name.equals("java.lang.Float");
	}

	/**
	 * Does the class with the given name represent a wrapper class for a
	 * primitive class?
	 * 
	 * @param name
	 *            the name of the class in question
	 * @return <code>true</code> if this is a class that represents a wrapper class
	 */
	protected static boolean isWrapperClass(String name) {
		return name.equals("java.lang.Integer")
				|| name.equals("java.lang.Long")
				|| name.equals("java.lang.Short")
				|| name.equals("java.math.BigInteger")
				|| name.equals("java.math.BigDecimal")
				|| name.equals("java.lang.Float")
				|| name.equals("java.lang.Double")
				|| name.equals("java.lang.Byte")
				|| name.equals("java.lang.Boolean")
				|| name.equals("java.lang.Character");
	}

	/**
	 * Is this a name of one of our <code>Canvas</code> classes?
	 * 
	 * @param name the name of the class to consider
	 * @return <code>true</code> if the given name is one of our 
	 * <code>Canvas<code> classes
	 */
	protected static boolean isOurCanvas(String name) {
		return name.equals("draw.Canvas") || name.equals("idraw.Canvas")
				|| name.equals("adraw.Canvas") || name.equals("funworld.Canvas")
				|| name.equals("impworld.Canvas") || name.equals("appletworld.Canvas")
				|| name.equals("javalib.worldcanvas.WorldCanvas")
        || name.equals("javalib.worldcanvas.AppletCanvas")
				|| name.equals("impsoundworld.Canvas")
        || name.equals("appletsoundworld.Canvas");
	}

  /**
   * Is this a name of one of classes in the <code>javalib.tunes</code>
   * package?
   * 
   * @param name the name of the class to consider
   * @return <code>true</code> if the given name is a class in 
   * the <code>javalib.tunes</code> package
   */
  protected static boolean isTunesPackage(String name) {
    return name.startsWith("javalib.tunes.");
  }

	/**
	 * If <code>obj1</code> is an instance of one of the <code>IColor</code>s
	 * compare the objects by just checking that the class names are the same
	 * 
	 * @param obj1
	 *            the object that could be an <code>IColor</code>
	 * @param obj2
	 *            the object we are comparing with
	 * @return <code>true</code> if the test succeeds
	 */
	protected <T> boolean checkIColors(T obj1, T obj2) {
		/** Convert IColor-s from the colors teachpack to java.awt.Color */
		if (obj1.getClass().getName().equals("colors.Red"))
			return obj2.getClass().getName().equals("colors.Red");
		if (obj1.getClass().getName().equals("colors.White"))
			return obj2.getClass().getName().equals("colors.White");
		if (obj1.getClass().getName().equals("colors.Blue"))
			return obj2.getClass().getName().equals("colors.Blue");
		if (obj1.getClass().getName().equals("colors.Black"))
			return obj2.getClass().getName().equals("colors.Black");
		if (obj1.getClass().getName().equals("colors.Green"))
			return obj2.getClass().getName().equals("colors.Green");
		if (obj1.getClass().getName().equals("colors.Yellow"))
			return obj2.getClass().getName().equals("colors.Yellow");
		return false;
	}

	/**
	 * If <code>name</code> is the name of one of the classes that 
	 * extend the <code>WorldImage</code> class within the interim
	 * implementation of the <em>World</em> library,
	 * compare the objects by just checking that the class names are the same
	 * 
	 * <em>Note:</em> The new (<code>javalib</code> package) implementation supports 
	 * the comparison of images that checks the relevant image parameters 
	 * (size, color, pinhole location, etc.) It also moved the definition of the 
	 * <em>WorldImage</em> class hierarchy into its own package, eliminating the 
	 * code duplication.
	 * 
	 * @param name
	 *            the class name that could be the name of the old <code>WorldImage</code> class
	 * @return <code>true</code> if the the given name matches one of the class names in the old 
	 * <code>WorldImage</code> hierarchy
	 */
	protected static <T> boolean isWorldImage(String name){
		ArrayList<String> worldPackageNames = new ArrayList<String>(Arrays.asList(
				"funworld", "impworld", "appletworld", 
				"impsoundworld", "appletsoundworld"));
		ArrayList<String> worldImageClassNames = new ArrayList<String>(Arrays.asList(
				"CircleImage", "DiskImage", "EllipseImage", "FrameImage",
				"FromFileImage", "LineImage", "OvalImage", "OverlayImages", 
				"OverlayImagesXY", "RectangleImage", "TextImage"));
		for(String packagename: worldPackageNames){
			if (name.startsWith(packagename))
		
				for (String classname : worldImageClassNames){
			        if (name.endsWith(classname))
				return true;
				}
		}
		return false;
	}
}