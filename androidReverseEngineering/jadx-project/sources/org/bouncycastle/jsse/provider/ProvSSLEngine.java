package org.bouncycastle.jsse.provider;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.Principal;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import org.bouncycastle.jsse.BCApplicationProtocolSelector;
import org.bouncycastle.jsse.BCExtendedSSLSession;
import org.bouncycastle.jsse.BCSSLConnection;
import org.bouncycastle.jsse.BCSSLEngine;
import org.bouncycastle.jsse.BCSSLParameters;
import org.bouncycastle.jsse.BCX509Key;
import org.bouncycastle.tls.RecordPreview;
import org.bouncycastle.tls.SecurityParameters;
import org.bouncycastle.tls.TlsClientProtocol;
import org.bouncycastle.tls.TlsFatalAlert;
import org.bouncycastle.tls.TlsProtocol;
import org.bouncycastle.tls.TlsServerProtocol;

/* loaded from: classes.dex */
class ProvSSLEngine extends SSLEngine implements BCSSLEngine, ProvTlsManager {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getLogger(ProvSSLEngine.class.getName());
    protected boolean closedEarly;
    protected ProvSSLConnection connection;
    protected final ContextData contextData;
    protected SSLException deferredException;
    protected boolean enableSessionCreation;
    protected ProvSSLSessionHandshake handshakeSession;
    protected SSLEngineResult.HandshakeStatus handshakeStatus;
    protected boolean initialHandshakeBegun;
    protected TlsProtocol protocol;
    protected ProvTlsPeer protocolPeer;
    protected final ProvSSLParameters sslParameters;
    protected boolean useClientMode;
    protected boolean useClientModeSet;

    public ProvSSLEngine(ContextData contextData) {
        this(contextData, null, -1);
    }

    private RecordPreview getRecordPreview(ByteBuffer byteBuffer) {
        if (byteBuffer.remaining() < 5) {
            return null;
        }
        byte[] bArr = new byte[5];
        int position = byteBuffer.position();
        byteBuffer.get(bArr);
        byteBuffer.position(position);
        return this.protocol.previewInputRecord(bArr);
    }

    private int getTotalRemaining(ByteBuffer[] byteBufferArr, int i2, int i3, int i4) {
        int i5 = 0;
        for (int i6 = 0; i6 < i3; i6++) {
            int remaining = byteBufferArr[i2 + i6].remaining();
            if (remaining >= i4 - i5) {
                return i4;
            }
            i5 += remaining;
        }
        return i5;
    }

    private boolean hasInsufficientSpace(ByteBuffer[] byteBufferArr, int i2, int i3, int i4) {
        return getTotalRemaining(byteBufferArr, i2, i3, i4) < i4;
    }

    @Override // javax.net.ssl.SSLEngine
    public synchronized void beginHandshake() {
        SSLEngineResult.HandshakeStatus handshakeStatus;
        if (!this.useClientModeSet) {
            throw new IllegalStateException("Client/Server mode must be set before the handshake can begin");
        }
        if (this.closedEarly) {
            throw new SSLException("Connection is already closed");
        }
        if (this.initialHandshakeBegun) {
            throw new UnsupportedOperationException("Renegotiation not supported");
        }
        this.initialHandshakeBegun = true;
        try {
            if (this.useClientMode) {
                TlsClientProtocol tlsClientProtocol = new TlsClientProtocol();
                this.protocol = tlsClientProtocol;
                ProvTlsClient provTlsClient = new ProvTlsClient(this, this.sslParameters);
                this.protocolPeer = provTlsClient;
                tlsClientProtocol.connect(provTlsClient);
                handshakeStatus = SSLEngineResult.HandshakeStatus.NEED_WRAP;
            } else {
                TlsServerProtocol tlsServerProtocol = new TlsServerProtocol();
                this.protocol = tlsServerProtocol;
                ProvTlsServer provTlsServer = new ProvTlsServer(this, this.sslParameters);
                this.protocolPeer = provTlsServer;
                tlsServerProtocol.accept(provTlsServer);
                handshakeStatus = SSLEngineResult.HandshakeStatus.NEED_UNWRAP;
            }
            this.handshakeStatus = handshakeStatus;
        } catch (SSLException e2) {
            throw e2;
        } catch (IOException e3) {
            throw new SSLException(e3);
        }
    }

    @Override // org.bouncycastle.jsse.provider.ProvTlsManager
    public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) {
        try {
            this.contextData.getX509TrustManager().checkClientTrusted((X509Certificate[]) x509CertificateArr.clone(), str, this);
        } catch (CertificateException e2) {
            throw new TlsFatalAlert((short) 46, (Throwable) e2);
        }
    }

    @Override // org.bouncycastle.jsse.provider.ProvTlsManager
    public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) {
        try {
            this.contextData.getX509TrustManager().checkServerTrusted((X509Certificate[]) x509CertificateArr.clone(), str, this);
        } catch (CertificateException e2) {
            throw new TlsFatalAlert((short) 46, (Throwable) e2);
        }
    }

    @Override // org.bouncycastle.jsse.provider.ProvTlsManager
    public BCX509Key chooseClientKey(String[] strArr, Principal[] principalArr) {
        return getContextData().getX509KeyManager().chooseEngineClientKeyBC(strArr, (Principal[]) JsseUtils.clone(principalArr), this);
    }

    @Override // org.bouncycastle.jsse.provider.ProvTlsManager
    public BCX509Key chooseServerKey(String[] strArr, Principal[] principalArr) {
        return getContextData().getX509KeyManager().chooseEngineServerKeyBC(strArr, (Principal[]) JsseUtils.clone(principalArr), this);
    }

    @Override // javax.net.ssl.SSLEngine
    public synchronized void closeInbound() {
        if (!this.closedEarly) {
            TlsProtocol tlsProtocol = this.protocol;
            if (tlsProtocol == null) {
                this.closedEarly = true;
            } else {
                try {
                    tlsProtocol.closeInput();
                } catch (IOException e2) {
                    throw new SSLException(e2);
                }
            }
        }
    }

    @Override // javax.net.ssl.SSLEngine
    public synchronized void closeOutbound() {
        if (!this.closedEarly) {
            TlsProtocol tlsProtocol = this.protocol;
            if (tlsProtocol == null) {
                this.closedEarly = true;
            } else {
                try {
                    tlsProtocol.close();
                } catch (IOException e2) {
                    LOG.log(Level.WARNING, "Failed to close outbound", (Throwable) e2);
                }
            }
        }
    }

    @Override // javax.net.ssl.SSLEngine, org.bouncycastle.jsse.BCSSLEngine
    public synchronized String getApplicationProtocol() {
        ProvSSLConnection provSSLConnection;
        provSSLConnection = this.connection;
        return provSSLConnection == null ? null : provSSLConnection.getApplicationProtocol();
    }

    @Override // org.bouncycastle.jsse.BCSSLEngine
    public synchronized BCApplicationProtocolSelector<SSLEngine> getBCHandshakeApplicationProtocolSelector() {
        return this.sslParameters.getEngineAPSelector();
    }

    @Override // org.bouncycastle.jsse.BCSSLEngine
    public synchronized BCExtendedSSLSession getBCHandshakeSession() {
        return this.handshakeSession;
    }

    @Override // org.bouncycastle.jsse.BCSSLEngine
    public BCExtendedSSLSession getBCSession() {
        return getSessionImpl();
    }

    @Override // org.bouncycastle.jsse.BCSSLEngine
    public synchronized BCSSLConnection getConnection() {
        return this.connection;
    }

    @Override // org.bouncycastle.jsse.provider.ProvTlsManager
    public ContextData getContextData() {
        return this.contextData;
    }

    @Override // javax.net.ssl.SSLEngine
    public synchronized Runnable getDelegatedTask() {
        return null;
    }

    @Override // javax.net.ssl.SSLEngine, org.bouncycastle.jsse.provider.ProvTlsManager
    public synchronized boolean getEnableSessionCreation() {
        return this.enableSessionCreation;
    }

    @Override // javax.net.ssl.SSLEngine
    public synchronized String[] getEnabledCipherSuites() {
        return this.sslParameters.getCipherSuites();
    }

    @Override // javax.net.ssl.SSLEngine
    public synchronized String[] getEnabledProtocols() {
        return this.sslParameters.getProtocols();
    }

    @Override // javax.net.ssl.SSLEngine, org.bouncycastle.jsse.BCSSLEngine
    public synchronized String getHandshakeApplicationProtocol() {
        ProvSSLSessionHandshake provSSLSessionHandshake;
        provSSLSessionHandshake = this.handshakeSession;
        return provSSLSessionHandshake == null ? null : provSSLSessionHandshake.getApplicationProtocol();
    }

    @Override // javax.net.ssl.SSLEngine
    public synchronized SSLSession getHandshakeSession() {
        ProvSSLSessionHandshake provSSLSessionHandshake;
        provSSLSessionHandshake = this.handshakeSession;
        return provSSLSessionHandshake == null ? null : provSSLSessionHandshake.getExportSSLSession();
    }

    @Override // javax.net.ssl.SSLEngine
    public synchronized SSLEngineResult.HandshakeStatus getHandshakeStatus() {
        return this.handshakeStatus;
    }

    @Override // javax.net.ssl.SSLEngine
    public synchronized boolean getNeedClientAuth() {
        return this.sslParameters.getNeedClientAuth();
    }

    @Override // org.bouncycastle.jsse.BCSSLEngine
    public synchronized BCSSLParameters getParameters() {
        return SSLParametersUtil.getParameters(this.sslParameters);
    }

    @Override // javax.net.ssl.SSLEngine, org.bouncycastle.jsse.provider.ProvTlsManager
    public String getPeerHost() {
        return super.getPeerHost();
    }

    @Override // org.bouncycastle.jsse.provider.ProvTlsManager
    public String getPeerHostSNI() {
        return super.getPeerHost();
    }

    @Override // javax.net.ssl.SSLEngine, org.bouncycastle.jsse.provider.ProvTlsManager
    public int getPeerPort() {
        return super.getPeerPort();
    }

    @Override // javax.net.ssl.SSLEngine
    public synchronized SSLParameters getSSLParameters() {
        return SSLParametersUtil.getSSLParameters(this.sslParameters);
    }

    @Override // javax.net.ssl.SSLEngine
    public SSLSession getSession() {
        return getSessionImpl().getExportSSLSession();
    }

    public ProvSSLSession getSessionImpl() {
        ProvSSLConnection provSSLConnection = this.connection;
        return provSSLConnection == null ? ProvSSLSession.NULL_SESSION : provSSLConnection.getSession();
    }

    @Override // javax.net.ssl.SSLEngine
    public synchronized String[] getSupportedCipherSuites() {
        return this.contextData.getContext().getSupportedCipherSuites();
    }

    @Override // javax.net.ssl.SSLEngine
    public synchronized String[] getSupportedProtocols() {
        return this.contextData.getContext().getSupportedProtocols();
    }

    @Override // javax.net.ssl.SSLEngine
    public synchronized boolean getUseClientMode() {
        return this.useClientMode;
    }

    @Override // javax.net.ssl.SSLEngine
    public synchronized boolean getWantClientAuth() {
        return this.sslParameters.getWantClientAuth();
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x000d, code lost:
    
        if (r0.isClosed() != false) goto L11;
     */
    @Override // javax.net.ssl.SSLEngine
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public synchronized boolean isInboundDone() {
        boolean z2;
        if (!this.closedEarly) {
            TlsProtocol tlsProtocol = this.protocol;
            if (tlsProtocol != null) {
            }
            z2 = false;
        }
        z2 = true;
        return z2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0016, code lost:
    
        if (r2.protocol.getAvailableOutputBytes() < 1) goto L13;
     */
    @Override // javax.net.ssl.SSLEngine
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public synchronized boolean isOutboundDone() {
        boolean z2;
        z2 = true;
        if (!this.closedEarly) {
            TlsProtocol tlsProtocol = this.protocol;
            if (tlsProtocol != null && tlsProtocol.isClosed()) {
            }
            z2 = false;
        }
        return z2;
    }

    @Override // org.bouncycastle.jsse.provider.ProvTlsManager
    public synchronized void notifyHandshakeComplete(ProvSSLConnection provSSLConnection) {
        ProvSSLSessionHandshake provSSLSessionHandshake = this.handshakeSession;
        if (provSSLSessionHandshake != null) {
            if (!provSSLSessionHandshake.isValid()) {
                provSSLConnection.getSession().invalidate();
            }
            this.handshakeSession.getJsseSecurityParameters().clear();
        }
        this.handshakeSession = null;
        this.connection = provSSLConnection;
    }

    @Override // org.bouncycastle.jsse.provider.ProvTlsManager
    public synchronized void notifyHandshakeSession(ProvSSLSessionContext provSSLSessionContext, SecurityParameters securityParameters, JsseSecurityParameters jsseSecurityParameters, ProvSSLSession provSSLSession) {
        String peerHost = getPeerHost();
        int peerPort = getPeerPort();
        if (provSSLSession != null) {
            this.handshakeSession = new ProvSSLSessionResumed(provSSLSessionContext, peerHost, peerPort, securityParameters, jsseSecurityParameters, provSSLSession.getTlsSession(), provSSLSession.getJsseSessionParameters());
        } else {
            this.handshakeSession = new ProvSSLSessionHandshake(provSSLSessionContext, peerHost, peerPort, securityParameters, jsseSecurityParameters);
        }
    }

    @Override // org.bouncycastle.jsse.provider.ProvTlsManager
    public synchronized String selectApplicationProtocol(List<String> list) {
        return this.sslParameters.getEngineAPSelector().select(this, list);
    }

    @Override // org.bouncycastle.jsse.BCSSLEngine
    public synchronized void setBCHandshakeApplicationProtocolSelector(BCApplicationProtocolSelector<SSLEngine> bCApplicationProtocolSelector) {
        this.sslParameters.setEngineAPSelector(bCApplicationProtocolSelector);
    }

    @Override // org.bouncycastle.jsse.BCSSLEngine
    public synchronized void setBCSessionToResume(BCExtendedSSLSession bCExtendedSSLSession) {
        try {
            if (bCExtendedSSLSession == null) {
                throw new NullPointerException("'session' cannot be null");
            }
            if (!(bCExtendedSSLSession instanceof ProvSSLSession)) {
                throw new IllegalArgumentException("Session-to-resume must be a session returned from 'getBCSession'");
            }
            if (this.initialHandshakeBegun) {
                throw new IllegalArgumentException("Session-to-resume cannot be set after the handshake has begun");
            }
            this.sslParameters.setSessionToResume((ProvSSLSession) bCExtendedSSLSession);
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // javax.net.ssl.SSLEngine
    public synchronized void setEnableSessionCreation(boolean z2) {
        this.enableSessionCreation = z2;
    }

    @Override // javax.net.ssl.SSLEngine
    public synchronized void setEnabledCipherSuites(String[] strArr) {
        this.sslParameters.setCipherSuites(strArr);
    }

    @Override // javax.net.ssl.SSLEngine
    public synchronized void setEnabledProtocols(String[] strArr) {
        this.sslParameters.setProtocols(strArr);
    }

    @Override // javax.net.ssl.SSLEngine
    public synchronized void setNeedClientAuth(boolean z2) {
        this.sslParameters.setNeedClientAuth(z2);
    }

    @Override // org.bouncycastle.jsse.BCSSLEngine
    public synchronized void setParameters(BCSSLParameters bCSSLParameters) {
        SSLParametersUtil.setParameters(this.sslParameters, bCSSLParameters);
    }

    @Override // javax.net.ssl.SSLEngine
    public synchronized void setSSLParameters(SSLParameters sSLParameters) {
        SSLParametersUtil.setSSLParameters(this.sslParameters, sSLParameters);
    }

    @Override // javax.net.ssl.SSLEngine
    public synchronized void setUseClientMode(boolean z2) {
        if (this.initialHandshakeBegun) {
            throw new IllegalArgumentException("Client/Server mode cannot be changed after the handshake has begun");
        }
        if (this.useClientMode != z2) {
            this.contextData.getContext().updateDefaultSSLParameters(this.sslParameters, z2);
            this.useClientMode = z2;
        }
        this.useClientModeSet = true;
    }

    @Override // javax.net.ssl.SSLEngine
    public synchronized void setWantClientAuth(boolean z2) {
        this.sslParameters.setWantClientAuth(z2);
    }

    @Override // javax.net.ssl.SSLEngine
    public synchronized SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer[] byteBufferArr, int i2, int i3) {
        int i4;
        SSLEngineResult.Status status;
        if (!this.initialHandshakeBegun) {
            beginHandshake();
        }
        SSLEngineResult.Status status2 = SSLEngineResult.Status.OK;
        int i5 = 0;
        if (this.protocol.isClosed()) {
            status = SSLEngineResult.Status.CLOSED;
            i4 = 0;
        } else {
            try {
                RecordPreview recordPreview = getRecordPreview(byteBuffer);
                if (recordPreview != null && byteBuffer.remaining() >= recordPreview.getRecordSize()) {
                    if (hasInsufficientSpace(byteBufferArr, i2, i3, recordPreview.getContentLimit())) {
                        status2 = SSLEngineResult.Status.BUFFER_OVERFLOW;
                        i4 = 0;
                        status = status2;
                    } else {
                        int recordSize = recordPreview.getRecordSize();
                        byte[] bArr = new byte[recordSize];
                        byteBuffer.get(bArr);
                        this.protocol.offerInput(bArr, 0, recordSize);
                        int i6 = recordSize + 0;
                        try {
                            int availableInputBytes = this.protocol.getAvailableInputBytes();
                            i4 = 0;
                            for (int i7 = 0; i7 < i3 && availableInputBytes > 0; i7++) {
                                try {
                                    ByteBuffer byteBuffer2 = byteBufferArr[i2 + i7];
                                    int min = Math.min(byteBuffer2.remaining(), availableInputBytes);
                                    if (min > 0) {
                                        byte[] bArr2 = new byte[min];
                                        this.protocol.readInput(bArr2, 0, min);
                                        byteBuffer2.put(bArr2);
                                        i4 += min;
                                        availableInputBytes -= min;
                                    }
                                } catch (IOException e2) {
                                    e = e2;
                                    i5 = i6;
                                    if (this.handshakeStatus != SSLEngineResult.HandshakeStatus.NEED_UNWRAP) {
                                        throw new SSLException(e);
                                    }
                                    if (this.deferredException == null) {
                                        this.deferredException = new SSLException(e);
                                    }
                                    this.handshakeStatus = SSLEngineResult.HandshakeStatus.NEED_WRAP;
                                    return new SSLEngineResult(SSLEngineResult.Status.OK, SSLEngineResult.HandshakeStatus.NEED_WRAP, i5, i4);
                                }
                            }
                            if (availableInputBytes != 0) {
                                throw new TlsFatalAlert((short) 22);
                            }
                            i5 = i6;
                            status = status2;
                        } catch (IOException e3) {
                            e = e3;
                            i4 = 0;
                        }
                    }
                }
                status2 = SSLEngineResult.Status.BUFFER_UNDERFLOW;
                i4 = 0;
                status = status2;
            } catch (IOException e4) {
                e = e4;
                i4 = 0;
            }
        }
        SSLEngineResult.HandshakeStatus handshakeStatus = this.handshakeStatus;
        if (handshakeStatus == SSLEngineResult.HandshakeStatus.NEED_UNWRAP) {
            if (this.protocol.getAvailableOutputBytes() > 0) {
                handshakeStatus = SSLEngineResult.HandshakeStatus.NEED_WRAP;
            } else if (this.protocolPeer.isHandshakeComplete()) {
                this.handshakeStatus = SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
                handshakeStatus = SSLEngineResult.HandshakeStatus.FINISHED;
            } else if (this.protocol.isClosed()) {
                handshakeStatus = SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
            }
            this.handshakeStatus = handshakeStatus;
        }
        return new SSLEngineResult(status, handshakeStatus, i5, i4);
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0082 A[Catch: all -> 0x00ce, TryCatch #1 {, blocks: (B:3:0x0001, B:5:0x0005, B:7:0x0009, B:8:0x000c, B:10:0x0015, B:12:0x001d, B:13:0x0020, B:16:0x0029, B:18:0x0035, B:20:0x0049, B:21:0x004d, B:24:0x0055, B:26:0x0065, B:28:0x0069, B:31:0x006c, B:32:0x007a, B:34:0x0082, B:36:0x008c, B:37:0x0099, B:38:0x009b, B:42:0x00a4, B:44:0x00ac, B:45:0x00b3, B:47:0x00bb, B:48:0x00bd, B:49:0x00c0, B:50:0x00c3, B:56:0x0073, B:57:0x0078, B:60:0x00cb, B:61:0x00cd), top: B:2:0x0001, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00ac A[Catch: all -> 0x00ce, TryCatch #1 {, blocks: (B:3:0x0001, B:5:0x0005, B:7:0x0009, B:8:0x000c, B:10:0x0015, B:12:0x001d, B:13:0x0020, B:16:0x0029, B:18:0x0035, B:20:0x0049, B:21:0x004d, B:24:0x0055, B:26:0x0065, B:28:0x0069, B:31:0x006c, B:32:0x007a, B:34:0x0082, B:36:0x008c, B:37:0x0099, B:38:0x009b, B:42:0x00a4, B:44:0x00ac, B:45:0x00b3, B:47:0x00bb, B:48:0x00bd, B:49:0x00c0, B:50:0x00c3, B:56:0x0073, B:57:0x0078, B:60:0x00cb, B:61:0x00cd), top: B:2:0x0001, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00b3 A[Catch: all -> 0x00ce, TryCatch #1 {, blocks: (B:3:0x0001, B:5:0x0005, B:7:0x0009, B:8:0x000c, B:10:0x0015, B:12:0x001d, B:13:0x0020, B:16:0x0029, B:18:0x0035, B:20:0x0049, B:21:0x004d, B:24:0x0055, B:26:0x0065, B:28:0x0069, B:31:0x006c, B:32:0x007a, B:34:0x0082, B:36:0x008c, B:37:0x0099, B:38:0x009b, B:42:0x00a4, B:44:0x00ac, B:45:0x00b3, B:47:0x00bb, B:48:0x00bd, B:49:0x00c0, B:50:0x00c3, B:56:0x0073, B:57:0x0078, B:60:0x00cb, B:61:0x00cd), top: B:2:0x0001, inners: #0 }] */
    @Override // javax.net.ssl.SSLEngine
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public synchronized SSLEngineResult wrap(ByteBuffer[] byteBufferArr, int i2, int i3, ByteBuffer byteBuffer) {
        SSLEngineResult.Status status;
        int i4;
        int i5;
        int availableOutputBytes;
        SSLEngineResult.HandshakeStatus handshakeStatus;
        SSLException sSLException = this.deferredException;
        if (sSLException != null) {
            this.deferredException = null;
            throw sSLException;
        }
        if (!this.initialHandshakeBegun) {
            beginHandshake();
        }
        status = SSLEngineResult.Status.OK;
        i4 = 0;
        if (this.handshakeStatus == SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING) {
            if (this.protocol.isClosed()) {
                status = SSLEngineResult.Status.CLOSED;
            } else if (this.protocol.getAvailableOutputBytes() <= 0) {
                try {
                    int totalRemaining = getTotalRemaining(byteBufferArr, i2, i3, this.protocol.getApplicationDataLimit());
                    if (totalRemaining > 0) {
                        RecordPreview previewOutputRecord = this.protocol.previewOutputRecord(totalRemaining);
                        int contentLimit = previewOutputRecord.getContentLimit();
                        if (byteBuffer.remaining() < previewOutputRecord.getRecordSize()) {
                            status = SSLEngineResult.Status.BUFFER_OVERFLOW;
                        } else {
                            byte[] bArr = new byte[contentLimit];
                            i5 = 0;
                            for (int i6 = 0; i6 < i3 && i5 < contentLimit; i6++) {
                                ByteBuffer byteBuffer2 = byteBufferArr[i2 + i6];
                                int min = Math.min(byteBuffer2.remaining(), contentLimit - i5);
                                if (min > 0) {
                                    byteBuffer2.get(bArr, i5, min);
                                    i5 += min;
                                }
                            }
                            this.protocol.writeApplicationData(bArr, 0, i5);
                            availableOutputBytes = this.protocol.getAvailableOutputBytes();
                            if (availableOutputBytes > 0) {
                                int min2 = Math.min(byteBuffer.remaining(), availableOutputBytes);
                                if (min2 > 0) {
                                    byte[] bArr2 = new byte[min2];
                                    this.protocol.readOutput(bArr2, 0, min2);
                                    byteBuffer.put(bArr2);
                                    i4 = 0 + min2;
                                    availableOutputBytes -= min2;
                                } else {
                                    status = SSLEngineResult.Status.BUFFER_OVERFLOW;
                                }
                            }
                            handshakeStatus = this.handshakeStatus;
                            if (handshakeStatus == SSLEngineResult.HandshakeStatus.NEED_WRAP && availableOutputBytes <= 0) {
                                if (this.protocolPeer.isHandshakeComplete()) {
                                    handshakeStatus = this.protocol.isClosed() ? SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING : SSLEngineResult.HandshakeStatus.NEED_UNWRAP;
                                    this.handshakeStatus = handshakeStatus;
                                } else {
                                    this.handshakeStatus = SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
                                    handshakeStatus = SSLEngineResult.HandshakeStatus.FINISHED;
                                }
                            }
                        }
                    }
                } catch (IOException e2) {
                    throw new SSLException(e2);
                }
            }
        }
        i5 = 0;
        availableOutputBytes = this.protocol.getAvailableOutputBytes();
        if (availableOutputBytes > 0) {
        }
        handshakeStatus = this.handshakeStatus;
        if (handshakeStatus == SSLEngineResult.HandshakeStatus.NEED_WRAP) {
            if (this.protocolPeer.isHandshakeComplete()) {
            }
        }
        return new SSLEngineResult(status, handshakeStatus, i5, i4);
    }

    public ProvSSLEngine(ContextData contextData, String str, int i2) {
        super(str, i2);
        this.enableSessionCreation = true;
        this.useClientMode = true;
        this.useClientModeSet = false;
        this.closedEarly = false;
        this.initialHandshakeBegun = false;
        this.handshakeStatus = SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
        this.protocol = null;
        this.protocolPeer = null;
        this.connection = null;
        this.handshakeSession = null;
        this.deferredException = null;
        this.contextData = contextData;
        this.sslParameters = contextData.getContext().getDefaultSSLParameters(this.useClientMode);
    }
}
