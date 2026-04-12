package p000;

import android.graphics.Color;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: fp */
/* loaded from: classes.dex */
public final class C0504fp {

    /* renamed from: a0 */
    public float f56306a0;

    /* renamed from: a1 */
    public float f56307a1;

    /* renamed from: a2 */
    public float f56308a2;

    /* renamed from: a3 */
    public float f56309a3;

    /* renamed from: a4 */
    public float f56310a4;

    /* renamed from: a5 */
    public float f56311a5;

    public C0504fp(float f, float f2, float f3, float f4, float f5, float f6) {
        this.f56306a0 = f;
        this.f56307a1 = f2;
        this.f56308a2 = f3;
        this.f56309a3 = f4;
        this.f56310a4 = f5;
        this.f56311a5 = f6;
    }

    /* renamed from: a1 */
    public static C0504fp m212842a1(int i) {
        ld1 ld1Var = ld1.f57886b0;
        float fM210586d5 = b81.m210586d5(Color.red(i));
        float fM210586d52 = b81.m210586d5(Color.green(i));
        float fM210586d53 = b81.m210586d5(Color.blue(i));
        float[][] fArr = b81.f45732a3;
        float[] fArr2 = fArr[0];
        float f = (fArr2[2] * fM210586d53) + (fArr2[1] * fM210586d52) + (fArr2[0] * fM210586d5);
        float[] fArr3 = fArr[1];
        float f2 = (fArr3[2] * fM210586d53) + (fArr3[1] * fM210586d52) + (fArr3[0] * fM210586d5);
        float[] fArr4 = fArr[2];
        float[] fArr5 = {f, f2, (fM210586d53 * fArr4[2]) + (fM210586d52 * fArr4[1]) + (fM210586d5 * fArr4[0])};
        float[][] fArr6 = b81.f45729a0;
        float f3 = fArr5[0];
        float[] fArr7 = fArr6[0];
        float f4 = fArr7[0] * f3;
        float f5 = fArr5[1];
        float f6 = (fArr7[1] * f5) + f4;
        float f7 = fArr5[2];
        float f8 = (fArr7[2] * f7) + f6;
        float[] fArr8 = fArr6[1];
        float f9 = (fArr8[2] * f7) + (fArr8[1] * f5) + (fArr8[0] * f3);
        float[] fArr9 = fArr6[2];
        float f10 = (f7 * fArr9[2]) + (f5 * fArr9[1]) + (f3 * fArr9[0]);
        float[] fArr10 = ld1Var.f57893a6;
        float f11 = ld1Var.f57895a8;
        float f12 = ld1Var.f57890a3;
        float f13 = ld1Var.f57887a0;
        float f14 = fArr10[0] * f8;
        float f15 = fArr10[1] * f9;
        float f16 = fArr10[2] * f10;
        float f17 = ld1Var.f57894a7;
        float fPow = (float) Math.pow((Math.abs(f14) * f17) / 100.0d, 0.42d);
        float fPow2 = (float) Math.pow((Math.abs(f15) * f17) / 100.0d, 0.42d);
        float fPow3 = (float) Math.pow((Math.abs(f16) * f17) / 100.0d, 0.42d);
        float fSignum = ((Math.signum(f14) * 400.0f) * fPow) / (fPow + 27.13f);
        float fSignum2 = ((Math.signum(f15) * 400.0f) * fPow2) / (fPow2 + 27.13f);
        float fSignum3 = ((Math.signum(f16) * 400.0f) * fPow3) / (fPow3 + 27.13f);
        double d = fSignum3;
        float f18 = ((float) (((fSignum2 * (-12.0d)) + (fSignum * 11.0d)) + d)) / 11.0f;
        float f19 = ((float) ((fSignum + fSignum2) - (d * 2.0d))) / 9.0f;
        float f20 = fSignum2 * 20.0f;
        float f21 = ((21.0f * fSignum3) + ((fSignum * 20.0f) + f20)) / 20.0f;
        float f22 = (((fSignum * 40.0f) + f20) + fSignum3) / 20.0f;
        float fAtan2 = (((float) Math.atan2(f19, f18)) * 180.0f) / 3.1415927f;
        if (fAtan2 < 0.0f) {
            fAtan2 += 360.0f;
        } else if (fAtan2 >= 360.0f) {
            fAtan2 -= 360.0f;
        }
        float f23 = (3.1415927f * fAtan2) / 180.0f;
        float fPow4 = ((float) Math.pow((f22 * ld1Var.f57888a1) / f13, ld1Var.f57896a9 * f12)) * 100.0f;
        Math.sqrt(fPow4 / 100.0f);
        float f24 = f13 + 4.0f;
        float fPow5 = ((float) Math.pow(1.64d - Math.pow(0.29d, ld1Var.f57892a5), 0.73d)) * ((float) Math.pow((((((((float) (Math.cos((((((double) fAtan2) < 20.14d ? 360.0f + fAtan2 : fAtan2) * 3.141592653589793d) / 180.0d) + 2.0d) + 3.8d)) * 0.25f) * 3846.1538f) * ld1Var.f57891a4) * ld1Var.f57889a2) * ((float) Math.sqrt((f19 * f19) + (f18 * f18)))) / (f21 + 0.305f), 0.9d)) * ((float) Math.sqrt(fPow4 / 100.0d));
        Math.sqrt((r0 * f12) / f24);
        float f25 = (1.7f * fPow4) / ((0.007f * fPow4) + 1.0f);
        float fLog = ((float) Math.log((f11 * fPow5 * 0.0228f) + 1.0f)) * 43.85965f;
        double d2 = f23;
        return new C0504fp(fAtan2, fPow5, fPow4, f25, fLog * ((float) Math.cos(d2)), fLog * ((float) Math.sin(d2)));
    }

    /* renamed from: a2 */
    public static C0504fp m212843a2(float f, float f2, float f3) {
        ld1 ld1Var = ld1.f57886b0;
        float f4 = ld1Var.f57890a3;
        Math.sqrt(f / 100.0d);
        float f5 = ld1Var.f57887a0 + 4.0f;
        float f6 = ld1Var.f57895a8 * f2;
        Math.sqrt(((f2 / ((float) Math.sqrt(r1))) * ld1Var.f57890a3) / f5);
        float f7 = (1.7f * f) / ((0.007f * f) + 1.0f);
        float fLog = ((float) Math.log((f6 * 0.0228d) + 1.0d)) * 43.85965f;
        double d = (3.1415927f * f3) / 180.0f;
        return new C0504fp(f3, f2, f, f7, fLog * ((float) Math.cos(d)), fLog * ((float) Math.sin(d)));
    }

    /* renamed from: a0 */
    public void m212844a0(float f, float f2, int i, int i2, float[] fArr) {
        float f3 = fArr[0];
        float f4 = fArr[1];
        float f5 = (f2 - 0.5f) * 2.0f;
        float f6 = f3 + this.f56308a2;
        float f7 = f4 + this.f56309a3;
        float f8 = (this.f56306a0 * (f - 0.5f) * 2.0f) + f6;
        float f9 = (this.f56307a1 * f5) + f7;
        float radians = (float) Math.toRadians(this.f56311a5);
        float radians2 = (float) Math.toRadians(this.f56310a4);
        double d = radians;
        double d2 = i2 * f5;
        float fSin = (((float) ((Math.sin(d) * ((-i) * r7)) - (Math.cos(d) * d2))) * radians2) + f8;
        float fCos = (radians2 * ((float) ((Math.cos(d) * (i * r7)) - (Math.sin(d) * d2)))) + f9;
        fArr[0] = fSin;
        fArr[1] = fCos;
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x001f  */
    /* renamed from: a3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int m212845a3(ld1 ld1Var) {
        float fSqrt;
        float f = this.f56308a2;
        float f2 = this.f56307a1;
        if (f2 != 0.0d) {
            double d = f;
            fSqrt = d == 0.0d ? 0.0f : f2 / ((float) Math.sqrt(d / 100.0d));
        }
        float f3 = ld1Var.f57892a5;
        float f4 = ld1Var.f57894a7;
        float fPow = (float) Math.pow(fSqrt / Math.pow(1.64d - Math.pow(0.29d, f3), 0.73d), 1.1111111111111112d);
        double d2 = (this.f56306a0 * 3.1415927f) / 180.0f;
        float fCos = ((float) (Math.cos(2.0d + d2) + 3.8d)) * 0.25f;
        float fPow2 = ld1Var.f57887a0 * ((float) Math.pow(f / 100.0d, (1.0d / ld1Var.f57890a3) / ld1Var.f57896a9));
        float f5 = fCos * 3846.1538f * ld1Var.f57891a4 * ld1Var.f57889a2;
        float f6 = fPow2 / ld1Var.f57888a1;
        float fSin = (float) Math.sin(d2);
        float fCos2 = (float) Math.cos(d2);
        float f7 = (((0.305f + f6) * 23.0f) * fPow) / (((fPow * 108.0f) * fSin) + (((11.0f * fPow) * fCos2) + (f5 * 23.0f)));
        float f8 = fCos2 * f7;
        float f9 = f7 * fSin;
        float f10 = f6 * 460.0f;
        float f11 = ((288.0f * f9) + ((451.0f * f8) + f10)) / 1403.0f;
        float f12 = ((f10 - (891.0f * f8)) - (261.0f * f9)) / 1403.0f;
        float f13 = ((f10 - (f8 * 220.0f)) - (f9 * 6300.0f)) / 1403.0f;
        float f14 = 100.0f / f4;
        float fSignum = Math.signum(f11) * f14 * ((float) Math.pow((float) Math.max(0.0d, (Math.abs(f11) * 27.13d) / (400.0d - Math.abs(f11))), 2.380952380952381d));
        float fSignum2 = Math.signum(f12) * f14 * ((float) Math.pow((float) Math.max(0.0d, (Math.abs(f12) * 27.13d) / (400.0d - Math.abs(f12))), 2.380952380952381d));
        float fSignum3 = Math.signum(f13) * f14 * ((float) Math.pow((float) Math.max(0.0d, (Math.abs(f13) * 27.13d) / (400.0d - Math.abs(f13))), 2.380952380952381d));
        float[] fArr = ld1Var.f57893a6;
        float f15 = fSignum / fArr[0];
        float f16 = fSignum2 / fArr[1];
        float f17 = fSignum3 / fArr[2];
        float[][] fArr2 = b81.f45730a1;
        float[] fArr3 = fArr2[0];
        float f18 = (fArr3[2] * f17) + (fArr3[1] * f16) + (fArr3[0] * f15);
        float[] fArr4 = fArr2[1];
        float f19 = (fArr4[2] * f17) + (fArr4[1] * f16) + (fArr4[0] * f15);
        float[] fArr5 = fArr2[2];
        return AbstractC0724jn.m213330a0(f18, f19, (f17 * fArr5[2]) + (f16 * fArr5[1]) + (f15 * fArr5[0]));
    }
}
