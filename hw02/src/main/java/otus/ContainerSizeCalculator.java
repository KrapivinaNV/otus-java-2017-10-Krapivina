package otus;

import java.util.ArrayList;
import java.util.List;

public class ContainerSizeCalculator extends SizeCalculator {

    private static final int COUNT_CONTAINERS = 1;
    private int size;

    ContainerSizeCalculator(int size) {
        this.size = size;
    }

    @Override
    public void populateObjects() {
        List<String> strings = new ArrayList<>();
        for (int index = 0; index < size; index++) {
            strings.add("");
        }
    }

    @Override
    public int getCount() {
        return COUNT_CONTAINERS;
    }
}