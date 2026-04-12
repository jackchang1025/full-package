package p000;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import androidx.fragment.app.AbstractComponentCallbacksC0069a5;
import androidx.lifecycle.C0076a0;
import androidx.lifecycle.Lifecycle$Event;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class n00 implements x30, yt0, sb1 {

    /* renamed from: a0 */
    public final AbstractComponentCallbacksC0069a5 f58413a0;

    /* renamed from: a1 */
    public final rb1 f58414a1;

    /* renamed from: a2 */
    public nb1 f58415a2;

    /* renamed from: a3 */
    public C0076a0 f58416a3 = null;

    /* renamed from: a4 */
    public xt0 f58417a4 = null;

    public n00(AbstractComponentCallbacksC0069a5 abstractComponentCallbacksC0069a5, rb1 rb1Var) {
        this.f58413a0 = abstractComponentCallbacksC0069a5;
        this.f58414a1 = rb1Var;
    }

    @Override // p000.yt0
    /* renamed from: a0 */
    public final vt0 mo209826a0() {
        m214027a6();
        return this.f58417a4.f61178a1;
    }

    /* renamed from: a1 */
    public final void m214026a1(Lifecycle$Event lifecycle$Event) {
        this.f58416a3.m210234g1(lifecycle$Event);
    }

    @Override // p000.x30
    /* renamed from: a2 */
    public final nb1 mo209827a2() {
        Application application;
        AbstractComponentCallbacksC0069a5 abstractComponentCallbacksC0069a5 = this.f58413a0;
        nb1 nb1VarMo209827a2 = abstractComponentCallbacksC0069a5.mo209827a2();
        if (!nb1VarMo209827a2.equals(abstractComponentCallbacksC0069a5.f45117e0)) {
            this.f58415a2 = nb1VarMo209827a2;
            return nb1VarMo209827a2;
        }
        if (this.f58415a2 == null) {
            Context applicationContext = abstractComponentCallbacksC0069a5.m210152c5().getApplicationContext();
            while (true) {
                if (!(applicationContext instanceof ContextWrapper)) {
                    application = null;
                    break;
                }
                if (applicationContext instanceof Application) {
                    application = (Application) applicationContext;
                    break;
                }
                applicationContext = ((ContextWrapper) applicationContext).getBaseContext();
            }
            this.f58415a2 = new zt0(application, this, abstractComponentCallbacksC0069a5.f45082a5);
        }
        return this.f58415a2;
    }

    @Override // p000.sb1
    /* renamed from: a4 */
    public final rb1 mo209829a4() {
        m214027a6();
        return this.f58414a1;
    }

    @Override // p000.ka0
    /* renamed from: a5 */
    public final C0076a0 mo209830a5() {
        m214027a6();
        return this.f58416a3;
    }

    /* renamed from: a6 */
    public final void m214027a6() {
        if (this.f58416a3 == null) {
            this.f58416a3 = new C0076a0(this, true);
            this.f58417a4 = xt0.f61176a3.create(this);
        }
    }
}
