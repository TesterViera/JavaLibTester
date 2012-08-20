package rangeCheck;

import java.util.Comparator;

/**
 * A class that implements Comparable to use in the range tests.
 */
public class Person implements Comparable<Person> {
	/**
	 * the name of this person
	 */
	String name;
	/**
	 * the age of this person
	 */
	int age;

	/**
	 * Constructor
	 *
	 * @param name the name of this person
	 * @param age  the age of this person
	 */
	Person(String name, int age) {
		this.name = name;
		this.age = age;
	}

	/**
	 * Compare this person to the given one by age
	 *
	 * @param that the given person
	 * @return 0 if the two are the same age
	 *         <0 if the first person is younger
	 *         >0 if the first person is older
	 */
	public int compareTo(Person that) {
		return this.age - that.age;
	}
}

/**
 * A class to implement a <code>Comparator</code> for
 * comparing two persons by their age.
 */
class PersonComparator implements Comparator<Person> {
	/**
	 * Compare this first person to the second one by age
	 *
	 * @param one the first person
	 * @param two the second person
	 * @return 0 if the two are the same age
	 *         <0 if the first person is younger
	 *         >0 if the first person is older
	 */
	public int compare(Person one, Person two) {
		return one.age - two.age;
	}
}