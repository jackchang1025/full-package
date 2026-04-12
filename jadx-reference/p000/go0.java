package p000;

import java.util.Arrays;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class go0 extends C0131be {

    /* renamed from: a5 */
    public e11[] f56545a5;

    /* renamed from: a6 */
    public e11[] f56546a6;

    /* renamed from: a7 */
    public int f56547a7;

    /* renamed from: a8 */
    public eo0 f56548a8;

    @Override // p000.C0131be
    /* renamed from: a3 */
    public final e11 mo210677a3(boolean[] zArr) {
        int i = -1;
        for (int i2 = 0; i2 < this.f56547a7; i2++) {
            e11[] e11VarArr = this.f56545a5;
            e11 e11Var = e11VarArr[i2];
            if (!zArr[e11Var.f55897a1]) {
                eo0 eo0Var = this.f56548a8;
                eo0Var.f56088a1 = e11Var;
                int i3 = 8;
                if (i == -1) {
                    while (i3 >= 0) {
                        float f = ((e11) eo0Var.f56088a1).f55903a7[i3];
                        if (f <= 0.0f) {
                            if (f < 0.0f) {
                                i = i2;
                                break;
                            }
                            i3--;
                        }
                    }
                } else {
                    e11 e11Var2 = e11VarArr[i];
                    while (true) {
                        if (i3 >= 0) {
                            float f2 = e11Var2.f55903a7[i3];
                            float f3 = ((e11) eo0Var.f56088a1).f55903a7[i3];
                            if (f3 == f2) {
                                i3--;
                            } else if (f3 < f2) {
                            }
                        }
                    }
                }
            }
        }
        if (i == -1) {
            return null;
        }
        return this.f56545a5[i];
    }

    @Override // p000.C0131be
    /* renamed from: a4 */
    public final boolean mo210678a4() {
        return this.f56547a7 == 0;
    }

    @Override // p000.C0131be
    /* renamed from: a8 */
    public final void mo210682a8(ab0 ab0Var, C0131be c0131be, boolean z) {
        e11 e11Var = c0131be.f45832a0;
        if (e11Var == null) {
            return;
        }
        float[] fArr = e11Var.f55903a7;
        C0128bb c0128bb = c0131be.f45835a3;
        int iM210626a3 = c0128bb.m210626a3();
        for (int i = 0; i < iM210626a3; i++) {
            e11 e11VarM210627a4 = c0128bb.m210627a4(i);
            float fM210628a5 = c0128bb.m210628a5(i);
            eo0 eo0Var = this.f56548a8;
            eo0Var.f56088a1 = e11VarM210627a4;
            if (e11VarM210627a4.f55896a0) {
                boolean z2 = true;
                for (int i2 = 0; i2 < 9; i2++) {
                    float[] fArr2 = ((e11) eo0Var.f56088a1).f55903a7;
                    float f = (fArr[i2] * fM210628a5) + fArr2[i2];
                    fArr2[i2] = f;
                    if (Math.abs(f) < 1.0E-4f) {
                        ((e11) eo0Var.f56088a1).f55903a7[i2] = 0.0f;
                    } else {
                        z2 = false;
                    }
                }
                if (z2) {
                    ((go0) eo0Var.f56089a2).m212977b0((e11) eo0Var.f56088a1);
                }
            } else {
                for (int i3 = 0; i3 < 9; i3++) {
                    float f2 = fArr[i3];
                    if (f2 != 0.0f) {
                        float f3 = f2 * fM210628a5;
                        if (Math.abs(f3) < 1.0E-4f) {
                            f3 = 0.0f;
                        }
                        ((e11) eo0Var.f56088a1).f55903a7[i3] = f3;
                    } else {
                        ((e11) eo0Var.f56088a1).f55903a7[i3] = 0.0f;
                    }
                }
                m212976a9(e11VarM210627a4);
            }
            this.f45833a1 = (c0131be.f45833a1 * fM210628a5) + this.f45833a1;
        }
        m212977b0(e11Var);
    }

    /* renamed from: a9 */
    public final void m212976a9(e11 e11Var) {
        int i;
        int i2 = this.f56547a7 + 1;
        e11[] e11VarArr = this.f56545a5;
        if (i2 > e11VarArr.length) {
            e11[] e11VarArr2 = (e11[]) Arrays.copyOf(e11VarArr, e11VarArr.length * 2);
            this.f56545a5 = e11VarArr2;
            this.f56546a6 = (e11[]) Arrays.copyOf(e11VarArr2, e11VarArr2.length * 2);
        }
        e11[] e11VarArr3 = this.f56545a5;
        int i3 = this.f56547a7;
        e11VarArr3[i3] = e11Var;
        int i4 = i3 + 1;
        this.f56547a7 = i4;
        if (i4 > 1 && e11VarArr3[i3].f55897a1 > e11Var.f55897a1) {
            int i5 = 0;
            while (true) {
                i = this.f56547a7;
                if (i5 >= i) {
                    break;
                }
                this.f56546a6[i5] = this.f56545a5[i5];
                i5++;
            }
            Arrays.sort(this.f56546a6, 0, i, new C1214s9(10));
            for (int i6 = 0; i6 < this.f56547a7; i6++) {
                this.f56545a5[i6] = this.f56546a6[i6];
            }
        }
        e11Var.f55896a0 = true;
        e11Var.m212649a0(this);
    }

    /* renamed from: b0 */
    public final void m212977b0(e11 e11Var) {
        int i = 0;
        while (i < this.f56547a7) {
            if (this.f56545a5[i] == e11Var) {
                while (true) {
                    int i2 = this.f56547a7;
                    if (i >= i2 - 1) {
                        this.f56547a7 = i2 - 1;
                        e11Var.f55896a0 = false;
                        return;
                    } else {
                        e11[] e11VarArr = this.f56545a5;
                        int i3 = i + 1;
                        e11VarArr[i] = e11VarArr[i3];
                        i = i3;
                    }
                }
            } else {
                i++;
            }
        }
    }

    @Override // p000.C0131be
    public final String toString() {
        eo0 eo0Var = this.f56548a8;
        String str = " goal -> (" + this.f45833a1 + ") : ";
        for (int i = 0; i < this.f56547a7; i++) {
            eo0Var.f56088a1 = this.f56545a5[i];
            str = str + eo0Var + " ";
        }
        return str;
    }
}
