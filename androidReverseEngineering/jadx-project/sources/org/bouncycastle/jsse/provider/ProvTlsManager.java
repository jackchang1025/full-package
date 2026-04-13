package org.bouncycastle.jsse.provider;

import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.List;
import org.bouncycastle.jsse.BCX509Key;
import org.bouncycastle.tls.SecurityParameters;

/* loaded from: classes.dex */
interface ProvTlsManager {
    void checkClientTrusted(X509Certificate[] x509CertificateArr, String str);

    void checkServerTrusted(X509Certificate[] x509CertificateArr, String str);

    BCX509Key chooseClientKey(String[] strArr, Principal[] principalArr);

    BCX509Key chooseServerKey(String[] strArr, Principal[] principalArr);

    ContextData getContextData();

    boolean getEnableSessionCreation();

    String getPeerHost();

    String getPeerHostSNI();

    int getPeerPort();

    void notifyHandshakeComplete(ProvSSLConnection provSSLConnection);

    void notifyHandshakeSession(ProvSSLSessionContext provSSLSessionContext, SecurityParameters securityParameters, JsseSecurityParameters jsseSecurityParameters, ProvSSLSession provSSLSession);

    String selectApplicationProtocol(List<String> list);
}
