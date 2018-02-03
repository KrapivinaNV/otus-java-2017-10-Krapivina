package otus.cache;

public class CacheConfig {
    private int maxElements;
    private long lifeTimeMs = 0;
    private long idleTimeMs = 0;
    private boolean isEternal;

    public int getMaxElements() {
        return maxElements;
    }

    public long getLifeTimeMs() {
        return lifeTimeMs;
    }

    public long getIdleTimeMs() {
        return idleTimeMs;
    }

    public boolean isEternal() {
        return isEternal;
    }

    public void setMaxElements(int maxElements) {
        this.maxElements = maxElements;
    }

    public void setLifeTimeMs(long lifeTimeMs) {
        this.lifeTimeMs = lifeTimeMs > 0 ? lifeTimeMs : 0;
    }

    public void setIdleTimeMs(long idleTimeMs) {
        this.idleTimeMs = idleTimeMs > 0 ? idleTimeMs : 0;
    }

    public void setEternal(boolean eternal) {
        isEternal = lifeTimeMs == 0 && idleTimeMs == 0 || isEternal;
    }
}
