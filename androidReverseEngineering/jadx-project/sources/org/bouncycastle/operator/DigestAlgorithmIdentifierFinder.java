package org.bouncycastle.operator;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

/* loaded from: classes.dex */
public interface DigestAlgorithmIdentifierFinder {
    AlgorithmIdentifier find(String str);

    AlgorithmIdentifier find(ASN1ObjectIdentifier aSN1ObjectIdentifier);

    AlgorithmIdentifier find(AlgorithmIdentifier algorithmIdentifier);
}
