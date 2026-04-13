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
public class GOST3410ParamSetParameters extends ASN1Object {

    /* renamed from: a */
    ASN1Integer f1064a;
    int keySize;

    /* renamed from: p */
    ASN1Integer f1065p;

    /* renamed from: q */
    ASN1Integer f1066q;

    public GOST3410ParamSetParameters(int i2, BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        this.keySize = i2;
        this.f1065p = new ASN1Integer(bigInteger);
        this.f1066q = new ASN1Integer(bigInteger2);
        this.f1064a = new ASN1Integer(bigInteger3);
    }

    public static GOST3410ParamSetParameters getInstance(Object obj) {
        if (obj == null || (obj instanceof GOST3410ParamSetParameters)) {
            return (GOST3410ParamSetParameters) obj;
        }
        if (obj instanceof ASN1Sequence) {
            return new GOST3410ParamSetParameters((ASN1Sequence) obj);
        }
        throw new IllegalArgumentException(AbstractC0000a.m10f(obj, "Invalid GOST3410Parameter: "));
    }

    public BigInteger getA() {
        return this.f1064a.getPositiveValue();
    }

    public int getKeySize() {
        return this.keySize;
    }

    public int getLKeySize() {
        return this.keySize;
    }

    public BigInteger getP() {
        return this.f1065p.getPositiveValue();
    }

    public BigInteger getQ() {
        return this.f1066q.getPositiveValue();
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(4);
        aSN1EncodableVector.add(new ASN1Integer(this.keySize));
        aSN1EncodableVector.add(this.f1065p);
        aSN1EncodableVector.add(this.f1066q);
        aSN1EncodableVector.add(this.f1064a);
        return new DERSequence(aSN1EncodableVector);
    }

    public GOST3410ParamSetParameters(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        this.keySize = ((ASN1Integer) objects.nextElement()).intValueExact();
        this.f1065p = (ASN1Integer) objects.nextElement();
        this.f1066q = (ASN1Integer) objects.nextElement();
        this.f1064a = (ASN1Integer) objects.nextElement();
    }

    public static GOST3410ParamSetParameters getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z2));
    }
}
