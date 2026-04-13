package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERTaggedObject;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class IssuerIdentifier extends ASN1Object implements ASN1Choice {
    public static final int extension = 2;
    public static final int self = 1;
    public static final int sha256AndDigest = 0;
    public static final int sha384AndDigest = 3;
    private final int choice;
    private final ASN1Encodable value;

    public static class Builder {
        public int choice;
        public ASN1Encodable value;

        public IssuerIdentifier createIssuerIdentifier() {
            return new IssuerIdentifier(this.choice, this.value);
        }

        public Builder extension(byte[] bArr) {
            this.choice = 2;
            this.value = new DEROctetString(bArr);
            return this;
        }

        public Builder self(HashAlgorithm hashAlgorithm) {
            this.choice = 1;
            this.value = hashAlgorithm;
            return this;
        }

        public Builder setChoice(int i2) {
            this.choice = i2;
            return this;
        }

        public Builder setValue(ASN1Encodable aSN1Encodable) {
            this.value = aSN1Encodable;
            return this;
        }

        public Builder sha256AndDigest(HashedId hashedId) {
            this.choice = 0;
            this.value = hashedId;
            return this;
        }

        public Builder sha384AndDigest(HashedId hashedId) {
            this.choice = 3;
            this.value = hashedId;
            return this;
        }
    }

    public IssuerIdentifier(int i2, ASN1Encodable aSN1Encodable) {
        this.choice = i2;
        this.value = aSN1Encodable;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static IssuerIdentifier getInstance(Object obj) {
        if (obj instanceof IssuerIdentifier) {
            return (IssuerIdentifier) obj;
        }
        ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance(obj);
        int tagNo = aSN1TaggedObject.getTagNo();
        if (tagNo == 0) {
            return new IssuerIdentifier(0, HashedId.getInstance(aSN1TaggedObject.getObject()));
        }
        if (tagNo == 1) {
            return new IssuerIdentifier(1, HashAlgorithm.getInstance(aSN1TaggedObject.getObject()));
        }
        if (tagNo == 2) {
            return new IssuerIdentifier(2, ASN1OctetString.getInstance(aSN1TaggedObject.getObject()));
        }
        if (tagNo == 3) {
            return new IssuerIdentifier(3, HashedId.getInstance(aSN1TaggedObject.getObject()));
        }
        throw new IllegalArgumentException(AbstractC0000a.m11g("unable to decode into known choice", tagNo));
    }

    public int getChoice() {
        return this.choice;
    }

    public ASN1Encodable getValue() {
        return this.value;
    }

    public boolean isSelf() {
        return this.choice == 1;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return new DERTaggedObject(this.choice, this.value);
    }
}
