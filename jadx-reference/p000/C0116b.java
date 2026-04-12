package p000;

import androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: b */
/* loaded from: classes.dex */
public final class C0116b {

    /* renamed from: a0 */
    public int f45656a0;

    /* renamed from: a3 */
    public C0797kv f45659a3;

    /* renamed from: a4 */
    public C0797kv f45660a4;

    /* renamed from: a5 */
    public C0797kv f45661a5;

    /* renamed from: a6 */
    public C0797kv f45662a6;

    /* renamed from: a7 */
    public int f45663a7;

    /* renamed from: a8 */
    public int f45664a8;

    /* renamed from: a9 */
    public int f45665a9;

    /* renamed from: b0 */
    public int f45666b0;

    /* renamed from: b6 */
    public int f45672b6;

    /* renamed from: b7 */
    public final /* synthetic */ C0154c f45673b7;

    /* renamed from: a1 */
    public C0829lq f45657a1 = null;

    /* renamed from: a2 */
    public int f45658a2 = 0;

    /* renamed from: b1 */
    public int f45667b1 = 0;

    /* renamed from: b2 */
    public int f45668b2 = 0;

    /* renamed from: b3 */
    public int f45669b3 = 0;

    /* renamed from: b4 */
    public int f45670b4 = 0;

    /* renamed from: b5 */
    public int f45671b5 = 0;

    public C0116b(C0154c c0154c, int i, C0797kv c0797kv, C0797kv c0797kv2, C0797kv c0797kv3, C0797kv c0797kv4, int i2) {
        this.f45673b7 = c0154c;
        this.f45656a0 = i;
        this.f45659a3 = c0797kv;
        this.f45660a4 = c0797kv2;
        this.f45661a5 = c0797kv3;
        this.f45662a6 = c0797kv4;
        this.f45663a7 = c0154c.f58339h8;
        this.f45664a8 = c0154c.f58335h4;
        this.f45665a9 = c0154c.f58340h9;
        this.f45666b0 = c0154c.f58336h5;
        this.f45672b6 = i2;
    }

    /* renamed from: a0 */
    public final void m210525a0(C0829lq c0829lq) {
        int i = this.f45656a0;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour = ConstraintWidget$DimensionBehaviour.f44426a2;
        C0154c c0154c = this.f45673b7;
        if (i == 0) {
            int iM210754f0 = c0154c.m210754f0(c0829lq, this.f45672b6);
            if (c0829lq.f58107e6[0] == constraintWidget$DimensionBehaviour) {
                this.f45671b5++;
                iM210754f0 = 0;
            }
            this.f45667b1 = iM210754f0 + (c0829lq.f58121g0 != 8 ? c0154c.f46033j7 : 0) + this.f45667b1;
            int iM210753e9 = c0154c.m210753e9(c0829lq, this.f45672b6);
            if (this.f45657a1 == null || this.f45658a2 < iM210753e9) {
                this.f45657a1 = c0829lq;
                this.f45658a2 = iM210753e9;
                this.f45668b2 = iM210753e9;
            }
        } else {
            int iM210754f02 = c0154c.m210754f0(c0829lq, this.f45672b6);
            int iM210753e92 = c0154c.m210753e9(c0829lq, this.f45672b6);
            if (c0829lq.f58107e6[1] == constraintWidget$DimensionBehaviour) {
                this.f45671b5++;
                iM210753e92 = 0;
            }
            this.f45668b2 = iM210753e92 + (c0829lq.f58121g0 != 8 ? c0154c.f46034j8 : 0) + this.f45668b2;
            if (this.f45657a1 == null || this.f45658a2 < iM210754f02) {
                this.f45657a1 = c0829lq;
                this.f45658a2 = iM210754f02;
                this.f45667b1 = iM210754f02;
            }
        }
        this.f45670b4++;
    }

    /* renamed from: a1 */
    public final void m210526a1(int i, boolean z, boolean z2) {
        C0154c c0154c;
        int i2;
        int i3;
        C0829lq c0829lq;
        boolean z3;
        int i4;
        int i5;
        char c;
        float f;
        float f2;
        float f3;
        int i6;
        float f4;
        float f5;
        int i7;
        int i8 = this.f45670b4;
        int i9 = 0;
        while (true) {
            c0154c = this.f45673b7;
            if (i9 >= i8 || (i7 = this.f45669b3 + i9) >= c0154c.f46045k9) {
                break;
            }
            C0829lq c0829lq2 = c0154c.f46044k8[i7];
            if (c0829lq2 != null) {
                c0829lq2.m213902d0();
            }
            i9++;
        }
        if (i8 == 0 || this.f45657a1 == null) {
            return;
        }
        boolean z4 = z2 && i == 0;
        int i10 = -1;
        int i11 = -1;
        for (int i12 = 0; i12 < i8; i12++) {
            int i13 = this.f45669b3 + (z ? (i8 - 1) - i12 : i12);
            if (i13 >= c0154c.f46045k9) {
                break;
            }
            C0829lq c0829lq3 = c0154c.f46044k8[i13];
            if (c0829lq3 != null && c0829lq3.f58121g0 == 0) {
                if (i10 == -1) {
                    i10 = i12;
                }
                i11 = i12;
            }
        }
        if (this.f45656a0 == 0) {
            C0829lq c0829lq4 = this.f45657a1;
            c0829lq4.f58125g4 = c0154c.f46022i6;
            C0797kv c0797kv = c0829lq4.f58099d8;
            C0797kv c0797kv2 = c0829lq4.f58097d6;
            int i14 = this.f45664a8;
            if (i > 0) {
                i14 += c0154c.f46034j8;
            }
            c0797kv2.m213746a0(this.f45660a4, i14);
            if (z2) {
                c0797kv.m213746a0(this.f45662a6, this.f45666b0);
            }
            if (i > 0) {
                this.f45660a4.f57724a3.f58099d8.m213746a0(c0797kv2, 0);
            }
            if (c0154c.f46036k0 != 3 || c0829lq4.f58091d0) {
                c0829lq = c0829lq4;
            } else {
                for (int i15 = 0; i15 < i8; i15++) {
                    int i16 = this.f45669b3 + (z ? (i8 - 1) - i15 : i15);
                    if (i16 >= c0154c.f46045k9) {
                        break;
                    }
                    c0829lq = c0154c.f46044k8[i16];
                    if (c0829lq.f58091d0) {
                        break;
                    }
                }
                c0829lq = c0829lq4;
            }
            int i17 = 0;
            C0829lq c0829lq5 = null;
            while (i17 < i8) {
                int i18 = z ? (i8 - 1) - i17 : i17;
                int i19 = this.f45669b3 + i18;
                if (i19 >= c0154c.f46045k9) {
                    return;
                }
                C0829lq c0829lq6 = c0154c.f46044k8[i19];
                if (c0829lq6 == null) {
                    i5 = i8;
                    z3 = z4;
                    i4 = i11;
                    c = 3;
                } else {
                    C0797kv c0797kv3 = c0829lq6.f58099d8;
                    C0797kv c0797kv4 = c0829lq6.f58097d6;
                    C0797kv c0797kv5 = c0829lq6.f58096d5;
                    z3 = z4;
                    if (i17 == 0) {
                        i4 = i11;
                        c0829lq6.m213881a4(c0797kv5, this.f45659a3, this.f45663a7);
                    } else {
                        i4 = i11;
                    }
                    if (i18 == 0) {
                        int i20 = c0154c.f46021i5;
                        if (z) {
                            f = 1.0f;
                            f2 = 1.0f - c0154c.f46027j1;
                        } else {
                            f = 1.0f;
                            f2 = c0154c.f46027j1;
                        }
                        if (this.f45669b3 == 0) {
                            i6 = c0154c.f46023i7;
                            f3 = f2;
                            if (i6 != -1) {
                                if (z) {
                                    f5 = c0154c.f46029j3;
                                    f4 = f - f5;
                                    c0829lq6.f58124g3 = i6;
                                    c0829lq6.f58118f7 = f4;
                                } else {
                                    f4 = c0154c.f46029j3;
                                    c0829lq6.f58124g3 = i6;
                                    c0829lq6.f58118f7 = f4;
                                }
                            }
                        } else {
                            f3 = f2;
                        }
                        if (!z2 || (i6 = c0154c.f46025i9) == -1) {
                            i6 = i20;
                            f4 = f3;
                        } else if (z) {
                            f5 = c0154c.f46031j5;
                            f4 = f - f5;
                        } else {
                            f4 = c0154c.f46031j5;
                        }
                        c0829lq6.f58124g3 = i6;
                        c0829lq6.f58118f7 = f4;
                    }
                    if (i17 == i8 - 1) {
                        i5 = i8;
                        c0829lq6.m213881a4(c0829lq6.f58098d7, this.f45661a5, this.f45665a9);
                    } else {
                        i5 = i8;
                    }
                    if (c0829lq5 != null) {
                        C0797kv c0797kv6 = c0829lq5.f58098d7;
                        c0797kv5.m213746a0(c0797kv6, c0154c.f46033j7);
                        if (i17 == i10) {
                            int i21 = this.f45663a7;
                            if (c0797kv5.m213753a7()) {
                                c0797kv5.f57728a7 = i21;
                            }
                        }
                        c0797kv6.m213746a0(c0797kv5, 0);
                        if (i17 == i4 + 1) {
                            int i22 = this.f45665a9;
                            if (c0797kv6.m213753a7()) {
                                c0797kv6.f57728a7 = i22;
                            }
                        }
                    }
                    if (c0829lq6 != c0829lq4) {
                        int i23 = c0154c.f46036k0;
                        c = 3;
                        if (i23 == 3 && c0829lq.f58091d0 && c0829lq6 != c0829lq && c0829lq6.f58091d0) {
                            c0829lq6.f58100d9.m213746a0(c0829lq.f58100d9, 0);
                        } else if (i23 == 0) {
                            c0797kv4.m213746a0(c0797kv2, 0);
                        } else if (i23 == 1) {
                            c0797kv3.m213746a0(c0797kv, 0);
                        } else if (z3) {
                            c0797kv4.m213746a0(this.f45660a4, this.f45664a8);
                            c0797kv3.m213746a0(this.f45662a6, this.f45666b0);
                        } else {
                            c0797kv4.m213746a0(c0797kv2, 0);
                            c0797kv3.m213746a0(c0797kv, 0);
                        }
                    } else {
                        c = 3;
                    }
                    c0829lq5 = c0829lq6;
                }
                i17++;
                z4 = z3;
                i11 = i4;
                i8 = i5;
            }
            return;
        }
        int i24 = i8;
        boolean z5 = z4;
        int i25 = i11;
        C0829lq c0829lq7 = this.f45657a1;
        c0829lq7.f58124g3 = c0154c.f46021i5;
        C0797kv c0797kv7 = c0829lq7.f58096d5;
        C0797kv c0797kv8 = c0829lq7.f58098d7;
        int i26 = this.f45663a7;
        if (i > 0) {
            i26 += c0154c.f46033j7;
        }
        if (z) {
            c0797kv8.m213746a0(this.f45661a5, i26);
            if (z2) {
                c0797kv7.m213746a0(this.f45659a3, this.f45665a9);
            }
            if (i > 0) {
                this.f45661a5.f57724a3.f58096d5.m213746a0(c0797kv8, 0);
            }
        } else {
            c0797kv7.m213746a0(this.f45659a3, i26);
            if (z2) {
                c0797kv8.m213746a0(this.f45661a5, this.f45665a9);
            }
            if (i > 0) {
                this.f45659a3.f57724a3.f58098d7.m213746a0(c0797kv7, 0);
            }
        }
        int i27 = 0;
        C0829lq c0829lq8 = null;
        while (true) {
            int i28 = i24;
            if (i27 >= i28 || (i2 = this.f45669b3 + i27) >= c0154c.f46045k9) {
                return;
            }
            C0829lq c0829lq9 = c0154c.f46044k8[i2];
            if (c0829lq9 == null) {
                i24 = i28;
            } else {
                C0797kv c0797kv9 = c0829lq9.f58097d6;
                C0797kv c0797kv10 = c0829lq9.f58098d7;
                C0797kv c0797kv11 = c0829lq9.f58096d5;
                if (i27 == 0) {
                    c0829lq9.m213881a4(c0797kv9, this.f45660a4, this.f45664a8);
                    int i29 = c0154c.f46022i6;
                    float f6 = c0154c.f46028j2;
                    if (this.f45669b3 == 0) {
                        int i30 = c0154c.f46024i8;
                        i24 = i28;
                        i3 = -1;
                        if (i30 != -1) {
                            f6 = c0154c.f46030j4;
                        }
                        i29 = i30;
                        c0829lq9.f58125g4 = i29;
                        c0829lq9.f58119f8 = f6;
                    } else {
                        i24 = i28;
                        i3 = -1;
                    }
                    if (z2 && (i30 = c0154c.f46026j0) != i3) {
                        f6 = c0154c.f46032j6;
                        i29 = i30;
                    }
                    c0829lq9.f58125g4 = i29;
                    c0829lq9.f58119f8 = f6;
                } else {
                    i24 = i28;
                }
                if (i27 == i24 - 1) {
                    c0829lq9.m213881a4(c0829lq9.f58099d8, this.f45662a6, this.f45666b0);
                }
                if (c0829lq8 != null) {
                    C0797kv c0797kv12 = c0829lq8.f58099d8;
                    c0797kv9.m213746a0(c0797kv12, c0154c.f46034j8);
                    if (i27 == i10) {
                        int i31 = this.f45664a8;
                        if (c0797kv9.m213753a7()) {
                            c0797kv9.f57728a7 = i31;
                        }
                    }
                    c0797kv12.m213746a0(c0797kv9, 0);
                    if (i27 == i25 + 1) {
                        int i32 = this.f45666b0;
                        if (c0797kv12.m213753a7()) {
                            c0797kv12.f57728a7 = i32;
                        }
                    }
                }
                if (c0829lq9 == c0829lq7) {
                    c0829lq8 = c0829lq9;
                } else if (z) {
                    int i33 = c0154c.f46035j9;
                    if (i33 == 0) {
                        c0797kv10.m213746a0(c0797kv8, 0);
                    } else if (i33 == 1) {
                        c0797kv11.m213746a0(c0797kv7, 0);
                    } else if (i33 == 2) {
                        c0797kv11.m213746a0(c0797kv7, 0);
                        c0797kv10.m213746a0(c0797kv8, 0);
                    }
                    c0829lq8 = c0829lq9;
                } else {
                    int i34 = c0154c.f46035j9;
                    if (i34 == 0) {
                        c0797kv11.m213746a0(c0797kv7, 0);
                    } else if (i34 == 1) {
                        c0797kv10.m213746a0(c0797kv8, 0);
                    } else if (i34 == 2) {
                        if (z5) {
                            c0797kv11.m213746a0(this.f45659a3, this.f45663a7);
                            c0797kv10.m213746a0(this.f45661a5, this.f45665a9);
                        } else {
                            c0797kv11.m213746a0(c0797kv7, 0);
                            c0797kv10.m213746a0(c0797kv8, 0);
                        }
                    }
                    c0829lq8 = c0829lq9;
                }
            }
            i27++;
        }
    }

    /* renamed from: a2 */
    public final int m210527a2() {
        return this.f45656a0 == 1 ? this.f45668b2 - this.f45673b7.f46034j8 : this.f45668b2;
    }

    /* renamed from: a3 */
    public final int m210528a3() {
        return this.f45656a0 == 0 ? this.f45667b1 - this.f45673b7.f46033j7 : this.f45667b1;
    }

    /* renamed from: a4 */
    public final void m210529a4(int i) {
        int i2 = this.f45671b5;
        if (i2 == 0) {
            return;
        }
        int i3 = this.f45670b4;
        int i4 = i / i2;
        for (int i5 = 0; i5 < i3; i5++) {
            int i6 = this.f45669b3;
            int i7 = i6 + i5;
            C0154c c0154c = this.f45673b7;
            if (i7 >= c0154c.f46045k9) {
                break;
            }
            C0829lq c0829lq = c0154c.f46044k8[i6 + i5];
            int i8 = this.f45656a0;
            ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour = ConstraintWidget$DimensionBehaviour.f44424a0;
            ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour2 = ConstraintWidget$DimensionBehaviour.f44426a2;
            if (i8 == 0) {
                if (c0829lq != null) {
                    ConstraintWidget$DimensionBehaviour[] constraintWidget$DimensionBehaviourArr = c0829lq.f58107e6;
                    if (constraintWidget$DimensionBehaviourArr[0] == constraintWidget$DimensionBehaviour2 && c0829lq.f58078b7 == 0) {
                        c0154c.m213972e8(c0829lq, constraintWidget$DimensionBehaviour, i4, constraintWidget$DimensionBehaviourArr[1], c0829lq.m213887b1());
                    }
                }
            } else if (c0829lq != null) {
                ConstraintWidget$DimensionBehaviour[] constraintWidget$DimensionBehaviourArr2 = c0829lq.f58107e6;
                if (constraintWidget$DimensionBehaviourArr2[1] == constraintWidget$DimensionBehaviour2 && c0829lq.f58079b8 == 0) {
                    int i9 = i4;
                    c0154c.m213972e8(c0829lq, constraintWidget$DimensionBehaviourArr2[0], c0829lq.m213891b7(), constraintWidget$DimensionBehaviour, i9);
                    i4 = i9;
                }
            }
        }
        this.f45667b1 = 0;
        this.f45668b2 = 0;
        this.f45657a1 = null;
        this.f45658a2 = 0;
        int i10 = this.f45670b4;
        for (int i11 = 0; i11 < i10; i11++) {
            int i12 = this.f45669b3 + i11;
            C0154c c0154c2 = this.f45673b7;
            if (i12 >= c0154c2.f46045k9) {
                return;
            }
            C0829lq c0829lq2 = c0154c2.f46044k8[i12];
            if (this.f45656a0 == 0) {
                int iM213891b7 = c0829lq2.m213891b7();
                int i13 = c0154c2.f46033j7;
                if (c0829lq2.f58121g0 == 8) {
                    i13 = 0;
                }
                this.f45667b1 = iM213891b7 + i13 + this.f45667b1;
                int iM210753e9 = c0154c2.m210753e9(c0829lq2, this.f45672b6);
                if (this.f45657a1 == null || this.f45658a2 < iM210753e9) {
                    this.f45657a1 = c0829lq2;
                    this.f45658a2 = iM210753e9;
                    this.f45668b2 = iM210753e9;
                }
            } else {
                int iM210754f0 = c0154c2.m210754f0(c0829lq2, this.f45672b6);
                int iM210753e92 = c0154c2.m210753e9(c0829lq2, this.f45672b6);
                int i14 = c0154c2.f46034j8;
                if (c0829lq2.f58121g0 == 8) {
                    i14 = 0;
                }
                this.f45668b2 = iM210753e92 + i14 + this.f45668b2;
                if (this.f45657a1 == null || this.f45658a2 < iM210754f0) {
                    this.f45657a1 = c0829lq2;
                    this.f45658a2 = iM210754f0;
                    this.f45667b1 = iM210754f0;
                }
            }
        }
    }

    /* renamed from: a5 */
    public final void m210530a5(int i, C0797kv c0797kv, C0797kv c0797kv2, C0797kv c0797kv3, C0797kv c0797kv4, int i2, int i3, int i4, int i5, int i6) {
        this.f45656a0 = i;
        this.f45659a3 = c0797kv;
        this.f45660a4 = c0797kv2;
        this.f45661a5 = c0797kv3;
        this.f45662a6 = c0797kv4;
        this.f45663a7 = i2;
        this.f45664a8 = i3;
        this.f45665a9 = i4;
        this.f45666b0 = i5;
        this.f45672b6 = i6;
    }
}
