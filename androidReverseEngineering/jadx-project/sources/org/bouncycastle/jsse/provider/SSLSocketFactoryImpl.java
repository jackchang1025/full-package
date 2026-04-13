package org.bouncycastle.jsse.provider;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;

/* loaded from: classes.dex */
public class SSLSocketFactoryImpl extends ProvSSLSocketFactory {
    public SSLSocketFactoryImpl() {
        super(DefaultSSLContextSpi.getDefaultInstance().getContextData());
    }

    @Override // org.bouncycastle.jsse.provider.ProvSSLSocketFactory, javax.net.SocketFactory
    public /* bridge */ /* synthetic */ Socket createSocket() {
        return super.createSocket();
    }

    @Override // org.bouncycastle.jsse.provider.ProvSSLSocketFactory, javax.net.ssl.SSLSocketFactory
    public /* bridge */ /* synthetic */ String[] getDefaultCipherSuites() {
        return super.getDefaultCipherSuites();
    }

    @Override // org.bouncycastle.jsse.provider.ProvSSLSocketFactory, javax.net.ssl.SSLSocketFactory
    public /* bridge */ /* synthetic */ String[] getSupportedCipherSuites() {
        return super.getSupportedCipherSuites();
    }

    @Override // org.bouncycastle.jsse.provider.ProvSSLSocketFactory, javax.net.SocketFactory
    public /* bridge */ /* synthetic */ Socket createSocket(String str, int i2) {
        return super.createSocket(str, i2);
    }

    @Override // org.bouncycastle.jsse.provider.ProvSSLSocketFactory, javax.net.SocketFactory
    public /* bridge */ /* synthetic */ Socket createSocket(String str, int i2, InetAddress inetAddress, int i3) {
        return super.createSocket(str, i2, inetAddress, i3);
    }

    @Override // org.bouncycastle.jsse.provider.ProvSSLSocketFactory, javax.net.SocketFactory
    public /* bridge */ /* synthetic */ Socket createSocket(InetAddress inetAddress, int i2) {
        return super.createSocket(inetAddress, i2);
    }

    @Override // org.bouncycastle.jsse.provider.ProvSSLSocketFactory, javax.net.SocketFactory
    public /* bridge */ /* synthetic */ Socket createSocket(InetAddress inetAddress, int i2, InetAddress inetAddress2, int i3) {
        return super.createSocket(inetAddress, i2, inetAddress2, i3);
    }

    @Override // org.bouncycastle.jsse.provider.ProvSSLSocketFactory
    public /* bridge */ /* synthetic */ Socket createSocket(Socket socket, InputStream inputStream, boolean z2) {
        return super.createSocket(socket, inputStream, z2);
    }

    @Override // org.bouncycastle.jsse.provider.ProvSSLSocketFactory, javax.net.ssl.SSLSocketFactory
    public /* bridge */ /* synthetic */ Socket createSocket(Socket socket, String str, int i2, boolean z2) {
        return super.createSocket(socket, str, i2, z2);
    }
}
