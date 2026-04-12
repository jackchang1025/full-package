package p000;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Handler;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: g */
/* loaded from: classes.dex */
public final class C0516g implements InterfaceC1374wf {

    /* renamed from: a0 */
    public final Context f56344a0;

    /* renamed from: a1 */
    public final C1094q2 f56345a1;

    /* renamed from: a2 */
    public final C1351vv f56346a2;

    /* renamed from: a3 */
    public final Object f56347a3 = new Object();

    /* renamed from: a4 */
    public Handler f56348a4;

    /* renamed from: a5 */
    public ThreadPoolExecutor f56349a5;

    /* renamed from: a6 */
    public ThreadPoolExecutor f56350a6;

    /* renamed from: a7 */
    public cq0 f56351a7;

    public C0516g(Context context, C1094q2 c1094q2) {
        b81.m210568a8(context, "Context cannot be null");
        this.f56344a0 = context.getApplicationContext();
        this.f56345a1 = c1094q2;
        this.f56346a2 = C0563h.f56591a3;
    }

    /* renamed from: a0 */
    public final void m212868a0() {
        synchronized (this.f56347a3) {
            try {
                this.f56351a7 = null;
                Handler handler = this.f56348a4;
                if (handler != null) {
                    handler.removeCallbacks(null);
                }
                this.f56348a4 = null;
                ThreadPoolExecutor threadPoolExecutor = this.f56350a6;
                if (threadPoolExecutor != null) {
                    threadPoolExecutor.shutdown();
                }
                this.f56349a5 = null;
                this.f56350a6 = null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: a1 */
    public final C1162r m212869a1() throws Resources.NotFoundException {
        try {
            C1351vv c1351vv = this.f56346a2;
            Context context = this.f56344a0;
            C1094q2 c1094q2 = this.f56345a1;
            c1351vv.getClass();
            C1166r3 c1166r3M214434d0 = AbstractC1117qo.m214434d0(context, c1094q2);
            int i = c1166r3M214434d0.f59607a0;
            if (i != 0) {
                throw new RuntimeException(AbstractC0003a2.m30b1("fetchFonts failed (", i, ")"));
            }
            C1162r[] c1162rArr = (C1162r[]) c1166r3M214434d0.f59608a1;
            if (c1162rArr == null || c1162rArr.length == 0) {
                throw new RuntimeException("fetchFonts failed (empty result)");
            }
            return c1162rArr[0];
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("provider not found", e);
        }
    }

    @Override // p000.InterfaceC1374wf
    /* renamed from: b4 */
    public final void mo212870b4(cq0 cq0Var) {
        synchronized (this.f56347a3) {
            this.f56351a7 = cq0Var;
        }
        synchronized (this.f56347a3) {
            try {
                if (this.f56351a7 == null) {
                    return;
                }
                if (this.f56349a5 == null) {
                    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 1, 15L, TimeUnit.SECONDS, new LinkedBlockingDeque(), new ThreadFactoryC0756kf("emojiCompat"));
                    threadPoolExecutor.allowCoreThreadTimeOut(true);
                    this.f56350a6 = threadPoolExecutor;
                    this.f56349a5 = threadPoolExecutor;
                }
                this.f56349a5.execute(new RunnableC0941o6(12, this));
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
