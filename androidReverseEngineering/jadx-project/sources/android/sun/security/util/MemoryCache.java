package android.sun.security.util;

import android.sun.security.util.Cache;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes.dex */
class MemoryCache extends Cache {
    private static final boolean DEBUG = false;
    private static final float LOAD_FACTOR = 0.75f;
    private final Map<Object, CacheEntry> cacheMap;
    private long lifetime;
    private int maxSize;
    private final ReferenceQueue queue;

    public interface CacheEntry {
        Object getKey();

        Object getValue();

        void invalidate();

        boolean isValid(long j2);
    }

    public static class HardCacheEntry implements CacheEntry {
        private long expirationTime;
        private Object key;
        private Object value;

        public HardCacheEntry(Object obj, Object obj2, long j2) {
            this.key = obj;
            this.value = obj2;
            this.expirationTime = j2;
        }

        @Override // android.sun.security.util.MemoryCache.CacheEntry
        public Object getKey() {
            return this.key;
        }

        @Override // android.sun.security.util.MemoryCache.CacheEntry
        public Object getValue() {
            return this.value;
        }

        @Override // android.sun.security.util.MemoryCache.CacheEntry
        public void invalidate() {
            this.key = null;
            this.value = null;
            this.expirationTime = -1L;
        }

        @Override // android.sun.security.util.MemoryCache.CacheEntry
        public boolean isValid(long j2) {
            boolean z2 = j2 <= this.expirationTime;
            if (!z2) {
                invalidate();
            }
            return z2;
        }
    }

    public static class SoftCacheEntry extends SoftReference implements CacheEntry {
        private long expirationTime;
        private Object key;

        public SoftCacheEntry(Object obj, Object obj2, long j2, ReferenceQueue referenceQueue) {
            super(obj2, referenceQueue);
            this.key = obj;
            this.expirationTime = j2;
        }

        @Override // android.sun.security.util.MemoryCache.CacheEntry
        public Object getKey() {
            return this.key;
        }

        @Override // android.sun.security.util.MemoryCache.CacheEntry
        public Object getValue() {
            return get();
        }

        @Override // android.sun.security.util.MemoryCache.CacheEntry
        public void invalidate() {
            clear();
            this.key = null;
            this.expirationTime = -1L;
        }

        @Override // android.sun.security.util.MemoryCache.CacheEntry
        public boolean isValid(long j2) {
            boolean z2 = j2 <= this.expirationTime && get() != 0;
            if (!z2) {
                invalidate();
            }
            return z2;
        }
    }

    public MemoryCache(boolean z2, int i2) {
        this(z2, i2, 0);
    }

    private void emptyQueue() {
        CacheEntry remove;
        if (this.queue == null) {
            return;
        }
        this.cacheMap.size();
        while (true) {
            CacheEntry cacheEntry = (CacheEntry) this.queue.poll();
            if (cacheEntry == null) {
                return;
            }
            Object key = cacheEntry.getKey();
            if (key != null && (remove = this.cacheMap.remove(key)) != null && cacheEntry != remove) {
                this.cacheMap.put(key, remove);
            }
        }
    }

    private void expungeExpiredEntries() {
        emptyQueue();
        if (this.lifetime == 0) {
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        Iterator<CacheEntry> it = this.cacheMap.values().iterator();
        while (it.hasNext()) {
            if (!it.next().isValid(currentTimeMillis)) {
                it.remove();
            }
        }
    }

    private Map<Object, Object> getCachedEntries() {
        HashMap hashMap = new HashMap(this.cacheMap.size());
        for (CacheEntry cacheEntry : this.cacheMap.values()) {
            hashMap.put(cacheEntry.getKey(), cacheEntry.getValue());
        }
        return hashMap;
    }

    @Override // android.sun.security.util.Cache
    public synchronized void accept(Cache.CacheVisitor cacheVisitor) {
        expungeExpiredEntries();
        cacheVisitor.visit(getCachedEntries());
    }

    @Override // android.sun.security.util.Cache
    public synchronized void clear() {
        if (this.queue != null) {
            Iterator<CacheEntry> it = this.cacheMap.values().iterator();
            while (it.hasNext()) {
                it.next().invalidate();
            }
            while (this.queue.poll() != null) {
            }
        }
        this.cacheMap.clear();
    }

    @Override // android.sun.security.util.Cache
    public synchronized Object get(Object obj) {
        emptyQueue();
        CacheEntry cacheEntry = this.cacheMap.get(obj);
        if (cacheEntry == null) {
            return null;
        }
        long j2 = 0;
        if (this.lifetime != 0) {
            j2 = System.currentTimeMillis();
        }
        if (cacheEntry.isValid(j2)) {
            return cacheEntry.getValue();
        }
        this.cacheMap.remove(obj);
        return null;
    }

    public CacheEntry newEntry(Object obj, Object obj2, long j2, ReferenceQueue referenceQueue) {
        return referenceQueue != null ? new SoftCacheEntry(obj, obj2, j2, referenceQueue) : new HardCacheEntry(obj, obj2, j2);
    }

    @Override // android.sun.security.util.Cache
    public synchronized void put(Object obj, Object obj2) {
        emptyQueue();
        long j2 = 0;
        if (this.lifetime != 0) {
            j2 = this.lifetime + System.currentTimeMillis();
        }
        CacheEntry put = this.cacheMap.put(obj, newEntry(obj, obj2, j2, this.queue));
        if (put != null) {
            put.invalidate();
            return;
        }
        if (this.maxSize > 0 && this.cacheMap.size() > this.maxSize) {
            expungeExpiredEntries();
            if (this.cacheMap.size() > this.maxSize) {
                Iterator<CacheEntry> it = this.cacheMap.values().iterator();
                CacheEntry next = it.next();
                it.remove();
                next.invalidate();
            }
        }
    }

    @Override // android.sun.security.util.Cache
    public synchronized void remove(Object obj) {
        emptyQueue();
        CacheEntry remove = this.cacheMap.remove(obj);
        if (remove != null) {
            remove.invalidate();
        }
    }

    @Override // android.sun.security.util.Cache
    public synchronized void setCapacity(int i2) {
        expungeExpiredEntries();
        if (i2 > 0 && this.cacheMap.size() > i2) {
            Iterator<CacheEntry> it = this.cacheMap.values().iterator();
            for (int size = this.cacheMap.size() - i2; size > 0; size--) {
                CacheEntry next = it.next();
                it.remove();
                next.invalidate();
            }
        }
        if (i2 <= 0) {
            i2 = 0;
        }
        this.maxSize = i2;
    }

    @Override // android.sun.security.util.Cache
    public synchronized void setTimeout(int i2) {
        emptyQueue();
        this.lifetime = i2 > 0 ? i2 * 1000 : 0L;
    }

    @Override // android.sun.security.util.Cache
    public synchronized int size() {
        expungeExpiredEntries();
        return this.cacheMap.size();
    }

    public MemoryCache(boolean z2, int i2, int i3) {
        this.maxSize = i2;
        this.lifetime = i3 * 1000;
        this.queue = z2 ? new ReferenceQueue() : null;
        this.cacheMap = new LinkedHashMap(((int) (i2 / LOAD_FACTOR)) + 1, LOAD_FACTOR, true);
    }
}
