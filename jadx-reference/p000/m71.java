package p000;

import android.graphics.Rect;
import android.view.accessibility.AccessibilityNodeInfo;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class m71 {

    /* renamed from: a0 */
    public final int f58283a0;

    /* renamed from: a1 */
    public final Rect f58284a1;

    /* renamed from: a2 */
    public final String f58285a2;

    /* renamed from: a3 */
    public final String f58286a3;

    /* renamed from: a4 */
    public final String f58287a4;

    /* renamed from: a5 */
    public final AccessibilityNodeInfo f58288a5;

    public m71(int i, Rect rect, String str, String str2, String str3, AccessibilityNodeInfo accessibilityNodeInfo) {
        t60.m214695b6(str3, "desc");
        this.f58283a0 = i;
        this.f58284a1 = rect;
        this.f58285a2 = str;
        this.f58286a3 = str2;
        this.f58287a4 = str3;
        this.f58288a5 = accessibilityNodeInfo;
    }

    /* renamed from: a0 */
    public final void m213947a0() {
        try {
            AccessibilityNodeInfo accessibilityNodeInfo = this.f58288a5;
            if (accessibilityNodeInfo.isClickable()) {
                accessibilityNodeInfo.performAction(16);
                return;
            }
            AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent();
            if (parent != null) {
                parent.performAction(16);
            }
        } catch (Exception unused) {
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof m71)) {
            return false;
        }
        m71 m71Var = (m71) obj;
        return this.f58283a0 == m71Var.f58283a0 && t60.m214686a2(this.f58284a1, m71Var.f58284a1) && t60.m214686a2(this.f58285a2, m71Var.f58285a2) && t60.m214686a2(this.f58286a3, m71Var.f58286a3) && t60.m214686a2(this.f58287a4, m71Var.f58287a4) && t60.m214686a2(this.f58288a5, m71Var.f58288a5);
    }

    public final int hashCode() {
        int iM214801a1 = tz0.m214801a1(tz0.m214801a1(tz0.m214801a1((this.f58284a1.hashCode() + (Integer.hashCode(this.f58283a0) * 31)) * 31, 31, this.f58285a2), 31, this.f58286a3), 31, this.f58287a4);
        AccessibilityNodeInfo accessibilityNodeInfo = this.f58288a5;
        return iM214801a1 + (accessibilityNodeInfo == null ? 0 : accessibilityNodeInfo.hashCode());
    }

    public final String toString() {
        return "CachedBtn(digit=" + this.f58283a0 + ", bounds=" + this.f58284a1 + ", id=" + this.f58285a2 + ", text=" + this.f58286a3 + ", desc=" + this.f58287a4 + ", nodeInfo=" + this.f58288a5 + ")";
    }
}
