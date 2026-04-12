package p000;

/* renamed from: ou */
/* loaded from: classes2.dex */
public class C1044ou extends AbstractC0124b7 {
    public C1044ou(byte[] bArr) {
        this(bArr, true);
    }

    public static C1044ou getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        AbstractC0164c9 object = abstractC0439e0.getObject();
        return (z || (object instanceof C1044ou)) ? getInstance((Object) object) : new C1044ou(AbstractC0161c6.getInstance(object).getOctets());
    }

    public C1044ou(byte[] bArr, boolean z) {
        super(bArr, z);
    }

    public static C1044ou getInstance(Object obj) {
        if (obj == null || (obj instanceof C1044ou)) {
            return (C1044ou) obj;
        }
        if (obj instanceof AbstractC0124b7) {
            return new C1044ou(((AbstractC0124b7) obj).contents, false);
        }
        if (!(obj instanceof byte[])) {
            throw new IllegalArgumentException(AbstractC0003a2.m28a9(obj, "illegal object in getInstance: "));
        }
        try {
            return (C1044ou) AbstractC0164c9.fromByteArray((byte[]) obj);
        } catch (Exception e) {
            throw new IllegalArgumentException(AbstractC0003a2.m27a8(e, new StringBuilder("encoding error in getInstance: ")));
        }
    }
}
