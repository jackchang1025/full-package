package org.bouncycastle.cert.jcajce;

import java.security.Provider;
import java.security.cert.CertificateException;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509ContentVerifierProviderBuilder;
import org.bouncycastle.operator.ContentVerifierProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder;

/* loaded from: classes.dex */
public class JcaX509ContentVerifierProviderBuilder implements X509ContentVerifierProviderBuilder {
    private JcaContentVerifierProviderBuilder builder = new JcaContentVerifierProviderBuilder();

    @Override // org.bouncycastle.cert.X509ContentVerifierProviderBuilder
    public ContentVerifierProvider build(SubjectPublicKeyInfo subjectPublicKeyInfo) {
        return this.builder.build(subjectPublicKeyInfo);
    }

    public JcaX509ContentVerifierProviderBuilder setProvider(String str) {
        this.builder.setProvider(str);
        return this;
    }

    @Override // org.bouncycastle.cert.X509ContentVerifierProviderBuilder
    public ContentVerifierProvider build(X509CertificateHolder x509CertificateHolder) {
        try {
            return this.builder.build(x509CertificateHolder);
        } catch (CertificateException e2) {
            throw new OperatorCreationException("Unable to process certificate: " + e2.getMessage(), e2);
        }
    }

    public JcaX509ContentVerifierProviderBuilder setProvider(Provider provider) {
        this.builder.setProvider(provider);
        return this;
    }
}
