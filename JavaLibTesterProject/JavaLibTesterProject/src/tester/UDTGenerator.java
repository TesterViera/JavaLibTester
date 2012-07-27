package tester;

import net.java.quickcheck.Generator;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import net.java.quickcheck.generator.PrimitiveGenerators;
import net.java.quickcheck.generator.iterable.Iterables;

/**
 * 
 * @author Sachin Venugopalan
 * 
 * UDTGenerator, the main component of the QuickCheck Integrated Tester
 */
public class UDTGenerator<T> implements net.java.quickcheck.Generator<T> {
    // TODO: make this an abstraction and split into two subclasses 
    
    Class<T> type;
    Generator<T> typeGenerator = null;

    HashMap<Field, Generator> fieldGenerators = null;
    boolean allFieldGeneratorsInitialized = false;

    Random rand = new Random();
    // HashMap<Class, Generator> subtypeGenerators = null;
    List<Generator> subtypeGenerators = null;
    boolean allSubtypeGeneratorsInitialized = false;

    static final HashMap<Class<?>, String> primitiveGenerators;
    static {
	primitiveGenerators = new HashMap<Class<?>, String>();
	primitiveGenerators.put(double.class, "doubles");
	primitiveGenerators.put(String.class, "strings");
	// primitiveGenerators.put(float.class, "doubles");
	primitiveGenerators.put(int.class, "integers");
	primitiveGenerators.put(byte.class, "bytes");
	primitiveGenerators.put(char.class, "characters");
	primitiveGenerators.put(long.class, "longs");
	// primitiveGenerators.put(short.class, "longs");
	primitiveGenerators.put(boolean.class, "booleans");
	primitiveGenerators.put(Date.class, "dates");
    }

    // to enable generation of many "kinds" of generators!!!? :D
    public UDTGenerator() {
    }

    public UDTGenerator(Class<T> typeParameterClass) {
	this();
	if (typeParameterClass == null)
	    try {
		throw new Exception("null type encountered!");
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	this.type = typeParameterClass;
    }

    @SuppressWarnings("unchecked")
    protected void initInnerGenerators() throws InstantiationException,
	    IllegalAccessException {

	// handle circularity
	if (!Utils.isConcrete(this.type)) {
	    this.initSubtypeGenerators();
	} else {
	    this.initFieldGenerators();
	}

    }

    @SuppressWarnings("unchecked")
    protected void initSubtypeGenerators() throws InstantiationException,
	    IllegalAccessException {

	if (!this.allSubtypeGeneratorsInitialized) {
	    // meta generator of sorts (for classes that implement this
	    // abstraction)
	    this.subtypeGenerators = new ArrayList<Generator>();

	    Set<Class> subtypes = Utils
		    .getConcreteDescendentsInSamePackage(this.type);
	    for (Class c : subtypes) {

		Constructor ctor;
		UDTGenerator cGen;
		try {
		    ctor = UDTGenerator.class
			    .getDeclaredConstructor(Class.class);
		    ctor.setAccessible(true);

		    cGen = (UDTGenerator) ctor.newInstance(c);

		    this.subtypeGenerators.add(cGen);

		    // recursive call
		    cGen.initFieldGenerators();

		} catch (NoSuchMethodException e) {
		    e.printStackTrace();
		} catch (SecurityException e) {
		    e.printStackTrace();
		} catch (IllegalArgumentException e) {
		    e.printStackTrace();
		} catch (InvocationTargetException e) {
		    e.printStackTrace();
		}

	    }
	}
	this.allSubtypeGeneratorsInitialized = true;
    }

    @SuppressWarnings("unchecked")
    protected void initFieldGenerators() throws InstantiationException,
	    IllegalAccessException {

	if (!this.allFieldGeneratorsInitialized) {
	    // lazy runtime initialization of generators
	    this.fieldGenerators = new HashMap<Field, Generator>();

	    T genObj = (T) Utils.getDummyObject(this.type);// this.type.newInstance();
							   // // throw-away
							   // object
	    Reflector r1 = new Reflector(genObj);
	    Field[] fields = r1.getFields(genObj);
	    for (Field f : fields) {

		Method method;
		Object o;

		// if this field has a built-in generator, use it
		if (primitiveGenerators.containsKey(f.getGenericType())) {
		    try {
			method = PrimitiveGenerators.class
				.getMethod(primitiveGenerators.get(f
					.getGenericType()));
			o = method.invoke(null);
			fieldGenerators.put(f, (Generator) o);

		    } catch (Exception e) {
			e.printStackTrace();
		    }
		}
		// if this field doesn't have built-in generator, create new
		// UDTGenerator
		else {
		    Constructor ctor;
		    UDTGenerator fieldGen;
		    try {
			ctor = UDTGenerator.class
				.getDeclaredConstructor(Class.class);
			ctor.setAccessible(true);
			Class fieldType = f.getType(); // f.getGenericType()

			fieldGen = (UDTGenerator) ctor.newInstance(fieldType);

			fieldGenerators.put(f, fieldGen);

			// recursive call
			fieldGen.initInnerGenerators();

		    } catch (NoSuchMethodException e) {
			e.printStackTrace();
		    } catch (SecurityException e) {
			e.printStackTrace();
		    } catch (IllegalArgumentException e) {
			e.printStackTrace();
		    } catch (InvocationTargetException e) {
			e.printStackTrace();
		    }

		}
	    }

	}
	this.allFieldGeneratorsInitialized = true;
    }

    /*
     * //handle circularity
     * 
     * Integer i1 = System.identityHashCode(obj1); Integer i2 =
     * System.identityHashCode(obj2);
     * 
     * Integer i2match = this.hashmap.get(i1 * i1 + i2 * i2);
     * 
     * if ((i2match != null) && (i2match.equals(i1 + i2))) { return true; }
     * this.hashmap.put(i1 * i1 + i2 * i2, i1 + i2);
     * 
     * // handle Array objects if (obj1.getClass().isArray() &&
     * obj2.getClass().isArray() && obj1.getClass() == obj2.getClass()) { int
     * length = Array.getLength(obj1); if (Array.getLength(obj2) == length) {
     * for (int i = 0; i < length; i++) { if (!isSame(Array.get(obj1, i),
     * Array.get(obj2, i))) return false; } return true; } else return false; }
     * 
     * // handle ISame objects by delegating to the user-defined method if
     * ((obj1 instanceof ISame) && (obj2 instanceof ISame)) return ((ISame)
     * obj1).same((ISame) obj2);
     * 
     * // handle the Set objects if ((obj1 instanceof Set) && (obj2 instanceof
     * Set) && obj1.getClass().getName().startsWith("java.util")) return
     * isSameSet((Set) obj1, (Set) obj2);
     * 
     * // handle Iterable objects if ((obj1 instanceof Iterable) && (obj2
     * instanceof Iterable) &&
     * obj1.getClass().getName().startsWith("java.util")) return
     * isSameIterablePrivate((Iterable<T>) obj1, (Iterable<T>) obj2);
     * 
     * // handle the Map objects if ((obj1 instanceof Map) && (obj2 instanceof
     * Map) && obj1.getClass().getName().startsWith("java.util")) return
     * isSameMap((Map) obj1, (Map) obj2);
     * 
     * // now handle the general case boolean sameValues = true; int i = 0; try
     * { for (; i < Array.getLength(r1.sampleDeclaredFields); i++) { sameValues
     * = sameValues && isSamePrivate(r1.sampleDeclaredFields[i].get(obj1),
     * r2.sampleDeclaredFields[i].get(obj2)); } } catch (IllegalAccessException
     * e) { System.out.println("same comparing " +
     * r1.sampleDeclaredFields[i].getType().getName() + " and " +
     * r2.sampleDeclaredFields[i].getType().getName() +
     * "cannot access the field " + i + " message: " + e.getMessage());
     * System.out.println("class 1: " + r1.sampleClass.getName());
     * System.out.println("class 2: " + r2.sampleClass.getName()); }
     * 
     * return sameValues; }
     */

    @Override
    public T next() {

	T genObj = null;
	// handle circularity
	if (!Utils.isConcrete(this.type)) {
	    genObj = this.nextAbstract();
	} else {
	    genObj = this.nextConcrete();
	}
	return genObj;
    }

    public T nextAbstract() {

	return (T) this.subtypeGenerators.get(
		rand.nextInt(this.subtypeGenerators.size())).next();
    }

    public T nextConcrete() {
	T genObj = null;
	try {
	    initFieldGenerators();

	    // Constructor ctor = this.type.getDeclaredConstructor();
	    // ctor.setAccessible(true);
	    genObj = (T) Utils.getDummyObject(this.type); // (T)this.type.newInstance();
	    // this.type.getFields();

	    Reflector r1 = new Reflector(genObj);
	    Field[] fields = r1.getFields(genObj);
	    for (Field f : fields) {
		f.set(genObj, fieldGenerators.get(f).next());
	    }

	} catch (InstantiationException e) {
	    e.printStackTrace();
	} catch (IllegalAccessException e) {
	    e.printStackTrace();
	} finally {
	    return genObj;
	}
    }

}
