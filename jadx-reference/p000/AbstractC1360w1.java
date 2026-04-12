package p000;

import android.app.Activity;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import java.util.Objects;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: w1 */
/* loaded from: classes.dex */
public abstract class AbstractC1360w1 {
    /* renamed from: a0 */
    public static OnBackInvokedDispatcher m214974a0(Activity activity) {
        return activity.getOnBackInvokedDispatcher();
    }

    /* renamed from: a1 */
    public static OnBackInvokedCallback m214975a1(Object obj, LayoutInflaterFactory2C1367w8 layoutInflaterFactory2C1367w8) {
        Objects.requireNonNull(layoutInflaterFactory2C1367w8);
        C1359w0 c1359w0 = new C1359w0(0, layoutInflaterFactory2C1367w8);
        AbstractC0741k1.m213409a8(obj).registerOnBackInvokedCallback(1000000, c1359w0);
        return c1359w0;
    }

    /* renamed from: a2 */
    public static void m214976a2(Object obj, Object obj2) {
        AbstractC0741k1.m213409a8(obj).unregisterOnBackInvokedCallback(AbstractC0741k1.m213406a5(obj2));
    }
}
