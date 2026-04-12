package p000;

import android.view.accessibility.AccessibilityNodeInfo;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class ij1 {

    /* renamed from: a0 */
    public final AccessibilityNodeInfo f56906a0;

    /* renamed from: a1 */
    public final int f56907a1;

    public ij1(AccessibilityNodeInfo accessibilityNodeInfo, int i) {
        this.f56906a0 = accessibilityNodeInfo;
        this.f56907a1 = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ij1)) {
            return false;
        }
        ij1 ij1Var = (ij1) obj;
        return t60.m214686a2(this.f56906a0, ij1Var.f56906a0) && this.f56907a1 == ij1Var.f56907a1;
    }

    public final int hashCode() {
        return Integer.hashCode(this.f56907a1) + (this.f56906a0.hashCode() * 31);
    }

    public final String toString() {
        return "StackItem(n=" + this.f56906a0 + ", d=" + this.f56907a1 + ")";
    }
}
