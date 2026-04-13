package org.bouncycastle.tls.crypto.impl.bc;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.DHPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.tls.Certificate;
import org.bouncycastle.tls.TlsCredentialedAgreement;
import org.bouncycastle.tls.crypto.TlsCertificate;
import org.bouncycastle.tls.crypto.TlsSecret;

/* loaded from: classes.dex */
public class BcDefaultTlsCredentialedAgreement implements TlsCredentialedAgreement {
    protected TlsCredentialedAgreement agreementCredentials;

    public class DHCredentialedAgreement implements TlsCredentialedAgreement {
        final Certificate certificate;
        final BcTlsCrypto crypto;
        final DHPrivateKeyParameters privateKey;

        public DHCredentialedAgreement(BcTlsCrypto bcTlsCrypto, Certificate certificate, DHPrivateKeyParameters dHPrivateKeyParameters) {
            this.crypto = bcTlsCrypto;
            this.certificate = certificate;
            this.privateKey = dHPrivateKeyParameters;
        }

        @Override // org.bouncycastle.tls.TlsCredentialedAgreement
        public TlsSecret generateAgreement(TlsCertificate tlsCertificate) {
            return BcTlsDHDomain.calculateDHAgreement(this.crypto, this.privateKey, BcTlsCertificate.convert(this.crypto, tlsCertificate).getPubKeyDH(), false);
        }

        @Override // org.bouncycastle.tls.TlsCredentials
        public Certificate getCertificate() {
            return this.certificate;
        }
    }

    public class ECCredentialedAgreement implements TlsCredentialedAgreement {
        final Certificate certificate;
        final BcTlsCrypto crypto;
        final ECPrivateKeyParameters privateKey;

        public ECCredentialedAgreement(BcTlsCrypto bcTlsCrypto, Certificate certificate, ECPrivateKeyParameters eCPrivateKeyParameters) {
            this.crypto = bcTlsCrypto;
            this.certificate = certificate;
            this.privateKey = eCPrivateKeyParameters;
        }

        @Override // org.bouncycastle.tls.TlsCredentialedAgreement
        public TlsSecret generateAgreement(TlsCertificate tlsCertificate) {
            return BcTlsECDomain.calculateBasicAgreement(this.crypto, this.privateKey, BcTlsCertificate.convert(this.crypto, tlsCertificate).getPubKeyEC());
        }

        @Override // org.bouncycastle.tls.TlsCredentials
        public Certificate getCertificate() {
            return this.certificate;
        }
    }

    public BcDefaultTlsCredentialedAgreement(BcTlsCrypto bcTlsCrypto, Certificate certificate, AsymmetricKeyParameter asymmetricKeyParameter) {
        TlsCredentialedAgreement eCCredentialedAgreement;
        if (bcTlsCrypto == null) {
            throw new IllegalArgumentException("'crypto' cannot be null");
        }
        if (certificate == null) {
            throw new IllegalArgumentException("'certificate' cannot be null");
        }
        if (certificate.isEmpty()) {
            throw new IllegalArgumentException("'certificate' cannot be empty");
        }
        if (asymmetricKeyParameter == null) {
            throw new IllegalArgumentException("'privateKey' cannot be null");
        }
        if (!asymmetricKeyParameter.isPrivate()) {
            throw new IllegalArgumentException("'privateKey' must be private");
        }
        if (asymmetricKeyParameter instanceof DHPrivateKeyParameters) {
            eCCredentialedAgreement = new DHCredentialedAgreement(bcTlsCrypto, certificate, (DHPrivateKeyParameters) asymmetricKeyParameter);
        } else {
            if (!(asymmetricKeyParameter instanceof ECPrivateKeyParameters)) {
                throw new IllegalArgumentException("'privateKey' type not supported: ".concat(asymmetricKeyParameter.getClass().getName()));
            }
            eCCredentialedAgreement = new ECCredentialedAgreement(bcTlsCrypto, certificate, (ECPrivateKeyParameters) asymmetricKeyParameter);
        }
        this.agreementCredentials = eCCredentialedAgreement;
    }

    @Override // org.bouncycastle.tls.TlsCredentialedAgreement
    public TlsSecret generateAgreement(TlsCertificate tlsCertificate) {
        return this.agreementCredentials.generateAgreement(tlsCertificate);
    }

    @Override // org.bouncycastle.tls.TlsCredentials
    public Certificate getCertificate() {
        return this.agreementCredentials.getCertificate();
    }
}
