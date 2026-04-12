package p000;

import androidx.fragment.app.AbstractComponentCallbacksC0069a5;
import androidx.fragment.app.C0071a7;
import androidx.fragment.app.Fragment$InstantiationException;
import java.lang.reflect.InvocationTargetException;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class e00 {

    /* renamed from: a1 */
    public static final t01 f55893a1 = new t01();

    /* renamed from: a0 */
    public final /* synthetic */ C0071a7 f55894a0;

    public e00(C0071a7 c0071a7) {
        this.f55894a0 = c0071a7;
    }

    /* renamed from: a1 */
    public static Class m212646a1(ClassLoader classLoader, String str) throws ClassNotFoundException {
        t01 t01Var = f55893a1;
        t01 t01Var2 = (t01) t01Var.getOrDefault(classLoader, null);
        if (t01Var2 == null) {
            t01Var2 = new t01();
            t01Var.put(classLoader, t01Var2);
        }
        Class cls = (Class) t01Var2.getOrDefault(str, null);
        if (cls != null) {
            return cls;
        }
        Class<?> cls2 = Class.forName(str, false, classLoader);
        t01Var2.put(str, cls2);
        return cls2;
    }

    /* renamed from: a2 */
    public static Class m212647a2(ClassLoader classLoader, String str) {
        try {
            return m212646a1(classLoader, str);
        } catch (ClassCastException e) {
            throw new Fragment$InstantiationException(AbstractC0003a2.m33b4("Unable to instantiate fragment ", str, ": make sure class is a valid subclass of Fragment"), e);
        } catch (ClassNotFoundException e2) {
            throw new Fragment$InstantiationException(AbstractC0003a2.m33b4("Unable to instantiate fragment ", str, ": make sure class name exists"), e2);
        }
    }

    /* renamed from: a0 */
    public final AbstractComponentCallbacksC0069a5 m212648a0(String str) {
        try {
            return (AbstractComponentCallbacksC0069a5) m212647a2(this.f55894a0.f45135b3.f61419c7.getClassLoader(), str).getConstructor(null).newInstance(null);
        } catch (IllegalAccessException e) {
            throw new Fragment$InstantiationException(AbstractC0003a2.m33b4("Unable to instantiate fragment ", str, ": make sure class name exists, is public, and has an empty constructor that is public"), e);
        } catch (InstantiationException e2) {
            throw new Fragment$InstantiationException(AbstractC0003a2.m33b4("Unable to instantiate fragment ", str, ": make sure class name exists, is public, and has an empty constructor that is public"), e2);
        } catch (NoSuchMethodException e3) {
            throw new Fragment$InstantiationException(AbstractC0003a2.m33b4("Unable to instantiate fragment ", str, ": could not find Fragment constructor"), e3);
        } catch (InvocationTargetException e4) {
            throw new Fragment$InstantiationException(AbstractC0003a2.m33b4("Unable to instantiate fragment ", str, ": calling Fragment constructor caused an exception"), e4);
        }
    }
}
