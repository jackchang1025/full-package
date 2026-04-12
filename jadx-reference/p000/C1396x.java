package p000;

import android.view.View;
import androidx.fragment.app.AbstractComponentCallbacksC0069a5;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: x */
/* loaded from: classes.dex */
public final class C1396x extends t60 {

    /* renamed from: c6 */
    public final /* synthetic */ AbstractComponentCallbacksC0069a5 f60978c6;

    public C1396x(AbstractComponentCallbacksC0069a5 abstractComponentCallbacksC0069a5) {
        this.f60978c6 = abstractComponentCallbacksC0069a5;
    }

    @Override // p000.t60
    /* renamed from: d9 */
    public final View mo214668d9(int i) {
        AbstractComponentCallbacksC0069a5 abstractComponentCallbacksC0069a5 = this.f60978c6;
        View view = abstractComponentCallbacksC0069a5.f45107d0;
        if (view != null) {
            return view.findViewById(i);
        }
        throw new IllegalStateException("Fragment " + abstractComponentCallbacksC0069a5 + " does not have a view");
    }

    @Override // p000.t60
    /* renamed from: e0 */
    public final boolean mo214669e0() {
        return this.f60978c6.f45107d0 != null;
    }
}
