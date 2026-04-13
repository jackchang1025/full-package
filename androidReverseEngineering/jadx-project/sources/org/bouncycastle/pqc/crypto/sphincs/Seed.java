package org.bouncycastle.pqc.crypto.sphincs;

import org.bouncycastle.crypto.engines.ChaChaEngine;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.pqc.crypto.sphincs.Tree;
import org.bouncycastle.util.Pack;

/* loaded from: classes.dex */
class Seed {
    public static void get_seed(HashFunctions hashFunctions, byte[] bArr, int i2, byte[] bArr2, Tree.leafaddr leafaddrVar) {
        byte[] bArr3 = new byte[40];
        for (int i3 = 0; i3 < 32; i3++) {
            bArr3[i3] = bArr2[i3];
        }
        Pack.longToLittleEndian((leafaddrVar.subleaf << 59) | leafaddrVar.level | (leafaddrVar.subtree << 4), bArr3, 32);
        hashFunctions.varlen_hash(bArr, i2, bArr3, 40);
    }

    public static void prg(byte[] bArr, int i2, long j2, byte[] bArr2, int i3) {
        ChaChaEngine chaChaEngine = new ChaChaEngine(12);
        chaChaEngine.init(true, new ParametersWithIV(new KeyParameter(bArr2, i3, 32), new byte[8]));
        chaChaEngine.processBytes(bArr, i2, (int) j2, bArr, i2);
    }
}
