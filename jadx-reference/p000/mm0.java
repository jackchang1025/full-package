package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class mm0 {

    /* renamed from: a0 */
    public boolean f58384a0;

    /* renamed from: a1 */
    public boolean f58385a1;

    /* renamed from: a2 */
    public boolean f58386a2;

    /* renamed from: a3 */
    public boolean f58387a3;

    /* renamed from: a4 */
    public boolean f58388a4;

    /* renamed from: a5 */
    public boolean f58389a5;

    /* renamed from: a6 */
    public boolean f58390a6;

    /* renamed from: a7 */
    public boolean f58391a7;

    /* renamed from: a8 */
    public boolean f58392a8;

    /* renamed from: a9 */
    public int f58393a9;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof mm0)) {
            return false;
        }
        mm0 mm0Var = (mm0) obj;
        return this.f58384a0 == mm0Var.f58384a0 && this.f58385a1 == mm0Var.f58385a1 && this.f58386a2 == mm0Var.f58386a2 && this.f58387a3 == mm0Var.f58387a3 && this.f58388a4 == mm0Var.f58388a4 && this.f58389a5 == mm0Var.f58389a5 && this.f58390a6 == mm0Var.f58390a6 && this.f58391a7 == mm0Var.f58391a7 && this.f58392a8 == mm0Var.f58392a8 && this.f58393a9 == mm0Var.f58393a9;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [int] */
    /* JADX WARN: Type inference failed for: r0v19 */
    /* JADX WARN: Type inference failed for: r0v20 */
    /* JADX WARN: Type inference failed for: r2v0, types: [boolean] */
    /* JADX WARN: Type inference failed for: r2v10, types: [boolean] */
    /* JADX WARN: Type inference failed for: r2v12, types: [boolean] */
    /* JADX WARN: Type inference failed for: r2v2, types: [boolean] */
    /* JADX WARN: Type inference failed for: r2v4, types: [boolean] */
    /* JADX WARN: Type inference failed for: r2v6, types: [boolean] */
    /* JADX WARN: Type inference failed for: r2v8, types: [boolean] */
    public final int hashCode() {
        boolean z = this.f58384a0;
        ?? r0 = z;
        if (z) {
            r0 = 1;
        }
        int i = r0 * 31;
        ?? r2 = this.f58385a1;
        int i2 = r2;
        if (r2 != 0) {
            i2 = 1;
        }
        int i3 = (i + i2) * 31;
        ?? r22 = this.f58386a2;
        int i4 = r22;
        if (r22 != 0) {
            i4 = 1;
        }
        int i5 = (i3 + i4) * 31;
        ?? r23 = this.f58387a3;
        int i6 = r23;
        if (r23 != 0) {
            i6 = 1;
        }
        int i7 = (i5 + i6) * 31;
        ?? r24 = this.f58388a4;
        int i8 = r24;
        if (r24 != 0) {
            i8 = 1;
        }
        int i9 = (i7 + i8) * 31;
        ?? r25 = this.f58389a5;
        int i10 = r25;
        if (r25 != 0) {
            i10 = 1;
        }
        int i11 = (i9 + i10) * 31;
        ?? r26 = this.f58390a6;
        int i12 = r26;
        if (r26 != 0) {
            i12 = 1;
        }
        int i13 = (i11 + i12) * 31;
        ?? r27 = this.f58391a7;
        int i14 = r27;
        if (r27 != 0) {
            i14 = 1;
        }
        int i15 = (i13 + i14) * 31;
        boolean z2 = this.f58392a8;
        return Integer.hashCode(this.f58393a9) + ((i15 + (z2 ? 1 : z2 ? 1 : 0)) * 31);
    }

    public final String toString() {
        return "UIAnalysisResult(patternKeywords=" + this.f58384a0 + ", pinKeywords=" + this.f58385a1 + ", passwordKeywords=" + this.f58386a2 + ", lockScreenKeywords=" + this.f58387a3 + ", passwordField=" + this.f58388a4 + ", keypad=" + this.f58389a5 + ", patternView=" + this.f58390a6 + ", isLockScreen=" + this.f58391a7 + ", unlockElements=" + this.f58392a8 + ", buttonCount=" + this.f58393a9 + ")";
    }
}
