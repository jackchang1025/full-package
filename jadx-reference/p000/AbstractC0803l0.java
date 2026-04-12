package p000;

import android.view.accessibility.AccessibilityRecord;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: l0 */
/* loaded from: classes.dex */
public abstract class AbstractC0803l0 {
    /* renamed from: a0 */
    public static int m213768a0(AccessibilityRecord accessibilityRecord) {
        return accessibilityRecord.getMaxScrollX();
    }

    /* renamed from: a1 */
    public static int m213769a1(AccessibilityRecord accessibilityRecord) {
        return accessibilityRecord.getMaxScrollY();
    }

    /* renamed from: a2 */
    public static void m213770a2(AccessibilityRecord accessibilityRecord, int i) {
        accessibilityRecord.setMaxScrollX(i);
    }

    /* renamed from: a3 */
    public static void m213771a3(AccessibilityRecord accessibilityRecord, int i) {
        accessibilityRecord.setMaxScrollY(i);
    }
}
