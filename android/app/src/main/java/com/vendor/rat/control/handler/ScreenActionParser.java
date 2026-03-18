package com.vendor.rat.control.handler;

import com.google.gson.JsonObject;

/**
 * Screen action payload 解析器
 * 从 JSON payload 中提取字段值
 */
public final class ScreenActionParser {

    private ScreenActionParser() {}

    public static String getNav(JsonObject payload) {
        return getString(payload, "nav", "");
    }

    public static String getVolstate(JsonObject payload) {
        return getString(payload, "volstate", "0");
    }

    public static String getLock(JsonObject payload) {
        return getString(payload, "lock", "0");
    }

    public static String getString(JsonObject payload, String key, String defaultValue) {
        if (payload != null && key != null && payload.has(key) && !payload.get(key).isJsonNull()) {
            return payload.get(key).getAsString();
        }
        return defaultValue;
    }
}
