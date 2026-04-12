package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class jg1 {

    /* renamed from: a0 */
    public final String f57334a0;

    /* renamed from: a1 */
    public final int f57335a1;

    public jg1(String str, int i) {
        t60.m214695b6(str, "workSpecId");
        this.f57334a0 = str;
        this.f57335a1 = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof jg1)) {
            return false;
        }
        jg1 jg1Var = (jg1) obj;
        return t60.m214686a2(this.f57334a0, jg1Var.f57334a0) && this.f57335a1 == jg1Var.f57335a1;
    }

    public final int hashCode() {
        return Integer.hashCode(this.f57335a1) + (this.f57334a0.hashCode() * 31);
    }

    public final String toString() {
        return "WorkGenerationalId(workSpecId=" + this.f57334a0 + ", generation=" + this.f57335a1 + ')';
    }
}
