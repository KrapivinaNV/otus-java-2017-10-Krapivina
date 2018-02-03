package otus.cache;

public interface MyCache <K, V> {

    void put(MyElement<K, V> element);

    MyElement<K, V> get(K key);

    int getHitCount();

    int getMissCount();

    int getGCMissCount();

    void dispose();
}
