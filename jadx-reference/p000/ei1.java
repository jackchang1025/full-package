package p000;

/* loaded from: classes2.dex */
public class ei1 extends AbstractC0158c3 {
    private static gi1 converter = new gi1();

    /* renamed from: f */
    protected AbstractC1330va f56065f;

    public ei1(AbstractC1330va abstractC1330va) {
        this.f56065f = abstractC1330va;
    }

    public AbstractC1330va getValue() {
        return this.f56065f;
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        return new C1048oy(converter.integerToBytes(this.f56065f.toBigInteger(), converter.getByteLength(this.f56065f)));
    }
}
