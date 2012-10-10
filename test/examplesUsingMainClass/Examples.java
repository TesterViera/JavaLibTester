package examplesUsingMainClass;

import tester.Equivalence;
import tester.ISame;
import tester.Tester;

/**
 * <P> Test using the <CODE>{@link Main}</CODE> class specified by passing 
 * <b>examplesUsingMainClass.Examples</b> as a runtime argument to the compiler.
 * 
 * <P>The <CODE>tester</CODE> checks whether the two given objects are equivalent according to 
 * the user-defined function object that implements the <CODE>{@link Equivalence}</CODE> interface.</P>
 * 
 * <P>It is assumed, but not required, that the implementation of the <CODE>{@link Equivalence}</CODE> 
 * interface represents a true equivalence relation, i.e. is reflexive, symmetric and transitive.</P>
 * 
 * @author Virag Shah
 * @since 5 October 2012
 * 
 */
public class Examples {

	public Examples(){
	}

	/** Data: <CODE>{@link Author Author}</CODE> -- implements
	 *  <CODE>{@link ISame ISame}</CODE> interface to compare names only
	 */
	public Author author1 = new Author("author1", 40);
	public Author author2 = new Author("author2", 55);
	public Author author3 = new Author("author1", 66);
	public Author author4 = new Author("author4", 26);

	/** Data: <CODE>{@link Book Book}</CODE> -- 
	 * illustrates object containment 
	 */
	public Book book1 = new Book("title1", author1, 1990); 
	public Book book2 = new Book("title2", author2, 1990);
	public Book book3 = new Book("title3", author3, 2000);
	public Book book4 = new Book("title1", author4, 1990);

	/**
	 * The <CODE>{@link Equivalence Equivalence}</CODE> that compares only the
	 * names of the book authors.
	 */
	Equivalence<Book> equivbooks = new EquivBooks();

	/**
	 * test the method checkEquivalent in the class 
	 * <CODE>{@link Tester Tester}</CODE>
	 * @param t the <CODE>{@link Tester Tester}</CODE> instance to invoke tests
	 */
	void testEquivalence(Tester t) {

		t.checkEquivalent(book1, book3, equivbooks, "Success: same author names");
		t.checkEquivalent(book1, book3, equivbooks); // no testname
		t.checkEquivalent(book1, book4, equivbooks, "Should fail: different authors");
		t.checkEquivalent(book1, book2, equivbooks, "Should fail: different books, authors");
	}
}