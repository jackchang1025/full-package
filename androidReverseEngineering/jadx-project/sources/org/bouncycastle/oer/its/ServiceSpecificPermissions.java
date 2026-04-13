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
public class ServiceSpecificPermissions extends ASN1Object implements ASN1Choice {
    public static final int bitmapSsp = 2;
    public static final int extension = 1;
    public static final int opaque = 0;
    private final int choice;
    private final ASN1Encodable object;

    public static class Builder {
        private int choice;
        private ASN1Encodable object;

        public Builder bitmapSsp(ASN1OctetString aSN1OctetString) {
            return setChoice(2).setObject(aSN1OctetString);
        }

        public ServiceSpecificPermissions createServiceSpecificPermissions() {
            return new ServiceSpecificPermissions(this.choice, this.object);
        }

        public Builder extension(byte[] bArr) {
            return setChoice(2).setObject(new DEROctetString(bArr));
        }

        public Builder opaque() {
            return setChoice(0);
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

    public ServiceSpecificPermissions(int i2, ASN1Encodable aSN1Encodable) {
        this.choice = i2;
        this.object = aSN1Encodable;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static ServiceSpecificPermissions getInstance(Object obj) {
        if (obj instanceof ServiceSpecificPermissions) {
            return (ServiceSpecificPermissions) obj;
        }
        ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance(obj);
        int tagNo = aSN1TaggedObject.getTagNo();
        if (tagNo == 0 || tagNo == 1) {
            return new ServiceSpecificPermissions(aSN1TaggedObject.getTagNo(), ASN1OctetString.getInstance(aSN1TaggedObject.getObject()));
        }
        if (tagNo == 2) {
            return new ServiceSpecificPermissions(aSN1TaggedObject.getTagNo(), ASN1OctetString.getInstance(aSN1TaggedObject.getObject()));
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
