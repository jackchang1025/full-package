package org.bouncycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.bouncycastle.asn1.eac.CertificateBody;
import org.bouncycastle.util.Arrays;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class ASN1ObjectIdentifier extends ASN1Primitive {
    private static final long LONG_LIMIT = 72057594037927808L;
    static final ASN1UniversalType TYPE = new ASN1UniversalType(ASN1ObjectIdentifier.class, 6) { // from class: org.bouncycastle.asn1.ASN1ObjectIdentifier.1
        @Override // org.bouncycastle.asn1.ASN1UniversalType
        public ASN1Primitive fromImplicitPrimitive(DEROctetString dEROctetString) {
            return ASN1ObjectIdentifier.createPrimitive(dEROctetString.getOctets(), false);
        }
    };
    private static final ConcurrentMap<OidHandle, ASN1ObjectIdentifier> pool = new ConcurrentHashMap();
    private byte[] contents;
    private final String identifier;

    public static class OidHandle {
        private final byte[] contents;
        private final int key;

        public OidHandle(byte[] bArr) {
            this.key = Arrays.hashCode(bArr);
            this.contents = bArr;
        }

        public boolean equals(Object obj) {
            if (obj instanceof OidHandle) {
                return Arrays.areEqual(this.contents, ((OidHandle) obj).contents);
            }
            return false;
        }

        public int hashCode() {
            return this.key;
        }
    }

    public ASN1ObjectIdentifier(String str) {
        if (str == null) {
            throw new NullPointerException("'identifier' cannot be null");
        }
        if (!isValidIdentifier(str)) {
            throw new IllegalArgumentException(AbstractC0000a.m16l("string ", str, " not an OID"));
        }
        this.identifier = str;
    }

    public static ASN1ObjectIdentifier createPrimitive(byte[] bArr, boolean z2) {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = pool.get(new OidHandle(bArr));
        return aSN1ObjectIdentifier == null ? new ASN1ObjectIdentifier(bArr, z2) : aSN1ObjectIdentifier;
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x003e  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:9:0x0048 -> B:4:0x0023). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void doOutput(ByteArrayOutputStream byteArrayOutputStream) {
        OIDTokenizer oIDTokenizer = new OIDTokenizer(this.identifier);
        int parseInt = Integer.parseInt(oIDTokenizer.nextToken()) * 40;
        String nextToken = oIDTokenizer.nextToken();
        if (nextToken.length() > 18) {
            ASN1RelativeOID.writeField(byteArrayOutputStream, new BigInteger(nextToken).add(BigInteger.valueOf(parseInt)));
            while (oIDTokenizer.hasMoreTokens()) {
            }
        }
        long parseLong = Long.parseLong(nextToken) + parseInt;
        ASN1RelativeOID.writeField(byteArrayOutputStream, parseLong);
        while (oIDTokenizer.hasMoreTokens()) {
            String nextToken2 = oIDTokenizer.nextToken();
            if (nextToken2.length() <= 18) {
                parseLong = Long.parseLong(nextToken2);
                ASN1RelativeOID.writeField(byteArrayOutputStream, parseLong);
                while (oIDTokenizer.hasMoreTokens()) {
                }
            } else {
                ASN1RelativeOID.writeField(byteArrayOutputStream, new BigInteger(nextToken2));
            }
        }
    }

    public static ASN1ObjectIdentifier fromContents(byte[] bArr) {
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

    public static ASN1ObjectIdentifier getInstance(Object obj) {
        if (obj == null || (obj instanceof ASN1ObjectIdentifier)) {
            return (ASN1ObjectIdentifier) obj;
        }
        if (obj instanceof ASN1Encodable) {
            ASN1Primitive aSN1Primitive = ((ASN1Encodable) obj).toASN1Primitive();
            if (aSN1Primitive instanceof ASN1ObjectIdentifier) {
                return (ASN1ObjectIdentifier) aSN1Primitive;
            }
        } else if (obj instanceof byte[]) {
            try {
                return (ASN1ObjectIdentifier) TYPE.fromByteArray((byte[]) obj);
            } catch (IOException e2) {
                throw new IllegalArgumentException(AbstractC0000a.m8d(e2, new StringBuilder("failed to construct object identifier from byte[]: ")));
            }
        }
        throw new IllegalArgumentException(AbstractC0000a.m10f(obj, "illegal object in getInstance: "));
    }

    private static boolean isValidIdentifier(String str) {
        char charAt;
        if (str.length() < 3 || str.charAt(1) != '.' || (charAt = str.charAt(0)) < '0' || charAt > '2') {
            return false;
        }
        return ASN1RelativeOID.isValidIdentifier(str, 2);
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (aSN1Primitive == this) {
            return true;
        }
        if (aSN1Primitive instanceof ASN1ObjectIdentifier) {
            return this.identifier.equals(((ASN1ObjectIdentifier) aSN1Primitive).identifier);
        }
        return false;
    }

    public ASN1ObjectIdentifier branch(String str) {
        return new ASN1ObjectIdentifier(this, str);
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public void encode(ASN1OutputStream aSN1OutputStream, boolean z2) {
        aSN1OutputStream.writeEncodingDL(z2, 6, getContents());
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

    public ASN1ObjectIdentifier intern() {
        OidHandle oidHandle = new OidHandle(getContents());
        ConcurrentMap<OidHandle, ASN1ObjectIdentifier> concurrentMap = pool;
        ASN1ObjectIdentifier aSN1ObjectIdentifier = concurrentMap.get(oidHandle);
        if (aSN1ObjectIdentifier != null) {
            return aSN1ObjectIdentifier;
        }
        ASN1ObjectIdentifier putIfAbsent = concurrentMap.putIfAbsent(oidHandle, this);
        return putIfAbsent == null ? this : putIfAbsent;
    }

    public boolean on(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        String id = getId();
        String id2 = aSN1ObjectIdentifier.getId();
        return id.length() > id2.length() && id.charAt(id2.length()) == '.' && id.startsWith(id2);
    }

    public String toString() {
        return getId();
    }

    public ASN1ObjectIdentifier(ASN1ObjectIdentifier aSN1ObjectIdentifier, String str) {
        if (!ASN1RelativeOID.isValidIdentifier(str, 0)) {
            throw new IllegalArgumentException(AbstractC0000a.m16l("string ", str, " not a valid OID branch"));
        }
        this.identifier = aSN1ObjectIdentifier.getId() + "." + str;
    }

    public static ASN1ObjectIdentifier getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        if (!z2 && !aSN1TaggedObject.isParsed()) {
            ASN1Primitive object = aSN1TaggedObject.getObject();
            if (!(object instanceof ASN1ObjectIdentifier)) {
                return fromContents(ASN1OctetString.getInstance(object).getOctets());
            }
        }
        return (ASN1ObjectIdentifier) TYPE.getContextInstance(aSN1TaggedObject, z2);
    }

    public ASN1ObjectIdentifier(byte[] bArr, boolean z2) {
        int i2;
        byte[] bArr2 = bArr;
        StringBuffer stringBuffer = new StringBuffer();
        boolean z3 = true;
        BigInteger bigInteger = null;
        int i3 = 0;
        long j2 = 0;
        while (i3 != bArr2.length) {
            int i4 = bArr2[i3] & 255;
            if (j2 <= LONG_LIMIT) {
                i2 = i3;
                long j3 = j2 + (i4 & CertificateBody.profileType);
                if ((i4 & 128) == 0) {
                    if (z3) {
                        if (j3 < 40) {
                            stringBuffer.append('0');
                        } else if (j3 < 80) {
                            stringBuffer.append('1');
                            j3 -= 40;
                        } else {
                            stringBuffer.append('2');
                            j3 -= 80;
                        }
                        z3 = false;
                    }
                    stringBuffer.append('.');
                    stringBuffer.append(j3);
                    j2 = 0;
                    i3 = i2 + 1;
                } else {
                    j2 = j3 << 7;
                    i3 = i2 + 1;
                }
            } else {
                i2 = i3;
                BigInteger or = (bigInteger == null ? BigInteger.valueOf(j2) : bigInteger).or(BigInteger.valueOf(i4 & CertificateBody.profileType));
                if ((i4 & 128) == 0) {
                    if (z3) {
                        stringBuffer.append('2');
                        or = or.subtract(BigInteger.valueOf(80L));
                        z3 = false;
                    }
                    stringBuffer.append('.');
                    stringBuffer.append(or);
                    bigInteger = null;
                    j2 = 0;
                    i3 = i2 + 1;
                } else {
                    bigInteger = or.shiftLeft(7);
                    i3 = i2 + 1;
                }
            }
        }
        this.identifier = stringBuffer.toString();
        this.contents = z2 ? Arrays.clone(bArr) : bArr2;
    }
}
