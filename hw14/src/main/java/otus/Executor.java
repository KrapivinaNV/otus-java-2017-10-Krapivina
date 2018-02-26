package otus;

//ДЗ-14: Многопоточная сортировка
//Написать приложение, которое сортирует массив чисел в 4 потоках с использованием библиотеки или без нее.

import otus.sorting.MultithreadedSorting;

import java.util.Arrays;
import java.util.Random;

public class Executor {

    private static final Random RANDOM = new Random();
    private static final int COUNT_ELEMENTS = 11;
    private static final int COUNT_THREADS = 2;


    public static void main(String[] args) {

        Integer[] inputArray = new Integer[COUNT_ELEMENTS];

        for (int index = 0; index < COUNT_ELEMENTS; index++) {
            inputArray[index] = RANDOM.nextInt(COUNT_ELEMENTS);
        }
        System.out.println("Input = " + Arrays.toString(inputArray));

        MultithreadedSorting multithreadedSorting = new MultithreadedSorting();
        Integer[] result = multithreadedSorting.multithreadedSorting(inputArray, COUNT_THREADS);

        System.out.println("Input = " + Arrays.toString(result));
    }
}