package org.bouncycastle.crypto.modes;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.StreamBlockCipher;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public class G3413OFBBlockCipher extends StreamBlockCipher {

    /* renamed from: R */
    private byte[] f1266R;
    private byte[] R_init;

    /* renamed from: Y */
    private byte[] f1267Y;
    private int blockSize;
    private int byteCount;
    private BlockCipher cipher;
    private boolean initialized;

    /* renamed from: m */
    private int f1268m;

    public G3413OFBBlockCipher(BlockCipher blockCipher) {
        super(blockCipher);
        this.initialized = false;
        int blockSize = blockCipher.getBlockSize();
        this.blockSize = blockSize;
        this.cipher = blockCipher;
        this.f1267Y = new byte[blockSize];
    }

    private void generateR() {
        byte[] LSB = GOST3413CipherUtil.LSB(this.f1266R, this.f1268m - this.blockSize);
        System.arraycopy(LSB, 0, this.f1266R, 0, LSB.length);
        System.arraycopy(this.f1267Y, 0, this.f1266R, LSB.length, this.f1268m - LSB.length);
    }

    private void generateY() {
        this.cipher.processBlock(GOST3413CipherUtil.MSB(this.f1266R, this.blockSize), 0, this.f1267Y, 0);
    }

    private void initArrays() {
        int i2 = this.f1268m;
        this.f1266R = new byte[i2];
        this.R_init = new byte[i2];
    }

    private void setupDefaultParams() {
        this.f1268m = this.blockSize * 2;
    }

    @Override // org.bouncycastle.crypto.StreamBlockCipher
    public byte calculateByte(byte b) {
        if (this.byteCount == 0) {
            generateY();
        }
        byte[] bArr = this.f1267Y;
        int i2 = this.byteCount;
        byte b2 = (byte) (b ^ bArr[i2]);
        int i3 = i2 + 1;
        this.byteCount = i3;
        if (i3 == getBlockSize()) {
            this.byteCount = 0;
            generateR();
        }
        return b2;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public String getAlgorithmName() {
        return this.cipher.getAlgorithmName() + "/OFB";
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return this.blockSize;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        BlockCipher blockCipher;
        if (!(cipherParameters instanceof ParametersWithIV)) {
            setupDefaultParams();
            initArrays();
            byte[] bArr = this.R_init;
            System.arraycopy(bArr, 0, this.f1266R, 0, bArr.length);
            if (cipherParameters != null) {
                blockCipher = this.cipher;
                blockCipher.init(true, cipherParameters);
            }
            this.initialized = true;
        }
        ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
        byte[] iv = parametersWithIV.getIV();
        if (iv.length < this.blockSize) {
            throw new IllegalArgumentException("Parameter m must blockSize <= m");
        }
        this.f1268m = iv.length;
        initArrays();
        byte[] clone = Arrays.clone(iv);
        this.R_init = clone;
        System.arraycopy(clone, 0, this.f1266R, 0, clone.length);
        if (parametersWithIV.getParameters() != null) {
            blockCipher = this.cipher;
            cipherParameters = parametersWithIV.getParameters();
            blockCipher.init(true, cipherParameters);
        }
        this.initialized = true;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        processBytes(bArr, i2, this.blockSize, bArr2, i3);
        return this.blockSize;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void reset() {
        if (this.initialized) {
            byte[] bArr = this.R_init;
            System.arraycopy(bArr, 0, this.f1266R, 0, bArr.length);
            Arrays.clear(this.f1267Y);
            this.byteCount = 0;
            this.cipher.reset();
        }
    }
}
