package rangeCheck;

import tester.ISame;

/**
 * A class to represent a book with an author.
 * An example of a class with object containment.
 */
public class Author implements ISame<Author> {
	/**
	 * Author's name
	 */
	public String name;
	/**
	 * Author's age
	 */
	public int age;

	/**
	 * Constructor
	 *
	 * @param name the Author's name
	 * @param age  the Author's age
	 */
	public Author(String name, int age) {
		this.name = name;
		this.age = age;
	}

	/**
	 * Implement the <CODE>{@link ISame ISame}</CODE> interface differently
	 * from the one provided by the test harness -- to test the correct dispatch
	 * of the test harness
	 */
	public boolean same(Author that) {
		return this.name.equals(that.name);
	}
}