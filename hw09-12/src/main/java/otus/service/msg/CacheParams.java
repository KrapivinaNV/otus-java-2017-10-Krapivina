package otus.service.msg;

import java.io.Serializable;

public class CacheParams implements Serializable {
    private int hitCount;
    private int missCount;
    private int gcMissCount;

    public CacheParams(int hitCount, int missCount, int gcMissCount) {
        this.hitCount = hitCount;
        this.missCount = missCount;
        this.gcMissCount = gcMissCount;
    }

    public int getHitCount() {
        return hitCount;
    }

    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }

    public int getMissCount() {
        return missCount;
    }

    public void setMissCount(int missCount) {
        this.missCount = missCount;
    }

    public int getGcMissCount() {
        return gcMissCount;
    }

    public void setGcMissCount(int gcMissCount) {
        this.gcMissCount = gcMissCount;
    }

    @Override
    public String toString() {
        return "CacheParams{" +
                "hitCount=" + hitCount +
                ", missCount=" + missCount +
                ", gcMissCount=" + gcMissCount +
                '}';
    }
}
