package p000;

import android.graphics.Matrix;
import android.os.Build;
import android.view.View;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public class jd1 extends t60 {

    /* renamed from: c6 */
    public static boolean f57320c6 = true;

    /* renamed from: c7 */
    public static boolean f57321c7 = true;

    /* renamed from: c8 */
    public static boolean f57322c8 = true;

    /* renamed from: c9 */
    public static boolean f57323c9 = true;

    @Override // p000.t60
    /* renamed from: f0 */
    public void mo213284f0(View view, int i) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        if (Build.VERSION.SDK_INT == 28) {
            super.mo213284f0(view, i);
        } else if (f57323c9) {
            try {
                view.setTransitionVisibility(i);
            } catch (NoSuchMethodError unused) {
                f57323c9 = false;
            }
        }
    }

    /* renamed from: f5 */
    public void mo213285f5(View view, int i, int i2, int i3, int i4) {
        if (f57322c8) {
            try {
                view.setLeftTopRightBottom(i, i2, i3, i4);
            } catch (NoSuchMethodError unused) {
                f57322c8 = false;
            }
        }
    }

    /* renamed from: f6 */
    public void mo213286f6(View view, Matrix matrix) {
        if (f57320c6) {
            try {
                view.transformMatrixToGlobal(matrix);
            } catch (NoSuchMethodError unused) {
                f57320c6 = false;
            }
        }
    }

    /* renamed from: f7 */
    public void mo213287f7(View view, Matrix matrix) {
        if (f57321c7) {
            try {
                view.transformMatrixToLocal(matrix);
            } catch (NoSuchMethodError unused) {
                f57321c7 = false;
            }
        }
    }
}
