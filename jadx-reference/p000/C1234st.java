package p000;

import android.app.Dialog;
import android.view.View;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: st */
/* loaded from: classes.dex */
public final class C1234st extends t60 {

    /* renamed from: c6 */
    public final /* synthetic */ C1396x f60083c6;

    /* renamed from: c7 */
    public final /* synthetic */ DialogInterfaceOnCancelListenerC1235su f60084c7;

    public C1234st(DialogInterfaceOnCancelListenerC1235su dialogInterfaceOnCancelListenerC1235su, C1396x c1396x) {
        this.f60084c7 = dialogInterfaceOnCancelListenerC1235su;
        this.f60083c6 = c1396x;
    }

    @Override // p000.t60
    /* renamed from: d9 */
    public final View mo214668d9(int i) {
        C1396x c1396x = this.f60083c6;
        if (c1396x.mo214669e0()) {
            return c1396x.mo214668d9(i);
        }
        Dialog dialog = this.f60084c7.f60096f5;
        if (dialog != null) {
            return dialog.findViewById(i);
        }
        return null;
    }

    @Override // p000.t60
    /* renamed from: e0 */
    public final boolean mo214669e0() {
        return this.f60083c6.mo214669e0() || this.f60084c7.f60099f8;
    }
}
