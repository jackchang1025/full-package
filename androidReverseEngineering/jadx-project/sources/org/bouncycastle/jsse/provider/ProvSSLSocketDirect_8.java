package org.bouncycastle.jsse.provider;

import java.net.InetAddress;
import java.util.List;
import java.util.function.BiFunction;
import javax.net.ssl.SSLSocket;

/* loaded from: classes.dex */
class ProvSSLSocketDirect_8 extends ProvSSLSocketDirect {
    public ProvSSLSocketDirect_8(ContextData contextData) {
        super(contextData);
    }

    @Override // javax.net.ssl.SSLSocket
    public synchronized BiFunction<SSLSocket, List<String>, String> getHandshakeApplicationProtocolSelector() {
        return JsseUtils_8.exportAPSelector(this.sslParameters.getSocketAPSelector());
    }

    @Override // javax.net.ssl.SSLSocket
    public synchronized void setHandshakeApplicationProtocolSelector(BiFunction<SSLSocket, List<String>, String> biFunction) {
        this.sslParameters.setSocketAPSelector(JsseUtils_8.importAPSelector(biFunction));
    }

    public ProvSSLSocketDirect_8(ContextData contextData, String str, int i2) {
        super(contextData, str, i2);
    }

    public ProvSSLSocketDirect_8(ContextData contextData, String str, int i2, InetAddress inetAddress, int i3) {
        super(contextData, str, i2, inetAddress, i3);
    }

    public ProvSSLSocketDirect_8(ContextData contextData, InetAddress inetAddress, int i2) {
        super(contextData, inetAddress, i2);
    }

    public ProvSSLSocketDirect_8(ContextData contextData, InetAddress inetAddress, int i2, InetAddress inetAddress2, int i3) {
        super(contextData, inetAddress, i2, inetAddress2, i3);
    }

    public ProvSSLSocketDirect_8(ContextData contextData, boolean z2, boolean z3, ProvSSLParameters provSSLParameters) {
        super(contextData, z2, z3, provSSLParameters);
    }
}
