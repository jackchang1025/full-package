package org.bouncycastle.crypto.params;

import java.io.EOFException;
import java.io.InputStream;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.io.Streams;

/* loaded from: classes.dex */
public final class Ed448PublicKeyParameters extends AsymmetricKeyParameter {
    public static final int KEY_SIZE = 57;
    private final byte[] data;

    public Ed448PublicKeyParameters(InputStream inputStream) {
        super(false);
        byte[] bArr = new byte[57];
        this.data = bArr;
        if (57 != Streams.readFully(inputStream, bArr)) {
            throw new EOFException("EOF encountered in middle of Ed448 public key");
        }
    }

    private static byte[] validate(byte[] bArr) {
        if (bArr.length == 57) {
            return bArr;
        }
        throw new IllegalArgumentException("'buf' must have length 57");
    }

    public void encode(byte[] bArr, int i2) {
        System.arraycopy(this.data, 0, bArr, i2, 57);
    }

    public byte[] getEncoded() {
        return Arrays.clone(this.data);
    }

    public Ed448PublicKeyParameters(byte[] bArr) {
        this(validate(bArr), 0);
    }

    public Ed448PublicKeyParameters(byte[] bArr, int i2) {
        super(false);
        byte[] bArr2 = new byte[57];
        this.data = bArr2;
        System.arraycopy(bArr, i2, bArr2, 0, 57);
    }
}
