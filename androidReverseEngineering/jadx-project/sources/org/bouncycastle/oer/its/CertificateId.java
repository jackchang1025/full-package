package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERTaggedObject;

/* loaded from: classes.dex */
public class CertificateId extends ASN1Object implements ASN1Choice {
    public static final int binaryId = 2;
    public static final int extension = 4;
    public static final int linkageData = 0;
    public static final int name = 1;
    public static final int none = 3;
    private final int choice;
    private final ASN1Encodable value;

    public static class Builder {
        private int choice;
        private ASN1Encodable value;

        public Builder binaryId(DEROctetString dEROctetString) {
            this.choice = 1;
            this.value = dEROctetString;
            return this;
        }

        public CertificateId createCertificateId() {
            return new CertificateId(this.choice, this.value);
        }

        public Builder extension(byte[] bArr) {
            this.choice = 4;
            this.value = new DEROctetString(bArr);
            return this;
        }

        public Builder linkageData(LinkageData linkageData) {
            this.choice = 0;
            this.value = linkageData;
            return this;
        }

        public Builder name(Hostname hostname) {
            this.choice = 1;
            this.value = hostname;
            return this;
        }

        public Builder none() {
            this.choice = 1;
            this.value = DERNull.INSTANCE;
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
    }

    public CertificateId(int i2, ASN1Encodable aSN1Encodable) {
        this.choice = i2;
        this.value = aSN1Encodable;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static CertificateId getInstance(Object obj) {
        if (obj instanceof CertificateId) {
            return (CertificateId) obj;
        }
        ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance(obj);
        int tagNo = aSN1TaggedObject.getTagNo();
        if (tagNo == 0) {
            return new CertificateId(tagNo, LinkageData.getInstance(aSN1TaggedObject.getObject()));
        }
        if (tagNo == 1) {
            return new CertificateId(tagNo, Hostname.getInstance(aSN1TaggedObject.getObject()));
        }
        if (tagNo != 2) {
            if (tagNo == 3) {
                return new CertificateId(tagNo, aSN1TaggedObject.getObject());
            }
            if (tagNo != 4) {
                throw new IllegalArgumentException("unknown choice in CertificateId");
            }
        }
        return new CertificateId(tagNo, ASN1OctetString.getInstance(aSN1TaggedObject.getObject()));
    }

    public int getChoice() {
        return this.choice;
    }

    public ASN1Encodable getValue() {
        return this.value;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return new DERTaggedObject(this.choice, this.value).toASN1Primitive();
    }
}
