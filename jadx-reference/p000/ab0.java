package p000;

import androidx.constraintlayout.core.SolverVariable$Type;
import java.util.ArrayList;
import java.util.Arrays;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class ab0 {

    /* renamed from: b5 */
    public static boolean f43590b5 = false;

    /* renamed from: b6 */
    public static int f43591b6 = 1000;

    /* renamed from: a2 */
    public final go0 f43594a2;

    /* renamed from: a5 */
    public C0131be[] f43597a5;

    /* renamed from: b1 */
    public final zg1 f43603b1;

    /* renamed from: b4 */
    public C0131be f43606b4;

    /* renamed from: a0 */
    public boolean f43592a0 = false;

    /* renamed from: a1 */
    public int f43593a1 = 0;

    /* renamed from: a3 */
    public int f43595a3 = 32;

    /* renamed from: a4 */
    public int f43596a4 = 32;

    /* renamed from: a6 */
    public boolean f43598a6 = false;

    /* renamed from: a7 */
    public boolean[] f43599a7 = new boolean[32];

    /* renamed from: a8 */
    public int f43600a8 = 1;

    /* renamed from: a9 */
    public int f43601a9 = 0;

    /* renamed from: b0 */
    public int f43602b0 = 32;

    /* renamed from: b2 */
    public e11[] f43604b2 = new e11[f43591b6];

    /* renamed from: b3 */
    public int f43605b3 = 0;

    public ab0() {
        this.f43597a5 = null;
        this.f43597a5 = new C0131be[32];
        m209776b8();
        zg1 zg1Var = new zg1();
        zg1Var.f61551a0 = new vn0();
        zg1Var.f61552a1 = new vn0();
        zg1Var.f61553a2 = new e11[32];
        this.f43603b1 = zg1Var;
        go0 go0Var = new go0(zg1Var);
        go0Var.f56545a5 = new e11[128];
        go0Var.f56546a6 = new e11[128];
        go0Var.f56547a7 = 0;
        go0Var.f56548a8 = new eo0(go0Var);
        this.f43594a2 = go0Var;
        this.f43606b4 = new C0131be(zg1Var);
    }

    /* renamed from: b3 */
    public static int m209758b3(Object obj) {
        e11 e11Var = ((C0797kv) obj).f57729a8;
        if (e11Var != null) {
            return (int) (e11Var.f55900a4 + 0.5f);
        }
        return 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0, types: [java.lang.Object[]] */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* renamed from: a0 */
    public final e11 m209759a0(SolverVariable$Type solverVariable$Type) {
        vn0 vn0Var = (vn0) this.f43603b1.f61552a1;
        int i = vn0Var.f60661a1;
        e11 e11Var = null;
        if (i > 0) {
            int i2 = i - 1;
            ?? r3 = vn0Var.f60660a0;
            ?? r4 = r3[i2];
            r3[i2] = 0;
            vn0Var.f60661a1 = i2;
            e11Var = r4;
        }
        e11 e11Var2 = e11Var;
        if (e11Var2 == null) {
            e11Var2 = new e11(solverVariable$Type);
            e11Var2.f55904a8 = solverVariable$Type;
        } else {
            e11Var2.m212651a2();
            e11Var2.f55904a8 = solverVariable$Type;
        }
        int i3 = this.f43605b3;
        int i4 = f43591b6;
        if (i3 >= i4) {
            int i5 = i4 * 2;
            f43591b6 = i5;
            this.f43604b2 = (e11[]) Arrays.copyOf(this.f43604b2, i5);
        }
        e11[] e11VarArr = this.f43604b2;
        int i6 = this.f43605b3;
        this.f43605b3 = i6 + 1;
        e11VarArr[i6] = e11Var2;
        return e11Var2;
    }

    /* renamed from: a1 */
    public final void m209760a1(e11 e11Var, e11 e11Var2, int i, float f, e11 e11Var3, e11 e11Var4, int i2, int i3) {
        C0131be c0131beM209770b1 = m209770b1();
        if (e11Var2 == e11Var3) {
            c0131beM209770b1.f45835a3.m210629a6(e11Var, 1.0f);
            c0131beM209770b1.f45835a3.m210629a6(e11Var4, 1.0f);
            c0131beM209770b1.f45835a3.m210629a6(e11Var2, -2.0f);
        } else if (f == 0.5f) {
            c0131beM209770b1.f45835a3.m210629a6(e11Var, 1.0f);
            c0131beM209770b1.f45835a3.m210629a6(e11Var2, -1.0f);
            c0131beM209770b1.f45835a3.m210629a6(e11Var3, -1.0f);
            c0131beM209770b1.f45835a3.m210629a6(e11Var4, 1.0f);
            if (i > 0 || i2 > 0) {
                c0131beM209770b1.f45833a1 = (-i) + i2;
            }
        } else if (f <= 0.0f) {
            c0131beM209770b1.f45835a3.m210629a6(e11Var, -1.0f);
            c0131beM209770b1.f45835a3.m210629a6(e11Var2, 1.0f);
            c0131beM209770b1.f45833a1 = i;
        } else if (f >= 1.0f) {
            c0131beM209770b1.f45835a3.m210629a6(e11Var4, -1.0f);
            c0131beM209770b1.f45835a3.m210629a6(e11Var3, 1.0f);
            c0131beM209770b1.f45833a1 = -i2;
        } else {
            float f2 = 1.0f - f;
            c0131beM209770b1.f45835a3.m210629a6(e11Var, f2 * 1.0f);
            c0131beM209770b1.f45835a3.m210629a6(e11Var2, f2 * (-1.0f));
            c0131beM209770b1.f45835a3.m210629a6(e11Var3, (-1.0f) * f);
            c0131beM209770b1.f45835a3.m210629a6(e11Var4, 1.0f * f);
            if (i > 0 || i2 > 0) {
                c0131beM209770b1.f45833a1 = (i2 * f) + ((-i) * f2);
            }
        }
        if (i3 != 8) {
            c0131beM209770b1.m210674a0(this, i3);
        }
        m209761a2(c0131beM209770b1);
    }

    /* JADX WARN: Removed duplicated region for block: B:120:0x01b2  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x01b7  */
    /* JADX WARN: Removed duplicated region for block: B:156:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00d8  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x00fa  */
    /* renamed from: a2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m209761a2(C0131be c0131be) {
        boolean z;
        SolverVariable$Type solverVariable$Type;
        boolean z2;
        e11 e11Var;
        e11 e11VarM210679a5;
        boolean z3 = true;
        if (this.f43601a9 + 1 >= this.f43602b0 || this.f43600a8 + 1 >= this.f43596a4) {
            m209772b4();
        }
        if (c0131be.f45836a4) {
            z = false;
        } else {
            ArrayList arrayList = c0131be.f45834a2;
            if (this.f43597a5.length != 0) {
                boolean z4 = false;
                while (!z4) {
                    int iM210626a3 = c0131be.f45835a3.m210626a3();
                    for (int i = 0; i < iM210626a3; i++) {
                        e11 e11VarM210627a4 = c0131be.f45835a3.m210627a4(i);
                        if (e11VarM210627a4.f55898a2 != -1 || e11VarM210627a4.f55901a5) {
                            arrayList.add(e11VarM210627a4);
                        }
                    }
                    int size = arrayList.size();
                    if (size > 0) {
                        for (int i2 = 0; i2 < size; i2++) {
                            e11 e11Var2 = (e11) arrayList.get(i2);
                            if (e11Var2.f55901a5) {
                                c0131be.m210681a7(this, e11Var2, true);
                            } else {
                                c0131be.mo210682a8(this, this.f43597a5[e11Var2.f55898a2], true);
                            }
                        }
                        arrayList.clear();
                    } else {
                        z4 = true;
                    }
                }
                if (c0131be.f45832a0 != null && c0131be.f45835a3.m210626a3() == 0) {
                    c0131be.f45836a4 = true;
                    this.f43592a0 = true;
                }
            }
            if (c0131be.mo210678a4()) {
                return;
            }
            float f = c0131be.f45833a1;
            float f2 = 0.0f;
            if (f < 0.0f) {
                c0131be.f45833a1 = f * (-1.0f);
                C0128bb c0128bb = c0131be.f45835a3;
                int i3 = c0128bb.f45775a7;
                for (int i4 = 0; i3 != -1 && i4 < c0128bb.f45768a0; i4++) {
                    float[] fArr = c0128bb.f45774a6;
                    fArr[i3] = fArr[i3] * (-1.0f);
                    i3 = c0128bb.f45773a5[i3];
                }
            }
            int iM210626a32 = c0131be.f45835a3.m210626a3();
            float f3 = 0.0f;
            float f4 = 0.0f;
            e11 e11Var3 = null;
            e11 e11Var4 = null;
            int i5 = 0;
            boolean z5 = false;
            boolean z6 = false;
            while (true) {
                solverVariable$Type = SolverVariable$Type.f44410a0;
                if (i5 >= iM210626a32) {
                    break;
                }
                float fM210628a5 = c0131be.f45835a3.m210628a5(i5);
                float f5 = f2;
                e11 e11VarM210627a42 = c0131be.f45835a3.m210627a4(i5);
                if (e11VarM210627a42.f55904a8 == solverVariable$Type) {
                    if (e11Var3 == null) {
                        z5 = e11VarM210627a42.f55907b1 <= 1;
                    } else if (f3 > fM210628a5) {
                        if (e11VarM210627a42.f55907b1 <= 1) {
                        }
                    } else if (z5 || e11VarM210627a42.f55907b1 > 1) {
                    }
                    f3 = fM210628a5;
                    e11Var3 = e11VarM210627a42;
                } else if (e11Var3 == null && fM210628a5 < f5) {
                    if (e11Var4 == null) {
                        z6 = e11VarM210627a42.f55907b1 <= 1;
                    } else if (f4 > fM210628a5) {
                        if (e11VarM210627a42.f55907b1 <= 1) {
                        }
                    } else if (z6 || e11VarM210627a42.f55907b1 > 1) {
                    }
                    f4 = fM210628a5;
                    e11Var4 = e11VarM210627a42;
                }
                i5++;
                f2 = f5;
            }
            float f6 = f2;
            if (e11Var3 == null) {
                e11Var3 = e11Var4;
            }
            if (e11Var3 == null) {
                z2 = true;
            } else {
                c0131be.m210680a6(e11Var3);
                z2 = false;
            }
            if (c0131be.f45835a3.m210626a3() == 0) {
                c0131be.f45836a4 = true;
            }
            if (z2) {
                if (this.f43600a8 + 1 >= this.f43596a4) {
                    m209772b4();
                }
                e11 e11VarM209759a0 = m209759a0(SolverVariable$Type.f44411a1);
                int i6 = this.f43593a1 + 1;
                this.f43593a1 = i6;
                this.f43600a8++;
                e11VarM209759a0.f55897a1 = i6;
                zg1 zg1Var = this.f43603b1;
                ((e11[]) zg1Var.f61553a2)[i6] = e11VarM209759a0;
                c0131be.f45832a0 = e11VarM209759a0;
                int i7 = this.f43601a9;
                m209766a7(c0131be);
                if (this.f43601a9 == i7 + 1) {
                    C0131be c0131be2 = this.f43606b4;
                    c0131be2.f45832a0 = null;
                    c0131be2.f45835a3.m210624a1();
                    for (int i8 = 0; i8 < c0131be.f45835a3.m210626a3(); i8++) {
                        c0131be2.f45835a3.m210623a0(c0131be.f45835a3.m210627a4(i8), c0131be.f45835a3.m210628a5(i8), true);
                    }
                    m209775b7(this.f43606b4);
                    if (e11VarM209759a0.f55898a2 == -1) {
                        if (c0131be.f45832a0 == e11VarM209759a0 && (e11VarM210679a5 = c0131be.m210679a5(null, e11VarM209759a0)) != null) {
                            c0131be.m210680a6(e11VarM210679a5);
                        }
                        if (!c0131be.f45836a4) {
                            c0131be.f45832a0.m212653a4(this, c0131be);
                        }
                        ((vn0) zg1Var.f61551a0).m214933a1(c0131be);
                        this.f43601a9--;
                    }
                }
                e11Var = c0131be.f45832a0;
                if (e11Var != null) {
                }
            } else {
                z3 = false;
                e11Var = c0131be.f45832a0;
                if (e11Var != null) {
                    return;
                }
                if (e11Var.f55904a8 != solverVariable$Type && c0131be.f45833a1 < f6) {
                    return;
                } else {
                    z = z3;
                }
            }
        }
        if (z) {
            return;
        }
        m209766a7(c0131be);
    }

    /* renamed from: a3 */
    public final void m209762a3(e11 e11Var, int i) {
        int i2 = e11Var.f55898a2;
        if (i2 == -1) {
            e11Var.m212652a3(this, i);
            for (int i3 = 0; i3 < this.f43593a1 + 1; i3++) {
                e11 e11Var2 = ((e11[]) this.f43603b1.f61553a2)[i3];
            }
            return;
        }
        if (i2 == -1) {
            C0131be c0131beM209770b1 = m209770b1();
            c0131beM209770b1.f45832a0 = e11Var;
            float f = i;
            e11Var.f55900a4 = f;
            c0131beM209770b1.f45833a1 = f;
            c0131beM209770b1.f45836a4 = true;
            m209761a2(c0131beM209770b1);
            return;
        }
        C0131be c0131be = this.f43597a5[i2];
        if (c0131be.f45836a4) {
            c0131be.f45833a1 = i;
            return;
        }
        if (c0131be.f45835a3.m210626a3() == 0) {
            c0131be.f45836a4 = true;
            c0131be.f45833a1 = i;
            return;
        }
        C0131be c0131beM209770b12 = m209770b1();
        if (i < 0) {
            c0131beM209770b12.f45833a1 = i * (-1);
            c0131beM209770b12.f45835a3.m210629a6(e11Var, 1.0f);
        } else {
            c0131beM209770b12.f45833a1 = i;
            c0131beM209770b12.f45835a3.m210629a6(e11Var, -1.0f);
        }
        m209761a2(c0131beM209770b12);
    }

    /* renamed from: a4 */
    public final void m209763a4(e11 e11Var, e11 e11Var2, int i, int i2) {
        if (i2 == 8 && e11Var2.f55901a5 && e11Var.f55898a2 == -1) {
            e11Var.m212652a3(this, e11Var2.f55900a4 + i);
            return;
        }
        C0131be c0131beM209770b1 = m209770b1();
        boolean z = false;
        if (i != 0) {
            if (i < 0) {
                i *= -1;
                z = true;
            }
            c0131beM209770b1.f45833a1 = i;
        }
        if (z) {
            c0131beM209770b1.f45835a3.m210629a6(e11Var, 1.0f);
            c0131beM209770b1.f45835a3.m210629a6(e11Var2, -1.0f);
        } else {
            c0131beM209770b1.f45835a3.m210629a6(e11Var, -1.0f);
            c0131beM209770b1.f45835a3.m210629a6(e11Var2, 1.0f);
        }
        if (i2 != 8) {
            c0131beM209770b1.m210674a0(this, i2);
        }
        m209761a2(c0131beM209770b1);
    }

    /* renamed from: a5 */
    public final void m209764a5(e11 e11Var, e11 e11Var2, int i, int i2) {
        C0131be c0131beM209770b1 = m209770b1();
        e11 e11VarM209771b2 = m209771b2();
        e11VarM209771b2.f55899a3 = 0;
        c0131beM209770b1.m210675a1(e11Var, e11Var2, e11VarM209771b2, i);
        if (i2 != 8) {
            c0131beM209770b1.f45835a3.m210629a6(m209768a9(i2), (int) (c0131beM209770b1.f45835a3.m210625a2(e11VarM209771b2) * (-1.0f)));
        }
        m209761a2(c0131beM209770b1);
    }

    /* renamed from: a6 */
    public final void m209765a6(e11 e11Var, e11 e11Var2, int i, int i2) {
        C0131be c0131beM209770b1 = m209770b1();
        e11 e11VarM209771b2 = m209771b2();
        e11VarM209771b2.f55899a3 = 0;
        c0131beM209770b1.m210676a2(e11Var, e11Var2, e11VarM209771b2, i);
        if (i2 != 8) {
            c0131beM209770b1.f45835a3.m210629a6(m209768a9(i2), (int) (c0131beM209770b1.f45835a3.m210625a2(e11VarM209771b2) * (-1.0f)));
        }
        m209761a2(c0131beM209770b1);
    }

    /* renamed from: a7 */
    public final void m209766a7(C0131be c0131be) {
        int i;
        if (c0131be.f45836a4) {
            c0131be.f45832a0.m212652a3(this, c0131be.f45833a1);
        } else {
            C0131be[] c0131beArr = this.f43597a5;
            int i2 = this.f43601a9;
            c0131beArr[i2] = c0131be;
            e11 e11Var = c0131be.f45832a0;
            e11Var.f55898a2 = i2;
            this.f43601a9 = i2 + 1;
            e11Var.m212653a4(this, c0131be);
        }
        if (this.f43592a0) {
            int i3 = 0;
            while (i3 < this.f43601a9) {
                if (this.f43597a5[i3] == null) {
                    System.out.println("WTF");
                }
                C0131be c0131be2 = this.f43597a5[i3];
                if (c0131be2 != null && c0131be2.f45836a4) {
                    c0131be2.f45832a0.m212652a3(this, c0131be2.f45833a1);
                    ((vn0) this.f43603b1.f61551a0).m214933a1(c0131be2);
                    this.f43597a5[i3] = null;
                    int i4 = i3 + 1;
                    int i5 = i4;
                    while (true) {
                        i = this.f43601a9;
                        if (i4 >= i) {
                            break;
                        }
                        C0131be[] c0131beArr2 = this.f43597a5;
                        int i6 = i4 - 1;
                        C0131be c0131be3 = c0131beArr2[i4];
                        c0131beArr2[i6] = c0131be3;
                        e11 e11Var2 = c0131be3.f45832a0;
                        if (e11Var2.f55898a2 == i4) {
                            e11Var2.f55898a2 = i6;
                        }
                        i5 = i4;
                        i4++;
                    }
                    if (i5 < i) {
                        this.f43597a5[i5] = null;
                    }
                    this.f43601a9 = i - 1;
                    i3--;
                }
                i3++;
            }
            this.f43592a0 = false;
        }
    }

    /* renamed from: a8 */
    public final void m209767a8() {
        for (int i = 0; i < this.f43601a9; i++) {
            C0131be c0131be = this.f43597a5[i];
            c0131be.f45832a0.f55900a4 = c0131be.f45833a1;
        }
    }

    /* renamed from: a9 */
    public final e11 m209768a9(int i) {
        if (this.f43600a8 + 1 >= this.f43596a4) {
            m209772b4();
        }
        e11 e11VarM209759a0 = m209759a0(SolverVariable$Type.f44412a2);
        float[] fArr = e11VarM209759a0.f55903a7;
        int i2 = this.f43593a1 + 1;
        this.f43593a1 = i2;
        this.f43600a8++;
        e11VarM209759a0.f55897a1 = i2;
        e11VarM209759a0.f55899a3 = i;
        ((e11[]) this.f43603b1.f61553a2)[i2] = e11VarM209759a0;
        go0 go0Var = this.f43594a2;
        go0Var.f56548a8.f56088a1 = e11VarM209759a0;
        Arrays.fill(fArr, 0.0f);
        fArr[e11VarM209759a0.f55899a3] = 1.0f;
        go0Var.m212976a9(e11VarM209759a0);
        return e11VarM209759a0;
    }

    /* renamed from: b0 */
    public final e11 m209769b0(Object obj) {
        if (obj == null) {
            return null;
        }
        if (this.f43600a8 + 1 >= this.f43596a4) {
            m209772b4();
        }
        if (!(obj instanceof C0797kv)) {
            return null;
        }
        C0797kv c0797kv = (C0797kv) obj;
        e11 e11Var = c0797kv.f57729a8;
        if (e11Var == null) {
            c0797kv.m213756b0();
            e11Var = c0797kv.f57729a8;
        }
        int i = e11Var.f55897a1;
        zg1 zg1Var = this.f43603b1;
        if (i != -1 && i <= this.f43593a1 && ((e11[]) zg1Var.f61553a2)[i] != null) {
            return e11Var;
        }
        if (i != -1) {
            e11Var.m212651a2();
        }
        int i2 = this.f43593a1 + 1;
        this.f43593a1 = i2;
        this.f43600a8++;
        e11Var.f55897a1 = i2;
        e11Var.f55904a8 = SolverVariable$Type.f44410a0;
        ((e11[]) zg1Var.f61553a2)[i2] = e11Var;
        return e11Var;
    }

    /* renamed from: b1 */
    public final C0131be m209770b1() {
        Object obj;
        zg1 zg1Var = this.f43603b1;
        vn0 vn0Var = (vn0) zg1Var.f61551a0;
        int i = vn0Var.f60661a1;
        if (i > 0) {
            int i2 = i - 1;
            Object[] objArr = vn0Var.f60660a0;
            obj = objArr[i2];
            objArr[i2] = null;
            vn0Var.f60661a1 = i2;
        } else {
            obj = null;
        }
        C0131be c0131be = (C0131be) obj;
        if (c0131be == null) {
            return new C0131be(zg1Var);
        }
        c0131be.f45832a0 = null;
        c0131be.f45835a3.m210624a1();
        c0131be.f45833a1 = 0.0f;
        c0131be.f45836a4 = false;
        return c0131be;
    }

    /* renamed from: b2 */
    public final e11 m209771b2() {
        if (this.f43600a8 + 1 >= this.f43596a4) {
            m209772b4();
        }
        e11 e11VarM209759a0 = m209759a0(SolverVariable$Type.f44411a1);
        int i = this.f43593a1 + 1;
        this.f43593a1 = i;
        this.f43600a8++;
        e11VarM209759a0.f55897a1 = i;
        ((e11[]) this.f43603b1.f61553a2)[i] = e11VarM209759a0;
        return e11VarM209759a0;
    }

    /* renamed from: b4 */
    public final void m209772b4() {
        int i = this.f43595a3 * 2;
        this.f43595a3 = i;
        this.f43597a5 = (C0131be[]) Arrays.copyOf(this.f43597a5, i);
        zg1 zg1Var = this.f43603b1;
        zg1Var.f61553a2 = (e11[]) Arrays.copyOf((e11[]) zg1Var.f61553a2, this.f43595a3);
        int i2 = this.f43595a3;
        this.f43599a7 = new boolean[i2];
        this.f43596a4 = i2;
        this.f43602b0 = i2;
    }

    /* renamed from: b5 */
    public final void m209773b5() {
        go0 go0Var = this.f43594a2;
        if (go0Var.mo210678a4()) {
            m209767a8();
            return;
        }
        if (!this.f43598a6) {
            m209774b6(go0Var);
            return;
        }
        for (int i = 0; i < this.f43601a9; i++) {
            if (!this.f43597a5[i].f45836a4) {
                m209774b6(go0Var);
                return;
            }
        }
        m209767a8();
    }

    /* renamed from: b6 */
    public final void m209774b6(go0 go0Var) {
        int i = 0;
        while (true) {
            if (i >= this.f43601a9) {
                break;
            }
            C0131be c0131be = this.f43597a5[i];
            SolverVariable$Type solverVariable$Type = c0131be.f45832a0.f55904a8;
            SolverVariable$Type solverVariable$Type2 = SolverVariable$Type.f44410a0;
            if (solverVariable$Type != solverVariable$Type2) {
                float f = 0.0f;
                if (c0131be.f45833a1 < 0.0f) {
                    boolean z = false;
                    int i2 = 0;
                    while (!z) {
                        i2++;
                        float f2 = Float.MAX_VALUE;
                        int i3 = 0;
                        int i4 = -1;
                        int i5 = -1;
                        int i6 = 0;
                        while (i3 < this.f43601a9) {
                            C0131be c0131be2 = this.f43597a5[i3];
                            if (c0131be2.f45832a0.f55904a8 != solverVariable$Type2 && !c0131be2.f45836a4 && c0131be2.f45833a1 < f) {
                                int iM210626a3 = c0131be2.f45835a3.m210626a3();
                                int i7 = 0;
                                while (i7 < iM210626a3) {
                                    e11 e11VarM210627a4 = c0131be2.f45835a3.m210627a4(i7);
                                    float f3 = f;
                                    float fM210625a2 = c0131be2.f45835a3.m210625a2(e11VarM210627a4);
                                    if (fM210625a2 > f3) {
                                        for (int i8 = 0; i8 < 9; i8++) {
                                            float f4 = e11VarM210627a4.f55902a6[i8] / fM210625a2;
                                            if ((f4 < f2 && i8 == i6) || i8 > i6) {
                                                i6 = i8;
                                                i5 = e11VarM210627a4.f55897a1;
                                                i4 = i3;
                                                f2 = f4;
                                            }
                                        }
                                    }
                                    i7++;
                                    f = f3;
                                }
                            }
                            i3++;
                            f = f;
                        }
                        float f5 = f;
                        if (i4 != -1) {
                            C0131be c0131be3 = this.f43597a5[i4];
                            c0131be3.f45832a0.f55898a2 = -1;
                            c0131be3.m210680a6(((e11[]) this.f43603b1.f61553a2)[i5]);
                            e11 e11Var = c0131be3.f45832a0;
                            e11Var.f55898a2 = i4;
                            e11Var.m212653a4(this, c0131be3);
                        } else {
                            z = true;
                        }
                        if (i2 > this.f43600a8 / 2) {
                            z = true;
                        }
                        f = f5;
                    }
                }
            }
            i++;
        }
        m209775b7(go0Var);
        m209767a8();
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0094 A[SYNTHETIC] */
    /* renamed from: b7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m209775b7(C0131be c0131be) {
        boolean z;
        int i = 0;
        for (int i2 = 0; i2 < this.f43600a8; i2++) {
            this.f43599a7[i2] = false;
        }
        boolean z2 = false;
        int i3 = 0;
        while (!z2) {
            i3++;
            if (i3 >= this.f43600a8 * 2) {
                return;
            }
            e11 e11Var = c0131be.f45832a0;
            if (e11Var != null) {
                this.f43599a7[e11Var.f55897a1] = true;
            }
            e11 e11VarMo210677a3 = c0131be.mo210677a3(this.f43599a7);
            if (e11VarMo210677a3 != null) {
                boolean[] zArr = this.f43599a7;
                int i4 = e11VarMo210677a3.f55897a1;
                if (zArr[i4]) {
                    return;
                } else {
                    zArr[i4] = true;
                }
            }
            if (e11VarMo210677a3 != null) {
                float f = Float.MAX_VALUE;
                int i5 = -1;
                for (int i6 = i; i6 < this.f43601a9; i6++) {
                    C0131be c0131be2 = this.f43597a5[i6];
                    if (c0131be2.f45832a0.f55904a8 != SolverVariable$Type.f44410a0 && !c0131be2.f45836a4) {
                        C0128bb c0128bb = c0131be2.f45835a3;
                        int i7 = c0128bb.f45775a7;
                        if (i7 == -1) {
                            z = false;
                            if (!z) {
                                float fM210625a2 = c0131be2.f45835a3.m210625a2(e11VarMo210677a3);
                                if (fM210625a2 < 0.0f) {
                                    float f2 = (-c0131be2.f45833a1) / fM210625a2;
                                    if (f2 < f) {
                                        i5 = i6;
                                        f = f2;
                                    }
                                }
                            }
                        } else {
                            for (int i8 = 0; i7 != -1 && i8 < c0128bb.f45768a0; i8++) {
                                if (c0128bb.f45772a4[i7] == e11VarMo210677a3.f55897a1) {
                                    z = true;
                                    break;
                                }
                                i7 = c0128bb.f45773a5[i7];
                            }
                            z = false;
                            if (!z) {
                            }
                        }
                    }
                }
                if (i5 > -1) {
                    C0131be c0131be3 = this.f43597a5[i5];
                    c0131be3.f45832a0.f55898a2 = -1;
                    c0131be3.m210680a6(e11VarMo210677a3);
                    e11 e11Var2 = c0131be3.f45832a0;
                    e11Var2.f55898a2 = i5;
                    e11Var2.m212653a4(this, c0131be3);
                }
            } else {
                z2 = true;
            }
            i = 0;
        }
    }

    /* renamed from: b8 */
    public final void m209776b8() {
        for (int i = 0; i < this.f43601a9; i++) {
            C0131be c0131be = this.f43597a5[i];
            if (c0131be != null) {
                ((vn0) this.f43603b1.f61551a0).m214933a1(c0131be);
            }
            this.f43597a5[i] = null;
        }
    }

    /* renamed from: b9 */
    public final void m209777b9() {
        zg1 zg1Var;
        int i = 0;
        while (true) {
            zg1Var = this.f43603b1;
            e11[] e11VarArr = (e11[]) zg1Var.f61553a2;
            if (i >= e11VarArr.length) {
                break;
            }
            e11 e11Var = e11VarArr[i];
            if (e11Var != null) {
                e11Var.m212651a2();
            }
            i++;
        }
        vn0 vn0Var = (vn0) zg1Var.f61552a1;
        e11[] e11VarArr2 = this.f43604b2;
        int length = this.f43605b3;
        vn0Var.getClass();
        if (length > e11VarArr2.length) {
            length = e11VarArr2.length;
        }
        for (int i2 = 0; i2 < length; i2++) {
            e11 e11Var2 = e11VarArr2[i2];
            int i3 = vn0Var.f60661a1;
            Object[] objArr = vn0Var.f60660a0;
            if (i3 < objArr.length) {
                objArr[i3] = e11Var2;
                vn0Var.f60661a1 = i3 + 1;
            }
        }
        this.f43605b3 = 0;
        Arrays.fill((e11[]) zg1Var.f61553a2, (Object) null);
        this.f43593a1 = 0;
        go0 go0Var = this.f43594a2;
        go0Var.f56547a7 = 0;
        go0Var.f45833a1 = 0.0f;
        this.f43600a8 = 1;
        for (int i4 = 0; i4 < this.f43601a9; i4++) {
            C0131be c0131be = this.f43597a5[i4];
        }
        m209776b8();
        this.f43601a9 = 0;
        this.f43606b4 = new C0131be(zg1Var);
    }
}
