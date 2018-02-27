package otus.sorting;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultithreadedSorting {

    private List<List<Integer>> sortedParts = new ArrayList();
    private List<Integer> result = new ArrayList<>();

    public Integer[] multithreadedSorting(Integer[] inputArray, int countThreads) {

        int countElements = inputArray.length;

        if (countElements == 0) {
            return new Integer[]{};
        }

        int countPartitions = (int) Math.ceil((float) countElements / countThreads);
        System.out.println("Count partitions = " + countPartitions);
        Iterable<List<Integer>> partition = Iterables.partition(Lists.newArrayList(inputArray), countPartitions);
        List<List<Integer>> listPartitions = Lists.newArrayList((Iterable) partition);

        Thread[] threads = new Thread[countThreads];

        int neededThreads = Math.min(listPartitions.size(), countThreads);

        ExecutorService executor = Executors.newFixedThreadPool(neededThreads);

        List<Future<List<Integer>>> futures = new ArrayList<>();
        for (int taskIndex = 0; taskIndex < neededThreads; taskIndex++) {
            int finalTaskIndex = taskIndex;
            futures.add(
                    executor.submit(
                            () -> {
                                List<Integer> part = Lists.newArrayList(listPartitions.get(finalTaskIndex));
                                part.sort(Comparator.comparingInt(o -> o));
                                return part;
                            }
                    )
            );
        }

        futures.forEach(listFuture -> {
            try {
                sortedParts.add(listFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
        executor.shutdown();

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
