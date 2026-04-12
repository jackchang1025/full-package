package p000;

import androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour;
import androidx.constraintlayout.core.widgets.analyzer.AbstractC0055a5;
import androidx.constraintlayout.core.widgets.analyzer.C0050a0;
import androidx.constraintlayout.core.widgets.analyzer.C0051a1;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: gs */
/* loaded from: classes.dex */
public final class C0555gs extends AbstractC0055a5 {

    /* renamed from: b0 */
    public final ArrayList f56568b0;

    /* renamed from: b1 */
    public int f56569b1;

    public C0555gs(C0829lq c0829lq, int i) {
        C0829lq c0829lq2;
        super(c0829lq);
        ArrayList arrayList = new ArrayList();
        this.f56568b0 = arrayList;
        this.f44462a5 = i;
        C0829lq c0829lq3 = this.f44458a1;
        C0829lq c0829lqM213889b3 = c0829lq3.m213889b3(i);
        while (true) {
            c0829lq2 = c0829lq3;
            c0829lq3 = c0829lqM213889b3;
            if (c0829lq3 == null) {
                break;
            } else {
                c0829lqM213889b3 = c0829lq3.m213889b3(this.f44462a5);
            }
        }
        this.f44458a1 = c0829lq2;
        int i2 = this.f44462a5;
        arrayList.add(i2 == 0 ? c0829lq2.f58064a3 : i2 == 1 ? c0829lq2.f58065a4 : null);
        C0829lq c0829lqM213888b2 = c0829lq2.m213888b2(this.f44462a5);
        while (c0829lqM213888b2 != null) {
            int i3 = this.f44462a5;
            arrayList.add(i3 == 0 ? c0829lqM213888b2.f58064a3 : i3 == 1 ? c0829lqM213888b2.f58065a4 : null);
            c0829lqM213888b2 = c0829lqM213888b2.m213888b2(this.f44462a5);
        }
        int size = arrayList.size();
        int i4 = 0;
        while (i4 < size) {
            Object obj = arrayList.get(i4);
            i4++;
            AbstractC0055a5 abstractC0055a5 = (AbstractC0055a5) obj;
            int i5 = this.f44462a5;
            if (i5 == 0) {
                abstractC0055a5.f44458a1.f58062a1 = this;
            } else if (i5 == 1) {
                abstractC0055a5.f44458a1.f58063a2 = this;
            }
        }
        if (this.f44462a5 == 0 && ((C0830lr) this.f44458a1.f58108e7).f58144h7 && arrayList.size() > 1) {
            this.f44458a1 = ((AbstractC0055a5) arrayList.get(arrayList.size() - 1)).f44458a1;
        }
        this.f56569b1 = this.f44462a5 == 0 ? this.f44458a1.f58124g3 : this.f44458a1.f58125g4;
    }

    /* JADX WARN: Removed duplicated region for block: B:62:0x00d1  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00e2  */
    @Override // p000.InterfaceC1215sa
    /* renamed from: a0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void mo209948a0(InterfaceC1215sa interfaceC1215sa) {
        int i;
        int i2;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour;
        boolean z;
        float f;
        int i3;
        int i4;
        int i5;
        int i6;
        float f2;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        boolean z2;
        int i15;
        int i16;
        C0050a0 c0050a0 = this.f44464a7;
        if (c0050a0.f44450a9) {
            C0050a0 c0050a02 = this.f44465a8;
            if (c0050a02.f44450a9) {
                C0829lq c0829lq = this.f44458a1.f58108e7;
                boolean z3 = c0829lq instanceof C0830lr ? ((C0830lr) c0829lq).f58144h7 : false;
                int i17 = c0050a02.f44447a6 - c0050a0.f44447a6;
                ArrayList arrayList = this.f56568b0;
                int size = arrayList.size();
                int i18 = 0;
                while (true) {
                    i = -1;
                    i2 = 8;
                    if (i18 >= size) {
                        i18 = -1;
                        break;
                    } else if (((AbstractC0055a5) arrayList.get(i18)).f44458a1.f58121g0 != 8) {
                        break;
                    } else {
                        i18++;
                    }
                }
                int i19 = size - 1;
                int i20 = i19;
                while (true) {
                    if (i20 < 0) {
                        break;
                    }
                    if (((AbstractC0055a5) arrayList.get(i20)).f44458a1.f58121g0 != 8) {
                        i = i20;
                        break;
                    }
                    i20--;
                }
                int i21 = 0;
                while (true) {
                    constraintWidget$DimensionBehaviour = ConstraintWidget$DimensionBehaviour.f44426a2;
                    if (i21 >= 2) {
                        z = z3;
                        f = 0.0f;
                        i3 = 0;
                        i4 = 0;
                        i5 = 0;
                        break;
                    }
                    f = 0.0f;
                    int i22 = 0;
                    i5 = 0;
                    i13 = 0;
                    i14 = 0;
                    while (i22 < size) {
                        AbstractC0055a5 abstractC0055a5 = (AbstractC0055a5) arrayList.get(i22);
                        boolean z4 = z3;
                        C0829lq c0829lq2 = abstractC0055a5.f44458a1;
                        int i23 = i21;
                        if (c0829lq2.f58121g0 != i2) {
                            i14++;
                            if (i22 > 0 && i22 >= i18) {
                                i5 += abstractC0055a5.f44464a7.f44446a5;
                            }
                            C0051a1 c0051a1 = abstractC0055a5.f44461a4;
                            int i24 = c0051a1.f44447a6;
                            boolean z5 = abstractC0055a5.f44460a3 != constraintWidget$DimensionBehaviour;
                            if (z5) {
                                int i25 = this.f44462a5;
                                z2 = z5;
                                if (i25 == 0 && !c0829lq2.f58064a3.f44461a4.f44450a9) {
                                    return;
                                }
                                if (i25 == 1 && !c0829lq2.f58065a4.f44461a4.f44450a9) {
                                    return;
                                } else {
                                    i15 = i5;
                                }
                            } else {
                                z2 = z5;
                                i15 = i5;
                                if (abstractC0055a5.f44457a0 == 1 && i23 == 0) {
                                    i16 = c0051a1.f44453b2;
                                    i13++;
                                } else if (c0051a1.f44450a9) {
                                    i16 = i24;
                                }
                                z2 = true;
                                if (z2) {
                                    i13++;
                                    float f3 = c0829lq2.f58126g5[this.f44462a5];
                                    if (f3 >= 0.0f) {
                                        f += f3;
                                    }
                                    i5 = i15;
                                } else {
                                    i5 = i15 + i16;
                                }
                                if (i22 >= i19 && i22 < i) {
                                    i5 += -abstractC0055a5.f44465a8.f44446a5;
                                }
                            }
                            i16 = i24;
                            if (z2) {
                            }
                            if (i22 >= i19) {
                            }
                        }
                        i22++;
                        z3 = z4;
                        i21 = i23;
                        i2 = 8;
                    }
                    z = z3;
                    int i26 = i21;
                    if (i5 < i17 || i13 == 0) {
                        break;
                    }
                    i21 = i26 + 1;
                    z3 = z;
                    i2 = 8;
                }
                i3 = i13;
                i4 = i14;
                int i27 = c0050a0.f44447a6;
                if (z) {
                    i27 = c0050a02.f44447a6;
                }
                float f4 = 0.5f;
                if (i5 > i17) {
                    i27 = z ? i27 + ((int) (((i5 - i17) / 2.0f) + 0.5f)) : i27 - ((int) (((i5 - i17) / 2.0f) + 0.5f));
                }
                if (i3 > 0) {
                    float f5 = i17 - i5;
                    int i28 = (int) ((f5 / i3) + 0.5f);
                    int i29 = 0;
                    int i30 = 0;
                    while (i29 < size) {
                        float f6 = f4;
                        AbstractC0055a5 abstractC0055a52 = (AbstractC0055a5) arrayList.get(i29);
                        int i31 = i27;
                        C0829lq c0829lq3 = abstractC0055a52.f44458a1;
                        int i32 = i3;
                        C0051a1 c0051a12 = abstractC0055a52.f44461a4;
                        float f7 = f5;
                        int i33 = i28;
                        if (c0829lq3.f58121g0 == 8 || abstractC0055a52.f44460a3 != constraintWidget$DimensionBehaviour || c0051a12.f44450a9) {
                            i12 = i29;
                        } else {
                            int i34 = f > 0.0f ? (int) (((c0829lq3.f58126g5[this.f44462a5] * f7) / f) + f6) : i33;
                            if (this.f44462a5 == 0) {
                                i10 = c0829lq3.f58082c1;
                                i11 = c0829lq3.f58081c0;
                            } else {
                                i10 = c0829lq3.f58085c4;
                                i11 = c0829lq3.f58084c3;
                            }
                            i12 = i29;
                            int iMax = Math.max(i11, abstractC0055a52.f44457a0 == 1 ? Math.min(i34, c0051a12.f44453b2) : i34);
                            if (i10 > 0) {
                                iMax = Math.min(i10, iMax);
                            }
                            if (iMax != i34) {
                                i30++;
                                i34 = iMax;
                            }
                            c0051a12.mo209951a3(i34);
                        }
                        i29 = i12 + 1;
                        i27 = i31;
                        f4 = f6;
                        i3 = i32;
                        f5 = f7;
                        i28 = i33;
                    }
                    i6 = i27;
                    f2 = f4;
                    int i35 = i3;
                    if (i30 > 0) {
                        i3 = i35 - i30;
                        i5 = 0;
                        for (int i36 = 0; i36 < size; i36++) {
                            AbstractC0055a5 abstractC0055a53 = (AbstractC0055a5) arrayList.get(i36);
                            if (abstractC0055a53.f44458a1.f58121g0 != 8) {
                                if (i36 > 0 && i36 >= i18) {
                                    i5 += abstractC0055a53.f44464a7.f44446a5;
                                }
                                i5 += abstractC0055a53.f44461a4.f44447a6;
                                if (i36 < i19 && i36 < i) {
                                    i5 += -abstractC0055a53.f44465a8.f44446a5;
                                }
                            }
                        }
                    } else {
                        i3 = i35;
                    }
                    i8 = 2;
                    if (this.f56569b1 == 2 && i30 == 0) {
                        i7 = 0;
                        this.f56569b1 = 0;
                    } else {
                        i7 = 0;
                    }
                } else {
                    i6 = i27;
                    f2 = 0.5f;
                    i7 = 0;
                    i8 = 2;
                }
                if (i5 > i17) {
                    this.f56569b1 = i8;
                }
                if (i4 > 0 && i3 == 0 && i18 == i) {
                    this.f56569b1 = i8;
                }
                int i37 = this.f56569b1;
                if (i37 == 1) {
                    int i38 = i4 > 1 ? (i17 - i5) / (i4 - 1) : i4 == 1 ? (i17 - i5) / 2 : i7;
                    if (i3 > 0) {
                        i38 = i7;
                    }
                    int i39 = i6;
                    for (int i40 = i7; i40 < size; i40++) {
                        AbstractC0055a5 abstractC0055a54 = (AbstractC0055a5) arrayList.get(z ? size - (i40 + 1) : i40);
                        C0829lq c0829lq4 = abstractC0055a54.f44458a1;
                        C0050a0 c0050a03 = abstractC0055a54.f44465a8;
                        C0050a0 c0050a04 = abstractC0055a54.f44464a7;
                        if (c0829lq4.f58121g0 == 8) {
                            c0050a04.mo209951a3(i39);
                            c0050a03.mo209951a3(i39);
                        } else {
                            if (i40 > 0) {
                                i39 = z ? i39 - i38 : i39 + i38;
                            }
                            if (i40 > 0 && i40 >= i18) {
                                i39 = z ? i39 - c0050a04.f44446a5 : i39 + c0050a04.f44446a5;
                            }
                            if (z) {
                                c0050a03.mo209951a3(i39);
                            } else {
                                c0050a04.mo209951a3(i39);
                            }
                            C0051a1 c0051a13 = abstractC0055a54.f44461a4;
                            int i41 = c0051a13.f44447a6;
                            if (abstractC0055a54.f44460a3 == constraintWidget$DimensionBehaviour) {
                                i9 = i39;
                                if (abstractC0055a54.f44457a0 == 1) {
                                    i41 = c0051a13.f44453b2;
                                }
                            } else {
                                i9 = i39;
                            }
                            i39 = z ? i9 - i41 : i9 + i41;
                            if (z) {
                                c0050a04.mo209951a3(i39);
                            } else {
                                c0050a03.mo209951a3(i39);
                            }
                            abstractC0055a54.f44463a6 = true;
                            if (i40 < i19 && i40 < i) {
                                i39 = z ? i39 - (-c0050a03.f44446a5) : i39 + (-c0050a03.f44446a5);
                            }
                        }
                    }
                    return;
                }
                if (i37 == 0) {
                    int i42 = (i17 - i5) / (i4 + 1);
                    if (i3 > 0) {
                        i42 = i7;
                    }
                    int i43 = i6;
                    for (int i44 = i7; i44 < size; i44++) {
                        AbstractC0055a5 abstractC0055a55 = (AbstractC0055a5) arrayList.get(z ? size - (i44 + 1) : i44);
                        C0829lq c0829lq5 = abstractC0055a55.f44458a1;
                        C0050a0 c0050a05 = abstractC0055a55.f44465a8;
                        C0050a0 c0050a06 = abstractC0055a55.f44464a7;
                        if (c0829lq5.f58121g0 == 8) {
                            c0050a06.mo209951a3(i43);
                            c0050a05.mo209951a3(i43);
                        } else {
                            int i45 = z ? i43 - i42 : i43 + i42;
                            if (i44 > 0 && i44 >= i18) {
                                i45 = z ? i45 - c0050a06.f44446a5 : i45 + c0050a06.f44446a5;
                            }
                            if (z) {
                                c0050a05.mo209951a3(i45);
                            } else {
                                c0050a06.mo209951a3(i45);
                            }
                            C0051a1 c0051a14 = abstractC0055a55.f44461a4;
                            int iMin = c0051a14.f44447a6;
                            if (abstractC0055a55.f44460a3 == constraintWidget$DimensionBehaviour && abstractC0055a55.f44457a0 == 1) {
                                iMin = Math.min(iMin, c0051a14.f44453b2);
                            }
                            i43 = z ? i45 - iMin : i45 + iMin;
                            if (z) {
                                c0050a06.mo209951a3(i43);
                            } else {
                                c0050a05.mo209951a3(i43);
                            }
                            if (i44 < i19 && i44 < i) {
                                i43 = z ? i43 - (-c0050a05.f44446a5) : i43 + (-c0050a05.f44446a5);
                            }
                        }
                    }
                    return;
                }
                if (i37 == 2) {
                    float f8 = this.f44462a5 == 0 ? this.f44458a1.f58118f7 : this.f44458a1.f58119f8;
                    if (z) {
                        f8 = 1.0f - f8;
                    }
                    int i46 = (int) (((i17 - i5) * f8) + f2);
                    if (i46 < 0 || i3 > 0) {
                        i46 = i7;
                    }
                    int i47 = z ? i6 - i46 : i6 + i46;
                    for (int i48 = i7; i48 < size; i48++) {
                        AbstractC0055a5 abstractC0055a56 = (AbstractC0055a5) arrayList.get(z ? size - (i48 + 1) : i48);
                        C0829lq c0829lq6 = abstractC0055a56.f44458a1;
                        C0050a0 c0050a07 = abstractC0055a56.f44465a8;
                        C0050a0 c0050a08 = abstractC0055a56.f44464a7;
                        if (c0829lq6.f58121g0 == 8) {
                            c0050a08.mo209951a3(i47);
                            c0050a07.mo209951a3(i47);
                        } else {
                            if (i48 > 0 && i48 >= i18) {
                                i47 = z ? i47 - c0050a08.f44446a5 : i47 + c0050a08.f44446a5;
                            }
                            if (z) {
                                c0050a07.mo209951a3(i47);
                            } else {
                                c0050a08.mo209951a3(i47);
                            }
                            C0051a1 c0051a15 = abstractC0055a56.f44461a4;
                            int i49 = c0051a15.f44447a6;
                            if (abstractC0055a56.f44460a3 == constraintWidget$DimensionBehaviour && abstractC0055a56.f44457a0 == 1) {
                                i49 = c0051a15.f44453b2;
                            }
                            i47 = z ? i47 - i49 : i47 + i49;
                            if (z) {
                                c0050a08.mo209951a3(i47);
                            } else {
                                c0050a07.mo209951a3(i47);
                            }
                            if (i48 < i19 && i48 < i) {
                                i47 = z ? i47 - (-c0050a07.f44446a5) : i47 + (-c0050a07.f44446a5);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.AbstractC0055a5
    /* renamed from: a3 */
    public final void mo209952a3() {
        ArrayList arrayList = this.f56568b0;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((AbstractC0055a5) obj).mo209952a3();
        }
        int size2 = arrayList.size();
        if (size2 < 1) {
            return;
        }
        C0829lq c0829lq = ((AbstractC0055a5) arrayList.get(0)).f44458a1;
        C0829lq c0829lq2 = ((AbstractC0055a5) arrayList.get(size2 - 1)).f44458a1;
        int i2 = this.f44462a5;
        C0050a0 c0050a0 = this.f44465a8;
        C0050a0 c0050a02 = this.f44464a7;
        if (i2 == 0) {
            C0797kv c0797kv = c0829lq.f58096d5;
            C0797kv c0797kv2 = c0829lq2.f58098d7;
            C0050a0 c0050a0M209962a8 = AbstractC0055a5.m209962a8(c0797kv, 0);
            int iM213750a4 = c0797kv.m213750a4();
            C0829lq c0829lqM212981b2 = m212981b2();
            if (c0829lqM212981b2 != null) {
                iM213750a4 = c0829lqM212981b2.f58096d5.m213750a4();
            }
            if (c0050a0M209962a8 != null) {
                AbstractC0055a5.m209960a1(c0050a02, c0050a0M209962a8, iM213750a4);
            }
            C0050a0 c0050a0M209962a82 = AbstractC0055a5.m209962a8(c0797kv2, 0);
            int iM213750a42 = c0797kv2.m213750a4();
            C0829lq c0829lqM212982b3 = m212982b3();
            if (c0829lqM212982b3 != null) {
                iM213750a42 = c0829lqM212982b3.f58098d7.m213750a4();
            }
            if (c0050a0M209962a82 != null) {
                AbstractC0055a5.m209960a1(c0050a0, c0050a0M209962a82, -iM213750a42);
            }
        } else {
            C0797kv c0797kv3 = c0829lq.f58097d6;
            C0797kv c0797kv4 = c0829lq2.f58099d8;
            C0050a0 c0050a0M209962a83 = AbstractC0055a5.m209962a8(c0797kv3, 1);
            int iM213750a43 = c0797kv3.m213750a4();
            C0829lq c0829lqM212981b22 = m212981b2();
            if (c0829lqM212981b22 != null) {
                iM213750a43 = c0829lqM212981b22.f58097d6.m213750a4();
            }
            if (c0050a0M209962a83 != null) {
                AbstractC0055a5.m209960a1(c0050a02, c0050a0M209962a83, iM213750a43);
            }
            C0050a0 c0050a0M209962a84 = AbstractC0055a5.m209962a8(c0797kv4, 1);
            int iM213750a44 = c0797kv4.m213750a4();
            C0829lq c0829lqM212982b32 = m212982b3();
            if (c0829lqM212982b32 != null) {
                iM213750a44 = c0829lqM212982b32.f58099d8.m213750a4();
            }
            if (c0050a0M209962a84 != null) {
                AbstractC0055a5.m209960a1(c0050a0, c0050a0M209962a84, -iM213750a44);
            }
        }
        c0050a02.f44441a0 = this;
        c0050a0.f44441a0 = this;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.AbstractC0055a5
    /* renamed from: a4 */
    public final void mo209953a4() {
        int i = 0;
        while (true) {
            ArrayList arrayList = this.f56568b0;
            if (i >= arrayList.size()) {
                return;
            }
            ((AbstractC0055a5) arrayList.get(i)).mo209953a4();
            i++;
        }
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.AbstractC0055a5
    /* renamed from: a5 */
    public final void mo209954a5() {
        this.f44459a2 = null;
        ArrayList arrayList = this.f56568b0;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((AbstractC0055a5) obj).mo209954a5();
        }
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.AbstractC0055a5
    /* renamed from: a9 */
    public final long mo209965a9() {
        ArrayList arrayList = this.f56568b0;
        int size = arrayList.size();
        long jMo209965a9 = 0;
        for (int i = 0; i < size; i++) {
            jMo209965a9 = r5.f44465a8.f44446a5 + ((AbstractC0055a5) arrayList.get(i)).mo209965a9() + jMo209965a9 + r5.f44464a7.f44446a5;
        }
        return jMo209965a9;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.AbstractC0055a5
    /* renamed from: b0 */
    public final boolean mo209955b0() {
        ArrayList arrayList = this.f56568b0;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (!((AbstractC0055a5) arrayList.get(i)).mo209955b0()) {
                return false;
            }
        }
        return true;
    }

    /* renamed from: b2 */
    public final C0829lq m212981b2() {
        int i = 0;
        while (true) {
            ArrayList arrayList = this.f56568b0;
            if (i >= arrayList.size()) {
                return null;
            }
            C0829lq c0829lq = ((AbstractC0055a5) arrayList.get(i)).f44458a1;
            if (c0829lq.f58121g0 != 8) {
                return c0829lq;
            }
            i++;
        }
    }

    /* renamed from: b3 */
    public final C0829lq m212982b3() {
        ArrayList arrayList = this.f56568b0;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            C0829lq c0829lq = ((AbstractC0055a5) arrayList.get(size)).f44458a1;
            if (c0829lq.f58121g0 != 8) {
                return c0829lq;
            }
        }
        return null;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ChainRun ");
        sb.append(this.f44462a5 == 0 ? "horizontal : " : "vertical : ");
        ArrayList arrayList = this.f56568b0;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            sb.append("<");
            sb.append((AbstractC0055a5) obj);
            sb.append("> ");
        }
        return sb.toString();
    }
}
