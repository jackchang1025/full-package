package p000;

import android.graphics.Rect;
import android.view.animation.Interpolator;
import androidx.constraintlayout.motion.widget.MotionLayout;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class ad1 {

    /* renamed from: a0 */
    public final int f43624a0;

    /* renamed from: a1 */
    public final int f43625a1;

    /* renamed from: a2 */
    public final og0 f43626a2;

    /* renamed from: a3 */
    public final int f43627a3;

    /* renamed from: a5 */
    public final C1094q2 f43629a5;

    /* renamed from: a6 */
    public final Interpolator f43630a6;

    /* renamed from: a8 */
    public float f43632a8;

    /* renamed from: a9 */
    public float f43633a9;

    /* renamed from: b2 */
    public final boolean f43636b2;

    /* renamed from: a4 */
    public final C1105qc f43628a4 = new C1105qc(1);

    /* renamed from: a7 */
    public boolean f43631a7 = false;

    /* renamed from: b1 */
    public final Rect f43635b1 = new Rect();

    /* renamed from: b0 */
    public long f43634b0 = System.nanoTime();

    public ad1(C1094q2 c1094q2, og0 og0Var, int i, int i2, int i3, Interpolator interpolator, int i4, int i5) {
        this.f43636b2 = false;
        this.f43629a5 = c1094q2;
        this.f43626a2 = og0Var;
        this.f43627a3 = i2;
        if (((ArrayList) c1094q2.f59367a3) == null) {
            c1094q2.f59367a3 = new ArrayList();
        }
        ((ArrayList) c1094q2.f59367a3).add(this);
        this.f43630a6 = interpolator;
        this.f43624a0 = i4;
        this.f43625a1 = i5;
        if (i3 == 3) {
            this.f43636b2 = true;
        }
        this.f43633a9 = i == 0 ? Float.MAX_VALUE : 1.0f / i;
        m209790a0();
    }

    /* renamed from: a0 */
    public final void m209790a0() {
        boolean z = this.f43631a7;
        int i = this.f43625a1;
        int i2 = this.f43624a0;
        Interpolator interpolator = this.f43630a6;
        C1094q2 c1094q2 = this.f43629a5;
        og0 og0Var = this.f43626a2;
        if (!z) {
            long jNanoTime = System.nanoTime();
            long j = jNanoTime - this.f43634b0;
            this.f43634b0 = jNanoTime;
            float f = (((float) (j * 1.0E-6d)) * this.f43633a9) + this.f43632a8;
            this.f43632a8 = f;
            if (f >= 1.0f) {
                this.f43632a8 = 1.0f;
            }
            boolean zM214196a4 = og0Var.m214196a4(interpolator == null ? this.f43632a8 : interpolator.getInterpolation(this.f43632a8), jNanoTime, this.f43628a4, og0Var.f58800a1);
            if (this.f43632a8 >= 1.0f) {
                if (i2 != -1) {
                    og0Var.f58800a1.setTag(i2, Long.valueOf(System.nanoTime()));
                }
                if (i != -1) {
                    og0Var.f58800a1.setTag(i, null);
                }
                if (!this.f43636b2) {
                    ((ArrayList) c1094q2.f59369a5).add(this);
                }
            }
            if (this.f43632a8 < 1.0f || zM214196a4) {
                ((MotionLayout) c1094q2.f59365a1).invalidate();
                return;
            }
            return;
        }
        long jNanoTime2 = System.nanoTime();
        long j2 = jNanoTime2 - this.f43634b0;
        this.f43634b0 = jNanoTime2;
        float f2 = this.f43632a8 - (((float) (j2 * 1.0E-6d)) * this.f43633a9);
        this.f43632a8 = f2;
        if (f2 < 0.0f) {
            this.f43632a8 = 0.0f;
        }
        float interpolation = this.f43632a8;
        if (interpolator != null) {
            interpolation = interpolator.getInterpolation(interpolation);
        }
        boolean zM214196a42 = og0Var.m214196a4(interpolation, jNanoTime2, this.f43628a4, og0Var.f58800a1);
        if (this.f43632a8 <= 0.0f) {
            if (i2 != -1) {
                og0Var.f58800a1.setTag(i2, Long.valueOf(System.nanoTime()));
            }
            if (i != -1) {
                og0Var.f58800a1.setTag(i, null);
            }
            ((ArrayList) c1094q2.f59369a5).add(this);
        }
        if (this.f43632a8 > 0.0f || zM214196a42) {
            ((MotionLayout) c1094q2.f59365a1).invalidate();
        }
    }

    /* renamed from: a1 */
    public final void m209791a1() {
        this.f43631a7 = true;
        int i = this.f43627a3;
        if (i != -1) {
            this.f43633a9 = i == 0 ? Float.MAX_VALUE : 1.0f / i;
        }
        ((MotionLayout) this.f43629a5.f59365a1).invalidate();
        this.f43634b0 = System.nanoTime();
    }
}
