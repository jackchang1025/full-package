package p000;

import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;
import android.view.ActionMode;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.core.internal.view.SupportMenu;
import androidx.fragment.app.AbstractComponentCallbacksC0069a5;
import androidx.fragment.app.C0071a7;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.C0077a1;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.utils.futures.C0100a1;
import java.util.HashMap;
import java.util.Iterator;
import java.util.WeakHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class eo0 {

    /* renamed from: a3 */
    public static final ol0 f56085a3 = new ol0();

    /* renamed from: a4 */
    public static final nl0 f56086a4 = new nl0();

    /* renamed from: a0 */
    public final /* synthetic */ int f56087a0;

    /* renamed from: a1 */
    public Object f56088a1;

    /* renamed from: a2 */
    public Object f56089a2;

    public eo0(WorkDatabase workDatabase) {
        this.f56087a0 = 0;
        this.f56088a1 = workDatabase;
        this.f56089a2 = new C1216sb(workDatabase);
    }

    /* renamed from: a0 */
    public void m212696a0(dr0 dr0Var, fj0 fj0Var) {
        t01 t01Var = (t01) this.f56088a1;
        hb1 hb1VarM213018a0 = (hb1) t01Var.getOrDefault(dr0Var, null);
        if (hb1VarM213018a0 == null) {
            hb1VarM213018a0 = hb1.m213018a0();
            t01Var.put(dr0Var, hb1VarM213018a0);
        }
        hb1VarM213018a0.f56645a2 = fj0Var;
        hb1VarM213018a0.f56643a0 |= 8;
    }

    /* renamed from: a1 */
    public void m212697a1(boolean z) {
        AbstractComponentCallbacksC0069a5 abstractComponentCallbacksC0069a5 = ((C0071a7) this.f56089a2).f45137b5;
        if (abstractComponentCallbacksC0069a5 != null) {
            abstractComponentCallbacksC0069a5.m210137b0().f45132b0.m212697a1(true);
        }
        Iterator it = ((CopyOnWriteArrayList) this.f56088a1).iterator();
        if (it.hasNext()) {
            if (it.next() != null) {
                throw new ClassCastException();
            }
            if (!z) {
                throw null;
            }
            throw null;
        }
    }

    /* renamed from: a2 */
    public void m212698a2(boolean z) {
        C0071a7 c0071a7 = (C0071a7) this.f56089a2;
        FragmentActivity fragmentActivity = c0071a7.f45135b3.f61419c7;
        AbstractComponentCallbacksC0069a5 abstractComponentCallbacksC0069a5 = c0071a7.f45137b5;
        if (abstractComponentCallbacksC0069a5 != null) {
            abstractComponentCallbacksC0069a5.m210137b0().f45132b0.m212698a2(true);
        }
        Iterator it = ((CopyOnWriteArrayList) this.f56088a1).iterator();
        if (it.hasNext()) {
            if (it.next() != null) {
                throw new ClassCastException();
            }
            if (!z) {
                throw null;
            }
            throw null;
        }
    }

    /* renamed from: a3 */
    public void m212699a3(boolean z) {
        AbstractComponentCallbacksC0069a5 abstractComponentCallbacksC0069a5 = ((C0071a7) this.f56089a2).f45137b5;
        if (abstractComponentCallbacksC0069a5 != null) {
            abstractComponentCallbacksC0069a5.m210137b0().f45132b0.m212699a3(true);
        }
        Iterator it = ((CopyOnWriteArrayList) this.f56088a1).iterator();
        if (it.hasNext()) {
            if (it.next() != null) {
                throw new ClassCastException();
            }
            if (!z) {
                throw null;
            }
            throw null;
        }
    }

    /* renamed from: a4 */
    public void m212700a4(boolean z) {
        AbstractComponentCallbacksC0069a5 abstractComponentCallbacksC0069a5 = ((C0071a7) this.f56089a2).f45137b5;
        if (abstractComponentCallbacksC0069a5 != null) {
            abstractComponentCallbacksC0069a5.m210137b0().f45132b0.m212700a4(true);
        }
        Iterator it = ((CopyOnWriteArrayList) this.f56088a1).iterator();
        if (it.hasNext()) {
            if (it.next() != null) {
                throw new ClassCastException();
            }
            if (!z) {
                throw null;
            }
            throw null;
        }
    }

    /* renamed from: a5 */
    public void m212701a5(boolean z) {
        AbstractComponentCallbacksC0069a5 abstractComponentCallbacksC0069a5 = ((C0071a7) this.f56089a2).f45137b5;
        if (abstractComponentCallbacksC0069a5 != null) {
            abstractComponentCallbacksC0069a5.m210137b0().f45132b0.m212701a5(true);
        }
        Iterator it = ((CopyOnWriteArrayList) this.f56088a1).iterator();
        if (it.hasNext()) {
            if (it.next() != null) {
                throw new ClassCastException();
            }
            if (!z) {
                throw null;
            }
            throw null;
        }
    }

    /* renamed from: a6 */
    public void m212702a6(boolean z) {
        AbstractComponentCallbacksC0069a5 abstractComponentCallbacksC0069a5 = ((C0071a7) this.f56089a2).f45137b5;
        if (abstractComponentCallbacksC0069a5 != null) {
            abstractComponentCallbacksC0069a5.m210137b0().f45132b0.m212702a6(true);
        }
        Iterator it = ((CopyOnWriteArrayList) this.f56088a1).iterator();
        if (it.hasNext()) {
            if (it.next() != null) {
                throw new ClassCastException();
            }
            if (!z) {
                throw null;
            }
            throw null;
        }
    }

    /* renamed from: a7 */
    public void m212703a7(boolean z) {
        C0071a7 c0071a7 = (C0071a7) this.f56089a2;
        FragmentActivity fragmentActivity = c0071a7.f45135b3.f61419c7;
        AbstractComponentCallbacksC0069a5 abstractComponentCallbacksC0069a5 = c0071a7.f45137b5;
        if (abstractComponentCallbacksC0069a5 != null) {
            abstractComponentCallbacksC0069a5.m210137b0().f45132b0.m212703a7(true);
        }
        Iterator it = ((CopyOnWriteArrayList) this.f56088a1).iterator();
        if (it.hasNext()) {
            if (it.next() != null) {
                throw new ClassCastException();
            }
            if (!z) {
                throw null;
            }
            throw null;
        }
    }

    /* renamed from: a8 */
    public void m212704a8(boolean z) {
        AbstractComponentCallbacksC0069a5 abstractComponentCallbacksC0069a5 = ((C0071a7) this.f56089a2).f45137b5;
        if (abstractComponentCallbacksC0069a5 != null) {
            abstractComponentCallbacksC0069a5.m210137b0().f45132b0.m212704a8(true);
        }
        Iterator it = ((CopyOnWriteArrayList) this.f56088a1).iterator();
        if (it.hasNext()) {
            if (it.next() != null) {
                throw new ClassCastException();
            }
            if (!z) {
                throw null;
            }
            throw null;
        }
    }

    /* renamed from: a9 */
    public void m212705a9(boolean z) {
        AbstractComponentCallbacksC0069a5 abstractComponentCallbacksC0069a5 = ((C0071a7) this.f56089a2).f45137b5;
        if (abstractComponentCallbacksC0069a5 != null) {
            abstractComponentCallbacksC0069a5.m210137b0().f45132b0.m212705a9(true);
        }
        Iterator it = ((CopyOnWriteArrayList) this.f56088a1).iterator();
        if (it.hasNext()) {
            if (it.next() != null) {
                throw new ClassCastException();
            }
            if (!z) {
                throw null;
            }
            throw null;
        }
    }

    /* renamed from: b0 */
    public void m212706b0(boolean z) {
        AbstractComponentCallbacksC0069a5 abstractComponentCallbacksC0069a5 = ((C0071a7) this.f56089a2).f45137b5;
        if (abstractComponentCallbacksC0069a5 != null) {
            abstractComponentCallbacksC0069a5.m210137b0().f45132b0.m212706b0(true);
        }
        Iterator it = ((CopyOnWriteArrayList) this.f56088a1).iterator();
        if (it.hasNext()) {
            if (it.next() != null) {
                throw new ClassCastException();
            }
            if (!z) {
                throw null;
            }
            throw null;
        }
    }

    /* renamed from: b1 */
    public void m212707b1(boolean z) {
        AbstractComponentCallbacksC0069a5 abstractComponentCallbacksC0069a5 = ((C0071a7) this.f56089a2).f45137b5;
        if (abstractComponentCallbacksC0069a5 != null) {
            abstractComponentCallbacksC0069a5.m210137b0().f45132b0.m212707b1(true);
        }
        Iterator it = ((CopyOnWriteArrayList) this.f56088a1).iterator();
        if (it.hasNext()) {
            if (it.next() != null) {
                throw new ClassCastException();
            }
            if (!z) {
                throw null;
            }
            throw null;
        }
    }

    /* renamed from: b2 */
    public void m212708b2(boolean z) {
        AbstractComponentCallbacksC0069a5 abstractComponentCallbacksC0069a5 = ((C0071a7) this.f56089a2).f45137b5;
        if (abstractComponentCallbacksC0069a5 != null) {
            abstractComponentCallbacksC0069a5.m210137b0().f45132b0.m212708b2(true);
        }
        Iterator it = ((CopyOnWriteArrayList) this.f56088a1).iterator();
        if (it.hasNext()) {
            if (it.next() != null) {
                throw new ClassCastException();
            }
            if (!z) {
                throw null;
            }
            throw null;
        }
    }

    /* renamed from: b3 */
    public void m212709b3(boolean z) {
        AbstractComponentCallbacksC0069a5 abstractComponentCallbacksC0069a5 = ((C0071a7) this.f56089a2).f45137b5;
        if (abstractComponentCallbacksC0069a5 != null) {
            abstractComponentCallbacksC0069a5.m210137b0().f45132b0.m212709b3(true);
        }
        Iterator it = ((CopyOnWriteArrayList) this.f56088a1).iterator();
        if (it.hasNext()) {
            if (it.next() != null) {
                throw new ClassCastException();
            }
            if (!z) {
                throw null;
            }
            throw null;
        }
    }

    /* renamed from: b4 */
    public void m212710b4(boolean z) {
        AbstractComponentCallbacksC0069a5 abstractComponentCallbacksC0069a5 = ((C0071a7) this.f56089a2).f45137b5;
        if (abstractComponentCallbacksC0069a5 != null) {
            abstractComponentCallbacksC0069a5.m210137b0().f45132b0.m212710b4(true);
        }
        Iterator it = ((CopyOnWriteArrayList) this.f56088a1).iterator();
        if (it.hasNext()) {
            if (it.next() != null) {
                throw new ClassCastException();
            }
            if (!z) {
                throw null;
            }
            throw null;
        }
    }

    /* renamed from: b5 */
    public Long m212711b5(String str) {
        fs0 fs0Var = (fs0) this.f56088a1;
        js0 js0VarAcquire = js0.f57367a8.acquire("SELECT long_value FROM Preference where `key`=?", 1);
        js0VarAcquire.mo213341a6(1, str);
        fs0Var.m212857a1();
        Cursor cursorM213580c7 = kj1.m213580c7(fs0Var, js0VarAcquire);
        try {
            Long lValueOf = null;
            if (cursorM213580c7.moveToFirst() && !cursorM213580c7.isNull(0)) {
                lValueOf = Long.valueOf(cursorM213580c7.getLong(0));
            }
            return lValueOf;
        } finally {
            cursorM213580c7.close();
            js0VarAcquire.m213344b0();
        }
    }

    /* renamed from: b6 */
    public void m212712b6(do0 do0Var) {
        fs0 fs0Var = (fs0) this.f56088a1;
        fs0Var.m212857a1();
        fs0Var.m212858a2();
        try {
            ((C1216sb) this.f56089a2).m214590a5(do0Var);
            fs0Var.m212863b2();
        } finally {
            fs0Var.m212860a9();
        }
    }

    /* renamed from: b7 */
    public void m212713b7(AbstractC1117qo abstractC1117qo) {
        boolean z;
        C0077a1 c0077a1 = (C0077a1) this.f56088a1;
        synchronized (c0077a1.f45198a0) {
            z = c0077a1.f45203a5 == C0077a1.f45197b0;
            c0077a1.f45203a5 = abstractC1117qo;
        }
        if (z) {
            C0112aw c0112awM210524f5 = C0112aw.m210524f5();
            RunnableC0165ca runnableC0165ca = c0077a1.f45207a9;
            C1187ro c1187ro = c0112awM210524f5.f45650c6;
            if (c1187ro.f59798c8 == null) {
                synchronized (c1187ro.f59796c6) {
                    try {
                        if (c1187ro.f59798c8 == null) {
                            c1187ro.f59798c8 = C1187ro.m214545f5(Looper.getMainLooper());
                        }
                    } finally {
                    }
                }
            }
            c1187ro.f59798c8.post(runnableC0165ca);
        }
        if (abstractC1117qo instanceof ol0) {
            ((C0100a1) this.f56089a2).m210484a8((ol0) abstractC1117qo);
        } else if (abstractC1117qo instanceof ml0) {
            ((C0100a1) this.f56089a2).m210485a9(((ml0) abstractC1117qo).f58381a8);
        }
    }

    /* renamed from: b8 */
    public void m212714b8(AbstractC0903n7 abstractC0903n7) {
        x31 x31Var = (x31) this.f56088a1;
        ((ActionMode.Callback) x31Var.f61012a0).onDestroyActionMode(x31Var.m215109a1(abstractC0903n7));
        LayoutInflaterFactory2C1367w8 layoutInflaterFactory2C1367w8 = (LayoutInflaterFactory2C1367w8) this.f56089a2;
        if (layoutInflaterFactory2C1367w8.f60821c2 != null) {
            layoutInflaterFactory2C1367w8.f60810b1.getDecorView().removeCallbacks(layoutInflaterFactory2C1367w8.f60822c3);
        }
        if (layoutInflaterFactory2C1367w8.f60820c1 != null) {
            mc1 mc1Var = layoutInflaterFactory2C1367w8.f60823c4;
            if (mc1Var != null) {
                mc1Var.m213968a1();
            }
            mc1 mc1VarM215138a0 = xa1.m215138a0(layoutInflaterFactory2C1367w8.f60820c1);
            mc1VarM215138a0.m213967a0(0.0f);
            layoutInflaterFactory2C1367w8.f60823c4 = mc1VarM215138a0;
            mc1VarM215138a0.m213970a3(new C1328v8(2, this));
        }
        layoutInflaterFactory2C1367w8.f60819c0 = null;
        ViewGroup viewGroup = layoutInflaterFactory2C1367w8.f60825c6;
        WeakHashMap weakHashMap = xa1.f61054a0;
        ja1.m213282a2(viewGroup);
        layoutInflaterFactory2C1367w8.m215038d3();
    }

    /* renamed from: b9 */
    public boolean m212715b9(AbstractC0903n7 abstractC0903n7, Menu menu) {
        ViewGroup viewGroup = ((LayoutInflaterFactory2C1367w8) this.f56089a2).f60825c6;
        WeakHashMap weakHashMap = xa1.f61054a0;
        ja1.m213282a2(viewGroup);
        x31 x31Var = (x31) this.f56088a1;
        ActionMode.Callback callback = (ActionMode.Callback) x31Var.f61012a0;
        z21 z21VarM215109a1 = x31Var.m215109a1(abstractC0903n7);
        t01 t01Var = (t01) x31Var.f61015a3;
        Menu wf0Var = (Menu) t01Var.getOrDefault(menu, null);
        if (wf0Var == null) {
            wf0Var = new wf0((Context) x31Var.f61013a1, (SupportMenu) menu);
            t01Var.put(menu, wf0Var);
        }
        return callback.onPrepareActionMode(z21VarM215109a1, wf0Var);
    }

    /* renamed from: c0 */
    public void m212716c0(C0739k c0739k) {
        Handler handler = (Handler) this.f56089a2;
        jl0 jl0Var = (jl0) this.f56088a1;
        int i = c0739k.f57403a1;
        if (i == 0) {
            handler.post(new RunnableC0884n2(jl0Var, c0739k.f57402a0, 4, false));
        } else {
            handler.post(new RunnableC0503fo(jl0Var, i, 0));
        }
    }

    /* renamed from: c1 */
    public fj0 m212717c1(dr0 dr0Var, int i) {
        hb1 hb1Var;
        fj0 fj0Var;
        t01 t01Var = (t01) this.f56088a1;
        int iM214676a4 = t01Var.m214676a4(dr0Var);
        if (iM214676a4 >= 0 && (hb1Var = (hb1) t01Var.m214681a9(iM214676a4)) != null) {
            int i2 = hb1Var.f56643a0;
            if ((i2 & i) != 0) {
                int i3 = i2 & (~i);
                hb1Var.f56643a0 = i3;
                if (i == 4) {
                    fj0Var = hb1Var.f56644a1;
                } else {
                    if (i != 8) {
                        throw new IllegalArgumentException("Must provide flag PRE or POST");
                    }
                    fj0Var = hb1Var.f56645a2;
                }
                if ((i3 & 12) == 0) {
                    t01Var.m214680a8(iM214676a4);
                    hb1Var.f56643a0 = 0;
                    hb1Var.f56644a1 = null;
                    hb1Var.f56645a2 = null;
                    hb1.f56642a3.mo214934a2(hb1Var);
                }
                return fj0Var;
            }
        }
        return null;
    }

    /* renamed from: c2 */
    public void m212718c2(dr0 dr0Var) {
        hb1 hb1Var = (hb1) ((t01) this.f56088a1).getOrDefault(dr0Var, null);
        if (hb1Var == null) {
            return;
        }
        hb1Var.f56643a0 &= -2;
    }

    /* renamed from: c3 */
    public void m212719c3(dr0 dr0Var) {
        nc0 nc0Var = (nc0) this.f56089a2;
        int iM214068a4 = nc0Var.m214068a4() - 1;
        while (true) {
            if (iM214068a4 < 0) {
                break;
            }
            if (dr0Var == nc0Var.m214069a5(iM214068a4)) {
                Object[] objArr = nc0Var.f58495a2;
                Object obj = objArr[iM214068a4];
                Object obj2 = nc0.f58492a4;
                if (obj != obj2) {
                    objArr[iM214068a4] = obj2;
                    nc0Var.f58493a0 = true;
                }
            } else {
                iM214068a4--;
            }
        }
        hb1 hb1Var = (hb1) ((t01) this.f56088a1).remove(dr0Var);
        if (hb1Var != null) {
            hb1Var.f56643a0 = 0;
            hb1Var.f56644a1 = null;
            hb1Var.f56645a2 = null;
            hb1.f56642a3.mo214934a2(hb1Var);
        }
    }

    public String toString() {
        switch (this.f56087a0) {
            case 8:
                String string = "[ ";
                if (((e11) this.f56088a1) != null) {
                    for (int i = 0; i < 9; i++) {
                        StringBuilder sbM37b8 = AbstractC0003a2.m37b8(string);
                        sbM37b8.append(((e11) this.f56088a1).f55903a7[i]);
                        sbM37b8.append(" ");
                        string = sbM37b8.toString();
                    }
                }
                StringBuilder sbM39c0 = AbstractC0003a2.m39c0(string, "] ");
                sbM39c0.append((e11) this.f56088a1);
                return sbM39c0.toString();
            default:
                return super.toString();
        }
    }

    public eo0(int i) {
        this.f56087a0 = i;
        switch (i) {
            case 5:
                break;
            case 9:
                this.f56088a1 = new t01();
                this.f56089a2 = new nc0();
                break;
            default:
                this.f56088a1 = new C0077a1();
                this.f56089a2 = new C0100a1();
                m212713b7(f56086a4);
                break;
        }
    }

    public eo0(go0 go0Var) {
        this.f56087a0 = 8;
        this.f56089a2 = go0Var;
    }

    public eo0(jl0 jl0Var, Handler handler) {
        this.f56087a0 = 3;
        this.f56088a1 = jl0Var;
        this.f56089a2 = handler;
    }

    public eo0(C0071a7 c0071a7) {
        this.f56087a0 = 6;
        this.f56088a1 = new CopyOnWriteArrayList();
        this.f56089a2 = c0071a7;
    }

    public eo0(Runnable runnable) {
        this.f56087a0 = 7;
        this.f56089a2 = new CopyOnWriteArrayList();
        new HashMap();
        this.f56088a1 = runnable;
    }

    public eo0(EditText editText) {
        this.f56087a0 = 4;
        this.f56088a1 = editText;
        C1389wt c1389wt = new C1389wt(editText);
        this.f56089a2 = c1389wt;
        editText.addTextChangedListener(c1389wt);
        if (C1379wj.f60937a1 == null) {
            synchronized (C1379wj.f60936a0) {
                try {
                    if (C1379wj.f60937a1 == null) {
                        C1379wj c1379wj = new C1379wj();
                        try {
                            C1379wj.f60938a2 = Class.forName("android.text.DynamicLayout$ChangeWatcher", false, C1379wj.class.getClassLoader());
                        } catch (Throwable unused) {
                        }
                        C1379wj.f60937a1 = c1379wj;
                    }
                } finally {
                }
            }
        }
        editText.setEditableFactory(C1379wj.f60937a1);
    }

    public eo0(LayoutInflaterFactory2C1367w8 layoutInflaterFactory2C1367w8, x31 x31Var) {
        this.f56087a0 = 2;
        this.f56089a2 = layoutInflaterFactory2C1367w8;
        this.f56088a1 = x31Var;
    }
}
