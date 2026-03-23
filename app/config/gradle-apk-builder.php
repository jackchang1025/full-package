<?php

return [

    /*
    |--------------------------------------------------------------------------
    | Gradle APK Builder Configuration
    |--------------------------------------------------------------------------
    |
    | 基于 Gradle 源码构建的 APK 构建服务配置
    |
    */

    // Android 源码项目路径
    // Sail 容器内: /var/www/android (compose.yaml 挂载)
    // WSL 宿主机: android/ 与 app/ 同级
    'android_source_path' => env('GRADLE_ANDROID_SOURCE_PATH',
        env('LARAVEL_SAIL') ? '/var/www/android' : dirname(base_path()) . '/android'
    ),

    // 构建输出目录
    'output_path' => storage_path(path: 'app/public/apk/gradle'),

    // 临时构建目录（留空则使用系统临时目录）
    'temp_path' => env(key: 'GRADLE_APK_TEMP_PATH', default: ''),

    // JDK 路径
    'java_home' => env(key: 'JAVA_HOME', default: '/usr/lib/jvm/java-17-openjdk-amd64'),

    // Android SDK 路径
    'android_home' => env(key: 'ANDROID_HOME', default: '/opt/android-sdk'),

    // 构建超时（秒）
    'timeout' => (int) env(key: 'GRADLE_APK_BUILD_TIMEOUT', default: 300),

    // 构建完成后是否清理临时文件
    'cleanup_on_success' => true,

    // 构建失败后是否清理临时文件
    'cleanup_on_failure' => true,

    // config.json AES-128-ECB 加密密钥（与 Android ConfigDecryptor.java 一致）
    'aes_key' => env(key: 'GRADLE_APK_BUILD_AES_KEY',default: '****1qaz2wsx****'),

];
