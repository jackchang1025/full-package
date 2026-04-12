package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class n60 extends k60 {

    /* renamed from: a4 */
    public static final m60 f58456a4 = new m60(null);

    /* renamed from: a5 */
    public static final n60 f58457a5 = new n60(1, 0, 1);

    /* renamed from: a1 */
    public final boolean m214033a1(int i) {
        return this.f57461a0 <= i && i <= this.f57462a1;
    }

    @Override // p000.k60
    public final boolean equals(Object obj) {
        if (!(obj instanceof n60)) {
            return false;
        }
        if (isEmpty() && ((n60) obj).isEmpty()) {
            return true;
        }
        n60 n60Var = (n60) obj;
        return this.f57461a0 == n60Var.f57461a0 && this.f57462a1 == n60Var.f57462a1;
    }

    @Override // p000.k60
    public final int hashCode() {
        if (isEmpty()) {
            return -1;
        }
        return (this.f57461a0 * 31) + this.f57462a1;
    }

    @Override // p000.k60
    public final boolean isEmpty() {
        return this.f57461a0 > this.f57462a1;
    }

    @Override // p000.k60
    public final String toString() {
        return this.f57461a0 + ".." + this.f57462a1;
    }
}
