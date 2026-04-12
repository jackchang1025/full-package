package p000;

import android.content.Context;
import android.content.SharedPreferences;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class or0 extends cg0 {

    /* renamed from: a2 */
    public final /* synthetic */ int f58912a2 = 1;

    /* renamed from: a3 */
    public final Context f58913a3;

    public or0(Context context, int i, int i2) {
        super(i, i2);
        this.f58913a3 = context;
    }

    @Override // p000.cg0
    /* renamed from: a0 */
    public final void mo210852a0(d31 d31Var) {
        switch (this.f58912a2) {
            case 0:
                t60.m214695b6(d31Var, "db");
                if (this.f46134a1 >= 10) {
                    d31Var.mo210439b7(new Object[]{"reschedule_needed", 1});
                    return;
                } else {
                    this.f58913a3.getSharedPreferences("androidx.work.util.preferences", 0).edit().putBoolean("reschedule_needed", true).apply();
                    return;
                }
            default:
                t60.m214695b6(d31Var, "db");
                d31Var.mo210435a4("CREATE TABLE IF NOT EXISTS `Preference` (`key` TEXT NOT NULL, `long_value` INTEGER, PRIMARY KEY(`key`))");
                Context context = this.f58913a3;
                SharedPreferences sharedPreferences = context.getSharedPreferences("androidx.work.util.preferences", 0);
                if (sharedPreferences.contains("reschedule_needed") || sharedPreferences.contains("last_cancel_all_time_ms")) {
                    long j = sharedPreferences.getLong("last_cancel_all_time_ms", 0L);
                    long j2 = sharedPreferences.getBoolean("reschedule_needed", false) ? 1L : 0L;
                    d31Var.mo210433a2();
                    try {
                        d31Var.mo210439b7(new Object[]{"last_cancel_all_time_ms", Long.valueOf(j)});
                        d31Var.mo210439b7(new Object[]{"reschedule_needed", Long.valueOf(j2)});
                        sharedPreferences.edit().clear().apply();
                        d31Var.mo210440b8();
                    } finally {
                    }
                }
                SharedPreferences sharedPreferences2 = context.getSharedPreferences("androidx.work.util.id", 0);
                if (sharedPreferences2.contains("next_job_scheduler_id") || sharedPreferences2.contains("next_job_scheduler_id")) {
                    int i = sharedPreferences2.getInt("next_job_scheduler_id", 0);
                    int i2 = sharedPreferences2.getInt("next_alarm_manager_id", 0);
                    d31Var.mo210433a2();
                    try {
                        d31Var.mo210439b7(new Object[]{"next_job_scheduler_id", Integer.valueOf(i)});
                        d31Var.mo210439b7(new Object[]{"next_alarm_manager_id", Integer.valueOf(i2)});
                        sharedPreferences2.edit().clear().apply();
                        d31Var.mo210440b8();
                        return;
                    } finally {
                    }
                }
                return;
        }
    }

    public or0(Context context) {
        super(9, 10);
        this.f58913a3 = context;
    }
}
