package p000;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.asn1.ASN1ParsingException;

/* renamed from: a6 */
/* loaded from: classes2.dex */
public abstract class AbstractC0007a6 extends AbstractC0164c9 implements InterfaceC0405d7, InterfaceC0008a7 {
    static final AbstractC0445e6 TYPE = new a0(AbstractC0007a6.class, 3);
    private static final char[] table = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    final byte[] contents;

    /* renamed from: a6$a0 */
    public static class a0 extends AbstractC0445e6 {
        public a0(Class cls, int i) {
            super(cls, i);
        }

        @Override // p000.AbstractC0445e6
        public AbstractC0164c9 fromImplicitConstructed(AbstractC0400d2 abstractC0400d2) {
            return abstractC0400d2.toASN1BitString();
        }

        @Override // p000.AbstractC0445e6
        public AbstractC0164c9 fromImplicitPrimitive(C1048oy c1048oy) {
            return AbstractC0007a6.createPrimitive(c1048oy.getOctets());
        }
    }

    public AbstractC0007a6(byte b, int i) {
        if (i > 7 || i < 0) {
            throw new IllegalArgumentException("pad bits cannot be greater than 7 or less than 0");
        }
        this.contents = new byte[]{(byte) i, b};
    }

    public static AbstractC0007a6 createPrimitive(byte[] bArr) {
        int length = bArr.length;
        if (length < 1) {
            throw new IllegalArgumentException("truncated BIT STRING detected");
        }
        int i = bArr[0] & 255;
        if (i > 0) {
            if (i > 7 || length < 2) {
                throw new IllegalArgumentException("invalid pad bits detected");
            }
            byte b = bArr[length - 1];
            if (b != ((byte) ((v10.MASK << i) & b))) {
                return new C1077pp(bArr, false);
            }
        }
        return new C0991oo(bArr, false);
    }

    public static AbstractC0007a6 getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        return (AbstractC0007a6) TYPE.getContextInstance(abstractC0439e0, z);
    }

    @Override // p000.AbstractC0164c9
    public boolean asn1Equals(AbstractC0164c9 abstractC0164c9) {
        if (!(abstractC0164c9 instanceof AbstractC0007a6)) {
            return false;
        }
        byte[] bArr = this.contents;
        byte[] bArr2 = ((AbstractC0007a6) abstractC0164c9).contents;
        int length = bArr.length;
        if (bArr2.length != length) {
            return false;
        }
        if (length == 1) {
            return true;
        }
        int i = length - 1;
        for (int i2 = 0; i2 < i; i2++) {
            if (bArr[i2] != bArr2[i2]) {
                return false;
            }
        }
        int i3 = bArr[0] & 255;
        byte b = bArr[i];
        int i4 = v10.MASK << i3;
        return ((byte) (b & i4)) == ((byte) (bArr2[i] & i4));
    }

    @Override // p000.InterfaceC0008a7
    public InputStream getBitStream() throws IOException {
        byte[] bArr = this.contents;
        return new ByteArrayInputStream(bArr, 1, bArr.length - 1);
    }

    public byte[] getBytes() {
        byte[] bArr = this.contents;
        if (bArr.length == 1) {
            return AbstractC0161c6.EMPTY_OCTETS;
        }
        int i = bArr[0] & 255;
        byte[] bArrCopyOfRange = C0133bg.copyOfRange(bArr, 1, bArr.length);
        int length = bArrCopyOfRange.length - 1;
        bArrCopyOfRange[length] = (byte) (((byte) (v10.MASK << i)) & bArrCopyOfRange[length]);
        return bArrCopyOfRange;
    }

    @Override // p000.InterfaceC0008a7, p000.i50
    public AbstractC0164c9 getLoadedObject() {
        return toASN1Primitive();
    }

    @Override // p000.InterfaceC0008a7
    public InputStream getOctetStream() throws IOException {
        int i = this.contents[0] & 255;
        if (i == 0) {
            return getBitStream();
        }
        throw new IOException(tz0.m214802a2(i, "expected octet-aligned bitstring, but found padBits: "));
    }

    public byte[] getOctets() {
        byte[] bArr = this.contents;
        if (bArr[0] == 0) {
            return C0133bg.copyOfRange(bArr, 1, bArr.length);
        }
        throw new IllegalStateException("attempt to get non-octet aligned data from BIT STRING");
    }

    @Override // p000.InterfaceC0008a7
    public int getPadBits() {
        return this.contents[0] & 255;
    }

    @Override // p000.InterfaceC0405d7
    public String getString() {
        try {
            byte[] encoded = getEncoded();
            StringBuffer stringBuffer = new StringBuffer((encoded.length * 2) + 1);
            stringBuffer.append('#');
            for (int i = 0; i != encoded.length; i++) {
                byte b = encoded[i];
                char[] cArr = table;
                stringBuffer.append(cArr[(b >>> 4) & 15]);
                stringBuffer.append(cArr[b & 15]);
            }
            return stringBuffer.toString();
        } catch (IOException e) {
            throw new ASN1ParsingException(AbstractC0003a2.m26a7(e, new StringBuilder("Internal error encoding BitString: ")), e);
        }
    }

    @Override // p000.AbstractC0164c9, p000.AbstractC0158c3
    public int hashCode() {
        byte[] bArr = this.contents;
        if (bArr.length < 2) {
            return 1;
        }
        int i = bArr[0] & 255;
        int length = bArr.length - 1;
        return (C0133bg.hashCode(bArr, 0, length) * 257) ^ ((byte) ((v10.MASK << i) & bArr[length]));
    }

    public int intValue() {
        int iMin = Math.min(5, this.contents.length - 1);
        int i = 0;
        for (int i2 = 1; i2 < iMin; i2++) {
            i |= (255 & this.contents[i2]) << ((i2 - 1) * 8);
        }
        if (1 > iMin || iMin >= 5) {
            return i;
        }
        byte[] bArr = this.contents;
        return ((((byte) (bArr[iMin] & (v10.MASK << (bArr[0] & 255)))) & 255) << ((iMin - 1) * 8)) | i;
    }

    @Override // p000.AbstractC0164c9
    public AbstractC0164c9 toDERObject() {
        return new C0991oo(this.contents, false);
    }

    @Override // p000.AbstractC0164c9
    public AbstractC0164c9 toDLObject() {
        return new C1077pp(this.contents, false);
    }

    public String toString() {
        return getString();
    }

    public AbstractC0007a6(byte[] bArr, int i) {
        if (bArr == null) {
            throw new NullPointerException("'data' cannot be null");
        }
        if (bArr.length == 0 && i != 0) {
            throw new IllegalArgumentException("zero length data with non-zero pad bits");
        }
        if (i > 7 || i < 0) {
            throw new IllegalArgumentException("pad bits cannot be greater than 7 or less than 0");
        }
        this.contents = C0133bg.prepend(bArr, (byte) i);
    }

    public static byte[] getBytes(int i) {
        if (i == 0) {
            return new byte[0];
        }
        int i2 = 4;
        for (int i3 = 3; i3 >= 1 && ((v10.MASK << (i3 * 8)) & i) == 0; i3--) {
            i2--;
        }
        byte[] bArr = new byte[i2];
        for (int i4 = 0; i4 < i2; i4++) {
            bArr[i4] = (byte) ((i >> (i4 * 8)) & v10.MASK);
        }
        return bArr;
    }

    public static AbstractC0007a6 getInstance(Object obj) {
        if (obj == null || (obj instanceof AbstractC0007a6)) {
            return (AbstractC0007a6) obj;
        }
        if (obj instanceof InterfaceC0117b0) {
            AbstractC0164c9 aSN1Primitive = ((InterfaceC0117b0) obj).toASN1Primitive();
            if (aSN1Primitive instanceof AbstractC0007a6) {
                return (AbstractC0007a6) aSN1Primitive;
            }
        } else if (obj instanceof byte[]) {
            try {
                return (AbstractC0007a6) TYPE.fromByteArray((byte[]) obj);
            } catch (IOException e) {
                throw new IllegalArgumentException(AbstractC0003a2.m26a7(e, new StringBuilder("failed to construct BIT STRING from byte[]: ")));
            }
        }
        throw new IllegalArgumentException(AbstractC0003a2.m28a9(obj, "illegal object in getInstance: "));
    }

    public static int getPadBits(int i) {
        int i2;
        int i3 = 3;
        while (true) {
            if (i3 < 0) {
                i2 = 0;
                break;
            }
            if (i3 != 0) {
                int i4 = i >> (i3 * 8);
                if (i4 != 0) {
                    i2 = i4 & v10.MASK;
                    break;
                }
                i3--;
            } else {
                if (i != 0) {
                    i2 = i & v10.MASK;
                    break;
                }
                i3--;
            }
        }
        if (i2 == 0) {
            return 0;
        }
        int i5 = 1;
        while (true) {
            i2 <<= 1;
            if ((i2 & v10.MASK) == 0) {
                return 8 - i5;
            }
            i5++;
        }
    }

    public AbstractC0007a6(byte[] bArr, boolean z) {
        if (z) {
            if (bArr == null) {
                throw new NullPointerException("'contents' cannot be null");
            }
            if (bArr.length < 1) {
                throw new IllegalArgumentException("'contents' cannot be empty");
            }
            int i = bArr[0] & 255;
            if (i > 0) {
                if (bArr.length < 2) {
                    throw new IllegalArgumentException("zero length data with non-zero pad bits");
                }
                if (i > 7) {
                    throw new IllegalArgumentException("pad bits cannot be greater than 7 or less than 0");
                }
            }
        }
        this.contents = bArr;
    }

    public InterfaceC0008a7 parser() {
        return this;
    }
}
