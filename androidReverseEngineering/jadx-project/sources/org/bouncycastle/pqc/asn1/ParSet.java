package org.bouncycastle.pqc.asn1;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.util.Arrays;
import p000a.AbstractC0000a;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class ParSet extends ASN1Object {

    /* renamed from: h */
    private int[] f1532h;

    /* renamed from: k */
    private int[] f1533k;

    /* renamed from: t */
    private int f1534t;

    /* renamed from: w */
    private int[] f1535w;

    public ParSet(int i2, int[] iArr, int[] iArr2, int[] iArr3) {
        this.f1534t = i2;
        this.f1532h = iArr;
        this.f1535w = iArr2;
        this.f1533k = iArr3;
    }

    private static int checkBigIntegerInIntRangeAndPositive(ASN1Encodable aSN1Encodable) {
        int intValueExact = ((ASN1Integer) aSN1Encodable).intValueExact();
        if (intValueExact > 0) {
            return intValueExact;
        }
        throw new IllegalArgumentException(AbstractC0000a.m11g("BigInteger not in Range: ", intValueExact));
    }

    public static ParSet getInstance(Object obj) {
        if (obj instanceof ParSet) {
            return (ParSet) obj;
        }
        if (obj != null) {
            return new ParSet(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public int[] getH() {
        return Arrays.clone(this.f1532h);
    }

    public int[] getK() {
        return Arrays.clone(this.f1533k);
    }

    public int getT() {
        return this.f1534t;
    }

    public int[] getW() {
        return Arrays.clone(this.f1535w);
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
        ASN1EncodableVector aSN1EncodableVector3 = new ASN1EncodableVector();
        for (int i2 = 0; i2 < this.f1532h.length; i2++) {
            aSN1EncodableVector.add(new ASN1Integer(this.f1532h[i2]));
            aSN1EncodableVector2.add(new ASN1Integer(this.f1535w[i2]));
            aSN1EncodableVector3.add(new ASN1Integer(this.f1533k[i2]));
        }
        ASN1EncodableVector aSN1EncodableVector4 = new ASN1EncodableVector();
        aSN1EncodableVector4.add(new ASN1Integer(this.f1534t));
        aSN1EncodableVector4.add(new DERSequence(aSN1EncodableVector));
        aSN1EncodableVector4.add(new DERSequence(aSN1EncodableVector2));
        aSN1EncodableVector4.add(new DERSequence(aSN1EncodableVector3));
        return new DERSequence(aSN1EncodableVector4);
    }

    private ParSet(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() != 4) {
            throw new IllegalArgumentException(AbstractC0413b.m1012f(aSN1Sequence, new StringBuilder("sie of seqOfParams = ")));
        }
        this.f1534t = checkBigIntegerInIntRangeAndPositive(aSN1Sequence.getObjectAt(0));
        ASN1Sequence aSN1Sequence2 = (ASN1Sequence) aSN1Sequence.getObjectAt(1);
        ASN1Sequence aSN1Sequence3 = (ASN1Sequence) aSN1Sequence.getObjectAt(2);
        ASN1Sequence aSN1Sequence4 = (ASN1Sequence) aSN1Sequence.getObjectAt(3);
        if (aSN1Sequence2.size() != this.f1534t || aSN1Sequence3.size() != this.f1534t || aSN1Sequence4.size() != this.f1534t) {
            throw new IllegalArgumentException("invalid size of sequences");
        }
        this.f1532h = new int[aSN1Sequence2.size()];
        this.f1535w = new int[aSN1Sequence3.size()];
        this.f1533k = new int[aSN1Sequence4.size()];
        for (int i2 = 0; i2 < this.f1534t; i2++) {
            this.f1532h[i2] = checkBigIntegerInIntRangeAndPositive(aSN1Sequence2.getObjectAt(i2));
            this.f1535w[i2] = checkBigIntegerInIntRangeAndPositive(aSN1Sequence3.getObjectAt(i2));
            this.f1533k[i2] = checkBigIntegerInIntRangeAndPositive(aSN1Sequence4.getObjectAt(i2));
        }
    }
}
