package otus;

import java.lang.management.ManagementFactory;

public class ObjectSizeCalculator {

    private static final int MAX_OBJECTS = 2000;
    private static final int COUNT_STRING = 100000;

    private static Runtime runtime = Runtime.getRuntime();

    public static void main(String[] args) {
        System.out.println("pid: " + ManagementFactory.getRuntimeMXBean().getName());
        long result = 0;

        for(int i = 0; i < MAX_OBJECTS; i++) {
            runtime.gc();

            long memoryAllocatedBefore = getMemoryAllocated();

            String strings="";
            for (int j = 0; j < COUNT_STRING ; j++) {
                strings += "";
            }

            long memoryAllocatedAfter = getMemoryAllocated();
            result += (memoryAllocatedAfter - memoryAllocatedBefore);
        }

        System.out.println(result / MAX_OBJECTS / (COUNT_STRING + 1));
    }

    private static long getMemoryAllocated() {
        return runtime.totalMemory() - runtime.freeMemory();
    }


}
