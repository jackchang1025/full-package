package org.bouncycastle.cms.jcajce;

import java.security.Provider;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cms.DefaultCMSSignatureAlgorithmNameGenerator;
import org.bouncycastle.cms.SignerInformationVerifier;
import org.bouncycastle.operator.ContentVerifierProvider;
import org.bouncycastle.operator.DefaultSignatureAlgorithmIdentifierFinder;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;

/* loaded from: classes.dex */
public class JcaSimpleSignerInfoVerifierBuilder {
    private Helper helper = new Helper();

    public class Helper {
        private Helper() {
        }

        public ContentVerifierProvider createContentVerifierProvider(PublicKey publicKey) {
            return new JcaContentVerifierProviderBuilder().build(publicKey);
        }

        public DigestCalculatorProvider createDigestCalculatorProvider() {
            return new JcaDigestCalculatorProviderBuilder().build();
        }

        public ContentVerifierProvider createContentVerifierProvider(X509Certificate x509Certificate) {
            return new JcaContentVerifierProviderBuilder().build(x509Certificate);
        }

        public ContentVerifierProvider createContentVerifierProvider(X509CertificateHolder x509CertificateHolder) {
            return new JcaContentVerifierProviderBuilder().build(x509CertificateHolder);
        }
    }

    public class NamedHelper extends Helper {
        private final String providerName;

        public NamedHelper(String str) {
            super();
            this.providerName = str;
        }

        @Override // org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder.Helper
        public ContentVerifierProvider createContentVerifierProvider(PublicKey publicKey) {
            return new JcaContentVerifierProviderBuilder().setProvider(this.providerName).build(publicKey);
        }

        @Override // org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder.Helper
        public DigestCalculatorProvider createDigestCalculatorProvider() {
            return new JcaDigestCalculatorProviderBuilder().setProvider(this.providerName).build();
        }

        @Override // org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder.Helper
        public ContentVerifierProvider createContentVerifierProvider(X509Certificate x509Certificate) {
            return new JcaContentVerifierProviderBuilder().setProvider(this.providerName).build(x509Certificate);
        }

        @Override // org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder.Helper
        public ContentVerifierProvider createContentVerifierProvider(X509CertificateHolder x509CertificateHolder) {
            return new JcaContentVerifierProviderBuilder().setProvider(this.providerName).build(x509CertificateHolder);
        }
    }

    public class ProviderHelper extends Helper {
        private final Provider provider;

        public ProviderHelper(Provider provider) {
            super();
            this.provider = provider;
        }

        @Override // org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder.Helper
        public ContentVerifierProvider createContentVerifierProvider(PublicKey publicKey) {
            return new JcaContentVerifierProviderBuilder().setProvider(this.provider).build(publicKey);
        }

        @Override // org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder.Helper
        public DigestCalculatorProvider createDigestCalculatorProvider() {
            return new JcaDigestCalculatorProviderBuilder().setProvider(this.provider).build();
        }

        @Override // org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder.Helper
        public ContentVerifierProvider createContentVerifierProvider(X509Certificate x509Certificate) {
            return new JcaContentVerifierProviderBuilder().setProvider(this.provider).build(x509Certificate);
        }

        @Override // org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder.Helper
        public ContentVerifierProvider createContentVerifierProvider(X509CertificateHolder x509CertificateHolder) {
            return new JcaContentVerifierProviderBuilder().setProvider(this.provider).build(x509CertificateHolder);
        }
    }

    public SignerInformationVerifier build(PublicKey publicKey) {
        return new SignerInformationVerifier(new DefaultCMSSignatureAlgorithmNameGenerator(), new DefaultSignatureAlgorithmIdentifierFinder(), this.helper.createContentVerifierProvider(publicKey), this.helper.createDigestCalculatorProvider());
    }

    public JcaSimpleSignerInfoVerifierBuilder setProvider(String str) {
        this.helper = new NamedHelper(str);
        return this;
    }

    public SignerInformationVerifier build(X509Certificate x509Certificate) {
        return new SignerInformationVerifier(new DefaultCMSSignatureAlgorithmNameGenerator(), new DefaultSignatureAlgorithmIdentifierFinder(), this.helper.createContentVerifierProvider(x509Certificate), this.helper.createDigestCalculatorProvider());
    }

    public JcaSimpleSignerInfoVerifierBuilder setProvider(Provider provider) {
        this.helper = new ProviderHelper(provider);
        return this;
    }

    public SignerInformationVerifier build(X509CertificateHolder x509CertificateHolder) {
        return new SignerInformationVerifier(new DefaultCMSSignatureAlgorithmNameGenerator(), new DefaultSignatureAlgorithmIdentifierFinder(), this.helper.createContentVerifierProvider(x509CertificateHolder), this.helper.createDigestCalculatorProvider());
    }
}
