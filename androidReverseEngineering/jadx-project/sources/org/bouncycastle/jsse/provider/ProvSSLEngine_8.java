package org.bouncycastle.jsse.provider;

import java.util.List;
import java.util.function.BiFunction;
import javax.net.ssl.SSLEngine;

/* loaded from: classes.dex */
class ProvSSLEngine_8 extends ProvSSLEngine {
    public ProvSSLEngine_8(ContextData contextData) {
        super(contextData);
    }

    @Override // javax.net.ssl.SSLEngine
    public synchronized BiFunction<SSLEngine, List<String>, String> getHandshakeApplicationProtocolSelector() {
        return JsseUtils_8.exportAPSelector(this.sslParameters.getEngineAPSelector());
    }

    @Override // javax.net.ssl.SSLEngine
    public synchronized void setHandshakeApplicationProtocolSelector(BiFunction<SSLEngine, List<String>, String> biFunction) {
        this.sslParameters.setEngineAPSelector(JsseUtils_8.importAPSelector(biFunction));
    }

    public ProvSSLEngine_8(ContextData contextData, String str, int i2) {
        super(contextData, str, i2);
    }
}
