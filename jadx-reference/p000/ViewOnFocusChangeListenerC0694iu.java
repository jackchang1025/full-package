package p000;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: iu */
/* loaded from: classes2.dex */
public final /* synthetic */ class ViewOnFocusChangeListenerC0694iu implements View.OnFocusChangeListener {

    /* renamed from: a0 */
    public final /* synthetic */ int f57224a0;

    /* renamed from: a1 */
    public final /* synthetic */ Object f57225a1;

    public /* synthetic */ ViewOnFocusChangeListenerC0694iu(int i, Object obj) {
        this.f57224a0 = i;
        this.f57225a1 = obj;
    }

    @Override // android.view.View.OnFocusChangeListener
    public final void onFocusChange(View view, boolean z) {
        switch (this.f57224a0) {
            case 0:
                C0697ix c0697ix = (C0697ix) this.f57225a1;
                c0697ix.m213199b8(c0697ix.m213200b9());
                break;
            case 1:
                for (EditText editText : (EditText[]) this.f57225a1) {
                    if (editText.hasFocus()) {
                        break;
                    }
                }
                ag1 ag1VarM215145a7 = xa1.m215145a7(view);
                if (ag1VarM215145a7 != null) {
                    ag1VarM215145a7.f43655a0.mo213549c2();
                    break;
                } else {
                    InputMethodManager inputMethodManager = (InputMethodManager) AbstractC0871mq.m214016a1(view.getContext(), InputMethodManager.class);
                    if (inputMethodManager != null) {
                        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        break;
                    }
                }
                break;
            default:
                C1309uq c1309uq = (C1309uq) this.f57225a1;
                c1309uq.f60497b1 = z;
                c1309uq.m215176b5();
                if (!z) {
                    c1309uq.m214858b8(false);
                    c1309uq.f60498b2 = false;
                    break;
                }
                break;
        }
    }
}
