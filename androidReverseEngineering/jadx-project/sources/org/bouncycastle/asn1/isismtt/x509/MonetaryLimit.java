package org.bouncycastle.asn1.isismtt.x509;

import java.math.BigInteger;
import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1PrintableString;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERPrintableString;
import org.bouncycastle.asn1.DERSequence;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class MonetaryLimit extends ASN1Object {
    ASN1Integer amount;
    ASN1PrintableString currency;
    ASN1Integer exponent;

    public MonetaryLimit(String str, int i2, int i3) {
        this.currency = new DERPrintableString(str, true);
        this.amount = new ASN1Integer(i2);
        this.exponent = new ASN1Integer(i3);
    }

    public static MonetaryLimit getInstance(Object obj) {
        if (obj == null || (obj instanceof MonetaryLimit)) {
            return (MonetaryLimit) obj;
        }
        if (obj instanceof ASN1Sequence) {
            return new MonetaryLimit(ASN1Sequence.getInstance(obj));
        }
        throw new IllegalArgumentException("unknown object in getInstance");
    }

    public BigInteger getAmount() {
        return this.amount.getValue();
    }

    public String getCurrency() {
        return this.currency.getString();
    }

    public BigInteger getExponent() {
        return this.exponent.getValue();
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(3);
        aSN1EncodableVector.add(this.currency);
        aSN1EncodableVector.add(this.amount);
        aSN1EncodableVector.add(this.exponent);
        return new DERSequence(aSN1EncodableVector);
    }

    private MonetaryLimit(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() != 3) {
            throw new IllegalArgumentException(AbstractC0413b.m1012f(aSN1Sequence, new StringBuilder("Bad sequence size: ")));
        }
        Enumeration objects = aSN1Sequence.getObjects();
        this.currency = ASN1PrintableString.getInstance(objects.nextElement());
        this.amount = ASN1Integer.getInstance(objects.nextElement());
        this.exponent = ASN1Integer.getInstance(objects.nextElement());
    }
}
