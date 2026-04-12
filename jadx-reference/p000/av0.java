package p000;

import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.google.android.material.search.SearchBar;
import com.google.android.material.search.SearchView;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final /* synthetic */ class av0 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f45646a0;

    /* renamed from: a1 */
    public final /* synthetic */ SearchView f45647a1;

    public /* synthetic */ av0(SearchView searchView, int i) {
        this.f45646a0 = i;
        this.f45647a1 = searchView;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ag1 ag1VarM215145a7;
        ag1 ag1VarM215145a72;
        switch (this.f45646a0) {
            case 0:
                SearchView searchView = this.f45647a1;
                EditText editText = searchView.f49738a9;
                if (editText.requestFocus()) {
                    editText.sendAccessibilityEvent(8);
                }
                if (searchView.f49751c2 && (ag1VarM215145a7 = xa1.m215145a7(editText)) != null) {
                    ag1VarM215145a7.f43655a0.mo213552f3();
                    break;
                } else {
                    ((InputMethodManager) AbstractC0871mq.m214016a1(editText.getContext(), InputMethodManager.class)).showSoftInput(editText, 1);
                    break;
                }
                break;
            case 1:
                SearchView searchView2 = this.f45647a1;
                EditText editText2 = searchView2.f49738a9;
                editText2.clearFocus();
                SearchBar searchBar = searchView2.f49746b7;
                if (searchBar != null) {
                    searchBar.requestFocus();
                }
                if (searchView2.f49751c2 && (ag1VarM215145a72 = xa1.m215145a7(editText2)) != null) {
                    ag1VarM215145a72.f43655a0.mo213549c2();
                    break;
                } else {
                    InputMethodManager inputMethodManager = (InputMethodManager) AbstractC0871mq.m214016a1(editText2.getContext(), InputMethodManager.class);
                    if (inputMethodManager != null) {
                        inputMethodManager.hideSoftInputFromWindow(editText2.getWindowToken(), 0);
                        break;
                    }
                }
                break;
            default:
                this.f45647a1.m211089a3();
                break;
        }
    }
}
