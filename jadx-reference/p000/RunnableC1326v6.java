package p000;

import android.view.ViewGroup;
import java.util.WeakHashMap;
import okio.internal.Buffer;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: v6 */
/* loaded from: classes.dex */
public final class RunnableC1326v6 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f60587a0;

    /* renamed from: a1 */
    public final /* synthetic */ LayoutInflaterFactory2C1367w8 f60588a1;

    public /* synthetic */ RunnableC1326v6(LayoutInflaterFactory2C1367w8 layoutInflaterFactory2C1367w8, int i) {
        this.f60587a0 = i;
        this.f60588a1 = layoutInflaterFactory2C1367w8;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ViewGroup viewGroup;
        int i = this.f60587a0;
        LayoutInflaterFactory2C1367w8 layoutInflaterFactory2C1367w8 = this.f60588a1;
        switch (i) {
            case 0:
                if ((layoutInflaterFactory2C1367w8.f60850f1 & 1) != 0) {
                    layoutInflaterFactory2C1367w8.m215025c0(0);
                }
                if ((layoutInflaterFactory2C1367w8.f60850f1 & Buffer.SEGMENTING_THRESHOLD) != 0) {
                    layoutInflaterFactory2C1367w8.m215025c0(108);
                }
                layoutInflaterFactory2C1367w8.f60849f0 = false;
                layoutInflaterFactory2C1367w8.f60850f1 = 0;
                break;
            default:
                layoutInflaterFactory2C1367w8.f60821c2.showAtLocation(layoutInflaterFactory2C1367w8.f60820c1, 55, 0, 0);
                mc1 mc1Var = layoutInflaterFactory2C1367w8.f60823c4;
                if (mc1Var != null) {
                    mc1Var.m213968a1();
                }
                if (layoutInflaterFactory2C1367w8.f60824c5 && (viewGroup = layoutInflaterFactory2C1367w8.f60825c6) != null) {
                    WeakHashMap weakHashMap = xa1.f61054a0;
                    if (ia1.m213142a2(viewGroup)) {
                        layoutInflaterFactory2C1367w8.f60820c1.setAlpha(0.0f);
                        mc1 mc1VarM215138a0 = xa1.m215138a0(layoutInflaterFactory2C1367w8.f60820c1);
                        mc1VarM215138a0.m213967a0(1.0f);
                        layoutInflaterFactory2C1367w8.f60823c4 = mc1VarM215138a0;
                        mc1VarM215138a0.m213970a3(new C1328v8(0, this));
                        break;
                    }
                }
                layoutInflaterFactory2C1367w8.f60820c1.setAlpha(1.0f);
                layoutInflaterFactory2C1367w8.f60820c1.setVisibility(0);
                break;
        }
    }
}
