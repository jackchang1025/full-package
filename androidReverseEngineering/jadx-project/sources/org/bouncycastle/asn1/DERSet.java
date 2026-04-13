package org.bouncycastle.asn1;

/* loaded from: classes.dex */
public class DERSet extends ASN1Set {
    private int contentsLength;

    public DERSet() {
        this.contentsLength = -1;
    }

    private static boolean checkSorted(boolean z2) {
        if (z2) {
            return z2;
        }
        throw new IllegalStateException("DERSet elements should always be in sorted order");
    }

    public static DERSet convert(ASN1Set aSN1Set) {
        return (DERSet) aSN1Set.toDERObject();
    }

    private int getContentsLength() {
        if (this.contentsLength < 0) {
            int length = this.elements.length;
            int i2 = 0;
            for (int i3 = 0; i3 < length; i3++) {
                i2 += this.elements[i3].toASN1Primitive().toDERObject().encodedLength(true);
            }
            this.contentsLength = i2;
        }
        return this.contentsLength;
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public void encode(ASN1OutputStream aSN1OutputStream, boolean z2) {
        aSN1OutputStream.writeIdentifier(z2, 49);
        DEROutputStream dERSubStream = aSN1OutputStream.getDERSubStream();
        int length = this.elements.length;
        int i2 = 0;
        if (this.contentsLength >= 0 || length > 16) {
            aSN1OutputStream.writeDL(getContentsLength());
            while (i2 < length) {
                this.elements[i2].toASN1Primitive().toDERObject().encode(dERSubStream, true);
                i2++;
            }
            return;
        }
        ASN1Primitive[] aSN1PrimitiveArr = new ASN1Primitive[length];
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4++) {
            ASN1Primitive dERObject = this.elements[i4].toASN1Primitive().toDERObject();
            aSN1PrimitiveArr[i4] = dERObject;
            i3 += dERObject.encodedLength(true);
        }
        this.contentsLength = i3;
        aSN1OutputStream.writeDL(i3);
        while (i2 < length) {
            aSN1PrimitiveArr[i2].encode(dERSubStream, true);
            i2++;
        }
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public int encodedLength(boolean z2) {
        return ASN1OutputStream.getLengthOfEncodingDL(z2, getContentsLength());
    }

    @Override // org.bouncycastle.asn1.ASN1Set, org.bouncycastle.asn1.ASN1Primitive
    public ASN1Primitive toDERObject() {
        return this.isSorted ? this : super.toDERObject();
    }

    @Override // org.bouncycastle.asn1.ASN1Set, org.bouncycastle.asn1.ASN1Primitive
    public ASN1Primitive toDLObject() {
        return this;
    }

    public DERSet(ASN1Encodable aSN1Encodable) {
        super(aSN1Encodable);
        this.contentsLength = -1;
    }

    public DERSet(ASN1EncodableVector aSN1EncodableVector) {
        super(aSN1EncodableVector, true);
        this.contentsLength = -1;
    }

    public DERSet(boolean z2, ASN1Encodable[] aSN1EncodableArr) {
        super(checkSorted(z2), aSN1EncodableArr);
        this.contentsLength = -1;
    }

    public DERSet(ASN1Encodable[] aSN1EncodableArr) {
        super(aSN1EncodableArr, true);
        this.contentsLength = -1;
    }
}
