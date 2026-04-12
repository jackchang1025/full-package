package p000;

import java.util.ArrayList;
import java.util.List;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: jo */
/* loaded from: classes2.dex */
public final class C0725jo {

    /* renamed from: a0 */
    public final List f57349a0;

    /* renamed from: a1 */
    public final List f57350a1;

    /* renamed from: a2 */
    public final List f57351a2;

    /* renamed from: a3 */
    public final List f57352a3;

    public C0725jo(ArrayList arrayList) {
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        this.f57349a0 = arrayList2;
        this.f57350a1 = arrayList;
        this.f57351a2 = arrayList3;
        this.f57352a3 = arrayList4;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C0725jo)) {
            return false;
        }
        C0725jo c0725jo = (C0725jo) obj;
        return t60.m214686a2(this.f57349a0, c0725jo.f57349a0) && t60.m214686a2(this.f57350a1, c0725jo.f57350a1) && t60.m214686a2(this.f57351a2, c0725jo.f57351a2) && t60.m214686a2(this.f57352a3, c0725jo.f57352a3);
    }

    public final int hashCode() {
        return this.f57352a3.hashCode() + ((this.f57351a2.hashCode() + ((this.f57350a1.hashCode() + (this.f57349a0.hashCode() * 31)) * 31)) * 31);
    }

    public final String toString() {
        return "CombineFilter(classNameConditions=" + this.f57349a0 + ", textConditions=" + this.f57350a1 + ", descConditions=" + this.f57351a2 + ", boolConditions=" + this.f57352a3 + ")";
    }
}
