package p000;

import java.security.AccessController;
import java.security.PrivilegedAction;

/* renamed from: io */
/* loaded from: classes2.dex */
public class C0628io {

    /* renamed from: io$a0 */
    public static class a0 implements PrivilegedAction {
        final /* synthetic */ String val$className;

        public a0(String str) {
            this.val$className = str;
        }

        @Override // java.security.PrivilegedAction
        public Object run() {
            try {
                return Class.forName(this.val$className);
            } catch (Exception unused) {
                return null;
            }
        }
    }

    public static Class loadClass(Class cls, String str) {
        try {
            ClassLoader classLoader = cls.getClassLoader();
            return classLoader != null ? classLoader.loadClass(str) : (Class) AccessController.doPrivileged(new a0(str));
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }
}
