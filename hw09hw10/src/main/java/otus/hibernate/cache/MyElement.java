package otus.hibernate.cache;

import otus.data.DataSet;

import java.lang.ref.SoftReference;

public class MyElement<K, V>  {

    private final K key;
    private SoftReference<V> value;

    private final long creationTime;
    private long lastAccessTime;

    public MyElement(K key, V value) {
        this.key = key;
        this.value = new SoftReference<V>(value) ;
        this.creationTime = getCurrentTime();
        this.lastAccessTime = getCurrentTime();
    }

    protected long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public K getKey() {
        return key;
    }

    public SoftReference<V> getValue() {
        return value;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public void setAccessed() {
        lastAccessTime = getCurrentTime();
    }


    @Override
    public String toString() {
        return "MyElement{" +
                "key=" + key +
                ", value=" + value.get() +
                "\n";
    }
}
