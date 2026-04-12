package p000;

import android.content.res.Resources;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class kb0 implements View.OnTouchListener {

    /* renamed from: b7 */
    public static final int f57490b7 = ViewConfiguration.getTapTimeout();

    /* renamed from: a0 */
    public final C0153bz f57491a0;

    /* renamed from: a1 */
    public final AccelerateInterpolator f57492a1;

    /* renamed from: a2 */
    public final C1304ul f57493a2;

    /* renamed from: a3 */
    public RunnableC0165ca f57494a3;

    /* renamed from: a4 */
    public final float[] f57495a4;

    /* renamed from: a5 */
    public final float[] f57496a5;

    /* renamed from: a6 */
    public final int f57497a6;

    /* renamed from: a7 */
    public final int f57498a7;

    /* renamed from: a8 */
    public final float[] f57499a8;

    /* renamed from: a9 */
    public final float[] f57500a9;

    /* renamed from: b0 */
    public final float[] f57501b0;

    /* renamed from: b1 */
    public boolean f57502b1;

    /* renamed from: b2 */
    public boolean f57503b2;

    /* renamed from: b3 */
    public boolean f57504b3;

    /* renamed from: b4 */
    public boolean f57505b4;

    /* renamed from: b5 */
    public boolean f57506b5;

    /* renamed from: b6 */
    public final C1304ul f57507b6;

    public kb0(C1304ul c1304ul) {
        C0153bz c0153bz = new C0153bz();
        c0153bz.f46016a4 = Long.MIN_VALUE;
        c0153bz.f46018a6 = -1L;
        c0153bz.f46017a5 = 0L;
        this.f57491a0 = c0153bz;
        this.f57492a1 = new AccelerateInterpolator();
        float[] fArr = {0.0f, 0.0f};
        this.f57495a4 = fArr;
        float[] fArr2 = {Float.MAX_VALUE, Float.MAX_VALUE};
        this.f57496a5 = fArr2;
        float[] fArr3 = {0.0f, 0.0f};
        this.f57499a8 = fArr3;
        float[] fArr4 = {0.0f, 0.0f};
        this.f57500a9 = fArr4;
        float[] fArr5 = {Float.MAX_VALUE, Float.MAX_VALUE};
        this.f57501b0 = fArr5;
        this.f57493a2 = c1304ul;
        float f = Resources.getSystem().getDisplayMetrics().density;
        float f2 = ((int) ((1575.0f * f) + 0.5f)) / 1000.0f;
        fArr5[0] = f2;
        fArr5[1] = f2;
        float f3 = ((int) ((f * 315.0f) + 0.5f)) / 1000.0f;
        fArr4[0] = f3;
        fArr4[1] = f3;
        this.f57497a6 = 1;
        fArr2[0] = Float.MAX_VALUE;
        fArr2[1] = Float.MAX_VALUE;
        fArr[0] = 0.2f;
        fArr[1] = 0.2f;
        fArr3[0] = 0.001f;
        fArr3[1] = 0.001f;
        this.f57498a7 = f57490b7;
        c0153bz.f46012a0 = 500;
        c0153bz.f46013a1 = 500;
        this.f57507b6 = c1304ul;
    }

    /* renamed from: a1 */
    public static float m213476a1(float f, float f2, float f3) {
        return f > f3 ? f3 : f < f2 ? f2 : f;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x003b A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:13:0x003c  */
    /* renamed from: a0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final float m213477a0(int i, float f, float f2, float f3) {
        float fM213476a1;
        float interpolation;
        float fM213476a12 = m213476a1(this.f57495a4[i] * f2, 0.0f, this.f57496a5[i]);
        float fM213478a2 = m213478a2(f2 - f, fM213476a12) - m213478a2(f, fM213476a12);
        AccelerateInterpolator accelerateInterpolator = this.f57492a1;
        if (fM213478a2 < 0.0f) {
            interpolation = -accelerateInterpolator.getInterpolation(-fM213478a2);
        } else {
            if (fM213478a2 <= 0.0f) {
                fM213476a1 = 0.0f;
                if (fM213476a1 != 0.0f) {
                    return 0.0f;
                }
                float f4 = this.f57499a8[i];
                float f5 = this.f57500a9[i];
                float f6 = this.f57501b0[i];
                float f7 = f4 * f3;
                return fM213476a1 > 0.0f ? m213476a1(fM213476a1 * f7, f5, f6) : -m213476a1((-fM213476a1) * f7, f5, f6);
            }
            interpolation = accelerateInterpolator.getInterpolation(fM213478a2);
        }
        fM213476a1 = m213476a1(interpolation, -1.0f, 1.0f);
        if (fM213476a1 != 0.0f) {
        }
    }

    /* renamed from: a2 */
    public final float m213478a2(float f, float f2) {
        if (f2 != 0.0f) {
            int i = this.f57497a6;
            if (i == 0 || i == 1) {
                if (f < f2) {
                    if (f >= 0.0f) {
                        return 1.0f - (f / f2);
                    }
                    if (this.f57505b4 && i == 1) {
                        return 1.0f;
                    }
                }
            } else if (i == 2 && f < 0.0f) {
                return f / (-f2);
            }
        }
        return 0.0f;
    }

    /* renamed from: a3 */
    public final void m213479a3() {
        int i = 0;
        if (this.f57503b2) {
            this.f57505b4 = false;
            return;
        }
        long jCurrentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
        C0153bz c0153bz = this.f57491a0;
        int i2 = (int) (jCurrentAnimationTimeMillis - c0153bz.f46016a4);
        int i3 = c0153bz.f46013a1;
        if (i2 > i3) {
            i = i3;
        } else if (i2 >= 0) {
            i = i2;
        }
        c0153bz.f46020a8 = i;
        c0153bz.f46019a7 = c0153bz.m210750a0(jCurrentAnimationTimeMillis);
        c0153bz.f46018a6 = jCurrentAnimationTimeMillis;
    }

    /* renamed from: a4 */
    public final boolean m213480a4() {
        C1304ul c1304ul;
        int count;
        C0153bz c0153bz = this.f57491a0;
        float f = c0153bz.f46015a3;
        int iAbs = (int) (f / Math.abs(f));
        Math.abs(c0153bz.f46014a2);
        if (iAbs != 0 && (count = (c1304ul = this.f57507b6).getCount()) != 0) {
            int childCount = c1304ul.getChildCount();
            int firstVisiblePosition = c1304ul.getFirstVisiblePosition();
            int i = firstVisiblePosition + childCount;
            if (iAbs <= 0 ? !(iAbs >= 0 || (firstVisiblePosition <= 0 && c1304ul.getChildAt(0).getTop() >= 0)) : !(i >= count && c1304ul.getChildAt(childCount - 1).getBottom() <= c1304ul.getHeight())) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0014, code lost:
    
        if (r0 != 3) goto L30;
     */
    @Override // android.view.View.OnTouchListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        int i;
        int i2 = 0;
        if (this.f57506b5) {
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked != 0) {
                if (actionMasked != 1) {
                    if (actionMasked != 2) {
                    }
                }
                m213479a3();
                return false;
            }
            this.f57504b3 = true;
            this.f57502b1 = false;
            float x = motionEvent.getX();
            float width = view.getWidth();
            C1304ul c1304ul = this.f57493a2;
            float fM213477a0 = m213477a0(0, x, width, c1304ul.getWidth());
            float fM213477a02 = m213477a0(1, motionEvent.getY(), view.getHeight(), c1304ul.getHeight());
            C0153bz c0153bz = this.f57491a0;
            c0153bz.f46014a2 = fM213477a0;
            c0153bz.f46015a3 = fM213477a02;
            if (!this.f57505b4 && m213480a4()) {
                if (this.f57494a3 == null) {
                    this.f57494a3 = new RunnableC0165ca(i2, this);
                }
                this.f57505b4 = true;
                this.f57503b2 = true;
                if (this.f57502b1 || (i = this.f57498a7) <= 0) {
                    this.f57494a3.run();
                } else {
                    RunnableC0165ca runnableC0165ca = this.f57494a3;
                    long j = i;
                    WeakHashMap weakHashMap = xa1.f61054a0;
                    fa1.m212776b3(c1304ul, runnableC0165ca, j);
                }
                this.f57502b1 = true;
            }
        }
        return false;
    }
}
