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
public class Ieee1609Dot2Content extends ASN1Object implements ASN1Choice {
    public static final int encryptedData = 2;
    public static final int extension = 4;
    public static final int signedCertificateRequest = 3;
    public static final int signedData = 1;
    public static final int unsecuredData = 0;
    private final int choice;
    private final ASN1Encodable object;

    public static class Builder {
        private int choice;
        private ASN1Encodable object;

        public Ieee1609Dot2Content build() {
            return new Ieee1609Dot2Content(this.choice, this.object);
        }

        public Builder encryptedData(EncryptedData encryptedData) {
            this.object = encryptedData;
            this.choice = 2;
            return this;
        }

        public Builder extension(byte[] bArr) {
            this.object = new DEROctetString(bArr);
            this.choice = 4;
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

        public Builder signedCertificateRequest(ASN1OctetString aSN1OctetString) {
            this.object = aSN1OctetString;
            this.choice = 3;
            return this;
        }

        public Builder signedData(SignedData signedData) {
            this.object = signedData;
            this.choice = 1;
            return this;
        }

        public Builder unsecuredData(ASN1OctetString aSN1OctetString) {
            this.object = aSN1OctetString;
            this.choice = 0;
            return this;
        }
    }

    public Ieee1609Dot2Content(int i2, ASN1Encodable aSN1Encodable) {
        this.choice = i2;
        this.object = aSN1Encodable;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Ieee1609Dot2Content getInstance(Object obj) {
        if (obj instanceof Ieee1609Dot2Content) {
            return (Ieee1609Dot2Content) obj;
        }
        ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance(obj);
        int tagNo = aSN1TaggedObject.getTagNo();
        if (tagNo != 0) {
            if (tagNo == 1) {
                return new Ieee1609Dot2Content(aSN1TaggedObject.getTagNo(), SignedData.getInstance(aSN1TaggedObject.getObject()));
            }
            if (tagNo == 2) {
                return new Ieee1609Dot2Content(aSN1TaggedObject.getTagNo(), EncryptedData.getInstance(aSN1TaggedObject.getObject()));
            }
            if (tagNo != 3 && tagNo != 4) {
                throw new IllegalArgumentException(AbstractC0413b.m1013g(aSN1TaggedObject, new StringBuilder("unknown tag value ")));
            }
        }
        return new Ieee1609Dot2Content(aSN1TaggedObject.getTagNo(), ASN1OctetString.getInstance(aSN1TaggedObject.getObject()));
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
