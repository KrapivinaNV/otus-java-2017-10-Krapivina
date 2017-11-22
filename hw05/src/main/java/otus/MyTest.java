package otus;

import otus.annotations.After;
import otus.annotations.Before;
import otus.annotations.Test;

public class MyTest {

    @Before
    public void setUp() {
        System.out.println("Before 1");
    }

    @Before
    public void setUp2() {
        System.out.println("Before 2");
    }

    @Test
    private void test() {
        System.out.println("Test 1");
    }


    @Test
    private void test2() {
        System.out.println("Test 2");
    }


    @Test
    private void test3() {
        System.out.println("Test 3");
    }

    @After
    public void tearDown1() {
        System.out.println("After 1");
    }

    @After
    public void tearDown2() {
        System.out.println("After 2");
    }
}