package p000;

import android.os.Handler;
import android.view.View;
import android.view.Window;
import androidx.fragment.app.C0071a7;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.C0076a0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: z */
/* loaded from: classes.dex */
public final class C1499z extends t60 implements sb1, ka0, l00 {

    /* renamed from: c6 */
    public final FragmentActivity f61418c6;

    /* renamed from: c7 */
    public final FragmentActivity f61419c7;

    /* renamed from: c8 */
    public final Handler f61420c8;

    /* renamed from: c9 */
    public final C0071a7 f61421c9;

    /* renamed from: d0 */
    public final /* synthetic */ FragmentActivity f61422d0;

    public C1499z(FragmentActivity fragmentActivity) {
        this.f61422d0 = fragmentActivity;
        Handler handler = new Handler();
        this.f61421c9 = new C0071a7();
        this.f61418c6 = fragmentActivity;
        this.f61419c7 = fragmentActivity;
        this.f61420c8 = handler;
    }

    @Override // p000.sb1
    /* renamed from: a4 */
    public final rb1 mo209829a4() {
        return this.f61422d0.mo209829a4();
    }

    @Override // p000.ka0
    /* renamed from: a5 */
    public final C0076a0 mo209830a5() {
        return this.f61422d0.f45018b8;
    }

    @Override // p000.t60
    /* renamed from: d9 */
    public final View mo214668d9(int i) {
        return this.f61422d0.findViewById(i);
    }

    @Override // p000.t60
    /* renamed from: e0 */
    public final boolean mo214669e0() {
        Window window = this.f61422d0.getWindow();
        return (window == null || window.peekDecorView() == null) ? false : true;
    }

    @Override // p000.l00
    /* renamed from: a1 */
    public final void mo212730a1() {
    }
}
