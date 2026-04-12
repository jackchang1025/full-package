package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public class hu0 extends AbstractC0482f4 implements InterfaceC0921np {

    /* renamed from: a3 */
    public final InterfaceC0876mv f56754a3;

    public hu0(InterfaceC0912ng interfaceC0912ng, InterfaceC0876mv interfaceC0876mv) {
        super(interfaceC0912ng, true);
        this.f56754a3 = interfaceC0876mv;
    }

    @Override // p000.y70
    /* renamed from: a5 */
    public void mo212674a5(Object obj) {
        b81.m210592e3(AbstractC0732jv.m213356a0(obj), kj1.m213575c2(this.f56754a3));
    }

    @Override // p000.y70
    /* renamed from: a6 */
    public void mo213092a6(Object obj) {
        this.f56754a3.resumeWith(AbstractC0732jv.m213356a0(obj));
    }

    @Override // p000.y70
    /* renamed from: d0 */
    public final boolean mo213093d0() {
        return true;
    }

    @Override // p000.InterfaceC0921np
    public final InterfaceC0921np getCallerFrame() {
        InterfaceC0876mv interfaceC0876mv = this.f56754a3;
        if (interfaceC0876mv instanceof InterfaceC0921np) {
            return (InterfaceC0921np) interfaceC0876mv;
        }
        return null;
    }
}
