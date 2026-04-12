package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class k20 {

    /* renamed from: a0 */
    public final long f57420a0;

    /* renamed from: a1 */
    public final String f57421a1;

    /* renamed from: a2 */
    public final long f57422a2;

    /* renamed from: a3 */
    public final String f57423a3;

    /* renamed from: a4 */
    public final int f57424a4;

    /* renamed from: a5 */
    public final int f57425a5;

    /* renamed from: a6 */
    public final long f57426a6;

    /* renamed from: a7 */
    public final String f57427a7;

    /* renamed from: a8 */
    public final String f57428a8;

    public k20(long j, String str, long j2, String str2, int i, int i2, long j3, String str3, String str4) {
        this.f57420a0 = j;
        this.f57421a1 = str;
        this.f57422a2 = j2;
        this.f57423a3 = str2;
        this.f57424a4 = i;
        this.f57425a5 = i2;
        this.f57426a6 = j3;
        this.f57427a7 = str3;
        this.f57428a8 = str4;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof k20)) {
            return false;
        }
        k20 k20Var = (k20) obj;
        return this.f57420a0 == k20Var.f57420a0 && t60.m214686a2(this.f57421a1, k20Var.f57421a1) && this.f57422a2 == k20Var.f57422a2 && t60.m214686a2(this.f57423a3, k20Var.f57423a3) && this.f57424a4 == k20Var.f57424a4 && this.f57425a5 == k20Var.f57425a5 && this.f57426a6 == k20Var.f57426a6 && t60.m214686a2(this.f57427a7, k20Var.f57427a7) && t60.m214686a2(this.f57428a8, k20Var.f57428a8);
    }

    public final int hashCode() {
        int iM214801a1 = tz0.m214801a1((Long.hashCode(this.f57426a6) + tz0.m214800a0(this.f57425a5, tz0.m214800a0(this.f57424a4, tz0.m214801a1((Long.hashCode(this.f57422a2) + tz0.m214801a1(Long.hashCode(this.f57420a0) * 31, 31, this.f57421a1)) * 31, 31, this.f57423a3), 31), 31)) * 31, 31, this.f57427a7);
        String str = this.f57428a8;
        return iM214801a1 + (str == null ? 0 : str.hashCode());
    }

    public final String toString() {
        return "GalleryItem(id=" + this.f57420a0 + ", displayName=" + this.f57421a1 + ", dateAdded=" + this.f57422a2 + ", mimeType=" + this.f57423a3 + ", width=" + this.f57424a4 + ", height=" + this.f57425a5 + ", size=" + this.f57426a6 + ", contentUri=" + this.f57427a7 + ", thumbnail=" + this.f57428a8 + ")";
    }
}
