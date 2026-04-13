package org.bouncycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

/* loaded from: classes.dex */
public class DERSequenceGenerator extends DERGenerator {
    private final ByteArrayOutputStream _bOut;

    public DERSequenceGenerator(OutputStream outputStream) {
        super(outputStream);
        this._bOut = new ByteArrayOutputStream();
    }

    public void addObject(ASN1Encodable aSN1Encodable) {
        aSN1Encodable.toASN1Primitive().encodeTo(this._bOut, ASN1Encoding.DER);
    }

    public void close() {
        writeDEREncoded(48, this._bOut.toByteArray());
    }

    @Override // org.bouncycastle.asn1.ASN1Generator
    public OutputStream getRawOutputStream() {
        return this._bOut;
    }

    public DERSequenceGenerator(OutputStream outputStream, int i2, boolean z2) {
        super(outputStream, i2, z2);
        this._bOut = new ByteArrayOutputStream();
    }

    public void addObject(ASN1Primitive aSN1Primitive) {
        aSN1Primitive.encodeTo(this._bOut, ASN1Encoding.DER);
    }
}
