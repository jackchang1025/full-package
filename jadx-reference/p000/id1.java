package p000;

import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class id1 {

    /* renamed from: a0 */
    public static final Method f56869a0;

    /* renamed from: a1 */
    public static final boolean f56870a1;

    static {
        f56870a1 = Build.VERSION.SDK_INT >= 27;
        try {
            Method declaredMethod = View.class.getDeclaredMethod("computeFitSystemWindows", Rect.class, Rect.class);
            f56869a0 = declaredMethod;
            if (declaredMethod.isAccessible()) {
                return;
            }
            declaredMethod.setAccessible(true);
        } catch (NoSuchMethodException unused) {
        }
    }

    /* renamed from: a0 */
    public static boolean m213156a0(View view) {
        WeakHashMap weakHashMap = xa1.f61054a0;
        return ga1.m212904a3(view) == 1;
    }
}
