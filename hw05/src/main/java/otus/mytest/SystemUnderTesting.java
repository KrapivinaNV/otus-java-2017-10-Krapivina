package otus.mytest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SystemUnderTesting {

    public <T extends Comparable<? super T>> List<T> sortCollection(List<T> list) {
        Collections.sort(list);
        return new ArrayList<>(list);
    }
}
