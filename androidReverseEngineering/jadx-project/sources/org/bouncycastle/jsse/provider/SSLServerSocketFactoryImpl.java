package org.bouncycastle.jsse.provider;

import java.net.InetAddress;
import java.net.ServerSocket;

/* loaded from: classes.dex */
public class SSLServerSocketFactoryImpl extends ProvSSLServerSocketFactory {
    public SSLServerSocketFactoryImpl() {
        super(DefaultSSLContextSpi.getDefaultInstance().getContextData());
    }

    @Override // org.bouncycastle.jsse.provider.ProvSSLServerSocketFactory, javax.net.ServerSocketFactory
    public /* bridge */ /* synthetic */ ServerSocket createServerSocket() {
        return super.createServerSocket();
    }

    @Override // org.bouncycastle.jsse.provider.ProvSSLServerSocketFactory, javax.net.ssl.SSLServerSocketFactory
    public /* bridge */ /* synthetic */ String[] getDefaultCipherSuites() {
        return super.getDefaultCipherSuites();
    }

    @Override // org.bouncycastle.jsse.provider.ProvSSLServerSocketFactory, javax.net.ssl.SSLServerSocketFactory
    public /* bridge */ /* synthetic */ String[] getSupportedCipherSuites() {
        return super.getSupportedCipherSuites();
    }

    @Override // org.bouncycastle.jsse.provider.ProvSSLServerSocketFactory, javax.net.ServerSocketFactory
    public /* bridge */ /* synthetic */ ServerSocket createServerSocket(int i2) {
        return super.createServerSocket(i2);
    }

    @Override // org.bouncycastle.jsse.provider.ProvSSLServerSocketFactory, javax.net.ServerSocketFactory
    public /* bridge */ /* synthetic */ ServerSocket createServerSocket(int i2, int i3) {
        return super.createServerSocket(i2, i3);
    }

    @Override // org.bouncycastle.jsse.provider.ProvSSLServerSocketFactory, javax.net.ServerSocketFactory
    public /* bridge */ /* synthetic */ ServerSocket createServerSocket(int i2, int i3, InetAddress inetAddress) {
        return super.createServerSocket(i2, i3, inetAddress);
    }
}
