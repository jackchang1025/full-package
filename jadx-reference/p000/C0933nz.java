package p000;

import android.database.DataSetObserver;
import androidx.appcompat.widget.ListPopupWindow;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: nz */
/* loaded from: classes.dex */
public final class C0933nz extends DataSetObserver {

    /* renamed from: a0 */
    public final /* synthetic */ int f58703a0;

    /* renamed from: a1 */
    public final /* synthetic */ Object f58704a1;

    public /* synthetic */ C0933nz(int i, Object obj) {
        this.f58703a0 = i;
        this.f58704a1 = obj;
    }

    @Override // android.database.DataSetObserver
    public final void onChanged() {
        switch (this.f58703a0) {
            case 0:
                x21 x21Var = (x21) this.f58704a1;
                x21Var.f58764a0 = true;
                x21Var.notifyDataSetChanged();
                break;
            default:
                ListPopupWindow listPopupWindow = (ListPopupWindow) this.f58704a1;
                if (listPopupWindow.f43997c5.isShowing()) {
                    listPopupWindow.mo209888a3();
                    break;
                }
                break;
        }
    }

    @Override // android.database.DataSetObserver
    public final void onInvalidated() {
        switch (this.f58703a0) {
            case 0:
                x21 x21Var = (x21) this.f58704a1;
                x21Var.f58764a0 = false;
                x21Var.notifyDataSetInvalidated();
                break;
            default:
                ((ListPopupWindow) this.f58704a1).dismiss();
                break;
        }
    }
}
