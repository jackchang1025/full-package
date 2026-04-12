package p000;

import kotlin.coroutines.AbstractC0775a0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class p81 implements InterfaceC0910ne, InterfaceC0911nf {

    /* renamed from: a0 */
    public static final p81 f59166a0 = new p81();

    @Override // p000.InterfaceC0912ng
    /* renamed from: b0 */
    public final Object mo212743b0(Object obj, l10 l10Var) {
        t60.m214695b6(l10Var, "operation");
        return l10Var.invoke(obj, this);
    }

    @Override // p000.InterfaceC0912ng
    /* renamed from: b2 */
    public final InterfaceC0912ng mo212744b2(InterfaceC0912ng interfaceC0912ng) {
        return AbstractC0775a0.m213638a1(this, interfaceC0912ng);
    }

    @Override // p000.InterfaceC0912ng
    /* renamed from: b4 */
    public final InterfaceC0910ne mo212745b4(InterfaceC0911nf interfaceC0911nf) {
        t60.m214695b6(interfaceC0911nf, "key");
        if (t60.m214686a2(this, interfaceC0911nf)) {
            return this;
        }
        return null;
    }

    @Override // p000.InterfaceC0912ng
    /* renamed from: c0 */
    public final InterfaceC0912ng mo212746c0(InterfaceC0911nf interfaceC0911nf) {
        return AbstractC0775a0.m213637a0(this, interfaceC0911nf);
    }

    @Override // p000.InterfaceC0910ne
    public final InterfaceC0911nf getKey() {
        return this;
    }
}
