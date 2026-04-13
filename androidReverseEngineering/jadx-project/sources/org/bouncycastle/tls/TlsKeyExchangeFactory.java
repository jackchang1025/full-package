package org.bouncycastle.tls;

import org.bouncycastle.tls.crypto.TlsDHConfig;
import org.bouncycastle.tls.crypto.TlsECConfig;

/* loaded from: classes.dex */
public interface TlsKeyExchangeFactory {
    TlsKeyExchange createDHEKeyExchangeClient(int i2, TlsDHGroupVerifier tlsDHGroupVerifier);

    TlsKeyExchange createDHEKeyExchangeServer(int i2, TlsDHConfig tlsDHConfig);

    TlsKeyExchange createDHKeyExchange(int i2);

    TlsKeyExchange createDHanonKeyExchangeClient(int i2, TlsDHGroupVerifier tlsDHGroupVerifier);

    TlsKeyExchange createDHanonKeyExchangeServer(int i2, TlsDHConfig tlsDHConfig);

    TlsKeyExchange createECDHEKeyExchangeClient(int i2);

    TlsKeyExchange createECDHEKeyExchangeServer(int i2, TlsECConfig tlsECConfig);

    TlsKeyExchange createECDHKeyExchange(int i2);

    TlsKeyExchange createECDHanonKeyExchangeClient(int i2);

    TlsKeyExchange createECDHanonKeyExchangeServer(int i2, TlsECConfig tlsECConfig);

    TlsKeyExchange createPSKKeyExchangeClient(int i2, TlsPSKIdentity tlsPSKIdentity, TlsDHGroupVerifier tlsDHGroupVerifier);

    TlsKeyExchange createPSKKeyExchangeServer(int i2, TlsPSKIdentityManager tlsPSKIdentityManager, TlsDHConfig tlsDHConfig, TlsECConfig tlsECConfig);

    TlsKeyExchange createRSAKeyExchange(int i2);

    TlsKeyExchange createSRPKeyExchangeClient(int i2, TlsSRPIdentity tlsSRPIdentity, TlsSRPConfigVerifier tlsSRPConfigVerifier);

    TlsKeyExchange createSRPKeyExchangeServer(int i2, TlsSRPLoginParameters tlsSRPLoginParameters);
}
