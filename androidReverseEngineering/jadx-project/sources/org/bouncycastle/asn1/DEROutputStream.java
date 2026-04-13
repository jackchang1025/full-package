package org.bouncycastle.asn1;

import java.io.OutputStream;

/* loaded from: classes.dex */
class DEROutputStream extends DLOutputStream {
    public DEROutputStream(OutputStream outputStream) {
        super(outputStream);
    }

    @Override // org.bouncycastle.asn1.ASN1OutputStream
    public DEROutputStream getDERSubStream() {
        return this;
    }

    @Override // org.bouncycastle.asn1.DLOutputStream, org.bouncycastle.asn1.ASN1OutputStream
    public void writeElements(ASN1Encodable[] aSN1EncodableArr) {
        for (ASN1Encodable aSN1Encodable : aSN1EncodableArr) {
            aSN1Encodable.toASN1Primitive().toDERObject().encode(this, true);
        }
    }

    @Override // org.bouncycastle.asn1.DLOutputStream, org.bouncycastle.asn1.ASN1OutputStream
    public void writePrimitive(ASN1Primitive aSN1Primitive, boolean z2) {
        aSN1Primitive.toDERObject().encode(this, z2);
    }

    @Override // org.bouncycastle.asn1.DLOutputStream, org.bouncycastle.asn1.ASN1OutputStream
    public void writePrimitives(ASN1Primitive[] aSN1PrimitiveArr) {
        for (ASN1Primitive aSN1Primitive : aSN1PrimitiveArr) {
            aSN1Primitive.toDERObject().encode(this, true);
        }
    }
}
