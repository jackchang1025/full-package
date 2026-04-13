package org.bouncycastle.operator;

import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cert.X509CertificateHolder;

/* loaded from: classes.dex */
public interface ContentVerifierProvider {
    ContentVerifier get(AlgorithmIdentifier algorithmIdentifier);

    X509CertificateHolder getAssociatedCertificate();

    boolean hasAssociatedCertificate();
}
