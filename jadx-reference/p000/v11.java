package p000;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.appcompat.R$dimen;
import androidx.appcompat.R$layout;
import androidx.appcompat.widget.C0043a3;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class v11 extends kf0 implements PopupWindow.OnDismissListener, View.OnKeyListener {

    /* renamed from: c0 */
    public static final int f60547c0 = R$layout.abc_popup_menu_item_layout;

    /* renamed from: a1 */
    public final Context f60548a1;

    /* renamed from: a2 */
    public final bf0 f60549a2;

    /* renamed from: a3 */
    public final ye0 f60550a3;

    /* renamed from: a4 */
    public final boolean f60551a4;

    /* renamed from: a5 */
    public final int f60552a5;

    /* renamed from: a6 */
    public final int f60553a6;

    /* renamed from: a7 */
    public final C0043a3 f60554a7;

    /* renamed from: b0 */
    public PopupWindow.OnDismissListener f60557b0;

    /* renamed from: b1 */
    public View f60558b1;

    /* renamed from: b2 */
    public View f60559b2;

    /* renamed from: b3 */
    public sf0 f60560b3;

    /* renamed from: b4 */
    public ViewTreeObserver f60561b4;

    /* renamed from: b5 */
    public boolean f60562b5;

    /* renamed from: b6 */
    public boolean f60563b6;

    /* renamed from: b7 */
    public int f60564b7;

    /* renamed from: b9 */
    public boolean f60566b9;

    /* renamed from: a8 */
    public final ViewTreeObserverOnGlobalLayoutListenerC0937o2 f60555a8 = new ViewTreeObserverOnGlobalLayoutListenerC0937o2(5, this);

    /* renamed from: a9 */
    public final ViewOnAttachStateChangeListenerC0539gk f60556a9 = new ViewOnAttachStateChangeListenerC0539gk(4, this);

    /* renamed from: b8 */
    public int f60565b8 = 0;

    public v11(Context context, bf0 bf0Var, View view, int i, boolean z) {
        this.f60548a1 = context;
        this.f60549a2 = bf0Var;
        this.f60551a4 = z;
        this.f60550a3 = new ye0(bf0Var, LayoutInflater.from(context), z, f60547c0);
        this.f60553a6 = i;
        Resources resources = context.getResources();
        this.f60552a5 = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(R$dimen.abc_config_prefDialogWidth));
        this.f60558b1 = view;
        this.f60554a7 = new C0043a3(context, null, i, 0);
        bf0Var.m210689a1(this, context);
    }

    @Override // p000.tf0
    /* renamed from: a0 */
    public final void mo61a0(bf0 bf0Var, boolean z) {
        if (bf0Var != this.f60549a2) {
            return;
        }
        dismiss();
        sf0 sf0Var = this.f60560b3;
        if (sf0Var != null) {
            sf0Var.mo210850a0(bf0Var, z);
        }
    }

    @Override // p000.p01
    /* renamed from: a1 */
    public final boolean mo209886a1() {
        return !this.f60562b5 && this.f60554a7.f43997c5.isShowing();
    }

    @Override // p000.p01
    /* renamed from: a3 */
    public final void mo209888a3() {
        View view;
        if (mo209886a1()) {
            return;
        }
        if (this.f60562b5 || (view = this.f60558b1) == null) {
            throw new IllegalStateException("StandardMenuPopup cannot be used without an anchor");
        }
        this.f60559b2 = view;
        C0043a3 c0043a3 = this.f60554a7;
        C1402x5 c1402x5 = c0043a3.f43997c5;
        C1402x5 c1402x52 = c0043a3.f43997c5;
        c1402x5.setOnDismissListener(this);
        c0043a3.f43987b5 = this;
        c0043a3.f43996c4 = true;
        c1402x52.setFocusable(true);
        View view2 = this.f60559b2;
        boolean z = this.f60561b4 == null;
        ViewTreeObserver viewTreeObserver = view2.getViewTreeObserver();
        this.f60561b4 = viewTreeObserver;
        if (z) {
            viewTreeObserver.addOnGlobalLayoutListener(this.f60555a8);
        }
        view2.addOnAttachStateChangeListener(this.f60556a9);
        c0043a3.f43986b4 = view2;
        c0043a3.f43983b1 = this.f60565b8;
        boolean z2 = this.f60563b6;
        Context context = this.f60548a1;
        ye0 ye0Var = this.f60550a3;
        if (!z2) {
            this.f60564b7 = kf0.m213497b4(ye0Var, context, this.f60552a5);
            this.f60563b6 = true;
        }
        c0043a3.m209896b7(this.f60564b7);
        c1402x52.setInputMethodMode(2);
        Rect rect = this.f57514a0;
        c0043a3.f43995c3 = rect != null ? new Rect(rect) : null;
        c0043a3.mo209888a3();
        C1304ul c1304ul = c0043a3.f43974a2;
        c1304ul.setOnKeyListener(this);
        if (this.f60566b9) {
            bf0 bf0Var = this.f60549a2;
            if (bf0Var.f45878b2 != null) {
                FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(context).inflate(R$layout.abc_popup_menu_header_item_layout, (ViewGroup) c1304ul, false);
                TextView textView = (TextView) frameLayout.findViewById(R.id.title);
                if (textView != null) {
                    textView.setText(bf0Var.f45878b2);
                }
                frameLayout.setEnabled(false);
                c1304ul.addHeaderView(frameLayout, null, false);
            }
        }
        c0043a3.mo209895b6(ye0Var);
        c0043a3.mo209888a3();
    }

    @Override // p000.tf0
    /* renamed from: a5 */
    public final void mo209940a5(sf0 sf0Var) {
        this.f60560b3 = sf0Var;
    }

    @Override // p000.p01
    /* renamed from: a7 */
    public final C1304ul mo209890a7() {
        return this.f60554a7.f43974a2;
    }

    @Override // p000.tf0
    /* renamed from: a9 */
    public final void mo66a9(boolean z) {
        this.f60563b6 = false;
        ye0 ye0Var = this.f60550a3;
        if (ye0Var != null) {
            ye0Var.notifyDataSetChanged();
        }
    }

    @Override // p000.tf0
    /* renamed from: b0 */
    public final boolean mo67b0(r21 r21Var) {
        boolean z;
        if (r21Var.hasVisibleItems()) {
            nf0 nf0Var = new nf0(this.f60548a1, r21Var, this.f60559b2, this.f60551a4, this.f60553a6, 0);
            sf0 sf0Var = this.f60560b3;
            nf0Var.f58623a7 = sf0Var;
            kf0 kf0Var = nf0Var.f58624a8;
            if (kf0Var != null) {
                kf0Var.mo209940a5(sf0Var);
            }
            int size = r21Var.f45871a5.size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    z = false;
                    break;
                }
                MenuItem item = r21Var.getItem(i);
                if (item.isVisible() && item.getIcon() != null) {
                    z = true;
                    break;
                }
                i++;
            }
            nf0Var.f58622a6 = z;
            kf0 kf0Var2 = nf0Var.f58624a8;
            if (kf0Var2 != null) {
                kf0Var2.mo212969b6(z);
            }
            nf0Var.f58625a9 = this.f60557b0;
            this.f60557b0 = null;
            this.f60549a2.m210690a2(false);
            C0043a3 c0043a3 = this.f60554a7;
            int width = c0043a3.f43977a5;
            int iM209894b4 = c0043a3.m209894b4();
            int i2 = this.f60565b8;
            View view = this.f60558b1;
            WeakHashMap weakHashMap = xa1.f61054a0;
            if ((Gravity.getAbsoluteGravity(i2, ga1.m212904a3(view)) & 7) == 5) {
                width += this.f60558b1.getWidth();
            }
            if (!nf0Var.m214075a1()) {
                if (nf0Var.f58620a4 != null) {
                    nf0Var.m214076a3(width, iM209894b4, true, true);
                }
            }
            sf0 sf0Var2 = this.f60560b3;
            if (sf0Var2 != null) {
                sf0Var2.mo210851b6(r21Var);
            }
            return true;
        }
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

    @Override // p000.kf0
    /* renamed from: b5 */
    public final void mo212968b5(View view) {
        this.f60558b1 = view;
    }

    @Override // p000.kf0
    /* renamed from: b6 */
    public final void mo212969b6(boolean z) {
        this.f60550a3.f61298a2 = z;
    }

    @Override // p000.kf0
    /* renamed from: b7 */
    public final void mo212970b7(int i) {
        this.f60565b8 = i;
    }

    @Override // p000.kf0
    /* renamed from: b8 */
    public final void mo212971b8(int i) {
        this.f60554a7.f43977a5 = i;
    }

    @Override // p000.kf0
    /* renamed from: b9 */
    public final void mo212972b9(PopupWindow.OnDismissListener onDismissListener) {
        this.f60557b0 = onDismissListener;
    }

    @Override // p000.kf0
    /* renamed from: c0 */
    public final void mo212973c0(boolean z) {
        this.f60566b9 = z;
    }

    @Override // p000.kf0
    /* renamed from: c1 */
    public final void mo212974c1(int i) {
        this.f60554a7.m209892b0(i);
    }

    @Override // p000.p01
    public final void dismiss() {
        if (mo209886a1()) {
            this.f60554a7.dismiss();
        }
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public final void onDismiss() {
        this.f60562b5 = true;
        this.f60549a2.m210690a2(true);
        ViewTreeObserver viewTreeObserver = this.f60561b4;
        if (viewTreeObserver != null) {
            if (!viewTreeObserver.isAlive()) {
                this.f60561b4 = this.f60559b2.getViewTreeObserver();
            }
            this.f60561b4.removeGlobalOnLayoutListener(this.f60555a8);
            this.f60561b4 = null;
        }
        this.f60559b2.removeOnAttachStateChangeListener(this.f60556a9);
        PopupWindow.OnDismissListener onDismissListener = this.f60557b0;
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
    }

    @Override // android.view.View.OnKeyListener
    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 1 || i != 82) {
            return false;
        }
        dismiss();
        return true;
    }

    @Override // p000.tf0
    /* renamed from: a4 */
    public final void mo63a4(Parcelable parcelable) {
    }

    @Override // p000.kf0
    /* renamed from: b3 */
    public final void mo212967b3(bf0 bf0Var) {
    }
}
