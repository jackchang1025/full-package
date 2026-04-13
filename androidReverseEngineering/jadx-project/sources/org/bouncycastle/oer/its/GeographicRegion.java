package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERTaggedObject;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class GeographicRegion extends ASN1Object implements ASN1Choice {
    public static final int circularRegion = 0;
    public static final int extension = 4;
    public static final int identifiedRegion = 3;
    public static final int polygonalRegion = 2;
    public static final int rectangularRegion = 1;
    private int choice;
    private ASN1Encodable region;

    public GeographicRegion(int i2, ASN1Encodable aSN1Encodable) {
        this.choice = i2;
        this.region = aSN1Encodable;
    }

    public static GeographicRegion getInstance(Object obj) {
        if (obj instanceof GeographicRegion) {
            return (GeographicRegion) obj;
        }
        ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance(obj);
        int tagNo = aSN1TaggedObject.getTagNo();
        if (tagNo == 0) {
            return new GeographicRegion(tagNo, CircularRegion.getInstance(aSN1TaggedObject.getObject()));
        }
        if (tagNo == 1) {
            return new GeographicRegion(tagNo, SequenceOfRectangularRegion.getInstance(aSN1TaggedObject.getObject()));
        }
        if (tagNo == 2) {
            return new GeographicRegion(tagNo, PolygonalRegion.getInstance(aSN1TaggedObject.getObject()));
        }
        if (tagNo == 3) {
            return new GeographicRegion(tagNo, SequenceOfIdentifiedRegion.getInstance(aSN1TaggedObject.getObject()));
        }
        if (tagNo == 4) {
            return new GeographicRegion(tagNo, ASN1OctetString.getInstance(aSN1TaggedObject.getObject()));
        }
        throw new IllegalStateException(AbstractC0000a.m11g("unknown region choice ", tagNo));
    }

    public int getChoice() {
        return this.choice;
    }

    public ASN1Encodable getRegion() {
        return this.region;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return new DERTaggedObject(this.choice, this.region);
    }
}
