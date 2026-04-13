package org.bouncycastle.asn1;

/* loaded from: classes.dex */
public class DEROctetString extends ASN1OctetString {
    public DEROctetString(ASN1Encodable aSN1Encodable) {
        super(aSN1Encodable.toASN1Primitive().getEncoded(ASN1Encoding.DER));
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public void encode(ASN1OutputStream aSN1OutputStream, boolean z2) {
        aSN1OutputStream.writeEncodingDL(z2, 4, this.string);
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public boolean encodeConstructed() {
        return false;
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public int encodedLength(boolean z2) {
        return ASN1OutputStream.getLengthOfEncodingDL(z2, this.string.length);
    }

    @Override // org.bouncycastle.asn1.ASN1OctetString, org.bouncycastle.asn1.ASN1Primitive
    public ASN1Primitive toDERObject() {
        return this;
    }

    @Override // org.bouncycastle.asn1.ASN1OctetString, org.bouncycastle.asn1.ASN1Primitive
    public ASN1Primitive toDLObject() {
        return this;
    }

    public DEROctetString(byte[] bArr) {
        super(bArr);
    }

    public static void encode(ASN1OutputStream aSN1OutputStream, boolean z2, byte[] bArr, int i2, int i3) {
        aSN1OutputStream.writeEncodingDL(z2, 4, bArr, i2, i3);
    }

    public static int encodedLength(boolean z2, int i2) {
        return ASN1OutputStream.getLengthOfEncodingDL(z2, i2);
    }
}
