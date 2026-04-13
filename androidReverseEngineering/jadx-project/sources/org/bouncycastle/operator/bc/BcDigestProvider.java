package org.bouncycastle.operator.bc;

import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.crypto.ExtendedDigest;

/* loaded from: classes.dex */
public interface BcDigestProvider {
    ExtendedDigest get(AlgorithmIdentifier algorithmIdentifier);
}
