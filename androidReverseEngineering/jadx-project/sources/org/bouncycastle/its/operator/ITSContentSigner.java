package org.bouncycastle.its.operator;

import java.io.OutputStream;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.its.ITSCertificate;

/* loaded from: classes.dex */
public interface ITSContentSigner {
    ITSCertificate getAssociatedCertificate();

    byte[] getAssociatedCertificateDigest();

    AlgorithmIdentifier getDigestAlgorithm();

    OutputStream getOutputStream();

    byte[] getSignature();

    boolean isForSelfSigning();
}
