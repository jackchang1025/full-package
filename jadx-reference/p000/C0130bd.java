package p000;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: bd */
/* loaded from: classes.dex */
public final class C0130bd extends t01 implements Map {

    /* renamed from: a7 */
    public C0129bc f45810a7;

    public C0130bd(t01 t01Var) {
        int i = t01Var.f60117a2;
        m214674a1(i);
        if (this.f60117a2 != 0) {
            for (int i2 = 0; i2 < i; i2++) {
                put(t01Var.m214679a7(i2), t01Var.m214681a9(i2));
            }
        } else if (i > 0) {
            System.arraycopy(t01Var.f60115a0, 0, this.f60115a0, 0, i);
            System.arraycopy(t01Var.f60116a1, 0, this.f60116a1, 0, i << 1);
            this.f60117a2 = i;
        }
    }

    @Override // java.util.Map
    public final Set entrySet() {
        if (this.f45810a7 == null) {
            this.f45810a7 = new C0129bc(0, this);
        }
        C0129bc c0129bc = this.f45810a7;
        if (((yc0) c0129bc.f55538a0) == null) {
            c0129bc.f55538a0 = new yc0(c0129bc, 0);
        }
        return (yc0) c0129bc.f55538a0;
    }

    @Override // java.util.Map
    public final Set keySet() {
        if (this.f45810a7 == null) {
            this.f45810a7 = new C0129bc(0, this);
        }
        C0129bc c0129bc = this.f45810a7;
        if (((yc0) c0129bc.f55539a1) == null) {
            c0129bc.f55539a1 = new yc0(c0129bc, 1);
        }
        return (yc0) c0129bc.f55539a1;
    }

    @Override // java.util.Map
    public final void putAll(Map map) {
        m214674a1(map.size() + this.f60117a2);
        for (Map.Entry entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override // java.util.Map
    public final Collection values() {
        if (this.f45810a7 == null) {
            this.f45810a7 = new C0129bc(0, this);
        }
        C0129bc c0129bc = this.f45810a7;
        if (((ad0) c0129bc.f55540a2) == null) {
            c0129bc.f55540a2 = new ad0(c0129bc);
        }
        return (ad0) c0129bc.f55540a2;
    }
}
