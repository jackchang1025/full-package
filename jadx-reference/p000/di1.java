package p000;

/* loaded from: classes2.dex */
public class di1 extends AbstractC0158c3 {

    /* renamed from: c */
    private AbstractC1316ux f55817c;
    private final AbstractC0161c6 encoding;

    /* renamed from: p */
    private AbstractC1341vl f55818p;

    public di1(AbstractC1316ux abstractC1316ux, AbstractC0161c6 abstractC0161c6) {
        this(abstractC1316ux, abstractC0161c6.getOctets());
    }

    public synchronized AbstractC1341vl getPoint() {
        try {
            if (this.f55818p == null) {
                this.f55818p = this.f55817c.decodePoint(this.encoding.getOctets()).normalize();
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.f55818p;
    }

    public byte[] getPointEncoding() {
        return C0133bg.clone(this.encoding.getOctets());
    }

    public boolean isPointCompressed() {
        byte b;
        byte[] octets = this.encoding.getOctets();
        return octets != null && octets.length > 0 && ((b = octets[0]) == 2 || b == 3);
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        return this.encoding;
    }

    public di1(AbstractC1316ux abstractC1316ux, byte[] bArr) {
        this.f55817c = abstractC1316ux;
        this.encoding = new C1048oy(C0133bg.clone(bArr));
    }

    public di1(AbstractC1341vl abstractC1341vl, boolean z) {
        this.f55818p = abstractC1341vl.normalize();
        this.encoding = new C1048oy(abstractC1341vl.getEncoded(z));
    }
}
