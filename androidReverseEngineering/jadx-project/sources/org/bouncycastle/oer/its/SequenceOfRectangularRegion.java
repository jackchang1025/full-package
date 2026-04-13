package org.bouncycastle.oer.its;

import java.util.Collections;
import java.util.List;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;

/* loaded from: classes.dex */
public class SequenceOfRectangularRegion extends ASN1Object {
    private final List<RectangularRegion> rectangularRegions;

    public SequenceOfRectangularRegion(List<RectangularRegion> list) {
        this.rectangularRegions = Collections.unmodifiableList(list);
    }

    public static SequenceOfRectangularRegion getInstance(Object obj) {
        return obj instanceof SequenceOfRectangularRegion ? (SequenceOfRectangularRegion) obj : new SequenceOfRectangularRegion(Utils.fillList(RectangularRegion.class, ASN1Sequence.getInstance(obj)));
    }

    public List<RectangularRegion> getRectangularRegions() {
        return this.rectangularRegions;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(this.rectangularRegions);
    }
}
