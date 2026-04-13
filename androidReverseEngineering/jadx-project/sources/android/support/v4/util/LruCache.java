package android.support.v4.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes.dex */
public class LruCache<K, V> {
    private int createCount;
    private int evictionCount;
    private int hitCount;
    private final LinkedHashMap<K, V> map;
    private int maxSize;
    private int missCount;
    private int putCount;
    private int size;

    public LruCache(int i2) {
        if (i2 <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        this.maxSize = i2;
        this.map = new LinkedHashMap<>(0, 0.75f, true);
    }

    private int safeSizeOf(K k2, V v2) {
        int sizeOf = sizeOf(k2, v2);
        if (sizeOf >= 0) {
            return sizeOf;
        }
        throw new IllegalStateException("Negative size: " + k2 + "=" + v2);
    }

    @Nullable
    public V create(@NonNull K k2) {
        return null;
    }

    public final synchronized int createCount() {
        return this.createCount;
    }

    public void entryRemoved(boolean z2, @NonNull K k2, @NonNull V v2, @Nullable V v3) {
    }

    public final void evictAll() {
        trimToSize(-1);
    }

    public final synchronized int evictionCount() {
        return this.evictionCount;
    }

    @Nullable
    public final V get(@NonNull K k2) {
        V v2;
        if (k2 == null) {
            throw new NullPointerException("key == null");
        }
        synchronized (this) {
            V v3 = this.map.get(k2);
            if (v3 != null) {
                this.hitCount++;
                return v3;
            }
            this.missCount++;
            V create = create(k2);
            if (create == null) {
                return null;
            }
            synchronized (this) {
                this.createCount++;
                v2 = (V) this.map.put(k2, create);
                if (v2 != null) {
                    this.map.put(k2, v2);
                } else {
                    this.size += safeSizeOf(k2, create);
                }
            }
            if (v2 != null) {
                entryRemoved(false, k2, create, v2);
                return v2;
            }
            trimToSize(this.maxSize);
            return create;
        }
    }

    public final synchronized int hitCount() {
        return this.hitCount;
    }

    public final synchronized int maxSize() {
        return this.maxSize;
    }

    public final synchronized int missCount() {
        return this.missCount;
    }

    @Nullable
    public final V put(@NonNull K k2, @NonNull V v2) {
        V put;
        if (k2 == null || v2 == null) {
            throw new NullPointerException("key == null || value == null");
        }
        synchronized (this) {
            this.putCount++;
            this.size += safeSizeOf(k2, v2);
            put = this.map.put(k2, v2);
            if (put != null) {
                this.size -= safeSizeOf(k2, put);
            }
        }
        if (put != null) {
            entryRemoved(false, k2, put, v2);
        }
        trimToSize(this.maxSize);
        return put;
    }

    public final synchronized int putCount() {
        return this.putCount;
    }

    @Nullable
    public final V remove(@NonNull K k2) {
        V remove;
        if (k2 == null) {
            throw new NullPointerException("key == null");
        }
        synchronized (this) {
            remove = this.map.remove(k2);
            if (remove != null) {
                this.size -= safeSizeOf(k2, remove);
            }
        }
        if (remove != null) {
            entryRemoved(false, k2, remove, null);
        }
        return remove;
    }

    public void resize(int i2) {
        if (i2 <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        synchronized (this) {
            this.maxSize = i2;
        }
        trimToSize(i2);
    }

    public final synchronized int size() {
        return this.size;
    }

    public int sizeOf(@NonNull K k2, @NonNull V v2) {
        return 1;
    }

    public final synchronized Map<K, V> snapshot() {
        return new LinkedHashMap(this.map);
    }

    public final synchronized String toString() {
        int i2;
        int i3;
        i2 = this.hitCount;
        i3 = this.missCount + i2;
        return String.format(Locale.US, "LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]", Integer.valueOf(this.maxSize), Integer.valueOf(this.hitCount), Integer.valueOf(this.missCount), Integer.valueOf(i3 != 0 ? (i2 * 100) / i3 : 0));
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0070, code lost:
    
        throw new java.lang.IllegalStateException(getClass().getName() + ".sizeOf() is reporting inconsistent results!");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void trimToSize(int i2) {
        K key;
        V value;
        while (true) {
            synchronized (this) {
                if (this.size >= 0 && (!this.map.isEmpty() || this.size == 0)) {
                    if (this.size <= i2 || this.map.isEmpty()) {
                        break;
                    }
                    Map.Entry<K, V> next = this.map.entrySet().iterator().next();
                    key = next.getKey();
                    value = next.getValue();
                    this.map.remove(key);
                    this.size -= safeSizeOf(key, value);
                    this.evictionCount++;
                } else {
                    break;
                }
            }
            entryRemoved(true, key, value, null);
        }
    }
}
