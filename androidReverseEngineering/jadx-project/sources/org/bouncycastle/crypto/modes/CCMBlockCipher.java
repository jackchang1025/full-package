package org.bouncycastle.crypto.modes;

import android.sun.security.util.DerValue;
import java.io.ByteArrayOutputStream;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.macs.CBCBlockCipherMac;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.Arrays;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class CCMBlockCipher implements AEADBlockCipher {
    private int blockSize;
    private BlockCipher cipher;
    private boolean forEncryption;
    private byte[] initialAssociatedText;
    private CipherParameters keyParam;
    private byte[] macBlock;
    private int macSize;
    private byte[] nonce;
    private ExposedByteArrayOutputStream associatedText = new ExposedByteArrayOutputStream();
    private ExposedByteArrayOutputStream data = new ExposedByteArrayOutputStream();

    public class ExposedByteArrayOutputStream extends ByteArrayOutputStream {
        public ExposedByteArrayOutputStream() {
        }

        public byte[] getBuffer() {
            return ((ByteArrayOutputStream) this).buf;
        }
    }

    public CCMBlockCipher(BlockCipher blockCipher) {
        this.cipher = blockCipher;
        int blockSize = blockCipher.getBlockSize();
        this.blockSize = blockSize;
        this.macBlock = new byte[blockSize];
        if (blockSize != 16) {
            throw new IllegalArgumentException("cipher required with a block size of 16.");
        }
    }

    private int calculateMac(byte[] bArr, int i2, int i3, byte[] bArr2) {
        CBCBlockCipherMac cBCBlockCipherMac = new CBCBlockCipherMac(this.cipher, this.macSize * 8);
        cBCBlockCipherMac.init(this.keyParam);
        byte[] bArr3 = new byte[16];
        if (hasAssociatedText()) {
            bArr3[0] = (byte) (bArr3[0] | DerValue.TAG_APPLICATION);
        }
        int i4 = 2;
        byte macSize = (byte) (bArr3[0] | ((((cBCBlockCipherMac.getMacSize() - 2) / 2) & 7) << 3));
        bArr3[0] = macSize;
        byte[] bArr4 = this.nonce;
        bArr3[0] = (byte) (macSize | (((15 - bArr4.length) - 1) & 7));
        System.arraycopy(bArr4, 0, bArr3, 1, bArr4.length);
        int i5 = i3;
        int i6 = 1;
        while (i5 > 0) {
            bArr3[16 - i6] = (byte) (i5 & 255);
            i5 >>>= 8;
            i6++;
        }
        cBCBlockCipherMac.update(bArr3, 0, 16);
        if (hasAssociatedText()) {
            int associatedTextLength = getAssociatedTextLength();
            if (associatedTextLength < 65280) {
                cBCBlockCipherMac.update((byte) (associatedTextLength >> 8));
                cBCBlockCipherMac.update((byte) associatedTextLength);
            } else {
                cBCBlockCipherMac.update((byte) -1);
                cBCBlockCipherMac.update((byte) -2);
                cBCBlockCipherMac.update((byte) (associatedTextLength >> 24));
                cBCBlockCipherMac.update((byte) (associatedTextLength >> 16));
                cBCBlockCipherMac.update((byte) (associatedTextLength >> 8));
                cBCBlockCipherMac.update((byte) associatedTextLength);
                i4 = 6;
            }
            byte[] bArr5 = this.initialAssociatedText;
            if (bArr5 != null) {
                cBCBlockCipherMac.update(bArr5, 0, bArr5.length);
            }
            if (this.associatedText.size() > 0) {
                cBCBlockCipherMac.update(this.associatedText.getBuffer(), 0, this.associatedText.size());
            }
            int i7 = (i4 + associatedTextLength) % 16;
            if (i7 != 0) {
                while (i7 != 16) {
                    cBCBlockCipherMac.update((byte) 0);
                    i7++;
                }
            }
        }
        cBCBlockCipherMac.update(bArr, i2, i3);
        return cBCBlockCipherMac.doFinal(bArr2, 0);
    }

    private int getAssociatedTextLength() {
        int size = this.associatedText.size();
        byte[] bArr = this.initialAssociatedText;
        return size + (bArr == null ? 0 : bArr.length);
    }

    private int getMacSize(boolean z2, int i2) {
        if (!z2 || (i2 >= 32 && i2 <= 128 && (i2 & 15) == 0)) {
            return i2 >>> 3;
        }
        throw new IllegalArgumentException("tag length in octets must be one of {4,6,8,10,12,14,16}");
    }

    private boolean hasAssociatedText() {
        return getAssociatedTextLength() > 0;
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public int doFinal(byte[] bArr, int i2) {
        int processPacket = processPacket(this.data.getBuffer(), 0, this.data.size(), bArr, i2);
        reset();
        return processPacket;
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public String getAlgorithmName() {
        return this.cipher.getAlgorithmName() + "/CCM";
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
        return this.cipher;
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public int getUpdateOutputSize(int i2) {
        return 0;
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        CipherParameters parameters;
        this.forEncryption = z2;
        if (cipherParameters instanceof AEADParameters) {
            AEADParameters aEADParameters = (AEADParameters) cipherParameters;
            this.nonce = aEADParameters.getNonce();
            this.initialAssociatedText = aEADParameters.getAssociatedText();
            this.macSize = getMacSize(z2, aEADParameters.getMacSize());
            parameters = aEADParameters.getKey();
        } else {
            if (!(cipherParameters instanceof ParametersWithIV)) {
                throw new IllegalArgumentException(AbstractC0413b.m1014h(cipherParameters, "invalid parameters passed to CCM: "));
            }
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            this.nonce = parametersWithIV.getIV();
            this.initialAssociatedText = null;
            this.macSize = getMacSize(z2, 64);
            parameters = parametersWithIV.getParameters();
        }
        if (parameters != null) {
            this.keyParam = parameters;
        }
        byte[] bArr = this.nonce;
        if (bArr == null || bArr.length < 7 || bArr.length > 13) {
            throw new IllegalArgumentException("nonce must have length from 7 to 13 octets");
        }
        reset();
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
            throw new DataLengthException("Input buffer too short");
        }
        this.data.write(bArr, i2, i3);
        return 0;
    }

    public int processPacket(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        int i5;
        if (this.keyParam == null) {
            throw new IllegalStateException("CCM cipher unitialized.");
        }
        byte[] bArr3 = this.nonce;
        int length = 15 - bArr3.length;
        if (length < 4 && i3 >= (1 << (length * 8))) {
            throw new IllegalStateException("CCM packet too large for choice of q.");
        }
        byte[] bArr4 = new byte[this.blockSize];
        bArr4[0] = (byte) ((length - 1) & 7);
        System.arraycopy(bArr3, 0, bArr4, 1, bArr3.length);
        SICBlockCipher sICBlockCipher = new SICBlockCipher(this.cipher);
        sICBlockCipher.init(this.forEncryption, new ParametersWithIV(this.keyParam, bArr4));
        if (!this.forEncryption) {
            int i6 = this.macSize;
            if (i3 < i6) {
                throw new InvalidCipherTextException("data too short");
            }
            int i7 = i3 - i6;
            if (bArr2.length < i7 + i4) {
                throw new OutputLengthException("Output buffer too short.");
            }
            int i8 = i2 + i7;
            System.arraycopy(bArr, i8, this.macBlock, 0, i6);
            byte[] bArr5 = this.macBlock;
            sICBlockCipher.processBlock(bArr5, 0, bArr5, 0);
            int i9 = this.macSize;
            while (true) {
                byte[] bArr6 = this.macBlock;
                if (i9 == bArr6.length) {
                    break;
                }
                bArr6[i9] = 0;
                i9++;
            }
            int i10 = i2;
            int i11 = i4;
            while (true) {
                i5 = this.blockSize;
                if (i10 >= i8 - i5) {
                    break;
                }
                sICBlockCipher.processBlock(bArr, i10, bArr2, i11);
                int i12 = this.blockSize;
                i11 += i12;
                i10 += i12;
            }
            byte[] bArr7 = new byte[i5];
            int i13 = i7 - (i10 - i2);
            System.arraycopy(bArr, i10, bArr7, 0, i13);
            sICBlockCipher.processBlock(bArr7, 0, bArr7, 0);
            System.arraycopy(bArr7, 0, bArr2, i11, i13);
            byte[] bArr8 = new byte[this.blockSize];
            calculateMac(bArr2, i4, i7, bArr8);
            if (Arrays.constantTimeAreEqual(this.macBlock, bArr8)) {
                return i7;
            }
            throw new InvalidCipherTextException("mac check in CCM failed");
        }
        int i14 = this.macSize + i3;
        if (bArr2.length < i14 + i4) {
            throw new OutputLengthException("Output buffer too short.");
        }
        calculateMac(bArr, i2, i3, this.macBlock);
        byte[] bArr9 = new byte[this.blockSize];
        sICBlockCipher.processBlock(this.macBlock, 0, bArr9, 0);
        int i15 = i2;
        int i16 = i4;
        while (true) {
            int i17 = i2 + i3;
            int i18 = this.blockSize;
            if (i15 >= i17 - i18) {
                byte[] bArr10 = new byte[i18];
                int i19 = i17 - i15;
                System.arraycopy(bArr, i15, bArr10, 0, i19);
                sICBlockCipher.processBlock(bArr10, 0, bArr10, 0);
                System.arraycopy(bArr10, 0, bArr2, i16, i19);
                System.arraycopy(bArr9, 0, bArr2, i4 + i3, this.macSize);
                return i14;
            }
            sICBlockCipher.processBlock(bArr, i15, bArr2, i16);
            int i20 = this.blockSize;
            i16 += i20;
            i15 += i20;
        }
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public void reset() {
        this.cipher.reset();
        this.associatedText.reset();
        this.data.reset();
    }

    public byte[] processPacket(byte[] bArr, int i2, int i3) {
        int i4;
        if (this.forEncryption) {
            i4 = this.macSize + i3;
        } else {
            int i5 = this.macSize;
            if (i3 < i5) {
                throw new InvalidCipherTextException("data too short");
            }
            i4 = i3 - i5;
        }
        byte[] bArr2 = new byte[i4];
        processPacket(bArr, i2, i3, bArr2, 0);
        return bArr2;
    }
}
