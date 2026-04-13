package org.bouncycastle.crypto.params;

import java.io.EOFException;
import java.io.InputStream;
import java.security.SecureRandom;
import org.bouncycastle.math.ec.rfc8032.Ed25519;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.io.Streams;

/* loaded from: classes.dex */
public final class Ed25519PrivateKeyParameters extends AsymmetricKeyParameter {
    public static final int KEY_SIZE = 32;
    public static final int SIGNATURE_SIZE = 64;
    private Ed25519PublicKeyParameters cachedPublicKey;
    private final byte[] data;

    public Ed25519PrivateKeyParameters(InputStream inputStream) {
        super(true);
        byte[] bArr = new byte[32];
        this.data = bArr;
        if (32 != Streams.readFully(inputStream, bArr)) {
            throw new EOFException("EOF encountered in middle of Ed25519 private key");
        }
    }

    private static byte[] validate(byte[] bArr) {
        if (bArr.length == 32) {
            return bArr;
        }
        throw new IllegalArgumentException("'buf' must have length 32");
    }

    public void encode(byte[] bArr, int i2) {
        System.arraycopy(this.data, 0, bArr, i2, 32);
    }

    public Ed25519PublicKeyParameters generatePublicKey() {
        Ed25519PublicKeyParameters ed25519PublicKeyParameters;
        synchronized (this.data) {
            if (this.cachedPublicKey == null) {
                byte[] bArr = new byte[32];
                Ed25519.generatePublicKey(this.data, 0, bArr, 0);
                this.cachedPublicKey = new Ed25519PublicKeyParameters(bArr, 0);
            }
            ed25519PublicKeyParameters = this.cachedPublicKey;
        }
        return ed25519PublicKeyParameters;
    }

    public byte[] getEncoded() {
        return Arrays.clone(this.data);
    }

    public void sign(int i2, Ed25519PublicKeyParameters ed25519PublicKeyParameters, byte[] bArr, byte[] bArr2, int i3, int i4, byte[] bArr3, int i5) {
        sign(i2, bArr, bArr2, i3, i4, bArr3, i5);
    }

    public Ed25519PrivateKeyParameters(SecureRandom secureRandom) {
        super(true);
        byte[] bArr = new byte[32];
        this.data = bArr;
        Ed25519.generatePrivateKey(secureRandom, bArr);
    }

    public void sign(int i2, byte[] bArr, byte[] bArr2, int i3, int i4, byte[] bArr3, int i5) {
        byte[] bArr4 = new byte[32];
        generatePublicKey().encode(bArr4, 0);
        if (i2 == 0) {
            if (bArr != null) {
                throw new IllegalArgumentException("ctx");
            }
            Ed25519.sign(this.data, 0, bArr4, 0, bArr2, i3, i4, bArr3, i5);
        } else if (i2 == 1) {
            Ed25519.sign(this.data, 0, bArr4, 0, bArr, bArr2, i3, i4, bArr3, i5);
        } else {
            if (i2 != 2) {
                throw new IllegalArgumentException("algorithm");
            }
            if (64 != i4) {
                throw new IllegalArgumentException("msgLen");
            }
            Ed25519.signPrehash(this.data, 0, bArr4, 0, bArr, bArr2, i3, bArr3, i5);
        }
    }

    public Ed25519PrivateKeyParameters(byte[] bArr) {
        this(validate(bArr), 0);
    }

    public Ed25519PrivateKeyParameters(byte[] bArr, int i2) {
        super(true);
        byte[] bArr2 = new byte[32];
        this.data = bArr2;
        System.arraycopy(bArr, i2, bArr2, 0, 32);
    }
}
