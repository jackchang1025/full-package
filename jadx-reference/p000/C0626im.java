package p000;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;
import kotlin.text.AbstractC0779a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: im */
/* loaded from: classes2.dex */
public final class C0626im {
    public /* synthetic */ C0626im(AbstractC1120qr abstractC1120qr) {
        this();
    }

    public final String getClassQualifiedName(Class<?> cls) {
        String str;
        t60.m214695b6(cls, "jClass");
        String strConcat = null;
        if (cls.isAnonymousClass() || cls.isLocalClass()) {
            return null;
        }
        if (!cls.isArray()) {
            String str2 = (String) C0627in.f56915a3.get(cls.getName());
            return str2 == null ? cls.getCanonicalName() : str2;
        }
        Class<?> componentType = cls.getComponentType();
        if (componentType.isPrimitive() && (str = (String) C0627in.f56915a3.get(componentType.getName())) != null) {
            strConcat = str.concat("Array");
        }
        return strConcat == null ? "kotlin.Array" : strConcat;
    }

    public final String getClassSimpleName(Class<?> cls) {
        String str;
        t60.m214695b6(cls, "jClass");
        String strConcat = null;
        if (cls.isAnonymousClass()) {
            return null;
        }
        if (!cls.isLocalClass()) {
            if (!cls.isArray()) {
                String str2 = (String) C0627in.f56916a4.get(cls.getName());
                return str2 == null ? cls.getSimpleName() : str2;
            }
            Class<?> componentType = cls.getComponentType();
            if (componentType.isPrimitive() && (str = (String) C0627in.f56916a4.get(componentType.getName())) != null) {
                strConcat = str.concat("Array");
            }
            return strConcat == null ? "Array" : strConcat;
        }
        String simpleName = cls.getSimpleName();
        Method enclosingMethod = cls.getEnclosingMethod();
        if (enclosingMethod != null) {
            return AbstractC0779a1.m213682d5(simpleName, enclosingMethod.getName() + '$');
        }
        Constructor<?> enclosingConstructor = cls.getEnclosingConstructor();
        if (enclosingConstructor != null) {
            return AbstractC0779a1.m213682d5(simpleName, enclosingConstructor.getName() + '$');
        }
        int iM213660b3 = AbstractC0779a1.m213660b3(simpleName, '$', 0, 6);
        if (iM213660b3 == -1) {
            return simpleName;
        }
        String strSubstring = simpleName.substring(iM213660b3 + 1, simpleName.length());
        t60.m214694b5(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
        return strSubstring;
    }

    public final boolean isInstance(Object obj, Class<?> cls) {
        t60.m214695b6(cls, "jClass");
        Map map = C0627in.f56914a2;
        t60.m214693b4(map, "null cannot be cast to non-null type kotlin.collections.Map<K of kotlin.collections.MapsKt__MapsKt.get, V of kotlin.collections.MapsKt__MapsKt.get>");
        Integer num = (Integer) map.get(cls);
        if (num != null) {
            return b81.m210585d4(num.intValue(), obj);
        }
        if (cls.isPrimitive()) {
            cls = t60.m214708c9(fr0.m212854a0(cls));
        }
        return cls.isInstance(obj);
    }

    private C0626im() {
    }
}
