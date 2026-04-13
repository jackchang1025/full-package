package org.bouncycastle.jsse.provider;

import java.net.InetAddress;
import java.net.ServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

/* loaded from: classes.dex */
class ProvSSLServerSocketFactory extends SSLServerSocketFactory {
    protected final ContextData contextData;

    public ProvSSLServerSocketFactory(ContextData contextData) {
        this.contextData = contextData;
    }

    @Override // javax.net.ServerSocketFactory
    public ServerSocket createServerSocket() {
        return new ProvSSLServerSocket(this.contextData);
    }

    @Override // javax.net.ssl.SSLServerSocketFactory
    public String[] getDefaultCipherSuites() {
        return this.contextData.getContext().getDefaultCipherSuites(false);
    }

    @Override // javax.net.ssl.SSLServerSocketFactory
    public String[] getSupportedCipherSuites() {
        return this.contextData.getContext().getSupportedCipherSuites();
    }

    @Override // javax.net.ServerSocketFactory
    public ServerSocket createServerSocket(int i2) {
        return new ProvSSLServerSocket(this.contextData, i2);
    }

    @Override // javax.net.ServerSocketFactory
    public ServerSocket createServerSocket(int i2, int i3) {
        return new ProvSSLServerSocket(this.contextData, i2, i3);
    }

    @Override // javax.net.ServerSocketFactory
    public ServerSocket createServerSocket(int i2, int i3, InetAddress inetAddress) {
        return new ProvSSLServerSocket(this.contextData, i2, i3, inetAddress);
    }
}
