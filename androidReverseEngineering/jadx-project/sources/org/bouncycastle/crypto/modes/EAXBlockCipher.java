package org.bouncycastle.crypto.modes;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.macs.CMac;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public class EAXBlockCipher implements AEADBlockCipher {
    private static final byte cTAG = 2;
    private static final byte hTAG = 1;
    private static final byte nTAG = 0;
    private byte[] associatedTextMac;
    private int blockSize;
    private byte[] bufBlock;
    private int bufOff;
    private SICBlockCipher cipher;
    private boolean cipherInitialized;
    private boolean forEncryption;
    private byte[] initialAssociatedText;
    private Mac mac;
    private byte[] macBlock;
    private int macSize;
    private byte[] nonceMac;

    public EAXBlockCipher(BlockCipher blockCipher) {
        this.blockSize = blockCipher.getBlockSize();
        CMac cMac = new CMac(blockCipher);
        this.mac = cMac;
        this.macBlock = new byte[this.blockSize];
        this.associatedTextMac = new byte[cMac.getMacSize()];
        this.nonceMac = new byte[this.mac.getMacSize()];
        this.cipher = new SICBlockCipher(blockCipher);
    }

    private void calculateMac() {
        byte[] bArr = new byte[this.blockSize];
        int i2 = 0;
        this.mac.doFinal(bArr, 0);
        while (true) {
            byte[] bArr2 = this.macBlock;
            if (i2 >= bArr2.length) {
                return;
            }
            bArr2[i2] = (byte) ((this.nonceMac[i2] ^ this.associatedTextMac[i2]) ^ bArr[i2]);
            i2++;
        }
    }

    private void initCipher() {
        if (this.cipherInitialized) {
            return;
        }
        this.cipherInitialized = true;
        this.mac.doFinal(this.associatedTextMac, 0);
        int i2 = this.blockSize;
        byte[] bArr = new byte[i2];
        bArr[i2 - 1] = 2;
        this.mac.update(bArr, 0, i2);
    }

    private int process(byte b, byte[] bArr, int i2) {
        int processBlock;
        byte[] bArr2 = this.bufBlock;
        int i3 = this.bufOff;
        int i4 = i3 + 1;
        this.bufOff = i4;
        bArr2[i3] = b;
        if (i4 != bArr2.length) {
            return 0;
        }
        int length = bArr.length;
        int i5 = this.blockSize;
        if (length < i2 + i5) {
            throw new OutputLengthException("Output buffer is too short");
        }
        if (this.forEncryption) {
            processBlock = this.cipher.processBlock(bArr2, 0, bArr, i2);
            this.mac.update(bArr, i2, this.blockSize);
        } else {
            this.mac.update(bArr2, 0, i5);
            processBlock = this.cipher.processBlock(this.bufBlock, 0, bArr, i2);
        }
        this.bufOff = 0;
        if (!this.forEncryption) {
            byte[] bArr3 = this.bufBlock;
            System.arraycopy(bArr3, this.blockSize, bArr3, 0, this.macSize);
            this.bufOff = this.macSize;
        }
        return processBlock;
    }

    private boolean verifyMac(byte[] bArr, int i2) {
        int i3 = 0;
        for (int i4 = 0; i4 < this.macSize; i4++) {
            i3 |= this.macBlock[i4] ^ bArr[i2 + i4];
        }
        return i3 == 0;
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public int doFinal(byte[] bArr, int i2) {
        initCipher();
        int i3 = this.bufOff;
        byte[] bArr2 = this.bufBlock;
        byte[] bArr3 = new byte[bArr2.length];
        this.bufOff = 0;
        if (this.forEncryption) {
            int i4 = i2 + i3;
            if (bArr.length < this.macSize + i4) {
                throw new OutputLengthException("Output buffer too short");
            }
            this.cipher.processBlock(bArr2, 0, bArr3, 0);
            System.arraycopy(bArr3, 0, bArr, i2, i3);
            this.mac.update(bArr3, 0, i3);
            calculateMac();
            System.arraycopy(this.macBlock, 0, bArr, i4, this.macSize);
            reset(false);
            return i3 + this.macSize;
        }
        int i5 = this.macSize;
        if (i3 < i5) {
            throw new InvalidCipherTextException("data too short");
        }
        if (bArr.length < (i2 + i3) - i5) {
            throw new OutputLengthException("Output buffer too short");
        }
        if (i3 > i5) {
            this.mac.update(bArr2, 0, i3 - i5);
            this.cipher.processBlock(this.bufBlock, 0, bArr3, 0);
            System.arraycopy(bArr3, 0, bArr, i2, i3 - this.macSize);
        }
        calculateMac();
        if (!verifyMac(this.bufBlock, i3 - this.macSize)) {
            throw new InvalidCipherTextException("mac check in EAX failed");
        }
        reset(false);
        return i3 - this.macSize;
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public String getAlgorithmName() {
        return this.cipher.getUnderlyingCipher().getAlgorithmName() + "/EAX";
    }

    public int getBlockSize() {
        return this.cipher.getBlockSize();
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public byte[] getMac() {
        int i2 = this.macSize;
        byte[] bArr = new byte[i2];
        System.arraycopy(this.macBlock, 0, bArr, 0, i2);
        return bArr;
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
        return this.cipher.getUnderlyingCipher();
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
        return i3 - (i3 % this.blockSize);
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        byte[] iv;
        CipherParameters parameters;
        this.forEncryption = z2;
        if (cipherParameters instanceof AEADParameters) {
            AEADParameters aEADParameters = (AEADParameters) cipherParameters;
            iv = aEADParameters.getNonce();
            this.initialAssociatedText = aEADParameters.getAssociatedText();
            this.macSize = aEADParameters.getMacSize() / 8;
            parameters = aEADParameters.getKey();
        } else {
            if (!(cipherParameters instanceof ParametersWithIV)) {
                throw new IllegalArgumentException("invalid parameters passed to EAX");
            }
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            iv = parametersWithIV.getIV();
            this.initialAssociatedText = null;
            this.macSize = this.mac.getMacSize() / 2;
            parameters = parametersWithIV.getParameters();
        }
        this.bufBlock = new byte[z2 ? this.blockSize : this.blockSize + this.macSize];
        byte[] bArr = new byte[this.blockSize];
        this.mac.init(parameters);
        int i2 = this.blockSize;
        bArr[i2 - 1] = 0;
        this.mac.update(bArr, 0, i2);
        this.mac.update(iv, 0, iv.length);
        this.mac.doFinal(this.nonceMac, 0);
        this.cipher.init(true, new ParametersWithIV(null, this.nonceMac));
        reset();
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public void processAADByte(byte b) {
        if (this.cipherInitialized) {
            throw new IllegalStateException("AAD data cannot be added after encryption/decryption processing has begun.");
        }
        this.mac.update(b);
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public void processAADBytes(byte[] bArr, int i2, int i3) {
        if (this.cipherInitialized) {
            throw new IllegalStateException("AAD data cannot be added after encryption/decryption processing has begun.");
        }
        this.mac.update(bArr, i2, i3);
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public int processByte(byte b, byte[] bArr, int i2) {
        initCipher();
        return process(b, bArr, i2);
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public int processBytes(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        initCipher();
        if (bArr.length < i2 + i3) {
            throw new DataLengthException("Input buffer too short");
        }
        int i5 = 0;
        for (int i6 = 0; i6 != i3; i6++) {
            i5 += process(bArr[i2 + i6], bArr2, i4 + i5);
        }
        return i5;
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public void reset() {
        reset(true);
    }

    private void reset(boolean z2) {
        this.cipher.reset();
        this.mac.reset();
        this.bufOff = 0;
        Arrays.fill(this.bufBlock, (byte) 0);
        if (z2) {
            Arrays.fill(this.macBlock, (byte) 0);
        }
        int i2 = this.blockSize;
        byte[] bArr = new byte[i2];
        bArr[i2 - 1] = 1;
        this.mac.update(bArr, 0, i2);
        this.cipherInitialized = false;
        byte[] bArr2 = this.initialAssociatedText;
        if (bArr2 != null) {
            processAADBytes(bArr2, 0, bArr2.length);
        }
    }
}
