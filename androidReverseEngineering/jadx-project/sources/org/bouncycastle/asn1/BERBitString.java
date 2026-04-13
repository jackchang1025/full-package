package org.bouncycastle.asn1;

/* loaded from: classes.dex */
public class BERBitString extends ASN1BitString {
    private static final int DEFAULT_SEGMENT_LIMIT = 1000;
    private final ASN1BitString[] elements;
    private final int segmentLimit;

    public BERBitString(byte b, int i2) {
        super(b, i2);
        this.elements = null;
        this.segmentLimit = 1000;
    }

    public static byte[] flattenBitStrings(ASN1BitString[] aSN1BitStringArr) {
        int length = aSN1BitStringArr.length;
        if (length == 0) {
            return new byte[]{0};
        }
        if (length == 1) {
            return aSN1BitStringArr[0].contents;
        }
        int i2 = length - 1;
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            byte[] bArr = aSN1BitStringArr[i4].contents;
            if (bArr[0] != 0) {
                throw new IllegalArgumentException("only the last nested bitstring can have padding");
            }
            i3 += bArr.length - 1;
        }
        byte[] bArr2 = aSN1BitStringArr[i2].contents;
        byte b = bArr2[0];
        byte[] bArr3 = new byte[i3 + bArr2.length];
        bArr3[0] = b;
        int i5 = 1;
        for (ASN1BitString aSN1BitString : aSN1BitStringArr) {
            byte[] bArr4 = aSN1BitString.contents;
            int length2 = bArr4.length - 1;
            System.arraycopy(bArr4, 1, bArr3, i5, length2);
            i5 += length2;
        }
        return bArr3;
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public void encode(ASN1OutputStream aSN1OutputStream, boolean z2) {
        if (!encodeConstructed()) {
            byte[] bArr = this.contents;
            DLBitString.encode(aSN1OutputStream, z2, bArr, 0, bArr.length);
            return;
        }
        aSN1OutputStream.writeIdentifier(z2, 35);
        aSN1OutputStream.write(128);
        ASN1BitString[] aSN1BitStringArr = this.elements;
        if (aSN1BitStringArr != null) {
            aSN1OutputStream.writePrimitives(aSN1BitStringArr);
        } else {
            byte[] bArr2 = this.contents;
            if (bArr2.length >= 2) {
                byte b = bArr2[0];
                int length = bArr2.length;
                int i2 = length - 1;
                int i3 = this.segmentLimit - 1;
                while (i2 > i3) {
                    DLBitString.encode(aSN1OutputStream, true, (byte) 0, this.contents, length - i2, i3);
                    i2 -= i3;
                }
                DLBitString.encode(aSN1OutputStream, true, b, this.contents, length - i2, i2);
            }
        }
        aSN1OutputStream.write(0);
        aSN1OutputStream.write(0);
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public boolean encodeConstructed() {
        return this.elements != null || this.contents.length > this.segmentLimit;
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public int encodedLength(boolean z2) {
        if (!encodeConstructed()) {
            return DLBitString.encodedLength(z2, this.contents.length);
        }
        int i2 = z2 ? 4 : 3;
        if (this.elements == null) {
            byte[] bArr = this.contents;
            if (bArr.length < 2) {
                return i2;
            }
            int length = bArr.length - 2;
            int i3 = this.segmentLimit;
            int i4 = length / (i3 - 1);
            return DLBitString.encodedLength(true, this.contents.length - ((this.segmentLimit - 1) * i4)) + (DLBitString.encodedLength(true, i3) * i4) + i2;
        }
        int i5 = 0;
        while (true) {
            ASN1BitString[] aSN1BitStringArr = this.elements;
            if (i5 >= aSN1BitStringArr.length) {
                return i2;
            }
            i2 += aSN1BitStringArr[i5].encodedLength(true);
            i5++;
        }
    }

    public BERBitString(ASN1Encodable aSN1Encodable) {
        this(aSN1Encodable.toASN1Primitive().getEncoded(ASN1Encoding.DER), 0);
    }

    public BERBitString(byte[] bArr) {
        this(bArr, 0);
    }

    public BERBitString(byte[] bArr, int i2) {
        this(bArr, i2, 1000);
    }

    public BERBitString(byte[] bArr, int i2, int i3) {
        super(bArr, i2);
        this.elements = null;
        this.segmentLimit = i3;
    }

    public BERBitString(byte[] bArr, boolean z2) {
        super(bArr, z2);
        this.elements = null;
        this.segmentLimit = 1000;
    }

    public BERBitString(ASN1BitString[] aSN1BitStringArr) {
        this(aSN1BitStringArr, 1000);
    }

    public BERBitString(ASN1BitString[] aSN1BitStringArr, int i2) {
        super(flattenBitStrings(aSN1BitStringArr), false);
        this.elements = aSN1BitStringArr;
        this.segmentLimit = i2;
    }
}
