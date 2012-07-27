package tester;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation allows us to mark classes that we would like to 
 * examine at runtime using the provided Tester.  
 * 
 * The method is named Example rather than Examples because of namespace
 * conflicts that could arise give the likelihood of a student wanting to
 * write Examples.java as a class in their classpath.  If they attempt to
 * do a batch import of the tester:<br />
 * <code> import tester.*;</code><br />
 * they would encounter a message indicating the class is already defined.
 * @author Weston Jossey
 * @version 1.0
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Example {}
