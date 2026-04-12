package p000;

import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import androidx.recyclerview.widget.RecyclerView;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public class er0 extends C0608i4 {

    /* renamed from: a3 */
    public final RecyclerView f56101a3;

    /* renamed from: a4 */
    public final C1293ua f56102a4;

    public er0(RecyclerView recyclerView) {
        this.f56101a3 = recyclerView;
        C1293ua c1293ua = this.f56102a4;
        if (c1293ua != null) {
            this.f56102a4 = c1293ua;
        } else {
            this.f56102a4 = new C1293ua(this);
        }
    }

    @Override // p000.C0608i4
    /* renamed from: a2 */
    public final void mo212721a2(View view, AccessibilityEvent accessibilityEvent) {
        super.mo212721a2(view, accessibilityEvent);
        if (!(view instanceof RecyclerView) || this.f56101a3.m210375d7()) {
            return;
        }
        RecyclerView recyclerView = (RecyclerView) view;
        if (recyclerView.getLayoutManager() != null) {
            recyclerView.getLayoutManager().mo210307e1(accessibilityEvent);
        }
    }

    @Override // p000.C0608i4
    /* renamed from: a3 */
    public void mo210912a3(View view, C0748k7 c0748k7) {
        this.f56792a0.onInitializeAccessibilityNodeInfo(view, c0748k7.f57472a0);
        RecyclerView recyclerView = this.f56101a3;
        if (recyclerView.m210375d7() || recyclerView.getLayoutManager() == null) {
            return;
        }
        pq0 layoutManager = recyclerView.getLayoutManager();
        RecyclerView recyclerView2 = layoutManager.f59319a1;
        layoutManager.mo212583e2(recyclerView2.f45255a1, recyclerView2.f45306f2, c0748k7);
    }

    @Override // p000.C0608i4
    /* renamed from: a6 */
    public final boolean mo211166a6(View view, int i, Bundle bundle) {
        if (super.mo211166a6(view, i, bundle)) {
            return true;
        }
        RecyclerView recyclerView = this.f56101a3;
        if (recyclerView.m210375d7() || recyclerView.getLayoutManager() == null) {
            return false;
        }
        pq0 layoutManager = recyclerView.getLayoutManager();
        RecyclerView recyclerView2 = layoutManager.f59319a1;
        return layoutManager.mo212584f5(recyclerView2.f45255a1, recyclerView2.f45306f2, i, bundle);
    }
}
