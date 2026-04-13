package org.bouncycastle.cert.crmf;

import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

/* loaded from: classes.dex */
public interface PKMACValuesCalculator {
    byte[] calculateDigest(byte[] bArr);

    byte[] calculateMac(byte[] bArr, byte[] bArr2);

    void setup(AlgorithmIdentifier algorithmIdentifier, AlgorithmIdentifier algorithmIdentifier2);
}
