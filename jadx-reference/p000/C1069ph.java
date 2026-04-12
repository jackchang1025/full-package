package p000;

/* renamed from: ph */
/* loaded from: classes2.dex */
public class C1069ph extends AbstractC0443e4 {
    public C1069ph(String str) {
        super(str);
    }

    public static C1069ph getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        AbstractC0164c9 object = abstractC0439e0.getObject();
        return (z || (object instanceof C1069ph)) ? getInstance((Object) object) : new C1069ph(AbstractC0161c6.getInstance(object).getOctets(), true);
    }

    public C1069ph(byte[] bArr, boolean z) {
        super(bArr, z);
    }

    public static C1069ph getInstance(Object obj) {
        if (obj == null || (obj instanceof C1069ph)) {
            return (C1069ph) obj;
        }
        if (obj instanceof AbstractC0443e4) {
            return new C1069ph(((AbstractC0443e4) obj).contents, false);
        }
        if (!(obj instanceof byte[])) {
            throw new IllegalArgumentException(AbstractC0003a2.m28a9(obj, "illegal object in getInstance: "));
        }
        try {
            return (C1069ph) AbstractC0164c9.fromByteArray((byte[]) obj);
        } catch (Exception e) {
            throw new IllegalArgumentException(AbstractC0003a2.m27a8(e, new StringBuilder("encoding error in getInstance: ")));
        }
    }
}
