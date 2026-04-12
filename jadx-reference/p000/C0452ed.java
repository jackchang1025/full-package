package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ed */
/* loaded from: classes2.dex */
public final class C0452ed {

    /* renamed from: a0 */
    public final String f55970a0;

    /* renamed from: a1 */
    public final String f55971a1;

    /* renamed from: a2 */
    public final String f55972a2;

    public C0452ed(String str, String str2, String str3) {
        this.f55970a0 = str;
        this.f55971a1 = str2;
        this.f55972a2 = str3;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C0452ed)) {
            return false;
        }
        C0452ed c0452ed = (C0452ed) obj;
        return t60.m214686a2(this.f55970a0, c0452ed.f55970a0) && t60.m214686a2(this.f55971a1, c0452ed.f55971a1) && t60.m214686a2(this.f55972a2, c0452ed.f55972a2);
    }

    public final int hashCode() {
        return this.f55972a2.hashCode() + tz0.m214801a1(this.f55970a0.hashCode() * 31, 31, this.f55971a1);
    }

    public final String toString() {
        return AbstractC0003a2.m35b6(AbstractC0003a2.m41c2("UpdateStrings(title=", this.f55970a0, ", subtitle=", this.f55971a1, ", updateSize="), this.f55972a2, ")");
    }
}
