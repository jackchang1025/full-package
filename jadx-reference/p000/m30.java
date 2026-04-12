package p000;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import androidx.work.WorkInfo$State;
import androidx.work.impl.C0096a0;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class m30 implements du0, bg1, InterfaceC1425xp {

    /* renamed from: a0 */
    public final Context f58249a0;

    /* renamed from: a1 */
    public final C0096a0 f58250a1;

    /* renamed from: a2 */
    public final zg1 f58251a2;

    /* renamed from: a4 */
    public final C1194rv f58253a4;

    /* renamed from: a5 */
    public boolean f58254a5;

    /* renamed from: a8 */
    public Boolean f58257a8;

    /* renamed from: a3 */
    public final HashSet f58252a3 = new HashSet();

    /* renamed from: a7 */
    public final og1 f58256a7 = new og1();

    /* renamed from: a6 */
    public final Object f58255a6 = new Object();

    static {
        C1351vv.m214966b1("GreedyScheduler");
    }

    public m30(Context context, C0793kr c0793kr, x31 x31Var, C0096a0 c0096a0) {
        this.f58249a0 = context;
        this.f58250a1 = c0096a0;
        this.f58251a2 = new zg1(x31Var, this);
        this.f58253a4 = new C1194rv(this, (tg0) c0793kr.f57713a6);
    }

    @Override // p000.du0
    /* renamed from: a0 */
    public final void mo212640a0(String str) {
        Runnable runnable;
        Boolean bool = this.f58257a8;
        C0096a0 c0096a0 = this.f58250a1;
        if (bool == null) {
            this.f58257a8 = Boolean.valueOf(qo0.m214465a0(this.f58249a0, c0096a0.f45558a5));
        }
        if (!this.f58257a8.booleanValue()) {
            C1351vv.m214963a5().getClass();
            return;
        }
        if (!this.f58254a5) {
            c0096a0.f45562a9.m214651a0(this);
            this.f58254a5 = true;
        }
        C1351vv.m214963a5().getClass();
        C1194rv c1194rv = this.f58253a4;
        if (c1194rv != null && (runnable = (Runnable) c1194rv.f59824a2.remove(str)) != null) {
            ((Handler) c1194rv.f59823a1.f60218a1).removeCallbacks(runnable);
        }
        Iterator it = this.f58256a7.m214211b3(str).iterator();
        while (it.hasNext()) {
            c0096a0.f45560a7.m214272b6(new f21(c0096a0, (x11) it.next(), false));
        }
    }

    @Override // p000.bg1
    /* renamed from: a1 */
    public final void mo210487a1(ArrayList arrayList) {
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            jg1 jg1VarM212483b3 = cq0.m212483b3((wg1) obj);
            C1351vv c1351vvM214963a5 = C1351vv.m214963a5();
            jg1VarM212483b3.toString();
            c1351vvM214963a5.getClass();
            x11 x11VarM214210b2 = this.f58256a7.m214210b2(jg1VarM212483b3);
            if (x11VarM214210b2 != null) {
                C0096a0 c0096a0 = this.f58250a1;
                c0096a0.f45560a7.m214272b6(new f21(c0096a0, x11VarM214210b2, false));
            }
        }
    }

    @Override // p000.du0
    /* renamed from: a2 */
    public final void mo212641a2(wg1... wg1VarArr) {
        if (this.f58257a8 == null) {
            this.f58257a8 = Boolean.valueOf(qo0.m214465a0(this.f58249a0, this.f58250a1.f45558a5));
        }
        if (!this.f58257a8.booleanValue()) {
            C1351vv.m214963a5().getClass();
            return;
        }
        if (!this.f58254a5) {
            this.f58250a1.f45562a9.m214651a0(this);
            this.f58254a5 = true;
        }
        HashSet hashSet = new HashSet();
        HashSet hashSet2 = new HashSet();
        for (wg1 wg1Var : wg1VarArr) {
            if (!this.f58256a7.m214201a1(cq0.m212483b3(wg1Var))) {
                long jM215066a0 = wg1Var.m215066a0();
                long jCurrentTimeMillis = System.currentTimeMillis();
                if (wg1Var.f60913a1 == WorkInfo$State.f45526a0) {
                    if (jCurrentTimeMillis < jM215066a0) {
                        C1194rv c1194rv = this.f58253a4;
                        if (c1194rv != null) {
                            tg0 tg0Var = c1194rv.f59823a1;
                            HashMap map = c1194rv.f59824a2;
                            Runnable runnable = (Runnable) map.remove(wg1Var.f60912a0);
                            if (runnable != null) {
                                ((Handler) tg0Var.f60218a1).removeCallbacks(runnable);
                            }
                            RunnableC0884n2 runnableC0884n2 = new RunnableC0884n2(c1194rv, 5, wg1Var);
                            map.put(wg1Var.f60912a0, runnableC0884n2);
                            ((Handler) tg0Var.f60218a1).postDelayed(runnableC0884n2, wg1Var.m215066a0() - System.currentTimeMillis());
                        }
                    } else if (wg1Var.m215067a2()) {
                        C0836lv c0836lv = wg1Var.f60921a9;
                        if (c0836lv.f58195a2) {
                            C1351vv c1351vvM214963a5 = C1351vv.m214963a5();
                            wg1Var.toString();
                            c1351vvM214963a5.getClass();
                        } else if (c0836lv.f58200a7.isEmpty()) {
                            hashSet.add(wg1Var);
                            hashSet2.add(wg1Var.f60912a0);
                        } else {
                            C1351vv c1351vvM214963a52 = C1351vv.m214963a5();
                            wg1Var.toString();
                            c1351vvM214963a52.getClass();
                        }
                    } else if (!this.f58256a7.m214201a1(cq0.m212483b3(wg1Var))) {
                        C1351vv.m214963a5().getClass();
                        C0096a0 c0096a0 = this.f58250a1;
                        og1 og1Var = this.f58256a7;
                        og1Var.getClass();
                        c0096a0.m210478g4(og1Var.m214214b6(cq0.m212483b3(wg1Var)), null);
                    }
                }
            }
        }
        synchronized (this.f58255a6) {
            try {
                if (!hashSet.isEmpty()) {
                    TextUtils.join(",", hashSet2);
                    C1351vv.m214963a5().getClass();
                    this.f58252a3.addAll(hashSet);
                    this.f58251a2.m215415b1(this.f58252a3);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // p000.bg1
    /* renamed from: a3 */
    public final void mo210488a3(List list) {
        ArrayList arrayList = (ArrayList) list;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            jg1 jg1VarM212483b3 = cq0.m212483b3((wg1) obj);
            og1 og1Var = this.f58256a7;
            if (!og1Var.m214201a1(jg1VarM212483b3)) {
                C1351vv c1351vvM214963a5 = C1351vv.m214963a5();
                jg1VarM212483b3.toString();
                c1351vvM214963a5.getClass();
                this.f58250a1.m210478g4(og1Var.m214214b6(jg1VarM212483b3), null);
            }
        }
    }

    @Override // p000.InterfaceC1425xp
    /* renamed from: a4 */
    public final void mo210482a4(jg1 jg1Var, boolean z) {
        this.f58256a7.m214210b2(jg1Var);
        synchronized (this.f58255a6) {
            try {
                Iterator it = this.f58252a3.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    wg1 wg1Var = (wg1) it.next();
                    if (cq0.m212483b3(wg1Var).equals(jg1Var)) {
                        C1351vv c1351vvM214963a5 = C1351vv.m214963a5();
                        Objects.toString(jg1Var);
                        c1351vvM214963a5.getClass();
                        this.f58252a3.remove(wg1Var);
                        this.f58251a2.m215415b1(this.f58252a3);
                        break;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // p000.du0
    /* renamed from: a5 */
    public final boolean mo212642a5() {
        return false;
    }
}
