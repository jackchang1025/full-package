package p000;

import android.content.Context;
import java.util.LinkedHashSet;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ln */
/* loaded from: classes2.dex */
public abstract class AbstractC0826ln {

    /* renamed from: a0 */
    public final pg1 f58053a0;

    /* renamed from: a1 */
    public final Context f58054a1;

    /* renamed from: a2 */
    public final Object f58055a2;

    /* renamed from: a3 */
    public final LinkedHashSet f58056a3;

    /* renamed from: a4 */
    public Object f58057a4;

    public AbstractC0826ln(Context context, pg1 pg1Var) {
        this.f58053a0 = pg1Var;
        Context applicationContext = context.getApplicationContext();
        t60.m214694b5(applicationContext, "context.applicationContext");
        this.f58054a1 = applicationContext;
        this.f58055a2 = new Object();
        this.f58056a3 = new LinkedHashSet();
    }

    /* renamed from: a0 */
    public abstract Object mo212612a0();

    /* renamed from: a1 */
    public final void m213873a1(AbstractC0799kx abstractC0799kx) {
        t60.m214695b6(abstractC0799kx, "listener");
        synchronized (this.f58055a2) {
            if (this.f58056a3.remove(abstractC0799kx) && this.f58056a3.isEmpty()) {
                mo212614a4();
            }
        }
    }

    /* renamed from: a2 */
    public final void m213874a2(Object obj) {
        synchronized (this.f58055a2) {
            Object obj2 = this.f58057a4;
            if (obj2 == null || !obj2.equals(obj)) {
                this.f58057a4 = obj;
                ((mg1) this.f58053a0.f59231a3).execute(new RunnableC1052p1(AbstractC0715je.m213303j0(this.f58056a3), 6, this));
            }
        }
    }

    /* renamed from: a3 */
    public abstract void mo212613a3();

    /* renamed from: a4 */
    public abstract void mo212614a4();
}
