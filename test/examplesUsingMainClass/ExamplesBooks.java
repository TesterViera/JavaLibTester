package examplesUsingMainClass;

import tester.IExamples;
import tester.Main;
import tester.Tester;

/**
 * <P>Test using <CODE>{@link Main}</CODE> class specified by implementing the {@link IExamples} 
 * interface. Pass the classname 'examplesUsingMainClass.ExamplesBooks' as a runtime argument to 
 * the compiler.
 * 
 * <P>Now the <b>tester</b> will run the <b>tests</> method. It is now much simpler to see which 
 * tests are being run, and alter which tests are processed. Simply comment out a test method 
 * invocation within the tests method, and it will be ignored. This makes it much easier to 
 * maintain your test code!
 * 
 * @author Virag Shah
 * @since 10 October 2012
 *
 */

public class ExamplesBooks implements IExamples {
	String title;
	String author;
	int pages;
	boolean hardcover;

	public ExamplesBooks(){
		this.title = "No Book";
		this.author = "No One";
		this.pages = 0;
		this.hardcover = false;
	}

	public ExamplesBooks(String title, String author, int pages, boolean hardcover){
		this.title = title;
		this.author = author;
		this.pages = pages;
		this.hardcover = hardcover;
	}

	// compute the cost of this book
	public int cost(){
		return pages * (1 / 2);
	}

	// produce a book like this one, but with the given title
	public ExamplesBooks changeTitle(String title){
		return new ExamplesBooks(title, this.author, this.pages, this.hardcover);
	}

	// test the method 'changeTitle'
	public void testTitleChange(Tester t){
		t.checkExpect(this.changeTitle("New Title"),
				new ExamplesBooks("New Title", this.author, this.pages, this.hardcover));
	}

	// test the method 'cost'
	public void testCost(Tester t){
		t.checkExpect(this.cost(), this.pages * (1 / 2));
	}

	// run all tests
	public void tests(Tester t) {
		testCost(t);
		testTitleChange(t);
	}
}
