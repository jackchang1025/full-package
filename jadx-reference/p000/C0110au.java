package p000;

import java.util.Arrays;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: au */
/* loaded from: classes.dex */
public final class C0110au extends b81 {

    /* renamed from: c2 */
    public final double[] f45641c2;

    /* renamed from: c3 */
    public final C0108at[] f45642c3;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:16:0x002d  */
    /* JADX WARN: Type inference failed for: r0v0, types: [au, java.lang.Object] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public C0110au(int[] iArr, double[] dArr, double[][] dArr2) {
        int i;
        double[] dArr3;
        double[] dArr4 = dArr;
        ?? obj = new Object();
        obj.f45641c2 = dArr4;
        int i2 = 1;
        obj.f45642c3 = new C0108at[dArr4.length - 1];
        boolean z = false;
        int i3 = 0;
        int i4 = 1;
        int i5 = 1;
        C0110au c0110au = obj;
        while (true) {
            C0108at[] c0108atArr = c0110au.f45642c3;
            if (i3 >= c0108atArr.length) {
                return;
            }
            int i6 = iArr[i3];
            if (i6 == 0) {
                i5 = 3;
            } else if (i6 == i2) {
                i4 = i2;
                i5 = i4;
            } else {
                if (i6 != 2) {
                    if (i6 == 3) {
                        if (i4 == i2) {
                        }
                        i5 = i4;
                    }
                }
                i4 = 2;
                i5 = i4;
            }
            double d = dArr4[i3];
            int i7 = i3 + 1;
            double d2 = dArr4[i7];
            double[] dArr5 = dArr2[i3];
            double d3 = dArr5[z ? 1 : 0];
            boolean z2 = i2;
            int i8 = i3;
            double d4 = dArr5[z2 ? 1 : 0];
            double[] dArr6 = dArr2[i7];
            double d5 = dArr6[z ? 1 : 0];
            double d6 = dArr6[z2 ? 1 : 0];
            C0108at c0108at = new C0108at();
            c0108at.f45639b7 = z;
            z = i5 == z2 ? z2 ? 1 : 0 : z;
            c0108at.f45638b6 = z;
            c0108at.f45624a2 = d;
            c0108at.f45625a3 = d2;
            double d7 = d2 - d;
            double d8 = 1.0d / d7;
            c0108at.f45630a8 = d8;
            if (3 == i5) {
                c0108at.f45639b7 = z2;
            }
            double d9 = d5 - d3;
            int i9 = i4;
            int i10 = i5;
            double d10 = d6 - d4;
            if (c0108at.f45639b7 || Math.abs(d9) < 0.001d || Math.abs(d10) < 0.001d) {
                i = 1;
                c0108at.f45639b7 = true;
                c0108at.f45626a4 = d3;
                c0108at.f45627a5 = d5;
                c0108at.f45628a6 = d4;
                c0108at.f45629a7 = d6;
                double dHypot = Math.hypot(d10, d9);
                c0108at.f45623a1 = dHypot;
                c0108at.f45635b3 = dHypot * d8;
                c0108at.f45633b1 = d9 / d7;
                c0108at.f45634b2 = d10 / d7;
            } else {
                double[] dArr7 = new double[101];
                c0108at.f45622a0 = dArr7;
                c0108at.f45631a9 = (z ? -1 : 1) * d9;
                c0108at.f45632b0 = d10 * (z ? 1 : -1);
                c0108at.f45633b1 = z ? d5 : d3;
                c0108at.f45634b2 = z ? d4 : d6;
                double d11 = d4 - d6;
                int i11 = 0;
                double dHypot2 = 0.0d;
                double d12 = 0.0d;
                double d13 = 0.0d;
                while (true) {
                    dArr3 = C0108at.f45621b8;
                    if (i11 >= 91) {
                        break;
                    }
                    double d14 = d11;
                    int i12 = i11;
                    double radians = Math.toRadians((i11 * 90.0d) / 90);
                    double dSin = Math.sin(radians) * d9;
                    double dCos = Math.cos(radians) * d14;
                    if (i12 > 0) {
                        dHypot2 += Math.hypot(dSin - d12, dCos - d13);
                        dArr3[i12] = dHypot2;
                    }
                    d13 = dCos;
                    d12 = dSin;
                    i11 = i12 + 1;
                    d11 = d14;
                }
                c0108at.f45623a1 = dHypot2;
                for (int i13 = 0; i13 < 91; i13++) {
                    dArr3[i13] = dArr3[i13] / dHypot2;
                }
                for (int i14 = 0; i14 < 101; i14++) {
                    double d15 = i14 / 100;
                    int iBinarySearch = Arrays.binarySearch(dArr3, d15);
                    if (iBinarySearch >= 0) {
                        dArr7[i14] = iBinarySearch / 90;
                    } else if (iBinarySearch == -1) {
                        dArr7[i14] = 0.0d;
                    } else {
                        int i15 = -iBinarySearch;
                        int i16 = i15 - 2;
                        double d16 = dArr3[i16];
                        dArr7[i14] = (((d15 - d16) / (dArr3[i15 - 1] - d16)) + i16) / 90;
                    }
                }
                c0108at.f45635b3 = c0108at.f45623a1 * c0108at.f45630a8;
                i = 1;
            }
            c0108atArr[i8] = c0108at;
            c0110au = this;
            dArr4 = dArr;
            i2 = i;
            i4 = i9;
            i3 = i7;
            i5 = i10;
            z = false;
        }
    }

    @Override // p000.b81
    /* renamed from: c0 */
    public final double mo210516c0(double d) {
        C0108at[] c0108atArr = this.f45642c3;
        C0108at c0108at = c0108atArr[0];
        double d2 = c0108at.f45624a2;
        if (d < d2) {
            double d3 = d - d2;
            if (c0108at.f45639b7) {
                return (d3 * c0108atArr[0].f45633b1) + c0108at.m210511a2(d2);
            }
            c0108at.m210515a6(d2);
            return (c0108atArr[0].m210509a0() * d3) + c0108atArr[0].m210513a4();
        }
        if (d > c0108atArr[c0108atArr.length - 1].f45625a3) {
            double d4 = c0108atArr[c0108atArr.length - 1].f45625a3;
            double d5 = d - d4;
            int length = c0108atArr.length - 1;
            return (d5 * c0108atArr[length].f45633b1) + c0108atArr[length].m210511a2(d4);
        }
        for (int i = 0; i < c0108atArr.length; i++) {
            C0108at c0108at2 = c0108atArr[i];
            if (d <= c0108at2.f45625a3) {
                if (c0108at2.f45639b7) {
                    return c0108at2.m210511a2(d);
                }
                c0108at2.m210515a6(d);
                return c0108atArr[i].m210513a4();
            }
        }
        return Double.NaN;
    }

    @Override // p000.b81
    /* renamed from: c1 */
    public final void mo210517c1(double d, double[] dArr) {
        C0108at[] c0108atArr = this.f45642c3;
        C0108at c0108at = c0108atArr[0];
        double d2 = c0108at.f45624a2;
        if (d < d2) {
            double d3 = d - d2;
            if (c0108at.f45639b7) {
                double dM210511a2 = c0108at.m210511a2(d2);
                C0108at c0108at2 = c0108atArr[0];
                dArr[0] = (c0108at2.f45633b1 * d3) + dM210511a2;
                dArr[1] = (d3 * c0108atArr[0].f45634b2) + c0108at2.m210512a3(d2);
                return;
            }
            c0108at.m210515a6(d2);
            dArr[0] = (c0108atArr[0].m210509a0() * d3) + c0108atArr[0].m210513a4();
            dArr[1] = (c0108atArr[0].m210510a1() * d3) + c0108atArr[0].m210514a5();
            return;
        }
        if (d <= c0108atArr[c0108atArr.length - 1].f45625a3) {
            for (int i = 0; i < c0108atArr.length; i++) {
                C0108at c0108at3 = c0108atArr[i];
                if (d <= c0108at3.f45625a3) {
                    if (c0108at3.f45639b7) {
                        dArr[0] = c0108at3.m210511a2(d);
                        dArr[1] = c0108atArr[i].m210512a3(d);
                        return;
                    } else {
                        c0108at3.m210515a6(d);
                        dArr[0] = c0108atArr[i].m210513a4();
                        dArr[1] = c0108atArr[i].m210514a5();
                        return;
                    }
                }
            }
            return;
        }
        double d4 = c0108atArr[c0108atArr.length - 1].f45625a3;
        double d5 = d - d4;
        int length = c0108atArr.length - 1;
        C0108at c0108at4 = c0108atArr[length];
        if (c0108at4.f45639b7) {
            double dM210511a22 = c0108at4.m210511a2(d4);
            C0108at c0108at5 = c0108atArr[length];
            dArr[0] = (c0108at5.f45633b1 * d5) + dM210511a22;
            dArr[1] = (d5 * c0108atArr[length].f45634b2) + c0108at5.m210512a3(d4);
            return;
        }
        c0108at4.m210515a6(d);
        dArr[0] = (c0108atArr[length].m210509a0() * d5) + c0108atArr[length].m210513a4();
        dArr[1] = (c0108atArr[length].m210510a1() * d5) + c0108atArr[length].m210514a5();
    }

    @Override // p000.b81
    /* renamed from: c2 */
    public final void mo210518c2(double d, float[] fArr) {
        C0108at[] c0108atArr = this.f45642c3;
        C0108at c0108at = c0108atArr[0];
        double d2 = c0108at.f45624a2;
        if (d < d2) {
            double d3 = d - d2;
            if (c0108at.f45639b7) {
                double dM210511a2 = c0108at.m210511a2(d2);
                C0108at c0108at2 = c0108atArr[0];
                fArr[0] = (float) ((c0108at2.f45633b1 * d3) + dM210511a2);
                fArr[1] = (float) ((d3 * c0108atArr[0].f45634b2) + c0108at2.m210512a3(d2));
                return;
            }
            c0108at.m210515a6(d2);
            fArr[0] = (float) ((c0108atArr[0].m210509a0() * d3) + c0108atArr[0].m210513a4());
            fArr[1] = (float) ((c0108atArr[0].m210510a1() * d3) + c0108atArr[0].m210514a5());
            return;
        }
        if (d <= c0108atArr[c0108atArr.length - 1].f45625a3) {
            for (int i = 0; i < c0108atArr.length; i++) {
                C0108at c0108at3 = c0108atArr[i];
                if (d <= c0108at3.f45625a3) {
                    if (c0108at3.f45639b7) {
                        fArr[0] = (float) c0108at3.m210511a2(d);
                        fArr[1] = (float) c0108atArr[i].m210512a3(d);
                        return;
                    } else {
                        c0108at3.m210515a6(d);
                        fArr[0] = (float) c0108atArr[i].m210513a4();
                        fArr[1] = (float) c0108atArr[i].m210514a5();
                        return;
                    }
                }
            }
            return;
        }
        double d4 = c0108atArr[c0108atArr.length - 1].f45625a3;
        double d5 = d - d4;
        int length = c0108atArr.length - 1;
        C0108at c0108at4 = c0108atArr[length];
        if (!c0108at4.f45639b7) {
            c0108at4.m210515a6(d);
            fArr[0] = (float) c0108atArr[length].m210513a4();
            fArr[1] = (float) c0108atArr[length].m210514a5();
        } else {
            double dM210511a22 = c0108at4.m210511a2(d4);
            C0108at c0108at5 = c0108atArr[length];
            fArr[0] = (float) ((c0108at5.f45633b1 * d5) + dM210511a22);
            fArr[1] = (float) ((d5 * c0108atArr[length].f45634b2) + c0108at5.m210512a3(d4));
        }
    }

    @Override // p000.b81
    /* renamed from: c3 */
    public final double mo210519c3(double d) {
        C0108at[] c0108atArr = this.f45642c3;
        double d2 = c0108atArr[0].f45624a2;
        if (d < d2) {
            d = d2;
        }
        if (d > c0108atArr[c0108atArr.length - 1].f45625a3) {
            d = c0108atArr[c0108atArr.length - 1].f45625a3;
        }
        for (int i = 0; i < c0108atArr.length; i++) {
            C0108at c0108at = c0108atArr[i];
            if (d <= c0108at.f45625a3) {
                if (c0108at.f45639b7) {
                    return c0108at.f45633b1;
                }
                c0108at.m210515a6(d);
                return c0108atArr[i].m210509a0();
            }
        }
        return Double.NaN;
    }

    @Override // p000.b81
    /* renamed from: c4 */
    public final void mo210520c4(double d, double[] dArr) {
        C0108at[] c0108atArr = this.f45642c3;
        double d2 = c0108atArr[0].f45624a2;
        if (d < d2) {
            d = d2;
        } else if (d > c0108atArr[c0108atArr.length - 1].f45625a3) {
            d = c0108atArr[c0108atArr.length - 1].f45625a3;
        }
        for (int i = 0; i < c0108atArr.length; i++) {
            C0108at c0108at = c0108atArr[i];
            if (d <= c0108at.f45625a3) {
                if (c0108at.f45639b7) {
                    dArr[0] = c0108at.f45633b1;
                    dArr[1] = c0108at.f45634b2;
                    return;
                } else {
                    c0108at.m210515a6(d);
                    dArr[0] = c0108atArr[i].m210509a0();
                    dArr[1] = c0108atArr[i].m210510a1();
                    return;
                }
            }
        }
    }

    @Override // p000.b81
    /* renamed from: c5 */
    public final double[] mo210521c5() {
        return this.f45641c2;
    }
}
