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
public class VerificationKeyIndicator extends ASN1Object implements ASN1Choice {
    public static final int extension = 2;
    public static final int reconstructionValue = 1;
    public static final int verificationKey = 0;
    private final int choice;
    private final ASN1Encodable object;

    public static class Builder {
        private int choice;
        private ASN1Encodable object;

        public VerificationKeyIndicator createVerificationKeyIndicator() {
            return new VerificationKeyIndicator(this.choice, this.object);
        }

        public Builder extension(byte[] bArr) {
            this.object = new DEROctetString(bArr);
            this.choice = 2;
            return this;
        }

        public Builder publicVerificationKey(PublicVerificationKey publicVerificationKey) {
            this.object = publicVerificationKey;
            this.choice = 0;
            return this;
        }

        public Builder reconstructionValue(EccP256CurvePoint eccP256CurvePoint) {
            this.object = eccP256CurvePoint;
            this.choice = 1;
            return this;
        }

        public Builder setChoice(int i2) {
            this.choice = i2;
            return this;
        }

        public Builder setObject(ASN1Encodable aSN1Encodable) {
            this.object = aSN1Encodable;
            return this;
        }
    }

    public VerificationKeyIndicator(int i2, ASN1Encodable aSN1Encodable) {
        this.choice = i2;
        this.object = aSN1Encodable;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static VerificationKeyIndicator getInstance(Object obj) {
        Builder choice;
        ASN1Encodable publicVerificationKey;
        if (obj instanceof VerificationKeyIndicator) {
            return (VerificationKeyIndicator) obj;
        }
        ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance(obj);
        int tagNo = aSN1TaggedObject.getTagNo();
        if (tagNo == 0) {
            choice = new Builder().setChoice(0);
            publicVerificationKey = PublicVerificationKey.getInstance(aSN1TaggedObject.getObject());
        } else {
            if (tagNo != 1) {
                if (tagNo == 2) {
                    return new VerificationKeyIndicator(2, ASN1OctetString.getInstance(aSN1TaggedObject.getLoadedObject()));
                }
                throw new IllegalArgumentException(AbstractC0413b.m1013g(aSN1TaggedObject, new StringBuilder("unhandled tag ")));
            }
            choice = new Builder().setChoice(1);
            publicVerificationKey = EccP256CurvePoint.getInstance(aSN1TaggedObject.getObject());
        }
        return choice.setObject(publicVerificationKey).createVerificationKeyIndicator();
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
