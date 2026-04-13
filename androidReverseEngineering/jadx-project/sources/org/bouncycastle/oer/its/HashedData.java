package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERTaggedObject;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class HashedData extends ASN1Object implements ASN1Choice {
    public static final int extension = 1;
    public static final int reserved = 3;
    public static final int sha256HashedData = 0;
    public static final int sha384HashedData = 2;
    private final int choice;
    private final ASN1Encodable value;

    public static class Builder {
        private int choice;
        private ASN1Encodable value;

        public HashedData createHashedData() {
            return new HashedData(this.choice, this.value);
        }

        public Builder extension(byte[] bArr) {
            this.value = new DEROctetString(bArr);
            return this;
        }

        public Builder reserved(ASN1OctetString aSN1OctetString) {
            this.value = aSN1OctetString;
            return this;
        }

        public Builder setChoice(int i2) {
            this.choice = i2;
            return this;
        }

        public Builder setSha256HashedData(ASN1Encodable aSN1Encodable) {
            this.value = aSN1Encodable;
            return this;
        }

        public Builder sha384HashedData(ASN1OctetString aSN1OctetString) {
            this.value = aSN1OctetString;
            return this;
        }
    }

    public HashedData(int i2, ASN1Encodable aSN1Encodable) {
        this.choice = i2;
        this.value = aSN1Encodable;
    }

    public static HashedData getInstance(Object obj) {
        if (obj instanceof HashedData) {
            return (HashedData) obj;
        }
        ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance(obj);
        int tagNo = aSN1TaggedObject.getTagNo();
        if (tagNo == 0 || tagNo == 1 || tagNo == 2 || tagNo == 3) {
            return new HashedData(aSN1TaggedObject.getTagNo(), ASN1OctetString.getInstance(aSN1TaggedObject.getObject()));
        }
        throw new IllegalStateException(AbstractC0413b.m1013g(aSN1TaggedObject, new StringBuilder("unknown choice value ")));
    }

    public int getChoice() {
        return this.choice;
    }

    public ASN1Encodable getValue() {
        return this.value;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return new DERTaggedObject(this.choice, this.value);
    }
}
