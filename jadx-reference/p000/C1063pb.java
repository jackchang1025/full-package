package p000;

/* renamed from: pb */
/* loaded from: classes2.dex */
public class C1063pb extends AbstractC0398d0 {
    public C1063pb(String str) {
        this(str, false);
    }

    public static C1063pb getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        AbstractC0164c9 object = abstractC0439e0.getObject();
        return (z || (object instanceof C1063pb)) ? getInstance((Object) object) : new C1063pb(AbstractC0161c6.getInstance(object).getOctets(), true);
    }

    public C1063pb(String str, boolean z) {
        super(str, z);
    }

    public static C1063pb getInstance(Object obj) {
        if (obj == null || (obj instanceof C1063pb)) {
            return (C1063pb) obj;
        }
        if (obj instanceof AbstractC0398d0) {
            return new C1063pb(((AbstractC0398d0) obj).contents, false);
        }
        if (!(obj instanceof byte[])) {
            throw new IllegalArgumentException(AbstractC0003a2.m28a9(obj, "illegal object in getInstance: "));
        }
        try {
            return (C1063pb) AbstractC0164c9.fromByteArray((byte[]) obj);
        } catch (Exception e) {
            throw new IllegalArgumentException(AbstractC0003a2.m27a8(e, new StringBuilder("encoding error in getInstance: ")));
        }
    }

    public C1063pb(byte[] bArr, boolean z) {
        super(bArr, z);
    }
}
