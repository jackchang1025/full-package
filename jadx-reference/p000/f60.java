package p000;

import android.graphics.Insets;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class f60 {

    /* renamed from: a4 */
    public static final f60 f56153a4 = new f60(0, 0, 0, 0);

    /* renamed from: a0 */
    public final int f56154a0;

    /* renamed from: a1 */
    public final int f56155a1;

    /* renamed from: a2 */
    public final int f56156a2;

    /* renamed from: a3 */
    public final int f56157a3;

    public f60(int i, int i2, int i3, int i4) {
        this.f56154a0 = i;
        this.f56155a1 = i2;
        this.f56156a2 = i3;
        this.f56157a3 = i4;
    }

    /* renamed from: a0 */
    public static f60 m212747a0(f60 f60Var, f60 f60Var2) {
        return m212748a1(Math.max(f60Var.f56154a0, f60Var2.f56154a0), Math.max(f60Var.f56155a1, f60Var2.f56155a1), Math.max(f60Var.f56156a2, f60Var2.f56156a2), Math.max(f60Var.f56157a3, f60Var2.f56157a3));
    }

    /* renamed from: a1 */
    public static f60 m212748a1(int i, int i2, int i3, int i4) {
        return (i == 0 && i2 == 0 && i3 == 0 && i4 == 0) ? f56153a4 : new f60(i, i2, i3, i4);
    }

    /* renamed from: a2 */
    public static f60 m212749a2(Insets insets) {
        return m212748a1(insets.left, insets.top, insets.right, insets.bottom);
    }

    /* renamed from: a3 */
    public final Insets m212750a3() {
        return e60.m212656a0(this.f56154a0, this.f56155a1, this.f56156a2, this.f56157a3);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || f60.class != obj.getClass()) {
            return false;
        }
        f60 f60Var = (f60) obj;
        return this.f56157a3 == f60Var.f56157a3 && this.f56154a0 == f60Var.f56154a0 && this.f56156a2 == f60Var.f56156a2 && this.f56155a1 == f60Var.f56155a1;
    }

    public final int hashCode() {
        return (((((this.f56154a0 * 31) + this.f56155a1) * 31) + this.f56156a2) * 31) + this.f56157a3;
    }

    public final String toString() {
        return "Insets{left=" + this.f56154a0 + ", top=" + this.f56155a1 + ", right=" + this.f56156a2 + ", bottom=" + this.f56157a3 + '}';
    }
}
