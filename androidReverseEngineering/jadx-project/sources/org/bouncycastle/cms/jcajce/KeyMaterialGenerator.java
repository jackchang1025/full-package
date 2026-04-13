package org.bouncycastle.cms.jcajce;

import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

/* loaded from: classes.dex */
interface KeyMaterialGenerator {
    byte[] generateKDFMaterial(AlgorithmIdentifier algorithmIdentifier, int i2, byte[] bArr);
}
