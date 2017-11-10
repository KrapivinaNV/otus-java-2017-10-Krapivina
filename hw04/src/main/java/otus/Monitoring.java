package otus;

import javax.management.NotificationEmitter;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

class Monitoring {

    private MyNotificationListener myNotificationListener = new MyNotificationListener();

    void installGCMonitoring() {
        List<GarbageCollectorMXBean> garbageCollectorMXBeans = ManagementFactory.getGarbageCollectorMXBeans();

        for (GarbageCollectorMXBean mxBean : garbageCollectorMXBeans) {
            System.out.println(mxBean.getName());
            NotificationEmitter emitter = (NotificationEmitter) mxBean;

            emitter.addNotificationListener(myNotificationListener, null, null);
        }
    }

    MyNotificationListener getMyNotificationListener() {
        return myNotificationListener;
    }
}
