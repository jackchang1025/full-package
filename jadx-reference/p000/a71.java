package p000;

import android.content.Context;
import android.os.Parcelable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class a71 implements tf0 {

    /* renamed from: a0 */
    public bf0 f43a0;

    /* renamed from: a1 */
    public ff0 f44a1;

    /* renamed from: a2 */
    public final /* synthetic */ Toolbar f45a2;

    public a71(Toolbar toolbar) {
        this.f45a2 = toolbar;
    }

    @Override // p000.tf0
    /* renamed from: a2 */
    public final boolean mo62a2(ff0 ff0Var) {
        Toolbar toolbar = this.f45a2;
        KeyEvent.Callback callback = toolbar.f44097a8;
        if (callback instanceof InterfaceC0699iz) {
            ((InterfaceC0699iz) callback).onActionViewCollapsed();
        }
        toolbar.removeView(toolbar.f44097a8);
        toolbar.removeView(toolbar.f44096a7);
        toolbar.f44097a8 = null;
        ArrayList arrayList = toolbar.f44119d0;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            toolbar.addView((View) arrayList.get(size));
        }
        arrayList.clear();
        this.f44a1 = null;
        toolbar.requestLayout();
        ff0Var.f56233c8 = false;
        ff0Var.f56218b3.mo210703b5(false);
        toolbar.m209936c0();
        return true;
    }

    @Override // p000.tf0
    /* renamed from: a6 */
    public final boolean mo64a6(ff0 ff0Var) {
        Toolbar toolbar = this.f45a2;
        toolbar.m209922a2();
        ViewParent parent = toolbar.f44096a7.getParent();
        if (parent != toolbar) {
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(toolbar.f44096a7);
            }
            toolbar.addView(toolbar.f44096a7);
        }
        View actionView = ff0Var.getActionView();
        toolbar.f44097a8 = actionView;
        this.f44a1 = ff0Var;
        ViewParent parent2 = actionView.getParent();
        if (parent2 != toolbar) {
            if (parent2 instanceof ViewGroup) {
                ((ViewGroup) parent2).removeView(toolbar.f44097a8);
            }
            b71 b71VarM209916a7 = Toolbar.m209916a7();
            b71VarM209916a7.f45727a0 = (toolbar.f44102b3 & 112) | 8388611;
            b71VarM209916a7.f45728a1 = 2;
            toolbar.f44097a8.setLayoutParams(b71VarM209916a7);
            toolbar.addView(toolbar.f44097a8);
        }
        for (int childCount = toolbar.getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = toolbar.getChildAt(childCount);
            if (((b71) childAt.getLayoutParams()).f45728a1 != 2 && childAt != toolbar.f44089a0) {
                toolbar.removeViewAt(childCount);
                toolbar.f44119d0.add(childAt);
            }
        }
        toolbar.requestLayout();
        ff0Var.f56233c8 = true;
        ff0Var.f56218b3.mo210703b5(false);
        KeyEvent.Callback callback = toolbar.f44097a8;
        if (callback instanceof InterfaceC0699iz) {
            ((InterfaceC0699iz) callback).onActionViewExpanded();
        }
        toolbar.m209936c0();
        return true;
    }

    @Override // p000.tf0
    /* renamed from: a8 */
    public final void mo65a8(Context context, bf0 bf0Var) {
        ff0 ff0Var;
        bf0 bf0Var2 = this.f43a0;
        if (bf0Var2 != null && (ff0Var = this.f44a1) != null) {
            bf0Var2.mo210691a3(ff0Var);
        }
        this.f43a0 = bf0Var;
    }

    @Override // p000.tf0
    /* renamed from: a9 */
    public final void mo66a9(boolean z) {
        if (this.f44a1 != null) {
            bf0 bf0Var = this.f43a0;
            if (bf0Var != null) {
                int size = bf0Var.f45871a5.size();
                for (int i = 0; i < size; i++) {
                    if (this.f43a0.getItem(i) == this.f44a1) {
                        return;
                    }
                }
            }
            mo62a2(this.f44a1);
        }
    }

    @Override // p000.tf0
    /* renamed from: b0 */
    public final boolean mo67b0(r21 r21Var) {
        return false;
    }

    @Override // p000.tf0
    /* renamed from: b1 */
    public final boolean mo68b1() {
        return false;
    }

    @Override // p000.tf0
    /* renamed from: b2 */
    public final Parcelable mo69b2() {
        return null;
    }

    @Override // p000.tf0
    public final int getId() {
        return 0;
    }

    @Override // p000.tf0
    /* renamed from: a4 */
    public final void mo63a4(Parcelable parcelable) {
    }

    @Override // p000.tf0
    /* renamed from: a0 */
    public final void mo61a0(bf0 bf0Var, boolean z) {
    }
}
