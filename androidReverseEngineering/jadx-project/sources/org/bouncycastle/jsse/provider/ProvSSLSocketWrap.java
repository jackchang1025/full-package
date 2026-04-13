package org.bouncycastle.jsse.provider;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.SequenceInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.SocketChannel;
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
class ProvSSLSocketWrap extends ProvSSLSocketBase implements ProvTlsManager {
    private static final Logger LOG = Logger.getLogger(ProvSSLSocketWrap.class.getName());
    protected final AppDataInput appDataIn;
    protected final AppDataOutput appDataOut;
    protected final boolean autoClose;
    protected ProvSSLConnection connection;
    protected final InputStream consumed;
    protected final ContextData contextData;
    protected boolean enableSessionCreation;
    protected ProvSSLSessionHandshake handshakeSession;
    protected String peerHost;
    protected String peerHostSNI;
    protected TlsProtocol protocol;
    protected ProvTlsPeer protocolPeer;
    protected final ProvSSLParameters sslParameters;
    protected boolean useClientMode;
    protected final Socket wrapSocket;

    public class AppDataInput extends InputStream {
        public AppDataInput() {
        }

        @Override // java.io.InputStream
        public int available() {
            int applicationDataAvailable;
            synchronized (ProvSSLSocketWrap.this) {
                TlsProtocol tlsProtocol = ProvSSLSocketWrap.this.protocol;
                applicationDataAvailable = tlsProtocol == null ? 0 : tlsProtocol.applicationDataAvailable();
            }
            return applicationDataAvailable;
        }

        @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            ProvSSLSocketWrap.this.close();
        }

        @Override // java.io.InputStream
        public int read() {
            ProvSSLSocketWrap.this.handshakeIfNecessary(true);
            byte[] bArr = new byte[1];
            if (ProvSSLSocketWrap.this.protocol.readApplicationData(bArr, 0, 1) < 0) {
                return -1;
            }
            return bArr[0] & 255;
        }

        @Override // java.io.InputStream
        public int read(byte[] bArr, int i2, int i3) {
            if (i3 < 1) {
                return 0;
            }
            ProvSSLSocketWrap.this.handshakeIfNecessary(true);
            return ProvSSLSocketWrap.this.protocol.readApplicationData(bArr, i2, i3);
        }
    }

    public class AppDataOutput extends OutputStream {
        public AppDataOutput() {
        }

        @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            ProvSSLSocketWrap.this.close();
        }

        @Override // java.io.OutputStream
        public void write(int i2) {
            write(new byte[]{(byte) i2}, 0, 1);
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i2, int i3) {
            if (i3 > 0) {
                ProvSSLSocketWrap.this.handshakeIfNecessary(true);
                ProvSSLSocketWrap.this.protocol.writeApplicationData(bArr, i2, i3);
            }
        }
    }

    public ProvSSLSocketWrap(ContextData contextData, Socket socket, InputStream inputStream, boolean z2) {
        this.appDataIn = new AppDataInput();
        this.appDataOut = new AppDataOutput();
        this.peerHost = null;
        this.peerHostSNI = null;
        this.enableSessionCreation = true;
        this.protocol = null;
        this.protocolPeer = null;
        this.connection = null;
        this.handshakeSession = null;
        this.contextData = contextData;
        this.wrapSocket = checkSocket(socket);
        this.consumed = inputStream;
        this.autoClose = z2;
        this.useClientMode = false;
        this.sslParameters = contextData.getContext().getDefaultSSLParameters(this.useClientMode);
        notifyConnected();
    }

    private static Socket checkSocket(Socket socket) {
        if (socket == null) {
            throw new NullPointerException("'s' cannot be null");
        }
        if (socket.isConnected()) {
            return socket;
        }
        throw new SocketException("'s' is not a connected socket");
    }

    @Override // java.net.Socket
    public void bind(SocketAddress socketAddress) {
        throw new SocketException("Wrapped socket should already be bound");
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

    @Override // org.bouncycastle.jsse.provider.ProvSSLSocketBase
    public void closeSocket() {
        if (this.autoClose) {
            this.wrapSocket.close();
        }
    }

    @Override // java.net.Socket
    public void connect(SocketAddress socketAddress, int i2) {
        throw new SocketException("Wrapped socket should already be connected");
    }

    public void finalize() {
        try {
            close();
        } catch (IOException unused) {
        } catch (Throwable th) {
            super.finalize();
            throw th;
        }
        super.finalize();
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

    @Override // java.net.Socket
    public SocketChannel getChannel() {
        return this.wrapSocket.getChannel();
    }

    @Override // org.bouncycastle.jsse.BCSSLSocket
    public synchronized BCSSLConnection getConnection() {
        try {
            handshakeIfNecessary(false);
        } catch (Exception e2) {
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
    public InetAddress getInetAddress() {
        return this.wrapSocket.getInetAddress();
    }

    @Override // java.net.Socket
    public InputStream getInputStream() {
        return this.appDataIn;
    }

    @Override // java.net.Socket
    public boolean getKeepAlive() {
        return this.wrapSocket.getKeepAlive();
    }

    @Override // java.net.Socket
    public InetAddress getLocalAddress() {
        return this.wrapSocket.getLocalAddress();
    }

    @Override // java.net.Socket
    public int getLocalPort() {
        return this.wrapSocket.getLocalPort();
    }

    @Override // java.net.Socket
    public SocketAddress getLocalSocketAddress() {
        return this.wrapSocket.getLocalSocketAddress();
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

    @Override // java.net.Socket
    public int getPort() {
        return this.wrapSocket.getPort();
    }

    @Override // java.net.Socket
    public int getReceiveBufferSize() {
        return this.wrapSocket.getReceiveBufferSize();
    }

    @Override // java.net.Socket
    public SocketAddress getRemoteSocketAddress() {
        return this.wrapSocket.getRemoteSocketAddress();
    }

    @Override // java.net.Socket
    public boolean getReuseAddress() {
        return this.wrapSocket.getReuseAddress();
    }

    @Override // javax.net.ssl.SSLSocket
    public synchronized SSLParameters getSSLParameters() {
        return SSLParametersUtil.getSSLParameters(this.sslParameters);
    }

    @Override // java.net.Socket
    public int getSendBufferSize() {
        return this.wrapSocket.getSendBufferSize();
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

    @Override // java.net.Socket
    public int getSoLinger() {
        return this.wrapSocket.getSoLinger();
    }

    @Override // java.net.Socket
    public int getSoTimeout() {
        return this.wrapSocket.getSoTimeout();
    }

    @Override // javax.net.ssl.SSLSocket
    public synchronized String[] getSupportedCipherSuites() {
        return this.contextData.getContext().getSupportedCipherSuites();
    }

    @Override // javax.net.ssl.SSLSocket
    public synchronized String[] getSupportedProtocols() {
        return this.contextData.getContext().getSupportedProtocols();
    }

    @Override // java.net.Socket
    public boolean getTcpNoDelay() {
        return this.wrapSocket.getTcpNoDelay();
    }

    @Override // java.net.Socket
    public int getTrafficClass() {
        return this.wrapSocket.getTrafficClass();
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

    @Override // java.net.Socket
    public boolean isBound() {
        return this.wrapSocket.isBound();
    }

    @Override // java.net.Socket
    public synchronized boolean isClosed() {
        boolean z2;
        TlsProtocol tlsProtocol = this.protocol;
        if (tlsProtocol != null) {
            z2 = tlsProtocol.isClosed();
        }
        return z2;
    }

    @Override // java.net.Socket
    public boolean isConnected() {
        return this.wrapSocket.isConnected();
    }

    @Override // java.net.Socket
    public boolean isInputShutdown() {
        return this.wrapSocket.isInputShutdown();
    }

    @Override // java.net.Socket
    public boolean isOutputShutdown() {
        return this.wrapSocket.isOutputShutdown();
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

    @Override // java.net.Socket
    public void setKeepAlive(boolean z2) {
        this.wrapSocket.setKeepAlive(z2);
    }

    @Override // javax.net.ssl.SSLSocket
    public synchronized void setNeedClientAuth(boolean z2) {
        this.sslParameters.setNeedClientAuth(z2);
    }

    @Override // org.bouncycastle.jsse.BCSSLSocket
    public synchronized void setParameters(BCSSLParameters bCSSLParameters) {
        SSLParametersUtil.setParameters(this.sslParameters, bCSSLParameters);
    }

    @Override // java.net.Socket
    public void setPerformancePreferences(int i2, int i3, int i4) {
        this.wrapSocket.setPerformancePreferences(i2, i3, i4);
    }

    @Override // java.net.Socket
    public void setReceiveBufferSize(int i2) {
        this.wrapSocket.setReceiveBufferSize(i2);
    }

    @Override // java.net.Socket
    public void setReuseAddress(boolean z2) {
        this.wrapSocket.setReuseAddress(z2);
    }

    @Override // javax.net.ssl.SSLSocket
    public synchronized void setSSLParameters(SSLParameters sSLParameters) {
        SSLParametersUtil.setSSLParameters(this.sslParameters, sSLParameters);
    }

    @Override // java.net.Socket
    public void setSendBufferSize(int i2) {
        this.wrapSocket.setSendBufferSize(i2);
    }

    @Override // java.net.Socket
    public void setSoLinger(boolean z2, int i2) {
        this.wrapSocket.setSoLinger(z2, i2);
    }

    @Override // java.net.Socket
    public void setSoTimeout(int i2) {
        this.wrapSocket.setSoTimeout(i2);
    }

    @Override // java.net.Socket
    public void setTcpNoDelay(boolean z2) {
        this.wrapSocket.setTcpNoDelay(z2);
    }

    @Override // java.net.Socket
    public void setTrafficClass(int i2) {
        this.wrapSocket.setTrafficClass(i2);
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
        this.wrapSocket.shutdownInput();
    }

    @Override // java.net.Socket
    public void shutdownOutput() {
        this.wrapSocket.shutdownOutput();
    }

    @Override // javax.net.ssl.SSLSocket
    public synchronized void startHandshake() {
        startHandshake(true);
    }

    @Override // javax.net.ssl.SSLSocket, java.net.Socket
    public String toString() {
        return this.wrapSocket.toString();
    }

    public ProvSSLSocketWrap(ContextData contextData, Socket socket, String str, int i2, boolean z2) {
        this.appDataIn = new AppDataInput();
        this.appDataOut = new AppDataOutput();
        this.peerHost = null;
        this.peerHostSNI = null;
        this.enableSessionCreation = true;
        this.protocol = null;
        this.protocolPeer = null;
        this.connection = null;
        this.handshakeSession = null;
        this.contextData = contextData;
        this.wrapSocket = checkSocket(socket);
        this.consumed = null;
        this.peerHost = str;
        this.autoClose = z2;
        this.useClientMode = true;
        this.sslParameters = contextData.getContext().getDefaultSSLParameters(this.useClientMode);
        notifyConnected();
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
        InputStream inputStream = this.wrapSocket.getInputStream();
        if (this.consumed != null) {
            inputStream = new SequenceInputStream(this.consumed, inputStream);
        }
        OutputStream outputStream = this.wrapSocket.getOutputStream();
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
}
