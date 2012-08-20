package equivalence;

import tester.Equivalence;

/**
 * A function object to represent equivalence relationship
 * for the given class of data.
 * 
 * @param <T> The data type for which the equivalence is defined
 */
public class EquivBooks implements Equivalence<Book> {
	/**
	 * Determine whether the two given object of the type <code>T</code>
	 * are equivalent.
	 * 
	 * @param t1 this first object 
	 * @param t2the second object
	 * @return true when the two objects are equivalent.
	 */
	public boolean equivalent(Book b1, Book b2){
		return b1.author.name.equals(b2.author.name);
	}
}