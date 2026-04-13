package org.bouncycastle.asn1;

/* loaded from: classes.dex */
public class BERTaggedObject extends ASN1TaggedObject {
    public BERTaggedObject(int i2) {
        super(false, i2, new BERSequence());
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public void encode(ASN1OutputStream aSN1OutputStream, boolean z2) {
        ASN1Primitive aSN1Primitive = this.obj.toASN1Primitive();
        boolean isExplicit = isExplicit();
        if (z2) {
            int i2 = this.tagClass;
            if (isExplicit || aSN1Primitive.encodeConstructed()) {
                i2 |= 32;
            }
            aSN1OutputStream.writeIdentifier(true, i2, this.tagNo);
        }
        if (!isExplicit) {
            aSN1Primitive.encode(aSN1OutputStream, false);
            return;
        }
        aSN1OutputStream.write(128);
        aSN1Primitive.encode(aSN1OutputStream, true);
        aSN1OutputStream.write(0);
        aSN1OutputStream.write(0);
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public boolean encodeConstructed() {
        return isExplicit() || this.obj.toASN1Primitive().encodeConstructed();
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public int encodedLength(boolean z2) {
        ASN1Primitive aSN1Primitive = this.obj.toASN1Primitive();
        boolean isExplicit = isExplicit();
        int encodedLength = aSN1Primitive.encodedLength(isExplicit);
        if (isExplicit) {
            encodedLength += 3;
        }
        return encodedLength + (z2 ? ASN1OutputStream.getLengthOfIdentifier(this.tagNo) : 0);
    }

    @Override // org.bouncycastle.asn1.ASN1TaggedObject
    public String getASN1Encoding() {
        return ASN1Encoding.BER;
    }

    @Override // org.bouncycastle.asn1.ASN1TaggedObject
    public ASN1Sequence rebuildConstructed(ASN1Primitive aSN1Primitive) {
        return new BERSequence(aSN1Primitive);
    }

    @Override // org.bouncycastle.asn1.ASN1TaggedObject
    public ASN1TaggedObject replaceTag(int i2, int i3) {
        return new BERTaggedObject(this.explicitness, i2, i3, this.obj);
    }

    public BERTaggedObject(int i2, int i3, int i4, ASN1Encodable aSN1Encodable) {
        super(i2, i3, i4, aSN1Encodable);
    }

    public BERTaggedObject(int i2, int i3, ASN1Encodable aSN1Encodable) {
        super(true, i2, i3, aSN1Encodable);
    }

    public BERTaggedObject(int i2, ASN1Encodable aSN1Encodable) {
        super(true, i2, aSN1Encodable);
    }

    public BERTaggedObject(boolean z2, int i2, int i3, ASN1Encodable aSN1Encodable) {
        super(z2, i2, i3, aSN1Encodable);
    }

    public BERTaggedObject(boolean z2, int i2, ASN1Encodable aSN1Encodable) {
        super(z2, i2, aSN1Encodable);
    }
}
