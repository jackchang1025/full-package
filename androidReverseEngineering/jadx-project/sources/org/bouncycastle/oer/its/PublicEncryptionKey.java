package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;

/* loaded from: classes.dex */
public class PublicEncryptionKey extends ASN1Object {
    private final BasePublicEncryptionKey basePublicEncryptionKey;
    private final SymmAlgorithm supportedSymmAlg;

    public PublicEncryptionKey(SymmAlgorithm symmAlgorithm, BasePublicEncryptionKey basePublicEncryptionKey) {
        this.supportedSymmAlg = symmAlgorithm;
        this.basePublicEncryptionKey = basePublicEncryptionKey;
    }

    public static PublicEncryptionKey getInstance(Object obj) {
        if (obj instanceof PublicEncryptionKey) {
            return (PublicEncryptionKey) obj;
        }
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(obj);
        return new PublicEncryptionKey(SymmAlgorithm.getInstance((Object) aSN1Sequence.getObjectAt(0)), BasePublicEncryptionKey.getInstance(aSN1Sequence.getObjectAt(1)));
    }

    public BasePublicEncryptionKey getBasePublicEncryptionKey() {
        return this.basePublicEncryptionKey;
    }

    public SymmAlgorithm getSupportedSymmAlg() {
        return this.supportedSymmAlg;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(this.supportedSymmAlg, this.basePublicEncryptionKey);
    }
}
