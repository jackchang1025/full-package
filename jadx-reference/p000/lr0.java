package p000;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import androidx.lifecycle.C0076a0;
import androidx.lifecycle.Lifecycle$Event;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public class lr0 extends Fragment {

    /* renamed from: a1 */
    public static final C0831a0 f58161a1 = new C0831a0(null);

    /* renamed from: a0 */
    public jl0 f58162a0;

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: lr0$a0 */
    public static final class C0831a0 {
        public /* synthetic */ C0831a0(AbstractC1120qr abstractC1120qr) {
            this();
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final void dispatch$lifecycle_runtime_release(Activity activity, Lifecycle$Event lifecycle$Event) {
            C0076a0 c0076a0Mo209830a5;
            t60.m214695b6(activity, "activity");
            t60.m214695b6(lifecycle$Event, "event");
            if (!(activity instanceof ka0) || (c0076a0Mo209830a5 = ((ka0) activity).mo209830a5()) == null) {
                return;
            }
            c0076a0Mo209830a5.m210234g1(lifecycle$Event);
        }

        public final lr0 get(Activity activity) {
            t60.m214695b6(activity, "<this>");
            Fragment fragmentFindFragmentByTag = activity.getFragmentManager().findFragmentByTag("androidx.lifecycle.LifecycleDispatcher.report_fragment_tag");
            t60.m214693b4(fragmentFindFragmentByTag, "null cannot be cast to non-null type androidx.lifecycle.ReportFragment");
            return (lr0) fragmentFindFragmentByTag;
        }

        public final void injectIfNeededIn(Activity activity) {
            t60.m214695b6(activity, "activity");
            if (Build.VERSION.SDK_INT >= 29) {
                C0832a1.Companion.registerIn(activity);
            }
            FragmentManager fragmentManager = activity.getFragmentManager();
            if (fragmentManager.findFragmentByTag("androidx.lifecycle.LifecycleDispatcher.report_fragment_tag") == null) {
                fragmentManager.beginTransaction().add(new lr0(), "androidx.lifecycle.LifecycleDispatcher.report_fragment_tag").commit();
                fragmentManager.executePendingTransactions();
            }
        }

        private C0831a0() {
        }

        public static /* synthetic */ void get$annotations(Activity activity) {
        }
    }

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: lr0$a1 */
    public static final class C0832a1 implements Application.ActivityLifecycleCallbacks {
        public static final a0 Companion = new a0(null);

        /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
        /* renamed from: lr0$a1$a0 */
        public static final class a0 {
            public /* synthetic */ a0(AbstractC1120qr abstractC1120qr) {
                this();
            }

            public final void registerIn(Activity activity) {
                t60.m214695b6(activity, "activity");
                activity.registerActivityLifecycleCallbacks(new C0832a1());
            }

            private a0() {
            }
        }

        public static final void registerIn(Activity activity) {
            Companion.registerIn(activity);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(Activity activity, Bundle bundle) {
            t60.m214695b6(activity, "activity");
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(Activity activity) {
            t60.m214695b6(activity, "activity");
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(Activity activity) {
            t60.m214695b6(activity, "activity");
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPostCreated(Activity activity, Bundle bundle) {
            t60.m214695b6(activity, "activity");
            lr0.f58161a1.dispatch$lifecycle_runtime_release(activity, Lifecycle$Event.ON_CREATE);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPostResumed(Activity activity) {
            t60.m214695b6(activity, "activity");
            lr0.f58161a1.dispatch$lifecycle_runtime_release(activity, Lifecycle$Event.ON_RESUME);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPostStarted(Activity activity) {
            t60.m214695b6(activity, "activity");
            lr0.f58161a1.dispatch$lifecycle_runtime_release(activity, Lifecycle$Event.ON_START);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPreDestroyed(Activity activity) {
            t60.m214695b6(activity, "activity");
            lr0.f58161a1.dispatch$lifecycle_runtime_release(activity, Lifecycle$Event.ON_DESTROY);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPrePaused(Activity activity) {
            t60.m214695b6(activity, "activity");
            lr0.f58161a1.dispatch$lifecycle_runtime_release(activity, Lifecycle$Event.ON_PAUSE);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPreStopped(Activity activity) {
            t60.m214695b6(activity, "activity");
            lr0.f58161a1.dispatch$lifecycle_runtime_release(activity, Lifecycle$Event.ON_STOP);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(Activity activity) {
            t60.m214695b6(activity, "activity");
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
            t60.m214695b6(activity, "activity");
            t60.m214695b6(bundle, "bundle");
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(Activity activity) {
            t60.m214695b6(activity, "activity");
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(Activity activity) {
            t60.m214695b6(activity, "activity");
        }
    }

    /* renamed from: a0 */
    public final void m213926a0(Lifecycle$Event lifecycle$Event) {
        if (Build.VERSION.SDK_INT < 29) {
            Activity activity = getActivity();
            t60.m214694b5(activity, "activity");
            f58161a1.dispatch$lifecycle_runtime_release(activity, lifecycle$Event);
        }
    }

    @Override // android.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        m213926a0(Lifecycle$Event.ON_CREATE);
    }

    @Override // android.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        m213926a0(Lifecycle$Event.ON_DESTROY);
        this.f58162a0 = null;
    }

    @Override // android.app.Fragment
    public final void onPause() {
        super.onPause();
        m213926a0(Lifecycle$Event.ON_PAUSE);
    }

    @Override // android.app.Fragment
    public final void onResume() {
        super.onResume();
        jl0 jl0Var = this.f58162a0;
        if (jl0Var != null) {
            ((no0) jl0Var.f57345a0).m214134a1();
        }
        m213926a0(Lifecycle$Event.ON_RESUME);
    }

    @Override // android.app.Fragment
    public final void onStart() {
        super.onStart();
        jl0 jl0Var = this.f58162a0;
        if (jl0Var != null) {
            no0 no0Var = (no0) jl0Var.f57345a0;
            int i = no0Var.f58678a0 + 1;
            no0Var.f58678a0 = i;
            if (i == 1 && no0Var.f58681a3) {
                no0Var.f58683a5.m210234g1(Lifecycle$Event.ON_START);
                no0Var.f58681a3 = false;
            }
        }
        m213926a0(Lifecycle$Event.ON_START);
    }

    @Override // android.app.Fragment
    public final void onStop() {
        super.onStop();
        m213926a0(Lifecycle$Event.ON_STOP);
    }
}
