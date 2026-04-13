package org.bouncycastle.crypto.modes;

import java.io.ByteArrayOutputStream;
import org.bouncycastle.asn1.cmc.BodyPartID;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.modes.kgcm.KGCMMultiplier;
import org.bouncycastle.crypto.modes.kgcm.Tables16kKGCMMultiplier_512;
import org.bouncycastle.crypto.modes.kgcm.Tables4kKGCMMultiplier_128;
import org.bouncycastle.crypto.modes.kgcm.Tables8kKGCMMultiplier_256;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Pack;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class KGCMBlockCipher implements AEADBlockCipher {
    private static final int MIN_MAC_BITS = 64;

    /* renamed from: b */
    private long[] f1273b;
    private final int blockSize;
    private BufferedBlockCipher ctrEngine;
    private BlockCipher engine;
    private boolean forEncryption;
    private byte[] initialAssociatedText;
    private byte[] iv;
    private byte[] macBlock;
    private KGCMMultiplier multiplier;
    private ExposedByteArrayOutputStream associatedText = new ExposedByteArrayOutputStream();
    private ExposedByteArrayOutputStream data = new ExposedByteArrayOutputStream();
    private int macSize = -1;

    public class ExposedByteArrayOutputStream extends ByteArrayOutputStream {
        public ExposedByteArrayOutputStream() {
        }

        public byte[] getBuffer() {
            return ((ByteArrayOutputStream) this).buf;
        }
    }

    public KGCMBlockCipher(BlockCipher blockCipher) {
        this.engine = blockCipher;
        this.ctrEngine = new BufferedBlockCipher(new KCTRBlockCipher(blockCipher));
        int blockSize = this.engine.getBlockSize();
        this.blockSize = blockSize;
        this.initialAssociatedText = new byte[blockSize];
        this.iv = new byte[blockSize];
        this.multiplier = createDefaultMultiplier(blockSize);
        this.f1273b = new long[blockSize >>> 3];
        this.macBlock = null;
    }

    private void calculateMac(byte[] bArr, int i2, int i3, int i4) {
        int i5 = i2 + i3;
        while (i2 < i5) {
            xorWithInput(this.f1273b, bArr, i2);
            this.multiplier.multiplyH(this.f1273b);
            i2 += this.blockSize;
        }
        long j2 = (i4 & BodyPartID.bodyIdMax) << 3;
        long j3 = (BodyPartID.bodyIdMax & i3) << 3;
        long[] jArr = this.f1273b;
        jArr[0] = j2 ^ jArr[0];
        int i6 = this.blockSize >>> 4;
        jArr[i6] = jArr[i6] ^ j3;
        byte[] longToLittleEndian = Pack.longToLittleEndian(jArr);
        this.macBlock = longToLittleEndian;
        this.engine.processBlock(longToLittleEndian, 0, longToLittleEndian, 0);
    }

    private static KGCMMultiplier createDefaultMultiplier(int i2) {
        if (i2 == 16) {
            return new Tables4kKGCMMultiplier_128();
        }
        if (i2 == 32) {
            return new Tables8kKGCMMultiplier_256();
        }
        if (i2 == 64) {
            return new Tables16kKGCMMultiplier_512();
        }
        throw new IllegalArgumentException("Only 128, 256, and 512 -bit block sizes supported");
    }

    private void processAAD(byte[] bArr, int i2, int i3) {
        int i4 = i3 + i2;
        while (i2 < i4) {
            xorWithInput(this.f1273b, bArr, i2);
            this.multiplier.multiplyH(this.f1273b);
            i2 += this.blockSize;
        }
    }

    private static void xorWithInput(long[] jArr, byte[] bArr, int i2) {
        for (int i3 = 0; i3 < jArr.length; i3++) {
            jArr[i3] = jArr[i3] ^ Pack.littleEndianToLong(bArr, i2);
            i2 += 8;
        }
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public int doFinal(byte[] bArr, int i2) {
        int doFinal;
        int size = this.data.size();
        if (!this.forEncryption && size < this.macSize) {
            throw new InvalidCipherTextException("data too short");
        }
        byte[] bArr2 = new byte[this.blockSize];
        this.engine.processBlock(bArr2, 0, bArr2, 0);
        long[] jArr = new long[this.blockSize >>> 3];
        Pack.littleEndianToLong(bArr2, 0, jArr);
        this.multiplier.init(jArr);
        Arrays.fill(bArr2, (byte) 0);
        Arrays.fill(jArr, 0L);
        int size2 = this.associatedText.size();
        if (size2 > 0) {
            processAAD(this.associatedText.getBuffer(), 0, size2);
        }
        if (!this.forEncryption) {
            int i3 = size - this.macSize;
            if (bArr.length - i2 < i3) {
                throw new OutputLengthException("Output buffer too short");
            }
            calculateMac(this.data.getBuffer(), 0, i3, size2);
            int processBytes = this.ctrEngine.processBytes(this.data.getBuffer(), 0, i3, bArr, i2);
            doFinal = this.ctrEngine.doFinal(bArr, i2 + processBytes) + processBytes;
        } else {
            if ((bArr.length - i2) - this.macSize < size) {
                throw new OutputLengthException("Output buffer too short");
            }
            int processBytes2 = this.ctrEngine.processBytes(this.data.getBuffer(), 0, size, bArr, i2);
            doFinal = this.ctrEngine.doFinal(bArr, i2 + processBytes2) + processBytes2;
            calculateMac(bArr, i2, size, size2);
        }
        byte[] bArr3 = this.macBlock;
        if (bArr3 == null) {
            throw new IllegalStateException("mac is not calculated");
        }
        if (this.forEncryption) {
            System.arraycopy(bArr3, 0, bArr, i2 + doFinal, this.macSize);
            reset();
            return doFinal + this.macSize;
        }
        byte[] bArr4 = new byte[this.macSize];
        byte[] buffer = this.data.getBuffer();
        int i4 = this.macSize;
        System.arraycopy(buffer, size - i4, bArr4, 0, i4);
        int i5 = this.macSize;
        byte[] bArr5 = new byte[i5];
        System.arraycopy(this.macBlock, 0, bArr5, 0, i5);
        if (!Arrays.constantTimeAreEqual(bArr4, bArr5)) {
            throw new InvalidCipherTextException("mac verification failed");
        }
        reset();
        return doFinal;
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public String getAlgorithmName() {
        return this.engine.getAlgorithmName() + "/KGCM";
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
        int size = this.data.size() + i2;
        if (this.forEncryption) {
            return size + this.macSize;
        }
        int i3 = this.macSize;
        if (size < i3) {
            return 0;
        }
        return size - i3;
    }

    @Override // org.bouncycastle.crypto.modes.AEADBlockCipher
    public BlockCipher getUnderlyingCipher() {
        return this.engine;
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public int getUpdateOutputSize(int i2) {
        return 0;
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        KeyParameter keyParameter;
        this.forEncryption = z2;
        if (cipherParameters instanceof AEADParameters) {
            AEADParameters aEADParameters = (AEADParameters) cipherParameters;
            byte[] nonce = aEADParameters.getNonce();
            byte[] bArr = this.iv;
            int length = bArr.length - nonce.length;
            Arrays.fill(bArr, (byte) 0);
            System.arraycopy(nonce, 0, this.iv, length, nonce.length);
            this.initialAssociatedText = aEADParameters.getAssociatedText();
            int macSize = aEADParameters.getMacSize();
            if (macSize < 64 || macSize > (this.blockSize << 3) || (macSize & 7) != 0) {
                throw new IllegalArgumentException(AbstractC0000a.m11g("Invalid value for MAC size: ", macSize));
            }
            this.macSize = macSize >>> 3;
            keyParameter = aEADParameters.getKey();
            byte[] bArr2 = this.initialAssociatedText;
            if (bArr2 != null) {
                processAADBytes(bArr2, 0, bArr2.length);
            }
        } else {
            if (!(cipherParameters instanceof ParametersWithIV)) {
                throw new IllegalArgumentException("Invalid parameter passed");
            }
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            byte[] iv = parametersWithIV.getIV();
            byte[] bArr3 = this.iv;
            int length2 = bArr3.length - iv.length;
            Arrays.fill(bArr3, (byte) 0);
            System.arraycopy(iv, 0, this.iv, length2, iv.length);
            this.initialAssociatedText = null;
            this.macSize = this.blockSize;
            keyParameter = (KeyParameter) parametersWithIV.getParameters();
        }
        this.macBlock = new byte[this.blockSize];
        this.ctrEngine.init(true, new ParametersWithIV(keyParameter, this.iv));
        this.engine.init(true, keyParameter);
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public void processAADByte(byte b) {
        this.associatedText.write(b);
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public void processAADBytes(byte[] bArr, int i2, int i3) {
        this.associatedText.write(bArr, i2, i3);
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public int processByte(byte b, byte[] bArr, int i2) {
        this.data.write(b);
        return 0;
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public int processBytes(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        if (bArr.length < i2 + i3) {
            throw new DataLengthException("input buffer too short");
        }
        this.data.write(bArr, i2, i3);
        return 0;
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public void reset() {
        Arrays.fill(this.f1273b, 0L);
        this.engine.reset();
        this.data.reset();
        this.associatedText.reset();
        byte[] bArr = this.initialAssociatedText;
        if (bArr != null) {
            processAADBytes(bArr, 0, bArr.length);
        }
    }
}
