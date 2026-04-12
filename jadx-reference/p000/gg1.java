package p000;

import android.content.Context;
import android.os.Build;
import androidx.work.impl.utils.futures.C0100a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class gg1 implements Runnable {

    /* renamed from: a6 */
    public static final /* synthetic */ int f56462a6 = 0;

    /* renamed from: a0 */
    public final C0100a1 f56463a0 = new C0100a1();

    /* renamed from: a1 */
    public final Context f56464a1;

    /* renamed from: a2 */
    public final wg1 f56465a2;

    /* renamed from: a3 */
    public final tb0 f56466a3;

    /* renamed from: a4 */
    public final ig1 f56467a4;

    /* renamed from: a5 */
    public final pg1 f56468a5;

    static {
        C1351vv.m214966b1("WorkForegroundRunnable");
    }

    public gg1(Context context, wg1 wg1Var, tb0 tb0Var, ig1 ig1Var, pg1 pg1Var) {
        this.f56464a1 = context;
        this.f56465a2 = wg1Var;
        this.f56466a3 = tb0Var;
        this.f56467a4 = ig1Var;
        this.f56468a5 = pg1Var;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (!this.f56465a2.f60928b6 || Build.VERSION.SDK_INT >= 31) {
            this.f56463a0.m210484a8(null);
            return;
        }
        C0100a1 c0100a1 = new C0100a1();
        pg1 pg1Var = this.f56468a5;
        ((mg1) pg1Var.f59231a3).execute(new RunnableC1052p1(this, 15, c0100a1));
        c0100a1.mo210459a0(new RunnableC0884n2(this, 15, c0100a1), (mg1) pg1Var.f59231a3);
    }
}
