package p000;

import java.io.IOException;

/* renamed from: a5 */
/* loaded from: classes2.dex */
public abstract class AbstractC0006a5 extends AbstractC0164c9 implements InterfaceC0405d7 {
    static final AbstractC0445e6 TYPE = new a0(AbstractC0006a5.class, 30);
    final char[] string;

    /* renamed from: a5$a0 */
    public static class a0 extends AbstractC0445e6 {
        public a0(Class cls, int i) {
            super(cls, i);
        }

        @Override // p000.AbstractC0445e6
        public AbstractC0164c9 fromImplicitPrimitive(C1048oy c1048oy) {
            return AbstractC0006a5.createPrimitive(c1048oy.getOctets());
        }
    }

    public AbstractC0006a5(String str) {
        if (str == null) {
            throw new NullPointerException("'string' cannot be null");
        }
        this.string = str.toCharArray();
    }

    public static AbstractC0006a5 createPrimitive(byte[] bArr) {
        return new C0990on(bArr);
    }

    public static AbstractC0006a5 getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        return (AbstractC0006a5) TYPE.getContextInstance(abstractC0439e0, z);
    }

    @Override // p000.AbstractC0164c9
    public final boolean asn1Equals(AbstractC0164c9 abstractC0164c9) {
        if (abstractC0164c9 instanceof AbstractC0006a5) {
            return C0133bg.areEqual(this.string, ((AbstractC0006a5) abstractC0164c9).string);
        }
        return false;
    }

    @Override // p000.AbstractC0164c9
    public final void encode(C0163c8 c0163c8, boolean z) throws IOException {
        int length = this.string.length;
        c0163c8.writeIdentifier(z, 30);
        c0163c8.writeDL(length * 2);
        byte[] bArr = new byte[8];
        int i = length & (-4);
        int i2 = 0;
        while (i2 < i) {
            char[] cArr = this.string;
            char c = cArr[i2];
            char c2 = cArr[i2 + 1];
            char c3 = cArr[i2 + 2];
            char c4 = cArr[i2 + 3];
            i2 += 4;
            bArr[0] = (byte) (c >> '\b');
            bArr[1] = (byte) c;
            bArr[2] = (byte) (c2 >> '\b');
            bArr[3] = (byte) c2;
            bArr[4] = (byte) (c3 >> '\b');
            bArr[5] = (byte) c3;
            bArr[6] = (byte) (c4 >> '\b');
            bArr[7] = (byte) c4;
            c0163c8.write(bArr, 0, 8);
        }
        if (i2 < length) {
            int i3 = 0;
            do {
                char c5 = this.string[i2];
                i2++;
                int i4 = i3 + 1;
                bArr[i3] = (byte) (c5 >> '\b');
                i3 += 2;
                bArr[i4] = (byte) c5;
            } while (i2 < length);
            c0163c8.write(bArr, 0, i3);
        }
    }

    @Override // p000.AbstractC0164c9
    public final boolean encodeConstructed() {
        return false;
    }

    @Override // p000.AbstractC0164c9
    public final int encodedLength(boolean z) {
        return C0163c8.getLengthOfEncodingDL(z, this.string.length * 2);
    }

    @Override // p000.InterfaceC0405d7
    public final String getString() {
        return new String(this.string);
    }

    @Override // p000.AbstractC0164c9, p000.AbstractC0158c3
    public final int hashCode() {
        return C0133bg.hashCode(this.string);
    }

    public String toString() {
        return getString();
    }

    public AbstractC0006a5(byte[] bArr) {
        if (bArr == null) {
            throw new NullPointerException("'string' cannot be null");
        }
        int length = bArr.length;
        if ((length & 1) != 0) {
            throw new IllegalArgumentException("malformed BMPString encoding encountered");
        }
        int i = length / 2;
        char[] cArr = new char[i];
        for (int i2 = 0; i2 != i; i2++) {
            int i3 = i2 * 2;
            cArr[i2] = (char) ((bArr[i3 + 1] & 255) | (bArr[i3] << 8));
        }
        this.string = cArr;
    }

    public static AbstractC0006a5 createPrimitive(char[] cArr) {
        return new C0990on(cArr);
    }

    public static AbstractC0006a5 getInstance(Object obj) {
        if (obj == null || (obj instanceof AbstractC0006a5)) {
            return (AbstractC0006a5) obj;
        }
        if (obj instanceof InterfaceC0117b0) {
            AbstractC0164c9 aSN1Primitive = ((InterfaceC0117b0) obj).toASN1Primitive();
            if (aSN1Primitive instanceof AbstractC0006a5) {
                return (AbstractC0006a5) aSN1Primitive;
            }
        }
        if (!(obj instanceof byte[])) {
            throw new IllegalArgumentException(AbstractC0003a2.m28a9(obj, "illegal object in getInstance: "));
        }
        try {
            return (AbstractC0006a5) TYPE.fromByteArray((byte[]) obj);
        } catch (Exception e) {
            throw new IllegalArgumentException(AbstractC0003a2.m27a8(e, new StringBuilder("encoding error in getInstance: ")));
        }
    }

    public AbstractC0006a5(char[] cArr) {
        if (cArr == null) {
            throw new NullPointerException("'string' cannot be null");
        }
        this.string = cArr;
    }
}
