package p000;

import java.io.IOException;

/* renamed from: a8 */
/* loaded from: classes2.dex */
public class C0009a8 extends AbstractC0164c9 {
    private final byte value;
    static final AbstractC0445e6 TYPE = new a0(C0009a8.class, 1);
    private static final byte FALSE_VALUE = 0;
    public static final C0009a8 FALSE = new C0009a8(FALSE_VALUE);
    private static final byte TRUE_VALUE = -1;
    public static final C0009a8 TRUE = new C0009a8(TRUE_VALUE);

    /* renamed from: a8$a0 */
    public static class a0 extends AbstractC0445e6 {
        public a0(Class cls, int i) {
            super(cls, i);
        }

        @Override // p000.AbstractC0445e6
        public AbstractC0164c9 fromImplicitPrimitive(C1048oy c1048oy) {
            return C0009a8.createPrimitive(c1048oy.getOctets());
        }
    }

    private C0009a8(byte b) {
        this.value = b;
    }

    public static C0009a8 createPrimitive(byte[] bArr) {
        if (bArr.length != 1) {
            throw new IllegalArgumentException("BOOLEAN value should have 1 byte in it");
        }
        byte b = bArr[0];
        return b != -1 ? b != 0 ? new C0009a8(b) : FALSE : TRUE;
    }

    public static C0009a8 getInstance(int i) {
        return i != 0 ? TRUE : FALSE;
    }

    @Override // p000.AbstractC0164c9
    public boolean asn1Equals(AbstractC0164c9 abstractC0164c9) {
        return (abstractC0164c9 instanceof C0009a8) && isTrue() == ((C0009a8) abstractC0164c9).isTrue();
    }

    @Override // p000.AbstractC0164c9
    public void encode(C0163c8 c0163c8, boolean z) throws IOException {
        c0163c8.writeEncodingDL(z, 1, this.value);
    }

    @Override // p000.AbstractC0164c9
    public boolean encodeConstructed() {
        return false;
    }

    @Override // p000.AbstractC0164c9
    public int encodedLength(boolean z) {
        return C0163c8.getLengthOfEncodingDL(z, 1);
    }

    @Override // p000.AbstractC0164c9, p000.AbstractC0158c3
    public int hashCode() {
        return isTrue() ? 1 : 0;
    }

    public boolean isTrue() {
        return this.value != 0;
    }

    @Override // p000.AbstractC0164c9
    public AbstractC0164c9 toDERObject() {
        return isTrue() ? TRUE : FALSE;
    }

    public String toString() {
        return isTrue() ? "TRUE" : "FALSE";
    }

    public static C0009a8 getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        return (C0009a8) TYPE.getContextInstance(abstractC0439e0, z);
    }

    public static C0009a8 getInstance(Object obj) {
        if (obj == null || (obj instanceof C0009a8)) {
            return (C0009a8) obj;
        }
        if (obj instanceof byte[]) {
            try {
                return (C0009a8) TYPE.fromByteArray((byte[]) obj);
            } catch (IOException e) {
                throw new IllegalArgumentException(AbstractC0003a2.m26a7(e, new StringBuilder("failed to construct boolean from byte[]: ")));
            }
        }
        throw new IllegalArgumentException(AbstractC0003a2.m28a9(obj, "illegal object in getInstance: "));
    }

    public static C0009a8 getInstance(boolean z) {
        return z ? TRUE : FALSE;
    }
}
