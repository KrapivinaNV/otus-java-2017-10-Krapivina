package otus;

public class StringSizeCalculator extends SizeCalculator {

    private static final int COUNT_STRINGS = 1;

    @Override
    public Object populateObjects() {
        return new String();
    }

    @Override
    public int getCount() {
        return COUNT_STRINGS;
    }
}