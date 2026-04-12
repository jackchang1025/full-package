package com.storm.safe.rock.util;

import android.content.Context;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class ReflectApi {
    public static final ReflectApi INSTANCE = new ReflectApi();

    private ReflectApi() {
    }

    private final Method findMethod(Class<?> cls, String str, Class<?>[] clsArr) throws SecurityException {
        try {
            try {
                return cls.getMethod(str, (Class[]) Arrays.copyOf(clsArr, clsArr.length));
            } catch (NoSuchMethodException unused) {
                while (cls != null) {
                    Method[] declaredMethods = cls.getDeclaredMethods();
                    t60.m214694b5(declaredMethods, "current.declaredMethods");
                    for (Method method : declaredMethods) {
                        if (t60.m214686a2(method.getName(), str) && method.getParameterTypes().length == clsArr.length) {
                            return method;
                        }
                    }
                    cls = cls.getSuperclass();
                }
                return null;
            }
        } catch (NoSuchMethodException unused2) {
            return cls.getDeclaredMethod(str, (Class[]) Arrays.copyOf(clsArr, clsArr.length));
        }
    }

    public final Object callMethod(Object obj, String str, Object... objArr) throws SecurityException {
        t60.m214695b6(obj, "obj");
        t60.m214695b6(str, "methodName");
        t60.m214695b6(objArr, "args");
        try {
            ArrayList arrayList = new ArrayList(objArr.length);
            int length = objArr.length;
            for (int i = 0; i < length; i++) {
                Object obj2 = objArr[i];
                Class<?> cls = obj2 != null ? obj2.getClass() : null;
                if (cls == null) {
                    cls = Object.class;
                }
                arrayList.add(cls);
            }
            Method methodFindMethod = findMethod(obj.getClass(), str, (Class[]) arrayList.toArray(new Class[0]));
            if (methodFindMethod == null) {
                return null;
            }
            methodFindMethod.setAccessible(true);
            return methodFindMethod.invoke(obj, Arrays.copyOf(objArr, objArr.length));
        } catch (Exception unused) {
            return null;
        }
    }

    public final Object callMethodTyped(Object obj, String str, Class<?>[] clsArr, Object... objArr) throws SecurityException {
        t60.m214695b6(obj, "obj");
        t60.m214695b6(str, "methodName");
        t60.m214695b6(clsArr, "paramTypes");
        t60.m214695b6(objArr, "args");
        try {
            Method methodFindMethod = findMethod(obj.getClass(), str, clsArr);
            if (methodFindMethod == null) {
                return null;
            }
            methodFindMethod.setAccessible(true);
            return methodFindMethod.invoke(obj, Arrays.copyOf(objArr, objArr.length));
        } catch (Exception unused) {
            return null;
        }
    }

    public final Object callStaticMethod(String str, String str2, Class<?>[] clsArr, Object... objArr) throws NoSuchMethodException, SecurityException {
        t60.m214695b6(str, "className");
        t60.m214695b6(str2, "methodName");
        t60.m214695b6(clsArr, "paramTypes");
        t60.m214695b6(objArr, "args");
        try {
            Method declaredMethod = Class.forName(str).getDeclaredMethod(str2, (Class[]) Arrays.copyOf(clsArr, clsArr.length));
            declaredMethod.setAccessible(true);
            return declaredMethod.invoke(null, Arrays.copyOf(objArr, objArr.length));
        } catch (Exception unused) {
            return null;
        }
    }

    public final Object getField(Object obj, String str) throws NoSuchFieldException, SecurityException {
        t60.m214695b6(obj, "obj");
        t60.m214695b6(str, "fieldName");
        try {
            Field declaredField = obj.getClass().getDeclaredField(str);
            declaredField.setAccessible(true);
            return declaredField.get(obj);
        } catch (Exception unused) {
            return null;
        }
    }

    public final Object getStaticField(String str, String str2) throws NoSuchFieldException, SecurityException {
        t60.m214695b6(str, "className");
        t60.m214695b6(str2, "fieldName");
        try {
            Field declaredField = Class.forName(str).getDeclaredField(str2);
            declaredField.setAccessible(true);
            return declaredField.get(null);
        } catch (Exception unused) {
            return null;
        }
    }

    public final Object getSystemService(Context context, String str) {
        t60.m214695b6(context, "context");
        t60.m214695b6(str, "serviceName");
        try {
            return Context.class.getMethod("getSystemService", String.class).invoke(context, str);
        } catch (Exception unused) {
            return null;
        }
    }

    public final Object newInstance(String str, Class<?>[] clsArr, Object... objArr) throws NoSuchMethodException, SecurityException {
        t60.m214695b6(str, "className");
        t60.m214695b6(clsArr, "paramTypes");
        t60.m214695b6(objArr, "args");
        try {
            Constructor<?> declaredConstructor = Class.forName(str).getDeclaredConstructor((Class[]) Arrays.copyOf(clsArr, clsArr.length));
            declaredConstructor.setAccessible(true);
            return declaredConstructor.newInstance(Arrays.copyOf(objArr, objArr.length));
        } catch (Exception unused) {
            return null;
        }
    }
}
