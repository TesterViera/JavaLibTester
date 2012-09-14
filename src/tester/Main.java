package tester;

import java.lang.reflect.*;
import java.util.Set;

import tester.cobertura.AnnotationScanner;

/**
 *  Copyright 2008, 2009, 2010, 2011 Viera K. Proulx, Matthias Felleisen
 * This program is distributed under the terms of the
 * GNU Lesser General Public License (LGPL)
 */


/**
 * <P>
 * The <code>main</code> method in this class uses the command-line argument to
 * determine the class that contains the test cases. If no class name is given
 * "Examples" is assumed.
 * </P>
 * <P>
 * The main method instantiates this class and invokes the
 * <CODE>{@link Tester Tester}</CODE> on this new instance.
 * </P>
 *
 * @author Viera K. Proulx, Matthias Felleisen, Weston Jossey
 * @since 3 March 2008, 16 October 2008, 20 December 2008, 13 May 2009,
 * 9 February 2010, 18 February 2011
 *
 */
public class Main {
	/**
	 * <p>Method that creates an instance of the <code>Tester</code> and an
	 * instance of the class that defines the tests, invokes the test evaluation
	 * by the <code>Tester</code>, and reports the results.</p>
	 * <p>The method scans all classes for <code>@Example</code> annotation
	 * and looks in those classes for methods annotated with the
	 * <code>@TestMethod</code> annotation. Tests for each class are evaluated
	 * and reported separately/consecutively.</p>
	 * <p>If there are no annotated classes the class <code>Examples</code> or
	 * the class whose name is given as the argument is the only test class.</p>
	 *
	 * @param argv [optional] the name of the class that defines the tests
	 * @throws Exception
	 */
	public static void main(String argv[]) throws Exception {
		//pool = Executors.newCachedThreadPool(); //For concurrency
		//Instrumentor inst = new Instrumentor();
		//Set<String> instrumentedClasses =
		//   new AnnotationScanner(tester.cobertura.Instrument.class).scan();

		//if(instrumentedClasses != null){
		//	inst.instrumentClasses();
		//}

    boolean noTests = true; // set to false if Annotations found test to run

		// set up the scanner to look for '@Example' class annotation
		AnnotationScanner scanner = new AnnotationScanner(
				tester.Example.class);
		Set<String> classes = null;

		// Find all classes annotated with '@Example' and in each run all tests
		try {
			classes = scanner.scan();

			// get the name of the class that defines the tests
			if(classes != null){
				for (String clazz : classes){
					System.out.println("\n\n---------------------------------\n" + "" +
							               "Tests for the class: " + clazz);
					new AnnotatedTest(clazz).run();
          noTests = false;
				}
			}
		} catch (Exception e) {
			System.err.println("Unable to scan for the annotated classes.\n" +
					"Shutting down...");
			e.printStackTrace();
		}finally{
			//if(instrumentedClasses != null){
			//	inst.generateReports();
			//}
		  //System.out.println("Done with the annotation scan");
		}

		// Now run tests for the 'Examples' class or the class given as argument
		String classname;
		if ((argv == null) || (Array.getLength(argv) == 0))
			classname = "Examples";
		else
			classname = argv[0];

		// System.out.println("Class name for tester.Main to use is: " + classname);

		// to represent an instance of the class that defines the tests
		Object o = null;

		// to represent the 'Examples' class that user supplied
		Class<?> examples;
		// Create an instance of the desired 'Examples' class
		// Note: if we already ran other tests, do not report errors here
		try {
			// get the specified 'Examples' class
			examples = Reflector.classForName(classname);

	    //System.out.println("Tests are defined in the class " + examples.getName());

			if (examples == null && noTests)
				throw new ClassNotFoundException("could not find the class " + classname);
			// else
			//	 System.out.println("found class " + examples.getName());

			// create an instance of this class
            try {
                Constructor<?> constructor = examples
                    .getDeclaredConstructor();
                Reflector.ensureIsAccessible(constructor);
                o = constructor.newInstance();

				System.out.println("\n\n---------------------------------\n" + "" +
						"Tests for the class: " + classname);

			// handle all exceptions when new instance cannot be created
			} catch (Throwable exception) {

        String excName;
        String excMessage;
        if (exception.getCause() != null) {
          excName = exception.getCause().getClass().getName();
          excMessage = exception.getCause().getMessage();
        } else {
          excName = exception.getClass().getName();
          excMessage = exception.getMessage();
        }

        if (excName.equals("java.lang.NoSuchMethodException")){
          System.out.println("no default constructor: "
                + excMessage);
        } else if (excName.equals("java.lang.InvocationTargetException")){
          System.out.println(
                "Invocation -- Exception thrown by the constructor: \n" +
                "Exception: " + excName + "\nMessage: " +
                excMessage);
        } else if (excName.equals("java.lang.InstantiationException")){
          System.out.println("Cannot construct an instance of "
                  + classname);
        } else if (excName.equals("java.lang.IllegalAccessException")){
          System.out.println(
                "Exception: " + excName + "\nMessage: " +
                excMessage);
        } else
          System.out.println(
              "Invocation -- Exception thrown by the constructor: \n" +
              "Exception: " + excName + "\nMessage: " +
              excMessage);
      }

			// run tests if the instance was successfully constructed
			if (o != null) {
				Tester t = new Tester();
				t.runAnyTests(o, false, true);
			}

			// report the problem if an instance of the class that defines the
			// tests has not been successfully constructed
			else {
				if (classes != null && noTests)
					throw new RuntimeException(
					"The " + classname + " class could not be instantiated.\n" +
					"Please check to make sure that your examples class has a default " +
					"constructor that does not fail during instantiation.");
			}
		}

		// report error if the class with the given classname does not exist
		catch (ClassNotFoundException c) {
		  if (noTests){
		    if (classname != null)
		      System.err.println(classname + " class doesn't exist.\n" +
		          "Please check to make sure that the argment that gives the name" +
		      " of your examples class has the correct spelling.");
		    else
		      System.err.println(c.getMessage());
		  }
		}

		// finish up
		finally {
			// System.out.println("done");
		}

		//System.exit(0); //Some bug is causing the threads not to terminate
	}
}
