package p000;

/* renamed from: os */
/* loaded from: classes2.dex */
public class C1042os extends AbstractC0122b5 {
    public C1042os(String str) {
        super(str);
    }

    public static C1042os getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        AbstractC0164c9 object = abstractC0439e0.getObject();
        return (z || (object instanceof C1042os)) ? getInstance((Object) object) : new C1042os(AbstractC0161c6.getInstance(object).getOctets(), true);
    }

    public C1042os(byte[] bArr, boolean z) {
        super(bArr, z);
    }

    public static C1042os getInstance(Object obj) {
        if (obj == null || (obj instanceof C1042os)) {
            return (C1042os) obj;
        }
        if (obj instanceof AbstractC0122b5) {
            return new C1042os(((AbstractC0122b5) obj).contents, false);
        }
        if (!(obj instanceof byte[])) {
            throw new IllegalArgumentException(AbstractC0003a2.m28a9(obj, "illegal object in getInstance: "));
        }
        try {
            return (C1042os) AbstractC0164c9.fromByteArray((byte[]) obj);
        } catch (Exception e) {
            throw new IllegalArgumentException(AbstractC0003a2.m27a8(e, new StringBuilder("encoding error in getInstance: ")));
        }
    }
}
