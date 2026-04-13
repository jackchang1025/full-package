package org.bouncycastle.asn1.est;

import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;

/* loaded from: classes.dex */
public class CsrAttrs extends ASN1Object {
    private final AttrOrOID[] attrOrOIDs;

    private CsrAttrs(ASN1Sequence aSN1Sequence) {
        this.attrOrOIDs = new AttrOrOID[aSN1Sequence.size()];
        for (int i2 = 0; i2 != aSN1Sequence.size(); i2++) {
            this.attrOrOIDs[i2] = AttrOrOID.getInstance(aSN1Sequence.getObjectAt(i2));
        }
    }

    public static CsrAttrs getInstance(Object obj) {
        if (obj instanceof CsrAttrs) {
            return (CsrAttrs) obj;
        }
        if (obj != null) {
            return new CsrAttrs(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public AttrOrOID[] getAttrOrOIDs() {
        return Utils.clone(this.attrOrOIDs);
    }

    public int size() {
        return this.attrOrOIDs.length;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return new DERSequence(this.attrOrOIDs);
    }

    public CsrAttrs(AttrOrOID attrOrOID) {
        this.attrOrOIDs = new AttrOrOID[]{attrOrOID};
    }

    public static CsrAttrs getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z2));
    }

    public CsrAttrs(AttrOrOID[] attrOrOIDArr) {
        this.attrOrOIDs = Utils.clone(attrOrOIDArr);
    }
}
