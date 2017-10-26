package otus;

public class StringSizeCalculator extends SizeCalculator {

    private static final int COUNT_STRINGS = 20000;

    @Override
    public void populateObjects() {
        String strings = "";
        for (int j = 0; j < COUNT_STRINGS ; j++) {
            strings += "";
        }
    }

    @Override
    public int getCount() {
        return COUNT_STRINGS + 1;
    }
}