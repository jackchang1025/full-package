package b1;

import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

/* renamed from: b1.r */
/* loaded from: classes.dex */
public final class C0096r implements X509TrustManager {
    @Override // javax.net.ssl.X509TrustManager
    public final void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) {
    }

    @Override // javax.net.ssl.X509TrustManager
    public final void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) {
    }

    @Override // javax.net.ssl.X509TrustManager
    public final X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}
