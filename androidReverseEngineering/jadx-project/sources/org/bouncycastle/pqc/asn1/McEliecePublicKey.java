package org.bouncycastle.pqc.asn1;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.pqc.math.linearalgebra.GF2Matrix;

/* loaded from: classes.dex */
public class McEliecePublicKey extends ASN1Object {

    /* renamed from: g */
    private final GF2Matrix f1529g;

    /* renamed from: n */
    private final int f1530n;

    /* renamed from: t */
    private final int f1531t;

    public McEliecePublicKey(int i2, int i3, GF2Matrix gF2Matrix) {
        this.f1530n = i2;
        this.f1531t = i3;
        this.f1529g = new GF2Matrix(gF2Matrix);
    }

    public static McEliecePublicKey getInstance(Object obj) {
        if (obj instanceof McEliecePublicKey) {
            return (McEliecePublicKey) obj;
        }
        if (obj != null) {
            return new McEliecePublicKey(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public GF2Matrix getG() {
        return new GF2Matrix(this.f1529g);
    }

    public int getN() {
        return this.f1530n;
    }

    public int getT() {
        return this.f1531t;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(new ASN1Integer(this.f1530n));
        aSN1EncodableVector.add(new ASN1Integer(this.f1531t));
        aSN1EncodableVector.add(new DEROctetString(this.f1529g.getEncoded()));
        return new DERSequence(aSN1EncodableVector);
    }

    private McEliecePublicKey(ASN1Sequence aSN1Sequence) {
        this.f1530n = ((ASN1Integer) aSN1Sequence.getObjectAt(0)).intValueExact();
        this.f1531t = ((ASN1Integer) aSN1Sequence.getObjectAt(1)).intValueExact();
        this.f1529g = new GF2Matrix(((ASN1OctetString) aSN1Sequence.getObjectAt(2)).getOctets());
    }
}
