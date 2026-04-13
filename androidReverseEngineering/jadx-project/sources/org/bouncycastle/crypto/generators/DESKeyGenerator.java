package org.bouncycastle.crypto.generators;

import org.bouncycastle.crypto.CipherKeyGenerator;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.crypto.params.DESParameters;

/* loaded from: classes.dex */
public class DESKeyGenerator extends CipherKeyGenerator {
    @Override // org.bouncycastle.crypto.CipherKeyGenerator
    public byte[] generateKey() {
        byte[] bArr = new byte[8];
        do {
            this.random.nextBytes(bArr);
            DESParameters.setOddParity(bArr);
        } while (DESParameters.isWeakKey(bArr, 0));
        return bArr;
    }

    @Override // org.bouncycastle.crypto.CipherKeyGenerator
    public void init(KeyGenerationParameters keyGenerationParameters) {
        super.init(keyGenerationParameters);
        int i2 = this.strength;
        if (i2 == 0 || i2 == 7) {
            this.strength = 8;
        } else if (i2 != 8) {
            throw new IllegalArgumentException("DES key must be 64 bits long.");
        }
    }
}
