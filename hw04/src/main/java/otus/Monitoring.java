package otus;

import javax.management.NotificationEmitter;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

class Monitoring {

    private MyNotificationListener myNotificationListener = new MyNotificationListener();

    void installGCMonitoring() {
        List<GarbageCollectorMXBean> garbageCollectorMXBeans = ManagementFactory.getGarbageCollectorMXBeans();

        for (GarbageCollectorMXBean gcbean : garbageCollectorMXBeans) {
            System.out.println(gcbean.getName());
            NotificationEmitter emitter = (NotificationEmitter) gcbean;

            emitter.addNotificationListener(myNotificationListener, null, null);
        }
    }

    MyNotificationListener getMyNotificationListener() {
        return myNotificationListener;
    }
}
