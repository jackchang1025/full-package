package org.bouncycastle.oer.its;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;

/* loaded from: classes.dex */
public class RegionAndSubregions extends ASN1Object implements RegionInterface {
    private final Region region;
    private final List<Uint16> subRegions;

    public static class Builder {
        private Region region;
        private List<Uint16> subRegions;

        public RegionAndSubregions createRegionAndSubregions() {
            return new RegionAndSubregions(this.region, this.subRegions);
        }

        public Builder setRegion(Region region) {
            this.region = region;
            return this;
        }

        public Builder setSubRegion(Uint16... uint16Arr) {
            this.subRegions.addAll(Arrays.asList(uint16Arr));
            return this;
        }

        public Builder setSubRegions(List<Uint16> list) {
            this.subRegions = list;
            return this;
        }
    }

    public RegionAndSubregions(Region region, List<Uint16> list) {
        this.region = region;
        this.subRegions = Collections.unmodifiableList(list);
    }

    public static RegionAndSubregions getInstance(Object obj) {
        if (obj instanceof RegionAndSubregions) {
            return (RegionAndSubregions) obj;
        }
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(0);
        Builder builder = new Builder();
        builder.setRegion(Region.getInstance((Object) aSN1Sequence.getObjectAt(0)));
        Iterator<ASN1Encodable> it = ASN1Sequence.getInstance(aSN1Sequence.getObjectAt(1)).iterator();
        while (it.hasNext()) {
            builder.setSubRegion(Uint16.getInstance(it.next()));
        }
        return builder.createRegionAndSubregions();
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(this.region, Utils.toSequence(this.subRegions));
    }
}
