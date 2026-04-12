package p000;

import androidx.constraintlayout.core.SolverVariable$Type;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: be */
/* loaded from: classes.dex */
public class C0131be {

    /* renamed from: a3 */
    public final C0128bb f45835a3;

    /* renamed from: a0 */
    public e11 f45832a0 = null;

    /* renamed from: a1 */
    public float f45833a1 = 0.0f;

    /* renamed from: a2 */
    public final ArrayList f45834a2 = new ArrayList();

    /* renamed from: a4 */
    public boolean f45836a4 = false;

    public C0131be(zg1 zg1Var) {
        this.f45835a3 = new C0128bb(this, zg1Var);
    }

    /* renamed from: a0 */
    public final void m210674a0(ab0 ab0Var, int i) {
        this.f45835a3.m210629a6(ab0Var.m209768a9(i), 1.0f);
        this.f45835a3.m210629a6(ab0Var.m209768a9(i), -1.0f);
    }

    /* renamed from: a1 */
    public final void m210675a1(e11 e11Var, e11 e11Var2, e11 e11Var3, int i) {
        boolean z = false;
        if (i != 0) {
            if (i < 0) {
                i *= -1;
                z = true;
            }
            this.f45833a1 = i;
        }
        if (z) {
            this.f45835a3.m210629a6(e11Var, 1.0f);
            this.f45835a3.m210629a6(e11Var2, -1.0f);
            this.f45835a3.m210629a6(e11Var3, -1.0f);
        } else {
            this.f45835a3.m210629a6(e11Var, -1.0f);
            this.f45835a3.m210629a6(e11Var2, 1.0f);
            this.f45835a3.m210629a6(e11Var3, 1.0f);
        }
    }

    /* renamed from: a2 */
    public final void m210676a2(e11 e11Var, e11 e11Var2, e11 e11Var3, int i) {
        boolean z = false;
        if (i != 0) {
            if (i < 0) {
                i *= -1;
                z = true;
            }
            this.f45833a1 = i;
        }
        if (z) {
            this.f45835a3.m210629a6(e11Var, 1.0f);
            this.f45835a3.m210629a6(e11Var2, -1.0f);
            this.f45835a3.m210629a6(e11Var3, 1.0f);
        } else {
            this.f45835a3.m210629a6(e11Var, -1.0f);
            this.f45835a3.m210629a6(e11Var2, 1.0f);
            this.f45835a3.m210629a6(e11Var3, -1.0f);
        }
    }

    /* renamed from: a3 */
    public e11 mo210677a3(boolean[] zArr) {
        return m210679a5(zArr, null);
    }

    /* renamed from: a4 */
    public boolean mo210678a4() {
        return this.f45832a0 == null && this.f45833a1 == 0.0f && this.f45835a3.m210626a3() == 0;
    }

    /* renamed from: a5 */
    public final e11 m210679a5(boolean[] zArr, e11 e11Var) {
        SolverVariable$Type solverVariable$Type;
        int iM210626a3 = this.f45835a3.m210626a3();
        e11 e11Var2 = null;
        float f = 0.0f;
        for (int i = 0; i < iM210626a3; i++) {
            float fM210628a5 = this.f45835a3.m210628a5(i);
            if (fM210628a5 < 0.0f) {
                e11 e11VarM210627a4 = this.f45835a3.m210627a4(i);
                if ((zArr == null || !zArr[e11VarM210627a4.f55897a1]) && e11VarM210627a4 != e11Var && (((solverVariable$Type = e11VarM210627a4.f55904a8) == SolverVariable$Type.f44411a1 || solverVariable$Type == SolverVariable$Type.f44412a2) && fM210628a5 < f)) {
                    f = fM210628a5;
                    e11Var2 = e11VarM210627a4;
                }
            }
        }
        return e11Var2;
    }

    /* renamed from: a6 */
    public final void m210680a6(e11 e11Var) {
        e11 e11Var2 = this.f45832a0;
        if (e11Var2 != null) {
            this.f45835a3.m210629a6(e11Var2, -1.0f);
            this.f45832a0.f55898a2 = -1;
            this.f45832a0 = null;
        }
        float fM210630a7 = this.f45835a3.m210630a7(e11Var, true) * (-1.0f);
        this.f45832a0 = e11Var;
        if (fM210630a7 == 1.0f) {
            return;
        }
        this.f45833a1 /= fM210630a7;
        C0128bb c0128bb = this.f45835a3;
        int i = c0128bb.f45775a7;
        for (int i2 = 0; i != -1 && i2 < c0128bb.f45768a0; i2++) {
            float[] fArr = c0128bb.f45774a6;
            fArr[i] = fArr[i] / fM210630a7;
            i = c0128bb.f45773a5[i];
        }
    }

    /* renamed from: a7 */
    public final void m210681a7(ab0 ab0Var, e11 e11Var, boolean z) {
        if (e11Var.f55901a5) {
            float fM210625a2 = this.f45835a3.m210625a2(e11Var);
            this.f45833a1 = (e11Var.f55900a4 * fM210625a2) + this.f45833a1;
            this.f45835a3.m210630a7(e11Var, z);
            if (z) {
                e11Var.m212650a1(this);
            }
            if (this.f45835a3.m210626a3() == 0) {
                this.f45836a4 = true;
                ab0Var.f43592a0 = true;
            }
        }
    }

    /* renamed from: a8 */
    public void mo210682a8(ab0 ab0Var, C0131be c0131be, boolean z) {
        C0128bb c0128bb = this.f45835a3;
        c0128bb.getClass();
        float fM210625a2 = c0128bb.m210625a2(c0131be.f45832a0);
        c0128bb.m210630a7(c0131be.f45832a0, z);
        C0128bb c0128bb2 = c0131be.f45835a3;
        int iM210626a3 = c0128bb2.m210626a3();
        for (int i = 0; i < iM210626a3; i++) {
            e11 e11VarM210627a4 = c0128bb2.m210627a4(i);
            c0128bb.m210623a0(e11VarM210627a4, c0128bb2.m210625a2(e11VarM210627a4) * fM210625a2, z);
        }
        this.f45833a1 = (c0131be.f45833a1 * fM210625a2) + this.f45833a1;
        if (z) {
            c0131be.f45832a0.m212650a1(this);
        }
        if (this.f45832a0 == null || this.f45835a3.m210626a3() != 0) {
            return;
        }
        this.f45836a4 = true;
        ab0Var.f43592a0 = true;
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0081  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String toString() {
        boolean z;
        String strM32b3 = AbstractC0003a2.m32b3(this.f45832a0 == null ? "0" : "" + this.f45832a0, " = ");
        if (this.f45833a1 != 0.0f) {
            StringBuilder sbM37b8 = AbstractC0003a2.m37b8(strM32b3);
            sbM37b8.append(this.f45833a1);
            strM32b3 = sbM37b8.toString();
            z = true;
        } else {
            z = false;
        }
        int iM210626a3 = this.f45835a3.m210626a3();
        for (int i = 0; i < iM210626a3; i++) {
            e11 e11VarM210627a4 = this.f45835a3.m210627a4(i);
            if (e11VarM210627a4 != null) {
                float fM210628a5 = this.f45835a3.m210628a5(i);
                if (fM210628a5 != 0.0f) {
                    String string = e11VarM210627a4.toString();
                    if (!z) {
                        if (fM210628a5 < 0.0f) {
                            strM32b3 = AbstractC0003a2.m32b3(strM32b3, "- ");
                            fM210628a5 *= -1.0f;
                        }
                        strM32b3 = fM210628a5 == 1.0f ? AbstractC0003a2.m32b3(strM32b3, string) : strM32b3 + fM210628a5 + " " + string;
                        z = true;
                    } else if (fM210628a5 > 0.0f) {
                        strM32b3 = AbstractC0003a2.m32b3(strM32b3, " + ");
                        if (fM210628a5 == 1.0f) {
                        }
                        z = true;
                    } else {
                        strM32b3 = AbstractC0003a2.m32b3(strM32b3, " - ");
                        fM210628a5 *= -1.0f;
                        if (fM210628a5 == 1.0f) {
                        }
                        z = true;
                    }
                }
            }
        }
        return !z ? AbstractC0003a2.m32b3(strM32b3, "0.0") : strM32b3;
    }
}
