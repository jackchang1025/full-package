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
public class PublicVerificationKey extends ASN1Object implements ASN1Choice {
    public static final int ecdsaBrainpoolP256r1 = 1;
    public static final int ecdsaBrainpoolP384r1 = 3;
    public static final int ecdsaNistP256 = 0;
    public static final int extension = 2;
    final int choice;
    final ASN1Encodable curvePoint;

    public static class Builder {
        private int choice;
        private ASN1Encodable curvePoint;

        public PublicVerificationKey createPublicVerificationKey() {
            return new PublicVerificationKey(this.choice, this.curvePoint);
        }

        public Builder ecdsaBrainpoolP256r1(EccP256CurvePoint eccP256CurvePoint) {
            this.curvePoint = eccP256CurvePoint;
            return this;
        }

        public Builder ecdsaBrainpoolP384r1(EccP384CurvePoint eccP384CurvePoint) {
            this.curvePoint = eccP384CurvePoint;
            return this;
        }

        public Builder ecdsaNistP256(EccP256CurvePoint eccP256CurvePoint) {
            this.curvePoint = eccP256CurvePoint;
            return this;
        }

        public Builder extension(byte[] bArr) {
            this.curvePoint = new DEROctetString(bArr);
            return this;
        }

        public Builder setChoice(int i2) {
            this.choice = i2;
            return this;
        }

        public Builder setCurvePoint(EccCurvePoint eccCurvePoint) {
            this.curvePoint = eccCurvePoint;
            return this;
        }
    }

    public PublicVerificationKey(int i2, ASN1Encodable aSN1Encodable) {
        this.choice = i2;
        this.curvePoint = aSN1Encodable;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static PublicVerificationKey getInstance(Object obj) {
        ASN1Encodable eccP256CurvePoint;
        if (obj instanceof PublicVerificationKey) {
            return (PublicVerificationKey) obj;
        }
        ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance(obj);
        int tagNo = aSN1TaggedObject.getTagNo();
        if (tagNo == 0 || tagNo == 1) {
            eccP256CurvePoint = EccP256CurvePoint.getInstance(aSN1TaggedObject.getObject());
        } else if (tagNo == 2) {
            eccP256CurvePoint = ASN1OctetString.getInstance(aSN1TaggedObject.getObject());
        } else {
            if (tagNo != 3) {
                throw new IllegalArgumentException(AbstractC0413b.m1013g(aSN1TaggedObject, new StringBuilder("unknown tag value ")));
            }
            eccP256CurvePoint = EccP384CurvePoint.getInstance(aSN1TaggedObject.getObject());
        }
        return new PublicVerificationKey(aSN1TaggedObject.getTagNo(), eccP256CurvePoint);
    }

    public int getChoice() {
        return this.choice;
    }

    public ASN1Encodable getCurvePoint() {
        return this.curvePoint;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return new DERTaggedObject(this.choice, this.curvePoint);
    }
}
