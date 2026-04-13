package org.bouncycastle.crypto.engines;

import java.security.SecureRandom;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.Wrapper;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public class RFC3211WrapEngine implements Wrapper {
    private CBCBlockCipher engine;
    private boolean forWrapping;
    private ParametersWithIV param;
    private SecureRandom rand;

    public RFC3211WrapEngine(BlockCipher blockCipher) {
        this.engine = new CBCBlockCipher(blockCipher);
    }

    @Override // org.bouncycastle.crypto.Wrapper
    public String getAlgorithmName() {
        return this.engine.getUnderlyingCipher().getAlgorithmName() + "/RFC3211Wrap";
    }

    @Override // org.bouncycastle.crypto.Wrapper
    public void init(boolean z2, CipherParameters cipherParameters) {
        this.forWrapping = z2;
        if (cipherParameters instanceof ParametersWithRandom) {
            ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
            this.rand = parametersWithRandom.getRandom();
            if (!(parametersWithRandom.getParameters() instanceof ParametersWithIV)) {
                throw new IllegalArgumentException("RFC3211Wrap requires an IV");
            }
            this.param = (ParametersWithIV) parametersWithRandom.getParameters();
            return;
        }
        if (z2) {
            this.rand = CryptoServicesRegistrar.getSecureRandom();
        }
        if (!(cipherParameters instanceof ParametersWithIV)) {
            throw new IllegalArgumentException("RFC3211Wrap requires an IV");
        }
        this.param = (ParametersWithIV) cipherParameters;
    }

    @Override // org.bouncycastle.crypto.Wrapper
    public byte[] unwrap(byte[] bArr, int i2, int i3) {
        if (this.forWrapping) {
            throw new IllegalStateException("not set for unwrapping");
        }
        int blockSize = this.engine.getBlockSize();
        if (i3 < blockSize * 2) {
            throw new InvalidCipherTextException("input too short");
        }
        byte[] bArr2 = new byte[i3];
        byte[] bArr3 = new byte[blockSize];
        System.arraycopy(bArr, i2, bArr2, 0, i3);
        System.arraycopy(bArr, i2, bArr3, 0, blockSize);
        this.engine.init(false, new ParametersWithIV(this.param.getParameters(), bArr3));
        for (int i4 = blockSize; i4 < i3; i4 += blockSize) {
            this.engine.processBlock(bArr2, i4, bArr2, i4);
        }
        System.arraycopy(bArr2, i3 - blockSize, bArr3, 0, blockSize);
        this.engine.init(false, new ParametersWithIV(this.param.getParameters(), bArr3));
        this.engine.processBlock(bArr2, 0, bArr2, 0);
        this.engine.init(false, this.param);
        for (int i5 = 0; i5 < i3; i5 += blockSize) {
            this.engine.processBlock(bArr2, i5, bArr2, i5);
        }
        int i6 = bArr2[0];
        int i7 = i3 - 4;
        boolean z2 = (i6 & 255) > i7;
        byte[] bArr4 = z2 ? new byte[i7] : new byte[i6 & 255];
        System.arraycopy(bArr2, 4, bArr4, 0, bArr4.length);
        int i8 = 0;
        int i9 = 0;
        while (i8 != 3) {
            int i10 = i8 + 1;
            i9 |= bArr2[i8 + 4] ^ ((byte) (~bArr2[i10]));
            i8 = i10;
        }
        Arrays.clear(bArr2);
        if (!z2 && !(i9 != 0)) {
            return bArr4;
        }
        throw new InvalidCipherTextException("wrapped key corrupted");
    }

    @Override // org.bouncycastle.crypto.Wrapper
    public byte[] wrap(byte[] bArr, int i2, int i3) {
        if (!this.forWrapping) {
            throw new IllegalStateException("not set for wrapping");
        }
        if (i3 > 255 || i3 < 0) {
            throw new IllegalArgumentException("input must be from 0 to 255 bytes");
        }
        this.engine.init(true, this.param);
        int blockSize = this.engine.getBlockSize();
        int i4 = i3 + 4;
        int i5 = blockSize * 2;
        if (i4 >= i5) {
            i5 = i4 % blockSize == 0 ? i4 : ((i4 / blockSize) + 1) * blockSize;
        }
        byte[] bArr2 = new byte[i5];
        bArr2[0] = (byte) i3;
        System.arraycopy(bArr, i2, bArr2, 4, i3);
        int length = bArr2.length - i4;
        byte[] bArr3 = new byte[length];
        this.rand.nextBytes(bArr3);
        System.arraycopy(bArr3, 0, bArr2, i4, length);
        bArr2[1] = (byte) (~bArr2[4]);
        bArr2[2] = (byte) (~bArr2[5]);
        bArr2[3] = (byte) (~bArr2[6]);
        for (int i6 = 0; i6 < bArr2.length; i6 += blockSize) {
            this.engine.processBlock(bArr2, i6, bArr2, i6);
        }
        for (int i7 = 0; i7 < bArr2.length; i7 += blockSize) {
            this.engine.processBlock(bArr2, i7, bArr2, i7);
        }
        return bArr2;
    }
}
