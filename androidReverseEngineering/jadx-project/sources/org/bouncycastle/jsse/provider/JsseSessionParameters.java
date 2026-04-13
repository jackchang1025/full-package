package org.bouncycastle.jsse.provider;

import org.bouncycastle.jsse.BCSNIServerName;

/* loaded from: classes.dex */
class JsseSessionParameters {
    private final String endpointIDAlgorithm;
    private final BCSNIServerName matchedSNIServerName;

    public JsseSessionParameters(String str, BCSNIServerName bCSNIServerName) {
        this.endpointIDAlgorithm = str;
        this.matchedSNIServerName = bCSNIServerName;
    }

    public String getEndpointIDAlgorithm() {
        return this.endpointIDAlgorithm;
    }

    public BCSNIServerName getMatchedSNIServerName() {
        return this.matchedSNIServerName;
    }
}
