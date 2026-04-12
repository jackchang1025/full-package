package p000;

/* renamed from: pi */
/* loaded from: classes2.dex */
public class C1070pi extends AbstractC0444e5 {
    public C1070pi(byte[] bArr) {
        this(bArr, true);
    }

    public static C1070pi getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        AbstractC0164c9 object = abstractC0439e0.getObject();
        return (z || (object instanceof C1070pi)) ? getInstance((Object) object) : new C1070pi(AbstractC0161c6.getInstance(object).getOctets(), true);
    }

    public C1070pi(byte[] bArr, boolean z) {
        super(bArr, z);
    }

    public static C1070pi getInstance(Object obj) {
        if (obj == null || (obj instanceof C1070pi)) {
            return (C1070pi) obj;
        }
        if (obj instanceof AbstractC0444e5) {
            return new C1070pi(((AbstractC0444e5) obj).contents, false);
        }
        if (!(obj instanceof byte[])) {
            throw new IllegalArgumentException(AbstractC0003a2.m28a9(obj, "illegal object in getInstance: "));
        }
        try {
            return (C1070pi) AbstractC0164c9.fromByteArray((byte[]) obj);
        } catch (Exception e) {
            throw new IllegalArgumentException(AbstractC0003a2.m27a8(e, new StringBuilder("encoding error getInstance: ")));
        }
    }
}
