package org.bouncycastle.tls;

import org.bouncycastle.tls.crypto.TlsDHConfig;
import org.bouncycastle.tls.crypto.TlsECConfig;

/* loaded from: classes.dex */
public class DefaultTlsKeyExchangeFactory extends AbstractTlsKeyExchangeFactory {
    @Override // org.bouncycastle.tls.AbstractTlsKeyExchangeFactory, org.bouncycastle.tls.TlsKeyExchangeFactory
    public TlsKeyExchange createDHEKeyExchangeClient(int i2, TlsDHGroupVerifier tlsDHGroupVerifier) {
        return new TlsDHEKeyExchange(i2, tlsDHGroupVerifier);
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchangeFactory, org.bouncycastle.tls.TlsKeyExchangeFactory
    public TlsKeyExchange createDHEKeyExchangeServer(int i2, TlsDHConfig tlsDHConfig) {
        return new TlsDHEKeyExchange(i2, tlsDHConfig);
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchangeFactory, org.bouncycastle.tls.TlsKeyExchangeFactory
    public TlsKeyExchange createDHKeyExchange(int i2) {
        return new TlsDHKeyExchange(i2);
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchangeFactory, org.bouncycastle.tls.TlsKeyExchangeFactory
    public TlsKeyExchange createDHanonKeyExchangeClient(int i2, TlsDHGroupVerifier tlsDHGroupVerifier) {
        return new TlsDHanonKeyExchange(i2, tlsDHGroupVerifier);
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchangeFactory, org.bouncycastle.tls.TlsKeyExchangeFactory
    public TlsKeyExchange createDHanonKeyExchangeServer(int i2, TlsDHConfig tlsDHConfig) {
        return new TlsDHanonKeyExchange(i2, tlsDHConfig);
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchangeFactory, org.bouncycastle.tls.TlsKeyExchangeFactory
    public TlsKeyExchange createECDHEKeyExchangeClient(int i2) {
        return new TlsECDHEKeyExchange(i2);
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchangeFactory, org.bouncycastle.tls.TlsKeyExchangeFactory
    public TlsKeyExchange createECDHEKeyExchangeServer(int i2, TlsECConfig tlsECConfig) {
        return new TlsECDHEKeyExchange(i2, tlsECConfig);
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchangeFactory, org.bouncycastle.tls.TlsKeyExchangeFactory
    public TlsKeyExchange createECDHKeyExchange(int i2) {
        return new TlsECDHKeyExchange(i2);
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchangeFactory, org.bouncycastle.tls.TlsKeyExchangeFactory
    public TlsKeyExchange createECDHanonKeyExchangeClient(int i2) {
        return new TlsECDHanonKeyExchange(i2);
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchangeFactory, org.bouncycastle.tls.TlsKeyExchangeFactory
    public TlsKeyExchange createECDHanonKeyExchangeServer(int i2, TlsECConfig tlsECConfig) {
        return new TlsECDHanonKeyExchange(i2, tlsECConfig);
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchangeFactory, org.bouncycastle.tls.TlsKeyExchangeFactory
    public TlsKeyExchange createPSKKeyExchangeClient(int i2, TlsPSKIdentity tlsPSKIdentity, TlsDHGroupVerifier tlsDHGroupVerifier) {
        return new TlsPSKKeyExchange(i2, tlsPSKIdentity, tlsDHGroupVerifier);
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchangeFactory, org.bouncycastle.tls.TlsKeyExchangeFactory
    public TlsKeyExchange createPSKKeyExchangeServer(int i2, TlsPSKIdentityManager tlsPSKIdentityManager, TlsDHConfig tlsDHConfig, TlsECConfig tlsECConfig) {
        return new TlsPSKKeyExchange(i2, tlsPSKIdentityManager, tlsDHConfig, tlsECConfig);
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchangeFactory, org.bouncycastle.tls.TlsKeyExchangeFactory
    public TlsKeyExchange createRSAKeyExchange(int i2) {
        return new TlsRSAKeyExchange(i2);
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchangeFactory, org.bouncycastle.tls.TlsKeyExchangeFactory
    public TlsKeyExchange createSRPKeyExchangeClient(int i2, TlsSRPIdentity tlsSRPIdentity, TlsSRPConfigVerifier tlsSRPConfigVerifier) {
        return new TlsSRPKeyExchange(i2, tlsSRPIdentity, tlsSRPConfigVerifier);
    }

    @Override // org.bouncycastle.tls.AbstractTlsKeyExchangeFactory, org.bouncycastle.tls.TlsKeyExchangeFactory
    public TlsKeyExchange createSRPKeyExchangeServer(int i2, TlsSRPLoginParameters tlsSRPLoginParameters) {
        return new TlsSRPKeyExchange(i2, tlsSRPLoginParameters);
    }
}
