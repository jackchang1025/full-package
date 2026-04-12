package p000;

import android.view.View;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import java.util.Objects;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class z61 {
    /* renamed from: a0 */
    public static OnBackInvokedDispatcher m215370a0(View view) {
        return view.findOnBackInvokedDispatcher();
    }

    /* renamed from: a1 */
    public static OnBackInvokedCallback m215371a1(Runnable runnable) {
        Objects.requireNonNull(runnable);
        return new C1359w0(1, runnable);
    }

    /* renamed from: a2 */
    public static void m215372a2(Object obj, Object obj2) {
        ((OnBackInvokedDispatcher) obj).registerOnBackInvokedCallback(1000000, (OnBackInvokedCallback) obj2);
    }

    /* renamed from: a3 */
    public static void m215373a3(Object obj, Object obj2) {
        ((OnBackInvokedDispatcher) obj).unregisterOnBackInvokedCallback((OnBackInvokedCallback) obj2);
    }
}
