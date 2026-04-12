package p000;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public abstract class v91 {

    /* renamed from: a0 */
    public final C0130bd f60608a0;

    /* renamed from: a1 */
    public final C0130bd f60609a1;

    /* renamed from: a2 */
    public final C0130bd f60610a2;

    public v91(C0130bd c0130bd, C0130bd c0130bd2, C0130bd c0130bd3) {
        this.f60608a0 = c0130bd;
        this.f60609a1 = c0130bd2;
        this.f60610a2 = c0130bd3;
    }

    /* renamed from: a0 */
    public abstract w91 mo214911a0();

    /* renamed from: a1 */
    public final Class m214912a1(Class cls) throws ClassNotFoundException {
        String name = cls.getName();
        C0130bd c0130bd = this.f60610a2;
        Class cls2 = (Class) c0130bd.getOrDefault(name, null);
        if (cls2 != null) {
            return cls2;
        }
        Class<?> cls3 = Class.forName(cls.getPackage().getName() + "." + cls.getSimpleName() + "Parcelizer", false, cls.getClassLoader());
        c0130bd.put(cls.getName(), cls3);
        return cls3;
    }

    /* renamed from: a2 */
    public final Method m214913a2(String str) throws NoSuchMethodException, SecurityException {
        C0130bd c0130bd = this.f60608a0;
        Method method = (Method) c0130bd.getOrDefault(str, null);
        if (method != null) {
            return method;
        }
        System.currentTimeMillis();
        Method declaredMethod = Class.forName(str, true, v91.class.getClassLoader()).getDeclaredMethod("read", v91.class);
        c0130bd.put(str, declaredMethod);
        return declaredMethod;
    }

    /* renamed from: a3 */
    public final Method m214914a3(Class cls) throws NoSuchMethodException, ClassNotFoundException, SecurityException {
        String name = cls.getName();
        C0130bd c0130bd = this.f60609a1;
        Method method = (Method) c0130bd.getOrDefault(name, null);
        if (method != null) {
            return method;
        }
        Class clsM214912a1 = m214912a1(cls);
        System.currentTimeMillis();
        Method declaredMethod = clsM214912a1.getDeclaredMethod("write", cls, v91.class);
        c0130bd.put(cls.getName(), declaredMethod);
        return declaredMethod;
    }

    /* renamed from: a4 */
    public abstract boolean mo214915a4(int i);

    /* renamed from: a5 */
    public final Parcelable m214916a5(Parcelable parcelable, int i) {
        if (!mo214915a4(i)) {
            return parcelable;
        }
        return ((w91) this).f60862a4.readParcelable(w91.class.getClassLoader());
    }

    /* renamed from: a6 */
    public final x91 m214917a6() {
        String string = ((w91) this).f60862a4.readString();
        if (string == null) {
            return null;
        }
        try {
            return (x91) m214913a2(string).invoke(null, mo214911a0());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("VersionedParcel encountered ClassNotFoundException", e);
        } catch (IllegalAccessException e2) {
            throw new RuntimeException("VersionedParcel encountered IllegalAccessException", e2);
        } catch (NoSuchMethodException e3) {
            throw new RuntimeException("VersionedParcel encountered NoSuchMethodException", e3);
        } catch (InvocationTargetException e4) {
            if (e4.getCause() instanceof RuntimeException) {
                throw ((RuntimeException) e4.getCause());
            }
            throw new RuntimeException("VersionedParcel encountered InvocationTargetException", e4);
        }
    }

    /* renamed from: a7 */
    public abstract void mo214918a7(int i);

    /* renamed from: a8 */
    public final void m214919a8(x91 x91Var) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (x91Var == null) {
            ((w91) this).f60862a4.writeString(null);
            return;
        }
        try {
            ((w91) this).f60862a4.writeString(m214912a1(x91Var.getClass()).getName());
            w91 w91VarMo214911a0 = mo214911a0();
            try {
                m214914a3(x91Var.getClass()).invoke(null, x91Var, w91VarMo214911a0);
                Parcel parcel = w91VarMo214911a0.f60862a4;
                int i = w91VarMo214911a0.f60866a8;
                if (i >= 0) {
                    int i2 = w91VarMo214911a0.f60861a3.get(i);
                    int iDataPosition = parcel.dataPosition();
                    parcel.setDataPosition(i2);
                    parcel.writeInt(iDataPosition - i2);
                    parcel.setDataPosition(iDataPosition);
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("VersionedParcel encountered ClassNotFoundException", e);
            } catch (IllegalAccessException e2) {
                throw new RuntimeException("VersionedParcel encountered IllegalAccessException", e2);
            } catch (NoSuchMethodException e3) {
                throw new RuntimeException("VersionedParcel encountered NoSuchMethodException", e3);
            } catch (InvocationTargetException e4) {
                if (!(e4.getCause() instanceof RuntimeException)) {
                    throw new RuntimeException("VersionedParcel encountered InvocationTargetException", e4);
                }
                throw ((RuntimeException) e4.getCause());
            }
        } catch (ClassNotFoundException e5) {
            throw new RuntimeException(x91Var.getClass().getSimpleName().concat(" does not have a Parcelizer"), e5);
        }
    }
}
