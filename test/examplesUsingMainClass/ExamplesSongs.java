package examplesUsingMainClass;


import tester.Example;
import tester.Main;
import tester.TestMethod;
import tester.Tester;


/**
 * <P>Test using <CODE>{@link Main}</CODE> class specified by the annotation @Example.
 * 
 * @author Virag  Shah
 * @since 10 October 2012
 *
 */
@Example //declares this class to be an example class
public class ExamplesSongs {
	String title;
	String artist;
	int duration;
	int rating;
	boolean released;

	public ExamplesSongs(){
		this.title = "Nothing";
		this.artist = "No One";
		this.duration = 0;
		this.rating = 0;
		this.released = false;
	}

	public ExamplesSongs(String title, String artist, int dur, int rate, boolean released){
		this.title = title;
		this.artist = artist;
		this.duration = dur;
		this.rating = rate;
		this.released = released;
	}

	//change the title of this Song
	public void changeName(String title){
		this.title = title;
	}

	//calculate the cost of this song
	public int cost(){
		if(this.released){
			return 2 * this.rating;
		}
		return 0;
	}

	//will be run as a test
	@TestMethod
	public void doSomeStuff(Tester t){
		t.checkExpect(true, "hahaha");
	}

	//will also be run as a test
	public void testCost(Tester t){
		if(this.released){
			t.checkExpect(this.cost(), 2 * this.rating, "TestCost");
		} else{
			t.checkExpect(this.cost(), 0, "TestCost");
		}
	}

	//also run as a test
	public void tests(Tester abc) {
		testCost(abc);
	}
}