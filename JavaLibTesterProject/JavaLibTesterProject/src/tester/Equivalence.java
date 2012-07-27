package tester;

/**
 * A function object to represent equivalence relationship
 * for the given class of data.
 * 
 * @author Viera K. Proulx
 * @since March 2, 2009
 * @param <T> The data type for which the equivalence is defined
 */
public interface Equivalence<T>{
	
	/**
	 * Determine whether the two given object of the type <code>T</code>
	 * are equivalent.
	 * 
	 * @param t1 this first object 
	 * @param t2 the second object
	 * @return true when the two objects are equivalent.
	 */
	public boolean equivalent(T t1, T t2);
}