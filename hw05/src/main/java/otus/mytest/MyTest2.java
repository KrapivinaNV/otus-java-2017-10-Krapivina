package otus.mytest;

import com.google.common.collect.Lists;
import otus.mytest.annotations.After;
import otus.mytest.annotations.Test;

import java.util.List;

public class MyTest2 {

    @Test
    public void test()  {
        System.out.println("Test 1");

        SystemUnderTesting systemUnderTesting = new SystemUnderTesting();
        List<String> list = Lists.newArrayList("123", "123", "2", "1", "3");
        List<String> actualValue = systemUnderTesting.sortCollection(list);

        MyAssert.assertEquals(Lists.newArrayList("1", "123", "123", "2", "3"), actualValue);
    }

    @After
    public void tearDown2() {
        System.out.println("After 2");
    }
}
