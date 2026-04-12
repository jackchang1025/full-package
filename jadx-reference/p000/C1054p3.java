package p000;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: p3 */
/* loaded from: classes.dex */
public final class C1054p3 implements Application.ActivityLifecycleCallbacks {

    /* renamed from: a0 */
    public Object f59139a0;

    /* renamed from: a1 */
    public Activity f59140a1;

    /* renamed from: a2 */
    public final int f59141a2;

    /* renamed from: a3 */
    public boolean f59142a3 = false;

    /* renamed from: a4 */
    public boolean f59143a4 = false;

    /* renamed from: a5 */
    public boolean f59144a5 = false;

    public C1054p3(Activity activity) {
        this.f59140a1 = activity;
        this.f59141a2 = activity.hashCode();
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityDestroyed(Activity activity) {
        if (this.f59140a1 == activity) {
            this.f59140a1 = null;
            this.f59143a4 = true;
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityPaused(Activity activity) {
        if (!this.f59143a4 || this.f59144a5 || this.f59142a3) {
            return;
        }
        Object obj = this.f59139a0;
        try {
            Object obj2 = AbstractC1055p4.f59149a2.get(activity);
            if (obj2 == obj && activity.hashCode() == this.f59141a2) {
                AbstractC1055p4.f59153a6.postAtFrontOfQueue(new RunnableC0884n2(AbstractC1055p4.f59148a1.get(activity), obj2, 3, false));
                this.f59144a5 = true;
                this.f59139a0 = null;
            }
        } catch (Throwable unused) {
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStarted(Activity activity) {
        if (this.f59140a1 == activity) {
            this.f59142a3 = true;
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityResumed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStopped(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }
}
