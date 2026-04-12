package p000;

import java.lang.reflect.Method;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public abstract class nn0 {

    /* renamed from: a0 */
    public static final Method f58675a0;

    static {
        Method method;
        Method[] methods = Throwable.class.getMethods();
        t60.m214694b5(methods, "throwableMethods");
        int length = methods.length;
        int i = 0;
        while (true) {
            method = null;
            if (i >= length) {
                break;
            }
            Method method2 = methods[i];
            if (t60.m214686a2(method2.getName(), "addSuppressed")) {
                Class<?>[] parameterTypes = method2.getParameterTypes();
                t60.m214694b5(parameterTypes, "it.parameterTypes");
                if (t60.m214686a2(parameterTypes.length == 1 ? parameterTypes[0] : null, Throwable.class)) {
                    method = method2;
                    break;
                }
            }
            i++;
        }
        f58675a0 = method;
        int length2 = methods.length;
        for (int i2 = 0; i2 < length2 && !t60.m214686a2(methods[i2].getName(), "getSuppressed"); i2++) {
        }
    }
}
