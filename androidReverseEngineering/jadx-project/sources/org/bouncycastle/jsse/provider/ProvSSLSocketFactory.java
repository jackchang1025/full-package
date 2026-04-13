package org.bouncycastle.jsse.provider;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import javax.net.ssl.SSLSocketFactory;

/* loaded from: classes.dex */
class ProvSSLSocketFactory extends SSLSocketFactory {
    protected final ContextData contextData;

    public ProvSSLSocketFactory(ContextData contextData) {
        this.contextData = contextData;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket() {
        return SSLSocketUtil.create(this.contextData);
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getDefaultCipherSuites() {
        return this.contextData.getContext().getDefaultCipherSuites(true);
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getSupportedCipherSuites() {
        return this.contextData.getContext().getSupportedCipherSuites();
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String str, int i2) {
        return SSLSocketUtil.create(this.contextData, str, i2);
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String str, int i2, InetAddress inetAddress, int i3) {
        return SSLSocketUtil.create(this.contextData, str, i2, inetAddress, i3);
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress inetAddress, int i2) {
        return SSLSocketUtil.create(this.contextData, inetAddress, i2);
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress inetAddress, int i2, InetAddress inetAddress2, int i3) {
        return SSLSocketUtil.create(this.contextData, inetAddress, i2, inetAddress2, i3);
    }

    public Socket createSocket(Socket socket, InputStream inputStream, boolean z2) {
        return SSLSocketUtil.create(this.contextData, socket, inputStream, z2);
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public Socket createSocket(Socket socket, String str, int i2, boolean z2) {
        return SSLSocketUtil.create(this.contextData, socket, str, i2, z2);
    }
}
