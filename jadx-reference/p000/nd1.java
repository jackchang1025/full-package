package p000;

import android.animation.Animator;
import android.view.View;
import android.view.ViewGroup;
import androidx.transition.R$id;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class nd1 extends t71 {

    /* renamed from: a0 */
    public final /* synthetic */ ViewGroup f58506a0;

    /* renamed from: a1 */
    public final /* synthetic */ View f58507a1;

    /* renamed from: a2 */
    public final /* synthetic */ View f58508a2;

    /* renamed from: a3 */
    public final /* synthetic */ C1482yj f58509a3;

    public nd1(C1482yj c1482yj, ViewGroup viewGroup, View view, View view2) {
        this.f58509a3 = c1482yj;
        this.f58506a0 = viewGroup;
        this.f58507a1 = view;
        this.f58508a2 = view2;
    }

    @Override // p000.t71, p000.r71
    /* renamed from: a1 */
    public final void mo212983a1() {
        this.f58506a0.getOverlay().remove(this.f58507a1);
    }

    @Override // p000.t71, p000.r71
    /* renamed from: a2 */
    public final void mo212984a2() {
        View view = this.f58507a1;
        if (view.getParent() == null) {
            this.f58506a0.getOverlay().add(view);
            return;
        }
        C1482yj c1482yj = this.f58509a3;
        ArrayList arrayList = c1482yj.f59911b2;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            ((Animator) arrayList.get(size)).cancel();
        }
        ArrayList arrayList2 = c1482yj.f59915b6;
        if (arrayList2 == null || arrayList2.size() <= 0) {
            return;
        }
        ArrayList arrayList3 = (ArrayList) c1482yj.f59915b6.clone();
        int size2 = arrayList3.size();
        for (int i = 0; i < size2; i++) {
            ((r71) arrayList3.get(i)).mo212986a4();
        }
    }

    @Override // p000.r71
    /* renamed from: a3 */
    public final void mo212985a3(s71 s71Var) {
        this.f58508a2.setTag(R$id.save_overlay_view, null);
        this.f58506a0.getOverlay().remove(this.f58507a1);
        s71Var.m214581c0(this);
    }
}
