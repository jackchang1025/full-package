package p000;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.lifecycle.Lifecycle$Event;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class mo0 extends AbstractC1392ww {
    final /* synthetic */ no0 this$0;

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: mo0$a0 */
    public static final class C0869a0 extends AbstractC1392ww {
        final /* synthetic */ no0 this$0;

        public C0869a0(no0 no0Var) {
            this.this$0 = no0Var;
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPostResumed(Activity activity) {
            t60.m214695b6(activity, "activity");
            this.this$0.m214134a1();
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPostStarted(Activity activity) {
            t60.m214695b6(activity, "activity");
            no0 no0Var = this.this$0;
            int i = no0Var.f58678a0 + 1;
            no0Var.f58678a0 = i;
            if (i == 1 && no0Var.f58681a3) {
                no0Var.f58683a5.m210234g1(Lifecycle$Event.ON_START);
                no0Var.f58681a3 = false;
            }
        }
    }

    public mo0(no0 no0Var) {
        this.this$0 = no0Var;
    }

    @Override // p000.AbstractC1392ww, android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
        t60.m214695b6(activity, "activity");
        if (Build.VERSION.SDK_INT < 29) {
            lr0.f58161a1.get(activity).f58162a0 = this.this$0.f58685a7;
        }
    }

    @Override // p000.AbstractC1392ww, android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
        t60.m214695b6(activity, "activity");
        no0 no0Var = this.this$0;
        int i = no0Var.f58679a1 - 1;
        no0Var.f58679a1 = i;
        if (i == 0) {
            Handler handler = no0Var.f58682a4;
            t60.m214692b3(handler);
            handler.postDelayed(no0Var.f58684a6, 700L);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPreCreated(Activity activity, Bundle bundle) {
        t60.m214695b6(activity, "activity");
        ko0.m213606a0(activity, new C0869a0(this.this$0));
    }

    @Override // p000.AbstractC1392ww, android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
        t60.m214695b6(activity, "activity");
        no0 no0Var = this.this$0;
        int i = no0Var.f58678a0 - 1;
        no0Var.f58678a0 = i;
        if (i == 0 && no0Var.f58680a2) {
            no0Var.f58683a5.m210234g1(Lifecycle$Event.ON_STOP);
            no0Var.f58681a3 = true;
        }
    }
}
