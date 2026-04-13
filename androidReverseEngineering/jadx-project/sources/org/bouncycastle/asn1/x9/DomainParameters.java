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
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class DomainParameters extends ASN1Object {

    /* renamed from: g */
    private final ASN1Integer f1109g;

    /* renamed from: j */
    private final ASN1Integer f1110j;

    /* renamed from: p */
    private final ASN1Integer f1111p;

    /* renamed from: q */
    private final ASN1Integer f1112q;
    private final ValidationParams validationParams;

    public DomainParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, ValidationParams validationParams) {
        if (bigInteger == null) {
            throw new IllegalArgumentException("'p' cannot be null");
        }
        if (bigInteger2 == null) {
            throw new IllegalArgumentException("'g' cannot be null");
        }
        if (bigInteger3 == null) {
            throw new IllegalArgumentException("'q' cannot be null");
        }
        this.f1111p = new ASN1Integer(bigInteger);
        this.f1109g = new ASN1Integer(bigInteger2);
        this.f1112q = new ASN1Integer(bigInteger3);
        this.f1110j = bigInteger4 != null ? new ASN1Integer(bigInteger4) : null;
        this.validationParams = validationParams;
    }

    public static DomainParameters getInstance(Object obj) {
        if (obj instanceof DomainParameters) {
            return (DomainParameters) obj;
        }
        if (obj != null) {
            return new DomainParameters(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    private static ASN1Encodable getNext(Enumeration enumeration) {
        if (enumeration.hasMoreElements()) {
            return (ASN1Encodable) enumeration.nextElement();
        }
        return null;
    }

    public BigInteger getG() {
        return this.f1109g.getPositiveValue();
    }

    public BigInteger getJ() {
        ASN1Integer aSN1Integer = this.f1110j;
        if (aSN1Integer == null) {
            return null;
        }
        return aSN1Integer.getPositiveValue();
    }

    public BigInteger getP() {
        return this.f1111p.getPositiveValue();
    }

    public BigInteger getQ() {
        return this.f1112q.getPositiveValue();
    }

    public ValidationParams getValidationParams() {
        return this.validationParams;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(5);
        aSN1EncodableVector.add(this.f1111p);
        aSN1EncodableVector.add(this.f1109g);
        aSN1EncodableVector.add(this.f1112q);
        ASN1Integer aSN1Integer = this.f1110j;
        if (aSN1Integer != null) {
            aSN1EncodableVector.add(aSN1Integer);
        }
        ValidationParams validationParams = this.validationParams;
        if (validationParams != null) {
            aSN1EncodableVector.add(validationParams);
        }
        return new DERSequence(aSN1EncodableVector);
    }

    private DomainParameters(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() < 3 || aSN1Sequence.size() > 5) {
            throw new IllegalArgumentException(AbstractC0413b.m1012f(aSN1Sequence, new StringBuilder("Bad sequence size: ")));
        }
        Enumeration objects = aSN1Sequence.getObjects();
        this.f1111p = ASN1Integer.getInstance(objects.nextElement());
        this.f1109g = ASN1Integer.getInstance(objects.nextElement());
        this.f1112q = ASN1Integer.getInstance(objects.nextElement());
        ASN1Encodable next = getNext(objects);
        if (next == null || !(next instanceof ASN1Integer)) {
            this.f1110j = null;
        } else {
            this.f1110j = ASN1Integer.getInstance(next);
            next = getNext(objects);
        }
        if (next != null) {
            this.validationParams = ValidationParams.getInstance(next.toASN1Primitive());
        } else {
            this.validationParams = null;
        }
    }

    public static DomainParameters getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z2));
    }
}
