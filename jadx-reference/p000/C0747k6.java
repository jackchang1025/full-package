package p000;

import android.os.Handler;
import android.os.Looper;
import android.view.accessibility.AccessibilityNodeInfo;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: k6 */
/* loaded from: classes.dex */
public final class C0747k6 {

    /* renamed from: a1 */
    public static C0747k6 f57458a1;

    /* renamed from: a0 */
    public final Object f57459a0;

    public /* synthetic */ C0747k6(Object obj) {
        this.f57459a0 = obj;
    }

    /* renamed from: a0 */
    public static C0747k6 m213450a0(int i, int i2, int i3) {
        return new C0747k6(AccessibilityNodeInfo.CollectionInfo.obtain(i, i2, false, i3));
    }

    /* renamed from: a1 */
    public static C0747k6 m213451a1(int i, int i2, int i3, int i4, boolean z, boolean z2) {
        return new C0747k6(AccessibilityNodeInfo.CollectionItemInfo.obtain(i, i2, i3, i4, z, z2));
    }

    public C0747k6() {
        this.f57459a0 = new Object();
        new Handler(Looper.getMainLooper(), new c11(this));
    }
}
