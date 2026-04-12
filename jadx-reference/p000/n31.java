package p000;

import android.view.View;
import android.view.ViewParent;
import com.google.android.material.behavior.SwipeDismissBehavior;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class n31 extends cq0 {

    /* renamed from: b0 */
    public int f58440b0;

    /* renamed from: b1 */
    public int f58441b1 = -1;

    /* renamed from: b2 */
    public final /* synthetic */ SwipeDismissBehavior f58442b2;

    public n31(SwipeDismissBehavior swipeDismissBehavior) {
        this.f58442b2 = swipeDismissBehavior;
    }

    @Override // p000.cq0
    /* renamed from: a5 */
    public final int mo212501a5(View view, int i) {
        int width;
        int width2;
        int width3;
        WeakHashMap weakHashMap = xa1.f61054a0;
        boolean z = ga1.m212904a3(view) == 1;
        int i2 = this.f58442b2.f49141a3;
        if (i2 == 0) {
            if (z) {
                width = this.f58440b0 - view.getWidth();
                width2 = this.f58440b0;
            } else {
                width = this.f58440b0;
                width3 = view.getWidth();
                width2 = width3 + width;
            }
        } else if (i2 != 1) {
            width = this.f58440b0 - view.getWidth();
            width2 = view.getWidth() + this.f58440b0;
        } else if (z) {
            width = this.f58440b0;
            width3 = view.getWidth();
            width2 = width3 + width;
        } else {
            width = this.f58440b0 - view.getWidth();
            width2 = this.f58440b0;
        }
        return Math.min(Math.max(width, i), width2);
    }

    @Override // p000.cq0
    /* renamed from: a6 */
    public final int mo212502a6(View view, int i) {
        return view.getTop();
    }

    @Override // p000.cq0
    /* renamed from: b6 */
    public final int mo212503b6(View view) {
        return view.getWidth();
    }

    @Override // p000.cq0
    /* renamed from: d0 */
    public final void mo212512d0(View view, int i) {
        this.f58441b1 = i;
        this.f58440b0 = view.getLeft();
        ViewParent parent = view.getParent();
        if (parent != null) {
            SwipeDismissBehavior swipeDismissBehavior = this.f58442b2;
            swipeDismissBehavior.f49140a2 = true;
            parent.requestDisallowInterceptTouchEvent(true);
            swipeDismissBehavior.f49140a2 = false;
        }
    }

    @Override // p000.cq0
    /* renamed from: d2 */
    public final void mo212514d2(View view, int i, int i2) {
        float width = view.getWidth();
        SwipeDismissBehavior swipeDismissBehavior = this.f58442b2;
        float f = width * swipeDismissBehavior.f49142a4;
        float width2 = view.getWidth() * swipeDismissBehavior.f49143a5;
        float fAbs = Math.abs(i - this.f58440b0);
        if (fAbs <= f) {
            view.setAlpha(1.0f);
        } else if (fAbs >= width2) {
            view.setAlpha(0.0f);
        } else {
            view.setAlpha(Math.min(Math.max(0.0f, 1.0f - ((fAbs - f) / (width2 - f))), 1.0f));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0067  */
    @Override // p000.cq0
    /* renamed from: d3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void mo212515d3(View view, float f, float f2) {
        int i;
        this.f58441b1 = -1;
        int width = view.getWidth();
        boolean z = false;
        SwipeDismissBehavior swipeDismissBehavior = this.f58442b2;
        if (f != 0.0f) {
            WeakHashMap weakHashMap = xa1.f61054a0;
            boolean z2 = ga1.m212904a3(view) == 1;
            int i2 = swipeDismissBehavior.f49141a3;
            if (i2 != 2 && (i2 != 0 ? i2 != 1 || (!z2 ? f < 0.0f : f > 0.0f) : !z2 ? f > 0.0f : f < 0.0f)) {
                i = this.f58440b0;
            } else if (f >= 0.0f) {
                int left = view.getLeft();
                int i3 = this.f58440b0;
                i = left < i3 ? this.f58440b0 - width : i3 + width;
                z = true;
            }
        } else {
            if (Math.abs(view.getLeft() - this.f58440b0) >= Math.round(view.getWidth() * 0.5f)) {
            }
        }
        if (swipeDismissBehavior.f49138a0.m210646b5(i, view.getTop())) {
            RunnableC0884n2 runnableC0884n2 = new RunnableC0884n2(swipeDismissBehavior, view, z);
            WeakHashMap weakHashMap2 = xa1.f61054a0;
            fa1.m212775b2(view, runnableC0884n2);
        }
    }

    @Override // p000.cq0
    /* renamed from: e1 */
    public final boolean mo212516e1(View view, int i) {
        int i2 = this.f58441b1;
        return (i2 == -1 || i2 == i) && this.f58442b2.mo210916b8(view);
    }

    @Override // p000.cq0
    /* renamed from: d1 */
    public final void mo212513d1(int i) {
    }
}
