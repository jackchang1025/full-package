package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;

/* loaded from: classes.dex */
public class CircularRegion extends ASN1Object implements RegionInterface {
    private final TwoDLocation center;
    private final Uint16 radius;

    public static class Builder {
        private TwoDLocation center;
        private Uint16 radius;

        public CircularRegion createCircularRegion() {
            return new CircularRegion(this.center, this.radius);
        }

        public Builder setCenter(TwoDLocation twoDLocation) {
            this.center = twoDLocation;
            return this;
        }

        public Builder setRadius(Uint16 uint16) {
            this.radius = uint16;
            return this;
        }
    }

    public CircularRegion(TwoDLocation twoDLocation, Uint16 uint16) {
        this.center = twoDLocation;
        this.radius = uint16;
    }

    public static CircularRegion getInstance(Object obj) {
        if (obj instanceof CircularRegion) {
            return (CircularRegion) obj;
        }
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(obj);
        return new CircularRegion(TwoDLocation.getInstance(aSN1Sequence.getObjectAt(0)), Uint16.getInstance(aSN1Sequence.getObjectAt(1)));
    }

    public TwoDLocation getCenter() {
        return this.center;
    }

    public Uint16 getRadius() {
        return this.radius;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(this.center, this.radius);
    }
}
