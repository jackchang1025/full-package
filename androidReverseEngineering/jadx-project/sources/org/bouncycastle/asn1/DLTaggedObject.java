package org.bouncycastle.asn1;

/* loaded from: classes.dex */
public class DLTaggedObject extends ASN1TaggedObject {
    public DLTaggedObject(int i2, int i3, int i4, ASN1Encodable aSN1Encodable) {
        super(i2, i3, i4, aSN1Encodable);
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public void encode(ASN1OutputStream aSN1OutputStream, boolean z2) {
        ASN1Primitive dLObject = this.obj.toASN1Primitive().toDLObject();
        boolean isExplicit = isExplicit();
        if (z2) {
            int i2 = this.tagClass;
            if (isExplicit || dLObject.encodeConstructed()) {
                i2 |= 32;
            }
            aSN1OutputStream.writeIdentifier(true, i2, this.tagNo);
        }
        if (isExplicit) {
            aSN1OutputStream.writeDL(dLObject.encodedLength(true));
        }
        dLObject.encode(aSN1OutputStream.getDLSubStream(), isExplicit);
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public boolean encodeConstructed() {
        return isExplicit() || this.obj.toASN1Primitive().toDLObject().encodeConstructed();
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public int encodedLength(boolean z2) {
        ASN1Primitive dLObject = this.obj.toASN1Primitive().toDLObject();
        boolean isExplicit = isExplicit();
        int encodedLength = dLObject.encodedLength(isExplicit);
        if (isExplicit) {
            encodedLength += ASN1OutputStream.getLengthOfDL(encodedLength);
        }
        return encodedLength + (z2 ? ASN1OutputStream.getLengthOfIdentifier(this.tagNo) : 0);
    }

    @Override // org.bouncycastle.asn1.ASN1TaggedObject
    public String getASN1Encoding() {
        return ASN1Encoding.DL;
    }

    @Override // org.bouncycastle.asn1.ASN1TaggedObject
    public ASN1Sequence rebuildConstructed(ASN1Primitive aSN1Primitive) {
        return new DLSequence(aSN1Primitive);
    }

    @Override // org.bouncycastle.asn1.ASN1TaggedObject
    public ASN1TaggedObject replaceTag(int i2, int i3) {
        return new DLTaggedObject(this.explicitness, i2, i3, this.obj);
    }

    @Override // org.bouncycastle.asn1.ASN1TaggedObject, org.bouncycastle.asn1.ASN1Primitive
    public ASN1Primitive toDLObject() {
        return this;
    }

    public DLTaggedObject(int i2, int i3, ASN1Encodable aSN1Encodable) {
        super(true, i2, i3, aSN1Encodable);
    }

    public DLTaggedObject(int i2, ASN1Encodable aSN1Encodable) {
        super(true, i2, aSN1Encodable);
    }

    public DLTaggedObject(boolean z2, int i2, int i3, ASN1Encodable aSN1Encodable) {
        super(z2, i2, i3, aSN1Encodable);
    }

    public DLTaggedObject(boolean z2, int i2, ASN1Encodable aSN1Encodable) {
        super(z2, i2, aSN1Encodable);
    }
}
