package org.bouncycastle.crypto.params;

import java.io.EOFException;
import java.io.InputStream;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.io.Streams;

/* loaded from: classes.dex */
public final class X448PublicKeyParameters extends AsymmetricKeyParameter {
    public static final int KEY_SIZE = 56;
    private final byte[] data;

    public X448PublicKeyParameters(InputStream inputStream) {
        super(false);
        byte[] bArr = new byte[56];
        this.data = bArr;
        if (56 != Streams.readFully(inputStream, bArr)) {
            throw new EOFException("EOF encountered in middle of X448 public key");
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

    public byte[] getEncoded() {
        return Arrays.clone(this.data);
    }

    public X448PublicKeyParameters(byte[] bArr) {
        this(validate(bArr), 0);
    }

    public X448PublicKeyParameters(byte[] bArr, int i2) {
        super(false);
        byte[] bArr2 = new byte[56];
        this.data = bArr2;
        System.arraycopy(bArr, i2, bArr2, 0, 56);
    }
}
