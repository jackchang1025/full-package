package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Integer;

/* loaded from: classes.dex */
public class ExplicitCertificate extends CertificateBase {
    public ExplicitCertificate(ASN1Integer aSN1Integer, IssuerIdentifier issuerIdentifier, ToBeSignedCertificate toBeSignedCertificate, Signature signature) {
        super(aSN1Integer, CertificateType.Explicit, issuerIdentifier, toBeSignedCertificate, signature);
    }
}
