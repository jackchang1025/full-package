package android.support.v4.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public class ArrayMap<K, V> extends SimpleArrayMap<K, V> implements Map<K, V> {

    @Nullable
    MapCollections<K, V> mCollections;

    public ArrayMap() {
    }

    public ArrayMap(int i2) {
        super(i2);
    }

    private MapCollections<K, V> getCollection() {
        if (this.mCollections == null) {
            this.mCollections = new MapCollections<K, V>() { // from class: android.support.v4.util.ArrayMap.1
                @Override // android.support.v4.util.MapCollections
                public void colClear() {
                    ArrayMap.this.clear();
                }

                @Override // android.support.v4.util.MapCollections
                public Object colGetEntry(int i2, int i3) {
                    return ArrayMap.this.mArray[(i2 << 1) + i3];
                }

                @Override // android.support.v4.util.MapCollections
                public Map<K, V> colGetMap() {
                    return ArrayMap.this;
                }

                @Override // android.support.v4.util.MapCollections
                public int colGetSize() {
                    return ArrayMap.this.mSize;
                }

                @Override // android.support.v4.util.MapCollections
                public int colIndexOfKey(Object obj) {
                    return ArrayMap.this.indexOfKey(obj);
                }

                @Override // android.support.v4.util.MapCollections
                public int colIndexOfValue(Object obj) {
                    return ArrayMap.this.indexOfValue(obj);
                }

                @Override // android.support.v4.util.MapCollections
                public void colPut(K k2, V v2) {
                    ArrayMap.this.put(k2, v2);
                }

                @Override // android.support.v4.util.MapCollections
                public void colRemoveAt(int i2) {
                    ArrayMap.this.removeAt(i2);
                }

                @Override // android.support.v4.util.MapCollections
                public V colSetValue(int i2, V v2) {
                    return ArrayMap.this.setValueAt(i2, v2);
                }
            };
        }
        return this.mCollections;
    }

    public boolean containsAll(@NonNull Collection<?> collection) {
        return MapCollections.containsAllHelper(this, collection);
    }

    @Override // java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        return getCollection().getEntrySet();
    }

    @Override // java.util.Map
    public Set<K> keySet() {
        return getCollection().getKeySet();
    }

    @Override // java.util.Map
    public void putAll(Map<? extends K, ? extends V> map) {
        ensureCapacity(map.size() + this.mSize);
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    public boolean removeAll(@NonNull Collection<?> collection) {
        return MapCollections.removeAllHelper(this, collection);
    }

    public boolean retainAll(@NonNull Collection<?> collection) {
        return MapCollections.retainAllHelper(this, collection);
    }

    @Override // java.util.Map
    public Collection<V> values() {
        return getCollection().getValues();
    }

    public ArrayMap(SimpleArrayMap simpleArrayMap) {
        super(simpleArrayMap);
    }
}
