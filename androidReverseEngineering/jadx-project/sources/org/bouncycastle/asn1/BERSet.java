package org.bouncycastle.asn1;

/* loaded from: classes.dex */
public class BERSet extends ASN1Set {
    public BERSet() {
    }

    public BERSet(ASN1Encodable aSN1Encodable) {
        super(aSN1Encodable);
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public void encode(ASN1OutputStream aSN1OutputStream, boolean z2) {
        aSN1OutputStream.writeEncodingIL(z2, 49, this.elements);
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public int encodedLength(boolean z2) {
        int i2 = z2 ? 4 : 3;
        int length = this.elements.length;
        for (int i3 = 0; i3 < length; i3++) {
            i2 += this.elements[i3].toASN1Primitive().encodedLength(true);
        }
        return i2;
    }

    public BERSet(ASN1EncodableVector aSN1EncodableVector) {
        super(aSN1EncodableVector, false);
    }

    public BERSet(boolean z2, ASN1Encodable[] aSN1EncodableArr) {
        super(z2, aSN1EncodableArr);
    }

    public BERSet(ASN1Encodable[] aSN1EncodableArr) {
        super(aSN1EncodableArr, false);
    }
}
