package org.bouncycastle.asn1;

/* loaded from: classes.dex */
public class DERApplicationSpecific extends ASN1ApplicationSpecific {
    public DERApplicationSpecific(int i2, ASN1Encodable aSN1Encodable) {
        this(true, i2, aSN1Encodable);
    }

    @Override // org.bouncycastle.asn1.ASN1ApplicationSpecific, org.bouncycastle.asn1.ASN1Primitive
    public ASN1Primitive toDERObject() {
        return this;
    }

    @Override // org.bouncycastle.asn1.ASN1ApplicationSpecific, org.bouncycastle.asn1.ASN1Primitive
    public ASN1Primitive toDLObject() {
        return this;
    }

    public DERApplicationSpecific(int i2, ASN1EncodableVector aSN1EncodableVector) {
        super(new DERTaggedObject(false, 64, i2, (ASN1Encodable) DERFactory.createSequence(aSN1EncodableVector)));
    }

    public DERApplicationSpecific(int i2, byte[] bArr) {
        super(new DERTaggedObject(false, 64, i2, (ASN1Encodable) new DEROctetString(bArr)));
    }

    public DERApplicationSpecific(ASN1TaggedObject aSN1TaggedObject) {
        super(aSN1TaggedObject);
    }

    public DERApplicationSpecific(boolean z2, int i2, ASN1Encodable aSN1Encodable) {
        super(new DERTaggedObject(z2, 64, i2, aSN1Encodable));
    }
}
