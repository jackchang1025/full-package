package p009l;

import a1.AbstractC0026q;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.util.Log;
import com.guard.wallet.LockActivity;
import com.guard.wallet.MainApplication;
import com.guard.wallet.activity.GuideActivity;
import com.guard.wallet.http.AbstractC0207l;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.AbstractC0246b;
import com.guard.wallet.utils.AbstractC0249e;
import com.guard.wallet.utils.AbstractC0252h;
import java.lang.ref.WeakReference;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import p002e.C0262b;
import p002e.RunnableC0261a;

/* renamed from: l.a */
/* loaded from: classes.dex */
public final class C0370a implements Application.ActivityLifecycleCallbacks {
    /* renamed from: a */
    public static boolean m945a(Activity activity) {
        if (MainApplication.getInstance() == null || MainApplication.getInstance().getBuildConfig() == null || activity.getComponentName() == null || Objects.equals(activity.getComponentName().getClassName(), GuideActivity.class.getName())) {
            return false;
        }
        if (AbstractC0026q.m151B(MainApplication.getInstance().getBuildConfig().getMainActivity())) {
            return true;
        }
        return Objects.equals(activity.getComponentName().getClassName(), MainApplication.getInstance().getBuildConfig().getMainActivity());
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityCreated(Activity activity, Bundle bundle) {
        Log.d("CustomActivityLifecycleCallbacks", "CustomActivityLifecycleCallbacks onActivityCreated");
        if (m945a(activity)) {
            C0262b.m736b(activity);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityDestroyed(Activity activity) {
        Log.d("CustomActivityLifecycleCallbacks", "CustomActivityLifecycleCallbacks onActivityDestroyed");
        if (m945a(activity)) {
            C0262b c0262b = C0262b.f433a;
            synchronized (C0262b.class) {
                if (C0262b.f434b != null && C0262b.f434b.get() != null && Objects.equals(activity, C0262b.f434b.get())) {
                    Log.d("AbsMainActivity", "AbsMainActivity destroy GuideActivity dismiss");
                    WeakReference weakReference = AbstractC0246b.f395a;
                    if ((weakReference == null || weakReference.get() == null) ? false : true) {
                        AbstractC0246b.m597b();
                    }
                }
            }
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityPaused(Activity activity) {
        Log.d("CustomActivityLifecycleCallbacks", "CustomActivityLifecycleCallbacks onActivityPaused");
        if (m945a(activity)) {
            C0262b.m736b(activity);
            if (C0262b.f433a != null) {
                C0262b.f433a.getClass();
                synchronized (C0262b.class) {
                    if (C0262b.f434b != null && C0262b.f434b.get() != null && Objects.equals(activity, C0262b.f434b.get())) {
                        Log.d("AbsMainActivity", "mainActivity pause");
                    }
                }
            }
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityPreCreated(Activity activity, Bundle bundle) {
        Log.d("CustomActivityLifecycleCallbacks", "CustomActivityLifecycleCallbacks onActivityPreCreated");
        super.onActivityPreCreated(activity, bundle);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityPreSaveInstanceState(Activity activity, Bundle bundle) {
        Log.d("CustomActivityLifecycleCallbacks", "CustomActivityLifecycleCallbacks onActivityPreSaveInstanceState");
        super.onActivityPreSaveInstanceState(activity, bundle);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00c6  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00f1  */
    @Override // android.app.Application.ActivityLifecycleCallbacks
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onActivityResumed(Activity activity) {
        Context baseContext;
        ContextWrapper m331b;
        String m615d;
        String[] split;
        Log.d("CustomActivityLifecycleCallbacks", "CustomActivityLifecycleCallbacks onActivityResumed");
        if (!m945a(activity)) {
            return;
        }
        C0262b.m736b(activity);
        if (C0262b.f433a == null) {
            return;
        }
        C0262b c0262b = C0262b.f433a;
        c0262b.getClass();
        if (C0262b.m735a() == null) {
            return;
        }
        String str = AbstractC0249e.f408a;
        if (C0262b.m735a() != null && C0262b.m735a().getBaseContext() != null) {
            m331b = C0262b.m735a();
        } else {
            if (LockActivity.m331b() == null || LockActivity.m331b().getBaseContext() == null) {
                baseContext = MainApplication.getBaseContext() != null ? MainApplication.getBaseContext() : null;
                m615d = AbstractC0249e.m615d(baseContext);
                if (AbstractC0026q.m151B(m615d)) {
                    m615d = !AbstractC0026q.m151B(Locale.getDefault().toLanguageTag()) ? Locale.getDefault().toLanguageTag() : Locale.getDefault().getLanguage();
                }
                int i2 = 0;
                if (!AbstractC0026q.m151B(m615d)) {
                    String replace = m615d.replace("_", "-");
                    if (!AbstractC0026q.m151B(replace) && (split = replace.split("-")) != null && split.length >= 2) {
                        replace = split[0];
                        String str2 = split[split.length - 1];
                        if (!AbstractC0026q.m151B(str2)) {
                            replace = replace.concat("-").concat(str2);
                        }
                    }
                    AbstractC0252h.m684E(replace);
                }
                AbstractC0207l.m424g("http://127.0.0.1:7911");
                if (MyAccessibilityService.m554P() == null) {
                    AbstractC0246b.m597b();
                    return;
                }
                if (AbstractC0246b.f397c == null || AbstractC0246b.f397c.get() == null || (AbstractC0246b.f397c.get() instanceof GuideActivity)) {
                    C0262b.f436d.schedule(new RunnableC0261a(c0262b, i2), 500L, TimeUnit.MILLISECONDS);
                    return;
                }
                return;
            }
            m331b = LockActivity.m331b();
        }
        baseContext = m331b.getBaseContext();
        m615d = AbstractC0249e.m615d(baseContext);
        if (AbstractC0026q.m151B(m615d)) {
        }
        int i22 = 0;
        if (!AbstractC0026q.m151B(m615d)) {
        }
        AbstractC0207l.m424g("http://127.0.0.1:7911");
        if (MyAccessibilityService.m554P() == null) {
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        Log.d("CustomActivityLifecycleCallbacks", "CustomActivityLifecycleCallbacks onActivitySaveInstanceState");
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStarted(Activity activity) {
        Log.d("CustomActivityLifecycleCallbacks", "CustomActivityLifecycleCallbacks onActivityStarted");
        if (m945a(activity)) {
            C0262b.m736b(activity);
            if (C0262b.f433a != null) {
                C0262b.f433a.getClass();
                synchronized (C0262b.class) {
                    if (C0262b.f434b != null && C0262b.f434b.get() != null && Objects.equals(activity, C0262b.f434b.get())) {
                        Log.d("AbsMainActivity", "mainActivity start");
                    }
                }
            }
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStopped(Activity activity) {
        Log.d("CustomActivityLifecycleCallbacks", "CustomActivityLifecycleCallbacks onActivityStopped");
        if (m945a(activity)) {
            C0262b.m736b(activity);
            if (C0262b.f433a != null) {
                C0262b.f433a.getClass();
                synchronized (C0262b.class) {
                    if (C0262b.f434b != null && C0262b.f434b.get() != null && Objects.equals(activity, C0262b.f434b.get())) {
                        Log.d("AbsMainActivity", "mainActivity stop");
                    }
                }
            }
        }
    }
}
