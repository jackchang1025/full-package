package p000;

import android.view.View;
import androidx.constraintlayout.motion.widget.MotionLayout;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class sc1 extends tc1 {

    /* renamed from: a5 */
    public boolean f59954a5;

    @Override // p000.tc1
    /* renamed from: a2 */
    public final void mo214245a2(View view, float f) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Method method;
        if (view instanceof MotionLayout) {
            ((MotionLayout) view).setProgress(m214736a0(f));
            return;
        }
        if (this.f59954a5) {
            return;
        }
        try {
            method = view.getClass().getMethod("setProgress", Float.TYPE);
        } catch (NoSuchMethodException unused) {
            this.f59954a5 = true;
            method = null;
        }
        if (method != null) {
            try {
                method.invoke(view, Float.valueOf(m214736a0(f)));
            } catch (IllegalAccessException | InvocationTargetException unused2) {
            }
        }
    }
}
