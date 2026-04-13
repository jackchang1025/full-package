package org.bouncycastle.crypto.fpe;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.params.FPEParameters;
import org.bouncycastle.util.Properties;

/* loaded from: classes.dex */
public class FPEFF1Engine extends FPEEngine {
    public FPEFF1Engine() {
        this(new AESEngine());
    }

    @Override // org.bouncycastle.crypto.fpe.FPEEngine
    public int decryptBlock(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        System.arraycopy(this.fpeParameters.getRadix() > 256 ? FPEEngine.toByteArray(SP80038G.decryptFF1w(this.baseCipher, this.fpeParameters.getRadix(), this.fpeParameters.getTweak(), FPEEngine.toShortArray(bArr), i2, i3 / 2)) : SP80038G.decryptFF1(this.baseCipher, this.fpeParameters.getRadix(), this.fpeParameters.getTweak(), bArr, i2, i3), 0, bArr2, i4, i3);
        return i3;
    }

    @Override // org.bouncycastle.crypto.fpe.FPEEngine
    public int encryptBlock(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        System.arraycopy(this.fpeParameters.getRadix() > 256 ? FPEEngine.toByteArray(SP80038G.encryptFF1w(this.baseCipher, this.fpeParameters.getRadix(), this.fpeParameters.getTweak(), FPEEngine.toShortArray(bArr), i2, i3 / 2)) : SP80038G.encryptFF1(this.baseCipher, this.fpeParameters.getRadix(), this.fpeParameters.getTweak(), bArr, i2, i3), 0, bArr2, i4, i3);
        return i3;
    }

    @Override // org.bouncycastle.crypto.fpe.FPEEngine
    public String getAlgorithmName() {
        return "FF1";
    }

    @Override // org.bouncycastle.crypto.fpe.FPEEngine
    public void init(boolean z2, CipherParameters cipherParameters) {
        this.forEncryption = z2;
        this.fpeParameters = (FPEParameters) cipherParameters;
        this.baseCipher.init(!r3.isUsingInverseFunction(), this.fpeParameters.getKey());
    }

    public FPEFF1Engine(BlockCipher blockCipher) {
        super(blockCipher);
        if (blockCipher.getBlockSize() != 16) {
            throw new IllegalArgumentException("base cipher needs to be 128 bits");
        }
        if (Properties.isOverrideSet("org.bouncycastle.fpe.disable") || Properties.isOverrideSet("org.bouncycastle.fpe.disable_ff1")) {
            throw new UnsupportedOperationException("FF1 encryption disabled");
        }
    }
}
