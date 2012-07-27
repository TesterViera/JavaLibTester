package tester;

//src: http://mike.shannonandmike.net/2009/09/02/java-reflecting-to-get-all-classes-in-a-package/

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

public class Utils {

    /*
     * private JarFile jar;
     * 
     * private Collection<Class<?>> loadJar() throws IOException {
     * List<Class<?>> classes = new ArrayList<Class<?>>(); for
     * (Enumeration<JarEntry> e = jar.entries(); e.hasMoreElements(){ JarEntry
     * je = e.nextElement(); String name = je.getName(); if
     * (name.endsWith(".class")) { name = name.substring(0, name.length() - 6);
     * name = name.replace('\\', '.'); InputStream is = jar.getInputStream(je);
     * ByteArrayOutputStream baos = new ByteArrayOutputStream(); byte[] b = new
     * byte[5000]; int i; while ((i = is.read(b)) != -1) { baos.write(b, 0, i);
     * } Class<?> c = defineClass(name, baos.toByteArray(), 0, baos.size());
     * boolean flag = true; for (ClassFilter cf : classFilters) { if
     * (!cf.checkClass(c)) { flag = false; break; } } if (flag) {
     * classes.add(c); } } } return classes; }
     */

    /**
     * Given a package name, attempts to reflect to find all classes within the
     * package on the local file system.
     * 
     * @param packageName
     * @return
     */
    public static Set<Class> getClassesInPackage(String packageName) {
	Set<Class> classes = new HashSet<Class>();
	String packageNameSlashed = "/" + packageName.replace(".", "/");
	// Get a File object for the package
	URL directoryURL = Thread.currentThread().getContextClassLoader()
		.getResource(packageNameSlashed);
	if (directoryURL == null) {
	    // LOG.warn("Could not retrieve URL resource: " +
	    // packageNameSlashed);
	    return classes;
	}

	String directoryString = directoryURL.getFile();
	if (directoryString == null) {
	    // LOG.warn("Could not find directory for URL resource: " +
	    // packageNameSlashed);
	    return classes;
	}

	File directory = new File(directoryString);
	if (directory.exists()) {
	    // Get the list of the files contained in the package
	    String[] files = directory.list();
	    for (String fileName : files) {
		// We are only interested in .class files
		if (fileName.endsWith(".class")) {
		    // Remove the .class extension
		    fileName = fileName.substring(0, fileName.length() - 6);
		    try {
			classes.add(Class.forName(packageName + "." + fileName));
		    } catch (ClassNotFoundException e) {
			// LOG.warn(packageName + "." + fileName +
			// " does not appear to be a valid class.", e);
		    }
		}
	    }
	} else {
	    // LOG.warn(packageName +
	    // " does not appear to exist as a valid package on the file system.");
	}
	return classes;
    }


    /*
    public static List getClassesInPackage
        (String jarName, String packageName){
	boolean debug = false;
      ArrayList classes = new ArrayList ();

      packageName = packageName.replaceAll("\\." , "/");
      if (debug) System.out.println
           ("Jar " + jarName + " looking for " + packageName);
      try{
        JarInputStream jarFile = new JarInputStream
           (new FileInputStream (jarName));
        JarEntry jarEntry;

        while(true) {
          jarEntry=jarFile.getNextJarEntry ();
          if(jarEntry == null){
            break;
          }
          if((jarEntry.getName ().startsWith (packageName)) &&
               (jarEntry.getName ().endsWith (".class")) ) {
            if (debug) System.out.println 
              ("Found " + jarEntry.getName().replaceAll("/", "\\."));
            classes.add (jarEntry.getName().replaceAll("/", "\\."));
          }
        }
      }
      catch( Exception e){
        e.printStackTrace ();
      }
      return classes;
   }
	*/
    
    public static Set<Class> getConcreteDescendentsInSamePackage(
	    Class parentType) {
	Set<Class> classes = Utils.getClassesInPackage(parentType.getPackage()
		.getName());

	Class c = null;
	for (Iterator<Class> it = classes.iterator(); it.hasNext(); c = it
		.next()) {
	    // abstract definition
	    if (!Utils.isConcrete(c))
		it.remove();
	    // concrete descendent
	    else {
		Object obj = getDummyObject(c);
		if (!parentType.isInstance(obj))
		    it.remove();
		//else keep it
	    }
	}
	return classes;
    }

    // ensure concrete before calling this
    public static Object getDummyObject(Class cls) {
	Object obj = null;
	Constructor ctor = null;

	try {
	    ctor = cls.getDeclaredConstructor();
	    ctor.setAccessible(true);
	    obj = ctor.newInstance();
	} catch (NoSuchMethodException e1) {
	    // just get "a" constructor ("any" constructor)
	    ctor = cls.getConstructors()[0];
	    Class[] paramTypes = ctor.getParameterTypes();
	    List<Object> params = new ArrayList<Object>();
	    for (Class paramType : paramTypes)
		params.add(paramType.isPrimitive() ? getTypeDefaultValue(paramType
			.getName()) : null);
	    try {
		obj = ctor.newInstance(params.toArray());
	    } catch (InstantiationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } catch (IllegalAccessException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } catch (IllegalArgumentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } catch (InvocationTargetException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	} catch (SecurityException e1) {
	    e1.printStackTrace();
	} catch (InstantiationException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IllegalAccessException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IllegalArgumentException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (InvocationTargetException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	finally {
	    return obj;
	}
    }

    public static Object getTypeDefaultValue(String typeName) {
	if (typeName.equals("byte"))
	    return 0;
	if (typeName.equals("short"))
	    return 0;
	if (typeName.equals("int"))
	    return 0;
	if (typeName.equals("long"))
	    return 0L;
	if (typeName.equals("char"))
	    return '\u0000';
	if (typeName.equals("float"))
	    return 0.0F;
	if (typeName.equals("double"))
	    return 0.0;
	if (typeName.equals("boolean"))
	    return false;
	throw new IllegalArgumentException("Not primitive type : " + typeName);
    }

    
    public static boolean isConcrete(Class type){
	int modifiers = type.getModifiers();
	return !(Modifier.isInterface(modifiers) || Modifier.isAbstract(modifiers));
    }
}
