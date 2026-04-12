package p000;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.google.android.material.bottomappbar.BottomAppBar;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ej */
/* loaded from: classes2.dex */
public final /* synthetic */ class RunnableC0458ej implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f56066a0;

    /* renamed from: a1 */
    public final /* synthetic */ View f56067a1;

    public /* synthetic */ RunnableC0458ej(View view, int i) {
        this.f56066a0 = i;
        this.f56067a1 = view;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.f56066a0;
        View view = this.f56067a1;
        switch (i) {
            case 0:
                int i2 = BottomAppBar.f49145g6;
                view.requestLayout();
                break;
            default:
                ((InputMethodManager) view.getContext().getSystemService("input_method")).showSoftInput(view, 0);
                break;
        }
    }
}
