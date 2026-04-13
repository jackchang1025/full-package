package org.bouncycastle.asn1;

/* loaded from: classes.dex */
public class DLSet extends ASN1Set {
    private int contentsLength;

    public DLSet() {
        this.contentsLength = -1;
    }

    private int getContentsLength() {
        if (this.contentsLength < 0) {
            int length = this.elements.length;
            int i2 = 0;
            for (int i3 = 0; i3 < length; i3++) {
                i2 += this.elements[i3].toASN1Primitive().toDLObject().encodedLength(true);
            }
            this.contentsLength = i2;
        }
        return this.contentsLength;
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public void encode(ASN1OutputStream aSN1OutputStream, boolean z2) {
        aSN1OutputStream.writeIdentifier(z2, 49);
        DLOutputStream dLSubStream = aSN1OutputStream.getDLSubStream();
        int length = this.elements.length;
        int i2 = 0;
        if (this.contentsLength >= 0 || length > 16) {
            aSN1OutputStream.writeDL(getContentsLength());
            while (i2 < length) {
                dLSubStream.writePrimitive(this.elements[i2].toASN1Primitive(), true);
                i2++;
            }
            return;
        }
        ASN1Primitive[] aSN1PrimitiveArr = new ASN1Primitive[length];
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4++) {
            ASN1Primitive dLObject = this.elements[i4].toASN1Primitive().toDLObject();
            aSN1PrimitiveArr[i4] = dLObject;
            i3 += dLObject.encodedLength(true);
        }
        this.contentsLength = i3;
        aSN1OutputStream.writeDL(i3);
        while (i2 < length) {
            dLSubStream.writePrimitive(aSN1PrimitiveArr[i2], true);
            i2++;
        }
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public int encodedLength(boolean z2) {
        return ASN1OutputStream.getLengthOfEncodingDL(z2, getContentsLength());
    }

    @Override // org.bouncycastle.asn1.ASN1Set, org.bouncycastle.asn1.ASN1Primitive
    public ASN1Primitive toDLObject() {
        return this;
    }

    public DLSet(ASN1Encodable aSN1Encodable) {
        super(aSN1Encodable);
        this.contentsLength = -1;
    }

    public DLSet(ASN1EncodableVector aSN1EncodableVector) {
        super(aSN1EncodableVector, false);
        this.contentsLength = -1;
    }

    public DLSet(boolean z2, ASN1Encodable[] aSN1EncodableArr) {
        super(z2, aSN1EncodableArr);
        this.contentsLength = -1;
    }

    public DLSet(ASN1Encodable[] aSN1EncodableArr) {
        super(aSN1EncodableArr, false);
        this.contentsLength = -1;
    }
}
