package otus;

public class ObjectSizeCalculator extends SizeCalculator {

    private static final int COUNT_OBJECTS = 1;

    @Override
    public void populateObjects() {
        Object object = new Object();
    }

    @Override
    public int getCount() {
        return COUNT_OBJECTS;
    }
}
