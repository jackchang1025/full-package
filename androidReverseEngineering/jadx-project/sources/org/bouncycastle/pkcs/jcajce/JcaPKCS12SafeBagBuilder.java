package org.bouncycastle.pkcs.jcajce;

import java.security.PrivateKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.operator.OutputEncryptor;
import org.bouncycastle.pkcs.PKCS12SafeBagBuilder;
import org.bouncycastle.pkcs.PKCSIOException;

/* loaded from: classes.dex */
public class JcaPKCS12SafeBagBuilder extends PKCS12SafeBagBuilder {
    public JcaPKCS12SafeBagBuilder(PrivateKey privateKey) {
        super(PrivateKeyInfo.getInstance(privateKey.getEncoded()));
    }

    private static Certificate convertCert(X509Certificate x509Certificate) {
        try {
            return Certificate.getInstance(x509Certificate.getEncoded());
        } catch (CertificateEncodingException e2) {
            throw new PKCSIOException("cannot encode certificate: " + e2.getMessage(), e2);
        }
    }

    public JcaPKCS12SafeBagBuilder(PrivateKey privateKey, OutputEncryptor outputEncryptor) {
        super(PrivateKeyInfo.getInstance(privateKey.getEncoded()), outputEncryptor);
    }

    public JcaPKCS12SafeBagBuilder(X509Certificate x509Certificate) {
        super(convertCert(x509Certificate));
    }
}
