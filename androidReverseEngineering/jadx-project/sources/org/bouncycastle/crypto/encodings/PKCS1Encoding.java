package org.bouncycastle.crypto.encodings;

import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Properties;

/* loaded from: classes.dex */
public class PKCS1Encoding implements AsymmetricBlockCipher {
    private static final int HEADER_LENGTH = 10;
    public static final String NOT_STRICT_LENGTH_ENABLED_PROPERTY = "org.bouncycastle.pkcs1.not_strict";
    public static final String STRICT_LENGTH_ENABLED_PROPERTY = "org.bouncycastle.pkcs1.strict";
    private byte[] blockBuffer;
    private AsymmetricBlockCipher engine;
    private byte[] fallback;
    private boolean forEncryption;
    private boolean forPrivateKey;
    private int pLen;
    private SecureRandom random;
    private boolean useStrictLength;

    public PKCS1Encoding(AsymmetricBlockCipher asymmetricBlockCipher) {
        this.pLen = -1;
        this.fallback = null;
        this.engine = asymmetricBlockCipher;
        this.useStrictLength = useStrict();
    }

    private static int checkPkcs1Encoding(byte[] bArr, int i2) {
        int i3 = 0 | (bArr[0] ^ 2);
        int i4 = i2 + 1;
        int length = bArr.length - i4;
        for (int i5 = 1; i5 < length; i5++) {
            byte b = bArr[i5];
            int i6 = b | (b >> 1);
            int i7 = i6 | (i6 >> 2);
            i3 |= ((i7 | (i7 >> 4)) & 1) - 1;
        }
        int i8 = bArr[bArr.length - i4] | i3;
        int i9 = i8 | (i8 >> 1);
        int i10 = i9 | (i9 >> 2);
        return ~(((i10 | (i10 >> 4)) & 1) - 1);
    }

    private byte[] decodeBlock(byte[] bArr, int i2, int i3) {
        if (this.pLen != -1) {
            return decodeBlockOrRandom(bArr, i2, i3);
        }
        byte[] processBlock = this.engine.processBlock(bArr, i2, i3);
        boolean z2 = this.useStrictLength & (processBlock.length != this.engine.getOutputBlockSize());
        if (processBlock.length < getOutputBlockSize()) {
            processBlock = this.blockBuffer;
        }
        byte b = processBlock[0];
        boolean z3 = !this.forPrivateKey ? b == 1 : b == 2;
        int findStart = findStart(b, processBlock) + 1;
        if (z3 || (findStart < 10)) {
            Arrays.fill(processBlock, (byte) 0);
            throw new InvalidCipherTextException("block incorrect");
        }
        if (z2) {
            Arrays.fill(processBlock, (byte) 0);
            throw new InvalidCipherTextException("block incorrect size");
        }
        int length = processBlock.length - findStart;
        byte[] bArr2 = new byte[length];
        System.arraycopy(processBlock, findStart, bArr2, 0, length);
        return bArr2;
    }

    private byte[] decodeBlockOrRandom(byte[] bArr, int i2, int i3) {
        if (!this.forPrivateKey) {
            throw new InvalidCipherTextException("sorry, this method is only for decryption, not for signing");
        }
        byte[] processBlock = this.engine.processBlock(bArr, i2, i3);
        byte[] bArr2 = this.fallback;
        if (bArr2 == null) {
            bArr2 = new byte[this.pLen];
            this.random.nextBytes(bArr2);
        }
        if (this.useStrictLength & (processBlock.length != this.engine.getOutputBlockSize())) {
            processBlock = this.blockBuffer;
        }
        int checkPkcs1Encoding = checkPkcs1Encoding(processBlock, this.pLen);
        byte[] bArr3 = new byte[this.pLen];
        int i4 = 0;
        while (true) {
            int i5 = this.pLen;
            if (i4 >= i5) {
                Arrays.fill(processBlock, (byte) 0);
                return bArr3;
            }
            bArr3[i4] = (byte) ((processBlock[(processBlock.length - i5) + i4] & (~checkPkcs1Encoding)) | (bArr2[i4] & checkPkcs1Encoding));
            i4++;
        }
    }

    private byte[] encodeBlock(byte[] bArr, int i2, int i3) {
        if (i3 > getInputBlockSize()) {
            throw new IllegalArgumentException("input data too large");
        }
        int inputBlockSize = this.engine.getInputBlockSize();
        byte[] bArr2 = new byte[inputBlockSize];
        if (this.forPrivateKey) {
            bArr2[0] = 1;
            for (int i4 = 1; i4 != (inputBlockSize - i3) - 1; i4++) {
                bArr2[i4] = -1;
            }
        } else {
            this.random.nextBytes(bArr2);
            bArr2[0] = 2;
            for (int i5 = 1; i5 != (inputBlockSize - i3) - 1; i5++) {
                while (bArr2[i5] == 0) {
                    bArr2[i5] = (byte) this.random.nextInt();
                }
            }
        }
        int i6 = inputBlockSize - i3;
        bArr2[i6 - 1] = 0;
        System.arraycopy(bArr, i2, bArr2, i6, i3);
        return this.engine.processBlock(bArr2, 0, inputBlockSize);
    }

    private int findStart(byte b, byte[] bArr) {
        int i2 = -1;
        boolean z2 = false;
        for (int i3 = 1; i3 != bArr.length; i3++) {
            byte b2 = bArr[i3];
            if ((b2 == 0) & (i2 < 0)) {
                i2 = i3;
            }
            z2 |= (b2 != -1) & (b == 1) & (i2 < 0);
        }
        if (z2) {
            return -1;
        }
        return i2;
    }

    private boolean useStrict() {
        if (Properties.isOverrideSetTo(NOT_STRICT_LENGTH_ENABLED_PROPERTY, true)) {
            return false;
        }
        return !Properties.isOverrideSetTo(STRICT_LENGTH_ENABLED_PROPERTY, false);
    }

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public int getInputBlockSize() {
        int inputBlockSize = this.engine.getInputBlockSize();
        return this.forEncryption ? inputBlockSize - 10 : inputBlockSize;
    }

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public int getOutputBlockSize() {
        int outputBlockSize = this.engine.getOutputBlockSize();
        return this.forEncryption ? outputBlockSize : outputBlockSize - 10;
    }

    public AsymmetricBlockCipher getUnderlyingCipher() {
        return this.engine;
    }

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        AsymmetricKeyParameter asymmetricKeyParameter;
        if (cipherParameters instanceof ParametersWithRandom) {
            ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
            this.random = parametersWithRandom.getRandom();
            asymmetricKeyParameter = (AsymmetricKeyParameter) parametersWithRandom.getParameters();
        } else {
            asymmetricKeyParameter = (AsymmetricKeyParameter) cipherParameters;
            if (!asymmetricKeyParameter.isPrivate() && z2) {
                this.random = CryptoServicesRegistrar.getSecureRandom();
            }
        }
        this.engine.init(z2, cipherParameters);
        this.forPrivateKey = asymmetricKeyParameter.isPrivate();
        this.forEncryption = z2;
        this.blockBuffer = new byte[this.engine.getOutputBlockSize()];
        if (this.pLen > 0 && this.fallback == null && this.random == null) {
            throw new IllegalArgumentException("encoder requires random");
        }
    }

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public byte[] processBlock(byte[] bArr, int i2, int i3) {
        return this.forEncryption ? encodeBlock(bArr, i2, i3) : decodeBlock(bArr, i2, i3);
    }

    public PKCS1Encoding(AsymmetricBlockCipher asymmetricBlockCipher, int i2) {
        this.pLen = -1;
        this.fallback = null;
        this.engine = asymmetricBlockCipher;
        this.useStrictLength = useStrict();
        this.pLen = i2;
    }

    public PKCS1Encoding(AsymmetricBlockCipher asymmetricBlockCipher, byte[] bArr) {
        this.pLen = -1;
        this.fallback = null;
        this.engine = asymmetricBlockCipher;
        this.useStrictLength = useStrict();
        this.fallback = bArr;
        this.pLen = bArr.length;
    }
}
