package org.bouncycastle.asn1.x9;

import java.math.BigInteger;
import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import p000a.AbstractC0000a;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class DHDomainParameters extends ASN1Object {

    /* renamed from: g */
    private ASN1Integer f1104g;

    /* renamed from: j */
    private ASN1Integer f1105j;

    /* renamed from: p */
    private ASN1Integer f1106p;

    /* renamed from: q */
    private ASN1Integer f1107q;
    private DHValidationParms validationParms;

    public DHDomainParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, DHValidationParms dHValidationParms) {
        if (bigInteger == null) {
            throw new IllegalArgumentException("'p' cannot be null");
        }
        if (bigInteger2 == null) {
            throw new IllegalArgumentException("'g' cannot be null");
        }
        if (bigInteger3 == null) {
            throw new IllegalArgumentException("'q' cannot be null");
        }
        this.f1106p = new ASN1Integer(bigInteger);
        this.f1104g = new ASN1Integer(bigInteger2);
        this.f1107q = new ASN1Integer(bigInteger3);
        this.f1105j = new ASN1Integer(bigInteger4);
        this.validationParms = dHValidationParms;
    }

    public static DHDomainParameters getInstance(Object obj) {
        if (obj == null || (obj instanceof DHDomainParameters)) {
            return (DHDomainParameters) obj;
        }
        if (obj instanceof ASN1Sequence) {
            return new DHDomainParameters((ASN1Sequence) obj);
        }
        throw new IllegalArgumentException(AbstractC0000a.m10f(obj, "Invalid DHDomainParameters: "));
    }

    private static ASN1Encodable getNext(Enumeration enumeration) {
        if (enumeration.hasMoreElements()) {
            return (ASN1Encodable) enumeration.nextElement();
        }
        return null;
    }

    public ASN1Integer getG() {
        return this.f1104g;
    }

    public ASN1Integer getJ() {
        return this.f1105j;
    }

    public ASN1Integer getP() {
        return this.f1106p;
    }

    public ASN1Integer getQ() {
        return this.f1107q;
    }

    public DHValidationParms getValidationParms() {
        return this.validationParms;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(5);
        aSN1EncodableVector.add(this.f1106p);
        aSN1EncodableVector.add(this.f1104g);
        aSN1EncodableVector.add(this.f1107q);
        ASN1Integer aSN1Integer = this.f1105j;
        if (aSN1Integer != null) {
            aSN1EncodableVector.add(aSN1Integer);
        }
        DHValidationParms dHValidationParms = this.validationParms;
        if (dHValidationParms != null) {
            aSN1EncodableVector.add(dHValidationParms);
        }
        return new DERSequence(aSN1EncodableVector);
    }

    public DHDomainParameters(ASN1Integer aSN1Integer, ASN1Integer aSN1Integer2, ASN1Integer aSN1Integer3, ASN1Integer aSN1Integer4, DHValidationParms dHValidationParms) {
        if (aSN1Integer == null) {
            throw new IllegalArgumentException("'p' cannot be null");
        }
        if (aSN1Integer2 == null) {
            throw new IllegalArgumentException("'g' cannot be null");
        }
        if (aSN1Integer3 == null) {
            throw new IllegalArgumentException("'q' cannot be null");
        }
        this.f1106p = aSN1Integer;
        this.f1104g = aSN1Integer2;
        this.f1107q = aSN1Integer3;
        this.f1105j = aSN1Integer4;
        this.validationParms = dHValidationParms;
    }

    private DHDomainParameters(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() < 3 || aSN1Sequence.size() > 5) {
            throw new IllegalArgumentException(AbstractC0413b.m1012f(aSN1Sequence, new StringBuilder("Bad sequence size: ")));
        }
        Enumeration objects = aSN1Sequence.getObjects();
        this.f1106p = ASN1Integer.getInstance(objects.nextElement());
        this.f1104g = ASN1Integer.getInstance(objects.nextElement());
        this.f1107q = ASN1Integer.getInstance(objects.nextElement());
        ASN1Encodable next = getNext(objects);
        if (next != null && (next instanceof ASN1Integer)) {
            this.f1105j = ASN1Integer.getInstance(next);
            next = getNext(objects);
        }
        if (next != null) {
            this.validationParms = DHValidationParms.getInstance(next.toASN1Primitive());
        }
    }

    public static DHDomainParameters getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z2));
    }
}
