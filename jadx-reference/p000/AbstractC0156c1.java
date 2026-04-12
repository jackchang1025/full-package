package p000;

import java.io.IOException;

/* renamed from: c1 */
/* loaded from: classes2.dex */
public abstract class AbstractC0156c1 extends AbstractC0164c9 {
    static final AbstractC0445e6 TYPE = new a0(AbstractC0156c1.class, 5);

    /* renamed from: c1$a0 */
    public static class a0 extends AbstractC0445e6 {
        public a0(Class cls, int i) {
            super(cls, i);
        }

        @Override // p000.AbstractC0445e6
        public AbstractC0164c9 fromImplicitPrimitive(C1048oy c1048oy) {
            return AbstractC0156c1.createPrimitive(c1048oy.getOctets());
        }
    }

    public static AbstractC0156c1 createPrimitive(byte[] bArr) {
        if (bArr.length == 0) {
            return C1046ow.INSTANCE;
        }
        throw new IllegalStateException("malformed NULL encoding encountered");
    }

    public static AbstractC0156c1 getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        return (AbstractC0156c1) TYPE.getContextInstance(abstractC0439e0, z);
    }

    @Override // p000.AbstractC0164c9
    public boolean asn1Equals(AbstractC0164c9 abstractC0164c9) {
        return abstractC0164c9 instanceof AbstractC0156c1;
    }

    @Override // p000.AbstractC0164c9, p000.AbstractC0158c3
    public int hashCode() {
        return -1;
    }

    public String toString() {
        return "NULL";
    }

    public static AbstractC0156c1 getInstance(Object obj) {
        if (obj instanceof AbstractC0156c1) {
            return (AbstractC0156c1) obj;
        }
        if (obj == null) {
            return null;
        }
        try {
            return (AbstractC0156c1) TYPE.fromByteArray((byte[]) obj);
        } catch (IOException e) {
            throw new IllegalArgumentException(AbstractC0003a2.m26a7(e, new StringBuilder("failed to construct NULL from byte[]: ")));
        }
    }
}
