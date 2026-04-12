package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class k41 {

    /* renamed from: a0 */
    public String f57454a0;

    /* renamed from: a1 */
    public int f57455a1;

    /* renamed from: a2 */
    public String f57456a2;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof k41)) {
            return false;
        }
        k41 k41Var = (k41) obj;
        return t60.m214686a2(this.f57454a0, k41Var.f57454a0) && this.f57455a1 == k41Var.f57455a1 && t60.m214686a2(this.f57456a2, k41Var.f57456a2);
    }

    public final int hashCode() {
        return this.f57456a2.hashCode() + tz0.m214800a0(this.f57455a1, this.f57454a0.hashCode() * 31, 31);
    }

    public final String toString() {
        String str = this.f57454a0;
        int i = this.f57455a1;
        return AbstractC0003a2.m35b6(AbstractC0003a2.m40c1("PairPortAndCodeResult(host=", str, ", pairPort=", i, ", pairCode="), this.f57456a2, ")");
    }
}
