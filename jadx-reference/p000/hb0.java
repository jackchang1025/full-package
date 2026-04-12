package p000;

import androidx.appcompat.widget.ListPopupWindow;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class hb0 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f56640a0;

    /* renamed from: a1 */
    public final /* synthetic */ ListPopupWindow f56641a1;

    public /* synthetic */ hb0(ListPopupWindow listPopupWindow, int i) {
        this.f56640a0 = i;
        this.f56641a1 = listPopupWindow;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.f56640a0;
        ListPopupWindow listPopupWindow = this.f56641a1;
        switch (i) {
            case 0:
                C1304ul c1304ul = listPopupWindow.f43974a2;
                if (c1304ul != null) {
                    c1304ul.setListSelectionHidden(true);
                    c1304ul.requestLayout();
                    break;
                }
                break;
            default:
                C1304ul c1304ul2 = listPopupWindow.f43974a2;
                if (c1304ul2 != null) {
                    WeakHashMap weakHashMap = xa1.f61054a0;
                    if (ia1.m213141a1(c1304ul2) && listPopupWindow.f43974a2.getCount() > listPopupWindow.f43974a2.getChildCount() && listPopupWindow.f43974a2.getChildCount() <= listPopupWindow.f43984b2) {
                        listPopupWindow.f43997c5.setInputMethodMode(2);
                        listPopupWindow.mo209888a3();
                        break;
                    }
                }
                break;
        }
    }
}
