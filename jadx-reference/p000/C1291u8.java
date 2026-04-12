package p000;

import android.os.Bundle;
import android.view.LayoutInflater;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: u8 */
/* loaded from: classes.dex */
public final class C1291u8 implements zk0 {

    /* renamed from: a0 */
    public final /* synthetic */ int f60339a0;

    /* renamed from: a1 */
    public final /* synthetic */ FragmentActivity f60340a1;

    public /* synthetic */ C1291u8(FragmentActivity fragmentActivity, int i) {
        this.f60339a0 = i;
        this.f60340a1 = fragmentActivity;
    }

    @Override // p000.zk0
    /* renamed from: a0 */
    public final void mo213357a0() {
        switch (this.f60339a0) {
            case 0:
                AppCompatActivity appCompatActivity = (AppCompatActivity) this.f60340a1;
                AbstractC1325v5 abstractC1325v5M209838b1 = appCompatActivity.m209838b1();
                LayoutInflaterFactory2C1367w8 layoutInflaterFactory2C1367w8 = (LayoutInflaterFactory2C1367w8) abstractC1325v5M209838b1;
                LayoutInflater layoutInflaterFrom = LayoutInflater.from(layoutInflaterFactory2C1367w8.f60809b0);
                if (layoutInflaterFrom.getFactory() == null) {
                    layoutInflaterFrom.setFactory2(layoutInflaterFactory2C1367w8);
                } else {
                    layoutInflaterFrom.getFactory2();
                }
                appCompatActivity.f43735a4.f61178a1.m214951a0("androidx:appcompat");
                abstractC1325v5M209838b1.mo214899a2();
                break;
            default:
                FragmentActivity fragmentActivity = this.f60340a1;
                tg0 tg0Var = fragmentActivity.f45017b7;
                C1499z c1499z = (C1499z) tg0Var.f60218a1;
                c1499z.f61421c9.m210163a1(c1499z, c1499z, null);
                Bundle bundleM214951a0 = fragmentActivity.f43735a4.f61178a1.m214951a0("android:support:fragments");
                if (bundleM214951a0 != null) {
                    ((C1499z) tg0Var.f60218a1).f61421c9.m210195d7(bundleM214951a0.getParcelable("android:support:fragments"));
                    break;
                }
                break;
        }
    }
}
