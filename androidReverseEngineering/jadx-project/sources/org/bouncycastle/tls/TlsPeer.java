package org.bouncycastle.tls;

import org.bouncycastle.tls.crypto.TlsCrypto;

/* loaded from: classes.dex */
public interface TlsPeer {
    boolean allowLegacyResumption();

    void cancel();

    int[] getCipherSuites();

    TlsCrypto getCrypto();

    int getHandshakeTimeoutMillis();

    TlsHeartbeat getHeartbeat();

    short getHeartbeatPolicy();

    TlsKeyExchangeFactory getKeyExchangeFactory();

    int getMaxCertificateChainLength();

    int getMaxHandshakeMessageSize();

    ProtocolVersion[] getProtocolVersions();

    short[] getPskKeyExchangeModes();

    int getRenegotiationPolicy();

    void notifyAlertRaised(short s2, short s3, String str, Throwable th);

    void notifyAlertReceived(short s2, short s3);

    void notifyCloseHandle(TlsCloseable tlsCloseable);

    void notifyHandshakeBeginning();

    void notifyHandshakeComplete();

    void notifySecureRenegotiation(boolean z2);

    boolean requiresCloseNotify();

    boolean requiresExtendedMasterSecret();

    boolean shouldCheckSigAlgOfPeerCerts();

    boolean shouldUseExtendedMasterSecret();

    boolean shouldUseExtendedPadding();

    boolean shouldUseGMTUnixTime();
}
