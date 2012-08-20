package exceptions;

import tester.Tester;

/**
 * <P> This class comprises test cases for asserting exceptions, being thrown both
 * in object constructors and methods. </P>
 * <P>Displays all data defined in classes
 * <CODE>{@link ExamplesConstructorExceptions ExamplesConstructorExceptions}</CODE>
 * and
 * <CODE>{@link ExamplesMethodExceptions ExamplesMethodExceptions}</CODE>. </P>
 * <P>Runs all tests defined in the classes
 * <CODE>{@link ExamplesConstructorExceptions ExamplesConstructorExceptions}</CODE>
 * and
 * <CODE>{@link ExamplesMethodExceptions ExamplesMethodExceptions}</CODE>. </P>
 * 
 * @author Virag Shah
 * @since 22 February 2011
 * 
 */
public class ExamplesExceptions {
	/**
	 * 
	 * <P>Display all data defined in the <CODE>{@link ExamplesExceptions ExamplesExceptions}</CODE> 
	 * class.</P>
	 * <P>Run all tests defined in the <CODE>{@link ExamplesExceptions ExamplesExceptions}</CODE> 
	 * class.</P>
	 *
	 */
	public static void main(String[] argv) {
		try {
			/** ---------- Test constructor exceptions ---------- */
			
			ExamplesConstructorExceptions ece = new ExamplesConstructorExceptions();
    		
    		System.out.println("Show all data defined in the ExamplesConstructorExceptions class:");
            System.out.println("\n\n---------------------------------------------------");
            System.out.println("Invoke tester.runReport(this, true, true):");
    	  	System.out.println("Print all data, all test results");

            Tester.runReport(ece, true, true);
            
            System.out.println("\n---------------------------------------------------");
            System.out.println("\n---------------------------------------------------");
            System.out.println("\n---------------------------------------------------");
            System.out.println("Invoke tester.runReport(this, false, false, true):");
            System.out.println("Print no data, all test results, no warnings");

            Tester.runReport(ece, false, false);

            /** ---------- Test method exceptions ---------- */
            
    		ExamplesMethodExceptions eme = new ExamplesMethodExceptions();

    		System.out.println("Show all data defined in the ExamplesMethodExceptions class:");
            System.out.println("\n\n---------------------------------------------------");
            System.out.println("Invoke tester.runReport(this, true, true):");
    	  	System.out.println("Print all data, all test results");

            Tester.runReport(eme, true, true);
            
            System.out.println("\n---------------------------------------------------");
            System.out.println("\n---------------------------------------------------");
            System.out.println("\n---------------------------------------------------");
            System.out.println("Invoke tester.runReport(this, false, false, true):");
            System.out.println("Print no data, all test results, no warnings");

            Tester.runReport(eme, false, false);
    		
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    }
}
