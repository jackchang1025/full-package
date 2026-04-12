package p000;

import java.util.Iterator;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: jl */
/* loaded from: classes2.dex */
public final class C0722jl implements nz0 {

    /* renamed from: a0 */
    public final /* synthetic */ int f57343a0;

    /* renamed from: a1 */
    public final /* synthetic */ Object f57344a1;

    public /* synthetic */ C0722jl(int i, Object obj) {
        this.f57343a0 = i;
        this.f57344a1 = obj;
    }

    @Override // p000.nz0
    public final Iterator iterator() {
        switch (this.f57343a0) {
            case 0:
                return ((Iterable) this.f57344a1).iterator();
            case 1:
                RestrictedSuspendLambda restrictedSuspendLambda = (RestrictedSuspendLambda) this.f57344a1;
                oz0 oz0Var = new oz0();
                oz0Var.f59126a2 = restrictedSuspendLambda.create(oz0Var, oz0Var);
                return oz0Var;
            default:
                return (Iterator) this.f57344a1;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public C0722jl(l10 l10Var) {
        this.f57343a0 = 1;
        this.f57344a1 = (RestrictedSuspendLambda) l10Var;
    }
}
