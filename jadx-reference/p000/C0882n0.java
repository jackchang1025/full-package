package p000;

import android.content.Context;
import android.view.View;
import androidx.appcompat.R$attr;
import androidx.appcompat.widget.C0041a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: n0 */
/* loaded from: classes.dex */
public final class C0882n0 extends nf0 {

    /* renamed from: b1 */
    public final /* synthetic */ int f58411b1 = 1;

    /* renamed from: b2 */
    public final /* synthetic */ C0041a1 f58412b2;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0882n0(C0041a1 c0041a1, Context context, bf0 bf0Var, View view) {
        super(context, bf0Var, view, true, R$attr.actionOverflowMenuStyle, 0);
        this.f58412b2 = c0041a1;
        this.f58621a5 = 8388613;
        tg0 tg0Var = c0041a1.f44161c3;
        this.f58623a7 = tg0Var;
        kf0 kf0Var = this.f58624a8;
        if (kf0Var != null) {
            kf0Var.mo209940a5(tg0Var);
        }
    }

    @Override // p000.nf0
    /* renamed from: a2 */
    public final void mo214025a2() {
        switch (this.f58411b1) {
            case 0:
                C0041a1 c0041a1 = this.f58412b2;
                c0041a1.f44158c0 = null;
                c0041a1.f44162c4 = 0;
                super.mo214025a2();
                break;
            default:
                C0041a1 c0041a12 = this.f58412b2;
                bf0 bf0Var = c0041a12.f44140a2;
                if (bf0Var != null) {
                    bf0Var.m210690a2(true);
                }
                c0041a12.f44157b9 = null;
                super.mo214025a2();
                break;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0882n0(C0041a1 c0041a1, Context context, r21 r21Var, View view) {
        super(context, r21Var, view, false, R$attr.actionOverflowMenuStyle, 0);
        this.f58412b2 = c0041a1;
        if ((r21Var.f59606c6.f56228c3 & 32) != 32) {
            View view2 = c0041a1.f44147a9;
            this.f58620a4 = view2 == null ? (View) c0041a1.f44145a7 : view2;
        }
        tg0 tg0Var = c0041a1.f44161c3;
        this.f58623a7 = tg0Var;
        kf0 kf0Var = this.f58624a8;
        if (kf0Var != null) {
            kf0Var.mo209940a5(tg0Var);
        }
    }
}
