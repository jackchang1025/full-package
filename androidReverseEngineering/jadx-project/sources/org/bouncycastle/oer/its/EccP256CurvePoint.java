package org.bouncycastle.oer.its;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Null;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.BigIntegers;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class EccP256CurvePoint extends EccCurvePoint {
    public static final int compressedY0 = 2;
    public static final int compressedY1 = 3;
    public static final int fill = 1;
    public static final int uncompressedP256 = 4;
    public static final int xOnly = 0;
    private final int choice;
    private final ASN1Encodable value;

    public static class Builder {
        private int choice;
        private ASN1Encodable value;

        /* JADX INFO: Access modifiers changed from: private */
        public EccP256CurvePoint createEccP256CurvePoint() {
            return new EccP256CurvePoint(this.choice, this.value);
        }

        public EccP256CurvePoint createCompressedY0(BigInteger bigInteger) {
            this.choice = 2;
            throw new IllegalStateException("not fully implemented.");
        }

        public EccP256CurvePoint createCompressedY1(BigInteger bigInteger) {
            this.choice = 3;
            throw new IllegalStateException("not fully implemented.");
        }

        public EccP256CurvePoint createFill() {
            this.choice = 1;
            this.value = DERNull.INSTANCE;
            return createEccP256CurvePoint();
        }

        public EccP256CurvePoint createUncompressedP256(BigInteger bigInteger, BigInteger bigInteger2) {
            this.choice = 4;
            this.value = new DERSequence(new ASN1Encodable[]{new DEROctetString(BigIntegers.asUnsignedByteArray(32, bigInteger)), new DEROctetString(BigIntegers.asUnsignedByteArray(32, bigInteger2))});
            return createEccP256CurvePoint();
        }

        public EccP256CurvePoint createXOnly(BigInteger bigInteger) {
            this.choice = 0;
            this.value = new DEROctetString(BigIntegers.asUnsignedByteArray(bigInteger));
            return createEccP256CurvePoint();
        }

        public Builder setChoice(int i2) {
            this.choice = i2;
            return this;
        }

        public Builder setValue(ASN1Encodable aSN1Encodable) {
            this.value = aSN1Encodable;
            return this;
        }
    }

    public EccP256CurvePoint(int i2, ASN1Encodable aSN1Encodable) {
        this.choice = i2;
        this.value = aSN1Encodable;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static EccP256CurvePoint getInstance(Object obj) {
        ASN1Encodable aSN1OctetString;
        if (obj instanceof EccP256CurvePoint) {
            return (EccP256CurvePoint) obj;
        }
        ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance(obj);
        int tagNo = aSN1TaggedObject.getTagNo();
        if (tagNo != 0) {
            if (tagNo == 1) {
                aSN1OctetString = ASN1Null.getInstance(aSN1TaggedObject.getObject());
            } else if (tagNo != 2 && tagNo != 3) {
                if (tagNo != 4) {
                    throw new IllegalArgumentException(AbstractC0413b.m1013g(aSN1TaggedObject, new StringBuilder("unknown tag ")));
                }
                aSN1OctetString = ASN1Sequence.getInstance(aSN1TaggedObject.getObject());
            }
            return new Builder().setChoice(aSN1TaggedObject.getTagNo()).setValue(aSN1OctetString).createEccP256CurvePoint();
        }
        aSN1OctetString = ASN1OctetString.getInstance(aSN1TaggedObject.getObject());
        return new Builder().setChoice(aSN1TaggedObject.getTagNo()).setValue(aSN1OctetString).createEccP256CurvePoint();
    }

    public int getChoice() {
        return this.choice;
    }

    @Override // org.bouncycastle.oer.its.EccCurvePoint
    public byte[] getEncodedPoint() {
        byte[] bArr;
        int i2 = this.choice;
        if (i2 == 0) {
            throw new IllegalStateException("x Only not implemented");
        }
        if (i2 == 2) {
            byte[] octets = ASN1OctetString.getInstance(this.value).getOctets();
            bArr = new byte[octets.length + 1];
            bArr[0] = 2;
            System.arraycopy(octets, 0, bArr, 1, octets.length);
        } else {
            if (i2 != 3) {
                if (i2 != 4) {
                    throw new IllegalStateException("unknown point choice");
                }
                ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(this.value);
                return Arrays.concatenate(new byte[]{4}, AbstractC0413b.m1006D(aSN1Sequence, 0), AbstractC0413b.m1006D(aSN1Sequence, 1));
            }
            byte[] octets2 = ASN1OctetString.getInstance(this.value).getOctets();
            bArr = new byte[octets2.length + 1];
            bArr[0] = 3;
            System.arraycopy(octets2, 0, bArr, 1, octets2.length);
        }
        return bArr;
    }

    public ASN1Encodable getValue() {
        return this.value;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return new DERTaggedObject(this.choice, this.value);
    }
}
