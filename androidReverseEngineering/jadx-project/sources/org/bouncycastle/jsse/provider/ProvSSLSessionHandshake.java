package org.bouncycastle.jsse.provider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.bouncycastle.jsse.BCSNIServerName;
import org.bouncycastle.tls.Certificate;
import org.bouncycastle.tls.ProtocolVersion;
import org.bouncycastle.tls.SecurityParameters;

/* loaded from: classes.dex */
class ProvSSLSessionHandshake extends ProvSSLSessionBase {
    protected final JsseSecurityParameters jsseSecurityParameters;
    protected final SecurityParameters securityParameters;

    public ProvSSLSessionHandshake(ProvSSLSessionContext provSSLSessionContext, String str, int i2, SecurityParameters securityParameters, JsseSecurityParameters jsseSecurityParameters) {
        super(provSSLSessionContext, str, i2);
        this.securityParameters = securityParameters;
        this.jsseSecurityParameters = jsseSecurityParameters;
    }

    public String getApplicationProtocol() {
        return JsseUtils.getApplicationProtocol(this.securityParameters);
    }

    @Override // org.bouncycastle.jsse.provider.ProvSSLSessionBase
    public int getCipherSuiteTLS() {
        return this.securityParameters.getCipherSuite();
    }

    @Override // org.bouncycastle.jsse.provider.ProvSSLSessionBase
    public byte[] getIDArray() {
        return this.securityParameters.getSessionID();
    }

    @Override // org.bouncycastle.jsse.provider.ProvSSLSessionBase
    public JsseSecurityParameters getJsseSecurityParameters() {
        return this.jsseSecurityParameters;
    }

    @Override // org.bouncycastle.jsse.provider.ProvSSLSessionBase
    public JsseSessionParameters getJsseSessionParameters() {
        return null;
    }

    @Override // org.bouncycastle.jsse.provider.ProvSSLSessionBase
    public Certificate getLocalCertificateTLS() {
        return this.securityParameters.getLocalCertificate();
    }

    @Override // org.bouncycastle.jsse.BCExtendedSSLSession
    public String[] getLocalSupportedSignatureAlgorithms() {
        return SignatureSchemeInfo.getJcaSignatureAlgorithms(this.jsseSecurityParameters.localSigSchemesCert);
    }

    @Override // org.bouncycastle.jsse.BCExtendedSSLSession
    public String[] getLocalSupportedSignatureAlgorithmsBC() {
        return SignatureSchemeInfo.getJcaSignatureAlgorithmsBC(this.jsseSecurityParameters.localSigSchemesCert);
    }

    @Override // org.bouncycastle.jsse.provider.ProvSSLSessionBase
    public Certificate getPeerCertificateTLS() {
        return this.securityParameters.getPeerCertificate();
    }

    @Override // org.bouncycastle.jsse.BCExtendedSSLSession
    public String[] getPeerSupportedSignatureAlgorithms() {
        return SignatureSchemeInfo.getJcaSignatureAlgorithms(this.jsseSecurityParameters.peerSigSchemesCert);
    }

    @Override // org.bouncycastle.jsse.BCExtendedSSLSession
    public String[] getPeerSupportedSignatureAlgorithmsBC() {
        return SignatureSchemeInfo.getJcaSignatureAlgorithmsBC(this.jsseSecurityParameters.peerSigSchemesCert);
    }

    @Override // org.bouncycastle.jsse.provider.ProvSSLSessionBase
    public ProtocolVersion getProtocolTLS() {
        return this.securityParameters.getNegotiatedVersion();
    }

    @Override // org.bouncycastle.jsse.BCExtendedSSLSession
    public List<BCSNIServerName> getRequestedServerNames() {
        return JsseUtils.convertSNIServerNames(this.securityParameters.getClientServerNames());
    }

    @Override // org.bouncycastle.jsse.BCExtendedSSLSession
    public List<byte[]> getStatusResponses() {
        List<byte[]> list = this.jsseSecurityParameters.statusResponses;
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(list.size());
        Iterator<byte[]> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().clone());
        }
        return Collections.unmodifiableList(arrayList);
    }

    @Override // org.bouncycastle.jsse.provider.ProvSSLSessionBase
    public void invalidateTLS() {
    }
}
