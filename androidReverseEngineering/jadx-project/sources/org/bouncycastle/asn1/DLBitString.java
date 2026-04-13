package org.bouncycastle.asn1;

/* loaded from: classes.dex */
public class DLBitString extends ASN1BitString {
    public DLBitString(byte b, int i2) {
        super(b, i2);
    }

    public static DLBitString fromOctetString(ASN1OctetString aSN1OctetString) {
        return new DLBitString(aSN1OctetString.getOctets(), true);
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public void encode(ASN1OutputStream aSN1OutputStream, boolean z2) {
        aSN1OutputStream.writeEncodingDL(z2, 3, this.contents);
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public boolean encodeConstructed() {
        return false;
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public int encodedLength(boolean z2) {
        return ASN1OutputStream.getLengthOfEncodingDL(z2, this.contents.length);
    }

    @Override // org.bouncycastle.asn1.ASN1BitString, org.bouncycastle.asn1.ASN1Primitive
    public ASN1Primitive toDLObject() {
        return this;
    }

    public DLBitString(int i2) {
        super(ASN1BitString.getBytes(i2), ASN1BitString.getPadBits(i2));
    }

    public static void encode(ASN1OutputStream aSN1OutputStream, boolean z2, byte b, byte[] bArr, int i2, int i3) {
        aSN1OutputStream.writeEncodingDL(z2, 3, b, bArr, i2, i3);
    }

    public static int encodedLength(boolean z2, int i2) {
        return ASN1OutputStream.getLengthOfEncodingDL(z2, i2);
    }

    public DLBitString(ASN1Encodable aSN1Encodable) {
        super(aSN1Encodable.toASN1Primitive().getEncoded(ASN1Encoding.DER), 0);
    }

    public static void encode(ASN1OutputStream aSN1OutputStream, boolean z2, byte[] bArr, int i2, int i3) {
        aSN1OutputStream.writeEncodingDL(z2, 3, bArr, i2, i3);
    }

    public DLBitString(byte[] bArr) {
        this(bArr, 0);
    }

    public DLBitString(byte[] bArr, int i2) {
        super(bArr, i2);
    }

    public DLBitString(byte[] bArr, boolean z2) {
        super(bArr, z2);
    }
}
