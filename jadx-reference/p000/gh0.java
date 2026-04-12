package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class gh0 extends AbstractC0926nu {
    public gh0(AbstractC0926nu abstractC0926nu) {
        t60.m214695b6(abstractC0926nu, "initialExtras");
        this.f58696a0.putAll(abstractC0926nu.f58696a0);
    }

    /* renamed from: a0 */
    public final Object m212951a0(InterfaceC0925nt interfaceC0925nt) {
        t60.m214695b6(interfaceC0925nt, "key");
        return this.f58696a0.get(interfaceC0925nt);
    }

    /* renamed from: a1 */
    public final void m212952a1(InterfaceC0925nt interfaceC0925nt, Object obj) {
        t60.m214695b6(interfaceC0925nt, "key");
        this.f58696a0.put(interfaceC0925nt, obj);
    }
}
