package org.bouncycastle.pqc.crypto.sphincsplus;

import java.util.LinkedList;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
class Fors {
    SPHINCSPlusEngine engine;
    private final WotsPlus wots;

    public Fors(SPHINCSPlusEngine sPHINCSPlusEngine) {
        this.engine = sPHINCSPlusEngine;
        this.wots = new WotsPlus(sPHINCSPlusEngine);
    }

    public static int[] message_to_idxs(byte[] bArr, int i2, int i3) {
        int[] iArr = new int[i2];
        int i4 = 0;
        for (int i5 = 0; i5 < i2; i5++) {
            iArr[i5] = 0;
            for (int i6 = 0; i6 < i3; i6++) {
                iArr[i5] = iArr[i5] ^ (((bArr[i4 >> 3] >> (i4 & 7)) & 1) << i6);
                i4++;
            }
        }
        return iArr;
    }

    public byte[] pkFromSig(SIG_FORS[] sig_forsArr, byte[] bArr, byte[] bArr2, ADRS adrs) {
        int i2 = 2;
        byte[][] bArr3 = new byte[2][];
        SPHINCSPlusEngine sPHINCSPlusEngine = this.engine;
        int i3 = sPHINCSPlusEngine.f1614K;
        byte[][] bArr4 = new byte[i3][];
        int i4 = sPHINCSPlusEngine.f1616T;
        int[] message_to_idxs = message_to_idxs(bArr, i3, sPHINCSPlusEngine.f1611A);
        int i5 = 0;
        while (i5 < this.engine.f1614K) {
            int i6 = message_to_idxs[i5];
            byte[] sk = sig_forsArr[i5].getSK();
            adrs.setTreeHeight(0);
            int i7 = (i5 * i4) + i6;
            adrs.setTreeIndex(i7);
            bArr3[0] = this.engine.mo1230F(bArr2, adrs, sk);
            byte[][] authPath = sig_forsArr[i5].getAuthPath();
            adrs.setTreeIndex(i7);
            int i8 = 0;
            while (i8 < this.engine.f1611A) {
                int i9 = i8 + 1;
                adrs.setTreeHeight(i9);
                if ((i6 / (1 << i8)) % i2 == 0) {
                    adrs.setTreeIndex(adrs.getTreeIndex() / i2);
                    bArr3[1] = this.engine.mo1231H(bArr2, adrs, bArr3[0], authPath[i8]);
                } else {
                    adrs.setTreeIndex((adrs.getTreeIndex() - 1) / 2);
                    bArr3[1] = this.engine.mo1231H(bArr2, adrs, authPath[i8], bArr3[0]);
                }
                bArr3[0] = bArr3[1];
                i8 = i9;
                i2 = 2;
            }
            bArr4[i5] = bArr3[0];
            i5++;
            i2 = 2;
        }
        ADRS adrs2 = new ADRS(adrs);
        adrs2.setType(4);
        adrs2.setKeyPairAddress(adrs.getKeyPairAddress());
        return this.engine.T_l(bArr2, adrs2, Arrays.concatenate(bArr4));
    }

    public byte[] pkGen(byte[] bArr, byte[] bArr2, ADRS adrs) {
        ADRS adrs2 = new ADRS(adrs);
        byte[][] bArr3 = new byte[this.engine.f1614K][];
        int i2 = 0;
        while (true) {
            SPHINCSPlusEngine sPHINCSPlusEngine = this.engine;
            if (i2 >= sPHINCSPlusEngine.f1614K) {
                adrs2.setType(4);
                adrs2.setKeyPairAddress(adrs.getKeyPairAddress());
                return this.engine.T_l(bArr2, adrs2, Arrays.concatenate(bArr3));
            }
            bArr3[i2] = treehash(bArr, i2 * sPHINCSPlusEngine.f1616T, sPHINCSPlusEngine.f1611A, bArr2, adrs);
            i2++;
        }
    }

    public SIG_FORS[] sign(byte[] bArr, byte[] bArr2, byte[] bArr3, ADRS adrs) {
        Fors fors = this;
        SPHINCSPlusEngine sPHINCSPlusEngine = fors.engine;
        int[] message_to_idxs = message_to_idxs(bArr, sPHINCSPlusEngine.f1614K, sPHINCSPlusEngine.f1611A);
        SPHINCSPlusEngine sPHINCSPlusEngine2 = fors.engine;
        SIG_FORS[] sig_forsArr = new SIG_FORS[sPHINCSPlusEngine2.f1614K];
        int i2 = sPHINCSPlusEngine2.f1616T;
        int i3 = 0;
        int i4 = 0;
        while (i4 < fors.engine.f1614K) {
            int i5 = message_to_idxs[i4];
            adrs.setTreeHeight(i3);
            int i6 = i4 * i2;
            adrs.setTreeIndex(i6 + i5);
            byte[] PRF = fors.engine.PRF(bArr2, adrs);
            byte[][] bArr4 = new byte[fors.engine.f1611A][];
            int i7 = i3;
            while (i7 < fors.engine.f1611A) {
                int i8 = 1 << i7;
                int i9 = i7;
                byte[][] bArr5 = bArr4;
                bArr5[i9] = treehash(bArr2, ((1 ^ (i5 / i8)) * i8) + i6, i7, bArr3, adrs);
                i7 = i9 + 1;
                PRF = PRF;
                bArr4 = bArr5;
                fors = this;
            }
            sig_forsArr[i4] = new SIG_FORS(PRF, bArr4);
            i4++;
            i3 = 0;
            fors = this;
        }
        return sig_forsArr;
    }

    public byte[] treehash(byte[] bArr, int i2, int i3, byte[] bArr2, ADRS adrs) {
        ADRS adrs2 = new ADRS(adrs);
        LinkedList linkedList = new LinkedList();
        int i4 = 1 << i3;
        if (i2 % i4 != 0) {
            return null;
        }
        for (int i5 = 0; i5 < i4; i5++) {
            adrs2.setTreeHeight(0);
            int i6 = i2 + i5;
            adrs2.setTreeIndex(i6);
            byte[] mo1230F = this.engine.mo1230F(bArr2, adrs2, this.engine.PRF(bArr, adrs2));
            adrs2.setTreeHeight(1);
            adrs2.setTreeIndex(i6);
            while (!linkedList.isEmpty() && ((NodeEntry) linkedList.get(0)).nodeHeight == adrs2.getTreeHeight()) {
                adrs2.setTreeIndex((adrs2.getTreeIndex() - 1) / 2);
                mo1230F = this.engine.mo1231H(bArr2, adrs2, ((NodeEntry) linkedList.remove(0)).nodeValue, mo1230F);
                adrs2.setTreeHeight(adrs2.getTreeHeight() + 1);
            }
            linkedList.add(0, new NodeEntry(mo1230F, adrs2.getTreeHeight()));
        }
        return ((NodeEntry) linkedList.get(0)).nodeValue;
    }
}
