package p000;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;

/* renamed from: d1 */
/* loaded from: classes2.dex */
public class C0399d1 extends AbstractC0164c9 {
    private static final long LONG_LIMIT = 72057594037927808L;
    static final AbstractC0445e6 TYPE = new a0(C0399d1.class, 13);
    private byte[] contents;
    private final String identifier;

    /* renamed from: d1$a0 */
    public static class a0 extends AbstractC0445e6 {
        public a0(Class cls, int i) {
            super(cls, i);
        }

        @Override // p000.AbstractC0445e6
        public AbstractC0164c9 fromImplicitPrimitive(C1048oy c1048oy) {
            return C0399d1.createPrimitive(c1048oy.getOctets(), false);
        }
    }

    public C0399d1(C0399d1 c0399d1, String str) {
        if (!isValidIdentifier(str, 0)) {
            throw new IllegalArgumentException(AbstractC0003a2.m33b4("string ", str, " not a valid OID branch"));
        }
        this.identifier = c0399d1.getId() + "." + str;
    }

    public static C0399d1 createPrimitive(byte[] bArr, boolean z) {
        return new C0399d1(bArr, z);
    }

    private void doOutput(ByteArrayOutputStream byteArrayOutputStream) {
        ok0 ok0Var = new ok0(this.identifier);
        while (ok0Var.hasMoreTokens()) {
            String strNextToken = ok0Var.nextToken();
            if (strNextToken.length() <= 18) {
                writeField(byteArrayOutputStream, Long.parseLong(strNextToken));
            } else {
                writeField(byteArrayOutputStream, new BigInteger(strNextToken));
            }
        }
    }

    public static C0399d1 fromContents(byte[] bArr) {
        return createPrimitive(bArr, true);
    }

    private synchronized byte[] getContents() {
        try {
            if (this.contents == null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                doOutput(byteArrayOutputStream);
                this.contents = byteArrayOutputStream.toByteArray();
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.contents;
    }

    public static C0399d1 getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        return (C0399d1) TYPE.getContextInstance(abstractC0439e0, z);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0022, code lost:
    
        return false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean isValidIdentifier(String str, int i) {
        int length = str.length();
        int i2 = 0;
        while (true) {
            int i3 = length - 1;
            if (i3 < i) {
                return i2 != 0 && (i2 <= 1 || str.charAt(length) != '0');
            }
            char cCharAt = str.charAt(i3);
            if (cCharAt == '.') {
                if (i2 == 0 || (i2 > 1 && str.charAt(length) == '0')) {
                    break;
                }
                i2 = 0;
            } else {
                if ('0' > cCharAt || cCharAt > '9') {
                    break;
                }
                i2++;
            }
            length = i3;
        }
        return false;
    }

    public static void writeField(ByteArrayOutputStream byteArrayOutputStream, long j) {
        byte[] bArr = new byte[9];
        int i = 8;
        bArr[8] = (byte) (((int) j) & 127);
        while (j >= 128) {
            j >>= 7;
            i--;
            bArr[i] = (byte) (((int) j) | 128);
        }
        byteArrayOutputStream.write(bArr, i, 9 - i);
    }

    @Override // p000.AbstractC0164c9
    public boolean asn1Equals(AbstractC0164c9 abstractC0164c9) {
        if (this == abstractC0164c9) {
            return true;
        }
        if (abstractC0164c9 instanceof C0399d1) {
            return this.identifier.equals(((C0399d1) abstractC0164c9).identifier);
        }
        return false;
    }

    public C0399d1 branch(String str) {
        return new C0399d1(this, str);
    }

    @Override // p000.AbstractC0164c9
    public void encode(C0163c8 c0163c8, boolean z) throws IOException {
        c0163c8.writeEncodingDL(z, 13, getContents());
    }

    @Override // p000.AbstractC0164c9
    public boolean encodeConstructed() {
        return false;
    }

    @Override // p000.AbstractC0164c9
    public int encodedLength(boolean z) {
        return C0163c8.getLengthOfEncodingDL(z, getContents().length);
    }

    public String getId() {
        return this.identifier;
    }

    @Override // p000.AbstractC0164c9, p000.AbstractC0158c3
    public int hashCode() {
        return this.identifier.hashCode();
    }

    public String toString() {
        return getId();
    }

    public static C0399d1 getInstance(Object obj) {
        if (obj == null || (obj instanceof C0399d1)) {
            return (C0399d1) obj;
        }
        if (obj instanceof InterfaceC0117b0) {
            AbstractC0164c9 aSN1Primitive = ((InterfaceC0117b0) obj).toASN1Primitive();
            if (aSN1Primitive instanceof C0399d1) {
                return (C0399d1) aSN1Primitive;
            }
        } else if (obj instanceof byte[]) {
            try {
                return (C0399d1) TYPE.fromByteArray((byte[]) obj);
            } catch (IOException e) {
                throw new IllegalArgumentException(AbstractC0003a2.m26a7(e, new StringBuilder("failed to construct relative OID from byte[]: ")));
            }
        }
        throw new IllegalArgumentException(AbstractC0003a2.m28a9(obj, "illegal object in getInstance: "));
    }

    public static void writeField(ByteArrayOutputStream byteArrayOutputStream, BigInteger bigInteger) {
        int iBitLength = (bigInteger.bitLength() + 6) / 7;
        if (iBitLength == 0) {
            byteArrayOutputStream.write(0);
            return;
        }
        byte[] bArr = new byte[iBitLength];
        int i = iBitLength - 1;
        for (int i2 = i; i2 >= 0; i2--) {
            bArr[i2] = (byte) (bigInteger.intValue() | 128);
            bigInteger = bigInteger.shiftRight(7);
        }
        bArr[i] = (byte) (bArr[i] & Byte.MAX_VALUE);
        byteArrayOutputStream.write(bArr, 0, iBitLength);
    }

    public C0399d1(String str) {
        if (str == null) {
            throw new NullPointerException("'identifier' cannot be null");
        }
        if (!isValidIdentifier(str, 0)) {
            throw new IllegalArgumentException(AbstractC0003a2.m33b4("string ", str, " not a relative OID"));
        }
        this.identifier = str;
    }

    private C0399d1(byte[] bArr, boolean z) {
        byte[] bArr2 = bArr;
        StringBuffer stringBuffer = new StringBuffer();
        boolean z2 = true;
        BigInteger bigIntegerShiftLeft = null;
        long j = 0;
        for (int i = 0; i != bArr2.length; i++) {
            byte b = bArr2[i];
            if (j <= LONG_LIMIT) {
                long j2 = j + (b & Byte.MAX_VALUE);
                if ((b & 128) == 0) {
                    if (z2) {
                        z2 = false;
                    } else {
                        stringBuffer.append('.');
                    }
                    stringBuffer.append(j2);
                    j = 0;
                } else {
                    j = j2 << 7;
                }
            } else {
                BigInteger bigIntegerOr = (bigIntegerShiftLeft == null ? BigInteger.valueOf(j) : bigIntegerShiftLeft).or(BigInteger.valueOf(b & Byte.MAX_VALUE));
                if ((b & 128) == 0) {
                    if (z2) {
                        z2 = false;
                    } else {
                        stringBuffer.append('.');
                    }
                    stringBuffer.append(bigIntegerOr);
                    bigIntegerShiftLeft = null;
                    j = 0;
                } else {
                    bigIntegerShiftLeft = bigIntegerOr.shiftLeft(7);
                }
            }
        }
        this.identifier = stringBuffer.toString();
        this.contents = z ? C0133bg.clone(bArr2) : bArr2;
    }
}
