package p000;

import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import java.lang.reflect.Method;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: uh */
/* loaded from: classes.dex */
public abstract class AbstractC1300uh {

    /* renamed from: a0 */
    public static final Method f60423a0;

    /* renamed from: a1 */
    public static final Method f60424a1;

    /* renamed from: a2 */
    public static final Method f60425a2;

    /* renamed from: a3 */
    public static final boolean f60426a3;

    static {
        try {
            Class cls = Integer.TYPE;
            Class cls2 = Boolean.TYPE;
            Class cls3 = Float.TYPE;
            Method declaredMethod = AbsListView.class.getDeclaredMethod("positionSelector", cls, View.class, cls2, cls3, cls3);
            f60423a0 = declaredMethod;
            declaredMethod.setAccessible(true);
            Method declaredMethod2 = AdapterView.class.getDeclaredMethod("setSelectedPositionInt", cls);
            f60424a1 = declaredMethod2;
            declaredMethod2.setAccessible(true);
            Method declaredMethod3 = AdapterView.class.getDeclaredMethod("setNextSelectedPositionInt", cls);
            f60425a2 = declaredMethod3;
            declaredMethod3.setAccessible(true);
            f60426a3 = true;
        } catch (NoSuchMethodException unused) {
        }
    }
}
