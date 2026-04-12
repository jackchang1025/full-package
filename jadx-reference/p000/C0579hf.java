package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: hf */
/* loaded from: classes2.dex */
public final class C0579hf {

    /* renamed from: a0 */
    public boolean f56662a0;

    /* renamed from: a1 */
    public boolean f56663a1;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C0579hf)) {
            return false;
        }
        C0579hf c0579hf = (C0579hf) obj;
        return this.f56662a0 == c0579hf.f56662a0 && this.f56663a1 == c0579hf.f56663a1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [int] */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v5 */
    public final int hashCode() {
        boolean z = this.f56662a0;
        ?? r0 = z;
        if (z) {
            r0 = 1;
        }
        int i = r0 * 31;
        boolean z2 = this.f56663a1;
        return i + (z2 ? 1 : z2 ? 1 : 0);
    }

    public final String toString() {
        return "CheckedResult{checked=" + this.f56662a0 + ", clicked=" + this.f56663a1 + "}";
    }
}
