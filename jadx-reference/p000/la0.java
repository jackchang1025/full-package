package p000;

import androidx.lifecycle.C0076a0;
import androidx.lifecycle.Lifecycle$State;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class la0 {
    public /* synthetic */ la0(AbstractC1120qr abstractC1120qr) {
        this();
    }

    public final C0076a0 createUnsafe(ka0 ka0Var) {
        t60.m214695b6(ka0Var, "owner");
        return new C0076a0(ka0Var, false);
    }

    public final Lifecycle$State min$lifecycle_runtime_release(Lifecycle$State lifecycle$State, Lifecycle$State lifecycle$State2) {
        t60.m214695b6(lifecycle$State, "state1");
        return (lifecycle$State2 == null || lifecycle$State2.compareTo(lifecycle$State) >= 0) ? lifecycle$State : lifecycle$State2;
    }

    private la0() {
    }
}
