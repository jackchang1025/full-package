package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public class HashedId extends ASN1Object {
    private final byte[] string;

    public static class HashedId10 extends HashedId {
        public HashedId10(byte[] bArr) {
            super(bArr);
            if (bArr.length != 10) {
                throw new IllegalArgumentException("hash id not 10 bytes");
            }
        }
    }

    public static class HashedId3 extends HashedId {
        public HashedId3(byte[] bArr) {
            super(bArr);
            if (bArr.length != 3) {
                throw new IllegalArgumentException("hash id not 3 bytes");
            }
        }
    }

    public static class HashedId32 extends HashedId {
        public HashedId32(byte[] bArr) {
            super(bArr);
            if (bArr.length != 32) {
                throw new IllegalArgumentException("hash id not 32 bytes");
            }
        }
    }

    public static class HashedId8 extends HashedId {
        public HashedId8(byte[] bArr) {
            super(bArr);
            if (bArr.length != 8) {
                throw new IllegalArgumentException("hash id not 8 bytes");
            }
        }
    }

    public HashedId(byte[] bArr) {
        this.string = Arrays.clone(bArr);
    }

    public static HashedId getInstance(Object obj) {
        if (obj instanceof HashedId) {
            return (HashedId) obj;
        }
        byte[] octets = ASN1OctetString.getInstance(obj).getOctets();
        int length = octets.length;
        if (length == 3) {
            return new HashedId3(octets);
        }
        if (length == 8) {
            return new HashedId8(octets);
        }
        if (length == 10) {
            return new HashedId10(octets);
        }
        if (length == 32) {
            return new HashedId32(octets);
        }
        throw new IllegalStateException("hash id of unsupported length, length was: " + octets.length);
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return new DEROctetString(this.string);
    }
}
