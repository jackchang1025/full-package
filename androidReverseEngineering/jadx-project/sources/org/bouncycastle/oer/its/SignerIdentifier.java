package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.oer.its.HashedId;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class SignerIdentifier extends ASN1Object implements ASN1Choice {
    public static final int certificate = 1;
    public static final int digest = 0;
    public static final int extension = 3;
    public static final int self = 2;
    private final int choice;
    private final ASN1Encodable object;

    public static class Builder {
        private int choice;
        private ASN1Encodable encodable;

        public SignerIdentifier build() {
            return new SignerIdentifier(this.choice, this.encodable);
        }

        public Builder certificate(SequenceOfCertificate sequenceOfCertificate) {
            this.choice = 1;
            this.encodable = sequenceOfCertificate;
            return this;
        }

        public Builder digest(HashedId.HashedId8 hashedId8) {
            this.choice = 0;
            this.encodable = hashedId8;
            return this;
        }

        public Builder extension(byte[] bArr) {
            this.choice = 3;
            this.encodable = new DEROctetString(bArr);
            return this;
        }

        public Builder self() {
            this.choice = 2;
            this.encodable = DERNull.INSTANCE;
            return this;
        }

        public Builder setChoice(int i2) {
            this.choice = i2;
            return this;
        }

        public Builder setEncodable(ASN1Encodable aSN1Encodable) {
            this.encodable = aSN1Encodable;
            return this;
        }
    }

    public SignerIdentifier(int i2, ASN1Encodable aSN1Encodable) {
        this.choice = i2;
        this.object = aSN1Encodable;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final SignerIdentifier getInstance(Object obj) {
        if (obj instanceof SignerIdentifier) {
            return (SignerIdentifier) obj;
        }
        ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance(obj);
        int tagNo = aSN1TaggedObject.getTagNo();
        if (tagNo == 0) {
            return new SignerIdentifier(aSN1TaggedObject.getTagNo(), HashedId.getInstance(aSN1TaggedObject.getObject()));
        }
        if (tagNo == 1) {
            return new SignerIdentifier(aSN1TaggedObject.getTagNo(), SequenceOfCertificate.getInstance(aSN1TaggedObject.getObject()));
        }
        throw new IllegalArgumentException(AbstractC0413b.m1013g(aSN1TaggedObject, new StringBuilder("unknown choice ")));
    }

    public int getChoice() {
        return this.choice;
    }

    public ASN1Encodable getObject() {
        return this.object;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return new DERTaggedObject(this.choice, this.object);
    }
}
