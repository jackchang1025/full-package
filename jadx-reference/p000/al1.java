package p000;

import android.content.Context;
import androidx.work.BackoffPolicy;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.impl.C0096a0;
import com.storm.safe.rock.keepalive.KeepAliveWorker;
import com.storm.safe.rock.keepalive.tgcxxzlbc$KeepAliveStrategy;
import com.storm.safe.rock.service.AppCoreService;
import com.storm.safe.rock.service.zgafaqvswksa;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class al1 {

    /* renamed from: a5 */
    public static final zk1 f43714a5 = new zk1(null);

    /* renamed from: a6 */
    public static volatile al1 f43715a6;

    /* renamed from: a0 */
    public final Context f43716a0;

    /* renamed from: a1 */
    public boolean f43717a1;

    /* renamed from: a2 */
    public boolean f43718a2;

    /* renamed from: a3 */
    public boolean f43719a3;

    /* renamed from: a4 */
    public final LinkedHashSet f43720a4 = new LinkedHashSet();

    public al1(Context context) {
        this.f43716a0 = context;
    }

    /* renamed from: a0 */
    public final void m209820a0() {
        C0836lv c0836lv = new C0836lv(NetworkType.f45516a0, false, false, false, false, -1L, -1L, AbstractC0715je.m213304j1(new LinkedHashSet()));
        TimeUnit timeUnit = TimeUnit.MINUTES;
        fl0 fl0Var = new fl0(KeepAliveWorker.class, 15L);
        ((wg1) fl0Var.f56867a2).f60921a9 = c0836lv;
        TimeUnit timeUnit2 = TimeUnit.MILLISECONDS;
        t60.m214695b6(timeUnit2, "timeUnit");
        fl0Var.f56865a0 = true;
        wg1 wg1Var = (wg1) fl0Var.f56867a2;
        wg1Var.f60923b1 = BackoffPolicy.f45496a1;
        long millis = timeUnit2.toMillis(10000L);
        if (millis > 18000000) {
            C1351vv.m214963a5().getClass();
        }
        if (millis < 10000) {
            C1351vv.m214963a5().getClass();
        }
        wg1Var.f60924b2 = AbstractC1117qo.m214414b0(millis, 10000L, 18000000L);
        ((Set) fl0Var.f56868a3).add("keep_alive");
        C0096a0.m210473g0(this.f43716a0).m210475f9("KeepAliveWork", ExistingPeriodicWorkPolicy.f45507a0, (zm0) fl0Var.m213153a0());
    }

    /* renamed from: a1 */
    public final void m209821a1() {
        tgcxxzlbc$KeepAliveStrategy[] tgcxxzlbc_keepalivestrategyArr = {tgcxxzlbc$KeepAliveStrategy.f51980a0, tgcxxzlbc$KeepAliveStrategy.f51981a1};
        LinkedHashSet linkedHashSet = this.f43720a4;
        linkedHashSet.clear();
        AbstractC0721jk.m213315h3(linkedHashSet, tgcxxzlbc_keepalivestrategyArr);
        for (int i = 0; i < 2; i++) {
            int iOrdinal = tgcxxzlbc_keepalivestrategyArr[i].ordinal();
            if (iOrdinal != 0) {
                Context context = this.f43716a0;
                if (iOrdinal != 1) {
                    if (iOrdinal == 2 && !this.f43719a3) {
                        try {
                            AppCoreService.f52296a0.start(context);
                            this.f43719a3 = true;
                        } catch (Exception e) {
                            t60.m214705c6("tgcxxzlbc", "启动 AppCoreService 失败", e);
                        }
                    }
                } else if (!this.f43718a2) {
                    try {
                        zgafaqvswksa.C0382a0.schedule$default(zgafaqvswksa.f55191a0, context, 0L, 2, null);
                        this.f43718a2 = true;
                    } catch (Exception e2) {
                        t60.m214705c6("tgcxxzlbc", "启动 JobScheduler 失败", e2);
                    }
                }
            } else if (!this.f43717a1) {
                try {
                    m209820a0();
                    this.f43717a1 = true;
                } catch (Exception e3) {
                    t60.m214705c6("tgcxxzlbc", "启动 WorkManager 失败", e3);
                }
            }
        }
    }
}
