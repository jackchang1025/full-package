package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERTaggedObject;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class Duration extends ASN1Object implements ASN1Choice {
    public static final int hours = 4;
    public static final int microseconds = 0;
    public static final int milliseconds = 1;
    public static final int minutes = 3;
    public static final int seconds = 2;
    public static final int sixtyHours = 5;
    public static final int years = 6;
    private final int tag;
    private final int value;

    public Duration(int i2, int i3) {
        this.tag = i2;
        this.value = i3;
    }

    public static Duration getInstance(Object obj) {
        if (obj instanceof Duration) {
            return (Duration) obj;
        }
        ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance(obj);
        int tagNo = aSN1TaggedObject.getTagNo();
        switch (tagNo) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                try {
                    return new Duration(tagNo, ASN1Integer.getInstance(aSN1TaggedObject.getObject()).getValue().intValue());
                } catch (Exception e2) {
                    throw new IllegalStateException(e2.getMessage(), e2);
                }
            default:
                throw new IllegalArgumentException(AbstractC0000a.m11g("invalid choice value ", tagNo));
        }
    }

    public int getTag() {
        return this.tag;
    }

    public int getValue() {
        return this.value;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return new DERTaggedObject(this.tag, new ASN1Integer(this.value));
    }
}
