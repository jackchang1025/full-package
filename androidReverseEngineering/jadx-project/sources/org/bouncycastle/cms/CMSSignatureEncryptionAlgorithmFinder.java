package org.bouncycastle.cms;

import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

/* loaded from: classes.dex */
public interface CMSSignatureEncryptionAlgorithmFinder {
    AlgorithmIdentifier findEncryptionAlgorithm(AlgorithmIdentifier algorithmIdentifier);
}
