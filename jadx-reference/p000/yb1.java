package p000;

import android.view.View;
import androidx.constraintlayout.motion.widget.MotionLayout;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class yb1 extends zb1 {

    /* renamed from: a3 */
    public boolean f61289a3;

    @Override // p000.zb1
    /* renamed from: a3 */
    public final void mo214920a3(View view, float f) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Method method;
        if (view instanceof MotionLayout) {
            ((MotionLayout) view).setProgress(m215389a0(f));
            return;
        }
        if (this.f61289a3) {
            return;
        }
        try {
            method = view.getClass().getMethod("setProgress", Float.TYPE);
        } catch (NoSuchMethodException unused) {
            this.f61289a3 = true;
            method = null;
        }
        if (method != null) {
            try {
                method.invoke(view, Float.valueOf(m215389a0(f)));
            } catch (IllegalAccessException | InvocationTargetException unused2) {
            }
        }
    }
}
