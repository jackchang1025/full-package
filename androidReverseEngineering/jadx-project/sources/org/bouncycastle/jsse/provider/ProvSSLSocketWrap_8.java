package org.bouncycastle.jsse.provider;

import java.io.InputStream;
import java.net.Socket;
import java.util.List;
import java.util.function.BiFunction;
import javax.net.ssl.SSLSocket;

/* loaded from: classes.dex */
class ProvSSLSocketWrap_8 extends ProvSSLSocketWrap {
    public ProvSSLSocketWrap_8(ContextData contextData, Socket socket, InputStream inputStream, boolean z2) {
        super(contextData, socket, inputStream, z2);
    }

    @Override // javax.net.ssl.SSLSocket
    public synchronized BiFunction<SSLSocket, List<String>, String> getHandshakeApplicationProtocolSelector() {
        return JsseUtils_8.exportAPSelector(this.sslParameters.getSocketAPSelector());
    }

    @Override // javax.net.ssl.SSLSocket
    public synchronized void setHandshakeApplicationProtocolSelector(BiFunction<SSLSocket, List<String>, String> biFunction) {
        this.sslParameters.setSocketAPSelector(JsseUtils_8.importAPSelector(biFunction));
    }

    public ProvSSLSocketWrap_8(ContextData contextData, Socket socket, String str, int i2, boolean z2) {
        super(contextData, socket, str, i2, z2);
    }
}
