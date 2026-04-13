package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Enumerated;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;

/* loaded from: classes.dex */
public class HashAlgorithm extends ASN1Object {
    private final ASN1Enumerated enumerated;
    public static final HashAlgorithm sha256 = new HashAlgorithm(0);
    public static final HashAlgorithm sha384 = new HashAlgorithm(1);
    public static final HashAlgorithm extension = new HashAlgorithm(2);

    public HashAlgorithm(int i2) {
        this.enumerated = new ASN1Enumerated(i2);
    }

    public static HashAlgorithm getInstance(Object obj) {
        if (obj == null) {
            return null;
        }
        return obj instanceof HashAlgorithm ? (HashAlgorithm) obj : new HashAlgorithm(ASN1Enumerated.getInstance(obj));
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return this.enumerated;
    }

    private HashAlgorithm(ASN1Enumerated aSN1Enumerated) {
        this.enumerated = aSN1Enumerated;
    }
}
