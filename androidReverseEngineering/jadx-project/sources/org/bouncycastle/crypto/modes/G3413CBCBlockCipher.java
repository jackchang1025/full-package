package org.bouncycastle.crypto.modes;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public class G3413CBCBlockCipher implements BlockCipher {

    /* renamed from: R */
    private byte[] f1260R;
    private byte[] R_init;
    private int blockSize;
    private BlockCipher cipher;
    private boolean forEncryption;
    private boolean initialized = false;

    /* renamed from: m */
    private int f1261m;

    public G3413CBCBlockCipher(BlockCipher blockCipher) {
        this.blockSize = blockCipher.getBlockSize();
        this.cipher = blockCipher;
    }

    private int decrypt(byte[] bArr, int i2, byte[] bArr2, int i3) {
        byte[] MSB = GOST3413CipherUtil.MSB(this.f1260R, this.blockSize);
        byte[] copyFromInput = GOST3413CipherUtil.copyFromInput(bArr, this.blockSize, i2);
        byte[] bArr3 = new byte[copyFromInput.length];
        this.cipher.processBlock(copyFromInput, 0, bArr3, 0);
        byte[] sum = GOST3413CipherUtil.sum(bArr3, MSB);
        System.arraycopy(sum, 0, bArr2, i3, sum.length);
        if (bArr2.length > i3 + sum.length) {
            generateR(copyFromInput);
        }
        return sum.length;
    }

    private int encrypt(byte[] bArr, int i2, byte[] bArr2, int i3) {
        byte[] sum = GOST3413CipherUtil.sum(GOST3413CipherUtil.copyFromInput(bArr, this.blockSize, i2), GOST3413CipherUtil.MSB(this.f1260R, this.blockSize));
        int length = sum.length;
        byte[] bArr3 = new byte[length];
        this.cipher.processBlock(sum, 0, bArr3, 0);
        System.arraycopy(bArr3, 0, bArr2, i3, length);
        if (bArr2.length > i3 + sum.length) {
            generateR(bArr3);
        }
        return length;
    }

    private void generateR(byte[] bArr) {
        byte[] LSB = GOST3413CipherUtil.LSB(this.f1260R, this.f1261m - this.blockSize);
        System.arraycopy(LSB, 0, this.f1260R, 0, LSB.length);
        System.arraycopy(bArr, 0, this.f1260R, LSB.length, this.f1261m - LSB.length);
    }

    private void initArrays() {
        int i2 = this.f1261m;
        this.f1260R = new byte[i2];
        this.R_init = new byte[i2];
    }

    private void setupDefaultParams() {
        this.f1261m = this.blockSize;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public String getAlgorithmName() {
        return this.cipher.getAlgorithmName() + "/CBC";
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return this.blockSize;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        BlockCipher blockCipher;
        this.forEncryption = z2;
        if (!(cipherParameters instanceof ParametersWithIV)) {
            setupDefaultParams();
            initArrays();
            byte[] bArr = this.R_init;
            System.arraycopy(bArr, 0, this.f1260R, 0, bArr.length);
            if (cipherParameters != null) {
                blockCipher = this.cipher;
                blockCipher.init(z2, cipherParameters);
            }
            this.initialized = true;
        }
        ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
        byte[] iv = parametersWithIV.getIV();
        if (iv.length < this.blockSize) {
            throw new IllegalArgumentException("Parameter m must blockSize <= m");
        }
        this.f1261m = iv.length;
        initArrays();
        byte[] clone = Arrays.clone(iv);
        this.R_init = clone;
        System.arraycopy(clone, 0, this.f1260R, 0, clone.length);
        if (parametersWithIV.getParameters() != null) {
            blockCipher = this.cipher;
            cipherParameters = parametersWithIV.getParameters();
            blockCipher.init(z2, cipherParameters);
        }
        this.initialized = true;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        return this.forEncryption ? encrypt(bArr, i2, bArr2, i3) : decrypt(bArr, i2, bArr2, i3);
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void reset() {
        if (this.initialized) {
            byte[] bArr = this.R_init;
            System.arraycopy(bArr, 0, this.f1260R, 0, bArr.length);
            this.cipher.reset();
        }
    }
}
