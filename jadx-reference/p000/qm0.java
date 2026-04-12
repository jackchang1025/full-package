package p000;

import android.graphics.Path;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class qm0 {

    /* renamed from: a0 */
    public char f59534a0;

    /* renamed from: a1 */
    public float[] f59535a1;

    /* renamed from: a0 */
    public static void m214400a0(Path path, float f, float f2, float f3, float f4, float f5, float f6, float f7, boolean z, boolean z2) {
        double d;
        double d2;
        double radians = Math.toRadians(f7);
        double dCos = Math.cos(radians);
        double dSin = Math.sin(radians);
        double d3 = f;
        double d4 = f2;
        double d5 = f5;
        double d6 = ((d4 * dSin) + (d3 * dCos)) / d5;
        double d7 = f6;
        double d8 = ((d4 * dCos) + ((-f) * dSin)) / d7;
        double d9 = f4;
        double d10 = ((d9 * dSin) + (f3 * dCos)) / d5;
        double d11 = ((d9 * dCos) + ((-f3) * dSin)) / d7;
        double d12 = d6 - d10;
        double d13 = d8 - d11;
        double d14 = (d6 + d10) / 2.0d;
        double d15 = (d8 + d11) / 2.0d;
        double d16 = (d13 * d13) + (d12 * d12);
        if (d16 == 0.0d) {
            return;
        }
        double d17 = (1.0d / d16) - 0.25d;
        if (d17 < 0.0d) {
            float fSqrt = (float) (Math.sqrt(d16) / 1.99999d);
            m214400a0(path, f, f2, f3, f4, f5 * fSqrt, fSqrt * f6, f7, z, z2);
            return;
        }
        double dSqrt = Math.sqrt(d17);
        double d18 = d12 * dSqrt;
        double d19 = dSqrt * d13;
        if (z == z2) {
            d = d14 - d19;
            d2 = d15 + d18;
        } else {
            d = d14 + d19;
            d2 = d15 - d18;
        }
        double dAtan2 = Math.atan2(d8 - d2, d6 - d);
        double dAtan22 = Math.atan2(d11 - d2, d10 - d) - dAtan2;
        if (z2 != (dAtan22 >= 0.0d)) {
            dAtan22 = dAtan22 > 0.0d ? dAtan22 - 6.283185307179586d : dAtan22 + 6.283185307179586d;
        }
        double d20 = d * d5;
        double d21 = d2 * d7;
        double d22 = (d20 * dCos) - (d21 * dSin);
        double d23 = (d21 * dCos) + (d20 * dSin);
        int iCeil = (int) Math.ceil(Math.abs((dAtan22 * 4.0d) / 3.141592653589793d));
        double dCos2 = Math.cos(radians);
        double dSin2 = Math.sin(radians);
        double dCos3 = Math.cos(dAtan2);
        double dSin3 = Math.sin(dAtan2);
        double d24 = -d5;
        double d25 = d24 * dCos2;
        double d26 = d7 * dSin2;
        double d27 = (d25 * dSin3) - (d26 * dCos3);
        double d28 = d24 * dSin2;
        double d29 = d7 * dCos2;
        double d30 = dAtan22 / iCeil;
        double d31 = (dCos3 * d29) + (dSin3 * d28);
        int i = 0;
        double d32 = d3;
        double d33 = d4;
        double d34 = dAtan2;
        while (i < iCeil) {
            double d35 = d34 + d30;
            double dSin4 = Math.sin(d35);
            double dCos4 = Math.cos(d35);
            double d36 = d30;
            double d37 = (((d5 * dCos2) * dCos4) + d22) - (d26 * dSin4);
            double d38 = d22;
            double d39 = (d29 * dSin4) + (d5 * dSin2 * dCos4) + d23;
            double d40 = (d25 * dSin4) - (d26 * dCos4);
            double d41 = (dCos4 * d29) + (dSin4 * d28);
            double d42 = d35 - d34;
            double dTan = Math.tan(d42 / 2.0d);
            double dSqrt2 = ((Math.sqrt(((dTan * 3.0d) * dTan) + 4.0d) - 1.0d) * Math.sin(d42)) / 3.0d;
            path.rLineTo(0.0f, 0.0f);
            path.cubicTo((float) ((d27 * dSqrt2) + d32), (float) ((d31 * dSqrt2) + d33), (float) (d37 - (dSqrt2 * d40)), (float) (d39 - (dSqrt2 * d41)), (float) d37, (float) d39);
            i++;
            d33 = d39;
            iCeil = iCeil;
            d28 = d28;
            dCos2 = dCos2;
            d34 = d35;
            d31 = d41;
            d27 = d40;
            d22 = d38;
            d32 = d37;
            d30 = d36;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: a1 */
    public static void m214401a1(qm0[] qm0VarArr, Path path) {
        int i;
        int i2;
        float[] fArr;
        int i3;
        char c;
        float f;
        float f2;
        float[] fArr2;
        boolean z;
        float f3;
        float f4;
        float f5;
        float f6;
        float f7;
        float f8;
        float f9;
        float f10;
        float f11;
        Path path2 = path;
        int i4 = 6;
        float[] fArr3 = new float[6];
        int i5 = 0;
        char c2 = 'm';
        int i6 = 0;
        while (i6 < qm0VarArr.length) {
            qm0 qm0Var = qm0VarArr[i6];
            char c3 = qm0Var.f59534a0;
            float[] fArr4 = qm0Var.f59535a1;
            float f12 = fArr3[i5];
            float f13 = fArr3[1];
            float f14 = fArr3[2];
            float f15 = fArr3[3];
            float f16 = fArr3[4];
            float f17 = fArr3[5];
            switch (c3) {
                case 'A':
                case 'a':
                    i = 7;
                    break;
                case 'C':
                case 'c':
                    i = i4;
                    break;
                case 'H':
                case 'V':
                case 'h':
                case 'v':
                    i = 1;
                    break;
                case 'Q':
                case 'S':
                case 'q':
                case 's':
                    i = 4;
                    break;
                case 'Z':
                case 'z':
                    path2.close();
                    path2.moveTo(f16, f17);
                    f12 = f16;
                    f14 = f12;
                    f13 = f17;
                    f15 = f13;
                default:
                    i = 2;
                    break;
            }
            float f18 = f13;
            float f19 = f16;
            float f20 = f17;
            float f21 = f12;
            int i7 = i5;
            while (i7 < fArr4.length) {
                if (c3 == 'A') {
                    i2 = i7;
                    fArr = fArr4;
                    i3 = i5;
                    float f22 = f18;
                    c = c3;
                    int i8 = i2 + 5;
                    int i9 = i2 + 6;
                    m214400a0(path, f21, f22, fArr[i8], fArr[i9], fArr[i2], fArr[i2 + 1], fArr[i2 + 2], fArr[i2 + 3] != 0.0f ? 1 : i3, fArr[i2 + 4] != 0.0f ? 1 : i3);
                    f14 = fArr[i8];
                    f21 = f14;
                    f = fArr[i9];
                    f15 = f;
                } else if (c3 == 'C') {
                    i2 = i7;
                    c = c3;
                    fArr = fArr4;
                    i3 = i5;
                    int i10 = i2 + 2;
                    int i11 = i2 + 3;
                    int i12 = i2 + 4;
                    int i13 = i2 + 5;
                    path2.cubicTo(fArr[i2], fArr[i2 + 1], fArr[i10], fArr[i11], fArr[i12], fArr[i13]);
                    float f23 = fArr[i12];
                    float f24 = fArr[i13];
                    f21 = f23;
                    f14 = fArr[i10];
                    f15 = fArr[i11];
                    f = f24;
                } else if (c3 != 'H') {
                    if (c3 != 'Q') {
                        i3 = i5;
                        if (c3 == 'V') {
                            i2 = i7;
                            c = c3;
                            fArr = fArr4;
                            path2.lineTo(f21, fArr[i2]);
                            f = fArr[i2];
                        } else if (c3 != 'a') {
                            if (c3 == 'c') {
                                i2 = i7;
                                int i14 = i2 + 2;
                                int i15 = i2 + 3;
                                int i16 = i2 + 4;
                                int i17 = i2 + 5;
                                path2.rCubicTo(fArr4[i2], fArr4[i2 + 1], fArr4[i14], fArr4[i15], fArr4[i16], fArr4[i17]);
                                float f25 = fArr4[i14] + f21;
                                float f26 = f18 + fArr4[i15];
                                f21 += fArr4[i16];
                                f18 += fArr4[i17];
                                f14 = f25;
                                f15 = f26;
                            } else if (c3 != 'h') {
                                if (c3 != 'q') {
                                    if (c3 != 'v') {
                                        if (c3 == 'L') {
                                            i2 = i7;
                                            int i18 = i2 + 1;
                                            path2.lineTo(fArr4[i2], fArr4[i18]);
                                            f7 = fArr4[i2];
                                            f = fArr4[i18];
                                        } else if (c3 == 'M') {
                                            i2 = i7;
                                            f7 = fArr4[i2];
                                            f = fArr4[i2 + 1];
                                            if (i2 > 0) {
                                                path2.lineTo(f7, f);
                                            } else {
                                                path2.moveTo(f7, f);
                                                f21 = f7;
                                                f19 = f21;
                                                f20 = f;
                                                c = c3;
                                                fArr = fArr4;
                                            }
                                        } else if (c3 == 'S') {
                                            i2 = i7;
                                            if (c2 == 'c' || c2 == 's' || c2 == 'C' || c2 == 'S') {
                                                f21 = (f21 * 2.0f) - f14;
                                                f18 = (f18 * 2.0f) - f15;
                                            }
                                            float f27 = f21;
                                            int i19 = i2 + 1;
                                            int i20 = i2 + 2;
                                            int i21 = i2 + 3;
                                            path2.cubicTo(f27, f18, fArr4[i2], fArr4[i19], fArr4[i20], fArr4[i21]);
                                            f2 = fArr4[i2];
                                            f15 = fArr4[i19];
                                            f21 = fArr4[i20];
                                            f = fArr4[i21];
                                            c = c3;
                                            fArr = fArr4;
                                        } else if (c3 == 'T') {
                                            i2 = i7;
                                            if (c2 == 'q' || c2 == 't' || c2 == 'Q' || c2 == 'T') {
                                                f21 = (f21 * 2.0f) - f14;
                                                f18 = (f18 * 2.0f) - f15;
                                            }
                                            float f28 = f18;
                                            int i22 = i2 + 1;
                                            path2.quadTo(f21, f28, fArr4[i2], fArr4[i22]);
                                            f15 = f28;
                                            c = c3;
                                            fArr = fArr4;
                                            f14 = f21;
                                            f21 = fArr4[i2];
                                            f = fArr4[i22];
                                        } else if (c3 == 'l') {
                                            i2 = i7;
                                            int i23 = i2 + 1;
                                            path2.rLineTo(fArr4[i2], fArr4[i23]);
                                            f21 += fArr4[i2];
                                            f6 = fArr4[i23];
                                        } else if (c3 == 'm') {
                                            i2 = i7;
                                            float f29 = fArr4[i2];
                                            f21 += f29;
                                            float f30 = fArr4[i2 + 1];
                                            f18 += f30;
                                            if (i2 > 0) {
                                                path2.rLineTo(f29, f30);
                                            } else {
                                                path2.rMoveTo(f29, f30);
                                                fArr = fArr4;
                                                f19 = f21;
                                                f = f18;
                                                f20 = f;
                                                c = c3;
                                            }
                                        } else if (c3 == 's') {
                                            if (c2 == 'c' || c2 == 's' || c2 == 'C' || c2 == 'S') {
                                                f8 = f18 - f15;
                                                f9 = f21 - f14;
                                            } else {
                                                f9 = 0.0f;
                                                f8 = 0.0f;
                                            }
                                            int i24 = i7 + 1;
                                            int i25 = i7 + 2;
                                            int i26 = i7 + 3;
                                            i2 = i7;
                                            path2.rCubicTo(f9, f8, fArr4[i7], fArr4[i24], fArr4[i25], fArr4[i26]);
                                            f3 = fArr4[i2] + f21;
                                            f4 = f18 + fArr4[i24];
                                            f21 += fArr4[i25];
                                            f5 = fArr4[i26];
                                        } else if (c3 != 't') {
                                            i2 = i7;
                                        } else {
                                            if (c2 == 'q' || c2 == 't' || c2 == 'Q' || c2 == 'T') {
                                                f10 = f21 - f14;
                                                f11 = f18 - f15;
                                            } else {
                                                f11 = 0.0f;
                                                f10 = 0.0f;
                                            }
                                            int i27 = i7 + 1;
                                            path2.rQuadTo(f10, f11, fArr4[i7], fArr4[i27]);
                                            float f31 = f10 + f21;
                                            float f32 = f18 + f11;
                                            f21 += fArr4[i7];
                                            f18 += fArr4[i27];
                                            f15 = f32;
                                            i2 = i7;
                                            f14 = f31;
                                        }
                                        f21 = f7;
                                        c = c3;
                                        fArr = fArr4;
                                    } else {
                                        i2 = i7;
                                        path2.rLineTo(0.0f, fArr4[i2]);
                                        f6 = fArr4[i2];
                                    }
                                    f18 += f6;
                                } else {
                                    i2 = i7;
                                    int i28 = i2 + 1;
                                    int i29 = i2 + 2;
                                    int i30 = i2 + 3;
                                    path2.rQuadTo(fArr4[i2], fArr4[i28], fArr4[i29], fArr4[i30]);
                                    f3 = fArr4[i2] + f21;
                                    f4 = f18 + fArr4[i28];
                                    f21 += fArr4[i29];
                                    f5 = fArr4[i30];
                                }
                                f18 += f5;
                                f14 = f3;
                                f15 = f4;
                            } else {
                                i2 = i7;
                                path2.rLineTo(fArr4[i2], 0.0f);
                                f21 += fArr4[i2];
                            }
                            fArr = fArr4;
                            f = f18;
                            c = c3;
                        } else {
                            i2 = i7;
                            int i31 = i2 + 5;
                            float f33 = fArr4[i31] + f21;
                            int i32 = i2 + 6;
                            float f34 = fArr4[i32] + f18;
                            float f35 = fArr4[i2];
                            float f36 = fArr4[i2 + 1];
                            float f37 = fArr4[i2 + 2];
                            if (fArr4[i2 + 3] != 0.0f) {
                                fArr2 = fArr4;
                                z = 1;
                            } else {
                                fArr2 = fArr4;
                                z = i3;
                            }
                            float f38 = fArr2[i2 + 4];
                            fArr = fArr2;
                            float f39 = f21;
                            boolean z2 = f38 != 0.0f ? 1 : i3;
                            float f40 = f18;
                            c = c3;
                            m214400a0(path, f39, f40, f33, f34, f35, f36, f37, z, z2);
                            f21 = f39 + fArr[i31];
                            f = fArr[i32] + f40;
                            f15 = f;
                            f14 = f21;
                        }
                    } else {
                        i2 = i7;
                        c = c3;
                        fArr = fArr4;
                        i3 = i5;
                        int i33 = i2 + 1;
                        int i34 = i2 + 2;
                        int i35 = i2 + 3;
                        path2.quadTo(fArr[i2], fArr[i33], fArr[i34], fArr[i35]);
                        f2 = fArr[i2];
                        f15 = fArr[i33];
                        f21 = fArr[i34];
                        f = fArr[i35];
                    }
                    f14 = f2;
                } else {
                    i2 = i7;
                    fArr = fArr4;
                    i3 = i5;
                    f = f18;
                    c = c3;
                    path2.lineTo(fArr[i2], f);
                    f21 = fArr[i2];
                }
                c2 = c;
                c3 = c2;
                i5 = i3;
                fArr4 = fArr;
                f18 = f;
                i7 = i2 + i;
                path2 = path;
            }
            fArr3[i5] = f21;
            fArr3[1] = f18;
            fArr3[2] = f14;
            fArr3[3] = f15;
            fArr3[4] = f19;
            fArr3[5] = f20;
            c2 = qm0VarArr[i6].f59534a0;
            i6++;
            path2 = path;
            i4 = 6;
        }
    }
}
