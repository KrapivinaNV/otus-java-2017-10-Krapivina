package otus.hibernate.cache;

public interface MyCacheBuilder <K, V> {

    void put(MyElement<K, V> element);

    MyElement<K, V> get(K key);

    int getHitCount();

    int getMissCount();

    void dispose();

    String getInfo();
}
