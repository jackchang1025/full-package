package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class rj0 {

    /* renamed from: a0 */
    public final boolean f59779a0;

    /* renamed from: a1 */
    public final boolean f59780a1;

    /* renamed from: a2 */
    public final boolean f59781a2;

    /* renamed from: a3 */
    public final boolean f59782a3;

    public rj0(boolean z, boolean z2, boolean z3, boolean z4) {
        this.f59779a0 = z;
        this.f59780a1 = z2;
        this.f59781a2 = z3;
        this.f59782a3 = z4;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof rj0)) {
            return false;
        }
        rj0 rj0Var = (rj0) obj;
        return this.f59779a0 == rj0Var.f59779a0 && this.f59780a1 == rj0Var.f59780a1 && this.f59781a2 == rj0Var.f59781a2 && this.f59782a3 == rj0Var.f59782a3;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        boolean z = this.f59779a0;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int i2 = i * 31;
        boolean z2 = this.f59780a1;
        int i3 = z2;
        if (z2 != 0) {
            i3 = 1;
        }
        int i4 = (i2 + i3) * 31;
        boolean z3 = this.f59781a2;
        int i5 = z3;
        if (z3 != 0) {
            i5 = 1;
        }
        int i6 = (i4 + i5) * 31;
        boolean z4 = this.f59782a3;
        return i6 + (z4 ? 1 : z4 ? 1 : 0);
    }

    public final String toString() {
        return "NetworkState(isConnected=" + this.f59779a0 + ", isValidated=" + this.f59780a1 + ", isMetered=" + this.f59781a2 + ", isNotRoaming=" + this.f59782a3 + ')';
    }
}
