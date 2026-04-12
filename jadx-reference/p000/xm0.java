package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class xm0 {

    /* renamed from: a0 */
    public final int f61157a0;

    /* renamed from: a1 */
    public final int f61158a1;

    /* renamed from: a2 */
    public final int f61159a2;

    /* renamed from: a3 */
    public final int f61160a3;

    /* renamed from: a4 */
    public final int f61161a4;

    /* renamed from: a5 */
    public final int f61162a5;

    /* renamed from: a6 */
    public final float f61163a6;

    public xm0(int i, int i2, int i3, int i4, int i5, int i6, float f) {
        this.f61157a0 = i;
        this.f61158a1 = i2;
        this.f61159a2 = i3;
        this.f61160a3 = i4;
        this.f61161a4 = i5;
        this.f61162a5 = i6;
        this.f61163a6 = f;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof xm0)) {
            return false;
        }
        xm0 xm0Var = (xm0) obj;
        return this.f61157a0 == xm0Var.f61157a0 && this.f61158a1 == xm0Var.f61158a1 && this.f61159a2 == xm0Var.f61159a2 && this.f61160a3 == xm0Var.f61160a3 && this.f61161a4 == xm0Var.f61161a4 && this.f61162a5 == xm0Var.f61162a5 && Float.compare(this.f61163a6, xm0Var.f61163a6) == 0;
    }

    public final int hashCode() {
        return Float.hashCode(this.f61163a6) + tz0.m214800a0(this.f61162a5, tz0.m214800a0(this.f61161a4, tz0.m214800a0(this.f61160a3, tz0.m214800a0(this.f61159a2, tz0.m214800a0(this.f61158a1, Integer.hashCode(this.f61157a0) * 31, 31), 31), 31), 31), 31);
    }

    public final String toString() {
        StringBuilder sbM38b9 = AbstractC0003a2.m38b9("SystemPatternStyle(haloSizePx=", this.f61157a0, ", innerDotSizePx=", this.f61158a1, ", dotSelectedPx=");
        sbM38b9.append(this.f61159a2);
        sbM38b9.append(", dotColor=");
        sbM38b9.append(this.f61160a3);
        sbM38b9.append(", pathColor=");
        sbM38b9.append(this.f61161a4);
        sbM38b9.append(", pathWidthPx=");
        sbM38b9.append(this.f61162a5);
        sbM38b9.append(", outerCircleAlpha=");
        sbM38b9.append(this.f61163a6);
        sbM38b9.append(")");
        return sbM38b9.toString();
    }
}
