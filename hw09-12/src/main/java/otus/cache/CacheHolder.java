package otus.cache;

public class CacheHolder {

    private static final CacheConfig CACHE_CONFIG = CacheConfigHolder.getCacheConfig();

    private static MyCache myCache;

    public static MyCache getMyCache() {
        if (myCache == null) {
            myCache = new CacheEngineImpl(CACHE_CONFIG);
        }
        return myCache;
    }

    private CacheHolder(CacheConfig config) {
    }
}
