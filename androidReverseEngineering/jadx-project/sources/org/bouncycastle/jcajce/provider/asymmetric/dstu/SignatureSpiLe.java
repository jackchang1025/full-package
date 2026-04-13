package org.bouncycastle.jcajce.provider.asymmetric.dstu;

import java.io.IOException;
import java.security.SignatureException;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DEROctetString;

/* loaded from: classes.dex */
public class SignatureSpiLe extends SignatureSpi {
    @Override // org.bouncycastle.jcajce.provider.asymmetric.dstu.SignatureSpi, java.security.SignatureSpi
    public byte[] engineSign() {
        byte[] octets = ASN1OctetString.getInstance(super.engineSign()).getOctets();
        reverseBytes(octets);
        try {
            return new DEROctetString(octets).getEncoded();
        } catch (Exception e2) {
            throw new SignatureException(e2.toString());
        }
    }

    @Override // org.bouncycastle.jcajce.provider.asymmetric.dstu.SignatureSpi, java.security.SignatureSpi
    public boolean engineVerify(byte[] bArr) {
        try {
            byte[] octets = ((ASN1OctetString) ASN1Primitive.fromByteArray(bArr)).getOctets();
            reverseBytes(octets);
            try {
                return super.engineVerify(new DEROctetString(octets).getEncoded());
            } catch (SignatureException e2) {
                throw e2;
            } catch (Exception e3) {
                throw new SignatureException(e3.toString());
            }
        } catch (IOException unused) {
            throw new SignatureException("error decoding signature bytes.");
        }
    }

    public void reverseBytes(byte[] bArr) {
        for (int i2 = 0; i2 < bArr.length / 2; i2++) {
            byte b = bArr[i2];
            bArr[i2] = bArr[(bArr.length - 1) - i2];
            bArr[(bArr.length - 1) - i2] = b;
        }
    }
}
