package otus;

public class ObjectSizeCalculator extends SizeCalculator {

    private static final int COUNT_OBJECTS = 1;

    @Override
    public Object populateObjects() {
        return new Object();
    }

    @Override
    public int getCount() {
        return COUNT_OBJECTS;
    }
}