package p000;

import android.os.Bundle;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class vt0 {

    /* renamed from: a1 */
    public boolean f60683a1;

    /* renamed from: a2 */
    public Bundle f60684a2;

    /* renamed from: a3 */
    public boolean f60685a3;

    /* renamed from: a4 */
    public C1290u7 f60686a4;

    /* renamed from: a0 */
    public final nt0 f60682a0 = new nt0();

    /* renamed from: a5 */
    public boolean f60687a5 = true;

    static {
        new tt0(null);
    }

    /* renamed from: a0 */
    public final Bundle m214951a0(String str) {
        if (!this.f60685a3) {
            throw new IllegalStateException("You can consumeRestoredStateForKey only after super.onCreate of corresponding component");
        }
        Bundle bundle = this.f60684a2;
        if (bundle == null) {
            return null;
        }
        Bundle bundle2 = bundle.getBundle(str);
        Bundle bundle3 = this.f60684a2;
        if (bundle3 != null) {
            bundle3.remove(str);
        }
        Bundle bundle4 = this.f60684a2;
        if (bundle4 != null && !bundle4.isEmpty()) {
            return bundle2;
        }
        this.f60684a2 = null;
        return bundle2;
    }

    /* renamed from: a1 */
    public final ut0 m214952a1() {
        String str;
        ut0 ut0Var;
        Iterator it = this.f60682a0.iterator();
        do {
            jt0 jt0Var = (jt0) it;
            if (!jt0Var.hasNext()) {
                return null;
            }
            Map.Entry entry = (Map.Entry) jt0Var.next();
            t60.m214694b5(entry, "components");
            str = (String) entry.getKey();
            ut0Var = (ut0) entry.getValue();
        } while (!t60.m214686a2(str, "androidx.lifecycle.internal.SavedStateHandlesProvider"));
        return ut0Var;
    }

    /* renamed from: a2 */
    public final void m214953a2(String str, ut0 ut0Var) {
        Object obj;
        t60.m214695b6(ut0Var, "provider");
        nt0 nt0Var = this.f60682a0;
        kt0 kt0VarMo214143a0 = nt0Var.mo214143a0(str);
        if (kt0VarMo214143a0 != null) {
            obj = kt0VarMo214143a0.f57717a1;
        } else {
            kt0 kt0Var = new kt0(str, ut0Var);
            nt0Var.f58695a3++;
            kt0 kt0Var2 = nt0Var.f58693a1;
            if (kt0Var2 == null) {
                nt0Var.f58692a0 = kt0Var;
                nt0Var.f58693a1 = kt0Var;
            } else {
                kt0Var2.f57718a2 = kt0Var;
                kt0Var.f57719a3 = kt0Var2;
                nt0Var.f58693a1 = kt0Var;
            }
            obj = null;
        }
        if (((ut0) obj) != null) {
            throw new IllegalArgumentException("SavedStateProvider with the given key is already registered");
        }
    }

    /* renamed from: a3 */
    public final void m214954a3() {
        if (!this.f60687a5) {
            throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
        }
        C1290u7 c1290u7 = this.f60686a4;
        if (c1290u7 == null) {
            c1290u7 = new C1290u7(this);
        }
        this.f60686a4 = c1290u7;
        try {
            ea0.class.getDeclaredConstructor(null);
            C1290u7 c1290u72 = this.f60686a4;
            if (c1290u72 != null) {
                ((LinkedHashSet) c1290u72.f60335a1).add(ea0.class.getName());
            }
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("Class " + ea0.class.getSimpleName() + " must have default constructor in order to be automatically recreated", e);
        }
    }
}
