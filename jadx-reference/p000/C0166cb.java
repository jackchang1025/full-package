package p000;

import android.animation.TimeInterpolator;
import android.util.AndroidRuntimeException;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: cb */
/* loaded from: classes.dex */
public final class C0166cb extends s71 {

    /* renamed from: c4 */
    public int f46089c4;

    /* renamed from: c2 */
    public ArrayList f46087c2 = new ArrayList();

    /* renamed from: c3 */
    public boolean f46088c3 = true;

    /* renamed from: c5 */
    public boolean f46090c5 = false;

    /* renamed from: c6 */
    public int f46091c6 = 0;

    public C0166cb() {
        m210798d4(1);
        m210795d1(new C1482yj(2));
        m210795d1(new C0560gx());
        m210795d1(new C1482yj(1));
    }

    @Override // p000.s71
    /* renamed from: a2 */
    public final void mo210780a2(y71 y71Var) {
        View view = y71Var.f61263a1;
        if (m214580b7(view)) {
            ArrayList arrayList = this.f46087c2;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                s71 s71Var = (s71) obj;
                if (s71Var.m214580b7(view)) {
                    s71Var.mo210780a2(y71Var);
                    y71Var.f61264a2.add(s71Var);
                }
            }
        }
    }

    @Override // p000.s71
    /* renamed from: a4 */
    public final void mo210781a4(y71 y71Var) {
        int size = this.f46087c2.size();
        for (int i = 0; i < size; i++) {
            ((s71) this.f46087c2.get(i)).mo210781a4(y71Var);
        }
    }

    @Override // p000.s71
    /* renamed from: a5 */
    public final void mo210782a5(y71 y71Var) {
        View view = y71Var.f61263a1;
        if (m214580b7(view)) {
            ArrayList arrayList = this.f46087c2;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                s71 s71Var = (s71) obj;
                if (s71Var.m214580b7(view)) {
                    s71Var.mo210782a5(y71Var);
                    y71Var.f61264a2.add(s71Var);
                }
            }
        }
    }

    @Override // p000.s71
    /* renamed from: a8 */
    public final s71 clone() {
        C0166cb c0166cb = (C0166cb) super.clone();
        c0166cb.f46087c2 = new ArrayList();
        int size = this.f46087c2.size();
        for (int i = 0; i < size; i++) {
            s71 s71VarClone = ((s71) this.f46087c2.get(i)).clone();
            c0166cb.f46087c2.add(s71VarClone);
            s71VarClone.f59907a8 = c0166cb;
        }
        return c0166cb;
    }

    @Override // p000.s71
    /* renamed from: b0 */
    public final void mo210784b0(ViewGroup viewGroup, x31 x31Var, x31 x31Var2, ArrayList arrayList, ArrayList arrayList2) {
        long j = this.f59900a1;
        int size = this.f46087c2.size();
        for (int i = 0; i < size; i++) {
            s71 s71Var = (s71) this.f46087c2.get(i);
            if (j > 0 && (this.f46088c3 || i == 0)) {
                long j2 = s71Var.f59900a1;
                if (j2 > 0) {
                    s71Var.mo210793c8(j2 + j);
                } else {
                    s71Var.mo210793c8(j);
                }
            }
            s71Var.mo210784b0(viewGroup, x31Var, x31Var2, arrayList, arrayList2);
        }
    }

    @Override // p000.s71
    /* renamed from: b9 */
    public final void mo210785b9(View view) {
        super.mo210785b9(view);
        int size = this.f46087c2.size();
        for (int i = 0; i < size; i++) {
            ((s71) this.f46087c2.get(i)).mo210785b9(view);
        }
    }

    @Override // p000.s71
    /* renamed from: c1 */
    public final void mo210786c1(View view) {
        super.mo210786c1(view);
        int size = this.f46087c2.size();
        for (int i = 0; i < size; i++) {
            ((s71) this.f46087c2.get(i)).mo210786c1(view);
        }
    }

    @Override // p000.s71
    /* renamed from: c2 */
    public final void mo210787c2() {
        if (this.f46087c2.isEmpty()) {
            m214582c9();
            m214576b1();
            return;
        }
        C1481yi c1481yi = new C1481yi();
        c1481yi.f61323a1 = this;
        ArrayList arrayList = this.f46087c2;
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            ((s71) obj).m214572a0(c1481yi);
        }
        this.f46089c4 = this.f46087c2.size();
        if (this.f46088c3) {
            ArrayList arrayList2 = this.f46087c2;
            int size2 = arrayList2.size();
            while (i < size2) {
                Object obj2 = arrayList2.get(i);
                i++;
                ((s71) obj2).mo210787c2();
            }
            return;
        }
        for (int i3 = 1; i3 < this.f46087c2.size(); i3++) {
            ((s71) this.f46087c2.get(i3 - 1)).m214572a0(new C1481yi(1, (s71) this.f46087c2.get(i3)));
        }
        s71 s71Var = (s71) this.f46087c2.get(0);
        if (s71Var != null) {
            s71Var.mo210787c2();
        }
    }

    @Override // p000.s71
    /* renamed from: c4 */
    public final void mo210789c4(t60 t60Var) {
        this.f46091c6 |= 8;
        int size = this.f46087c2.size();
        for (int i = 0; i < size; i++) {
            ((s71) this.f46087c2.get(i)).mo210789c4(t60Var);
        }
    }

    @Override // p000.s71
    /* renamed from: c6 */
    public final void mo210791c6(fh0 fh0Var) {
        super.mo210791c6(fh0Var);
        this.f46091c6 |= 4;
        if (this.f46087c2 != null) {
            for (int i = 0; i < this.f46087c2.size(); i++) {
                ((s71) this.f46087c2.get(i)).mo210791c6(fh0Var);
            }
        }
    }

    @Override // p000.s71
    /* renamed from: c7 */
    public final void mo210792c7() {
        this.f46091c6 |= 2;
        int size = this.f46087c2.size();
        for (int i = 0; i < size; i++) {
            ((s71) this.f46087c2.get(i)).mo210792c7();
        }
    }

    @Override // p000.s71
    /* renamed from: c8 */
    public final void mo210793c8(long j) {
        this.f59900a1 = j;
    }

    @Override // p000.s71
    /* renamed from: d0 */
    public final String mo210794d0(String str) {
        String strMo210794d0 = super.mo210794d0(str);
        for (int i = 0; i < this.f46087c2.size(); i++) {
            StringBuilder sbM39c0 = AbstractC0003a2.m39c0(strMo210794d0, "\n");
            sbM39c0.append(((s71) this.f46087c2.get(i)).mo210794d0(str + "  "));
            strMo210794d0 = sbM39c0.toString();
        }
        return strMo210794d0;
    }

    /* renamed from: d1 */
    public final void m210795d1(s71 s71Var) {
        this.f46087c2.add(s71Var);
        s71Var.f59907a8 = this;
        long j = this.f59901a2;
        if (j >= 0) {
            s71Var.mo210788c3(j);
        }
        if ((this.f46091c6 & 1) != 0) {
            s71Var.mo210790c5(this.f59902a3);
        }
        if ((this.f46091c6 & 2) != 0) {
            s71Var.mo210792c7();
        }
        if ((this.f46091c6 & 4) != 0) {
            s71Var.mo210791c6(this.f59917b8);
        }
        if ((this.f46091c6 & 8) != 0) {
            s71Var.mo210789c4(null);
        }
    }

    @Override // p000.s71
    /* renamed from: d2, reason: merged with bridge method [inline-methods] */
    public final void mo210788c3(long j) {
        ArrayList arrayList;
        this.f59901a2 = j;
        if (j < 0 || (arrayList = this.f46087c2) == null) {
            return;
        }
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            ((s71) this.f46087c2.get(i)).mo210788c3(j);
        }
    }

    @Override // p000.s71
    /* renamed from: d3, reason: merged with bridge method [inline-methods] */
    public final void mo210790c5(TimeInterpolator timeInterpolator) {
        this.f46091c6 |= 1;
        ArrayList arrayList = this.f46087c2;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                ((s71) this.f46087c2.get(i)).mo210790c5(timeInterpolator);
            }
        }
        this.f59902a3 = timeInterpolator;
    }

    /* renamed from: d4 */
    public final void m210798d4(int i) {
        if (i == 0) {
            this.f46088c3 = true;
        } else {
            if (i != 1) {
                throw new AndroidRuntimeException(tz0.m214802a2(i, "Invalid parameter for TransitionSet ordering: "));
            }
            this.f46088c3 = false;
        }
    }
}
