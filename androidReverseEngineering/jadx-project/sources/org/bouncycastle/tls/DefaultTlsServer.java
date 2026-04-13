package org.bouncycastle.tls;

import org.bouncycastle.tls.crypto.TlsCrypto;

/* loaded from: classes.dex */
public abstract class DefaultTlsServer extends AbstractTlsServer {
    private static final int[] DEFAULT_CIPHER_SUITES = {CipherSuite.TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_DHE_RSA_WITH_CHACHA20_POLY1305_SHA256, CipherSuite.TLS_DHE_RSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA256, CipherSuite.TLS_DHE_RSA_WITH_AES_128_CBC_SHA256, 57, 51, CipherSuite.TLS_RSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_RSA_WITH_AES_128_GCM_SHA256, 61, 60, 53, 47};

    public DefaultTlsServer(TlsCrypto tlsCrypto) {
        super(tlsCrypto);
    }

    public TlsCredentials getCredentials() {
        int keyExchangeAlgorithm = this.context.getSecurityParametersHandshake().getKeyExchangeAlgorithm();
        if (keyExchangeAlgorithm == 1) {
            return getRSAEncryptionCredentials();
        }
        if (keyExchangeAlgorithm == 3) {
            return getDSASignerCredentials();
        }
        if (keyExchangeAlgorithm != 5) {
            if (keyExchangeAlgorithm == 11) {
                return null;
            }
            if (keyExchangeAlgorithm == 17) {
                return getECDSASignerCredentials();
            }
            if (keyExchangeAlgorithm != 19) {
                if (keyExchangeAlgorithm == 20) {
                    return null;
                }
                throw new TlsFatalAlert((short) 80);
            }
        }
        return getRSASignerCredentials();
    }

    public TlsCredentialedSigner getDSASignerCredentials() {
        throw new TlsFatalAlert((short) 80);
    }

    public TlsCredentialedSigner getECDSASignerCredentials() {
        throw new TlsFatalAlert((short) 80);
    }

    public TlsCredentialedDecryptor getRSAEncryptionCredentials() {
        throw new TlsFatalAlert((short) 80);
    }

    public TlsCredentialedSigner getRSASignerCredentials() {
        throw new TlsFatalAlert((short) 80);
    }

    @Override // org.bouncycastle.tls.AbstractTlsPeer
    public int[] getSupportedCipherSuites() {
        return TlsUtils.getSupportedCipherSuites(getCrypto(), DEFAULT_CIPHER_SUITES);
    }
}
