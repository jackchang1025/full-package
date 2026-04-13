package org.bouncycastle.oer.its;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;

/* loaded from: classes.dex */
public class SequenceOfPsidSspRange extends ASN1Object {
    private final List<PsidSspRange> items;

    public static class Builder {
        private ArrayList<PsidSspRange> psidSspRanges = new ArrayList<>();

        public Builder add(PsidSspRange... psidSspRangeArr) {
            this.psidSspRanges.addAll(Arrays.asList(psidSspRangeArr));
            return this;
        }

        public SequenceOfPsidSspRange build() {
            return new SequenceOfPsidSspRange(this.psidSspRanges);
        }
    }

    public SequenceOfPsidSspRange(List<PsidSspRange> list) {
        this.items = Collections.unmodifiableList(list);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static SequenceOfPsidSspRange getInstance(Object obj) {
        if (obj instanceof SequenceOfPsidSspRange) {
            return (SequenceOfPsidSspRange) obj;
        }
        Enumeration objects = ASN1Sequence.getInstance(obj).getObjects();
        ArrayList arrayList = new ArrayList();
        while (objects.hasMoreElements()) {
            arrayList.add(PsidSspRange.getInstance(objects.nextElement()));
        }
        return new SequenceOfPsidSspRange(arrayList);
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        Iterator<PsidSspRange> it = this.items.iterator();
        while (it.hasNext()) {
            aSN1EncodableVector.add(it.next());
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
