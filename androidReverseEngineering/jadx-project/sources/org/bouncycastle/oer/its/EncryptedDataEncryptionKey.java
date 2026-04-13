package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERTaggedObject;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class EncryptedDataEncryptionKey extends ASN1Object implements ASN1Choice {
    public static final int eciesBrainpoolP256r1 = 1;
    public static final int eciesNistP256 = 0;
    public static final int extension = 2;
    private final int choice;
    private final ASN1Encodable value;

    public static class Builder {
        private int choice;
        private ASN1Encodable value;

        public EncryptedDataEncryptionKey createEncryptedDataEncryptionKey() {
            return new EncryptedDataEncryptionKey(this.choice, this.value);
        }

        public Builder setChoice(int i2) {
            this.choice = i2;
            return this;
        }

        public Builder setValue(ASN1Encodable aSN1Encodable) {
            this.value = aSN1Encodable;
            return this;
        }
    }

    public EncryptedDataEncryptionKey(int i2, ASN1Encodable aSN1Encodable) {
        this.choice = i2;
        this.value = aSN1Encodable;
    }

    public static EncryptedDataEncryptionKey getInstance(Object obj) {
        if (obj instanceof EncryptedDataEncryptionKey) {
            return (EncryptedDataEncryptionKey) obj;
        }
        ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance(obj);
        int tagNo = aSN1TaggedObject.getTagNo();
        if (tagNo == 0 || tagNo == 1) {
            return new Builder().setChoice(aSN1TaggedObject.getTagNo()).setValue(aSN1TaggedObject.getObject()).createEncryptedDataEncryptionKey();
        }
        throw new IllegalStateException(AbstractC0413b.m1013g(aSN1TaggedObject, new StringBuilder("unknown choice ")));
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
