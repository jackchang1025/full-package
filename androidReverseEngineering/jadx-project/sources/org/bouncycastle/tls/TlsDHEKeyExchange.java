package org.bouncycastle.tls;

import java.io.InputStream;
import java.io.OutputStream;
import org.bouncycastle.tls.crypto.TlsAgreement;
import org.bouncycastle.tls.crypto.TlsCertificate;
import org.bouncycastle.tls.crypto.TlsDHConfig;
import org.bouncycastle.tls.crypto.TlsSecret;
import org.bouncycastle.util.io.TeeInputStream;

/* loaded from: classes.dex */
public class TlsDHEKeyExchange extends AbstractTlsKeyExchange {
    protected TlsAgreement agreement;
    protected TlsDHConfig dhConfig;
    protected TlsDHGroupVerifier dhGroupVerifier;
    protected TlsCertificate serverCertificate;
    protected TlsCredentialedSigner serverCredentials;

    public TlsDHEKeyExchange(int i2, TlsDHGroupVerifier tlsDHGroupVerifier) {
        this(i2, tlsDHGroupVerifier, null);
    }

    private static int checkKeyExchange(int i2) {
        if (i2 == 3 || i2 == 5) {
            return i2;
        }
        throw new IllegalArgumentException("unsupported key exchange algorithm");
    }

    @Override // org.bouncycastle.tls.TlsKeyExchange
    public void generateClientKeyExchange(OutputStream outputStream) {
        TlsUtils.writeOpaque16(this.agreement.generateEphemeral(), outputStream);
    }

    @Override // org.bouncycastle.tls.TlsKeyExchange
    public TlsSecret generatePreMasterSecret() {
        return this.agreement.calculateSecret();
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchange, org.bouncycastle.tls.TlsKeyExchange
    public byte[] generateServerKeyExchange() {
        DigestInputBuffer digestInputBuffer = new DigestInputBuffer();
        TlsDHUtils.writeDHConfig(this.dhConfig, digestInputBuffer);
        TlsAgreement createDH = this.context.getCrypto().createDHDomain(this.dhConfig).createDH();
        this.agreement = createDH;
        TlsUtils.writeOpaque16(createDH.generateEphemeral(), digestInputBuffer);
        TlsUtils.generateServerKeyExchangeSignature(this.context, this.serverCredentials, null, digestInputBuffer);
        return digestInputBuffer.toByteArray();
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchange, org.bouncycastle.tls.TlsKeyExchange
    public short[] getClientCertificateTypes() {
        return new short[]{2, 64, 1};
    }

    @Override // org.bouncycastle.tls.TlsKeyExchange
    public void processClientCredentials(TlsCredentials tlsCredentials) {
        TlsUtils.requireSignerCredentials(tlsCredentials);
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchange, org.bouncycastle.tls.TlsKeyExchange
    public void processClientKeyExchange(InputStream inputStream) {
        this.agreement.receivePeerValue(TlsUtils.readOpaque16(inputStream, 1));
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchange, org.bouncycastle.tls.TlsKeyExchange
    public void processServerCertificate(Certificate certificate) {
        this.serverCertificate = certificate.getCertificateAt(0);
    }

    @Override // org.bouncycastle.tls.TlsKeyExchange
    public void processServerCredentials(TlsCredentials tlsCredentials) {
        this.serverCredentials = TlsUtils.requireSignerCredentials(tlsCredentials);
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchange, org.bouncycastle.tls.TlsKeyExchange
    public void processServerKeyExchange(InputStream inputStream) {
        DigestInputBuffer digestInputBuffer = new DigestInputBuffer();
        TeeInputStream teeInputStream = new TeeInputStream(inputStream, digestInputBuffer);
        this.dhConfig = TlsDHUtils.receiveDHConfig(this.context, this.dhGroupVerifier, teeInputStream);
        byte[] readOpaque16 = TlsUtils.readOpaque16(teeInputStream, 1);
        TlsUtils.verifyServerKeyExchangeSignature(this.context, inputStream, this.serverCertificate, null, digestInputBuffer);
        TlsAgreement createDH = this.context.getCrypto().createDHDomain(this.dhConfig).createDH();
        this.agreement = createDH;
        createDH.receivePeerValue(readOpaque16);
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchange, org.bouncycastle.tls.TlsKeyExchange
    public boolean requiresServerKeyExchange() {
        return true;
    }

    @Override // org.bouncycastle.tls.TlsKeyExchange
    public void skipServerCredentials() {
        throw new TlsFatalAlert((short) 80);
    }

    private TlsDHEKeyExchange(int i2, TlsDHGroupVerifier tlsDHGroupVerifier, TlsDHConfig tlsDHConfig) {
        super(checkKeyExchange(i2));
        this.serverCredentials = null;
        this.serverCertificate = null;
        this.dhGroupVerifier = tlsDHGroupVerifier;
        this.dhConfig = tlsDHConfig;
    }

    public TlsDHEKeyExchange(int i2, TlsDHConfig tlsDHConfig) {
        this(i2, null, tlsDHConfig);
    }
}
