package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class km0 {

    /* renamed from: a0 */
    public float f57546a0;

    /* renamed from: a1 */
    public float f57547a1;

    /* renamed from: a2 */
    public float f57548a2;

    /* renamed from: a3 */
    public float f57549a3;

    /* renamed from: a4 */
    public float f57550a4;

    /* renamed from: a5 */
    public float f57551a5;

    /* renamed from: a6 */
    public int f57552a6;

    /* renamed from: a7 */
    public int f57553a7;

    /* renamed from: a8 */
    public int f57554a8;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof km0)) {
            return false;
        }
        km0 km0Var = (km0) obj;
        return Float.compare(this.f57546a0, km0Var.f57546a0) == 0 && Float.compare(this.f57547a1, km0Var.f57547a1) == 0 && Float.compare(this.f57548a2, km0Var.f57548a2) == 0 && Float.compare(this.f57549a3, km0Var.f57549a3) == 0 && Float.compare(this.f57550a4, km0Var.f57550a4) == 0 && Float.compare(this.f57551a5, km0Var.f57551a5) == 0 && this.f57552a6 == km0Var.f57552a6 && this.f57553a7 == km0Var.f57553a7 && this.f57554a8 == km0Var.f57554a8;
    }

    public final int hashCode() {
        return Integer.hashCode(this.f57554a8) + tz0.m214800a0(this.f57553a7, tz0.m214800a0(this.f57552a6, (Float.hashCode(this.f57551a5) + ((Float.hashCode(this.f57550a4) + ((Float.hashCode(this.f57549a3) + ((Float.hashCode(this.f57548a2) + ((Float.hashCode(this.f57547a1) + (Float.hashCode(this.f57546a0) * 31)) * 31)) * 31)) * 31)) * 31)) * 31, 31), 31);
    }

    public final String toString() {
        return "Particle(x=" + this.f57546a0 + ", y=" + this.f57547a1 + ", radius=" + this.f57548a2 + ", alpha=" + this.f57549a3 + ", speedY=" + this.f57550a4 + ", speedX=" + this.f57551a5 + ", colorR=" + this.f57552a6 + ", colorG=" + this.f57553a7 + ", colorB=" + this.f57554a8 + ")";
    }
}
