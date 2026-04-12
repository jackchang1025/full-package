package p000;

import android.view.KeyEvent;
import android.widget.TextView;
import androidx.appcompat.widget.SearchView;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class ev0 implements TextView.OnEditorActionListener {

    /* renamed from: a0 */
    public final /* synthetic */ SearchView f56112a0;

    public ev0(SearchView searchView) {
        this.f56112a0 = searchView;
    }

    @Override // android.widget.TextView.OnEditorActionListener
    public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        this.f56112a0.m209904b6();
        return true;
    }
}
