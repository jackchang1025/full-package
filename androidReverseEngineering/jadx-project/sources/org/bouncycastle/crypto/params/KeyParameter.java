package org.bouncycastle.crypto.params;

import org.bouncycastle.crypto.CipherParameters;

/* loaded from: classes.dex */
public class KeyParameter implements CipherParameters {
    private byte[] key;

    public KeyParameter(byte[] bArr) {
        this(bArr, 0, bArr.length);
    }

    public byte[] getKey() {
        return this.key;
    }

    public KeyParameter(byte[] bArr, int i2, int i3) {
        byte[] bArr2 = new byte[i3];
        this.key = bArr2;
        System.arraycopy(bArr, i2, bArr2, 0, i3);
    }
}
