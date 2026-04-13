package org.bouncycastle.oer.its;

import java.util.Collections;
import java.util.List;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;

/* loaded from: classes.dex */
public class SequenceOfIdentifiedRegion extends ASN1Object {
    private final List<IdentifiedRegion> identifiedRegions;

    public SequenceOfIdentifiedRegion(List<IdentifiedRegion> list) {
        this.identifiedRegions = Collections.unmodifiableList(list);
    }

    public static SequenceOfIdentifiedRegion getInstance(Object obj) {
        return obj instanceof SequenceOfIdentifiedRegion ? (SequenceOfIdentifiedRegion) obj : new SequenceOfIdentifiedRegion(Utils.fillList(IdentifiedRegion.class, ASN1Sequence.getInstance(obj)));
    }

    public List<IdentifiedRegion> getIdentifiedRegions() {
        return this.identifiedRegions;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return new DERSequence((ASN1Encodable[]) this.identifiedRegions.toArray(new ASN1Encodable[0]));
    }
}
