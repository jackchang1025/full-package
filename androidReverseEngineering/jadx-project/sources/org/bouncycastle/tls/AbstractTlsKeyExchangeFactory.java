package org.bouncycastle.tls;

import org.bouncycastle.tls.crypto.TlsDHConfig;
import org.bouncycastle.tls.crypto.TlsECConfig;

/* loaded from: classes.dex */
public abstract class AbstractTlsKeyExchangeFactory implements TlsKeyExchangeFactory {
    @Override // org.bouncycastle.tls.TlsKeyExchangeFactory
    public TlsKeyExchange createDHEKeyExchangeClient(int i2, TlsDHGroupVerifier tlsDHGroupVerifier) {
        throw new TlsFatalAlert((short) 80);
    }

    @Override // org.bouncycastle.tls.TlsKeyExchangeFactory
    public TlsKeyExchange createDHEKeyExchangeServer(int i2, TlsDHConfig tlsDHConfig) {
        throw new TlsFatalAlert((short) 80);
    }

    @Override // org.bouncycastle.tls.TlsKeyExchangeFactory
    public TlsKeyExchange createDHKeyExchange(int i2) {
        throw new TlsFatalAlert((short) 80);
    }

    @Override // org.bouncycastle.tls.TlsKeyExchangeFactory
    public TlsKeyExchange createDHanonKeyExchangeClient(int i2, TlsDHGroupVerifier tlsDHGroupVerifier) {
        throw new TlsFatalAlert((short) 80);
    }

    @Override // org.bouncycastle.tls.TlsKeyExchangeFactory
    public TlsKeyExchange createDHanonKeyExchangeServer(int i2, TlsDHConfig tlsDHConfig) {
        throw new TlsFatalAlert((short) 80);
    }

    @Override // org.bouncycastle.tls.TlsKeyExchangeFactory
    public TlsKeyExchange createECDHEKeyExchangeClient(int i2) {
        throw new TlsFatalAlert((short) 80);
    }

    @Override // org.bouncycastle.tls.TlsKeyExchangeFactory
    public TlsKeyExchange createECDHEKeyExchangeServer(int i2, TlsECConfig tlsECConfig) {
        throw new TlsFatalAlert((short) 80);
    }

    @Override // org.bouncycastle.tls.TlsKeyExchangeFactory
    public TlsKeyExchange createECDHKeyExchange(int i2) {
        throw new TlsFatalAlert((short) 80);
    }

    @Override // org.bouncycastle.tls.TlsKeyExchangeFactory
    public TlsKeyExchange createECDHanonKeyExchangeClient(int i2) {
        throw new TlsFatalAlert((short) 80);
    }

    @Override // org.bouncycastle.tls.TlsKeyExchangeFactory
    public TlsKeyExchange createECDHanonKeyExchangeServer(int i2, TlsECConfig tlsECConfig) {
        throw new TlsFatalAlert((short) 80);
    }

    @Override // org.bouncycastle.tls.TlsKeyExchangeFactory
    public TlsKeyExchange createPSKKeyExchangeClient(int i2, TlsPSKIdentity tlsPSKIdentity, TlsDHGroupVerifier tlsDHGroupVerifier) {
        throw new TlsFatalAlert((short) 80);
    }

    @Override // org.bouncycastle.tls.TlsKeyExchangeFactory
    public TlsKeyExchange createPSKKeyExchangeServer(int i2, TlsPSKIdentityManager tlsPSKIdentityManager, TlsDHConfig tlsDHConfig, TlsECConfig tlsECConfig) {
        throw new TlsFatalAlert((short) 80);
    }

    @Override // org.bouncycastle.tls.TlsKeyExchangeFactory
    public TlsKeyExchange createRSAKeyExchange(int i2) {
        throw new TlsFatalAlert((short) 80);
    }

    @Override // org.bouncycastle.tls.TlsKeyExchangeFactory
    public TlsKeyExchange createSRPKeyExchangeClient(int i2, TlsSRPIdentity tlsSRPIdentity, TlsSRPConfigVerifier tlsSRPConfigVerifier) {
        throw new TlsFatalAlert((short) 80);
    }

    @Override // org.bouncycastle.tls.TlsKeyExchangeFactory
    public TlsKeyExchange createSRPKeyExchangeServer(int i2, TlsSRPLoginParameters tlsSRPLoginParameters) {
        throw new TlsFatalAlert((short) 80);
    }
}
