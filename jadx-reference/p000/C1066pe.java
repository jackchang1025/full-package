package p000;

/* renamed from: pe */
/* loaded from: classes2.dex */
public class C1066pe extends AbstractC0406d8 {
    public C1066pe(String str) {
        super(str);
    }

    public static C1066pe getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        AbstractC0164c9 object = abstractC0439e0.getObject();
        return (z || (object instanceof C1066pe)) ? getInstance((Object) object) : new C1066pe(AbstractC0161c6.getInstance(object).getOctets(), true);
    }

    public C1066pe(byte[] bArr) {
        this(bArr, true);
    }

    public static C1066pe getInstance(Object obj) {
        if (obj == null || (obj instanceof C1066pe)) {
            return (C1066pe) obj;
        }
        if (obj instanceof AbstractC0406d8) {
            return new C1066pe(((AbstractC0406d8) obj).contents, false);
        }
        if (!(obj instanceof byte[])) {
            throw new IllegalArgumentException(AbstractC0003a2.m28a9(obj, "illegal object in getInstance: "));
        }
        try {
            return (C1066pe) AbstractC0164c9.fromByteArray((byte[]) obj);
        } catch (Exception e) {
            throw new IllegalArgumentException(AbstractC0003a2.m27a8(e, new StringBuilder("encoding error in getInstance: ")));
        }
    }

    public C1066pe(byte[] bArr, boolean z) {
        super(bArr, z);
    }
}
