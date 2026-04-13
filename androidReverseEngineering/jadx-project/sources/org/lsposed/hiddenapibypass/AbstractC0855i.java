package org.lsposed.hiddenapibypass;

import android.util.Log;
import dalvik.system.VMRuntime;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import sun.misc.Unsafe;

/* renamed from: org.lsposed.hiddenapibypass.i */
/* loaded from: classes.dex */
public abstract class AbstractC0855i {

    /* renamed from: a */
    public static final Unsafe f1668a;

    /* renamed from: b */
    public static final long f1669b;

    /* renamed from: c */
    public static final long f1670c;

    /* renamed from: d */
    public static final long f1671d;

    /* renamed from: e */
    public static final long f1672e;

    /* renamed from: f */
    public static final long f1673f;

    static {
        long objectFieldOffset;
        try {
            Unsafe unsafe = (Unsafe) Unsafe.class.getDeclaredMethod("getUnsafe", new Class[0]).invoke(null, new Object[0]);
            f1668a = unsafe;
            C0847a c0847a = new C0847a();
            Class loadClass = c0847a.loadClass(Executable.class.getName());
            Class loadClass2 = c0847a.loadClass(MethodHandle.class.getName());
            Class loadClass3 = c0847a.loadClass(Class.class.getName());
            f1669b = unsafe.objectFieldOffset(loadClass.getDeclaredField("artMethod"));
            unsafe.objectFieldOffset(loadClass.getDeclaredField("declaringClass"));
            f1670c = unsafe.objectFieldOffset(loadClass2.getDeclaredField("artFieldOrMethod"));
            try {
                objectFieldOffset = unsafe.objectFieldOffset(loadClass3.getDeclaredField("fields"));
            } catch (NoSuchFieldException unused) {
                Unsafe unsafe2 = f1668a;
                objectFieldOffset = unsafe2.objectFieldOffset(loadClass3.getDeclaredField("iFields"));
                unsafe2.objectFieldOffset(loadClass3.getDeclaredField("sFields"));
            }
            Unsafe unsafe3 = f1668a;
            long objectFieldOffset2 = unsafe3.objectFieldOffset(loadClass3.getDeclaredField("methods"));
            f1671d = objectFieldOffset2;
            Method declaredMethod = C0853g.class.getDeclaredMethod("a", new Class[0]);
            Method declaredMethod2 = C0853g.class.getDeclaredMethod("b", new Class[0]);
            declaredMethod.setAccessible(true);
            declaredMethod2.setAccessible(true);
            MethodHandle unreflect = MethodHandles.lookup().unreflect(declaredMethod);
            MethodHandle unreflect2 = MethodHandles.lookup().unreflect(declaredMethod2);
            long j2 = f1670c;
            long j3 = unsafe3.getLong(unreflect, j2);
            long j4 = unsafe3.getLong(unreflect2, j2);
            long j5 = unsafe3.getLong(C0853g.class, objectFieldOffset2);
            long j6 = j4 - j3;
            f1672e = j6;
            f1673f = (j3 - j5) - j6;
            Field declaredField = C0853g.class.getDeclaredField("i");
            Field declaredField2 = C0853g.class.getDeclaredField("j");
            declaredField.setAccessible(true);
            declaredField2.setAccessible(true);
            MethodHandle unreflectGetter = MethodHandles.lookup().unreflectGetter(declaredField);
            MethodHandle unreflectGetter2 = MethodHandles.lookup().unreflectGetter(declaredField2);
            unsafe3.getLong(unreflectGetter, j2);
            unsafe3.getLong(unreflectGetter2, j2);
            unsafe3.getLong(C0853g.class, objectFieldOffset);
        } catch (ReflectiveOperationException e2) {
            Log.e("HiddenApiBypass", "Initialize error", e2);
            throw new ExceptionInInitializerError(e2);
        }
    }

    /* renamed from: a */
    public static Object m1237a(Class cls, Object obj, String str, Object... objArr) {
        if (obj != null && !cls.isInstance(obj)) {
            throw new IllegalArgumentException("this object is not an instance of the given class");
        }
        Method declaredMethod = C0851e.class.getDeclaredMethod("invoke", Object[].class);
        declaredMethod.setAccessible(true);
        Unsafe unsafe = f1668a;
        long j2 = unsafe.getLong(cls, f1671d);
        if (j2 == 0) {
            throw new NoSuchMethodException("Cannot find matching method");
        }
        int i2 = unsafe.getInt(j2);
        for (int i3 = 0; i3 < i2; i3++) {
            f1668a.putLong(declaredMethod, f1669b, (i3 * f1672e) + j2 + f1673f);
            if (str.equals(declaredMethod.getName()) && AbstractC0854h.m1236a(declaredMethod.getParameterTypes(), objArr)) {
                return declaredMethod.invoke(obj, objArr);
            }
        }
        throw new NoSuchMethodException("Cannot find matching method");
    }

    /* renamed from: b */
    public static boolean m1238b(String... strArr) {
        try {
            m1237a(VMRuntime.class, m1237a(VMRuntime.class, null, "getRuntime", new Object[0]), "setHiddenApiExemptions", strArr);
            return true;
        } catch (ReflectiveOperationException e2) {
            Log.w("HiddenApiBypass", "setHiddenApiExemptions", e2);
            return false;
        }
    }
}
