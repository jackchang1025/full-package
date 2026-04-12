package p000;

import java.util.Arrays;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: bb */
/* loaded from: classes.dex */
public final class C0128bb {

    /* renamed from: a1 */
    public final C0131be f45769a1;

    /* renamed from: a2 */
    public final zg1 f45770a2;

    /* renamed from: a0 */
    public int f45768a0 = 0;

    /* renamed from: a3 */
    public int f45771a3 = 8;

    /* renamed from: a4 */
    public int[] f45772a4 = new int[8];

    /* renamed from: a5 */
    public int[] f45773a5 = new int[8];

    /* renamed from: a6 */
    public float[] f45774a6 = new float[8];

    /* renamed from: a7 */
    public int f45775a7 = -1;

    /* renamed from: a8 */
    public int f45776a8 = -1;

    /* renamed from: a9 */
    public boolean f45777a9 = false;

    public C0128bb(C0131be c0131be, zg1 zg1Var) {
        this.f45769a1 = c0131be;
        this.f45770a2 = zg1Var;
    }

    /* renamed from: a0 */
    public final void m210623a0(e11 e11Var, float f, boolean z) {
        if (f <= -0.001f || f >= 0.001f) {
            int i = this.f45775a7;
            C0131be c0131be = this.f45769a1;
            if (i == -1) {
                this.f45775a7 = 0;
                this.f45774a6[0] = f;
                this.f45772a4[0] = e11Var.f55897a1;
                this.f45773a5[0] = -1;
                e11Var.f55907b1++;
                e11Var.m212649a0(c0131be);
                this.f45768a0++;
                if (this.f45777a9) {
                    return;
                }
                int i2 = this.f45776a8 + 1;
                this.f45776a8 = i2;
                int[] iArr = this.f45772a4;
                if (i2 >= iArr.length) {
                    this.f45777a9 = true;
                    this.f45776a8 = iArr.length - 1;
                    return;
                }
                return;
            }
            int i3 = -1;
            for (int i4 = 0; i != -1 && i4 < this.f45768a0; i4++) {
                int i5 = this.f45772a4[i];
                int i6 = e11Var.f55897a1;
                if (i5 == i6) {
                    float[] fArr = this.f45774a6;
                    float f2 = fArr[i] + f;
                    if (f2 > -0.001f && f2 < 0.001f) {
                        f2 = 0.0f;
                    }
                    fArr[i] = f2;
                    if (f2 == 0.0f) {
                        if (i == this.f45775a7) {
                            this.f45775a7 = this.f45773a5[i];
                        } else {
                            int[] iArr2 = this.f45773a5;
                            iArr2[i3] = iArr2[i];
                        }
                        if (z) {
                            e11Var.m212650a1(c0131be);
                        }
                        if (this.f45777a9) {
                            this.f45776a8 = i;
                        }
                        e11Var.f55907b1--;
                        this.f45768a0--;
                        return;
                    }
                    return;
                }
                if (i5 < i6) {
                    i3 = i;
                }
                i = this.f45773a5[i];
            }
            int length = this.f45776a8;
            int i7 = length + 1;
            if (this.f45777a9) {
                int[] iArr3 = this.f45772a4;
                if (iArr3[length] != -1) {
                    length = iArr3.length;
                }
            } else {
                length = i7;
            }
            int[] iArr4 = this.f45772a4;
            if (length >= iArr4.length && this.f45768a0 < iArr4.length) {
                int i8 = 0;
                while (true) {
                    int[] iArr5 = this.f45772a4;
                    if (i8 >= iArr5.length) {
                        break;
                    }
                    if (iArr5[i8] == -1) {
                        length = i8;
                        break;
                    }
                    i8++;
                }
            }
            int[] iArr6 = this.f45772a4;
            if (length >= iArr6.length) {
                length = iArr6.length;
                int i9 = this.f45771a3 * 2;
                this.f45771a3 = i9;
                this.f45777a9 = false;
                this.f45776a8 = length - 1;
                this.f45774a6 = Arrays.copyOf(this.f45774a6, i9);
                this.f45772a4 = Arrays.copyOf(this.f45772a4, this.f45771a3);
                this.f45773a5 = Arrays.copyOf(this.f45773a5, this.f45771a3);
            }
            this.f45772a4[length] = e11Var.f55897a1;
            this.f45774a6[length] = f;
            if (i3 != -1) {
                int[] iArr7 = this.f45773a5;
                iArr7[length] = iArr7[i3];
                iArr7[i3] = length;
            } else {
                this.f45773a5[length] = this.f45775a7;
                this.f45775a7 = length;
            }
            e11Var.f55907b1++;
            e11Var.m212649a0(c0131be);
            this.f45768a0++;
            if (!this.f45777a9) {
                this.f45776a8++;
            }
            int i10 = this.f45776a8;
            int[] iArr8 = this.f45772a4;
            if (i10 >= iArr8.length) {
                this.f45777a9 = true;
                this.f45776a8 = iArr8.length - 1;
            }
        }
    }

    /* renamed from: a1 */
    public final void m210624a1() {
        int i = this.f45775a7;
        for (int i2 = 0; i != -1 && i2 < this.f45768a0; i2++) {
            e11 e11Var = ((e11[]) this.f45770a2.f61553a2)[this.f45772a4[i]];
            if (e11Var != null) {
                e11Var.m212650a1(this.f45769a1);
            }
            i = this.f45773a5[i];
        }
        this.f45775a7 = -1;
        this.f45776a8 = -1;
        this.f45777a9 = false;
        this.f45768a0 = 0;
    }

    /* renamed from: a2 */
    public final float m210625a2(e11 e11Var) {
        int i = this.f45775a7;
        for (int i2 = 0; i != -1 && i2 < this.f45768a0; i2++) {
            if (this.f45772a4[i] == e11Var.f55897a1) {
                return this.f45774a6[i];
            }
            i = this.f45773a5[i];
        }
        return 0.0f;
    }

    /* renamed from: a3 */
    public final int m210626a3() {
        return this.f45768a0;
    }

    /* renamed from: a4 */
    public final e11 m210627a4(int i) {
        int i2 = this.f45775a7;
        for (int i3 = 0; i2 != -1 && i3 < this.f45768a0; i3++) {
            if (i3 == i) {
                return ((e11[]) this.f45770a2.f61553a2)[this.f45772a4[i2]];
            }
            i2 = this.f45773a5[i2];
        }
        return null;
    }

    /* renamed from: a5 */
    public final float m210628a5(int i) {
        int i2 = this.f45775a7;
        for (int i3 = 0; i2 != -1 && i3 < this.f45768a0; i3++) {
            if (i3 == i) {
                return this.f45774a6[i2];
            }
            i2 = this.f45773a5[i2];
        }
        return 0.0f;
    }

    /* renamed from: a6 */
    public final void m210629a6(e11 e11Var, float f) {
        if (f == 0.0f) {
            m210630a7(e11Var, true);
            return;
        }
        int i = this.f45775a7;
        C0131be c0131be = this.f45769a1;
        if (i == -1) {
            this.f45775a7 = 0;
            this.f45774a6[0] = f;
            this.f45772a4[0] = e11Var.f55897a1;
            this.f45773a5[0] = -1;
            e11Var.f55907b1++;
            e11Var.m212649a0(c0131be);
            this.f45768a0++;
            if (this.f45777a9) {
                return;
            }
            int i2 = this.f45776a8 + 1;
            this.f45776a8 = i2;
            int[] iArr = this.f45772a4;
            if (i2 >= iArr.length) {
                this.f45777a9 = true;
                this.f45776a8 = iArr.length - 1;
                return;
            }
            return;
        }
        int i3 = -1;
        for (int i4 = 0; i != -1 && i4 < this.f45768a0; i4++) {
            int i5 = this.f45772a4[i];
            int i6 = e11Var.f55897a1;
            if (i5 == i6) {
                this.f45774a6[i] = f;
                return;
            }
            if (i5 < i6) {
                i3 = i;
            }
            i = this.f45773a5[i];
        }
        int length = this.f45776a8;
        int i7 = length + 1;
        if (this.f45777a9) {
            int[] iArr2 = this.f45772a4;
            if (iArr2[length] != -1) {
                length = iArr2.length;
            }
        } else {
            length = i7;
        }
        int[] iArr3 = this.f45772a4;
        if (length >= iArr3.length && this.f45768a0 < iArr3.length) {
            int i8 = 0;
            while (true) {
                int[] iArr4 = this.f45772a4;
                if (i8 >= iArr4.length) {
                    break;
                }
                if (iArr4[i8] == -1) {
                    length = i8;
                    break;
                }
                i8++;
            }
        }
        int[] iArr5 = this.f45772a4;
        if (length >= iArr5.length) {
            length = iArr5.length;
            int i9 = this.f45771a3 * 2;
            this.f45771a3 = i9;
            this.f45777a9 = false;
            this.f45776a8 = length - 1;
            this.f45774a6 = Arrays.copyOf(this.f45774a6, i9);
            this.f45772a4 = Arrays.copyOf(this.f45772a4, this.f45771a3);
            this.f45773a5 = Arrays.copyOf(this.f45773a5, this.f45771a3);
        }
        this.f45772a4[length] = e11Var.f55897a1;
        this.f45774a6[length] = f;
        if (i3 != -1) {
            int[] iArr6 = this.f45773a5;
            iArr6[length] = iArr6[i3];
            iArr6[i3] = length;
        } else {
            this.f45773a5[length] = this.f45775a7;
            this.f45775a7 = length;
        }
        e11Var.f55907b1++;
        e11Var.m212649a0(c0131be);
        int i10 = this.f45768a0 + 1;
        this.f45768a0 = i10;
        if (!this.f45777a9) {
            this.f45776a8++;
        }
        int[] iArr7 = this.f45772a4;
        if (i10 >= iArr7.length) {
            this.f45777a9 = true;
        }
        if (this.f45776a8 >= iArr7.length) {
            this.f45777a9 = true;
            this.f45776a8 = iArr7.length - 1;
        }
    }

    /* renamed from: a7 */
    public final float m210630a7(e11 e11Var, boolean z) {
        int i = this.f45775a7;
        if (i == -1) {
            return 0.0f;
        }
        int i2 = 0;
        int i3 = -1;
        while (i != -1 && i2 < this.f45768a0) {
            if (this.f45772a4[i] == e11Var.f55897a1) {
                if (i == this.f45775a7) {
                    this.f45775a7 = this.f45773a5[i];
                } else {
                    int[] iArr = this.f45773a5;
                    iArr[i3] = iArr[i];
                }
                if (z) {
                    e11Var.m212650a1(this.f45769a1);
                }
                e11Var.f55907b1--;
                this.f45768a0--;
                this.f45772a4[i] = -1;
                if (this.f45777a9) {
                    this.f45776a8 = i;
                }
                return this.f45774a6[i];
            }
            i2++;
            i3 = i;
            i = this.f45773a5[i];
        }
        return 0.0f;
    }

    public final String toString() {
        int i = this.f45775a7;
        String string = "";
        for (int i2 = 0; i != -1 && i2 < this.f45768a0; i2++) {
            StringBuilder sbM37b8 = AbstractC0003a2.m37b8(AbstractC0003a2.m32b3(string, " -> "));
            sbM37b8.append(this.f45774a6[i]);
            sbM37b8.append(" : ");
            StringBuilder sbM37b82 = AbstractC0003a2.m37b8(sbM37b8.toString());
            sbM37b82.append(((e11[]) this.f45770a2.f61553a2)[this.f45772a4[i]]);
            string = sbM37b82.toString();
            i = this.f45773a5[i];
        }
        return string;
    }
}
