package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class j40 {

    /* renamed from: a0 */
    public final float f57260a0;

    /* renamed from: a1 */
    public final float f57261a1;

    /* renamed from: a2 */
    public final float f57262a2;

    /* renamed from: a3 */
    public final float f57263a3;

    /* renamed from: a4 */
    public final String f57264a4;

    public j40(float f, float f2, float f3, float f4, String str) {
        this.f57260a0 = f;
        this.f57261a1 = f2;
        this.f57262a2 = f3;
        this.f57263a3 = f4;
        this.f57264a4 = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof j40)) {
            return false;
        }
        j40 j40Var = (j40) obj;
        return Float.compare(this.f57260a0, j40Var.f57260a0) == 0 && Float.compare(this.f57261a1, j40Var.f57261a1) == 0 && Float.compare(this.f57262a2, j40Var.f57262a2) == 0 && Float.compare(this.f57263a3, j40Var.f57263a3) == 0 && t60.m214686a2(this.f57264a4, j40Var.f57264a4);
    }

    public final int hashCode() {
        return this.f57264a4.hashCode() + ((Float.hashCode(this.f57263a3) + ((Float.hashCode(this.f57262a2) + ((Float.hashCode(this.f57261a1) + (Float.hashCode(this.f57260a0) * 31)) * 31)) * 31)) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("HonorPercentConfig(x1=");
        sb.append(this.f57260a0);
        sb.append(", y1=");
        sb.append(this.f57261a1);
        sb.append(", x2=");
        sb.append(this.f57262a2);
        sb.append(", y2=");
        sb.append(this.f57263a3);
        sb.append(", description=");
        return AbstractC0003a2.m35b6(sb, this.f57264a4, ")");
    }
}
