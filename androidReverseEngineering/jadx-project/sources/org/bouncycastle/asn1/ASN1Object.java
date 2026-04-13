package org.bouncycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import org.bouncycastle.util.Encodable;

/* loaded from: classes.dex */
public abstract class ASN1Object implements ASN1Encodable, Encodable {
    public static boolean hasEncodedTagValue(Object obj, int i2) {
        return (obj instanceof byte[]) && ((byte[]) obj)[0] == i2;
    }

    public void encodeTo(OutputStream outputStream) {
        toASN1Primitive().encodeTo(outputStream);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ASN1Encodable) {
            return toASN1Primitive().equals(((ASN1Encodable) obj).toASN1Primitive());
        }
        return false;
    }

    @Override // org.bouncycastle.util.Encodable
    public byte[] getEncoded() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        toASN1Primitive().encodeTo(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public int hashCode() {
        return toASN1Primitive().hashCode();
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public abstract ASN1Primitive toASN1Primitive();

    public void encodeTo(OutputStream outputStream, String str) {
        toASN1Primitive().encodeTo(outputStream, str);
    }

    public byte[] getEncoded(String str) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        toASN1Primitive().encodeTo(byteArrayOutputStream, str);
        return byteArrayOutputStream.toByteArray();
    }
}
