package org.bouncycastle.asn1;

/* loaded from: classes.dex */
public class DERTaggedObject extends ASN1TaggedObject {
    public DERTaggedObject(int i2, int i3, int i4, ASN1Encodable aSN1Encodable) {
        super(i2, i3, i4, aSN1Encodable);
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public void encode(ASN1OutputStream aSN1OutputStream, boolean z2) {
        ASN1Primitive dERObject = this.obj.toASN1Primitive().toDERObject();
        boolean isExplicit = isExplicit();
        if (z2) {
            int i2 = this.tagClass;
            if (isExplicit || dERObject.encodeConstructed()) {
                i2 |= 32;
            }
            aSN1OutputStream.writeIdentifier(true, i2, this.tagNo);
        }
        if (isExplicit) {
            aSN1OutputStream.writeDL(dERObject.encodedLength(true));
        }
        dERObject.encode(aSN1OutputStream.getDERSubStream(), isExplicit);
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public boolean encodeConstructed() {
        return isExplicit() || this.obj.toASN1Primitive().toDERObject().encodeConstructed();
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public int encodedLength(boolean z2) {
        ASN1Primitive dERObject = this.obj.toASN1Primitive().toDERObject();
        boolean isExplicit = isExplicit();
        int encodedLength = dERObject.encodedLength(isExplicit);
        if (isExplicit) {
            encodedLength += ASN1OutputStream.getLengthOfDL(encodedLength);
        }
        return encodedLength + (z2 ? ASN1OutputStream.getLengthOfIdentifier(this.tagNo) : 0);
    }

    @Override // org.bouncycastle.asn1.ASN1TaggedObject
    public String getASN1Encoding() {
        return ASN1Encoding.DER;
    }

    @Override // org.bouncycastle.asn1.ASN1TaggedObject
    public ASN1Sequence rebuildConstructed(ASN1Primitive aSN1Primitive) {
        return new DERSequence(aSN1Primitive);
    }

    @Override // org.bouncycastle.asn1.ASN1TaggedObject
    public ASN1TaggedObject replaceTag(int i2, int i3) {
        return new DERTaggedObject(this.explicitness, i2, i3, this.obj);
    }

    @Override // org.bouncycastle.asn1.ASN1TaggedObject, org.bouncycastle.asn1.ASN1Primitive
    public ASN1Primitive toDERObject() {
        return this;
    }

    @Override // org.bouncycastle.asn1.ASN1TaggedObject, org.bouncycastle.asn1.ASN1Primitive
    public ASN1Primitive toDLObject() {
        return this;
    }

    public DERTaggedObject(int i2, int i3, ASN1Encodable aSN1Encodable) {
        super(true, i2, i3, aSN1Encodable);
    }

    public DERTaggedObject(int i2, ASN1Encodable aSN1Encodable) {
        super(true, i2, aSN1Encodable);
    }

    public DERTaggedObject(boolean z2, int i2, int i3, ASN1Encodable aSN1Encodable) {
        super(z2, i2, i3, aSN1Encodable);
    }

    public DERTaggedObject(boolean z2, int i2, ASN1Encodable aSN1Encodable) {
        super(z2, i2, aSN1Encodable);
    }
}
