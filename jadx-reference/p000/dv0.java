package p000;

import android.view.View;
import androidx.appcompat.widget.SearchView;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class dv0 implements View.OnFocusChangeListener {

    /* renamed from: a0 */
    public final /* synthetic */ SearchView f55887a0;

    public dv0(SearchView searchView) {
        this.f55887a0 = searchView;
    }

    @Override // android.view.View.OnFocusChangeListener
    public final void onFocusChange(View view, boolean z) {
        SearchView searchView = this.f55887a0;
        View.OnFocusChangeListener onFocusChangeListener = searchView.f44020d6;
        if (onFocusChangeListener != null) {
            onFocusChangeListener.onFocusChange(searchView, z);
        }
    }
}
