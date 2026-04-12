package p000;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.ListAdapter;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.ListPopupWindow;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: y6 */
/* loaded from: classes.dex */
public final class C1446y6 extends ListPopupWindow implements InterfaceC1447y7 {

    /* renamed from: c8 */
    public CharSequence f61240c8;

    /* renamed from: c9 */
    public C1443y3 f61241c9;

    /* renamed from: d0 */
    public final Rect f61242d0;

    /* renamed from: d1 */
    public int f61243d1;

    /* renamed from: d2 */
    public final /* synthetic */ AppCompatSpinner f61244d2;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1446y6(AppCompatSpinner appCompatSpinner, Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f61244d2 = appCompatSpinner;
        this.f61242d0 = new Rect();
        this.f43986b4 = appCompatSpinner;
        this.f43996c4 = true;
        this.f43997c5.setFocusable(true);
        this.f43987b5 = new C1444y4(0, this);
    }

    @Override // p000.InterfaceC1447y7
    /* renamed from: a6 */
    public final void mo215227a6(CharSequence charSequence) {
        this.f61240c8 = charSequence;
    }

    @Override // p000.InterfaceC1447y7
    /* renamed from: b1 */
    public final void mo215230b1(int i) {
        this.f61243d1 = i;
    }

    @Override // p000.InterfaceC1447y7
    /* renamed from: b3 */
    public final void mo215232b3(int i, int i2) {
        ViewTreeObserver viewTreeObserver;
        C1402x5 c1402x5 = this.f43997c5;
        boolean zIsShowing = c1402x5.isShowing();
        m215245b8();
        c1402x5.setInputMethodMode(2);
        mo209888a3();
        C1304ul c1304ul = this.f43974a2;
        c1304ul.setChoiceMode(1);
        AbstractC1440y0.m215222a3(c1304ul, i);
        AbstractC1440y0.m215221a2(c1304ul, i2);
        AppCompatSpinner appCompatSpinner = this.f61244d2;
        int selectedItemPosition = appCompatSpinner.getSelectedItemPosition();
        C1304ul c1304ul2 = this.f43974a2;
        if (c1402x5.isShowing() && c1304ul2 != null) {
            c1304ul2.setListSelectionHidden(false);
            c1304ul2.setSelection(selectedItemPosition);
            if (c1304ul2.getChoiceMode() != 0) {
                c1304ul2.setItemChecked(selectedItemPosition, true);
            }
        }
        if (zIsShowing || (viewTreeObserver = appCompatSpinner.getViewTreeObserver()) == null) {
            return;
        }
        ViewTreeObserverOnGlobalLayoutListenerC0937o2 viewTreeObserverOnGlobalLayoutListenerC0937o2 = new ViewTreeObserverOnGlobalLayoutListenerC0937o2(2, this);
        viewTreeObserver.addOnGlobalLayoutListener(viewTreeObserverOnGlobalLayoutListenerC0937o2);
        c1402x5.setOnDismissListener(new C1445y5(this, viewTreeObserverOnGlobalLayoutListenerC0937o2));
    }

    @Override // p000.InterfaceC1447y7
    /* renamed from: b5 */
    public final CharSequence mo215234b5() {
        return this.f61240c8;
    }

    @Override // androidx.appcompat.widget.ListPopupWindow, p000.InterfaceC1447y7
    /* renamed from: b6 */
    public final void mo209895b6(ListAdapter listAdapter) {
        super.mo209895b6(listAdapter);
        this.f61241c9 = (C1443y3) listAdapter;
    }

    /* renamed from: b8 */
    public final void m215245b8() {
        int i;
        AppCompatSpinner appCompatSpinner = this.f61244d2;
        Rect rect = appCompatSpinner.f43932a7;
        C1402x5 c1402x5 = this.f43997c5;
        Drawable background = c1402x5.getBackground();
        if (background != null) {
            background.getPadding(rect);
            i = id1.m213156a0(appCompatSpinner) ? rect.right : -rect.left;
        } else {
            i = 0;
            rect.right = 0;
            rect.left = 0;
        }
        int paddingLeft = appCompatSpinner.getPaddingLeft();
        int paddingRight = appCompatSpinner.getPaddingRight();
        int width = appCompatSpinner.getWidth();
        int i2 = appCompatSpinner.f43931a6;
        if (i2 == -2) {
            int iM209879a0 = appCompatSpinner.m209879a0(this.f61241c9, c1402x5.getBackground());
            int i3 = (appCompatSpinner.getContext().getResources().getDisplayMetrics().widthPixels - rect.left) - rect.right;
            if (iM209879a0 > i3) {
                iM209879a0 = i3;
            }
            m209896b7(Math.max(iM209879a0, (width - paddingLeft) - paddingRight));
        } else if (i2 == -1) {
            m209896b7((width - paddingLeft) - paddingRight);
        } else {
            m209896b7(i2);
        }
        this.f43977a5 = id1.m213156a0(appCompatSpinner) ? (((width - paddingRight) - this.f43976a4) - this.f61243d1) + i : paddingLeft + this.f61243d1 + i;
    }
}
