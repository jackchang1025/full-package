package org.bouncycastle.asn1;

/* loaded from: classes.dex */
public class DLApplicationSpecific extends ASN1ApplicationSpecific {
    public DLApplicationSpecific(int i2, ASN1Encodable aSN1Encodable) {
        this(true, i2, aSN1Encodable);
    }

    @Override // org.bouncycastle.asn1.ASN1ApplicationSpecific, org.bouncycastle.asn1.ASN1Primitive
    public ASN1Primitive toDLObject() {
        return this;
    }

    public DLApplicationSpecific(int i2, ASN1EncodableVector aSN1EncodableVector) {
        super(new DLTaggedObject(false, 64, i2, (ASN1Encodable) DLFactory.createSequence(aSN1EncodableVector)));
    }

    public DLApplicationSpecific(int i2, byte[] bArr) {
        super(new DLTaggedObject(false, 64, i2, (ASN1Encodable) new DEROctetString(bArr)));
    }

    public DLApplicationSpecific(ASN1TaggedObject aSN1TaggedObject) {
        super(aSN1TaggedObject);
    }

    public DLApplicationSpecific(boolean z2, int i2, ASN1Encodable aSN1Encodable) {
        super(new DLTaggedObject(z2, 64, i2, aSN1Encodable));
    }
}
