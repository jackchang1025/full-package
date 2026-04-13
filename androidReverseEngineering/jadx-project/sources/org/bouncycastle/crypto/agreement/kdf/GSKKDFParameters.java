package org.bouncycastle.crypto.agreement.kdf;

import org.bouncycastle.crypto.DerivationParameters;

/* loaded from: classes.dex */
public class GSKKDFParameters implements DerivationParameters {
    private final byte[] nonce;
    private final int startCounter;

    /* renamed from: z */
    private final byte[] f1135z;

    public GSKKDFParameters(byte[] bArr, int i2) {
        this(bArr, i2, null);
    }

    public byte[] getNonce() {
        return this.nonce;
    }

    public int getStartCounter() {
        return this.startCounter;
    }

    public byte[] getZ() {
        return this.f1135z;
    }

    public GSKKDFParameters(byte[] bArr, int i2, byte[] bArr2) {
        this.f1135z = bArr;
        this.startCounter = i2;
        this.nonce = bArr2;
    }
}
