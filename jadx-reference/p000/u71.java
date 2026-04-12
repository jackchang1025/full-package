package p000;

import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class u71 extends t71 {

    /* renamed from: a0 */
    public final /* synthetic */ C0130bd f60337a0;

    /* renamed from: a1 */
    public final /* synthetic */ v71 f60338a1;

    public u71(v71 v71Var, C0130bd c0130bd) {
        this.f60338a1 = v71Var;
        this.f60337a0 = c0130bd;
    }

    @Override // p000.r71
    /* renamed from: a3 */
    public final void mo212985a3(s71 s71Var) {
        ((ArrayList) this.f60337a0.getOrDefault(this.f60338a1.f60596a1, null)).remove(s71Var);
        s71Var.m214581c0(this);
    }
}
