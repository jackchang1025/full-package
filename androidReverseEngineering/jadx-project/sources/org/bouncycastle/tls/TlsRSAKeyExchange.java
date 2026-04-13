package org.bouncycastle.tls;

import java.io.InputStream;
import java.io.OutputStream;
import org.bouncycastle.tls.crypto.TlsCryptoParameters;
import org.bouncycastle.tls.crypto.TlsEncryptor;
import org.bouncycastle.tls.crypto.TlsSecret;

/* loaded from: classes.dex */
public class TlsRSAKeyExchange extends AbstractTlsKeyExchange {
    protected TlsSecret preMasterSecret;
    protected TlsCredentialedDecryptor serverCredentials;
    protected TlsEncryptor serverEncryptor;

    public TlsRSAKeyExchange(int i2) {
        super(checkKeyExchange(i2));
        this.serverCredentials = null;
    }

    private static int checkKeyExchange(int i2) {
        if (i2 == 1) {
            return i2;
        }
        throw new IllegalArgumentException("unsupported key exchange algorithm");
    }

    @Override // org.bouncycastle.tls.TlsKeyExchange
    public void generateClientKeyExchange(OutputStream outputStream) {
        this.preMasterSecret = TlsUtils.generateEncryptedPreMasterSecret(this.context, this.serverEncryptor, outputStream);
    }

    @Override // org.bouncycastle.tls.TlsKeyExchange
    public TlsSecret generatePreMasterSecret() {
        TlsSecret tlsSecret = this.preMasterSecret;
        this.preMasterSecret = null;
        return tlsSecret;
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchange, org.bouncycastle.tls.TlsKeyExchange
    public short[] getClientCertificateTypes() {
        return new short[]{1, 2, 64};
    }

    @Override // org.bouncycastle.tls.TlsKeyExchange
    public void processClientCredentials(TlsCredentials tlsCredentials) {
        TlsUtils.requireSignerCredentials(tlsCredentials);
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchange, org.bouncycastle.tls.TlsKeyExchange
    public void processClientKeyExchange(InputStream inputStream) {
        this.preMasterSecret = this.serverCredentials.decrypt(new TlsCryptoParameters(this.context), TlsUtils.readEncryptedPMS(this.context, inputStream));
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchange, org.bouncycastle.tls.TlsKeyExchange
    public void processServerCertificate(Certificate certificate) {
        this.serverEncryptor = certificate.getCertificateAt(0).createEncryptor(3);
    }

    @Override // org.bouncycastle.tls.TlsKeyExchange
    public void processServerCredentials(TlsCredentials tlsCredentials) {
        this.serverCredentials = TlsUtils.requireDecryptorCredentials(tlsCredentials);
    }

    @Override // org.bouncycastle.tls.TlsKeyExchange
    public void skipServerCredentials() {
        throw new TlsFatalAlert((short) 80);
    }
}
