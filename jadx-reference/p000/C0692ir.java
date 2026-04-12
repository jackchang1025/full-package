package p000;

import androidx.lifecycle.Lifecycle$Event;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ir */
/* loaded from: classes.dex */
public final class C0692ir {

    /* renamed from: a2 */
    public static final C0692ir f57220a2 = new C0692ir();

    /* renamed from: a0 */
    public final HashMap f57221a0 = new HashMap();

    /* renamed from: a1 */
    public final HashMap f57222a1 = new HashMap();

    /* renamed from: a1 */
    public static void m213187a1(HashMap map, C0691iq c0691iq, Lifecycle$Event lifecycle$Event, Class cls) {
        Lifecycle$Event lifecycle$Event2 = (Lifecycle$Event) map.get(c0691iq);
        if (lifecycle$Event2 == null || lifecycle$Event == lifecycle$Event2) {
            if (lifecycle$Event2 == null) {
                map.put(c0691iq, lifecycle$Event);
                return;
            }
            return;
        }
        throw new IllegalArgumentException("Method " + c0691iq.f57219a1.getName() + " in " + cls.getName() + " already declared with different @OnLifecycleEvent value: previous value " + lifecycle$Event2 + ", new value " + lifecycle$Event);
    }

    /* renamed from: a0 */
    public final C0690ip m213188a0(Class cls, Method[] methodArr) throws SecurityException {
        int i;
        Class superclass = cls.getSuperclass();
        HashMap map = new HashMap();
        HashMap map2 = this.f57221a0;
        if (superclass != null) {
            C0690ip c0690ipM213188a0 = (C0690ip) map2.get(superclass);
            if (c0690ipM213188a0 == null) {
                c0690ipM213188a0 = m213188a0(superclass, null);
            }
            map.putAll(c0690ipM213188a0.f57217a1);
        }
        for (Class<?> cls2 : cls.getInterfaces()) {
            C0690ip c0690ipM213188a02 = (C0690ip) map2.get(cls2);
            if (c0690ipM213188a02 == null) {
                c0690ipM213188a02 = m213188a0(cls2, null);
            }
            for (Map.Entry entry : c0690ipM213188a02.f57217a1.entrySet()) {
                m213187a1(map, (C0691iq) entry.getKey(), (Lifecycle$Event) entry.getValue(), cls);
            }
        }
        if (methodArr == null) {
            try {
                methodArr = cls.getDeclaredMethods();
            } catch (NoClassDefFoundError e) {
                throw new IllegalArgumentException("The observer class has some methods that use newer APIs which are not available in the current OS version. Lifecycles cannot access even other methods so you should make sure that your observer classes only access framework classes that are available in your min API level OR use lifecycle:compiler annotation processor.", e);
            }
        }
        boolean z = false;
        for (Method method : methodArr) {
            al0 al0Var = (al0) method.getAnnotation(al0.class);
            if (al0Var != null) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length <= 0) {
                    i = 0;
                } else {
                    if (!ka0.class.isAssignableFrom(parameterTypes[0])) {
                        throw new IllegalArgumentException("invalid parameter type. Must be one and instanceof LifecycleOwner");
                    }
                    i = 1;
                }
                Lifecycle$Event lifecycle$EventValue = al0Var.value();
                if (parameterTypes.length > 1) {
                    if (!Lifecycle$Event.class.isAssignableFrom(parameterTypes[1])) {
                        throw new IllegalArgumentException("invalid parameter type. second arg must be an event");
                    }
                    if (lifecycle$EventValue != Lifecycle$Event.ON_ANY) {
                        throw new IllegalArgumentException("Second arg is supported only for ON_ANY value");
                    }
                    i = 2;
                }
                if (parameterTypes.length > 2) {
                    throw new IllegalArgumentException("cannot have more than 2 params");
                }
                m213187a1(map, new C0691iq(i, method), lifecycle$EventValue, cls);
                z = true;
            }
        }
        C0690ip c0690ip = new C0690ip(map);
        map2.put(cls, c0690ip);
        this.f57222a1.put(cls, Boolean.valueOf(z));
        return c0690ip;
    }
}
