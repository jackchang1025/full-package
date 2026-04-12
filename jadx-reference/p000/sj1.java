package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class sj1 {

    /* renamed from: a0 */
    public final boolean f60000a0;

    /* renamed from: a1 */
    public final int f60001a1;

    /* renamed from: a2 */
    public final int f60002a2;

    /* renamed from: a3 */
    public final int f60003a3;

    /* renamed from: a4 */
    public final int f60004a4;

    /* renamed from: a5 */
    public final int f60005a5;

    /* renamed from: a6 */
    public final float f60006a6;

    public sj1(boolean z, int i, int i2, int i3, int i4, int i5, float f) {
        this.f60000a0 = z;
        this.f60001a1 = i;
        this.f60002a2 = i2;
        this.f60003a3 = i3;
        this.f60004a4 = i4;
        this.f60005a5 = i5;
        this.f60006a6 = f;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof sj1)) {
            return false;
        }
        sj1 sj1Var = (sj1) obj;
        return this.f60000a0 == sj1Var.f60000a0 && this.f60001a1 == sj1Var.f60001a1 && this.f60002a2 == sj1Var.f60002a2 && this.f60003a3 == sj1Var.f60003a3 && this.f60004a4 == sj1Var.f60004a4 && this.f60005a5 == sj1Var.f60005a5 && Float.compare(this.f60006a6, sj1Var.f60006a6) == 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [int] */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r0v9 */
    public final int hashCode() {
        boolean z = this.f60000a0;
        ?? r0 = z;
        if (z) {
            r0 = 1;
        }
        return Float.hashCode(this.f60006a6) + tz0.m214800a0(this.f60005a5, tz0.m214800a0(this.f60004a4, tz0.m214800a0(this.f60003a3, tz0.m214800a0(this.f60002a2, tz0.m214800a0(this.f60001a1, r0 * 31, 31), 31), 31), 31), 31);
    }

    public final String toString() {
        return "NotificationStyle(isDarkMode=" + this.f60000a0 + ", backgroundColor=" + this.f60001a1 + ", titleColor=" + this.f60002a2 + ", contentColor=" + this.f60003a3 + ", subtitleColor=" + this.f60004a4 + ", accentColor=" + this.f60005a5 + ", cornerRadius=" + this.f60006a6 + ")";
    }
}
