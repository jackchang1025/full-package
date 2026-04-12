package p000;

import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class m41 implements X509TrustManager {

    /* renamed from: a0 */
    public final /* synthetic */ int f58264a0;

    @Override // javax.net.ssl.X509TrustManager
    public final void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) {
        int i = this.f58264a0;
    }

    @Override // javax.net.ssl.X509TrustManager
    public final void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) {
        int i = this.f58264a0;
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:3:0x0002. Please report as an issue. */
    @Override // javax.net.ssl.X509TrustManager
    public final X509Certificate[] getAcceptedIssuers() {
        switch (this.f58264a0) {
        }
        return new X509Certificate[0];
    }

    /* renamed from: a0 */
    private final void m213939a0(X509Certificate[] x509CertificateArr, String str) {
    }

    /* renamed from: a1 */
    private final void m213940a1(X509Certificate[] x509CertificateArr, String str) {
    }

    /* renamed from: a2 */
    private final void m213941a2(X509Certificate[] x509CertificateArr, String str) {
    }

    /* renamed from: a3 */
    private final void m213942a3(X509Certificate[] x509CertificateArr, String str) {
    }
}
