package org.bouncycastle.tls;

import org.bouncycastle.tls.crypto.TlsCrypto;

/* loaded from: classes.dex */
public abstract class AbstractTlsPeer implements TlsPeer {
    private volatile TlsCloseable closeHandle;
    private final TlsCrypto crypto;

    public AbstractTlsPeer(TlsCrypto tlsCrypto) {
        this.crypto = tlsCrypto;
    }

    @Override // org.bouncycastle.tls.TlsPeer
    public boolean allowLegacyResumption() {
        return false;
    }

    @Override // org.bouncycastle.tls.TlsPeer
    public void cancel() {
        TlsCloseable tlsCloseable = this.closeHandle;
        if (tlsCloseable != null) {
            tlsCloseable.close();
        }
    }

    @Override // org.bouncycastle.tls.TlsPeer
    public TlsCrypto getCrypto() {
        return this.crypto;
    }

    @Override // org.bouncycastle.tls.TlsPeer
    public int getHandshakeTimeoutMillis() {
        return 0;
    }

    @Override // org.bouncycastle.tls.TlsPeer
    public TlsHeartbeat getHeartbeat() {
        return null;
    }

    @Override // org.bouncycastle.tls.TlsPeer
    public short getHeartbeatPolicy() {
        return (short) 2;
    }

    @Override // org.bouncycastle.tls.TlsPeer
    public TlsKeyExchangeFactory getKeyExchangeFactory() {
        return new DefaultTlsKeyExchangeFactory();
    }

    @Override // org.bouncycastle.tls.TlsPeer
    public int getMaxCertificateChainLength() {
        return 10;
    }

    @Override // org.bouncycastle.tls.TlsPeer
    public int getMaxHandshakeMessageSize() {
        return 32768;
    }

    @Override // org.bouncycastle.tls.TlsPeer
    public short[] getPskKeyExchangeModes() {
        return new short[]{1};
    }

    @Override // org.bouncycastle.tls.TlsPeer
    public int getRenegotiationPolicy() {
        return 0;
    }

    public abstract int[] getSupportedCipherSuites();

    public ProtocolVersion[] getSupportedVersions() {
        return ProtocolVersion.TLSv12.downTo(ProtocolVersion.TLSv10);
    }

    @Override // org.bouncycastle.tls.TlsPeer
    public void notifyAlertRaised(short s2, short s3, String str, Throwable th) {
    }

    @Override // org.bouncycastle.tls.TlsPeer
    public void notifyAlertReceived(short s2, short s3) {
    }

    @Override // org.bouncycastle.tls.TlsPeer
    public void notifyCloseHandle(TlsCloseable tlsCloseable) {
        this.closeHandle = tlsCloseable;
    }

    @Override // org.bouncycastle.tls.TlsPeer
    public void notifyHandshakeBeginning() {
    }

    @Override // org.bouncycastle.tls.TlsPeer
    public void notifyHandshakeComplete() {
    }

    @Override // org.bouncycastle.tls.TlsPeer
    public void notifySecureRenegotiation(boolean z2) {
        if (!z2) {
            throw new TlsFatalAlert((short) 40);
        }
    }

    @Override // org.bouncycastle.tls.TlsPeer
    public boolean requiresCloseNotify() {
        return true;
    }

    @Override // org.bouncycastle.tls.TlsPeer
    public boolean requiresExtendedMasterSecret() {
        return false;
    }

    @Override // org.bouncycastle.tls.TlsPeer
    public boolean shouldCheckSigAlgOfPeerCerts() {
        return true;
    }

    @Override // org.bouncycastle.tls.TlsPeer
    public boolean shouldUseExtendedMasterSecret() {
        return true;
    }

    @Override // org.bouncycastle.tls.TlsPeer
    public boolean shouldUseExtendedPadding() {
        return false;
    }

    @Override // org.bouncycastle.tls.TlsPeer
    public boolean shouldUseGMTUnixTime() {
        return false;
    }
}
