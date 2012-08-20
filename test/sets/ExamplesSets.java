package sets;

import java.util.Arrays;
import java.util.HashSet;
import java.util.TreeSet;

import tester.Tester;

/**
 * <P>Class that test the <CODE>checkSet</CODE> in the <CODE>{@link Tester Tester}</CODE> class 
 * both for <CODE>HashSet</CODE> and <CODE>TreeSet</CODE>.</P>
 * 
 * @author Virag Shah
 * @since 18 March 2011
 *
 */
public class ExamplesSets {

	/**
	 * Create instances of Song
	 */
	Song song1 = new Song("title1", 4);
	Song song1a = new Song("title1", 4);
	Song song1b = new Song("title1", 2);
	Song song2 = new Song("title2", 2);
	Song song2a = new Song("title2", 2);
	Song song2b = new Song("title2", 4);
	Song song3 = new Song("title3", 2);
	Song song4 = this.song3;
	/**
	 * Create HashSets of Song
	 */
	HashSet<Song> songSet1a = new HashSet<Song>(Arrays.asList(this.song1a, this.song2, this.song3));
	HashSet<Song> songSet1b = new HashSet<Song>(Arrays.asList(this.song3, this.song1a, this.song2));
	HashSet<Song> songSet1c = new HashSet<Song>(Arrays.asList(this.song3, this.song1, this.song2));
	HashSet<Song> songSet1d = new HashSet<Song>(Arrays.asList(this.song4, this.song1, this.song2));
	HashSet<Song> songSet2a = new HashSet<Song>(Arrays.asList(this.song3));
	HashSet<Song> songSet2b = new HashSet<Song>(Arrays.asList(this.song4));
	HashSet<Song> songSet3a = new HashSet<Song>(Arrays.asList(this.song1));
	HashSet<Song> songSet3b = new HashSet<Song>(Arrays.asList(this.song1a));
	/**
	 * Create TreeSets of Song
	 */
	TreeSet<Song> songSet4a = new TreeSet<Song>(Arrays.asList(this.song1a));
	TreeSet<Song> songSet4b = new TreeSet<Song>(Arrays.asList(this.song1a));
	TreeSet<Song> songSet4c = new TreeSet<Song>(Arrays.asList(this.song1));
	TreeSet<Song> songSet5a = new TreeSet<Song>(Arrays.asList(this.song3));
	TreeSet<Song> songSet5b = new TreeSet<Song>(Arrays.asList(this.song4));

	/**
	 * Tests for <CODE>checkSet</CODE> using both <CODE>HashSet</CODE> and <CODE>TreeSet</CODE>.
	 * 
	 * @param t the <CODE>{@link Tester Tester}</CODE> that performs the tests
	 */
	public void testSets(Tester t) {
		/**
		 * Tests to compare HashSets
		 */
		t.checkSet(songSet1a, songSet1b, "Success: HashSet same lists differently ordered");
		t.checkSet(songSet2a, songSet2b, "Success: HashSet same lists");
		t.checkSet(songSet3a, songSet3a, "Success: HashSet same lists");
		t.checkSet(songSet1c, songSet1d, "Success: HashSet similar lists");
		/**
		 * Tests to compare TreeSets
		 */
		t.checkSet(songSet4a, songSet4b, "Success: TreeSet same lists");
		t.checkSet(songSet5a, songSet5b, "Success: TreeSet same lists");
		t.checkSet(songSet4a, songSet4a, "Success: TreeSet same lists");
		/**
		 * Tests to compare HashSets with TreeSets
		 */
		t.checkSet(songSet2a, songSet5a, "Success: Comparing HashSet and TreeSet");
		t.checkSet(songSet5b, songSet2a, "Success: Comparing HashSet and TreeSet");
		/**
		 * Tests that should fail
		 */
		t.checkSet(songSet1a, songSet1c, "Should fail: HashSet similar elements but different lists");
		t.checkSet(songSet3a, songSet3b, "Should fail: HashSet similar elements but different lists");
		t.checkSet(songSet3a, songSet2a, "Should fail: HashSet different lists");

		t.checkSet(songSet4a, songSet5a, "Should fail: TreeSet different lists");
		t.checkSet(songSet4a, songSet4c, "Should fail: TreeSet similar elements but different lists");

		t.checkSet(songSet3b, songSet4c, "Should fail: Comparing different TreeSet and Hashet lists");
		t.checkSet(songSet1d, songSet5b, "Should fail: Comparing different TreeSet and Hashet lists");
	}

	/**
	 * <P>Display all data defined in the <CODE>{@link ExamplesSets ExamplesSets}</CODE> 
	 * class.</P>
	 * <P>Run all tests defined in the <CODE>{@link ExamplesSets ExamplesSets}</CODE> 
	 * class.</P>
	 */
	public static void main(String[] argv)
	{
		ExamplesSets es = new ExamplesSets();

		System.out.println("Show all data defined in the ExamplesSets class:");
		System.out.println("\n\n---------------------------------------------------");
		System.out.println("Invoke tester.runReport(this, true, true):");
		System.out.println("Print all data, all test results");

		Tester.runReport(es, true, true);

		System.out.println("\n---------------------------------------------------");
		System.out.println("\n---------------------------------------------------");
		System.out.println("\n---------------------------------------------------");
		System.out.println("Invoke tester.runReport(this, false, false, true):");
		System.out.println("Print no data, all test results, no warnings");

		Tester.runReport(es, false, false);
	}
}
