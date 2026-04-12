package p000;

import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: w */
/* loaded from: classes.dex */
public abstract class AbstractViewOnTouchListenerC1358w implements View.OnTouchListener, View.OnAttachStateChangeListener {

    /* renamed from: a0 */
    public final float f60727a0;

    /* renamed from: a1 */
    public final int f60728a1;

    /* renamed from: a2 */
    public final int f60729a2;

    /* renamed from: a3 */
    public final View f60730a3;

    /* renamed from: a4 */
    public RunnableC1319v f60731a4;

    /* renamed from: a5 */
    public RunnableC1319v f60732a5;

    /* renamed from: a6 */
    public boolean f60733a6;

    /* renamed from: a7 */
    public int f60734a7;

    /* renamed from: a8 */
    public final int[] f60735a8 = new int[2];

    public AbstractViewOnTouchListenerC1358w(View view) {
        this.f60730a3 = view;
        view.setLongClickable(true);
        view.addOnAttachStateChangeListener(this);
        this.f60727a0 = ViewConfiguration.get(view.getContext()).getScaledTouchSlop();
        int tapTimeout = ViewConfiguration.getTapTimeout();
        this.f60728a1 = tapTimeout;
        this.f60729a2 = (ViewConfiguration.getLongPressTimeout() + tapTimeout) / 2;
    }

    /* renamed from: a0 */
    public final void m214973a0() {
        RunnableC1319v runnableC1319v = this.f60732a5;
        View view = this.f60730a3;
        if (runnableC1319v != null) {
            view.removeCallbacks(runnableC1319v);
        }
        RunnableC1319v runnableC1319v2 = this.f60731a4;
        if (runnableC1319v2 != null) {
            view.removeCallbacks(runnableC1319v2);
        }
    }

    /* renamed from: a1 */
    public abstract p01 mo213948a1();

    /* renamed from: a2 */
    public abstract boolean mo213949a2();

    /* renamed from: a3 */
    public boolean mo213950a3() {
        p01 p01VarMo213948a1 = mo213948a1();
        if (p01VarMo213948a1 == null || !p01VarMo213948a1.mo209886a1()) {
            return true;
        }
        p01VarMo213948a1.dismiss();
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0100  */
    @Override // android.view.View.OnTouchListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onTouch(View view, MotionEvent motionEvent) throws IllegalAccessException, IllegalArgumentException {
        boolean z;
        C1304ul c1304ulMo209890a7;
        boolean z2 = this.f60733a6;
        View view2 = this.f60730a3;
        if (z2) {
            p01 p01VarMo213948a1 = mo213948a1();
            if (p01VarMo213948a1 == null || !p01VarMo213948a1.mo209886a1() || (c1304ulMo209890a7 = p01VarMo213948a1.mo209890a7()) == null || !c1304ulMo209890a7.isShown()) {
                z = !mo213950a3();
            } else {
                MotionEvent motionEventObtainNoHistory = MotionEvent.obtainNoHistory(motionEvent);
                int[] iArr = this.f60735a8;
                view2.getLocationOnScreen(iArr);
                motionEventObtainNoHistory.offsetLocation(iArr[0], iArr[1]);
                c1304ulMo209890a7.getLocationOnScreen(iArr);
                motionEventObtainNoHistory.offsetLocation(-iArr[0], -iArr[1]);
                boolean zM214853a1 = c1304ulMo209890a7.m214853a1(motionEventObtainNoHistory, this.f60734a7);
                motionEventObtainNoHistory.recycle();
                int actionMasked = motionEvent.getActionMasked();
                boolean z3 = (actionMasked == 1 || actionMasked == 3) ? false : true;
                if (!zM214853a1 || !z3) {
                }
            }
        } else if (view2.isEnabled()) {
            int actionMasked2 = motionEvent.getActionMasked();
            if (actionMasked2 == 0) {
                this.f60734a7 = motionEvent.getPointerId(0);
                if (this.f60731a4 == null) {
                    this.f60731a4 = new RunnableC1319v(this, 0);
                }
                view2.postDelayed(this.f60731a4, this.f60728a1);
                if (this.f60732a5 == null) {
                    this.f60732a5 = new RunnableC1319v(this, 1);
                }
                view2.postDelayed(this.f60732a5, this.f60729a2);
            } else if (actionMasked2 == 1) {
                m214973a0();
            } else if (actionMasked2 == 2) {
                int iFindPointerIndex = motionEvent.findPointerIndex(this.f60734a7);
                if (iFindPointerIndex >= 0) {
                    float x = motionEvent.getX(iFindPointerIndex);
                    float y = motionEvent.getY(iFindPointerIndex);
                    float f = this.f60727a0;
                    float f2 = -f;
                    if (x < f2 || y < f2 || x >= (view2.getRight() - view2.getLeft()) + f || y >= (view2.getBottom() - view2.getTop()) + f) {
                        m214973a0();
                        view2.getParent().requestDisallowInterceptTouchEvent(true);
                        if (mo213949a2()) {
                            z = true;
                        }
                        if (z) {
                            long jUptimeMillis = SystemClock.uptimeMillis();
                            MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0);
                            view2.onTouchEvent(motionEventObtain);
                            motionEventObtain.recycle();
                        }
                    }
                }
            } else if (actionMasked2 == 3) {
            }
            z = false;
            if (z) {
            }
        } else {
            z = false;
            if (z) {
            }
        }
        this.f60733a6 = z;
        return z || z2;
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewDetachedFromWindow(View view) {
        this.f60733a6 = false;
        this.f60734a7 = -1;
        RunnableC1319v runnableC1319v = this.f60731a4;
        if (runnableC1319v != null) {
            this.f60730a3.removeCallbacks(runnableC1319v);
        }
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewAttachedToWindow(View view) {
    }
}
