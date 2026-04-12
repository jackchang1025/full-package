package p000;

import android.app.Application;
import android.os.Bundle;
import androidx.lifecycle.AbstractC0078a2;
import androidx.lifecycle.C0076a0;
import androidx.lifecycle.LegacySavedStateHandleController$tryToAddRecreator$1;
import androidx.lifecycle.Lifecycle$State;
import androidx.lifecycle.SavedStateHandleController;
import java.io.IOException;
import java.lang.reflect.Constructor;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class zt0 extends qb1 implements nb1 {

    /* renamed from: a0 */
    public final Application f61582a0;

    /* renamed from: a1 */
    public final lb1 f61583a1;

    /* renamed from: a2 */
    public final Bundle f61584a2;

    /* renamed from: a3 */
    public final kg1 f61585a3;

    /* renamed from: a4 */
    public final vt0 f61586a4;

    public zt0(Application application, yt0 yt0Var, Bundle bundle) {
        this.f61586a4 = yt0Var.mo209826a0();
        this.f61585a3 = yt0Var.mo209830a5();
        this.f61584a2 = bundle;
        this.f61582a0 = application;
        this.f61583a1 = application != null ? lb1.f57870a4.getInstance(application) : new lb1(null);
    }

    @Override // p000.nb1
    /* renamed from: a0 */
    public final ib1 mo213203a0(Class cls) {
        String canonicalName = cls.getCanonicalName();
        if (canonicalName != null) {
            return m215433a2(cls, canonicalName);
        }
        throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
    }

    @Override // p000.nb1
    /* renamed from: a1 */
    public final ib1 mo213829a1(Class cls, gh0 gh0Var) {
        String str = (String) gh0Var.m212951a0(pb1.f59189a2);
        if (str == null) {
            throw new IllegalStateException("VIEW_MODEL_KEY must always be provided by ViewModelProvider");
        }
        if (gh0Var.m212951a0(AbstractC0078a2.f45208a0) == null || gh0Var.m212951a0(AbstractC0078a2.f45209a1) == null) {
            if (this.f61585a3 != null) {
                return m215433a2(cls, str);
            }
            throw new IllegalStateException("SAVED_STATE_REGISTRY_OWNER_KEY andVIEW_MODEL_STORE_OWNER_KEY must be provided in the creation extras tosuccessfully create a ViewModel.");
        }
        Application application = (Application) gh0Var.m212951a0(lb1.f57872a6);
        boolean zIsAssignableFrom = AbstractC1212s7.class.isAssignableFrom(cls);
        Constructor constructorM210522a0 = (!zIsAssignableFrom || application == null) ? au0.m210522a0(cls, au0.f45644a1) : au0.m210522a0(cls, au0.f45643a0);
        return constructorM210522a0 == null ? this.f61583a1.mo213829a1(cls, gh0Var) : (!zIsAssignableFrom || application == null) ? au0.m210523a1(cls, constructorM210522a0, AbstractC0078a2.m210243a0(gh0Var)) : au0.m210523a1(cls, constructorM210522a0, application, AbstractC0078a2.m210243a0(gh0Var));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: a2 */
    public final ib1 m215433a2(Class cls, String str) throws IOException {
        Object obj;
        Application application;
        kg1 kg1Var = this.f61585a3;
        if (kg1Var == null) {
            throw new UnsupportedOperationException("SavedStateViewModelFactory constructed with empty constructor supports only calls to create(modelClass: Class<T>, extras: CreationExtras).");
        }
        boolean zIsAssignableFrom = AbstractC1212s7.class.isAssignableFrom(cls);
        Constructor constructorM210522a0 = (!zIsAssignableFrom || this.f61582a0 == null) ? au0.m210522a0(cls, au0.f45644a1) : au0.m210522a0(cls, au0.f45643a0);
        if (constructorM210522a0 == null) {
            return this.f61582a0 != null ? this.f61583a1.mo213203a0(cls) : pb1.f59187a0.getInstance().mo213203a0(cls);
        }
        vt0 vt0Var = this.f61586a4;
        t60.m214692b3(vt0Var);
        pt0 pt0VarCreateHandle = pt0.f59335a5.createHandle(vt0Var.m214951a0(str), this.f61584a2);
        SavedStateHandleController savedStateHandleController = new SavedStateHandleController(str, pt0VarCreateHandle);
        savedStateHandleController.m210229a1(vt0Var, kg1Var);
        Lifecycle$State lifecycle$State = ((C0076a0) kg1Var).f45191a6;
        if (lifecycle$State == Lifecycle$State.f45174a1 || lifecycle$State.compareTo(Lifecycle$State.f45176a3) >= 0) {
            vt0Var.m214954a3();
        } else {
            kg1Var.mo210230a0(new LegacySavedStateHandleController$tryToAddRecreator$1(vt0Var, kg1Var));
        }
        ib1 ib1VarM210523a1 = (!zIsAssignableFrom || (application = this.f61582a0) == null) ? au0.m210523a1(cls, constructorM210522a0, pt0VarCreateHandle) : au0.m210523a1(cls, constructorM210522a0, application, pt0VarCreateHandle);
        synchronized (ib1VarM210523a1.f56853a0) {
            try {
                obj = ib1VarM210523a1.f56853a0.get("androidx.lifecycle.savedstate.vm.tag");
                if (obj == 0) {
                    ib1VarM210523a1.f56853a0.put("androidx.lifecycle.savedstate.vm.tag", savedStateHandleController);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (obj != 0) {
            savedStateHandleController = obj;
        }
        if (ib1VarM210523a1.f56855a2) {
            ib1.m213147a0(savedStateHandleController);
        }
        return ib1VarM210523a1;
    }
}
