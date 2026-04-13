package org.bouncycastle.crypto.modes;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.modes.gcm.BasicGCMExponentiator;
import org.bouncycastle.crypto.modes.gcm.GCMExponentiator;
import org.bouncycastle.crypto.modes.gcm.GCMMultiplier;
import org.bouncycastle.crypto.modes.gcm.GCMUtil;
import org.bouncycastle.crypto.modes.gcm.Tables4kGCMMultiplier;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Pack;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class GCMBlockCipher implements AEADBlockCipher {
    private static final int BLOCK_SIZE = 16;

    /* renamed from: H */
    private byte[] f1270H;
    private byte[] J0;

    /* renamed from: S */
    private byte[] f1271S;
    private byte[] S_at;
    private byte[] S_atPre;
    private byte[] atBlock;
    private int atBlockPos;
    private long atLength;
    private long atLengthPre;
    private int blocksRemaining;
    private byte[] bufBlock;
    private int bufOff;
    private BlockCipher cipher;
    private byte[] counter;
    private GCMExponentiator exp;
    private boolean forEncryption;
    private byte[] initialAssociatedText;
    private boolean initialised;
    private byte[] lastKey;
    private byte[] macBlock;
    private int macSize;
    private GCMMultiplier multiplier;
    private byte[] nonce;
    private long totalLength;

    public GCMBlockCipher(BlockCipher blockCipher) {
        this(blockCipher, null);
    }

    private void checkStatus() {
        if (this.initialised) {
            return;
        }
        if (!this.forEncryption) {
            throw new IllegalStateException("GCM cipher needs to be initialised");
        }
        throw new IllegalStateException("GCM cipher cannot be reused for encryption");
    }

    private void gHASH(byte[] bArr, byte[] bArr2, int i2) {
        for (int i3 = 0; i3 < i2; i3 += 16) {
            gHASHPartial(bArr, bArr2, i3, Math.min(i2 - i3, 16));
        }
    }

    private void gHASHBlock(byte[] bArr, byte[] bArr2) {
        GCMUtil.xor(bArr, bArr2);
        this.multiplier.multiplyH(bArr);
    }

    private void gHASHPartial(byte[] bArr, byte[] bArr2, int i2, int i3) {
        GCMUtil.xor(bArr, bArr2, i2, i3);
        this.multiplier.multiplyH(bArr);
    }

    private void getNextCTRBlock(byte[] bArr) {
        int i2 = this.blocksRemaining;
        if (i2 == 0) {
            throw new IllegalStateException("Attempt to process too many blocks");
        }
        this.blocksRemaining = i2 - 1;
        byte[] bArr2 = this.counter;
        int i3 = (bArr2[15] & 255) + 1;
        bArr2[15] = (byte) i3;
        int i4 = (i3 >>> 8) + (bArr2[14] & 255);
        bArr2[14] = (byte) i4;
        int i5 = (i4 >>> 8) + (bArr2[13] & 255);
        bArr2[13] = (byte) i5;
        bArr2[12] = (byte) ((i5 >>> 8) + (bArr2[12] & 255));
        this.cipher.processBlock(bArr2, 0, bArr, 0);
    }

    private void initCipher() {
        if (this.atLength > 0) {
            System.arraycopy(this.S_at, 0, this.S_atPre, 0, 16);
            this.atLengthPre = this.atLength;
        }
        int i2 = this.atBlockPos;
        if (i2 > 0) {
            gHASHPartial(this.S_atPre, this.atBlock, 0, i2);
            this.atLengthPre += this.atBlockPos;
        }
        if (this.atLengthPre > 0) {
            System.arraycopy(this.S_atPre, 0, this.f1271S, 0, 16);
        }
    }

    private void processBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        if (bArr2.length - i3 < 16) {
            throw new OutputLengthException("Output buffer too short");
        }
        if (this.totalLength == 0) {
            initCipher();
        }
        byte[] bArr3 = new byte[16];
        getNextCTRBlock(bArr3);
        if (this.forEncryption) {
            GCMUtil.xor(bArr3, bArr, i2);
            gHASHBlock(this.f1271S, bArr3);
            System.arraycopy(bArr3, 0, bArr2, i3, 16);
        } else {
            gHASHBlock(this.f1271S, bArr, i2);
            GCMUtil.xor(bArr3, 0, bArr, i2, bArr2, i3);
        }
        this.totalLength += 16;
    }

    private void processPartial(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        byte[] bArr3 = new byte[16];
        getNextCTRBlock(bArr3);
        if (this.forEncryption) {
            GCMUtil.xor(bArr, i2, bArr3, 0, i3);
            gHASHPartial(this.f1271S, bArr, i2, i3);
        } else {
            gHASHPartial(this.f1271S, bArr, i2, i3);
            GCMUtil.xor(bArr, i2, bArr3, 0, i3);
        }
        System.arraycopy(bArr, i2, bArr2, i4, i3);
        this.totalLength += i3;
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public int doFinal(byte[] bArr, int i2) {
        checkStatus();
        if (this.totalLength == 0) {
            initCipher();
        }
        int i3 = this.bufOff;
        if (!this.forEncryption) {
            int i4 = this.macSize;
            if (i3 < i4) {
                throw new InvalidCipherTextException("data too short");
            }
            i3 -= i4;
            if (bArr.length - i2 < i3) {
                throw new OutputLengthException("Output buffer too short");
            }
        } else if (bArr.length - i2 < this.macSize + i3) {
            throw new OutputLengthException("Output buffer too short");
        }
        if (i3 > 0) {
            processPartial(this.bufBlock, 0, i3, bArr, i2);
        }
        long j2 = this.atLength;
        int i5 = this.atBlockPos;
        long j3 = j2 + i5;
        this.atLength = j3;
        if (j3 > this.atLengthPre) {
            if (i5 > 0) {
                gHASHPartial(this.S_at, this.atBlock, 0, i5);
            }
            if (this.atLengthPre > 0) {
                GCMUtil.xor(this.S_at, this.S_atPre);
            }
            long j4 = ((this.totalLength * 8) + 127) >>> 7;
            byte[] bArr2 = new byte[16];
            if (this.exp == null) {
                BasicGCMExponentiator basicGCMExponentiator = new BasicGCMExponentiator();
                this.exp = basicGCMExponentiator;
                basicGCMExponentiator.init(this.f1270H);
            }
            this.exp.exponentiateX(j4, bArr2);
            GCMUtil.multiply(this.S_at, bArr2);
            GCMUtil.xor(this.f1271S, this.S_at);
        }
        byte[] bArr3 = new byte[16];
        Pack.longToBigEndian(this.atLength * 8, bArr3, 0);
        Pack.longToBigEndian(this.totalLength * 8, bArr3, 8);
        gHASHBlock(this.f1271S, bArr3);
        byte[] bArr4 = new byte[16];
        this.cipher.processBlock(this.J0, 0, bArr4, 0);
        GCMUtil.xor(bArr4, this.f1271S);
        int i6 = this.macSize;
        byte[] bArr5 = new byte[i6];
        this.macBlock = bArr5;
        System.arraycopy(bArr4, 0, bArr5, 0, i6);
        if (this.forEncryption) {
            System.arraycopy(this.macBlock, 0, bArr, i2 + this.bufOff, this.macSize);
            i3 += this.macSize;
        } else {
            int i7 = this.macSize;
            byte[] bArr6 = new byte[i7];
            System.arraycopy(this.bufBlock, i3, bArr6, 0, i7);
            if (!Arrays.constantTimeAreEqual(this.macBlock, bArr6)) {
                throw new InvalidCipherTextException("mac check in GCM failed");
            }
        }
        reset(false);
        return i3;
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public String getAlgorithmName() {
        return this.cipher.getAlgorithmName() + "/GCM";
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public byte[] getMac() {
        byte[] bArr = this.macBlock;
        return bArr == null ? new byte[this.macSize] : Arrays.clone(bArr);
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public int getOutputSize(int i2) {
        int i3 = i2 + this.bufOff;
        if (this.forEncryption) {
            return i3 + this.macSize;
        }
        int i4 = this.macSize;
        if (i3 < i4) {
            return 0;
        }
        return i3 - i4;
    }

    @Override // org.bouncycastle.crypto.modes.AEADBlockCipher
    public BlockCipher getUnderlyingCipher() {
        return this.cipher;
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public int getUpdateOutputSize(int i2) {
        int i3 = i2 + this.bufOff;
        if (!this.forEncryption) {
            int i4 = this.macSize;
            if (i3 < i4) {
                return 0;
            }
            i3 -= i4;
        }
        return i3 - (i3 % 16);
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        byte[] iv;
        KeyParameter keyParameter;
        byte[] bArr;
        this.forEncryption = z2;
        this.macBlock = null;
        this.initialised = true;
        if (cipherParameters instanceof AEADParameters) {
            AEADParameters aEADParameters = (AEADParameters) cipherParameters;
            iv = aEADParameters.getNonce();
            this.initialAssociatedText = aEADParameters.getAssociatedText();
            int macSize = aEADParameters.getMacSize();
            if (macSize < 32 || macSize > 128 || macSize % 8 != 0) {
                throw new IllegalArgumentException(AbstractC0000a.m11g("Invalid value for MAC size: ", macSize));
            }
            this.macSize = macSize / 8;
            keyParameter = aEADParameters.getKey();
        } else {
            if (!(cipherParameters instanceof ParametersWithIV)) {
                throw new IllegalArgumentException("invalid parameters passed to GCM");
            }
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            iv = parametersWithIV.getIV();
            this.initialAssociatedText = null;
            this.macSize = 16;
            keyParameter = (KeyParameter) parametersWithIV.getParameters();
        }
        this.bufBlock = new byte[z2 ? 16 : this.macSize + 16];
        if (iv == null || iv.length < 1) {
            throw new IllegalArgumentException("IV must be at least 1 byte");
        }
        if (z2 && (bArr = this.nonce) != null && Arrays.areEqual(bArr, iv)) {
            if (keyParameter == null) {
                throw new IllegalArgumentException("cannot reuse nonce for GCM encryption");
            }
            byte[] bArr2 = this.lastKey;
            if (bArr2 != null && Arrays.areEqual(bArr2, keyParameter.getKey())) {
                throw new IllegalArgumentException("cannot reuse nonce for GCM encryption");
            }
        }
        this.nonce = iv;
        if (keyParameter != null) {
            this.lastKey = keyParameter.getKey();
        }
        if (keyParameter != null) {
            this.cipher.init(true, keyParameter);
            byte[] bArr3 = new byte[16];
            this.f1270H = bArr3;
            this.cipher.processBlock(bArr3, 0, bArr3, 0);
            this.multiplier.init(this.f1270H);
            this.exp = null;
        } else if (this.f1270H == null) {
            throw new IllegalArgumentException("Key must be specified in initial init");
        }
        byte[] bArr4 = new byte[16];
        this.J0 = bArr4;
        byte[] bArr5 = this.nonce;
        if (bArr5.length == 12) {
            System.arraycopy(bArr5, 0, bArr4, 0, bArr5.length);
            this.J0[15] = 1;
        } else {
            gHASH(bArr4, bArr5, bArr5.length);
            byte[] bArr6 = new byte[16];
            Pack.longToBigEndian(this.nonce.length * 8, bArr6, 8);
            gHASHBlock(this.J0, bArr6);
        }
        this.f1271S = new byte[16];
        this.S_at = new byte[16];
        this.S_atPre = new byte[16];
        this.atBlock = new byte[16];
        this.atBlockPos = 0;
        this.atLength = 0L;
        this.atLengthPre = 0L;
        this.counter = Arrays.clone(this.J0);
        this.blocksRemaining = -2;
        this.bufOff = 0;
        this.totalLength = 0L;
        byte[] bArr7 = this.initialAssociatedText;
        if (bArr7 != null) {
            processAADBytes(bArr7, 0, bArr7.length);
        }
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public void processAADByte(byte b) {
        checkStatus();
        byte[] bArr = this.atBlock;
        int i2 = this.atBlockPos;
        bArr[i2] = b;
        int i3 = i2 + 1;
        this.atBlockPos = i3;
        if (i3 == 16) {
            gHASHBlock(this.S_at, bArr);
            this.atBlockPos = 0;
            this.atLength += 16;
        }
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public void processAADBytes(byte[] bArr, int i2, int i3) {
        checkStatus();
        for (int i4 = 0; i4 < i3; i4++) {
            byte[] bArr2 = this.atBlock;
            int i5 = this.atBlockPos;
            bArr2[i5] = bArr[i2 + i4];
            int i6 = i5 + 1;
            this.atBlockPos = i6;
            if (i6 == 16) {
                gHASHBlock(this.S_at, bArr2);
                this.atBlockPos = 0;
                this.atLength += 16;
            }
        }
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public int processByte(byte b, byte[] bArr, int i2) {
        checkStatus();
        byte[] bArr2 = this.bufBlock;
        int i3 = this.bufOff;
        bArr2[i3] = b;
        int i4 = i3 + 1;
        this.bufOff = i4;
        if (i4 != bArr2.length) {
            return 0;
        }
        processBlock(bArr2, 0, bArr, i2);
        if (this.forEncryption) {
            this.bufOff = 0;
        } else {
            byte[] bArr3 = this.bufBlock;
            System.arraycopy(bArr3, 16, bArr3, 0, this.macSize);
            this.bufOff = this.macSize;
        }
        return 16;
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public int processBytes(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        int i5;
        checkStatus();
        if (bArr.length - i2 < i3) {
            throw new DataLengthException("Input buffer too short");
        }
        if (this.forEncryption) {
            if (this.bufOff != 0) {
                while (i3 > 0) {
                    i3--;
                    byte[] bArr3 = this.bufBlock;
                    int i6 = this.bufOff;
                    int i7 = i2 + 1;
                    bArr3[i6] = bArr[i2];
                    int i8 = i6 + 1;
                    this.bufOff = i8;
                    if (i8 == 16) {
                        processBlock(bArr3, 0, bArr2, i4);
                        this.bufOff = 0;
                        i5 = 16;
                        i2 = i7;
                        break;
                    }
                    i2 = i7;
                }
            }
            i5 = 0;
            while (i3 >= 16) {
                processBlock(bArr, i2, bArr2, i4 + i5);
                i2 += 16;
                i3 -= 16;
                i5 += 16;
            }
            if (i3 > 0) {
                System.arraycopy(bArr, i2, this.bufBlock, 0, i3);
                this.bufOff = i3;
            }
        } else {
            i5 = 0;
            for (int i9 = 0; i9 < i3; i9++) {
                byte[] bArr4 = this.bufBlock;
                int i10 = this.bufOff;
                bArr4[i10] = bArr[i2 + i9];
                int i11 = i10 + 1;
                this.bufOff = i11;
                if (i11 == bArr4.length) {
                    processBlock(bArr4, 0, bArr2, i4 + i5);
                    byte[] bArr5 = this.bufBlock;
                    System.arraycopy(bArr5, 16, bArr5, 0, this.macSize);
                    this.bufOff = this.macSize;
                    i5 += 16;
                }
            }
        }
        return i5;
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public void reset() {
        reset(true);
    }

    public GCMBlockCipher(BlockCipher blockCipher, GCMMultiplier gCMMultiplier) {
        if (blockCipher.getBlockSize() != 16) {
            throw new IllegalArgumentException("cipher required with a block size of 16.");
        }
        gCMMultiplier = gCMMultiplier == null ? new Tables4kGCMMultiplier() : gCMMultiplier;
        this.cipher = blockCipher;
        this.multiplier = gCMMultiplier;
    }

    private void gHASHBlock(byte[] bArr, byte[] bArr2, int i2) {
        GCMUtil.xor(bArr, bArr2, i2);
        this.multiplier.multiplyH(bArr);
    }

    private void reset(boolean z2) {
        this.cipher.reset();
        this.f1271S = new byte[16];
        this.S_at = new byte[16];
        this.S_atPre = new byte[16];
        this.atBlock = new byte[16];
        this.atBlockPos = 0;
        this.atLength = 0L;
        this.atLengthPre = 0L;
        this.counter = Arrays.clone(this.J0);
        this.blocksRemaining = -2;
        this.bufOff = 0;
        this.totalLength = 0L;
        byte[] bArr = this.bufBlock;
        if (bArr != null) {
            Arrays.fill(bArr, (byte) 0);
        }
        if (z2) {
            this.macBlock = null;
        }
        if (this.forEncryption) {
            this.initialised = false;
            return;
        }
        byte[] bArr2 = this.initialAssociatedText;
        if (bArr2 != null) {
            processAADBytes(bArr2, 0, bArr2.length);
        }
    }
}
