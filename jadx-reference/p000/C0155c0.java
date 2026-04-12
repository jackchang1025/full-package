package p000;

import java.io.IOException;
import java.math.BigInteger;

/* renamed from: c0 */
/* loaded from: classes2.dex */
public class C0155c0 extends AbstractC0164c9 {
    static final int SIGN_EXT_SIGNED = -1;
    static final int SIGN_EXT_UNSIGNED = 255;
    static final AbstractC0445e6 TYPE = new a0(C0155c0.class, 2);
    private final byte[] bytes;
    private final int start;

    /* renamed from: c0$a0 */
    public static class a0 extends AbstractC0445e6 {
        public a0(Class cls, int i) {
            super(cls, i);
        }

        @Override // p000.AbstractC0445e6
        public AbstractC0164c9 fromImplicitPrimitive(C1048oy c1048oy) {
            return C0155c0.createPrimitive(c1048oy.getOctets());
        }
    }

    public C0155c0(long j) {
        this.bytes = BigInteger.valueOf(j).toByteArray();
        this.start = 0;
    }

    public static C0155c0 createPrimitive(byte[] bArr) {
        return new C0155c0(bArr, false);
    }

    public static C0155c0 getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        return (C0155c0) TYPE.getContextInstance(abstractC0439e0, z);
    }

    public static int intValue(byte[] bArr, int i, int i2) {
        int length = bArr.length;
        int iMax = Math.max(i, length - 4);
        int i3 = i2 & bArr[iMax];
        while (true) {
            iMax++;
            if (iMax >= length) {
                return i3;
            }
            i3 = (i3 << 8) | (bArr[iMax] & 255);
        }
    }

    public static boolean isMalformed(byte[] bArr) {
        int length = bArr.length;
        if (length != 0) {
            return (length == 1 || bArr[0] != (bArr[1] >> 7) || ap0.isOverrideSet("org.bouncycastle.asn1.allow_unsafe_integer")) ? false : true;
        }
        return true;
    }

    public static long longValue(byte[] bArr, int i, int i2) {
        int length = bArr.length;
        int iMax = Math.max(i, length - 8);
        long j = i2 & bArr[iMax];
        while (true) {
            iMax++;
            if (iMax >= length) {
                return j;
            }
            j = (j << 8) | (bArr[iMax] & 255);
        }
    }

    public static int signBytesToSkip(byte[] bArr) {
        int length = bArr.length - 1;
        int i = 0;
        while (i < length) {
            int i2 = i + 1;
            if (bArr[i] != (bArr[i2] >> 7)) {
                break;
            }
            i = i2;
        }
        return i;
    }

    @Override // p000.AbstractC0164c9
    public boolean asn1Equals(AbstractC0164c9 abstractC0164c9) {
        if (abstractC0164c9 instanceof C0155c0) {
            return C0133bg.areEqual(this.bytes, ((C0155c0) abstractC0164c9).bytes);
        }
        return false;
    }

    @Override // p000.AbstractC0164c9
    public void encode(C0163c8 c0163c8, boolean z) throws IOException {
        c0163c8.writeEncodingDL(z, 2, this.bytes);
    }

    @Override // p000.AbstractC0164c9
    public boolean encodeConstructed() {
        return false;
    }

    @Override // p000.AbstractC0164c9
    public int encodedLength(boolean z) {
        return C0163c8.getLengthOfEncodingDL(z, this.bytes.length);
    }

    public BigInteger getPositiveValue() {
        return new BigInteger(1, this.bytes);
    }

    public BigInteger getValue() {
        return new BigInteger(this.bytes);
    }

    public boolean hasValue(int i) {
        byte[] bArr = this.bytes;
        int length = bArr.length;
        int i2 = this.start;
        return length - i2 <= 4 && intValue(bArr, i2, -1) == i;
    }

    @Override // p000.AbstractC0164c9, p000.AbstractC0158c3
    public int hashCode() {
        return C0133bg.hashCode(this.bytes);
    }

    public int intPositiveValueExact() {
        byte[] bArr = this.bytes;
        int length = bArr.length;
        int i = this.start;
        int i2 = length - i;
        if (i2 > 4 || (i2 == 4 && (bArr[i] & 128) != 0)) {
            throw new ArithmeticException("ASN.1 Integer out of positive int range");
        }
        return intValue(bArr, i, 255);
    }

    public int intValueExact() {
        byte[] bArr = this.bytes;
        int length = bArr.length;
        int i = this.start;
        if (length - i <= 4) {
            return intValue(bArr, i, -1);
        }
        throw new ArithmeticException("ASN.1 Integer out of int range");
    }

    public long longValueExact() {
        byte[] bArr = this.bytes;
        int length = bArr.length;
        int i = this.start;
        if (length - i <= 8) {
            return longValue(bArr, i, -1);
        }
        throw new ArithmeticException("ASN.1 Integer out of long range");
    }

    public String toString() {
        return getValue().toString();
    }

    public C0155c0(BigInteger bigInteger) {
        this.bytes = bigInteger.toByteArray();
        this.start = 0;
    }

    public static C0155c0 getInstance(Object obj) {
        if (obj == null || (obj instanceof C0155c0)) {
            return (C0155c0) obj;
        }
        if (!(obj instanceof byte[])) {
            throw new IllegalArgumentException(AbstractC0003a2.m28a9(obj, "illegal object in getInstance: "));
        }
        try {
            return (C0155c0) TYPE.fromByteArray((byte[]) obj);
        } catch (Exception e) {
            throw new IllegalArgumentException(AbstractC0003a2.m27a8(e, new StringBuilder("encoding error in getInstance: ")));
        }
    }

    public boolean hasValue(long j) {
        byte[] bArr = this.bytes;
        int length = bArr.length;
        int i = this.start;
        return length - i <= 8 && longValue(bArr, i, -1) == j;
    }

    public C0155c0(byte[] bArr) {
        this(bArr, true);
    }

    public boolean hasValue(BigInteger bigInteger) {
        return bigInteger != null && intValue(this.bytes, this.start, -1) == bigInteger.intValue() && getValue().equals(bigInteger);
    }

    public C0155c0(byte[] bArr, boolean z) {
        if (isMalformed(bArr)) {
            throw new IllegalArgumentException("malformed integer");
        }
        this.bytes = z ? C0133bg.clone(bArr) : bArr;
        this.start = signBytesToSkip(bArr);
    }
}
