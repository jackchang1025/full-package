package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;

/* loaded from: classes.dex */
public class SignedDataPayload extends ASN1Object {
    private final Ieee1609Dot2Data data;
    private final HashedData extDataHash;

    public static class Builder {
        private Ieee1609Dot2Data data;
        private HashedData extDataHash;

        public SignedDataPayload build() {
            return new SignedDataPayload(this.data, this.extDataHash);
        }

        public Builder setData(Ieee1609Dot2Data ieee1609Dot2Data) {
            this.data = ieee1609Dot2Data;
            return this;
        }

        public Builder setExtDataHash(HashedData hashedData) {
            this.extDataHash = hashedData;
            return this;
        }
    }

    public SignedDataPayload(Ieee1609Dot2Data ieee1609Dot2Data, HashedData hashedData) {
        this.data = ieee1609Dot2Data;
        this.extDataHash = hashedData;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static SignedDataPayload getInstance(Object obj) {
        if (obj instanceof SignedDataPayload) {
            return (SignedDataPayload) obj;
        }
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(obj);
        return new SignedDataPayload(Ieee1609Dot2Data.getInstance(aSN1Sequence.getObjectAt(0)), HashedData.getInstance(aSN1Sequence.getObjectAt(1)));
    }

    public Ieee1609Dot2Data getData() {
        return this.data;
    }

    public HashedData getExtDataHash() {
        return this.extDataHash;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return new DERSequence(new ASN1EncodableVector());
    }
}
