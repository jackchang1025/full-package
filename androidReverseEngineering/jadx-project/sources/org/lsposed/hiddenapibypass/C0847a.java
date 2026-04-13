package org.lsposed.hiddenapibypass;

import dalvik.system.PathClassLoader;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.Executable;
import com.guard.wallet.entity.BuildConfig;

/* renamed from: org.lsposed.hiddenapibypass.a */
/* loaded from: classes.dex */
public final class C0847a extends PathClassLoader {
    public C0847a() {
        super(System.getProperty("java.boot.class.path", BuildConfig.FLAVOR).split(":", 2)[0], null);
    }

    @Override // java.lang.ClassLoader
    public final Class loadClass(String str) {
        if (Object.class.getName().equals(str)) {
            return Object.class;
        }
        try {
            return findClass(str);
        } catch (ClassNotFoundException unused) {
            return Executable.class.getName().equals(str) ? C0850d.class : MethodHandle.class.getName().equals(str) ? C0852f.class : Class.class.getName().equals(str) ? C0849c.class : super.loadClass(str);
        }
    }
}
