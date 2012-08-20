package userTypes;

/**
 * A class to represent a book with an author
 * @author Virag Shah
 * @since 15 February 2011
 */
public class Book {

	/** the title of the book */
	public String title;
	/** the author of the book - an instance of 
	 * <CODE>{@link Author Author}</CODE> class */
	protected Author author;
	/** the price of the book */
	protected float price;
	
	/**
	 * The standard full constructor
	 * @param title the title of the book
	 * @param author the author of the book
	 */
	public Book(String title, Author author) {
		this.title = title;
		this.author = author;
	}
	
	/**
	 * The standard full constructor
	 * @param title the title of the book
	 * @param author the author of the book
	 * @param price the price of the book
	 */
	public Book(String title, Author author, float price) {
		this.title = title;
		this.author = author;
		this.price = price;
	}
}
