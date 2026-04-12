package p000;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import kotlin.text.AbstractC0779a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class na0 {

    /* renamed from: a0 */
    public static final HashMap f58468a0 = new HashMap();

    /* renamed from: a1 */
    public static final HashMap f58469a1 = new HashMap();

    /* renamed from: a0 */
    public static void m214055a0(Constructor constructor, ja0 ja0Var) {
        try {
            t60.m214694b5(constructor.newInstance(ja0Var), "{\n            constructo…tance(`object`)\n        }");
            throw new ClassCastException();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e2) {
            throw new RuntimeException(e2);
        } catch (InvocationTargetException e3) {
            throw new RuntimeException(e3);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:60:0x0116  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0148 A[SYNTHETIC] */
    /* renamed from: a1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int m214056a1(Class cls) throws NoSuchMethodException, SecurityException {
        Constructor<?> declaredConstructor;
        boolean zBooleanValue;
        int length;
        int i;
        HashMap map = f58468a0;
        Integer num = (Integer) map.get(cls);
        if (num != null) {
            return num.intValue();
        }
        int i2 = 1;
        if (cls.getCanonicalName() != null) {
            ArrayList arrayList = null;
            try {
                Package r3 = cls.getPackage();
                String canonicalName = cls.getCanonicalName();
                String name = r3 != null ? r3.getName() : "";
                t60.m214694b5(name, "fullPackage");
                if (name.length() != 0) {
                    t60.m214694b5(canonicalName, "name");
                    canonicalName = canonicalName.substring(name.length() + 1);
                    t60.m214694b5(canonicalName, "this as java.lang.String).substring(startIndex)");
                }
                t60.m214694b5(canonicalName, "if (fullPackage.isEmpty(…g(fullPackage.length + 1)");
                String strConcat = AbstractC0779a1.m213673c6(canonicalName, ".", "_").concat("_LifecycleAdapter");
                if (name.length() != 0) {
                    strConcat = name + '.' + strConcat;
                }
                declaredConstructor = Class.forName(strConcat).getDeclaredConstructor(cls);
                if (!declaredConstructor.isAccessible()) {
                    declaredConstructor.setAccessible(true);
                }
            } catch (ClassNotFoundException unused) {
                declaredConstructor = null;
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            HashMap map2 = f58469a1;
            if (declaredConstructor != null) {
                map2.put(cls, AbstractC1117qo.m214451e7(declaredConstructor));
            } else {
                C0692ir c0692ir = C0692ir.f57220a2;
                HashMap map3 = c0692ir.f57222a1;
                Boolean bool = (Boolean) map3.get(cls);
                if (bool != null) {
                    zBooleanValue = bool.booleanValue();
                } else {
                    try {
                        Method[] declaredMethods = cls.getDeclaredMethods();
                        int length2 = declaredMethods.length;
                        int i3 = 0;
                        while (true) {
                            if (i3 >= length2) {
                                map3.put(cls, Boolean.FALSE);
                                zBooleanValue = false;
                                break;
                            }
                            if (((al0) declaredMethods[i3].getAnnotation(al0.class)) != null) {
                                c0692ir.m213188a0(cls, declaredMethods);
                                zBooleanValue = true;
                                break;
                            }
                            i3++;
                        }
                    } catch (NoClassDefFoundError e2) {
                        throw new IllegalArgumentException("The observer class has some methods that use newer APIs which are not available in the current OS version. Lifecycles cannot access even other methods so you should make sure that your observer classes only access framework classes that are available in your min API level OR use lifecycle:compiler annotation processor.", e2);
                    }
                }
                if (!zBooleanValue) {
                    Class superclass = cls.getSuperclass();
                    if (superclass != null && ja0.class.isAssignableFrom(superclass)) {
                        t60.m214694b5(superclass, "superclass");
                        if (m214056a1(superclass) != 1) {
                            Object obj = map2.get(superclass);
                            t60.m214692b3(obj);
                            arrayList = new ArrayList((Collection) obj);
                            Class<?>[] interfaces = cls.getInterfaces();
                            t60.m214694b5(interfaces, "klass.interfaces");
                            length = interfaces.length;
                            i = 0;
                            while (true) {
                                if (i < length) {
                                    Class<?> cls2 = interfaces[i];
                                    if (cls2 != null && ja0.class.isAssignableFrom(cls2)) {
                                        t60.m214694b5(cls2, "intrface");
                                        if (m214056a1(cls2) == 1) {
                                            break;
                                        }
                                        if (arrayList == null) {
                                            arrayList = new ArrayList();
                                        }
                                        Object obj2 = map2.get(cls2);
                                        t60.m214692b3(obj2);
                                        arrayList.addAll((Collection) obj2);
                                    }
                                    i++;
                                } else if (arrayList != null) {
                                    map2.put(cls, arrayList);
                                }
                            }
                        }
                    } else {
                        Class<?>[] interfaces2 = cls.getInterfaces();
                        t60.m214694b5(interfaces2, "klass.interfaces");
                        length = interfaces2.length;
                        i = 0;
                        while (true) {
                            if (i < length) {
                            }
                            i++;
                        }
                    }
                }
            }
            i2 = 2;
        }
        map.put(cls, Integer.valueOf(i2));
        return i2;
    }
}
