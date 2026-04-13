package org.bouncycastle.cms.jcajce;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.cms.ecc.ECCCMSSharedInfo;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.util.Pack;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
class RFC5753KeyMaterialGenerator implements KeyMaterialGenerator {
    @Override // org.bouncycastle.cms.jcajce.KeyMaterialGenerator
    public byte[] generateKDFMaterial(AlgorithmIdentifier algorithmIdentifier, int i2, byte[] bArr) {
        try {
            return new ECCCMSSharedInfo(algorithmIdentifier, bArr, Pack.intToBigEndian(i2)).getEncoded(ASN1Encoding.DER);
        } catch (IOException e2) {
            throw new IllegalStateException(AbstractC0000a.m13i("Unable to create KDF material: ", e2));
        }
    }
}
