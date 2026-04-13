package org.bouncycastle.eac;

import java.io.IOException;
import java.io.OutputStream;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1ParsingException;
import org.bouncycastle.asn1.eac.CVCertificate;
import org.bouncycastle.asn1.eac.PublicKeyDataObject;
import org.bouncycastle.eac.operator.EACSignatureVerifier;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class EACCertificateHolder {
    private CVCertificate cvCertificate;

    public EACCertificateHolder(CVCertificate cVCertificate) {
        this.cvCertificate = cVCertificate;
    }

    private static CVCertificate parseBytes(byte[] bArr) {
        try {
            return CVCertificate.getInstance(bArr);
        } catch (ClassCastException e2) {
            throw new EACIOException("malformed data: " + e2.getMessage(), e2);
        } catch (IllegalArgumentException e3) {
            throw new EACIOException("malformed data: " + e3.getMessage(), e3);
        } catch (ASN1ParsingException e4) {
            if (e4.getCause() instanceof IOException) {
                throw ((IOException) e4.getCause());
            }
            throw new EACIOException("malformed data: " + e4.getMessage(), e4);
        }
    }

    public PublicKeyDataObject getPublicKeyDataObject() {
        return this.cvCertificate.getBody().getPublicKey();
    }

    public boolean isSignatureValid(EACSignatureVerifier eACSignatureVerifier) {
        try {
            OutputStream outputStream = eACSignatureVerifier.getOutputStream();
            outputStream.write(this.cvCertificate.getBody().getEncoded(ASN1Encoding.DER));
            outputStream.close();
            return eACSignatureVerifier.verify(this.cvCertificate.getSignature());
        } catch (Exception e2) {
            throw new EACException(AbstractC0000a.m9e(e2, new StringBuilder("unable to process signature: ")), e2);
        }
    }

    public CVCertificate toASN1Structure() {
        return this.cvCertificate;
    }

    public EACCertificateHolder(byte[] bArr) {
        this(parseBytes(bArr));
    }
}
