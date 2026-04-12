package p000;

/* loaded from: classes2.dex */
public class t21 extends AbstractC0158c3 {
    private byte[] keyidentifier;

    public t21(AbstractC0161c6 abstractC0161c6) {
        this(abstractC0161c6.getOctets());
    }

    public static t21 fromExtensions(C1454ye c1454ye) {
        return getInstance(C1454ye.getExtensionParsedValue(c1454ye, C1452yc.subjectKeyIdentifier));
    }

    public static t21 getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        return getInstance(AbstractC0161c6.getInstance(abstractC0439e0, z));
    }

    public byte[] getKeyIdentifier() {
        return C0133bg.clone(this.keyidentifier);
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        return new C1048oy(getKeyIdentifier());
    }

    public t21(byte[] bArr) {
        this.keyidentifier = C0133bg.clone(bArr);
    }

    public static t21 getInstance(Object obj) {
        if (obj instanceof t21) {
            return (t21) obj;
        }
        if (obj != null) {
            return new t21(AbstractC0161c6.getInstance(obj));
        }
        return null;
    }
}
