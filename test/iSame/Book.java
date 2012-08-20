package iSame;

import tester.ISame;

/**
 * A class to represent a book with an author.
 * An example of a class with containment.
 */
public class Book implements ISame<Book> {
	/** the title of the book */
	public String title;
	/** the author of the book - an instance of 
	 * <CODE>{@link Author Author}</CODE> class */
	protected Author author;
	/** the year of publication */
	protected int year;

	/**
	 * Constructor
	 * 
	 * @param title the tile of the book
	 * @param author the author of the book
	 * @param year the year of publication
	 */
	public Book(String title, Author author, int year) {
		this.title=title;
		this.author=author;
		this.year=year;
	}

	/**
	 * Is this book the same as that book?
	 * 
	 * @param that the book to compare with
	 * @return true if this book is the same as that book
	 */
	public boolean same(Book that) {
		return 
		this.title.equals(that.title) &&
		this.author.same(that.author) &&
		this.year == that.year;
	}
}