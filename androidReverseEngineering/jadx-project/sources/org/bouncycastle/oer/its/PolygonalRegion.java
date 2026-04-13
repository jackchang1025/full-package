package org.bouncycastle.oer.its;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;

/* loaded from: classes.dex */
public class PolygonalRegion extends ASN1Object implements RegionInterface {
    private final List<TwoDLocation> points;

    public static class Builder {
        private List<TwoDLocation> locations = new ArrayList();

        public PolygonalRegion createPolygonalRegion() {
            return new PolygonalRegion(this.locations);
        }

        public Builder setLocations(List<TwoDLocation> list) {
            this.locations = list;
            return this;
        }

        public Builder setLocations(TwoDLocation... twoDLocationArr) {
            this.locations.addAll(Arrays.asList(twoDLocationArr));
            return this;
        }
    }

    public PolygonalRegion(List<TwoDLocation> list) {
        this.points = Collections.unmodifiableList(list);
    }

    public static PolygonalRegion getInstance(Object obj) {
        if (obj instanceof PolygonalRegion) {
            return (PolygonalRegion) obj;
        }
        if (obj != null) {
            return new PolygonalRegion(Utils.fillList(TwoDLocation.class, ASN1Sequence.getInstance(obj)));
        }
        return null;
    }

    public List<TwoDLocation> getPoints() {
        return this.points;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(this.points);
    }
}
