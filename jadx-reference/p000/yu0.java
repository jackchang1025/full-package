package p000;

import android.view.View;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.search.SearchView;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final /* synthetic */ class yu0 implements fd1, vk0 {

    /* renamed from: a0 */
    public final /* synthetic */ SearchView f61397a0;

    public /* synthetic */ yu0(SearchView searchView) {
        this.f61397a0 = searchView;
    }

    @Override // p000.vk0
    /* renamed from: a6 */
    public xf1 mo213324a6(View view, xf1 xf1Var) {
        SearchView.m211086a0(this.f61397a0, xf1Var);
        return xf1Var;
    }

    @Override // p000.fd1
    /* renamed from: b5 */
    public xf1 mo212585b5(View view, xf1 xf1Var, gd1 gd1Var) {
        MaterialToolbar materialToolbar = this.f61397a0.f49735a6;
        boolean zM214447e3 = AbstractC1117qo.m214447e3(materialToolbar);
        materialToolbar.setPadding(xf1Var.m215172a1() + (zM214447e3 ? gd1Var.f56447a2 : gd1Var.f56445a0), gd1Var.f56446a1, xf1Var.m215173a2() + (zM214447e3 ? gd1Var.f56445a0 : gd1Var.f56447a2), gd1Var.f56448a3);
        return xf1Var;
    }
}
