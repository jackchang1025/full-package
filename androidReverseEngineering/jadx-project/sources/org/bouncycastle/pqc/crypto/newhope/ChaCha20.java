package org.bouncycastle.pqc.crypto.newhope;

import org.bouncycastle.crypto.engines.ChaChaEngine;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

/* loaded from: classes.dex */
class ChaCha20 {
    public static void process(byte[] bArr, byte[] bArr2, byte[] bArr3, int i2, int i3) {
        ChaChaEngine chaChaEngine = new ChaChaEngine(20);
        chaChaEngine.init(true, new ParametersWithIV(new KeyParameter(bArr), bArr2));
        chaChaEngine.processBytes(bArr3, i2, i3, bArr3, i2);
    }
}
