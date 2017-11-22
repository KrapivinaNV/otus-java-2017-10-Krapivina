package otus;

import otus.annotations.After;
import otus.annotations.Test;

public class MyTest2 {

    @Test
    public void test() {
        System.out.println("Test 1");
    }

    @After
    public void tearDown2() {
        System.out.println("After 2");
    }
}
