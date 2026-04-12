package p000;

import java.util.Map;
import kotlin.collections.builders.MapBuilder;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class uc0 implements Map.Entry, d80 {

    /* renamed from: a0 */
    public final MapBuilder f60376a0;

    /* renamed from: a1 */
    public final int f60377a1;

    public uc0(MapBuilder mapBuilder, int i) {
        t60.m214695b6(mapBuilder, "map");
        this.f60376a0 = mapBuilder;
        this.f60377a1 = i;
    }

    @Override // java.util.Map.Entry
    public final boolean equals(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        return t60.m214686a2(entry.getKey(), getKey()) && t60.m214686a2(entry.getValue(), getValue());
    }

    @Override // java.util.Map.Entry
    public final Object getKey() {
        return this.f60376a0.f57587a0[this.f60377a1];
    }

    @Override // java.util.Map.Entry
    public final Object getValue() {
        Object[] objArr = this.f60376a0.f57588a1;
        t60.m214692b3(objArr);
        return objArr[this.f60377a1];
    }

    @Override // java.util.Map.Entry
    public final int hashCode() {
        Object key = getKey();
        int iHashCode = key != null ? key.hashCode() : 0;
        Object value = getValue();
        return iHashCode ^ (value != null ? value.hashCode() : 0);
    }

    @Override // java.util.Map.Entry
    public final Object setValue(Object obj) {
        MapBuilder mapBuilder = this.f60376a0;
        mapBuilder.m213628a2();
        Object[] objArr = mapBuilder.f57588a1;
        if (objArr == null) {
            int length = mapBuilder.f57587a0.length;
            if (length < 0) {
                throw new IllegalArgumentException("capacity must be non-negative.");
            }
            objArr = new Object[length];
            mapBuilder.f57588a1 = objArr;
        }
        int i = this.f60377a1;
        Object obj2 = objArr[i];
        objArr[i] = obj;
        return obj2;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getKey());
        sb.append('=');
        sb.append(getValue());
        return sb.toString();
    }
}
