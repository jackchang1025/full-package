package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;

/* loaded from: classes.dex */
public class GroupLinkageValue extends ASN1Object {
    private final ASN1OctetString jValue;
    private final ASN1OctetString value;

    private GroupLinkageValue(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() != 2) {
            throw new IllegalArgumentException("sequence not length 2");
        }
        this.jValue = ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(0));
        this.value = ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(1));
    }

    public static GroupLinkageValue getInstance(Object obj) {
        if (obj instanceof GroupLinkageValue) {
            return (GroupLinkageValue) obj;
        }
        if (obj != null) {
            return new GroupLinkageValue(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public ASN1OctetString getValue() {
        return this.value;
    }

    public ASN1OctetString getjValue() {
        return this.jValue;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(this.jValue, this.value);
    }
}
