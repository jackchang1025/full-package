package p000;

import androidx.work.OverwritingInputMerger;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class fl0 extends id0 {

    /* renamed from: a4 */
    public final /* synthetic */ int f56284a4 = 0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public fl0(Class cls) {
        super(cls);
        t60.m214695b6(cls, "workerClass");
        ((wg1) this.f56867a2).f60915a3 = OverwritingInputMerger.class.getName();
    }

    @Override // p000.id0
    /* renamed from: a1 */
    public final tg1 mo212832a1() {
        switch (this.f56284a4) {
            case 0:
                if (this.f56865a0 && ((wg1) this.f56867a2).f60921a9.f58195a2) {
                    throw new IllegalArgumentException("Cannot set backoff criteria on an idle mode job");
                }
                return new hl0((UUID) this.f56866a1, (wg1) this.f56867a2, (Set) this.f56868a3);
            default:
                if (this.f56865a0 && ((wg1) this.f56867a2).f60921a9.f58195a2) {
                    throw new IllegalArgumentException("Cannot set backoff criteria on an idle mode job");
                }
                if (((wg1) this.f56867a2).f60928b6) {
                    throw new IllegalArgumentException("PeriodicWorkRequests cannot be expedited");
                }
                return new zm0((UUID) this.f56866a1, (wg1) this.f56867a2, (Set) this.f56868a3);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public fl0(Class cls, long j) {
        super(cls);
        TimeUnit timeUnit = TimeUnit.MINUTES;
        t60.m214695b6(timeUnit, "repeatIntervalTimeUnit");
        wg1 wg1Var = (wg1) this.f56867a2;
        long millis = timeUnit.toMillis(j);
        wg1Var.getClass();
        if (millis < 900000) {
            C1351vv.m214963a5().getClass();
        }
        long j2 = millis < 900000 ? 900000L : millis;
        long j3 = millis < 900000 ? 900000L : millis;
        if (j2 < 900000) {
            C1351vv.m214963a5().getClass();
        }
        wg1Var.f60919a7 = j2 >= 900000 ? j2 : 900000L;
        if (j3 < 300000) {
            C1351vv.m214963a5().getClass();
        }
        if (j3 > wg1Var.f60919a7) {
            C1351vv.m214963a5().getClass();
        }
        wg1Var.f60920a8 = AbstractC1117qo.m214414b0(j3, 300000L, wg1Var.f60919a7);
    }
}
