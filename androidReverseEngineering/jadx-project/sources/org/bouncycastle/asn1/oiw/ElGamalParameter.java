package org.bouncycastle.asn1.oiw;

import java.math.BigInteger;
import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;

/* loaded from: classes.dex */
public class ElGamalParameter extends ASN1Object {

    /* renamed from: g */
    ASN1Integer f1075g;

    /* renamed from: p */
    ASN1Integer f1076p;

    public ElGamalParameter(BigInteger bigInteger, BigInteger bigInteger2) {
        this.f1076p = new ASN1Integer(bigInteger);
        this.f1075g = new ASN1Integer(bigInteger2);
    }

    public static ElGamalParameter getInstance(Object obj) {
        if (obj instanceof ElGamalParameter) {
            return (ElGamalParameter) obj;
        }
        if (obj != null) {
            return new ElGamalParameter(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public BigInteger getG() {
        return this.f1075g.getPositiveValue();
    }

    public BigInteger getP() {
        return this.f1076p.getPositiveValue();
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(2);
        aSN1EncodableVector.add(this.f1076p);
        aSN1EncodableVector.add(this.f1075g);
        return new DERSequence(aSN1EncodableVector);
    }

    private ElGamalParameter(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        this.f1076p = (ASN1Integer) objects.nextElement();
        this.f1075g = (ASN1Integer) objects.nextElement();
    }
}
