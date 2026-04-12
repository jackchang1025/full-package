package p000;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class qe1 {

    /* renamed from: a5 */
    public static int f59484a5;

    /* renamed from: a0 */
    public ArrayList f59485a0;

    /* renamed from: a1 */
    public int f59486a1;

    /* renamed from: a2 */
    public int f59487a2;

    /* renamed from: a3 */
    public ArrayList f59488a3;

    /* renamed from: a4 */
    public int f59489a4;

    /* renamed from: a0 */
    public final void m214382a0(ArrayList arrayList) {
        int size = this.f59485a0.size();
        if (this.f59489a4 != -1 && size > 0) {
            for (int i = 0; i < arrayList.size(); i++) {
                qe1 qe1Var = (qe1) arrayList.get(i);
                if (this.f59489a4 == qe1Var.f59486a1) {
                    m214384a2(this.f59487a2, qe1Var);
                }
            }
        }
        if (size == 0) {
            arrayList.remove(this);
        }
    }

    /* renamed from: a1 */
    public final int m214383a1(ab0 ab0Var, int i) {
        int iM209758b3;
        int iM209758b32;
        ArrayList arrayList = this.f59485a0;
        if (arrayList.size() == 0) {
            return 0;
        }
        C0830lr c0830lr = (C0830lr) ((C0829lq) arrayList.get(0)).f58108e7;
        ab0Var.m209777b9();
        c0830lr.mo210751a1(ab0Var, false);
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            ((C0829lq) arrayList.get(i2)).mo210751a1(ab0Var, false);
        }
        if (i == 0 && c0830lr.f58148i1 > 0) {
            b81.m210560a0(c0830lr, ab0Var, arrayList, 0);
        }
        if (i == 1 && c0830lr.f58149i2 > 0) {
            b81.m210560a0(c0830lr, ab0Var, arrayList, 1);
        }
        try {
            ab0Var.m209773b5();
        } catch (Exception unused) {
        }
        this.f59488a3 = new ArrayList();
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            C0829lq c0829lq = (C0829lq) arrayList.get(i3);
            fh0 fh0Var = new fh0(19);
            new WeakReference(c0829lq);
            ab0.m209758b3(c0829lq.f58096d5);
            ab0.m209758b3(c0829lq.f58097d6);
            ab0.m209758b3(c0829lq.f58098d7);
            ab0.m209758b3(c0829lq.f58099d8);
            ab0.m209758b3(c0829lq.f58100d9);
            this.f59488a3.add(fh0Var);
        }
        if (i == 0) {
            iM209758b3 = ab0.m209758b3(c0830lr.f58096d5);
            iM209758b32 = ab0.m209758b3(c0830lr.f58098d7);
            ab0Var.m209777b9();
        } else {
            iM209758b3 = ab0.m209758b3(c0830lr.f58097d6);
            iM209758b32 = ab0.m209758b3(c0830lr.f58099d8);
            ab0Var.m209777b9();
        }
        return iM209758b32 - iM209758b3;
    }

    /* renamed from: a2 */
    public final void m214384a2(int i, qe1 qe1Var) {
        int i2 = qe1Var.f59486a1;
        ArrayList arrayList = this.f59485a0;
        int size = arrayList.size();
        int i3 = 0;
        while (i3 < size) {
            Object obj = arrayList.get(i3);
            i3++;
            C0829lq c0829lq = (C0829lq) obj;
            ArrayList arrayList2 = qe1Var.f59485a0;
            if (!arrayList2.contains(c0829lq)) {
                arrayList2.add(c0829lq);
            }
            if (i == 0) {
                c0829lq.f58131h0 = i2;
            } else {
                c0829lq.f58132h1 = i2;
            }
        }
        this.f59489a4 = i2;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        int i = this.f59487a2;
        sb.append(i == 0 ? "Horizontal" : i == 1 ? "Vertical" : i == 2 ? "Both" : "Unknown");
        sb.append(" [");
        sb.append(this.f59486a1);
        sb.append("] <");
        String string = sb.toString();
        ArrayList arrayList = this.f59485a0;
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            StringBuilder sbM39c0 = AbstractC0003a2.m39c0(string, " ");
            sbM39c0.append(((C0829lq) obj).f58123g2);
            string = sbM39c0.toString();
        }
        return AbstractC0003a2.m32b3(string, " >");
    }
}
