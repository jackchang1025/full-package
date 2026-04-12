package p000;

import java.io.IOException;

/* renamed from: e5 */
/* loaded from: classes2.dex */
public abstract class AbstractC0444e5 extends AbstractC0164c9 implements InterfaceC0405d7 {
    static final AbstractC0445e6 TYPE = new a0(AbstractC0444e5.class, 28);
    private static final char[] table = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    final byte[] contents;

    /* renamed from: e5$a0 */
    public static class a0 extends AbstractC0445e6 {
        public a0(Class cls, int i) {
            super(cls, i);
        }

        @Override // p000.AbstractC0445e6
        public AbstractC0164c9 fromImplicitPrimitive(C1048oy c1048oy) {
            return AbstractC0444e5.createPrimitive(c1048oy.getOctets());
        }
    }

    public AbstractC0444e5(byte[] bArr, boolean z) {
        this.contents = z ? C0133bg.clone(bArr) : bArr;
    }

    public static AbstractC0444e5 createPrimitive(byte[] bArr) {
        return new C1070pi(bArr, false);
    }

    private static void encodeHexByte(StringBuffer stringBuffer, int i) {
        char[] cArr = table;
        stringBuffer.append(cArr[(i >>> 4) & 15]);
        stringBuffer.append(cArr[i & 15]);
    }

    private static void encodeHexDL(StringBuffer stringBuffer, int i) {
        int i2;
        if (i < 128) {
            encodeHexByte(stringBuffer, i);
            return;
        }
        byte[] bArr = new byte[5];
        int i3 = 5;
        while (true) {
            i2 = i3 - 1;
            bArr[i2] = (byte) i;
            i >>>= 8;
            if (i == 0) {
                break;
            } else {
                i3 = i2;
            }
        }
        int i4 = i3 - 2;
        bArr[i4] = (byte) ((5 - i2) | 128);
        while (true) {
            int i5 = i4 + 1;
            encodeHexByte(stringBuffer, bArr[i4]);
            if (i5 >= 5) {
                return;
            } else {
                i4 = i5;
            }
        }
    }

    public static AbstractC0444e5 getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        return (AbstractC0444e5) TYPE.getContextInstance(abstractC0439e0, z);
    }

    @Override // p000.AbstractC0164c9
    public final boolean asn1Equals(AbstractC0164c9 abstractC0164c9) {
        if (abstractC0164c9 instanceof AbstractC0444e5) {
            return C0133bg.areEqual(this.contents, ((AbstractC0444e5) abstractC0164c9).contents);
        }
        return false;
    }

    @Override // p000.AbstractC0164c9
    public final void encode(C0163c8 c0163c8, boolean z) throws IOException {
        c0163c8.writeEncodingDL(z, 28, this.contents);
    }

    @Override // p000.AbstractC0164c9
    public final boolean encodeConstructed() {
        return false;
    }

    @Override // p000.AbstractC0164c9
    public final int encodedLength(boolean z) {
        return C0163c8.getLengthOfEncodingDL(z, this.contents.length);
    }

    public final byte[] getOctets() {
        return C0133bg.clone(this.contents);
    }

    @Override // p000.InterfaceC0405d7
    public final String getString() {
        int length = this.contents.length;
        StringBuffer stringBuffer = new StringBuffer(((C0163c8.getLengthOfDL(length) + length) * 2) + 3);
        stringBuffer.append("#1C");
        encodeHexDL(stringBuffer, length);
        for (int i = 0; i < length; i++) {
            encodeHexByte(stringBuffer, this.contents[i]);
        }
        return stringBuffer.toString();
    }

    @Override // p000.AbstractC0164c9, p000.AbstractC0158c3
    public final int hashCode() {
        return C0133bg.hashCode(this.contents);
    }

    public String toString() {
        return getString();
    }

    public static AbstractC0444e5 getInstance(Object obj) {
        if (obj == null || (obj instanceof AbstractC0444e5)) {
            return (AbstractC0444e5) obj;
        }
        if (obj instanceof InterfaceC0117b0) {
            AbstractC0164c9 aSN1Primitive = ((InterfaceC0117b0) obj).toASN1Primitive();
            if (aSN1Primitive instanceof AbstractC0444e5) {
                return (AbstractC0444e5) aSN1Primitive;
            }
        }
        if (!(obj instanceof byte[])) {
            throw new IllegalArgumentException(AbstractC0003a2.m28a9(obj, "illegal object in getInstance: "));
        }
        try {
            return (AbstractC0444e5) TYPE.fromByteArray((byte[]) obj);
        } catch (Exception e) {
            throw new IllegalArgumentException(AbstractC0003a2.m27a8(e, new StringBuilder("encoding error getInstance: ")));
        }
    }
}
