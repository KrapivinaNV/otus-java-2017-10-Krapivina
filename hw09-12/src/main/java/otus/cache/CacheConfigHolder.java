package otus.cache;

class CacheConfigHolder {

    private static final int MAX_ELEMENTS = 3;
    private static final boolean ETERNAL = true;

    private static CacheConfig cacheConfig;

    static CacheConfig getCacheConfig() {
        if (cacheConfig == null) {
            cacheConfig = new CacheConfig();
            cacheConfig.setMaxElements(MAX_ELEMENTS);
            cacheConfig.setEternal(ETERNAL);
        }
        return cacheConfig;
    }

    private CacheConfigHolder() {
    }
}