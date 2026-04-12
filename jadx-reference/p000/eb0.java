package p000;

import android.view.View;
import android.widget.AdapterView;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.appcompat.widget.SearchView;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class eb0 implements AdapterView.OnItemSelectedListener {

    /* renamed from: a0 */
    public final /* synthetic */ int f55949a0;

    /* renamed from: a1 */
    public final /* synthetic */ Object f55950a1;

    public /* synthetic */ eb0(int i, Object obj) {
        this.f55949a0 = i;
        this.f55950a1 = obj;
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public final void onItemSelected(AdapterView adapterView, View view, int i, long j) {
        C1304ul c1304ul;
        switch (this.f55949a0) {
            case 0:
                if (i != -1 && (c1304ul = ((ListPopupWindow) this.f55950a1).f43974a2) != null) {
                    c1304ul.setListSelectionHidden(false);
                    break;
                }
                break;
            default:
                ((SearchView) this.f55950a1).m209902b4(i);
                break;
        }
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public final void onNothingSelected(AdapterView adapterView) {
        int i = this.f55949a0;
    }

    /* renamed from: a0 */
    private final void m212664a0(AdapterView adapterView) {
    }

    /* renamed from: a1 */
    private final void m212665a1(AdapterView adapterView) {
    }
}
