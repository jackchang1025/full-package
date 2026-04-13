package org.bouncycastle.pkcs.bc;

import java.io.IOException;
import org.bouncycastle.asn1.pkcs.CertificationRequest;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCSException;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class BcPKCS10CertificationRequest extends PKCS10CertificationRequest {
    public BcPKCS10CertificationRequest(CertificationRequest certificationRequest) {
        super(certificationRequest);
    }

    public AsymmetricKeyParameter getPublicKey() {
        try {
            return PublicKeyFactory.createKey(getSubjectPublicKeyInfo());
        } catch (IOException e2) {
            throw new PKCSException(AbstractC0000a.m8d(e2, new StringBuilder("error extracting key encoding: ")), e2);
        }
    }

    public BcPKCS10CertificationRequest(PKCS10CertificationRequest pKCS10CertificationRequest) {
        super(pKCS10CertificationRequest.toASN1Structure());
    }

    public BcPKCS10CertificationRequest(byte[] bArr) {
        super(bArr);
    }
}
