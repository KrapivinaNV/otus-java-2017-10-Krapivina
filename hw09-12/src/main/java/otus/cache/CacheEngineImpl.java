package otus.cache;

import java.lang.ref.SoftReference;
import java.util.*;
import java.util.function.Function;

public class CacheEngineImpl<K, V> implements MyCache<K, V> {
    private static final int TIME_THRESHOLD_MS = 5;

    private CacheConfig cacheConfig;

    private final Map<K, SoftReference<MyElement<K, V>>> elements = new LinkedHashMap<>();
    private final Timer timer = new Timer();

    private int hit = 0;
    private int miss = 0;
    private int gcMiss = 0;

    public CacheEngineImpl(CacheConfig config) {
        this.cacheConfig = config;
    }

    public void put(MyElement<K, V> element) {
        if (elements.size() == cacheConfig.getMaxElements()) {
            boolean anyRemoved = elements.entrySet().removeIf(entry -> entry.getValue().get() == null);
            if (!anyRemoved) {
                Optional<Map.Entry<K, SoftReference<MyElement<K, V>>>> min = elements.entrySet().stream()
                        .filter(e -> e.getValue().get() != null)
                        .min(Comparator.comparingLong(o -> o.getValue().get().getLastAccessTime()));
                min.ifPresent(kMyElementEntry -> elements.remove(kMyElementEntry.getKey()));
            }
        }

        if (elements.size() < cacheConfig.getMaxElements()) {
            SoftReference<MyElement<K, V>> myElementSoftReference = new SoftReference<>(element);
            K key = element.getKey();
            elements.put(key, myElementSoftReference);

            if (!cacheConfig.isEternal()) {
                if (cacheConfig.getLifeTimeMs() != 0) {
                    TimerTask lifeTimerTask = getTimerTask(key, lifeElement -> lifeElement.getCreationTime() + cacheConfig.getLifeTimeMs());
                    timer.schedule(lifeTimerTask, cacheConfig.getLifeTimeMs());
                }
                if (cacheConfig.getIdleTimeMs() != 0) {
                    TimerTask idleTimerTask = getTimerTask(key, idleElement -> idleElement.getLastAccessTime() + cacheConfig.getIdleTimeMs());
                    timer.schedule(idleTimerTask, cacheConfig.getIdleTimeMs(), cacheConfig.getIdleTimeMs());
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
