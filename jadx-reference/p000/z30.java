package p000;

import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.OverScroller;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public abstract class z30 extends tb1 {

    /* renamed from: a2 */
    public RunnableC0818lf f61430a2;

    /* renamed from: a3 */
    public OverScroller f61431a3;

    /* renamed from: a4 */
    public boolean f61432a4;

    /* renamed from: a5 */
    public int f61433a5;

    /* renamed from: a6 */
    public int f61434a6;

    /* renamed from: a7 */
    public int f61435a7;

    /* renamed from: a8 */
    public VelocityTracker f61436a8;

    /* JADX WARN: Removed duplicated region for block: B:21:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00a5  */
    @Override // p000.AbstractC0879my
    /* renamed from: a6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean mo210914a6(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
        VelocityTracker velocityTracker;
        View view2;
        int iFindPointerIndex;
        if (this.f61435a7 < 0) {
            this.f61435a7 = ViewConfiguration.get(coordinatorLayout.getContext()).getScaledTouchSlop();
        }
        if (motionEvent.getActionMasked() == 2 && this.f61432a4) {
            int i = this.f61433a5;
            if (i != -1 && (iFindPointerIndex = motionEvent.findPointerIndex(i)) != -1) {
                int y = (int) motionEvent.getY(iFindPointerIndex);
                if (Math.abs(y - this.f61434a6) > this.f61435a7) {
                    this.f61434a6 = y;
                    return true;
                }
                if (motionEvent.getActionMasked() == 0) {
                }
                velocityTracker = this.f61436a8;
                if (velocityTracker != null) {
                }
            }
        } else {
            if (motionEvent.getActionMasked() == 0) {
                this.f61433a5 = -1;
                int x = (int) motionEvent.getX();
                int y2 = (int) motionEvent.getY();
                WeakReference weakReference = ((AppBarLayout.BaseBehavior) this).f49046b3;
                boolean z = (weakReference == null || !((view2 = (View) weakReference.get()) == null || !view2.isShown() || view2.canScrollVertically(-1))) && coordinatorLayout.m210066b4(view, x, y2);
                this.f61432a4 = z;
                if (z) {
                    this.f61434a6 = y2;
                    this.f61433a5 = motionEvent.getPointerId(0);
                    if (this.f61436a8 == null) {
                        this.f61436a8 = VelocityTracker.obtain();
                    }
                    OverScroller overScroller = this.f61431a3;
                    if (overScroller != null && !overScroller.isFinished()) {
                        this.f61431a3.abortAnimation();
                        return true;
                    }
                }
            }
            velocityTracker = this.f61436a8;
            if (velocityTracker != null) {
                velocityTracker.addMovement(motionEvent);
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x00e6  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00ef  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00f6 A[ADDED_TO_REGION] */
    @Override // p000.AbstractC0879my
    /* renamed from: b7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean mo210915b7(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
        boolean z;
        VelocityTracker velocityTracker;
        VelocityTracker velocityTracker2;
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 1) {
            if (actionMasked == 2) {
                int iFindPointerIndex = motionEvent.findPointerIndex(this.f61433a5);
                if (iFindPointerIndex != -1) {
                    int y = (int) motionEvent.getY(iFindPointerIndex);
                    int i = this.f61434a6 - y;
                    this.f61434a6 = y;
                    mo210894c1(coordinatorLayout, view, mo210893c0() - i, -((AppBarLayout) view).getDownNestedScrollRange(), 0);
                }
            }
            if (actionMasked != 3) {
                if (actionMasked == 6) {
                    int i2 = motionEvent.getActionIndex() == 0 ? 1 : 0;
                    this.f61433a5 = motionEvent.getPointerId(i2);
                    this.f61434a6 = (int) (motionEvent.getY(i2) + 0.5f);
                }
            }
            z = false;
            velocityTracker2 = this.f61436a8;
            if (velocityTracker2 != null) {
                velocityTracker2.addMovement(motionEvent);
            }
            return !this.f61432a4 || z;
        }
        VelocityTracker velocityTracker3 = this.f61436a8;
        if (velocityTracker3 != null) {
            velocityTracker3.addMovement(motionEvent);
            this.f61436a8.computeCurrentVelocity(1000);
            float yVelocity = this.f61436a8.getYVelocity(this.f61433a5);
            AppBarLayout appBarLayout = (AppBarLayout) view;
            int i3 = -appBarLayout.getTotalScrollRange();
            RunnableC0818lf runnableC0818lf = this.f61430a2;
            if (runnableC0818lf != null) {
                view.removeCallbacks(runnableC0818lf);
                this.f61430a2 = null;
            }
            if (this.f61431a3 == null) {
                this.f61431a3 = new OverScroller(view.getContext());
            }
            this.f61431a3.fling(0, m214734b8(), 0, Math.round(yVelocity), 0, 0, i3, 0);
            if (this.f61431a3.computeScrollOffset()) {
                RunnableC0818lf runnableC0818lf2 = new RunnableC0818lf(this, coordinatorLayout, view);
                this.f61430a2 = runnableC0818lf2;
                WeakHashMap weakHashMap = xa1.f61054a0;
                fa1.m212775b2(view, runnableC0818lf2);
            } else {
                ((AppBarLayout.BaseBehavior) this).m210898c8(coordinatorLayout, appBarLayout);
                if (appBarLayout.f49029b1) {
                    appBarLayout.m210880a5(appBarLayout.m210881a6(AppBarLayout.BaseBehavior.m210884c5(coordinatorLayout)));
                }
            }
            z = true;
        }
        this.f61432a4 = false;
        this.f61433a5 = -1;
        velocityTracker = this.f61436a8;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.f61436a8 = null;
        }
        velocityTracker2 = this.f61436a8;
        if (velocityTracker2 != null) {
        }
        if (this.f61432a4) {
        }
        z = false;
        this.f61432a4 = false;
        this.f61433a5 = -1;
        velocityTracker = this.f61436a8;
        if (velocityTracker != null) {
        }
        velocityTracker2 = this.f61436a8;
        if (velocityTracker2 != null) {
        }
        if (this.f61432a4) {
        }
    }

    /* renamed from: c0 */
    public abstract int mo210893c0();

    /* renamed from: c1 */
    public abstract int mo210894c1(CoordinatorLayout coordinatorLayout, View view, int i, int i2, int i3);

    /* renamed from: c2 */
    public final void m215339c2(CoordinatorLayout coordinatorLayout, View view, int i) {
        mo210894c1(coordinatorLayout, view, i, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
}
