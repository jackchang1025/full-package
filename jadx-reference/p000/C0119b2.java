package p000;

import java.io.IOException;
import java.math.BigInteger;

/* renamed from: b2 */
/* loaded from: classes2.dex */
public class C0119b2 extends AbstractC0164c9 {
    static final AbstractC0445e6 TYPE = new a0(C0119b2.class, 10);
    private static final C0119b2[] cache = new C0119b2[12];
    private final byte[] contents;
    private final int start;

    /* renamed from: b2$a0 */
    public static class a0 extends AbstractC0445e6 {
        public a0(Class cls, int i) {
            super(cls, i);
        }

        @Override // p000.AbstractC0445e6
        public AbstractC0164c9 fromImplicitPrimitive(C1048oy c1048oy) {
            return C0119b2.createPrimitive(c1048oy.getOctets(), false);
        }
    }

    public C0119b2(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("enumerated must be non-negative");
        }
        this.contents = BigInteger.valueOf(i).toByteArray();
        this.start = 0;
    }

    public static C0119b2 createPrimitive(byte[] bArr, boolean z) {
        if (bArr.length > 1) {
            return new C0119b2(bArr, z);
        }
        if (bArr.length == 0) {
            throw new IllegalArgumentException("ENUMERATED has zero length");
        }
        int i = bArr[0] & 255;
        C0119b2[] c0119b2Arr = cache;
        if (i >= c0119b2Arr.length) {
            return new C0119b2(bArr, z);
        }
        C0119b2 c0119b2 = c0119b2Arr[i];
        if (c0119b2 != null) {
            return c0119b2;
        }
        C0119b2 c0119b22 = new C0119b2(bArr, z);
        c0119b2Arr[i] = c0119b22;
        return c0119b22;
    }

    public static C0119b2 getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        return (C0119b2) TYPE.getContextInstance(abstractC0439e0, z);
    }

    @Override // p000.AbstractC0164c9
    public boolean asn1Equals(AbstractC0164c9 abstractC0164c9) {
        if (abstractC0164c9 instanceof C0119b2) {
            return C0133bg.areEqual(this.contents, ((C0119b2) abstractC0164c9).contents);
        }
        return false;
    }

    @Override // p000.AbstractC0164c9
    public void encode(C0163c8 c0163c8, boolean z) throws IOException {
        c0163c8.writeEncodingDL(z, 10, this.contents);
    }

    @Override // p000.AbstractC0164c9
    public boolean encodeConstructed() {
        return false;
    }

    @Override // p000.AbstractC0164c9
    public int encodedLength(boolean z) {
        return C0163c8.getLengthOfEncodingDL(z, this.contents.length);
    }

    public BigInteger getValue() {
        return new BigInteger(this.contents);
    }

    public boolean hasValue(int i) {
        byte[] bArr = this.contents;
        int length = bArr.length;
        int i2 = this.start;
        return length - i2 <= 4 && C0155c0.intValue(bArr, i2, -1) == i;
    }

    @Override // p000.AbstractC0164c9, p000.AbstractC0158c3
    public int hashCode() {
        return C0133bg.hashCode(this.contents);
    }

    public int intValueExact() {
        byte[] bArr = this.contents;
        int length = bArr.length;
        int i = this.start;
        if (length - i <= 4) {
            return C0155c0.intValue(bArr, i, -1);
        }
        throw new ArithmeticException("ASN.1 Enumerated out of int range");
    }

    public C0119b2(BigInteger bigInteger) {
        if (bigInteger.signum() < 0) {
            throw new IllegalArgumentException("enumerated must be non-negative");
        }
        this.contents = bigInteger.toByteArray();
        this.start = 0;
    }

    public static C0119b2 getInstance(Object obj) {
        if (obj == null || (obj instanceof C0119b2)) {
            return (C0119b2) obj;
        }
        if (!(obj instanceof byte[])) {
            throw new IllegalArgumentException(AbstractC0003a2.m28a9(obj, "illegal object in getInstance: "));
        }
        try {
            return (C0119b2) TYPE.fromByteArray((byte[]) obj);
        } catch (Exception e) {
            throw new IllegalArgumentException(AbstractC0003a2.m27a8(e, new StringBuilder("encoding error in getInstance: ")));
        }
    }

    public boolean hasValue(BigInteger bigInteger) {
        return bigInteger != null && C0155c0.intValue(this.contents, this.start, -1) == bigInteger.intValue() && getValue().equals(bigInteger);
    }

    public C0119b2(byte[] bArr) {
        this(bArr, true);
    }

    public C0119b2(byte[] bArr, boolean z) {
        if (C0155c0.isMalformed(bArr)) {
            throw new IllegalArgumentException("malformed enumerated");
        }
        if ((bArr[0] & 128) != 0) {
            throw new IllegalArgumentException("enumerated must be non-negative");
        }
        this.contents = z ? C0133bg.clone(bArr) : bArr;
        this.start = C0155c0.signBytesToSkip(bArr);
    }
}
