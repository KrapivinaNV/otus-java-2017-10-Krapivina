package otus;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.util.ArrayList;
import java.util.List;

public class MyNotificationListener implements NotificationListener {

    private static final String END_OF_MINOR_GC = "end of minor GC";
    private static final String END_OF_MAJOR_GC = "end of major GC";

    private List<GCInfo> gcInfos = new ArrayList<>();

    @Override
    public void handleNotification(Notification notification, Object handback) {

        if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
            GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());

            switch (info.getGcAction()) {
                case END_OF_MINOR_GC:
                    gcInfos.add(new GCInfo(TypeGeneration.YOUNG_GENERATION, info.getGcInfo().getDuration(), info.getGcInfo().getStartTime(), info.getGcInfo().getEndTime()));
                    break;
                case END_OF_MAJOR_GC:
                    gcInfos.add(new GCInfo(TypeGeneration.OLD_GENERATION, info.getGcInfo().getDuration(), info.getGcInfo().getStartTime(), info.getGcInfo().getEndTime()));
                    break;
                default:
                    throw new IllegalStateException("illegal gcType;" + info.getGcAction());
            }
        }
    }

    List<GCInfo> getGcInfos() {
        return gcInfos;
    }
}