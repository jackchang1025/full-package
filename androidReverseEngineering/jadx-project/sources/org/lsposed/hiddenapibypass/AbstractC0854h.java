package org.lsposed.hiddenapibypass;

import java.util.HashSet;

/* renamed from: org.lsposed.hiddenapibypass.h */
/* loaded from: classes.dex */
public abstract class AbstractC0854h {

    /* renamed from: a */
    public static final HashSet f1667a = new HashSet();

    /* renamed from: a */
    public static boolean m1236a(Class[] clsArr, Object[] objArr) {
        if (clsArr.length != objArr.length) {
            return false;
        }
        for (int i2 = 0; i2 < clsArr.length; i2++) {
            if (clsArr[i2].isPrimitive()) {
                Class cls = clsArr[i2];
                if (cls == Integer.TYPE && !(objArr[i2] instanceof Integer)) {
                    return false;
                }
                if (cls == Byte.TYPE && !(objArr[i2] instanceof Byte)) {
                    return false;
                }
                if (cls == Character.TYPE && !(objArr[i2] instanceof Character)) {
                    return false;
                }
                if (cls == Boolean.TYPE && !(objArr[i2] instanceof Boolean)) {
                    return false;
                }
                if (cls == Double.TYPE && !(objArr[i2] instanceof Double)) {
                    return false;
                }
                if (cls == Float.TYPE && !(objArr[i2] instanceof Float)) {
                    return false;
                }
                if (cls == Long.TYPE && !(objArr[i2] instanceof Long)) {
                    return false;
                }
                if (cls == Short.TYPE && !(objArr[i2] instanceof Short)) {
                    return false;
                }
            } else {
                Object obj = objArr[i2];
                if (obj != null && !clsArr[i2].isInstance(obj)) {
                    return false;
                }
            }
        }
        return true;
    }
}
