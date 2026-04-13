package org.bouncycastle.crypto.modes;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.StreamBlockCipher;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public class KCTRBlockCipher extends StreamBlockCipher {
    private int byteCount;
    private BlockCipher engine;
    private boolean initialised;
    private byte[] iv;
    private byte[] ofbOutV;
    private byte[] ofbV;

    public KCTRBlockCipher(BlockCipher blockCipher) {
        super(blockCipher);
        this.engine = blockCipher;
        this.iv = new byte[blockCipher.getBlockSize()];
        this.ofbV = new byte[blockCipher.getBlockSize()];
        this.ofbOutV = new byte[blockCipher.getBlockSize()];
    }

    private void checkCounter() {
    }

    private void incrementCounterAt(int i2) {
        while (true) {
            byte[] bArr = this.ofbV;
            if (i2 >= bArr.length) {
                return;
            }
            int i3 = i2 + 1;
            byte b = (byte) (bArr[i2] + 1);
            bArr[i2] = b;
            if (b != 0) {
                return;
            } else {
                i2 = i3;
            }
        }
    }

    @Override // org.bouncycastle.crypto.StreamBlockCipher
    public byte calculateByte(byte b) {
        int i2 = this.byteCount;
        if (i2 == 0) {
            incrementCounterAt(0);
            checkCounter();
            this.engine.processBlock(this.ofbV, 0, this.ofbOutV, 0);
            byte[] bArr = this.ofbOutV;
            int i3 = this.byteCount;
            this.byteCount = i3 + 1;
            return (byte) (b ^ bArr[i3]);
        }
        byte[] bArr2 = this.ofbOutV;
        int i4 = i2 + 1;
        this.byteCount = i4;
        byte b2 = (byte) (b ^ bArr2[i2]);
        if (i4 == this.ofbV.length) {
            this.byteCount = 0;
        }
        return b2;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public String getAlgorithmName() {
        return this.engine.getAlgorithmName() + "/KCTR";
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return this.engine.getBlockSize();
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        this.initialised = true;
        if (!(cipherParameters instanceof ParametersWithIV)) {
            throw new IllegalArgumentException("invalid parameter passed");
        }
        ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
        byte[] iv = parametersWithIV.getIV();
        byte[] bArr = this.iv;
        int length = bArr.length - iv.length;
        Arrays.fill(bArr, (byte) 0);
        System.arraycopy(iv, 0, this.iv, length, iv.length);
        CipherParameters parameters = parametersWithIV.getParameters();
        if (parameters != null) {
            this.engine.init(true, parameters);
        }
        reset();
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        if (bArr.length - i2 < getBlockSize()) {
            throw new DataLengthException("input buffer too short");
        }
        if (bArr2.length - i3 < getBlockSize()) {
            throw new OutputLengthException("output buffer too short");
        }
        processBytes(bArr, i2, getBlockSize(), bArr2, i3);
        return getBlockSize();
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void reset() {
        if (this.initialised) {
            this.engine.processBlock(this.iv, 0, this.ofbV, 0);
        }
        this.engine.reset();
        this.byteCount = 0;
    }
}
