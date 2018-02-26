package otus.sorting;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MultithreadedSorting {
    private int COUNT_ELEMENTS;
    private List<List<Integer>> sortedParts = Collections.synchronizedList(new ArrayList<List<Integer>>());
    private List<Integer> result = new ArrayList<>();

    public Integer[] multithreadedSorting(Integer[] inputArray, int countThreads) {

        COUNT_ELEMENTS = inputArray.length;

        if(COUNT_ELEMENTS == 0){
            return new Integer[]{};
        }


        int countPartitions = (int) Math.ceil((float) COUNT_ELEMENTS / countThreads);
        System.out.println("Count partitions = " + countPartitions);
        Iterable<List<Integer>> partition = Iterables.partition(Lists.newArrayList(inputArray), countPartitions);
        List<List<Integer>> listPartitions = Lists.newArrayList((Iterable) partition);

        Thread[] threads = new Thread[countThreads];


        int neededThreads = Math.min(listPartitions.size(), countThreads);


        for (int index = 0; index < neededThreads; index++) {
            int threadIndex = index;
            Thread thread = new Thread() {
                public void run() {
                    System.out.println("Thread " + (threadIndex + 1) + " Running");
                    List<Integer> part = Lists.newArrayList(listPartitions.get(threadIndex));
                    part.sort(Comparator.comparingInt(o -> o));
                    sortedParts.add(part);
                    System.out.printf("Thread %d%s%n", threadIndex + 1, part.toString());
                }
            };
            thread.setName("Thread " + (threadIndex + 1));
            threads[threadIndex] = thread;
        }

        for (int index = 0; index < neededThreads; index++) {
            threads[index].start();
        }

        for (int index = 0; index < neededThreads; index++) {
            try {
                threads[index].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if(sortedParts.size() != 0) {
            merge(sortedParts);
        }

        Integer[] arr = new Integer[result.size()];
        arr = result.toArray(arr);
        return arr;
    }


    private void merge(List<List<Integer>> parts) {
        List<List<Integer>> forMerges = new ArrayList<>();
        for (int index = 0; index < parts.size(); index++) {
            if (index != parts.size() - 1) {
                forMerges.add(mergeSort(parts.get(index), parts.get(index + 1)));
                index++;
            } else {
                forMerges.add(parts.get(index));
            }
        }
        if (forMerges.size() != 1) {
            merge(forMerges);
        } else {
            result = Lists.newArrayList(forMerges.get(0));
        }
    }


    private List<Integer> mergeSort(List<Integer> list1, List<Integer> list2) {
        ArrayList<Integer> resultList = new ArrayList<>();

        int counter1 = 0, counter2 = 0;

        for (int index = 0; index < (list1.size() + list2.size()); index++) {
            if (counter1 > (list1.size() - 1)) {
                resultList.add(list2.get(counter2));
                counter2++;
            } else if (counter2 > (list2.size() - 1)) {
                resultList.add(list1.get(counter1));
                counter1++;
            } else if (list1.get(counter1) <= list2.get(counter2)) {
                resultList.add(list1.get(counter1));
                counter1++;
            } else {
                resultList.add(list2.get(counter2));
                counter2++;
            }
        }
        return resultList;
    }
}

