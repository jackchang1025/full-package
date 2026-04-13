package org.bouncycastle.oer.its;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;

/* loaded from: classes.dex */
public class CountryAndRegions extends ASN1Object implements RegionInterface {
    private final CountryOnly countryOnly;
    private final List<Region> regions;

    public static class CountryAndRegionsBuilder {
        private CountryOnly countryOnly;
        private List<Region> regionList = new ArrayList();

        public CountryAndRegionsBuilder addRegion(Region region) {
            this.regionList.add(region);
            return this;
        }

        public CountryAndRegions build() {
            return new CountryAndRegions(this.countryOnly, this.regionList);
        }

        public CountryAndRegionsBuilder setCountryOnly(CountryOnly countryOnly) {
            this.countryOnly = countryOnly;
            return this;
        }

        public CountryAndRegionsBuilder setRegionList(List<Region> list) {
            this.regionList.addAll(list);
            return this;
        }
    }

    public CountryAndRegions(CountryOnly countryOnly, List<Region> list) {
        this.countryOnly = countryOnly;
        this.regions = Collections.unmodifiableList(list);
    }

    public static CountryAndRegionsBuilder builder() {
        return new CountryAndRegionsBuilder();
    }

    public static CountryAndRegions getInstance(Object obj) {
        if (obj instanceof CountryAndRegions) {
            return (CountryAndRegions) obj;
        }
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(obj);
        return new CountryAndRegions(CountryOnly.getInstance((Object) aSN1Sequence.getObjectAt(0)), Utils.fillList(Region.class, ASN1Sequence.getInstance(aSN1Sequence.getObjectAt(1))));
    }

    public CountryOnly getCountryOnly() {
        return this.countryOnly;
    }

    public List<Region> getRegions() {
        return this.regions;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(this.countryOnly, Utils.toSequence(this.regions));
    }
}
