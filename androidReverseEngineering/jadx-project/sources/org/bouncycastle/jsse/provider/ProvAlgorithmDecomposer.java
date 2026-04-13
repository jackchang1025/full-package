package org.bouncycastle.jsse.provider;

import java.util.Set;
import org.bouncycastle.tls.CipherSuite;

/* loaded from: classes.dex */
class ProvAlgorithmDecomposer extends JcaAlgorithmDecomposer {
    static final ProvAlgorithmDecomposer INSTANCE_TLS = new ProvAlgorithmDecomposer(true);
    static final ProvAlgorithmDecomposer INSTANCE_X509 = new ProvAlgorithmDecomposer(false);
    private final boolean enableTLSAlgorithms;

    private ProvAlgorithmDecomposer(boolean z2) {
        this.enableTLSAlgorithms = z2;
    }

    @Override // org.bouncycastle.jsse.provider.JcaAlgorithmDecomposer, org.bouncycastle.jsse.provider.AlgorithmDecomposer
    public Set<String> decompose(String str) {
        CipherSuiteInfo cipherSuiteInfo;
        return (!str.startsWith("TLS_") || (cipherSuiteInfo = ProvSSLContextSpi.getCipherSuiteInfo(str)) == null || CipherSuite.isSCSV(cipherSuiteInfo.getCipherSuite())) ? super.decompose(str) : this.enableTLSAlgorithms ? cipherSuiteInfo.getDecompositionTLS() : cipherSuiteInfo.getDecompositionX509();
    }
}
