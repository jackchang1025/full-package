package p000;

/* renamed from: pk */
/* loaded from: classes2.dex */
public class C1072pk extends AbstractC0476f0 {
    public C1072pk(String str) {
        super(str);
    }

    public static C1072pk getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        AbstractC0164c9 object = abstractC0439e0.getObject();
        return (z || (object instanceof C1072pk)) ? getInstance((Object) object) : new C1072pk(AbstractC0161c6.getInstance(object).getOctets(), true);
    }

    public C1072pk(byte[] bArr, boolean z) {
        super(bArr, z);
    }

    public static C1072pk getInstance(Object obj) {
        if (obj == null || (obj instanceof C1072pk)) {
            return (C1072pk) obj;
        }
        if (obj instanceof AbstractC0476f0) {
            return new C1072pk(((AbstractC0476f0) obj).contents, false);
        }
        if (!(obj instanceof byte[])) {
            throw new IllegalArgumentException(AbstractC0003a2.m28a9(obj, "illegal object in getInstance: "));
        }
        try {
            return (C1072pk) AbstractC0164c9.fromByteArray((byte[]) obj);
        } catch (Exception e) {
            throw new IllegalArgumentException(AbstractC0003a2.m27a8(e, new StringBuilder("encoding error in getInstance: ")));
        }
    }
}
