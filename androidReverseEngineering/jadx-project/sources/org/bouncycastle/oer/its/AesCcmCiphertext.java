package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;

/* loaded from: classes.dex */
public class AesCcmCiphertext extends ASN1Object {
    private final ASN1OctetString nonce;
    private final SequenceOfOctetString opaque;

    public static class Builder {
        private ASN1OctetString nonce;
        private SequenceOfOctetString opaque;

        public AesCcmCiphertext createAesCcmCiphertext() {
            return new AesCcmCiphertext(this.nonce, this.opaque);
        }

        public Builder setNonce(ASN1OctetString aSN1OctetString) {
            this.nonce = aSN1OctetString;
            return this;
        }

        public Builder setOpaque(SequenceOfOctetString sequenceOfOctetString) {
            this.opaque = sequenceOfOctetString;
            return this;
        }
    }

    public AesCcmCiphertext(ASN1OctetString aSN1OctetString, SequenceOfOctetString sequenceOfOctetString) {
        this.nonce = aSN1OctetString;
        this.opaque = sequenceOfOctetString;
    }

    public static AesCcmCiphertext getInstance(Object obj) {
        if (obj instanceof AesCcmCiphertext) {
            return (AesCcmCiphertext) obj;
        }
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(obj);
        return new Builder().setNonce(ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(0))).setOpaque(SequenceOfOctetString.getInstance(aSN1Sequence.getObjectAt(1))).createAesCcmCiphertext();
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(this.nonce, this.opaque);
    }
}
