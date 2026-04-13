package org.bouncycastle.tls;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.bouncycastle.tls.crypto.TlsAgreement;
import org.bouncycastle.tls.crypto.TlsDHConfig;
import org.bouncycastle.tls.crypto.TlsSecret;

/* loaded from: classes.dex */
public class TlsDHanonKeyExchange extends AbstractTlsKeyExchange {
    protected TlsAgreement agreement;
    protected TlsDHConfig dhConfig;
    protected TlsDHGroupVerifier dhGroupVerifier;

    public TlsDHanonKeyExchange(int i2, TlsDHGroupVerifier tlsDHGroupVerifier) {
        this(i2, tlsDHGroupVerifier, null);
    }

    private static int checkKeyExchange(int i2) {
        if (i2 == 11) {
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
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        TlsDHUtils.writeDHConfig(this.dhConfig, byteArrayOutputStream);
        TlsAgreement createDH = this.context.getCrypto().createDHDomain(this.dhConfig).createDH();
        this.agreement = createDH;
        TlsUtils.writeOpaque16(createDH.generateEphemeral(), byteArrayOutputStream);
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
        this.agreement.receivePeerValue(TlsUtils.readOpaque16(inputStream, 1));
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
        this.dhConfig = TlsDHUtils.receiveDHConfig(this.context, this.dhGroupVerifier, inputStream);
        byte[] readOpaque16 = TlsUtils.readOpaque16(inputStream, 1);
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
    }

    private TlsDHanonKeyExchange(int i2, TlsDHGroupVerifier tlsDHGroupVerifier, TlsDHConfig tlsDHConfig) {
        super(checkKeyExchange(i2));
        this.dhGroupVerifier = tlsDHGroupVerifier;
        this.dhConfig = tlsDHConfig;
    }

    public TlsDHanonKeyExchange(int i2, TlsDHConfig tlsDHConfig) {
        this(i2, null, tlsDHConfig);
    }
}
