package p000;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.work.NetworkType;
import androidx.work.impl.C0096a0;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.WorkDatabase_Impl;
import androidx.work.impl.background.systemalarm.ConstraintProxyUpdateReceiver;
import androidx.work.impl.background.systemalarm.SystemAlarmService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: jq */
/* loaded from: classes2.dex */
public final class C0727jq implements InterfaceC1425xp {

    /* renamed from: a4 */
    public static final /* synthetic */ int f57353a4 = 0;

    /* renamed from: a0 */
    public final Context f57354a0;

    /* renamed from: a1 */
    public final HashMap f57355a1 = new HashMap();

    /* renamed from: a2 */
    public final Object f57356a2 = new Object();

    /* renamed from: a3 */
    public final og1 f57357a3;

    static {
        C1351vv.m214966b1("CommandHandler");
    }

    public C0727jq(Context context, og1 og1Var) {
        this.f57354a0 = context;
        this.f57357a3 = og1Var;
    }

    /* renamed from: a1 */
    public static jg1 m213335a1(Intent intent) {
        return new jg1(intent.getStringExtra("KEY_WORKSPEC_ID"), intent.getIntExtra("KEY_WORKSPEC_GENERATION", 0));
    }

    /* renamed from: a2 */
    public static void m213336a2(Intent intent, jg1 jg1Var) {
        intent.putExtra("KEY_WORKSPEC_ID", jg1Var.f57334a0);
        intent.putExtra("KEY_WORKSPEC_GENERATION", jg1Var.f57335a1);
    }

    /* renamed from: a0 */
    public final void m213337a0(Intent intent, int i, q31 q31Var) {
        List<x11> listM214211b3;
        int i2;
        String action = intent.getAction();
        int i3 = 3;
        if ("ACTION_CONSTRAINTS_CHANGED".equals(action)) {
            C1351vv c1351vvM214963a5 = C1351vv.m214963a5();
            Objects.toString(intent);
            c1351vvM214963a5.getClass();
            Context context = this.f57354a0;
            C0840lx c0840lx = new C0840lx(context, i, q31Var);
            zg1 zg1Var = c0840lx.f58204a1;
            ArrayList arrayListM215182a5 = q31Var.f59379a4.f45559a6.mo210465b9().m215182a5();
            int i4 = AbstractC0817le.f57897a0;
            int size = arrayListM215182a5.size();
            boolean z = false;
            boolean z2 = false;
            boolean z3 = false;
            boolean z4 = false;
            int i5 = 0;
            while (true) {
                if (i5 >= size) {
                    i2 = 0;
                    break;
                }
                Object obj = arrayListM215182a5.get(i5);
                i5++;
                C0836lv c0836lv = ((wg1) obj).f60921a9;
                i2 = 0;
                z |= c0836lv.f58196a3;
                z2 |= c0836lv.f58194a1;
                z3 |= c0836lv.f58197a4;
                z4 |= c0836lv.f58193a0 != NetworkType.f45516a0;
                if (z && z2 && z3 && z4) {
                    break;
                }
            }
            int i6 = ConstraintProxyUpdateReceiver.f45572a0;
            Intent intent2 = new Intent("androidx.work.impl.background.systemalarm.UpdateProxies");
            intent2.setComponent(new ComponentName(context, (Class<?>) ConstraintProxyUpdateReceiver.class));
            intent2.putExtra("KEY_BATTERY_NOT_LOW_PROXY_ENABLED", z).putExtra("KEY_BATTERY_CHARGING_PROXY_ENABLED", z2).putExtra("KEY_STORAGE_NOT_LOW_PROXY_ENABLED", z3).putExtra("KEY_NETWORK_STATE_PROXY_ENABLED", z4);
            context.sendBroadcast(intent2);
            zg1Var.m215415b1(arrayListM215182a5);
            ArrayList arrayList = new ArrayList(arrayListM215182a5.size());
            long jCurrentTimeMillis = System.currentTimeMillis();
            int size2 = arrayListM215182a5.size();
            int i7 = i2;
            while (i7 < size2) {
                Object obj2 = arrayListM215182a5.get(i7);
                i7++;
                wg1 wg1Var = (wg1) obj2;
                String str = wg1Var.f60912a0;
                if (jCurrentTimeMillis >= wg1Var.m215066a0() && (!wg1Var.m215067a2() || zg1Var.m215406a2(str))) {
                    arrayList.add(wg1Var);
                }
            }
            int size3 = arrayList.size();
            int i8 = i2;
            while (i8 < size3) {
                Object obj3 = arrayList.get(i8);
                i8++;
                wg1 wg1Var2 = (wg1) obj3;
                String str2 = wg1Var2.f60912a0;
                jg1 jg1VarM212483b3 = cq0.m212483b3(wg1Var2);
                Intent intent3 = new Intent(context, (Class<?>) SystemAlarmService.class);
                intent3.setAction("ACTION_DELAY_MET");
                m213336a2(intent3, jg1VarM212483b3);
                C1351vv.m214963a5().getClass();
                ((mg1) q31Var.f59376a1.f59231a3).execute(new RunnableC0707j6(c0840lx.f58203a0, i3, q31Var, intent3));
            }
            zg1Var.m215416b2();
            return;
        }
        boolean z5 = false;
        if ("ACTION_RESCHEDULE".equals(action)) {
            C1351vv c1351vvM214963a52 = C1351vv.m214963a5();
            Objects.toString(intent);
            c1351vvM214963a52.getClass();
            q31Var.f59379a4.m210477g3();
            return;
        }
        Bundle extras = intent.getExtras();
        String[] strArr = {"KEY_WORKSPEC_ID"};
        if (extras == null || extras.isEmpty() || extras.get(strArr[0]) == null) {
            C1351vv.m214963a5().getClass();
            return;
        }
        if ("ACTION_SCHEDULE_WORK".equals(action)) {
            Context context2 = this.f57354a0;
            jg1 jg1VarM213335a1 = m213335a1(intent);
            C1351vv c1351vvM214963a53 = C1351vv.m214963a5();
            jg1VarM213335a1.toString();
            c1351vvM214963a53.getClass();
            WorkDatabase workDatabase = q31Var.f59379a4.f45559a6;
            workDatabase.m212858a2();
            try {
                wg1 wg1VarM215185a8 = workDatabase.mo210465b9().m215185a8(jg1VarM213335a1.f57334a0);
                if (wg1VarM215185a8 == null) {
                    C1351vv c1351vvM214963a54 = C1351vv.m214963a5();
                    jg1VarM213335a1.toString();
                    c1351vvM214963a54.getClass();
                    return;
                }
                if (wg1VarM215185a8.f60913a1.m210457a0()) {
                    C1351vv c1351vvM214963a55 = C1351vv.m214963a5();
                    jg1VarM213335a1.toString();
                    c1351vvM214963a55.getClass();
                    return;
                }
                long jM215066a0 = wg1VarM215185a8.m215066a0();
                if (wg1VarM215185a8.m215067a2()) {
                    C1351vv c1351vvM214963a56 = C1351vv.m214963a5();
                    jg1VarM213335a1.toString();
                    c1351vvM214963a56.getClass();
                    AbstractC1100q7.m214362a1(context2, workDatabase, jg1VarM213335a1, jM215066a0);
                    Intent intent4 = new Intent(context2, (Class<?>) SystemAlarmService.class);
                    intent4.setAction("ACTION_CONSTRAINTS_CHANGED");
                    ((mg1) q31Var.f59376a1.f59231a3).execute(new RunnableC0707j6(i, i3, q31Var, intent4));
                } else {
                    C1351vv c1351vvM214963a57 = C1351vv.m214963a5();
                    jg1VarM213335a1.toString();
                    c1351vvM214963a57.getClass();
                    AbstractC1100q7.m214362a1(context2, workDatabase, jg1VarM213335a1, jM215066a0);
                }
                workDatabase.m212863b2();
                return;
            } finally {
                workDatabase.m212860a9();
            }
        }
        if ("ACTION_DELAY_MET".equals(action)) {
            synchronized (this.f57356a2) {
                try {
                    jg1 jg1VarM213335a12 = m213335a1(intent);
                    C1351vv c1351vvM214963a58 = C1351vv.m214963a5();
                    jg1VarM213335a12.toString();
                    c1351vvM214963a58.getClass();
                    if (this.f57355a1.containsKey(jg1VarM213335a12)) {
                        C1351vv c1351vvM214963a59 = C1351vv.m214963a5();
                        jg1VarM213335a12.toString();
                        c1351vvM214963a59.getClass();
                    } else {
                        C1193ru c1193ru = new C1193ru(this.f57354a0, i, q31Var, this.f57357a3.m214214b6(jg1VarM213335a12));
                        this.f57355a1.put(jg1VarM213335a12, c1193ru);
                        c1193ru.m214548a4();
                    }
                } finally {
                }
            }
            return;
        }
        if (!"ACTION_STOP_WORK".equals(action)) {
            if (!"ACTION_EXECUTION_COMPLETED".equals(action)) {
                C1351vv c1351vvM214963a510 = C1351vv.m214963a5();
                intent.toString();
                c1351vvM214963a510.getClass();
                return;
            } else {
                jg1 jg1VarM213335a13 = m213335a1(intent);
                boolean z6 = intent.getExtras().getBoolean("KEY_NEEDS_RESCHEDULE");
                C1351vv c1351vvM214963a511 = C1351vv.m214963a5();
                intent.toString();
                c1351vvM214963a511.getClass();
                mo210482a4(jg1VarM213335a13, z6);
                return;
            }
        }
        og1 og1Var = this.f57357a3;
        Bundle extras2 = intent.getExtras();
        String string = extras2.getString("KEY_WORKSPEC_ID");
        if (extras2.containsKey("KEY_WORKSPEC_GENERATION")) {
            int i9 = extras2.getInt("KEY_WORKSPEC_GENERATION");
            ArrayList arrayList2 = new ArrayList(1);
            x11 x11VarM214210b2 = og1Var.m214210b2(new jg1(string, i9));
            listM214211b3 = arrayList2;
            if (x11VarM214210b2 != null) {
                arrayList2.add(x11VarM214210b2);
                listM214211b3 = arrayList2;
            }
        } else {
            listM214211b3 = og1Var.m214211b3(string);
        }
        for (x11 x11Var : listM214211b3) {
            C1351vv.m214963a5().getClass();
            C0096a0 c0096a0 = q31Var.f59379a4;
            c0096a0.f45560a7.m214272b6(new f21(c0096a0, x11Var, z5));
            Context context3 = this.f57354a0;
            WorkDatabase workDatabase2 = q31Var.f59379a4.f45559a6;
            jg1 jg1Var = x11Var.f60991a0;
            int i10 = AbstractC1100q7.f59419a0;
            x31 x31VarMo210462b6 = workDatabase2.mo210462b6();
            v31 v31VarM215110a2 = x31VarMo210462b6.m215110a2(jg1Var);
            if (v31VarM215110a2 != null) {
                AbstractC1100q7.m214361a0(context3, jg1Var, v31VarM215110a2.f60573a2);
                C1351vv c1351vvM214963a512 = C1351vv.m214963a5();
                jg1Var.toString();
                c1351vvM214963a512.getClass();
                String str3 = jg1Var.f57334a0;
                int i11 = jg1Var.f57335a1;
                WorkDatabase_Impl workDatabase_Impl = (WorkDatabase_Impl) x31VarMo210462b6.f61012a0;
                workDatabase_Impl.m212857a1();
                w31 w31Var = (w31) x31VarMo210462b6.f61014a2;
                u00 u00VarM210428a0 = w31Var.m210428a0();
                if (str3 == null) {
                    u00VarM210428a0.mo213343a9(1);
                } else {
                    u00VarM210428a0.mo213341a6(1, str3);
                }
                u00VarM210428a0.mo213346b6(2, i11);
                workDatabase_Impl.m212858a2();
                try {
                    u00VarM210428a0.m214812a0();
                    workDatabase_Impl.m212863b2();
                } finally {
                    workDatabase_Impl.m212860a9();
                    w31Var.m210431a3(u00VarM210428a0);
                }
            }
            q31Var.mo210482a4(x11Var.f60991a0, false);
            z5 = false;
        }
    }

    @Override // p000.InterfaceC1425xp
    /* renamed from: a4 */
    public final void mo210482a4(jg1 jg1Var, boolean z) {
        synchronized (this.f57356a2) {
            try {
                C1193ru c1193ru = (C1193ru) this.f57355a1.remove(jg1Var);
                this.f57357a3.m214210b2(jg1Var);
                if (c1193ru != null) {
                    c1193ru.m214549a5(z);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
