package p000;

/* renamed from: ox */
/* loaded from: classes2.dex */
public class C1047ox extends AbstractC0157c2 {
    public C1047ox(String str) {
        this(str, false);
    }

    public static C1047ox getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        AbstractC0164c9 object = abstractC0439e0.getObject();
        return (z || (object instanceof C1047ox)) ? getInstance((Object) object) : new C1047ox(AbstractC0161c6.getInstance(object).getOctets(), true);
    }

    public C1047ox(String str, boolean z) {
        super(str, z);
    }

    public static C1047ox getInstance(Object obj) {
        if (obj == null || (obj instanceof C1047ox)) {
            return (C1047ox) obj;
        }
        if (obj instanceof AbstractC0157c2) {
            return new C1047ox(((AbstractC0157c2) obj).contents, false);
        }
        if (!(obj instanceof byte[])) {
            throw new IllegalArgumentException(AbstractC0003a2.m28a9(obj, "illegal object in getInstance: "));
        }
        try {
            return (C1047ox) AbstractC0164c9.fromByteArray((byte[]) obj);
        } catch (Exception e) {
            throw new IllegalArgumentException(AbstractC0003a2.m27a8(e, new StringBuilder("encoding error in getInstance: ")));
        }
    }

    public C1047ox(byte[] bArr, boolean z) {
        super(bArr, z);
    }
}
