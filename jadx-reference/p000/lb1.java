package p000;

import android.app.Application;
import java.lang.reflect.InvocationTargetException;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class lb1 extends pb1 {

    /* renamed from: a5 */
    public static lb1 f57871a5;

    /* renamed from: a3 */
    public final Application f57873a3;

    /* renamed from: a4 */
    public static final kb1 f57870a4 = new kb1(null);

    /* renamed from: a6 */
    public static final C1351vv f57872a6 = C1351vv.f60711b2;

    public lb1(Application application) {
        this.f57873a3 = application;
    }

    @Override // p000.pb1, p000.nb1
    /* renamed from: a0 */
    public final ib1 mo213203a0(Class cls) {
        Application application = this.f57873a3;
        if (application != null) {
            return m213830a2(cls, application);
        }
        throw new UnsupportedOperationException("AndroidViewModelFactory constructed with empty constructor works only with create(modelClass: Class<T>, extras: CreationExtras).");
    }

    @Override // p000.nb1
    /* renamed from: a1 */
    public final ib1 mo213829a1(Class cls, gh0 gh0Var) {
        if (this.f57873a3 != null) {
            return mo213203a0(cls);
        }
        Application application = (Application) gh0Var.m212951a0(f57872a6);
        if (application != null) {
            return m213830a2(cls, application);
        }
        if (AbstractC1212s7.class.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("CreationExtras must have an application by `APPLICATION_KEY`");
        }
        return super.mo213203a0(cls);
    }

    /* renamed from: a2 */
    public final ib1 m213830a2(Class cls, Application application) {
        if (!AbstractC1212s7.class.isAssignableFrom(cls)) {
            return super.mo213203a0(cls);
        }
        try {
            ib1 ib1Var = (ib1) cls.getConstructor(Application.class).newInstance(application);
            t60.m214694b5(ib1Var, "{\n                try {\n…          }\n            }");
            return ib1Var;
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot create an instance of " + cls, e);
        } catch (InstantiationException e2) {
            throw new RuntimeException("Cannot create an instance of " + cls, e2);
        } catch (NoSuchMethodException e3) {
            throw new RuntimeException("Cannot create an instance of " + cls, e3);
        } catch (InvocationTargetException e4) {
            throw new RuntimeException("Cannot create an instance of " + cls, e4);
        }
    }
}
