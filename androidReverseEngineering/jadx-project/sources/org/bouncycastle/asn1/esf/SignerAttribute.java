package org.bouncycastle.asn1.esf;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x509.Attribute;
import org.bouncycastle.asn1.x509.AttributeCertificate;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class SignerAttribute extends ASN1Object {
    private Object[] values;

    private SignerAttribute(ASN1Sequence aSN1Sequence) {
        this.values = new Object[aSN1Sequence.size()];
        Enumeration objects = aSN1Sequence.getObjects();
        int i2 = 0;
        while (objects.hasMoreElements()) {
            ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance(objects.nextElement());
            if (aSN1TaggedObject.getTagNo() == 0) {
                ASN1Sequence aSN1Sequence2 = ASN1Sequence.getInstance(aSN1TaggedObject, true);
                int size = aSN1Sequence2.size();
                Attribute[] attributeArr = new Attribute[size];
                for (int i3 = 0; i3 != size; i3++) {
                    attributeArr[i3] = Attribute.getInstance(aSN1Sequence2.getObjectAt(i3));
                }
                this.values[i2] = attributeArr;
            } else {
                if (aSN1TaggedObject.getTagNo() != 1) {
                    throw new IllegalArgumentException(AbstractC0413b.m1013g(aSN1TaggedObject, new StringBuilder("illegal tag: ")));
                }
                this.values[i2] = AttributeCertificate.getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, true));
            }
            i2++;
        }
    }

    public static SignerAttribute getInstance(Object obj) {
        if (obj instanceof SignerAttribute) {
            return (SignerAttribute) obj;
        }
        if (obj != null) {
            return new SignerAttribute(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public Object[] getValues() {
        Object[] objArr = this.values;
        int length = objArr.length;
        Object[] objArr2 = new Object[length];
        System.arraycopy(objArr, 0, objArr2, 0, length);
        return objArr2;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(this.values.length);
        int i2 = 0;
        while (true) {
            Object[] objArr = this.values;
            if (i2 == objArr.length) {
                return new DERSequence(aSN1EncodableVector);
            }
            Object obj = objArr[i2];
            if (obj instanceof Attribute[]) {
                aSN1EncodableVector.add(new DERTaggedObject(0, new DERSequence((Attribute[]) this.values[i2])));
            } else {
                aSN1EncodableVector.add(new DERTaggedObject(1, (AttributeCertificate) obj));
            }
            i2++;
        }
    }

    public SignerAttribute(AttributeCertificate attributeCertificate) {
        this.values = new Object[]{attributeCertificate};
    }

    public SignerAttribute(Attribute[] attributeArr) {
        this.values = new Object[]{attributeArr};
    }
}
