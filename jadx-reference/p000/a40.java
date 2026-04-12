package p000;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.search.SearchBar;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public abstract class a40 extends tb1 {

    /* renamed from: a2 */
    public final Rect f38a2;

    /* renamed from: a3 */
    public final Rect f39a3;

    /* renamed from: a4 */
    public int f40a4;

    /* renamed from: a5 */
    public int f41a5;

    public a40() {
        this.f38a2 = new Rect();
        this.f39a3 = new Rect();
        this.f40a4 = 0;
    }

    @Override // p000.AbstractC0879my
    /* renamed from: a8 */
    public final boolean mo55a8(CoordinatorLayout coordinatorLayout, View view, int i, int i2, int i3) {
        AppBarLayout appBarLayoutM210900c1;
        xf1 lastWindowInsets;
        int i4 = view.getLayoutParams().height;
        if ((i4 != -1 && i4 != -2) || (appBarLayoutM210900c1 = AppBarLayout.ScrollingViewBehavior.m210900c1(coordinatorLayout.m210064b0(view))) == null) {
            return false;
        }
        int size = View.MeasureSpec.getSize(i3);
        if (size > 0) {
            WeakHashMap weakHashMap = xa1.f61054a0;
            if (fa1.m212764a1(appBarLayoutM210900c1) && (lastWindowInsets = coordinatorLayout.getLastWindowInsets()) != null) {
                size += lastWindowInsets.m215171a0() + lastWindowInsets.m215174a3();
            }
        } else {
            size = coordinatorLayout.getHeight();
        }
        int totalScrollRange = appBarLayoutM210900c1.getTotalScrollRange() + size;
        int measuredHeight = appBarLayoutM210900c1.getMeasuredHeight();
        if (this instanceof SearchBar.ScrollingViewBehavior) {
            view.setTranslationY(-measuredHeight);
        } else {
            view.setTranslationY(0.0f);
            totalScrollRange -= measuredHeight;
        }
        coordinatorLayout.m210069b7(i, i2, View.MeasureSpec.makeMeasureSpec(totalScrollRange, i4 == -1 ? 1073741824 : Integer.MIN_VALUE), view);
        return true;
    }

    @Override // p000.tb1
    /* renamed from: b9 */
    public final void mo56b9(CoordinatorLayout coordinatorLayout, View view, int i) {
        AppBarLayout appBarLayoutM210900c1 = AppBarLayout.ScrollingViewBehavior.m210900c1(coordinatorLayout.m210064b0(view));
        if (appBarLayoutM210900c1 == null) {
            coordinatorLayout.m210068b6(view, i);
            this.f40a4 = 0;
            return;
        }
        C0907nb c0907nb = (C0907nb) view.getLayoutParams();
        int paddingLeft = coordinatorLayout.getPaddingLeft() + ((ViewGroup.MarginLayoutParams) c0907nb).leftMargin;
        int bottom = appBarLayoutM210900c1.getBottom() + ((ViewGroup.MarginLayoutParams) c0907nb).topMargin;
        int width = (coordinatorLayout.getWidth() - coordinatorLayout.getPaddingRight()) - ((ViewGroup.MarginLayoutParams) c0907nb).rightMargin;
        int bottom2 = ((appBarLayoutM210900c1.getBottom() + coordinatorLayout.getHeight()) - coordinatorLayout.getPaddingBottom()) - ((ViewGroup.MarginLayoutParams) c0907nb).bottomMargin;
        Rect rect = this.f38a2;
        rect.set(paddingLeft, bottom, width, bottom2);
        xf1 lastWindowInsets = coordinatorLayout.getLastWindowInsets();
        if (lastWindowInsets != null) {
            WeakHashMap weakHashMap = xa1.f61054a0;
            if (fa1.m212764a1(coordinatorLayout) && !fa1.m212764a1(view)) {
                rect.left = lastWindowInsets.m215172a1() + rect.left;
                rect.right -= lastWindowInsets.m215173a2();
            }
        }
        int i2 = c0907nb.f58472a2;
        if (i2 == 0) {
            i2 = 8388659;
        }
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        Rect rect2 = this.f39a3;
        l30.m213778a1(i2, measuredWidth, measuredHeight, rect, rect2, i);
        int iM57c0 = m57c0(appBarLayoutM210900c1);
        view.layout(rect2.left, rect2.top - iM57c0, rect2.right, rect2.bottom - iM57c0);
        this.f40a4 = rect2.top - appBarLayoutM210900c1.getBottom();
    }

    /* renamed from: c0 */
    public final int m57c0(View view) {
        int i;
        if (this.f41a5 == 0) {
            return 0;
        }
        float f = 0.0f;
        if (view instanceof AppBarLayout) {
            AppBarLayout appBarLayout = (AppBarLayout) view;
            int totalScrollRange = appBarLayout.getTotalScrollRange();
            int downNestedPreScrollRange = appBarLayout.getDownNestedPreScrollRange();
            AbstractC0879my abstractC0879my = ((C0907nb) appBarLayout.getLayoutParams()).f58470a0;
            int iMo210893c0 = abstractC0879my instanceof AppBarLayout.BaseBehavior ? ((AppBarLayout.BaseBehavior) abstractC0879my).mo210893c0() : 0;
            if ((downNestedPreScrollRange == 0 || totalScrollRange + iMo210893c0 > downNestedPreScrollRange) && (i = totalScrollRange - downNestedPreScrollRange) != 0) {
                f = (iMo210893c0 / i) + 1.0f;
            }
        }
        int i2 = this.f41a5;
        return cq0.m212476a4((int) (f * i2), 0, i2);
    }

    public a40(int i) {
        super(0);
        this.f38a2 = new Rect();
        this.f39a3 = new Rect();
        this.f40a4 = 0;
    }
}
