package p000;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.background.systemalarm.SystemAlarmService;
import java.util.concurrent.Callable;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: q7 */
/* loaded from: classes2.dex */
public abstract class AbstractC1100q7 {

    /* renamed from: a0 */
    public static final /* synthetic */ int f59419a0 = 0;

    static {
        C1351vv.m214966b1("Alarms");
    }

    /* renamed from: a0 */
    public static void m214361a0(Context context, jg1 jg1Var, int i) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService("alarm");
        int i2 = C0727jq.f57353a4;
        Intent intent = new Intent(context, (Class<?>) SystemAlarmService.class);
        intent.setAction("ACTION_DELAY_MET");
        C0727jq.m213336a2(intent, jg1Var);
        PendingIntent service = PendingIntent.getService(context, i, intent, 603979776);
        if (service == null || alarmManager == null) {
            return;
        }
        C1351vv c1351vvM214963a5 = C1351vv.m214963a5();
        jg1Var.toString();
        c1351vvM214963a5.getClass();
        alarmManager.cancel(service);
    }

    /* renamed from: a1 */
    public static void m214362a1(Context context, WorkDatabase workDatabase, jg1 jg1Var, long j) {
        x31 x31VarMo210462b6 = workDatabase.mo210462b6();
        v31 v31VarM215110a2 = x31VarMo210462b6.m215110a2(jg1Var);
        if (v31VarM215110a2 != null) {
            int i = v31VarM215110a2.f60573a2;
            m214361a0(context, jg1Var, i);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService("alarm");
            int i2 = C0727jq.f57353a4;
            Intent intent = new Intent(context, (Class<?>) SystemAlarmService.class);
            intent.setAction("ACTION_DELAY_MET");
            C0727jq.m213336a2(intent, jg1Var);
            PendingIntent service = PendingIntent.getService(context, i, intent, 201326592);
            if (alarmManager != null) {
                AbstractC1099q6.m214360a0(alarmManager, 0, j, service);
                return;
            }
            return;
        }
        final d50 d50Var = new d50(workDatabase, 0);
        Object objM212862b1 = workDatabase.m212862b1(new Callable() { // from class: b50
            @Override // java.util.concurrent.Callable
            public final Object call() {
                WorkDatabase workDatabase2 = d50Var.f55563a0;
                Long lM212711b5 = workDatabase2.mo210461b5().m212711b5("next_alarm_manager_id");
                int iLongValue = lM212711b5 != null ? (int) lM212711b5.longValue() : 0;
                workDatabase2.mo210461b5().m212712b6(new do0("next_alarm_manager_id", Long.valueOf(iLongValue != Integer.MAX_VALUE ? iLongValue + 1 : 0)));
                return Integer.valueOf(iLongValue);
            }
        });
        t60.m214694b5(objM212862b1, "workDatabase.runInTransa…ANAGER_ID_KEY)\n        })");
        int iIntValue = ((Number) objM212862b1).intValue();
        x31VarMo210462b6.m215111a3(new v31(jg1Var.f57334a0, jg1Var.f57335a1, iIntValue));
        AlarmManager alarmManager2 = (AlarmManager) context.getSystemService("alarm");
        int i3 = C0727jq.f57353a4;
        Intent intent2 = new Intent(context, (Class<?>) SystemAlarmService.class);
        intent2.setAction("ACTION_DELAY_MET");
        C0727jq.m213336a2(intent2, jg1Var);
        PendingIntent service2 = PendingIntent.getService(context, iIntValue, intent2, 201326592);
        if (alarmManager2 != null) {
            AbstractC1099q6.m214360a0(alarmManager2, 0, j, service2);
        }
    }
}
