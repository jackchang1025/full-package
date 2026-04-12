package p000;

/* renamed from: pj */
/* loaded from: classes2.dex */
public class C1071pj extends AbstractC0448e9 {
    public C1071pj(byte[] bArr) {
        this(bArr, true);
    }

    public static C1071pj getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        AbstractC0164c9 object = abstractC0439e0.getObject();
        return (z || (object instanceof C1071pj)) ? getInstance((Object) object) : new C1071pj(AbstractC0161c6.getInstance(object).getOctets());
    }

    public C1071pj(byte[] bArr, boolean z) {
        super(bArr, z);
    }

    public static C1071pj getInstance(Object obj) {
        if (obj == null || (obj instanceof C1071pj)) {
            return (C1071pj) obj;
        }
        if (obj instanceof AbstractC0448e9) {
            return new C1071pj(((AbstractC0448e9) obj).contents, false);
        }
        if (!(obj instanceof byte[])) {
            throw new IllegalArgumentException(AbstractC0003a2.m28a9(obj, "illegal object in getInstance: "));
        }
        try {
            return (C1071pj) AbstractC0164c9.fromByteArray((byte[]) obj);
        } catch (Exception e) {
            throw new IllegalArgumentException(AbstractC0003a2.m27a8(e, new StringBuilder("encoding error in getInstance: ")));
        }
    }
}
