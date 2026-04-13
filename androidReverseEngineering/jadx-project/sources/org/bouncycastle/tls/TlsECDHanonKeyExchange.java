package org.bouncycastle.tls;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.bouncycastle.tls.crypto.TlsAgreement;
import org.bouncycastle.tls.crypto.TlsECConfig;
import org.bouncycastle.tls.crypto.TlsSecret;

/* loaded from: classes.dex */
public class TlsECDHanonKeyExchange extends AbstractTlsKeyExchange {
    protected TlsAgreement agreement;
    protected TlsECConfig ecConfig;

    public TlsECDHanonKeyExchange(int i2) {
        this(i2, null);
    }

    private static int checkKeyExchange(int i2) {
        if (i2 == 20) {
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
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        TlsECCUtils.writeECConfig(this.ecConfig, byteArrayOutputStream);
        this.agreement = this.context.getCrypto().createECDomain(this.ecConfig).createECDH();
        generateEphemeral(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchange, org.bouncycastle.tls.TlsKeyExchange
    public short[] getClientCertificateTypes() {
        return null;
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchange, org.bouncycastle.tls.TlsKeyExchange
    public void processClientCertificate(Certificate certificate) {
        throw new TlsFatalAlert((short) 10);
    }

    @Override // org.bouncycastle.tls.TlsKeyExchange
    public void processClientCredentials(TlsCredentials tlsCredentials) {
        throw new TlsFatalAlert((short) 80);
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
        throw new TlsFatalAlert((short) 10);
    }

    @Override // org.bouncycastle.tls.TlsKeyExchange
    public void processServerCredentials(TlsCredentials tlsCredentials) {
        throw new TlsFatalAlert((short) 80);
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchange, org.bouncycastle.tls.TlsKeyExchange
    public void processServerKeyExchange(InputStream inputStream) {
        this.ecConfig = TlsECCUtils.receiveECDHConfig(this.context, inputStream);
        byte[] readOpaque8 = TlsUtils.readOpaque8(inputStream, 1);
        this.agreement = this.context.getCrypto().createECDomain(this.ecConfig).createECDH();
        processEphemeral(readOpaque8);
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchange, org.bouncycastle.tls.TlsKeyExchange
    public boolean requiresServerKeyExchange() {
        return true;
    }

    @Override // org.bouncycastle.tls.TlsKeyExchange
    public void skipServerCredentials() {
    }

    public TlsECDHanonKeyExchange(int i2, TlsECConfig tlsECConfig) {
        super(checkKeyExchange(i2));
        this.ecConfig = tlsECConfig;
    }
}
