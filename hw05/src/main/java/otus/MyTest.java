package otus;

import otus.annotations.After;
import otus.annotations.Before;
import otus.annotations.Test;

public class MyTest {

    public MyTest() {
    }

    @Before
    public void setup_1() {
        System.out.println("Before 1");
    }

    @Before
    public void setup_2() {
        System.out.println("Before 2");
    }

    @Test
    private void test_1() {
        System.out.println("Test 1");
    }


    @Test
    private void test_2() {
        System.out.println("Test 2");
    }


    @Test
    private void test_3() {
        System.out.println("Test 3");
    }

    @After
    public void teardown_1() {
        System.out.println("After 1");
    }

    @After
    public void teardown_2() {
        System.out.println("After 2");
    }
}