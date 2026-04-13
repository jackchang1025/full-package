package org.bouncycastle.crypto.modes;

import java.io.ByteArrayOutputStream;
import org.bouncycastle.asn1.eac.CertificateBody;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.gcm.GCMMultiplier;
import org.bouncycastle.crypto.modes.gcm.Tables4kGCMMultiplier;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Pack;

/* loaded from: classes.dex */
public class GCMSIVBlockCipher implements AEADBlockCipher {
    private static final byte ADD = -31;
    private static final int AEAD_COMPLETE = 2;
    private static final int BUFLEN = 16;
    private static final int HALFBUFLEN = 8;
    private static final int INIT = 1;
    private static final byte MASK = Byte.MIN_VALUE;
    private static final int MAX_DATALEN = 2147483623;
    private static final int NONCELEN = 12;
    private boolean forEncryption;
    private byte[] macBlock;
    private final GCMSIVHasher theAEADHasher;
    private final BlockCipher theCipher;
    private final GCMSIVHasher theDataHasher;
    private GCMSIVCache theEncData;
    private int theFlags;
    private final byte[] theGHash;
    private byte[] theInitialAEAD;
    private final GCMMultiplier theMultiplier;
    private byte[] theNonce;
    private GCMSIVCache thePlain;
    private final byte[] theReverse;

    public static class GCMSIVCache extends ByteArrayOutputStream {
        public void clearBuffer() {
            Arrays.fill(getBuffer(), (byte) 0);
        }

        public byte[] getBuffer() {
            return ((ByteArrayOutputStream) this).buf;
        }
    }

    public class GCMSIVHasher {
        private int numActive;
        private long numHashed;
        private final byte[] theBuffer;
        private final byte[] theByte;

        private GCMSIVHasher() {
            this.theBuffer = new byte[16];
            this.theByte = new byte[1];
        }

        public void completeHash() {
            if (this.numActive > 0) {
                Arrays.fill(GCMSIVBlockCipher.this.theReverse, (byte) 0);
                GCMSIVBlockCipher.fillReverse(this.theBuffer, 0, this.numActive, GCMSIVBlockCipher.this.theReverse);
                GCMSIVBlockCipher gCMSIVBlockCipher = GCMSIVBlockCipher.this;
                gCMSIVBlockCipher.gHASH(gCMSIVBlockCipher.theReverse);
            }
        }

        public long getBytesProcessed() {
            return this.numHashed;
        }

        public void reset() {
            this.numActive = 0;
            this.numHashed = 0L;
        }

        public void updateHash(byte b) {
            byte[] bArr = this.theByte;
            bArr[0] = b;
            updateHash(bArr, 0, 1);
        }

        public void updateHash(byte[] bArr, int i2, int i3) {
            int i4;
            int i5 = this.numActive;
            int i6 = 16 - i5;
            int i7 = 0;
            if (i5 <= 0 || i3 < i6) {
                i4 = i3;
            } else {
                System.arraycopy(bArr, i2, this.theBuffer, i5, i6);
                GCMSIVBlockCipher.fillReverse(this.theBuffer, 0, 16, GCMSIVBlockCipher.this.theReverse);
                GCMSIVBlockCipher gCMSIVBlockCipher = GCMSIVBlockCipher.this;
                gCMSIVBlockCipher.gHASH(gCMSIVBlockCipher.theReverse);
                i4 = i3 - i6;
                this.numActive = 0;
                i7 = i6 + 0;
            }
            while (i4 >= 16) {
                GCMSIVBlockCipher.fillReverse(bArr, i2 + i7, 16, GCMSIVBlockCipher.this.theReverse);
                GCMSIVBlockCipher gCMSIVBlockCipher2 = GCMSIVBlockCipher.this;
                gCMSIVBlockCipher2.gHASH(gCMSIVBlockCipher2.theReverse);
                i7 += i6;
                i4 -= i6;
            }
            if (i4 > 0) {
                System.arraycopy(bArr, i2 + i7, this.theBuffer, this.numActive, i4);
                this.numActive += i4;
            }
            this.numHashed += i3;
        }
    }

    public GCMSIVBlockCipher() {
        this(new AESEngine());
    }

    private static int bufLength(byte[] bArr) {
        if (bArr == null) {
            return 0;
        }
        return bArr.length;
    }

    private byte[] calculateTag() {
        this.theDataHasher.completeHash();
        byte[] completePolyVal = completePolyVal();
        byte[] bArr = new byte[16];
        for (int i2 = 0; i2 < 12; i2++) {
            completePolyVal[i2] = (byte) (completePolyVal[i2] ^ this.theNonce[i2]);
        }
        completePolyVal[15] = (byte) (completePolyVal[15] & (-129));
        this.theCipher.processBlock(completePolyVal, 0, bArr, 0);
        return bArr;
    }

    private void checkAEADStatus(int i2) {
        int i3 = this.theFlags;
        if ((i3 & 1) == 0) {
            throw new IllegalStateException("Cipher is not initialised");
        }
        if ((i3 & 2) != 0) {
            throw new IllegalStateException("AEAD data cannot be processed after ordinary data");
        }
        if (this.theAEADHasher.getBytesProcessed() - Long.MIN_VALUE > (MAX_DATALEN - i2) - Long.MIN_VALUE) {
            throw new IllegalStateException("AEAD byte count exceeded");
        }
    }

    private static void checkBuffer(byte[] bArr, int i2, int i3, boolean z2) {
        int bufLength = bufLength(bArr);
        int i4 = i2 + i3;
        if ((i3 < 0 || i2 < 0 || i4 < 0) || i4 > bufLength) {
            if (!z2) {
                throw new DataLengthException("Input buffer too short.");
            }
        }
    }

    private void checkStatus(int i2) {
        long j2;
        int i3 = this.theFlags;
        if ((i3 & 1) == 0) {
            throw new IllegalStateException("Cipher is not initialised");
        }
        if ((i3 & 2) == 0) {
            this.theAEADHasher.completeHash();
            this.theFlags |= 2;
        }
        long size = this.thePlain.size();
        if (this.forEncryption) {
            j2 = 2147483623;
        } else {
            size = this.theEncData.size();
            j2 = 2147483639;
        }
        if (size - Long.MIN_VALUE > (j2 - i2) - Long.MIN_VALUE) {
            throw new IllegalStateException("byte count exceeded");
        }
    }

    private byte[] completePolyVal() {
        byte[] bArr = new byte[16];
        gHashLengths();
        fillReverse(this.theGHash, 0, 16, bArr);
        return bArr;
    }

    private void decryptPlain() {
        byte[] buffer = this.theEncData.getBuffer();
        int size = this.theEncData.size() - 16;
        if (size < 0) {
            throw new InvalidCipherTextException("Data too short");
        }
        byte[] copyOfRange = Arrays.copyOfRange(buffer, size, size + 16);
        byte[] clone = Arrays.clone(copyOfRange);
        clone[15] = (byte) (clone[15] | Byte.MIN_VALUE);
        byte[] bArr = new byte[16];
        int i2 = 0;
        while (size > 0) {
            this.theCipher.processBlock(clone, 0, bArr, 0);
            int min = Math.min(16, size);
            xorBlock(bArr, buffer, i2, min);
            this.thePlain.write(bArr, 0, min);
            this.theDataHasher.updateHash(bArr, 0, min);
            size -= min;
            i2 += min;
            incrementCounter(clone);
        }
        byte[] calculateTag = calculateTag();
        if (!Arrays.constantTimeAreEqual(calculateTag, copyOfRange)) {
            reset();
            throw new InvalidCipherTextException("mac check failed");
        }
        byte[] bArr2 = this.macBlock;
        System.arraycopy(calculateTag, 0, bArr2, 0, bArr2.length);
    }

    private void deriveKeys(KeyParameter keyParameter) {
        byte[] bArr = new byte[16];
        byte[] bArr2 = new byte[16];
        byte[] bArr3 = new byte[16];
        int length = keyParameter.getKey().length;
        byte[] bArr4 = new byte[length];
        System.arraycopy(this.theNonce, 0, bArr, 4, 12);
        this.theCipher.init(true, keyParameter);
        this.theCipher.processBlock(bArr, 0, bArr2, 0);
        System.arraycopy(bArr2, 0, bArr3, 0, 8);
        bArr[0] = (byte) (bArr[0] + 1);
        this.theCipher.processBlock(bArr, 0, bArr2, 0);
        System.arraycopy(bArr2, 0, bArr3, 8, 8);
        bArr[0] = (byte) (bArr[0] + 1);
        this.theCipher.processBlock(bArr, 0, bArr2, 0);
        System.arraycopy(bArr2, 0, bArr4, 0, 8);
        bArr[0] = (byte) (bArr[0] + 1);
        this.theCipher.processBlock(bArr, 0, bArr2, 0);
        System.arraycopy(bArr2, 0, bArr4, 8, 8);
        if (length == 32) {
            bArr[0] = (byte) (bArr[0] + 1);
            this.theCipher.processBlock(bArr, 0, bArr2, 0);
            System.arraycopy(bArr2, 0, bArr4, 16, 8);
            bArr[0] = (byte) (bArr[0] + 1);
            this.theCipher.processBlock(bArr, 0, bArr2, 0);
            System.arraycopy(bArr2, 0, bArr4, 24, 8);
        }
        this.theCipher.init(true, new KeyParameter(bArr4));
        fillReverse(bArr3, 0, 16, bArr2);
        mulX(bArr2);
        this.theMultiplier.init(bArr2);
        this.theFlags |= 1;
    }

    private int encryptPlain(byte[] bArr, byte[] bArr2, int i2) {
        byte[] buffer = this.thePlain.getBuffer();
        byte[] clone = Arrays.clone(bArr);
        clone[15] = (byte) (clone[15] | Byte.MIN_VALUE);
        byte[] bArr3 = new byte[16];
        int size = this.thePlain.size();
        int i3 = 0;
        while (size > 0) {
            this.theCipher.processBlock(clone, 0, bArr3, 0);
            int min = Math.min(16, size);
            xorBlock(bArr3, buffer, i3, min);
            System.arraycopy(bArr3, 0, bArr2, i2 + i3, min);
            size -= min;
            i3 += min;
            incrementCounter(clone);
        }
        return this.thePlain.size();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void fillReverse(byte[] bArr, int i2, int i3, byte[] bArr2) {
        int i4 = 0;
        int i5 = 15;
        while (i4 < i3) {
            bArr2[i5] = bArr[i2 + i4];
            i4++;
            i5--;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void gHASH(byte[] bArr) {
        xorBlock(this.theGHash, bArr);
        this.theMultiplier.multiplyH(this.theGHash);
    }

    private void gHashLengths() {
        byte[] bArr = new byte[16];
        Pack.longToBigEndian(this.theDataHasher.getBytesProcessed() * 8, bArr, 0);
        Pack.longToBigEndian(this.theAEADHasher.getBytesProcessed() * 8, bArr, 8);
        gHASH(bArr);
    }

    private static void incrementCounter(byte[] bArr) {
        for (int i2 = 0; i2 < 4; i2++) {
            byte b = (byte) (bArr[i2] + 1);
            bArr[i2] = b;
            if (b != 0) {
                return;
            }
        }
    }

    private static void mulX(byte[] bArr) {
        int i2 = 0;
        for (int i3 = 0; i3 < 16; i3++) {
            byte b = bArr[i3];
            bArr[i3] = (byte) (i2 | ((b >> 1) & CertificateBody.profileType));
            i2 = (b & 1) == 0 ? 0 : -128;
        }
        if (i2 != 0) {
            bArr[0] = (byte) (bArr[0] ^ ADD);
        }
    }

    private void resetStreams() {
        GCMSIVCache gCMSIVCache = this.thePlain;
        if (gCMSIVCache != null) {
            gCMSIVCache.clearBuffer();
        }
        this.theAEADHasher.reset();
        this.theDataHasher.reset();
        this.thePlain = new GCMSIVCache();
        this.theEncData = this.forEncryption ? null : new GCMSIVCache();
        this.theFlags &= -3;
        Arrays.fill(this.theGHash, (byte) 0);
        byte[] bArr = this.theInitialAEAD;
        if (bArr != null) {
            this.theAEADHasher.updateHash(bArr, 0, bArr.length);
        }
    }

    private static void xorBlock(byte[] bArr, byte[] bArr2) {
        for (int i2 = 0; i2 < 16; i2++) {
            bArr[i2] = (byte) (bArr[i2] ^ bArr2[i2]);
        }
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public int doFinal(byte[] bArr, int i2) {
        checkStatus(0);
        checkBuffer(bArr, i2, getOutputSize(0), true);
        if (!this.forEncryption) {
            decryptPlain();
            int size = this.thePlain.size();
            System.arraycopy(this.thePlain.getBuffer(), 0, bArr, i2, size);
            resetStreams();
            return size;
        }
        byte[] calculateTag = calculateTag();
        int encryptPlain = encryptPlain(calculateTag, bArr, i2) + 16;
        System.arraycopy(calculateTag, 0, bArr, this.thePlain.size() + i2, 16);
        byte[] bArr2 = this.macBlock;
        System.arraycopy(calculateTag, 0, bArr2, 0, bArr2.length);
        resetStreams();
        return encryptPlain;
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public String getAlgorithmName() {
        return this.theCipher.getAlgorithmName() + "-GCM-SIV";
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public byte[] getMac() {
        return Arrays.clone(this.macBlock);
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public int getOutputSize(int i2) {
        if (this.forEncryption) {
            return this.thePlain.size() + i2 + 16;
        }
        int size = this.theEncData.size() + i2;
        if (size > 16) {
            return size - 16;
        }
        return 0;
    }

    @Override // org.bouncycastle.crypto.modes.AEADBlockCipher
    public BlockCipher getUnderlyingCipher() {
        return this.theCipher;
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public int getUpdateOutputSize(int i2) {
        return 0;
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        byte[] iv;
        KeyParameter keyParameter;
        byte[] bArr;
        if (cipherParameters instanceof AEADParameters) {
            AEADParameters aEADParameters = (AEADParameters) cipherParameters;
            bArr = aEADParameters.getAssociatedText();
            iv = aEADParameters.getNonce();
            keyParameter = aEADParameters.getKey();
        } else {
            if (!(cipherParameters instanceof ParametersWithIV)) {
                throw new IllegalArgumentException("invalid parameters passed to GCM-SIV");
            }
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            iv = parametersWithIV.getIV();
            keyParameter = (KeyParameter) parametersWithIV.getParameters();
            bArr = null;
        }
        if (iv == null || iv.length != 12) {
            throw new IllegalArgumentException("Invalid nonce");
        }
        if (keyParameter == null || !(keyParameter.getKey().length == 16 || keyParameter.getKey().length == 32)) {
            throw new IllegalArgumentException("Invalid key");
        }
        this.forEncryption = z2;
        this.theInitialAEAD = bArr;
        this.theNonce = iv;
        deriveKeys(keyParameter);
        resetStreams();
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public void processAADByte(byte b) {
        checkAEADStatus(1);
        this.theAEADHasher.updateHash(b);
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public void processAADBytes(byte[] bArr, int i2, int i3) {
        checkAEADStatus(i3);
        checkBuffer(bArr, i2, i3, false);
        this.theAEADHasher.updateHash(bArr, i2, i3);
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public int processByte(byte b, byte[] bArr, int i2) {
        checkStatus(1);
        if (!this.forEncryption) {
            this.theEncData.write(b);
            return 0;
        }
        this.thePlain.write(b);
        this.theDataHasher.updateHash(b);
        return 0;
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public int processBytes(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        checkStatus(i3);
        checkBuffer(bArr, i2, i3, false);
        if (this.forEncryption) {
            this.thePlain.write(bArr, i2, i3);
            this.theDataHasher.updateHash(bArr, i2, i3);
        } else {
            this.theEncData.write(bArr, i2, i3);
        }
        return 0;
    }

    @Override // org.bouncycastle.crypto.modes.AEADCipher
    public void reset() {
        resetStreams();
    }

    public GCMSIVBlockCipher(BlockCipher blockCipher) {
        this(blockCipher, new Tables4kGCMMultiplier());
    }

    private static void xorBlock(byte[] bArr, byte[] bArr2, int i2, int i3) {
        for (int i4 = 0; i4 < i3; i4++) {
            bArr[i4] = (byte) (bArr[i4] ^ bArr2[i4 + i2]);
        }
    }

    public GCMSIVBlockCipher(BlockCipher blockCipher, GCMMultiplier gCMMultiplier) {
        this.theGHash = new byte[16];
        this.theReverse = new byte[16];
        this.macBlock = new byte[16];
        if (blockCipher.getBlockSize() != 16) {
            throw new IllegalArgumentException("Cipher required with a block size of 16.");
        }
        this.theCipher = blockCipher;
        this.theMultiplier = gCMMultiplier;
        this.theAEADHasher = new GCMSIVHasher();
        this.theDataHasher = new GCMSIVHasher();
    }
}
