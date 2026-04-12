package p000;

import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import java.util.Objects;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class xk0 {
    /* renamed from: a0 */
    public static OnBackInvokedCallback m215193a0(Runnable runnable) {
        Objects.requireNonNull(runnable);
        return new C1359w0(1, runnable);
    }

    /* renamed from: a1 */
    public static void m215194a1(Object obj, int i, Object obj2) {
        ((OnBackInvokedDispatcher) obj).registerOnBackInvokedCallback(i, (OnBackInvokedCallback) obj2);
    }

    /* renamed from: a2 */
    public static void m215195a2(Object obj, Object obj2) {
        ((OnBackInvokedDispatcher) obj).unregisterOnBackInvokedCallback((OnBackInvokedCallback) obj2);
    }
}
