package org.bouncycastle.asn1;

/* loaded from: classes.dex */
abstract class ASN1UniversalType extends ASN1Type {
    final ASN1Tag tag;

    public ASN1UniversalType(Class cls, int i2) {
        super(cls);
        this.tag = ASN1Tag.create(0, i2);
    }

    public final ASN1Primitive checkedCast(ASN1Primitive aSN1Primitive) {
        if (this.javaClass.isInstance(aSN1Primitive)) {
            return aSN1Primitive;
        }
        throw new IllegalStateException("unexpected object: ".concat(aSN1Primitive.getClass().getName()));
    }

    public final ASN1Primitive fromByteArray(byte[] bArr) {
        return checkedCast(ASN1Primitive.fromByteArray(bArr));
    }

    public ASN1Primitive fromImplicitConstructed(ASN1Sequence aSN1Sequence) {
        throw new IllegalStateException("unexpected implicit constructed encoding");
    }

    public ASN1Primitive fromImplicitPrimitive(DEROctetString dEROctetString) {
        throw new IllegalStateException("unexpected implicit primitive encoding");
    }

    public final ASN1Primitive getContextInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        if (128 == aSN1TaggedObject.getTagClass()) {
            return checkedCast(aSN1TaggedObject.getBaseUniversal(z2, this));
        }
        throw new IllegalStateException("this method only valid for CONTEXT_SPECIFIC tags");
    }

    public final ASN1Tag getTag() {
        return this.tag;
    }
}
