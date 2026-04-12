package p000;

import android.os.Bundle;
import androidx.lifecycle.C0076a0;
import androidx.lifecycle.Lifecycle$Event;
import androidx.lifecycle.Lifecycle$State;
import androidx.savedstate.Recreator;
import java.util.Map;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class xt0 {

    /* renamed from: a3 */
    public static final wt0 f61176a3 = new wt0(null);

    /* renamed from: a0 */
    public final yt0 f61177a0;

    /* renamed from: a1 */
    public final vt0 f61178a1 = new vt0();

    /* renamed from: a2 */
    public boolean f61179a2;

    public xt0(yt0 yt0Var) {
        this.f61177a0 = yt0Var;
    }

    /* renamed from: a0 */
    public final void m215205a0() {
        yt0 yt0Var = this.f61177a0;
        C0076a0 c0076a0Mo209830a5 = yt0Var.mo209830a5();
        if (c0076a0Mo209830a5.f45191a6 != Lifecycle$State.f45174a1) {
            throw new IllegalStateException("Restarter must be created only during owner's initialization stage");
        }
        c0076a0Mo209830a5.mo210230a0(new Recreator(yt0Var));
        final vt0 vt0Var = this.f61178a1;
        vt0Var.getClass();
        if (vt0Var.f60683a1) {
            throw new IllegalStateException("SavedStateRegistry was already attached.");
        }
        c0076a0Mo209830a5.mo210230a0(new ia0() { // from class: rt0
            @Override // p000.ia0
            /* renamed from: a2 */
            public final void mo209833a2(ka0 ka0Var, Lifecycle$Event lifecycle$Event) {
                vt0 vt0Var2 = vt0Var;
                t60.m214695b6(vt0Var2, "this$0");
                if (lifecycle$Event == Lifecycle$Event.ON_START) {
                    vt0Var2.f60687a5 = true;
                } else if (lifecycle$Event == Lifecycle$Event.ON_STOP) {
                    vt0Var2.f60687a5 = false;
                }
            }
        });
        vt0Var.f60683a1 = true;
        this.f61179a2 = true;
    }

    /* renamed from: a1 */
    public final void m215206a1(Bundle bundle) {
        if (!this.f61179a2) {
            m215205a0();
        }
        C0076a0 c0076a0Mo209830a5 = this.f61177a0.mo209830a5();
        if (c0076a0Mo209830a5.f45191a6.compareTo(Lifecycle$State.f45176a3) >= 0) {
            throw new IllegalStateException(("performRestore cannot be called when owner is " + c0076a0Mo209830a5.f45191a6).toString());
        }
        vt0 vt0Var = this.f61178a1;
        if (!vt0Var.f60683a1) {
            throw new IllegalStateException("You must call performAttach() before calling performRestore(Bundle).");
        }
        if (vt0Var.f60685a3) {
            throw new IllegalStateException("SavedStateRegistry was already restored.");
        }
        vt0Var.f60684a2 = bundle != null ? bundle.getBundle("androidx.lifecycle.BundlableSavedStateRegistry.key") : null;
        vt0Var.f60685a3 = true;
    }

    /* renamed from: a2 */
    public final void m215207a2(Bundle bundle) {
        t60.m214695b6(bundle, "outBundle");
        vt0 vt0Var = this.f61178a1;
        vt0Var.getClass();
        Bundle bundle2 = new Bundle();
        Bundle bundle3 = vt0Var.f60684a2;
        if (bundle3 != null) {
            bundle2.putAll(bundle3);
        }
        nt0 nt0Var = vt0Var.f60682a0;
        nt0Var.getClass();
        lt0 lt0Var = new lt0(nt0Var);
        nt0Var.f58694a2.put(lt0Var, Boolean.FALSE);
        while (lt0Var.hasNext()) {
            Map.Entry entry = (Map.Entry) lt0Var.next();
            bundle2.putBundle((String) entry.getKey(), ((ut0) entry.getValue()).mo210245a0());
        }
        if (bundle2.isEmpty()) {
            return;
        }
        bundle.putBundle("androidx.lifecycle.BundlableSavedStateRegistry.key", bundle2);
    }
}
