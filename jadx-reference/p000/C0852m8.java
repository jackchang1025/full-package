package p000;

import android.view.View;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.widget.ActivityChooserView;
import androidx.appcompat.widget.C0041a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: m8 */
/* loaded from: classes.dex */
public final class C0852m8 extends AbstractViewOnTouchListenerC1358w {

    /* renamed from: a9 */
    public final /* synthetic */ int f58289a9;

    /* renamed from: b0 */
    public final /* synthetic */ View f58290b0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C0852m8(View view, View view2, int i) {
        super(view2);
        this.f58289a9 = i;
        this.f58290b0 = view;
    }

    @Override // p000.AbstractViewOnTouchListenerC1358w
    /* renamed from: a1 */
    public final p01 mo213948a1() {
        C0882n0 c0882n0;
        switch (this.f58289a9) {
            case 0:
                AbstractC0853m9 abstractC0853m9 = ((ActionMenuItemView) this.f58290b0).f43781b2;
                if (abstractC0853m9 == null || (c0882n0 = ((C0883n1) abstractC0853m9).f58418a0.f44158c0) == null) {
                    return null;
                }
                return c0882n0.m214074a0();
            case 1:
                C0882n0 c0882n02 = ((C0885n3) this.f58290b0).f58437a3.f44157b9;
                if (c0882n02 == null) {
                    return null;
                }
                return c0882n02.m214074a0();
            default:
                return ((ActivityChooserView) this.f58290b0).getListPopupWindow();
        }
    }

    @Override // p000.AbstractViewOnTouchListenerC1358w
    /* renamed from: a2 */
    public final boolean mo213949a2() {
        p01 p01VarMo213948a1;
        switch (this.f58289a9) {
            case 0:
                ActionMenuItemView actionMenuItemView = (ActionMenuItemView) this.f58290b0;
                af0 af0Var = actionMenuItemView.f43779b0;
                return af0Var != null && af0Var.mo209799a0(actionMenuItemView.f43776a7) && (p01VarMo213948a1 = mo213948a1()) != null && p01VarMo213948a1.mo209886a1();
            case 1:
                ((C0885n3) this.f58290b0).f58437a3.m209942b3();
                return true;
            default:
                ActivityChooserView activityChooserView = (ActivityChooserView) this.f58290b0;
                if (activityChooserView.m209876a1() || !activityChooserView.f43885b0) {
                    return true;
                }
                activityChooserView.f43875a0.getClass();
                throw new IllegalStateException("No data model. Did you call #setDataModel?");
        }
    }

    @Override // p000.AbstractViewOnTouchListenerC1358w
    /* renamed from: a3 */
    public boolean mo213950a3() {
        switch (this.f58289a9) {
            case 1:
                C0041a1 c0041a1 = ((C0885n3) this.f58290b0).f58437a3;
                if (c0041a1.f44159c1 != null) {
                    return false;
                }
                c0041a1.m209939a3();
                return true;
            case 2:
                ((ActivityChooserView) this.f58290b0).m209875a0();
                return true;
            default:
                return super.mo213950a3();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0852m8(ActionMenuItemView actionMenuItemView) {
        super(actionMenuItemView);
        this.f58289a9 = 0;
        this.f58290b0 = actionMenuItemView;
    }
}
