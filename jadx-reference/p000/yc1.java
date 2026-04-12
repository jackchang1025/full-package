package p000;

import android.view.View;
import androidx.constraintlayout.motion.widget.MotionLayout;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class yc1 extends zc1 {

    /* renamed from: a6 */
    public boolean f61292a6;

    @Override // p000.zc1
    /* renamed from: a1 */
    public final boolean mo214921a1(float f, long j, C1105qc c1105qc, View view) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Method method;
        yc1 yc1Var;
        if (view instanceof MotionLayout) {
            float fM215392a0 = m215392a0(f, j, c1105qc, view);
            yc1Var = this;
            ((MotionLayout) view).setProgress(fM215392a0);
        } else {
            if (this.f61292a6) {
                return false;
            }
            try {
                method = view.getClass().getMethod("setProgress", Float.TYPE);
            } catch (NoSuchMethodException unused) {
                this.f61292a6 = true;
                method = null;
            }
            if (method != null) {
                try {
                    float fM215392a02 = m215392a0(f, j, c1105qc, view);
                    yc1Var = this;
                    try {
                        method.invoke(view, Float.valueOf(fM215392a02));
                    } catch (IllegalAccessException | InvocationTargetException unused2) {
                    }
                } catch (IllegalAccessException | InvocationTargetException unused3) {
                    yc1Var = this;
                }
            } else {
                yc1Var = this;
            }
        }
        return yc1Var.f61504a3;
    }
}
