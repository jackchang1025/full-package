package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;

/* loaded from: classes.dex */
public class EncryptedData extends ASN1Object {
    private final SymmetricCiphertext ciphertext;
    private final SequenceOfRecipientInfo recipients;

    public static class Builder {
        private SymmetricCiphertext ciphertext;
        private SequenceOfRecipientInfo recipients;

        public EncryptedData createEncryptedData() {
            return new EncryptedData(this.recipients, this.ciphertext);
        }

        public Builder setCiphertext(SymmetricCiphertext symmetricCiphertext) {
            this.ciphertext = symmetricCiphertext;
            return this;
        }

        public Builder setRecipients(SequenceOfRecipientInfo sequenceOfRecipientInfo) {
            this.recipients = sequenceOfRecipientInfo;
            return this;
        }
    }

    public EncryptedData(SequenceOfRecipientInfo sequenceOfRecipientInfo, SymmetricCiphertext symmetricCiphertext) {
        this.recipients = sequenceOfRecipientInfo;
        this.ciphertext = symmetricCiphertext;
    }

    public static EncryptedData getInstance(Object obj) {
        if (obj == null || (obj instanceof EncryptedData)) {
            return (EncryptedData) obj;
        }
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(obj);
        return new EncryptedData(SequenceOfRecipientInfo.getInstance(aSN1Sequence.getObjectAt(0)), SymmetricCiphertext.getInstance(aSN1Sequence.getObjectAt(1)));
    }

    public SymmetricCiphertext getCiphertext() {
        return this.ciphertext;
    }

    public SequenceOfRecipientInfo getRecipients() {
        return this.recipients;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(this.recipients, this.ciphertext);
    }
}
