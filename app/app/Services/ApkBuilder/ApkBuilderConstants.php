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
}
