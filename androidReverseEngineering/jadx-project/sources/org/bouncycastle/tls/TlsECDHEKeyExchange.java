package org.bouncycastle.tls;

import java.io.InputStream;
import java.io.OutputStream;
import org.bouncycastle.tls.crypto.TlsAgreement;
import org.bouncycastle.tls.crypto.TlsCertificate;
import org.bouncycastle.tls.crypto.TlsECConfig;
import org.bouncycastle.tls.crypto.TlsSecret;
import org.bouncycastle.util.io.TeeInputStream;

/* loaded from: classes.dex */
public class TlsECDHEKeyExchange extends AbstractTlsKeyExchange {
    protected TlsAgreement agreement;
    protected TlsECConfig ecConfig;
    protected TlsCertificate serverCertificate;
    protected TlsCredentialedSigner serverCredentials;

    public TlsECDHEKeyExchange(int i2) {
        this(i2, null);
    }

    private static int checkKeyExchange(int i2) {
        if (i2 == 17 || i2 == 19) {
            return i2;
        }
        throw new IllegalArgumentException("unsupported key exchange algorithm");
    }

    @Override // org.bouncycastle.tls.TlsKeyExchange
    public void generateClientKeyExchange(OutputStream outputStream) {
        generateEphemeral(outputStream);
    }

    public void generateEphemeral(OutputStream outputStream) {
        TlsUtils.writeOpaque8(this.agreement.generateEphemeral(), outputStream);
    }

    @Override // org.bouncycastle.tls.TlsKeyExchange
    public TlsSecret generatePreMasterSecret() {
        return this.agreement.calculateSecret();
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchange, org.bouncycastle.tls.TlsKeyExchange
    public byte[] generateServerKeyExchange() {
        DigestInputBuffer digestInputBuffer = new DigestInputBuffer();
        TlsECCUtils.writeECConfig(this.ecConfig, digestInputBuffer);
        this.agreement = this.context.getCrypto().createECDomain(this.ecConfig).createECDH();
        generateEphemeral(digestInputBuffer);
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
        processEphemeral(TlsUtils.readOpaque8(inputStream, 1));
    }

    public void processEphemeral(byte[] bArr) {
        TlsECCUtils.checkPointEncoding(this.ecConfig.getNamedGroup(), bArr);
        this.agreement.receivePeerValue(bArr);
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
        this.ecConfig = TlsECCUtils.receiveECDHConfig(this.context, teeInputStream);
        byte[] readOpaque8 = TlsUtils.readOpaque8(teeInputStream, 1);
        TlsUtils.verifyServerKeyExchangeSignature(this.context, inputStream, this.serverCertificate, null, digestInputBuffer);
        this.agreement = this.context.getCrypto().createECDomain(this.ecConfig).createECDH();
        processEphemeral(readOpaque8);
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchange, org.bouncycastle.tls.TlsKeyExchange
    public boolean requiresServerKeyExchange() {
        return true;
    }

    @Override // org.bouncycastle.tls.TlsKeyExchange
    public void skipServerCredentials() {
        throw new TlsFatalAlert((short) 80);
    }

    public TlsECDHEKeyExchange(int i2, TlsECConfig tlsECConfig) {
        super(checkKeyExchange(i2));
        this.serverCredentials = null;
        this.serverCertificate = null;
        this.ecConfig = tlsECConfig;
    }
}
