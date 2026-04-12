package p000;

import android.view.accessibility.AccessibilityManager;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: j0 */
/* loaded from: classes.dex */
public abstract class AbstractC0701j0 {
    /* renamed from: a0 */
    public static boolean m213201a0(AccessibilityManager accessibilityManager, InterfaceC0702j1 interfaceC0702j1) {
        return accessibilityManager.addTouchExplorationStateChangeListener(new AccessibilityManagerTouchExplorationStateChangeListenerC0703j2(interfaceC0702j1));
    }

    /* renamed from: a1 */
    public static boolean m213202a1(AccessibilityManager accessibilityManager, InterfaceC0702j1 interfaceC0702j1) {
        return accessibilityManager.removeTouchExplorationStateChangeListener(new AccessibilityManagerTouchExplorationStateChangeListenerC0703j2(interfaceC0702j1));
    }
}
