package org.bouncycastle.crypto.macs;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;

/* loaded from: classes.dex */
public class CMacWithIV extends CMac {
    public CMacWithIV(BlockCipher blockCipher) {
        super(blockCipher);
    }

    @Override // org.bouncycastle.crypto.macs.CMac
    public void validate(CipherParameters cipherParameters) {
    }

    public CMacWithIV(BlockCipher blockCipher, int i2) {
        super(blockCipher, i2);
    }
}
