package org.bouncycastle.asn1.cryptopro;

import java.math.BigInteger;
import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class ECGOST3410ParamSetParameters extends ASN1Object {

    /* renamed from: a */
    ASN1Integer f1058a;

    /* renamed from: b */
    ASN1Integer f1059b;

    /* renamed from: p */
    ASN1Integer f1060p;

    /* renamed from: q */
    ASN1Integer f1061q;

    /* renamed from: x */
    ASN1Integer f1062x;

    /* renamed from: y */
    ASN1Integer f1063y;

    public ECGOST3410ParamSetParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, int i2, BigInteger bigInteger5) {
        this.f1058a = new ASN1Integer(bigInteger);
        this.f1059b = new ASN1Integer(bigInteger2);
        this.f1060p = new ASN1Integer(bigInteger3);
        this.f1061q = new ASN1Integer(bigInteger4);
        this.f1062x = new ASN1Integer(i2);
        this.f1063y = new ASN1Integer(bigInteger5);
    }

    public static ECGOST3410ParamSetParameters getInstance(Object obj) {
        if (obj == null || (obj instanceof ECGOST3410ParamSetParameters)) {
            return (ECGOST3410ParamSetParameters) obj;
        }
        if (obj instanceof ASN1Sequence) {
            return new ECGOST3410ParamSetParameters((ASN1Sequence) obj);
        }
        throw new IllegalArgumentException(AbstractC0000a.m10f(obj, "Invalid GOST3410Parameter: "));
    }

    public BigInteger getA() {
        return this.f1058a.getPositiveValue();
    }

    public BigInteger getP() {
        return this.f1060p.getPositiveValue();
    }

    public BigInteger getQ() {
        return this.f1061q.getPositiveValue();
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(6);
        aSN1EncodableVector.add(this.f1058a);
        aSN1EncodableVector.add(this.f1059b);
        aSN1EncodableVector.add(this.f1060p);
        aSN1EncodableVector.add(this.f1061q);
        aSN1EncodableVector.add(this.f1062x);
        aSN1EncodableVector.add(this.f1063y);
        return new DERSequence(aSN1EncodableVector);
    }

    public ECGOST3410ParamSetParameters(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        this.f1058a = (ASN1Integer) objects.nextElement();
        this.f1059b = (ASN1Integer) objects.nextElement();
        this.f1060p = (ASN1Integer) objects.nextElement();
        this.f1061q = (ASN1Integer) objects.nextElement();
        this.f1062x = (ASN1Integer) objects.nextElement();
        this.f1063y = (ASN1Integer) objects.nextElement();
    }

    public static ECGOST3410ParamSetParameters getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z2));
    }
}
