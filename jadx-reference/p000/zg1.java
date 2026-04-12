package p000;

import android.animation.ValueAnimator;
import android.database.Cursor;
import android.view.Choreographer;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.AbstractComponentCallbacksC0069a5;
import androidx.fragment.app.C0067a3;
import androidx.fragment.app.C0071a7;
import androidx.fragment.app.C0072a8;
import androidx.work.impl.WorkDatabase_Impl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class zg1 implements InterfaceC0532gd {

    /* renamed from: a3 */
    public static zg1 f61550a3;

    /* renamed from: a0 */
    public Object f61551a0;

    /* renamed from: a1 */
    public Object f61552a1;

    /* renamed from: a2 */
    public Object f61553a2;

    public zg1(int i) {
        switch (i) {
            case 5:
                this.f61551a0 = new ArrayList();
                this.f61552a1 = null;
                this.f61553a2 = new C0847m3(8, this);
                break;
            default:
                this.f61551a0 = new ArrayList();
                this.f61552a1 = new HashMap();
                break;
        }
    }

    /* renamed from: a0 */
    public void m215404a0(AbstractComponentCallbacksC0069a5 abstractComponentCallbacksC0069a5) {
        if (((ArrayList) this.f61551a0).contains(abstractComponentCallbacksC0069a5)) {
            throw new IllegalStateException("Fragment already added: " + abstractComponentCallbacksC0069a5);
        }
        synchronized (((ArrayList) this.f61551a0)) {
            ((ArrayList) this.f61551a0).add(abstractComponentCallbacksC0069a5);
        }
        abstractComponentCallbacksC0069a5.f45087b0 = true;
    }

    /* renamed from: a1 */
    public void m215405a1(int[] iArr, ValueAnimator valueAnimator) {
        fh0 fh0Var = new fh0(iArr, valueAnimator);
        valueAnimator.addListener((C0847m3) this.f61553a2);
        ((ArrayList) this.f61551a0).add(fh0Var);
    }

    /* renamed from: a2 */
    public boolean m215406a2(String str) {
        AbstractC0799kx abstractC0799kx;
        boolean z;
        t60.m214695b6(str, "workSpecId");
        synchronized (this.f61553a2) {
            try {
                AbstractC0799kx[] abstractC0799kxArr = (AbstractC0799kx[]) this.f61552a1;
                int length = abstractC0799kxArr.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        abstractC0799kx = null;
                        break;
                    }
                    abstractC0799kx = abstractC0799kxArr[i];
                    abstractC0799kx.getClass();
                    Object obj = abstractC0799kx.f57743a3;
                    if (obj != null && abstractC0799kx.mo212610a1(obj) && abstractC0799kx.f57742a2.contains(str)) {
                        break;
                    }
                    i++;
                }
                if (abstractC0799kx != null) {
                    C1351vv c1351vvM214963a5 = C1351vv.m214963a5();
                    int i2 = cg1.f46135a0;
                    c1351vvM214963a5.getClass();
                }
                z = abstractC0799kx == null;
            } catch (Throwable th) {
                throw th;
            }
        }
        return z;
    }

    /* renamed from: a3 */
    public AbstractComponentCallbacksC0069a5 m215407a3(String str) {
        C0072a8 c0072a8 = (C0072a8) ((HashMap) this.f61552a1).get(str);
        if (c0072a8 != null) {
            return c0072a8.f45157a2;
        }
        return null;
    }

    /* renamed from: a4 */
    public AbstractComponentCallbacksC0069a5 m215408a4(String str) {
        for (C0072a8 c0072a8 : ((HashMap) this.f61552a1).values()) {
            if (c0072a8 != null) {
                AbstractComponentCallbacksC0069a5 abstractComponentCallbacksC0069a5M215408a4 = c0072a8.f45157a2;
                if (!str.equals(abstractComponentCallbacksC0069a5M215408a4.f45081a4)) {
                    abstractComponentCallbacksC0069a5M215408a4 = abstractComponentCallbacksC0069a5M215408a4.f45096b9.f45124a2.m215408a4(str);
                }
                if (abstractComponentCallbacksC0069a5M215408a4 != null) {
                    return abstractComponentCallbacksC0069a5M215408a4;
                }
            }
        }
        return null;
    }

    /* renamed from: a5 */
    public ArrayList m215409a5() {
        ArrayList arrayList = new ArrayList();
        for (C0072a8 c0072a8 : ((HashMap) this.f61552a1).values()) {
            if (c0072a8 != null) {
                arrayList.add(c0072a8);
            }
        }
        return arrayList;
    }

    /* renamed from: a6 */
    public List m215410a6() {
        ArrayList arrayList;
        if (((ArrayList) this.f61551a0).isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        synchronized (((ArrayList) this.f61551a0)) {
            arrayList = new ArrayList((ArrayList) this.f61551a0);
        }
        return arrayList;
    }

    /* renamed from: a7 */
    public ArrayList m215411a7(String str) {
        WorkDatabase_Impl workDatabase_Impl = (WorkDatabase_Impl) this.f61551a0;
        js0 js0VarAcquire = js0.f57367a8.acquire("SELECT DISTINCT tag FROM worktag WHERE work_spec_id=?", 1);
        if (str == null) {
            js0VarAcquire.mo213343a9(1);
        } else {
            js0VarAcquire.mo213341a6(1, str);
        }
        workDatabase_Impl.m212857a1();
        Cursor cursorM213580c7 = kj1.m213580c7(workDatabase_Impl, js0VarAcquire);
        try {
            ArrayList arrayList = new ArrayList(cursorM213580c7.getCount());
            while (cursorM213580c7.moveToNext()) {
                arrayList.add(cursorM213580c7.isNull(0) ? null : cursorM213580c7.getString(0));
            }
            return arrayList;
        } finally {
            cursorM213580c7.close();
            js0VarAcquire.m213344b0();
        }
    }

    /* renamed from: a8 */
    public void m215412a8(String str, Set set) {
        t60.m214695b6(str, "id");
        t60.m214695b6(set, "tags");
        Iterator it = set.iterator();
        while (it.hasNext()) {
            yg1 yg1Var = new yg1((String) it.next(), str);
            WorkDatabase_Impl workDatabase_Impl = (WorkDatabase_Impl) this.f61551a0;
            workDatabase_Impl.m212857a1();
            workDatabase_Impl.m212858a2();
            try {
                ((C1216sb) this.f61552a1).m214590a5(yg1Var);
                workDatabase_Impl.m212863b2();
            } finally {
                workDatabase_Impl.m212860a9();
            }
        }
    }

    /* renamed from: a9 */
    public void m215413a9(C0072a8 c0072a8) {
        AbstractComponentCallbacksC0069a5 abstractComponentCallbacksC0069a5 = c0072a8.f45157a2;
        String str = abstractComponentCallbacksC0069a5.f45081a4;
        HashMap map = (HashMap) this.f61552a1;
        if (map.get(str) != null) {
            return;
        }
        map.put(abstractComponentCallbacksC0069a5.f45081a4, c0072a8);
        if (C0071a7.m210158c7(2)) {
            abstractComponentCallbacksC0069a5.toString();
        }
    }

    /* renamed from: b0 */
    public void m215414b0(C0072a8 c0072a8) {
        AbstractComponentCallbacksC0069a5 abstractComponentCallbacksC0069a5 = c0072a8.f45157a2;
        if (abstractComponentCallbacksC0069a5.f45103c6) {
            ((k00) this.f61553a2).m213395a2(abstractComponentCallbacksC0069a5);
        }
        if (((C0072a8) ((HashMap) this.f61552a1).put(abstractComponentCallbacksC0069a5.f45081a4, null)) != null && C0071a7.m210158c7(2)) {
            abstractComponentCallbacksC0069a5.toString();
        }
    }

    /* renamed from: b1 */
    public void m215415b1(Collection collection) {
        t60.m214695b6(collection, "workSpecs");
        synchronized (this.f61553a2) {
            try {
                for (AbstractC0799kx abstractC0799kx : (AbstractC0799kx[]) this.f61552a1) {
                    if (abstractC0799kx.f57744a4 != null) {
                        abstractC0799kx.f57744a4 = null;
                        abstractC0799kx.m213765a3(null, abstractC0799kx.f57743a3);
                    }
                }
                for (AbstractC0799kx abstractC0799kx2 : (AbstractC0799kx[]) this.f61552a1) {
                    abstractC0799kx2.m213764a2(collection);
                }
                for (AbstractC0799kx abstractC0799kx3 : (AbstractC0799kx[]) this.f61552a1) {
                    if (abstractC0799kx3.f57744a4 != this) {
                        abstractC0799kx3.f57744a4 = this;
                        abstractC0799kx3.m213765a3(this, abstractC0799kx3.f57743a3);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: b2 */
    public void m215416b2() {
        synchronized (this.f61553a2) {
            for (AbstractC0799kx abstractC0799kx : (AbstractC0799kx[]) this.f61552a1) {
                ArrayList arrayList = abstractC0799kx.f57741a1;
                if (!arrayList.isEmpty()) {
                    arrayList.clear();
                    abstractC0799kx.f57740a0.m213873a1(abstractC0799kx);
                }
            }
        }
    }

    @Override // p000.InterfaceC0532gd
    public void onCancel() {
        View view = (View) this.f61551a0;
        view.clearAnimation();
        ((ViewGroup) this.f61552a1).endViewTransition(view);
        ((C0067a3) this.f61553a2).m215007a1();
    }

    public zg1(x31 x31Var, bg1 bg1Var) {
        t60.m214695b6(x31Var, "trackers");
        AbstractC0826ln abstractC0826ln = (AbstractC0826ln) x31Var.f61012a0;
        t60.m214695b6(abstractC0826ln, "tracker");
        C0420dk c0420dk = new C0420dk(abstractC0826ln, 0);
        C0421dl c0421dl = (C0421dl) x31Var.f61013a1;
        t60.m214695b6(c0421dl, "tracker");
        C0420dk c0420dk2 = new C0420dk(c0421dl, 1);
        AbstractC0826ln abstractC0826ln2 = (AbstractC0826ln) x31Var.f61015a3;
        t60.m214695b6(abstractC0826ln2, "tracker");
        C0420dk c0420dk3 = new C0420dk(abstractC0826ln2, 4);
        AbstractC0826ln abstractC0826ln3 = (AbstractC0826ln) x31Var.f61014a2;
        t60.m214695b6(abstractC0826ln3, "tracker");
        C0420dk c0420dk4 = new C0420dk(abstractC0826ln3, 2);
        t60.m214695b6(abstractC0826ln3, "tracker");
        C0420dk c0420dk5 = new C0420dk(abstractC0826ln3, 3);
        t60.m214695b6(abstractC0826ln3, "tracker");
        qj0 qj0Var = new qj0(abstractC0826ln3);
        t60.m214695b6(abstractC0826ln3, "tracker");
        AbstractC0799kx[] abstractC0799kxArr = {c0420dk, c0420dk2, c0420dk3, c0420dk4, c0420dk5, qj0Var, new oj0(abstractC0826ln3)};
        this.f61551a0 = bg1Var;
        this.f61552a1 = abstractC0799kxArr;
        this.f61553a2 = new Object();
    }

    public zg1(tg0 tg0Var) {
        this.f61551a0 = tg0Var;
        this.f61552a1 = Choreographer.getInstance();
        this.f61553a2 = new ChoreographerFrameCallbackC1247t5(this);
    }
}
