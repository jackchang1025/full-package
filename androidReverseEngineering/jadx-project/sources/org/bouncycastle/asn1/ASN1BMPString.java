package org.bouncycastle.asn1;

import org.bouncycastle.util.Arrays;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public abstract class ASN1BMPString extends ASN1Primitive implements ASN1String {
    static final ASN1UniversalType TYPE = new ASN1UniversalType(ASN1BMPString.class, 30) { // from class: org.bouncycastle.asn1.ASN1BMPString.1
        @Override // org.bouncycastle.asn1.ASN1UniversalType
        public ASN1Primitive fromImplicitPrimitive(DEROctetString dEROctetString) {
            return ASN1BMPString.createPrimitive(dEROctetString.getOctets());
        }
    };
    final char[] string;

    public ASN1BMPString(String str) {
        if (str == null) {
            throw new NullPointerException("'string' cannot be null");
        }
        this.string = str.toCharArray();
    }

    public static ASN1BMPString createPrimitive(byte[] bArr) {
        return new DERBMPString(bArr);
    }

    public static ASN1BMPString getInstance(Object obj) {
        if (obj == null || (obj instanceof ASN1BMPString)) {
            return (ASN1BMPString) obj;
        }
        if (obj instanceof ASN1Encodable) {
            ASN1Primitive aSN1Primitive = ((ASN1Encodable) obj).toASN1Primitive();
            if (aSN1Primitive instanceof ASN1BMPString) {
                return (ASN1BMPString) aSN1Primitive;
            }
        }
        if (!(obj instanceof byte[])) {
            throw new IllegalArgumentException(AbstractC0000a.m10f(obj, "illegal object in getInstance: "));
        }
        try {
            return (ASN1BMPString) TYPE.fromByteArray((byte[]) obj);
        } catch (Exception e2) {
            throw new IllegalArgumentException(AbstractC0000a.m29y(e2, new StringBuilder("encoding error in getInstance: ")));
        }
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public final boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (aSN1Primitive instanceof ASN1BMPString) {
            return Arrays.areEqual(this.string, ((ASN1BMPString) aSN1Primitive).string);
        }
        return false;
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public final void encode(ASN1OutputStream aSN1OutputStream, boolean z2) {
        int length = this.string.length;
        aSN1OutputStream.writeIdentifier(z2, 30);
        aSN1OutputStream.writeDL(length * 2);
        byte[] bArr = new byte[8];
        int i2 = length & (-4);
        int i3 = 0;
        while (i3 < i2) {
            char[] cArr = this.string;
            char c = cArr[i3];
            char c2 = cArr[i3 + 1];
            char c3 = cArr[i3 + 2];
            char c4 = cArr[i3 + 3];
            i3 += 4;
            bArr[0] = (byte) (c >> '\b');
            bArr[1] = (byte) c;
            bArr[2] = (byte) (c2 >> '\b');
            bArr[3] = (byte) c2;
            bArr[4] = (byte) (c3 >> '\b');
            bArr[5] = (byte) c3;
            bArr[6] = (byte) (c4 >> '\b');
            bArr[7] = (byte) c4;
            aSN1OutputStream.write(bArr, 0, 8);
        }
        if (i3 < length) {
            int i4 = 0;
            do {
                char c5 = this.string[i3];
                i3++;
                int i5 = i4 + 1;
                bArr[i4] = (byte) (c5 >> '\b');
                i4 = i5 + 1;
                bArr[i5] = (byte) c5;
            } while (i3 < length);
            aSN1OutputStream.write(bArr, 0, i4);
        }
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public final boolean encodeConstructed() {
        return false;
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public final int encodedLength(boolean z2) {
        return ASN1OutputStream.getLengthOfEncodingDL(z2, this.string.length * 2);
    }

    @Override // org.bouncycastle.asn1.ASN1String
    public final String getString() {
        return new String(this.string);
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive, org.bouncycastle.asn1.ASN1Object
    public final int hashCode() {
        return Arrays.hashCode(this.string);
    }

    public String toString() {
        return getString();
    }

    public ASN1BMPString(byte[] bArr) {
        if (bArr == null) {
            throw new NullPointerException("'string' cannot be null");
        }
        int length = bArr.length;
        if ((length & 1) != 0) {
            throw new IllegalArgumentException("malformed BMPString encoding encountered");
        }
        int i2 = length / 2;
        char[] cArr = new char[i2];
        for (int i3 = 0; i3 != i2; i3++) {
            int i4 = i3 * 2;
            cArr[i3] = (char) ((bArr[i4 + 1] & 255) | (bArr[i4] << 8));
        }
        this.string = cArr;
    }

    public static ASN1BMPString createPrimitive(char[] cArr) {
        return new DERBMPString(cArr);
    }

    public ASN1BMPString(char[] cArr) {
        if (cArr == null) {
            throw new NullPointerException("'string' cannot be null");
        }
        this.string = cArr;
    }

    public static ASN1BMPString getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        return (ASN1BMPString) TYPE.getContextInstance(aSN1TaggedObject, z2);
    }
}
