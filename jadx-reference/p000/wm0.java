package p000;

import android.graphics.Rect;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class wm0 {

    /* renamed from: a0 */
    public final Rect f60946a0;

    /* renamed from: a1 */
    public final Rect f60947a1;

    public wm0(Rect rect, Rect rect2) {
        this.f60946a0 = rect;
        this.f60947a1 = rect2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof wm0)) {
            return false;
        }
        wm0 wm0Var = (wm0) obj;
        return this.f60946a0.equals(wm0Var.f60946a0) && this.f60947a1.equals(wm0Var.f60947a1);
    }

    public final int hashCode() {
        return this.f60947a1.hashCode() + (this.f60946a0.hashCode() * 31);
    }

    public final String toString() {
        return "SystemPatternInfo(node=null, boundsInScreen=" + this.f60946a0 + ", boundsInParent=" + this.f60947a1 + ")";
    }
}
