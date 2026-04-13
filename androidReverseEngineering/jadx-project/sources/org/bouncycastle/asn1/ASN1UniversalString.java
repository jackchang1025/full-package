package org.bouncycastle.asn1;

import org.bouncycastle.util.Arrays;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public abstract class ASN1UniversalString extends ASN1Primitive implements ASN1String {
    static final ASN1UniversalType TYPE = new ASN1UniversalType(ASN1UniversalString.class, 28) { // from class: org.bouncycastle.asn1.ASN1UniversalString.1
        @Override // org.bouncycastle.asn1.ASN1UniversalType
        public ASN1Primitive fromImplicitPrimitive(DEROctetString dEROctetString) {
            return ASN1UniversalString.createPrimitive(dEROctetString.getOctets());
        }
    };
    private static final char[] table = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    final byte[] contents;

    public ASN1UniversalString(byte[] bArr, boolean z2) {
        this.contents = z2 ? Arrays.clone(bArr) : bArr;
    }

    public static ASN1UniversalString createPrimitive(byte[] bArr) {
        return new DERUniversalString(bArr, false);
    }

    private static void encodeHexByte(StringBuffer stringBuffer, int i2) {
        char[] cArr = table;
        stringBuffer.append(cArr[(i2 >>> 4) & 15]);
        stringBuffer.append(cArr[i2 & 15]);
    }

    private static void encodeHexDL(StringBuffer stringBuffer, int i2) {
        if (i2 < 128) {
            encodeHexByte(stringBuffer, i2);
            return;
        }
        byte[] bArr = new byte[5];
        int i3 = 5;
        do {
            i3--;
            bArr[i3] = (byte) i2;
            i2 >>>= 8;
        } while (i2 != 0);
        int i4 = 5 - i3;
        int i5 = i3 - 1;
        bArr[i5] = (byte) (i4 | 128);
        while (true) {
            int i6 = i5 + 1;
            encodeHexByte(stringBuffer, bArr[i5]);
            if (i6 >= 5) {
                return;
            } else {
                i5 = i6;
            }
        }
    }

    public static ASN1UniversalString getInstance(Object obj) {
        if (obj == null || (obj instanceof ASN1UniversalString)) {
            return (ASN1UniversalString) obj;
        }
        if (obj instanceof ASN1Encodable) {
            ASN1Primitive aSN1Primitive = ((ASN1Encodable) obj).toASN1Primitive();
            if (aSN1Primitive instanceof ASN1UniversalString) {
                return (ASN1UniversalString) aSN1Primitive;
            }
        }
        if (!(obj instanceof byte[])) {
            throw new IllegalArgumentException(AbstractC0000a.m10f(obj, "illegal object in getInstance: "));
        }
        try {
            return (ASN1UniversalString) TYPE.fromByteArray((byte[]) obj);
        } catch (Exception e2) {
            throw new IllegalArgumentException(AbstractC0000a.m29y(e2, new StringBuilder("encoding error getInstance: ")));
        }
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public final boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (aSN1Primitive instanceof ASN1UniversalString) {
            return Arrays.areEqual(this.contents, ((ASN1UniversalString) aSN1Primitive).contents);
        }
        return false;
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public final void encode(ASN1OutputStream aSN1OutputStream, boolean z2) {
        aSN1OutputStream.writeEncodingDL(z2, 28, this.contents);
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public final boolean encodeConstructed() {
        return false;
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public final int encodedLength(boolean z2) {
        return ASN1OutputStream.getLengthOfEncodingDL(z2, this.contents.length);
    }

    public final byte[] getOctets() {
        return Arrays.clone(this.contents);
    }

    @Override // org.bouncycastle.asn1.ASN1String
    public final String getString() {
        int length = this.contents.length;
        StringBuffer stringBuffer = new StringBuffer(((ASN1OutputStream.getLengthOfDL(length) + length) * 2) + 3);
        stringBuffer.append("#1C");
        encodeHexDL(stringBuffer, length);
        for (int i2 = 0; i2 < length; i2++) {
            encodeHexByte(stringBuffer, this.contents[i2]);
        }
        return stringBuffer.toString();
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive, org.bouncycastle.asn1.ASN1Object
    public final int hashCode() {
        return Arrays.hashCode(this.contents);
    }

    public String toString() {
        return getString();
    }

    public static ASN1UniversalString getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        return (ASN1UniversalString) TYPE.getContextInstance(aSN1TaggedObject, z2);
    }
}
