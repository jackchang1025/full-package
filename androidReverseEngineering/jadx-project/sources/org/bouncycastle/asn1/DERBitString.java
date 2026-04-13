package org.bouncycastle.asn1;

import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class DERBitString extends ASN1BitString {
    public DERBitString(byte b, int i2) {
        super(b, i2);
    }

    public static DERBitString convert(ASN1BitString aSN1BitString) {
        return (DERBitString) aSN1BitString.toDERObject();
    }

    public static DERBitString fromOctetString(ASN1OctetString aSN1OctetString) {
        return new DERBitString(aSN1OctetString.getOctets(), true);
    }

    public static DERBitString getInstance(Object obj) {
        if (obj == null || (obj instanceof DERBitString)) {
            return (DERBitString) obj;
        }
        if (obj instanceof ASN1BitString) {
            return convert((ASN1BitString) obj);
        }
        if (!(obj instanceof byte[])) {
            throw new IllegalArgumentException(AbstractC0000a.m10f(obj, "illegal object in getInstance: "));
        }
        try {
            return convert((ASN1BitString) ASN1Primitive.fromByteArray((byte[]) obj));
        } catch (Exception e2) {
            throw new IllegalArgumentException(AbstractC0000a.m29y(e2, new StringBuilder("encoding error in getInstance: ")));
        }
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public void encode(ASN1OutputStream aSN1OutputStream, boolean z2) {
        byte[] bArr = this.contents;
        int i2 = bArr[0] & 255;
        int length = bArr.length - 1;
        byte b = bArr[length];
        byte b2 = (byte) ((255 << i2) & b);
        if (b == b2) {
            aSN1OutputStream.writeEncodingDL(z2, 3, bArr);
        } else {
            aSN1OutputStream.writeEncodingDL(z2, 3, bArr, 0, length, b2);
        }
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public boolean encodeConstructed() {
        return false;
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public int encodedLength(boolean z2) {
        return ASN1OutputStream.getLengthOfEncodingDL(z2, this.contents.length);
    }

    @Override // org.bouncycastle.asn1.ASN1BitString, org.bouncycastle.asn1.ASN1Primitive
    public ASN1Primitive toDERObject() {
        return this;
    }

    @Override // org.bouncycastle.asn1.ASN1BitString, org.bouncycastle.asn1.ASN1Primitive
    public ASN1Primitive toDLObject() {
        return this;
    }

    public DERBitString(int i2) {
        super(ASN1BitString.getBytes(i2), ASN1BitString.getPadBits(i2));
    }

    public DERBitString(ASN1Encodable aSN1Encodable) {
        super(aSN1Encodable.toASN1Primitive().getEncoded(ASN1Encoding.DER), 0);
    }

    public DERBitString(byte[] bArr) {
        this(bArr, 0);
    }

    public DERBitString(byte[] bArr, int i2) {
        super(bArr, i2);
    }

    public DERBitString(byte[] bArr, boolean z2) {
        super(bArr, z2);
    }

    public static DERBitString getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        ASN1Primitive object = aSN1TaggedObject.getObject();
        return (z2 || (object instanceof DERBitString)) ? getInstance((Object) object) : fromOctetString(ASN1OctetString.getInstance(object));
    }
}
