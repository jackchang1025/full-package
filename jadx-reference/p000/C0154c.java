package p000;

import androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: c */
/* loaded from: classes.dex */
public final class C0154c extends md1 {

    /* renamed from: k8 */
    public C0829lq[] f46044k8;

    /* renamed from: i5 */
    public int f46021i5 = -1;

    /* renamed from: i6 */
    public int f46022i6 = -1;

    /* renamed from: i7 */
    public int f46023i7 = -1;

    /* renamed from: i8 */
    public int f46024i8 = -1;

    /* renamed from: i9 */
    public int f46025i9 = -1;

    /* renamed from: j0 */
    public int f46026j0 = -1;

    /* renamed from: j1 */
    public float f46027j1 = 0.5f;

    /* renamed from: j2 */
    public float f46028j2 = 0.5f;

    /* renamed from: j3 */
    public float f46029j3 = 0.5f;

    /* renamed from: j4 */
    public float f46030j4 = 0.5f;

    /* renamed from: j5 */
    public float f46031j5 = 0.5f;

    /* renamed from: j6 */
    public float f46032j6 = 0.5f;

    /* renamed from: j7 */
    public int f46033j7 = 0;

    /* renamed from: j8 */
    public int f46034j8 = 0;

    /* renamed from: j9 */
    public int f46035j9 = 2;

    /* renamed from: k0 */
    public int f46036k0 = 2;

    /* renamed from: k1 */
    public int f46037k1 = 0;

    /* renamed from: k2 */
    public int f46038k2 = -1;

    /* renamed from: k3 */
    public int f46039k3 = 0;

    /* renamed from: k4 */
    public final ArrayList f46040k4 = new ArrayList();

    /* renamed from: k5 */
    public C0829lq[] f46041k5 = null;

    /* renamed from: k6 */
    public C0829lq[] f46042k6 = null;

    /* renamed from: k7 */
    public int[] f46043k7 = null;

    /* renamed from: k9 */
    public int f46045k9 = 0;

    @Override // p000.C0829lq
    /* renamed from: a1 */
    public final void mo210751a1(ab0 ab0Var, boolean z) {
        C0829lq c0829lq;
        float f;
        int i;
        super.mo210751a1(ab0Var, z);
        C0829lq c0829lq2 = this.f58108e7;
        boolean z2 = c0829lq2 != null && ((C0830lr) c0829lq2).f58144h7;
        int i2 = this.f46037k1;
        ArrayList arrayList = this.f46040k4;
        if (i2 != 0) {
            if (i2 == 1) {
                int size = arrayList.size();
                int i3 = 0;
                while (i3 < size) {
                    ((C0116b) arrayList.get(i3)).m210526a1(i3, z2, i3 == size + (-1));
                    i3++;
                }
            } else if (i2 != 2) {
                if (i2 == 3) {
                    int size2 = arrayList.size();
                    int i4 = 0;
                    while (i4 < size2) {
                        ((C0116b) arrayList.get(i4)).m210526a1(i4, z2, i4 == size2 + (-1));
                        i4++;
                    }
                }
            } else if (this.f46043k7 != null && this.f46042k6 != null && this.f46041k5 != null) {
                for (int i5 = 0; i5 < this.f46045k9; i5++) {
                    this.f46044k8[i5].m213902d0();
                }
                int[] iArr = this.f46043k7;
                int i6 = iArr[0];
                int i7 = iArr[1];
                float f2 = this.f46027j1;
                C0829lq c0829lq3 = null;
                int i8 = 0;
                while (i8 < i6) {
                    if (z2) {
                        i = (i6 - i8) - 1;
                        f = 1.0f - this.f46027j1;
                    } else {
                        f = f2;
                        i = i8;
                    }
                    C0829lq c0829lq4 = this.f46042k6[i];
                    if (c0829lq4 != null) {
                        C0797kv c0797kv = c0829lq4.f58096d5;
                        if (c0829lq4.f58121g0 != 8) {
                            if (i8 == 0) {
                                c0829lq4.m213881a4(c0797kv, this.f58096d5, this.f58339h8);
                                c0829lq4.f58124g3 = this.f46021i5;
                                c0829lq4.f58118f7 = f;
                            }
                            if (i8 == i6 - 1) {
                                c0829lq4.m213881a4(c0829lq4.f58098d7, this.f58098d7, this.f58340h9);
                            }
                            if (i8 > 0 && c0829lq3 != null) {
                                C0797kv c0797kv2 = c0829lq3.f58098d7;
                                c0829lq4.m213881a4(c0797kv, c0797kv2, this.f46033j7);
                                c0829lq3.m213881a4(c0797kv2, c0797kv, 0);
                            }
                            c0829lq3 = c0829lq4;
                        }
                    }
                    i8++;
                    f2 = f;
                }
                for (int i9 = 0; i9 < i7; i9++) {
                    C0829lq c0829lq5 = this.f46041k5[i9];
                    if (c0829lq5 != null) {
                        C0797kv c0797kv3 = c0829lq5.f58097d6;
                        if (c0829lq5.f58121g0 != 8) {
                            if (i9 == 0) {
                                c0829lq5.m213881a4(c0797kv3, this.f58097d6, this.f58335h4);
                                c0829lq5.f58125g4 = this.f46022i6;
                                c0829lq5.f58119f8 = this.f46028j2;
                            }
                            if (i9 == i7 - 1) {
                                c0829lq5.m213881a4(c0829lq5.f58099d8, this.f58099d8, this.f58336h5);
                            }
                            if (i9 > 0 && c0829lq3 != null) {
                                C0797kv c0797kv4 = c0829lq3.f58099d8;
                                c0829lq5.m213881a4(c0797kv3, c0797kv4, this.f46034j8);
                                c0829lq3.m213881a4(c0797kv4, c0797kv3, 0);
                            }
                            c0829lq3 = c0829lq5;
                        }
                    }
                }
                for (int i10 = 0; i10 < i6; i10++) {
                    for (int i11 = 0; i11 < i7; i11++) {
                        int i12 = (i11 * i6) + i10;
                        if (this.f46039k3 == 1) {
                            i12 = (i10 * i7) + i11;
                        }
                        C0829lq[] c0829lqArr = this.f46044k8;
                        if (i12 < c0829lqArr.length && (c0829lq = c0829lqArr[i12]) != null && c0829lq.f58121g0 != 8) {
                            C0829lq c0829lq6 = this.f46042k6[i10];
                            C0829lq c0829lq7 = this.f46041k5[i11];
                            if (c0829lq != c0829lq6) {
                                c0829lq.m213881a4(c0829lq.f58096d5, c0829lq6.f58096d5, 0);
                                c0829lq.m213881a4(c0829lq.f58098d7, c0829lq6.f58098d7, 0);
                            }
                            if (c0829lq != c0829lq7) {
                                c0829lq.m213881a4(c0829lq.f58097d6, c0829lq7.f58097d6, 0);
                                c0829lq.m213881a4(c0829lq.f58099d8, c0829lq7.f58099d8, 0);
                            }
                        }
                    }
                }
            }
        } else if (arrayList.size() > 0) {
            ((C0116b) arrayList.get(0)).m210526a1(0, z2, true);
        }
        this.f58341i0 = false;
    }

    @Override // p000.b40, p000.C0829lq
    /* renamed from: a6 */
    public final void mo210535a6(C0829lq c0829lq, HashMap map) {
        super.mo210535a6(c0829lq, map);
        C0154c c0154c = (C0154c) c0829lq;
        this.f46021i5 = c0154c.f46021i5;
        this.f46022i6 = c0154c.f46022i6;
        this.f46023i7 = c0154c.f46023i7;
        this.f46024i8 = c0154c.f46024i8;
        this.f46025i9 = c0154c.f46025i9;
        this.f46026j0 = c0154c.f46026j0;
        this.f46027j1 = c0154c.f46027j1;
        this.f46028j2 = c0154c.f46028j2;
        this.f46029j3 = c0154c.f46029j3;
        this.f46030j4 = c0154c.f46030j4;
        this.f46031j5 = c0154c.f46031j5;
        this.f46032j6 = c0154c.f46032j6;
        this.f46033j7 = c0154c.f46033j7;
        this.f46034j8 = c0154c.f46034j8;
        this.f46035j9 = c0154c.f46035j9;
        this.f46036k0 = c0154c.f46036k0;
        this.f46037k1 = c0154c.f46037k1;
        this.f46038k2 = c0154c.f46038k2;
        this.f46039k3 = c0154c.f46039k3;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:405:0x06da  */
    /* JADX WARN: Removed duplicated region for block: B:406:0x06dd  */
    /* JADX WARN: Removed duplicated region for block: B:413:0x06f9  */
    /* JADX WARN: Removed duplicated region for block: B:414:0x06fc  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0114  */
    @Override // p000.md1
    /* renamed from: e7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void mo210752e7(int i, int i2, int i3, int i4) {
        int i5;
        C0829lq[] c0829lqArr;
        int i6;
        int i7;
        int i8;
        int[] iArr;
        int i9;
        C0116b c0116b;
        char c;
        int i10;
        int i11;
        int i12;
        int i13;
        int iCeil;
        int iCeil2;
        Object obj;
        C0829lq c0829lq;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18 = this.f45712h3;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour = ConstraintWidget$DimensionBehaviour.f44426a2;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour2 = ConstraintWidget$DimensionBehaviour.f44425a1;
        if (i18 > 0) {
            C0829lq c0829lq2 = this.f58108e7;
            C0813la c0813la = c0829lq2 != null ? ((C0830lr) c0829lq2).f58143h6 : null;
            if (c0813la == null) {
                this.f58342i1 = 0;
                this.f58343i2 = 0;
                this.f58341i0 = false;
                return;
            }
            for (int i19 = 0; i19 < this.f45712h3; i19++) {
                C0829lq c0829lq3 = this.f45711h2[i19];
                if (c0829lq3 != null && !(c0829lq3 instanceof o30)) {
                    ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviourM213886b0 = c0829lq3.m213886b0(0);
                    ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviourM213886b02 = c0829lq3.m213886b0(1);
                    if (constraintWidget$DimensionBehaviourM213886b0 != constraintWidget$DimensionBehaviour || c0829lq3.f58078b7 == 1 || constraintWidget$DimensionBehaviourM213886b02 != constraintWidget$DimensionBehaviour || c0829lq3.f58079b8 == 1) {
                        if (constraintWidget$DimensionBehaviourM213886b0 == constraintWidget$DimensionBehaviour) {
                            constraintWidget$DimensionBehaviourM213886b0 = constraintWidget$DimensionBehaviour2;
                        }
                        if (constraintWidget$DimensionBehaviourM213886b02 == constraintWidget$DimensionBehaviour) {
                            constraintWidget$DimensionBehaviourM213886b02 = constraintWidget$DimensionBehaviour2;
                        }
                        C0418dj c0418dj = this.f58344i3;
                        c0418dj.f55819a0 = constraintWidget$DimensionBehaviourM213886b0;
                        c0418dj.f55820a1 = constraintWidget$DimensionBehaviourM213886b02;
                        c0418dj.f55821a2 = c0829lq3.m213891b7();
                        c0418dj.f55822a3 = c0829lq3.m213887b1();
                        c0813la.m213800a1(c0829lq3, c0418dj);
                        c0829lq3.m213911e1(c0418dj.f55823a4);
                        c0829lq3.m213908d8(c0418dj.f55824a5);
                        c0829lq3.m213905d5(c0418dj.f55825a6);
                    }
                }
            }
        }
        int i20 = this.f58339h8;
        int i21 = this.f58340h9;
        int i22 = this.f58335h4;
        int i23 = this.f58336h5;
        int[] iArr2 = new int[2];
        int i24 = (i2 - i20) - i21;
        int i25 = this.f46039k3;
        if (i25 == 1) {
            i24 = (i4 - i22) - i23;
        }
        int i26 = i24;
        if (i25 == 0) {
            if (this.f46021i5 == -1) {
                this.f46021i5 = 0;
            }
            if (this.f46022i6 == -1) {
                this.f46022i6 = 0;
            }
        } else {
            if (this.f46021i5 == -1) {
                this.f46021i5 = 0;
            }
            if (this.f46022i6 == -1) {
                this.f46022i6 = 0;
            }
        }
        C0829lq[] c0829lqArr2 = this.f45711h2;
        int i27 = 0;
        int i28 = 0;
        int i29 = 0;
        while (true) {
            i5 = this.f45712h3;
            if (i27 >= i5) {
                break;
            }
            if (this.f45711h2[i27].f58121g0 == 8) {
                i28++;
            }
            i27++;
        }
        if (i28 > 0) {
            C0829lq[] c0829lqArr3 = new C0829lq[i5 - i28];
            int i30 = 0;
            i5 = 0;
            while (i30 < this.f45712h3) {
                C0829lq c0829lq4 = this.f45711h2[i30];
                int i31 = i20;
                C0829lq[] c0829lqArr4 = c0829lqArr3;
                if (c0829lq4.f58121g0 != 8) {
                    c0829lqArr4[i5] = c0829lq4;
                    i5++;
                }
                i30++;
                i20 = i31;
                c0829lqArr3 = c0829lqArr4;
            }
            c0829lqArr = c0829lqArr3;
        } else {
            c0829lqArr = c0829lqArr2;
        }
        int i32 = i20;
        this.f46044k8 = c0829lqArr;
        this.f46045k9 = i5;
        int i33 = this.f46037k1;
        ArrayList arrayList = this.f46040k4;
        if (i33 != 0) {
            C0797kv c0797kv = this.f58097d6;
            C0797kv c0797kv2 = this.f58096d5;
            C0797kv c0797kv3 = this.f58098d7;
            C0797kv c0797kv4 = this.f58099d8;
            if (i33 == 1) {
                i6 = i21;
                i7 = i22;
                i8 = i23;
                iArr = iArr2;
                i9 = i32;
                int i34 = this.f46039k3;
                if (i5 != 0) {
                    arrayList.clear();
                    C0116b c0116b2 = new C0116b(this, i34, this.f58096d5, this.f58097d6, this.f58098d7, this.f58099d8, i26);
                    arrayList.add(c0116b2);
                    if (i34 == 0) {
                        i11 = 0;
                        int i35 = 0;
                        int i36 = 0;
                        while (i36 < i5) {
                            C0829lq c0829lq5 = c0829lqArr[i36];
                            int iM210754f0 = m210754f0(c0829lq5, i26);
                            if (c0829lq5.f58107e6[0] == constraintWidget$DimensionBehaviour) {
                                i11++;
                            }
                            int i37 = i11;
                            boolean z = (i35 == i26 || (this.f46033j7 + i35) + iM210754f0 > i26) && c0116b2.f45657a1 != null;
                            if (!z && i36 > 0 && (i13 = this.f46038k2) > 0 && i36 % i13 == 0) {
                                z = true;
                            }
                            if (z) {
                                c0116b2 = new C0116b(this, i34, this.f58096d5, this.f58097d6, this.f58098d7, this.f58099d8, i26);
                                c0116b2.f45669b3 = i36;
                                arrayList.add(c0116b2);
                            } else {
                                if (i36 > 0) {
                                    i35 = this.f46033j7 + iM210754f0 + i35;
                                }
                                c0116b2.m210525a0(c0829lq5);
                                i36++;
                                i11 = i37;
                            }
                            i35 = iM210754f0;
                            c0116b2.m210525a0(c0829lq5);
                            i36++;
                            i11 = i37;
                        }
                    } else {
                        i11 = 0;
                        int i38 = 0;
                        int i39 = 0;
                        while (i39 < i5) {
                            C0829lq c0829lq6 = c0829lqArr[i39];
                            int iM210753e9 = m210753e9(c0829lq6, i26);
                            if (c0829lq6.f58107e6[1] == constraintWidget$DimensionBehaviour) {
                                i11++;
                            }
                            int i40 = i11;
                            boolean z2 = (i38 == i26 || (this.f46034j8 + i38) + iM210753e9 > i26) && c0116b2.f45657a1 != null;
                            if (!z2 && i39 > 0 && (i12 = this.f46038k2) > 0 && i39 % i12 == 0) {
                                z2 = true;
                            }
                            if (z2) {
                                c0116b2 = new C0116b(this, i34, this.f58096d5, this.f58097d6, this.f58098d7, this.f58099d8, i26);
                                c0116b2.f45669b3 = i39;
                                arrayList.add(c0116b2);
                            } else {
                                if (i39 > 0) {
                                    i38 = this.f46034j8 + iM210753e9 + i38;
                                }
                                c0116b2.m210525a0(c0829lq6);
                                i39++;
                                i11 = i40;
                            }
                            i38 = iM210753e9;
                            c0116b2.m210525a0(c0829lq6);
                            i39++;
                            i11 = i40;
                        }
                    }
                    int size = arrayList.size();
                    int i41 = this.f58339h8;
                    int i42 = this.f58335h4;
                    int i43 = this.f58340h9;
                    int i44 = this.f58336h5;
                    ConstraintWidget$DimensionBehaviour[] constraintWidget$DimensionBehaviourArr = this.f58107e6;
                    boolean z3 = constraintWidget$DimensionBehaviourArr[0] == constraintWidget$DimensionBehaviour2 || constraintWidget$DimensionBehaviourArr[1] == constraintWidget$DimensionBehaviour2;
                    if (i11 > 0 && z3) {
                        for (int i45 = 0; i45 < size; i45++) {
                            C0116b c0116b3 = (C0116b) arrayList.get(i45);
                            if (i34 == 0) {
                                c0116b3.m210529a4(i26 - c0116b3.m210528a3());
                            } else {
                                c0116b3.m210529a4(i26 - c0116b3.m210527a2());
                            }
                        }
                    }
                    int i46 = i41;
                    int i47 = i42;
                    int i48 = i43;
                    int i49 = i44;
                    C0797kv c0797kv5 = c0797kv;
                    C0797kv c0797kv6 = c0797kv2;
                    int iMax = 0;
                    int i50 = 0;
                    C0797kv c0797kv7 = c0797kv3;
                    C0797kv c0797kv8 = c0797kv4;
                    for (int i51 = 0; i51 < size; i51++) {
                        C0116b c0116b4 = (C0116b) arrayList.get(i51);
                        if (i34 == 0) {
                            if (i51 < size - 1) {
                                c0797kv8 = ((C0116b) arrayList.get(i51 + 1)).f45657a1.f58097d6;
                                i49 = 0;
                            } else {
                                i49 = this.f58336h5;
                                c0797kv8 = c0797kv4;
                            }
                            C0797kv c0797kv9 = c0116b4.f45657a1.f58099d8;
                            c0116b4.m210530a5(i34, c0797kv6, c0797kv5, c0797kv7, c0797kv8, i46, i47, i48, i49, i26);
                            iMax = Math.max(iMax, c0116b4.m210528a3());
                            int iM210527a2 = c0116b4.m210527a2() + i50;
                            if (i51 > 0) {
                                iM210527a2 += this.f46034j8;
                            }
                            i50 = iM210527a2;
                            c0797kv5 = c0797kv9;
                            i47 = 0;
                        } else {
                            if (i51 < size - 1) {
                                c0797kv7 = ((C0116b) arrayList.get(i51 + 1)).f45657a1.f58096d5;
                                i48 = 0;
                            } else {
                                i48 = this.f58340h9;
                                c0797kv7 = c0797kv3;
                            }
                            C0797kv c0797kv10 = c0116b4.f45657a1.f58098d7;
                            c0116b4.m210530a5(i34, c0797kv6, c0797kv5, c0797kv7, c0797kv8, i46, i47, i48, i49, i26);
                            int iM210528a3 = c0116b4.m210528a3() + iMax;
                            int iMax2 = Math.max(i50, c0116b4.m210527a2());
                            if (i51 > 0) {
                                iM210528a3 += this.f46033j7;
                            }
                            i50 = iMax2;
                            iMax = iM210528a3;
                            c0797kv6 = c0797kv10;
                            i46 = 0;
                        }
                    }
                    iArr[0] = iMax;
                    iArr[1] = i50;
                }
            } else if (i33 == 2) {
                i6 = i21;
                i7 = i22;
                i8 = i23;
                iArr = iArr2;
                i9 = i32;
                int i52 = this.f46039k3;
                if (i52 == 0) {
                    int i53 = this.f46038k2;
                    if (i53 <= 0) {
                        int i54 = 0;
                        iCeil2 = 0;
                        for (int i55 = 0; i55 < i5; i55++) {
                            if (i55 > 0) {
                                i54 += this.f46033j7;
                            }
                            C0829lq c0829lq7 = c0829lqArr[i55];
                            if (c0829lq7 != null) {
                                int iM210754f02 = m210754f0(c0829lq7, i26) + i54;
                                if (iM210754f02 > i26) {
                                    break;
                                }
                                iCeil2++;
                                i54 = iM210754f02;
                            }
                        }
                    } else {
                        iCeil2 = i53;
                    }
                    iCeil = 0;
                } else {
                    iCeil = this.f46038k2;
                    if (iCeil <= 0) {
                        int i56 = 0;
                        int i57 = 0;
                        for (int i58 = 0; i58 < i5; i58++) {
                            if (i58 > 0) {
                                i56 += this.f46034j8;
                            }
                            C0829lq c0829lq8 = c0829lqArr[i58];
                            if (c0829lq8 != null) {
                                int iM210753e92 = m210753e9(c0829lq8, i26) + i56;
                                if (iM210753e92 > i26) {
                                    break;
                                }
                                i57++;
                                i56 = iM210753e92;
                            }
                        }
                        iCeil = i57;
                    }
                    iCeil2 = 0;
                }
                if (this.f46043k7 == null) {
                    this.f46043k7 = new int[2];
                }
                boolean z4 = (iCeil == 0 && i52 == 1) || (iCeil2 == 0 && i52 == 0);
                while (!z4) {
                    if (i52 == 0) {
                        iCeil = (int) Math.ceil(i5 / iCeil2);
                    } else {
                        iCeil2 = (int) Math.ceil(i5 / iCeil);
                    }
                    C0829lq[] c0829lqArr5 = this.f46042k6;
                    if (c0829lqArr5 == null || c0829lqArr5.length < iCeil2) {
                        obj = null;
                        this.f46042k6 = new C0829lq[iCeil2];
                    } else {
                        obj = null;
                        Arrays.fill(c0829lqArr5, (Object) null);
                    }
                    C0829lq[] c0829lqArr6 = this.f46041k5;
                    if (c0829lqArr6 == null || c0829lqArr6.length < iCeil) {
                        this.f46041k5 = new C0829lq[iCeil];
                    } else {
                        Arrays.fill(c0829lqArr6, obj);
                    }
                    for (int i59 = 0; i59 < iCeil2; i59++) {
                        for (int i60 = 0; i60 < iCeil; i60++) {
                            int i61 = (i60 * iCeil2) + i59;
                            if (i52 == 1) {
                                i61 = (i59 * iCeil) + i60;
                            }
                            if (i61 < c0829lqArr.length && (c0829lq = c0829lqArr[i61]) != null) {
                                int iM210754f03 = m210754f0(c0829lq, i26);
                                C0829lq c0829lq9 = this.f46042k6[i59];
                                if (c0829lq9 == null || c0829lq9.m213891b7() < iM210754f03) {
                                    this.f46042k6[i59] = c0829lq;
                                }
                                int iM210753e93 = m210753e9(c0829lq, i26);
                                C0829lq c0829lq10 = this.f46041k5[i60];
                                if (c0829lq10 == null || c0829lq10.m213887b1() < iM210753e93) {
                                    this.f46041k5[i60] = c0829lq;
                                }
                            }
                        }
                    }
                    int iM210754f04 = 0;
                    for (int i62 = 0; i62 < iCeil2; i62++) {
                        C0829lq c0829lq11 = this.f46042k6[i62];
                        if (c0829lq11 != null) {
                            if (i62 > 0) {
                                iM210754f04 += this.f46033j7;
                            }
                            iM210754f04 = m210754f0(c0829lq11, i26) + iM210754f04;
                        }
                    }
                    int iM210753e94 = 0;
                    for (int i63 = 0; i63 < iCeil; i63++) {
                        C0829lq c0829lq12 = this.f46041k5[i63];
                        if (c0829lq12 != null) {
                            if (i63 > 0) {
                                iM210753e94 += this.f46034j8;
                            }
                            iM210753e94 = m210753e9(c0829lq12, i26) + iM210753e94;
                        }
                    }
                    iArr[0] = iM210754f04;
                    iArr[1] = iM210753e94;
                    if (i52 == 0) {
                        if (iM210754f04 <= i26 || iCeil2 <= 1) {
                            z4 = true;
                        } else {
                            iCeil2--;
                        }
                    } else if (iM210753e94 <= i26 || iCeil <= 1) {
                        z4 = true;
                    } else {
                        iCeil--;
                    }
                }
                int[] iArr3 = this.f46043k7;
                iArr3[0] = iCeil2;
                iArr3[1] = iCeil;
                c = 1;
            } else if (i33 != 3) {
                i6 = i21;
                i7 = i22;
                i8 = i23;
                iArr = iArr2;
                i9 = i32;
            } else {
                int i64 = this.f46039k3;
                if (i5 != 0) {
                    arrayList.clear();
                    iArr = iArr2;
                    i8 = i23;
                    i9 = i32;
                    i6 = i21;
                    i7 = i22;
                    C0116b c0116b5 = new C0116b(this, i64, this.f58096d5, this.f58097d6, this.f58098d7, this.f58099d8, i26);
                    arrayList.add(c0116b5);
                    if (i64 == 0) {
                        int i65 = 0;
                        int i66 = 0;
                        i14 = 0;
                        int i67 = 0;
                        while (i65 < i5) {
                            int i68 = i66 + 1;
                            C0829lq c0829lq13 = c0829lqArr[i65];
                            int iM210754f05 = m210754f0(c0829lq13, i26);
                            int i69 = i64;
                            if (c0829lq13.f58107e6[0] == constraintWidget$DimensionBehaviour) {
                                i14++;
                            }
                            int i70 = i14;
                            boolean z5 = (i67 == i26 || (this.f46033j7 + i67) + iM210754f05 > i26) && c0116b5.f45657a1 != null;
                            if (!z5 && i65 > 0 && (i17 = this.f46038k2) > 0 && i68 > i17) {
                                z5 = true;
                            }
                            if (z5) {
                                i16 = i65;
                                i64 = i69;
                                c0116b5 = new C0116b(this, i64, this.f58096d5, this.f58097d6, this.f58098d7, this.f58099d8, i26);
                                c0116b5.f45669b3 = i16;
                                arrayList.add(c0116b5);
                                i67 = iM210754f05;
                                i66 = i68;
                            } else {
                                i16 = i65;
                                i64 = i69;
                                i67 = i16 > 0 ? this.f46033j7 + iM210754f05 + i67 : iM210754f05;
                                i66 = 0;
                            }
                            c0116b5.m210525a0(c0829lq13);
                            i65 = i16 + 1;
                            i14 = i70;
                        }
                    } else {
                        int i71 = 0;
                        int i72 = 0;
                        int i73 = 0;
                        while (i73 < i5) {
                            C0829lq c0829lq14 = c0829lqArr[i73];
                            int iM210753e95 = m210753e9(c0829lq14, i26);
                            if (c0829lq14.f58107e6[1] == constraintWidget$DimensionBehaviour) {
                                i71++;
                            }
                            int i74 = i71;
                            boolean z6 = (i72 == i26 || (this.f46034j8 + i72) + iM210753e95 > i26) && c0116b5.f45657a1 != null;
                            if (!z6 && i73 > 0 && (i15 = this.f46038k2) > 0 && i15 < 0) {
                                z6 = true;
                            }
                            if (z6) {
                                c0116b5 = new C0116b(this, i64, this.f58096d5, this.f58097d6, this.f58098d7, this.f58099d8, i26);
                                c0116b5.f45669b3 = i73;
                                arrayList.add(c0116b5);
                            } else {
                                if (i73 > 0) {
                                    i72 = this.f46034j8 + iM210753e95 + i72;
                                }
                                c0116b5.m210525a0(c0829lq14);
                                i73++;
                                i71 = i74;
                            }
                            i72 = iM210753e95;
                            c0116b5.m210525a0(c0829lq14);
                            i73++;
                            i71 = i74;
                        }
                        i14 = i71;
                    }
                    int size2 = arrayList.size();
                    int i75 = this.f58339h8;
                    int i76 = this.f58335h4;
                    int i77 = this.f58340h9;
                    int i78 = this.f58336h5;
                    ConstraintWidget$DimensionBehaviour[] constraintWidget$DimensionBehaviourArr2 = this.f58107e6;
                    boolean z7 = constraintWidget$DimensionBehaviourArr2[0] == constraintWidget$DimensionBehaviour2 || constraintWidget$DimensionBehaviourArr2[1] == constraintWidget$DimensionBehaviour2;
                    if (i14 > 0 && z7) {
                        for (int i79 = 0; i79 < size2; i79++) {
                            C0116b c0116b6 = (C0116b) arrayList.get(i79);
                            if (i64 == 0) {
                                c0116b6.m210529a4(i26 - c0116b6.m210528a3());
                            } else {
                                c0116b6.m210529a4(i26 - c0116b6.m210527a2());
                            }
                        }
                    }
                    int i80 = i75;
                    int i81 = i76;
                    int i82 = i77;
                    int i83 = i78;
                    C0797kv c0797kv11 = c0797kv;
                    C0797kv c0797kv12 = c0797kv2;
                    int iMax3 = 0;
                    int i84 = 0;
                    C0797kv c0797kv13 = c0797kv3;
                    C0797kv c0797kv14 = c0797kv4;
                    for (int i85 = 0; i85 < size2; i85++) {
                        C0116b c0116b7 = (C0116b) arrayList.get(i85);
                        if (i64 == 0) {
                            if (i85 < size2 - 1) {
                                c0797kv14 = ((C0116b) arrayList.get(i85 + 1)).f45657a1.f58097d6;
                                i83 = 0;
                            } else {
                                i83 = this.f58336h5;
                                c0797kv14 = c0797kv4;
                            }
                            C0797kv c0797kv15 = c0116b7.f45657a1.f58099d8;
                            c0116b7.m210530a5(i64, c0797kv12, c0797kv11, c0797kv13, c0797kv14, i80, i81, i82, i83, i26);
                            iMax3 = Math.max(iMax3, c0116b7.m210528a3());
                            int iM210527a22 = c0116b7.m210527a2() + i84;
                            if (i85 > 0) {
                                iM210527a22 += this.f46034j8;
                            }
                            i84 = iM210527a22;
                            c0797kv11 = c0797kv15;
                            i81 = 0;
                        } else {
                            if (i85 < size2 - 1) {
                                c0797kv13 = ((C0116b) arrayList.get(i85 + 1)).f45657a1.f58096d5;
                                i82 = 0;
                            } else {
                                i82 = this.f58340h9;
                                c0797kv13 = c0797kv3;
                            }
                            C0797kv c0797kv16 = c0116b7.f45657a1.f58098d7;
                            c0116b7.m210530a5(i64, c0797kv12, c0797kv11, c0797kv13, c0797kv14, i80, i81, i82, i83, i26);
                            int iM210528a32 = c0116b7.m210528a3() + iMax3;
                            int iMax4 = Math.max(i84, c0116b7.m210527a2());
                            if (i85 > 0) {
                                iM210528a32 += this.f46033j7;
                            }
                            i84 = iMax4;
                            iMax3 = iM210528a32;
                            c0797kv12 = c0797kv16;
                            i80 = 0;
                        }
                    }
                    iArr[0] = iMax3;
                    iArr[1] = i84;
                }
            }
            c = 1;
        } else {
            i6 = i21;
            i7 = i22;
            i8 = i23;
            iArr = iArr2;
            i9 = i32;
            int i86 = this.f46039k3;
            if (i5 == 0) {
                c = 1;
            } else {
                if (arrayList.size() == 0) {
                    c0116b = new C0116b(this, i86, this.f58096d5, this.f58097d6, this.f58098d7, this.f58099d8, i26);
                    arrayList.add(c0116b);
                } else {
                    C0116b c0116b8 = (C0116b) arrayList.get(0);
                    c0116b8.f45658a2 = 0;
                    c0116b8.f45657a1 = null;
                    c0116b8.f45667b1 = 0;
                    c0116b8.f45668b2 = 0;
                    c0116b8.f45669b3 = 0;
                    c0116b8.f45670b4 = 0;
                    c0116b8.f45671b5 = 0;
                    c0116b8.m210530a5(i86, this.f58096d5, this.f58097d6, this.f58098d7, this.f58099d8, this.f58339h8, this.f58335h4, this.f58340h9, this.f58336h5, i26);
                    c0116b = c0116b8;
                }
                for (int i87 = 0; i87 < i5; i87++) {
                    c0116b.m210525a0(c0829lqArr[i87]);
                }
                i29 = 0;
                iArr[0] = c0116b.m210528a3();
                c = 1;
                iArr[1] = c0116b.m210527a2();
            }
        }
        int iMin = iArr[i29] + i9 + i6;
        int iMin2 = iArr[c] + i7 + i8;
        if (i == 1073741824) {
            iMin = i2;
        } else {
            if (i != Integer.MIN_VALUE) {
                i10 = i3;
                if (i != 0) {
                    iMin = i29;
                }
                if (i10 != 1073741824) {
                    iMin2 = i4;
                } else if (i10 == Integer.MIN_VALUE) {
                    iMin2 = Math.min(iMin2, i4);
                } else if (i10 != 0) {
                    iMin2 = i29;
                }
                this.f58342i1 = iMin;
                this.f58343i2 = iMin2;
                m213911e1(iMin);
                m213908d8(iMin2);
                this.f58341i0 = this.f45712h3 <= 0 ? c : i29;
            }
            iMin = Math.min(iMin, i2);
        }
        i10 = i3;
        if (i10 != 1073741824) {
        }
        this.f58342i1 = iMin;
        this.f58343i2 = iMin2;
        m213911e1(iMin);
        m213908d8(iMin2);
        this.f58341i0 = this.f45712h3 <= 0 ? c : i29;
    }

    /* renamed from: e9 */
    public final int m210753e9(C0829lq c0829lq, int i) {
        C0829lq c0829lq2;
        if (c0829lq == null) {
            return 0;
        }
        if (c0829lq.f58107e6[1] == ConstraintWidget$DimensionBehaviour.f44426a2) {
            int i2 = c0829lq.f58079b8;
            if (i2 == 0) {
                return 0;
            }
            if (i2 == 2) {
                int i3 = (int) (c0829lq.f58086c5 * i);
                if (i3 != c0829lq.m213887b1()) {
                    c0829lq.f58067a6 = true;
                    m213972e8(c0829lq, c0829lq.f58107e6[0], c0829lq.m213891b7(), ConstraintWidget$DimensionBehaviour.f44424a0, i3);
                }
                return i3;
            }
            c0829lq2 = c0829lq;
            if (i2 == 1) {
                return c0829lq2.m213887b1();
            }
            if (i2 == 3) {
                return (int) ((c0829lq2.m213891b7() * c0829lq2.f58111f0) + 0.5f);
            }
        } else {
            c0829lq2 = c0829lq;
        }
        return c0829lq2.m213887b1();
    }

    /* renamed from: f0 */
    public final int m210754f0(C0829lq c0829lq, int i) {
        C0829lq c0829lq2;
        if (c0829lq == null) {
            return 0;
        }
        if (c0829lq.f58107e6[0] == ConstraintWidget$DimensionBehaviour.f44426a2) {
            int i2 = c0829lq.f58078b7;
            if (i2 == 0) {
                return 0;
            }
            if (i2 == 2) {
                int i3 = (int) (c0829lq.f58083c2 * i);
                if (i3 != c0829lq.m213891b7()) {
                    c0829lq.f58067a6 = true;
                    m213972e8(c0829lq, ConstraintWidget$DimensionBehaviour.f44424a0, i3, c0829lq.f58107e6[1], c0829lq.m213887b1());
                }
                return i3;
            }
            c0829lq2 = c0829lq;
            if (i2 == 1) {
                return c0829lq2.m213891b7();
            }
            if (i2 == 3) {
                return (int) ((c0829lq2.m213887b1() * c0829lq2.f58111f0) + 0.5f);
            }
        } else {
            c0829lq2 = c0829lq;
        }
        return c0829lq2.m213891b7();
    }
}
