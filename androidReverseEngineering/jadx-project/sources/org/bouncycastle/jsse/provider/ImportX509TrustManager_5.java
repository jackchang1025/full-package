package org.bouncycastle.jsse.provider;

import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509TrustManager;
import org.bouncycastle.jcajce.util.JcaJceHelper;
import org.bouncycastle.jsse.BCX509ExtendedTrustManager;
import org.bouncycastle.tls.TlsUtils;

/* loaded from: classes.dex */
class ImportX509TrustManager_5 extends BCX509ExtendedTrustManager implements ImportX509TrustManager {
    final JcaJceHelper helper;
    final boolean isInFipsMode;
    final X509TrustManager x509TrustManager;

    public ImportX509TrustManager_5(boolean z2, JcaJceHelper jcaJceHelper, X509TrustManager x509TrustManager) {
        this.isInFipsMode = z2;
        this.helper = jcaJceHelper;
        this.x509TrustManager = x509TrustManager;
    }

    private void checkAdditionalTrust(X509Certificate[] x509CertificateArr, String str, TransportData transportData, boolean z2) {
        checkAlgorithmConstraints(x509CertificateArr, str, transportData, z2);
        ProvX509TrustManager.checkExtendedTrust(x509CertificateArr, transportData, z2);
    }

    private void checkAlgorithmConstraints(X509Certificate[] x509CertificateArr, String str, TransportData transportData, boolean z2) {
        try {
            ProvAlgorithmChecker.checkChain(this.isInFipsMode, this.helper, TransportData.getAlgorithmConstraints(transportData, false), getTrustedCerts(), x509CertificateArr, ProvX509TrustManager.getRequiredExtendedKeyUsage(z2), ProvX509TrustManager.getRequiredKeyUsage(z2, str));
        } catch (GeneralSecurityException e2) {
            throw new CertificateException("Certificates do not conform to algorithm constraints", e2);
        }
    }

    private static X509Certificate[] checkChain(X509Certificate[] x509CertificateArr) {
        if (TlsUtils.isNullOrEmpty(x509CertificateArr)) {
            throw new IllegalArgumentException("'chain' must be a chain of at least one certificate");
        }
        return x509CertificateArr;
    }

    private static X509Certificate[] copyChain(X509Certificate[] x509CertificateArr) {
        return (X509Certificate[]) checkChain(x509CertificateArr).clone();
    }

    private Set<X509Certificate> getTrustedCerts() {
        X509Certificate[] acceptedIssuers = getAcceptedIssuers();
        if (TlsUtils.isNullOrEmpty(acceptedIssuers)) {
            return Collections.emptySet();
        }
        HashSet hashSet = new HashSet();
        for (X509Certificate x509Certificate : acceptedIssuers) {
            if (x509Certificate != null) {
                hashSet.add(x509Certificate);
            }
        }
        return Collections.unmodifiableSet(hashSet);
    }

    @Override // javax.net.ssl.X509TrustManager
    public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) {
        this.x509TrustManager.checkClientTrusted(copyChain(x509CertificateArr), str);
        checkAdditionalTrust(x509CertificateArr, str, null, false);
    }

    @Override // javax.net.ssl.X509TrustManager
    public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) {
        this.x509TrustManager.checkServerTrusted(copyChain(x509CertificateArr), str);
        checkAdditionalTrust(x509CertificateArr, str, null, true);
    }

    @Override // javax.net.ssl.X509TrustManager
    public X509Certificate[] getAcceptedIssuers() {
        return this.x509TrustManager.getAcceptedIssuers();
    }

    @Override // org.bouncycastle.jsse.provider.ImportX509TrustManager
    public X509TrustManager unwrap() {
        return this.x509TrustManager;
    }

    @Override // org.bouncycastle.jsse.BCX509ExtendedTrustManager
    public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str, Socket socket) {
        this.x509TrustManager.checkClientTrusted(copyChain(x509CertificateArr), str);
        checkAdditionalTrust(x509CertificateArr, str, TransportData.from(socket), false);
    }

    @Override // org.bouncycastle.jsse.BCX509ExtendedTrustManager
    public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str, Socket socket) {
        this.x509TrustManager.checkServerTrusted(copyChain(x509CertificateArr), str);
        checkAdditionalTrust(x509CertificateArr, str, TransportData.from(socket), true);
    }

    @Override // org.bouncycastle.jsse.BCX509ExtendedTrustManager
    public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str, SSLEngine sSLEngine) {
        this.x509TrustManager.checkClientTrusted(copyChain(x509CertificateArr), str);
        checkAdditionalTrust(x509CertificateArr, str, TransportData.from(sSLEngine), false);
    }

    @Override // org.bouncycastle.jsse.BCX509ExtendedTrustManager
    public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str, SSLEngine sSLEngine) {
        this.x509TrustManager.checkServerTrusted(copyChain(x509CertificateArr), str);
        checkAdditionalTrust(x509CertificateArr, str, TransportData.from(sSLEngine), true);
    }
}
