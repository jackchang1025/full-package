package com.guard.wallet.utils;

import com.google.gson.Gson;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 本地化文本值工具类 — 从 locateValues.json 加载 key→text 映射。
 * <p>
 * vendor 源文件: f.java (com.guard.wallet.utils)
 * 重命名: f → LocateValuesUtils
 *   字段: a → locateValuesMap, b → loaded
 *   方法: a() → loadValues(), b(String) → getValue(String)
 * </p>
 */
public abstract class LocateValuesUtils {
    /** 已加载的 key→本地化文本映射 (vendor: f.a) */
    public static final ConcurrentHashMap<String, String> locateValuesMap = new ConcurrentHashMap<>();

    /** 标记是否已完成加载 (vendor: f.b) */
    public static final AtomicBoolean loaded = new AtomicBoolean(false);

    /**
     * 从文件系统加载 locateValues.json 到内存映射。
     * vendor: f.a()
     */
    public static void loadValues() {
        if (!locateValuesMap.isEmpty()) return;
        String basePath = SystemHelper.i0();
        if (basePath == null || basePath.isEmpty()) return;
        String filePath = basePath + "/locateValues.json";
        try {
            java.io.File file = new java.io.File(filePath);
            if (!file.exists()) return;
            String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
            if (content.isEmpty()) return;
            Type type = new com.google.gson.reflect.TypeToken<HashMap<String, String>>(){}.getType();
            HashMap<String, String> map = new Gson().fromJson(content, type);
            if (map != null && !map.isEmpty()) {
                locateValuesMap.putAll(map);
            }
        } catch (Exception e) {
            android.util.Log.e("LocateValuesUtils", "load error", e);
        }
    }

    /**
     * 根据 key 获取本地化文本值，若未加载则先触发加载。
     * vendor: f.b(String)
     *
     * @param key 文本键名
     * @return 对应文本值，不存在时返回空字符串
     */
    public static String getValue(String key) {
        if (key == null || key.isEmpty()) return "";
        try {
            if (locateValuesMap.isEmpty()) loadValues();
            String val = locateValuesMap.get(key);
            return val != null ? val : "";
        } catch (Exception e) {
            android.util.Log.e("LocateValuesUtils", "get error", e);
            return "";
        }
    }
}
