package org.bouncycastle.jsse;

import java.net.Socket;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509TrustManager;

/* loaded from: classes.dex */
public abstract class BCX509ExtendedTrustManager implements X509TrustManager {
    public abstract void checkClientTrusted(X509Certificate[] x509CertificateArr, String str, Socket socket);

    public abstract void checkClientTrusted(X509Certificate[] x509CertificateArr, String str, SSLEngine sSLEngine);

    public abstract void checkServerTrusted(X509Certificate[] x509CertificateArr, String str, Socket socket);

    public abstract void checkServerTrusted(X509Certificate[] x509CertificateArr, String str, SSLEngine sSLEngine);
}
