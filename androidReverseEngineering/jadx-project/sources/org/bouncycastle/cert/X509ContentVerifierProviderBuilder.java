package org.bouncycastle.cert;

import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.operator.ContentVerifierProvider;

/* loaded from: classes.dex */
public interface X509ContentVerifierProviderBuilder {
    ContentVerifierProvider build(SubjectPublicKeyInfo subjectPublicKeyInfo);

    ContentVerifierProvider build(X509CertificateHolder x509CertificateHolder);
}
