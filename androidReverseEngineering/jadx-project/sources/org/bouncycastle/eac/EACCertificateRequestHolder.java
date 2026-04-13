package org.bouncycastle.eac;

import java.io.IOException;
import java.io.OutputStream;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1ParsingException;
import org.bouncycastle.asn1.eac.CVCertificateRequest;
import org.bouncycastle.asn1.eac.PublicKeyDataObject;
import org.bouncycastle.eac.operator.EACSignatureVerifier;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class EACCertificateRequestHolder {
    private CVCertificateRequest request;

    public EACCertificateRequestHolder(CVCertificateRequest cVCertificateRequest) {
        this.request = cVCertificateRequest;
    }

    private static CVCertificateRequest parseBytes(byte[] bArr) {
        try {
            return CVCertificateRequest.getInstance(bArr);
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
        return this.request.getPublicKey();
    }

    public boolean isInnerSignatureValid(EACSignatureVerifier eACSignatureVerifier) {
        try {
            OutputStream outputStream = eACSignatureVerifier.getOutputStream();
            outputStream.write(this.request.getCertificateBody().getEncoded(ASN1Encoding.DER));
            outputStream.close();
            return eACSignatureVerifier.verify(this.request.getInnerSignature());
        } catch (Exception e2) {
            throw new EACException(AbstractC0000a.m9e(e2, new StringBuilder("unable to process signature: ")), e2);
        }
    }

    public CVCertificateRequest toASN1Structure() {
        return this.request;
    }

    public EACCertificateRequestHolder(byte[] bArr) {
        this(parseBytes(bArr));
    }
}
