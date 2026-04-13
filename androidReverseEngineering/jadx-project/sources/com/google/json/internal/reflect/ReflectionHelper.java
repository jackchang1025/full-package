package com.google.json.internal.reflect;

import com.google.json.JsonIOException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class ReflectionHelper {
    private static final RecordHelper RECORD_HELPER;

    public static abstract class RecordHelper {
        private RecordHelper() {
        }

        public abstract Method getAccessor(Class<?> cls, Field field);

        public abstract <T> Constructor<T> getCanonicalRecordConstructor(Class<T> cls);

        public abstract String[] getRecordComponentNames(Class<?> cls);

        public abstract boolean isRecord(Class<?> cls);
    }

    public static class RecordNotSupportedHelper extends RecordHelper {
        private RecordNotSupportedHelper() {
            super();
        }

        @Override // com.google.json.internal.reflect.ReflectionHelper.RecordHelper
        public Method getAccessor(Class<?> cls, Field field) {
            throw new UnsupportedOperationException("Records are not supported on this JVM, this method should not be called");
        }

        @Override // com.google.json.internal.reflect.ReflectionHelper.RecordHelper
        public <T> Constructor<T> getCanonicalRecordConstructor(Class<T> cls) {
            throw new UnsupportedOperationException("Records are not supported on this JVM, this method should not be called");
        }

        @Override // com.google.json.internal.reflect.ReflectionHelper.RecordHelper
        public String[] getRecordComponentNames(Class<?> cls) {
            throw new UnsupportedOperationException("Records are not supported on this JVM, this method should not be called");
        }

        @Override // com.google.json.internal.reflect.ReflectionHelper.RecordHelper
        public boolean isRecord(Class<?> cls) {
            return false;
        }
    }

    public static class RecordSupportedHelper extends RecordHelper {
        private final Method getName;
        private final Method getRecordComponents;
        private final Method getType;
        private final Method isRecord;

        private RecordSupportedHelper() {
            super();
            this.isRecord = Class.class.getMethod("isRecord", new Class[0]);
            Method method = Class.class.getMethod("getRecordComponents", new Class[0]);
            this.getRecordComponents = method;
            Class<?> componentType = method.getReturnType().getComponentType();
            this.getName = componentType.getMethod("getName", new Class[0]);
            this.getType = componentType.getMethod("getType", new Class[0]);
        }

        @Override // com.google.json.internal.reflect.ReflectionHelper.RecordHelper
        public Method getAccessor(Class<?> cls, Field field) {
            try {
                return cls.getMethod(field.getName(), new Class[0]);
            } catch (ReflectiveOperationException e2) {
                throw ReflectionHelper.createExceptionForRecordReflectionException(e2);
            }
        }

        @Override // com.google.json.internal.reflect.ReflectionHelper.RecordHelper
        public <T> Constructor<T> getCanonicalRecordConstructor(Class<T> cls) {
            try {
                Object[] objArr = (Object[]) this.getRecordComponents.invoke(cls, new Object[0]);
                Class<?>[] clsArr = new Class[objArr.length];
                for (int i2 = 0; i2 < objArr.length; i2++) {
                    clsArr[i2] = (Class) this.getType.invoke(objArr[i2], new Object[0]);
                }
                return cls.getDeclaredConstructor(clsArr);
            } catch (ReflectiveOperationException e2) {
                throw ReflectionHelper.createExceptionForRecordReflectionException(e2);
            }
        }

        @Override // com.google.json.internal.reflect.ReflectionHelper.RecordHelper
        public String[] getRecordComponentNames(Class<?> cls) {
            try {
                Object[] objArr = (Object[]) this.getRecordComponents.invoke(cls, new Object[0]);
                String[] strArr = new String[objArr.length];
                for (int i2 = 0; i2 < objArr.length; i2++) {
                    strArr[i2] = (String) this.getName.invoke(objArr[i2], new Object[0]);
                }
                return strArr;
            } catch (ReflectiveOperationException e2) {
                throw ReflectionHelper.createExceptionForRecordReflectionException(e2);
            }
        }

        @Override // com.google.json.internal.reflect.ReflectionHelper.RecordHelper
        public boolean isRecord(Class<?> cls) {
            try {
                return ((Boolean) this.isRecord.invoke(cls, new Object[0])).booleanValue();
            } catch (ReflectiveOperationException e2) {
                throw ReflectionHelper.createExceptionForRecordReflectionException(e2);
            }
        }
    }

    static {
        RecordHelper recordNotSupportedHelper;
        try {
            recordNotSupportedHelper = new RecordSupportedHelper();
        } catch (NoSuchMethodException unused) {
            recordNotSupportedHelper = new RecordNotSupportedHelper();
        }
        RECORD_HELPER = recordNotSupportedHelper;
    }

    private ReflectionHelper() {
    }

    private static void appendExecutableParameters(AccessibleObject accessibleObject, StringBuilder sb) {
        sb.append('(');
        Class<?>[] parameterTypes = accessibleObject instanceof Method ? ((Method) accessibleObject).getParameterTypes() : ((Constructor) accessibleObject).getParameterTypes();
        for (int i2 = 0; i2 < parameterTypes.length; i2++) {
            if (i2 > 0) {
                sb.append(", ");
            }
            sb.append(parameterTypes[i2].getSimpleName());
        }
        sb.append(')');
    }

    public static String constructorToString(Constructor<?> constructor) {
        StringBuilder sb = new StringBuilder(constructor.getDeclaringClass().getName());
        appendExecutableParameters(constructor, sb);
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static RuntimeException createExceptionForRecordReflectionException(ReflectiveOperationException reflectiveOperationException) {
        throw new RuntimeException("Unexpected ReflectiveOperationException occurred (Gson 2.10.1). To support Java records, reflection is utilized to read out information about records. All these invocations happens after it is established that records exist in the JVM. This exception is unexpected behavior.", reflectiveOperationException);
    }

    public static RuntimeException createExceptionForUnexpectedIllegalAccess(IllegalAccessException illegalAccessException) {
        throw new RuntimeException("Unexpected IllegalAccessException occurred (Gson 2.10.1). Certain ReflectionAccessFilter features require Java >= 9 to work correctly. If you are not using ReflectionAccessFilter, report this to the Gson maintainers.", illegalAccessException);
    }

    public static String fieldToString(Field field) {
        return field.getDeclaringClass().getName() + "#" + field.getName();
    }

    /* JADX WARN: Removed duplicated region for block: B:13:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0079  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String getAccessibleObjectDescription(AccessibleObject accessibleObject, boolean z2) {
        StringBuilder sb;
        String constructorToString;
        String str;
        if (accessibleObject instanceof Field) {
            sb = new StringBuilder("field '");
            constructorToString = fieldToString((Field) accessibleObject);
        } else {
            if (accessibleObject instanceof Method) {
                Method method = (Method) accessibleObject;
                StringBuilder sb2 = new StringBuilder(method.getName());
                appendExecutableParameters(method, sb2);
                str = "method '" + method.getDeclaringClass().getName() + "#" + sb2.toString() + "'";
                if (z2 && Character.isLowerCase(str.charAt(0))) {
                    return Character.toUpperCase(str.charAt(0)) + str.substring(1);
                }
            }
            if (!(accessibleObject instanceof Constructor)) {
                sb = new StringBuilder("<unknown AccessibleObject> ");
                sb.append(accessibleObject.toString());
                str = sb.toString();
                return z2 ? str : str;
            }
            sb = new StringBuilder("constructor '");
            constructorToString = constructorToString((Constructor) accessibleObject);
        }
        sb.append(constructorToString);
        sb.append("'");
        str = sb.toString();
        if (z2) {
        }
    }

    public static Method getAccessor(Class<?> cls, Field field) {
        return RECORD_HELPER.getAccessor(cls, field);
    }

    public static <T> Constructor<T> getCanonicalRecordConstructor(Class<T> cls) {
        return RECORD_HELPER.getCanonicalRecordConstructor(cls);
    }

    public static String[] getRecordComponentNames(Class<?> cls) {
        return RECORD_HELPER.getRecordComponentNames(cls);
    }

    public static boolean isRecord(Class<?> cls) {
        return RECORD_HELPER.isRecord(cls);
    }

    public static void makeAccessible(AccessibleObject accessibleObject) {
        try {
            accessibleObject.setAccessible(true);
        } catch (Exception e2) {
            throw new JsonIOException(AbstractC0000a.m16l("Failed making ", getAccessibleObjectDescription(accessibleObject, false), " accessible; either increase its visibility or write a custom TypeAdapter for its declaring type."), e2);
        }
    }

    public static String tryMakeAccessible(Constructor<?> constructor) {
        try {
            constructor.setAccessible(true);
            return null;
        } catch (Exception e2) {
            return "Failed making constructor '" + constructorToString(constructor) + "' accessible; either increase its visibility or write a custom InstanceCreator or TypeAdapter for its declaring type: " + e2.getMessage();
        }
    }
}
