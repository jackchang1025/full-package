package p000;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;
import com.google.android.material.tabs.TabLayout;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class w41 extends LinearLayout {

    /* renamed from: a2 */
    public static final /* synthetic */ int f60770a2 = 0;

    /* renamed from: a0 */
    public ValueAnimator f60771a0;

    /* renamed from: a1 */
    public final /* synthetic */ TabLayout f60772a1;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public w41(TabLayout tabLayout, Context context) {
        super(context);
        this.f60772a1 = tabLayout;
        setWillNotDraw(false);
    }

    /* renamed from: a0 */
    public final void m215004a0(int i) {
        int i2 = TabLayout.f49891c9;
        TabLayout tabLayout = this.f60772a1;
        tabLayout.getClass();
        View childAt = getChildAt(i);
        fh0 fh0Var = tabLayout.f49917c4;
        Drawable drawable = tabLayout.f49901a8;
        fh0Var.getClass();
        RectF rectFM212807a1 = fh0.m212807a1(tabLayout, childAt);
        drawable.setBounds((int) rectFM212807a1.left, drawable.getBounds().top, (int) rectFM212807a1.right, drawable.getBounds().bottom);
        tabLayout.f49893a0 = i;
    }

    /* renamed from: a1 */
    public final void m215005a1(int i) {
        TabLayout tabLayout = this.f60772a1;
        Rect bounds = tabLayout.f49901a8.getBounds();
        tabLayout.f49901a8.setBounds(bounds.left, 0, bounds.right, i);
        requestLayout();
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        int height;
        TabLayout tabLayout = this.f60772a1;
        int iHeight = tabLayout.f49901a8.getBounds().height();
        if (iHeight < 0) {
            iHeight = tabLayout.f49901a8.getIntrinsicHeight();
        }
        int i = tabLayout.f49910b7;
        if (i == 0) {
            height = getHeight() - iHeight;
            iHeight = getHeight();
        } else if (i != 1) {
            height = 0;
            if (i != 2) {
                iHeight = i != 3 ? 0 : getHeight();
            }
        } else {
            height = (getHeight() - iHeight) / 2;
            iHeight = (getHeight() + iHeight) / 2;
        }
        if (tabLayout.f49901a8.getBounds().width() > 0) {
            Rect bounds = tabLayout.f49901a8.getBounds();
            tabLayout.f49901a8.setBounds(bounds.left, height, bounds.right, iHeight);
            tabLayout.f49901a8.draw(canvas);
        }
        super.draw(canvas);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        ValueAnimator valueAnimator = this.f60771a0;
        TabLayout tabLayout = this.f60772a1;
        if (valueAnimator == null || !valueAnimator.isRunning()) {
            if (tabLayout.f49893a0 == -1) {
                tabLayout.f49893a0 = tabLayout.getSelectedTabPosition();
            }
            m215004a0(tabLayout.f49893a0);
            return;
        }
        int selectedTabPosition = tabLayout.getSelectedTabPosition();
        if (tabLayout.f49893a0 == selectedTabPosition) {
            return;
        }
        View childAt = getChildAt(tabLayout.getSelectedTabPosition());
        View childAt2 = getChildAt(selectedTabPosition);
        if (childAt2 == null) {
            m215004a0(tabLayout.getSelectedTabPosition());
            return;
        }
        tabLayout.f49893a0 = selectedTabPosition;
        C1484yl c1484yl = new C1484yl(this, childAt, childAt2);
        this.f60771a0.removeAllUpdateListeners();
        this.f60771a0.addUpdateListener(c1484yl);
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (View.MeasureSpec.getMode(i) != 1073741824) {
            return;
        }
        TabLayout tabLayout = this.f60772a1;
        boolean z = true;
        if (tabLayout.f49908b5 == 1 || tabLayout.f49911b8 == 2) {
            int childCount = getChildCount();
            int iMax = 0;
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = getChildAt(i3);
                if (childAt.getVisibility() == 0) {
                    iMax = Math.max(iMax, childAt.getMeasuredWidth());
                }
            }
            if (iMax <= 0) {
                return;
            }
            if (iMax * childCount <= getMeasuredWidth() - (((int) AbstractC1117qo.m214422b8(getContext(), 16)) * 2)) {
                boolean z2 = false;
                for (int i4 = 0; i4 < childCount; i4++) {
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getChildAt(i4).getLayoutParams();
                    if (layoutParams.width != iMax || layoutParams.weight != 0.0f) {
                        layoutParams.width = iMax;
                        layoutParams.weight = 0.0f;
                        z2 = true;
                    }
                }
                z = z2;
            } else {
                tabLayout.f49908b5 = 0;
                tabLayout.m211135a2(false);
            }
            if (z) {
                super.onMeasure(i, i2);
            }
        }
    }
}
