package iSame;

import tester.ISame;
import tester.Tester;

/**
 * <P>The programmer may decide that for one or more classes in his program the equality
 * comparison should be handled in a special way (e.g. only some of the fields have to 
 * match, or that Strings should be compared in a case-insensitive manner). In this case
 * these classes should implement the <CODE>{@link ISame ISame}</CODE> interface by 
 * defining the method <CODE>same</CODE> that compares <CODE>this</CODE> object with the 
 * given one. Any two instances of a class that implements the <CODE>{@link ISame ISame}
 * </CODE> interface will be compared by applying the <CODE>same</CODE> method defined in 
 * that class. The remainder of the test evaluation follows the regular rules.</P>
 * 
 * @author Virag Shah
 * @since 5 March 2011
 *
 */
public class ExamplesISame {
	/**
	 * Sample authors
	 */
	public Author sk=new Author("Steven", "King");
	public Author dk=new Author("Dan", "King");
	public Author db=new Author("Dan", "Brown");

	/**
	 * Sample books
	 */
	public Book book1=new Book("title1",sk,2000); 
	public Book book2=new Book("title2",db,2000);
	public Book book3=new Book("title1",db,2000);
	public Book book4=new Book("title1",dk,2000);
	public Book book5=new Book("title2",db,2000);

	/**
	 * <P>Tests if the two books are same. For the two books to be same, they should 
	 * have the same title, same author last name and same year of publication.
	 * Note that they can have different first names for the author.</P>
	 * 
	 * @param t the tester object
	 */
	void testISame(Tester t) {

		t.checkExpect(this.book1, this.book4, "Success: same author last names");
		t.checkExpect(this.book2, this.book5, "Success: same author first names and last names");

		t.checkFail(this.book1, this.book2, "Test to fail: different books, authors");
		t.checkFail(this.book3, this.book4, "Test to fail: different author last names");
		t.checkFail(this.book2, this.book3, "Test to fail: different titles");
	}

	/**
	 * <P>Display all data defined in the <CODE>{@link ExamplesISame ExamplesISame}</CODE> 
	 * class.</P>
	 * <P>Run all tests defined in the <CODE>{@link ExamplesISame ExamplesISame}</CODE> 
	 * class.</P>
	 */
	public static void main(String[] argv) {

		ExamplesISame eis = new ExamplesISame();

		System.out.println("Show all data defined in the ExamplesISame class:");
		System.out.println("\n\n---------------------------------------------------");
		System.out.println("Invoke tester.runReport(this, true, true):");
		System.out.println("Print all data, all test results");

		Tester.runReport(eis, true, true);

		System.out.println("\n---------------------------------------------------");
		System.out.println("\n---------------------------------------------------");
		System.out.println("\n---------------------------------------------------");
		System.out.println("Invoke tester.runReport(this, false, false, true):");
		System.out.println("Print no data, all test results, no warnings");

		Tester.runReport(eis, false, false);
	}
}