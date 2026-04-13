package org.bouncycastle.crypto.modes;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.StreamBlockCipher;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public class G3413CFBBlockCipher extends StreamBlockCipher {

    /* renamed from: R */
    private byte[] f1262R;
    private byte[] R_init;
    private int blockSize;
    private int byteCount;
    private BlockCipher cipher;
    private boolean forEncryption;
    private byte[] gamma;
    private byte[] inBuf;
    private boolean initialized;

    /* renamed from: m */
    private int f1263m;

    /* renamed from: s */
    private final int f1264s;

    public G3413CFBBlockCipher(BlockCipher blockCipher) {
        this(blockCipher, blockCipher.getBlockSize() * 8);
    }

    private void initArrays() {
        int i2 = this.f1263m;
        this.f1262R = new byte[i2];
        this.R_init = new byte[i2];
    }

    private void setupDefaultParams() {
        this.f1263m = this.blockSize * 2;
    }

    @Override // org.bouncycastle.crypto.StreamBlockCipher
    public byte calculateByte(byte b) {
        if (this.byteCount == 0) {
            this.gamma = createGamma();
        }
        byte[] bArr = this.gamma;
        int i2 = this.byteCount;
        byte b2 = (byte) (bArr[i2] ^ b);
        byte[] bArr2 = this.inBuf;
        int i3 = i2 + 1;
        this.byteCount = i3;
        if (this.forEncryption) {
            b = b2;
        }
        bArr2[i2] = b;
        if (i3 == getBlockSize()) {
            this.byteCount = 0;
            generateR(this.inBuf);
        }
        return b2;
    }

    public byte[] createGamma() {
        byte[] MSB = GOST3413CipherUtil.MSB(this.f1262R, this.blockSize);
        byte[] bArr = new byte[MSB.length];
        this.cipher.processBlock(MSB, 0, bArr, 0);
        return GOST3413CipherUtil.MSB(bArr, this.f1264s);
    }

    public void generateR(byte[] bArr) {
        byte[] LSB = GOST3413CipherUtil.LSB(this.f1262R, this.f1263m - this.f1264s);
        System.arraycopy(LSB, 0, this.f1262R, 0, LSB.length);
        System.arraycopy(bArr, 0, this.f1262R, LSB.length, this.f1263m - LSB.length);
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public String getAlgorithmName() {
        return this.cipher.getAlgorithmName() + "/CFB" + (this.blockSize * 8);
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return this.f1264s;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        BlockCipher blockCipher;
        this.forEncryption = z2;
        if (!(cipherParameters instanceof ParametersWithIV)) {
            setupDefaultParams();
            initArrays();
            byte[] bArr = this.R_init;
            System.arraycopy(bArr, 0, this.f1262R, 0, bArr.length);
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
        this.f1263m = iv.length;
        initArrays();
        byte[] clone = Arrays.clone(iv);
        this.R_init = clone;
        System.arraycopy(clone, 0, this.f1262R, 0, clone.length);
        if (parametersWithIV.getParameters() != null) {
            blockCipher = this.cipher;
            cipherParameters = parametersWithIV.getParameters();
            blockCipher.init(true, cipherParameters);
        }
        this.initialized = true;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        processBytes(bArr, i2, getBlockSize(), bArr2, i3);
        return getBlockSize();
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void reset() {
        this.byteCount = 0;
        Arrays.clear(this.inBuf);
        Arrays.clear(this.gamma);
        if (this.initialized) {
            byte[] bArr = this.R_init;
            System.arraycopy(bArr, 0, this.f1262R, 0, bArr.length);
            this.cipher.reset();
        }
    }

    public G3413CFBBlockCipher(BlockCipher blockCipher, int i2) {
        super(blockCipher);
        this.initialized = false;
        if (i2 < 0 || i2 > blockCipher.getBlockSize() * 8) {
            throw new IllegalArgumentException("Parameter bitBlockSize must be in range 0 < bitBlockSize <= " + (blockCipher.getBlockSize() * 8));
        }
        this.blockSize = blockCipher.getBlockSize();
        this.cipher = blockCipher;
        this.f1264s = i2 / 8;
        this.inBuf = new byte[getBlockSize()];
    }
}
