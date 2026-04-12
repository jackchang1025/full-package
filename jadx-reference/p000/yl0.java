package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class yl0 implements InterfaceC0625il {

    /* renamed from: a0 */
    public final Class f61344a0;

    public yl0(Class cls) {
        this.f61344a0 = cls;
    }

    @Override // p000.InterfaceC0625il
    /* renamed from: a0 */
    public final Class mo213174a0() {
        return this.f61344a0;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof yl0) {
            return t60.m214686a2(this.f61344a0, ((yl0) obj).f61344a0);
        }
        return false;
    }

    public final int hashCode() {
        return this.f61344a0.hashCode();
    }

    public final String toString() {
        return this.f61344a0.toString() + " (Kotlin reflection is not available)";
    }
}
