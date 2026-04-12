package p000;

import android.view.DisplayCutout;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: tl */
/* loaded from: classes.dex */
public final class C1264tl {

    /* renamed from: a0 */
    public final DisplayCutout f60237a0;

    public C1264tl(DisplayCutout displayCutout) {
        this.f60237a0 = displayCutout;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || C1264tl.class != obj.getClass()) {
            return false;
        }
        return tk0.m214759a0(this.f60237a0, ((C1264tl) obj).f60237a0);
    }

    public final int hashCode() {
        return this.f60237a0.hashCode();
    }

    public final String toString() {
        return "DisplayCutoutCompat{" + this.f60237a0 + "}";
    }
}
