package tester;

import java.lang.reflect.*;
import java.util.ArrayList;

//import org.apache.log4j.Logger;

/**
 * Copyright 2007, 2008 Viera K. Proulx
 * This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)
 */

/**
 * This class illustrates the use of the Java reflection classes and is used by
 * the <code>Inspector</code> class to design tests for extenstional equality
 * between two objects of arbitrary type.
 * 
 * It includes methods for displaying the information about objects, their class
 * and their fields -- for information and developer only.
 * 
 * @author Viera K. Proulx
 * @since 3 June 2007
 * 
 */
public class Reflector {
//	private static final Logger logger = Logger
//			.getLogger(Reflector.class);

	/** the object to examine */
	protected Object sample;

	/** The class for the object to examine */
	protected Class<?> sampleClass;

	/** All public or protected fields declared for this object */
	protected Field[] sampleDeclaredFields;

	/**
	 * Constructor: For the given instance get its <code>Class</code> and all
	 * public declared fields.
	 */
	protected Reflector(Object sample) {
		if (!(sample == null)) {
			this.sample = sample;
			this.sampleClass = this.sample.getClass();
			this.sampleDeclaredFields = this.getFields(sample);
		}
	}

	/* ------------------------------------------------------------------ */
	/* --------------- Accessing fields in the super class -------------- */
	/* ------------------------------------------------------------------ */

	/**
	 * Produce an <CODE>ArrayList</CODE> of all classes in the class hierarchy
	 * for the given obj - that are within the same package as the declaring
	 * class for the given instance obj
	 * 
	 * @param obj
	 * @return <code>ArrayList</code> of all classes in the given object's class
	 *         hierarchy - not including the java library classes
	 */
	protected ArrayList<Class<?>> getClasses(Object obj) {

		// a list of class hierarchy for object obj
		ArrayList<Class<?>> classTower = new ArrayList<Class<?>>();

		// there is nothing in the tower for a null object
		if (obj == null)
			return classTower;
		// add the class of obj to the class tower
		Class<?> c = obj.getClass();

		// Never read- Weston Jossey
		// Package p = c.getPackage();

		String cPackageName = getPackageName(c);
		classTower.add(c);

		// get info about its super class
		Class<?> sc = c.getSuperclass();
		
		/*BUG FIX... Dec 28, 2008: Wes Jossey
		 * While working with the tester, I encountered
		 * some false-negatives and false-positives when
		 * working with two objects that are included in the
		 * java libraries.  The issue involved not
		 * properly including fields that were a part of a
		 * superclass if they were in the java library.
		 */
//		if (!((cPackageName.equals("Object Type"))
//				|| (cPackageName.equals("Wrapper Class"))
//				|| (cPackageName.equals("Libraries")) || (cPackageName
//				.equals("Java Library")))) {
		if (!((cPackageName.equals("Object Type"))
				|| (cPackageName.equals("Wrapper Class"))
				|| (cPackageName.equals("Libraries")))){
			//String scPackageName = getPackageName(sc);

			// while the super class is in the same package, add to the
			// classTower
			while (true //(cPackageName.equals(scPackageName)
					&& !(sc.getName().equals("java.lang.Object"))) {

				// repeated here, so in the next iteration access is safe
				//scPackageName = getPackageName(sc);

				classTower.add(0, sc);

				// get the next super class
				sc = sc.getSuperclass();
			}
		}

		// this is the class tower for all super classes in the same package
		return classTower;
	}

	protected String getPackageName(Class<?> c) {
		String name = c.getName();

		if (name.equals("java.lang.Object"))
			return "Object Type";

		if (Inspector.isWrapperClass(name))
			return "Wrapper Class";

		if (name.startsWith("java") || name.startsWith("sun")
				|| name.startsWith("apple") || name.startsWith("javax"))
			return "Java Library";

		if (name.startsWith("draw") || name.startsWith("idraw")
				|| name.startsWith("adraw") || name.startsWith("geometry")
				|| name.startsWith("colors"))
			return "Libraries";

		Package p = c.getPackage();
		if (p == null)
			return "Default Package";
		else
			return p.getName();
	}

	/**
	 * <P>
	 * Get all fields for the given object, including those defined in super
	 * classes in the same package as object.
	 * </P>
	 * <P>
	 * Exclude the traversal up the hierarchy for the wrapper classes for
	 * primitive types
	 * </P>
	 * 
	 * @param obj
	 *            the object for which we look for fields
	 * @return an <CODE>Array</CODE> of fields for the given object
	 */
	protected Field[] getFields(Object obj) {
		ArrayList<Field> allFields = new ArrayList<Field>();
		ArrayList<Class<?>> classTower = this.getClasses(obj);
		// Printout to observe the shown packages
		
		//if(logger.isDebugEnabled())
		//	logger.debug("Object " + obj.toString() + "has " +
		//			classTower.size() + " classes in its package hierarchy");
				
		// System.out.println("Object " + obj.toString() + "has " +
		// classTower.size() + " classes in its package hierarchy.");

		// for (ArrayList<Class> c: classTower){
		for (Class<?> cl : classTower) {
			Field[] fields = cl.getDeclaredFields();
			for (Field f : fields) {
				// permit access to the protected and private field values
				f.setAccessible(true);
				int modif = f.getModifiers();
				if ( (!(Modifier.isStatic(modif)))  &&
						 (!(Modifier.isVolatile(modif))) &&
						 (!(Modifier.isTransient(modif))) &&
						 ((f.getDeclaringClass().equals(cl))) ){

					/*
					if(logger.isDebugEnabled())
						System.out.println("Field " + f.getName() +
								" in the class " +
								f.getDeclaringClass() + " added for class " +
								cl.getName());
					*/
					// System.out.println("Field " + f.getName() +
					// " in the class " +
					// f.getDeclaringClass() + " added for class " +
					// cl.getName());

					allFields.add(f);
				}
			}
		}
		// System.out.println("Object " + obj + "has " +
		// allFields.size() + " fields.");

		Field[] allFieldsArray = new Field[allFields.size()];
		return allFields.toArray(allFieldsArray);
	}

	/*
	 * UNUSED --- can be activated if we decide to give the user a choice as to
	 * what visibility modifiers are allowed.
	 * 
	 * <P>Select only the fields that we can display and use in comparison from
	 * the provided all declared fields</P> <P>Do not invoke this method on the
	 * wrapper classes as they contain a field that <code>TYPE</code> that
	 * represents the corresponding primitive type and thus introduces circular
	 * self-reference.</P>
	 * 
	 * @param fields all declared fields for the class of this object
	 * 
	 * @return all fields declared as 'public' that we can access
	 */
	/*
	 * public Field[] publicOrProtected(Field[] fields){ ArrayList<Field> result
	 * = new ArrayList<Field>();
	 * 
	 * // for all classes we select only the fields we can access: // those that
	 * have been declared 'public' or 'protected' or 'private' for (Field f:
	 * fields){ / if (Modifier.isPublic(f.getModifiers()) ||
	 * Modifier.isProtected(f.getModifiers()) ||
	 * Modifier.isPrivate(f.getModifiers())) result.add(f); } Field[]
	 * publicOrProtectedFields = new Field[result.size()]; return
	 * result.toArray(publicOrProtectedFields);
	 * 
	 * }
	 */

	/** ---------- METHODS TO DISPLAY THE AVAILABLE INFORMATION --------------- */
	/** ------------ USED ONLY FOR DEVELOPMENT AND DIAGNOSTICS ---------------- */

	/**
	 * Display the available information about the sample object.
	 */
	protected void printInfo() {
		System.out.println("sample: " + this.sample);
		System.out.println("sampleClass: " + this.sampleClass);

		System.out.println("sampleDeclaredFields: " + "\n");
		this.printSampleFields(this.sampleDeclaredFields);
	}

	/**
	 * Display for each field in the given <code>Array</code> of fields its name
	 * and its value.
	 */
	protected void printSampleFields(Field[] fieldArray) {
		System.out.println("number of fields: "
				+ Array.getLength(fieldArray) + "\n");
		for (Field f : fieldArray) {
			Class<?> t = f.getType();
			System.out.println("field name = " + f.getName()
					+ "\nfield  type = " + t.getName());
			try {
				System.out
						.println("field value = " + f.get(this.sample) + "\n");
			} catch (IllegalAccessException e) {
				//logger.error("printSampleFields cannot access the field "
				//				+ " message: " + e.getMessage());
				System.out
						.println("printSampleFields cannot access the field "
								+ " message: " + e.getMessage());
			}
		}
	}

	/**
	 * Produce the field with the given name in the <code>sample</code> object
	 * 
	 * @param fieldName the name of the desired field
	 * @return the <code>Field</code> with this name in <code>sample</code>
	 *         object (<code>null</code> if such field does not exist)
	 */
	protected Field findField(String fieldName) {
		for (Field f : this.sampleDeclaredFields) {
			if (f.getName().equals(fieldName))
				return f;
		}
		return null;
	}

	/**
	 * Print the information about the field with the given name in the
	 * <code>sample</code> object, i.e. the field name and its value - report
	 * errors as necessary
	 * 
	 * @param fieldName the name of the desired field
	 */
	protected void show(String fieldName) {
		Field f = findField(fieldName);
		if (f == null) {
			System.out.println("Object " + fieldName
					+ " is not declared in this class.");
		} else
			try {
				System.out.println("this." + fieldName + " = "
						+ Printer.produceString(f.get(this.sample)));
			} catch (IllegalAccessException e) {
				//logger.error("the field " + f.getName() +
				//		"cannot be shown \n" + " message: "
				//		+ e.getLocalizedMessage());
				System.out.println("the field " + f.getName()
						+ " cannot be shown \n" + " message: "
						+ e.getMessage());
			}
	}

	/**
	 * <P>
	 * Produce the (first) field with the given value in the <code>sample</code>
	 * object
	 * </P>
	 * 
	 * <P>
	 * The values are compared using the <code>equals</code> method - not the
	 * extensional equality.
	 * 
	 * @param fieldValue the value of the desired field
	 * @return the <code>Field</code> with this name in <code>sample</code>
	 *         object (<code>null</code> if such field does not exist)
	 */
	protected Field findFieldWithValue(Object fieldValue) {
		for (Field f : this.sampleDeclaredFields) {
			try {
				if (f.get(this.sample).equals(fieldValue))
					return f;
			} catch (IllegalAccessException e) {
				System.out
						.println("findFieldWithValue cannot access the field "
								+ f.getName()
								+ " of the class "
								+ this.sampleClass.getName()
								+ "\n   message: " + e.getMessage());
			}
		}
		return null;
	}

	/**
	 * Print the information about the (first) field with the given value in the
	 * <code>sample</code> object, i.e. the filed name and its value - report
	 * errors as necessary
	 * 
	 * @param fieldValue the value of the desired field
	 */
	protected void showFieldWithValue(Object fieldValue) {
		Field f = findFieldWithValue(fieldValue);
		if (f == null) {
			System.out.println("Object " + fieldValue
					+ " is not declared in this class.");
		} else
			try {
				System.out.println("this." + f.getName() + " = "
						+ Printer.produceString(f.get(this.sample)));
			} catch (IllegalAccessException e) {
				System.out.println("the field " + f.getName()
						+ "cannot be shown \n" + " message: "
						+ e.getMessage());
			}
	}
}