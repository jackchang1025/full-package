package p000;

import java.util.concurrent.CancellationException;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: jr */
/* loaded from: classes2.dex */
public final class C0728jr {

    /* renamed from: a0 */
    public final Object f57358a0;

    /* renamed from: a1 */
    public final C0509fu f57359a1;

    /* renamed from: a2 */
    public final h10 f57360a2;

    /* renamed from: a3 */
    public final Object f57361a3;

    /* renamed from: a4 */
    public final Throwable f57362a4;

    public C0728jr(Object obj, C0509fu c0509fu, h10 h10Var, Object obj2, Throwable th) {
        this.f57358a0 = obj;
        this.f57359a1 = c0509fu;
        this.f57360a2 = h10Var;
        this.f57361a3 = obj2;
        this.f57362a4 = th;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v2, types: [java.lang.Throwable] */
    /* renamed from: a0 */
    public static C0728jr m213338a0(C0728jr c0728jr, C0509fu c0509fu, CancellationException cancellationException, int i) {
        Object obj = c0728jr.f57358a0;
        if ((i & 2) != 0) {
            c0509fu = c0728jr.f57359a1;
        }
        C0509fu c0509fu2 = c0509fu;
        h10 h10Var = c0728jr.f57360a2;
        Object obj2 = c0728jr.f57361a3;
        CancellationException cancellationException2 = cancellationException;
        if ((i & 16) != 0) {
            cancellationException2 = c0728jr.f57362a4;
        }
        return new C0728jr(obj, c0509fu2, h10Var, obj2, cancellationException2);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C0728jr)) {
            return false;
        }
        C0728jr c0728jr = (C0728jr) obj;
        return t60.m214686a2(this.f57358a0, c0728jr.f57358a0) && t60.m214686a2(this.f57359a1, c0728jr.f57359a1) && t60.m214686a2(this.f57360a2, c0728jr.f57360a2) && t60.m214686a2(this.f57361a3, c0728jr.f57361a3) && t60.m214686a2(this.f57362a4, c0728jr.f57362a4);
    }

    public final int hashCode() {
        Object obj = this.f57358a0;
        int iHashCode = (obj == null ? 0 : obj.hashCode()) * 31;
        C0509fu c0509fu = this.f57359a1;
        int iHashCode2 = (iHashCode + (c0509fu == null ? 0 : c0509fu.hashCode())) * 31;
        h10 h10Var = this.f57360a2;
        int iHashCode3 = (iHashCode2 + (h10Var == null ? 0 : h10Var.hashCode())) * 31;
        Object obj2 = this.f57361a3;
        int iHashCode4 = (iHashCode3 + (obj2 == null ? 0 : obj2.hashCode())) * 31;
        Throwable th = this.f57362a4;
        return iHashCode4 + (th != null ? th.hashCode() : 0);
    }

    public final String toString() {
        return "CompletedContinuation(result=" + this.f57358a0 + ", cancelHandler=" + this.f57359a1 + ", onCancellation=" + this.f57360a2 + ", idempotentResume=" + this.f57361a3 + ", cancelCause=" + this.f57362a4 + ')';
    }

    public /* synthetic */ C0728jr(Object obj, C0509fu c0509fu, h10 h10Var, CancellationException cancellationException, int i) {
        this(obj, (i & 2) != 0 ? null : c0509fu, (i & 4) != 0 ? null : h10Var, (Object) null, (i & 16) != 0 ? null : cancellationException);
    }
}
