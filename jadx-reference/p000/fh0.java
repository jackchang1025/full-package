package p000;

import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.google.android.material.tabs.TabLayout;
import java.util.Collections;
import java.util.List;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public class fh0 implements to0, InterfaceC0925nt, aj0 {

    /* renamed from: a1 */
    public static fh0 f56255a1;

    /* renamed from: a0 */
    public final /* synthetic */ int f56256a0;

    public /* synthetic */ fh0(int i) {
        this.f56256a0 = i;
    }

    /* renamed from: a1 */
    public static RectF m212807a1(TabLayout tabLayout, View view) {
        if (view == null) {
            return new RectF();
        }
        if (tabLayout.f49913c0 || !(view instanceof y41)) {
            return new RectF(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        }
        y41 y41Var = (y41) view;
        int iM215239a1 = y41Var.m215239a1();
        int iM215238a0 = y41Var.m215238a0();
        int iM214422b8 = (int) AbstractC1117qo.m214422b8(y41Var.getContext(), 24);
        if (iM215239a1 < iM214422b8) {
            iM215239a1 = iM214422b8;
        }
        int right = (y41Var.getRight() + y41Var.getLeft()) / 2;
        int bottom = (y41Var.getBottom() + y41Var.getTop()) / 2;
        int i = iM215239a1 / 2;
        return new RectF(right - i, bottom - (iM215238a0 / 2), i + right, (right / 2) + bottom);
    }

    /* renamed from: a3 */
    public static Path m212808a3(float f, float f2, float f3, float f4) {
        Path path = new Path();
        path.moveTo(f, f2);
        path.lineTo(f3, f4);
        return path;
    }

    @Override // p000.to0
    /* renamed from: a0 */
    public void mo212810a0(int i, Object obj) {
        switch (this.f56256a0) {
            case 4:
                break;
            default:
                if (i == 6 || i == 7 || i == 8) {
                    break;
                }
                break;
        }
    }

    /* renamed from: a2 */
    public float mo212608a2(float f, float f2) {
        return 1.0f;
    }

    /* renamed from: a4 */
    public boolean mo212811a4(CharSequence charSequence) {
        return false;
    }

    /* renamed from: a6 */
    public void mo212812a6(TabLayout tabLayout, View view, View view2, float f, Drawable drawable) {
        RectF rectFM212807a1 = m212807a1(tabLayout, view);
        RectF rectFM212807a12 = m212807a1(tabLayout, view2);
        drawable.setBounds(AbstractC1249t7.m214729a2((int) rectFM212807a1.left, f, (int) rectFM212807a12.left), drawable.getBounds().top, AbstractC1249t7.m214729a2((int) rectFM212807a1.right, f, (int) rectFM212807a12.right), drawable.getBounds().bottom);
    }

    public fh0(int[] iArr, ValueAnimator valueAnimator) {
        this.f56256a0 = 12;
    }

    public fh0() {
        this.f56256a0 = 20;
        List list = Collections.EMPTY_LIST;
    }

    /* renamed from: a5 */
    private final void m212809a5(int i, Object obj) {
    }
}
