package f0;

/* renamed from: f0.u */
/* loaded from: classes.dex */
public final class C0300u extends AbstractC0303x {

    /* renamed from: b */
    public final Object f556b;

    public C0300u(int i2, InterfaceC0301v interfaceC0301v) {
        super(i2);
        if (i2 <= 0) {
            throw new IllegalArgumentException("length should be > 0");
        }
        this.f556b = interfaceC0301v;
    }

    @Override // f0.AbstractC0303x
    /* renamed from: a */
    public final AbstractC0303x mo820a(InterfaceC0294o interfaceC0294o, C0292m c0292m) {
        byte[] bArr = new byte[this.f559a];
        c0292m.m807e(bArr);
        ((InterfaceC0301v) this.f556b).mo391c(bArr);
        return null;
    }
}
