package org.bouncycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import org.bouncycastle.asn1.eac.CertificateBody;
import org.bouncycastle.util.Arrays;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class ASN1RelativeOID extends ASN1Primitive {
    private static final long LONG_LIMIT = 72057594037927808L;
    static final ASN1UniversalType TYPE = new ASN1UniversalType(ASN1RelativeOID.class, 13) { // from class: org.bouncycastle.asn1.ASN1RelativeOID.1
        @Override // org.bouncycastle.asn1.ASN1UniversalType
        public ASN1Primitive fromImplicitPrimitive(DEROctetString dEROctetString) {
            return ASN1RelativeOID.createPrimitive(dEROctetString.getOctets(), false);
        }
    };
    private byte[] contents;
    private final String identifier;

    public ASN1RelativeOID(String str) {
        if (str == null) {
            throw new NullPointerException("'identifier' cannot be null");
        }
        if (!isValidIdentifier(str, 0)) {
            throw new IllegalArgumentException(AbstractC0000a.m16l("string ", str, " not a relative OID"));
        }
        this.identifier = str;
    }

    public static ASN1RelativeOID createPrimitive(byte[] bArr, boolean z2) {
        return new ASN1RelativeOID(bArr, z2);
    }

    private void doOutput(ByteArrayOutputStream byteArrayOutputStream) {
        OIDTokenizer oIDTokenizer = new OIDTokenizer(this.identifier);
        while (oIDTokenizer.hasMoreTokens()) {
            String nextToken = oIDTokenizer.nextToken();
            if (nextToken.length() <= 18) {
                writeField(byteArrayOutputStream, Long.parseLong(nextToken));
            } else {
                writeField(byteArrayOutputStream, new BigInteger(nextToken));
            }
        }
    }

    public static ASN1RelativeOID fromContents(byte[] bArr) {
        return createPrimitive(bArr, true);
    }

    private synchronized byte[] getContents() {
        if (this.contents == null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            doOutput(byteArrayOutputStream);
            this.contents = byteArrayOutputStream.toByteArray();
        }
        return this.contents;
    }

    public static ASN1RelativeOID getInstance(Object obj) {
        if (obj == null || (obj instanceof ASN1RelativeOID)) {
            return (ASN1RelativeOID) obj;
        }
        if (obj instanceof ASN1Encodable) {
            ASN1Primitive aSN1Primitive = ((ASN1Encodable) obj).toASN1Primitive();
            if (aSN1Primitive instanceof ASN1RelativeOID) {
                return (ASN1RelativeOID) aSN1Primitive;
            }
        } else if (obj instanceof byte[]) {
            try {
                return (ASN1RelativeOID) TYPE.fromByteArray((byte[]) obj);
            } catch (IOException e2) {
                throw new IllegalArgumentException(AbstractC0000a.m8d(e2, new StringBuilder("failed to construct relative OID from byte[]: ")));
            }
        }
        throw new IllegalArgumentException(AbstractC0000a.m10f(obj, "illegal object in getInstance: "));
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x002b, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0015, code lost:
    
        if (r2 == 0) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0017, code lost:
    
        if (r2 <= 1) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x001f, code lost:
    
        if (r7.charAt(r0 + 1) != '0') goto L33;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean isValidIdentifier(String str, int i2) {
        int length = str.length();
        loop0: while (true) {
            int i3 = 0;
            while (true) {
                length--;
                if (length < i2) {
                    return i3 != 0 && (i3 <= 1 || str.charAt(length + 1) != '0');
                }
                char charAt = str.charAt(length);
                if (charAt == '.') {
                    break;
                }
                if ('0' > charAt || charAt > '9') {
                    break loop0;
                }
                i3++;
            }
        }
        return false;
    }

    public static void writeField(ByteArrayOutputStream byteArrayOutputStream, long j2) {
        byte[] bArr = new byte[9];
        int i2 = 8;
        bArr[8] = (byte) (((int) j2) & CertificateBody.profileType);
        while (j2 >= 128) {
            j2 >>= 7;
            i2--;
            bArr[i2] = (byte) (((int) j2) | 128);
        }
        byteArrayOutputStream.write(bArr, i2, 9 - i2);
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (this == aSN1Primitive) {
            return true;
        }
        if (aSN1Primitive instanceof ASN1RelativeOID) {
            return this.identifier.equals(((ASN1RelativeOID) aSN1Primitive).identifier);
        }
        return false;
    }

    public ASN1RelativeOID branch(String str) {
        return new ASN1RelativeOID(this, str);
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public void encode(ASN1OutputStream aSN1OutputStream, boolean z2) {
        aSN1OutputStream.writeEncodingDL(z2, 13, getContents());
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public boolean encodeConstructed() {
        return false;
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public int encodedLength(boolean z2) {
        return ASN1OutputStream.getLengthOfEncodingDL(z2, getContents().length);
    }

    public String getId() {
        return this.identifier;
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive, org.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        return this.identifier.hashCode();
    }

    public String toString() {
        return getId();
    }

    public static void writeField(ByteArrayOutputStream byteArrayOutputStream, BigInteger bigInteger) {
        int bitLength = (bigInteger.bitLength() + 6) / 7;
        if (bitLength == 0) {
            byteArrayOutputStream.write(0);
            return;
        }
        byte[] bArr = new byte[bitLength];
        int i2 = bitLength - 1;
        for (int i3 = i2; i3 >= 0; i3--) {
            bArr[i3] = (byte) (bigInteger.intValue() | 128);
            bigInteger = bigInteger.shiftRight(7);
        }
        bArr[i2] = (byte) (bArr[i2] & Byte.MAX_VALUE);
        byteArrayOutputStream.write(bArr, 0, bitLength);
    }

    public ASN1RelativeOID(ASN1RelativeOID aSN1RelativeOID, String str) {
        if (!isValidIdentifier(str, 0)) {
            throw new IllegalArgumentException(AbstractC0000a.m16l("string ", str, " not a valid OID branch"));
        }
        this.identifier = aSN1RelativeOID.getId() + "." + str;
    }

    public static ASN1RelativeOID getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        return (ASN1RelativeOID) TYPE.getContextInstance(aSN1TaggedObject, z2);
    }

    private ASN1RelativeOID(byte[] bArr, boolean z2) {
        byte[] bArr2 = bArr;
        StringBuffer stringBuffer = new StringBuffer();
        boolean z3 = true;
        BigInteger bigInteger = null;
        long j2 = 0;
        for (int i2 = 0; i2 != bArr2.length; i2++) {
            int i3 = bArr2[i2] & 255;
            if (j2 <= LONG_LIMIT) {
                long j3 = j2 + (i3 & CertificateBody.profileType);
                if ((i3 & 128) == 0) {
                    if (z3) {
                        z3 = false;
                    } else {
                        stringBuffer.append('.');
                    }
                    stringBuffer.append(j3);
                    j2 = 0;
                } else {
                    j2 = j3 << 7;
                }
            } else {
                BigInteger or = (bigInteger == null ? BigInteger.valueOf(j2) : bigInteger).or(BigInteger.valueOf(i3 & CertificateBody.profileType));
                if ((i3 & 128) == 0) {
                    if (z3) {
                        z3 = false;
                    } else {
                        stringBuffer.append('.');
                    }
                    stringBuffer.append(or);
                    bigInteger = null;
                    j2 = 0;
                } else {
                    bigInteger = or.shiftLeft(7);
                }
            }
        }
        this.identifier = stringBuffer.toString();
        this.contents = z2 ? Arrays.clone(bArr) : bArr2;
    }
}
