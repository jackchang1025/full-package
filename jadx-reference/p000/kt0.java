package p000;

import java.util.Map;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class kt0 implements Map.Entry {

    /* renamed from: a0 */
    public final Object f57716a0;

    /* renamed from: a1 */
    public final Object f57717a1;

    /* renamed from: a2 */
    public kt0 f57718a2;

    /* renamed from: a3 */
    public kt0 f57719a3;

    public kt0(Object obj, Object obj2) {
        this.f57716a0 = obj;
        this.f57717a1 = obj2;
    }

    @Override // java.util.Map.Entry
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof kt0)) {
            return false;
        }
        kt0 kt0Var = (kt0) obj;
        return this.f57716a0.equals(kt0Var.f57716a0) && this.f57717a1.equals(kt0Var.f57717a1);
    }

    @Override // java.util.Map.Entry
    public final Object getKey() {
        return this.f57716a0;
    }

    @Override // java.util.Map.Entry
    public final Object getValue() {
        return this.f57717a1;
    }

    @Override // java.util.Map.Entry
    public final int hashCode() {
        return this.f57716a0.hashCode() ^ this.f57717a1.hashCode();
    }

    @Override // java.util.Map.Entry
    public final Object setValue(Object obj) {
        throw new UnsupportedOperationException("An entry modification is not supported");
    }

    public final String toString() {
        return this.f57716a0 + "=" + this.f57717a1;
    }
}
