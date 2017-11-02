package otus;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Collections;

import static org.testng.Assert.assertEquals;

public class MyArrayListTest {

    @Test(dataProvider = "addAllDataProvider")
    public void addAll(Object[] array, MyArrayList expected) {
        MyArrayList<Object> myArray = new MyArrayList<>();
        Collections.addAll(myArray, array);

        assertEquals(expected, myArray);
    }

    @DataProvider
    public static Object[][] addAllDataProvider() {
        return new Object[][]{
                {
                        new String[]{"1", "2", "3", "4", "1", "222", "1", "2", "3", "4", "1", "222", "1", "2", "3", "4", "1", "222"},
                        new MyArrayList<>(
                                new String[]{"1", "2", "3", "4", "1", "222", "1", "2", "3", "4", "1", "222", "1", "2", "3", "4", "1", "222"}
                        )
                },
                {
                        new String[]{},
                        new MyArrayList<>(new String[]{}),
                },
                {
                        new Integer[]{1, 3, 5, 7, 8, 9, 0, 1},
                        new MyArrayList<>(new Integer[]{1, 3, 5, 7, 8, 9, 0, 1})
                },
                {
                        new Object[]{new MyArrayList(), new MyArrayList(), new MyArrayList(), new MyArrayList()},
                        new MyArrayList<>(
                                new MyArrayList[]{
                                        new MyArrayList(),
                                        new MyArrayList(),
                                        new MyArrayList(),
                                        new MyArrayList()
                                }
                        )
                },
        };
    }

    @Test(dataProvider = "copyDataProvider")
    public void copy(MyArrayList source, MyArrayList expected) {
        MyArrayList<Object> myArray = new MyArrayList<>(source.size());
        Collections.copy(myArray, source);

        assertEquals(expected, myArray);
    }

    @DataProvider
    public static Object[][] copyDataProvider() {
        return new Object[][]{
                {
                        new MyArrayList<>(
                                new String[]{"1", "2", "3", "4", "1", "222", "1", "2", "3", "4", "1", "222", "1", "2", "3", "4", "1", "222"}),
                        new MyArrayList<>(
                                new String[]{"1", "2", "3", "4", "1", "222", "1", "2", "3", "4", "1", "222", "1", "2", "3", "4", "1", "222"})
                },
                {
                        new MyArrayList<>(new Integer[]{1, 3, 5, 7, 8, 9, 0, 1}),
                        new MyArrayList<>(new Integer[]{1, 3, 5, 7, 8, 9, 0, 1})
                },
                {
                        new MyArrayList<>(new String[]{}),
                        new MyArrayList<>(new String[]{})
                },
                {
                        new MyArrayList<>(),
                        new MyArrayList<>()
                }
        };
    }

    @Test(dataProvider = "sortDataProvider")
    public void sort(MyArrayList source, MyArrayList expected) {
        Collections.sort(source);

        assertEquals(expected, source);
    }

    @DataProvider
    public static Object[][] sortDataProvider() {
        return new Object[][]{
                {
                        new MyArrayList<>(
                                new String[]{"1", "2", "3", "4", "1", "222"}),
                        new MyArrayList<>(
                                new String[]{"1", "1", "2", "222", "3", "4"})
                },
                {
                        new MyArrayList<>(new Integer[]{1, 10, 5, 0, 8, 9, 0, 1}),
                        new MyArrayList<>(new Integer[]{0, 0, 1, 1, 5, 8, 9, 10})
                },
                {
                        new MyArrayList<>(new Integer[]{0, 0, 1, 1, 5, 8, 9, 10}),
                        new MyArrayList<>(new Integer[]{0, 0, 1, 1, 5, 8, 9, 10})
                },
                {
                        new MyArrayList<>(),
                        new MyArrayList<>()
                }
        };
    }
}