package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class do0 {

    /* renamed from: a0 */
    public final String f55836a0;

    /* renamed from: a1 */
    public final Long f55837a1;

    public do0(String str, Long l) {
        this.f55836a0 = str;
        this.f55837a1 = l;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof do0)) {
            return false;
        }
        do0 do0Var = (do0) obj;
        return t60.m214686a2(this.f55836a0, do0Var.f55836a0) && t60.m214686a2(this.f55837a1, do0Var.f55837a1);
    }

    public final int hashCode() {
        int iHashCode = this.f55836a0.hashCode() * 31;
        Long l = this.f55837a1;
        return iHashCode + (l == null ? 0 : l.hashCode());
    }

    public final String toString() {
        return "Preference(key=" + this.f55836a0 + ", value=" + this.f55837a1 + ')';
    }
}
