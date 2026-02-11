<?php

declare(strict_types=1);

namespace App\Services\ApkBuilder;

/**
 * APK 构建相关共享常量
 */
final class ApkBuilderConstants
{
    /** 模板默认包名 */
    public const DEFAULT_PACKAGE = 'com.icontrol.protector';

    /** 核心 Smali 配置文件相对路径 */
    public const CONFIGS_SMALI_RELATIVE = 'smali/com/icontrol/protector/My_Configs.smali';

    /** 资产文件最小有效大小（字节），用于过滤损坏或空文件 */
    public const MIN_ASSET_FILE_SIZE = 100;

    /** Smali 源码目录列表（apktool 解包结构） */
    public const SMALI_DIRS = [
        'smali',
        'smali_classes2',
        'smali_classes3',
        'smali_classes4',
        'smali_classes5',
        'smali_classes6',
        'smali_classes7',
    ];

    /** 图标 drawable 目录列表 */
    public const DRAWABLE_DIRS = [
        'drawable',
        'drawable-hdpi',
        'drawable-mdpi',
        'drawable-xhdpi',
        'drawable-xxhdpi',
        'drawable-xxxhdpi',
    ];

    /** 命令心跳间隔（秒） */
    public const HEARTBEAT_INTERVAL_SEC = 10;

    /** 文件复制心跳间隔（每 N 个文件） */
    public const FILE_COPY_HEARTBEAT_INTERVAL = 100;

    /** 默认 Android SDK build-tools 路径 */
    public const DEFAULT_ANDROID_SDK_TOOLS = '/opt/android-sdk/build-tools/34.0.0';

    /** 工作目录内 APK 文件名 */
    public const APK_UNSIGNED = 'app-unsigned.apk';

    public const APK_ALIGNED = 'app-aligned.apk';

    public const APK_SIGNED = 'app-signed.apk';

    /** 进程轮询间隔（微秒），100ms */
    public const PROCESS_POLL_INTERVAL_US = 100_000;

    // ========== 文件路径常量 ==========

    /** AndroidManifest.xml 相对路径 */
    public const MANIFEST_PATH = '/AndroidManifest.xml';

    /** strings.xml 相对路径 */
    public const STRINGS_XML_PATH = '/res/values/strings.xml';

    /** public.xml 相对路径 */
    public const PUBLIC_XML_PATH = '/res/values/public.xml';

    /** apktool.yml 相对路径 */
    public const APKTOOL_YML_PATH = '/apktool.yml';

    /** assets 目录相对路径 */
    public const ASSETS_PATH = '/assets';

    /** xml 资源目录相对路径 */
    public const XML_DIR_PATH = '/res/xml';

    /** blackui 背景图相对路径 */
    public const BLACKUI_PATH = '/res/drawable/blackui.png';

    /** 默认图标文件名 */
    public const ICON_FILENAME = 'mylogo.png';

    /** 新图标文件名 */
    public const APP_ICON_FILENAME = 'app_icon.png';

    // ========== 默认 XML 内容 ==========

    /** 默认无障碍服务 XML */
    public const DEFAULT_ACCESSIBILITY_XML = '<?xml version="1.0" encoding="utf-8"?>'.
        '<accessibility-service xmlns:android="http://schemas.android.com/apk/res/android" '.
        'android:accessibilityEventTypes="typeAllMask" android:canRetrieveWindowContent="true"/>';

    // ========== ZipArchive 错误消息 ==========

    public const ZIP_ERROR_MESSAGES = [
        \ZipArchive::ER_EXISTS => '文件已存在',
        \ZipArchive::ER_INCONS => '压缩包不一致',
        \ZipArchive::ER_INVAL => '无效参数',
        \ZipArchive::ER_MEMORY => '内存分配失败',
        \ZipArchive::ER_NOENT => '文件不存在',
        \ZipArchive::ER_NOZIP => '不是有效的 ZIP 文件',
        \ZipArchive::ER_OPEN => '无法打开文件',
        \ZipArchive::ER_READ => '读取错误',
        \ZipArchive::ER_SEEK => '定位错误',
    ];
}
