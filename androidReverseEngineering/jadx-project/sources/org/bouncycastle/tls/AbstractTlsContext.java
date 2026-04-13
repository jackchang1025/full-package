package org.bouncycastle.tls;

import java.io.IOException;
import org.bouncycastle.tls.crypto.TlsCrypto;
import org.bouncycastle.tls.crypto.TlsCryptoUtils;
import org.bouncycastle.tls.crypto.TlsNonceGenerator;
import org.bouncycastle.tls.crypto.TlsSecret;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Pack;
import org.bouncycastle.util.Times;

/* loaded from: classes.dex */
abstract class AbstractTlsContext implements TlsContext {
    private static long counter = Times.nanoTime();
    private int connectionEnd;
    private TlsCrypto crypto;
    private TlsNonceGenerator nonceGenerator;
    private SecurityParameters securityParametersHandshake = null;
    private SecurityParameters securityParametersConnection = null;
    private ProtocolVersion[] clientSupportedVersions = null;
    private ProtocolVersion clientVersion = null;
    private ProtocolVersion rsaPreMasterSecretVersion = null;
    private TlsSession session = null;
    private Object userObject = null;

    public AbstractTlsContext(TlsCrypto tlsCrypto, int i2) {
        this.crypto = tlsCrypto;
        this.connectionEnd = i2;
        this.nonceGenerator = createNonceGenerator(tlsCrypto, i2);
    }

    private static TlsNonceGenerator createNonceGenerator(TlsCrypto tlsCrypto, int i2) {
        byte[] bArr = new byte[16];
        Pack.longToBigEndian(nextCounterValue(), bArr, 0);
        Pack.longToBigEndian(Times.nanoTime(), bArr, 8);
        byte b = (byte) (bArr[0] & Byte.MAX_VALUE);
        bArr[0] = b;
        bArr[0] = (byte) (((byte) (i2 << 7)) | b);
        return tlsCrypto.createNonceGenerator(bArr);
    }

    private static synchronized long nextCounterValue() {
        long j2;
        synchronized (AbstractTlsContext.class) {
            j2 = counter + 1;
            counter = j2;
        }
        return j2;
    }

    public TlsSecret checkEarlyExportSecret(TlsSecret tlsSecret) {
        if (tlsSecret != null) {
            return tlsSecret;
        }
        throw new IllegalStateException("Export of early key material not available for this handshake");
    }

    public TlsSecret checkExportSecret(TlsSecret tlsSecret) {
        if (tlsSecret != null) {
            return tlsSecret;
        }
        throw new IllegalStateException("Export of key material only available from notifyHandshakeComplete()");
    }

    @Override // org.bouncycastle.tls.TlsContext
    public byte[] exportChannelBinding(int i2) {
        SecurityParameters securityParametersConnection = getSecurityParametersConnection();
        if (securityParametersConnection == null) {
            throw new IllegalStateException("Export of channel bindings unavailable before handshake completion");
        }
        if (TlsUtils.isTLSv13(securityParametersConnection.getNegotiatedVersion())) {
            return null;
        }
        if (i2 != 0) {
            if (i2 == 1) {
                return Arrays.clone(securityParametersConnection.getTLSUnique());
            }
            throw new UnsupportedOperationException();
        }
        byte[] tLSServerEndPoint = securityParametersConnection.getTLSServerEndPoint();
        if (TlsUtils.isNullOrEmpty(tLSServerEndPoint)) {
            return null;
        }
        return Arrays.clone(tLSServerEndPoint);
    }

    @Override // org.bouncycastle.tls.TlsContext
    public byte[] exportEarlyKeyingMaterial(String str, byte[] bArr, int i2) {
        SecurityParameters securityParametersHandshake = getSecurityParametersHandshake();
        if (securityParametersHandshake != null) {
            return exportKeyingMaterial13(checkEarlyExportSecret(securityParametersHandshake.getEarlyExporterMasterSecret()), securityParametersHandshake.getPRFCryptoHashAlgorithm(), str, bArr, i2);
        }
        throw new IllegalStateException("Export of early key material only available during handshake");
    }

    @Override // org.bouncycastle.tls.TlsContext
    public byte[] exportKeyingMaterial(String str, byte[] bArr, int i2) {
        SecurityParameters securityParametersConnection = getSecurityParametersConnection();
        if (securityParametersConnection == null) {
            throw new IllegalStateException("Export of key material unavailable before handshake completion");
        }
        if (!securityParametersConnection.isExtendedMasterSecret()) {
            throw new IllegalStateException("Export of key material requires extended_master_secret");
        }
        if (TlsUtils.isTLSv13(securityParametersConnection.getNegotiatedVersion())) {
            return exportKeyingMaterial13(checkExportSecret(securityParametersConnection.getExporterMasterSecret()), securityParametersConnection.getPRFCryptoHashAlgorithm(), str, bArr, i2);
        }
        return TlsUtils.PRF(securityParametersConnection, checkExportSecret(securityParametersConnection.getMasterSecret()), str, TlsUtils.calculateExporterSeed(securityParametersConnection, bArr), i2).extract();
    }

    public byte[] exportKeyingMaterial13(TlsSecret tlsSecret, int i2, String str, byte[] bArr, int i3) {
        if (bArr == null) {
            bArr = TlsUtils.EMPTY_BYTES;
        } else if (!TlsUtils.isValidUint16(bArr.length)) {
            throw new IllegalArgumentException("'context' must have length less than 2^16 (or be null)");
        }
        try {
            return TlsCryptoUtils.hkdfExpandLabel(tlsSecret, i2, str, bArr, i3).extract();
        } catch (IOException e2) {
            throw new RuntimeException(e2);
        }
    }

    @Override // org.bouncycastle.tls.TlsContext
    public ProtocolVersion[] getClientSupportedVersions() {
        return this.clientSupportedVersions;
    }

    @Override // org.bouncycastle.tls.TlsContext
    public ProtocolVersion getClientVersion() {
        return this.clientVersion;
    }

    @Override // org.bouncycastle.tls.TlsContext
    public TlsCrypto getCrypto() {
        return this.crypto;
    }

    @Override // org.bouncycastle.tls.TlsContext
    public TlsNonceGenerator getNonceGenerator() {
        return this.nonceGenerator;
    }

    @Override // org.bouncycastle.tls.TlsContext
    public ProtocolVersion getRSAPreMasterSecretVersion() {
        return this.rsaPreMasterSecretVersion;
    }

    @Override // org.bouncycastle.tls.TlsContext
    public TlsSession getResumableSession() {
        TlsSession session = getSession();
        if (session == null || !session.isResumable()) {
            return null;
        }
        return session;
    }

    @Override // org.bouncycastle.tls.TlsContext
    public synchronized SecurityParameters getSecurityParameters() {
        SecurityParameters securityParameters;
        securityParameters = this.securityParametersHandshake;
        if (securityParameters == null) {
            securityParameters = this.securityParametersConnection;
        }
        return securityParameters;
    }

    @Override // org.bouncycastle.tls.TlsContext
    public synchronized SecurityParameters getSecurityParametersConnection() {
        return this.securityParametersConnection;
    }

    @Override // org.bouncycastle.tls.TlsContext
    public synchronized SecurityParameters getSecurityParametersHandshake() {
        return this.securityParametersHandshake;
    }

    @Override // org.bouncycastle.tls.TlsContext
    public ProtocolVersion getServerVersion() {
        return getSecurityParameters().getNegotiatedVersion();
    }

    @Override // org.bouncycastle.tls.TlsContext
    public TlsSession getSession() {
        return this.session;
    }

    @Override // org.bouncycastle.tls.TlsContext
    public Object getUserObject() {
        return this.userObject;
    }

    public void handshakeBeginning(TlsPeer tlsPeer) {
        synchronized (this) {
            if (this.securityParametersHandshake != null) {
                throw new TlsFatalAlert((short) 80, "Handshake already started");
            }
            SecurityParameters securityParameters = new SecurityParameters();
            this.securityParametersHandshake = securityParameters;
            securityParameters.entity = this.connectionEnd;
            SecurityParameters securityParameters2 = this.securityParametersConnection;
            if (securityParameters2 != null) {
                securityParameters.renegotiating = true;
                securityParameters.secureRenegotiation = securityParameters2.isSecureRenegotiation();
                this.securityParametersHandshake.negotiatedVersion = this.securityParametersConnection.getNegotiatedVersion();
            }
        }
        tlsPeer.notifyHandshakeBeginning();
    }

    public void handshakeComplete(TlsPeer tlsPeer, TlsSession tlsSession) {
        synchronized (this) {
            SecurityParameters securityParameters = this.securityParametersHandshake;
            if (securityParameters == null) {
                throw new TlsFatalAlert((short) 80);
            }
            this.session = tlsSession;
            this.securityParametersConnection = securityParameters;
            this.securityParametersHandshake = null;
        }
        tlsPeer.notifyHandshakeComplete();
    }

    public synchronized boolean isConnected() {
        return this.securityParametersConnection != null;
    }

    public synchronized boolean isHandshaking() {
        return this.securityParametersHandshake != null;
    }

    public void setClientSupportedVersions(ProtocolVersion[] protocolVersionArr) {
        this.clientSupportedVersions = protocolVersionArr;
    }

    public void setClientVersion(ProtocolVersion protocolVersion) {
        this.clientVersion = protocolVersion;
    }

    public void setRSAPreMasterSecretVersion(ProtocolVersion protocolVersion) {
        this.rsaPreMasterSecretVersion = protocolVersion;
    }

    @Override // org.bouncycastle.tls.TlsContext
    public void setUserObject(Object obj) {
        this.userObject = obj;
    }
}
