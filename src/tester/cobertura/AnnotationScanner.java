package tester.cobertura;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.Set;

import org.scannotation.AnnotationDB;
import org.scannotation.ClasspathUrlFinder;

/**
 * This class serves the purpose of scanning all of the classes in
 * the class path for an annotation with a particular name or class.
 * It is preferable to use a class rather than a name, but they should 
 * produce the same result (although efficiency will most likely suffer
 * with a String).    
 * @author Weston Jossey
 * @since December 12 2008
 * @version 1.0
 * 
 *
 */
public class AnnotationScanner {
	
	private String name;
	private Class<? extends Annotation> c;

	/**
	 * Scan for the following class based on the given String.
	 * @param name String to match against
	 */
	public AnnotationScanner(String name) {
		this.name = name;
	}

	/**
	 * Scan for the following class based on the given annotation.
	 * @param c The annotation class to match against.
	 */
	public AnnotationScanner(Class<? extends Annotation> c) {
		this.c = c;
	}

	public Set<String> scan() throws Exception {
		URL[] urls = ClasspathUrlFinder.findClassPaths(); // scan
															// java.class.path
		AnnotationDB db = new AnnotationDB();
		try {
			db.scanArchives(urls);
			if(this.name != null)
				return db.getAnnotationIndex().get(this.name);
			else if(c != null)
				return db.getAnnotationIndex().get(c.getName());
			else
				throw new NullPointerException("String or Class was null");
		} 
		catch (IOException e) {
			throw new Exception("IO Error");
		}
		catch (RuntimeException e) {
			// no error message if the classpath is not defined correctly
			if (e.getMessage().startsWith("File in java.class.path does not exist: "))
				return null;
			else
				throw new RuntimeException(e.getMessage());
		}
		
	}

}
