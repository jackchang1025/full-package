package p000;

import androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour;
import androidx.constraintlayout.core.widgets.analyzer.AbstractC0055a5;
import androidx.constraintlayout.core.widgets.analyzer.C0050a0;
import androidx.constraintlayout.core.widgets.analyzer.C0051a1;
import androidx.constraintlayout.core.widgets.analyzer.C0052a2;
import androidx.constraintlayout.core.widgets.analyzer.C0053a3;
import androidx.constraintlayout.core.widgets.analyzer.C0054a4;
import java.util.ArrayList;
import java.util.HashSet;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: sd */
/* loaded from: classes.dex */
public final class C1218sd {

    /* renamed from: a0 */
    public C0830lr f59955a0;

    /* renamed from: a1 */
    public boolean f59956a1;

    /* renamed from: a2 */
    public boolean f59957a2;

    /* renamed from: a3 */
    public C0830lr f59958a3;

    /* renamed from: a4 */
    public ArrayList f59959a4;

    /* renamed from: a5 */
    public C0813la f59960a5;

    /* renamed from: a6 */
    public C0418dj f59961a6;

    /* renamed from: a7 */
    public ArrayList f59962a7;

    /* renamed from: a0 */
    public final void m214600a0(C0050a0 c0050a0, int i, ArrayList arrayList, ps0 ps0Var) {
        AbstractC0055a5 abstractC0055a5 = c0050a0.f44444a3;
        ps0 ps0Var2 = abstractC0055a5.f44459a2;
        C0050a0 c0050a02 = abstractC0055a5.f44465a8;
        C0050a0 c0050a03 = abstractC0055a5.f44464a7;
        if (ps0Var2 == null) {
            C0830lr c0830lr = this.f59955a0;
            if (abstractC0055a5 == c0830lr.f58064a3 || abstractC0055a5 == c0830lr.f58065a4) {
                return;
            }
            if (ps0Var == null) {
                ps0Var = new ps0();
                ps0Var.f59333a0 = null;
                ps0Var.f59334a1 = new ArrayList();
                ps0Var.f59333a0 = abstractC0055a5;
                arrayList.add(ps0Var);
            }
            abstractC0055a5.f44459a2 = ps0Var;
            ps0Var.f59334a1.add(abstractC0055a5);
            ArrayList arrayList2 = c0050a03.f44451b0;
            int size = arrayList2.size();
            int i2 = 0;
            int i3 = 0;
            while (i3 < size) {
                Object obj = arrayList2.get(i3);
                i3++;
                InterfaceC1215sa interfaceC1215sa = (InterfaceC1215sa) obj;
                if (interfaceC1215sa instanceof C0050a0) {
                    m214600a0((C0050a0) interfaceC1215sa, i, arrayList, ps0Var);
                }
            }
            ArrayList arrayList3 = c0050a02.f44451b0;
            int size2 = arrayList3.size();
            int i4 = 0;
            while (i4 < size2) {
                Object obj2 = arrayList3.get(i4);
                i4++;
                InterfaceC1215sa interfaceC1215sa2 = (InterfaceC1215sa) obj2;
                if (interfaceC1215sa2 instanceof C0050a0) {
                    m214600a0((C0050a0) interfaceC1215sa2, i, arrayList, ps0Var);
                }
            }
            if (i == 1 && (abstractC0055a5 instanceof C0054a4)) {
                ArrayList arrayList4 = ((C0054a4) abstractC0055a5).f44455b0.f44451b0;
                int size3 = arrayList4.size();
                int i5 = 0;
                while (i5 < size3) {
                    Object obj3 = arrayList4.get(i5);
                    i5++;
                    InterfaceC1215sa interfaceC1215sa3 = (InterfaceC1215sa) obj3;
                    if (interfaceC1215sa3 instanceof C0050a0) {
                        m214600a0((C0050a0) interfaceC1215sa3, i, arrayList, ps0Var);
                    }
                }
            }
            ArrayList arrayList5 = c0050a03.f44452b1;
            int size4 = arrayList5.size();
            int i6 = 0;
            while (i6 < size4) {
                Object obj4 = arrayList5.get(i6);
                i6++;
                m214600a0((C0050a0) obj4, i, arrayList, ps0Var);
            }
            ArrayList arrayList6 = c0050a02.f44452b1;
            int size5 = arrayList6.size();
            int i7 = 0;
            while (i7 < size5) {
                Object obj5 = arrayList6.get(i7);
                i7++;
                m214600a0((C0050a0) obj5, i, arrayList, ps0Var);
            }
            if (i == 1 && (abstractC0055a5 instanceof C0054a4)) {
                ArrayList arrayList7 = ((C0054a4) abstractC0055a5).f44455b0.f44452b1;
                int size6 = arrayList7.size();
                while (i2 < size6) {
                    Object obj6 = arrayList7.get(i2);
                    i2++;
                    m214600a0((C0050a0) obj6, i, arrayList, ps0Var);
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:132:0x02f2  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x0304  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x0316  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00bc A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00c0 A[ADDED_TO_REGION] */
    /* renamed from: a1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m214601a1(C0830lr c0830lr) {
        int i;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour2;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour3;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour4;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour5;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour6;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour7;
        int i2;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour8;
        ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour9;
        ArrayList arrayList = c0830lr.f58139h2;
        int size = arrayList.size();
        char c = 0;
        int i3 = 0;
        while (i3 < size) {
            Object obj = arrayList.get(i3);
            i3++;
            C0829lq c0829lq = (C0829lq) obj;
            ConstraintWidget$DimensionBehaviour[] constraintWidget$DimensionBehaviourArr = c0829lq.f58107e6;
            C0797kv[] c0797kvArr = c0829lq.f58104e3;
            C0797kv c0797kv = c0829lq.f58099d8;
            C0797kv c0797kv2 = c0829lq.f58097d6;
            C0797kv c0797kv3 = c0829lq.f58098d7;
            C0797kv c0797kv4 = c0829lq.f58096d5;
            ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour10 = constraintWidget$DimensionBehaviourArr[c];
            ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour11 = constraintWidget$DimensionBehaviourArr[1];
            if (c0829lq.f58121g0 == 8) {
                c0829lq.f58061a0 = true;
            } else {
                float f = c0829lq.f58083c2;
                char c2 = c;
                ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour12 = ConstraintWidget$DimensionBehaviour.f44426a2;
                if (f < 1.0f && constraintWidget$DimensionBehaviour10 == constraintWidget$DimensionBehaviour12) {
                    c0829lq.f58078b7 = 2;
                }
                float f2 = c0829lq.f58086c5;
                if (f2 < 1.0f && constraintWidget$DimensionBehaviour11 == constraintWidget$DimensionBehaviour12) {
                    c0829lq.f58079b8 = 2;
                }
                float f3 = c0829lq.f58111f0;
                ArrayList arrayList2 = arrayList;
                ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour13 = ConstraintWidget$DimensionBehaviour.f44425a1;
                int i4 = size;
                ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour14 = ConstraintWidget$DimensionBehaviour.f44424a0;
                if (f3 <= 0.0f) {
                    i = i3;
                    if (constraintWidget$DimensionBehaviour10 == constraintWidget$DimensionBehaviour12 && c0829lq.f58078b7 == 1 && (c0797kv4.f57726a5 == null || c0797kv3.f57726a5 == null)) {
                        constraintWidget$DimensionBehaviour10 = constraintWidget$DimensionBehaviour13;
                    }
                    if (constraintWidget$DimensionBehaviour11 == constraintWidget$DimensionBehaviour12 && c0829lq.f58079b8 == 1 && (c0797kv2.f57726a5 == null || c0797kv.f57726a5 == null)) {
                        constraintWidget$DimensionBehaviour11 = constraintWidget$DimensionBehaviour13;
                    }
                    C0053a3 c0053a3 = c0829lq.f58064a3;
                    c0053a3.f44460a3 = constraintWidget$DimensionBehaviour10;
                    int i5 = c0829lq.f58078b7;
                    c0053a3.f44457a0 = i5;
                    C0054a4 c0054a4 = c0829lq.f58065a4;
                    c0054a4.f44460a3 = constraintWidget$DimensionBehaviour11;
                    int i6 = c0829lq.f58079b8;
                    c0054a4.f44457a0 = i6;
                    constraintWidget$DimensionBehaviour = ConstraintWidget$DimensionBehaviour.f44427a3;
                    if ((constraintWidget$DimensionBehaviour10 == constraintWidget$DimensionBehaviour || constraintWidget$DimensionBehaviour10 == constraintWidget$DimensionBehaviour14 || constraintWidget$DimensionBehaviour10 == constraintWidget$DimensionBehaviour13) && (constraintWidget$DimensionBehaviour11 == constraintWidget$DimensionBehaviour || constraintWidget$DimensionBehaviour11 == constraintWidget$DimensionBehaviour14 || constraintWidget$DimensionBehaviour11 == constraintWidget$DimensionBehaviour13)) {
                        constraintWidget$DimensionBehaviour2 = constraintWidget$DimensionBehaviour10;
                        int iM213891b7 = c0829lq.m213891b7();
                        if (constraintWidget$DimensionBehaviour2 == constraintWidget$DimensionBehaviour) {
                            iM213891b7 = (c0830lr.m213891b7() - c0797kv4.f57727a6) - c0797kv3.f57727a6;
                            constraintWidget$DimensionBehaviour2 = constraintWidget$DimensionBehaviour14;
                        }
                        int iM213887b1 = c0829lq.m213887b1();
                        if (constraintWidget$DimensionBehaviour11 == constraintWidget$DimensionBehaviour) {
                            iM213887b1 = (c0830lr.m213887b1() - c0797kv2.f57727a6) - c0797kv.f57727a6;
                            constraintWidget$DimensionBehaviour3 = constraintWidget$DimensionBehaviour14;
                        } else {
                            constraintWidget$DimensionBehaviour3 = constraintWidget$DimensionBehaviour11;
                        }
                        m214605a5(c0829lq, constraintWidget$DimensionBehaviour2, iM213891b7, constraintWidget$DimensionBehaviour3, iM213887b1);
                        c0829lq.f58064a3.f44461a4.mo209951a3(c0829lq.m213891b7());
                        c0829lq.f58065a4.f44461a4.mo209951a3(c0829lq.m213887b1());
                        c0829lq.f58061a0 = true;
                    } else {
                        if (constraintWidget$DimensionBehaviour10 != constraintWidget$DimensionBehaviour12 || (constraintWidget$DimensionBehaviour11 != constraintWidget$DimensionBehaviour13 && constraintWidget$DimensionBehaviour11 != constraintWidget$DimensionBehaviour14)) {
                            constraintWidget$DimensionBehaviour4 = constraintWidget$DimensionBehaviour13;
                            constraintWidget$DimensionBehaviour5 = constraintWidget$DimensionBehaviour11;
                        } else if (i5 == 3) {
                            if (constraintWidget$DimensionBehaviour11 == constraintWidget$DimensionBehaviour13) {
                                m214605a5(c0829lq, constraintWidget$DimensionBehaviour13, 0, constraintWidget$DimensionBehaviour13, 0);
                            }
                            int iM213887b12 = c0829lq.m213887b1();
                            m214605a5(c0829lq, constraintWidget$DimensionBehaviour14, (int) ((iM213887b12 * c0829lq.f58111f0) + 0.5f), constraintWidget$DimensionBehaviour14, iM213887b12);
                            c0829lq.f58064a3.f44461a4.mo209951a3(c0829lq.m213891b7());
                            c0829lq.f58065a4.f44461a4.mo209951a3(c0829lq.m213887b1());
                            c0829lq.f58061a0 = true;
                        } else {
                            constraintWidget$DimensionBehaviour4 = constraintWidget$DimensionBehaviour13;
                            if (i5 == 1) {
                                m214605a5(c0829lq, constraintWidget$DimensionBehaviour4, 0, constraintWidget$DimensionBehaviour11, 0);
                                c0829lq.f58064a3.f44461a4.f44453b2 = c0829lq.m213891b7();
                            } else {
                                ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour15 = constraintWidget$DimensionBehaviour11;
                                if (i5 == 2) {
                                    ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour16 = c0830lr.f58107e6[c2];
                                    if (constraintWidget$DimensionBehaviour16 == constraintWidget$DimensionBehaviour14 || constraintWidget$DimensionBehaviour16 == constraintWidget$DimensionBehaviour) {
                                        m214605a5(c0829lq, constraintWidget$DimensionBehaviour14, (int) ((f * c0830lr.m213891b7()) + 0.5f), constraintWidget$DimensionBehaviour15, c0829lq.m213887b1());
                                        c0829lq.f58064a3.f44461a4.mo209951a3(c0829lq.m213891b7());
                                        c0829lq.f58065a4.f44461a4.mo209951a3(c0829lq.m213887b1());
                                        c0829lq.f58061a0 = true;
                                    } else {
                                        constraintWidget$DimensionBehaviour14 = constraintWidget$DimensionBehaviour14;
                                        constraintWidget$DimensionBehaviour5 = constraintWidget$DimensionBehaviour15;
                                    }
                                } else {
                                    constraintWidget$DimensionBehaviour14 = constraintWidget$DimensionBehaviour14;
                                    constraintWidget$DimensionBehaviour5 = constraintWidget$DimensionBehaviour15;
                                    if (c0797kvArr[c2].f57726a5 == null || c0797kvArr[1].f57726a5 == null) {
                                        m214605a5(c0829lq, constraintWidget$DimensionBehaviour4, 0, constraintWidget$DimensionBehaviour5, 0);
                                        c0829lq.f58064a3.f44461a4.mo209951a3(c0829lq.m213891b7());
                                        c0829lq.f58065a4.f44461a4.mo209951a3(c0829lq.m213887b1());
                                        c0829lq.f58061a0 = true;
                                    }
                                }
                            }
                        }
                        if (constraintWidget$DimensionBehaviour5 != constraintWidget$DimensionBehaviour12 || (constraintWidget$DimensionBehaviour10 != constraintWidget$DimensionBehaviour4 && constraintWidget$DimensionBehaviour10 != constraintWidget$DimensionBehaviour14)) {
                            constraintWidget$DimensionBehaviour6 = constraintWidget$DimensionBehaviour4;
                            constraintWidget$DimensionBehaviour7 = constraintWidget$DimensionBehaviour5;
                            i2 = 1;
                            constraintWidget$DimensionBehaviour8 = constraintWidget$DimensionBehaviour14;
                            constraintWidget$DimensionBehaviour9 = constraintWidget$DimensionBehaviour10;
                        } else if (i6 == 3) {
                            if (constraintWidget$DimensionBehaviour10 == constraintWidget$DimensionBehaviour4) {
                                m214605a5(c0829lq, constraintWidget$DimensionBehaviour4, 0, constraintWidget$DimensionBehaviour4, 0);
                            }
                            int iM213891b72 = c0829lq.m213891b7();
                            float f4 = c0829lq.f58111f0;
                            if (c0829lq.f58112f1 == -1) {
                                f4 = 1.0f / f4;
                            }
                            m214605a5(c0829lq, constraintWidget$DimensionBehaviour14, iM213891b72, constraintWidget$DimensionBehaviour14, (int) ((iM213891b72 * f4) + 0.5f));
                            c0829lq.f58064a3.f44461a4.mo209951a3(c0829lq.m213891b7());
                            c0829lq.f58065a4.f44461a4.mo209951a3(c0829lq.m213887b1());
                            c0829lq.f58061a0 = true;
                        } else if (i6 == 1) {
                            m214605a5(c0829lq, constraintWidget$DimensionBehaviour10, 0, constraintWidget$DimensionBehaviour4, 0);
                            c0829lq.f58065a4.f44461a4.f44453b2 = c0829lq.m213887b1();
                        } else {
                            constraintWidget$DimensionBehaviour6 = constraintWidget$DimensionBehaviour4;
                            ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour17 = constraintWidget$DimensionBehaviour10;
                            if (i6 == 2) {
                                ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour18 = c0830lr.f58107e6[1];
                                if (constraintWidget$DimensionBehaviour18 == constraintWidget$DimensionBehaviour14 || constraintWidget$DimensionBehaviour18 == constraintWidget$DimensionBehaviour) {
                                    m214605a5(c0829lq, constraintWidget$DimensionBehaviour17, c0829lq.m213891b7(), constraintWidget$DimensionBehaviour14, (int) ((f2 * c0830lr.m213887b1()) + 0.5f));
                                    c0829lq.f58064a3.f44461a4.mo209951a3(c0829lq.m213891b7());
                                    c0829lq.f58065a4.f44461a4.mo209951a3(c0829lq.m213887b1());
                                    c0829lq.f58061a0 = true;
                                } else {
                                    constraintWidget$DimensionBehaviour8 = constraintWidget$DimensionBehaviour14;
                                    constraintWidget$DimensionBehaviour9 = constraintWidget$DimensionBehaviour17;
                                    constraintWidget$DimensionBehaviour7 = constraintWidget$DimensionBehaviour5;
                                    i2 = 1;
                                }
                            } else {
                                constraintWidget$DimensionBehaviour8 = constraintWidget$DimensionBehaviour14;
                                constraintWidget$DimensionBehaviour9 = constraintWidget$DimensionBehaviour17;
                                if (c0797kvArr[2].f57726a5 == null || c0797kvArr[3].f57726a5 == null) {
                                    m214605a5(c0829lq, constraintWidget$DimensionBehaviour6, 0, constraintWidget$DimensionBehaviour5, 0);
                                    c0829lq.f58064a3.f44461a4.mo209951a3(c0829lq.m213891b7());
                                    c0829lq.f58065a4.f44461a4.mo209951a3(c0829lq.m213887b1());
                                    c0829lq.f58061a0 = true;
                                }
                                constraintWidget$DimensionBehaviour7 = constraintWidget$DimensionBehaviour5;
                                i2 = 1;
                            }
                        }
                        if (constraintWidget$DimensionBehaviour9 == constraintWidget$DimensionBehaviour12 && constraintWidget$DimensionBehaviour7 == constraintWidget$DimensionBehaviour12) {
                            if (i5 == i2 || i6 == i2) {
                                ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour19 = constraintWidget$DimensionBehaviour6;
                                m214605a5(c0829lq, constraintWidget$DimensionBehaviour19, 0, constraintWidget$DimensionBehaviour19, 0);
                                c0829lq.f58064a3.f44461a4.f44453b2 = c0829lq.m213891b7();
                                c0829lq.f58065a4.f44461a4.f44453b2 = c0829lq.m213887b1();
                            } else if (i6 == 2 && i5 == 2) {
                                ConstraintWidget$DimensionBehaviour[] constraintWidget$DimensionBehaviourArr2 = c0830lr.f58107e6;
                                if (constraintWidget$DimensionBehaviourArr2[c2] == constraintWidget$DimensionBehaviour8 && constraintWidget$DimensionBehaviourArr2[i2] == constraintWidget$DimensionBehaviour8) {
                                    m214605a5(c0829lq, constraintWidget$DimensionBehaviour8, (int) ((f * c0830lr.m213891b7()) + 0.5f), constraintWidget$DimensionBehaviour8, (int) ((f2 * c0830lr.m213887b1()) + 0.5f));
                                    c0829lq.f58064a3.f44461a4.mo209951a3(c0829lq.m213891b7());
                                    c0829lq.f58065a4.f44461a4.mo209951a3(c0829lq.m213887b1());
                                    c0829lq.f58061a0 = true;
                                }
                            }
                        }
                    }
                    c = c2;
                    arrayList = arrayList2;
                    size = i4;
                    i3 = i;
                } else {
                    if (constraintWidget$DimensionBehaviour10 == constraintWidget$DimensionBehaviour12 && (constraintWidget$DimensionBehaviour11 == constraintWidget$DimensionBehaviour13 || constraintWidget$DimensionBehaviour11 == constraintWidget$DimensionBehaviour14)) {
                        c0829lq.f58078b7 = 3;
                    } else if (constraintWidget$DimensionBehaviour11 == constraintWidget$DimensionBehaviour12 && (constraintWidget$DimensionBehaviour10 == constraintWidget$DimensionBehaviour13 || constraintWidget$DimensionBehaviour10 == constraintWidget$DimensionBehaviour14)) {
                        c0829lq.f58079b8 = 3;
                    } else {
                        if (constraintWidget$DimensionBehaviour10 == constraintWidget$DimensionBehaviour12 && constraintWidget$DimensionBehaviour11 == constraintWidget$DimensionBehaviour12) {
                            i = i3;
                            if (c0829lq.f58078b7 == 0) {
                                c0829lq.f58078b7 = 3;
                            }
                            if (c0829lq.f58079b8 == 0) {
                                c0829lq.f58079b8 = 3;
                            }
                        }
                        if (constraintWidget$DimensionBehaviour10 == constraintWidget$DimensionBehaviour12) {
                            constraintWidget$DimensionBehaviour10 = constraintWidget$DimensionBehaviour13;
                        }
                        if (constraintWidget$DimensionBehaviour11 == constraintWidget$DimensionBehaviour12) {
                            constraintWidget$DimensionBehaviour11 = constraintWidget$DimensionBehaviour13;
                        }
                        C0053a3 c0053a32 = c0829lq.f58064a3;
                        c0053a32.f44460a3 = constraintWidget$DimensionBehaviour10;
                        int i52 = c0829lq.f58078b7;
                        c0053a32.f44457a0 = i52;
                        C0054a4 c0054a42 = c0829lq.f58065a4;
                        c0054a42.f44460a3 = constraintWidget$DimensionBehaviour11;
                        int i62 = c0829lq.f58079b8;
                        c0054a42.f44457a0 = i62;
                        constraintWidget$DimensionBehaviour = ConstraintWidget$DimensionBehaviour.f44427a3;
                        if (constraintWidget$DimensionBehaviour10 == constraintWidget$DimensionBehaviour) {
                            constraintWidget$DimensionBehaviour2 = constraintWidget$DimensionBehaviour10;
                            int iM213891b73 = c0829lq.m213891b7();
                            if (constraintWidget$DimensionBehaviour2 == constraintWidget$DimensionBehaviour) {
                            }
                            int iM213887b13 = c0829lq.m213887b1();
                            if (constraintWidget$DimensionBehaviour11 == constraintWidget$DimensionBehaviour) {
                            }
                            m214605a5(c0829lq, constraintWidget$DimensionBehaviour2, iM213891b73, constraintWidget$DimensionBehaviour3, iM213887b13);
                            c0829lq.f58064a3.f44461a4.mo209951a3(c0829lq.m213891b7());
                            c0829lq.f58065a4.f44461a4.mo209951a3(c0829lq.m213887b1());
                            c0829lq.f58061a0 = true;
                            c = c2;
                            arrayList = arrayList2;
                            size = i4;
                            i3 = i;
                        } else {
                            constraintWidget$DimensionBehaviour2 = constraintWidget$DimensionBehaviour10;
                            int iM213891b732 = c0829lq.m213891b7();
                            if (constraintWidget$DimensionBehaviour2 == constraintWidget$DimensionBehaviour) {
                            }
                            int iM213887b132 = c0829lq.m213887b1();
                            if (constraintWidget$DimensionBehaviour11 == constraintWidget$DimensionBehaviour) {
                            }
                            m214605a5(c0829lq, constraintWidget$DimensionBehaviour2, iM213891b732, constraintWidget$DimensionBehaviour3, iM213887b132);
                            c0829lq.f58064a3.f44461a4.mo209951a3(c0829lq.m213891b7());
                            c0829lq.f58065a4.f44461a4.mo209951a3(c0829lq.m213887b1());
                            c0829lq.f58061a0 = true;
                            c = c2;
                            arrayList = arrayList2;
                            size = i4;
                            i3 = i;
                        }
                    }
                    i = i3;
                    if (constraintWidget$DimensionBehaviour10 == constraintWidget$DimensionBehaviour12) {
                    }
                    if (constraintWidget$DimensionBehaviour11 == constraintWidget$DimensionBehaviour12) {
                    }
                    C0053a3 c0053a322 = c0829lq.f58064a3;
                    c0053a322.f44460a3 = constraintWidget$DimensionBehaviour10;
                    int i522 = c0829lq.f58078b7;
                    c0053a322.f44457a0 = i522;
                    C0054a4 c0054a422 = c0829lq.f58065a4;
                    c0054a422.f44460a3 = constraintWidget$DimensionBehaviour11;
                    int i622 = c0829lq.f58079b8;
                    c0054a422.f44457a0 = i622;
                    constraintWidget$DimensionBehaviour = ConstraintWidget$DimensionBehaviour.f44427a3;
                    if (constraintWidget$DimensionBehaviour10 == constraintWidget$DimensionBehaviour) {
                    }
                }
            }
        }
    }

    /* renamed from: a2 */
    public final void m214602a2() {
        C0830lr c0830lr = this.f59955a0;
        ArrayList arrayList = this.f59962a7;
        ArrayList arrayList2 = this.f59959a4;
        arrayList2.clear();
        C0830lr c0830lr2 = this.f59958a3;
        c0830lr2.f58064a3.mo209954a5();
        c0830lr2.f58065a4.mo209954a5();
        arrayList2.add(c0830lr2.f58064a3);
        arrayList2.add(c0830lr2.f58065a4);
        ArrayList arrayList3 = c0830lr2.f58139h2;
        int size = arrayList3.size();
        HashSet hashSet = null;
        int i = 0;
        while (i < size) {
            Object obj = arrayList3.get(i);
            i++;
            C0829lq c0829lq = (C0829lq) obj;
            if (c0829lq instanceof o30) {
                p30 p30Var = new p30(c0829lq);
                c0829lq.f58064a3.mo209954a5();
                c0829lq.f58065a4.mo209954a5();
                p30Var.f44462a5 = ((o30) c0829lq).f58730h6;
                arrayList2.add(p30Var);
            } else {
                if (c0829lq.m213898c4()) {
                    if (c0829lq.f58062a1 == null) {
                        c0829lq.f58062a1 = new C0555gs(c0829lq, 0);
                    }
                    if (hashSet == null) {
                        hashSet = new HashSet();
                    }
                    hashSet.add(c0829lq.f58062a1);
                } else {
                    arrayList2.add(c0829lq.f58064a3);
                }
                if (c0829lq.m213899c5()) {
                    if (c0829lq.f58063a2 == null) {
                        c0829lq.f58063a2 = new C0555gs(c0829lq, 1);
                    }
                    if (hashSet == null) {
                        hashSet = new HashSet();
                    }
                    hashSet.add(c0829lq.f58063a2);
                } else {
                    arrayList2.add(c0829lq.f58065a4);
                }
                if (c0829lq instanceof b40) {
                    arrayList2.add(new C0052a2(c0829lq));
                }
            }
        }
        if (hashSet != null) {
            arrayList2.addAll(hashSet);
        }
        int size2 = arrayList2.size();
        int i2 = 0;
        while (i2 < size2) {
            Object obj2 = arrayList2.get(i2);
            i2++;
            ((AbstractC0055a5) obj2).mo209954a5();
        }
        int size3 = arrayList2.size();
        int i3 = 0;
        while (i3 < size3) {
            Object obj3 = arrayList2.get(i3);
            i3++;
            AbstractC0055a5 abstractC0055a5 = (AbstractC0055a5) obj3;
            if (abstractC0055a5.f44458a1 != c0830lr2) {
                abstractC0055a5.mo209952a3();
            }
        }
        arrayList.clear();
        m214604a4(c0830lr.f58064a3, 0, arrayList);
        m214604a4(c0830lr.f58065a4, 1, arrayList);
        this.f59956a1 = false;
    }

    /* renamed from: a3 */
    public final int m214603a3(C0830lr c0830lr, int i) {
        ArrayList arrayList;
        int i2;
        long jMax;
        float f;
        C0830lr c0830lr2 = c0830lr;
        ArrayList arrayList2 = this.f59962a7;
        int size = arrayList2.size();
        long j = 0;
        int i3 = 0;
        long jMax2 = 0;
        while (i3 < size) {
            AbstractC0055a5 abstractC0055a5 = ((ps0) arrayList2.get(i3)).f59333a0;
            if (!(abstractC0055a5 instanceof C0555gs) ? !(i != 0 ? (abstractC0055a5 instanceof C0054a4) : (abstractC0055a5 instanceof C0053a3)) : ((C0555gs) abstractC0055a5).f44462a5 != i) {
                C0050a0 c0050a0 = (i == 0 ? c0830lr2.f58064a3 : c0830lr2.f58065a4).f44464a7;
                C0050a0 c0050a02 = (i == 0 ? c0830lr2.f58064a3 : c0830lr2.f58065a4).f44465a8;
                C0050a0 c0050a03 = abstractC0055a5.f44464a7;
                C0050a0 c0050a04 = abstractC0055a5.f44465a8;
                boolean zContains = c0050a03.f44452b1.contains(c0050a0);
                boolean zContains2 = c0050a04.f44452b1.contains(c0050a02);
                long jMo209965a9 = abstractC0055a5.mo209965a9();
                if (zContains && zContains2) {
                    long jM214335a1 = ps0.m214335a1(c0050a03, j);
                    long jM214334a0 = ps0.m214334a0(c0050a04, j);
                    long j2 = jM214335a1 - jMo209965a9;
                    int i4 = c0050a04.f44446a5;
                    arrayList = arrayList2;
                    i2 = size;
                    if (j2 >= (-i4)) {
                        j2 += i4;
                    }
                    long j3 = c0050a03.f44446a5;
                    long j4 = ((-jM214334a0) - jMo209965a9) - j3;
                    if (j4 >= j3) {
                        j4 -= j3;
                    }
                    C0829lq c0829lq = abstractC0055a5.f44458a1;
                    if (i == 0) {
                        f = c0829lq.f58118f7;
                    } else if (i == 1) {
                        f = c0829lq.f58119f8;
                    } else {
                        c0829lq.getClass();
                        f = -1.0f;
                    }
                    float f2 = f > 0.0f ? (long) ((j2 / (1.0f - f)) + (j4 / f)) : 0L;
                    jMax = (c0050a03.f44446a5 + ((((long) ((f2 * f) + 0.5f)) + jMo209965a9) + ((long) AbstractC0003a2.m19a0(1.0f, f, f2, 0.5f)))) - c0050a04.f44446a5;
                } else {
                    arrayList = arrayList2;
                    i2 = size;
                    jMax = zContains ? Math.max(ps0.m214335a1(c0050a03, c0050a03.f44446a5), c0050a03.f44446a5 + jMo209965a9) : zContains2 ? Math.max(-ps0.m214334a0(c0050a04, c0050a04.f44446a5), (-c0050a04.f44446a5) + jMo209965a9) : (abstractC0055a5.mo209965a9() + c0050a03.f44446a5) - c0050a04.f44446a5;
                }
            } else {
                arrayList = arrayList2;
                i2 = size;
                jMax = j;
            }
            jMax2 = Math.max(jMax2, jMax);
            i3++;
            c0830lr2 = c0830lr;
            arrayList2 = arrayList;
            size = i2;
            j = 0;
        }
        return (int) jMax2;
    }

    /* renamed from: a4 */
    public final void m214604a4(AbstractC0055a5 abstractC0055a5, int i, ArrayList arrayList) {
        C0050a0 c0050a0 = abstractC0055a5.f44464a7;
        C0050a0 c0050a02 = abstractC0055a5.f44465a8;
        ArrayList arrayList2 = c0050a0.f44451b0;
        int size = arrayList2.size();
        int i2 = 0;
        int i3 = 0;
        while (i3 < size) {
            Object obj = arrayList2.get(i3);
            i3++;
            InterfaceC1215sa interfaceC1215sa = (InterfaceC1215sa) obj;
            if (interfaceC1215sa instanceof C0050a0) {
                m214600a0((C0050a0) interfaceC1215sa, i, arrayList, null);
            } else if (interfaceC1215sa instanceof AbstractC0055a5) {
                m214600a0(((AbstractC0055a5) interfaceC1215sa).f44464a7, i, arrayList, null);
            }
        }
        ArrayList arrayList3 = c0050a02.f44451b0;
        int size2 = arrayList3.size();
        int i4 = 0;
        while (i4 < size2) {
            Object obj2 = arrayList3.get(i4);
            i4++;
            InterfaceC1215sa interfaceC1215sa2 = (InterfaceC1215sa) obj2;
            if (interfaceC1215sa2 instanceof C0050a0) {
                m214600a0((C0050a0) interfaceC1215sa2, i, arrayList, null);
            } else if (interfaceC1215sa2 instanceof AbstractC0055a5) {
                m214600a0(((AbstractC0055a5) interfaceC1215sa2).f44465a8, i, arrayList, null);
            }
        }
        if (i == 1) {
            ArrayList arrayList4 = ((C0054a4) abstractC0055a5).f44455b0.f44451b0;
            int size3 = arrayList4.size();
            while (i2 < size3) {
                Object obj3 = arrayList4.get(i2);
                i2++;
                InterfaceC1215sa interfaceC1215sa3 = (InterfaceC1215sa) obj3;
                if (interfaceC1215sa3 instanceof C0050a0) {
                    m214600a0((C0050a0) interfaceC1215sa3, i, arrayList, null);
                }
            }
        }
    }

    /* renamed from: a5 */
    public final void m214605a5(C0829lq c0829lq, ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour, int i, ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour2, int i2) {
        C0418dj c0418dj = this.f59961a6;
        c0418dj.f55819a0 = constraintWidget$DimensionBehaviour;
        c0418dj.f55820a1 = constraintWidget$DimensionBehaviour2;
        c0418dj.f55821a2 = i;
        c0418dj.f55822a3 = i2;
        this.f59960a5.m213800a1(c0829lq, c0418dj);
        c0829lq.m213911e1(c0418dj.f55823a4);
        c0829lq.m213908d8(c0418dj.f55824a5);
        c0829lq.f58091d0 = c0418dj.f55826a7;
        c0829lq.m213905d5(c0418dj.f55825a6);
    }

    /* renamed from: a6 */
    public final void m214606a6() {
        C0417di c0417di;
        C1218sd c1218sd = this;
        ArrayList arrayList = c1218sd.f59955a0.f58139h2;
        int size = arrayList.size();
        char c = 0;
        int i = 0;
        while (i < size) {
            int i2 = i + 1;
            C0829lq c0829lq = (C0829lq) arrayList.get(i);
            if (!c0829lq.f58061a0) {
                ConstraintWidget$DimensionBehaviour[] constraintWidget$DimensionBehaviourArr = c0829lq.f58107e6;
                ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour = constraintWidget$DimensionBehaviourArr[c];
                ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour2 = constraintWidget$DimensionBehaviourArr[1];
                int i3 = c0829lq.f58078b7;
                int i4 = c0829lq.f58079b8;
                ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour3 = ConstraintWidget$DimensionBehaviour.f44426a2;
                ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour4 = ConstraintWidget$DimensionBehaviour.f44425a1;
                char c2 = (constraintWidget$DimensionBehaviour == constraintWidget$DimensionBehaviour4 || (constraintWidget$DimensionBehaviour == constraintWidget$DimensionBehaviour3 && i3 == 1)) ? (char) 1 : c;
                char c3 = (constraintWidget$DimensionBehaviour2 == constraintWidget$DimensionBehaviour4 || (constraintWidget$DimensionBehaviour2 == constraintWidget$DimensionBehaviour3 && i4 == 1)) ? (char) 1 : c;
                C0051a1 c0051a1 = c0829lq.f58064a3.f44461a4;
                boolean z = c0051a1.f44450a9;
                C0051a1 c0051a12 = c0829lq.f58065a4.f44461a4;
                boolean z2 = c0051a12.f44450a9;
                char c4 = c2;
                ConstraintWidget$DimensionBehaviour constraintWidget$DimensionBehaviour5 = ConstraintWidget$DimensionBehaviour.f44424a0;
                if (z && z2) {
                    c1218sd.m214605a5(c0829lq, constraintWidget$DimensionBehaviour5, c0051a1.f44447a6, constraintWidget$DimensionBehaviour5, c0051a12.f44447a6);
                    c0829lq.f58061a0 = true;
                } else if (z && c3 != 0) {
                    m214605a5(c0829lq, constraintWidget$DimensionBehaviour5, c0051a1.f44447a6, constraintWidget$DimensionBehaviour4, c0051a12.f44447a6);
                    if (constraintWidget$DimensionBehaviour2 == constraintWidget$DimensionBehaviour3) {
                        c0829lq.f58065a4.f44461a4.f44453b2 = c0829lq.m213887b1();
                    } else {
                        c0829lq.f58065a4.f44461a4.mo209951a3(c0829lq.m213887b1());
                        c0829lq.f58061a0 = true;
                    }
                } else if (z2 && c4 != 0) {
                    m214605a5(c0829lq, constraintWidget$DimensionBehaviour4, c0051a1.f44447a6, constraintWidget$DimensionBehaviour5, c0051a12.f44447a6);
                    if (constraintWidget$DimensionBehaviour == constraintWidget$DimensionBehaviour3) {
                        c0829lq.f58064a3.f44461a4.f44453b2 = c0829lq.m213891b7();
                    } else {
                        c0829lq.f58064a3.f44461a4.mo209951a3(c0829lq.m213891b7());
                        c0829lq.f58061a0 = true;
                    }
                }
                if (c0829lq.f58061a0 && (c0417di = c0829lq.f58065a4.f44456b1) != null) {
                    c0417di.mo209951a3(c0829lq.f58115f4);
                }
                c = 0;
                c1218sd = this;
            }
            i = i2;
        }
    }
}
