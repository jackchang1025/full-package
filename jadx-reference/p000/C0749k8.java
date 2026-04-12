package p000;

import android.os.Bundle;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import java.util.List;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: k8 */
/* loaded from: classes.dex */
public class C0749k8 extends AccessibilityNodeProvider {

    /* renamed from: a0 */
    public final tg0 f57481a0;

    public C0749k8(tg0 tg0Var) {
        this.f57481a0 = tg0Var;
    }

    @Override // android.view.accessibility.AccessibilityNodeProvider
    public final AccessibilityNodeInfo createAccessibilityNodeInfo(int i) {
        C0748k7 c0748k7Mo214746b8 = this.f57481a0.mo214746b8(i);
        if (c0748k7Mo214746b8 == null) {
            return null;
        }
        return c0748k7Mo214746b8.f57472a0;
    }

    @Override // android.view.accessibility.AccessibilityNodeProvider
    public final List findAccessibilityNodeInfosByText(String str, int i) {
        this.f57481a0.getClass();
        return null;
    }

    @Override // android.view.accessibility.AccessibilityNodeProvider
    public final AccessibilityNodeInfo findFocus(int i) {
        C0748k7 c0748k7Mo214747b9 = this.f57481a0.mo214747b9(i);
        if (c0748k7Mo214747b9 == null) {
            return null;
        }
        return c0748k7Mo214747b9.f57472a0;
    }

    @Override // android.view.accessibility.AccessibilityNodeProvider
    public final boolean performAction(int i, int i2, Bundle bundle) {
        return this.f57481a0.mo214749c3(i, i2, bundle);
    }
}
