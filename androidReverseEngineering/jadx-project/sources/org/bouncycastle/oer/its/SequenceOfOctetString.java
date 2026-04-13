package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.util.Arrays;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class SequenceOfOctetString extends ASN1Object {
    private byte[][] octetStrings;

    private SequenceOfOctetString(ASN1Sequence aSN1Sequence) {
        this.octetStrings = toByteArrays(aSN1Sequence);
    }

    public static SequenceOfOctetString getInstance(Object obj) {
        if (obj instanceof SequenceOfOctetString) {
            return (SequenceOfOctetString) obj;
        }
        if (obj != null) {
            return new SequenceOfOctetString(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static byte[][] toByteArrays(ASN1Sequence aSN1Sequence) {
        byte[][] bArr = new byte[aSN1Sequence.size()][];
        for (int i2 = 0; i2 != aSN1Sequence.size(); i2++) {
            bArr[i2] = AbstractC0413b.m1006D(aSN1Sequence, i2);
        }
        return bArr;
    }

    public int size() {
        return this.octetStrings.length;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        for (int i2 = 0; i2 != this.octetStrings.length; i2++) {
            aSN1EncodableVector.add(new DEROctetString(Arrays.clone(this.octetStrings[i2])));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
