package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class im0 {

    /* renamed from: a0 */
    public final Object f56911a0;

    /* renamed from: a1 */
    public final Object f56912a1;

    public im0(Object obj, Object obj2) {
        this.f56911a0 = obj;
        this.f56912a1 = obj2;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof im0)) {
            return false;
        }
        im0 im0Var = (im0) obj;
        return tk0.m214759a0(im0Var.f56911a0, this.f56911a0) && tk0.m214759a0(im0Var.f56912a1, this.f56912a1);
    }

    public final int hashCode() {
        Object obj = this.f56911a0;
        int iHashCode = obj == null ? 0 : obj.hashCode();
        Object obj2 = this.f56912a1;
        return (obj2 != null ? obj2.hashCode() : 0) ^ iHashCode;
    }

    public final String toString() {
        return "Pair{" + this.f56911a0 + " " + this.f56912a1 + "}";
    }
}
