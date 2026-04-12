package p000;

import android.widget.AbsListView;
import androidx.appcompat.widget.ListPopupWindow;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class ib0 implements AbsListView.OnScrollListener {

    /* renamed from: a0 */
    public final /* synthetic */ ListPopupWindow f56852a0;

    public ib0(ListPopupWindow listPopupWindow) {
        this.f56852a0 = listPopupWindow;
    }

    @Override // android.widget.AbsListView.OnScrollListener
    public final void onScrollStateChanged(AbsListView absListView, int i) {
        ListPopupWindow listPopupWindow = this.f56852a0;
        hb0 hb0Var = listPopupWindow.f43989b7;
        C1402x5 c1402x5 = listPopupWindow.f43997c5;
        if (i != 1 || c1402x5.getInputMethodMode() == 2 || c1402x5.getContentView() == null) {
            return;
        }
        listPopupWindow.f43993c1.removeCallbacks(hb0Var);
        hb0Var.run();
    }

    @Override // android.widget.AbsListView.OnScrollListener
    public final void onScroll(AbsListView absListView, int i, int i2, int i3) {
    }
}
