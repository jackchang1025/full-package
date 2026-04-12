package p000;

import android.app.Application;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class au0 {

    /* renamed from: a0 */
    public static final List f45643a0 = AbstractC0716jf.m213306g5(Application.class, pt0.class);

    /* renamed from: a1 */
    public static final List f45644a1 = AbstractC1117qo.m214451e7(pt0.class);

    /* renamed from: a0 */
    public static final Constructor m210522a0(Class cls, List list) throws SecurityException {
        t60.m214695b6(list, "signature");
        Constructor<?>[] constructors = cls.getConstructors();
        t60.m214694b5(constructors, "modelClass.constructors");
        for (Constructor<?> constructor : constructors) {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            t60.m214694b5(parameterTypes, "constructor.parameterTypes");
            List listM210733f6 = AbstractC0134bh.m210733f6(parameterTypes);
            if (list.equals(listM210733f6)) {
                return constructor;
            }
            if (list.size() == listM210733f6.size() && listM210733f6.containsAll(list)) {
                throw new UnsupportedOperationException("Class " + cls.getSimpleName() + " must have parameters in the proper order: " + list);
            }
        }
        return null;
    }

    /* renamed from: a1 */
    public static final ib1 m210523a1(Class cls, Constructor constructor, Object... objArr) {
        try {
            return (ib1) constructor.newInstance(Arrays.copyOf(objArr, objArr.length));
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to access " + cls, e);
        } catch (InstantiationException e2) {
            throw new RuntimeException("A " + cls + " cannot be instantiated.", e2);
        } catch (InvocationTargetException e3) {
            throw new RuntimeException("An exception happened in constructor of " + cls, e3.getCause());
        }
    }
}
