package p000;

import android.view.accessibility.AccessibilityManager;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: j2 */
/* loaded from: classes.dex */
public final class AccessibilityManagerTouchExplorationStateChangeListenerC0703j2 implements AccessibilityManager.TouchExplorationStateChangeListener {

    /* renamed from: a0 */
    public final InterfaceC0702j1 f57255a0;

    public AccessibilityManagerTouchExplorationStateChangeListenerC0703j2(InterfaceC0702j1 interfaceC0702j1) {
        this.f57255a0 = interfaceC0702j1;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof AccessibilityManagerTouchExplorationStateChangeListenerC0703j2) {
            return this.f57255a0.equals(((AccessibilityManagerTouchExplorationStateChangeListenerC0703j2) obj).f57255a0);
        }
        return false;
    }

    public final int hashCode() {
        return this.f57255a0.hashCode();
    }

    @Override // android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener
    public final void onTouchExplorationStateChanged(boolean z) {
        this.f57255a0.onTouchExplorationStateChanged(z);
    }
}
