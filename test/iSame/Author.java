package iSame;

import tester.ISame;

/**
 * A class to represent an author with first name and last name.
 * An example of a class with object containment.
 */
public class Author implements ISame<Author> {
	/** Author's first name */
	public String firstName;
	/** Author's last name */
	public String lastName;

	/**
	 * Constructor
	 * 
	 * @param firstName the author's first name
	 * @param lastName the author's last name
	 */
	public Author(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	/**
	 * Implement the <CODE>{@link ISame ISame}</CODE> interface differently 
	 * from the one provided by the test harness -- to test the correct dispatch
	 * of the test harness
	 * 
	 * Two authors are same if their last names match
	 */
	public boolean same(Author that) {
		return this.lastName.equals(that.lastName);
	}
}