package org.bouncycastle.pkcs;

import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

/* loaded from: classes.dex */
public interface PKCS12MacCalculatorBuilderProvider {
    PKCS12MacCalculatorBuilder get(AlgorithmIdentifier algorithmIdentifier);
}
