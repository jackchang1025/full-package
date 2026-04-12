package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class y50 {

    /* renamed from: a0 */
    public final String f61237a0;

    /* renamed from: a1 */
    public final float f61238a1;

    /* renamed from: a2 */
    public final float f61239a2;

    public y50(String str, float f, float f2) {
        this.f61237a0 = str;
        this.f61238a1 = f;
        this.f61239a2 = f2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof y50)) {
            return false;
        }
        y50 y50Var = (y50) obj;
        return t60.m214686a2(this.f61237a0, y50Var.f61237a0) && Float.compare(this.f61238a1, y50Var.f61238a1) == 0 && Float.compare(this.f61239a2, y50Var.f61239a2) == 0;
    }

    public final int hashCode() {
        return Float.hashCode(this.f61239a2) + ((Float.hashCode(this.f61238a1) + (this.f61237a0.hashCode() * 31)) * 31);
    }

    public final String toString() {
        return "KeyButton(text=" + this.f61237a0 + ", x=" + this.f61238a1 + ", y=" + this.f61239a2 + ")";
    }
}
