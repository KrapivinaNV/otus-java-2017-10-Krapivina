package otus;

/*
 -agentlib:jdwp=transport=dt_socket,address=14000,server=y,suspend=n
 -Xms20m
 -Xmx20m
 -XX:MaxMetaspaceSize=256m
 -XX:+UseConcMarkSweepGC
 -XX:+CMSParallelRemarkEnabled
 -XX:+UseCMSInitiatingOccupancyOnly
 -XX:CMSInitiatingOccupancyFraction=70
 -XX:+ScavengeBeforeFullGC
 -XX:+CMSScavengeBeforeRemark
 -XX:+UseParNewGC
 -verbose:gc
 -Xloggc:./logs/gc_pid_%p.log
 -XX:+PrintGCDateStamps
 -XX:+PrintGCDetails
 -XX:+UseGCLogFileRotation
 -XX:NumberOfGCLogFiles=10
 -XX:GCLogFileSize=1M
 -Dcom.sun.management.jmxremote.port=15000
 -Dcom.sun.management.jmxremote.authenticate=false
 -Dcom.sun.management.jmxremote.ssl=false
 -XX:+HeapDumpOnOutOfMemoryError
 -XX:HeapDumpPath=./dumps/


-XX:+UseSerialGC
Copy
MarkSweepCompact
GCInfo{duration=4, startTime=7064, endTime=7068, type=YOUNG_GENERATION}
GCInfo{duration=9, startTime=33278, endTime=33287, type=YOUNG_GENERATION}
GCInfo{duration=19, startTime=48686, endTime=48705, type=YOUNG_GENERATION}
GCInfo{duration=0, startTime=57830, endTime=57830, type=YOUNG_GENERATION}
GCInfo{duration=17, startTime=57830, endTime=57847, type=OLD_GENERATION}
GCInfo{duration=2, startTime=78115, endTime=78117, type=YOUNG_GENERATION}
GCInfo{duration=13, startTime=78117, endTime=78130, type=OLD_GENERATION}
GCInfo{duration=13, startTime=78130, endTime=78143, type=OLD_GENERATION}
GCInfo{duration=0, startTime=181904, endTime=181904, type=YOUNG_GENERATION}
GCInfo{duration=19, startTime=181904, endTime=181923, type=OLD_GENERATION}
GCInfo{duration=3, startTime=202139, endTime=202142, type=YOUNG_GENERATION}
GCInfo{duration=16, startTime=202142, endTime=202158, type=OLD_GENERATION}
GCInfo{duration=16, startTime=202158, endTime=202174, type=OLD_GENERATION}
Count = 13
Count strings = 1_823_230


-XX:+UseParallelGC -XX:-UseParallelOldGC    ++++++
PS Scavenge
PS MarkSweep
GCInfo{duration=3, startTime=6841, endTime=6844, type=YOUNG_GENERATION}
GCInfo{duration=1, startTime=76750, endTime=76751, type=YOUNG_GENERATION}
GCInfo{duration=1, startTime=76752, endTime=76753, type=YOUNG_GENERATION}
GCInfo{duration=11, startTime=76753, endTime=76764, type=OLD_GENERATION}
GCInfo{duration=1, startTime=200457, endTime=200458, type=YOUNG_GENERATION}
GCInfo{duration=13, startTime=200458, endTime=200471, type=OLD_GENERATION}
GCInfo{duration=0, startTime=200471, endTime=200471, type=YOUNG_GENERATION}
GCInfo{duration=13, startTime=200471, endTime=200484, type=OLD_GENERATION}
Count = 8
Count strings = 1_823_230


-XX:-UseParNewGC -XX:+UseConcMarkSweepGC +++++
Copy
ConcurrentMarkSweep
java.lang.OutOfMemoryError: Java heap space
GCInfo{duration=6, startTime=6855, endTime=6861, type=YOUNG_GENERATION}
GCInfo{duration=9, startTime=33095, endTime=33104, type=YOUNG_GENERATION}
GCInfo{duration=0, startTime=77152, endTime=77152, type=YOUNG_GENERATION}
GCInfo{duration=40, startTime=77138, endTime=77178, type=OLD_GENERATION}
GCInfo{duration=12, startTime=199806, endTime=199818, type=YOUNG_GENERATION}
GCInfo{duration=15, startTime=199818, endTime=199833, type=OLD_GENERATION}
GCInfo{duration=12, startTime=199833, endTime=199845, type=OLD_GENERATION}
Count = 7
Count strings = 1_823_230


G1	-XX:+UseG1GC +++++
G1 Young Generation
G1 Old Generation
GCInfo{duration=23, startTime=15777, endTime=15800, type=YOUNG_GENERATION}
GCInfo{duration=28, startTime=38981, endTime=39009, type=YOUNG_GENERATION}
GCInfo{duration=15, startTime=39009, endTime=39024, type=OLD_GENERATION}
GCInfo{duration=10, startTime=97868, endTime=97878, type=YOUNG_GENERATION}
GCInfo{duration=0, startTime=97878, endTime=97878, type=YOUNG_GENERATION}
GCInfo{duration=19, startTime=97879, endTime=97898, type=OLD_GENERATION}
GCInfo{duration=13, startTime=495335, endTime=495348, type=YOUNG_GENERATION}
GCInfo{duration=1, startTime=495348, endTime=495349, type=YOUNG_GENERATION}
GCInfo{duration=25, startTime=495349, endTime=495374, type=OLD_GENERATION}
GCInfo{duration=24, startTime=495374, endTime=495398, type=OLD_GENERATION}
Count = 10
Count strings = 1_823_230
*/

import java.util.ArrayList;
import java.util.List;

public class Starter {

    public static void main(String[] args) throws InterruptedException {
        Monitoring monitoring = new Monitoring();
        monitoring.installGCMonitoring();
        List<String> strings = new ArrayList<>();
        int size = 50_000_000;

        try {
            for (int index = 0; index < size; index++) {
                String s = "123456789123456789";
                strings.add(s);
                strings.add(s);
                strings.add(s);

                strings.remove(0);
            }
        } catch (OutOfMemoryError outOfMemoryError) {
            for (GCInfo gcInfo : monitoring.getMyNotificationListener().getGcInfos()) {
                System.out.println(gcInfo.toString());
            }
            System.out.println("Count = " + monitoring.getMyNotificationListener().getGcInfos().size());
            System.out.println("Count strings = " + strings.size());
        }
    }
}