package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;

/* loaded from: classes.dex */
public class PKRecipientInfo extends ASN1Object {
    private final EncryptedDataEncryptionKey encKey;
    private final HashedId recipientId;

    public static class Builder {
        private EncryptedDataEncryptionKey encKey;
        private HashedId recipientId;

        public PKRecipientInfo createPKRecipientInfo() {
            return new PKRecipientInfo(this.recipientId, this.encKey);
        }

        public Builder setEncKey(EncryptedDataEncryptionKey encryptedDataEncryptionKey) {
            this.encKey = encryptedDataEncryptionKey;
            return this;
        }

        public Builder setRecipientId(HashedId hashedId) {
            this.recipientId = hashedId;
            return this;
        }
    }

    public PKRecipientInfo(HashedId hashedId, EncryptedDataEncryptionKey encryptedDataEncryptionKey) {
        this.recipientId = hashedId;
        this.encKey = encryptedDataEncryptionKey;
    }

    public static PKRecipientInfo getInstance(Object obj) {
        if (obj instanceof PKRecipientInfo) {
            return (PKRecipientInfo) obj;
        }
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(obj);
        return new PKRecipientInfo(HashedId.getInstance(aSN1Sequence.getObjectAt(0)), EncryptedDataEncryptionKey.getInstance(aSN1Sequence.getObjectAt(0)));
    }

    public EncryptedDataEncryptionKey getEncKey() {
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
