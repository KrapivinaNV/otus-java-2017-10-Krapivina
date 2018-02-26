package otus.sorting;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class SortingTest {

    @Test(dataProvider = "multithreadedSortingDataProvider")
    public void multithreadedSorting(Integer[] input, int threads, Integer[] expected) {

        MultithreadedSorting multithreadedSorting = new MultithreadedSorting();
        Integer[] result = multithreadedSorting.multithreadedSorting(input, threads);
        assertEquals(result, expected);
    }

    @DataProvider
    private Object[][] multithreadedSortingDataProvider() {
        return new Object[][]{
                {
                        new Integer[]{},
                        4,
                        new Integer[]{}
                },
                {
                        new Integer[]{6, 9, 6, 3},
                        4,
                        new Integer[]{3, 6, 6, 9}
                },
                {
                        new Integer[]{2, 2, 9, 19, 17, 18, 9, 1, 8, 5, 1, 4, 5, 10, 1, 10, 11, 7, 9, 12},
                        4,
                        new Integer[]{1, 1, 1, 2, 2, 4, 5, 5, 7, 8, 9, 9, 9, 10, 10, 11, 12, 17, 18, 19}
                },
                {
                        new Integer[]{2, 0, 3, 7, 6, 6, 3, 0, 4, 9, 4},
                        4,
                        new Integer[]{0, 0, 2, 3, 3, 4, 4, 6, 6, 7, 9}
                },
                {
                        new Integer[]{6, 6, 0, 0, 8, 9, 4, 10, 7, 9, 2},
                        2,
                        new Integer[]{0, 0, 2, 4, 6, 6, 7, 8, 9, 9, 10}
                },
                {
                        new Integer[]{6, 6, 0, 0, 8, 9, 4, 10, 7, 9, 2},
                        100,
                        new Integer[]{0, 0, 2, 4, 6, 6, 7, 8, 9, 9, 10}
                },
                {
                        new Integer[]{6, 6, 0, 0, 8, 9, 4, 10, 7, 9, 2},
                        1,
                        new Integer[]{0, 0, 2, 4, 6, 6, 7, 8, 9, 9, 10}
                }
        };
    }
}