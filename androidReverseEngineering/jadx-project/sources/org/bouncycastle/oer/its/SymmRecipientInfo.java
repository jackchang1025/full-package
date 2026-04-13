package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;

/* loaded from: classes.dex */
public class SymmRecipientInfo extends ASN1Object {
    private final SymmetricCiphertext encKey;
    private final HashedId recipientId;

    public SymmRecipientInfo(HashedId hashedId, SymmetricCiphertext symmetricCiphertext) {
        this.recipientId = hashedId;
        this.encKey = symmetricCiphertext;
    }

    public SymmetricCiphertext getEncKey() {
        return this.encKey;
    }

    public HashedId getRecipientId() {
        return this.recipientId;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(this.recipientId, this.encKey);
    }
}
