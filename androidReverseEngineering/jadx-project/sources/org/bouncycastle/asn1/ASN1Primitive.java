package org.bouncycastle.asn1;

import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes.dex */
public abstract class ASN1Primitive extends ASN1Object {
    public static ASN1Primitive fromByteArray(byte[] bArr) {
        ASN1InputStream aSN1InputStream = new ASN1InputStream(bArr);
        try {
            ASN1Primitive readObject = aSN1InputStream.readObject();
            if (aSN1InputStream.available() == 0) {
                return readObject;
            }
            throw new IOException("Extra data detected in stream");
        } catch (ClassCastException unused) {
            throw new IOException("cannot recognise object in stream");
        }
    }

    public abstract boolean asn1Equals(ASN1Primitive aSN1Primitive);

    public abstract void encode(ASN1OutputStream aSN1OutputStream, boolean z2);

    public abstract boolean encodeConstructed();

    @Override // org.bouncycastle.asn1.ASN1Object
    public void encodeTo(OutputStream outputStream) {
        ASN1OutputStream create = ASN1OutputStream.create(outputStream);
        create.writePrimitive(this, true);
        create.flushInternal();
    }

    public abstract int encodedLength(boolean z2);

    @Override // org.bouncycastle.asn1.ASN1Object
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ASN1Encodable) && asn1Equals(((ASN1Encodable) obj).toASN1Primitive());
    }

    @Override // org.bouncycastle.asn1.ASN1Object
    public abstract int hashCode();

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public final ASN1Primitive toASN1Primitive() {
        return this;
    }

    public ASN1Primitive toDERObject() {
        return this;
    }

    public ASN1Primitive toDLObject() {
        return this;
    }

    @Override // org.bouncycastle.asn1.ASN1Object
    public void encodeTo(OutputStream outputStream, String str) {
        ASN1OutputStream create = ASN1OutputStream.create(outputStream, str);
        create.writePrimitive(this, true);
        create.flushInternal();
    }

    public final boolean equals(ASN1Encodable aSN1Encodable) {
        return this == aSN1Encodable || (aSN1Encodable != null && asn1Equals(aSN1Encodable.toASN1Primitive()));
    }

    public final boolean equals(ASN1Primitive aSN1Primitive) {
        return this == aSN1Primitive || asn1Equals(aSN1Primitive);
    }
}
