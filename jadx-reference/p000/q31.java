package p000;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.os.PowerManager;
import android.text.TextUtils;
import androidx.work.impl.C0096a0;
import androidx.work.impl.background.systemalarm.SystemAlarmService;
import java.util.ArrayList;
import java.util.Objects;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class q31 implements InterfaceC1425xp {

    /* renamed from: a9 */
    public static final /* synthetic */ int f59374a9 = 0;

    /* renamed from: a0 */
    public final Context f59375a0;

    /* renamed from: a1 */
    public final pg1 f59376a1;

    /* renamed from: a2 */
    public final ch1 f59377a2;

    /* renamed from: a3 */
    public final so0 f59378a3;

    /* renamed from: a4 */
    public final C0096a0 f59379a4;

    /* renamed from: a5 */
    public final C0727jq f59380a5;

    /* renamed from: a6 */
    public final ArrayList f59381a6;

    /* renamed from: a7 */
    public Intent f59382a7;

    /* renamed from: a8 */
    public SystemAlarmService f59383a8;

    static {
        C1351vv.m214966b1("SystemAlarmDispatcher");
    }

    public q31(SystemAlarmService systemAlarmService) {
        Context applicationContext = systemAlarmService.getApplicationContext();
        this.f59375a0 = applicationContext;
        this.f59380a5 = new C0727jq(applicationContext, new og1());
        C0096a0 c0096a0M210473g0 = C0096a0.m210473g0(systemAlarmService);
        this.f59379a4 = c0096a0M210473g0;
        this.f59377a2 = new ch1((tg0) c0096a0M210473g0.f45558a5.f57713a6);
        so0 so0Var = c0096a0M210473g0.f45562a9;
        this.f59378a3 = so0Var;
        this.f59376a1 = c0096a0M210473g0.f45560a7;
        so0Var.m214651a0(this);
        this.f59381a6 = new ArrayList();
        this.f59382a7 = null;
    }

    /* renamed from: a1 */
    public static void m214348a1() {
        if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
            throw new IllegalStateException("Needs to be invoked on the main thread.");
        }
    }

    /* renamed from: a0 */
    public final void m214349a0(Intent intent, int i) {
        C1351vv c1351vvM214963a5 = C1351vv.m214963a5();
        Objects.toString(intent);
        c1351vvM214963a5.getClass();
        m214348a1();
        String action = intent.getAction();
        if (TextUtils.isEmpty(action)) {
            C1351vv.m214963a5().getClass();
            return;
        }
        if ("ACTION_CONSTRAINTS_CHANGED".equals(action)) {
            m214348a1();
            synchronized (this.f59381a6) {
                try {
                    ArrayList arrayList = this.f59381a6;
                    int size = arrayList.size();
                    int i2 = 0;
                    while (i2 < size) {
                        Object obj = arrayList.get(i2);
                        i2++;
                        if ("ACTION_CONSTRAINTS_CHANGED".equals(((Intent) obj).getAction())) {
                            return;
                        }
                    }
                } finally {
                }
            }
        }
        intent.putExtra("KEY_START_ID", i);
        synchronized (this.f59381a6) {
            try {
                boolean zIsEmpty = this.f59381a6.isEmpty();
                this.f59381a6.add(intent);
                if (zIsEmpty) {
                    m214350a2();
                }
            } finally {
            }
        }
    }

    /* renamed from: a2 */
    public final void m214350a2() {
        m214348a1();
        PowerManager.WakeLock wakeLockM213032a0 = he1.m213032a0(this.f59375a0, "ProcessCommand");
        try {
            wakeLockM213032a0.acquire();
            this.f59379a4.f45560a7.m214272b6(new p31(this, 0));
        } finally {
            wakeLockM213032a0.release();
        }
    }

    @Override // p000.InterfaceC1425xp
    /* renamed from: a4 */
    public final void mo210482a4(jg1 jg1Var, boolean z) {
        mg1 mg1Var = (mg1) this.f59376a1.f59231a3;
        int i = C0727jq.f57353a4;
        Intent intent = new Intent(this.f59375a0, (Class<?>) SystemAlarmService.class);
        intent.setAction("ACTION_EXECUTION_COMPLETED");
        intent.putExtra("KEY_NEEDS_RESCHEDULE", z);
        C0727jq.m213336a2(intent, jg1Var);
        mg1Var.execute(new RunnableC0707j6(0, 3, this, intent));
    }
}
