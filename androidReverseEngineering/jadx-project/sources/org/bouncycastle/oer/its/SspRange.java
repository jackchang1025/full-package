package org.bouncycastle.oer.its;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Null;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERTaggedObject;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class SspRange extends ASN1Object implements ASN1Choice {
    private static final int all = 1;
    private static final int bitmapSspRange = 3;
    private static final int extension = 2;
    private static final int opaque = 0;
    private final int choice;
    private final ASN1Encodable value;

    public static class Builder {
        private int choice;
        private ASN1Encodable value;

        public Builder all() {
            this.value = DERNull.INSTANCE;
            this.choice = 0;
            return this;
        }

        public Builder bitmapSSPRange(BitmapSspRange bitmapSspRange) {
            this.value = bitmapSspRange;
            this.choice = 3;
            return this;
        }

        public SspRange createSspRange() {
            return new SspRange(this.choice, this.value);
        }

        public Builder extension(byte[] bArr) {
            this.value = new DEROctetString(bArr);
            this.choice = 2;
            return this;
        }

        public Builder opaque(SequenceOfOctetString sequenceOfOctetString) {
            this.value = sequenceOfOctetString;
            this.choice = 0;
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

    public SspRange(int i2, ASN1Encodable aSN1Encodable) {
        if (i2 != 0) {
            if (i2 != 1) {
                if (i2 != 2) {
                    if (i2 == 3 && !(aSN1Encodable instanceof BitmapSspRange)) {
                        throw new IllegalArgumentException("value is not BitmapSspRange");
                    }
                } else if (!(aSN1Encodable instanceof ASN1OctetString)) {
                    throw new IllegalArgumentException("value is not ASN1OctetString");
                }
            } else if (!(aSN1Encodable instanceof ASN1Null)) {
                throw new IllegalArgumentException("value is not ASN1Null");
            }
        } else if (!(aSN1Encodable instanceof SequenceOfOctetString)) {
            throw new IllegalArgumentException("value is not SequenceOfOctetString");
        }
        this.choice = i2;
        this.value = aSN1Encodable;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static SspRange getInstance(Object obj) {
        if (obj instanceof SspRange) {
            return (SspRange) obj;
        }
        ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance(obj);
        int tagNo = aSN1TaggedObject.getTagNo();
        if (tagNo == 0) {
            return new SspRange(0, SequenceOfOctetString.getInstance(aSN1TaggedObject.getObject()));
        }
        if (tagNo == 1) {
            return new SspRange(1, DERNull.INSTANCE);
        }
        if (tagNo != 2) {
            if (tagNo == 3) {
                return new SspRange(3, BitmapSspRange.getInstance(aSN1TaggedObject.getObject()));
            }
            throw new IllegalStateException(AbstractC0000a.m11g("unknown choice ", tagNo));
        }
        try {
            return new SspRange(2, new DEROctetString(aSN1TaggedObject.getObject().getEncoded()));
        } catch (IOException e2) {
            throw new RuntimeException(e2.getMessage(), e2);
        }
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return new DERTaggedObject(this.choice, this.value);
    }
}
