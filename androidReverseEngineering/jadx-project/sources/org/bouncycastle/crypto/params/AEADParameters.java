package org.bouncycastle.crypto.params;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public class AEADParameters implements CipherParameters {
    private byte[] associatedText;
    private KeyParameter key;
    private int macSize;
    private byte[] nonce;

    public AEADParameters(KeyParameter keyParameter, int i2, byte[] bArr) {
        this(keyParameter, i2, bArr, null);
    }

    public byte[] getAssociatedText() {
        return Arrays.clone(this.associatedText);
    }

    public KeyParameter getKey() {
        return this.key;
    }

    public int getMacSize() {
        return this.macSize;
    }

    public byte[] getNonce() {
        return Arrays.clone(this.nonce);
    }

    public AEADParameters(KeyParameter keyParameter, int i2, byte[] bArr, byte[] bArr2) {
        this.key = keyParameter;
        this.nonce = Arrays.clone(bArr);
        this.macSize = i2;
        this.associatedText = Arrays.clone(bArr2);
    }
}
