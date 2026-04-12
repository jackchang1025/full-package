package p000;

import android.util.Base64;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.recyclerview.widget.RecyclerView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: q2 */
/* loaded from: classes.dex */
public final class C1094q2 {

    /* renamed from: a0 */
    public final /* synthetic */ int f59364a0;

    /* renamed from: a1 */
    public final Object f59365a1;

    /* renamed from: a2 */
    public final Serializable f59366a2;

    /* renamed from: a3 */
    public Serializable f59367a3;

    /* renamed from: a4 */
    public Object f59368a4;

    /* renamed from: a5 */
    public final Object f59369a5;

    public C1094q2(MotionLayout motionLayout) {
        this.f59364a0 = 2;
        this.f59366a2 = new ArrayList();
        this.f59369a5 = new ArrayList();
        this.f59365a1 = motionLayout;
    }

    /* renamed from: a0 */
    public boolean m214338a0(int i) {
        ArrayList arrayList = (ArrayList) this.f59367a3;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            C1093q1 c1093q1 = (C1093q1) arrayList.get(i2);
            int i3 = c1093q1.f59354a0;
            if (i3 != 8) {
                if (i3 == 1) {
                    int i4 = c1093q1.f59355a1;
                    int i5 = c1093q1.f59356a2 + i4;
                    while (i4 < i5) {
                        if (m214342a4(i4, i2 + 1) == i) {
                            return true;
                        }
                        i4++;
                    }
                } else {
                    continue;
                }
            } else {
                if (m214342a4(c1093q1.f59356a2, i2 + 1) == i) {
                    return true;
                }
            }
        }
        return false;
    }

    /* renamed from: a1 */
    public void m214339a1() {
        fq0 fq0Var = (fq0) this.f59368a4;
        ArrayList arrayList = (ArrayList) this.f59367a3;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            ((fq0) this.f59368a4).m212846a0((C1093q1) arrayList.get(i));
        }
        m214346a8(arrayList);
        ArrayList arrayList2 = (ArrayList) this.f59366a2;
        int size2 = arrayList2.size();
        for (int i2 = 0; i2 < size2; i2++) {
            C1093q1 c1093q1 = (C1093q1) arrayList2.get(i2);
            int i3 = c1093q1.f59354a0;
            if (i3 == 1) {
                fq0Var.m212846a0(c1093q1);
                fq0Var.m212849a3(c1093q1.f59355a1, c1093q1.f59356a2);
            } else if (i3 == 2) {
                fq0Var.m212846a0(c1093q1);
                int i4 = c1093q1.f59355a1;
                int i5 = c1093q1.f59356a2;
                RecyclerView recyclerView = fq0Var.f56313a0;
                recyclerView.m210379e1(i4, i5, true);
                recyclerView.f45309f5 = true;
                recyclerView.f45306f2.f45598a2 += i5;
            } else if (i3 == 4) {
                fq0Var.m212846a0(c1093q1);
                fq0Var.m212848a2(c1093q1.f59355a1, c1093q1.f59356a2);
            } else if (i3 == 8) {
                fq0Var.m212846a0(c1093q1);
                fq0Var.m212850a4(c1093q1.f59355a1, c1093q1.f59356a2);
            }
        }
        m214346a8(arrayList2);
    }

    /* renamed from: a2 */
    public void m214340a2(C1093q1 c1093q1) {
        int i;
        vn0 vn0Var = (vn0) this.f59365a1;
        int i2 = c1093q1.f59354a0;
        if (i2 == 1 || i2 == 8) {
            throw new IllegalArgumentException("should not dispatch add or move for pre layout");
        }
        int iM214347a9 = m214347a9(c1093q1.f59355a1, i2);
        int i3 = c1093q1.f59355a1;
        int i4 = c1093q1.f59354a0;
        if (i4 == 2) {
            i = 0;
        } else {
            if (i4 != 4) {
                throw new IllegalArgumentException("op should be remove or update." + c1093q1);
            }
            i = 1;
        }
        int i5 = 1;
        for (int i6 = 1; i6 < c1093q1.f59356a2; i6++) {
            int iM214347a92 = m214347a9((i * i6) + c1093q1.f59355a1, c1093q1.f59354a0);
            int i7 = c1093q1.f59354a0;
            if (i7 == 2 ? iM214347a92 != iM214347a9 : !(i7 == 4 && iM214347a92 == iM214347a9 + 1)) {
                C1093q1 c1093q1M214344a6 = m214344a6(i7, iM214347a9, i5);
                m214341a3(c1093q1M214344a6, i3);
                vn0Var.mo214934a2(c1093q1M214344a6);
                if (c1093q1.f59354a0 == 4) {
                    i3 += i5;
                }
                i5 = 1;
                iM214347a9 = iM214347a92;
            } else {
                i5++;
            }
        }
        vn0Var.mo214934a2(c1093q1);
        if (i5 > 0) {
            C1093q1 c1093q1M214344a62 = m214344a6(c1093q1.f59354a0, iM214347a9, i5);
            m214341a3(c1093q1M214344a62, i3);
            vn0Var.mo214934a2(c1093q1M214344a62);
        }
    }

    /* renamed from: a3 */
    public void m214341a3(C1093q1 c1093q1, int i) {
        fq0 fq0Var = (fq0) this.f59368a4;
        fq0Var.m212846a0(c1093q1);
        int i2 = c1093q1.f59354a0;
        if (i2 != 2) {
            if (i2 != 4) {
                throw new IllegalArgumentException("only remove and update ops can be dispatched in first pass");
            }
            fq0Var.m212848a2(i, c1093q1.f59356a2);
        } else {
            int i3 = c1093q1.f59356a2;
            RecyclerView recyclerView = fq0Var.f56313a0;
            recyclerView.m210379e1(i, i3, true);
            recyclerView.f45309f5 = true;
            recyclerView.f45306f2.f45598a2 += i3;
        }
    }

    /* renamed from: a4 */
    public int m214342a4(int i, int i2) {
        ArrayList arrayList = (ArrayList) this.f59367a3;
        int size = arrayList.size();
        while (i2 < size) {
            C1093q1 c1093q1 = (C1093q1) arrayList.get(i2);
            int i3 = c1093q1.f59354a0;
            if (i3 == 8) {
                int i4 = c1093q1.f59355a1;
                if (i4 == i) {
                    i = c1093q1.f59356a2;
                } else {
                    if (i4 < i) {
                        i--;
                    }
                    if (c1093q1.f59356a2 <= i) {
                        i++;
                    }
                }
            } else {
                int i5 = c1093q1.f59355a1;
                if (i5 > i) {
                    continue;
                } else if (i3 == 2) {
                    int i6 = c1093q1.f59356a2;
                    if (i < i5 + i6) {
                        return -1;
                    }
                    i -= i6;
                } else if (i3 == 1) {
                    i += c1093q1.f59356a2;
                }
            }
            i2++;
        }
        return i;
    }

    /* renamed from: a5 */
    public boolean m214343a5() {
        return ((ArrayList) this.f59366a2).size() > 0;
    }

    /* renamed from: a6 */
    public C1093q1 m214344a6(int i, int i2, int i3) {
        C1093q1 c1093q1 = (C1093q1) ((vn0) this.f59365a1).mo214932a0();
        if (c1093q1 != null) {
            c1093q1.f59354a0 = i;
            c1093q1.f59355a1 = i2;
            c1093q1.f59356a2 = i3;
            return c1093q1;
        }
        C1093q1 c1093q12 = new C1093q1();
        c1093q12.f59354a0 = i;
        c1093q12.f59355a1 = i2;
        c1093q12.f59356a2 = i3;
        return c1093q12;
    }

    /* renamed from: a7 */
    public void m214345a7(C1093q1 c1093q1) {
        fq0 fq0Var = (fq0) this.f59368a4;
        ((ArrayList) this.f59367a3).add(c1093q1);
        int i = c1093q1.f59354a0;
        if (i == 1) {
            fq0Var.m212849a3(c1093q1.f59355a1, c1093q1.f59356a2);
            return;
        }
        if (i == 2) {
            int i2 = c1093q1.f59355a1;
            int i3 = c1093q1.f59356a2;
            RecyclerView recyclerView = fq0Var.f56313a0;
            recyclerView.m210379e1(i2, i3, false);
            recyclerView.f45309f5 = true;
            return;
        }
        if (i == 4) {
            fq0Var.m212848a2(c1093q1.f59355a1, c1093q1.f59356a2);
        } else if (i == 8) {
            fq0Var.m212850a4(c1093q1.f59355a1, c1093q1.f59356a2);
        } else {
            throw new IllegalArgumentException("Unknown update op type for " + c1093q1);
        }
    }

    /* renamed from: a8 */
    public void m214346a8(ArrayList arrayList) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            C1093q1 c1093q1 = (C1093q1) arrayList.get(i);
            c1093q1.getClass();
            ((vn0) this.f59365a1).mo214934a2(c1093q1);
        }
        arrayList.clear();
    }

    /* renamed from: a9 */
    public int m214347a9(int i, int i2) {
        int i3;
        int i4;
        vn0 vn0Var = (vn0) this.f59365a1;
        ArrayList arrayList = (ArrayList) this.f59367a3;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            C1093q1 c1093q1 = (C1093q1) arrayList.get(size);
            int i5 = c1093q1.f59354a0;
            if (i5 == 8) {
                int i6 = c1093q1.f59355a1;
                int i7 = c1093q1.f59356a2;
                if (i6 < i7) {
                    i4 = i6;
                    i3 = i7;
                } else {
                    i3 = i6;
                    i4 = i7;
                }
                if (i < i4 || i > i3) {
                    if (i < i6) {
                        if (i2 == 1) {
                            c1093q1.f59355a1 = i6 + 1;
                            c1093q1.f59356a2 = i7 + 1;
                        } else if (i2 == 2) {
                            c1093q1.f59355a1 = i6 - 1;
                            c1093q1.f59356a2 = i7 - 1;
                        }
                    }
                } else if (i4 == i6) {
                    if (i2 == 1) {
                        c1093q1.f59356a2 = i7 + 1;
                    } else if (i2 == 2) {
                        c1093q1.f59356a2 = i7 - 1;
                    }
                    i++;
                } else {
                    if (i2 == 1) {
                        c1093q1.f59355a1 = i6 + 1;
                    } else if (i2 == 2) {
                        c1093q1.f59355a1 = i6 - 1;
                    }
                    i--;
                }
            } else {
                int i8 = c1093q1.f59355a1;
                if (i8 <= i) {
                    if (i5 == 1) {
                        i -= c1093q1.f59356a2;
                    } else if (i5 == 2) {
                        i += c1093q1.f59356a2;
                    }
                } else if (i2 == 1) {
                    c1093q1.f59355a1 = i8 + 1;
                } else if (i2 == 2) {
                    c1093q1.f59355a1 = i8 - 1;
                }
            }
        }
        for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
            C1093q1 c1093q12 = (C1093q1) arrayList.get(size2);
            if (c1093q12.f59354a0 == 8) {
                int i9 = c1093q12.f59356a2;
                if (i9 == c1093q12.f59355a1 || i9 < 0) {
                    arrayList.remove(size2);
                    vn0Var.mo214934a2(c1093q12);
                }
            } else if (c1093q12.f59356a2 <= 0) {
                arrayList.remove(size2);
                vn0Var.mo214934a2(c1093q12);
            }
        }
        return i;
    }

    public String toString() {
        switch (this.f59364a0) {
            case 1:
                List list = (List) this.f59368a4;
                StringBuilder sb = new StringBuilder();
                sb.append("FontRequest {mProviderAuthority: " + ((String) this.f59365a1) + ", mProviderPackage: " + ((String) this.f59366a2) + ", mQuery: " + ((String) this.f59367a3) + ", mCertificates:");
                for (int i = 0; i < list.size(); i++) {
                    sb.append(" [");
                    List list2 = (List) list.get(i);
                    for (int i2 = 0; i2 < list2.size(); i2++) {
                        sb.append(" \"");
                        sb.append(Base64.encodeToString((byte[]) list2.get(i2), 0));
                        sb.append("\"");
                    }
                    sb.append(" ]");
                }
                sb.append("}mCertificatesArray: 0");
                return sb.toString();
            default:
                return super.toString();
        }
    }

    public C1094q2(String str, String str2, String str3, List list) {
        this.f59364a0 = 1;
        str.getClass();
        this.f59365a1 = str;
        str2.getClass();
        this.f59366a2 = str2;
        this.f59367a3 = str3;
        list.getClass();
        this.f59368a4 = list;
        this.f59369a5 = AbstractC0003a2.m34b5(str, "-", str2, "-", str3);
    }

    public C1094q2(fq0 fq0Var) {
        this.f59364a0 = 0;
        this.f59365a1 = new vn0(30);
        this.f59366a2 = new ArrayList();
        this.f59367a3 = new ArrayList();
        this.f59368a4 = fq0Var;
        this.f59369a5 = new jl0(this);
    }
}
