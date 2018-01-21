package otus.hibernate.cache;

import java.lang.ref.SoftReference;
import java.util.*;
import java.util.function.Function;

public class CacheEngineImpl<K, V> implements MyCacheBuilder<K, V> {
    private static final int TIME_THRESHOLD_MS = 5;

    private final int maxElements;
    private final long lifeTimeMs;
    private final long idleTimeMs;
    private final boolean isEternal;

    private final Map<K, SoftReference<MyElement<K, V>>> elements = new LinkedHashMap<>();
    private final Timer timer = new Timer();

    private int hit = 0;
    private int miss = 0;
    private int gcMiss = 0;

    public CacheEngineImpl(int maxElements, long lifeTimeMs, long idleTimeMs, boolean isEternal) {
        this.maxElements = maxElements;
        this.lifeTimeMs = lifeTimeMs > 0 ? lifeTimeMs : 0;
        this.idleTimeMs = idleTimeMs > 0 ? idleTimeMs : 0;
        this.isEternal = lifeTimeMs == 0 && idleTimeMs == 0 || isEternal;
    }

    public void put(MyElement<K, V> element) {
        if (elements.size() == maxElements) {
            boolean anyRemoved = elements.entrySet().removeIf(entry -> entry.getValue().get() == null);
            if (!anyRemoved) {
                Optional<Map.Entry<K, SoftReference<MyElement<K, V>>>> min = elements.entrySet().stream()
                        .filter(e -> e.getValue().get() != null)
                        .min(Comparator.comparingLong(o -> o.getValue().get().getLastAccessTime()));
                min.ifPresent(kMyElementEntry -> elements.remove(kMyElementEntry.getKey()));
            }
        }

        if (elements.size() < maxElements) {
            SoftReference<MyElement<K, V>> myElementSoftReference = new SoftReference<>(element);
            K key = element.getKey();
            elements.put(key, myElementSoftReference);

            if (!isEternal) {
                if (lifeTimeMs != 0) {
                    TimerTask lifeTimerTask = getTimerTask(key, lifeElement -> lifeElement.getCreationTime() + lifeTimeMs);
                    timer.schedule(lifeTimerTask, lifeTimeMs);
                }
                if (idleTimeMs != 0) {
                    TimerTask idleTimerTask = getTimerTask(key, idleElement -> idleElement.getLastAccessTime() + idleTimeMs);
                    timer.schedule(idleTimerTask, idleTimeMs, idleTimeMs);
                }
            }
        }
    }

    public MyElement<K, V> get(K key) {
        MyElement<K, V> element = null;
        SoftReference<MyElement<K, V>> myElementSoftReference = elements.get(key);
        if (myElementSoftReference != null) {
            element = myElementSoftReference.get();
            if (element != null && element.getValue() != null) {
                hit++;
                element.setAccessed();
            } else {
                elements.remove(key);
                gcMiss++;
            }
        } else {
            miss++;
        }
        return element;
    }

    public int getHitCount() {
        return hit;
    }

    public int getMissCount() {
        return miss;
    }

    public int getGCMissCount() {
        return gcMiss;
    }

    @Override
    public void dispose() {
        timer.cancel();
    }

    private TimerTask getTimerTask(final K key, Function<MyElement<K, V>, Long> timeFunction) {
        return new TimerTask() {
            @Override
            public void run() {
                MyElement<K, V> element = elements.get(key).get();
                if (element == null || isT1BeforeT2(timeFunction.apply(element), System.currentTimeMillis())) {
                    elements.remove(key);
                    this.cancel();
                }
            }
        };
    }

    private boolean isT1BeforeT2(long t1, long t2) {
        return t1 < t2 + TIME_THRESHOLD_MS;
    }
}
