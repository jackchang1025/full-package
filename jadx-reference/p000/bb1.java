package p000;

import android.content.Context;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.OverScroller;
import java.util.Arrays;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class bb1 {

    /* renamed from: c3 */
    public static final eq0 f45781c3 = new eq0(1);

    /* renamed from: a0 */
    public int f45782a0;

    /* renamed from: a1 */
    public int f45783a1;

    /* renamed from: a3 */
    public float[] f45785a3;

    /* renamed from: a4 */
    public float[] f45786a4;

    /* renamed from: a5 */
    public float[] f45787a5;

    /* renamed from: a6 */
    public float[] f45788a6;

    /* renamed from: a7 */
    public int[] f45789a7;

    /* renamed from: a8 */
    public int[] f45790a8;

    /* renamed from: a9 */
    public int[] f45791a9;

    /* renamed from: b0 */
    public int f45792b0;

    /* renamed from: b1 */
    public VelocityTracker f45793b1;

    /* renamed from: b2 */
    public final float f45794b2;

    /* renamed from: b3 */
    public float f45795b3;

    /* renamed from: b4 */
    public int f45796b4;

    /* renamed from: b5 */
    public final int f45797b5;

    /* renamed from: b6 */
    public int f45798b6;

    /* renamed from: b7 */
    public final OverScroller f45799b7;

    /* renamed from: b8 */
    public final cq0 f45800b8;

    /* renamed from: b9 */
    public View f45801b9;

    /* renamed from: c0 */
    public boolean f45802c0;

    /* renamed from: c1 */
    public final ViewGroup f45803c1;

    /* renamed from: a2 */
    public int f45784a2 = -1;

    /* renamed from: c2 */
    public final RunnableC0165ca f45804c2 = new RunnableC0165ca(19, this);

    public bb1(Context context, ViewGroup viewGroup, cq0 cq0Var) {
        if (cq0Var == null) {
            throw new IllegalArgumentException("Callback may not be null");
        }
        this.f45803c1 = viewGroup;
        this.f45800b8 = cq0Var;
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        int i = (int) ((context.getResources().getDisplayMetrics().density * 20.0f) + 0.5f);
        this.f45797b5 = i;
        this.f45796b4 = i;
        this.f45783a1 = viewConfiguration.getScaledTouchSlop();
        this.f45794b2 = viewConfiguration.getScaledMaximumFlingVelocity();
        this.f45795b3 = viewConfiguration.getScaledMinimumFlingVelocity();
        this.f45799b7 = new OverScroller(context, f45781c3);
    }

    /* renamed from: a0 */
    public final void m210631a0() {
        this.f45784a2 = -1;
        float[] fArr = this.f45785a3;
        if (fArr != null) {
            Arrays.fill(fArr, 0.0f);
            Arrays.fill(this.f45786a4, 0.0f);
            Arrays.fill(this.f45787a5, 0.0f);
            Arrays.fill(this.f45788a6, 0.0f);
            Arrays.fill(this.f45789a7, 0);
            Arrays.fill(this.f45790a8, 0);
            Arrays.fill(this.f45791a9, 0);
            this.f45792b0 = 0;
        }
        VelocityTracker velocityTracker = this.f45793b1;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.f45793b1 = null;
        }
    }

    /* renamed from: a1 */
    public final void m210632a1(View view, int i) {
        ViewParent parent = view.getParent();
        ViewGroup viewGroup = this.f45803c1;
        if (parent != viewGroup) {
            throw new IllegalArgumentException("captureChildView: parameter must be a descendant of the ViewDragHelper's tracked parent view (" + viewGroup + ")");
        }
        this.f45801b9 = view;
        this.f45784a2 = i;
        this.f45800b8.mo212512d0(view, i);
        m210645b4(1);
    }

    /* renamed from: a2 */
    public final boolean m210633a2(float f, float f2, int i, int i2) {
        float fAbs = Math.abs(f);
        float fAbs2 = Math.abs(f2);
        if ((this.f45789a7[i] & i2) != i2 || (this.f45798b6 & i2) == 0 || (this.f45791a9[i] & i2) == i2 || (this.f45790a8[i] & i2) == i2) {
            return false;
        }
        float f3 = this.f45783a1;
        if (fAbs <= f3 && fAbs2 <= f3) {
            return false;
        }
        if (fAbs < fAbs2 * 0.5f) {
            this.f45800b8.getClass();
        }
        return (this.f45790a8[i] & i2) == 0 && fAbs > ((float) this.f45783a1);
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0044 A[RETURN] */
    /* renamed from: a3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean m210634a3(View view, float f, float f2) {
        if (view != null) {
            cq0 cq0Var = this.f45800b8;
            boolean z = cq0Var.mo212503b6(view) > 0;
            boolean z2 = cq0Var.mo212504b7() > 0;
            if (z && z2) {
                float f3 = (f2 * f2) + (f * f);
                int i = this.f45783a1;
                if (f3 > i * i) {
                }
            } else if (!z ? !(!z2 || Math.abs(f2) <= this.f45783a1) : Math.abs(f) > this.f45783a1) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: a4 */
    public final void m210635a4(int i) {
        float[] fArr = this.f45785a3;
        if (fArr != null) {
            int i2 = this.f45792b0;
            int i3 = 1 << i;
            if ((i2 & i3) != 0) {
                fArr[i] = 0.0f;
                this.f45786a4[i] = 0.0f;
                this.f45787a5[i] = 0.0f;
                this.f45788a6[i] = 0.0f;
                this.f45789a7[i] = 0;
                this.f45790a8[i] = 0;
                this.f45791a9[i] = 0;
                this.f45792b0 = (~i3) & i2;
            }
        }
    }

    /* renamed from: a5 */
    public final int m210636a5(int i, int i2, int i3) {
        if (i == 0) {
            return 0;
        }
        float width = this.f45803c1.getWidth() / 2;
        float fSin = (((float) Math.sin((Math.min(1.0f, Math.abs(i) / r0) - 0.5f) * 0.47123894f)) * width) + width;
        int iAbs = Math.abs(i2);
        return Math.min(iAbs > 0 ? Math.round(Math.abs(fSin / iAbs) * 1000.0f) * 4 : (int) (((Math.abs(i) / i3) + 1.0f) * 256.0f), 600);
    }

    /* renamed from: a6 */
    public final boolean m210637a6() {
        if (this.f45782a0 == 2) {
            OverScroller overScroller = this.f45799b7;
            boolean zComputeScrollOffset = overScroller.computeScrollOffset();
            int currX = overScroller.getCurrX();
            int currY = overScroller.getCurrY();
            int left = currX - this.f45801b9.getLeft();
            int top = currY - this.f45801b9.getTop();
            if (left != 0) {
                View view = this.f45801b9;
                WeakHashMap weakHashMap = xa1.f61054a0;
                view.offsetLeftAndRight(left);
            }
            if (top != 0) {
                View view2 = this.f45801b9;
                WeakHashMap weakHashMap2 = xa1.f61054a0;
                view2.offsetTopAndBottom(top);
            }
            if (left != 0 || top != 0) {
                this.f45800b8.mo212514d2(this.f45801b9, currX, currY);
            }
            if (zComputeScrollOffset && currX == overScroller.getFinalX() && currY == overScroller.getFinalY()) {
                overScroller.abortAnimation();
                zComputeScrollOffset = false;
            }
            if (!zComputeScrollOffset) {
                this.f45803c1.post(this.f45804c2);
            }
        }
        return this.f45782a0 == 2;
    }

    /* renamed from: a7 */
    public final View m210638a7(int i, int i2) {
        ViewGroup viewGroup = this.f45803c1;
        for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
            this.f45800b8.getClass();
            View childAt = viewGroup.getChildAt(childCount);
            if (i >= childAt.getLeft() && i < childAt.getRight() && i2 >= childAt.getTop() && i2 < childAt.getBottom()) {
                return childAt;
            }
        }
        return null;
    }

    /* renamed from: a8 */
    public final boolean m210639a8(int i, int i2, int i3, int i4) {
        float f;
        float f2;
        float f3;
        float f4;
        int left = this.f45801b9.getLeft();
        int top = this.f45801b9.getTop();
        int i5 = i - left;
        int i6 = i2 - top;
        OverScroller overScroller = this.f45799b7;
        if (i5 == 0 && i6 == 0) {
            overScroller.abortAnimation();
            m210645b4(0);
            return false;
        }
        View view = this.f45801b9;
        int i7 = (int) this.f45795b3;
        int i8 = (int) this.f45794b2;
        int iAbs = Math.abs(i3);
        if (iAbs < i7) {
            i3 = 0;
        } else if (iAbs > i8) {
            i3 = i3 > 0 ? i8 : -i8;
        }
        int i9 = (int) this.f45795b3;
        int iAbs2 = Math.abs(i4);
        if (iAbs2 < i9) {
            i4 = 0;
        } else if (iAbs2 > i8) {
            i4 = i4 > 0 ? i8 : -i8;
        }
        int iAbs3 = Math.abs(i5);
        int iAbs4 = Math.abs(i6);
        int iAbs5 = Math.abs(i3);
        int iAbs6 = Math.abs(i4);
        int i10 = iAbs5 + iAbs6;
        int i11 = iAbs3 + iAbs4;
        if (i3 != 0) {
            f = iAbs5;
            f2 = i10;
        } else {
            f = iAbs3;
            f2 = i11;
        }
        float f5 = f / f2;
        if (i4 != 0) {
            f3 = iAbs6;
            f4 = i10;
        } else {
            f3 = iAbs4;
            f4 = i11;
        }
        float f6 = f3 / f4;
        cq0 cq0Var = this.f45800b8;
        overScroller.startScroll(left, top, i5, i6, (int) ((m210636a5(i6, i4, cq0Var.mo212504b7()) * f6) + (m210636a5(i5, i3, cq0Var.mo212503b6(view)) * f5)));
        m210645b4(2);
        return true;
    }

    /* renamed from: a9 */
    public final void m210640a9(MotionEvent motionEvent) {
        int i;
        int actionMasked = motionEvent.getActionMasked();
        int actionIndex = motionEvent.getActionIndex();
        if (actionMasked == 0) {
            m210631a0();
        }
        if (this.f45793b1 == null) {
            this.f45793b1 = VelocityTracker.obtain();
        }
        this.f45793b1.addMovement(motionEvent);
        cq0 cq0Var = this.f45800b8;
        if (actionMasked == 0) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            int pointerId = motionEvent.getPointerId(0);
            View viewM210638a7 = m210638a7((int) x, (int) y);
            m210643b2(x, y, pointerId);
            m210649b8(viewM210638a7, pointerId);
            if ((this.f45789a7[pointerId] & this.f45798b6) != 0) {
                cq0Var.mo212506c4();
                return;
            }
            return;
        }
        if (actionMasked == 1) {
            if (this.f45782a0 == 1) {
                m210641b0();
            }
            m210631a0();
            return;
        }
        if (actionMasked == 2) {
            if (this.f45782a0 != 1) {
                int pointerCount = motionEvent.getPointerCount();
                for (int i2 = 0; i2 < pointerCount; i2++) {
                    int pointerId2 = motionEvent.getPointerId(i2);
                    if ((this.f45792b0 & (1 << pointerId2)) != 0) {
                        float x2 = motionEvent.getX(i2);
                        float y2 = motionEvent.getY(i2);
                        float f = x2 - this.f45785a3[pointerId2];
                        float f2 = y2 - this.f45786a4[pointerId2];
                        m210642b1(f, f2, pointerId2);
                        if (this.f45782a0 == 1) {
                            break;
                        }
                        View viewM210638a72 = m210638a7((int) x2, (int) y2);
                        if (m210634a3(viewM210638a72, f, f2) && m210649b8(viewM210638a72, pointerId2)) {
                            break;
                        }
                    }
                }
                m210644b3(motionEvent);
                return;
            }
            int i3 = this.f45784a2;
            if (((this.f45792b0 & (1 << i3)) != 0 ? 1 : 0) == 0) {
                return;
            }
            int iFindPointerIndex = motionEvent.findPointerIndex(i3);
            float x3 = motionEvent.getX(iFindPointerIndex);
            float y3 = motionEvent.getY(iFindPointerIndex);
            float[] fArr = this.f45787a5;
            int i4 = this.f45784a2;
            int i5 = (int) (x3 - fArr[i4]);
            int i6 = (int) (y3 - this.f45788a6[i4]);
            int left = this.f45801b9.getLeft() + i5;
            int top = this.f45801b9.getTop() + i6;
            int left2 = this.f45801b9.getLeft();
            int top2 = this.f45801b9.getTop();
            if (i5 != 0) {
                left = cq0Var.mo212501a5(this.f45801b9, left);
                WeakHashMap weakHashMap = xa1.f61054a0;
                this.f45801b9.offsetLeftAndRight(left - left2);
            }
            if (i6 != 0) {
                top = cq0Var.mo212502a6(this.f45801b9, top);
                WeakHashMap weakHashMap2 = xa1.f61054a0;
                this.f45801b9.offsetTopAndBottom(top - top2);
            }
            if (i5 != 0 || i6 != 0) {
                cq0Var.mo212514d2(this.f45801b9, left, top);
            }
            m210644b3(motionEvent);
            return;
        }
        if (actionMasked == 3) {
            if (this.f45782a0 == 1) {
                this.f45802c0 = true;
                cq0Var.mo212515d3(this.f45801b9, 0.0f, 0.0f);
                this.f45802c0 = false;
                if (this.f45782a0 == 1) {
                    m210645b4(0);
                }
            }
            m210631a0();
            return;
        }
        if (actionMasked != 5) {
            if (actionMasked != 6) {
                return;
            }
            int pointerId3 = motionEvent.getPointerId(actionIndex);
            if (this.f45782a0 == 1 && pointerId3 == this.f45784a2) {
                int pointerCount2 = motionEvent.getPointerCount();
                while (true) {
                    if (i >= pointerCount2) {
                        i = -1;
                        break;
                    }
                    int pointerId4 = motionEvent.getPointerId(i);
                    if (pointerId4 != this.f45784a2) {
                        View viewM210638a73 = m210638a7((int) motionEvent.getX(i), (int) motionEvent.getY(i));
                        View view = this.f45801b9;
                        if (viewM210638a73 == view && m210649b8(view, pointerId4)) {
                            i = this.f45784a2;
                            break;
                        }
                    }
                    i++;
                }
                if (i == -1) {
                    m210641b0();
                }
            }
            m210635a4(pointerId3);
            return;
        }
        int pointerId5 = motionEvent.getPointerId(actionIndex);
        float x4 = motionEvent.getX(actionIndex);
        float y4 = motionEvent.getY(actionIndex);
        m210643b2(x4, y4, pointerId5);
        if (this.f45782a0 == 0) {
            m210649b8(m210638a7((int) x4, (int) y4), pointerId5);
            if ((this.f45789a7[pointerId5] & this.f45798b6) != 0) {
                cq0Var.mo212506c4();
                return;
            }
            return;
        }
        int i7 = (int) x4;
        int i8 = (int) y4;
        View view2 = this.f45801b9;
        if (view2 != null && i7 >= view2.getLeft() && i7 < view2.getRight() && i8 >= view2.getTop() && i8 < view2.getBottom()) {
            i = 1;
        }
        if (i != 0) {
            m210649b8(this.f45801b9, pointerId5);
        }
    }

    /* renamed from: b0 */
    public final void m210641b0() {
        VelocityTracker velocityTracker = this.f45793b1;
        float f = this.f45794b2;
        velocityTracker.computeCurrentVelocity(1000, f);
        float xVelocity = this.f45793b1.getXVelocity(this.f45784a2);
        float f2 = this.f45795b3;
        float fAbs = Math.abs(xVelocity);
        if (fAbs < f2) {
            xVelocity = 0.0f;
        } else if (fAbs > f) {
            xVelocity = xVelocity > 0.0f ? f : -f;
        }
        float yVelocity = this.f45793b1.getYVelocity(this.f45784a2);
        float f3 = this.f45795b3;
        float fAbs2 = Math.abs(yVelocity);
        if (fAbs2 < f3) {
            f = 0.0f;
        } else if (fAbs2 <= f) {
            f = yVelocity;
        } else if (yVelocity <= 0.0f) {
            f = -f;
        }
        this.f45802c0 = true;
        this.f45800b8.mo212515d3(this.f45801b9, xVelocity, f);
        this.f45802c0 = false;
        if (this.f45782a0 == 1) {
            m210645b4(0);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v15 */
    /* JADX WARN: Type inference failed for: r0v16 */
    /* JADX WARN: Type inference failed for: r0v4, types: [int] */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r3v3, types: [cq0] */
    /* renamed from: b1 */
    public final void m210642b1(float f, float f2, int i) {
        boolean zM210633a2 = m210633a2(f, f2, i, 1);
        boolean z = zM210633a2;
        if (m210633a2(f2, f, i, 4)) {
            z = (zM210633a2 ? 1 : 0) | 4;
        }
        boolean z2 = z;
        if (m210633a2(f, f2, i, 2)) {
            z2 = (z ? 1 : 0) | 2;
        }
        ?? r0 = z2;
        if (m210633a2(f2, f, i, 8)) {
            r0 = (z2 ? 1 : 0) | 8;
        }
        if (r0 != 0) {
            int[] iArr = this.f45790a8;
            iArr[i] = iArr[i] | r0;
            this.f45800b8.mo212505c3(r0, i);
        }
    }

    /* renamed from: b2 */
    public final void m210643b2(float f, float f2, int i) {
        float[] fArr = this.f45785a3;
        if (fArr == null || fArr.length <= i) {
            int i2 = i + 1;
            float[] fArr2 = new float[i2];
            float[] fArr3 = new float[i2];
            float[] fArr4 = new float[i2];
            float[] fArr5 = new float[i2];
            int[] iArr = new int[i2];
            int[] iArr2 = new int[i2];
            int[] iArr3 = new int[i2];
            if (fArr != null) {
                System.arraycopy(fArr, 0, fArr2, 0, fArr.length);
                float[] fArr6 = this.f45786a4;
                System.arraycopy(fArr6, 0, fArr3, 0, fArr6.length);
                float[] fArr7 = this.f45787a5;
                System.arraycopy(fArr7, 0, fArr4, 0, fArr7.length);
                float[] fArr8 = this.f45788a6;
                System.arraycopy(fArr8, 0, fArr5, 0, fArr8.length);
                int[] iArr4 = this.f45789a7;
                System.arraycopy(iArr4, 0, iArr, 0, iArr4.length);
                int[] iArr5 = this.f45790a8;
                System.arraycopy(iArr5, 0, iArr2, 0, iArr5.length);
                int[] iArr6 = this.f45791a9;
                System.arraycopy(iArr6, 0, iArr3, 0, iArr6.length);
            }
            this.f45785a3 = fArr2;
            this.f45786a4 = fArr3;
            this.f45787a5 = fArr4;
            this.f45788a6 = fArr5;
            this.f45789a7 = iArr;
            this.f45790a8 = iArr2;
            this.f45791a9 = iArr3;
        }
        float[] fArr9 = this.f45785a3;
        this.f45787a5[i] = f;
        fArr9[i] = f;
        float[] fArr10 = this.f45786a4;
        this.f45788a6[i] = f2;
        fArr10[i] = f2;
        int[] iArr7 = this.f45789a7;
        int i3 = (int) f;
        int i4 = (int) f2;
        ViewGroup viewGroup = this.f45803c1;
        int i5 = i3 < viewGroup.getLeft() + this.f45796b4 ? 1 : 0;
        if (i4 < viewGroup.getTop() + this.f45796b4) {
            i5 |= 4;
        }
        if (i3 > viewGroup.getRight() - this.f45796b4) {
            i5 |= 2;
        }
        if (i4 > viewGroup.getBottom() - this.f45796b4) {
            i5 |= 8;
        }
        iArr7[i] = i5;
        this.f45792b0 |= 1 << i;
    }

    /* renamed from: b3 */
    public final void m210644b3(MotionEvent motionEvent) {
        int pointerCount = motionEvent.getPointerCount();
        for (int i = 0; i < pointerCount; i++) {
            int pointerId = motionEvent.getPointerId(i);
            if ((this.f45792b0 & (1 << pointerId)) != 0) {
                float x = motionEvent.getX(i);
                float y = motionEvent.getY(i);
                this.f45787a5[pointerId] = x;
                this.f45788a6[pointerId] = y;
            }
        }
    }

    /* renamed from: b4 */
    public final void m210645b4(int i) {
        this.f45803c1.removeCallbacks(this.f45804c2);
        if (this.f45782a0 != i) {
            this.f45782a0 = i;
            this.f45800b8.mo212513d1(i);
            if (this.f45782a0 == 0) {
                this.f45801b9 = null;
            }
        }
    }

    /* renamed from: b5 */
    public final boolean m210646b5(int i, int i2) {
        if (this.f45802c0) {
            return m210639a8(i, i2, (int) this.f45793b1.getXVelocity(this.f45784a2), (int) this.f45793b1.getYVelocity(this.f45784a2));
        }
        throw new IllegalStateException("Cannot settleCapturedViewAt outside of a call to Callback#onViewReleased");
    }

    /* JADX WARN: Removed duplicated region for block: B:53:0x00dd  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00f5  */
    /* renamed from: b6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean m210647b6(MotionEvent motionEvent) {
        View viewM210638a7;
        int actionMasked = motionEvent.getActionMasked();
        int actionIndex = motionEvent.getActionIndex();
        if (actionMasked == 0) {
            m210631a0();
        }
        if (this.f45793b1 == null) {
            this.f45793b1 = VelocityTracker.obtain();
        }
        this.f45793b1.addMovement(motionEvent);
        cq0 cq0Var = this.f45800b8;
        if (actionMasked == 0) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            int pointerId = motionEvent.getPointerId(0);
            m210643b2(x, y, pointerId);
            View viewM210638a72 = m210638a7((int) x, (int) y);
            if (viewM210638a72 == this.f45801b9 && this.f45782a0 == 2) {
                m210649b8(viewM210638a72, pointerId);
            }
            if ((this.f45789a7[pointerId] & this.f45798b6) != 0) {
                cq0Var.mo212506c4();
            }
        } else if (actionMasked == 1) {
            m210631a0();
        } else if (actionMasked != 2) {
            if (actionMasked != 3) {
                if (actionMasked == 5) {
                    int pointerId2 = motionEvent.getPointerId(actionIndex);
                    float x2 = motionEvent.getX(actionIndex);
                    float y2 = motionEvent.getY(actionIndex);
                    m210643b2(x2, y2, pointerId2);
                    int i = this.f45782a0;
                    if (i == 0) {
                        if ((this.f45789a7[pointerId2] & this.f45798b6) != 0) {
                            cq0Var.mo212506c4();
                        }
                    } else if (i == 2 && (viewM210638a7 = m210638a7((int) x2, (int) y2)) == this.f45801b9) {
                        m210649b8(viewM210638a7, pointerId2);
                    }
                } else if (actionMasked == 6) {
                    m210635a4(motionEvent.getPointerId(actionIndex));
                }
            }
        } else if (this.f45785a3 != null && this.f45786a4 != null) {
            int pointerCount = motionEvent.getPointerCount();
            for (int i2 = 0; i2 < pointerCount; i2++) {
                int pointerId3 = motionEvent.getPointerId(i2);
                if ((this.f45792b0 & (1 << pointerId3)) != 0) {
                    float x3 = motionEvent.getX(i2);
                    float y3 = motionEvent.getY(i2);
                    float f = x3 - this.f45785a3[pointerId3];
                    float f2 = y3 - this.f45786a4[pointerId3];
                    View viewM210638a73 = m210638a7((int) x3, (int) y3);
                    boolean z = viewM210638a73 != null && m210634a3(viewM210638a73, f, f2);
                    if (z) {
                        int left = viewM210638a73.getLeft();
                        int iMo212501a5 = cq0Var.mo212501a5(viewM210638a73, ((int) f) + left);
                        int top = viewM210638a73.getTop();
                        int iMo212502a6 = cq0Var.mo212502a6(viewM210638a73, ((int) f2) + top);
                        int iMo212503b6 = cq0Var.mo212503b6(viewM210638a73);
                        int iMo212504b7 = cq0Var.mo212504b7();
                        if ((iMo212503b6 == 0 || (iMo212503b6 > 0 && iMo212501a5 == left)) && (iMo212504b7 == 0 || (iMo212504b7 > 0 && iMo212502a6 == top))) {
                            break;
                        }
                        m210642b1(f, f2, pointerId3);
                        if (this.f45782a0 == 1 || (z && m210649b8(viewM210638a73, pointerId3))) {
                            break;
                        }
                    }
                }
            }
            m210644b3(motionEvent);
        }
        return this.f45782a0 == 1;
    }

    /* renamed from: b7 */
    public final boolean m210648b7(View view, int i, int i2) {
        this.f45801b9 = view;
        this.f45784a2 = -1;
        boolean zM210639a8 = m210639a8(i, i2, 0, 0);
        if (!zM210639a8 && this.f45782a0 == 0 && this.f45801b9 != null) {
            this.f45801b9 = null;
        }
        return zM210639a8;
    }

    /* renamed from: b8 */
    public final boolean m210649b8(View view, int i) {
        if (view == this.f45801b9 && this.f45784a2 == i) {
            return true;
        }
        if (view == null || !this.f45800b8.mo212516e1(view, i)) {
            return false;
        }
        this.f45784a2 = i;
        m210632a1(view, i);
        return true;
    }
}
