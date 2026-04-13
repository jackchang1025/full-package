package org.bouncycastle.tls;

import java.io.InputStream;
import java.io.OutputStream;
import org.bouncycastle.tls.crypto.TlsCertificate;
import org.bouncycastle.tls.crypto.TlsSecret;

/* loaded from: classes.dex */
public class TlsDHKeyExchange extends AbstractTlsKeyExchange {
    protected TlsCredentialedAgreement agreementCredentials;
    protected TlsCertificate dhPeerCertificate;

    public TlsDHKeyExchange(int i2) {
        super(checkKeyExchange(i2));
    }

    private static int checkKeyExchange(int i2) {
        if (i2 == 7 || i2 == 9) {
            return i2;
        }
        throw new IllegalArgumentException("unsupported key exchange algorithm");
    }

    @Override // org.bouncycastle.tls.TlsKeyExchange
    public void generateClientKeyExchange(OutputStream outputStream) {
    }

    @Override // org.bouncycastle.tls.TlsKeyExchange
    public TlsSecret generatePreMasterSecret() {
        return this.agreementCredentials.generateAgreement(this.dhPeerCertificate);
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchange, org.bouncycastle.tls.TlsKeyExchange
    public short[] getClientCertificateTypes() {
        return new short[]{4, 3};
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchange, org.bouncycastle.tls.TlsKeyExchange
    public void processClientCertificate(Certificate certificate) {
        this.dhPeerCertificate = certificate.getCertificateAt(0).checkUsageInRole(1);
    }

    @Override // org.bouncycastle.tls.TlsKeyExchange
    public void processClientCredentials(TlsCredentials tlsCredentials) {
        this.agreementCredentials = TlsUtils.requireAgreementCredentials(tlsCredentials);
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchange, org.bouncycastle.tls.TlsKeyExchange
    public void processClientKeyExchange(InputStream inputStream) {
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchange, org.bouncycastle.tls.TlsKeyExchange
    public void processServerCertificate(Certificate certificate) {
        this.dhPeerCertificate = certificate.getCertificateAt(0).checkUsageInRole(1);
    }

    @Override // org.bouncycastle.tls.TlsKeyExchange
    public void processServerCredentials(TlsCredentials tlsCredentials) {
        this.agreementCredentials = TlsUtils.requireAgreementCredentials(tlsCredentials);
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchange, org.bouncycastle.tls.TlsKeyExchange
    public boolean requiresCertificateVerify() {
        return false;
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchange, org.bouncycastle.tls.TlsKeyExchange
    public void skipClientCredentials() {
        throw new TlsFatalAlert((short) 10);
    }

    @Override // org.bouncycastle.tls.TlsKeyExchange
    public void skipServerCredentials() {
        throw new TlsFatalAlert((short) 80);
    }
}
