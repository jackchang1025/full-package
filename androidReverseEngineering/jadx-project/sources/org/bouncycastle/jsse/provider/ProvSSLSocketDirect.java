package org.bouncycastle.jsse.provider;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.security.Principal;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import org.bouncycastle.jsse.BCApplicationProtocolSelector;
import org.bouncycastle.jsse.BCExtendedSSLSession;
import org.bouncycastle.jsse.BCSSLConnection;
import org.bouncycastle.jsse.BCSSLParameters;
import org.bouncycastle.jsse.BCX509Key;
import org.bouncycastle.tls.SecurityParameters;
import org.bouncycastle.tls.TlsFatalAlert;
import org.bouncycastle.tls.TlsProtocol;

/* loaded from: classes.dex */
class ProvSSLSocketDirect extends ProvSSLSocketBase implements ProvTlsManager {
    private static final Logger LOG = Logger.getLogger(ProvSSLSocketDirect.class.getName());
    protected final AppDataInput appDataIn;
    protected final AppDataOutput appDataOut;
    protected ProvSSLConnection connection;
    protected final ContextData contextData;
    protected boolean enableSessionCreation;
    protected ProvSSLSessionHandshake handshakeSession;
    protected String peerHost;
    protected String peerHostSNI;
    protected TlsProtocol protocol;
    protected ProvTlsPeer protocolPeer;
    protected final ProvSSLParameters sslParameters;
    protected boolean useClientMode;

    public class AppDataInput extends InputStream {
        public AppDataInput() {
        }

        @Override // java.io.InputStream
        public int available() {
            int applicationDataAvailable;
            synchronized (ProvSSLSocketDirect.this) {
                TlsProtocol tlsProtocol = ProvSSLSocketDirect.this.protocol;
                applicationDataAvailable = tlsProtocol == null ? 0 : tlsProtocol.applicationDataAvailable();
            }
            return applicationDataAvailable;
        }

        @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            ProvSSLSocketDirect.this.close();
        }

        @Override // java.io.InputStream
        public int read() {
            byte[] bArr = new byte[1];
            if (read(bArr, 0, 1) <= 0) {
                return -1;
            }
            return bArr[0] & 255;
        }

        @Override // java.io.InputStream
        public int read(byte[] bArr, int i2, int i3) {
            if (i3 < 1) {
                return 0;
            }
            ProvSSLSocketDirect.this.handshakeIfNecessary(true);
            return ProvSSLSocketDirect.this.protocol.readApplicationData(bArr, i2, i3);
        }
    }

    public class AppDataOutput extends OutputStream {
        public AppDataOutput() {
        }

        @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            ProvSSLSocketDirect.this.close();
        }

        @Override // java.io.OutputStream
        public void write(int i2) {
            write(new byte[]{(byte) i2}, 0, 1);
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i2, int i3) {
            if (i3 > 0) {
                ProvSSLSocketDirect.this.handshakeIfNecessary(true);
                ProvSSLSocketDirect.this.protocol.writeApplicationData(bArr, i2, i3);
            }
        }
    }

    public ProvSSLSocketDirect(ContextData contextData) {
        this.appDataIn = new AppDataInput();
        this.appDataOut = new AppDataOutput();
        this.peerHost = null;
        this.peerHostSNI = null;
        this.enableSessionCreation = true;
        this.useClientMode = true;
        this.protocol = null;
        this.protocolPeer = null;
        this.connection = null;
        this.handshakeSession = null;
        this.contextData = contextData;
        this.sslParameters = contextData.getContext().getDefaultSSLParameters(this.useClientMode);
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
        return getContextData().getX509KeyManager().chooseClientKeyBC(strArr, (Principal[]) JsseUtils.clone(principalArr), this);
    }

    @Override // org.bouncycastle.jsse.provider.ProvTlsManager
    public BCX509Key chooseServerKey(String[] strArr, Principal[] principalArr) {
        return getContextData().getX509KeyManager().chooseServerKeyBC(strArr, (Principal[]) JsseUtils.clone(principalArr), this);
    }

    @Override // java.net.Socket, java.io.Closeable, java.lang.AutoCloseable
    public synchronized void close() {
        TlsProtocol tlsProtocol = this.protocol;
        if (tlsProtocol == null) {
            closeSocket();
        } else {
            tlsProtocol.close();
        }
    }

    @Override // java.net.Socket
    public void connect(SocketAddress socketAddress, int i2) {
        if (!(socketAddress instanceof InetSocketAddress)) {
            throw new SocketException("Only InetSocketAddress is supported.");
        }
        super.connect(socketAddress, i2);
        notifyConnected();
    }

    public void finalize() {
        try {
            try {
                try {
                    close();
                } catch (IOException unused) {
                    super.close();
                }
            } catch (IOException unused2) {
            }
        } finally {
            super.finalize();
        }
    }

    @Override // javax.net.ssl.SSLSocket, org.bouncycastle.jsse.BCSSLSocket
    public synchronized String getApplicationProtocol() {
        ProvSSLConnection provSSLConnection;
        provSSLConnection = this.connection;
        return provSSLConnection == null ? null : provSSLConnection.getApplicationProtocol();
    }

    @Override // org.bouncycastle.jsse.BCSSLSocket
    public synchronized BCApplicationProtocolSelector<SSLSocket> getBCHandshakeApplicationProtocolSelector() {
        return this.sslParameters.getSocketAPSelector();
    }

    @Override // org.bouncycastle.jsse.BCSSLSocket
    public synchronized BCExtendedSSLSession getBCHandshakeSession() {
        return this.handshakeSession;
    }

    @Override // org.bouncycastle.jsse.BCSSLSocket
    public BCExtendedSSLSession getBCSession() {
        return getSessionImpl();
    }

    @Override // org.bouncycastle.jsse.BCSSLSocket
    public synchronized BCSSLConnection getConnection() {
        try {
            handshakeIfNecessary(false);
        } catch (IOException e2) {
            LOG.log(Level.FINE, "Failed to establish connection", (Throwable) e2);
        }
        return this.connection;
    }

    @Override // org.bouncycastle.jsse.provider.ProvTlsManager
    public ContextData getContextData() {
        return this.contextData;
    }

    @Override // javax.net.ssl.SSLSocket, org.bouncycastle.jsse.provider.ProvTlsManager
    public synchronized boolean getEnableSessionCreation() {
        return this.enableSessionCreation;
    }

    @Override // javax.net.ssl.SSLSocket
    public synchronized String[] getEnabledCipherSuites() {
        return this.sslParameters.getCipherSuites();
    }

    @Override // javax.net.ssl.SSLSocket
    public synchronized String[] getEnabledProtocols() {
        return this.sslParameters.getProtocols();
    }

    @Override // javax.net.ssl.SSLSocket, org.bouncycastle.jsse.BCSSLSocket
    public synchronized String getHandshakeApplicationProtocol() {
        ProvSSLSessionHandshake provSSLSessionHandshake;
        provSSLSessionHandshake = this.handshakeSession;
        return provSSLSessionHandshake == null ? null : provSSLSessionHandshake.getApplicationProtocol();
    }

    @Override // javax.net.ssl.SSLSocket
    public synchronized SSLSession getHandshakeSession() {
        ProvSSLSessionHandshake provSSLSessionHandshake;
        provSSLSessionHandshake = this.handshakeSession;
        return provSSLSessionHandshake == null ? null : provSSLSessionHandshake.getExportSSLSession();
    }

    @Override // java.net.Socket
    public InputStream getInputStream() {
        return this.appDataIn;
    }

    @Override // javax.net.ssl.SSLSocket
    public synchronized boolean getNeedClientAuth() {
        return this.sslParameters.getNeedClientAuth();
    }

    @Override // java.net.Socket
    public OutputStream getOutputStream() {
        return this.appDataOut;
    }

    @Override // org.bouncycastle.jsse.BCSSLSocket
    public synchronized BCSSLParameters getParameters() {
        return SSLParametersUtil.getParameters(this.sslParameters);
    }

    @Override // org.bouncycastle.jsse.provider.ProvTlsManager
    public synchronized String getPeerHost() {
        return this.peerHost;
    }

    @Override // org.bouncycastle.jsse.provider.ProvTlsManager
    public synchronized String getPeerHostSNI() {
        return this.peerHostSNI;
    }

    @Override // org.bouncycastle.jsse.provider.ProvTlsManager
    public int getPeerPort() {
        return getPort();
    }

    @Override // javax.net.ssl.SSLSocket
    public synchronized SSLParameters getSSLParameters() {
        return SSLParametersUtil.getSSLParameters(this.sslParameters);
    }

    @Override // javax.net.ssl.SSLSocket
    public SSLSession getSession() {
        return getSessionImpl().getExportSSLSession();
    }

    public synchronized ProvSSLSession getSessionImpl() {
        ProvSSLConnection provSSLConnection;
        getConnection();
        provSSLConnection = this.connection;
        return provSSLConnection == null ? ProvSSLSession.NULL_SESSION : provSSLConnection.getSession();
    }

    @Override // javax.net.ssl.SSLSocket
    public synchronized String[] getSupportedCipherSuites() {
        return this.contextData.getContext().getSupportedCipherSuites();
    }

    @Override // javax.net.ssl.SSLSocket
    public synchronized String[] getSupportedProtocols() {
        return this.contextData.getContext().getSupportedProtocols();
    }

    @Override // javax.net.ssl.SSLSocket
    public synchronized boolean getUseClientMode() {
        return this.useClientMode;
    }

    @Override // javax.net.ssl.SSLSocket
    public synchronized boolean getWantClientAuth() {
        return this.sslParameters.getWantClientAuth();
    }

    public synchronized void handshakeIfNecessary(boolean z2) {
        TlsProtocol tlsProtocol = this.protocol;
        if (tlsProtocol == null || tlsProtocol.isHandshaking()) {
            startHandshake(z2);
        }
    }

    public synchronized void notifyConnected() {
        if (JsseUtils.isNameSpecified(this.peerHost)) {
            this.peerHostSNI = this.peerHost;
            return;
        }
        InetAddress inetAddress = getInetAddress();
        if (inetAddress == null) {
            return;
        }
        boolean z2 = this.useClientMode;
        if (!z2 || !ProvSSLSocketBase.provAssumeOriginalHostName) {
            this.peerHost = (z2 && ProvSSLSocketBase.provJdkTlsTrustNameService) ? inetAddress.getHostName() : inetAddress.getHostAddress();
            this.peerHostSNI = null;
        } else {
            String hostName = inetAddress.getHostName();
            this.peerHost = hostName;
            this.peerHostSNI = hostName;
        }
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
        notifyHandshakeCompletedListeners(provSSLConnection.getSession().exportSSLSession);
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
        return this.sslParameters.getSocketAPSelector().select(this, list);
    }

    @Override // org.bouncycastle.jsse.BCSSLSocket
    public synchronized void setBCHandshakeApplicationProtocolSelector(BCApplicationProtocolSelector<SSLSocket> bCApplicationProtocolSelector) {
        this.sslParameters.setSocketAPSelector(bCApplicationProtocolSelector);
    }

    @Override // org.bouncycastle.jsse.BCSSLSocket
    public synchronized void setBCSessionToResume(BCExtendedSSLSession bCExtendedSSLSession) {
        try {
            if (bCExtendedSSLSession == null) {
                throw new NullPointerException("'session' cannot be null");
            }
            if (!(bCExtendedSSLSession instanceof ProvSSLSession)) {
                throw new IllegalArgumentException("Session-to-resume must be a session returned from 'getBCSession'");
            }
            if (this.protocol != null) {
                throw new IllegalArgumentException("Session-to-resume cannot be set after the handshake has begun");
            }
            this.sslParameters.setSessionToResume((ProvSSLSession) bCExtendedSSLSession);
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // javax.net.ssl.SSLSocket
    public synchronized void setEnableSessionCreation(boolean z2) {
        this.enableSessionCreation = z2;
    }

    @Override // javax.net.ssl.SSLSocket
    public synchronized void setEnabledCipherSuites(String[] strArr) {
        this.sslParameters.setCipherSuites(strArr);
    }

    @Override // javax.net.ssl.SSLSocket
    public synchronized void setEnabledProtocols(String[] strArr) {
        this.sslParameters.setProtocols(strArr);
    }

    @Override // org.bouncycastle.jsse.BCSSLSocket
    public synchronized void setHost(String str) {
        this.peerHost = str;
        this.peerHostSNI = str;
    }

    @Override // javax.net.ssl.SSLSocket
    public synchronized void setNeedClientAuth(boolean z2) {
        this.sslParameters.setNeedClientAuth(z2);
    }

    @Override // org.bouncycastle.jsse.BCSSLSocket
    public synchronized void setParameters(BCSSLParameters bCSSLParameters) {
        SSLParametersUtil.setParameters(this.sslParameters, bCSSLParameters);
    }

    @Override // javax.net.ssl.SSLSocket
    public synchronized void setSSLParameters(SSLParameters sSLParameters) {
        SSLParametersUtil.setSSLParameters(this.sslParameters, sSLParameters);
    }

    @Override // javax.net.ssl.SSLSocket
    public synchronized void setUseClientMode(boolean z2) {
        if (this.protocol != null) {
            throw new IllegalArgumentException("Mode cannot be changed after the initial handshake has begun");
        }
        if (this.useClientMode != z2) {
            this.contextData.getContext().updateDefaultSSLParameters(this.sslParameters, z2);
            this.useClientMode = z2;
        }
    }

    @Override // javax.net.ssl.SSLSocket
    public synchronized void setWantClientAuth(boolean z2) {
        this.sslParameters.setWantClientAuth(z2);
    }

    @Override // java.net.Socket
    public void shutdownInput() {
        throw new UnsupportedOperationException("shutdownInput() not supported in TLS");
    }

    @Override // java.net.Socket
    public void shutdownOutput() {
        throw new UnsupportedOperationException("shutdownOutput() not supported in TLS");
    }

    @Override // javax.net.ssl.SSLSocket
    public synchronized void startHandshake() {
        startHandshake(true);
    }

    public ProvSSLSocketDirect(ContextData contextData, String str, int i2) {
        this.appDataIn = new AppDataInput();
        this.appDataOut = new AppDataOutput();
        this.peerHost = null;
        this.peerHostSNI = null;
        this.enableSessionCreation = true;
        this.useClientMode = true;
        this.protocol = null;
        this.protocolPeer = null;
        this.connection = null;
        this.handshakeSession = null;
        this.contextData = contextData;
        this.sslParameters = contextData.getContext().getDefaultSSLParameters(this.useClientMode);
        this.peerHost = str;
        implConnect(str, i2);
    }

    public void startHandshake(boolean z2) {
        TlsProtocol tlsProtocol = this.protocol;
        if (tlsProtocol != null) {
            if (!tlsProtocol.isHandshaking()) {
                throw new UnsupportedOperationException("Renegotiation not supported");
            }
            this.protocol.setResumableHandshake(z2);
            this.protocol.resumeHandshake();
            return;
        }
        InputStream inputStream = super.getInputStream();
        OutputStream outputStream = super.getOutputStream();
        if (this.useClientMode) {
            ProvTlsClientProtocol provTlsClientProtocol = new ProvTlsClientProtocol(inputStream, outputStream, this.socketCloser);
            provTlsClientProtocol.setResumableHandshake(z2);
            this.protocol = provTlsClientProtocol;
            ProvTlsClient provTlsClient = new ProvTlsClient(this, this.sslParameters);
            this.protocolPeer = provTlsClient;
            provTlsClientProtocol.connect(provTlsClient);
            return;
        }
        ProvTlsServerProtocol provTlsServerProtocol = new ProvTlsServerProtocol(inputStream, outputStream, this.socketCloser);
        provTlsServerProtocol.setResumableHandshake(z2);
        this.protocol = provTlsServerProtocol;
        ProvTlsServer provTlsServer = new ProvTlsServer(this, this.sslParameters);
        this.protocolPeer = provTlsServer;
        provTlsServerProtocol.accept(provTlsServer);
    }

    public ProvSSLSocketDirect(ContextData contextData, String str, int i2, InetAddress inetAddress, int i3) {
        this.appDataIn = new AppDataInput();
        this.appDataOut = new AppDataOutput();
        this.peerHost = null;
        this.peerHostSNI = null;
        this.enableSessionCreation = true;
        this.useClientMode = true;
        this.protocol = null;
        this.protocolPeer = null;
        this.connection = null;
        this.handshakeSession = null;
        this.contextData = contextData;
        this.sslParameters = contextData.getContext().getDefaultSSLParameters(this.useClientMode);
        this.peerHost = str;
        implBind(inetAddress, i3);
        implConnect(str, i2);
    }

    public ProvSSLSocketDirect(ContextData contextData, InetAddress inetAddress, int i2) {
        this.appDataIn = new AppDataInput();
        this.appDataOut = new AppDataOutput();
        this.peerHost = null;
        this.peerHostSNI = null;
        this.enableSessionCreation = true;
        this.useClientMode = true;
        this.protocol = null;
        this.protocolPeer = null;
        this.connection = null;
        this.handshakeSession = null;
        this.contextData = contextData;
        this.sslParameters = contextData.getContext().getDefaultSSLParameters(this.useClientMode);
        implConnect(inetAddress, i2);
    }

    public ProvSSLSocketDirect(ContextData contextData, InetAddress inetAddress, int i2, InetAddress inetAddress2, int i3) {
        this.appDataIn = new AppDataInput();
        this.appDataOut = new AppDataOutput();
        this.peerHost = null;
        this.peerHostSNI = null;
        this.enableSessionCreation = true;
        this.useClientMode = true;
        this.protocol = null;
        this.protocolPeer = null;
        this.connection = null;
        this.handshakeSession = null;
        this.contextData = contextData;
        this.sslParameters = contextData.getContext().getDefaultSSLParameters(this.useClientMode);
        implBind(inetAddress2, i3);
        implConnect(inetAddress, i2);
    }

    public ProvSSLSocketDirect(ContextData contextData, boolean z2, boolean z3, ProvSSLParameters provSSLParameters) {
        this.appDataIn = new AppDataInput();
        this.appDataOut = new AppDataOutput();
        this.peerHost = null;
        this.peerHostSNI = null;
        this.protocol = null;
        this.protocolPeer = null;
        this.connection = null;
        this.handshakeSession = null;
        this.contextData = contextData;
        this.enableSessionCreation = z2;
        this.useClientMode = z3;
        this.sslParameters = provSSLParameters;
    }
}
