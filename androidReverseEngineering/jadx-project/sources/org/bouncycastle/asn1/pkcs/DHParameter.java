package org.bouncycastle.asn1.pkcs;

import java.math.BigInteger;
import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;

/* loaded from: classes.dex */
public class DHParameter extends ASN1Object {

    /* renamed from: g */
    ASN1Integer f1077g;

    /* renamed from: l */
    ASN1Integer f1078l;

    /* renamed from: p */
    ASN1Integer f1079p;

    public DHParameter(BigInteger bigInteger, BigInteger bigInteger2, int i2) {
        this.f1079p = new ASN1Integer(bigInteger);
        this.f1077g = new ASN1Integer(bigInteger2);
        this.f1078l = i2 != 0 ? new ASN1Integer(i2) : null;
    }

    public static DHParameter getInstance(Object obj) {
        if (obj instanceof DHParameter) {
            return (DHParameter) obj;
        }
        if (obj != null) {
            return new DHParameter(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public BigInteger getG() {
        return this.f1077g.getPositiveValue();
    }

    public BigInteger getL() {
        ASN1Integer aSN1Integer = this.f1078l;
        if (aSN1Integer == null) {
            return null;
        }
        return aSN1Integer.getPositiveValue();
    }

    public BigInteger getP() {
        return this.f1079p.getPositiveValue();
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(3);
        aSN1EncodableVector.add(this.f1079p);
        aSN1EncodableVector.add(this.f1077g);
        if (getL() != null) {
            aSN1EncodableVector.add(this.f1078l);
        }
        return new DERSequence(aSN1EncodableVector);
    }

    private DHParameter(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        this.f1079p = ASN1Integer.getInstance(objects.nextElement());
        this.f1077g = ASN1Integer.getInstance(objects.nextElement());
        this.f1078l = objects.hasMoreElements() ? (ASN1Integer) objects.nextElement() : null;
    }
}
