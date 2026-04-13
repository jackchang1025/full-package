package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERTaggedObject;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class Signature extends ASN1Object implements ASN1Choice {
    public static final int ecdsaBrainpoolP256r1Signature = 1;
    public static final int ecdsaBrainpoolP384r1Signature = 3;
    public static final int ecdsaNistP256Signature = 0;
    private static final int extension = 2;
    private final int choice;
    private final ASN1Encodable value;

    public static class Builder {
        private int choice;
        private ASN1Encodable value;

        public Signature createSignature() {
            return new Signature(this.choice, this.value);
        }

        public Builder ecdsaBrainpoolP256r1Signature(EcdsaP256Signature ecdsaP256Signature) {
            this.choice = 1;
            this.value = ecdsaP256Signature;
            return this;
        }

        public Builder ecdsaBrainpoolP384r1Signature(EcdsaP384Signature ecdsaP384Signature) {
            this.choice = 3;
            this.value = ecdsaP384Signature;
            return this;
        }

        public Builder ecdsaNistP256Signature(EcdsaP256Signature ecdsaP256Signature) {
            this.choice = 0;
            this.value = ecdsaP256Signature;
            return this;
        }
    }

    public Signature(int i2, ASN1Encodable aSN1Encodable) {
        this.choice = i2;
        this.value = aSN1Encodable;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Signature getInstance(Object obj) {
        ASN1Encodable ecdsaP256Signature;
        if (obj instanceof Signature) {
            return (Signature) obj;
        }
        ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance(obj);
        int tagNo = aSN1TaggedObject.getTagNo();
        if (tagNo == 0 || tagNo == 1) {
            ecdsaP256Signature = EcdsaP256Signature.getInstance(aSN1TaggedObject.getObject());
        } else if (tagNo == 2) {
            ecdsaP256Signature = ASN1OctetString.getInstance(aSN1TaggedObject.getObject());
        } else {
            if (tagNo != 3) {
                throw new IllegalStateException(AbstractC0413b.m1013g(aSN1TaggedObject, new StringBuilder("unknown choice ")));
            }
            ecdsaP256Signature = EcdsaP384Signature.getInstance(aSN1TaggedObject.getObject());
        }
        return new Signature(aSN1TaggedObject.getTagNo(), ecdsaP256Signature);
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
