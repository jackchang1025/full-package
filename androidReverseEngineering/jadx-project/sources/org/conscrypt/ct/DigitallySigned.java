package org.conscrypt.ct;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class DigitallySigned {
    private final HashAlgorithm hashAlgorithm;
    private final byte[] signature;
    private final SignatureAlgorithm signatureAlgorithm;

    public enum HashAlgorithm {
        NONE,
        MD5,
        SHA1,
        SHA224,
        SHA256,
        SHA384,
        SHA512;

        private static HashAlgorithm[] values = values();

        public static HashAlgorithm valueOf(int i2) {
            try {
                return values[i2];
            } catch (IndexOutOfBoundsException e2) {
                throw new IllegalArgumentException(AbstractC0000a.m11g("Invalid hash algorithm ", i2), e2);
            }
        }
    }

    public enum SignatureAlgorithm {
        ANONYMOUS,
        RSA,
        DSA,
        ECDSA;

        private static SignatureAlgorithm[] values = values();

        public static SignatureAlgorithm valueOf(int i2) {
            try {
                return values[i2];
            } catch (IndexOutOfBoundsException e2) {
                throw new IllegalArgumentException(AbstractC0000a.m11g("Invalid signature algorithm ", i2), e2);
            }
        }
    }

    public DigitallySigned(int i2, int i3, byte[] bArr) {
        this(HashAlgorithm.valueOf(i2), SignatureAlgorithm.valueOf(i3), bArr);
    }

    public static DigitallySigned decode(InputStream inputStream) {
        try {
            return new DigitallySigned(Serialization.readNumber(inputStream, 1), Serialization.readNumber(inputStream, 1), Serialization.readVariableBytes(inputStream, 2));
        } catch (IllegalArgumentException e2) {
            throw new SerializationException(e2);
        }
    }

    public String getAlgorithm() {
        return String.format("%swith%s", this.hashAlgorithm, this.signatureAlgorithm);
    }

    public HashAlgorithm getHashAlgorithm() {
        return this.hashAlgorithm;
    }

    public byte[] getSignature() {
        return this.signature;
    }

    public SignatureAlgorithm getSignatureAlgorithm() {
        return this.signatureAlgorithm;
    }

    public DigitallySigned(HashAlgorithm hashAlgorithm, SignatureAlgorithm signatureAlgorithm, byte[] bArr) {
        this.hashAlgorithm = hashAlgorithm;
        this.signatureAlgorithm = signatureAlgorithm;
        this.signature = bArr;
    }

    public static DigitallySigned decode(byte[] bArr) {
        return decode(new ByteArrayInputStream(bArr));
    }
}
