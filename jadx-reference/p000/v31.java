package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class v31 {

    /* renamed from: a0 */
    public final String f60571a0;

    /* renamed from: a1 */
    public final int f60572a1;

    /* renamed from: a2 */
    public final int f60573a2;

    public v31(String str, int i, int i2) {
        t60.m214695b6(str, "workSpecId");
        this.f60571a0 = str;
        this.f60572a1 = i;
        this.f60573a2 = i2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof v31)) {
            return false;
        }
        v31 v31Var = (v31) obj;
        return t60.m214686a2(this.f60571a0, v31Var.f60571a0) && this.f60572a1 == v31Var.f60572a1 && this.f60573a2 == v31Var.f60573a2;
    }

    public final int hashCode() {
        return Integer.hashCode(this.f60573a2) + tz0.m214800a0(this.f60572a1, this.f60571a0.hashCode() * 31, 31);
    }

    public final String toString() {
        return "SystemIdInfo(workSpecId=" + this.f60571a0 + ", generation=" + this.f60572a1 + ", systemId=" + this.f60573a2 + ')';
    }
}
