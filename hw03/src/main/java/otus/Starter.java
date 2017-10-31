package otus;

import java.util.Collections;
import java.util.List;

public class Starter {

    public static void main(String[] args) {
        List<String> source = new MyArrayList<>();

        Collections.addAll(source, "1", "2", "3", "4", "1", "222", "1", "2", "3", "4", "1", "222", "1", "2", "3", "4", "1", "222");

        List<String> destination = new MyArrayList<>(source.size());
        Collections.copy(destination, source);

        source.add("1");
        source.add("z");

        destination.add("a");
        destination.add("n");
        destination.add("0");

        Collections.sort(destination);

        for (int index = 0; index < source.size(); index ++) {
            System.out.print(source.get(index) + " ");
        }

        System.out.println();

        for (int index = 0; index < destination.size(); index ++) {
            System.out.print(destination.get(index) + " ");
        }
    }
}
