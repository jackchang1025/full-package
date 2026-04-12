package p000;

import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: v8 */
/* loaded from: classes.dex */
public final class C1328v8 extends kj1 {

    /* renamed from: a6 */
    public final /* synthetic */ int f60597a6;

    /* renamed from: a7 */
    public final /* synthetic */ Object f60598a7;

    public /* synthetic */ C1328v8(int i, Object obj) {
        this.f60597a6 = i;
        this.f60598a7 = obj;
    }

    @Override // p000.oc1
    /* renamed from: a0 */
    public final void mo212658a0() {
        int i = this.f60597a6;
        Object obj = this.f60598a7;
        switch (i) {
            case 0:
                LayoutInflaterFactory2C1367w8 layoutInflaterFactory2C1367w8 = ((RunnableC1326v6) obj).f60588a1;
                layoutInflaterFactory2C1367w8.f60820c1.setAlpha(1.0f);
                layoutInflaterFactory2C1367w8.f60823c4.m213970a3(null);
                layoutInflaterFactory2C1367w8.f60823c4 = null;
                break;
            case 1:
                LayoutInflaterFactory2C1367w8 layoutInflaterFactory2C1367w82 = (LayoutInflaterFactory2C1367w8) obj;
                layoutInflaterFactory2C1367w82.f60820c1.setAlpha(1.0f);
                layoutInflaterFactory2C1367w82.f60823c4.m213970a3(null);
                layoutInflaterFactory2C1367w82.f60823c4 = null;
                break;
            default:
                LayoutInflaterFactory2C1367w8 layoutInflaterFactory2C1367w83 = (LayoutInflaterFactory2C1367w8) ((eo0) obj).f56089a2;
                layoutInflaterFactory2C1367w83.f60820c1.setVisibility(8);
                PopupWindow popupWindow = layoutInflaterFactory2C1367w83.f60821c2;
                if (popupWindow != null) {
                    popupWindow.dismiss();
                } else if (layoutInflaterFactory2C1367w83.f60820c1.getParent() instanceof View) {
                    View view = (View) layoutInflaterFactory2C1367w83.f60820c1.getParent();
                    WeakHashMap weakHashMap = xa1.f61054a0;
                    ja1.m213282a2(view);
                }
                layoutInflaterFactory2C1367w83.f60820c1.m209854a4();
                layoutInflaterFactory2C1367w83.f60823c4.m213970a3(null);
                layoutInflaterFactory2C1367w83.f60823c4 = null;
                ViewGroup viewGroup = layoutInflaterFactory2C1367w83.f60825c6;
                WeakHashMap weakHashMap2 = xa1.f61054a0;
                ja1.m213282a2(viewGroup);
                break;
        }
    }

    @Override // p000.kj1, p000.oc1
    /* renamed from: a2 */
    public void mo212660a2() {
        int i = this.f60597a6;
        Object obj = this.f60598a7;
        switch (i) {
            case 0:
                ((RunnableC1326v6) obj).f60588a1.f60820c1.setVisibility(0);
                break;
            case 1:
                LayoutInflaterFactory2C1367w8 layoutInflaterFactory2C1367w8 = (LayoutInflaterFactory2C1367w8) obj;
                layoutInflaterFactory2C1367w8.f60820c1.setVisibility(0);
                if (layoutInflaterFactory2C1367w8.f60820c1.getParent() instanceof View) {
                    View view = (View) layoutInflaterFactory2C1367w8.f60820c1.getParent();
                    WeakHashMap weakHashMap = xa1.f61054a0;
                    ja1.m213282a2(view);
                    break;
                }
                break;
        }
    }
}
