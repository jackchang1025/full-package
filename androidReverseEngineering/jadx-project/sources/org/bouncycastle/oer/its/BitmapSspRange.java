package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;

/* loaded from: classes.dex */
public class BitmapSspRange extends ASN1Object {
    private final ASN1OctetString sspBitmask;
    private final ASN1OctetString sspValue;

    public BitmapSspRange(ASN1OctetString aSN1OctetString, ASN1OctetString aSN1OctetString2) {
        this.sspValue = aSN1OctetString;
        this.sspBitmask = aSN1OctetString2;
    }

    public static BitmapSspRange getInstance(Object obj) {
        if (obj instanceof BitmapSspRange) {
            return (BitmapSspRange) obj;
        }
        if (obj == null) {
            return null;
        }
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(obj);
        return new BitmapSspRange(ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(0)), ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(1)));
    }

    public ASN1OctetString getSspBitmask() {
        return this.sspBitmask;
    }

    public ASN1OctetString getSspValue() {
        return this.sspValue;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(this.sspValue, this.sspBitmask);
    }
}
