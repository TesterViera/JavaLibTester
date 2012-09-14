package tester;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


/**
 * Ideally, this class will be used to create threaded tests that
 * can execute sequentially.  Unfortunately, the current limitations of
 * Cobertura prevent us from running non-synchronous tests.
 * @author Weston Jossey
 * @since December 12 2008
 * @version 2.0
 */
public class AnnotatedTest implements Runnable{
    String name;
    Object o = null;

    public AnnotatedTest(String name){
        this.name = name;
        init();
    }

    private void init() {
        try {
            Class<?> examples = Reflector.classForName(name);
            Constructor<?> constructor =
                examples.getDeclaredConstructor();
            Reflector.ensureIsAccessible(constructor);
            o = constructor.newInstance();

            System.out.println("Tester Results");
        } catch (NoSuchMethodException ex) {
            System.out
                    .println("no default costructor: " + ex.getMessage());
        } catch (InvocationTargetException ex) {
            System.out.println("Invocation: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {

        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void run() {
        // run tests if the instance was successfully constructed
        Tester t = new Tester();
        if (o != null) {
            t.runAnyTests(o);
        }
    }

}
