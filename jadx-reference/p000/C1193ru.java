package p000;

import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import androidx.work.impl.background.systemalarm.SystemAlarmService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ru */
/* loaded from: classes2.dex */
public final class C1193ru implements bg1, ah1 {

    /* renamed from: a0 */
    public final Context f59806a0;

    /* renamed from: a1 */
    public final int f59807a1;

    /* renamed from: a2 */
    public final jg1 f59808a2;

    /* renamed from: a3 */
    public final q31 f59809a3;

    /* renamed from: a4 */
    public final zg1 f59810a4;

    /* renamed from: a5 */
    public final Object f59811a5;

    /* renamed from: a6 */
    public int f59812a6;

    /* renamed from: a7 */
    public final ExecutorC0034an f59813a7;

    /* renamed from: a8 */
    public final mg1 f59814a8;

    /* renamed from: a9 */
    public PowerManager.WakeLock f59815a9;

    /* renamed from: b0 */
    public boolean f59816b0;

    /* renamed from: b1 */
    public final x11 f59817b1;

    static {
        C1351vv.m214966b1("DelayMetCommandHandler");
    }

    public C1193ru(Context context, int i, q31 q31Var, x11 x11Var) {
        this.f59806a0 = context;
        this.f59807a1 = i;
        this.f59809a3 = q31Var;
        this.f59808a2 = x11Var.f60991a0;
        this.f59817b1 = x11Var;
        x31 x31Var = q31Var.f59379a4.f45566b3;
        pg1 pg1Var = q31Var.f59376a1;
        this.f59813a7 = (ExecutorC0034an) pg1Var.f59229a1;
        this.f59814a8 = (mg1) pg1Var.f59231a3;
        this.f59810a4 = new zg1(x31Var, this);
        this.f59816b0 = false;
        this.f59812a6 = 0;
        this.f59811a5 = new Object();
    }

    /* renamed from: a0 */
    public static void m214546a0(C1193ru c1193ru) {
        int i = c1193ru.f59807a1;
        mg1 mg1Var = c1193ru.f59814a8;
        Context context = c1193ru.f59806a0;
        q31 q31Var = c1193ru.f59809a3;
        jg1 jg1Var = c1193ru.f59808a2;
        if (c1193ru.f59812a6 >= 2) {
            C1351vv.m214963a5().getClass();
            return;
        }
        c1193ru.f59812a6 = 2;
        C1351vv.m214963a5().getClass();
        Intent intent = new Intent(context, (Class<?>) SystemAlarmService.class);
        intent.setAction("ACTION_STOP_WORK");
        C0727jq.m213336a2(intent, jg1Var);
        mg1Var.execute(new RunnableC0707j6(i, 3, q31Var, intent));
        if (!q31Var.f59378a3.m214652a2(jg1Var.f57334a0)) {
            C1351vv.m214963a5().getClass();
            return;
        }
        C1351vv.m214963a5().getClass();
        Intent intent2 = new Intent(context, (Class<?>) SystemAlarmService.class);
        intent2.setAction("ACTION_SCHEDULE_WORK");
        C0727jq.m213336a2(intent2, jg1Var);
        mg1Var.execute(new RunnableC0707j6(i, 3, q31Var, intent2));
    }

    @Override // p000.bg1
    /* renamed from: a1 */
    public final void mo210487a1(ArrayList arrayList) {
        this.f59813a7.execute(new RunnableC1192rt(this, 0));
    }

    /* renamed from: a2 */
    public final void m214547a2() {
        synchronized (this.f59811a5) {
            try {
                this.f59810a4.m215416b2();
                this.f59809a3.f59377a2.m210853a0(this.f59808a2);
                PowerManager.WakeLock wakeLock = this.f59815a9;
                if (wakeLock != null && wakeLock.isHeld()) {
                    C1351vv c1351vvM214963a5 = C1351vv.m214963a5();
                    Objects.toString(this.f59815a9);
                    Objects.toString(this.f59808a2);
                    c1351vvM214963a5.getClass();
                    this.f59815a9.release();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // p000.bg1
    /* renamed from: a3 */
    public final void mo210488a3(List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            if (cq0.m212483b3((wg1) it.next()).equals(this.f59808a2)) {
                this.f59813a7.execute(new RunnableC1192rt(this, 1));
                return;
            }
        }
    }

    /* renamed from: a4 */
    public final void m214548a4() throws Throwable {
        String str = this.f59808a2.f57334a0;
        StringBuilder sbM39c0 = AbstractC0003a2.m39c0(str, " (");
        sbM39c0.append(this.f59807a1);
        sbM39c0.append(")");
        this.f59815a9 = he1.m213032a0(this.f59806a0, sbM39c0.toString());
        C1351vv c1351vvM214963a5 = C1351vv.m214963a5();
        Objects.toString(this.f59815a9);
        c1351vvM214963a5.getClass();
        this.f59815a9.acquire();
        wg1 wg1VarM215185a8 = this.f59809a3.f59379a4.f45559a6.mo210465b9().m215185a8(str);
        if (wg1VarM215185a8 == null) {
            this.f59813a7.execute(new RunnableC1192rt(this, 0));
            return;
        }
        boolean zM215067a2 = wg1VarM215185a8.m215067a2();
        this.f59816b0 = zM215067a2;
        if (zM215067a2) {
            this.f59810a4.m215415b1(Collections.singletonList(wg1VarM215185a8));
        } else {
            C1351vv.m214963a5().getClass();
            mo210488a3(Collections.singletonList(wg1VarM215185a8));
        }
    }

    /* renamed from: a5 */
    public final void m214549a5(boolean z) {
        C1351vv c1351vvM214963a5 = C1351vv.m214963a5();
        jg1 jg1Var = this.f59808a2;
        Objects.toString(jg1Var);
        c1351vvM214963a5.getClass();
        m214547a2();
        int i = this.f59807a1;
        q31 q31Var = this.f59809a3;
        mg1 mg1Var = this.f59814a8;
        Context context = this.f59806a0;
        if (z) {
            Intent intent = new Intent(context, (Class<?>) SystemAlarmService.class);
            intent.setAction("ACTION_SCHEDULE_WORK");
            C0727jq.m213336a2(intent, jg1Var);
            mg1Var.execute(new RunnableC0707j6(i, 3, q31Var, intent));
        }
        if (this.f59816b0) {
            Intent intent2 = new Intent(context, (Class<?>) SystemAlarmService.class);
            intent2.setAction("ACTION_CONSTRAINTS_CHANGED");
            mg1Var.execute(new RunnableC0707j6(i, 3, q31Var, intent2));
        }
    }
}
