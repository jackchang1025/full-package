package p000;

/* renamed from: on */
/* loaded from: classes2.dex */
public class C0990on extends AbstractC0006a5 {
    public C0990on(String str) {
        super(str);
    }

    public static C0990on getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        AbstractC0164c9 object = abstractC0439e0.getObject();
        return (z || (object instanceof C0990on)) ? getInstance((Object) object) : new C0990on(AbstractC0161c6.getInstance(object).getOctets());
    }

    public C0990on(byte[] bArr) {
        super(bArr);
    }

    public static C0990on getInstance(Object obj) {
        if (obj == null || (obj instanceof C0990on)) {
            return (C0990on) obj;
        }
        if (obj instanceof AbstractC0006a5) {
            return new C0990on(((AbstractC0006a5) obj).string);
        }
        if (!(obj instanceof byte[])) {
            throw new IllegalArgumentException(AbstractC0003a2.m28a9(obj, "illegal object in getInstance: "));
        }
        try {
            return (C0990on) AbstractC0164c9.fromByteArray((byte[]) obj);
        } catch (Exception e) {
            throw new IllegalArgumentException(AbstractC0003a2.m27a8(e, new StringBuilder("encoding error in getInstance: ")));
        }
    }

    public C0990on(char[] cArr) {
        super(cArr);
    }
}
