package tester;

import java.lang.annotation.*;

/**
 * This annotation class serves the purpose of marking methods
 * that we would like to test with the NU Tester.  This is 
 * done in a similar fashion to the process that is undergone
 * to mark JUnit test cases.
 * @author Weston Jossey
 * @since Jan 25, 2009
 * @version 2.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestMethod {}
