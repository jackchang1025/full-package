package org.bouncycastle.crypto.prng;

import org.bouncycastle.crypto.BlockCipher;

/* loaded from: classes.dex */
public class X931RNG {
    private static final int BLOCK128_MAX_BITS_REQUEST = 262144;
    private static final long BLOCK128_RESEED_MAX = 8388608;
    private static final int BLOCK64_MAX_BITS_REQUEST = 4096;
    private static final long BLOCK64_RESEED_MAX = 32768;
    private final byte[] DT;

    /* renamed from: I */
    private final byte[] f1339I;

    /* renamed from: R */
    private final byte[] f1340R;

    /* renamed from: V */
    private byte[] f1341V;
    private final BlockCipher engine;
    private final EntropySource entropySource;
    private long reseedCounter = 1;

    public X931RNG(BlockCipher blockCipher, byte[] bArr, EntropySource entropySource) {
        this.engine = blockCipher;
        this.entropySource = entropySource;
        byte[] bArr2 = new byte[blockCipher.getBlockSize()];
        this.DT = bArr2;
        System.arraycopy(bArr, 0, bArr2, 0, bArr2.length);
        this.f1339I = new byte[blockCipher.getBlockSize()];
        this.f1340R = new byte[blockCipher.getBlockSize()];
    }

    private void increment(byte[] bArr) {
        for (int length = bArr.length - 1; length >= 0; length--) {
            byte b = (byte) (bArr[length] + 1);
            bArr[length] = b;
            if (b != 0) {
                return;
            }
        }
    }

    private static boolean isTooLarge(byte[] bArr, int i2) {
        return bArr != null && bArr.length > i2;
    }

    private void process(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        for (int i2 = 0; i2 != bArr.length; i2++) {
            bArr[i2] = (byte) (bArr2[i2] ^ bArr3[i2]);
        }
        this.engine.processBlock(bArr, 0, bArr, 0);
    }

    public int generate(byte[] bArr, boolean z2) {
        if (this.f1340R.length == 8) {
            if (this.reseedCounter > BLOCK64_RESEED_MAX) {
                return -1;
            }
            if (isTooLarge(bArr, 512)) {
                throw new IllegalArgumentException("Number of bits per request limited to 4096");
            }
        } else {
            if (this.reseedCounter > BLOCK128_RESEED_MAX) {
                return -1;
            }
            if (isTooLarge(bArr, 32768)) {
                throw new IllegalArgumentException("Number of bits per request limited to 262144");
            }
        }
        if (z2 || this.f1341V == null) {
            byte[] entropy = this.entropySource.getEntropy();
            this.f1341V = entropy;
            if (entropy.length != this.engine.getBlockSize()) {
                throw new IllegalStateException("Insufficient entropy returned");
            }
        }
        int length = bArr.length / this.f1340R.length;
        for (int i2 = 0; i2 < length; i2++) {
            this.engine.processBlock(this.DT, 0, this.f1339I, 0);
            process(this.f1340R, this.f1339I, this.f1341V);
            process(this.f1341V, this.f1340R, this.f1339I);
            byte[] bArr2 = this.f1340R;
            System.arraycopy(bArr2, 0, bArr, bArr2.length * i2, bArr2.length);
            increment(this.DT);
        }
        int length2 = bArr.length - (this.f1340R.length * length);
        if (length2 > 0) {
            this.engine.processBlock(this.DT, 0, this.f1339I, 0);
            process(this.f1340R, this.f1339I, this.f1341V);
            process(this.f1341V, this.f1340R, this.f1339I);
            byte[] bArr3 = this.f1340R;
            System.arraycopy(bArr3, 0, bArr, length * bArr3.length, length2);
            increment(this.DT);
        }
        this.reseedCounter++;
        return bArr.length;
    }

    public EntropySource getEntropySource() {
        return this.entropySource;
    }

    public void reseed() {
        byte[] entropy = this.entropySource.getEntropy();
        this.f1341V = entropy;
        if (entropy.length != this.engine.getBlockSize()) {
            throw new IllegalStateException("Insufficient entropy returned");
        }
        this.reseedCounter = 1L;
    }
}
