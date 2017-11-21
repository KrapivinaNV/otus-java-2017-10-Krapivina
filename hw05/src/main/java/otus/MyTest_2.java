package otus;

import otus.annotations.After;
import otus.annotations.Before;
import otus.annotations.Test;

public class MyTest_2 {
    public MyTest_2() {
    }

    @Test
    public void setup_1() {
        System.out.println("Test 1");
    }

    @After
    public void teardown_2() {
        System.out.println("After 2");
    }
}
