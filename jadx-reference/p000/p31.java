package p000;

import android.content.Intent;
import android.os.PowerManager;
import androidx.work.impl.background.systemalarm.SystemAlarmService;
import java.util.Objects;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class p31 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f59145a0;

    /* renamed from: a1 */
    public final q31 f59146a1;

    public /* synthetic */ p31(q31 q31Var, int i) {
        this.f59145a0 = i;
        this.f59146a1 = q31Var;
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x007d A[Catch: all -> 0x0039, TryCatch #1 {all -> 0x0039, blocks: (B:6:0x0015, B:8:0x0019, B:10:0x0035, B:13:0x003b, B:14:0x0042, B:15:0x0043, B:16:0x004d, B:20:0x0057, B:22:0x005f, B:23:0x0061, B:27:0x006b, B:29:0x0076, B:37:0x0088, B:33:0x007c, B:34:0x007d, B:36:0x0085, B:41:0x008c, B:24:0x0062, B:25:0x0068, B:17:0x004e, B:18:0x0054), top: B:65:0x0015, inners: #2, #3 }] */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        mg1 mg1Var;
        p31 p31Var;
        boolean zIsEmpty;
        boolean zIsEmpty2;
        switch (this.f59145a0) {
            case 0:
                synchronized (this.f59146a1.f59381a6) {
                    q31 q31Var = this.f59146a1;
                    q31Var.f59382a7 = (Intent) q31Var.f59381a6.get(0);
                }
                Intent intent = this.f59146a1.f59382a7;
                if (intent != null) {
                    String action = intent.getAction();
                    int intExtra = this.f59146a1.f59382a7.getIntExtra("KEY_START_ID", 0);
                    C1351vv c1351vvM214963a5 = C1351vv.m214963a5();
                    int i = q31.f59374a9;
                    Objects.toString(this.f59146a1.f59382a7);
                    c1351vvM214963a5.getClass();
                    PowerManager.WakeLock wakeLockM213032a0 = he1.m213032a0(this.f59146a1.f59375a0, action + " (" + intExtra + ")");
                    int i2 = 1;
                    try {
                        try {
                            C1351vv c1351vvM214963a52 = C1351vv.m214963a5();
                            wakeLockM213032a0.toString();
                            c1351vvM214963a52.getClass();
                            wakeLockM213032a0.acquire();
                            q31 q31Var2 = this.f59146a1;
                            q31Var2.f59380a5.m213337a0(q31Var2.f59382a7, intExtra, q31Var2);
                            C1351vv c1351vvM214963a53 = C1351vv.m214963a5();
                            wakeLockM213032a0.toString();
                            c1351vvM214963a53.getClass();
                            wakeLockM213032a0.release();
                            q31 q31Var3 = this.f59146a1;
                            mg1Var = (mg1) q31Var3.f59376a1.f59231a3;
                            p31Var = new p31(q31Var3, i2);
                        } catch (Throwable unused) {
                            C1351vv c1351vvM214963a54 = C1351vv.m214963a5();
                            int i3 = q31.f59374a9;
                            c1351vvM214963a54.getClass();
                            C1351vv c1351vvM214963a55 = C1351vv.m214963a5();
                            wakeLockM213032a0.toString();
                            c1351vvM214963a55.getClass();
                            wakeLockM213032a0.release();
                            q31 q31Var4 = this.f59146a1;
                            mg1Var = (mg1) q31Var4.f59376a1.f59231a3;
                            p31Var = new p31(q31Var4, i2);
                        }
                        mg1Var.execute(p31Var);
                        return;
                    } catch (Throwable th) {
                        C1351vv c1351vvM214963a56 = C1351vv.m214963a5();
                        int i4 = q31.f59374a9;
                        wakeLockM213032a0.toString();
                        c1351vvM214963a56.getClass();
                        wakeLockM213032a0.release();
                        q31 q31Var5 = this.f59146a1;
                        ((mg1) q31Var5.f59376a1.f59231a3).execute(new p31(q31Var5, i2));
                        throw th;
                    }
                }
                return;
            default:
                q31 q31Var6 = this.f59146a1;
                C1351vv.m214963a5().getClass();
                q31.m214348a1();
                synchronized (q31Var6.f59381a6) {
                    try {
                        if (q31Var6.f59382a7 != null) {
                            C1351vv c1351vvM214963a57 = C1351vv.m214963a5();
                            Objects.toString(q31Var6.f59382a7);
                            c1351vvM214963a57.getClass();
                            if (!((Intent) q31Var6.f59381a6.remove(0)).equals(q31Var6.f59382a7)) {
                                throw new IllegalStateException("Dequeue-d command is not the first.");
                            }
                            q31Var6.f59382a7 = null;
                        }
                        ExecutorC0034an executorC0034an = (ExecutorC0034an) q31Var6.f59376a1.f59229a1;
                        C0727jq c0727jq = q31Var6.f59380a5;
                        synchronized (c0727jq.f57356a2) {
                            zIsEmpty = c0727jq.f57355a1.isEmpty();
                        }
                        if (zIsEmpty && q31Var6.f59381a6.isEmpty()) {
                            synchronized (executorC0034an.f43727a3) {
                                zIsEmpty2 = executorC0034an.f43725a1.isEmpty();
                            }
                            if (zIsEmpty2) {
                                C1351vv.m214963a5().getClass();
                                SystemAlarmService systemAlarmService = q31Var6.f59383a8;
                                if (systemAlarmService != null) {
                                    systemAlarmService.m210480a1();
                                }
                            }
                        } else if (!q31Var6.f59381a6.isEmpty()) {
                            q31Var6.m214350a2();
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }
                return;
        }
    }
}
