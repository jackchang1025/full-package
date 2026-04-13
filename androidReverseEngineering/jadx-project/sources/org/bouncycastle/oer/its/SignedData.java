package org.bouncycastle.oer.its;

import java.util.Iterator;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;

/* loaded from: classes.dex */
public class SignedData extends ASN1Object {
    private final HashAlgorithm hashId;
    private final Signature signature;
    private final SignerIdentifier signer;
    private final ToBeSignedData tbsData;

    public class Builder {
        private HashAlgorithm hashId;
        private Signature signature;
        private SignerIdentifier signer;
        private ToBeSignedData tbsData;

        public Builder() {
        }

        public SignedData build() {
            return new SignedData(this.hashId, this.tbsData, this.signer, this.signature);
        }

        public Builder setHashId(HashAlgorithm hashAlgorithm) {
            this.hashId = hashAlgorithm;
            return this;
        }

        public Builder setSignature(Signature signature) {
            this.signature = signature;
            return this;
        }

        public Builder setSigner(SignerIdentifier signerIdentifier) {
            this.signer = signerIdentifier;
            return this;
        }

        public Builder setTbsData(ToBeSignedData toBeSignedData) {
            this.tbsData = toBeSignedData;
            return this;
        }
    }

    public SignedData(HashAlgorithm hashAlgorithm, ToBeSignedData toBeSignedData, SignerIdentifier signerIdentifier, Signature signature) {
        this.hashId = hashAlgorithm;
        this.tbsData = toBeSignedData;
        this.signer = signerIdentifier;
        this.signature = signature;
    }

    public static SignedData getInstance(Object obj) {
        if (obj instanceof SignedData) {
            return (SignedData) obj;
        }
        Iterator<ASN1Encodable> it = ASN1Sequence.getInstance(obj).iterator();
        return new SignedData(HashAlgorithm.getInstance(it.next()), ToBeSignedData.getInstance(it.next()), SignerIdentifier.getInstance(it.next()), Signature.getInstance(it.next()));
    }

    public Builder builder() {
        return new Builder();
    }

    public HashAlgorithm getHashId() {
        return this.hashId;
    }

    public Signature getSignature() {
        return this.signature;
    }

    public SignerIdentifier getSigner() {
        return this.signer;
    }

    public ToBeSignedData getTbsData() {
        return this.tbsData;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(this.hashId, this.tbsData, this.signer, this.signature);
    }
}
