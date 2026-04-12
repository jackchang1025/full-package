package p000;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import androidx.lifecycle.Lifecycle$Event;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class lo0 {
    public /* synthetic */ lo0(AbstractC1120qr abstractC1120qr) {
        this();
    }

    public final ka0 get() {
        return no0.f58677a9;
    }

    public final void init$lifecycle_process_release(Context context) {
        t60.m214695b6(context, "context");
        no0 no0Var = no0.f58677a9;
        no0Var.getClass();
        no0Var.f58682a4 = new Handler();
        no0Var.f58683a5.m210234g1(Lifecycle$Event.ON_CREATE);
        Context applicationContext = context.getApplicationContext();
        t60.m214693b4(applicationContext, "null cannot be cast to non-null type android.app.Application");
        ((Application) applicationContext).registerActivityLifecycleCallbacks(new mo0(no0Var));
    }

    private lo0() {
    }

    public static /* synthetic */ void getTIMEOUT_MS$lifecycle_process_release$annotations() {
    }
}
