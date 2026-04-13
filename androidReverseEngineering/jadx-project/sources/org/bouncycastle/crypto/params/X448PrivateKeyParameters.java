package org.bouncycastle.crypto.params;

import java.io.EOFException;
import java.io.InputStream;
import java.security.SecureRandom;
import org.bouncycastle.math.ec.rfc7748.X448;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.io.Streams;

/* loaded from: classes.dex */
public final class X448PrivateKeyParameters extends AsymmetricKeyParameter {
    public static final int KEY_SIZE = 56;
    public static final int SECRET_SIZE = 56;
    private final byte[] data;

    public X448PrivateKeyParameters(InputStream inputStream) {
        super(true);
        byte[] bArr = new byte[56];
        this.data = bArr;
        if (56 != Streams.readFully(inputStream, bArr)) {
            throw new EOFException("EOF encountered in middle of X448 private key");
        }
    }

    private static byte[] validate(byte[] bArr) {
        if (bArr.length == 56) {
            return bArr;
        }
        throw new IllegalArgumentException("'buf' must have length 56");
    }

    public void encode(byte[] bArr, int i2) {
        System.arraycopy(this.data, 0, bArr, i2, 56);
    }

    public X448PublicKeyParameters generatePublicKey() {
        byte[] bArr = new byte[56];
        X448.generatePublicKey(this.data, 0, bArr, 0);
        return new X448PublicKeyParameters(bArr, 0);
    }

    public void generateSecret(X448PublicKeyParameters x448PublicKeyParameters, byte[] bArr, int i2) {
        byte[] bArr2 = new byte[56];
        x448PublicKeyParameters.encode(bArr2, 0);
        if (!X448.calculateAgreement(this.data, 0, bArr2, 0, bArr, i2)) {
            throw new IllegalStateException("X448 agreement failed");
        }
    }

    public byte[] getEncoded() {
        return Arrays.clone(this.data);
    }

    public X448PrivateKeyParameters(SecureRandom secureRandom) {
        super(true);
        byte[] bArr = new byte[56];
        this.data = bArr;
        X448.generatePrivateKey(secureRandom, bArr);
    }

    public X448PrivateKeyParameters(byte[] bArr) {
        this(validate(bArr), 0);
    }

    public X448PrivateKeyParameters(byte[] bArr, int i2) {
        super(true);
        byte[] bArr2 = new byte[56];
        this.data = bArr2;
        System.arraycopy(bArr, i2, bArr2, 0, 56);
    }
}
