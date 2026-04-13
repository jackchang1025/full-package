package org.bouncycastle.asn1;

import java.io.OutputStream;

/* loaded from: classes.dex */
public class BERSequenceGenerator extends BERGenerator {
    public BERSequenceGenerator(OutputStream outputStream) {
        super(outputStream);
        writeBERHeader(48);
    }

    public void addObject(ASN1Encodable aSN1Encodable) {
        aSN1Encodable.toASN1Primitive().encodeTo(this._out);
    }

    public void close() {
        writeBEREnd();
    }

    public BERSequenceGenerator(OutputStream outputStream, int i2, boolean z2) {
        super(outputStream, i2, z2);
        writeBERHeader(48);
    }

    public void addObject(ASN1Primitive aSN1Primitive) {
        aSN1Primitive.encodeTo(this._out);
    }
}
