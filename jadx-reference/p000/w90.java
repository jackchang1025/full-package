package p000;

import java.lang.reflect.Array;
import java.security.SecureRandom;

/* loaded from: classes2.dex */
public class w90 {
    private short[][][] coeff_alpha;
    private short[][][] coeff_beta;
    private short[] coeff_eta;
    private short[][] coeff_gamma;

    /* renamed from: oi */
    private int f60859oi;

    /* renamed from: vi */
    private int f60860vi;
    private int viNext;

    public w90(byte b, byte b2, short[][][] sArr, short[][][] sArr2, short[][] sArr3, short[] sArr4) {
        int i = b & 255;
        this.f60860vi = i;
        int i2 = b2 & 255;
        this.viNext = i2;
        this.f60859oi = i2 - i;
        this.coeff_alpha = sArr;
        this.coeff_beta = sArr2;
        this.coeff_gamma = sArr3;
        this.coeff_eta = sArr4;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof w90)) {
            w90 w90Var = (w90) obj;
            if (this.f60860vi == w90Var.getVi() && this.viNext == w90Var.getViNext() && this.f60859oi == w90Var.getOi() && yp0.equals(this.coeff_alpha, w90Var.getCoeffAlpha()) && yp0.equals(this.coeff_beta, w90Var.getCoeffBeta()) && yp0.equals(this.coeff_gamma, w90Var.getCoeffGamma()) && yp0.equals(this.coeff_eta, w90Var.getCoeffEta())) {
                return true;
            }
        }
        return false;
    }

    public short[][][] getCoeffAlpha() {
        return this.coeff_alpha;
    }

    public short[][][] getCoeffBeta() {
        return this.coeff_beta;
    }

    public short[] getCoeffEta() {
        return this.coeff_eta;
    }

    public short[][] getCoeffGamma() {
        return this.coeff_gamma;
    }

    public int getOi() {
        return this.f60859oi;
    }

    public int getVi() {
        return this.f60860vi;
    }

    public int getViNext() {
        return this.viNext;
    }

    public int hashCode() {
        return C0133bg.hashCode(this.coeff_eta) + ((C0133bg.hashCode(this.coeff_gamma) + ((C0133bg.hashCode(this.coeff_beta) + ((C0133bg.hashCode(this.coeff_alpha) + (((((this.f60860vi * 37) + this.viNext) * 37) + this.f60859oi) * 37)) * 37)) * 37)) * 37);
    }

    public short[][] plugInVinegars(short[] sArr) {
        int i = this.f60859oi;
        int i2 = 0;
        short[][] sArr2 = (short[][]) Array.newInstance((Class<?>) Short.TYPE, i, i + 1);
        short[] sArr3 = new short[this.f60859oi];
        for (int i3 = 0; i3 < this.f60859oi; i3++) {
            for (int i4 = 0; i4 < this.f60860vi; i4++) {
                for (int i5 = 0; i5 < this.f60860vi; i5++) {
                    sArr3[i3] = v10.addElem(sArr3[i3], v10.multElem(v10.multElem(this.coeff_beta[i3][i4][i5], sArr[i4]), sArr[i5]));
                }
            }
        }
        for (int i6 = 0; i6 < this.f60859oi; i6++) {
            for (int i7 = 0; i7 < this.f60859oi; i7++) {
                for (int i8 = 0; i8 < this.f60860vi; i8++) {
                    short sMultElem = v10.multElem(this.coeff_alpha[i6][i7][i8], sArr[i8]);
                    short[] sArr4 = sArr2[i6];
                    sArr4[i7] = v10.addElem(sArr4[i7], sMultElem);
                }
            }
        }
        for (int i9 = 0; i9 < this.f60859oi; i9++) {
            for (int i10 = 0; i10 < this.f60860vi; i10++) {
                sArr3[i9] = v10.addElem(sArr3[i9], v10.multElem(this.coeff_gamma[i9][i10], sArr[i10]));
            }
        }
        for (int i11 = 0; i11 < this.f60859oi; i11++) {
            for (int i12 = this.f60860vi; i12 < this.viNext; i12++) {
                short[] sArr5 = sArr2[i11];
                int i13 = this.f60860vi;
                sArr5[i12 - i13] = v10.addElem(this.coeff_gamma[i11][i12], sArr5[i12 - i13]);
            }
        }
        for (int i14 = 0; i14 < this.f60859oi; i14++) {
            sArr3[i14] = v10.addElem(sArr3[i14], this.coeff_eta[i14]);
        }
        while (true) {
            int i15 = this.f60859oi;
            if (i2 >= i15) {
                return sArr2;
            }
            sArr2[i2][i15] = sArr3[i2];
            i2++;
        }
    }

    public w90(int i, int i2, SecureRandom secureRandom) {
        this.f60860vi = i;
        this.viNext = i2;
        int i3 = i2 - i;
        this.f60859oi = i3;
        int[] iArr = {i3, i3, i};
        Class cls = Short.TYPE;
        this.coeff_alpha = (short[][][]) Array.newInstance((Class<?>) cls, iArr);
        int i4 = this.f60859oi;
        int i5 = this.f60860vi;
        this.coeff_beta = (short[][][]) Array.newInstance((Class<?>) cls, i4, i5, i5);
        this.coeff_gamma = (short[][]) Array.newInstance((Class<?>) cls, this.f60859oi, this.viNext);
        int i6 = this.f60859oi;
        this.coeff_eta = new short[i6];
        for (int i7 = 0; i7 < i6; i7++) {
            for (int i8 = 0; i8 < this.f60859oi; i8++) {
                for (int i9 = 0; i9 < this.f60860vi; i9++) {
                    this.coeff_alpha[i7][i8][i9] = (short) (secureRandom.nextInt() & v10.MASK);
                }
            }
        }
        for (int i10 = 0; i10 < i6; i10++) {
            for (int i11 = 0; i11 < this.f60860vi; i11++) {
                for (int i12 = 0; i12 < this.f60860vi; i12++) {
                    this.coeff_beta[i10][i11][i12] = (short) (secureRandom.nextInt() & v10.MASK);
                }
            }
        }
        for (int i13 = 0; i13 < i6; i13++) {
            for (int i14 = 0; i14 < this.viNext; i14++) {
                this.coeff_gamma[i13][i14] = (short) (secureRandom.nextInt() & v10.MASK);
            }
        }
        for (int i15 = 0; i15 < i6; i15++) {
            this.coeff_eta[i15] = (short) (secureRandom.nextInt() & v10.MASK);
        }
    }
}
