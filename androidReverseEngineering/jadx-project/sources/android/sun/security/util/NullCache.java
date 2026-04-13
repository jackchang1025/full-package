package android.sun.security.util;

import android.sun.security.util.Cache;

/* loaded from: classes.dex */
class NullCache extends Cache {
    static final Cache INSTANCE = new NullCache();

    private NullCache() {
    }

    @Override // android.sun.security.util.Cache
    public void accept(Cache.CacheVisitor cacheVisitor) {
    }

    @Override // android.sun.security.util.Cache
    public void clear() {
    }

    @Override // android.sun.security.util.Cache
    public Object get(Object obj) {
        return null;
    }

    @Override // android.sun.security.util.Cache
    public void put(Object obj, Object obj2) {
    }

    @Override // android.sun.security.util.Cache
    public void remove(Object obj) {
    }

    @Override // android.sun.security.util.Cache
    public void setCapacity(int i2) {
    }

    @Override // android.sun.security.util.Cache
    public void setTimeout(int i2) {
    }

    @Override // android.sun.security.util.Cache
    public int size() {
        return 0;
    }
}
