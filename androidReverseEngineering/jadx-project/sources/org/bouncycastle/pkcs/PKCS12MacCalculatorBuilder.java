package org.bouncycastle.pkcs;

import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.operator.MacCalculator;

/* loaded from: classes.dex */
public interface PKCS12MacCalculatorBuilder {
    MacCalculator build(char[] cArr);

    AlgorithmIdentifier getDigestAlgorithmIdentifier();
}
