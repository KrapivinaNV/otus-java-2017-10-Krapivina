package otus;

public abstract class SizeCalculator {

    private static final int COUNT_ITERATE = 2000;
    private static final Runtime RUNTIME = Runtime.getRuntime();

    long calculateSize() {
        long result = 0;

        for (int i = 0; i < COUNT_ITERATE; i++) {
            RUNTIME.gc();

            long memoryAllocatedBefore = getMemoryAllocated();

            populateObjects();

            long memoryAllocatedAfter = getMemoryAllocated();
            result += (memoryAllocatedAfter - memoryAllocatedBefore);
        }
        return result / COUNT_ITERATE / getCount();
    }

    private long getMemoryAllocated() {
        return RUNTIME.totalMemory() - RUNTIME.freeMemory();
    }

    public abstract void populateObjects();

    public abstract int getCount();
}