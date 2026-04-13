package org.bouncycastle.jsse.util;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import javax.net.ssl.SSLSocketFactory;

/* loaded from: classes.dex */
public class CustomSSLSocketFactory extends SSLSocketFactory {
    protected final SSLSocketFactory delegate;

    public CustomSSLSocketFactory(SSLSocketFactory sSLSocketFactory) {
        if (sSLSocketFactory == null) {
            throw new NullPointerException("'delegate' cannot be null");
        }
        this.delegate = sSLSocketFactory;
    }

    public Socket configureSocket(Socket socket) {
        return socket;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket() {
        return configureSocket(this.delegate.createSocket());
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getDefaultCipherSuites() {
        return this.delegate.getDefaultCipherSuites();
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getSupportedCipherSuites() {
        return this.delegate.getSupportedCipherSuites();
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String str, int i2) {
        return configureSocket(this.delegate.createSocket(str, i2));
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String str, int i2, InetAddress inetAddress, int i3) {
        return configureSocket(this.delegate.createSocket(str, i2, inetAddress, i3));
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress inetAddress, int i2) {
        return configureSocket(this.delegate.createSocket(inetAddress, i2));
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress inetAddress, int i2, InetAddress inetAddress2, int i3) {
        return configureSocket(this.delegate.createSocket(inetAddress, i2, inetAddress2, i3));
    }

    public Socket createSocket(Socket socket, InputStream inputStream, boolean z2) {
        return configureSocket(this.delegate.createSocket(socket, inputStream, z2));
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public Socket createSocket(Socket socket, String str, int i2, boolean z2) {
        return configureSocket(this.delegate.createSocket(socket, str, i2, z2));
    }
}
