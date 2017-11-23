package otus;

import otus.exeptions.MyAssertionError;

public class MyAssert {

    static void assertEquals(Object expected, Object actual) throws MyAssertionError {
        if (!expected.equals(actual)) {
            System.out.println("expected: "+ expected);
            System.out.println("actual: "+ actual);

            throw new MyAssertionError();
        }
        System.out.println("Values are equal");
    }

}
