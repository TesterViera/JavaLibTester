package userTypes;

/**
 * A class to represent an Author of the book
 * @author Virag Shah
 * @since 15 February 2011
 */
public class Author {
	/** the name of the author */
	public String name;
	/** the age of the author */
	public int age;
	
	/** The standard full constructor
	 * @param name the name of the author
	 * @param age the age of the author
	 */
	public Author(String name, int age) {
		this.name = name;
		this.age = age;
	}
}