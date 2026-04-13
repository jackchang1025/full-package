package org.bouncycastle.asn1;

/* loaded from: classes.dex */
public class DERSequence extends ASN1Sequence {
    private int contentsLength;

    public DERSequence() {
        this.contentsLength = -1;
    }

    public static DERSequence convert(ASN1Sequence aSN1Sequence) {
        return (DERSequence) aSN1Sequence.toDERObject();
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
        aSN1OutputStream.writeIdentifier(z2, 48);
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

    @Override // org.bouncycastle.asn1.ASN1Sequence
    public ASN1BitString toASN1BitString() {
        return new DERBitString(BERBitString.flattenBitStrings(getConstructedBitStrings()), false);
    }

    @Override // org.bouncycastle.asn1.ASN1Sequence
    public ASN1External toASN1External() {
        return new DERExternal(this);
    }

    @Override // org.bouncycastle.asn1.ASN1Sequence
    public ASN1OctetString toASN1OctetString() {
        return new DEROctetString(BEROctetString.flattenOctetStrings(getConstructedOctetStrings()));
    }

    @Override // org.bouncycastle.asn1.ASN1Sequence
    public ASN1Set toASN1Set() {
        return new DLSet(false, toArrayInternal());
    }

    @Override // org.bouncycastle.asn1.ASN1Sequence, org.bouncycastle.asn1.ASN1Primitive
    public ASN1Primitive toDERObject() {
        return this;
    }

    @Override // org.bouncycastle.asn1.ASN1Sequence, org.bouncycastle.asn1.ASN1Primitive
    public ASN1Primitive toDLObject() {
        return this;
    }

    public DERSequence(ASN1Encodable aSN1Encodable) {
        super(aSN1Encodable);
        this.contentsLength = -1;
    }

    public DERSequence(ASN1EncodableVector aSN1EncodableVector) {
        super(aSN1EncodableVector);
        this.contentsLength = -1;
    }

    public DERSequence(ASN1Encodable[] aSN1EncodableArr) {
        super(aSN1EncodableArr);
        this.contentsLength = -1;
    }

    public DERSequence(ASN1Encodable[] aSN1EncodableArr, boolean z2) {
        super(aSN1EncodableArr, z2);
        this.contentsLength = -1;
    }
}
