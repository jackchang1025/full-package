package p000;

import android.window.OnBackInvokedCallback;
import java.lang.reflect.InvocationTargetException;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: w0 */
/* loaded from: classes.dex */
public final /* synthetic */ class C1359w0 implements OnBackInvokedCallback {

    /* renamed from: a0 */
    public final /* synthetic */ int f60736a0;

    /* renamed from: a1 */
    public final /* synthetic */ Object f60737a1;

    public /* synthetic */ C1359w0(int i, Object obj) {
        this.f60736a0 = i;
        this.f60737a1 = obj;
    }

    @Override // android.window.OnBackInvokedCallback
    public final void onBackInvoked() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        switch (this.f60736a0) {
            case 0:
                ((LayoutInflaterFactory2C1367w8) this.f60737a1).m215033c8();
                break;
            default:
                ((Runnable) this.f60737a1).run();
                break;
        }
    }
}
