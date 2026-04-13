package org.bouncycastle.pqc.crypto.sphincsplus;

import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Pack;

/* loaded from: classes.dex */
class WotsPlus {
    private final SPHINCSPlusEngine engine;

    /* renamed from: w */
    private final int f1617w;

    public WotsPlus(SPHINCSPlusEngine sPHINCSPlusEngine) {
        this.engine = sPHINCSPlusEngine;
        this.f1617w = sPHINCSPlusEngine.WOTS_W;
    }

    public int[] base_w(byte[] bArr, int i2, int i3) {
        int[] iArr = new int[i3];
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        for (int i8 = 0; i8 < i3; i8++) {
            if (i4 == 0) {
                i7 = bArr[i5];
                i5++;
                i4 += 8;
            }
            i4 -= this.engine.WOTS_LOGW;
            iArr[i6] = (i7 >>> i4) & (i2 - 1);
            i6++;
        }
        return iArr;
    }

    public byte[] chain(byte[] bArr, int i2, int i3, byte[] bArr2, ADRS adrs) {
        if (i3 == 0) {
            return Arrays.clone(bArr);
        }
        int i4 = i2 + i3;
        if (i4 > this.f1617w - 1) {
            return null;
        }
        byte[] chain = chain(bArr, i2, i3 - 1, bArr2, adrs);
        adrs.setHashAddress(i4 - 1);
        return this.engine.mo1230F(bArr2, adrs, chain);
    }

    public byte[] pkFromSig(byte[] bArr, byte[] bArr2, byte[] bArr3, ADRS adrs) {
        SPHINCSPlusEngine sPHINCSPlusEngine;
        ADRS adrs2 = new ADRS(adrs);
        int[] base_w = base_w(bArr2, this.f1617w, this.engine.WOTS_LEN1);
        int i2 = 0;
        int i3 = 0;
        while (true) {
            sPHINCSPlusEngine = this.engine;
            if (i2 >= sPHINCSPlusEngine.WOTS_LEN1) {
                break;
            }
            i3 += (this.f1617w - 1) - base_w[i2];
            i2++;
        }
        int i4 = sPHINCSPlusEngine.WOTS_LEN2;
        int i5 = sPHINCSPlusEngine.WOTS_LOGW;
        int[] concatenate = Arrays.concatenate(base_w, base_w(Arrays.copyOfRange(Pack.intToBigEndian(i3 << (8 - ((i4 * i5) % 8))), 4 - (((i4 * i5) + 7) / 8), 4), this.f1617w, this.engine.WOTS_LEN2));
        SPHINCSPlusEngine sPHINCSPlusEngine2 = this.engine;
        byte[] bArr4 = new byte[sPHINCSPlusEngine2.f1615N];
        byte[][] bArr5 = new byte[sPHINCSPlusEngine2.WOTS_LEN][];
        for (int i6 = 0; i6 < this.engine.WOTS_LEN; i6++) {
            adrs.setChainAddress(i6);
            int i7 = this.engine.f1615N;
            System.arraycopy(bArr, i6 * i7, bArr4, 0, i7);
            int i8 = concatenate[i6];
            bArr5[i6] = chain(bArr4, i8, (this.f1617w - 1) - i8, bArr3, adrs);
        }
        adrs2.setType(1);
        adrs2.setKeyPairAddress(adrs.getKeyPairAddress());
        return this.engine.T_l(bArr3, adrs2, Arrays.concatenate(bArr5));
    }

    public byte[] pkGen(byte[] bArr, byte[] bArr2, ADRS adrs) {
        ADRS adrs2 = new ADRS(adrs);
        byte[][] bArr3 = new byte[this.engine.WOTS_LEN][];
        for (int i2 = 0; i2 < this.engine.WOTS_LEN; i2++) {
            ADRS adrs3 = new ADRS(adrs);
            adrs3.setChainAddress(i2);
            adrs3.setHashAddress(0);
            bArr3[i2] = chain(this.engine.PRF(bArr, adrs3), 0, this.f1617w - 1, bArr2, adrs3);
        }
        adrs2.setType(1);
        adrs2.setKeyPairAddress(adrs.getKeyPairAddress());
        return this.engine.T_l(bArr2, adrs2, Arrays.concatenate(bArr3));
    }

    public byte[] sign(byte[] bArr, byte[] bArr2, byte[] bArr3, ADRS adrs) {
        SPHINCSPlusEngine sPHINCSPlusEngine;
        ADRS adrs2 = new ADRS(adrs);
        int[] base_w = base_w(bArr, this.f1617w, this.engine.WOTS_LEN1);
        int i2 = 0;
        int i3 = 0;
        while (true) {
            sPHINCSPlusEngine = this.engine;
            if (i2 >= sPHINCSPlusEngine.WOTS_LEN1) {
                break;
            }
            i3 += (this.f1617w - 1) - base_w[i2];
            i2++;
        }
        int i4 = sPHINCSPlusEngine.WOTS_LOGW;
        if (i4 % 8 != 0) {
            i3 <<= 8 - ((sPHINCSPlusEngine.WOTS_LEN2 * i4) % 8);
        }
        int i5 = ((sPHINCSPlusEngine.WOTS_LEN2 * i4) + 7) / 8;
        byte[] intToBigEndian = Pack.intToBigEndian(i3);
        int[] concatenate = Arrays.concatenate(base_w, base_w(Arrays.copyOfRange(intToBigEndian, i5, intToBigEndian.length), this.f1617w, this.engine.WOTS_LEN2));
        byte[][] bArr4 = new byte[this.engine.WOTS_LEN][];
        for (int i6 = 0; i6 < this.engine.WOTS_LEN; i6++) {
            adrs2.setChainAddress(i6);
            adrs2.setHashAddress(0);
            bArr4[i6] = chain(this.engine.PRF(bArr2, adrs2), 0, concatenate[i6], bArr3, adrs2);
        }
        return Arrays.concatenate(bArr4);
    }
}
