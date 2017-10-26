package otus;

import java.lang.management.ManagementFactory;

public class Launcher {

    public static void main(String[] args) {
        System.out.println(String.format("pid: %s ", ManagementFactory.getRuntimeMXBean().getName()));

        SizeCalculator stringSizeCalculator = new StringSizeCalculator();
        SizeCalculator containerSizeCalculator = new ContainerSizeCalculator(0);

        SizeCalculator objectSizeCalculator = new ObjectSizeCalculator();

        System.out.println(String.format("Empty String size: %s", stringSizeCalculator.calculateSize()));
        System.out.println(String.format("Empty Container size: %s", containerSizeCalculator.calculateSize()));
        System.out.println(String.format("Object size: %s", objectSizeCalculator.calculateSize()));

        for (int elementIndex = 1; elementIndex < 20; elementIndex++) {
            System.out.println(
                    String.format("Container with %d elements = %s",
                            elementIndex,
                            new ContainerSizeCalculator(elementIndex).calculateSize()
                    )
            );
        }
    }
}